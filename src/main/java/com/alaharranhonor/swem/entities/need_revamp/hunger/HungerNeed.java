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
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class HungerNeed implements INeed {

	private final SWEMHorseEntityBase horse;
	private int missedMeals = 0;
	private final int[] pointsFromCategory = new int[3];
	private static final int MAX_TREAT_POINTS = 13;
	private static final int MAX_SWEET_FEED_POINTS = 13;
	private int requiredPointsToFed = 224;
	private HungerLevel currentLevel;
	private static AttributeModifier speedTempModifier;

	public HungerNeed(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.currentLevel = HungerLevel.FULLY_FED;
	}

	@Override
	public void check(int checkTime) {

		int levelChange = checkPoints();
		if (levelChange == 0) {
			this.missMeal();
		}

		// Reset points every check.
		Arrays.fill(pointsFromCategory, 0);
	}

	public void decrementLevel() {
		this.currentLevel.getRemoveEffectMethod().accept(this.horse, this.currentLevel);
		this.currentLevel = HungerLevel.values()[MathHelper.clamp(this.currentLevel.ordinal() - 1, 0, HungerLevel.values().length - 1)];
		this.currentLevel.getApplyEffectMethod().accept(this.horse, this.currentLevel);
	}

	public void setLevel(HungerLevel levelToSet) {
		this.currentLevel.getRemoveEffectMethod().accept(this.horse, this.currentLevel);
		this.currentLevel = levelToSet;
		this.currentLevel.getApplyEffectMethod().accept(this.horse, this.currentLevel);
	}

	public void incrementLevel() {
		this.currentLevel.getRemoveEffectMethod().accept(this.horse, this.currentLevel);
		this.currentLevel = HungerLevel.values()[MathHelper.clamp(this.currentLevel.ordinal() + 1, 0, HungerLevel.values().length - 1)];
		this.currentLevel.getApplyEffectMethod().accept(this.horse, this.currentLevel);
	}

	private void missMeal() {
		missedMeals++;
		if (missedMeals == 1) {
			this.setLevel(HungerLevel.HUNGRY);
		} else if (missedMeals == 3) {
			this.setLevel(HungerLevel.MALNOURISHED);
		} else if (missedMeals == 7) {
			this.setLevel(HungerLevel.STARVING);
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
				foodItem.getExtraEffectMethod().accept(this.horse, foodItem);
				addPointsToCategory(foodItem);
				checkPoints();
				return true; // Return early, since we don't care about other items, since we already matched one.
			}
		}
		return false;
	}

	public void addPointsToCategory(FoodItem foodItem) {
		if (canAddPointsToCategory(foodItem.getCategoryIndex())) {
			pointsFromCategory[foodItem.getCategoryIndex()] = pointsFromCategory[foodItem.getCategoryIndex()] + foodItem.getPoints();
		}
	}

	private boolean canAddPointsToCategory(int index) {
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

	}

	@Override
	public void write(CompoundNBT nbt) {

	}

	@Override
	public void incrementNeedRequirement() {
		this.requiredPointsToFed += 112;
		if (this.getCurrentPoints() < this.getRequiredPointsToFed()) {
			this.decrementLevel();

		}
		// If points is less than requiredPointsFed now, decrement level and reapply effects.
	}

	public int getRequiredPointsForMaxLevel() {
		return this.requiredPointsToFed + MAX_SWEET_FEED_POINTS;
	}

	public int getRequiredPointsToFed() {
		return this.requiredPointsToFed;
	}


	private static void applyStarvingEffects(SWEMHorseEntityBase horse, HungerLevel level) {
		// TODO: Limit the max gait to walk
		addSpeedModifier(horse, level.getSkillModifier());
		addObedienceModifier(horse, level.getObedienceModifier());
	}


	private static void applyMalnourishedEffects(SWEMHorseEntityBase horse, HungerLevel level) {
		// TODO: Limit the max gait to Canter
		addSpeedModifier(horse, level.getSkillModifier());
		addObedienceModifier(horse, level.getObedienceModifier());
	}


	private static void applyHungryEffects(SWEMHorseEntityBase horse, HungerLevel level) {
		horse.setMaxGallopSeconds(horse.getMaxGallopSeconds() - 2);
		addSpeedModifier(horse, level.getSkillModifier());
		addObedienceModifier(horse, level.getObedienceModifier());
	}



	private static void applyFedEffects(SWEMHorseEntityBase horse, HungerLevel level) {
		addSpeedModifier(horse, level.getSkillModifier());
		addObedienceModifier(horse, level.getObedienceModifier());
	}


	private static void applyFullyFedEffects(SWEMHorseEntityBase horse, HungerLevel level) {
		addSpeedModifier(horse, level.getSkillModifier());
		addObedienceModifier(horse, level.getObedienceModifier());
	}

	private static void removeStarvingEffects(SWEMHorseEntityBase horse, HungerLevel level) {
		// Note: Don't remove the gait cap here, since it will be overwritten by the apply methods.
		removeSpeedModifier(horse);
		removeObedienceModifier(horse, level.getObedienceModifier());
	}


	private static void removeMalnourishedEffects(SWEMHorseEntityBase horse, HungerLevel level) {
		// Note: Don't remove the gait cap here, since it will be overwritten by the apply methods.
		removeSpeedModifier(horse);
		removeObedienceModifier(horse, level.getObedienceModifier());
	}


	private static void removeHungryEffects(SWEMHorseEntityBase horse, HungerLevel level) {
		horse.setMaxGallopSeconds(horse.getMaxGallopSeconds() + 2);
		removeSpeedModifier(horse);
		removeObedienceModifier(horse, level.getObedienceModifier());
	}


	private static void removeFedEffects(SWEMHorseEntityBase horse, HungerLevel level) {
		removeSpeedModifier(horse);
		removeObedienceModifier(horse, level.getObedienceModifier());
	}


	private static void removeFullyFedEffects(SWEMHorseEntityBase horse, HungerLevel level) {
		removeSpeedModifier(horse);
		removeObedienceModifier(horse, level.getObedienceModifier());
	}

	private static void addSpeedModifier(SWEMHorseEntityBase horse, float speedModifier) {
		if (horse.getAttribute(Attributes.MOVEMENT_SPEED) == null) {
			SWEM.LOGGER.error(horse + " movement speed attribute was null, when trying to add the hunger speed modifier.");
			return;
		}
		speedTempModifier = new AttributeModifier("hunger_speed_modifier", speedModifier, AttributeModifier.Operation.MULTIPLY_BASE);
		horse.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(speedTempModifier);
	}

	private static void removeSpeedModifier(SWEMHorseEntityBase horse) {
		if (speedTempModifier != null) {
			if (horse.getAttribute(Attributes.MOVEMENT_SPEED) == null) {
				SWEM.LOGGER.error(horse + " movement speed attribute was null, when trying to remove the hunger speed modifier.");
			}
			horse.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(speedTempModifier);
		}
		speedTempModifier = null;
	}

	private static void addObedienceModifier(SWEMHorseEntityBase horse, float obedienceModifier) {
		// TODO: Add the obedience modifier to the horse, once the system is setup on the horse.
		// horse.setObedienceModifier(horse.getObedienceModifier() + obedienceModifier);
	}

	private static void removeObedienceModifier(SWEMHorseEntityBase horse, float obedienceModifier) {
		// TODO: Add the obedience modifier to the horse, once the system is setup on the horse.
		// horse.setObedienceModifier(horse.getObedienceModifier() - obedienceModifier);
	}


	enum HungerLevel {
		STARVING(1.0f, 0.7f, HungerNeed::applyStarvingEffects, HungerNeed::removeStarvingEffects),
		MALNOURISHED(0.8f, 0.8f, HungerNeed::applyMalnourishedEffects, HungerNeed::removeMalnourishedEffects),
		HUNGRY(0.9f, 0.9f, HungerNeed::applyHungryEffects, HungerNeed::removeHungryEffects),
		FED(1.0f, 1f, HungerNeed::applyFedEffects, HungerNeed::removeFedEffects),
		FULLY_FED(1.0f, 1.1f, HungerNeed::applyFullyFedEffects, HungerNeed::removeFullyFedEffects);


		private final float skillModifier;
		private final float obedienceModifier;
		private final BiConsumer<SWEMHorseEntityBase, HungerLevel> applyEffectMethod;
		private final BiConsumer<SWEMHorseEntityBase, HungerLevel> removeEffectMethod;
		HungerLevel(float skillModifier, float obedienceModifier, BiConsumer<SWEMHorseEntityBase, HungerLevel> applyEffectMethod, BiConsumer<SWEMHorseEntityBase, HungerLevel> removeEffectMethod) {
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

		public BiConsumer<SWEMHorseEntityBase, HungerLevel> getApplyEffectMethod() {
			return applyEffectMethod;
		}

		public BiConsumer<SWEMHorseEntityBase, HungerLevel> getRemoveEffectMethod() {
			return removeEffectMethod;
		}
	}
}
