package com.alaharranhonor.swem.entity.layers;

import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entity.model.SWEMHorseModel;
import com.alaharranhonor.swem.entity.model.WesternSaddleModel;
import com.alaharranhonor.swem.items.HorseSaddleItem;
import com.alaharranhonor.swem.items.WesternSaddleItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class WesternSaddleLayer extends GeoLayerRenderer<SWEMHorseEntity> {

	private final WesternSaddleModel<SWEMHorseEntity> modelSaddle = new WesternSaddleModel<>();

	public WesternSaddleLayer(IGeoRenderer<SWEMHorseEntity> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SWEMHorseEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.hasSaddle();
		if (!stack.isEmpty()) {
			HorseSaddleItem saddleItem = (HorseSaddleItem) stack.getItem();
			if (shouldRender(stack, entitylivingbaseIn)) {
				matrixStackIn.push();
				matrixStackIn.translate(0.0D, 1.5D, 0.125D);
				matrixStackIn.rotate(new Quaternion(0.0f, 0.0f, 1.0f, 0.0f));
				this.modelSaddle.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(bufferIn, RenderType.getArmorCutoutNoCull(saddleItem.getTexture()), false, stack.hasEffect());
				this.modelSaddle.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.pop();
			}
		}
	}

	public boolean shouldRender(ItemStack stack, SWEMHorseEntity entity) {
		return stack.getItem() instanceof WesternSaddleItem;
	}
}
