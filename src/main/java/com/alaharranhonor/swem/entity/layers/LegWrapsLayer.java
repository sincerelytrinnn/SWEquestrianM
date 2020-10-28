package com.alaharranhonor.swem.entity.layers;

import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entity.model.SWEMHorseModel;
import com.alaharranhonor.swem.items.BlanketItem;
import com.alaharranhonor.swem.items.LegWrapsItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;

public class LegWrapsLayer extends LayerRenderer<SWEMHorseEntity, SWEMHorseModel> {
	public LegWrapsLayer(IEntityRenderer<SWEMHorseEntity, SWEMHorseModel> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SWEMHorseEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.getLegWraps();
		if (!stack.isEmpty()) {
			LegWrapsItem legWraps = (LegWrapsItem)stack.getItem();

			IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(legWraps.getArmorTexture()));
			this.getEntityModel().render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
		}
	}
}
