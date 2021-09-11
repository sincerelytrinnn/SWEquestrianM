package com.alaharranhonor.swem.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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

import net.minecraft.block.AbstractBlock.Properties;

public class WesternPoleBlock extends Block {

	public static final EnumProperty<HitchingPostBase.PostPart> PART = SWEMBlockStateProperties.POST_PART;

	public WesternPoleBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
				this.stateDefinition.any()
						.setValue(PART, HitchingPostBase.PostPart.LOWER)
		);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.getValue(PART) == HitchingPostBase.PostPart.UPPER) {
			return Stream.of(
					Block.box(7.5, 3, 7.5, 8.5, 32, 8.5),
					Block.box(6.5, 2, 6.5, 9.5, 3, 9.5),
					Block.box(5.5, 0, 5.5, 10.5, 2, 10.5)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get().move(0.0d, -1.0d, 0.0d);
		} else {
			return Stream.of(
					Block.box(7.5, 3, 7.5, 8.5, 32, 8.5),
					Block.box(6.5, 2, 6.5, 9.5, 3, 9.5),
					Block.box(5.5, 0, 5.5, 10.5, 2, 10.5)
			).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
		}
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(PART);
	}

	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(worldIn, pos, state, placer, stack);
		if (!worldIn.isClientSide) {
			BlockPos blockpos = pos.relative(Direction.UP);
			worldIn.setBlock(blockpos, state.setValue(PART, HitchingPostBase.PostPart.UPPER), 3);
			state.updateNeighbourShapes(worldIn, pos, 3);
		}
	}


	@Override
	public void playerWillDestroy(World p_176208_1_, BlockPos p_176208_2_, BlockState p_176208_3_, PlayerEntity p_176208_4_) {
		super.playerWillDestroy(p_176208_1_, p_176208_2_, p_176208_3_, p_176208_4_);

		if (p_176208_3_.getValue(PART) == HitchingPostBase.PostPart.LOWER) {
			p_176208_1_.setBlock(p_176208_2_.above(), Blocks.AIR.defaultBlockState(), 3);
		} else if (p_176208_3_.getValue(PART) == HitchingPostBase.PostPart.UPPER) {
			p_176208_1_.setBlock(p_176208_2_.below(), Blocks.AIR.defaultBlockState(), 3);
		}
	}
}
