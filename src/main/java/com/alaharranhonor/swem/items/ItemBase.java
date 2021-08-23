package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.item.Item;

public class ItemBase extends Item {
    public ItemBase() {
        super(new Item.Properties().tab(SWEM.TAB));
    }

    public ItemBase(Item.Properties props) {
        super(props);
    }
}
