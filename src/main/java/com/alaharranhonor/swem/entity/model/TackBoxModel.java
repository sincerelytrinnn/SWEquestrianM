package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.SWEMBlockStateProperties;
import com.alaharranhonor.swem.tileentity.TackBoxTE;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TackBoxModel extends AnimatedGeoModel<TackBoxTE> {

	@Override
	public ResourceLocation getModelLocation(TackBoxTE tackBoxTE) {
		if (tackBoxTE.getBlockState().getValue(SWEMBlockStateProperties.D_SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
			return new ResourceLocation(SWEM.MOD_ID, "geo/tile/tackbox_left.geo.json");
		} else {
			return new ResourceLocation(SWEM.MOD_ID, "geo/tile/tackbox_right.geo.json");
		}
	}

	@Override
	public ResourceLocation getTextureLocation(TackBoxTE tackBoxTE) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/tile/tackbox_white.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(TackBoxTE tackBoxTE) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/tackbox.json");
	}
}

