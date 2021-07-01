package com.alaharranhonor.swem.armor;

import com.alaharranhonor.swem.enchantments.UpstepEnchantment;
import com.alaharranhonor.swem.items.SWEMArmorItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


import net.minecraft.item.Item.Properties;

public class LeatherRidingBoots extends SWEMArmorItem {
	public LeatherRidingBoots(String path, IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(path, materialIn, slot, builderIn);
	}

	/**
	 * Called when item is crafted/smelted. Used only by maps so far.
	 *
	 * @param stack
	 * @param worldIn
	 * @param playerIn
	 */
	@Override
	public void onCraftedBy(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		stack.enchant(new UpstepEnchantment(Enchantment.Rarity.RARE, EnchantmentType.ARMOR_FEET, new EquipmentSlotType[] {EquipmentSlotType.FEET} ), 1);
		super.onCraftedBy(stack, worldIn, playerIn);
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}

}
