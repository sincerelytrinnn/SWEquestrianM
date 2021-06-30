package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

import net.minecraft.item.Item.Properties;

public class MedicalHorseItem extends Item {
	private float heal;
	private float xp;

	public MedicalHorseItem(Properties properties, float heal, float xp) {
		super(properties);
		this.heal = heal;
		this.xp = xp;
	}

	@Override
	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (!playerIn.world.isRemote) {
			if (target instanceof SWEMHorseEntityBase) {
				SWEMHorseEntityBase horse = (SWEMHorseEntityBase) target;

				if (horse.getHealth() == horse.getMaxHealth()) {
					return ActionResultType.FAIL;
				}

				horse.heal(heal, xp);
				return ActionResultType.CONSUME;
			} else {
				return ActionResultType.FAIL;
			}
		}
		return ActionResultType.PASS;
	}
}
