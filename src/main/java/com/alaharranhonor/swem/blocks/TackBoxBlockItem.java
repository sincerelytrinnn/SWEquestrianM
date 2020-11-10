package com.alaharranhonor.swem.blocks;

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
		super(block, new Item.Properties().group(SWEM.TAB));
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
	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof SWEMHorseEntityBase) {
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) target;
			// TODO: ONLY ALLOW HORSES TAMED BY THE PLAYER TO SET THE HORSE ID
			stack.getOrCreateTag().putInt("horseID", horse.getEntityId());
			return ActionResultType.func_233537_a_(playerIn.world.isRemote);

		}
		return ActionResultType.FAIL;
	}
}
