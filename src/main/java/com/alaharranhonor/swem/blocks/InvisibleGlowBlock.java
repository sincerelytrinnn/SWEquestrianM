package com.alaharranhonor.swem.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import net.minecraft.block.AbstractBlock.Properties;

public class InvisibleGlowBlock extends Block {
	public InvisibleGlowBlock(Properties properties) {
		super(properties);
	}

	/**
	 * Get a light value for this block, taking into account the given state and coordinates, normal ranges are between 0 and 15
	 *
	 * @param state
	 * @param world
	 * @param pos
	 * @return The light value
	 */
	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return 8;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.empty();
	}

	@Override
	public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
		return true;
	}
}
