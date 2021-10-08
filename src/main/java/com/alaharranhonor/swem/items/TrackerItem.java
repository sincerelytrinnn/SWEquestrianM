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

			if (!horse.getOwnerUUID().equals(playerIn.getUUID())) {
				playerIn.displayClientMessage(new StringTextComponent("You can't track horses, that aren't yours."), true);
				return ActionResultType.FAIL;
			}

			if (playerIn.isShiftKeyDown()) {
				// Remove tracking status
				CompoundNBT tracked = stack.getOrCreateTagElement("tracked");
				CompoundNBT trackedNew = new CompoundNBT();
				boolean removed = false;
				for (int i = 0; i < tracked.size(); i++) {

					if (tracked.getUUID(String.valueOf(i)).equals(horse.getUUID())) {
						playerIn.displayClientMessage(new StringTextComponent("Horse is no longer being tracked"), true);
						removed = true;
						horse.setTracked(false);
						continue;
					}
					trackedNew.putUUID(String.valueOf(removed ? i - 1 : i), tracked.getUUID(String.valueOf(i)));

				}


				stack.getOrCreateTag().put("tracked", trackedNew);
			} else {
				//Add tracking status
				CompoundNBT nbt = stack.getOrCreateTag();
				CompoundNBT tracked = stack.getOrCreateTagElement("tracked");

				for (int i = 0; i < tracked.size(); i++) {
					if (tracked.getUUID(String.valueOf(i)).equals(horse.getUUID())) {
						playerIn.displayClientMessage(new StringTextComponent("Horse is already being tracked."), true);
						return ActionResultType.FAIL;
					}
				}
				tracked.putUUID(Integer.toString(tracked.size()), horse.getUUID());

				horse.setTracked(true);

				playerIn.displayClientMessage(new StringTextComponent("Horse is now being tracked"), true);

				nbt.put("tracked", tracked);
			}

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
