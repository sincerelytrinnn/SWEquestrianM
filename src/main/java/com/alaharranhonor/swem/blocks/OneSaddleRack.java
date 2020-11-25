package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.items.tack.HorseSaddleItem;
import com.alaharranhonor.swem.tileentity.OneSaddleRackTE;
import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SaddleItem;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class OneSaddleRack extends HorizontalBlock {

	public OneSaddleRack(Properties properties) {
		super(properties);
		this.setDefaultState(
			this.stateContainer.getBaseState()
				.with(HORIZONTAL_FACING, Direction.NORTH)
				.with(BlockStateProperties.HANGING, false)
		);

	}



	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		TileEntity tile = worldIn.getTileEntity(pos);
		OneSaddleRackTE rack = (OneSaddleRackTE) tile;
		if (player.getHeldItem(handIn).getItem() instanceof HorseSaddleItem) {
			ItemStack saddle = player.getHeldItem(handIn);
			if (rack.getItems().getItem() == Items.AIR) {
				rack.setItems(saddle);
				tile.markDirty();
				return ActionResultType.func_233537_a_(worldIn.isRemote);
			} else {
				return ActionResultType.PASS;
			}
		} else {
			if (rack.getItems().getItem() != Items.AIR) {
				ItemStack stack = rack.getItems();
				InventoryHelper.dropItems(worldIn, pos, NonNullList.withSize(1, stack));
				rack.setItems(new ItemStack(Items.AIR));
				rack.markDirty();
			}
			return ActionResultType.PASS;
		}
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return SWEMTileEntities.ONE_SADDLE_RACK_TILE_ENTITY.get().create();
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.create(0.01d, 0.01d, 0.01d, 0.99d, 0.99d, 0.99d);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		boolean wallMount = context.getWorld().getBlockState(context.getPos().offset(context.getPlacementHorizontalFacing())).isSolid();

		return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing()).with(BlockStateProperties.HANGING, wallMount);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, SWEMBlockStateProperties.HANGING);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
}
