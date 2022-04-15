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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HungerNeed implements INeed {

	private SWEMHorseEntityBase horse;
	private int missedMeals = 0;
	private int[] pointsFromCategory = new int[3];
	private int requiredPointsToFed = 224;
	private HungerLevel currentLevel;

	public HungerNeed(SWEMHorseEntityBase horse) {
		this.horse = horse;
	}

	@Override
	public void check(int checkTime) {
		if (getCheckTimes().get(0) == checkTime) {
			missedMeals++;
			//currentLevel - 1;
		} else {
			//check points

			// if level needs to change
				// reset effects
				currentLevel.removeEffectMethod.run();
				//set current level.
				// if -1 then 	missedMeals++;
				// currentlevel +- 1

				// apply new effects.
				currentLevel.applyEffectMethod.run();
		}

		// Reset points every check.
		Arrays.fill(pointsFromCategory, 0);
	}


	public int getCurrentPoints() {
		return Arrays.stream(pointsFromCategory).sum();
	}

	@Override
	public List<Integer> getCheckTimes() {
		return new ArrayList<>(Arrays.asList(1000, 20000));
	}

	@Override
	public void interact(ItemStack stack) {
		for (FoodItem foodItem : FoodItem.values()) {
			/*if (stack.getItem() == foodItem.getItem()) {
				foodItem.getExtraEffectMethod.accept(this.horse, foodItem);
				// Add to points and call checkPoints();
			}*/
		}
	}

	private void checkPoints() {
		// If enough points to increment, reset missedMeal value.
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
		// If points is less than requiredPointsFed now, decrement level and reapply effects.
	}


	private static void applyStarvingEffects() {

	}


	private static void applyMalnourishedEffects() {

	}


	private static void applyHungryEffects() {

	}


	private static void applyFedEffects() {

	}


	private static void applyFullyFedEffects() {

	}

	private static void removeStarvingEffects() {

	}


	private static void removeMalnourishedEffects() {

	}


	private static void removeHungryEffects() {

	}


	private static void removeFedEffects() {

	}


	private static void removeFullyFedEffects() {

	}


	enum HungerLevel {
		STARVING(1.0f, 0.7f, HungerNeed::applyStarvingEffects, HungerNeed::removeStarvingEffects);


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
