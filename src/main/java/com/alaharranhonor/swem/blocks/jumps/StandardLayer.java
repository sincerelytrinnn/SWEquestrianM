package com.alaharranhonor.swem.blocks.jumps;

import com.alaharranhonor.swem.blocks.SWEMBlockStateProperties;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.block.BlockState;

public enum StandardLayer {

	NONE("None", SWEMBlocks.JUMP_STANDARD_NONE.get().defaultBlockState()),
	SCHOOLING("Schooling", SWEMBlocks.JUMP_STANDARD_SCHOOLING.get().defaultBlockState().setValue(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.LEFT), SWEMBlocks.JUMP_STANDARD_SCHOOLING.get().defaultBlockState().setValue(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.MIDDLE), SWEMBlocks.JUMP_STANDARD_SCHOOLING.get().defaultBlockState().setValue(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.RIGHT)),
	RADIAL("Radial", SWEMBlocks.JUMP_STANDARD_RADIAL.get().defaultBlockState().setValue(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.LEFT), SWEMBlocks.JUMP_STANDARD_RADIAL.get().defaultBlockState().setValue(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.MIDDLE), SWEMBlocks.JUMP_STANDARD_RADIAL.get().defaultBlockState().setValue(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.RIGHT)),
	VERTICAL_SLAT("Vertical Slat", SWEMBlocks.JUMP_STANDARD_VERTICAL_SLAT.get().defaultBlockState().setValue(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.LEFT), SWEMBlocks.JUMP_STANDARD_VERTICAL_SLAT.get().defaultBlockState().setValue(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.MIDDLE), SWEMBlocks.JUMP_STANDARD_VERTICAL_SLAT.get().defaultBlockState().setValue(JumpStandardBlock.STANDARD_PIECE, SWEMBlockStateProperties.TripleBlockSide.RIGHT));

	String displayName;
	BlockState bottomState;
	BlockState middleState;
	BlockState topState;

	StandardLayer(String displayName, BlockState allState) {
		this.displayName = displayName;
		this.bottomState = allState;
		this.middleState = allState;
		this.topState = allState;
	}


	StandardLayer(String displayName, BlockState bottomState, BlockState middleState, BlockState topState) {
		this.displayName = displayName;
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

	public String getDisplayName() {
		return this.displayName;
	}
}
