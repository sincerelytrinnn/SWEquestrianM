package com.alaharranhonor.swem.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

public abstract class ArmorBaseModel extends BipedModel {
	public final ModelRenderer armorHead;
	protected final ModelRenderer armorBody;
	protected final ModelRenderer armorRightArm;
	protected final ModelRenderer armorLeftArm;
	protected final ModelRenderer armorRightLeg;
	protected final ModelRenderer armorLeftLeg;
	protected final ModelRenderer armorRightBoot;
	protected final ModelRenderer armorLeftBoot;

	private String texture;

	public ArmorBaseModel(int textureWidth, int textureHeight, ResourceLocation texture){
		super(1F);
		this.texWidth = textureWidth;
		this.texHeight = textureHeight;
		this.texture = texture.toString();

		armorHead = new ModelRenderer(this);
		armorHead.setPos(0.0F, 0.0F, 0.0F);
		head.addChild(armorHead);

		armorBody = new ModelRenderer(this);
		armorBody.setPos(0.0F, 0.0F, 0.0F);
		body.addChild(armorBody);
		armorRightArm = new ModelRenderer(this);
		armorRightArm.setPos(0.0F, 0.0F, 0.0F);
		rightArm.addChild(armorRightArm);

		armorLeftArm = new ModelRenderer(this);
		armorLeftArm.setPos(0.0F, 0.0F, 0.0F);
		leftArm.addChild(armorLeftArm);

		armorRightLeg = new ModelRenderer(this);
		armorRightLeg.setPos(0.0F, 0.0F, 0.0F);
		rightLeg.addChild(armorRightLeg);

		armorLeftLeg = new ModelRenderer(this);
		armorLeftLeg.setPos(0.0F, 0.0F, 0.0F);
		leftLeg.addChild(armorLeftLeg);


		armorRightBoot = new ModelRenderer(this);
		armorRightBoot.setPos(0.0F, 0.0F, 0.0F);
		rightLeg.addChild(armorRightBoot);

		armorLeftBoot = new ModelRenderer(this);
		armorLeftBoot.setPos(0.0F, 0.0F, 0.0F);
		leftLeg.addChild(armorLeftBoot);

		setupArmorParts();
	}

	public abstract void setupArmorParts();

	public final String getTexture(){
		return this.texture;
	}

	/**
	 * Feel free to override this method as needed.
	 * It's just required to hide armor parts depending on the equipment slot
	 */
	public BipedModel applySlot(EquipmentSlotType slot){
		armorHead.visible = false;
		armorBody.visible = false;
		armorRightArm.visible = false;
		armorLeftArm.visible = false;
		armorRightLeg.visible = false;
		armorLeftLeg.visible = false;
		armorRightBoot.visible = false;
		armorLeftBoot.visible = false;

		switch(slot){
			case HEAD:
				armorHead.visible = true;
				break;
			case CHEST:
				armorBody.visible = true;
				armorRightArm.visible = true;
				armorLeftArm.visible = true;
				break;
			case LEGS:
				armorRightLeg.visible = true;
				armorLeftLeg.visible = true;
				break;
			case FEET:
				armorRightBoot.visible = true;
				armorLeftBoot.visible = true;
				break;
			default:
				break;
		}

		return this;
	}

	public final ArmorBaseModel applyEntityStats(BipedModel defaultArmor){
		this.young = defaultArmor.young;
		this.crouching = defaultArmor.crouching;
		this.riding = defaultArmor.riding;
		this.rightArmPose = defaultArmor.rightArmPose;
		this.leftArmPose = defaultArmor.leftArmPose;

		return this;
	}


	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		copyModelAngles(this.head, this.armorHead);
		copyModelAngles(this.body, this.armorBody);
		copyModelAngles(this.rightArm, this.armorRightArm);
		copyModelAngles(this.leftArm, this.armorLeftArm);
		copyModelAngles(this.rightLeg, this.armorRightLeg);
		copyModelAngles(this.leftLeg, this.armorLeftLeg);
		copyModelAngles(this.rightLeg, this.armorRightBoot);
		copyModelAngles(this.leftLeg, this.armorLeftBoot);

		matrixStack.pushPose();
		if(crouching) matrixStack.translate(0, 0.2, 0);
		this.armorHead.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		this.armorBody.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		this.armorRightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		this.armorLeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		this.armorRightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		this.armorLeftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		this.armorRightBoot.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		this.armorLeftBoot.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

		matrixStack.popPose();
	}

	public final void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	private final void copyModelAngles(ModelRenderer in, ModelRenderer out){
		out.xRot = in.xRot;
		out.yRot = in.yRot;
		out.zRot = in.zRot;
	}
}
