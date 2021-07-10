package com.alaharranhonor.swem.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.stream.Stream;

import net.minecraft.block.AbstractBlock.Properties;

public class HorsePoopBlock extends HorizontalBlock {
	public HorsePoopBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
				this.stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
		);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return Stream.of(
				Block.box(5, 0, 7, 8, 2, 9),
				Block.box(8, 0, 5, 10, 3, 7),
				Block.box(6, 0, 6, 8, 1, 7),
				Block.box(8, 0, 8, 10, 1, 11),
				Block.box(6, 0, 10, 7, 1, 11),
				Block.box(11, 0, 4, 12, 1, 5),
				Block.box(10, 0, 6, 11, 1, 8)
		).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
