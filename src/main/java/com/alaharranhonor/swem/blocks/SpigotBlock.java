package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.util.SWEMUtil;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class SpigotBlock extends HorizontalBlock {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;

    public static final VoxelShape SHAPE_N = Stream.of(Block.box(6, 8, 0, 10, 12, 5)).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();
    public static final VoxelShape SHAPE_E = Stream.of(Block.box(11, 8, 6, 16, 12, 10)).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();
    public static final VoxelShape SHAPE_S = Stream.of(Block.box(6, 8, 11, 10, 12, 16)).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();
    public static final VoxelShape SHAPE_W = Stream.of(Block.box(0, 8, 6, 5, 12, 10)).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();


    /**
     * Instantiates a new Spigot.
     *
     * @param properties the properties
     */
    public SpigotBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
        boolean hasWater = SWEMUtil.isInDistanceOfBlock(pLevel, pPos, 15, SWEMBlocks.BLOCK_O_WATER.get());
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Item item = itemstack.getItem();


        if (item == Items.BUCKET) {
            if (hasWater) {
                itemstack.shrink(1);
                if (itemstack.isEmpty()) {
                    pPlayer.setItemInHand(pHand, new ItemStack(Items.WATER_BUCKET));
                } else if (!pPlayer.inventory.add(new ItemStack(Items.WATER_BUCKET))) {
                    pPlayer.drop(new ItemStack(Items.WATER_BUCKET), false);
                }
                pPlayer.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);

                if (!pLevel.isClientSide) {
                    CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity) pPlayer, new ItemStack(Items.WATER_BUCKET));
                }
                return ActionResultType.sidedSuccess(pLevel.isClientSide());
            } else {
                pPlayer.displayClientMessage(new StringTextComponent("There is no water nearby."), true);
                return ActionResultType.FAIL;
            }
        }

        BlockState belowState = pLevel.getBlockState(pPos.below());
        if (belowState.getBlock() instanceof CauldronBlock || belowState.getBlock() instanceof HalfBarrelBlock) {
            if (hasWater) {
                int i = belowState.getValue(CauldronBlock.LEVEL);
                if (i == 3) return ActionResultType.PASS;
                if (i < 3 && !pLevel.isClientSide) {

                    pPlayer.awardStat(Stats.FILL_CAULDRON);
                    pLevel.setBlock(pPos.below(), belowState.setValue(CauldronBlock.LEVEL, MathHelper.clamp(3, 0, 3)), 3);
                    pLevel.updateNeighbourForOutputSignal(pPos, this);

                    pLevel.playSound(null, pPos, SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return ActionResultType.sidedSuccess(pLevel.isClientSide());
            } else {
                pPlayer.displayClientMessage(new StringTextComponent("There is no water nearby."), true);
                return ActionResultType.FAIL;
            }
        } else if (belowState.getBlock() instanceof WaterTroughBlock) {
            if (hasWater) {
                WaterTroughBlock wtBlock = (WaterTroughBlock) belowState.getBlock();
                if (!pLevel.isClientSide())
                    wtBlock.setWaterLevel(pLevel, pPos.below(), belowState, false);
                pLevel.playSound(null, pPos, SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return ActionResultType.sidedSuccess(pLevel.isClientSide());
            } else {
                pPlayer.displayClientMessage(new StringTextComponent("There is no water nearby."), true);
                return ActionResultType.FAIL;
            }
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(FACING)) {
            case EAST: {
                return SHAPE_E;
            }
            case SOUTH: {
                return SHAPE_S;
            }
            case WEST: {
                return SHAPE_W;
            }
            default: {
                return SHAPE_N;
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
