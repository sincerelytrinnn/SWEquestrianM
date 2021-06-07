package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.entity.coats.SWEMCoatColors;
import com.alaharranhonor.swem.util.initialization.SWEMEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class HorseTransformItem extends Item {

	private SWEMCoatColors coat;

	public HorseTransformItem(SWEMCoatColors coat) {
		super(new Item.Properties());
		this.coat = coat;
	}

	@Override
	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof SWEMHorseEntityBase) {
			SWEMHorseEntityBase horseEntity = (SWEMHorseEntityBase) target;

			if (!playerIn.world.isRemote) {
				BlockPos targetPos = target.getPosition();
				horseEntity.setCoatColour(this.coat);
			}
			stack.shrink(1);
			return ActionResultType.func_233537_a_(playerIn.world.isRemote);
		}
		return ActionResultType.PASS;
	}
}
