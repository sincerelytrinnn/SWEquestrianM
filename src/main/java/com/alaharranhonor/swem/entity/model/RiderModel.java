package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.RiderEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RiderModel extends AnimatedGeoModel<RiderEntity> {

	@Override
	public ResourceLocation getModelLocation(RiderEntity r) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/rider.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(RiderEntity r) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/rider.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(RiderEntity r) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/rider.animation.json");
	}
}
