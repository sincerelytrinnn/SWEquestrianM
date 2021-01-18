package com.alaharranhonor.swem.entity.layers;

import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entity.model.WesternSaddleModel;
import com.alaharranhonor.swem.items.tack.HorseSaddleItem;
import com.alaharranhonor.swem.items.tack.WesternSaddleItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.GeoUtils;

import java.util.Iterator;

public class WesternSaddleLayer extends GeoLayerRenderer<SWEMHorseEntity> {

	private final WesternSaddleModel modelSaddle = new WesternSaddleModel();
	private final IGeoRenderer entityRenderer;

	public WesternSaddleLayer(IGeoRenderer<SWEMHorseEntity> entityRendererIn) {
		super(entityRendererIn);
		this.entityRenderer = entityRendererIn;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SWEMHorseEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.hasSaddle();
		if (!stack.isEmpty()) {
			HorseSaddleItem saddleItem = (HorseSaddleItem) stack.getItem();
			if (shouldRender(stack, entitylivingbaseIn)) {
				matrixStackIn.push();
				matrixStackIn.translate(0.0D, 1.5D, 0.125D);
				GeoModel horseModel = this.entityRenderer.getGeoModelProvider().getModel(this.entityRenderer.getGeoModelProvider().getModelLocation(entitylivingbaseIn));

				IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(bufferIn, RenderType.getArmorCutoutNoCull(saddleItem.getTexture()), false, stack.hasEffect());
				Iterator group = modelSaddle.getModel(modelSaddle.getModelLocation((WesternSaddleItem) saddleItem)).topLevelBones.iterator();
				while (group.hasNext()) {
					GeoBone bone = (GeoBone) group.next();
					GeoBone horseBody = horseModel.getBone("body").get();
					GeoBone horseBack = horseModel.getBone("middle").get();

					bone.setRotationY(horseBody.getRotationY());
					bone.setRotationX(horseBody.getRotationZ());
					bone.setRotationZ(horseBody.getRotationX());

					bone.setPositionX(horseBody.getPositionZ());
					bone.setPositionY(horseBody.getPositionY());
					bone.setPositionZ(horseBody.getPositionX());


					this.entityRenderer.renderRecursively(bone, matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				}
				matrixStackIn.pop();
			}
		}
	}

	public boolean shouldRender(ItemStack stack, SWEMHorseEntity entity) {
		return stack.getItem() instanceof WesternSaddleItem;
	}
}
