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
        super(block, new Item.Properties().group(SWEM.TAB).maxStackSize(16).defaultMaxDamage(8));
    }

    @Override
    public boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        stack.damageItem(1, player, playerEntity -> {});
        ItemStack newItem = stack;
        player.inventory.add(player.inventory.currentItem, newItem);
        return false;
    }

    public static class SoiledShavingsItem extends Item {

        public SoiledShavingsItem() {
            super(new Item.Properties().group(SWEM.TAB).maxStackSize(16));
        }


    }
}
