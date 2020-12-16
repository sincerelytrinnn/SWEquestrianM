package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class AdventureSaddleModel<T extends SWEMHorseEntityBase> extends EntityModel<T> {
	private final ModelRenderer bone5;
	private final ModelRenderer bone14;
	private final ModelRenderer cube_r1;
	private final ModelRenderer bone15;
	private final ModelRenderer cube_r2;
	private final ModelRenderer bone11;
	private final ModelRenderer cube_r3;
	private final ModelRenderer bone12;
	private final ModelRenderer bone16;
	private final ModelRenderer cube_r4;
	private final ModelRenderer bone17;

	public AdventureSaddleModel() {
		textureWidth = 64;
		textureHeight = 64;

		bone5 = new ModelRenderer(this);
		bone5.setRotationPoint(0.0F, 21.625F, -1.875F);
		bone5.setTextureOffset(0, 0).addBox(-5.0F, -8.125F, -4.125F, 10.0F, 2.0F, 9.0F, 0.0F, false);

		bone14 = new ModelRenderer(this);
		bone14.setRotationPoint(1.0F, -7.125F, 0.875F);
		bone5.addChild(bone14);
		bone14.setTextureOffset(0, 0).addBox(1.0F, -3.0F, -6.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		bone14.setTextureOffset(0, 11).addBox(-3.0F, -3.0F, -5.0F, 4.0F, 2.0F, 1.0F, 0.0F, false);
		bone14.setTextureOffset(0, 4).addBox(-6.0F, -3.0F, -6.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		bone14.setTextureOffset(10, 22).addBox(-5.0F, -4.0F, -6.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(-1.0F, -0.4375F, 3.3125F);
		bone14.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.7854F, 0.0F, 0.0F);
		cube_r1.setTextureOffset(18, 11).addBox(-5.0F, -3.0F, 0.0F, 10.0F, 3.0F, 1.0F, 0.0F, false);

		bone15 = new ModelRenderer(this);
		bone15.setRotationPoint(0.0F, -0.4375F, 0.3125F);
		bone14.addChild(bone15);


		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-1.0F, -1.3398F, 4.3224F);
		bone15.addChild(cube_r2);
		setRotationAngle(cube_r2, -0.3927F, 0.0F, 0.0F);
		cube_r2.setTextureOffset(18, 15).addBox(-4.0F, -2.0F, 0.0F, 8.0F, 2.0F, 1.0F, 0.0F, false);

		bone11 = new ModelRenderer(this);
		bone11.setRotationPoint(1.0F, -6.875F, -0.125F);
		bone5.addChild(bone11);


		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(6.4749F, 1.932F, -2.0F);
		bone11.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 0.0F, 0.7854F);
		cube_r3.setTextureOffset(18, 18).addBox(-4.0F, -0.5F, -3.0F, 4.0F, 1.0F, 10.0F, 0.01F, false);

		bone12 = new ModelRenderer(this);
		bone12.setRotationPoint(6.4749F, 1.932F, -3.0F);
		bone11.addChild(bone12);
		bone12.setTextureOffset(0, 36).addBox(-0.6464F, -0.3536F, -1.0F, 1.0F, 7.0F, 5.0F, 0.0F, false);
		bone12.setTextureOffset(10, 29).addBox(-1.1464F, -0.3536F, -2.0F, 1.0F, 4.0F, 8.0F, 0.0F, false);

		bone16 = new ModelRenderer(this);
		bone16.setRotationPoint(1.0F, -6.875F, -0.125F);
		bone5.addChild(bone16);


		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(-8.4749F, 1.932F, -2.0F);
		bone16.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0F, 0.0F, -0.7854F);
		cube_r4.setTextureOffset(0, 11).addBox(0.0F, -0.5F, -3.0F, 4.0F, 1.0F, 10.0F, 0.01F, false);

		bone17 = new ModelRenderer(this);
		bone17.setRotationPoint(6.4749F, 1.932F, -3.0F);
		bone16.addChild(bone17);
		bone17.setTextureOffset(28, 29).addBox(-15.3033F, -0.3536F, -1.0F, 1.0F, 7.0F, 5.0F, 0.0F, false);
		bone17.setTextureOffset(0, 22).addBox(-14.8033F, -0.3536F, -2.0F, 1.0F, 4.0F, 8.0F, 0.0F, false);
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

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}
}
