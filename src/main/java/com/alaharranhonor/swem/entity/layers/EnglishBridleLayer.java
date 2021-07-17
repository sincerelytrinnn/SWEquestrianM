package com.alaharranhonor.swem.entity.layers;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.items.tack.BridleItem;
import com.alaharranhonor.swem.items.tack.EnglishBridleItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class EnglishBridleLayer extends GeoLayerRenderer<SWEMHorseEntity> {

	private IGeoRenderer entityRenderer;
	public EnglishBridleLayer(IGeoRenderer<SWEMHorseEntity> entityRendererIn) {
		super(entityRendererIn);
		this.entityRenderer = entityRendererIn;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SWEMHorseEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.getHalter();
		if (!stack.isEmpty()) {
			if (stack.getItem() instanceof BridleItem) {
				BridleItem bridleItem = (BridleItem)stack.getItem();
				if (shouldRender(stack, entitylivingbaseIn)) {
					this.entityRenderer.render(getEntityModel().getModel(new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/swem_horse_new.geo.json")),
							entitylivingbaseIn,
							partialTicks,
							RenderType.entityCutoutNoCull(bridleItem.getModelTexture()),
							matrixStackIn,
							bufferIn,
							bufferIn.getBuffer(RenderType.entityCutoutNoCull(bridleItem.getModelTexture())),
							packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1
					);

					this.entityRenderer.render(getEntityModel().getModel(new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/swem_horse_new.geo.json")),
							entitylivingbaseIn,
							partialTicks,
							RenderType.entityCutout(bridleItem.getArmorTexture()),
							matrixStackIn,
							bufferIn,
							bufferIn.getBuffer(RenderType.entityCutout(bridleItem.getArmorTexture())),
							packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1
					);

				}
			}
		}

	}

	public boolean shouldRender(ItemStack stack, SWEMHorseEntity entity) {
		return stack.getItem() instanceof EnglishBridleItem;
	}
}
