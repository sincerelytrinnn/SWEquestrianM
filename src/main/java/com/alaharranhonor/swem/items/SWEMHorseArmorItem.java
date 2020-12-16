package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.client.renderer.entity.layers.LeatherHorseArmorLayer;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;

public class SWEMHorseArmorItem extends HorseArmorItem {

	private ResourceLocation TEXTURE;
	public SWEMHorseArmorItem(String path, int armorValue, ResourceLocation texture, Properties builder) {
		super(armorValue, texture, builder);
		this.TEXTURE = new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/armor/" + path + ".png");
	}

	@Override
	public ResourceLocation getArmorTexture() {
		return this.TEXTURE;
	}

	//private AnimationFactory factory = new AnimationFactory(this);




//	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event)
//	{
//		return PlayState.STOP;
//	}
//
//	@Override
//	public void registerControllers(AnimationData animationData) {
//		animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
//	}
//
//	@Override
//	public AnimationFactory getFactory() {
//		return this.factory;
//	}
}
