package com.alaharranhonor.swem.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.LeadItem;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class FenceBaseBlock extends FourWayBlock {

	private final VoxelShape[] renderShapes;
	public static final BooleanProperty FULL_FENCE = SWEMBlockStateProperties.FULL_FENCE;
	public static final BooleanProperty HALF_FENCE = SWEMBlockStateProperties.HALF_FENCE;

	public FenceBaseBlock(Properties properties) {
		super(2.0f, 2.0f, 16.0f, 16.0f, 24.0f, properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, Boolean.valueOf(false)).with(EAST, Boolean.valueOf(false)).with(SOUTH, Boolean.valueOf(false)).with(WEST, Boolean.valueOf(false)).with(WATERLOGGED, Boolean.valueOf(false)).with(FULL_FENCE, Boolean.valueOf(false)).with(HALF_FENCE, Boolean.valueOf(false)));
		this.renderShapes = this.makeShapes(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
	}


	public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
			return this.renderShapes[this.getIndex(state)];
	}

	public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return this.getShape(state, reader, pos, context);
	}

	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}

	public boolean canConnect(BlockState state, boolean isSideSolid, Direction direction) {
		Block block = state.getBlock();
		boolean flag = this.isWoodenFence(block);
		boolean flag1 = block instanceof FenceGateBlock && FenceGateBlock.isParallel(state, direction);
		return !cannotAttach(block) && isSideSolid || flag || flag1;
	}

	private boolean isWoodenFence(Block block) {
		return block instanceof FenceBaseBlock;
	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IBlockReader iblockreader = context.getWorld();
		BlockPos blockpos = context.getPos();
		FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
		BlockPos blockpos1 = blockpos.north();
		BlockPos blockpos2 = blockpos.east();
		BlockPos blockpos3 = blockpos.south();
		BlockPos blockpos4 = blockpos.west();
		BlockState blockstate = iblockreader.getBlockState(blockpos1);
		BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
		BlockState blockstate2 = iblockreader.getBlockState(blockpos3);
		BlockState blockstate3 = iblockreader.getBlockState(blockpos4);
		return super.getStateForPlacement(context).with(NORTH, Boolean.valueOf(this.canConnect(blockstate, blockstate.isSolidSide(iblockreader, blockpos1, Direction.SOUTH), Direction.SOUTH))).with(EAST, Boolean.valueOf(this.canConnect(blockstate1, blockstate1.isSolidSide(iblockreader, blockpos2, Direction.WEST), Direction.WEST))).with(SOUTH, Boolean.valueOf(this.canConnect(blockstate2, blockstate2.isSolidSide(iblockreader, blockpos3, Direction.NORTH), Direction.NORTH))).with(WEST, Boolean.valueOf(this.canConnect(blockstate3, blockstate3.isSolidSide(iblockreader, blockpos4, Direction.EAST), Direction.EAST))).with(WATERLOGGED, Boolean.valueOf(fluidstate.getFluid() == Fluids.WATER));
	}

	/**
	 * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
	 * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
	 * returns its solidified counterpart.
	 * Note that this method should ideally consider only the specific face passed in.
	 */
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}

		BlockState updated = facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? stateIn.with(FACING_TO_PROPERTY_MAP.get(facing), Boolean.valueOf(this.canConnect(facingState, facingState.isSolidSide(worldIn, facingPos, facing.getOpposite()), facing.getOpposite()))).with(FULL_FENCE, Boolean.valueOf(false)).with(HALF_FENCE, Boolean.valueOf(false)) : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		if (checkForFullFence(worldIn, currentPos, updated)) {
			if (stateIn.get(HALF_FENCE)) {
				return updated.with(HALF_FENCE, Boolean.valueOf(true));
			} else {
				return updated.with(FULL_FENCE, Boolean.valueOf(true));
			}
		}
		return updated;
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED, FULL_FENCE, HALF_FENCE);
	}

	private boolean checkForFullFence(IWorld worldIn, BlockPos currentPos, BlockState stateIn) {
		boolean north = stateIn.get(NORTH);
		boolean east = stateIn.get(EAST);
		boolean south = stateIn.get(SOUTH);
		boolean west = stateIn.get(WEST);

		if (north && south && !east && !west) {
			return true;
		}
		return east && west && !north && !south;
	}

	public enum FencePart implements IStringSerializable {
		POST("post"),
		CONNECTOR("connector");

		private final String name;

		private FencePart(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}

		public String getString() {
			return this.name;
		}
	}
}
