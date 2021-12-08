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

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import net.minecraft.block.AbstractBlock.Properties;

public class FenceBaseBlock extends FourWayBlock {

	private final VoxelShape[] renderShapes;
	public static final BooleanProperty HALF_FENCE = SWEMBlockStateProperties.HALF_FENCE;

	public FenceBaseBlock(Properties properties) {
		super(2.0f, 2.0f, 16.0f, 16.0f, 24.0f, properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.valueOf(false)).setValue(EAST, Boolean.valueOf(false)).setValue(SOUTH, Boolean.valueOf(false)).setValue(WEST, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(HALF_FENCE, Boolean.valueOf(false)));
		this.renderShapes = this.makeShapes(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
	}


	public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
			return this.renderShapes[this.getAABBIndex(state)];
	}

	public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return this.getShape(state, reader, pos, context);
	}

	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}

	public boolean canConnect(BlockState state, boolean isSideSolid, Direction direction) {
		Block block = state.getBlock();
		boolean flag = this.isWoodenFence(block);
		boolean flag1 = block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, direction);
		return !isExceptionForConnection(block) && isSideSolid || flag || flag1;
	}

	private boolean isWoodenFence(Block block) {
		return block instanceof FenceBaseBlock;
	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IBlockReader iblockreader = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		BlockPos blockpos1 = blockpos.north();
		BlockPos blockpos2 = blockpos.east();
		BlockPos blockpos3 = blockpos.south();
		BlockPos blockpos4 = blockpos.west();
		BlockState blockstate = iblockreader.getBlockState(blockpos1);
		BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
		BlockState blockstate2 = iblockreader.getBlockState(blockpos3);
		BlockState blockstate3 = iblockreader.getBlockState(blockpos4);
		return super.getStateForPlacement(context).setValue(NORTH, Boolean.valueOf(this.canConnect(blockstate, blockstate.isFaceSturdy(iblockreader, blockpos1, Direction.SOUTH), Direction.SOUTH))).setValue(EAST, Boolean.valueOf(this.canConnect(blockstate1, blockstate1.isFaceSturdy(iblockreader, blockpos2, Direction.WEST), Direction.WEST))).setValue(SOUTH, Boolean.valueOf(this.canConnect(blockstate2, blockstate2.isFaceSturdy(iblockreader, blockpos3, Direction.NORTH), Direction.NORTH))).setValue(WEST, Boolean.valueOf(this.canConnect(blockstate3, blockstate3.isFaceSturdy(iblockreader, blockpos4, Direction.EAST), Direction.EAST))).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
	}

	/**
	 * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
	 * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
	 * returns its solidified counterpart.
	 * Note that this method should ideally consider only the specific face passed in.
	 */
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}

		BlockState updated = facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? stateIn.setValue(PROPERTY_BY_DIRECTION.get(facing), Boolean.valueOf(this.canConnect(facingState, facingState.isFaceSturdy(worldIn, facingPos, facing.getOpposite()), facing.getOpposite()))) : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);

		return updated;
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED, HALF_FENCE);
	}
	
}
