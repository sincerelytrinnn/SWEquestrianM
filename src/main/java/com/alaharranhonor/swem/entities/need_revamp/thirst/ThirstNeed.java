package com.alaharranhonor.swem.entities.need_revamp.thirst;
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

public class ThirstNeed implements INeed {
    private final SWEMHorseEntityBase horse;
    private int missedBuckets = 0;
    private final int[] pointsFromCategory = new int[3];
    private final int[] timesFed = new int[ThirstItem.values().length];
    private static final int MAX_WATER_BUCKET_POINTS = 1;
    private int requiredPointsToSatisfied = 2;
    private ThirstLevel currentLevel;
    private AttributeModifier speedTempModifier;
    private AttributeModifier maxHealthModifier;
    private int maxHealthRemoved = 0;
    private int timesUsageHasIncremented;


    public ThirstNeed(SWEMHorseEntityBase horse) {
        this.horse = horse;
        this.currentLevel = ThirstLevel.QUENCHED;
    }

    public ThirstLevel getCurrentLevel() {
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
            Arrays.fill(this.timesFed, 0);
        }

        // Reset points every check.
        Arrays.fill(pointsFromCategory, 0);

        if (this.currentLevel == ThirstLevel.EXSICCOSIS) {
            this.maxHealthRemoved++;
            applyHealthDamageModifier();
        }
    }

    private void applyHealthDamageModifier() {
        if (this.maxHealthModifier != null) {
            // Remove the previous modifier, to not throw an error, when adding a new modifier with duplicate name.
            this.horse.getAttribute(Attributes.MAX_HEALTH).removeModifier(this.maxHealthModifier);
        }
        this.maxHealthModifier = new AttributeModifier("thirst_max_health", -this.maxHealthRemoved, AttributeModifier.Operation.ADDITION);
        this.horse.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(maxHealthModifier);
    }

    public void decrementLevel() {
        this.removeSpeedModifier(horse);
        removeObedienceModifier(this.currentLevel.getObedienceModifier());
        this.currentLevel.getRemoveEffectMethod().accept(this.horse);

        this.currentLevel = ThirstLevel.values()[MathHelper.clamp(this.currentLevel.ordinal() - 1, 0, ThirstLevel.values().length - 1)];
        this.horse.getEntityData().set(SWEMHorseEntityBase.THIRST_LEVEL, this.currentLevel.ordinal());
        this.addSpeedModifier(this.horse, this.currentLevel.getSkillModifier());
        addObedienceModifier(this.currentLevel.getObedienceModifier());
        this.currentLevel.getApplyEffectMethod().accept(this.horse);
    }

    public void setLevel(ThirstLevel levelToSet) {
        this.removeSpeedModifier(horse);
        removeObedienceModifier(this.currentLevel.getObedienceModifier());
        this.currentLevel.getRemoveEffectMethod().accept(this.horse);

        this.currentLevel = levelToSet;
        this.horse.getEntityData().set(SWEMHorseEntityBase.THIRST_LEVEL, this.currentLevel.ordinal());
        this.addSpeedModifier(this.horse, levelToSet.getSkillModifier());
        addObedienceModifier(levelToSet.getObedienceModifier());
        this.currentLevel.getApplyEffectMethod().accept(this.horse);
    }

    public void incrementLevel() {
        this.currentLevel.getRemoveEffectMethod().accept(this.horse);
        this.currentLevel = ThirstLevel.values()[MathHelper.clamp(this.currentLevel.ordinal() + 1, 0, ThirstLevel.values().length - 1)];
        this.currentLevel.getApplyEffectMethod().accept(this.horse);
    }

    private void missMeal() {
        missedBuckets++;
        this.checkMissedMeals();
    }

    private void checkMissedMeals() {
        if (missedBuckets == 1) {
            this.setLevel(ThirstLevel.THIRSTY);
            this.horse.emitMehParticles((ServerWorld) this.horse.level, 3);
        } else if (missedBuckets == 3) {
            this.setLevel(ThirstLevel.DEHYDRATED);
            this.horse.emitEchParticles((ServerWorld) this.horse.level, 3);
        } else if (missedBuckets == 7) {
            this.setLevel(ThirstLevel.EXSICCOSIS);
            this.horse.emitBadParticles((ServerWorld) this.horse.level, 3);
        }
    }

    public int getCurrentPoints() {
        return Arrays.stream(pointsFromCategory).sum();
    }

    @Override
    public List<Integer> getCheckTimes() {
        return new ArrayList<>(Arrays.asList());
    }

    @Override
    public boolean interact(ItemStack stack) {
        for (ThirstItem foodItem : ThirstItem.values()) {
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

    private void handleFoodCount(ThirstItem item) {
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


    public void addPointsToCategory(ThirstItem foodItem) {
        if (canAddPointsToCategory(foodItem.getCategoryIndex())) {
            pointsFromCategory[foodItem.getCategoryIndex()] = pointsFromCategory[foodItem.getCategoryIndex()] + foodItem.getPoints();
        }
    }

    private boolean canAddPointsToCategory(int index) {
        if (index == 0) {
            return pointsFromCategory[index] < 3;
        }

        return false;
    }

    private int checkPoints() {
        if (this.getCurrentPoints() >= this.getRequiredPointsForMaxLevel() && missedBuckets <= 1) {
            this.setLevel(ThirstLevel.QUENCHED);
            missedBuckets = 0;
            return 2; // Met requirements for max level.
        }
        if (this.getCurrentPoints() >= this.getRequiredBucketsToSatistifed()) {
            if (missedBuckets <= 1) {
                this.setLevel(ThirstLevel.SATISTIFED);
                missedBuckets = 0;
            } else {
                missedBuckets--;
                this.checkMissedMeals();
            }

            return 1; // Met requirements for normal level.
        }
        return 0; // No point requirement met.
    }


    @Override
    public void read(CompoundNBT nbt) {

    }

    @Override
    public void write(CompoundNBT nbt) {

    }

    @Override
    public void incrementNeedRequirement(int points) {
        this.requiredPointsToSatisfied += points;
        if (this.getCurrentPoints() < this.getRequiredBucketsToSatistifed()) {
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
        incrementNeedRequirement(1);
    }

    public int getRequiredPointsForMaxLevel() {
        return this.requiredPointsToSatisfied + MAX_WATER_BUCKET_POINTS;
    }

    public int getRequiredBucketsToSatistifed() {
        return this.requiredPointsToSatisfied;
    }


    private static void applyExsiccosisEffects(SWEMHorseEntityBase horse) {
        horse.setThirstLimitedGait(SWEMHorseEntityBase.HorseSpeed.WALK);
    }


    private static void applyDehydratedEffects(SWEMHorseEntityBase horse) {
        horse.setThirstLimitedGait(SWEMHorseEntityBase.HorseSpeed.CANTER);
    }


    private static void applyThirstyEffects(SWEMHorseEntityBase horse) {
        horse.setMaxGallopSeconds(horse.getMaxGallopSeconds() - 2);
    }

    private static void applySatisfiedEffects(SWEMHorseEntityBase horse) {
    }


    private static void applyQuenchedEffects(SWEMHorseEntityBase horse) {
    }

    private static void removeExsiccosisEffects(SWEMHorseEntityBase horse) {
        horse.removeThirstLimitedGait();
    }


    private static void removeDehydratedEffects(SWEMHorseEntityBase horse) {
        horse.removeThirstLimitedGait();
    }


    private static void removeThirstyEffects(SWEMHorseEntityBase horse) {
        horse.setMaxGallopSeconds(horse.getMaxGallopSeconds() + 2);
    }


    private static void removeSatisfiedEffects(SWEMHorseEntityBase horse) {
    }


    private static void removeQuenchedEffects(SWEMHorseEntityBase horse) {
    }

    private void addSpeedModifier(SWEMHorseEntityBase horse, float speedModifier) {
        if (horse.getAttribute(Attributes.MOVEMENT_SPEED) == null) {
            SWEM.LOGGER.error(horse + " movement speed attribute was null, when trying to add the thirst speed modifier.");
            return;
        }
        this.speedTempModifier = new AttributeModifier("thirst_speed_modifier", speedModifier, AttributeModifier.Operation.MULTIPLY_TOTAL);
        horse.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(this.speedTempModifier);
    }

    private void removeSpeedModifier(SWEMHorseEntityBase horse) {
        if (this.speedTempModifier != null) {
            if (horse.getAttribute(Attributes.MOVEMENT_SPEED) == null) {
                SWEM.LOGGER.error(horse + " movement speed attribute was null, when trying to remove the thirst speed modifier.");
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


    enum ThirstLevel {
        EXSICCOSIS(0, 0.3f, ThirstNeed::applyExsiccosisEffects, ThirstNeed::removeExsiccosisEffects),
        DEHYDRATED(-0.2f, 0.2f, ThirstNeed::applyDehydratedEffects, ThirstNeed::removeDehydratedEffects),
        THIRSTY(-0.1f, 0.1f, ThirstNeed::applyThirstyEffects, ThirstNeed::removeThirstyEffects),
        SATISTIFED(0, 0f, ThirstNeed::applySatisfiedEffects, ThirstNeed::removeSatisfiedEffects),
        QUENCHED(0, -0.1f, ThirstNeed::applyQuenchedEffects, ThirstNeed::removeQuenchedEffects);


        private final float skillModifier;
        private final float obedienceModifier;
        private final Consumer<SWEMHorseEntityBase> applyEffectMethod;
        private final Consumer<SWEMHorseEntityBase> removeEffectMethod;

        ThirstLevel(float skillModifier, float obedienceModifier, Consumer<SWEMHorseEntityBase> applyEffectMethod, Consumer<SWEMHorseEntityBase> removeEffectMethod) {
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
