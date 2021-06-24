package com.alaharranhonor.swem.blocks;

import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;


public class NonParallelBlock extends HorizontalBlock {

	public static final EnumProperty<SWEMBlockStateProperties.TwoWay> PART = SWEMBlockStateProperties.TWO_WAY;

	private DyeColor colour;

	public NonParallelBlock(Properties properties, DyeColor colour) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(PART, SWEMBlockStateProperties.TwoWay.SINGLE));
		this.colour = colour;
	}

	public DyeColor getColour() {
		return this.colour;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {

		// TODO: IF SINGLE CAN CONNECT ON ALL 4 SIDES
		// TODO: IF NOT SINGLE; ONLY CONNECT TO THE SIDE IT'S ALREADY CONNECTED.

		IBlockReader iblockreader = context.getWorld();
		BlockPos blockpos = context.getPos();
		BlockState blockstate = iblockreader.getBlockState(blockpos);

		BlockPos blockpos1 = context.getPos().offset(context.getPlacementHorizontalFacing().rotateY());
		BlockPos blockpos2 = context.getPos().offset(context.getPlacementHorizontalFacing().rotateYCCW());
		BlockPos blockpos3 = context.getPos().offset(context.getPlacementHorizontalFacing());
		BlockPos blockpos4 = context.getPos().offset(context.getPlacementHorizontalFacing().getOpposite());


		BlockState blockstate1 = iblockreader.getBlockState(blockpos1);
		BlockState blockstate2 = iblockreader.getBlockState(blockpos2);
		BlockState blockstate3 = iblockreader.getBlockState(blockpos3);
		BlockState blockstate4 = iblockreader.getBlockState(blockpos4);

		BlockState standard = this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing());




		if (blockstate1.getBlock() == this && blockstate2.getBlock() == this) {

			return blockstate1.get(HORIZONTAL_FACING).getAxis() == context.getPlacementHorizontalFacing().getAxis() && blockstate2.get(HORIZONTAL_FACING).getAxis() == context.getPlacementHorizontalFacing().getAxis() ? standard.with(PART, SWEMBlockStateProperties.TwoWay.MIDDLE) : standard;
		} else if (blockstate1.getBlock() == this) {
			return blockstate1.get(HORIZONTAL_FACING).getAxis() == context.getPlacementHorizontalFacing().getAxis() ? standard.with(PART, SWEMBlockStateProperties.TwoWay.LEFT) : standard;
		} else if (blockstate2.getBlock() == this) {
			return blockstate2.get(HORIZONTAL_FACING).getAxis() == context.getPlacementHorizontalFacing().getAxis() ? standard.with(PART, SWEMBlockStateProperties.TwoWay.RIGHT) : standard;
		} else {
			return standard;
		}
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {

		switch (stateIn.get(PART)) {
			case RIGHT: {
				if (facing == stateIn.get(HORIZONTAL_FACING).rotateYCCW()) {
					if (facingState.isAir()) {
						return stateIn.with(PART, SWEMBlockStateProperties.TwoWay.SINGLE);
					}
				} else if (facing == stateIn.get(HORIZONTAL_FACING).rotateY()) {
					if (facingState.getBlock() == this && stateIn.get(HORIZONTAL_FACING).getAxis() == facingState.get(HORIZONTAL_FACING).getAxis()) {
						return stateIn.with(PART, SWEMBlockStateProperties.TwoWay.MIDDLE);
					}
				}
				break;
			}
			case LEFT: {
				if (facing == stateIn.get(HORIZONTAL_FACING).rotateY()) {
					if (facingState.isAir()) {
						return stateIn.with(PART, SWEMBlockStateProperties.TwoWay.SINGLE);
					}
				} else if (facing == stateIn.get(HORIZONTAL_FACING).rotateYCCW()) {
					if (facingState.getBlock() == this && stateIn.get(HORIZONTAL_FACING).getAxis() == facingState.get(HORIZONTAL_FACING).getAxis()) {
						return stateIn.with(PART, SWEMBlockStateProperties.TwoWay.MIDDLE);
					}
				}
				break;
			}
			case SINGLE: {
				if (facing == stateIn.get(HORIZONTAL_FACING)) {
					System.out.println(facingState.get(PART));
					return stateIn.with(HORIZONTAL_FACING, facingState.get(HORIZONTAL_FACING)).with(PART, SWEMBlockStateProperties.TwoWay.LEFT);
				} else if (facing == stateIn.get(HORIZONTAL_FACING).getOpposite()) {
					return stateIn.with(HORIZONTAL_FACING, facingState.get(HORIZONTAL_FACING)).with(PART, SWEMBlockStateProperties.TwoWay.RIGHT);
				}

				if (facing == stateIn.get(HORIZONTAL_FACING).rotateY()) {
					if (facingState.getBlock() == this && stateIn.get(HORIZONTAL_FACING).getAxis() == facingState.get(HORIZONTAL_FACING).getAxis()) {
						return stateIn.with(PART, SWEMBlockStateProperties.TwoWay.LEFT);
					}
				} else if (facing == stateIn.get(HORIZONTAL_FACING).rotateYCCW()) {
					if (facingState.getBlock() == this && stateIn.get(HORIZONTAL_FACING).getAxis() == facingState.get(HORIZONTAL_FACING).getAxis()) {
						return stateIn.with(PART, SWEMBlockStateProperties.TwoWay.RIGHT);
					}
				}
				break;
			}
			case MIDDLE: {
				if (facing == stateIn.get(HORIZONTAL_FACING).rotateY()) {
					if (facingState.isAir()) {
						return stateIn.with(PART, SWEMBlockStateProperties.TwoWay.RIGHT);
					}
				} else if (facing == stateIn.get(HORIZONTAL_FACING).rotateYCCW()) {
					if (facingState.isAir()) {
						return stateIn.with(PART, SWEMBlockStateProperties.TwoWay.LEFT);
					}
				}
				break;
			}
		}
		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);

	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, PART);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.create(0.01d, 0.01d, 0.01d, 0.99d, 0.99d, 0.99d);
	}
}
