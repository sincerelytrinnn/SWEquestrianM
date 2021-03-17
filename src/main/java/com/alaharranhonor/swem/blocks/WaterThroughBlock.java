package com.alaharranhonor.swem.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.DyeColor;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class WaterThroughBlock extends NonParallelBlock {
	public static final IntegerProperty LEVEL = SWEMBlockStateProperties.LEVEL_0_12;
	public WaterThroughBlock(Properties properties, DyeColor colour) {
		super(properties, colour);
	}

	// TODO: ADD WATER INSIDE THE TROUGH AND SPLIT IT BASED ON HOW MANY CONNECTING PARTS.

	// TODO: CREATE A FUNCTION TO FIND ALL CONNECTING PARTS. TO LIMIT IT TO A MAX OF 4

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.create(0.01, 0.01, 0.01, 0.99, 0.99, 0.99);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
	}
}
