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

import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.*;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.world.World;

public class GrainFeederBlock extends HorizontalBlock {

	public static final BooleanProperty LEFT = SWEMBlockStateProperties.CONNECTED_LEFT;
	public static final BooleanProperty RIGHT = SWEMBlockStateProperties.CONNECTED_RIGHT;

	public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;

	private DyeColor colour;

	public GrainFeederBlock(Properties properties, DyeColor colour) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(LEFT, false).setValue(RIGHT, false).setValue(OCCUPIED, false).setValue(FACING, Direction.NORTH));
		this.colour = colour;
	}

	public DyeColor getColour() {
		return this.colour;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IBlockReader iblockreader = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockPos blockpos1 = blockpos.relative(context.getHorizontalDirection().getClockWise());
		BlockPos blockpos2 = blockpos.relative(context.getHorizontalDirection().getCounterClockWise());
		BlockState blockstate = iblockreader.getBlockState(blockpos1);
		BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
		BlockState modified = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
		if (blockstate.canOcclude()) {
			return modified.setValue(RIGHT, true);
		} else if (blockstate1.canOcclude()) {
			return modified.setValue(LEFT, true);
		}
		return modified;
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getItemInHand(handIn);
		boolean isOccupied = state.getValue(OCCUPIED);
		if (itemstack.isEmpty() || isOccupied) {
			return ActionResultType.PASS;
		} else {
			Item item = itemstack.getItem();
			if (item == SWEMItems.SWEET_FEED_OPENED.get()) {
				if (!player.abilities.instabuild) {
					player.getItemInHand(handIn).hurtAndBreak(1, player, playerEntity -> {});
				}
				this.occupyBlock(worldIn, pos, state);

				return ActionResultType.CONSUME;
			} else {
				return ActionResultType.PASS;
			}
		}
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(LEFT, RIGHT, OCCUPIED, FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return Block.box(0, 0, 0, 15.99, 10, 15.99);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
		return VoxelShapes.box(0.01, 0.01, 0.01, 0.99, 1.5, 0.99);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.relative(state.getValue(FACING))).canOcclude();
	}

	public boolean isFeedable(World worldIn, BlockState state) {
		return state.getValue(OCCUPIED);
	}

	public void occupyBlock(World worldIn, BlockPos pos, BlockState state) {
		worldIn.setBlock(pos, state.setValue(OCCUPIED, true), 3);
	}

	public void eat(World worldIn, BlockPos pos, BlockState state) {
		worldIn.setBlock(pos, state.setValue(OCCUPIED, false), 3);
	}
}
