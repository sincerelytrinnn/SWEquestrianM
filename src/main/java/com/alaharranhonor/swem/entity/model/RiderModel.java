package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.RiderEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RiderModel extends AnimatedGeoModel<RiderEntity> {

	@Override
	public ResourceLocation getModelLocation(RiderEntity r) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/rider_" + (((ClientPlayerEntity) r.getPlayer()).getModelName().equals("default") ? "steve" : "alex") + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(RiderEntity r) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/rider.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(RiderEntity r) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/rider_" + (((ClientPlayerEntity) r.getPlayer()).getModelName().equals("default") ? "steve" : "alex") + ".animation.json");
	}
}
