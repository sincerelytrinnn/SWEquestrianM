package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.network.HorseStateChange;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

public class DesensitizingItem extends ItemBase {

	private int id;

	public DesensitizingItem(int id) {
		this.id = id;
	}

	@Override
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof SWEMHorseEntityBase) {
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) target;
			switch (this.id) {
				case 0: {
					SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(1, horse.getId()));
					return ActionResultType.sidedSuccess(playerIn.level.isClientSide);
				}
				case 1: {
					SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(2, horse.getId()));
					return ActionResultType.sidedSuccess(playerIn.level.isClientSide);
				}
				case 2: {
					SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(3, horse.getId()));
					return ActionResultType.sidedSuccess(playerIn.level.isClientSide);
				}
				case 3: {
					SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(4, horse.getId()));
					return ActionResultType.sidedSuccess(playerIn.level.isClientSide);
				}
				case 4: {
					SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(5, horse.getId()));
					return ActionResultType.sidedSuccess(playerIn.level.isClientSide);
				}
			}
		}
		return super.interactLivingEntity(stack, playerIn, target, hand);
	}
}
