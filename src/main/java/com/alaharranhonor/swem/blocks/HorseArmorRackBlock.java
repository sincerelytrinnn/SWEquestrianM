package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.items.tack.HorseSaddleItem;
import com.alaharranhonor.swem.tileentity.HorseArmorRackTE;
import com.alaharranhonor.swem.tileentity.OneSaddleRackTE;
import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import net.minecraft.block.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HorseArmorRackBlock extends HorizontalBlock {

	public static final EnumProperty<SWEMBlockStateProperties.DoubleBlockSide> SIDE = SWEMBlockStateProperties.D_SIDE;

	public HorseArmorRackBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.setDefaultState(
				this.stateContainer.getBaseState()
						.with(HORIZONTAL_FACING, Direction.NORTH)
						.with(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT)
		);

	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		/*if (!worldIn.isRemote && handIn == Hand.MAIN_HAND) {
			BlockPos position = pos;
			if (state.get(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.RIGHT)
				position = pos.rotateCCW
			TileEntity tile = worldIn.getTileEntity(hit.getPos());
			if (tile instanceof OneSaddleRackTE) {
				OneSaddleRackTE rack = (OneSaddleRackTE) tile;
				if (player.getHeldItem(handIn).getItem() instanceof HorseSaddleItem) {
					ItemStack saddle = player.getHeldItem(handIn);
					if (rack.itemHandler.getStackInSlot(0) == ItemStack.EMPTY) {
						ItemStack saddleCopy;
						if (player.isCreative()) {
							saddleCopy = saddle.copy();
						} else {
							saddleCopy = saddle.split(1);
						}

						rack.itemHandler.setStackInSlot(0, saddleCopy);
						PacketDistributor.TRACKING_CHUNK.with(() -> rack.getWorld().getChunkAt(rack.getPos())).send(rack.getUpdatePacket());
						return ActionResultType.func_233537_a_(worldIn.isRemote);
					}
				} else {
					if (rack.itemHandler.getStackInSlot(0) != ItemStack.EMPTY) {
						ItemEntity itementity = new ItemEntity(worldIn, rack.getPos().getX(), rack.getPos().getY(), rack.getPos().getZ(), rack.itemHandler.getStackInSlot(0));
						itementity.setMotion(RANDOM.nextGaussian() * (double)0.05F, RANDOM.nextGaussian() * (double)0.05F + (double)0.2F, RANDOM.nextGaussian() * (double)0.05F);
						worldIn.addEntity(itementity);

						rack.itemHandler.setStackInSlot(0, ItemStack.EMPTY);
						PacketDistributor.TRACKING_CHUNK.with(() -> rack.getWorld().getChunkAt(rack.getPos())).send(rack.getUpdatePacket());
						return ActionResultType.func_233537_a_(worldIn.isRemote);
					}

				}
			}
		}*/
		return ActionResultType.PASS;
	}

	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		if (te instanceof HorseArmorRackTE && !player.abilities.isCreativeMode) {
			((HorseArmorRackTE)te).dropItems();
		}
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return SWEMTileEntities.HORSE_ARMOR_RACK_TILE_ENTITY.get().create();
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.create(0.01d, 0.01d, 0.01d, 0.99d, 0.99d, 0.99d);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return true;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing());
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, SIDE);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
}
