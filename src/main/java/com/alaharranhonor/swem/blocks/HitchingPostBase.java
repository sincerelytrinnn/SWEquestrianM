package com.alaharranhonor.swem.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class HitchingPostBase extends Block {

	private final HitchingPostType type;
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final EnumProperty<PostPart> PART = SWEMBlockStateProperties.POST_PART;


	public HitchingPostBase(HitchingPostType type, Properties properties) {
		super(properties);

		this.type = type;
		this.setDefaultState(
				this.stateContainer.getBaseState()
				.with(FACING, Direction.NORTH)
				.with(PART, PostPart.LOWER)
		);

	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		VoxelShape shape = this.type.getVoxelShape(state.get(FACING));
		if (state.get(PART) == PostPart.UPPER) {
			return shape.withOffset(0.0d, -1.0d, 0.0d);
		} else {
			return shape;
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite()).with(PART, PostPart.LOWER);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
		builder.add(PART);
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	 *
	 * @param worldIn
	 * @param pos
	 * @param state
	 * @param placer
	 * @param stack
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		if (!worldIn.isRemote) {
			BlockPos blockpos = pos.offset(Direction.UP);
			worldIn.setBlockState(blockpos, state.with(PART, PostPart.UPPER), 3);
			state.updateNeighbours(worldIn, pos, 3);
		}
	}

	/**
	 * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
	 * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
	 * returns its solidified counterpart.
	 * Note that this method should ideally consider only the specific face passed in.
	 *
	 * @param stateIn
	 * @param facing
	 * @param facingState
	 * @param worldIn
	 * @param currentPos
	 * @param facingPos
	 */
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(PART) == PostPart.LOWER && facing == Direction.UP && facingState.getBlock() == Blocks.AIR) {
			return Blocks.AIR.getDefaultState();
		} else if (stateIn.get(PART) == PostPart.UPPER && facing == Direction.DOWN && facingState.getBlock() == Blocks.AIR) {
			return Blocks.AIR.getDefaultState();
		}
		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	private static Direction getDirectionToOther(BedPart part, Direction direction) {
		return part == BedPart.FOOT ? direction : direction.getOpposite();
	}

	public enum HitchingPostType {

		WESTERN(
				Stream.of(
						Block.makeCuboidShape(6.757359312880716, 28.4375, 6.4375, 9.242640687119284, 29.4375, 7.4375),
						Block.makeCuboidShape(6.757359312880716, 28.4375, 6.4375, 9.242640687119284, 29.4375, 7.4375),
						Block.makeCuboidShape(6.757359312880716, 23.4375, 6.4375, 9.242640687119284, 24.4375, 7.4375),
						Block.makeCuboidShape(6.757359312880716, 23.4375, 6.4375, 9.242640687119284, 24.4375, 7.4375),
						Block.makeCuboidShape(10, 25.194859312880716, 6.4375, 11, 27.680140687119284, 7.4375),
						Block.makeCuboidShape(10, 25.194859312880716, 6.4375, 11, 27.680140687119284, 7.4375),
						Block.makeCuboidShape(5, 25.194859312880716, 6.4375, 6, 27.680140687119284, 7.4375),
						Block.makeCuboidShape(5, 25.194859312880716, 6.4375, 6, 27.680140687119284, 7.4375),
						Block.makeCuboidShape(7, 29.381281566461773, 10.9375, 9, 31.381281566461773, 14.9375),
						Block.makeCuboidShape(7, 27.568781566461773, 11, 9, 29.568781566461773, 13),
						Block.makeCuboidShape(7, 23.818781566461773, 10.6875, 9, 27.818781566461773, 12.6875),
						Block.makeCuboidShape(7, 24.82246122290213, 12.24003877709787, 9, 28.82246122290213, 14.240038777097872),
						Block.makeCuboidShape(7, 24.82246122290213, 11.24003877709787, 9, 28.82246122290213, 14.240038777097872),
						Block.makeCuboidShape(7, 26.38496122290213, 10.24003877709787, 9, 28.38496122290213, 11.24003877709787),
						Block.makeCuboidShape(8.5, 2, 11.5, 9.5, 23, 12.5),
						Block.makeCuboidShape(7.5, 2, 10.5, 8.5, 23, 11.5),
						Block.makeCuboidShape(6.5, 2, 11.5, 7.5, 23, 12.5),
						Block.makeCuboidShape(7.5, 2, 12.5, 8.5, 23, 13.5),
						Block.makeCuboidShape(7, 3, 11, 9, 4, 13),
						Block.makeCuboidShape(7, 21, 11, 9, 22, 13),
						Block.makeCuboidShape(7, 28.375, 9.5, 9, 30.5, 11.625),
						Block.makeCuboidShape(7, 28.3125, 5.9375, 9, 30.3125, 7.9375),
						Block.makeCuboidShape(7, 28.74467202344118, 6.011792397422603, 9, 30.74467202344118, 10.011792397422603),
						Block.makeCuboidShape(7, 29.788011838136406, 10.540467641163463, 9, 31.788011838136406, 10.540467641163465),
						Block.makeCuboidShape(7, 28.5, 7.5, 9, 30.5, 10.5),
						Block.makeCuboidShape(7, 29.5625, 9.5, 9, 31.5625, 11.5),
						Block.makeCuboidShape(6, 1, 10, 10, 2, 14),
						Block.makeCuboidShape(6, 23, 10, 10, 24, 14),
						Block.makeCuboidShape(7, 2, 11, 9, 23, 13),
						Block.makeCuboidShape(6, 24, 9, 10, 25, 15),
						Block.makeCuboidShape(5, 0, 9, 11, 1, 15)
				).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
				VoxelShapes.fullCube(),
				VoxelShapes.fullCube(),
				VoxelShapes.fullCube()
		);


		private final VoxelShape north;
		private final VoxelShape east;
		private final VoxelShape south;
		private final VoxelShape west;
		HitchingPostType(VoxelShape north, VoxelShape east, VoxelShape south, VoxelShape west) {
			this.north = north;
			this.east = east;
			this.south = south;
			this.west = west;
		}

		public VoxelShape getVoxelShape(Direction facing) {
			switch (facing) {
				case EAST:
					return this.east;
				case SOUTH:
					return this.south;
				case WEST:
					return this.west;
				default:
					return this.north;
			}
		}
	}

	public enum PostPart implements IStringSerializable {
		LOWER("lower"),
		UPPER("upper");

		private final String name;

		private PostPart(String name) {
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
