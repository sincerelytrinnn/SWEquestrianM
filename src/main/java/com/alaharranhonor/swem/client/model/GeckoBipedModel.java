package com.alaharranhonor.swem.client.model;
/*
 * All Rights Reserved
 *
 * Copyright (c) 2022, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
// Huge thanks to Mowzie's Mobs for making this custom player renderer
// https://www.curseforge.com/minecraft/mc-mods/mowzies-mobs

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.client.render.player.GeckoRider;
import com.alaharranhonor.swem.client.tools.geckolib.CustomAnimatedGeoModel;
import com.alaharranhonor.swem.client.tools.geckolib.CustomGeoBone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class GeckoBipedModel extends CustomAnimatedGeoModel<GeckoRider> {

  private ResourceLocation animationFileLocation;
  private ResourceLocation modelLocation;
  private ResourceLocation textureLocation;

  public boolean isSitting = false;
  public boolean isChild = true;
  public float swingProgress;
  public boolean isSneak;
  public float swimAnimation;

  public BipedModel.ArmPose leftArmPose = BipedModel.ArmPose.EMPTY;
  public BipedModel.ArmPose rightArmPose = BipedModel.ArmPose.EMPTY;

  protected boolean useSmallArms;

  @Override
  public ResourceLocation getAnimationFileLocation(GeckoRider animatable) {
    return animationFileLocation;
  }

  @Override
  public ResourceLocation getModelLocation(GeckoRider object) {
    return modelLocation;
  }

  @Override
  public ResourceLocation getTextureLocation(GeckoRider object) {
    return textureLocation;
  }

  public boolean resourceForModelId(AbstractClientPlayerEntity player) {
    this.animationFileLocation =
        new ResourceLocation(SWEM.MOD_ID, "animations/animated_player.animation.json");
    this.modelLocation = new ResourceLocation(SWEM.MOD_ID, "geo/animated_player.geo.json");
    this.textureLocation = player.getSkinTextureLocation();
    return true;
  }

  public void setUseSmallArms(boolean useSmallArms) {
    this.useSmallArms = useSmallArms;
  }

  public boolean isUsingSmallArms() {
    return useSmallArms;
  }

  public CustomGeoBone bipedHead() {
    return getCustomBone("Head");
  }

  public CustomGeoBone bipedHeadwear() {
    return getCustomBone("HatLayer");
  }

  public CustomGeoBone bipedBody() {
    return getCustomBone("Body");
  }

  public CustomGeoBone bipedRightArm() {
    return getCustomBone("RightArm");
  }

  public CustomGeoBone bipedLeftArm() {
    return getCustomBone("LeftArm");
  }

  public CustomGeoBone bipedRightLeg() {
    return getCustomBone("RightLeg");
  }

  public CustomGeoBone bipedLeftLeg() {
    return getCustomBone("LeftLeg");
  }

  public void setVisible(boolean visible) {
    this.bipedHead().setHidden(!visible);
    this.bipedHeadwear().setHidden(!visible);
    this.bipedBody().setHidden(!visible);
    this.bipedRightArm().setHidden(!visible);
    this.bipedLeftArm().setHidden(!visible);
    this.bipedRightLeg().setHidden(!visible);
    this.bipedLeftLeg().setHidden(!visible);
  }

  public void setRotationAngles() {
    CustomGeoBone head = getCustomBone("Head");
    CustomGeoBone neck = getCustomBone("Neck");
    float yaw = 0;
    float pitch = 0;
    float roll = 0;
    GeoBone parent = neck.parent;
    while (parent != null) {
      pitch += parent.getRotationX();
      yaw += parent.getRotationY();
      roll += parent.getRotationZ();
      parent = parent.parent;
    }
    neck.addRotation(-yaw, -pitch, -roll);
  }

  public void setRotationAngles(
      PlayerEntity entityIn,
      float limbSwing,
      float limbSwingAmount,
      float ageInTicks,
      float netHeadYaw,
      float headPitch,
      float partialTick) {
    if (!isInitialized()) return;
    if (Minecraft.getInstance().isPaused()) return;

    CustomGeoBone rightArmClassic = getCustomBone("RightArmClassic");
    CustomGeoBone leftArmClassic = getCustomBone("LeftArmClassic");
    CustomGeoBone rightArmSlim = getCustomBone("RightArmSlim");
    CustomGeoBone leftArmSlim = getCustomBone("LeftArmSlim");
    if (useSmallArms) {
      rightArmClassic.setHidden(true);
      leftArmClassic.setHidden(true);
      rightArmSlim.setHidden(false);
      leftArmSlim.setHidden(false);
    } else {
      rightArmSlim.setHidden(true);
      leftArmSlim.setHidden(true);
      rightArmClassic.setHidden(false);
      leftArmClassic.setHidden(false);
    }

    this.swimAnimation = entityIn.getSwimAmount(partialTick);

    float headLookAmount = getControllerValue("HeadLookController");
    float armLookAmount = 1f - getControllerValue("ArmPitchController");
    float armLookAmountRight = getBone("ArmPitchController").getPositionY();
    float armLookAmountLeft = getBone("ArmPitchController").getPositionZ();
    boolean flag = entityIn.getFallFlyingTicks() > 4;
    boolean flag1 = entityIn.isVisuallySwimming();
    this.bipedHead().addRotationY(headLookAmount * -netHeadYaw * ((float) Math.PI / 180F));
    this.getCustomBone("LeftClavicle")
        .addRotationY(
            Math.min(armLookAmount + armLookAmountLeft, 1)
                * -netHeadYaw
                * ((float) Math.PI / 180F));
    this.getCustomBone("RightClavicle")
        .addRotationY(
            Math.min(armLookAmount + armLookAmountRight, 1)
                * -netHeadYaw
                * ((float) Math.PI / 180F));
    if (flag) {
      this.bipedHead().addRotationX((-(float) Math.PI / 4F));
    } else if (this.swimAnimation > 0.0F) {
      if (flag1) {
        this.bipedHead()
            .addRotationX(
                headLookAmount
                    * this.rotLerpRad(
                        this.swimAnimation,
                        this.bipedHead().getRotationX(),
                        (-(float) Math.PI / 4F)));
      } else {
        this.bipedHead()
            .addRotationX(
                headLookAmount
                    * this.rotLerpRad(
                        this.swimAnimation,
                        this.bipedHead().getRotationX(),
                        headPitch * ((float) Math.PI / 180F)));
      }
    } else {
      this.bipedHead().addRotationX(headLookAmount * -headPitch * ((float) Math.PI / 180F));
      this.getCustomBone("LeftClavicle")
          .addRotationX(
              Math.min(armLookAmount + armLookAmountLeft, 1)
                  * -headPitch
                  * ((float) Math.PI / 180F));
      this.getCustomBone("RightClavicle")
          .addRotationX(
              Math.min(armLookAmount + armLookAmountRight, 1)
                  * -headPitch
                  * ((float) Math.PI / 180F));
    }

    float f = 1.0F;
    if (flag) {
      f = (float) entityIn.getDeltaMovement().lengthSqr();
      f = f / 0.2F;
      f = f * f * f;
    }

    if (f < 1.0F) {
      f = 1.0F;
    }

    float legWalkAmount = getControllerValue("LegWalkController");
    float armSwingAmount = getControllerValue("ArmSwingController");
    float armSwingAmountRight = 1.0f - getBone("ArmSwingController").getPositionY();
    float armSwingAmountLeft = 1.0f - getBone("ArmSwingController").getPositionZ();
    this.bipedRightArm()
        .addRotationX(
            armSwingAmount
                * armSwingAmountRight
                * MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI)
                * 2.0F
                * limbSwingAmount
                * 0.5F
                / f);
    this.bipedLeftArm()
        .addRotationX(
            armSwingAmount
                * armSwingAmountLeft
                * MathHelper.cos(limbSwing * 0.6662F)
                * 2.0F
                * limbSwingAmount
                * 0.5F
                / f);
    this.bipedRightLeg()
        .addRotationX(
            legWalkAmount * MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f);
    this.bipedLeftLeg()
        .addRotationX(
            legWalkAmount
                * MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI)
                * 1.4F
                * limbSwingAmount
                / f);

    if (this.isSitting) {
      this.bipedRightArm().setRotationX(bipedRightArm().getRotationX() + (-(float) Math.PI / 5F));
      this.bipedLeftArm().setRotationX(bipedRightArm().getRotationX() + (-(float) Math.PI / 5F));
      this.bipedRightLeg().setRotationX(-1.4137167F);
      this.bipedRightLeg().setRotationY(((float) Math.PI / 10F));
      this.bipedRightLeg().setRotationZ(0.07853982F);
      this.bipedLeftLeg().setRotationX(-1.4137167F);
      this.bipedLeftLeg().setRotationY((-(float) Math.PI / 10F));
      this.bipedLeftLeg().setRotationZ(-0.07853982F);
      getCustomBone("Waist").setRotation(0, 0, 0);
      getCustomBone("Root").setRotation(0, 0, 0);
    }

    boolean flag2 = entityIn.getMainArm() == HandSide.RIGHT;
    boolean flag3 = flag2 ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();
    if (flag2 != flag3) {
      this.func_241655_c_(entityIn);
      this.func_241654_b_(entityIn);
    } else {
      this.func_241654_b_(entityIn);
      this.func_241655_c_(entityIn);
    }

    //		this.swingAnim(entityIn, ageInTicks);

    float sneakController = getControllerValue("CrouchController");
    if (this.isSneak) {
      this.bipedBody().addRotationX(-0.5F * sneakController);
      this.getCustomBone("Neck").addRotationX(0.5F * sneakController);
      this.bipedRightArm().addRotation(0.4F * sneakController, 0, 0);
      this.bipedLeftArm().addRotation(0.4F * sneakController, 0, 0);
      this.bipedHead().addPositionY(-1F * sneakController);
      this.bipedBody().addPosition(0, -1.5F * sneakController, 1.7f * sneakController);
      this.getCustomBone("Waist").addPosition(0, -0.2f * sneakController, 4F * sneakController);
      this.bipedLeftArm().addRotationX(-0.4f * sneakController);
      this.bipedLeftArm().addPosition(0, 0.2f * sneakController, -1f * sneakController);
      this.bipedRightArm().addRotationX(-0.4f * sneakController);
      this.bipedRightArm().addPosition(0, 0.2f * sneakController, -1f * sneakController);

      this.getCustomBone("Waist").addPositionY(2f * (1f - sneakController));
    }

    float armBreathAmount = getControllerValue("ArmBreathController");
    breathAnim(this.bipedRightArm(), this.bipedLeftArm(), ageInTicks, armBreathAmount);

    //		if (this.swimAnimation > 0.0F) {
    //			float f1 = limbSwing % 26.0F;
    //			HandSide handside = this.getMainHand(entityIn);
    //			float f2 = handside == HandSide.RIGHT && this.swingProgress > 0.0F ? 0.0F :
    // this.swimAnimation;
    //			float f3 = handside == HandSide.LEFT && this.swingProgress > 0.0F ? 0.0F :
    // this.swimAnimation;
    //			if (f1 < 14.0F) {
    //				this.bipedLeftArm().setRotationX(this.rotLerpRad(f3, this.bipedLeftArm().getRotationX(),
    // 0.0F));
    //				this.bipedRightArm().setRotationX(MathHelper.lerp(f2, this.bipedRightArm().getRotationX(),
    // 0.0F));
    //				this.bipedLeftArm().setRotationY(this.rotLerpRad(f3, this.bipedLeftArm().getRotationY(),
    // (float)Math.PI));
    //				this.bipedRightArm().setRotationY(MathHelper.lerp(f2, this.bipedRightArm().getRotationY(),
    // (float)Math.PI));
    //				this.bipedLeftArm().setRotationZ(this.rotLerpRad(f3, this.bipedLeftArm().getRotationZ(),
    // (float)Math.PI + 1.8707964F * this.getArmAngleSq(f1) / this.getArmAngleSq(14.0F)));
    //				this.bipedRightArm().setRotationZ(MathHelper.lerp(f2, this.bipedRightArm().getRotationZ(),
    // (float)Math.PI - 1.8707964F * this.getArmAngleSq(f1) / this.getArmAngleSq(14.0F)));
    //			} else if (f1 >= 14.0F && f1 < 22.0F) {
    //				float f6 = (f1 - 14.0F) / 8.0F;
    //				this.bipedLeftArm().setRotationX(this.rotLerpRad(f3, this.bipedLeftArm().getRotationX(),
    // ((float)Math.PI / 2F) * f6));
    //				this.bipedRightArm().setRotationX(MathHelper.lerp(f2, this.bipedRightArm().getRotationX(),
    // ((float)Math.PI / 2F) * f6));
    //				this.bipedLeftArm().setRotationY(this.rotLerpRad(f3, this.bipedLeftArm().getRotationY(),
    // (float)Math.PI));
    //				this.bipedRightArm().setRotationY(MathHelper.lerp(f2, this.bipedRightArm().getRotationY(),
    // (float)Math.PI));
    //				this.bipedLeftArm().setRotationZ(this.rotLerpRad(f3, this.bipedLeftArm().getRotationZ(),
    // 5.012389F - 1.8707964F * f6));
    //				this.bipedRightArm().setRotationZ(MathHelper.lerp(f2, this.bipedRightArm().getRotationZ(),
    // 1.2707963F + 1.8707964F * f6));
    //			} else if (f1 >= 22.0F && f1 < 26.0F) {
    //				float f4 = (f1 - 22.0F) / 4.0F;
    //				this.bipedLeftArm().setRotationX(this.rotLerpRad(f3, this.bipedLeftArm().getRotationX(),
    // ((float)Math.PI / 2F) - ((float)Math.PI / 2F) * f4));
    //				this.bipedRightArm().setRotationX(MathHelper.lerp(f2, this.bipedRightArm().getRotationX(),
    // ((float)Math.PI / 2F) - ((float)Math.PI / 2F) * f4));
    //				this.bipedLeftArm().setRotationY(this.rotLerpRad(f3, this.bipedLeftArm().getRotationY(),
    // (float)Math.PI));
    //				this.bipedRightArm().setRotationY(MathHelper.lerp(f2, this.bipedRightArm().getRotationY(),
    // (float)Math.PI));
    //				this.bipedLeftArm().setRotationZ(this.rotLerpRad(f3, this.bipedLeftArm().getRotationZ(),
    // (float)Math.PI));
    //				this.bipedRightArm().setRotationZ(MathHelper.lerp(f2, this.bipedRightArm().getRotationZ(),
    // (float)Math.PI));
    //			}
    //
    //			float f7 = 0.3F;
    //			float f5 = 0.33333334F;
    //			this.bipedLeftLeg().setRotationX(MathHelper.lerp(this.swimAnimation,
    // this.bipedLeftLeg().getRotationX(), 0.3F * MathHelper.cos(limbSwing * 0.33333334F +
    // (float)Math.PI)));
    //			this.bipedRightLeg().setRotationX(MathHelper.lerp(this.swimAnimation,
    // this.bipedRightLeg().getRotationX(), 0.3F * MathHelper.cos(limbSwing * 0.33333334F)));
    //		}

  }

  protected CustomGeoBone getArmForSide(HandSide side) {
    return side == HandSide.LEFT ? this.bipedLeftArm() : this.bipedRightArm();
  }

  protected float rotLerpRad(float angleIn, float maxAngleIn, float mulIn) {
    float f = (mulIn - maxAngleIn) % ((float) Math.PI * 2F);
    if (f < -(float) Math.PI) {
      f += ((float) Math.PI * 2F);
    }

    if (f >= (float) Math.PI) {
      f -= ((float) Math.PI * 2F);
    }

    return maxAngleIn + angleIn * f;
  }

  private float getArmAngleSq(float limbSwing) {
    return -65.0F * limbSwing + limbSwing * limbSwing;
  }

  protected HandSide getMainHand(PlayerEntity entityIn) {
    HandSide handside = entityIn.getMainArm();
    return entityIn.swingingArm == Hand.MAIN_HAND ? handside : handside.getOpposite();
  }

  public static void breathAnim(
      CustomGeoBone rightArm, CustomGeoBone leftArm, float ageInTicks, float armBreathAmount) {
    rightArm.addRotationZ(armBreathAmount * MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
    leftArm.addRotationZ(armBreathAmount * -MathHelper.cos(ageInTicks * 0.09F) * 0.05F - 0.05F);
    rightArm.addRotationX(armBreathAmount * MathHelper.sin(ageInTicks * 0.067F) * 0.05F);
    leftArm.addRotationX(armBreathAmount * -MathHelper.sin(ageInTicks * 0.067F) * 0.05F);
  }

  private void func_241654_b_(PlayerEntity p_241654_1_) {
    float armSwingAmount = getControllerValue("ArmSwingController");
    switch (this.rightArmPose) {
      case EMPTY:
        break;
      case BLOCK:
        this.bipedRightArm().addRotationX(0.9424779F * armSwingAmount);
        break;
      case ITEM:
        this.bipedRightArm().addRotationX(((float) Math.PI / 10F) * armSwingAmount);
        break;
    }
  }

  private void func_241655_c_(PlayerEntity p_241655_1_) {
    float armSwingAmount = getControllerValue("ArmSwingController");
    switch (this.leftArmPose) {
      case EMPTY:
        break;
      case BLOCK:
        this.bipedLeftArm().addRotationX(0.9424779F * armSwingAmount);
        break;
      case ITEM:
        this.bipedLeftArm().addRotationX(((float) Math.PI / 10F) * armSwingAmount);
        break;
    }
  }
}
