package com.alaharranhonor.swem.armor;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


public class DiamondRidingBoots extends GoldRidingBoots {

	private int tickDurability = 0;
	public DiamondRidingBoots(String path, IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(path, materialIn, slot, builderIn);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		super.onCreated(stack, worldIn, playerIn);
	}



	/**
	 * Called to tick armor in the armor slot. Override to do something
	 *
	 * @param stack
	 * @param world
	 * @param player
	 */
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 1, 2));
		if (player.isBurning()) {
			if (this.tickDurability == 20) {
				stack.damageItem(1, player, (broken) -> {
					broken.sendBreakAnimation(EquipmentSlotType.FEET);
				});
				this.tickDurability = 0;
			} else {
				this.tickDurability++;
			}
		}
		super.onArmorTick(stack, world, player);
	}

	@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class AmethystRidingBootsEquipped {

		@SubscribeEvent
		public static void onFall(LivingFallEvent event) {
			event.setCanceled(true);
		}
	}
}
