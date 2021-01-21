package com.alaharranhonor.swem.items.tack;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.items.ItemBase;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class SaddlebagItem extends ItemBase implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);
	private ResourceLocation texture;

	public SaddlebagItem(String texturePath) {
		super();
		this.texture = new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/saddlebags/" + texturePath + ".png");
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
