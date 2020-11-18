package com.alaharranhonor.swem.items;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;

public class SWEMArmorItem extends GeoArmorItem implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);

	private String TEXTURE_PATH;


	public SWEMArmorItem(String path, IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(materialIn, slot, builder);
		this.TEXTURE_PATH = path;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event)
	{
		return PlayState.STOP;
	}

	public String getTexturePath() {
		return this.TEXTURE_PATH;
	}

	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController(this, "controller", 20, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}
