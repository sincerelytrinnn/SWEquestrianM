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

import com.alaharranhonor.swem.network.ClientStatusMessagePacket;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.network.SyncEntityIdToClient;
import com.alaharranhonor.swem.tileentity.TackBoxTE;
import com.alaharranhonor.swem.util.registry.SWEMTileEntities;
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
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public class TackBoxBlock extends HorizontalBlock {
	public TackBoxBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
				this.stateDefinition.any()
						.setValue(FACING, Direction.NORTH)
						.setValue(SWEMBlockStateProperties.D_SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT)
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
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isClientSide) {
			BlockPos offsetPos = state.getValue(SWEMBlockStateProperties.D_SIDE) == SWEMBlockStateProperties.DoubleBlockSide.RIGHT ? pos.relative(state.getValue(FACING).getClockWise()).mutable() : pos;
			TileEntity tile = worldIn.getBlockEntity(offsetPos);

			if (tile instanceof TackBoxTE) {
				if (!tile.getTileData().hasUUID("horseUUID")) {
					SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new ClientStatusMessagePacket(2, 0, new ArrayList<>()));
					return ActionResultType.FAIL;
				}
				UUID uuid = tile.getTileData().getUUID("horseUUID");
				int entityID = ((ServerWorld)worldIn).getEntity(uuid).getId();
				SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SyncEntityIdToClient(entityID, tile.getBlockPos().getX(), tile.getBlockPos().getY(), tile.getBlockPos().getZ()));
				NetworkHooks.openGui((ServerPlayerEntity) player, (TackBoxTE) tile, (buffer) -> {
					buffer.writeBlockPos(tile.getBlockPos());
				});
				return ActionResultType.CONSUME;
			}
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity te = worldIn.getBlockEntity(pos);
			if (te instanceof TackBoxTE) {
				InventoryHelper.dropContents(worldIn, pos, ((TackBoxTE)te).getItems());

			}
			if (state.getValue(SWEMBlockStateProperties.D_SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
					worldIn.setBlock(pos.relative(state.getValue(FACING).getCounterClockWise()), Blocks.AIR.defaultBlockState(), 3);
			} else {
				worldIn.setBlock(pos.relative(state.getValue(FACING).getClockWise()), Blocks.AIR.defaultBlockState(), 3);
			}
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.box(0.01d, 0.01d, 0.01d, 0.99d, 0.99d, 0.99d);
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
			if (stack.hasTag()) {
				UUID id = stack.getTag().getUUID("horseUUID");
				TileEntity tile = worldIn.getBlockEntity(pos);
				if (tile instanceof TackBoxTE) {
					tile.getTileData().putUUID("horseUUID", id);
				}
			}
		}


		worldIn.setBlock(pos.relative(state.getValue(FACING).getCounterClockWise()), state.setValue(SWEMBlockStateProperties.D_SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT), 3);

		if (!worldIn.isClientSide) {
			if (stack.hasTag()) {
				UUID id = stack.getTag().getUUID("horseUUID");
				TileEntity tile = worldIn.getBlockEntity(pos.relative(state.getValue(FACING).getClockWise()));
				if (tile instanceof TackBoxTE) {
					tile.getTileData().putUUID("horseUUID", id);
				}
			}
		}

	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		boolean flag = context.getLevel().getBlockState(context.getClickedPos().relative(context.getHorizontalDirection().getOpposite().getCounterClockWise())).getBlock() == Blocks.AIR;
		return flag ? this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()) : null;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, SWEMBlockStateProperties.D_SIDE);
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
}
