package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.items.tack.WesternSaddleItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class WesternSaddleModel extends AnimatedGeoModel<WesternSaddleItem> {


	@Override
	public ResourceLocation getModelLocation(WesternSaddleItem westernSaddleItem) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/saddles/western_saddle.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(WesternSaddleItem westernSaddleItem) {
		return westernSaddleItem.getTexture();
	}

	@Override
	public ResourceLocation getAnimationFileLocation(WesternSaddleItem westernSaddleItem) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/swem_horse.json");
	}
}
