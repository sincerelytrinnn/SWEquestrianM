package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.SWEMBlockStateProperties;
import com.alaharranhonor.swem.tileentity.HorseArmorRackTE;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HorseArmorRackModel extends AnimatedGeoModel<HorseArmorRackTE> {

	@Override
	public ResourceLocation getModelLocation(HorseArmorRackTE horseArmorRackTE) {
		if (horseArmorRackTE.getBlockState().get(SWEMBlockStateProperties.D_SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
			return new ResourceLocation(SWEM.MOD_ID, "geo/tile/horse_armor_rack_front.geo.json");
		}
		return new ResourceLocation(SWEM.MOD_ID, "geo/tile/horse_armor_rack_back.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(HorseArmorRackTE horseArmorRackTE) {
		if (horseArmorRackTE.getBlockState().get(SWEMBlockStateProperties.D_SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
			return new ResourceLocation(SWEM.MOD_ID, "textures/blocks/horse_armor_rack_front.png");
		}
		return new ResourceLocation(SWEM.MOD_ID, "textures/blocks/horse_armor_rack_back.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(HorseArmorRackTE horseArmorRackTE) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/tackbox.json");
	}
}
