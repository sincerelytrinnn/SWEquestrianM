package com.alaharranhonor.swem.tools;

import com.alaharranhonor.swem.blocks.FenceBaseBlock;
import com.alaharranhonor.swem.blocks.SWEMBlockStateProperties;
import com.alaharranhonor.swem.items.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class FenceToolItem extends ItemBase {
	/**
	 * Called when this item is used when targetting a Block
	 *
	 * @param context
	 */
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		BlockState targetState = context.getWorld().getBlockState(context.getPos());

		Block target = targetState.getBlock();
		if (target instanceof FenceBaseBlock) {
			context.getWorld().setBlockState(context.getPos(), targetState.with(SWEMBlockStateProperties.HALF_FENCE, !targetState.get(SWEMBlockStateProperties.HALF_FENCE)));
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}


}
