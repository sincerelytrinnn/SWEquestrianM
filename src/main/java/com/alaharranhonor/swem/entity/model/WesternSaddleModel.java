package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class WesternSaddleModel<T extends SWEMHorseEntityBase> extends EntityModel<T> {
	private final ModelRenderer bone5;
	private final ModelRenderer bone14;
	private final ModelRenderer bone21;
	private final ModelRenderer bone19;
	private final ModelRenderer bone15;
	private final ModelRenderer bone20;
	private final ModelRenderer bone17;
	private final ModelRenderer bone12;
	private final ModelRenderer bone18;
	private final ModelRenderer bone11;
	private final ModelRenderer bone16;

	public WesternSaddleModel() {
		this.textureHeight = 64;
		this.textureWidth = 64;

		bone5 = new ModelRenderer(this);
		bone5.setRotationPoint(0.0F, -1.0F, -2.0F);
		bone5.setTextureOffset(0, 0).addBox(-5.0F, -8.125F, -4.125F, 10.0F, 2.0F, 9.0F, 0.0F, false);

		bone14 = new ModelRenderer(this);
		bone14.setRotationPoint(1.0F, -7.125F, 0.875F);
		bone5.addChild(bone14);
		bone14.setTextureOffset(0, 4).addBox(1.0F, -2.0F, -6.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		bone14.setTextureOffset(0, 13).addBox(-3.0F, -2.0F, -6.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		bone14.setTextureOffset(0, 11).addBox(-3.0F, -2.0F, -5.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		bone14.setTextureOffset(0, 15).addBox(-2.0F, -2.0F, -4.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		bone14.setTextureOffset(0, 0).addBox(-6.0F, -2.0F, -6.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		bone14.setTextureOffset(12, 22).addBox(-4.0F, -3.0F, -6.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);

		bone21 = new ModelRenderer(this);
		bone21.setRotationPoint(1.5F, -3.2929F, -7.2097F);
		bone14.addChild(bone21);
		setRotationAngle(bone21, -0.7854F, 0.0F, 0.0F);
		bone21.setTextureOffset(0, 17).addBox(-3.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0625F, false);

		bone19 = new ModelRenderer(this);
		bone19.setRotationPoint(-1.0F, -0.4375F, 3.3125F);
		bone14.addChild(bone19);
		bone19.setTextureOffset(6, 15).addBox(-0.5F, -3.5625F, -9.3125F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		bone15 = new ModelRenderer(this);
		bone15.setRotationPoint(0.0F, -2.2375F, 0.1125F);
		bone14.addChild(bone15);
		setRotationAngle(bone15, -0.3927F, 0.0F, 0.0F);
		bone15.setTextureOffset(18, 15).addBox(-4.0F, -3.3398F, 4.3224F, 6.0F, 2.0F, 1.0F, 0.0F, false);

		bone20 = new ModelRenderer(this);
		bone20.setRotationPoint(0.0F, -2.0F, 0.4F);
		bone14.addChild(bone20);
		setRotationAngle(bone20, -0.7854F, 0.0F, 0.0F);
		bone20.setTextureOffset(29, 29).addBox(-5.0F, -3.9325F, 3.1004F, 8.0F, 3.0F, 1.0F, 0.0F, false);

		bone17 = new ModelRenderer(this);
		bone17.setRotationPoint(7.4749F, -4.943F, -3.125F);
		bone5.addChild(bone17);
		bone17.setTextureOffset(22, 29).addBox(-15.3033F, -0.3536F, -1.0F, 1.0F, 7.0F, 5.0F, 0.0F, false);
		bone17.setTextureOffset(0, 22).addBox(-14.8033F, -0.3536F, -2.0F, 1.0F, 4.0F, 10.0F, 0.0F, false);

		bone12 = new ModelRenderer(this);
		bone12.setRotationPoint(7.4749F, -4.943F, -3.125F);
		bone5.addChild(bone12);
		bone12.setTextureOffset(34, 34).addBox(-0.6464F, -0.3536F, -1.0F, 1.0F, 7.0F, 5.0F, 0.0F, false);
		bone12.setTextureOffset(28, 1).addBox(-1.1464F, -0.3536F, -2.0F, 1.0F, 4.0F, 10.0F, 0.0F, false);

		bone18 = new ModelRenderer(this);
		bone18.setRotationPoint(0.3536F, 0.6464F, 0.0F);
		bone12.addChild(bone18);


		bone11 = new ModelRenderer(this);
		bone11.setRotationPoint(1.0F, -6.875F, -0.125F);
		bone5.addChild(bone11);
		setRotationAngle(bone11, 0.0F, 0.0F, 0.7854F);
		bone11.setTextureOffset(18, 18).addBox(1.9445F, -3.7123F, -5.0F, 4.0F, 1.0F, 10.0F, 0.0F, false);

		bone16 = new ModelRenderer(this);
		bone16.setRotationPoint(1.0F, -6.875F, -0.125F);
		bone5.addChild(bone16);
		setRotationAngle(bone16, 0.0F, 0.0F, -0.7854F);
		bone16.setTextureOffset(0, 11).addBox(-7.3588F, -5.1265F, -5.0F, 4.0F, 1.0F, 10.0F, 0.0F, false);
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


	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		bone5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}
}
