package com.alaharranhonor.swem.items.potions;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.util.initialization.SWEMEntities;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.horse.CoatColors;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
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

public class CantazaritePotionItem extends PotionItem {


	public CantazaritePotionItem(Properties builder) {
		super(builder);
	}

	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof HorseEntity) {
			HorseEntity horseEntity = (HorseEntity) target;
			CoatColors vanillaCoat = horseEntity.func_234239_eK_();

			if (!playerIn.world.isRemote) {
				BlockPos targetPos = target.getPosition();
				target.remove();
				SWEMHorseEntity horse1 = (SWEMHorseEntity) SWEMEntities.SWEM_HORSE_ENTITY.get().spawn((ServerWorld) playerIn.world, null, playerIn, targetPos, SpawnReason.MOB_SUMMONED, true, false);
				//horse1.calculatePotionCoat(vanillaCoat);
			}
			stack.shrink(1);
			return ActionResultType.func_233537_a_(playerIn.world.isRemote);
		}
		return ActionResultType.PASS;
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
