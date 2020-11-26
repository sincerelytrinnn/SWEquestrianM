package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.RopeKnotEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.LeashKnotModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.item.LeadItem;
import net.minecraft.util.ResourceLocation;

public class RopeKnotRenderer extends EntityRenderer<RopeKnotEntity> {

	private static final ResourceLocation LEASH_KNOT_TEXTURES = new ResourceLocation(SWEM.MOD_ID, "textures/entity/rope_knot.png");
	private final LeashKnotModel<RopeKnotEntity> leashKnotModel = new LeashKnotModel<>();

	public RopeKnotRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}

	public void render(RopeKnotEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();
		matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
		this.leashKnotModel.setRotationAngles(entityIn, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.leashKnotModel.getRenderType(LEASH_KNOT_TEXTURES));
		this.leashKnotModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	public ResourceLocation getEntityTexture(RopeKnotEntity entity) {
		return LEASH_KNOT_TEXTURES;
	}
}
