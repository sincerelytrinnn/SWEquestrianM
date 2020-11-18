package com.alaharranhonor.swem.items.potions;

import com.alaharranhonor.swem.util.initialization.SWEMItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class CantazariteBrewingRecipe implements IBrewingRecipe {
	@Override
	public boolean isInput(ItemStack input) {
		// TODO: Make the input check, work, for only water potions.
		return input.getTag().getString("Potion").equals("water");
	}

	@Override
	public boolean isIngredient(ItemStack ingredient) {
		return ingredient.getItem() == SWEMItems.CANTAZARITE_DYE.get();
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		return new ItemStack(SWEMItems.CANTAZARITE_POTION.get());
	}


}
