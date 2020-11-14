package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class EnglishBridleModel<T extends SWEMHorseEntityBase> extends EntityModel<T> {
	private final ModelRenderer englishbridle;
	private final ModelRenderer rightsidebit_r2;
	private final ModelRenderer rightsidebit_r1;
	private final ModelRenderer leftsidebit;
	private final ModelRenderer rightsidebit;
	private final ModelRenderer leftsiderein;
	private final ModelRenderer bone15;
	private final ModelRenderer cube_r1_r1;
	private final ModelRenderer cube_r1;
	private final ModelRenderer bone16;
	private final ModelRenderer cube_r2_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer rightsiderein;
	private final ModelRenderer bone17;
	private final ModelRenderer cube_r3_r1;
	private final ModelRenderer cube_r3;
	private final ModelRenderer bone18;
	private final ModelRenderer cube_r4_r1;
	private final ModelRenderer cube_r4;

	public EnglishBridleModel() {
		textureWidth = 16;
		textureHeight = 16;

		englishbridle = new ModelRenderer(this);
		englishbridle.setRotationPoint(3.5F, 0.5F, -32.5F);


		rightsidebit_r2 = new ModelRenderer(this);
		rightsidebit_r2.setRotationPoint(-7.0F, 0.0F, 0.0F);
		englishbridle.addChild(rightsidebit_r2);
		setRotationAngle(rightsidebit_r2, 0.2618F, 0.0F, 0.0F);


		rightsidebit_r1 = new ModelRenderer(this);
		rightsidebit_r1.setRotationPoint(-7.0F, 0.0F, 0.0F);
		englishbridle.addChild(rightsidebit_r1);
		setRotationAngle(rightsidebit_r1, 0.2618F, 0.0F, 0.0F);


		leftsidebit = new ModelRenderer(this);
		leftsidebit.setRotationPoint(0.0F, 0.0F, 0.0F);
		englishbridle.addChild(leftsidebit);
		setRotationAngle(leftsidebit, 0.2618F, 0.0F, 0.0F);
		leftsidebit.setTextureOffset(3, 3).addBox(-0.5F, -1.1474F, 1.9921F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		leftsidebit.setTextureOffset(10, 2).addBox(-0.5F, -0.1474F, 0.9921F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		leftsidebit.setTextureOffset(10, 2).addBox(-0.5F, -2.1474F, 0.9921F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		leftsidebit.setTextureOffset(3, 3).addBox(-0.5F, -2.1474F, -0.0079F, 1.0F, 3.0F, 1.0F, 0.0F, false);

		rightsidebit = new ModelRenderer(this);
		rightsidebit.setRotationPoint(-7.0F, 0.0F, 0.0F);
		englishbridle.addChild(rightsidebit);
		setRotationAngle(rightsidebit, 0.2618F, 0.0F, 0.0F);
		rightsidebit.setTextureOffset(0, 0).addBox(-0.5F, -2.1474F, -0.0079F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		rightsidebit.setTextureOffset(7, 4).addBox(-0.5F, -2.1474F, 0.9921F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		rightsidebit.setTextureOffset(6, 2).addBox(-0.5F, -0.1474F, 0.9921F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		rightsidebit.setTextureOffset(4, 0).addBox(-0.5F, -1.1474F, 1.9921F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		leftsiderein = new ModelRenderer(this);
		leftsiderein.setRotationPoint(-3.5F, 29.5F, 29.5F);
		englishbridle.addChild(leftsiderein);
		leftsiderein.setTextureOffset(0, 5).addBox(4.125F, -31.25F, -27.25F, 0.0F, 1.0F, 7.0F, 0.0F, false);

		bone15 = new ModelRenderer(this);
		bone15.setRotationPoint(4.125F, -30.25F, -27.25F);
		leftsiderein.addChild(bone15);


		cube_r1_r1 = new ModelRenderer(this);
		cube_r1_r1.setRotationPoint(0.0F, 0.0F, 7.0F);
		bone15.addChild(cube_r1_r1);
		setRotationAngle(cube_r1_r1, 0.3927F, 0.0F, 0.0F);


		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, 0.0F, 7.0F);
		bone15.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.3927F, 0.0F, 0.0F);
		cube_r1.setTextureOffset(0, 3).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 8.0F, 0.0F, false);

		bone16 = new ModelRenderer(this);
		bone16.setRotationPoint(0.0F, 0.0F, 7.0F);
		bone15.addChild(bone16);


		cube_r2_r1 = new ModelRenderer(this);
		cube_r2_r1.setRotationPoint(0.0F, -3.0615F, 7.391F);
		bone16.addChild(cube_r2_r1);
		setRotationAngle(cube_r2_r1, 0.3927F, 0.0F, 0.0F);


		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, -3.0615F, 7.391F);
		bone16.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.3927F, 0.0F, 0.0F);
		cube_r2.setTextureOffset(0, 2).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 8.0F, 0.0F, false);

		rightsiderein = new ModelRenderer(this);
		rightsiderein.setRotationPoint(-11.75F, 29.5F, 29.5F);
		englishbridle.addChild(rightsiderein);
		rightsiderein.setTextureOffset(0, 0).addBox(4.125F, -31.25F, -27.25F, 0.0F, 1.0F, 7.0F, 0.0F, false);

		bone17 = new ModelRenderer(this);
		bone17.setRotationPoint(4.125F, -30.25F, -27.25F);
		rightsiderein.addChild(bone17);


		cube_r3_r1 = new ModelRenderer(this);
		cube_r3_r1.setRotationPoint(0.0F, 0.0F, 7.0F);
		bone17.addChild(cube_r3_r1);
		setRotationAngle(cube_r3_r1, 0.3927F, 0.0F, 0.0F);


		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, 0.0F, 7.0F);
		bone17.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.3927F, 0.0F, 0.0F);
		cube_r3.setTextureOffset(0, 1).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 8.0F, 0.0F, false);

		bone18 = new ModelRenderer(this);
		bone18.setRotationPoint(0.0F, 0.0F, 7.0F);
		bone17.addChild(bone18);


		cube_r4_r1 = new ModelRenderer(this);
		cube_r4_r1.setRotationPoint(0.0F, -3.0615F, 7.391F);
		bone18.addChild(cube_r4_r1);
		setRotationAngle(cube_r4_r1, 0.3927F, 0.0F, 0.0F);


		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(0.0F, -3.0615F, 7.391F);
		bone18.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.3927F, 0.0F, 0.0F);
		cube_r4.setTextureOffset(0, 0).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 8.0F, 0.0F, false);
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
		englishbridle.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
