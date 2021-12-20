package com.alaharranhonor.swem.items;


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

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

import net.minecraft.item.Item.Properties;
import net.minecraft.world.World;

public class MedicalItem extends Item {
	private float heal;
	private float xp;

	public MedicalItem(Properties properties, float heal, float xp) {
		super(properties);
		this.heal = heal;
		this.xp = xp;
	}

	@Override
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (!playerIn.level.isClientSide) {
			if (target instanceof SWEMHorseEntityBase) {
				SWEMHorseEntityBase horse = (SWEMHorseEntityBase) target;

				if (horse.getHealth() == horse.getMaxHealth()) {
					return ActionResultType.FAIL;
				}

				horse.heal(heal, xp);
				stack.shrink(1);
				return ActionResultType.CONSUME;
			} else {
				if (target.getType().getCategory() != EntityClassification.CREATURE && !(target instanceof PlayerEntity)) {
					return ActionResultType.FAIL;
				}
				if (target.getHealth() == target.getMaxHealth()) {
					return ActionResultType.FAIL;
				}

				target.heal(heal);
				stack.shrink(1);
				return ActionResultType.CONSUME;
			}
		}
		return ActionResultType.PASS;
	}



	/**
	 * How long it takes to use or consume an item
	 *
	 * @param pStack
	 */
	@Override
	public int getUseDuration(ItemStack pStack) {
		return 20;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 *
	 * @param pStack
	 */
	@Override
	public UseAction getUseAnimation(ItemStack pStack) {
		return UseAction.BOW;
	}

	/**
	 * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
	 * the Item before the action is complete.
	 *
	 * @param pStack
	 * @param pLevel
	 * @param pEntityLiving
	 */
	@Override
	public ItemStack finishUsingItem(ItemStack pStack, World pLevel, LivingEntity pEntityLiving) {
		pEntityLiving.heal(heal);
		pStack.shrink(1);
		return pStack;
	}

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
	 *
	 * @param pLevel
	 * @param pPlayer
	 * @param pHand
	 */
	@Override
	public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
		if (pPlayer.getHealth() == pPlayer.getMaxHealth()) {
			return ActionResult.fail(pPlayer.getItemInHand(pHand));
		}
		pPlayer.startUsingItem(pHand);
		return ActionResult.consume(pPlayer.getItemInHand(pHand));
	}
}
