package com.alaharranhonor.swem.enchantments;


/*
 * All Rights Reserved
 *
 * Copyright (c) 2021, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
	 * @param pUser     The user of the enchantment.
	 * @param pAttacker The entity that attacked the user.
	 * @param pLevel    The level of the enchantment.
	 */
	@Override
	public void doPostHurt(LivingEntity pUser, Entity pAttacker, int pLevel) {
		Random random = pUser.getRandom();
		Map.Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomItemWith(this, pUser);
		if (shouldHit(random)) {
			pAttacker.hurt(DamageSource.thorns(pUser), (float)getDamage( random));

			if (entry != null) {
				entry.getValue().hurtAndBreak(2, pUser, (livingEntity) -> livingEntity.broadcastBreakEvent(entry.getKey()));
			}
		}
	}

	/**
	 * Calculates the damage protection of the enchantment based on level and damage source passed.
	 *
	 * @param pLevel  The level of the enchantment being used.
	 * @param pSource The source of the damage.
	 */
	@Override
	public int getDamageProtection(int pLevel, DamageSource pSource) {
		int actualLevel = 2;
		int actualDamagerModifier = actualLevel;
		if (pSource.isBypassInvul())
		{
			return 0;

		}
		// Since we have Blast Protection;
		if (pSource.isExplosion())
		{
			actualDamagerModifier += actualLevel * 2;
		}
		// Since we have Projectile Protection
		if (pSource.isProjectile())
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
