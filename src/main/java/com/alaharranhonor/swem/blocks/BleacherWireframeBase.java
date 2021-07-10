package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class BleacherWireframeBase extends SlabBlock {

	private VoxelShape BOTTOM_SHAPE_WIREFRAME_BLEACHER = Stream.of(
			Block.box(14, 0, 14, 16, 8, 16),
			Block.box(0, 0, 14, 2, 8, 16),
			Block.box(14, 0, 0, 16, 8, 2),
			Block.box(0, 0, 0, 2, 8, 2),
			Block.box(15.0625, 0.0625, 2.0625, 15.9375, 0.9375, 15.9375),
			Block.box(0.0625, 0.0625, 2.0625, 0.9375, 0.9375, 15.9375),
			Block.box(0.0625, 0.0625, 15.0625, 13.9375, 0.9375, 15.9375),
			Block.box(0.0625, 0.0625, 0.0625, 13.9375, 0.9375, 0.9375)
	).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();

	private VoxelShape TOP_SHAPE_WIREFRAME_BLEACHER = Stream.of(
			Block.box(14, 8, 14, 16, 16, 16),
			Block.box(0, 8, 14, 2, 16, 16),
			Block.box(14, 8, 0, 16, 16, 2),
			Block.box(0, 8, 0, 2, 16, 2),
			Block.box(15.0625, 8.0625, 2.0625, 15.9375, 16, 15.9375),
			Block.box(0.0625, 8.0625, 2.0625, 0.9375, 16, 15.9375),
			Block.box(0.0625, 8.0625, 15.0625, 13.9375, 16, 15.9375),
			Block.box(0.0625, 8.0625, 0.0625, 13.9375, 16, 0.9375)
	).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();

	private VoxelShape FULL_BLOCK_WIREFRAME_BLEACHER = Stream.of(
			Block.box(14, 8, 14, 16, 16, 16),
			Block.box(0, 8, 14, 2, 16, 16),
			Block.box(14, 8, 0, 16, 16, 2),
			Block.box(0, 8, 0, 2, 16, 2),
			// The 4 pillars

			Block.box(15.0625, 0, 2.0625, 15.9375, 16, 15.9375),
			Block.box(0.0625, 0, 2.0625, 0.9375, 16, 15.9375),
			Block.box(0.0625, 0, 15.0625, 13.9375, 16, 15.9375),
			Block.box(0.0625, 0, 0.0625, 13.9375, 16, 0.9375),
			// Sides
			// Top Wireframe

			Block.box(14, 0, 14, 16, 8, 16),
			Block.box(0, 0, 14, 2, 8, 16),
			Block.box(14, 0, 0, 16, 8, 2),
			Block.box(0, 0, 0, 2, 8, 2)
			// Pillars
			// Bottom Wireframe

	).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();


	public BleacherWireframeBase(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		SlabType slab_type = state.getValue(TYPE);
		switch (slab_type) {
			case TOP:
				return TOP_SHAPE_WIREFRAME_BLEACHER;
			case BOTTOM:
				return BOTTOM_SHAPE_WIREFRAME_BLEACHER;

			default:
				return FULL_BLOCK_WIREFRAME_BLEACHER;
		}
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		World world = (World) worldIn;
		if (facing == Direction.UP && world.getBlockState(currentPos.above()).getBlock() == Blocks.AIR) {
			Block bleacher = SWEMBlocks.BLEACHER_SLAB.get();
			return bleacher.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.DOUBLE);
		}
		return stateIn;
	}


}
