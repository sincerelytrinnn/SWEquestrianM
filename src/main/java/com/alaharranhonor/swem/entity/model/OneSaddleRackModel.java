package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.tileentity.OneSaddleRackTE;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class OneSaddleRackModel extends AnimatedGeoModel<OneSaddleRackTE> {
	@Override
	public ResourceLocation getModelLocation(OneSaddleRackTE oneSaddleRackTE) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/tile/saddle_rack_floor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(OneSaddleRackTE oneSaddleRackTE) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/tile/saddle_rack_floor.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(OneSaddleRackTE oneSaddleRackTE) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/tackbox.json");
	}
}
