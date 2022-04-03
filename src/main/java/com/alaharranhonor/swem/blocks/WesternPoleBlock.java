package com.alaharranhonor.swem.blocks;


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

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.stream.Stream;

import net.minecraft.block.AbstractBlock.Properties;

public class WesternPoleBlock extends Block {

	public static final EnumProperty<SWEMBlockStateProperties.TripleBlockSide> PART = SWEMBlockStateProperties.T_SIDE;

	/**
	 * Instantiates a new Western pole block.
	 *
	 * @param properties the properties
	 */
	public WesternPoleBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
				this.stateDefinition.any()
						.setValue(PART, SWEMBlockStateProperties.TripleBlockSide.LEFT)
		);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.getValue(PART) == SWEMBlockStateProperties.TripleBlockSide.RIGHT) {
			return Block.box(7, 0, 7, 9, 48, 9).move(0.0d, -2.0d, 0.0d);
		} else if (state.getValue(PART) == SWEMBlockStateProperties.TripleBlockSide.MIDDLE) {
			return Block.box(7, 0, 7, 9, 48, 9).move(0.0d, -1.0d, 0.0d);
		} else {
			return Block.box(7, 0, 7, 9, 48, 9);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(PART);
	}

	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(worldIn, pos, state, placer, stack);
		if (!worldIn.isClientSide) {
			BlockPos blockpos = pos.relative(Direction.UP);
			worldIn.setBlock(blockpos, state.setValue(PART, SWEMBlockStateProperties.TripleBlockSide.MIDDLE), 3);
			worldIn.setBlock(blockpos.above(), state.setValue(PART, SWEMBlockStateProperties.TripleBlockSide.RIGHT), 3);
			state.updateNeighbourShapes(worldIn, pos, 3);
		}
	}


	@Override
	public void playerWillDestroy(World p_176208_1_, BlockPos p_176208_2_, BlockState p_176208_3_, PlayerEntity p_176208_4_) {
		super.playerWillDestroy(p_176208_1_, p_176208_2_, p_176208_3_, p_176208_4_);

		if (p_176208_3_.getValue(PART) == SWEMBlockStateProperties.TripleBlockSide.LEFT) {
			p_176208_1_.setBlock(p_176208_2_.above(), Blocks.AIR.defaultBlockState(), 3);
			p_176208_1_.setBlock(p_176208_2_.above().above(), Blocks.AIR.defaultBlockState(), 3);
		} else if (p_176208_3_.getValue(PART) == SWEMBlockStateProperties.TripleBlockSide.RIGHT) {
			p_176208_1_.setBlock(p_176208_2_.below(), Blocks.AIR.defaultBlockState(), 3);
			p_176208_1_.setBlock(p_176208_2_.below().below(), Blocks.AIR.defaultBlockState(), 3);
		} else if (p_176208_3_.getValue(PART) == SWEMBlockStateProperties.TripleBlockSide.MIDDLE) {
			p_176208_1_.setBlock(p_176208_2_.below(), Blocks.AIR.defaultBlockState(), 3);
			p_176208_1_.setBlock(p_176208_2_.above(), Blocks.AIR.defaultBlockState(), 3);
		}
	}

	@Override
	public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
		return pLevel.getBlockState(pPos).isAir() && pLevel.getBlockState(pPos.above()).isAir() && pLevel.getBlockState(pPos.above(2)).isAir();
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState pState) {
		return PushReaction.BLOCK;
	}
}
