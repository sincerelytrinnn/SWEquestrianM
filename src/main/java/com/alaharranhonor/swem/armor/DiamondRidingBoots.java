package com.alaharranhonor.swem.armor;

import com.alaharranhonor.swem.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class DiamondRidingBoots extends ArmorItem {

	private int tickDurability = 0;
	public DiamondRidingBoots(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		stack.addEnchantment(RegistryHandler.UPSTEP.get(), 1);
		stack.addEnchantment(RegistryHandler.DESTRIER.get(), 1);
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
	}


}
