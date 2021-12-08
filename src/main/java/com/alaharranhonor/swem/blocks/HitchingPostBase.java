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
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.stream.Stream;

import net.minecraft.block.AbstractBlock.Properties;

public class HitchingPostBase extends Block {

	private final HitchingPostType type;
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	public static final EnumProperty<PostPart> PART = SWEMBlockStateProperties.POST_PART;


	public HitchingPostBase(HitchingPostType type, Properties properties) {
		super(properties);

		this.type = type;
		this.registerDefaultState(
				this.stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
				.setValue(PART, PostPart.LOWER)
		);

	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		VoxelShape shape = this.type.getVoxelShape(state.getValue(FACING));
		if (state.getValue(PART) == PostPart.UPPER) {
			return shape.move(0.0d, -1.0d, 0.0d);
		} else {
			return shape;
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(PART, PostPart.LOWER);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
		builder.add(PART);
	}


	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isClientSide) {
			ItemStack itemstack = player.getItemInHand(handIn);
			return itemstack.getItem() == Items.LEAD ? ActionResultType.SUCCESS : ActionResultType.PASS;
		} else {
			return LeadItem.bindPlayerMobs(player, worldIn, state.getValue(PART) == PostPart.LOWER ? pos.above() : pos);
		}
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	 *
	 * @param worldIn
	 * @param pos
	 * @param state
	 * @param placer
	 * @param stack
	 */
	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (!worldIn.isClientSide) {
			BlockPos blockpos = pos.relative(Direction.UP);
			worldIn.setBlock(blockpos, state.setValue(PART, PostPart.UPPER), 3);
		}
	}

	@Override
	public void playerWillDestroy(World p_176208_1_, BlockPos p_176208_2_, BlockState p_176208_3_, PlayerEntity p_176208_4_) {
		super.playerWillDestroy(p_176208_1_, p_176208_2_, p_176208_3_, p_176208_4_);

		if (p_176208_3_.getValue(PART) == PostPart.LOWER) {
			p_176208_1_.setBlock(p_176208_2_.above(), Blocks.AIR.defaultBlockState(), 3);
		} else if (p_176208_3_.getValue(PART) == PostPart.UPPER) {
			p_176208_1_.setBlock(p_176208_2_.below(), Blocks.AIR.defaultBlockState(), 3);
		}
	}

	public enum HitchingPostType {

		WESTERN(
				Block.box(5, 0, 9, 11, 32, 15),
				Block.box(1, 0, 5, 7, 32, 11),
				Block.box(5, 0, 1, 11, 32, 7),
				Block.box(9, 0, 5, 15, 32, 11)

		),

		ENGLISH(
				Block.box(5, 0, 8, 11, 32, 14),
				Block.box(2, 0, 5, 8, 32, 11),
				Block.box(5, 0, 2, 11, 32, 8),
				Block.box(8, 0, 5, 14, 32, 11)

		),

		PASTURE(
				Block.box(6, 0, 10, 10, 32, 14),
				Block.box(2, 0, 6, 6, 32, 10),
				Block.box(6, 0, 2, 10, 32, 6),
				Block.box(10, 0, 6, 14, 32, 10)
		);


		private final VoxelShape north;
		private final VoxelShape east;
		private final VoxelShape south;
		private final VoxelShape west;
		HitchingPostType(VoxelShape north, VoxelShape east, VoxelShape south, VoxelShape west) {
			this.north = north;
			this.east = east;
			this.south = south;
			this.west = west;
		}

		public VoxelShape getVoxelShape(Direction facing) {
			switch (facing) {
				case EAST:
					return this.east;
				case SOUTH:
					return this.south;
				case WEST:
					return this.west;
				default:
					return this.north;
			}
		}
	}

	public enum PostPart implements IStringSerializable {
		LOWER("lower"),
		UPPER("upper");

		private final String name;

		private PostPart(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}

		public String getSerializedName() {
			return this.name;
		}
	}



}
