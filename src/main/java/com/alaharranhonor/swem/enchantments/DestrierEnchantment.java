package com.alaharranhonor.swem.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import java.util.Map;
import java.util.Random;

import net.minecraft.enchantment.Enchantment.Rarity;

public class DestrierEnchantment extends Enchantment {
	public DestrierEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
		super(rarityIn, typeIn, slots);
	}

	/**
	 * Returns the minimum level that the enchantment can have.
	 */
	@Override
	public int getMinLevel() {
		return 1;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel() {
		return 1;
	}

	/**
	 * Whenever an entity that has this enchantment on one of its associated items is damaged this method will be called.
	 *
	 * @param user
	 * @param attacker
	 * @param level
	 */
	@Override
	public void onUserHurt(LivingEntity user, Entity attacker, int level) {
		Random random = user.getRNG();
		Map.Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomItemWithEnchantment(this, user);
		if (shouldHit(random)) {
			if (attacker != null) {
				attacker.attackEntityFrom(DamageSource.causeThornsDamage(user), (float)getDamage( random));
			}

			if (entry != null) {
				entry.getValue().damageItem(2, user, (livingEntity) -> {
					livingEntity.sendBreakAnimation(entry.getKey());
				});
			}
		}
	}

	/**
	 * Calculates the damage protection of the enchantment based on level and damage source passed.
	 *
	 * @param level
	 * @param source
	 */
	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		int actualLevel = 2;
		int actualDamagerModifier = actualLevel;
		if (source.canHarmInCreative())
		{
			return 0;

		}
		// Since we have Blast Protection;
		if (source.isExplosion())
		{
			actualDamagerModifier += actualLevel * 2;
		}
		// Since we have Projectile Protection
		if (source.isProjectile())
		{
			actualDamagerModifier += actualLevel * 2;
		}

		return actualDamagerModifier;
	}

	public static boolean shouldHit(Random rnd) {
		return rnd.nextFloat() < 0.15F;

	}

	public static int getDamage(Random rnd) {
		return 1 + rnd.nextInt(4);
	}
}
