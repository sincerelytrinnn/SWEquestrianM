package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.items.SWEMHorseArmorItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HorseArmorModelGeo extends AnimatedGeoModel<SWEMHorseArmorItem> {
	@Override
	public ResourceLocation getModelLocation(SWEMHorseArmorItem swemHorseArmorItem) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/armor/" + swemHorseArmorItem.type + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SWEMHorseArmorItem swemHorseArmorItem) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/armor/" + swemHorseArmorItem.type + ".png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(SWEMHorseArmorItem swemHorseArmorItem) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/swem_horse.json"); // Make it dynamic
	}


}
