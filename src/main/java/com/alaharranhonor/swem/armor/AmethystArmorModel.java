package com.alaharranhonor.swem.armor;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.items.SWEMArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AmethystArmorModel extends AnimatedGeoModel<SWEMArmorItem> {
	@Override
	public ResourceLocation getModelLocation(SWEMArmorItem swemArmorItem) {
		if (Minecraft.getInstance().player.getSkinType().equals("default")) {
			return new ResourceLocation(SWEM.MOD_ID, "geo/armor/amethyst_armor_set.geo.json");
		} else {
			return new ResourceLocation(SWEM.MOD_ID, "geo/armor/amethyst:armor_set_slim.geo.json");
		}
	}

	@Override
	public ResourceLocation getTextureLocation(SWEMArmorItem swemArmorItem) {
		//if (Minecraft.getInstance().player.getSkinType().equals("default")) {
			return new ResourceLocation(SWEM.MOD_ID, "textures/models/armor/" + swemArmorItem.getTexturePath() +".png");
		/*} else {
			return new ResourceLocation(SWEM.MOD_ID, "textures/models/armor/" + swemArmorItem.getTexturePath() +"_slim.png");
		}*/
	}

	@Override
	public ResourceLocation getAnimationFileLocation(SWEMArmorItem swemArmorItem) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/swem_horse.json");
	}
}
