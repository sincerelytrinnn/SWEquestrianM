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

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.entities.need_revamp.INeed;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HungerNeed implements INeed {

	private final SWEMHorseEntityBase horse;
	private int missedMeals = 0;
	private int[] pointsFromCategory = new int[3];
	private static int MAX_TREAT_POINTS = 13;
	private static int MAX_SWEET_FEED_POINTS = 13;
	private int requiredPointsToFed = 224;
	private HungerLevel currentLevel;

	public HungerNeed(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.currentLevel = HungerLevel.FULLY_FED;
	}

	@Override
	public void check(int checkTime) {
		if (getCheckTimes().get(0) == checkTime) {
			this.missMeal();
		} else {
			int levelChange = checkPoints();
			if (levelChange == 0) {
				this.missMeal();
			}
		}

		// Reset points every check.
		Arrays.fill(pointsFromCategory, 0);
	}

	public void decrementLevel() {
		this.currentLevel.removeEffectMethod.run();
		this.currentLevel = HungerLevel.values()[MathHelper.clamp(this.currentLevel.ordinal() - 1, 0, HungerLevel.values().length - 1)];
		this.currentLevel.applyEffectMethod.run();
	}

	public void setLevel(HungerLevel levelToSet) {
		this.currentLevel.removeEffectMethod.run();
		this.currentLevel = levelToSet;
		this.currentLevel.applyEffectMethod.run();
	}

	public void incrementLevel() {
		this.currentLevel.removeEffectMethod.run();
		this.currentLevel = HungerLevel.values()[MathHelper.clamp(this.currentLevel.ordinal() + 1, 0, HungerLevel.values().length - 1)];
		this.currentLevel.applyEffectMethod.run();
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
				stack.shrink(1);
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
		if (this.getCurrentPoints() >= this.getRequiredPointsForMaxLevel()) {
			this.setLevel(HungerLevel.FULLY_FED);
			missedMeals = 0;
			return 2; // Met requirements for max level.
		}
		if (this.getCurrentPoints() >= this.getRequiredPointsToFed()) {
			this.setLevel(HungerLevel.FED);
			missedMeals = 0;

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


	private static void applyStarvingEffects() {
		System.out.println("Applying starving effects");
	}


	private static void applyMalnourishedEffects() {
		System.out.println("Applying mal effects");
	}


	private static void applyHungryEffects() {
		System.out.println("Applying hungry effects");
	}


	private static void applyFedEffects() {
		System.out.println("Applying fed effects");
	}


	private static void applyFullyFedEffects() {
		System.out.println("Applying fully effects");
	}

	private static void removeStarvingEffects() {
		System.out.println("remove starving effects");
	}


	private static void removeMalnourishedEffects() {
		System.out.println("remove mal effects");
	}


	private static void removeHungryEffects() {
		System.out.println("remove hungry effects");
	}


	private static void removeFedEffects() {
		System.out.println("remove fed effects");
	}


	private static void removeFullyFedEffects() {
		System.out.println("remove fully effects");
	}


	enum HungerLevel {
		STARVING(1.0f, 0.7f, HungerNeed::applyStarvingEffects, HungerNeed::removeStarvingEffects),
		MALNOURISHED(0.8f, 0.8f, HungerNeed::applyMalnourishedEffects, HungerNeed::removeMalnourishedEffects),
		HUNGRY(0.9f, 0.9f, HungerNeed::applyHungryEffects, HungerNeed::removeHungryEffects),
		FED(1.0f, 1f, HungerNeed::applyFedEffects, HungerNeed::removeFedEffects),
		FULLY_FED(1.0f, 1.1f, HungerNeed::applyFullyFedEffects, HungerNeed::removeFullyFedEffects);


		private float skillModifier;
		private float obedienceModifier;
		private Runnable applyEffectMethod;
		private Runnable removeEffectMethod;
		HungerLevel(float skillModifier, float obedienceModifier, Runnable applyEffectMethod, Runnable removeEffectMethod) {
			this.skillModifier = skillModifier;
			this.obedienceModifier = obedienceModifier;
			this.applyEffectMethod = applyEffectMethod;
			this.removeEffectMethod = removeEffectMethod;
		}
	}
}
