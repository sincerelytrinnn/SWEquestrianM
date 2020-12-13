package com.alaharranhonor.swem.entity.layers;

import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entity.model.AdventureSaddleModel;
import com.alaharranhonor.swem.entity.model.EnglishSaddleModel;
import com.alaharranhonor.swem.items.tack.AdventureSaddleItem;
import com.alaharranhonor.swem.items.tack.EnglishSaddleItem;
import com.alaharranhonor.swem.items.tack.HorseSaddleItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class AdventureSaddleLayer extends GeoLayerRenderer<SWEMHorseEntity> {

	private final AdventureSaddleModel<SWEMHorseEntity> modelSaddle = new AdventureSaddleModel();

	public AdventureSaddleLayer(IGeoRenderer<SWEMHorseEntity> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SWEMHorseEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.hasSaddle();
		if (!stack.isEmpty()) {
			HorseSaddleItem saddleItem = (HorseSaddleItem)stack.getItem();
			if (shouldRender(stack, entitylivingbaseIn)) {
				matrixStackIn.push();
				matrixStackIn.translate(0.0D, 2.9125, 0.125D); //1.55D Y
				matrixStackIn.rotate(new Quaternion(0.0f, 0.0f, 1.0f, 0.0f));
				this.modelSaddle.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(bufferIn, RenderType.getArmorCutoutNoCull(saddleItem.getTexture()), false, stack.hasEffect());
				this.modelSaddle.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.pop();
			}
		}

	}

	public boolean shouldRender(ItemStack stack, SWEMHorseEntity entity) {
		return stack.getItem() instanceof AdventureSaddleItem;
	}
}
