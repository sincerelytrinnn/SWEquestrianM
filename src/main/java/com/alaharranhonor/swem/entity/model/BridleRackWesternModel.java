package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BridleRackWesternModel<T extends SWEMHorseEntityBase> extends EntityModel<T> {
	private final ModelRenderer western;
	private final ModelRenderer bitright;
	private final ModelRenderer bitleft;

	public BridleRackWesternModel() {
		texWidth = 16;
		texHeight = 16;

		western = new ModelRenderer(this);
		western.setPos(-6.0F, 5.0F, 4.0F);
		western.setTexSize(0, 0).addBox(3.5F, 5.5F, -9.75F, 5.0F, 0.0F, 1.0F, 0.01F, false);
		western.setTexSize(2, 6).addBox(8.5F, 5.5F, -9.75F, 0.0F, 8.0F, 1.0F, 0.0F, false);
		western.setTexSize(0, 6).addBox(3.5F, 5.5F, -9.75F, 0.0F, 8.0F, 1.0F, 0.0F, false);
		western.setTexSize(0, 1).addBox(3.5F, 13.5F, -11.75F, 0.0F, 1.0F, 4.0F, 0.0F, false);
		western.setTexSize(8, 7).addBox(3.5F, 7.5F, -8.75F, 0.0F, 1.0F, 2.0F, 0.0F, false);
		western.setTexSize(8, 3).addBox(3.5F, 8.5F, -11.75F, 0.0F, 1.0F, 2.0F, 0.0F, false);
		western.setTexSize(0, 6).addBox(3.5F, 13.5F, -7.75F, 5.0F, 1.0F, 0.0F, 0.01F, false);
		western.setTexSize(4, 8).addBox(5.0F, 12.5F, -7.75F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		western.setTexSize(4, 7).addBox(4.0F, 8.5F, -6.75F, 4.0F, 1.0F, 0.0F, 0.01F, false);
		western.setTexSize(10, 2).addBox(5.0F, 14.5F, -7.75F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		western.setTexSize(10, 1).addBox(5.0F, 9.5F, -6.75F, 2.0F, 1.0F, 0.0F, 0.0F, false);
		western.setTexSize(0, 3).addBox(3.5F, 7.5F, -6.75F, 5.0F, 1.0F, 0.0F, 0.0F, false);
		western.setTexSize(0, 2).addBox(3.5F, 13.5F, -11.75F, 5.0F, 1.0F, 0.0F, 0.0F, false);
		western.setTexSize(0, 1).addBox(3.5F, 8.5F, -11.75F, 5.0F, 1.0F, 0.0F, 0.0F, false);
		western.setTexSize(0, 0).addBox(8.5F, 13.5F, -11.75F, 0.0F, 1.0F, 4.0F, 0.0F, false);
		western.setTexSize(8, 2).addBox(8.5F, 7.5F, -8.75F, 0.0F, 1.0F, 2.0F, 0.0F, false);
		western.setTexSize(8, 8).addBox(8.5F, 8.5F, -11.75F, 0.0F, 1.0F, 2.0F, 0.0F, false);

		bitright = new ModelRenderer(this);
		bitright.setPos(-5.0F, 6.0F, -4.0F);
		western.addChild(bitright);
		bitright.setTexSize(6, 8).addBox(8.4375F, 7.0F, -5.0F, 0.0F, 4.0F, 1.0F, 0.0F, false);
		bitright.setTexSize(6, 12).addBox(8.4375F, 8.0F, -7.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
		bitright.setTexSize(4, 12).addBox(8.4375F, 7.0F, -6.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
		bitright.setTexSize(10, 11).addBox(8.4375F, 9.0F, -6.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
		bitright.setTexSize(10, 10).addBox(8.4375F, 11.0F, -6.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);

		bitleft = new ModelRenderer(this);
		bitleft.setPos(-5.0F, 6.0F, -4.0F);
		western.addChild(bitleft);
		bitleft.setTexSize(4, 8).addBox(13.5625F, 7.0F, -5.0F, 0.0F, 4.0F, 1.0F, 0.0F, false);
		bitleft.setTexSize(8, 11).addBox(13.5625F, 8.0F, -7.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
		bitleft.setTexSize(10, 5).addBox(13.5625F, 7.0F, -6.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
		bitleft.setTexSize(10, 2).addBox(13.5625F, 9.0F, -6.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
		bitleft.setTexSize(8, 10).addBox(13.5625F, 11.0F, -6.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);

	}

	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		western.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
