package com.alaharranhonor.swem.entities.need_revamp.hunger;
/*
 * All Rights Reserved
 *
 * Copyright (c) 2022, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.entities.need_revamp.INeed;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class HungerNeed implements INeed {

    private final SWEMHorseEntityBase horse;
    private int missedMeals = 0;
    private int[] pointsFromCategory = new int[3];
    private int[] timesFed = new int[FoodItem.values().length];
    private static final int MAX_TREAT_POINTS = 13;
    private static final int MAX_SWEET_FEED_POINTS = 112;
    private int requiredPointsToFed = 224;
    private HungerLevel currentLevel;
    private AttributeModifier speedTempModifier;
    private AttributeModifier maxHealthModifier;
    private int maxHealthRemoved = 0;
    private int timesUsageHasIncremented;


    public HungerNeed(SWEMHorseEntityBase horse) {
        this.horse = horse;
        this.currentLevel = HungerLevel.FULLY_FED;
    }

    public HungerLevel getCurrentLevel() {
        return currentLevel;
    }

    @Override
    public void check(int checkTime) {

        if (this.currentLevel.ordinal() >= 3) {
            this.maxHealthRemoved--;
            this.applyHealthDamageModifier();
            this.incrementNeedRequirement(16);
        }

        int levelChange = checkPoints();
        if (levelChange == 0) {
            this.missMeal();
        }

        // 7AM Reset amount of times fed.
        if (this.getCheckTimes().get(0) == checkTime) {
            this.removeOverfedEffect();
        }

        // Reset points every check.
        Arrays.fill(pointsFromCategory, 0);
        Arrays.fill(this.timesFed, 0);
        this.timesUsageHasIncremented = 0;
        this.requiredPointsToFed = 224;

        if (this.currentLevel == HungerLevel.STARVING) {
            this.maxHealthRemoved++;
            applyHealthDamageModifier();
        }
    }

    private void applyHealthDamageModifier() {
        if (this.maxHealthModifier != null) {
            // Remove the previous modifier, to not throw an error, when adding a new modifier with duplicate name.
            this.horse.getAttribute(Attributes.MAX_HEALTH).removeModifier(this.maxHealthModifier);
        }
        this.maxHealthModifier = new AttributeModifier("hunger_max_health", -this.maxHealthRemoved, AttributeModifier.Operation.ADDITION);
        this.horse.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(maxHealthModifier);
    }

    public void decrementLevel() {
        this.removeSpeedModifier(horse);
        removeObedienceModifier(this.currentLevel.getObedienceModifier());
        this.currentLevel.getRemoveEffectMethod().accept(this.horse);

        this.currentLevel = HungerLevel.values()[MathHelper.clamp(this.currentLevel.ordinal() - 1, 0, HungerLevel.values().length - 1)];
        this.horse.getEntityData().set(SWEMHorseEntityBase.HUNGER_LEVEL, this.currentLevel.ordinal());
        this.addSpeedModifier(this.horse, this.currentLevel.getSkillModifier());
        addObedienceModifier(this.currentLevel.getObedienceModifier());
        this.currentLevel.getApplyEffectMethod().accept(this.horse);
    }

    public void setLevel(HungerLevel levelToSet) {
        this.removeSpeedModifier(horse);
        removeObedienceModifier(this.currentLevel.getObedienceModifier());
        this.currentLevel.getRemoveEffectMethod().accept(this.horse);

        this.currentLevel = levelToSet;
        this.horse.getEntityData().set(SWEMHorseEntityBase.HUNGER_LEVEL, this.currentLevel.ordinal());
        this.addSpeedModifier(this.horse, levelToSet.getSkillModifier());
        addObedienceModifier(levelToSet.getObedienceModifier());
        this.currentLevel.getApplyEffectMethod().accept(this.horse);
    }

    public void incrementLevel() {
        this.currentLevel.getRemoveEffectMethod().accept(this.horse);
        this.currentLevel = HungerLevel.values()[MathHelper.clamp(this.currentLevel.ordinal() + 1, 0, HungerLevel.values().length - 1)];
        this.currentLevel.getApplyEffectMethod().accept(this.horse);
    }

    private void missMeal() {
        missedMeals++;
        if (missedMeals == 1) {
            this.setLevel(HungerLevel.HUNGRY);
            this.horse.emitMehParticles((ServerWorld) this.horse.level, 3);
        } else if (missedMeals == 3) {
            this.setLevel(HungerLevel.MALNOURISHED);
            this.horse.emitEchParticles((ServerWorld) this.horse.level, 3);
        } else if (missedMeals == 7) {
            this.setLevel(HungerLevel.STARVING);
            this.horse.emitBadParticles((ServerWorld) this.horse.level, 3);
        }
    }

    public int getCurrentPoints() {
        return Arrays.stream(pointsFromCategory).sum();
    }

    @Override
    public List<Integer> getCheckTimes() {
        return new ArrayList<>(Arrays.asList(1000, 20000));
    }

    @Override
    public boolean interact(ItemStack stack) {
        for (FoodItem foodItem : FoodItem.values()) {
            if (foodItem.getItem().test(stack)) {

                this.timesFed[foodItem.ordinal()] = this.timesFed[foodItem.ordinal()] + 1;
                handleFoodCount(foodItem);

                if (canAddPointsToCategory(foodItem.getCategoryIndex())) {
                    foodItem.getExtraEffectMethod().accept(this.horse, foodItem);
                    addPointsToCategory(foodItem);
                    checkPoints();
                }
                return true; // Return early, since we don't care about other items, since we already matched one.
            }
        }
        return false;
    }

    private void handleFoodCount(FoodItem item) {
        int timesFed = this.timesFed[item.ordinal()];
        if (timesFed <= item.getMin()) {
            this.horse.emitYayParticles((ServerWorld) this.horse.level, 3);
        } else if (timesFed <= item.getMax()) {
            this.horse.emitMehParticles((ServerWorld) this.horse.level, 3);
        } else {
            this.horse.emitBadParticles((ServerWorld) this.horse.level, 3);
            this.addOverfedEffect();
        }
    }


    public void addPointsToCategory(FoodItem foodItem) {
        if (canAddPointsToCategory(foodItem.getCategoryIndex())) {
            pointsFromCategory[foodItem.getCategoryIndex()] = pointsFromCategory[foodItem.getCategoryIndex()] + foodItem.getPoints();
        }
    }

    public boolean canAddPointsToCategory(int index) {
        if (index == 0) {
            return pointsFromCategory[index] < MAX_TREAT_POINTS;
        } else if (index == 1) {
            return pointsFromCategory[index] < this.getRequiredPointsForMaxLevel() - MAX_SWEET_FEED_POINTS;
        } else if (index == 2) {
            return pointsFromCategory[index] < MAX_SWEET_FEED_POINTS;
        }

        return false;
    }

    private int checkPoints() {
        if (this.getCurrentPoints() >= this.getRequiredPointsForMaxLevel() && missedMeals <= 1) {
            this.setLevel(HungerLevel.FULLY_FED);
            missedMeals = 0;
            return 2; // Met requirements for max level.
        }
        if (this.getCurrentPoints() >= this.getRequiredPointsToFed()) {
            if (missedMeals <= 1) {
                this.setLevel(HungerLevel.FED);
                missedMeals = 0;
            } else {
                missedMeals--;
            }

            return 1; // Met requirements for normal level.
        }
        return 0; // No point requirement met.
    }


    @Override
    public void read(CompoundNBT nbt) {
        this.timesFed = nbt.getIntArray("hungerTimesFed");
        this.timesFed = this.timesFed.length == 0 ? new int[FoodItem.values().length] : this.timesFed;
        this.pointsFromCategory = nbt.getIntArray("hungerPointsFromCategory");
        this.pointsFromCategory = this.pointsFromCategory.length == 0 ? new int[3] : this.pointsFromCategory;
        this.missedMeals = nbt.getInt("hungerMissedMeals");
        this.currentLevel = HungerLevel.values()[nbt.getInt("hungerCurrentLevel")];
        this.horse.getEntityData().set(SWEMHorseEntityBase.HUNGER_LEVEL, this.currentLevel.ordinal());
        this.currentLevel.getApplyEffectMethod().accept(this.horse);
        this.addSpeedModifier(this.horse, this.currentLevel.getSkillModifier());
        addObedienceModifier(this.currentLevel.getObedienceModifier());
        this.timesUsageHasIncremented = nbt.getInt("hungerTimesUsageHasIncremented");
        if (timesUsageHasIncremented > 0) {
            incrementNeedRequirement(timesUsageHasIncremented * 112);
        }
        this.maxHealthRemoved = nbt.getInt("hungerMaxHealthRemoved");

        applyHealthDamageModifier();
    }

    @Override
    public void write(CompoundNBT nbt) {
        nbt.putInt("hungerMissedMeals", missedMeals);
        nbt.putInt("hungerCurrentLevel", this.currentLevel.ordinal());
        nbt.putIntArray("hungerTimesFed", this.timesFed);
        nbt.putIntArray("hungerPointsFromCategory", this.pointsFromCategory);
        nbt.putInt("hungerTimesUsageHasIncremented", this.timesUsageHasIncremented);
        nbt.putInt("hungerMaxHealthRemoved", this.maxHealthRemoved);
    }

    @Override
    public void incrementNeedRequirement(int points) {
        this.requiredPointsToFed += points;
        if (this.getCurrentPoints() < this.getRequiredPointsToFed()) {
            this.decrementLevel();
        }
        // If points is less than requiredPointsFed now, decrement level and reapply effects.
    }

    @Override
    public void usageIncrement() {
        if (this.timesUsageHasIncremented >= 2) {
            return;
        }
        this.timesUsageHasIncremented++;
        incrementNeedRequirement(112);
    }

    public int getRequiredPointsForMaxLevel() {
        return this.requiredPointsToFed + MAX_SWEET_FEED_POINTS;
    }

    public int getRequiredPointsToFed() {
        return this.requiredPointsToFed;
    }


    private static void applyStarvingEffects(SWEMHorseEntityBase horse) {
        horse.setHungerLimitedGait(SWEMHorseEntityBase.HorseSpeed.WALK);
    }


    private static void applyMalnourishedEffects(SWEMHorseEntityBase horse) {
        horse.setHungerLimitedGait(SWEMHorseEntityBase.HorseSpeed.CANTER);
    }


    private static void applyHungryEffects(SWEMHorseEntityBase horse) {
        horse.setMaxGallopSeconds(horse.getMaxGallopSeconds() - 2);
    }

    private static void applyFedEffects(SWEMHorseEntityBase horse) {
    }


    private static void applyFullyFedEffects(SWEMHorseEntityBase horse) {
    }

    private static void removeStarvingEffects(SWEMHorseEntityBase horse) {
        horse.removeHungerLimitedGait();
    }


    private static void removeMalnourishedEffects(SWEMHorseEntityBase horse) {
        horse.removeHungerLimitedGait();
    }


    private static void removeHungryEffects(SWEMHorseEntityBase horse) {
        horse.setMaxGallopSeconds(horse.getMaxGallopSeconds() + 2);
    }


    private static void removeFedEffects(SWEMHorseEntityBase horse) {
    }


    private static void removeFullyFedEffects(SWEMHorseEntityBase horse) {
    }

    private void addSpeedModifier(SWEMHorseEntityBase horse, float speedModifier) {
        if (horse.getAttribute(Attributes.MOVEMENT_SPEED) == null) {
            SWEM.LOGGER.error(horse + " movement speed attribute was null, when trying to add the hunger speed modifier.");
            return;
        }
        this.speedTempModifier = new AttributeModifier("hunger_speed_modifier", speedModifier, AttributeModifier.Operation.MULTIPLY_TOTAL);
        horse.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(this.speedTempModifier);
    }

    private void removeSpeedModifier(SWEMHorseEntityBase horse) {
        if (this.speedTempModifier != null) {
            if (horse.getAttribute(Attributes.MOVEMENT_SPEED) == null) {
                SWEM.LOGGER.error(horse + " movement speed attribute was null, when trying to remove the hunger speed modifier.");
            }
            horse.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(this.speedTempModifier);
        }
        this.speedTempModifier = null;
    }

    private void addObedienceModifier(float obedienceModifier) {
        this.horse.addObedienceModifier(obedienceModifier);
    }

    private void removeObedienceModifier(float obedienceModifier) {
        horse.removeObedienceModifier(obedienceModifier);
    }

    private void addOverfedEffect() {
        addObedienceModifier(0.15f);
    }

    private void removeOverfedEffect() {
        this.horse.removeEffect(Effects.CONFUSION);
        removeObedienceModifier(0.15f);
    }


    public enum HungerLevel {
        STARVING(0, 0.3f, HungerNeed::applyStarvingEffects, HungerNeed::removeStarvingEffects),
        MALNOURISHED(-0.2f, 0.2f, HungerNeed::applyMalnourishedEffects, HungerNeed::removeMalnourishedEffects),
        HUNGRY(-0.1f, 0.1f, HungerNeed::applyHungryEffects, HungerNeed::removeHungryEffects),
        FED(0, 0f, HungerNeed::applyFedEffects, HungerNeed::removeFedEffects),
        FULLY_FED(0, -0.1f, HungerNeed::applyFullyFedEffects, HungerNeed::removeFullyFedEffects);


        private final float skillModifier;
        private final float obedienceModifier;
        private final Consumer<SWEMHorseEntityBase> applyEffectMethod;
        private final Consumer<SWEMHorseEntityBase> removeEffectMethod;

        HungerLevel(float skillModifier, float obedienceModifier, Consumer<SWEMHorseEntityBase> applyEffectMethod, Consumer<SWEMHorseEntityBase> removeEffectMethod) {
            this.skillModifier = skillModifier;
            this.obedienceModifier = obedienceModifier;
            this.applyEffectMethod = applyEffectMethod;
            this.removeEffectMethod = removeEffectMethod;
        }

        public float getSkillModifier() {
            return skillModifier;
        }

        public float getObedienceModifier() {
            return obedienceModifier;
        }

        public Consumer<SWEMHorseEntityBase> getApplyEffectMethod() {
            return applyEffectMethod;
        }

        public Consumer<SWEMHorseEntityBase> getRemoveEffectMethod() {
            return removeEffectMethod;
        }
    }
}
