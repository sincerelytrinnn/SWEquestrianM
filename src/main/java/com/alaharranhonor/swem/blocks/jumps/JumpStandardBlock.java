package com.alaharranhonor.swem.blocks.jumps;

import com.alaharranhonor.swem.blocks.SWEMBlockStateProperties;
import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock.Properties;

public class JumpStandardBlock extends HorizontalBlock {

	public static final EnumProperty<SWEMBlockStateProperties.TripleBlockSide> STANDARD_PIECE = SWEMBlockStateProperties.T_SIDE;
	public JumpStandardBlock(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, STANDARD_PIECE);
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
