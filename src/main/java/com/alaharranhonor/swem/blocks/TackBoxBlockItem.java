package com.alaharranhonor.swem.blocks;


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

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

public class TackBoxBlockItem extends BlockItem {

	public TackBoxBlockItem(Block block) {
		super(block, new Item.Properties().tab(SWEM.TAB));
	}

	/**
	 * Returns true if the item can be used on the given entity, e.g. shears on sheep.
	 *
	 * @param stack
	 * @param playerIn
	 * @param target
	 * @param hand
	 */
	@Override
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof SWEMHorseEntityBase) {
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) target;
			// TODO: ONLY ALLOW HORSES TAMED BY THE PLAYER TO SET THE HORSE ID
			stack.getOrCreateTag().putUUID("horseUUID", horse.getUUID());
			return ActionResultType.sidedSuccess(playerIn.level.isClientSide);

		}
		return ActionResultType.FAIL;
	}
}
