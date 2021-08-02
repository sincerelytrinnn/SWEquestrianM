package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.entities.RiderEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.entity.model.RiderModel;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class RiderGeoRenderer<T extends RiderEntity> implements IGeoRenderer<T> {

	public static final RiderGeoRenderer<RiderEntity> INSTANCE = new RiderGeoRenderer<>();

	private static final RiderModel riderModel = new RiderModel();


	@Override
	public GeoModelProvider getGeoModelProvider() {
		return riderModel;
	}

	@Override
	public ResourceLocation getTextureLocation(T t) {
		return riderModel.getTextureLocation(t);
	}

	@Override
	public void render(GeoModel model, T animatable, float partialTicks, RenderType type, MatrixStack matrixStackIn, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		RiderEntity rider = animatable;
		Entity entity = rider.getPlayer().getVehicle();
		if (entity instanceof SWEMHorseEntityBase) {

			float limbSwing = 0.0f;
			float limbSwingAmount = 0.0f;
			AnimationEvent<T> predicate = new AnimationEvent((IAnimatable)entity, limbSwing, limbSwingAmount, partialTicks, limbSwingAmount <= -0.15F || limbSwingAmount >= 0.15F, Collections.singletonList(new EntityModelData()));
			this.riderModel.setLivingAnimations(animatable, this.getUniqueID(animatable), predicate);


			this.renderEarly(animatable, matrixStackIn, partialTicks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
			if (renderTypeBuffer != null) {
				vertexBuilder = renderTypeBuffer.getBuffer(type);
			}

			this.renderLate(animatable, matrixStackIn, partialTicks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
			Iterator var14 = model.topLevelBones.iterator();

			while(var14.hasNext()) {
				GeoBone group = (GeoBone)var14.next();
				this.renderRecursively(group, matrixStackIn, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
			}


			renderHeldItem(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		}
	}

	private void renderHeldItem(GeoModel model, T animatable, float partialTicks, RenderType type, MatrixStack matrixStackIn, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		PlayerEntity player = animatable.getPlayer();
		boolean flag = player.getMainArm() == HandSide.RIGHT;
		ItemStack itemstack = flag ? player.getOffhandItem() : player.getMainHandItem();
		ItemStack itemstack1 = flag ? player.getMainHandItem() : player.getOffhandItem();

		if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
			matrixStackIn.pushPose();

			this.renderArmWithItem(animatable, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HandSide.RIGHT, matrixStackIn, renderTypeBuffer, packedLightIn);
			this.renderArmWithItem(animatable, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HandSide.LEFT, matrixStackIn, renderTypeBuffer, packedLightIn);
			matrixStackIn.popPose();
		}
	}

	private void renderArmWithItem(RiderEntity playerEntity, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, HandSide handSide, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int packedLight) {
		if (!itemStack.isEmpty()) {
			matrixStack.pushPose();
			riderModel.translateToHand(handSide, matrixStack, playerEntity);
			boolean flag = handSide == HandSide.LEFT;
			matrixStack.translate((double)((float)(flag ? 6 : -6) / 16.0F), 0.9D, -0.45D);
			matrixStack.mulPose(Vector3f.XP.rotationDegrees(-60.0F));
			Minecraft.getInstance().getItemInHandRenderer().renderItem(playerEntity.getPlayer(), itemStack, transformType, flag, matrixStack, renderTypeBuffer, packedLight);
			matrixStack.popPose();
		}
	}


}
