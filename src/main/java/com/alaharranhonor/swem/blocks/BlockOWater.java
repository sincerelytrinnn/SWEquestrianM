package com.alaharranhonor.swem.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class BlockOWater extends Block {
    /**
     * Instantiates a Block O' Water.
     */
    public BlockOWater() {
        super(
            AbstractBlock.Properties.copy(Blocks.STONE));
    }
}
