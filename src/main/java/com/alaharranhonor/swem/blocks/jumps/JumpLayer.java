
package com.alaharranhonor.swem.blocks.jumps;

import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;

import com.alaharranhonor.swem.blocks.SWEMBlockStateProperties.TripleBlockSide;

public enum JumpLayer {

	AIR("Air", Blocks.AIR.defaultBlockState(), 1,1, 5, false, new ArrayList<>()),

	NONE("None", Blocks.AIR.defaultBlockState(), 1, 5, 1, false, new ArrayList<>()),

	LOG("Log", SWEMBlocks.JUMP_LOG.get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT), SWEMBlocks.JUMP_LOG.get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, false, new ArrayList<>()),
	CAVALETTI("Cavaletti", SWEMBlocks.CAVALETTIS.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.CAVALETTIS.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, true, SWEMBlocks.CAVALETTIS),
	POLE_ON_BOX_SMALL("Pole On Box Small", SWEMBlocks.POLE_ON_BOXES_SMALL.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT), SWEMBlocks.POLE_ON_BOXES_SMALL.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, true, SWEMBlocks.POLE_ON_BOXES_SMALL),
	POLE_ON_BOX_LARGE("Pole On Box Large", SWEMBlocks.POLE_ON_BOXES_LARGE.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT), SWEMBlocks.POLE_ON_BOXES_LARGE.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, true, SWEMBlocks.POLE_ON_BOXES_LARGE),
	STAIR_DROP("Stair Drop", SWEMBlocks.JUMP_STAIR_DROP.get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.CAVALETTIS.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, false, new ArrayList<>()),

	HEDGE("Hedge", SWEMBlocks.JUMP_HEDGE.get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_HEDGE.get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 2, false, new ArrayList<>()),
	WALL("Wall", SWEMBlocks.JUMP_WALL.get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_WALL.get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 2, false, new ArrayList<>()),

	BRUSH_BOX("Brush Box", SWEMBlocks.JUMP_BRUSH_BOX.get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_BRUSH_BOX.get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, false, new ArrayList<>()),
	FLOWER_BOX("Flower Box", SWEMBlocks.FLOWER_BOXES.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT), SWEMBlocks.FLOWER_BOXES.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, true, SWEMBlocks.FLOWER_BOXES),
	COOP("Coop", SWEMBlocks.JUMP_COOP.get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_COOP.get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, false, new ArrayList<>()),
	ROLL_TOP("Roll Top", SWEMBlocks.ROLL_TOPS.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT), SWEMBlocks.ROLL_TOPS.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, true, SWEMBlocks.ROLL_TOPS),
	WALL_MINI("Wall Mini", SWEMBlocks.JUMP_WALL_MINI.get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.JUMP_WALL_MINI.get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 1, 1, false, new ArrayList<>()),
	GROUND_POLE("Ground Pole", SWEMBlocks.GROUND_POLES.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT), 1, 1, 1, true, SWEMBlocks.GROUND_POLES),

	RAIL("Rail", SWEMBlocks.RAILS.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 5, 1, true, SWEMBlocks.RAILS),
	PLANK("Plank", SWEMBlocks.PLANKS.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.PLANKS.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 5, 1, true, SWEMBlocks.PLANKS),
	PLANK_FANCY("Plank Fancy", SWEMBlocks.FANCY_PLANKS.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.FANCY_PLANKS.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.RIGHT), SWEMBlocks.FANCY_PLANKS.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 5, 1, true, SWEMBlocks.FANCY_PLANKS),

	PANEL_WAVE("Panel Wave", SWEMBlocks.PANELS_WAVE.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.PANELS_WAVE.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 4, 1, true, SWEMBlocks.PANELS_WAVE),
	PANEL_ARROW("Panel Arrow", SWEMBlocks.PANELS_ARROW.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.PANELS_ARROW.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 4, 1, true, SWEMBlocks.PANELS_ARROW),
	PANEL_STRIPE("Panel Stripe", SWEMBlocks.PANELS_STRIPE.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),SWEMBlocks.PANELS_STRIPE.get(0).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 4, 1, true, SWEMBlocks.PANELS_STRIPE);

	//CROSS_RAILS(JUMP_CROSS_RAILS.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),JUMP_CROSS_RAILS.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 5, 1),

	//SWEDISH_RAILS(JUMP_SWEDISH_RAILS.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),JUMP_SWEDISH_RAILS.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 2, 5, 1),

	//RED_FLAG(JUMP_RED_FLAG.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 6, 6, 2),
	//WHITE_FLAG(JUMP_WHITE_FLAG.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 6, 6, 2),
	//RED_WHITE_FLAG(JUMP_RED_WHITE_FLAG.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 6, 6, 2),

	//NUMBERS(JUMP_NUMBERS.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),JUMP_NUMBERS.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 2, 6, 2);

	String displayName;
	BlockState endState;
	BlockState betweenState;
	BlockState middleState;
	int minLayer;
	int maxLayer;
	int minHeight;
	boolean hasColorVariants;
	List<RegistryObject<JumpBlock>> colorVariants;

	JumpLayer(String displayName, BlockState allState, int minLayer, int maxLayer, int minHeight, boolean hasColorVariants, List<RegistryObject<JumpBlock>> colorVariants) {
		this.displayName = displayName;
		this.endState = allState;
		this.betweenState = allState;
		this.middleState = allState;
		this.minLayer = minLayer;
		this.maxLayer = maxLayer;
		this.minHeight = minHeight;
		this.hasColorVariants = hasColorVariants;
		this.colorVariants = colorVariants;
	}

	JumpLayer(String displayName, BlockState endState, BlockState middleState, int minLayer, int maxLayer, int minHeight, boolean hasColorVariants, List<RegistryObject<JumpBlock>> colorVariants) {
		this.displayName = displayName;
		this.endState = endState;
		this.betweenState = middleState;
		this.middleState = middleState;
		this.minLayer = minLayer;
		this.maxLayer = maxLayer;
		this.minHeight = minHeight;
		this.hasColorVariants = hasColorVariants;
		this.colorVariants = colorVariants;
	}

	JumpLayer(String displayName, BlockState endState, BlockState betweenState, BlockState middleState, int minLayer, int maxLayer, int minHeight, boolean hasColorVariants, List<RegistryObject<JumpBlock>> colorVariants) {
		this.displayName = displayName;
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
			return this.colorVariants.get(color).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT);
		}
		return endState;
	}

	public BlockState getBetweenState(int color) {
		if (this.hasColorVariants) {
			return this.colorVariants.get(color).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.RIGHT);
		}
		return betweenState;
	}

	public BlockState getMiddleState(int color) {
		if (this.hasColorVariants) {
			return this.colorVariants.get(color).get().defaultBlockState().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE);
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

	public String getDisplayName() {
		return displayName;
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

