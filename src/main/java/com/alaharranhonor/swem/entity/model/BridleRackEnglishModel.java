package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.items.tack.EnglishBridleItem;
import com.alaharranhonor.swem.items.tack.WesternSaddleItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BridleRackEnglishModel extends AnimatedGeoModel<EnglishBridleItem> {


	@Override
	public ResourceLocation getModelLocation(EnglishBridleItem englishBridleItem) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/tile/bridle_rack/bridle_rack_english.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(EnglishBridleItem englishBridleItem) {
		return englishBridleItem.getBridleRackTexture();
	}

	@Override
	public ResourceLocation getAnimationFileLocation(EnglishBridleItem englishBridleItem) {
		return null;
	}
}
