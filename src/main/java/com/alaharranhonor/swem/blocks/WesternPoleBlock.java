package com.alaharranhonor.swem.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
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

public class WesternPoleBlock extends Block {

	public static final EnumProperty<HitchingPostBase.PostPart> PART = SWEMBlockStateProperties.POST_PART;

	public WesternPoleBlock(Properties properties) {
		super(properties);
		this.setDefaultState(
				this.stateContainer.getBaseState()
						.with(PART, HitchingPostBase.PostPart.LOWER)
		);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.get(PART) == HitchingPostBase.PostPart.UPPER) {
			return Stream.of(
					Block.makeCuboidShape(7.5, 3, 7.5, 8.5, 32, 8.5),
					Block.makeCuboidShape(6.5, 2, 6.5, 9.5, 3, 9.5),
					Block.makeCuboidShape(5.5, 0, 5.5, 10.5, 2, 10.5)
			).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get().withOffset(0.0d, -1.0d, 0.0d);
		} else {
			return Stream.of(
					Block.makeCuboidShape(7.5, 3, 7.5, 8.5, 32, 8.5),
					Block.makeCuboidShape(6.5, 2, 6.5, 9.5, 3, 9.5),
					Block.makeCuboidShape(5.5, 0, 5.5, 10.5, 2, 10.5)
			).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(PART);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		if (!worldIn.isRemote) {
			BlockPos blockpos = pos.offset(Direction.UP);
			worldIn.setBlockState(blockpos, state.with(PART, HitchingPostBase.PostPart.UPPER), 3);
			state.updateNeighbours(worldIn, pos, 3);
		}
	}


	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(PART) == HitchingPostBase.PostPart.LOWER && facing == Direction.UP && facingState.getBlock() == Blocks.AIR) {
			return Blocks.AIR.getDefaultState();
		} else if (stateIn.get(PART) == HitchingPostBase.PostPart.UPPER && facing == Direction.DOWN && facingState.getBlock() == Blocks.AIR) {
			return Blocks.AIR.getDefaultState();
		}
		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
}
