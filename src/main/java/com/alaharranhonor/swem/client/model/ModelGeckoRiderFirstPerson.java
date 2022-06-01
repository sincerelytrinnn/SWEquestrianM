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
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class ModelGeckoRiderFirstPerson extends CustomAnimatedGeoModel<GeckoRider> {

  private ResourceLocation animationFileLocation;
  private ResourceLocation modelLocation;
  private ResourceLocation textureLocation;

  public BipedModel.ArmPose leftArmPose = BipedModel.ArmPose.EMPTY;
  public BipedModel.ArmPose rightArmPose = BipedModel.ArmPose.EMPTY;

  protected boolean useSmallArms;

  @Override
  public ResourceLocation getAnimationFileLocation(GeckoRider animatable) {
    return animationFileLocation;
  }

  @Override
  public ResourceLocation getModelLocation(GeckoRider animatable) {
    return modelLocation;
  }

  @Override
  public ResourceLocation getTextureLocation(GeckoRider animatable) {
    return textureLocation;
  }

  public void setUseSmallArms(boolean useSmallArms) {
    this.useSmallArms = useSmallArms;
  }

  public boolean isUsingSmallArms() {
    return useSmallArms;
  }

  @Override
  public void setLivingAnimations(GeckoRider entity, Integer uniqueID) {
    super.setLivingAnimations(entity, uniqueID);
    if (isInitialized()) {
      CustomGeoBone rightArmLayerClassic = getCustomBone("RightArmLayerClassic");
      CustomGeoBone leftArmLayerClassic = getCustomBone("LeftArmLayerClassic");
      CustomGeoBone rightArmLayerSlim = getCustomBone("RightArmLayerSlim");
      CustomGeoBone leftArmLayerSlim = getCustomBone("LeftArmLayerSlim");
      CustomGeoBone rightArmClassic = getCustomBone("RightArmClassic");
      CustomGeoBone leftArmClassic = getCustomBone("LeftArmClassic");
      CustomGeoBone rightArmSlim = getCustomBone("RightArmSlim");
      CustomGeoBone leftArmSlim = getCustomBone("LeftArmSlim");
      getCustomBone("LeftHeldItem").setHidden(true);
      getCustomBone("RightHeldItem").setHidden(true);
      rightArmClassic.setHidden(true);
      leftArmClassic.setHidden(true);
      rightArmLayerClassic.setHidden(true);
      leftArmLayerClassic.setHidden(true);
      rightArmSlim.setHidden(true);
      leftArmSlim.setHidden(true);
      rightArmLayerSlim.setHidden(true);
      leftArmLayerSlim.setHidden(true);
    }
  }

  /** Check if the modelId has some ResourceLocation * */
  @Override
  public boolean resourceForModelId(AbstractClientPlayerEntity player) {
    this.animationFileLocation =
        new ResourceLocation(SWEM.MOD_ID, "animations/animated_player_first_person.animation.json");
    this.modelLocation =
        new ResourceLocation(SWEM.MOD_ID, "geo/animated_player_first_person.geo.json");
    this.textureLocation = player.getSkinTextureLocation();
    return true;
  }
}
