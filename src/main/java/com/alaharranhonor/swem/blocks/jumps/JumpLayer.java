
package com.alaharranhonor.swem.blocks.jumps;

import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import static com.alaharranhonor.swem.blocks.SWEMBlockStateProperties.*;

public enum JumpLayer {

	AIR(Blocks.AIR.getDefaultState(), 1,1, 5),

	NONE(SWEMBlocks.JUMP_NONE.get().getDefaultState(), 1, 5, 1),

	LOG(SWEMBlocks.JUMP_LOG.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT), SWEMBlocks.JUMP_LOG.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1),
	CAVALETTI(SWEMBlocks.JUMP_CAVALETTI.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_CAVALETTI.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1),
	POLE_ON_BOX(SWEMBlocks.JUMP_POLE_ON_BOX.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_POLE_ON_BOX.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1),
	STAIR_DROP(SWEMBlocks.JUMP_STAIR_DROP.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_STAIR_DROP.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1),

	HEDGE(SWEMBlocks.JUMP_HEDGE.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_HEDGE.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 2),
	WALL(SWEMBlocks.JUMP_WALL.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_WALL.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 2),

	BRUSH_BOX(SWEMBlocks.JUMP_BRUSH_BOX.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_BRUSH_BOX.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1),
	FLOWER_BOX(SWEMBlocks.JUMP_FLOWER_BOX.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_FLOWER_BOX.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1),
	COOP(SWEMBlocks.JUMP_COOP.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_COOP.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1),
	ROLL_TOP(SWEMBlocks.JUMP_ROLL_TOP.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_ROLL_TOP.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1),
	WALL_MINI(SWEMBlocks.JUMP_WALL_MINI.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_WALL_MINI.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1),
	GROUND_POLE(SWEMBlocks.JUMP_GROUND_POLE.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1),

	RAIL(SWEMBlocks.JUMP_RAIL.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 5, 1),
	PLANK(SWEMBlocks.JUMP_PLANK.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_PLANK.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 5, 1),
	PLANK_FANCY(SWEMBlocks.JUMP_PLANK_FANCY.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_PLANK_FANCY.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.RIGHT), SWEMBlocks.JUMP_PLANK_FANCY.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 5, 1),

	PANELS(SWEMBlocks.JUMP_PANELS.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_PANELS.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 4, 1);

	//CROSS_RAILS(SWEMBlocks.JUMP_CROSS_RAILS.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_CROSS_RAILS.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 5, 1),

	//SWEDISH_RAILS(SWEMBlocks.JUMP_SWEDISH_RAILS.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_SWEDISH_RAILS.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 2, 5, 1),

	//RED_FLAG(SWEMBlocks.JUMP_RED_FLAG.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 6, 6, 2),
	//WHITE_FLAG(SWEMBlocks.JUMP_WHITE_FLAG.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 6, 6, 2),
	//RED_WHITE_FLAG(SWEMBlocks.JUMP_RED_WHITE_FLAG.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 6, 6, 2),

	//NUMBERS(SWEMBlocks.JUMP_NUMBERS.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_NUMBERS.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 2, 6, 2);


	BlockState endState;
	BlockState betweenState;
	BlockState middleState;
	int minLayer;
	int maxLayer;
	int minHeight;

	JumpLayer(BlockState allState, int minLayer, int maxLayer, int minHeight) {
		this.endState = allState;
		this.betweenState = allState;
		this.middleState = allState;
		this.minLayer = minLayer;
		this.maxLayer = maxLayer;
		this.minHeight = minHeight;
	}

	JumpLayer(BlockState endState, BlockState middleState, int minLayer, int maxLayer, int minHeight) {
		this.endState = endState;
		this.betweenState = middleState;
		this.middleState = middleState;
		this.minLayer = minLayer;
		this.maxLayer = maxLayer;
		this.minHeight = minHeight;
	}

	JumpLayer(BlockState endState, BlockState betweenState, BlockState middleState, int minLayer, int maxLayer, int minHeight) {
		this.endState = endState;
		this.betweenState = betweenState;
		this.middleState = middleState;
		this.minLayer = minLayer;
		this.maxLayer = maxLayer;
		this.minHeight = minHeight;
	}

	public BlockState getEndState() {
		return endState;
	}

	public BlockState getBetweenState() {
		return betweenState;
	}

	public BlockState getMiddleState() {
		return middleState;
	}

	public int getMinLayer() {
		return minLayer;
	}

	public int getMaxLayer() {
		return maxLayer;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public static boolean testForRail(JumpLayer layer) {
		return layer == RAIL || layer == PLANK || layer == PLANK_FANCY; //|| layer == SWEDISH_RAILS;
	}

	public static boolean testForNone(JumpLayer layer) {
		return layer == NONE;
	}
}

