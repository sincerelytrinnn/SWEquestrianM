package com.alaharranhonor.swem.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
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

    //re-doing it because previous implementation was overkill
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        return true;
    }
}