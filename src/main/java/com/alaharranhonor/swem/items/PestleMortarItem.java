package com.alaharranhonor.swem.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.item.Item.Properties;

public class PestleMortarItem extends Item {
	
	public PestleMortarItem(final Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		return new ItemStack(itemStack.getItem());
	}

	@Override
	public boolean hasContainerItem() {
		return true;
	}
}
