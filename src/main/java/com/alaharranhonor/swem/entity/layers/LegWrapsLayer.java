package com.alaharranhonor.swem.entity.layers;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.items.tack.LegWrapsItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class LegWrapsLayer extends GeoLayerRenderer<SWEMHorseEntity> {

	private IGeoRenderer<SWEMHorseEntity> entityRenderer;
	public LegWrapsLayer(IGeoRenderer<SWEMHorseEntity> entityRendererIn) {
		super(entityRendererIn);
		this.entityRenderer = entityRendererIn;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SWEMHorseEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.getLegWraps();
		if (!stack.isEmpty()) {
			LegWrapsItem legWraps = (LegWrapsItem)stack.getItem();

			this.entityRenderer.render(getEntityModel().getModel(new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/swem_horse_new.geo.json")),
					entitylivingbaseIn,
					partialTicks,
					RenderType.getEntityCutout(legWraps.getArmorTexture()),
					matrixStackIn,
					bufferIn,
					bufferIn.getBuffer(RenderType.getEntityCutout(legWraps.getArmorTexture())),
					packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1
			);
		}
	}
}
