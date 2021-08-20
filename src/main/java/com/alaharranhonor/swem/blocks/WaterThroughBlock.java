package com.alaharranhonor.swem.blocks;


import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.GameType;
import net.minecraft.world.IBlockReader;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


	private int countConnectionsFromLeft(BlockState state, IWorldReader world, BlockPos pos) {
		Direction dir = state.getValue(HorizontalBlock.FACING);
		int connections = 0;
		for (int i = 1; i < 5; i++) {
			BlockState checkState = world.getBlockState(pos.relative(dir.getClockWise(), i));
			if (checkState.isAir()) {
				break;
			}
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

	private int countConnectionsFromRight(BlockState state, IWorldReader world, BlockPos pos) {
		Direction dir = state.getValue(HorizontalBlock.FACING);
		int connections = 0;
		for (int i = 1; i < 5; i++) {
			BlockState checkState = world.getBlockState(pos.relative(dir.getCounterClockWise(), i));
			if (checkState.isAir()) {
				break;
			}
			if (checkState.getValue(NonParallelBlock.PART) != SWEMBlockStateProperties.TwoWay.LEFT) {
				connections++;
			} else if (checkState.getValue(NonParallelBlock.PART) == SWEMBlockStateProperties.TwoWay.LEFT){
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

	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		// Check the facing, then check counter clockwise and clockwise directiosn, and offset on the blockpos.
		// IF we encounter a Left piece, we count connections going from the left (current method)
		// If we encounter a Right piece, we count connections going from theright (seperate method).
		// If the connections, are 4, return false, to deny placement.

		Direction facing = state.getValue(FACING);
		BlockState checkLeft = world.getBlockState(pos.relative(facing.getClockWise()));
		BlockState checkRight = world.getBlockState(pos.relative(facing.getCounterClockWise()));
		BlockState checkStraight = world.getBlockState(pos.relative(facing));
		BlockState checkBehind = world.getBlockState(pos.relative(facing.getOpposite()));

		List<BlockState> states = Arrays.asList(checkLeft, checkRight, checkStraight, checkBehind);

		for (BlockState checkState : states) {
			if (!checkState.isAir() && checkState.getBlock() instanceof WaterThroughBlock) {
				int connections = 0;
				if (checkState.getValue(PART) == SWEMBlockStateProperties.TwoWay.LEFT) {
					connections = countConnectionsFromLeft(checkState, world, pos);
				} else if (checkState.getValue(PART) == SWEMBlockStateProperties.TwoWay.RIGHT) {
					connections = countConnectionsFromRight(checkState, world, pos);
				}

				if (connections >= 4) {
					return false;
				}
			}
		}
		
		return true;
	}
}
