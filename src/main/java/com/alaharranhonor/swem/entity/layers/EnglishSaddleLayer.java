package com.alaharranhonor.swem.entity.layers;

import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entity.model.EnglishSaddleModel;
import com.alaharranhonor.swem.entity.model.SWEMHorseModel;
import com.alaharranhonor.swem.items.EnglishSaddleItem;
import com.alaharranhonor.swem.items.HorseSaddleItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;

public class EnglishSaddleLayer extends LayerRenderer<SWEMHorseEntity, SWEMHorseModel> {

	private final EnglishSaddleModel<SWEMHorseEntity> modelSaddle = new EnglishSaddleModel<>();

	public EnglishSaddleLayer(IEntityRenderer<SWEMHorseEntity, SWEMHorseModel> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SWEMHorseEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.hasSaddle();
		if (!stack.isEmpty()) {
			HorseSaddleItem saddleItem = (HorseSaddleItem)stack.getItem();
			if (shouldRender(stack, entitylivingbaseIn)) {
				matrixStackIn.push();
				matrixStackIn.translate(0.0D, 0.0D, 0.125D);
				this.getEntityModel().copyModelAttributesTo(this.modelSaddle);
				this.modelSaddle.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(bufferIn, RenderType.getArmorCutoutNoCull(saddleItem.getTexture()), false, stack.hasEffect());
				this.modelSaddle.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.pop();
			}
		}

	}

	public boolean shouldRender(ItemStack stack, SWEMHorseEntity entity) {
		return stack.getItem() instanceof EnglishSaddleItem;
	}
}
