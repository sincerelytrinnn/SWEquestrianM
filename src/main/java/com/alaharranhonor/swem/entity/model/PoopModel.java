package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.PoopEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PoopModel extends AnimatedGeoModel<PoopEntity> {
	@Override
	public ResourceLocation getModelLocation(PoopEntity poopEntity) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse_poop.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(PoopEntity poopEntity) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse_poop.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(PoopEntity poopEntity) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/swem_horse.json");
	}
}
