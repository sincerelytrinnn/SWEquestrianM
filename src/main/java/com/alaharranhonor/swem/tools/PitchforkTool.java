package com.alaharranhonor.swem.tools;

import com.alaharranhonor.swem.blocks.Shavings;
import com.alaharranhonor.swem.items.ItemBase;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;

public class PitchforkTool extends ItemBase {

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		if (state.getBlock() instanceof Shavings) {
			return 5;
		}
		return super.getDestroySpeed(stack, state);
	}


}
