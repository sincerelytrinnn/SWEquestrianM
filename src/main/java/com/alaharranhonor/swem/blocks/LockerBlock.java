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

import com.alaharranhonor.swem.tileentity.LockerTE;
import com.alaharranhonor.swem.util.registry.SWEMTileEntities;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
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
	/**
	 * Instantiates a new Locker block.
	 *
	 * @param properties the properties
	 */
	public LockerBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.registerDefaultState(
				this.stateDefinition.any()
						.setValue(FACING, Direction.NORTH)
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
	 * @param world The world to box the TE in
	 * @return A instance of a class extending TileEntity
	 */
	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return SWEMTileEntities.LOCKER_TILE_ENTITY.get().create();
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isClientSide) {
			TileEntity tile = worldIn.getBlockEntity(pos);
			if (tile instanceof LockerTE) {

				boolean leftHit = this.determineHitVec(hit.getLocation(), state, pos);

				LockerTE locker = (LockerTE) tile;
				locker.setLeftSideOpened(leftHit);

				NetworkHooks.openGui((ServerPlayerEntity) player, (LockerTE) tile, (buffer) -> {
					buffer.writeBlockPos(pos);
					buffer.writeBoolean(leftHit);
				});

				return ActionResultType.CONSUME;
			}
		}
		return ActionResultType.SUCCESS;
	}


	/**
	 * Determine hit vec boolean.
	 *
	 * @param hitVec the hit vec
	 * @param state  the state
	 * @param pos    the pos
	 * @return the boolean
	 */
	private boolean determineHitVec(Vector3d hitVec, BlockState state, BlockPos pos) {

		switch (state.getValue(FACING)) {
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
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity te = worldIn.getBlockEntity(pos);
			if (te instanceof LockerTE) {
				InventoryHelper.dropContents(worldIn, pos, ((LockerTE)te).getItems());

			}
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.getValue(FACING)) {
			case NORTH: {
				return VoxelShapes.box(0.01d, 0.01d, 0.01d, 0.99d, 0.99d, 0.5d);
			}
			case EAST: {
				return VoxelShapes.box(0.5d, 0.01d, 0.01d, 0.99d, 0.99d, 0.99d);
			}
			case SOUTH: {
				return VoxelShapes.box(0.01d, 0.01d, 0.5d, 0.99d, 0.99d, 0.99d);
			}
			case WEST: {
				return VoxelShapes.box(0.01d, 0.01d, 0.01d, 0.5d, 0.99d, 0.99d);
			}
			default: {
				return VoxelShapes.box(0.01d, 0.01d, 0.01d, 0.99d, 0.99d, 0.99d);
			}
		}

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

}
