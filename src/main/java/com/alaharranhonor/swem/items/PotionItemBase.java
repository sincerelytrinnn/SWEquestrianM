package com.alaharranhonor.swem.items.potions;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.item.Item;
import net.minecraft.item.PotionItem;

public class PotionItemBase extends PotionItem {

    public PotionItemBase() {
        super(new Item.Properties().tab(SWEM.TAB));
    }

    public PotionItemBase(Item.Properties props) {
        super(props);
    }
}