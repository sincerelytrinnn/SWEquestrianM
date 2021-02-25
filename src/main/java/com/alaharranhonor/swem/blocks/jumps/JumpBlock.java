package com.alaharranhonor.swem.blocks.jumps;

import com.alaharranhonor.swem.blocks.SWEMBlockStateProperties;
import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class JumpBlock extends HorizontalBlock {

	public static final EnumProperty<SWEMBlockStateProperties.TripleBlockSide> JUMP_PIECE = SWEMBlockStateProperties.T_SIDE;

	public JumpBlock() {
		super(AbstractBlock.Properties.create(Material.IRON).notSolid());
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.get(HORIZONTAL_FACING).getAxis() == Direction.Axis.X) {
			return VoxelShapes.create(0.125d, 0, 0, 0.875d, 1.0d, 1.0d);
		} else {
			return VoxelShapes.create(0, 0, 0.125d, 1.0d, 1.0d, 0.875d);
		}
	}



	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, JUMP_PIECE);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return SWEMTileEntities.JUMP_PASSER_TILE_ENTITY.get().create();
	}
}
