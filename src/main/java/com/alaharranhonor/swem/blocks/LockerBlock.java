package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.tileentity.LockerTE;
import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.item.BlockItemUseContext;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class LockerBlock extends HorizontalBlock {
	public LockerBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.setDefaultState(
				this.stateContainer.getBaseState()
						.with(HORIZONTAL_FACING, Direction.NORTH)
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
		return SWEMTileEntities.LOCKER_TILE_ENTITY.get().create();
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isRemote) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile instanceof LockerTE) {

				boolean leftHit = this.determineHitVec(hit.getHitVec(), state, pos);

				LockerTE locker = (LockerTE) tile;
				locker.setLeftSideOpened(leftHit);

				NetworkHooks.openGui((ServerPlayerEntity) player, (LockerTE) tile, (buffer) -> {
					buffer.writeBlockPos(pos);
					buffer.writeBoolean(leftHit);
				});

				return ActionResultType.CONSUME;
			}
		}
		return ActionResultType.FAIL;
	}


	private boolean determineHitVec(Vector3d hitVec, BlockState state, BlockPos pos) {

		switch (state.get(HORIZONTAL_FACING)) {
			case EAST: {
				return !(hitVec.z > pos.getZ() + 0.5);
			}
			case WEST: {
				return !(hitVec.z < pos.getZ() + 0.5);
			}
			case NORTH: {
				return !(hitVec.x > pos.getX() + 0.5);
			}
			case SOUTH: {
				return !(hitVec.x < pos.getX() + 0.5);
			}
			default: {
				return true;
			}
		}
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof LockerTE) {
				InventoryHelper.dropItems(worldIn, pos, ((LockerTE)te).getItems());

			}
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.create(0.01d, 0.01d, 0.01d, 0.99d, 0.99d, 0.99d);
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

}
