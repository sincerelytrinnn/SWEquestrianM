package com.alaharranhonor.swem.armor;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


import net.minecraft.item.Item.Properties;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DiamondRidingBoots extends GoldRidingBoots {

	private int tickDurability = 0;
	public DiamondRidingBoots(String path, IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(path, materialIn, slot, builderIn);
	}

	@Override
	public void onCraftedBy(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		super.onCraftedBy(stack, worldIn, playerIn);
	}

	@Override
	public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
		p_77624_3_.add(new StringTextComponent("Fire, Fire baby.").setStyle(Style.EMPTY.withColor(Color.parseColor("#585858"))));
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
		player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 1, 2));
		if (player.isOnFire()) {
			if (this.tickDurability == 20) {
				stack.hurtAndBreak(1, player, (broken) -> {
					broken.broadcastBreakEvent(EquipmentSlotType.FEET);
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
