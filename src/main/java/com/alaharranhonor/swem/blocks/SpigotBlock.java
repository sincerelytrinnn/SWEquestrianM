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
import net.minecraft.state.properties.BlockStateProperties;
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
import net.minecraft.world.IWorldReader;
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
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.HANGING, false));
        ;
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
        if (!state.getValue(BlockStateProperties.HANGING)) {
            return VoxelShapes.box(0.375D, 0.0D, 0.375D, 0.625D, 0.75D, 0.625D);
        }

        switch (state.getValue(FACING)) {
            case EAST: {
                return VoxelShapes.box(1D, 0.5D, 0.1875D, 0.6875D, 0.75D, 0.8125D);
            }
            case SOUTH: {
                return VoxelShapes.box(0.1875D, 0.5D, 1D, 0.8125D, 0.75D, 0.6875D);
            }
            case WEST: {
                return VoxelShapes.box(0D, 0.5D, 0.1875D, 0.3125D, 0.75D, 0.8125D);
            }
            default: {
                return VoxelShapes.box(0.1875D, 0.5D, 0D, 0.8125D, 0.75D, 0.3125D);
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos.below()).isFaceSturdy(worldIn, pos.below(), Direction.UP)) {
            return true;
        } else return worldIn.getBlockState(pos.relative(state.getValue(FACING))).isFaceSturdy(worldIn, pos.relative(state.getValue(FACING)), state.getValue(FACING).getOpposite());
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = this.defaultBlockState().setValue(FACING, context.getClickedFace().getAxis().getPlane() == Direction.Plane.HORIZONTAL ? context.getClickedFace().getOpposite() : context.getHorizontalDirection());
        if (context.getLevel().getBlockState(context.getClickedPos().below()).isFaceSturdy(context.getLevel(), context.getClickedPos().below(), Direction.UP) && context.getClickedFace() == Direction.UP) {
            return state.setValue(BlockStateProperties.HANGING, false);
        } else {
            return state.setValue(BlockStateProperties.HANGING, true);
        }
    }


    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, BlockStateProperties.HANGING);
    }
}
