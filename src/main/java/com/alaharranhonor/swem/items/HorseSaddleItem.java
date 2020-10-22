package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.entities.ISWEMEquipable;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;


public class HorseSaddleItem extends Item {


	public HorseSaddleItem(Properties properties) {
		super(properties);
	}

	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof ISWEMEquipable && target.isAlive()) {
			ISWEMEquipable iequipable = (ISWEMEquipable)target;
			if (!iequipable.isHorseSaddled() && iequipable.func_230264_L__()) {
				if (!playerIn.world.isRemote) {
					iequipable.func_230266_a_(SoundCategory.NEUTRAL, stack);
					stack.shrink(1);
				}

				return ActionResultType.func_233537_a_(playerIn.world.isRemote);
			}
		}

		return ActionResultType.PASS;
	}
}
