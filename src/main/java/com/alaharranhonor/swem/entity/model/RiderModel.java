package com.alaharranhonor.swem.entity.model;


/*
 * All Rights Reserved
 *
 * Copyright (c) 2021, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.RiderEntity;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RiderModel extends AnimatedGeoModel<RiderEntity> {

	@Override
	public ResourceLocation getModelLocation(RiderEntity r) {
		return new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/rider_" + (((AbstractClientPlayerEntity) r.getPlayer()).getModelName().equals("default") ? "steve" : "alex") + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(RiderEntity r) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/rider.png");
	}



	@Override
	public ResourceLocation getAnimationFileLocation(RiderEntity r) {
		return new ResourceLocation(SWEM.MOD_ID, "animations/rider_" + (((AbstractClientPlayerEntity) r.getPlayer()).getModelName().equals("default") ? "steve" : "alex") + ".animation.json");
	}


	/**
	 * Gets arm.
	 *
	 * @param p_187074_1_ the p 187074 1
	 * @param rider       the rider
	 * @return the arm
	 */
	protected GeoBone getArm(HandSide p_187074_1_, RiderEntity rider) {
		GeoModel model = this.getModel(this.getModelLocation(rider));
		return p_187074_1_ == HandSide.LEFT ? model.getBone("LeftArm").get() : model.getBone("RightArm").get();
	}


	/**
	 * Translate to hand.
	 *
	 * @param p_225599_1_ the p 225599 1
	 * @param p_225599_2_ the p 225599 2
	 * @param rider       the rider
	 */
	public void translateToHand(HandSide p_225599_1_, MatrixStack p_225599_2_, RiderEntity rider) {
		GeoBone modelrenderer = this.getArm(p_225599_1_, rider);
		if (((AbstractClientPlayerEntity)rider.getPlayer()).getModelName().equals("slim")) {
			float f = 0.5F * (float)(p_225599_1_ == HandSide.RIGHT ? 1 : -1);
			modelrenderer.setPositionX(modelrenderer.getPositionX() + f);
			boneTranslateAndRotate(modelrenderer, p_225599_2_);
			modelrenderer.setPositionX(modelrenderer.getPositionX() - f);
		} else {
			boneTranslateAndRotate(modelrenderer, p_225599_2_);
		}
	}


	/**
	 * Bone translate and rotate.
	 *
	 * @param bone  the bone
	 * @param stack the stack
	 */
	private void boneTranslateAndRotate(GeoBone bone, MatrixStack stack) {
		stack.translate((double)(bone.getPositionX() / 16.0F), (double)(bone.getPositionY() / 16.0F), (double)(bone.getPositionZ() / 16.0F));
		if (bone.getRotationZ() != 0.0F) {
			stack.mulPose(Vector3f.ZP.rotation(bone.getRotationX()));
		}

		if (bone.getRotationY() != 0.0F) {
			stack.mulPose(Vector3f.YP.rotation(bone.getRotationY()));
		}

		if (bone.getRotationX() != 0.0F) {
			stack.mulPose(Vector3f.XP.rotation(bone.getRotationZ()));
		}
	}

	/**
	 * Head parts iterable.
	 *
	 * @param model the model
	 * @return the iterable
	 */
	public Iterable<GeoBone> headParts(GeoModel model) {
		ImmutableList<GeoBone> imList = ImmutableList.of(model.getBone("Head").get());
		if (model.getBone("HatLayer").isPresent()) {
			return Iterables.concat(imList, ImmutableList.of(model.getBone("HatLayer").get()));
		}
		return imList;
	}

	/**
	 * Body parts iterable.
	 *
	 * @param model the model
	 * @return the iterable
	 */
	public Iterable<GeoBone> bodyParts(GeoModel model) {
		ImmutableList<GeoBone> imList = ImmutableList.of();

		if (model.getBone("BodyLayer").isPresent()) {
			imList = ImmutableList.of(model.getBone("BodyLayer").get());
		}

		if (model.getBone("LeftArmLayer").isPresent()) {
			imList = (ImmutableList<GeoBone>) Iterables.concat(imList, ImmutableList.of(model.getBone("LeftArmLayer").get()));
		}

		if (model.getBone("RightArmLayer").isPresent()) {
			imList = (ImmutableList<GeoBone>) Iterables.concat(imList, ImmutableList.of(model.getBone("RightArmLayer").get()));
		}

		if (model.getBone("LeftLegLayer").isPresent()) {
			imList = (ImmutableList<GeoBone>) Iterables.concat(imList, ImmutableList.of(model.getBone("LeftLegLayer").get()));
		}

		if (model.getBone("RightLegLayer").isPresent()) {
			imList = (ImmutableList<GeoBone>) Iterables.concat(imList, ImmutableList.of(model.getBone("RightLegLayer").get()));
		}


		return Iterables.concat(imList, ImmutableList.of(model.getBone("Body").get(),
				model.getBone("RightArm").get(),
				model.getBone("LeftArm").get(),
				model.getBone("RightLeg").get(),
				model.getBone("LeftLeg").get())
		);
	}

}
