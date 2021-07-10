package com.alaharranhonor.swem.armor;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.items.SWEMArmorItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.item.Item.Properties;

public class AmethystLeggings extends SWEMArmorItem {
	public AmethystLeggings(String path, IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(path, materialIn, slot, builder);
	}

	@Override
	public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
		return true;
	}


	@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class LastStand {

		@SubscribeEvent
		public static void onHurtEvent(LivingHurtEvent event) {
			if (!(event.getEntity() instanceof PlayerEntity)) return;

			PlayerEntity player = (PlayerEntity) event.getEntity();

			if (!(player.getItemBySlot(EquipmentSlotType.LEGS).getItem() instanceof AmethystLeggings)) return;

			float playerHealth = player.getHealth();
			float healthAfterHit = playerHealth - event.getAmount();
			if (healthAfterHit < 0.5F) {
				float playerExp = player.totalExperience;
				float requiredXp = (healthAfterHit * -1) * 40.0F;

				if (playerExp >= requiredXp) {
					player.giveExperiencePoints((int)-requiredXp);
					event.setAmount(-1);
					event.setCanceled(true);
					player.setHealth(6);
				}

			}

		}

	}
}
