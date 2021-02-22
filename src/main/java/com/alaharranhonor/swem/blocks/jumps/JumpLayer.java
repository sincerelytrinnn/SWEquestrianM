
package com.alaharranhonor.swem.blocks.jumps;

import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;

import static com.alaharranhonor.swem.blocks.SWEMBlockStateProperties.*;

public enum JumpLayer {

	AIR(Blocks.AIR.getDefaultState(), 1,1, 5, false, new ArrayList<>()),

	NONE(SWEMBlocks.JUMP_NONE.get().getDefaultState(), 1, 5, 1, false, new ArrayList<>()),

	LOG(SWEMBlocks.JUMP_LOG.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT), SWEMBlocks.JUMP_LOG.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, false, new ArrayList<>()),
	CAVALETTI(SWEMBlocks.CAVALETTIS.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.CAVALETTIS.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, true, SWEMBlocks.CAVALETTIS),
	POLE_ON_BOX_SMALL(SWEMBlocks.POLE_ON_BOXES_SMALL.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT), SWEMBlocks.POLE_ON_BOXES_SMALL.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, true, SWEMBlocks.POLE_ON_BOXES_SMALL),
	POLE_ON_BOX_LARGE(SWEMBlocks.POLE_ON_BOXES_LARGE.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT), SWEMBlocks.POLE_ON_BOXES_LARGE.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, true, SWEMBlocks.POLE_ON_BOXES_LARGE),
	STAIR_DROP(SWEMBlocks.JUMP_STAIR_DROP.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.CAVALETTIS.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, false, new ArrayList<>()),

	HEDGE(SWEMBlocks.JUMP_HEDGE.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_HEDGE.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 2, false, new ArrayList<>()),
	WALL(SWEMBlocks.JUMP_WALL.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_WALL.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 2, false, new ArrayList<>()),

	BRUSH_BOX(SWEMBlocks.JUMP_BRUSH_BOX.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_BRUSH_BOX.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, false, new ArrayList<>()),
	FLOWER_BOX(SWEMBlocks.FLOWER_BOXES.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT), SWEMBlocks.FLOWER_BOXES.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, true, SWEMBlocks.FLOWER_BOXES),
	COOP(SWEMBlocks.JUMP_COOP.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_COOP.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, false, new ArrayList<>()),
	ROLL_TOP(SWEMBlocks.ROLL_TOPS.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT), SWEMBlocks.ROLL_TOPS.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, true, SWEMBlocks.ROLL_TOPS),
	WALL_MINI(SWEMBlocks.JUMP_WALL_MINI.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_WALL_MINI.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, false, new ArrayList<>()),
	GROUND_POLE(SWEMBlocks.GROUND_POLES.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT), 1, 1, 1, true, SWEMBlocks.GROUND_POLES),

	RAIL(SWEMBlocks.RAILS.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 5, 1, true, SWEMBlocks.RAILS),
	PLANK(SWEMBlocks.PLANKS.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.PLANKS.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 5, 1, true, SWEMBlocks.PLANKS),
	PLANK_FANCY(SWEMBlocks.FANCY_PLANKS.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.FANCY_PLANKS.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.RIGHT), SWEMBlocks.FANCY_PLANKS.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 5, 1, true, SWEMBlocks.FANCY_PLANKS),

	PANEL_WAVE(SWEMBlocks.PANELS_WAVE.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.PANELS_WAVE.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 4, 1, true, SWEMBlocks.PANELS_WAVE),
	PANEL_ARROW(SWEMBlocks.PANELS_ARROW.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.PANELS_ARROW.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 4, 1, true, SWEMBlocks.PANELS_ARROW),
	PANEL_STRIPE(SWEMBlocks.PANELS_STRIPE.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.PANELS_STRIPE.get(0).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 4, 1, true, SWEMBlocks.PANELS_STRIPE);

	//CROSS_RAILS(JUMP_CROSS_RAILS.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),JUMP_CROSS_RAILS.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 5, 1),

	//SWEDISH_RAILS(JUMP_SWEDISH_RAILS.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),JUMP_SWEDISH_RAILS.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 2, 5, 1),

	//RED_FLAG(JUMP_RED_FLAG.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 6, 6, 2),
	//WHITE_FLAG(JUMP_WHITE_FLAG.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 6, 6, 2),
	//RED_WHITE_FLAG(JUMP_RED_WHITE_FLAG.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 6, 6, 2),

	//NUMBERS(JUMP_NUMBERS.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),JUMP_NUMBERS.get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 2, 6, 2);


	BlockState endState;
	BlockState betweenState;
	BlockState middleState;
	int minLayer;
	int maxLayer;
	int minHeight;
	boolean hasColorVariants;
	List<RegistryObject<JumpBlock>> colorVariants;

	JumpLayer(BlockState allState, int minLayer, int maxLayer, int minHeight, boolean hasColorVariants, List<RegistryObject<JumpBlock>> colorVariants) {
		this.endState = allState;
		this.betweenState = allState;
		this.middleState = allState;
		this.minLayer = minLayer;
		this.maxLayer = maxLayer;
		this.minHeight = minHeight;
		this.hasColorVariants = hasColorVariants;
		this.colorVariants = colorVariants;
	}

	JumpLayer(BlockState endState, BlockState middleState, int minLayer, int maxLayer, int minHeight, boolean hasColorVariants, List<RegistryObject<JumpBlock>> colorVariants) {
		this.endState = endState;
		this.betweenState = middleState;
		this.middleState = middleState;
		this.minLayer = minLayer;
		this.maxLayer = maxLayer;
		this.minHeight = minHeight;
		this.hasColorVariants = hasColorVariants;
		this.colorVariants = colorVariants;
	}

	JumpLayer(BlockState endState, BlockState betweenState, BlockState middleState, int minLayer, int maxLayer, int minHeight, boolean hasColorVariants, List<RegistryObject<JumpBlock>> colorVariants) {
		this.endState = endState;
		this.betweenState = betweenState;
		this.middleState = middleState;
		this.minLayer = minLayer;
		this.maxLayer = maxLayer;
		this.minHeight = minHeight;this.hasColorVariants = hasColorVariants;
		this.colorVariants = colorVariants;

	}

	public BlockState getEndState(int color) {
		if (this.hasColorVariants) {
			return this.colorVariants.get(color).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT);
		}
		return endState;
	}

	public BlockState getBetweenState(int color) {
		if (this.hasColorVariants) {
			return this.colorVariants.get(color).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.RIGHT);
		}
		return betweenState;
	}

	public BlockState getMiddleState(int color) {
		if (this.hasColorVariants) {
			return this.colorVariants.get(color).get().getDefaultState().with(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE);
		}
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

	public boolean hasColorVariants() {
		return this.hasColorVariants;
	}

	public List<RegistryObject<JumpBlock>> getColorVariants() {
		return this.colorVariants;
	}

	public static boolean testForRail(JumpLayer layer) {
		return layer == RAIL || layer == PLANK || layer == PLANK_FANCY; //|| layer == SWEDISH_RAILS;
	}

	public static boolean testForNone(JumpLayer layer) {
		return layer == NONE;
	}
}

