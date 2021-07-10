package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.entity.coats.SWEMCoatColors;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public class HorseTransformItem extends Item {

	private SWEMCoatColors coat;

	public HorseTransformItem(SWEMCoatColors coat) {
		super(new Item.Properties());
		this.coat = coat;
	}

	@Override
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof SWEMHorseEntityBase) {
			SWEMHorseEntityBase horseEntity = (SWEMHorseEntityBase) target;

			if (!playerIn.level.isClientSide) {
				BlockPos targetPos = target.blockPosition();
				horseEntity.setCoatColour(this.coat);
			}
			stack.shrink(1);
			return ActionResultType.sidedSuccess(playerIn.level.isClientSide);
		}
		return ActionResultType.PASS;
	}
}
