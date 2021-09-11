package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.items.tack.BridleItem;
import com.alaharranhonor.swem.items.tack.WesternBridleItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BridleRackWesternModel extends AnimatedGeoModel<BridleItem> {

	@Override
	public ResourceLocation getModelLocation(BridleItem westernBridleItem) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/tile/bridle_rack/bridle_rack_western.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(BridleItem westernBridleItem) {
		return westernBridleItem.getBridleRackTexture();
	}

	@Override
	public ResourceLocation getAnimationFileLocation(BridleItem westernBridleItem) {
		return null;
	}
}
