package com.alaharranhonor.swem.armor;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.items.SWEMArmorItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AmethystArmorModel extends AnimatedGeoModel<SWEMArmorItem> {
	@Override
	public ResourceLocation getModelLocation(SWEMArmorItem swemArmorItem) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/armor/amethyst_armor_set.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SWEMArmorItem swemArmorItem) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/models/armor/" + swemArmorItem.getTexturePath() +".png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(SWEMArmorItem swemArmorItem) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/swem_horse.json");
	}
}
