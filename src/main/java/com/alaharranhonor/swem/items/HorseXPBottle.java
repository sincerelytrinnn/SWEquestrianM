package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

public class HorseXPBottle extends ItemBase {

	private final String leveler;

	public HorseXPBottle(String leveler) {
		super();
		this.leveler = leveler;
	}

	@Override
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof SWEMHorseEntityBase) {
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) target;
			if (this.leveler.equals("affinity")) {
				horse.progressionManager.getAffinityLeveling().addXP(50);
				return ActionResultType.sidedSuccess(playerIn.getCommandSenderWorld().isClientSide);
			} else if (this.leveler.equals("speed")) {
				horse.progressionManager.getSpeedLeveling().addXP(50);
				return ActionResultType.sidedSuccess(playerIn.getCommandSenderWorld().isClientSide);
			} else if (this.leveler.equals("jump")) {
				horse.progressionManager.getJumpLeveling().addXP(50);
				return ActionResultType.sidedSuccess(playerIn.getCommandSenderWorld().isClientSide);
			} else if (this.leveler.equals("health")) {
				horse.progressionManager.getHealthLeveling().addXP(50);
				return ActionResultType.sidedSuccess(playerIn.getCommandSenderWorld().isClientSide);
			} else {
				horse.progressionManager.getAffinityLeveling().addXP(50);
				horse.progressionManager.getJumpLeveling().addXP(50);
				horse.progressionManager.getSpeedLeveling().addXP(50);
				horse.progressionManager.getHealthLeveling().addXP(50);
				return ActionResultType.sidedSuccess(playerIn.getCommandSenderWorld().isClientSide);
			}
		}
		return ActionResultType.PASS;
	}
}
