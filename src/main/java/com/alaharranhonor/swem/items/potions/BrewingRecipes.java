package com.alaharranhonor.swem.items.potions;

import com.alaharranhonor.swem.util.initialization.SWEMItems;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class BrewingRecipes {

	public static class CantazariteBrewingRecipe implements IBrewingRecipe {
		@Override
		public boolean isInput(ItemStack input) {
			// TODO: Make the input check, work, for only water potions.
			return PotionUtils.getPotionFromItem(input) == Potions.WATER;
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
			return PotionUtils.getPotionFromItem(input) == Potions.WATER;
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
