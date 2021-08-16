package com.alaharranhonor.swem.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.world.IWorld;
import org.jetbrains.annotations.Nullable;

public class WaterThroughBlock extends NonParallelBlock {
	public static final IntegerProperty LEVEL = SWEMBlockStateProperties.LEVEL_0_12;
	public WaterThroughBlock(Properties properties, DyeColor colour) {
		super(properties, colour);
	}

	// TODO: ADD WATER INSIDE THE TROUGH AND SPLIT IT BASED ON HOW MANY CONNECTING PARTS.

	// TODO: CREATE A FUNCTION TO FIND ALL CONNECTING PARTS. TO LIMIT IT TO A MAX OF 4

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.box(0.01, 0.01, 0.01, 0.99, 0.99, 0.99);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		BlockState orgState = stateIn;
		BlockState updated = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		int connections = 0;
		if ((orgState.getValue(NonParallelBlock.PART) == SWEMBlockStateProperties.TwoWay.LEFT) && (updated.getValue(NonParallelBlock.PART) == SWEMBlockStateProperties.TwoWay.MIDDLE)) {
			connections = countConnections(updated, worldIn, currentPos);
		}

		if (connections > 4) {
			return this.defaultBlockState().setValue(FACING, stateIn.getValue(FACING));
		}
		return updated;

	}

	private int countConnections(BlockState state, IWorld world, BlockPos pos) {
		Direction dir = state.getValue(HorizontalBlock.FACING);
		BlockPos leftPos = pos.relative(dir.getCounterClockWise());
		int connections = 0;
		for (int i = 0; i < 5; i++) {
			BlockState checkState = world.getBlockState(leftPos.relative(dir.getClockWise(), i));
			if (checkState.getValue(NonParallelBlock.PART) != SWEMBlockStateProperties.TwoWay.RIGHT) {
				connections++;
			} else if (checkState.getValue(NonParallelBlock.PART) == SWEMBlockStateProperties.TwoWay.RIGHT){
				connections++;
				break;
			} else {
				break;
			}
		}

		System.out.println(connections);
		return connections;

	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
	}
}
