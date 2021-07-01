package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BridleRackEnglishModel<T extends SWEMHorseEntityBase> extends EntityModel<T> {

	private final ModelRenderer english;
	private final ModelRenderer bitright;
	private final ModelRenderer bitleft;

	public BridleRackEnglishModel() {
		texWidth = 16;
		texHeight = 16;

		english = new ModelRenderer(this);
		english.setPos(-6.0F, 5.0F, 4.0F);
		english.setTexSize(0, 0).addBox(3.5F, 5.5F, -9.75F, 5.0F, 0.0F, 1.0F, 0.01F, false);
		english.setTexSize(2, 6).addBox(8.5F, 5.5F, -9.75F, 0.0F, 8.0F, 1.0F, 0.0F, false);
		english.setTexSize(0, 6).addBox(3.5F, 5.5F, -9.75F, 0.0F, 8.0F, 1.0F, 0.0F, false);
		english.setTexSize(0, 1).addBox(3.5F, 13.5F, -11.75F, 0.0F, 1.0F, 4.0F, 0.0F, false);
		english.setTexSize(4, 6).addBox(3.5F, 7.5F, -8.75F, 0.0F, 1.0F, 2.0F, 0.0F, false);
		english.setTexSize(4, 8).addBox(3.5F, 8.5F, -11.75F, 0.0F, 1.0F, 2.0F, 0.0F, false);
		english.setTexSize(0, 6).addBox(3.5F, 13.5F, -7.75F, 5.0F, 1.0F, 0.0F, 0.0F, false);
		english.setTexSize(0, 1).addBox(3.5F, 7.5F, -6.75F, 5.0F, 1.0F, 0.0F, 0.0F, false);
		english.setTexSize(0, 3).addBox(3.5F, 13.5F, -11.75F, 5.0F, 1.0F, 0.0F, 0.0F, false);
		english.setTexSize(0, 2).addBox(3.5F, 8.5F, -11.75F, 5.0F, 1.0F, 0.0F, 0.0F, false);
		english.setTexSize(0, 0).addBox(8.5F, 13.5F, -11.75F, 0.0F, 1.0F, 4.0F, 0.0F, false);
		english.setTexSize(4, 5).addBox(8.5F, 7.5F, -8.75F, 0.0F, 1.0F, 2.0F, 0.0F, false);
		english.setTexSize(4, 7).addBox(8.5F, 8.5F, -11.75F, 0.0F, 1.0F, 2.0F, 0.0F, false);

		bitright = new ModelRenderer(this);
		bitright.setPos(-5.0F, 6.0F, -4.0F);
		english.addChild(bitright);
		bitright.setTexSize(4, 10).addBox(8.4375F, 7.0F, -5.0F, 0.0F, 3.0F, 1.0F, 0.0F, false);
		bitright.setTexSize(8, 7).addBox(8.4375F, 8.0F, -7.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
		bitright.setTexSize(10, 10).addBox(8.4375F, 7.0F, -6.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
		bitright.setTexSize(6, 10).addBox(8.4375F, 9.0F, -6.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);

		bitleft = new ModelRenderer(this);
		bitleft.setPos(-5.0F, 6.0F, -4.0F);
		english.addChild(bitleft);
		bitleft.setTexSize(8, 8).addBox(13.5625F, 7.0F, -5.0F, 0.0F, 3.0F, 1.0F, 0.0F, false);
		bitleft.setTexSize(8, 6).addBox(13.5625F, 8.0F, -7.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
		bitleft.setTexSize(8, 4).addBox(13.5625F, 7.0F, -6.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
		bitleft.setTexSize(8, 3).addBox(13.5625F, 9.0F, -6.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		english.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
