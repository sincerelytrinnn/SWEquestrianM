package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.blocks.PeeBlock;
import com.alaharranhonor.swem.util.SWEMUtil;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class HoseItem extends Item {

    /**
     * Instantiates a new Hose item.
     *
     * @param pProperties the p properties
     */
    public HoseItem(Properties pProperties) {
        super(pProperties);
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     *
     * @param pLevel
     * @param pPlayer
     * @param pHand
     */
    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        RayTraceResult raytraceresult = getPlayerPOVHitResult(pLevel, pPlayer, RayTraceContext.FluidMode.NONE);
        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(pPlayer, pLevel, itemstack, raytraceresult);
        if (ret != null) return ret;
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return ActionResult.pass(itemstack);
        } else if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.pass(itemstack);
        } else {

            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceresult;
            BlockPos blockpos = blockraytraceresult.getBlockPos();
            Direction direction = blockraytraceresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);

            if (pLevel.getBlockState(blockpos1).getBlock() instanceof PeeBlock) {
                if (!pLevel.isClientSide()) {
                    for (int i = 0; i < 5; i++) {
                        ((ServerWorld) pLevel).sendParticles(ParticleTypes.SPLASH, blockpos1.getX() + Math.random(), blockpos1.getY() + 1, blockpos1.getZ() + Math.random(), 1, 0, 0, 0, 1);
                    }
                }
                pLevel.setBlock(blockpos1, Blocks.AIR.defaultBlockState(), 3);
                return ActionResult.sidedSuccess(itemstack, pLevel.isClientSide());
            }

            if (pLevel.mayInteract(pPlayer, blockpos) && pPlayer.mayUseItemAt(blockpos1, direction, itemstack)) {

                boolean isWaterNearby = SWEMUtil.isInDistanceOfBlock(pLevel, blockpos, 15, SWEMBlocks.BLOCK_O_WATER.get());
                if (!isWaterNearby) {
                    pPlayer.displayClientMessage(new StringTextComponent("text.swem.no_usable_water_nearby"), true);
                    return ActionResult.fail(itemstack);
                }

                BlockState blockstate = pLevel.getBlockState(blockpos);
                BlockPos blockpos2 = canBlockContainFluid(pLevel, blockpos, blockstate) ? blockpos : blockpos1;
                if (this.emptyBucket(pPlayer, pLevel, blockpos2, blockraytraceresult)) {
                    this.checkExtraContent(pLevel, itemstack, blockpos2);
                    if (pPlayer instanceof ServerPlayerEntity) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) pPlayer, blockpos2, itemstack);
                    }

                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                    return ActionResult.sidedSuccess(itemstack, pLevel.isClientSide());
                } else {
                    return ActionResult.fail(itemstack);
                }
            } else {
                return ActionResult.fail(itemstack);
            }
        }
    }

    public void checkExtraContent(World p_203792_1_, ItemStack p_203792_2_, BlockPos p_203792_3_) {
    }

    public boolean emptyBucket(@Nullable PlayerEntity p_180616_1_, World p_180616_2_, BlockPos p_180616_3_, @Nullable BlockRayTraceResult p_180616_4_) {
        BlockState blockstate = p_180616_2_.getBlockState(p_180616_3_);
        Block block = blockstate.getBlock();
        Material material = blockstate.getMaterial();
        boolean flag = blockstate.canBeReplaced(Fluids.WATER);
        boolean flag1 = blockstate.isAir() || flag || block instanceof ILiquidContainer && ((ILiquidContainer) block).canPlaceLiquid(p_180616_2_, p_180616_3_, blockstate, Fluids.WATER);
        if (!flag1) {
            return p_180616_4_ != null && this.emptyBucket(p_180616_1_, p_180616_2_, p_180616_4_.getBlockPos().relative(p_180616_4_.getDirection()), (BlockRayTraceResult) null);
        } else if (p_180616_2_.dimensionType().ultraWarm() && Fluids.WATER.is(FluidTags.WATER)) {
            int i = p_180616_3_.getX();
            int j = p_180616_3_.getY();
            int k = p_180616_3_.getZ();
            p_180616_2_.playSound(p_180616_1_, p_180616_3_, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (p_180616_2_.random.nextFloat() - p_180616_2_.random.nextFloat()) * 0.8F);

            for (int l = 0; l < 8; ++l) {
                p_180616_2_.addParticle(ParticleTypes.LARGE_SMOKE, (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0D, 0.0D, 0.0D);
            }

            return true;
        } else if (block instanceof ILiquidContainer && ((ILiquidContainer) block).canPlaceLiquid(p_180616_2_, p_180616_3_, blockstate, Fluids.WATER)) {
            ((ILiquidContainer) block).placeLiquid(p_180616_2_, p_180616_3_, blockstate, ((FlowingFluid) Fluids.WATER).getSource(false));
            this.playEmptySound(p_180616_1_, p_180616_2_, p_180616_3_);
            return true;
        } else {
            if (!p_180616_2_.isClientSide && flag && !material.isLiquid()) {
                p_180616_2_.destroyBlock(p_180616_3_, true);
            }

            if (!p_180616_2_.setBlock(p_180616_3_, Fluids.WATER.defaultFluidState().createLegacyBlock(), 11) && !blockstate.getFluidState().isSource()) {
                return false;
            } else {
                this.playEmptySound(p_180616_1_, p_180616_2_, p_180616_3_);
                return true;
            }
        }

    }

    protected void playEmptySound(@Nullable PlayerEntity pPlayer, IWorld pLevel, BlockPos pPos) {
        SoundEvent soundevent = Fluids.WATER.getAttributes().getEmptySound();
        if (soundevent == null)
            soundevent = Fluids.WATER.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
        pLevel.playSound(pPlayer, pPos, soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    private boolean canBlockContainFluid(World worldIn, BlockPos posIn, BlockState blockstate) {
        return blockstate.getBlock() instanceof ILiquidContainer && ((ILiquidContainer) blockstate.getBlock()).canPlaceLiquid(worldIn, posIn, blockstate, Fluids.WATER);
    }
}
