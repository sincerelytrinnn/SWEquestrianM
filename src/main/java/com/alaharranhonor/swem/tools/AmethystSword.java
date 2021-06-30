package com.alaharranhonor.swem.tools;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.MathHelper;

import net.minecraft.item.Item.Properties;

public class AmethystSword extends SwordItem {
	public AmethystSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
	}

	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.applyKnockback((float)3 * 0.5F, (double) MathHelper.sin( attacker.rotationYaw * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(attacker.rotationYaw * ((float)Math.PI / 180F))));
		return super.hitEntity(stack, target, attacker);
	}
}
