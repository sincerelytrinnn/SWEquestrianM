package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.RiderEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.entity.model.RiderModel;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.ElytraModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.RenderUtils;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class RiderGeoRenderer<T extends RiderEntity> implements IGeoRenderer<T> {

	public static final RiderGeoRenderer<RiderEntity> INSTANCE = new RiderGeoRenderer<>();

	private static final RiderModel riderModel = new RiderModel();

	private static final ResourceLocation WINGS_LOCATION = new ResourceLocation("textures/entity/elytra.png");

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
		Entity entity = animatable.getPlayer().getVehicle();
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
			renderArmorPiece(matrixStackIn, renderTypeBuffer, animatable, EquipmentSlotType.CHEST, packedLightIn, this.getGeoModelProvider().getModel(new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/rider_armor.geo.json")), model);
			renderArmorPiece(matrixStackIn, renderTypeBuffer, animatable, EquipmentSlotType.LEGS, packedLightIn, this.getGeoModelProvider().getModel(new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/rider_armor.geo.json")), model);
			renderArmorPiece(matrixStackIn, renderTypeBuffer, animatable, EquipmentSlotType.FEET, packedLightIn, this.getGeoModelProvider().getModel(new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/rider_armor.geo.json")), model);
			renderArmorPiece(matrixStackIn, renderTypeBuffer, animatable, EquipmentSlotType.HEAD, packedLightIn, this.getGeoModelProvider().getModel(new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/rider_armor.geo.json")), model);

			if (!Minecraft.getInstance().options.hideGui) {
				checkRenderNameTag(animatable, animatable.getPlayer().getDisplayName(), matrixStackIn, renderTypeBuffer, packedLightIn);
			}

			renderElytraPiece(matrixStackIn, renderTypeBuffer, animatable, packedLightIn, new ElytraModel<>(), model, partialTicks);


		}
	}

	// See ElytraLayer#render
	private void renderElytraPiece(MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, T animatable, int packedLight, ElytraModel model, GeoModel copyFrom, float partialTicks) {
		ItemStack chestStack = animatable.getPlayer().getItemBySlot(EquipmentSlotType.CHEST);
		if (chestStack.getItem() == Items.ELYTRA) { //shouldRender call.
			ResourceLocation resourcelocation;
			if (animatable.getPlayer() instanceof AbstractClientPlayerEntity) {
				AbstractClientPlayerEntity abstractclientplayerentity = (AbstractClientPlayerEntity) animatable.getPlayer();
				if (abstractclientplayerentity.isElytraLoaded() && abstractclientplayerentity.getElytraTextureLocation() != null) {
					resourcelocation = abstractclientplayerentity.getElytraTextureLocation();
				} else if (abstractclientplayerentity.isCapeLoaded() && abstractclientplayerentity.getCloakTextureLocation() != null && abstractclientplayerentity.isModelPartShown(PlayerModelPart.CAPE)) {
					resourcelocation = abstractclientplayerentity.getCloakTextureLocation();
				} else {
					resourcelocation = WINGS_LOCATION;
				}
			} else {
				resourcelocation = WINGS_LOCATION;
			}

			matrixStack.pushPose();
			matrixStack.translate(0.0D, 0.0D, 0.125D);

			boolean shouldSit = animatable.getPlayer().isPassenger();

			float f = MathHelper.rotLerp(partialTicks, animatable.getPlayer().yBodyRotO, animatable.getPlayer().yBodyRot);
			float f1 = MathHelper.rotLerp(partialTicks, animatable.getPlayer().yHeadRotO, animatable.getPlayer().yHeadRot);
			float f2 = f1 - f;
			if (shouldSit && animatable.getPlayer().getVehicle() instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity)animatable.getPlayer().getVehicle();
				f = MathHelper.rotLerp(partialTicks, livingEntity.yBodyRotO, livingEntity.yBodyRot);
				f2 = f1 - f;
				float f3 = MathHelper.wrapDegrees(f2);
				if (f3 < -85.0F) {
					f3 = -85.0F;
				}

				if (f3 >= 85.0F) {
					f3 = 85.0F;
				}

				f = f1 - f3;
				if (f3 * f3 > 2500.0F) {
					f += f3 * 0.2F;
				}

				f2 = f1 - f;
			}

			float f5 = 0.0F;
			float f6 = MathHelper.lerp(partialTicks, animatable.getPlayer().xRotO, animatable.getPlayer().xRot);
			float f7 = this.getBob(animatable, partialTicks);
			float f8 = 0.0F;

			if (!shouldSit && animatable.getPlayer().isAlive()) {
				f8 = MathHelper.lerp(partialTicks, animatable.getPlayer().animationSpeedOld, animatable.getPlayer().animationSpeed);
				f5 = animatable.getPlayer().animationPosition - animatable.getPlayer().animationSpeed * (1.0F - partialTicks);
				if (animatable.getPlayer().isBaby()) {
					f5 *= 3.0F;
				}

				if (f8 > 1.0F) {
					f8 = 1.0F;
				}
			}

			model.setupAnim(animatable.getPlayer(), f5, f8, f7, f2, f6);
			IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(resourcelocation), false, chestStack.hasFoil());
			model.renderToBuffer(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			matrixStack.popPose();
		}
	}

	// See bipedArmorLayer#render
	private void renderArmorPiece(MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, T animatable, EquipmentSlotType slotType, int packedLight, GeoModel model, GeoModel copyFrom) {
		ItemStack itemStack = animatable.getPlayer().getItemBySlot(slotType);
		if (itemStack.getItem() instanceof ArmorItem) {
			ArmorItem armorItem = (ArmorItem) itemStack.getItem();
			if (armorItem.getSlot() == slotType) {
				boolean flag1 = itemStack.hasFoil();
				this.copyPropertiesTo(copyFrom, model);
				this.setPartVisibility(model, slotType);
				IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(this.getArmorResource(animatable.getPlayer(), itemStack, slotType, null)), false, flag1);
				if (armorItem instanceof net.minecraft.item.IDyeableArmorItem) {
					int i = ((net.minecraft.item.IDyeableArmorItem) armorItem).getColor(itemStack);
					float f = (float) (i >> 16 & 255) / 255.0F;
					float f1 = (float) (i >> 8 & 255) / 255.0F;
					float f2 = (float) (i & 255) / 255.0F;
					this.renderToBuffer(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F, model);

					IVertexBuilder ivertexbuilderArmor = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(this.getArmorResource(animatable.getPlayer(), itemStack, slotType, "overlay")), false, flag1);
					this.renderToBuffer(matrixStack, ivertexbuilderArmor, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F, model);
				} else {
					this.renderToBuffer(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0F, model);
				}
			}
		}
	}

	private void copyPropertiesTo(GeoModel from, GeoModel to) {
		List<GeoBone> fromBones = from.topLevelBones;
		for (int i = 0; i < fromBones.size(); i++) {
			GeoBone copyFrom = fromBones.get(i);
			GeoBone copyTo = to.topLevelBones.get(i);
			copyTo.setPositionX(copyFrom.getPositionX());
			copyTo.setPositionY(copyFrom.getPositionY());
			copyTo.setPositionZ(copyFrom.getPositionZ());
			copyTo.setRotationX(copyFrom.getRotationX());
			copyTo.setRotationY(copyFrom.getRotationY());
			copyTo.setRotationZ(copyFrom.getRotationZ());
		}
	}

	private void setPartVisibility(GeoModel model, EquipmentSlotType slot) {
		model.topLevelBones.forEach((bone) -> bone.isHidden = true);
		switch(slot) {
			case HEAD:
				model.getBone("Head").get().isHidden = false;
				break;
			case CHEST:
				model.getBone("Body").get().isHidden = false;
				model.getBone("RightArm").get().isHidden = false;
				model.getBone("LeftArm").get().isHidden = false;
				break;
			case LEGS:
				model.getBone("Body").get().isHidden = false;
				model.getBone("RightLeg").get().isHidden = false;
				model.getBone("LeftLeg").get().isHidden = false;
				break;
			case FEET:
				model.getBone("RightLeg").get().isHidden = false;
				model.getBone("LeftLeg").get().isHidden = false;

		}
	}


	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder vertexBuilder, int packedLight, int overlay, float red, float green, float blue, float alpha, GeoModel model) {
		riderModel.headParts(model).forEach((bone) -> {
			this.renderRecursively(bone, matrixStack, vertexBuilder, packedLight, overlay, red, green, blue, alpha);
		});

		riderModel.bodyParts(model).forEach((bone) -> {
			this.renderRecursively(bone, matrixStack, vertexBuilder, packedLight, overlay, red, green, blue, alpha);
		});
	}

	public ResourceLocation getArmorResource(net.minecraft.entity.Entity entity, ItemStack stack, EquipmentSlotType slot, @javax.annotation.Nullable String type) {
		ArmorItem item = (ArmorItem)stack.getItem();
		String texture = item.getMaterial().getName();
		String domain = "minecraft";
		int idx = texture.indexOf(':');
		if (idx != -1) {
			domain = texture.substring(0, idx);
			texture = texture.substring(idx + 1);
		}
		String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (slot == EquipmentSlotType.LEGS ? 2 : 1), type == null ? "" : String.format("_%s", type));

		s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
		ResourceLocation resourcelocation = BipedArmorLayer.ARMOR_LOCATION_CACHE.get(s1);

		if (resourcelocation == null) {
			resourcelocation = new ResourceLocation(s1);
			BipedArmorLayer.ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
		}

		return resourcelocation;
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

	private void checkRenderNameTag(T animatable, ITextComponent p_225629_2_, MatrixStack p_225629_3_, IRenderTypeBuffer p_225629_4_, int p_225629_5_) {
		double d0 = Minecraft.getInstance().getEntityRenderDispatcher().distanceToSqr(animatable.getPlayer());
		p_225629_3_.pushPose();
		if (d0 < 100.0D) {
			Scoreboard scoreboard = animatable.getPlayer().getScoreboard();
			ScoreObjective scoreobjective = scoreboard.getDisplayObjective(2);
			if (scoreobjective != null) {
				Score score = scoreboard.getOrCreatePlayerScore(animatable.getPlayer().getScoreboardName(), scoreobjective);
				this.renderNameTagInWorld(animatable, (new StringTextComponent(Integer.toString(score.getScore()))).append(" ").append(scoreobjective.getDisplayName()), p_225629_3_, p_225629_4_, p_225629_5_);
				p_225629_3_.translate(0.0D, (double)(9.0F * 1.15F * 0.025F), 0.0D);
			}
		}

		this.renderNameTagInWorld(animatable, p_225629_2_, p_225629_3_, p_225629_4_, p_225629_5_);
		p_225629_3_.popPose();
	}


	private void renderNameTagInWorld(T p_225629_1_, ITextComponent p_225629_2_, MatrixStack p_225629_3_, IRenderTypeBuffer p_225629_4_, int p_225629_5_) {
		double d0 = Minecraft.getInstance().getEntityRenderDispatcher().distanceToSqr(p_225629_1_.getPlayer());
		if (net.minecraftforge.client.ForgeHooksClient.isNameplateInRenderDistance(p_225629_1_.getPlayer(), d0)) {
			boolean flag = !p_225629_1_.getPlayer().isDiscrete();
			float f = p_225629_1_.getPlayer().getBbHeight() + 1F;
			int i = "deadmau5".equals(p_225629_2_.getString()) ? -10 : 0;
			p_225629_3_.pushPose();
			p_225629_3_.translate(0.0D, (double)f, 0.0D);
			p_225629_3_.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
			p_225629_3_.scale(-0.025F, -0.025F, 0.025F);
			Matrix4f matrix4f = p_225629_3_.last().pose();
			float f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
			int j = (int)(f1 * 255.0F) << 24;
			FontRenderer fontrenderer = Minecraft.getInstance().getEntityRenderDispatcher().getFont();
			float f2 = (float)(-fontrenderer.width(p_225629_2_) / 2);
			fontrenderer.drawInBatch(p_225629_2_, f2, (float)i, 553648127, false, matrix4f, p_225629_4_, flag, j, p_225629_5_);
			if (flag) {
				fontrenderer.drawInBatch(p_225629_2_, f2, (float)i, -1, false, matrix4f, p_225629_4_, false, 0, p_225629_5_);
			}

			p_225629_3_.popPose();
		}
	}

	private float getBob(T animatable, float partialTicks) {
		return (float)animatable.getPlayer().tickCount + partialTicks;
	}


}
