package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ShavingsItem extends BlockItem {
    public ShavingsItem(Block block) {
        super(block, new Item.Properties().tab(SWEM.TAB).stacksTo(16).defaultDurability(8));
    }

    @Override
    public boolean updateCustomBlockEntityTag(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        stack.hurtAndBreak(1, player, playerEntity -> {});
        ItemStack newItem = stack;
        player.inventory.add(player.inventory.selected, newItem);
        return false;
    }

    public static class SoiledShavingsItem extends Item {

        public SoiledShavingsItem() {
            super(new Item.Properties().tab(SWEM.TAB).stacksTo(16));
        }


    }
}
