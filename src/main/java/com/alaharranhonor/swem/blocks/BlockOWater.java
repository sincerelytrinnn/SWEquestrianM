package com.alaharranhonor.swem.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class BlockOWater extends Block {
    /**
     * Instantiates a Block O' Water.
     */
    public BlockOWater() {
        super(
                AbstractBlock.Properties.of(Material.WATER)
                        .strength(2.0f, 6.0f)
                        .sound(SoundType.WET_GRASS)
                        .harvestLevel(1)
                        .harvestTool(ToolType.PICKAXE));
    }

    /**
     * Fills buckets with water.
     */
    @Override
    public ActionResultType use(
            BlockState state,
            World worldIn,
            BlockPos pos,
            PlayerEntity player,
            Hand handIn,
            BlockRayTraceResult p_225533_6_) {
        ItemStack itemstack = player.getItemInHand(handIn);
        Item item = itemstack.getItem();
        if (itemstack.isEmpty()) {
            return ActionResultType.PASS;
        } else if (item == Items.BUCKET) {
            itemstack.shrink(1);
            if (itemstack.isEmpty()) {
                player.setItemInHand(handIn, new ItemStack(Items.WATER_BUCKET));
            } else if (!player.inventory.add(new ItemStack(Items.WATER_BUCKET))) {
                player.drop(new ItemStack(Items.WATER_BUCKET), false);
            }
            worldIn.playSound((PlayerEntity) null, pos, SoundEvents.BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResultType.sidedSuccess(worldIn.isClientSide);
        } else {
            return ActionResultType.PASS;
        }

    }
}
