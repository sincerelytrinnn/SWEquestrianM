package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class RainbowDryFeedItem extends ItemBase {

	private int counter = 0;

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!isSelected) return;
		if (!worldIn.getLevelData().isRaining()) return;
		counter++;
		if (counter % 80 == 0) {
			PlayerEntity entity = (PlayerEntity) entityIn;
			ItemStack din = new ItemStack(SWEMItems.RAINBOW_DINDIN.get());
			entity.setItemInHand(Hand.MAIN_HAND, din);
		}
	}
}
