package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.items.tack.HalterItem;
import com.alaharranhonor.swem.tileentity.BridleRackTE;
import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import net.minecraft.block.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
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
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;

public class BridleRackBlock extends HorizontalBlock {

	public BridleRackBlock(Properties builder) {
		super(builder);
		this.setDefaultState(
				this.stateContainer.getBaseState()
						.with(HORIZONTAL_FACING, Direction.NORTH)
		);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isRemote && handIn == Hand.MAIN_HAND) {
			TileEntity tile = worldIn.getTileEntity(pos);
			BridleRackTE rack = (BridleRackTE) tile;
			if (player.getHeldItem(handIn).getItem() instanceof HalterItem) {
				ItemStack halter = player.getHeldItem(handIn);
				if (rack.itemHandler.getStackInSlot(0) == ItemStack.EMPTY) {
					ItemStack saddleCopy;
					if (player.isCreative()) {
						saddleCopy = halter.copy();
					} else {
						saddleCopy = halter.split(1);
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
		return ActionResultType.PASS;
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof BridleRackTE) {
				((BridleRackTE)te).dropItems();
			}
		}
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (facing == stateIn.get(HORIZONTAL_FACING)) {
			if (!facingState.isSolid()) {
				ItemEntity entity = new ItemEntity((World) worldIn, currentPos.getX(), currentPos.getY(), currentPos.getZ(), new ItemStack(SWEMBlocks.BRIDLE_RACK_ITEM.get()));
				worldIn.addEntity(entity);
				return Blocks.AIR.getDefaultState();
			}
		}
		return stateIn;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return SWEMTileEntities.BRIDLE_RACK_TILE_ENTITY.get().create();
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.get(HORIZONTAL_FACING)) {
			case NORTH: {
				return VoxelShapes.create(0.25d, 0, 0, 0.75d, 1d, 0.375d);
			}
			case EAST: {
				return VoxelShapes.create(0.625d, 0, 0.25d, 1, 1d, 0.75d);
			}
			case SOUTH: {
				return VoxelShapes.create(0.25d, 0, 0.625d, 0.75d, 1d, 1);
			}
			case WEST: {
				return VoxelShapes.create(0, 0, 0.25d, 0.375d, 1d, 0.75d);
			}
			default: {
				return VoxelShapes.fullCube();
			}
		}

	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.offset(state.get(HORIZONTAL_FACING))).isSolid();
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing());
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
}
