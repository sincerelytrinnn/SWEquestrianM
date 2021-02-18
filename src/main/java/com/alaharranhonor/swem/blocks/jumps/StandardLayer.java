package com.alaharranhonor.swem.blocks.jumps;

import com.alaharranhonor.swem.blocks.SWEMBlockStateProperties;
import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import net.minecraft.block.BlockState;

public enum StandardLayer {

	NONE(SWEMBlocks.JUMP_STANDARD_NONE.get().getDefaultState()),
	SCHOOLING(SWEMBlocks.JUMP_STANDARD_SCHOOLING.get().getDefaultState().with(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.LEFT), SWEMBlocks.JUMP_STANDARD_SCHOOLING.get().getDefaultState().with(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.MIDDLE), SWEMBlocks.JUMP_STANDARD_SCHOOLING.get().getDefaultState().with(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.RIGHT)),
	RADIAL(SWEMBlocks.JUMP_STANDARD_RADIAL.get().getDefaultState().with(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.LEFT), SWEMBlocks.JUMP_STANDARD_RADIAL.get().getDefaultState().with(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.MIDDLE), SWEMBlocks.JUMP_STANDARD_RADIAL.get().getDefaultState().with(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.RIGHT)),
	VERTICAL_SLAT(SWEMBlocks.JUMP_STANDARD_VERTICAL_SLAT.get().getDefaultState().with(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.LEFT), SWEMBlocks.JUMP_STANDARD_VERTICAL_SLAT.get().getDefaultState().with(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.MIDDLE), SWEMBlocks.JUMP_STANDARD_VERTICAL_SLAT.get().getDefaultState().with(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.RIGHT));

	BlockState bottomState;
	BlockState middleState;
	BlockState topState;

	StandardLayer(BlockState allState) {
		this.bottomState = allState;
		this.middleState = allState;
		this.topState = allState;
	}


	StandardLayer(BlockState bottomState, BlockState middleState, BlockState topState) {
		this.bottomState = bottomState;
		this.middleState = middleState;
		this.topState = topState;
	}

	public BlockState getBottomState() {
		return bottomState;
	}

	public BlockState getMiddleState() {
		return middleState;
	}

	public BlockState getTopState() {
		return topState;
	}
}
