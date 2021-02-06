package com.alaharranhonor.swem.armor;

import com.alaharranhonor.swem.enchantments.UpstepEnchantment;
import com.alaharranhonor.swem.items.SWEMArmorItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.function.Supplier;

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
	public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		stack.addEnchantment(new UpstepEnchantment(Enchantment.Rarity.RARE, EnchantmentType.ARMOR_FEET, new EquipmentSlotType[] {EquipmentSlotType.FEET} ), 1);
		super.onCreated(stack, worldIn, playerIn);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return false;
	}

}
