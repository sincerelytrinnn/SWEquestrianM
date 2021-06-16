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


public class WhistleItem extends Item {

	public WhistleItem() {
		super(new Properties().group(SWEM.TAB).maxStackSize(1));
	}

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
	 * {@link #onItemUse}.
	 *
	 * @param worldIn
	 * @param playerIn
	 * @param handIn
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (worldIn.isRemote) return ActionResult.resultPass(playerIn.getHeldItem(handIn));

		ItemStack stack = playerIn.getHeldItem(handIn);
		CompoundNBT tag = stack.getOrCreateTag();

		if (tag.contains("boundHorse")) {
			UUID horseUUID = tag.getUniqueId("boundHorse");
			SWEMHorseEntityBase horse = ((SWEMHorseEntityBase) ((ServerWorld) worldIn).getEntityByUuid(horseUUID));
			if (horse != null) {
				if (horse.getPosition().withinDistance(playerIn.getPosition(), 100.0f)) {
					horse.getNavigator().clearPath();
					horse.getNavigator().tryMoveToEntityLiving(playerIn, 1.0f);
					((ServerWorld) worldIn).spawnParticle(SWEMParticles.YAY.get(), horse.getPosX(), horse.getPosY(), horse.getPosZ(), 20, 0, 0, 0, 1.0d);
					return ActionResult.resultConsume(stack);
				}
			}
		}

		return ActionResult.resultFail(stack);
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
	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (playerIn.getEntityWorld().isRemote) return ActionResultType.PASS;

		CompoundNBT tag = stack.getOrCreateTag();

		if (target instanceof SWEMHorseEntityBase) {
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) target;
			System.out.println(horse.getOwnerUniqueId());
			System.out.println(playerIn.getUniqueID());
			if (horse.getOwnerUniqueId().equals(playerIn.getUniqueID())) {
				tag.putUniqueId("boundHorse", horse.getUniqueID());
				return ActionResultType.CONSUME;
			}
		}

		return ActionResultType.FAIL;
	}

}
