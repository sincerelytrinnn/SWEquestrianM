package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.items.tack.SaddlebagItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SaddlebagModel extends AnimatedGeoModel<SaddlebagItem> {
	@Override
	public ResourceLocation getModelLocation(SaddlebagItem saddlebagItem) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/entity/saddlebag.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SaddlebagItem saddlebagItem) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/saddlebags/saddlebag.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(SaddlebagItem saddlebagItem) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/swem_horse.json");
	}
}
