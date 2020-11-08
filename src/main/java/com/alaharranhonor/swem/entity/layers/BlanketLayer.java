package com.alaharranhonor.swem.entity.layers;

import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.items.BlanketItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class BlanketLayer extends GeoLayerRenderer<SWEMHorseEntity> {

	private IGeoRenderer<SWEMHorseEntity> entity;

	public BlanketLayer(IGeoRenderer<SWEMHorseEntity> entityRendererIn) {
		super(entityRendererIn);
		this.entity = entityRendererIn;

	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SWEMHorseEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.getBlanket();
		if (!stack.isEmpty()) {
			BlanketItem blanket = (BlanketItem)stack.getItem();
			IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(blanket.getArmorTexture()));
			this.entity.render(this.entity.getGeoModelProvider().getModel(this.getEntityModel().getModelLocation(entitylivingbaseIn)), entitylivingbaseIn, partialTicks, this.entity.getRenderType(entitylivingbaseIn, partialTicks, matrixStackIn, bufferIn, ivertexbuilder, packedLightIn, blanket.getArmorTexture()),matrixStackIn, bufferIn, ivertexbuilder, packedLightIn, LivingRenderer.getPackedOverlay(entitylivingbaseIn, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
		}
	}
}
