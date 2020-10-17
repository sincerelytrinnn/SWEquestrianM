package com.alaharranhonor.swem.enchantments;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.util.RegistryHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class UpstepEnchantment extends Enchantment {
	public UpstepEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
		super(rarityIn, typeIn, slots);
	}

	@Override
	public int getMaxLevel()
	{
		return 1;
	}

	@Override
	public int getMinLevel()
	{
		return 1;
	}

	@Override
	public boolean isAllowedOnBooks()
	{
		return false;
	}

	/**
	 * Determines if this enchantment can be applied to a specific ItemStack.
	 *
	 * @param stack
	 */
	@Override
	public boolean canApply(ItemStack stack) {
		return false;
	}



	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return false;
	}

	@Override
	protected boolean canApplyTogether(Enchantment ench)
	{
		return !ench.equals(Enchantments.DEPTH_STRIDER);
	}

	@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class UpstepEquipped
	{
		@SubscribeEvent
		public static void CheckForPlayersWearingLRB(TickEvent.PlayerTickEvent event)
		{
			if (event.phase.equals(TickEvent.Phase.END)) {
				if (event.player.inventory.armorInventory.get(0).getItem() == RegistryHandler.LEATHER_RIDING_BOOTS.get())
				{
					event.player.stepHeight = 1.0f;
				} else {
					event.player.stepHeight = 0.6f;
				}
			}

		}

	}
}
