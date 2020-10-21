package com.alaharranhonor.swem.armor;

import com.alaharranhonor.swem.enchantments.DestrierEnchantment;
import com.alaharranhonor.swem.enchantments.UpstepEnchantment;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GoldRidingBoots extends IronRidingBoots {
	public GoldRidingBoots(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		super.onCreated(stack, worldIn, playerIn);
	}

	/**
	 * Called to tick armor in the armor slot. Override to do something
	 *
	 * @param stack
	 * @param world
	 * @param player
	 */
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (world.getBlockState(player.getPosition().down()) == Blocks.WATER.getDefaultState()) {

		}
	}
}
