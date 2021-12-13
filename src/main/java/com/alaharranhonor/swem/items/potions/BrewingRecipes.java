package com.alaharranhonor.swem.items.potions;


/*
 * All Rights Reserved
 *
 * Copyright (c) 2021, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class BrewingRecipes {

	public static class CantazariteBrewingRecipe implements IBrewingRecipe {
		@Override
		public boolean isInput(ItemStack input) {
			// TODO: Make the input check, work, for only water potions.
			return PotionUtils.getPotion(input) == Potions.WATER;
		}

		@Override
		public boolean isIngredient(ItemStack ingredient) {
			return ingredient.getItem() == SWEMItems.CANTAZARITE_DYE.get();
		}

		@Override
		public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
			if (!isInput(input)) return ItemStack.EMPTY;
			if (!isIngredient(ingredient)) return ItemStack.EMPTY;
			return new ItemStack(SWEMItems.CANTAZARITE_POTION.get());
		}


	}

	public static class RainbowChicPotion implements IBrewingRecipe {
		@Override
		public boolean isInput(ItemStack input) {
			// TODO: Make the input check, work, for only water potions.
			return PotionUtils.getPotion(input) == Potions.WATER;
		}

		@Override
		public boolean isIngredient(ItemStack ingredient) {
			return ingredient.getItem() == SWEMItems.RAINBOW_EGG.get();
		}

		@Override
		public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
			if (!isInput(input)) return ItemStack.EMPTY;
			if (!isIngredient(ingredient)) return ItemStack.EMPTY;
			return new ItemStack(SWEMItems.RAINBOW_CHIC.get());
		}




	}
}
