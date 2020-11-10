package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.tileentity.TackBoxTE;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TackBoxModel extends AnimatedGeoModel<TackBoxTE> {

	@Override
	public ResourceLocation getModelLocation(TackBoxTE tackBoxTE) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/entity/tackbox.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(TackBoxTE tackBoxTE) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/entity/tackbox.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(TackBoxTE tackBoxTE) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/tackbox.json");
	}
}

