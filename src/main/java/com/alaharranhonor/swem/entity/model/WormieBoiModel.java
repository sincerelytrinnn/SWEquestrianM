package com.alaharranhonor.swem.entity.model;


import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.WormieBoiEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib.model.AnimatedGeoModel;

public class WormieBoiModel extends AnimatedGeoModel<WormieBoiEntity> {

	@Override
	public ResourceLocation getModelLocation(WormieBoiEntity wormieBoiEntity) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/entity/wormieboi.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(WormieBoiEntity wormieBoiEntity) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/entity/wormieboi.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(WormieBoiEntity wormieBoiEntity) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/wormieboi.json");
	}
}
