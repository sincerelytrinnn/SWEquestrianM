package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Gallaxium extends ItemBase {

	@Override
	public void inventoryTick(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
		if (!(p_77663_3_ instanceof PlayerEntity)) return;

		if (p_77663_3_.blockPosition().getY() < 250) return;

		PlayerEntity player = (PlayerEntity) p_77663_3_;
		player.inventory.removeItem(p_77663_1_);
		player.inventory.add(new ItemStack(SWEMItems.GALLAXIA.get()));
	}
}
