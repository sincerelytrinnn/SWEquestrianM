package com.alaharranhonor.swem.blocks.jumps;


/*
 * All Rights Reserved
 *
 * Copyright (c) 2021, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.alaharranhonor.swem.blocks.SWEMBlockStateProperties;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

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
