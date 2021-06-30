package com.alaharranhonor.swem.items.tack;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.ISWEMEquipable;
import com.alaharranhonor.swem.items.ItemBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class SaddlebagItem extends ItemBase implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);
	private ResourceLocation texture;

	public SaddlebagItem(String texturePath) {
		super();
		this.texture = new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/saddlebags/" + texturePath + ".png");
	}

	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		if (target instanceof ISWEMEquipable && target.isAlive()) {
			ISWEMEquipable iequipable = (ISWEMEquipable)target;
			if (iequipable.isSaddleable()) {
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


	@Override
	public void registerControllers(AnimationData animationData) {

	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public ResourceLocation getArmorTexture() {
		return this.texture;
	}
}
