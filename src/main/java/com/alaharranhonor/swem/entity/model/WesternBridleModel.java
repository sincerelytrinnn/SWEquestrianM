package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class WesternBridleModel<T extends SWEMHorseEntityBase> extends EntityModel<T> {
	private final ModelRenderer westernbridle;
	private final ModelRenderer rightsidebit_r2;
	private final ModelRenderer rightsidebit_r1;
	private final ModelRenderer leftsidebit;
	private final ModelRenderer rightsidebit;
	private final ModelRenderer leftsiderein;
	private final ModelRenderer cube_r1_r1;
	private final ModelRenderer cube_r1;
	private final ModelRenderer bone19;
	private final ModelRenderer cube_r2_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer bone20;
	private final ModelRenderer cube_r3_r1;
	private final ModelRenderer cube_r3;
	private final ModelRenderer rightsiderein;
	private final ModelRenderer cube_r4_r1;
	private final ModelRenderer cube_r4;
	private final ModelRenderer bone17;
	private final ModelRenderer cube_r5_r1;
	private final ModelRenderer cube_r5;
	private final ModelRenderer bone18;
	private final ModelRenderer cube_r6_r1;
	private final ModelRenderer cube_r6;

	public WesternBridleModel() {
		textureWidth = 32;
		textureHeight = 32;

		westernbridle = new ModelRenderer(this);
		westernbridle.setRotationPoint(3.5F, 0.5F, -32.5F);


		rightsidebit_r2 = new ModelRenderer(this);
		rightsidebit_r2.setRotationPoint(-7.0F, 0.0F, 0.0F);
		westernbridle.addChild(rightsidebit_r2);
		setRotationAngle(rightsidebit_r2, 0.2618F, 0.0F, 0.0F);


		rightsidebit_r1 = new ModelRenderer(this);
		rightsidebit_r1.setRotationPoint(-7.0F, 0.0F, 0.0F);
		westernbridle.addChild(rightsidebit_r1);
		setRotationAngle(rightsidebit_r1, 0.2618F, 0.0F, 0.0F);


		leftsidebit = new ModelRenderer(this);
		leftsidebit.setRotationPoint(0.0F, 0.0F, 0.0F);
		westernbridle.addChild(leftsidebit);
		setRotationAngle(leftsidebit, 0.2618F, 0.0F, 0.0F);
		leftsidebit.setTextureOffset(13, 3).addBox(-0.5F, -1.1474F, 1.9921F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		leftsidebit.setTextureOffset(11, 8).addBox(-0.5F, 1.8526F, 0.9921F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		leftsidebit.setTextureOffset(10, 4).addBox(-0.5F, -0.1474F, 0.9921F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		leftsidebit.setTextureOffset(9, 6).addBox(-0.5F, -2.1474F, 0.9921F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		leftsidebit.setTextureOffset(4, 0).addBox(-0.5F, -2.1474F, -0.0079F, 1.0F, 4.0F, 1.0F, 0.0F, false);

		rightsidebit = new ModelRenderer(this);
		rightsidebit.setRotationPoint(-7.0F, 0.0F, 0.0F);
		westernbridle.addChild(rightsidebit);
		setRotationAngle(rightsidebit, 0.2618F, 0.0F, 0.0F);
		rightsidebit.setTextureOffset(8, 2).addBox(-0.5F, -1.1474F, 1.9921F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		rightsidebit.setTextureOffset(8, 0).addBox(-0.5F, 1.8526F, 0.9921F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		rightsidebit.setTextureOffset(4, 7).addBox(-0.5F, -0.1474F, 0.9921F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		rightsidebit.setTextureOffset(0, 7).addBox(-0.5F, -2.1474F, 0.9921F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		rightsidebit.setTextureOffset(0, 0).addBox(-0.5F, -2.1474F, -0.0079F, 1.0F, 4.0F, 1.0F, 0.0F, false);

		leftsiderein = new ModelRenderer(this);
		leftsiderein.setRotationPoint(-3.5F, 29.5F, 29.5F);
		westernbridle.addChild(leftsiderein);


		cube_r1_r1 = new ModelRenderer(this);
		cube_r1_r1.setRotationPoint(4.125F, -27.25F, -27.5F);
		leftsiderein.addChild(cube_r1_r1);
		setRotationAngle(cube_r1_r1, -0.3927F, 0.0F, 0.0F);


		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(4.125F, -27.25F, -27.5F);
		leftsiderein.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.3927F, 0.0F, 0.0F);
		cube_r1.setTextureOffset(0, 1).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 5.0F, 0.0F, false);

		bone19 = new ModelRenderer(this);
		bone19.setRotationPoint(4.125F, -30.25F, -27.25F);
		leftsiderein.addChild(bone19);


		cube_r2_r1 = new ModelRenderer(this);
		cube_r2_r1.setRotationPoint(0.0F, 4.4207F, 4.3736F);
		bone19.addChild(cube_r2_r1);
		setRotationAngle(cube_r2_r1, 0.3927F, 0.0F, 0.0F);


		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, 4.4207F, 4.3736F);
		bone19.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.3927F, 0.0F, 0.0F);
		cube_r2.setTextureOffset(0, 3).addBox(0.0F, -0.5464F, -0.1924F, 0.0F, 1.0F, 10.0F, 0.0F, false);

		bone20 = new ModelRenderer(this);
		bone20.setRotationPoint(0.0F, 0.0F, 7.0F);
		bone19.addChild(bone20);


		cube_r3_r1 = new ModelRenderer(this);
		cube_r3_r1.setRotationPoint(0.0F, 1.8519F, 4.7604F);
		bone20.addChild(cube_r3_r1);
		setRotationAngle(cube_r3_r1, 0.3927F, 0.0F, 0.0F);


		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, 1.8519F, 4.7604F);
		bone20.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.3927F, 0.0F, 0.0F);
		cube_r3.setTextureOffset(0, 1).addBox(0.0F, -1.0F, 2.0F, 0.0F, 1.0F, 11.0F, 0.0F, false);

		rightsiderein = new ModelRenderer(this);
		rightsiderein.setRotationPoint(-11.75F, 29.5F, 29.5F);
		westernbridle.addChild(rightsiderein);


		cube_r4_r1 = new ModelRenderer(this);
		cube_r4_r1.setRotationPoint(4.125F, -27.25F, -27.5F);
		rightsiderein.addChild(cube_r4_r1);
		setRotationAngle(cube_r4_r1, -0.3927F, 0.0F, 0.0F);


		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(4.125F, -27.25F, -27.5F);
		rightsiderein.addChild(cube_r4);
		setRotationAngle(cube_r4, -0.3927F, 0.0F, 0.0F);
		cube_r4.setTextureOffset(0, 0).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 5.0F, 0.0F, false);

		bone17 = new ModelRenderer(this);
		bone17.setRotationPoint(4.125F, -30.25F, -27.25F);
		rightsiderein.addChild(bone17);


		cube_r5_r1 = new ModelRenderer(this);
		cube_r5_r1.setRotationPoint(0.0F, 4.4207F, 4.3736F);
		bone17.addChild(cube_r5_r1);
		setRotationAngle(cube_r5_r1, 0.3927F, 0.0F, 0.0F);


		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(0.0F, 4.4207F, 4.3736F);
		bone17.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.3927F, 0.0F, 0.0F);
		cube_r5.setTextureOffset(0, 0).addBox(0.0F, -0.5464F, -0.1924F, 0.0F, 1.0F, 10.0F, 0.0F, false);

		bone18 = new ModelRenderer(this);
		bone18.setRotationPoint(0.0F, 0.0F, 7.0F);
		bone17.addChild(bone18);


		cube_r6_r1 = new ModelRenderer(this);
		cube_r6_r1.setRotationPoint(0.0F, 1.8519F, 4.7604F);
		bone18.addChild(cube_r6_r1);
		setRotationAngle(cube_r6_r1, 0.3927F, 0.0F, 0.0F);


		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(0.0F, 1.8519F, 4.7604F);
		bone18.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.3927F, 0.0F, 0.0F);
		cube_r6.setTextureOffset(0, 0).addBox(0.0F, -1.0F, 2.0F, 0.0F, 1.0F, 11.0F, 0.0F, false);
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
		westernbridle.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
