package com.alaharranhonor.swem.items.tack;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.ISWEMEquipable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlanketItem extends Item {

	private final ResourceLocation texture;
	public BlanketItem(String textureName, Properties properties) {
		this(new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/blanket/" + textureName + ".png"), properties);

	}
	public BlanketItem(ResourceLocation texture, Properties properties) {
		super(properties);
		this.texture = texture;
	}

	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof ISWEMEquipable && target.isAlive()) {
			ISWEMEquipable iequipable = (ISWEMEquipable)target;
			if (playerIn.world.isRemote && !iequipable.hasHalter()) {
				playerIn.sendStatusMessage(new StringTextComponent("You need to equip a Halter/Bridle first."), true);
				return ActionResultType.FAIL;
			}
			if (!iequipable.hasBlanket() && iequipable.func_230264_L__() && iequipable.hasHalter()) {
				if (!playerIn.world.isRemote) {
					iequipable.func_230266_a_(SoundCategory.NEUTRAL, stack);
					stack.shrink(1);
				}

				return ActionResultType.func_233537_a_(playerIn.world.isRemote);
			}
		}
		return ActionResultType.PASS;
	}

	@OnlyIn(Dist.CLIENT)
	public ResourceLocation getArmorTexture() {
		return texture;
	}
}
