package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.items.RopeItem;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BedPart;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
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


	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isRemote) {
			ItemStack itemstack = player.getHeldItem(handIn);
			return itemstack.getItem() == Items.LEAD ? ActionResultType.SUCCESS : ActionResultType.PASS;
		} else {
			return RopeItem.bindPlayerMobs(player, worldIn, state.get(PART) == PostPart.LOWER ? pos.up() : pos);
		}
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
				Stream.of(
						Block.makeCuboidShape(3, 3, 7, 5, 4, 9),
						Block.makeCuboidShape(3.5, 2, 8.5, 4.5, 16, 9.5),
						Block.makeCuboidShape(4.5, 2, 7.5, 5.5, 16, 8.5),
						Block.makeCuboidShape(3.5, 2, 6.5, 4.5, 16, 7.5),
						Block.makeCuboidShape(2.5, 2, 7.5, 3.5, 16, 8.5),
						Block.makeCuboidShape(1, 0, 5, 7, 1, 11),
						Block.makeCuboidShape(2, 1, 6, 6, 2, 10),
						Block.makeCuboidShape(3, 2, 7, 5, 16, 9),
						Block.makeCuboidShape(8.5625, 28.4375, 6.757359312880716, 9.5625, 29.4375, 9.242640687119284),
						Block.makeCuboidShape(8.5625, 28.4375, 6.757359312880716, 9.5625, 29.4375, 9.242640687119284),
						Block.makeCuboidShape(8.5625, 23.4375, 6.757359312880716, 9.5625, 24.4375, 9.242640687119284),
						Block.makeCuboidShape(8.5625, 23.4375, 6.757359312880716, 9.5625, 24.4375, 9.242640687119284),
						Block.makeCuboidShape(8.5625, 25.194859312880716, 10, 9.5625, 27.680140687119284, 11),
						Block.makeCuboidShape(8.5625, 25.194859312880716, 10, 9.5625, 27.680140687119284, 11),
						Block.makeCuboidShape(8.5625, 25.194859312880716, 5, 9.5625, 27.680140687119284, 6),
						Block.makeCuboidShape(8.5625, 25.194859312880716, 5, 9.5625, 27.680140687119284, 6),
						Block.makeCuboidShape(4.375, 28.375, 7, 6.5, 30.5, 9),
						Block.makeCuboidShape(8.0625, 28.3125, 7, 10.0625, 30.3125, 9),
						Block.makeCuboidShape(5.988207602577397, 28.74467202344118, 7, 9.988207602577397, 30.74467202344118, 9),
						Block.makeCuboidShape(5.459532358836533, 29.788011838136406, 7, 5.459532358836537, 31.788011838136406, 9),
						Block.makeCuboidShape(5.5, 28.5, 7, 8.5, 30.5, 9),
						Block.makeCuboidShape(4.5, 29.5625, 7, 6.5, 31.5625, 9),
						Block.makeCuboidShape(1.0625, 29.381281566461773, 7, 5.0625, 31.381281566461773, 9),
						Block.makeCuboidShape(3, 27.568781566461773, 7, 5, 29.568781566461773, 9),
						Block.makeCuboidShape(3.3125, 23.818781566461773, 7, 5.3125, 27.818781566461773, 9),
						Block.makeCuboidShape(1.7599612229021275, 24.82246122290213, 7, 3.7599612229021275, 28.82246122290213, 9),
						Block.makeCuboidShape(1.7599612229021275, 24.82246122290213, 7, 4.7599612229021275, 28.82246122290213, 9),
						Block.makeCuboidShape(4.7599612229021275, 26.38496122290213, 7, 5.7599612229021275, 28.38496122290213, 9),
						Block.makeCuboidShape(3, 21, 7, 5, 22, 9),
						Block.makeCuboidShape(3.5, 16, 8.5, 4.5, 23, 9.5),
						Block.makeCuboidShape(4.5, 16, 7.5, 5.5, 23, 8.5),
						Block.makeCuboidShape(3.5, 16, 6.5, 4.5, 23, 7.5),
						Block.makeCuboidShape(2.5, 16, 7.5, 3.5, 23, 8.5),
						Block.makeCuboidShape(1, 24, 6, 7, 25, 10),
						Block.makeCuboidShape(2, 23, 6, 6, 24, 10),
						Block.makeCuboidShape(3, 16, 7, 5, 23, 9)
				).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
				Stream.of(
						Block.makeCuboidShape(7, 3, 3, 9, 4, 5),
						Block.makeCuboidShape(6.5, 2, 3.5, 7.5, 16, 4.5),
						Block.makeCuboidShape(7.5, 2, 4.5, 8.5, 16, 5.5),
						Block.makeCuboidShape(8.5, 2, 3.5, 9.5, 16, 4.5),
						Block.makeCuboidShape(7.5, 2, 2.5, 8.5, 16, 3.5),
						Block.makeCuboidShape(5, 0, 1, 11, 1, 7),
						Block.makeCuboidShape(6, 1, 2, 10, 2, 6),
						Block.makeCuboidShape(7, 2, 3, 9, 16, 5),
						Block.makeCuboidShape(6.757359312880716, 28.4375, 8.5625, 9.242640687119284, 29.4375, 9.5625),
						Block.makeCuboidShape(6.757359312880716, 28.4375, 8.5625, 9.242640687119284, 29.4375, 9.5625),
						Block.makeCuboidShape(6.757359312880716, 23.4375, 8.5625, 9.242640687119284, 24.4375, 9.5625),
						Block.makeCuboidShape(6.757359312880716, 23.4375, 8.5625, 9.242640687119284, 24.4375, 9.5625),
						Block.makeCuboidShape(5, 25.194859312880716, 8.5625, 6, 27.680140687119284, 9.5625),
						Block.makeCuboidShape(5, 25.194859312880716, 8.5625, 6, 27.680140687119284, 9.5625),
						Block.makeCuboidShape(10, 25.194859312880716, 8.5625, 11, 27.680140687119284, 9.5625),
						Block.makeCuboidShape(10, 25.194859312880716, 8.5625, 11, 27.680140687119284, 9.5625),
						Block.makeCuboidShape(7, 28.375, 4.375, 9, 30.5, 6.5),
						Block.makeCuboidShape(7, 28.3125, 8.0625, 9, 30.3125, 10.0625),
						Block.makeCuboidShape(7, 28.74467202344118, 5.988207602577397, 9, 30.74467202344118, 9.988207602577397),
						Block.makeCuboidShape(7, 29.788011838136406, 5.459532358836533, 9, 31.788011838136406, 5.459532358836537),
						Block.makeCuboidShape(7, 28.5, 5.5, 9, 30.5, 8.5),
						Block.makeCuboidShape(7, 29.5625, 4.5, 9, 31.5625, 6.5),
						Block.makeCuboidShape(7, 29.381281566461773, 1.0625, 9, 31.381281566461773, 5.0625),
						Block.makeCuboidShape(7, 27.568781566461773, 3, 9, 29.568781566461773, 5),
						Block.makeCuboidShape(7, 23.818781566461773, 3.3125, 9, 27.818781566461773, 5.3125),
						Block.makeCuboidShape(7, 24.82246122290213, 1.7599612229021275, 9, 28.82246122290213, 3.7599612229021275),
						Block.makeCuboidShape(7, 24.82246122290213, 1.7599612229021275, 9, 28.82246122290213, 4.7599612229021275),
						Block.makeCuboidShape(7, 26.38496122290213, 4.7599612229021275, 9, 28.38496122290213, 5.7599612229021275),
						Block.makeCuboidShape(7, 21, 3, 9, 22, 5),
						Block.makeCuboidShape(6.5, 16, 3.5, 7.5, 23, 4.5),
						Block.makeCuboidShape(7.5, 16, 4.5, 8.5, 23, 5.5),
						Block.makeCuboidShape(8.5, 16, 3.5, 9.5, 23, 4.5),
						Block.makeCuboidShape(7.5, 16, 2.5, 8.5, 23, 3.5),
						Block.makeCuboidShape(6, 24, 1, 10, 25, 7),
						Block.makeCuboidShape(6, 23, 2, 10, 24, 6),
						Block.makeCuboidShape(7, 16, 3, 9, 23, 5)
				).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
				Stream.of(
						Block.makeCuboidShape(11, 3, 7, 13, 4, 9),
						Block.makeCuboidShape(11.5, 2, 6.5, 12.5, 16, 7.5),
						Block.makeCuboidShape(10.5, 2, 7.5, 11.5, 16, 8.5),
						Block.makeCuboidShape(11.5, 2, 8.5, 12.5, 16, 9.5),
						Block.makeCuboidShape(12.5, 2, 7.5, 13.5, 16, 8.5),
						Block.makeCuboidShape(9, 0, 5, 15, 1, 11),
						Block.makeCuboidShape(10, 1, 6, 14, 2, 10),
						Block.makeCuboidShape(11, 2, 7, 13, 16, 9),
						Block.makeCuboidShape(6.4375, 28.4375, 6.757359312880716, 7.4375, 29.4375, 9.242640687119284),
						Block.makeCuboidShape(6.4375, 28.4375, 6.757359312880716, 7.4375, 29.4375, 9.242640687119284),
						Block.makeCuboidShape(6.4375, 23.4375, 6.757359312880716, 7.4375, 24.4375, 9.242640687119284),
						Block.makeCuboidShape(6.4375, 23.4375, 6.757359312880716, 7.4375, 24.4375, 9.242640687119284),
						Block.makeCuboidShape(6.4375, 25.194859312880716, 5, 7.4375, 27.680140687119284, 6),
						Block.makeCuboidShape(6.4375, 25.194859312880716, 5, 7.4375, 27.680140687119284, 6),
						Block.makeCuboidShape(6.4375, 25.194859312880716, 10, 7.4375, 27.680140687119284, 11),
						Block.makeCuboidShape(6.4375, 25.194859312880716, 10, 7.4375, 27.680140687119284, 11),
						Block.makeCuboidShape(9.5, 28.375, 7, 11.625, 30.5, 9),
						Block.makeCuboidShape(5.9375, 28.3125, 7, 7.9375, 30.3125, 9),
						Block.makeCuboidShape(6.011792397422603, 28.74467202344118, 7, 10.011792397422603, 30.74467202344118, 9),
						Block.makeCuboidShape(10.540467641163463, 29.788011838136406, 7, 10.540467641163467, 31.788011838136406, 9),
						Block.makeCuboidShape(7.5, 28.5, 7, 10.5, 30.5, 9),
						Block.makeCuboidShape(9.5, 29.5625, 7, 11.5, 31.5625, 9),
						Block.makeCuboidShape(10.9375, 29.381281566461773, 7, 14.9375, 31.381281566461773, 9),
						Block.makeCuboidShape(11, 27.568781566461773, 7, 13, 29.568781566461773, 9),
						Block.makeCuboidShape(10.6875, 23.818781566461773, 7, 12.6875, 27.818781566461773, 9),
						Block.makeCuboidShape(12.240038777097872, 24.82246122290213, 7, 14.240038777097872, 28.82246122290213, 9),
						Block.makeCuboidShape(11.240038777097872, 24.82246122290213, 7, 14.240038777097872, 28.82246122290213, 9),
						Block.makeCuboidShape(10.240038777097872, 26.38496122290213, 7, 11.240038777097872, 28.38496122290213, 9),
						Block.makeCuboidShape(11, 21, 7, 13, 22, 9),
						Block.makeCuboidShape(11.5, 16, 6.5, 12.5, 23, 7.5),
						Block.makeCuboidShape(10.5, 16, 7.5, 11.5, 23, 8.5),
						Block.makeCuboidShape(11.5, 16, 8.5, 12.5, 23, 9.5),
						Block.makeCuboidShape(12.5, 16, 7.5, 13.5, 23, 8.5),
						Block.makeCuboidShape(9, 24, 6, 15, 25, 10),
						Block.makeCuboidShape(10, 23, 6, 14, 24, 10),
						Block.makeCuboidShape(11, 16, 7, 13, 23, 9)
				).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()
		),

		ENGLISH(
				Stream.of(
						Block.makeCuboidShape(5, 0, 8, 11, 2, 14),
						Block.makeCuboidShape(6, 2, 9, 10, 3, 13),
						Block.makeCuboidShape(7, 3, 10, 9, 16, 12),
						Block.makeCuboidShape(7.5, 28, 5, 8.5, 29, 10),
						Block.makeCuboidShape(7.5, 26.75, 4.5, 8.5, 28.75, 5.5),
						Block.makeCuboidShape(7.5, 25.5, 6.5, 8.5, 28.5, 7.5),
						Block.makeCuboidShape(7.5, 26, 10.5, 8.5, 28, 11.5),
						Block.makeCuboidShape(7, 25, 10, 9, 26, 12),
						Block.makeCuboidShape(7, 31, 10, 9, 32, 12),
						Block.makeCuboidShape(7, 28, 10, 9, 29, 12),
						Block.makeCuboidShape(6, 24, 9, 10, 25, 13),
						Block.makeCuboidShape(6, 29, 9, 10, 31, 13),
						Block.makeCuboidShape(7, 16, 10, 9, 24, 12),
						Block.makeCuboidShape(6.17157287525381, 28, 10.5, 8.82842712474619, 29, 11.5),
						Block.makeCuboidShape(5, 25.17157287525381, 10.5, 6, 27.82842712474619, 11.5),
						Block.makeCuboidShape(5, 26.17157287525381, 10.5, 6, 27.82842712474619, 11.5),
						Block.makeCuboidShape(7.17157287525381, 25, 10.5, 9.82842712474619, 26, 11.5),
						Block.makeCuboidShape(7.5, 25, 10.17157287525381, 8.5, 26, 12.82842712474619),
						Block.makeCuboidShape(7.5, 25, 9.17157287525381, 8.5, 26, 11.82842712474619),
						Block.makeCuboidShape(10, 26.17157287525381, 10.5, 11, 28.82842712474619, 11.5),
						Block.makeCuboidShape(7.5, 26.17157287525381, 13, 8.5, 28.82842712474619, 14),
						Block.makeCuboidShape(7.5, 26.17157287525381, 8, 8.5, 28.82842712474619, 9),
						Block.makeCuboidShape(10, 26.17157287525381, 10.5, 11, 27.82842712474619, 11.5),
						Block.makeCuboidShape(7.5, 26.17157287525381, 13, 8.5, 27.82842712474619, 14),
						Block.makeCuboidShape(7.5, 26.17157287525381, 8, 8.5, 27.82842712474619, 9),
						Block.makeCuboidShape(6.757359312880716, 26.25, 4.4375, 9.242640687119284, 27.25, 5.4375),
						Block.makeCuboidShape(6.757359312880716, 26.25, 4.4375, 9.242640687119284, 27.25, 5.4375),
						Block.makeCuboidShape(6.757359312880716, 21.25, 4.4375, 9.242640687119284, 22.25, 5.4375),
						Block.makeCuboidShape(6.757359312880716, 21.25, 4.4375, 9.242640687119284, 22.25, 5.4375),
						Block.makeCuboidShape(10, 23.007359312880716, 4.4375, 11, 25.492640687119284, 5.4375),
						Block.makeCuboidShape(10, 23.007359312880716, 4.4375, 11, 25.492640687119284, 5.4375),
						Block.makeCuboidShape(5, 23.007359312880716, 4.4375, 6, 25.492640687119284, 5.4375),
						Block.makeCuboidShape(5, 23.007359312880716, 4.4375, 6, 25.492640687119284, 5.4375)
				).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
				Stream.of(
						Block.makeCuboidShape(2, 0, 5, 8, 2, 11),
						Block.makeCuboidShape(3, 2, 6, 7, 3, 10),
						Block.makeCuboidShape(4, 3, 7, 6, 16, 9),
						Block.makeCuboidShape(6, 28, 7.5, 11, 29, 8.5),
						Block.makeCuboidShape(10.5, 26.75, 7.5, 11.5, 28.75, 8.5),
						Block.makeCuboidShape(8.5, 25.5, 7.5, 9.5, 28.5, 8.5),
						Block.makeCuboidShape(4.5, 26, 7.5, 5.5, 28, 8.5),
						Block.makeCuboidShape(4, 25, 7, 6, 26, 9),
						Block.makeCuboidShape(4, 31, 7, 6, 32, 9),
						Block.makeCuboidShape(4, 28, 7, 6, 29, 9),
						Block.makeCuboidShape(3, 24, 6, 7, 25, 10),
						Block.makeCuboidShape(3, 29, 6, 7, 31, 10),
						Block.makeCuboidShape(4, 16, 7, 6, 24, 9),
						Block.makeCuboidShape(4.5, 28, 6.17157287525381, 5.5, 29, 8.82842712474619),
						Block.makeCuboidShape(4.5, 25.17157287525381, 5, 5.5, 27.82842712474619, 6),
						Block.makeCuboidShape(4.5, 26.17157287525381, 5, 5.5, 27.82842712474619, 6),
						Block.makeCuboidShape(4.5, 25, 7.17157287525381, 5.5, 26, 9.82842712474619),
						Block.makeCuboidShape(3.17157287525381, 25, 7.5, 5.82842712474619, 26, 8.5),
						Block.makeCuboidShape(4.17157287525381, 25, 7.5, 6.82842712474619, 26, 8.5),
						Block.makeCuboidShape(4.5, 26.17157287525381, 10, 5.5, 28.82842712474619, 11),
						Block.makeCuboidShape(2, 26.17157287525381, 7.5, 3, 28.82842712474619, 8.5),
						Block.makeCuboidShape(7, 26.17157287525381, 7.5, 8, 28.82842712474619, 8.5),
						Block.makeCuboidShape(4.5, 26.17157287525381, 10, 5.5, 27.82842712474619, 11),
						Block.makeCuboidShape(2, 26.17157287525381, 7.5, 3, 27.82842712474619, 8.5),
						Block.makeCuboidShape(7, 26.17157287525381, 7.5, 8, 27.82842712474619, 8.5),
						Block.makeCuboidShape(10.5625, 26.25, 6.757359312880716, 11.5625, 27.25, 9.242640687119284),
						Block.makeCuboidShape(10.5625, 26.25, 6.757359312880716, 11.5625, 27.25, 9.242640687119284),
						Block.makeCuboidShape(10.5625, 21.25, 6.757359312880716, 11.5625, 22.25, 9.242640687119284),
						Block.makeCuboidShape(10.5625, 21.25, 6.757359312880716, 11.5625, 22.25, 9.242640687119284),
						Block.makeCuboidShape(10.5625, 23.007359312880716, 10, 11.5625, 25.492640687119284, 11),
						Block.makeCuboidShape(10.5625, 23.007359312880716, 10, 11.5625, 25.492640687119284, 11),
						Block.makeCuboidShape(10.5625, 23.007359312880716, 5, 11.5625, 25.492640687119284, 6),
						Block.makeCuboidShape(10.5625, 23.007359312880716, 5, 11.5625, 25.492640687119284, 6)
				).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
				Stream.of(
						Block.makeCuboidShape(5, 0, 2, 11, 2, 8),
						Block.makeCuboidShape(6, 2, 3, 10, 3, 7),
						Block.makeCuboidShape(7, 3, 4, 9, 16, 6),
						Block.makeCuboidShape(7.5, 28, 6, 8.5, 29, 11),
						Block.makeCuboidShape(7.5, 26.75, 10.5, 8.5, 28.75, 11.5),
						Block.makeCuboidShape(7.5, 25.5, 8.5, 8.5, 28.5, 9.5),
						Block.makeCuboidShape(7.5, 26, 4.5, 8.5, 28, 5.5),
						Block.makeCuboidShape(7, 25, 4, 9, 26, 6),
						Block.makeCuboidShape(7, 31, 4, 9, 32, 6),
						Block.makeCuboidShape(7, 28, 4, 9, 29, 6),
						Block.makeCuboidShape(6, 24, 3, 10, 25, 7),
						Block.makeCuboidShape(6, 29, 3, 10, 31, 7),
						Block.makeCuboidShape(7, 16, 4, 9, 24, 6),
						Block.makeCuboidShape(6.17157287525381, 28, 4.5, 8.82842712474619, 29, 5.5),
						Block.makeCuboidShape(5, 25.17157287525381, 4.5, 6, 27.82842712474619, 5.5),
						Block.makeCuboidShape(5, 26.17157287525381, 4.5, 6, 27.82842712474619, 5.5),
						Block.makeCuboidShape(7.17157287525381, 25, 4.5, 9.82842712474619, 26, 5.5),
						Block.makeCuboidShape(7.5, 25, 3.17157287525381, 8.5, 26, 5.82842712474619),
						Block.makeCuboidShape(7.5, 25, 4.17157287525381, 8.5, 26, 6.82842712474619),
						Block.makeCuboidShape(10, 26.17157287525381, 4.5, 11, 28.82842712474619, 5.5),
						Block.makeCuboidShape(7.5, 26.17157287525381, 2, 8.5, 28.82842712474619, 3),
						Block.makeCuboidShape(7.5, 26.17157287525381, 7, 8.5, 28.82842712474619, 8),
						Block.makeCuboidShape(10, 26.17157287525381, 4.5, 11, 27.82842712474619, 5.5),
						Block.makeCuboidShape(7.5, 26.17157287525381, 2, 8.5, 27.82842712474619, 3),
						Block.makeCuboidShape(7.5, 26.17157287525381, 7, 8.5, 27.82842712474619, 8),
						Block.makeCuboidShape(6.757359312880716, 26.25, 10.5625, 9.242640687119284, 27.25, 11.5625),
						Block.makeCuboidShape(6.757359312880716, 26.25, 10.5625, 9.242640687119284, 27.25, 11.5625),
						Block.makeCuboidShape(6.757359312880716, 21.25, 10.5625, 9.242640687119284, 22.25, 11.5625),
						Block.makeCuboidShape(6.757359312880716, 21.25, 10.5625, 9.242640687119284, 22.25, 11.5625),
						Block.makeCuboidShape(10, 23.007359312880716, 10.5625, 11, 25.492640687119284, 11.5625),
						Block.makeCuboidShape(10, 23.007359312880716, 10.5625, 11, 25.492640687119284, 11.5625),
						Block.makeCuboidShape(5, 23.007359312880716, 10.5625, 6, 25.492640687119284, 11.5625),
						Block.makeCuboidShape(5, 23.007359312880716, 10.5625, 6, 25.492640687119284, 11.5625)
				).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
				Stream.of(
						Block.makeCuboidShape(8, 0, 5, 14, 2, 11),
						Block.makeCuboidShape(9, 2, 6, 13, 3, 10),
						Block.makeCuboidShape(10, 3, 7, 12, 16, 9),
						Block.makeCuboidShape(5, 28, 7.5, 10, 29, 8.5),
						Block.makeCuboidShape(4.5, 26.75, 7.5, 5.5, 28.75, 8.5),
						Block.makeCuboidShape(6.5, 25.5, 7.5, 7.5, 28.5, 8.5),
						Block.makeCuboidShape(10.5, 26, 7.5, 11.5, 28, 8.5),
						Block.makeCuboidShape(10, 25, 7, 12, 26, 9),
						Block.makeCuboidShape(10, 31, 7, 12, 32, 9),
						Block.makeCuboidShape(10, 28, 7, 12, 29, 9),
						Block.makeCuboidShape(9, 24, 6, 13, 25, 10),
						Block.makeCuboidShape(9, 29, 6, 13, 31, 10),
						Block.makeCuboidShape(10, 16, 7, 12, 24, 9),
						Block.makeCuboidShape(10.5, 28, 6.17157287525381, 11.5, 29, 8.82842712474619),
						Block.makeCuboidShape(10.5, 25.17157287525381, 5, 11.5, 27.82842712474619, 6),
						Block.makeCuboidShape(10.5, 26.17157287525381, 5, 11.5, 27.82842712474619, 6),
						Block.makeCuboidShape(10.5, 25, 7.17157287525381, 11.5, 26, 9.82842712474619),
						Block.makeCuboidShape(10.17157287525381, 25, 7.5, 12.82842712474619, 26, 8.5),
						Block.makeCuboidShape(9.17157287525381, 25, 7.5, 11.82842712474619, 26, 8.5),
						Block.makeCuboidShape(10.5, 26.17157287525381, 10, 11.5, 28.82842712474619, 11),
						Block.makeCuboidShape(13, 26.17157287525381, 7.5, 14, 28.82842712474619, 8.5),
						Block.makeCuboidShape(8, 26.17157287525381, 7.5, 9, 28.82842712474619, 8.5),
						Block.makeCuboidShape(10.5, 26.17157287525381, 10, 11.5, 27.82842712474619, 11),
						Block.makeCuboidShape(13, 26.17157287525381, 7.5, 14, 27.82842712474619, 8.5),
						Block.makeCuboidShape(8, 26.17157287525381, 7.5, 9, 27.82842712474619, 8.5),
						Block.makeCuboidShape(4.4375, 26.25, 6.757359312880716, 5.4375, 27.25, 9.242640687119284),
						Block.makeCuboidShape(4.4375, 26.25, 6.757359312880716, 5.4375, 27.25, 9.242640687119284),
						Block.makeCuboidShape(4.4375, 21.25, 6.757359312880716, 5.4375, 22.25, 9.242640687119284),
						Block.makeCuboidShape(4.4375, 21.25, 6.757359312880716, 5.4375, 22.25, 9.242640687119284),
						Block.makeCuboidShape(4.4375, 23.007359312880716, 10, 5.4375, 25.492640687119284, 11),
						Block.makeCuboidShape(4.4375, 23.007359312880716, 10, 5.4375, 25.492640687119284, 11),
						Block.makeCuboidShape(4.4375, 23.007359312880716, 5, 5.4375, 25.492640687119284, 6),
						Block.makeCuboidShape(4.4375, 23.007359312880716, 5, 5.4375, 25.492640687119284, 6)
				).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()

		),

		PASTURE(
				Stream.of(
						Block.makeCuboidShape(6, 0, 10, 10, 16, 14),
						Block.makeCuboidShape(7, 28.73223304703363, 6.939339828220179, 9, 29.73223304703363, 10.939339828220179),
						Block.makeCuboidShape(7, 27.17157287525381, 7.17157287525381, 9, 28.17157287525381, 10.17157287525381),
						Block.makeCuboidShape(6, 16, 10, 10, 32, 14),
						Block.makeCuboidShape(6.757359312880716, 27.25, 7.4375, 9.242640687119284, 28.25, 8.4375),
						Block.makeCuboidShape(6.757359312880716, 27.25, 7.4375, 9.242640687119284, 28.25, 8.4375),
						Block.makeCuboidShape(6.757359312880716, 22.25, 7.4375, 9.242640687119284, 23.25, 8.4375),
						Block.makeCuboidShape(6.757359312880716, 22.25, 7.4375, 9.242640687119284, 23.25, 8.4375),
						Block.makeCuboidShape(10, 24.007359312880716, 7.4375, 11, 26.492640687119284, 8.4375),
						Block.makeCuboidShape(10, 24.007359312880716, 7.4375, 11, 26.492640687119284, 8.4375),
						Block.makeCuboidShape(5, 24.007359312880716, 7.4375, 6, 26.492640687119284, 8.4375),
						Block.makeCuboidShape(5, 24.007359312880716, 7.4375, 6, 26.492640687119284, 8.4375)
				).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
				Stream.of(
						Block.makeCuboidShape(2, 0, 6, 6, 16, 10),
						Block.makeCuboidShape(5.060660171779823, 28.73223304703363, 7, 9.060660171779823, 29.73223304703363, 9),
						Block.makeCuboidShape(5.828427124746192, 27.17157287525381, 7, 8.828427124746192, 28.17157287525381, 9),
						Block.makeCuboidShape(2, 16, 6, 6, 32, 10),
						Block.makeCuboidShape(7.5625, 27.25, 6.757359312880716, 8.5625, 28.25, 9.242640687119284),
						Block.makeCuboidShape(7.5625, 27.25, 6.757359312880716, 8.5625, 28.25, 9.242640687119284),
						Block.makeCuboidShape(7.5625, 22.25, 6.757359312880716, 8.5625, 23.25, 9.242640687119284),
						Block.makeCuboidShape(7.5625, 22.25, 6.757359312880716, 8.5625, 23.25, 9.242640687119284),
						Block.makeCuboidShape(7.5625, 24.007359312880716, 10, 8.5625, 26.492640687119284, 11),
						Block.makeCuboidShape(7.5625, 24.007359312880716, 10, 8.5625, 26.492640687119284, 11),
						Block.makeCuboidShape(7.5625, 24.007359312880716, 5, 8.5625, 26.492640687119284, 6),
						Block.makeCuboidShape(7.5625, 24.007359312880716, 5, 8.5625, 26.492640687119284, 6)
				).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
				Stream.of(
						Block.makeCuboidShape(6, 0, 2, 10, 16, 6),
						Block.makeCuboidShape(7, 28.73223304703363, 5.060660171779821, 9, 29.73223304703363, 9.060660171779821),
						Block.makeCuboidShape(7, 27.17157287525381, 5.82842712474619, 9, 28.17157287525381, 8.82842712474619),
						Block.makeCuboidShape(6, 16, 2, 10, 32, 6),
						Block.makeCuboidShape(6.757359312880716, 27.25, 7.5625, 9.242640687119284, 28.25, 8.5625),
						Block.makeCuboidShape(6.757359312880716, 27.25, 7.5625, 9.242640687119284, 28.25, 8.5625),
						Block.makeCuboidShape(6.757359312880716, 22.25, 7.5625, 9.242640687119284, 23.25, 8.5625),
						Block.makeCuboidShape(6.757359312880716, 22.25, 7.5625, 9.242640687119284, 23.25, 8.5625),
						Block.makeCuboidShape(10, 24.007359312880716, 7.5625, 11, 26.492640687119284, 8.5625),
						Block.makeCuboidShape(10, 24.007359312880716, 7.5625, 11, 26.492640687119284, 8.5625),
						Block.makeCuboidShape(5, 24.007359312880716, 7.5625, 6, 26.492640687119284, 8.5625),
						Block.makeCuboidShape(5, 24.007359312880716, 7.5625, 6, 26.492640687119284, 8.5625)
				).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
				Stream.of(
						Block.makeCuboidShape(10, 0, 6, 14, 16, 10),
						Block.makeCuboidShape(6.939339828220177, 28.73223304703363, 7, 10.939339828220177, 29.73223304703363, 9),
						Block.makeCuboidShape(7.171572875253808, 27.17157287525381, 7, 10.171572875253808, 28.17157287525381, 9),
						Block.makeCuboidShape(10, 16, 6, 14, 32, 10),
						Block.makeCuboidShape(7.4375, 27.25, 6.757359312880716, 8.4375, 28.25, 9.242640687119284),
						Block.makeCuboidShape(7.4375, 27.25, 6.757359312880716, 8.4375, 28.25, 9.242640687119284),
						Block.makeCuboidShape(7.4375, 22.25, 6.757359312880716, 8.4375, 23.25, 9.242640687119284),
						Block.makeCuboidShape(7.4375, 22.25, 6.757359312880716, 8.4375, 23.25, 9.242640687119284),
						Block.makeCuboidShape(7.4375, 24.007359312880716, 10, 8.4375, 26.492640687119284, 11),
						Block.makeCuboidShape(7.4375, 24.007359312880716, 10, 8.4375, 26.492640687119284, 11),
						Block.makeCuboidShape(7.4375, 24.007359312880716, 5, 8.4375, 26.492640687119284, 6),
						Block.makeCuboidShape(7.4375, 24.007359312880716, 5, 8.4375, 26.492640687119284, 6)
				).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()
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
