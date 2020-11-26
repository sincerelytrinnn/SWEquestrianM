package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SixWayBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SlowFeederBlock extends Block {

	public static final BooleanProperty NORTH = SixWayBlock.NORTH;
	public static final BooleanProperty EAST = SixWayBlock.EAST;
	public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
	public static final BooleanProperty WEST = SixWayBlock.WEST;

	public static final IntegerProperty LEVEL = SWEMBlockStateProperties.LEVEL_0_2;
	public static final IntegerProperty LEVEL_VANILLA = SWEMBlockStateProperties.LEVEL_0_2_VANILLA;

	public SlowFeederBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, Boolean.valueOf(false)).with(EAST, Boolean.valueOf(false)).with(SOUTH, Boolean.valueOf(false)).with(WEST, Boolean.valueOf(false)).with(LEVEL, 0).with(LEVEL_VANILLA, 0));
	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IBlockReader iblockreader = context.getWorld();
		BlockPos blockpos = context.getPos();
		FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
		BlockPos blockpos1 = blockpos.north();
		BlockPos blockpos2 = blockpos.east();
		BlockPos blockpos3 = blockpos.south();
		BlockPos blockpos4 = blockpos.west();
		BlockState blockstate = iblockreader.getBlockState(blockpos1);
		BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
		BlockState blockstate2 = iblockreader.getBlockState(blockpos3);
		BlockState blockstate3 = iblockreader.getBlockState(blockpos4);
		return this.getDefaultState().with(NORTH, this.isBlock(blockstate)).with(EAST, this.isBlock(blockstate1)).with(SOUTH, this.isBlock(blockstate2)).with(WEST, this.isBlock(blockstate3));
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getHeldItem(handIn);
		if (itemstack.isEmpty()) {
			return ActionResultType.PASS;
		} else {
			int level_swem = state.get(LEVEL);
			int level_vanilla = state.get(LEVEL_VANILLA);
			Item item = itemstack.getItem();
			if (item == SWEMBlocks.QUALITY_BALE_ITEM.get() && level_vanilla == 0) {
				if (level_swem == 0) {
					this.setHayLevel(worldIn, pos, state, LEVEL, 2);
					return ActionResultType.func_233537_a_(worldIn.isRemote);
				} else {
					return ActionResultType.PASS;
				}

			} else if (item == Items.HAY_BLOCK && level_swem == 0) {
				if (level_vanilla == 0) {
					this.setHayLevel(worldIn, pos, state, LEVEL_VANILLA, 2);
					return ActionResultType.func_233537_a_(worldIn.isRemote);
				} else {
					return ActionResultType.PASS;
				}

			} else {
				return ActionResultType.PASS;
			}
		}
	}

	private Boolean isBlock(BlockState state) {
		if (!state.equals(Blocks.AIR.getDefaultState()) && !state.equals(Blocks.CAVE_AIR.getDefaultState()) && !state.equals(Blocks.VOID_AIR.getDefaultState())) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, WEST, SOUTH, LEVEL, LEVEL_VANILLA);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return Block.makeCuboidShape(0, 0, 0, 15.99, 15.99, 15.99);
	}

	public boolean isFeedable(World worldIn, BlockState state) {
		int level = state.get(LEVEL);

		if (level > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void setHayLevel(World worldIn, BlockPos pos, BlockState state, IntegerProperty prop, int level) {
		worldIn.setBlockState(pos, state.with(prop, Integer.valueOf(MathHelper.clamp(level, 0, 2))));
	}

	public void eatHay(World worldIn, BlockPos pos, BlockState state) {
		int level = state.get(LEVEL);
		int level_vanilla = state.get(LEVEL_VANILLA);

		if ( level > 0 ) {
			worldIn.setBlockState(pos, state.with(LEVEL, Integer.valueOf(MathHelper.clamp(level - 1, 0, 2))));
		} else if ( level_vanilla > 0 ) {
			worldIn.setBlockState(pos, state.with(LEVEL_VANILLA, Integer.valueOf(MathHelper.clamp(level - 1, 0, 2))));
		}
	}
}
