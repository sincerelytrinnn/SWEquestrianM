package com.alaharranhonor.swem.blocks;


import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class WaterThroughBlock extends NonParallelBlock {
	public WaterThroughBlock(Properties properties) {
		super(properties);
	}

	// TODO: ADD WATER INSIDE THE TROUGH AND SPLIT IT BASED ON HOW MANY CONNECTING PARTS.

	// TODO: CREATE A FUNCTION TO FIND ALL CONNECTING PARTS. TO LIMIT IT TO A MAX OF 4

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.create(0.01, 0.01, 0.01, 0.99, 0.99, 0.99);
	}

}
