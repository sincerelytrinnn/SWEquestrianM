package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.items.tack.AdventureSaddleItem;
import com.alaharranhonor.swem.items.tack.EnglishSaddleItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AdventureSaddleModel extends AnimatedGeoModel<AdventureSaddleItem> {
	@Override
	public ResourceLocation getModelLocation(AdventureSaddleItem westernSaddleItem) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/saddles/adventure_saddle.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(AdventureSaddleItem westernSaddleItem) {
		return westernSaddleItem.getTexture();
	}

	@Override
	public ResourceLocation getAnimationFileLocation(AdventureSaddleItem westernSaddleItem) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/swem_horse.json");
	}
}
