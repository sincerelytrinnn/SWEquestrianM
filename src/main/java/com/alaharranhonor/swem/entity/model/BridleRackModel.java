package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.tileentity.BridleRackTE;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BridleRackModel extends AnimatedGeoModel<BridleRackTE> {
@Override
	public ResourceLocation getModelLocation(BridleRackTE bridleRackTE) {
			return new ResourceLocation(SWEM.MOD_ID, "geo/tile/bridle_rack/bridle_rack.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(BridleRackTE bridleRackTE) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/tile/bridle_rack/bridle_rack.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(BridleRackTE bridleRackTE) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/tackbox.json");
	}
}
