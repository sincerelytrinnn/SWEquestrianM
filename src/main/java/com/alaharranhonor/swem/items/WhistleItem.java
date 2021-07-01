package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.initialization.SWEMParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.UUID;


import net.minecraft.item.Item.Properties;

public class WhistleItem extends Item {

	public WhistleItem() {
		super(new Properties().tab(SWEM.TAB).stacksTo(1));
	}


	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (worldIn.isClientSide) return ActionResult.pass(playerIn.getItemInHand(handIn));

		ItemStack stack = playerIn.getItemInHand(handIn);
		CompoundNBT tag = stack.getOrCreateTag();

		if (tag.contains("boundHorse")) {
			UUID horseUUID = tag.getUUID("boundHorse");
			SWEMHorseEntityBase horse = ((SWEMHorseEntityBase) ((ServerWorld) worldIn).getEntity(horseUUID));
			if (horse != null) {
				if (horse.blockPosition().closerThan(playerIn.blockPosition(), 100.0f)) {
					horse.getNavigation().stop();
					horse.getNavigation().moveTo(playerIn, 1.0f);
					((ServerWorld) worldIn).addParticle(SWEMParticles.YAY.get(), horse.getX(), horse.getY(), horse.getZ(), 20.0, 0.0, 0.0);
					return ActionResult.consume(stack);
				}
			}
		}

		return ActionResult.fail(stack);
	}

	/**
	 * Returns true if the item can be used on the given entity, e.g. shears on sheep.
	 *
	 * @param stack
	 * @param playerIn
	 * @param target
	 * @param hand
	 */
	@Override
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (playerIn.getCommandSenderWorld().isClientSide) return ActionResultType.PASS;

		CompoundNBT tag = stack.getOrCreateTag();

		if (target instanceof SWEMHorseEntityBase) {
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) target;
			System.out.println(horse.getOwnerUUID());
			System.out.println(playerIn.getUUID());
			if (horse.getOwnerUUID().equals(playerIn.getUUID())) {
				tag.putUUID("boundHorse", horse.getUUID());
				return ActionResultType.CONSUME;
			}
		}

		return ActionResultType.FAIL;
	}

}
