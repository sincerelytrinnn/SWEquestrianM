package com.alaharranhonor.swem.blocks;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock.Properties;

public class GrainFeederBlock extends HorizontalBlock {

	public static final BooleanProperty LEFT = SWEMBlockStateProperties.CONNECTED_LEFT;
	public static final BooleanProperty RIGHT = SWEMBlockStateProperties.CONNECTED_RIGHT;

	public static final IntegerProperty LEVEL = SWEMBlockStateProperties.LEVEL_0_2;

	private DyeColor colour;

	public GrainFeederBlock(Properties properties, DyeColor colour) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(LEFT, false).with(RIGHT, false).with(LEVEL, 0).with(HORIZONTAL_FACING, Direction.NORTH));
		this.colour = colour;
	}

	public DyeColor getColour() {
		return this.colour;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IBlockReader iblockreader = context.getWorld();
		BlockPos blockpos = context.getPos();
		FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
		BlockPos blockpos1 = blockpos.offset(context.getPlacementHorizontalFacing().rotateY());
		BlockPos blockpos2 = blockpos.offset(context.getPlacementHorizontalFacing().rotateYCCW());
		BlockState blockstate = iblockreader.getBlockState(blockpos1);
		BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
		BlockState modified = this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing());
		if (blockstate.isSolid()) {
			return modified.with(RIGHT, true);
		} else if (blockstate1.isSolid()) {
			return modified.with(LEFT, true);
		}
		return modified;
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(LEFT, RIGHT, LEVEL, HORIZONTAL_FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return Block.makeCuboidShape(0, 0, 0, 15.99, 15.99, 15.99);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.offset(state.get(HORIZONTAL_FACING))).isSolid();
	}
}
