package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.UUID;

public class TrackerItem extends ItemBase {


	@Override
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof SWEMHorseEntityBase) {
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) target;

			if (horse.isBaby() || !horse.isTamed()) return ActionResultType.FAIL;

			CompoundNBT nbt = stack.getOrCreateTag();
			CompoundNBT tracked = stack.getOrCreateTagElement("tracked");

			tracked.putUUID(Integer.toString(tracked.size()), horse.getUUID());

			playerIn.displayClientMessage(new StringTextComponent("Horse is now being tracked"), true);

			nbt.put("tracked", tracked);
			return ActionResultType.CONSUME;
		}
		return ActionResultType.PASS;
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (!worldIn.isClientSide) {
			ItemStack stack = playerIn.getItemInHand(handIn);

			CompoundNBT tracked = stack.getOrCreateTagElement("tracked");

			StringBuilder builder = new StringBuilder();

			ServerWorld world = (ServerWorld) worldIn;

			for (int i = 0; i < tracked.size(); i++) {
				UUID uuid = tracked.getUUID(Integer.toString(i));
				Entity entity = world.getEntity(uuid);
				if (entity instanceof SWEMHorseEntityBase) {
					builder.append(entity.getName().getString()).append(" x: ").append(entity.blockPosition().getX()).append(" - y: ").append(entity.blockPosition().getY()).append(" - z: ").append(entity.blockPosition().getZ()).append("\n");
				}
			}

			playerIn.sendMessage(new StringTextComponent(builder.toString()), Util.NIL_UUID);
			return ActionResult.consume(stack);
		}

		return super.use(worldIn, playerIn, handIn);

	}
}
