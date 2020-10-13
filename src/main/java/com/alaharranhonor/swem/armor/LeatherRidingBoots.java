package com.alaharranhonor.swem.armor;

import com.alaharranhonor.swem.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LeatherRidingBoots extends ArmorItem {
	public LeatherRidingBoots(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	/**
	 * Called when item is crafted/smelted. Used only by maps so far.
	 *
	 * @param stack
	 * @param worldIn
	 * @param playerIn
	 */
	@Override
	public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		stack.addEnchantment(RegistryHandler.UPSTEP.get(), 1);
		super.onCreated(stack, worldIn, playerIn);
	}
}
