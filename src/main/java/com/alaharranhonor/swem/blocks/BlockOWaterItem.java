package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockOWaterItem extends BlockItem {

    public BlockOWaterItem(Block block) {
        super(block, new Item.Properties().tab(SWEM.TAB));
    }
}
