package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BridleRackHalterModel<T extends SWEMHorseEntityBase> extends EntityModel<T> {
	private final ModelRenderer halter;

	public BridleRackHalterModel() {
		texWidth = 16;
		texHeight = 16;

		halter = new ModelRenderer(this);
		halter.setPos(-6.0F, 5.0F, 4.0F);
		halter.setTexSize(0, 0).addBox(3.5F, 5.5F, -9.75F, 5.0F, 0.0F, 1.0F, 0.01F, false);
		halter.setTexSize(2, 5).addBox(8.5F, 5.5F, -9.75F, 0.0F, 8.0F, 1.0F, 0.0F, false);
		halter.setTexSize(4, 8).addBox(5.5F, 9.5F, -11.75F, 1.0F, 4.0F, 0.0F, 0.0F, false);
		halter.setTexSize(0, 5).addBox(3.5F, 5.5F, -9.75F, 0.0F, 8.0F, 1.0F, 0.0F, false);
		halter.setTexSize(0, 1).addBox(3.5F, 13.5F, -11.75F, 0.0F, 1.0F, 4.0F, 0.0F, false);
		halter.setTexSize(4, 5).addBox(3.5F, 8.5F, -11.75F, 0.0F, 1.0F, 2.0F, 0.0F, false);
		halter.setTexSize(0, 3).addBox(3.5F, 13.5F, -7.75F, 5.0F, 1.0F, 0.0F, 0.0F, false);
		halter.setTexSize(0, 2).addBox(3.5F, 13.5F, -11.75F, 5.0F, 1.0F, 0.0F, 0.0F, false);
		halter.setTexSize(0, 1).addBox(3.5F, 8.5F, -11.75F, 5.0F, 1.0F, 0.0F, 0.0F, false);
		halter.setTexSize(0, 0).addBox(8.5F, 13.5F, -11.75F, 0.0F, 1.0F, 4.0F, 0.0F, false);
		halter.setTexSize(4, 4).addBox(8.5F, 8.5F, -11.75F, 0.0F, 1.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		halter.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
