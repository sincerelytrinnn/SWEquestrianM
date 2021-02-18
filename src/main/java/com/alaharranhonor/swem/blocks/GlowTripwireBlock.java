package com.alaharranhonor.swem.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.TripWireBlock;
import net.minecraft.block.TripWireHookBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class GlowTripwireBlock extends TripWireBlock {
	public GlowTripwireBlock(TripWireHookBlock hook, Properties properties) {
		super(hook, properties);
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return 15;
	}
}
