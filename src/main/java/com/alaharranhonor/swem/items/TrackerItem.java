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
	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof SWEMHorseEntityBase) {
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) target;

			if (horse.isChild() || !horse.isTame()) return ActionResultType.FAIL;

			CompoundNBT nbt = stack.getOrCreateTag();
			CompoundNBT tracked = stack.getOrCreateChildTag("tracked");

			tracked.putUniqueId(Integer.toString(tracked.size()), horse.getUniqueID());

			playerIn.sendStatusMessage(new StringTextComponent("Horse is now being tracked"), true);

			nbt.put("tracked", tracked);
			return ActionResultType.CONSUME;
		}
		return ActionResultType.PASS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (!worldIn.isRemote) {
			ItemStack stack = playerIn.getHeldItem(handIn);

			CompoundNBT tracked = stack.getOrCreateChildTag("tracked");

			StringBuilder builder = new StringBuilder();

			ServerWorld world = (ServerWorld) worldIn;

			for (int i = 0; i < tracked.size(); i++) {
				UUID uuid = tracked.getUniqueId(Integer.toString(i));
				Entity entity = world.getEntityByUuid(uuid);
				if (entity instanceof SWEMHorseEntityBase) {
					builder.append(entity.getName().getString()).append(" x: ").append(entity.getPosition().getX()).append(" - y: ").append(entity.getPosition().getY()).append(" - z: ").append(entity.getPosition().getZ()).append("\n");
				}
			}

			playerIn.sendMessage(new StringTextComponent(builder.toString()), Util.DUMMY_UUID);
			return ActionResult.resultConsume(stack);
		}

		return super.onItemRightClick(worldIn, playerIn, handIn);

	}
}
