package com.alaharranhonor.swem.entity.layers;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.items.tack.SaddlebagItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import java.util.Iterator;

public class SaddlebagLayer extends GeoLayerRenderer<SWEMHorseEntity> {

	private IGeoRenderer entityRenderer;

	public SaddlebagLayer(IGeoRenderer<SWEMHorseEntity> entityRendererIn) {
		super(entityRendererIn);
		this.entityRenderer = entityRendererIn;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SWEMHorseEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.getSaddlebag();
		if (!stack.isEmpty()) {
			SaddlebagItem bagItem = (SaddlebagItem)stack.getItem();
			this.entityRenderer.render(getEntityModel().getModel(new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/swem_horse_new.geo.json")),
					entitylivingbaseIn,
					partialTicks,
					RenderType.entityCutout(new ResourceLocation(SWEM.MOD_ID, "textures/finished/saddlebag_bedroll.png")),
					matrixStackIn,
					bufferIn,
					bufferIn.getBuffer(RenderType.entityCutout(new ResourceLocation(SWEM.MOD_ID, "textures/finished/saddlebag_bedroll.png"))),
					packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1
			);
		}
	}
}
