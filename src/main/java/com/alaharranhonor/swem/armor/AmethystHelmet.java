package com.alaharranhonor.swem.armor;

import com.alaharranhonor.swem.items.SWEMArmorItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class AmethystHelmet extends SWEMArmorItem {
	public AmethystHelmet(String path, IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(path, materialIn, slot, builder);
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		player.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 1, 2));
		super.onArmorTick(stack, world, player);
	}


}
