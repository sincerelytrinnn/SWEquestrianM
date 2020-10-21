package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class SWLMBlockItemBase extends BlockItem {
	public SWLMBlockItemBase(Block block) {
		super(block, new Item.Properties().group(SWEM.SWLMTAB));
	}
}
