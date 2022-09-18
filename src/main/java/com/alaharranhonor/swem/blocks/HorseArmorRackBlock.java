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

import com.alaharranhonor.swem.items.SWEMHorseArmorItem;
import com.alaharranhonor.swem.items.tack.AdventureSaddleItem;
import com.alaharranhonor.swem.tileentity.HorseArmorRackTE;
import com.alaharranhonor.swem.util.registry.SWEMTileEntities;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
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

public class HorseArmorRackBlock extends HorizontalBlock {

    public static final EnumProperty<SWEMBlockStateProperties.DoubleBlockSide> SIDE =
            SWEMBlockStateProperties.D_SIDE;

    /**
     * Instantiates a new Horse armor rack block.
     *
     * @param properties the properties
     */
    public HorseArmorRackBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT));
    }

    @Override
    public ActionResultType use(
            BlockState state,
            World worldIn,
            BlockPos pos,
            PlayerEntity player,
            Hand handIn,
            BlockRayTraceResult hit) {
        if (handIn == Hand.MAIN_HAND) {
            BlockPos position = pos;
            if (state.getValue(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.RIGHT)
                position = pos.relative(state.getValue(FACING).getClockWise());

            TileEntity tile = worldIn.getBlockEntity(position);
            if (tile instanceof HorseArmorRackTE) {
                HorseArmorRackTE rack = (HorseArmorRackTE) tile;
                if (player.getItemInHand(handIn).getItem() instanceof SWEMHorseArmorItem) {
                    ItemStack armor = player.getItemInHand(handIn);
                    if (rack.itemHandler.getStackInSlot(0) == ItemStack.EMPTY) {
                        ItemStack armorCopy;
                        if (player.isCreative()) {
                            armorCopy = armor.copy();
                        } else {
                            armorCopy = armor.split(1);
                        }

                        if (!worldIn.isClientSide()) {
                            rack.itemHandler.setStackInSlot(0, armorCopy);
                            worldIn.playSound(
                                    null, pos, SoundEvents.ARMOR_EQUIP_LEATHER, SoundCategory.NEUTRAL, 0.5F, 1.0F);
                            PacketDistributor.TRACKING_CHUNK
                                    .with(() -> rack.getLevel().getChunkAt(rack.getBlockPos()))
                                    .send(rack.getUpdatePacket());
                        }
                        return ActionResultType.sidedSuccess(worldIn.isClientSide);
                    }
                } else if (player.getItemInHand(handIn).getItem() instanceof AdventureSaddleItem) {
                    ItemStack saddle = player.getItemInHand(handIn);
                    if (rack.itemHandler.getStackInSlot(1) == ItemStack.EMPTY) {
                        ItemStack saddleCopy;
                        if (player.isCreative()) {
                            saddleCopy = saddle.copy();
                        } else {
                            saddleCopy = saddle.split(1);
                        }

                        if (!worldIn.isClientSide()) {
                            rack.itemHandler.setStackInSlot(1, saddleCopy);
                            worldIn.playSound(
                                null, pos, SoundEvents.ARMOR_EQUIP_LEATHER, SoundCategory.NEUTRAL, 0.5F, 1.0F);
                            PacketDistributor.TRACKING_CHUNK
                                .with(() -> rack.getLevel().getChunkAt(rack.getBlockPos()))
                                .send(rack.getUpdatePacket());
                        }

                        return ActionResultType.sidedSuccess(worldIn.isClientSide);
                    }
                } else {
                    if (rack.itemHandler.getStackInSlot(1) != ItemStack.EMPTY) {

                        if (!player.abilities.instabuild) {
                            ItemEntity itementity =
                                    new ItemEntity(
                                            worldIn,
                                            rack.getBlockPos().getX(),
                                            rack.getBlockPos().getY(),
                                            rack.getBlockPos().getZ(),
                                            rack.itemHandler.getStackInSlot(1));
                            itementity.setDeltaMovement(
                                    RANDOM.nextGaussian() * (double) 0.05F,
                                    RANDOM.nextGaussian() * (double) 0.05F + (double) 0.2F,
                                    RANDOM.nextGaussian() * (double) 0.05F);
                            worldIn.addFreshEntity(itementity);
                        }

                        if (!worldIn.isClientSide()) {
                            rack.itemHandler.setStackInSlot(1, ItemStack.EMPTY);
                            worldIn.playSound(
                                null, pos, SoundEvents.ARMOR_EQUIP_LEATHER, SoundCategory.NEUTRAL, 0.5F, 1.0F);
                            PacketDistributor.TRACKING_CHUNK
                                .with(() -> rack.getLevel().getChunkAt(rack.getBlockPos()))
                                .send(rack.getUpdatePacket());
                        }
                        return ActionResultType.sidedSuccess(worldIn.isClientSide);

                    } else if (rack.itemHandler.getStackInSlot(0) != ItemStack.EMPTY
                            && rack.itemHandler.getStackInSlot(1) == ItemStack.EMPTY) {

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

                        if (!worldIn.isClientSide()) {
                            rack.itemHandler.setStackInSlot(0, ItemStack.EMPTY);
                            worldIn.playSound(
                                null, pos, SoundEvents.ARMOR_EQUIP_LEATHER, SoundCategory.NEUTRAL, 0.5F, 1.0F);
                            PacketDistributor.TRACKING_CHUNK
                                .with(() -> rack.getLevel().getChunkAt(rack.getBlockPos()))
                                .send(rack.getUpdatePacket());
                        }
                        return ActionResultType.sidedSuccess(worldIn.isClientSide);
                    }
                }
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public void setPlacedBy(
            World worldIn,
            BlockPos pos,
            BlockState state,
            @Nullable LivingEntity placer,
            ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);

        // Rotate counter clockwise, because the Direction has been inverted in #getStateForPlacement
        worldIn.setBlock(
                pos.relative(state.getValue(FACING).getCounterClockWise()),
                state.setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT),
                3);
    }

    @Override
    public void playerWillDestroy(
            World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (state.getValue(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
            worldIn.setBlock(
                    pos.relative(state.getValue(FACING).getCounterClockWise()),
                    Blocks.AIR.defaultBlockState(),
                    3);
        } else {
            worldIn.setBlock(
                    pos.relative(state.getValue(FACING).getClockWise()), Blocks.AIR.defaultBlockState(), 3);
        }

        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public void playerDestroy(
            World worldIn,
            PlayerEntity player,
            BlockPos pos,
            BlockState state,
            @Nullable TileEntity te,
            ItemStack stack) {
        if (te instanceof HorseArmorRackTE && !player.abilities.instabuild) {
            ((HorseArmorRackTE) te).dropItems();
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
        return SWEMTileEntities.HORSE_ARMOR_RACK_TILE_ENTITY.get().create();
    }

    @Override
    public VoxelShape getShape(
            BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.box(0.01d, 0.01d, 0.01d, 0.99d, 0.99d, 0.99d);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return true;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        boolean flag =
                context
                        .getLevel()
                        .getBlockState(
                                context
                                        .getClickedPos()
                                        .relative(
                                                context.getHorizontalDirection().getOpposite().getCounterClockWise()))
                        .getBlock()
                        == Blocks.AIR;
        return flag
                ? this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
                : null;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, SIDE);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
