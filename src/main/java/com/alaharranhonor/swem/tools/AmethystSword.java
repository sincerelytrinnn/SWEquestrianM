package com.alaharranhonor.swem.tools;


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

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.MathHelper;

import net.minecraft.item.Item.Properties;

public class AmethystSword extends SwordItem {
	/**
	 * Instantiates a new Amethyst sword.
	 *
	 * @param tier           the tier
	 * @param attackDamageIn the attack damage in
	 * @param attackSpeedIn  the attack speed in
	 * @param builderIn      the builder in
	 */
	public AmethystSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.knockback((float)3 * 0.5F, (double) MathHelper.sin( attacker.yRot * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(attacker.yRot * ((float)Math.PI / 180F))));
		return super.hurtEnemy(stack, target, attacker);
	}
}
