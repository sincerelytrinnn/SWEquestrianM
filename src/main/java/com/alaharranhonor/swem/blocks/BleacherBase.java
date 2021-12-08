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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class BleacherBase extends SlabBlock {

	private VoxelShape BOTTOM_SHAPE_TOP_BLEACHER = Stream.of(
			Block.box(14, 0, 14, 16, 7, 16),
			Block.box(0, 0, 14, 2, 7, 16),
			Block.box(14, 0, 0, 16, 7, 2),
			Block.box(0, 0, 0, 2, 7, 2),
			// The 4 pillars ^^^
			Block.box(0, 7, 0, 16, 8, 16),
			// Top plate ^^
			Block.box(0.0625, 0, 0.0625, 13.9375, 7, 0.9375),
			Block.box(0.0625, 0, 15.0625, 13.9375, 7, 15.9375),
			Block.box(0.0625, 0, 2.0625, 0.9375, 7, 15.9375),
			Block.box(15.0625, 0, 2.0625, 15.9375, 7, 15.9375)
	).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();
	private VoxelShape TOP_SHAPE_TOP_BLEACHER = Stream.of(
			Block.box(14, 8, 14, 16, 15, 16),
			Block.box(0, 8, 14, 2, 15, 16),
			Block.box(14, 8, 0, 16, 15, 2),
			Block.box(0, 8, 0, 2, 15, 2),
			// The 4 pillars ^^^
			Block.box(0, 15, 0, 16, 16, 16),
			// Top plate ^^
			Block.box(0.0625, 8, 0.0625, 13.9375, 15, 0.9375),
			Block.box(0.0625, 8, 15.0625, 13.9375, 15, 15.9375),
			Block.box(0.0625, 8, 2.0625, 0.9375, 15, 15.9375),
			Block.box(15.0625, 8, 2.0625, 15.9375, 15, 15.9375)
	).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();

	private VoxelShape FULL_BLOCK_BLEACHER = Stream.of(
			Block.box(14, 8, 14, 16, 15, 16),
			Block.box(0, 8, 14, 2, 15, 16),
			Block.box(14, 8, 0, 16, 15, 2),
			Block.box(0, 8, 0, 2, 15, 2),
			// Pillars

			Block.box(0, 15, 0, 16, 16, 16),
			// Top plate

			Block.box(0.0625, 0, 0.0625, 13.9375, 16, 0.9375),
			Block.box(0.0625, 0, 15.0625, 13.9375, 16, 15.9375),
			Block.box(0.0625, 0, 2.0625, 0.9375, 16, 15.9375),
			Block.box(15.0625, 0, 2.0625, 15.9375, 16, 15.9375),
			// Sides
			// Top bleacher


			Block.box(14, 0, 14, 16, 8, 16),
			Block.box(0, 0, 14, 2, 8, 16),
			Block.box(14, 0, 0, 16, 8, 2),
			Block.box(0, 0, 0, 2, 8, 2)
			// Pillars
			// Bottom wireframe bleacher

	).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get();


	public BleacherBase(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		SlabType slab_type = state.getValue(TYPE);
		switch (slab_type) {
			case TOP:
				return TOP_SHAPE_TOP_BLEACHER;
			case BOTTOM:
				return BOTTOM_SHAPE_TOP_BLEACHER;
			default:
				return FULL_BLOCK_BLEACHER;
		}
	}



	/**
	 * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
	 * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
	 * returns its solidified counterpart.
	 * Note that this method should ideally consider only the specific face passed in.
	 *
	 * @param stateIn
	 * @param facing
	 * @param facingState
	 * @param worldIn
	 * @param currentPos
	 * @param facingPos
	 */
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		World world = (World) worldIn;
		if (facing == Direction.UP && stateIn.getValue(TYPE) == SlabType.DOUBLE && world.getBlockState(currentPos.above()).getBlock() == SWEMBlocks.BLEACHER_SLAB.get()) {
			Block wireframe = SWEMBlocks.BLEACHER_WIREFRAME.get();
			return wireframe.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.DOUBLE);
		}
		return stateIn;
	}


}
