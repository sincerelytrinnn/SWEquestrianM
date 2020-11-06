package com.alaharranhonor.swem.entity.layers;

import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entity.model.SWEMHorseModel;
import com.alaharranhonor.swem.items.BlanketItem;
import com.alaharranhonor.swem.items.BreastCollarItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib.renderers.geo.IGeoRenderer;

public class BreastCollarLayer extends GeoLayerRenderer<SWEMHorseEntity> {

	private IGeoRenderer<SWEMHorseEntity> entity;

	public BreastCollarLayer(IGeoRenderer<SWEMHorseEntity> entityRendererIn) {
		super(entityRendererIn);
		this.entity = entityRendererIn;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SWEMHorseEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.getBreastCollar();
		if (!stack.isEmpty()) {
			BreastCollarItem breastCollar = (BreastCollarItem)stack.getItem();
			IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(breastCollar.getArmorTexture()));
			this.entity.render(this.entity.getGeoModelProvider().getModel(this.getEntityModel().getModelLocation(entitylivingbaseIn)), entitylivingbaseIn, partialTicks, this.entity.getRenderType(entitylivingbaseIn, partialTicks, matrixStackIn, bufferIn, ivertexbuilder, packedLightIn, breastCollar.getArmorTexture()),matrixStackIn, bufferIn, ivertexbuilder, packedLightIn, LivingRenderer.getPackedOverlay(entitylivingbaseIn, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
		}
	}
}
