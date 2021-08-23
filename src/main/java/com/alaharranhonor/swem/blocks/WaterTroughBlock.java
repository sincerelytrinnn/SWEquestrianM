package com.alaharranhonor.swem.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WaterThroughBlock extends NonParallelBlock {
	public static final IntegerProperty LEVEL = SWEMBlockStateProperties.LEVEL_0_16;
	public WaterThroughBlock(Properties properties, DyeColor colour) {
		super(properties, colour);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.box(0.01, 0.01, 0.01, 0.99, 0.99, 0.99);
	}


	/*@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
		ItemStack itemstack = player.getItemInHand(handIn);
		if (itemstack.isEmpty()) {
			return ActionResultType.PASS;
		} else {
			int i = state.getValue(LEVEL);
			Item item = itemstack.getItem();
			if (item == Items.WATER_BUCKET) {
				if (i < 24 && !worldIn.isClientSide) {
					if (!player.abilities.instabuild) {
						player.setItemInHand(handIn, new ItemStack(Items.BUCKET));
					}

					player.awardStat(Stats.FILL_CAULDRON);
					this.setWaterLevel(worldIn, pos, state, i + 1);
					worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return ActionResultType.sidedSuccess(worldIn.isClientSide);


			} else if (item == Items.BUCKET) {
				if (i >= 1 && !worldIn.isClientSide) {
					if (!player.abilities.instabuild) {
						itemstack.shrink(1);
						if (itemstack.isEmpty()) {
							player.setItemInHand(handIn, new ItemStack(Items.WATER_BUCKET));
						} else if (!player.inventory.add(new ItemStack(Items.WATER_BUCKET))) {
							player.drop(new ItemStack(Items.WATER_BUCKET), false);
						}
					}

					player.awardStat(Stats.USE_CAULDRON);
					this.setWaterLevel(worldIn, pos, state, i - 1);
					worldIn.playSound((PlayerEntity) null, pos, SoundEvents.BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
			} else {
				return ActionResultType.PASS;
			}
			return ActionResultType.PASS;
		}
	}*/

	/*public void setWaterLevel(World worldIn, BlockPos pos, BlockState state, boolean removeWater) {
		// Iterate over connections
		// Divide 16 by connections.
		// Clamp this to each water through state, to a min 0 and max 36.

		ArrayList<BlockState> states = new ArrayList<>();
		if (state.getValue(PART) == SWEMBlockStateProperties.TwoWay.LEFT) {
			states = countConnectionsFromLeft(state, worldIn, pos);
		}

		if (states.size() == 3) {
			return;
		}

		states.forEach((iterator) -> {
			state.setValue()
		});





		worldIn.setBlock(pos, state.setValue(LEVEL, Integer.valueOf(MathHelper.clamp(level, 0, 12))), 2);
		worldIn.updateNeighbourForOutputSignal(pos, this);
	}
*/
	private ArrayList<BlockState> countConnectionsFromLeft(BlockState state, IWorldReader world, BlockPos pos) {
		ArrayList<BlockState> states = new ArrayList<>();
		Direction dir = state.getValue(HorizontalBlock.FACING);
		for (int i = 1; i < 5; i++) {
			BlockState checkState = world.getBlockState(pos.relative(dir.getClockWise(), i));
			if (checkState.isAir()) {
				break;
			}
			if (checkState.getValue(NonParallelBlock.PART) != SWEMBlockStateProperties.TwoWay.RIGHT) {
				states.add(checkState);
			} else if (checkState.getValue(NonParallelBlock.PART) == SWEMBlockStateProperties.TwoWay.RIGHT){
				states.add(checkState);
				break;
			} else {
				break;
			}
		}
		System.out.println(states.size());
		return states;

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
		builder.add(FACING, PART);
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
					connections = countConnectionsFromLeft(checkState, world, pos).size();
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
