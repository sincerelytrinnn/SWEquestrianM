package com.alaharranhonor.swem.enchantments;

import com.alaharranhonor.swem.items.Gallaxorium;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class GalaxyCoatEnchantment extends Enchantment {
	public GalaxyCoatEnchantment(Rarity p_i46731_1_, EnchantmentType p_i46731_2_, EquipmentSlotType... p_i46731_3_) {
		super(p_i46731_1_, p_i46731_2_, p_i46731_3_);
	}

	@Override
	public int getMinLevel() {
		return 1;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public int getMinCost(int p_77321_1_) {
		return 1;
	}

	@Override
	protected boolean checkCompatibility(Enchantment p_77326_1_) {
		return false;
	}

	@Override
	public boolean canEnchant(ItemStack p_92089_1_) {
		return p_92089_1_.getItem() instanceof Gallaxorium;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return stack.getItem() instanceof Gallaxorium;
	}

	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}

	@Override
	public boolean isDiscoverable() {
		return false;
	}
}

