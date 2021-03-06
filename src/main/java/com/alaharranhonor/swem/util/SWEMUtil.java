package com.alaharranhonor.swem.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class SWEMUtil {

	public static final Map<Item, Block> mappings = new HashMap<Item, Block>() {{
		put(SWLRegistryHandler.STAR_WORM_BLOCK_ACACIA_LOG_ITEM.get(), Blocks.ACACIA_LOG);
	}};

	public static String checkTextOverflow(String text, int maxLimit) {
		if (text.length() > maxLimit - 2) {
			return text.substring(0, maxLimit - 2) + "...";
		} else {
			return text;
		}
	}
}
