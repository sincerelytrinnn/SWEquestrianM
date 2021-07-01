package com.alaharranhonor.swem.armor;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class AmethystRidingBoots extends DiamondRidingBoots {
	public AmethystRidingBoots(String path, IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(path, materialIn, slot, builderIn);
	}

	@Override
	public void onCraftedBy(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		super.onCraftedBy(stack, worldIn, playerIn);
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		super.onArmorTick(stack, world, player);
		if (player.isCrouching()) return;
		Vector3d motion = player.getDeltaMovement();
		if (!player.isOnGround() && motion.y < 0.0D) {
			player.setDeltaMovement(motion.multiply(1.0D, 0.7D, 1.0D));
		}
	}
}
