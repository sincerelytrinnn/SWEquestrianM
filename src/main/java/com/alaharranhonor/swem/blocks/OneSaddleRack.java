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

import com.alaharranhonor.swem.items.tack.HorseSaddleItem;
import com.alaharranhonor.swem.tileentity.OneSaddleRackTE;
import com.alaharranhonor.swem.util.registry.SWEMTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.UUID;

public class OneSaddleRack extends HorizontalBlock {

    /**
     * Instantiates a new One saddle rack.
     *
     * @param properties the properties
     */
    public OneSaddleRack(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(BlockStateProperties.HANGING, false));
    }

    @Override
    public ActionResultType use(
            BlockState state,
            World worldIn,
            BlockPos pos,
            PlayerEntity player,
            Hand handIn,
            BlockRayTraceResult hit) {
        if (!worldIn.isClientSide && handIn == Hand.MAIN_HAND) {
            TileEntity tile = worldIn.getBlockEntity(hit.getBlockPos());
            if (tile instanceof OneSaddleRackTE) {
                OneSaddleRackTE rack = (OneSaddleRackTE) tile;
                if (player.getItemInHand(handIn).getItem() instanceof HorseSaddleItem) {
                    ItemStack saddle = player.getItemInHand(handIn);
                    if (rack.itemHandler.getStackInSlot(0) == ItemStack.EMPTY) {
                        ItemStack saddleCopy;
                        if (player.isCreative()) {
                            saddleCopy = saddle.copy();
                        } else {
                            saddleCopy = saddle.split(1);
                        }
                        CompoundNBT tag = saddleCopy.getOrCreateTag();
                        tag.putUUID("UUID", UUID.randomUUID());
                        saddleCopy.setTag(tag);
                        rack.itemHandler.setStackInSlot(0, saddleCopy);
                        worldIn.playSound(
                                null, pos, SoundEvents.ARMOR_EQUIP_LEATHER, SoundCategory.NEUTRAL, 0.5F, 1.0F);
                        PacketDistributor.TRACKING_CHUNK
                                .with(() -> rack.getLevel().getChunkAt(rack.getBlockPos()))
                                .send(rack.getUpdatePacket());
                        return ActionResultType.sidedSuccess(worldIn.isClientSide);
                    }
                } else {
                    if (rack.itemHandler.getStackInSlot(0) != ItemStack.EMPTY) {
                        if (!player.abilities.instabuild) {
                            ItemEntity itementity =
                                    new ItemEntity(
                                            worldIn,
                                            rack.getBlockPos().getX(),
                                            rack.getBlockPos().getY(),
                                            rack.getBlockPos().getZ(),
                                            rack.itemHandler.getStackInSlot(0));
                            itementity.setDeltaMovement(
                                    RANDOM.nextGaussian() * (double) 0.05F,
                                    RANDOM.nextGaussian() * (double) 0.05F + (double) 0.2F,
                                    RANDOM.nextGaussian() * (double) 0.05F);
                            worldIn.addFreshEntity(itementity);
                        }

                        rack.itemHandler.setStackInSlot(0, ItemStack.EMPTY);
                        worldIn.playSound(
                                null, pos, SoundEvents.ARMOR_EQUIP_LEATHER, SoundCategory.NEUTRAL, 0.5F, 1.0F);
                        PacketDistributor.TRACKING_CHUNK
                                .with(() -> rack.getLevel().getChunkAt(rack.getBlockPos()))
                                .send(rack.getUpdatePacket());
                        return ActionResultType.sidedSuccess(worldIn.isClientSide);
                    }
                }
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public void playerDestroy(
            World worldIn,
            PlayerEntity player,
            BlockPos pos,
            BlockState state,
            @Nullable TileEntity te,
            ItemStack stack) {
        if (te instanceof OneSaddleRackTE && !player.abilities.instabuild) {
            ((OneSaddleRackTE) te).dropItems();
        }
        super.playerDestroy(worldIn, player, pos, state, te, stack);
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
    public VoxelShape getShape(
            BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.getValue(FACING).getAxis() == Direction.Axis.Z) {
            return VoxelShapes.box(0.125d, 0.01d, 0.01d, 0.875d, 0.99d, 0.99d);
        }

        return VoxelShapes.box(0.01d, 0.01d, 0.125d, 0.99d, 0.99d, 0.875d);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos.below()).canOcclude()) {
            return true;
        } else return worldIn.getBlockState(pos.relative(state.getValue(FACING))).canOcclude();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        boolean wallMount =
                context
                        .getLevel()
                        .getBlockState(context.getClickedPos().relative(context.getHorizontalDirection()))
                        .canOcclude()
                        && !context.getLevel().getBlockState(context.getClickedPos().below()).canOcclude();

        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection())
                .setValue(BlockStateProperties.HANGING, wallMount);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, BlockStateProperties.HANGING);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
