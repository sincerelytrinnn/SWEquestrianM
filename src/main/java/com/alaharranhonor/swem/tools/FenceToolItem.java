package com.alaharranhonor.swem.tools;

import com.alaharranhonor.swem.blocks.EnglishFenceBlock;
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
	public ActionResultType useOn(ItemUseContext context) {
		BlockState targetState = context.getLevel().getBlockState(context.getClickedPos());

		Block target = targetState.getBlock();
		if (target instanceof FenceBaseBlock) {
			context.getLevel().setBlock(context.getClickedPos(), targetState.setValue(SWEMBlockStateProperties.HALF_FENCE, !targetState.getValue(SWEMBlockStateProperties.HALF_FENCE)), 3);
			return ActionResultType.CONSUME;
		}
		if (target instanceof EnglishFenceBlock) {
			if (context.getPlayer().isShiftKeyDown()) {
				context.getLevel().setBlock(context.getClickedPos(), targetState.cycle(EnglishFenceBlock.FACING), 3);
			} else {
				context.getLevel().setBlock(context.getClickedPos(), targetState.cycle(EnglishFenceBlock.PART), 3);
			}
			return ActionResultType.CONSUME;
		}
		return ActionResultType.PASS;
	}


}
