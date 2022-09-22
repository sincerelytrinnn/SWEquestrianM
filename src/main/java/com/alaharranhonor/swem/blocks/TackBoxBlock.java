package com.alaharranhonor.swem.blocks;

/*
 * All Rights Reserved
 *
 * Copyright (c) 2022, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.network.ClientStatusMessagePacket;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.tileentity.TackBoxTE;
import com.alaharranhonor.swem.util.registry.SWEMTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
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
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public class TackBoxBlock extends HorizontalBlock {
    public static final EnumProperty<SWEMBlockStateProperties.TackBoxType> TYPE =
            SWEMBlockStateProperties.TACK_BOX_TYPE;
    public static final IntegerProperty COLOR = IntegerProperty.create("color", 0, 15);

    public TackBoxBlock(Properties properties, int color) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(TYPE, SWEMBlockStateProperties.TackBoxType.SINGLE)
                        .setValue(COLOR, color));
    }

    public static Direction getConnectedDirection(BlockState blockState) {
        Direction direction = blockState.getValue(FACING);
        return blockState.getValue(TYPE) == SWEMBlockStateProperties.TackBoxType.LEFT
                ? direction.getClockWise()
                : direction.getCounterClockWise();
    }

    /**
     * Called throughout the code as a replacement for block instanceof BlockContainer Moving this to
     * the Block base class allows for mods that wish to extend vanilla blocks, and also want to have
     * a tile entity on that block, may.
     *
     * <p>Return true from this function to specify this block has a tile entity.
     *
     * @param state State of the current block
     * @return True if block has a tile entity, false otherwise
     */
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    /**
     * Called throughout the code as a replacement for ITileEntityProvider.createNewTileEntity Return
     * the same thing you would from that function. This will fall back to
     * ITileEntityProvider.createNewTileEntity(World) if this block is a ITileEntityProvider
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
    public ActionResultType use(
            BlockState state,
            World worldIn,
            BlockPos pos,
            PlayerEntity player,
            Hand handIn,
            BlockRayTraceResult hit) {

        if (!worldIn.isClientSide) {
            BlockPos offsetPos =
                    state.getValue(TYPE) == SWEMBlockStateProperties.TackBoxType.LEFT
                            ? pos.relative(state.getValue(FACING).getClockWise()).mutable()
                            : pos;
            TileEntity tile = worldIn.getBlockEntity(offsetPos);
            if (tile instanceof TackBoxTE) {
                if (!tile.getTileData().hasUUID("horseUUID")) {
                    SWEMPacketHandler.INSTANCE.send(
                            PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player),
                            new ClientStatusMessagePacket(2, 0, new ArrayList<>()));
                    return ActionResultType.FAIL;
                }
                Entity entity = ((ServerWorld) worldIn).getEntity(tile.getTileData().getUUID("horseUUID"));
                if (entity instanceof SWEMHorseEntityBase) {
                    NetworkHooks.openGui(
                            (ServerPlayerEntity) player,
                            (TackBoxTE) tile,
                            (buffer) -> {
                                buffer.writeBlockPos(tile.getBlockPos());
                                buffer.writeInt(entity.getId());
                            });
                    return ActionResultType.CONSUME;
                }

                return ActionResultType.FAIL;
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onRemove(
            BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity te = worldIn.getBlockEntity(pos);
            if (te instanceof TackBoxTE) {
                InventoryHelper.dropContents(worldIn, pos, ((TackBoxTE) te).getItems());
            }
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
    public void setPlacedBy(
            World worldIn,
            BlockPos pos,
            BlockState state,
            @Nullable LivingEntity placer,
            ItemStack stack) {
        if (!worldIn.isClientSide) {
            if (stack.hasTag()) {
                UUID id = stack.getTag().getUUID("horseUUID");
                TileEntity tile = worldIn.getBlockEntity(pos);
                TileEntity tile2 =
                        worldIn.getBlockEntity(pos.relative(state.getValue(FACING).getClockWise()));
                if (tile instanceof TackBoxTE) {
                    tile.getTileData().putUUID("horseUUID", id);
                }
                if (tile2 instanceof TackBoxTE) {
                    tile2.getTileData().putUUID("horseUUID", id);
                }
            }
        }
    }

    @Override
    public VoxelShape getShape(
            BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.box(0.05d, 0.05d, 0.01d, 0.99d, 0.75d, 0.99d);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, COLOR);
    }

    private Direction candidatePartnerFacing(BlockItemUseContext pContext, Direction pDirection) {
        BlockState blockstate =
                pContext.getLevel().getBlockState(pContext.getClickedPos().relative(pDirection));
        return blockstate.is(this)
                && blockstate.getValue(TYPE) == SWEMBlockStateProperties.TackBoxType.SINGLE
                ? blockstate.getValue(FACING)
                : null;
    }

    public BlockState getStateForPlacement(BlockItemUseContext pContext) {
        SWEMBlockStateProperties.TackBoxType tackBoxType = SWEMBlockStateProperties.TackBoxType.SINGLE;
        Direction direction = pContext.getHorizontalDirection().getOpposite();
        boolean flag = pContext.isSecondaryUseActive();
        Direction direction1 = pContext.getClickedFace();
        if (direction1.getAxis().isHorizontal() && flag) {
            Direction direction2 = this.candidatePartnerFacing(pContext, direction1.getOpposite());
            if (direction2 != null && direction2.getAxis() != direction1.getAxis()) {
                direction = direction2;
                tackBoxType =
                        direction2.getCounterClockWise() == direction1.getOpposite()
                                ? SWEMBlockStateProperties.TackBoxType.RIGHT
                                : SWEMBlockStateProperties.TackBoxType.LEFT;
            }
        }

        if (tackBoxType == SWEMBlockStateProperties.TackBoxType.SINGLE && !flag) {
            if (direction == this.candidatePartnerFacing(pContext, direction.getClockWise())) {
                tackBoxType = SWEMBlockStateProperties.TackBoxType.LEFT;
            } else if (direction
                    == this.candidatePartnerFacing(pContext, direction.getCounterClockWise())) {
                tackBoxType = SWEMBlockStateProperties.TackBoxType.RIGHT;
            }
        }

        return this.defaultBlockState().setValue(FACING, direction).setValue(TYPE, tackBoxType);
    }

    public BlockState updateShape(
            BlockState pState,
            Direction pFacing,
            BlockState pFacingState,
            IWorld pLevel,
            BlockPos pCurrentPos,
            BlockPos pFacingPos) {
        if (pFacingState.is(this) && pFacing.getAxis().isHorizontal()) {
            SWEMBlockStateProperties.TackBoxType tackBoxType = pFacingState.getValue(TYPE);
            if (pState.getValue(TYPE) == SWEMBlockStateProperties.TackBoxType.SINGLE
                    && tackBoxType != SWEMBlockStateProperties.TackBoxType.SINGLE
                    && pState.getValue(FACING) == pFacingState.getValue(FACING)
                    && getConnectedDirection(pFacingState) == pFacing.getOpposite()) {
                return pState.setValue(TYPE, tackBoxType.getOpposite());
            }
        } else if (getConnectedDirection(pState) == pFacing) {
            return pState.setValue(TYPE, SWEMBlockStateProperties.TackBoxType.SINGLE);
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }
}
