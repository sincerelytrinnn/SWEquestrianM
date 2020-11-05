package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		if (!playerIn.getActiveItemStack().hasTag()) {
			playerIn.getActiveItemStack().getOrCreateTag();
		}
		if (!playerIn.getActiveItemStack().getTag().contains("horseBound")) return ActionResult.resultPass(playerIn.getActiveItemStack());
		UUID horseUUID = playerIn.getActiveItemStack().getTag().getUniqueId("horseBound");
		SWEMHorseEntityBase horse = ((SWEMHorseEntityBase)((ServerWorld) playerIn.getEntityWorld()).getEntityByUuid(horseUUID));
		if (horse != null) {
			if (horse.getPosition().withinDistance(playerIn.getPosition(), 100.0f)) {
				// Try and whistle the horse.
				horse.playSound(SoundEvents.ENTITY_HORSE_ANGRY, 0.15f, 1.0f);
				float disobeyChance = horse.progressionManager.getAffinityLeveling().getDebuff();
				float roll = horse.getRNG().nextFloat();
				if (roll > disobeyChance) {
					horse.getNavigator().clearPath();
					horse.getNavigator().setPath(horse.getNavigator().getPathToEntity(playerIn, 0), 1.0D);

					return ActionResult.resultPass(playerIn.getActiveItemStack());
				}
			}

		}
		return ActionResult.resultPass(playerIn.getActiveItemStack());

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
		if (!stack.hasTag()) {
			stack.getOrCreateTag();
		}
		if (target instanceof SWEMHorseEntityBase) {
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) target;
			if (!horse.getWhistleBound()) {
				if (stack.getTag().contains("boundHorse")) {
					((SWEMHorseEntityBase)((ServerWorld) playerIn.getEntityWorld()).getEntityByUuid(stack.getTag().getUniqueId("horseBound"))).setWhistleBound(false);
				}
				horse.setWhistleBound(true);
				stack.getTag().putUniqueId("horseBound", horse.getUniqueID());
				return ActionResultType.SUCCESS;
			} else {
				return ActionResultType.FAIL;
			}
		} else {
			return ActionResultType.FAIL;
		}
	}

}
