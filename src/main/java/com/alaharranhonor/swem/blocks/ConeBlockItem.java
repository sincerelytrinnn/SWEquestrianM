package com.alaharranhonor.swem.blocks;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ConeBlockItem extends BlockItem {

	public ConeBlockItem(Block blockIn) {
		super(blockIn, new Item.Properties().group(SWEM.TAB));
	}

	@Nullable
	@Override
	public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
		return EquipmentSlotType.HEAD;
	}
}
