package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.items.tack.EnglishBridleItem;
import com.alaharranhonor.swem.items.tack.HalterItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BridleRackHalterModel extends AnimatedGeoModel<HalterItem> {

	@Override
	public ResourceLocation getModelLocation(HalterItem halterItem) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/tile/bridle_rack/bridle_rack_halter.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(HalterItem halterItem) {
		return halterItem.getBridleRackTexture();
	}

	@Override
	public ResourceLocation getAnimationFileLocation(HalterItem halterItem) {
		return null;
	}
}
