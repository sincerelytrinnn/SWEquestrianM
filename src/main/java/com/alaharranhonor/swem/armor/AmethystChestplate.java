package com.alaharranhonor.swem.armor;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.items.SWEMArmorItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.item.Item.Properties;

public class AmethystChestplate extends SWEMArmorItem {
	public AmethystChestplate(String path, IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(path, materialIn, slot, builder);
	}

	@Override
	public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
		return true;
	}

	@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class KnockbackNegation {

		@SubscribeEvent
		public static void onKnockback(LivingKnockBackEvent event) {
			if (!(event.getEntity() instanceof PlayerEntity)) return;

			PlayerEntity player = (PlayerEntity) event.getEntity();
			if (player.getItemBySlot(EquipmentSlotType.CHEST).getItem() instanceof AmethystChestplate) {
				event.setStrength(0);
				event.setRatioX(0);
				event.setRatioZ(0);
				event.setCanceled(true);
			}
		}
	}
}
