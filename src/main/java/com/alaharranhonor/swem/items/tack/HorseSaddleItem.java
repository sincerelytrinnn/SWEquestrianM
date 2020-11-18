package com.alaharranhonor.swem.items.tack;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.ISWEMEquipable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;


public class HorseSaddleItem extends Item {

	private ResourceLocation texture;


	public HorseSaddleItem(String textureName, Properties properties) {
		super(properties);
		this.texture = new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/saddle/" + textureName + ".png");
	}

	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof ISWEMEquipable && target.isAlive()) {
			ISWEMEquipable iequipable = (ISWEMEquipable)target;
			if (playerIn.world.isRemote && !iequipable.canEquipSaddle()) {
				playerIn.sendStatusMessage(new StringTextComponent("You need to equip a Blanket first!"), true);
				return ActionResultType.FAIL;
			}
			if (!iequipable.isHorseSaddled() && iequipable.func_230264_L__() && iequipable.canEquipSaddle()) {
				if (!playerIn.world.isRemote) {
					iequipable.func_230266_a_(SoundCategory.NEUTRAL, stack);
					stack.shrink(1);
				}

				return ActionResultType.func_233537_a_(playerIn.world.isRemote);
			}
		}

		return ActionResultType.PASS;
	}

	public ResourceLocation getTexture() {
		return this.texture;
	}
}
