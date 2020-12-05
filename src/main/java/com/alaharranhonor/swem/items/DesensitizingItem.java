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
	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof SWEMHorseEntityBase) {
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) target;
			switch (this.id) {
				case 0: {
					SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(1, horse.getEntityId()));
					return ActionResultType.func_233537_a_(playerIn.world.isRemote);
				}
				case 1: {
					SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(2, horse.getEntityId()));
					return ActionResultType.func_233537_a_(playerIn.world.isRemote);
				}
				case 2: {
					SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(3, horse.getEntityId()));
					return ActionResultType.func_233537_a_(playerIn.world.isRemote);
				}
				case 3: {
					SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(4, horse.getEntityId()));
					return ActionResultType.func_233537_a_(playerIn.world.isRemote);
				}
				case 4: {
					SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(5, horse.getEntityId()));
					return ActionResultType.func_233537_a_(playerIn.world.isRemote);
				}
			}
		}
		return super.itemInteractionForEntity(stack, playerIn, target, hand);
	}
}
