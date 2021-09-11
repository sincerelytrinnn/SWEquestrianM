package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.items.tack.HalterItem;
import com.alaharranhonor.swem.tileentity.BridleRackTE;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMTileEntities;
import net.minecraft.block.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
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
		this.registerDefaultState(
				this.stateDefinition.any()
						.setValue(FACING, Direction.NORTH)
		);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isClientSide && handIn == Hand.MAIN_HAND) {
			TileEntity tile = worldIn.getBlockEntity(hit.getBlockPos());
			if (tile instanceof BridleRackTE) {
				BridleRackTE rack = (BridleRackTE) tile;
				if (player.getItemInHand(handIn).getItem() instanceof HalterItem) {
					ItemStack halter = player.getItemInHand(handIn);
					if (rack.itemHandler.getStackInSlot(0) == ItemStack.EMPTY) {
						ItemStack saddleCopy;
						if (player.isCreative()) {
							saddleCopy = halter.copy();
						} else {
							saddleCopy = halter.split(1);
						}

						rack.itemHandler.setStackInSlot(0, saddleCopy);
						worldIn.playSound(null, pos, SoundEvents.ARMOR_EQUIP_LEATHER, SoundCategory.NEUTRAL, 0.5F, 1.0F);
						PacketDistributor.TRACKING_CHUNK.with(() -> rack.getLevel().getChunkAt(rack.getBlockPos())).send(rack.getUpdatePacket());
						return ActionResultType.sidedSuccess(worldIn.isClientSide);
					}
				} else {
					if (rack.itemHandler.getStackInSlot(0) != ItemStack.EMPTY) {
						if (!player.abilities.instabuild) {
							ItemEntity itementity = new ItemEntity(worldIn, rack.getBlockPos().getX(), rack.getBlockPos().getY(), rack.getBlockPos().getZ(), rack.itemHandler.getStackInSlot(0));
							itementity.setDeltaMovement(RANDOM.nextGaussian() * (double)0.05F, RANDOM.nextGaussian() * (double)0.05F + (double)0.2F, RANDOM.nextGaussian() * (double)0.05F);
							worldIn.addFreshEntity(itementity);
						}

						rack.itemHandler.setStackInSlot(0, ItemStack.EMPTY);
						worldIn.playSound(null, pos, SoundEvents.ARMOR_EQUIP_LEATHER, SoundCategory.NEUTRAL, 0.5F, 1.0F);
						PacketDistributor.TRACKING_CHUNK.with(() -> rack.getLevel().getChunkAt(rack.getBlockPos())).send(rack.getUpdatePacket());
						return ActionResultType.sidedSuccess(worldIn.isClientSide);
					}

				}
			}
		}
		return ActionResultType.PASS;
	}

	@Override
	public void playerDestroy(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		if (te instanceof BridleRackTE && !player.abilities.instabuild) {
			((BridleRackTE)te).dropItems();
		}

		super.playerDestroy(worldIn, player, pos, state, te, stack);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (facing == stateIn.getValue(FACING)) {
			if (!facingState.canOcclude()) {
				return Blocks.AIR.defaultBlockState();
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
		switch (state.getValue(FACING)) {
			case NORTH: {
				return VoxelShapes.box(0.25d, 0, 0, 0.75d, 1d, 0.375d);
			}
			case EAST: {
				return VoxelShapes.box(0.625d, 0, 0.25d, 1, 1d, 0.75d);
			}
			case SOUTH: {
				return VoxelShapes.box(0.25d, 0, 0.625d, 0.75d, 1d, 1);
			}
			case WEST: {
				return VoxelShapes.box(0, 0, 0.25d, 0.375d, 1d, 0.75d);
			}
			default: {
				return VoxelShapes.block();
			}
		}

	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.relative(state.getValue(FACING))).canOcclude();
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
}
