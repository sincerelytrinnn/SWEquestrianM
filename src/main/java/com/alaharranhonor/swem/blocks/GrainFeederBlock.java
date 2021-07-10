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
		this.registerDefaultState(this.stateDefinition.any().setValue(LEFT, false).setValue(RIGHT, false).setValue(LEVEL, 0).setValue(FACING, Direction.NORTH));
		this.colour = colour;
	}

	public DyeColor getColour() {
		return this.colour;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IBlockReader iblockreader = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		BlockPos blockpos1 = blockpos.relative(context.getHorizontalDirection().getClockWise());
		BlockPos blockpos2 = blockpos.relative(context.getHorizontalDirection().getCounterClockWise());
		BlockState blockstate = iblockreader.getBlockState(blockpos1);
		BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
		BlockState modified = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
		if (blockstate.canOcclude()) {
			return modified.setValue(RIGHT, true);
		} else if (blockstate1.canOcclude()) {
			return modified.setValue(LEFT, true);
		}
		return modified;
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(LEFT, RIGHT, LEVEL, FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return Block.box(0, 0, 0, 15.99, 15.99, 15.99);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.relative(state.getValue(FACING))).canOcclude();
	}
}
