package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.network.SyncEntityIdToClient;
import com.alaharranhonor.swem.tileentity.TackBoxTE;
import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
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
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.UUID;

import net.minecraft.block.AbstractBlock.Properties;

public class TackBoxBlock extends HorizontalBlock {
	public TackBoxBlock(Properties properties) {
		super(properties);
		this.setDefaultState(
				this.stateContainer.getBaseState()
						.with(HORIZONTAL_FACING, Direction.NORTH)
						.with(SWEMBlockStateProperties.D_SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT)
		);
	}

	/**
	 * Called throughout the code as a replacement for block instanceof BlockContainer
	 * Moving this to the Block base class allows for mods that wish to extend vanilla
	 * blocks, and also want to have a tile entity on that block, may.
	 * <p>
	 * Return true from this function to specify this block has a tile entity.
	 *
	 * @param state State of the current block
	 * @return True if block has a tile entity, false otherwise
	 */
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	/**
	 * Called throughout the code as a replacement for ITileEntityProvider.createNewTileEntity
	 * Return the same thing you would from that function.
	 * This will fall back to ITileEntityProvider.createNewTileEntity(World) if this block is a ITileEntityProvider
	 *
	 * @param state The state of the current block
	 * @param world The world to create the TE in
	 * @return A instance of a class extending TileEntity
	 */
	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return SWEMTileEntities.TACK_BOX_TILE_ENTITY.get().create();
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isRemote) {
			BlockPos offsetPos = state.get(SWEMBlockStateProperties.D_SIDE) == SWEMBlockStateProperties.DoubleBlockSide.RIGHT ? pos.offset(state.get(HORIZONTAL_FACING).rotateY()).toMutable() : pos;
			TileEntity tile = worldIn.getTileEntity(offsetPos);

			if (tile instanceof TackBoxTE) {
				UUID uuid = tile.getTileData().getUniqueId("horseUUID");
				int entityID = ((ServerWorld)worldIn).getEntityByUuid(uuid).getEntityId();
				SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SyncEntityIdToClient(entityID, tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ()));
				NetworkHooks.openGui((ServerPlayerEntity) player, (TackBoxTE) tile, (buffer) -> {
					buffer.writeBlockPos(tile.getPos());
				});
			}
		}
		return ActionResultType.FAIL;
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof TackBoxTE) {
				InventoryHelper.dropItems(worldIn, pos, ((TackBoxTE)te).getItems());

			}
			if (state.get(SWEMBlockStateProperties.D_SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
					worldIn.setBlockState(pos.offset(state.get(HORIZONTAL_FACING).rotateYCCW()), Blocks.AIR.getDefaultState());
			} else {
				worldIn.setBlockState(pos.offset(state.get(HORIZONTAL_FACING).rotateY()), Blocks.AIR.getDefaultState());
			}
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.create(0.01d, 0.01d, 0.01d, 0.99d, 0.99d, 0.99d);
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
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (!worldIn.isRemote) {
			if (stack.hasTag()) {
				UUID id = stack.getTag().getUniqueId("horseUUID");
				TileEntity tile = worldIn.getTileEntity(pos);
				if (tile instanceof TackBoxTE) {
					tile.getTileData().putUniqueId("horseUUID", id);
				}
			}
		}


		worldIn.setBlockState(pos.offset(state.get(HORIZONTAL_FACING).rotateYCCW()), state.with(SWEMBlockStateProperties.D_SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT));

		if (!worldIn.isRemote) {
			if (stack.hasTag()) {
				UUID id = stack.getTag().getUniqueId("horseUUID");
				TileEntity tile = worldIn.getTileEntity(pos.offset(state.get(HORIZONTAL_FACING).rotateY()));
				if (tile instanceof TackBoxTE) {
					tile.getTileData().putUniqueId("horseUUID", id);
				}
			}
		}

	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, SWEMBlockStateProperties.D_SIDE);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
}
