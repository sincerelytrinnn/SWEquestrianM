package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FuelBlockItemBase extends BlockItem {
    private int ticks;
    public FuelBlockItemBase(Block block, int ticks) {
        super(block, new Item.Properties().tab(SWEM.TAB));
        this.ticks = ticks;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return ticks;
    }
}