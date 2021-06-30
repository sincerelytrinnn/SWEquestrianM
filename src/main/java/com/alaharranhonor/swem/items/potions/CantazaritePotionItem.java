package com.alaharranhonor.swem.items.potions;

import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.util.initialization.SWEMEntities;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.horse.CoatColors;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.Item.Properties;

public class CantazaritePotionItem extends PotionItem {


	public CantazaritePotionItem(Properties builder) {
		super(builder);
	}

	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof HorseEntity) {
			HorseEntity horseEntity = (HorseEntity) target;
			CoatColors vanillaCoat = horseEntity.getVariant();

			if (!playerIn.world.isRemote) {
				BlockPos targetPos = target.getPosition();
				target.remove();
				SWEMHorseEntity horse1 = (SWEMHorseEntity) SWEMEntities.SWEM_HORSE_ENTITY.get().spawn((ServerWorld) playerIn.world, null, playerIn, targetPos, SpawnReason.MOB_SUMMONED, true, false);
				horse1.calculatePotionCoat(vanillaCoat);
			}
			stack.shrink(1);
			return ActionResultType.sidedSuccess(playerIn.world.isRemote);
		}
		return ActionResultType.PASS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		return ActionResult.resultPass(playerIn.getHeldItem(handIn));
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("swem.potion.cantazarite_potion.effect"));
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.isInGroup(group)) {
			items.add(new ItemStack(this));
		}
	}
}
