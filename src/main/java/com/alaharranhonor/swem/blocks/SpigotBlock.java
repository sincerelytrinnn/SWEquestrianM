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
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class SpigotBlock extends HorizontalBlock {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;

    /**
     * Instantiates a new Spigot.
     *
     * @param properties the properties
     */
    public SpigotBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.HANGING, true));
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
                pPlayer.displayClientMessage(new TranslationTextComponent("text.swem.no_usable_water_nearby"), true);
                return ActionResultType.FAIL;
            }
        }

        BlockPos checkPos = pState.getValue(BlockStateProperties.HANGING) ? pPos.below() : pPos.below().relative(pState.getValue(FACING).getOpposite());
        BlockState checkState = pLevel.getBlockState(checkPos);
        if (checkState.getBlock() instanceof CauldronBlock || checkState.getBlock() instanceof HalfBarrelBlock) {
            if (hasWater) {
                int i = checkState.getValue(CauldronBlock.LEVEL);
                if (i == 3) return ActionResultType.PASS;
                if (i < 3 && !pLevel.isClientSide) {

                    for (int j = 0; j < 6; j++) {
                        ((ServerWorld) pLevel).sendParticles(ParticleTypes.SPLASH, checkPos.getX() + Math.random(), checkPos.getY() + 1, checkPos.getZ() + Math.random(), 1, 0, 0, 0, 1);
                    }
                    pPlayer.awardStat(Stats.FILL_CAULDRON);
                    pLevel.setBlock(checkPos, checkState.setValue(CauldronBlock.LEVEL, MathHelper.clamp(3, 0, 3)), 3);
                    pLevel.updateNeighbourForOutputSignal(pPos, this);

                    pLevel.playSound(null, pPos, SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return ActionResultType.sidedSuccess(pLevel.isClientSide());
            } else {
                pPlayer.displayClientMessage(new StringTextComponent("text.swem.no_usable_water_nearby"), true);
                return ActionResultType.FAIL;
            }
        } else if (checkState.getBlock() instanceof WaterTroughBlock) {
            if (hasWater) {
                if (checkState.getValue(WaterTroughBlock.LEVEL) == 16) return ActionResultType.PASS;

                WaterTroughBlock wtBlock = (WaterTroughBlock) checkState.getBlock();
                if (!pLevel.isClientSide()) {
                    wtBlock.setWaterLevel(pLevel, checkPos, checkState, false);
                    for (int i = 0; i < 6; i++) {
                        ((ServerWorld) pLevel).sendParticles(ParticleTypes.SPLASH, checkPos.getX() + Math.random(), checkPos.getY() + 1, checkPos.getZ() + Math.random(), 1, 0, 0, 0, 1);
                    }
                }
                pLevel.playSound(null, pPos, SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return ActionResultType.sidedSuccess(pLevel.isClientSide());
            } else {
                pPlayer.displayClientMessage(new StringTextComponent("text.swem.no_usable_water_nearby"), true);
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
                return VoxelShapes.box(1D, 0.1875D, 0.1875D, 0.6875D, 0.4375D, 0.8125D);
            }
            case SOUTH: {
                return VoxelShapes.box(0.1875D, 0.1875D, 1D, 0.8125D, 0.4375D, 0.6875D);
            }
            case WEST: {
                return VoxelShapes.box(0D, 0.1875D, 0.1875D, 0.3125D, 0.4375D, 0.8125D);
            }
            default: {
                return VoxelShapes.box(0.1875D, 0.1875D, 0D, 0.8125D, 0.4375D, 0.3125D);
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos.below()).isFaceSturdy(worldIn, pos.below(), Direction.UP, BlockVoxelShape.CENTER)) {
            return true;
        } else
            return worldIn.getBlockState(pos.relative(state.getValue(FACING))).isFaceSturdy(worldIn, pos.relative(state.getValue(FACING)), state.getValue(FACING).getOpposite(), BlockVoxelShape.CENTER);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = this.defaultBlockState().setValue(FACING, context.getClickedFace().getAxis().getPlane() == Direction.Plane.HORIZONTAL ? context.getClickedFace().getOpposite() : context.getHorizontalDirection());
        if (context.getLevel().getBlockState(context.getClickedPos().below()).isFaceSturdy(context.getLevel(), context.getClickedPos().below(), Direction.UP, BlockVoxelShape.CENTER) && context.getClickedFace() == Direction.UP) {
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
