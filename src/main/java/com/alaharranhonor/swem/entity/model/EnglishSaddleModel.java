package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class EnglishSaddleModel<T extends SWEMHorseEntityBase> extends EntityModel<T> {

	private final ModelRenderer bone5;
	private final ModelRenderer bone;
	private final ModelRenderer bone15;
	private final ModelRenderer bone14;
	private final ModelRenderer bone12;
	private final ModelRenderer bone18;
	private final ModelRenderer bone11;
	private final ModelRenderer bone17;
	private final ModelRenderer bone16;

	public EnglishSaddleModel() {
		this.textureHeight = 64;
		this.textureWidth = 64;

		bone5 = new ModelRenderer(this);
		bone5.setRotationPoint(1.0F, 17.0F, 0.0F);
		bone5.setTextureOffset(0, 0).addBox(-6.0F, -25.25F, -4.0F, 10.0F, 2.0F, 7.0F, 0.0F, false);

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, -25.25F, 1.0625F);
		bone5.addChild(bone);
		setRotationAngle(bone, -0.7854F, 0.0F, 0.0F);
		bone.setTextureOffset(23, 9).addBox(-5.0F, -2.4375F, 1.3125F, 8.0F, 2.0F, 1.0F, 0.0F, false);

		bone15 = new ModelRenderer(this);
		bone15.setRotationPoint(0.0F, -0.9956F, 1.6054F);
		bone.addChild(bone15);
		setRotationAngle(bone15, 0.3927F, 0.0F, 0.0F);
		bone15.setTextureOffset(0, 20).addBox(-4.0F, -2.0616F, 0.2051F, 6.0F, 1.0F, 1.0F, 0.0F, false);

		bone14 = new ModelRenderer(this);
		bone14.setRotationPoint(0.0F, -20.25F, -1.0F);
		bone5.addChild(bone14);
		setRotationAngle(bone14, -0.7854F, 0.0F, 0.0F);
		bone14.setTextureOffset(23, 12).addBox(-3.0F, -2.4375F, -5.6875F, 4.0F, 2.0F, 1.0F, 0.0F, false);
		bone14.setTextureOffset(0, 9).addBox(-2.0F, -2.4375F, -6.6875F, 2.0F, 1.0F, 1.0F, 0.0F, false);

		bone12 = new ModelRenderer(this);
		bone12.setRotationPoint(6.4749F, -22.068F, -3.0F);
		bone5.addChild(bone12);
		bone12.setTextureOffset(9, 16).addBox(-0.6464F, -0.3536F, -2.0F, 1.0F, 4.0F, 7.0F, 0.0F, false);
		bone12.setTextureOffset(20, 23).addBox(-0.6464F, 3.6464F, -1.0F, 1.0F, 1.0F, 5.0F, 0.0F, false);

		bone18 = new ModelRenderer(this);
		bone18.setRotationPoint(-6.9749F, 1.2089F, -1.5F);
		bone12.addChild(bone18);
		setRotationAngle(bone18, 0.0F, -0.7854F, 0.0F);
		bone18.setTextureOffset(3, 3).addBox(4.8094F, -1.5F, -5.4939F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		bone18.setTextureOffset(0, 0).addBox(-6.2071F, -1.5F, 5.5104F, 1.0F, 3.0F, 1.0F, 0.0F, false);

		bone11 = new ModelRenderer(this);
		bone11.setRotationPoint(0.0F, -24.0F, 0.0F);
		bone5.addChild(bone11);
		setRotationAngle(bone11, 0.0F, 0.0F, 0.7854F);
		bone11.setTextureOffset(18, 16).addBox(1.9445F, -3.7123F, -4.0F, 4.0F, 1.0F, 6.0F, 0.0F, false);

		bone17 = new ModelRenderer(this);
		bone17.setRotationPoint(6.4749F, -22.068F, -3.0F);
		bone5.addChild(bone17);
		bone17.setTextureOffset(0, 9).addBox(-15.3033F, -0.3536F, -2.0F, 1.0F, 4.0F, 7.0F, 0.0F, false);
		bone17.setTextureOffset(0, 22).addBox(-15.3033F, 3.6464F, -1.0F, 1.0F, 1.0F, 5.0F, 0.0F, false);

		bone16 = new ModelRenderer(this);
		bone16.setRotationPoint(0.0F, -24.0F, 0.0F);
		bone5.addChild(bone16);
		setRotationAngle(bone16, 0.0F, 0.0F, -0.7854F);
		bone16.setTextureOffset(9, 9).addBox(-7.3588F, -5.1265F, -4.0F, 4.0F, 1.0F, 6.0F, 0.0F, false);
	}

	/**
	 * Sets this entity's model rotation angles
	 *
	 * @param entityIn
	 * @param limbSwing
	 * @param limbSwingAmount
	 * @param ageInTicks
	 * @param netHeadYaw
	 * @param headPitch
	 */
	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}


	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bone5.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
