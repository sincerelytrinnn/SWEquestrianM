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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;



import net.minecraft.item.Item.Properties;

public class HorseSaddleItem extends Item implements IAnimatable {

	private ResourceLocation texture;
	private ResourceLocation saddleRackTexture;
	private final AnimationFactory factory = new AnimationFactory(this);


	public HorseSaddleItem(String textureName, Properties properties) {
		super(properties);
		this.texture = new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/saddle/" + textureName + ".png");
		this.saddleRackTexture = new ResourceLocation(SWEM.MOD_ID, "textures/tile/saddle_rack/" + textureName + ".png");
	}

	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof ISWEMEquipable && target.isAlive()) {
			ISWEMEquipable iequipable = (ISWEMEquipable)target;
			if (playerIn.world.isRemote && !iequipable.canEquipSaddle()) {
				playerIn.sendStatusMessage(new StringTextComponent("You need to equip a Blanket first!"), true);
				return ActionResultType.FAIL;
			}
			if (!iequipable.isHorseSaddled() && iequipable.isSaddleable() && iequipable.canEquipSaddle()) {
				if (!playerIn.world.isRemote) {
					iequipable.equipSaddle(SoundCategory.NEUTRAL, stack);
					if (!playerIn.abilities.isCreativeMode)
						stack.shrink(1);
				}

				return ActionResultType.sidedSuccess(playerIn.world.isRemote);
			}
		}

		return ActionResultType.PASS;
	}

	public ResourceLocation getTexture() {
		return this.texture;
	}
	public ResourceLocation getSaddleRackTexture() {
		return this.texture;
	}

	@Override
	public void registerControllers(AnimationData animationData) {

	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}
