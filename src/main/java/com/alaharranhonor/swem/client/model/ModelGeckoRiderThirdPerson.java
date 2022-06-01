package com.alaharranhonor.swem.client.model;

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
// Huge thanks to Mowzie's Mobs for making this custom player renderer
// https://www.curseforge.com/minecraft/mc-mods/mowzies-mobs

import com.alaharranhonor.swem.client.tools.geckolib.CustomGeoBone;
import net.minecraft.entity.player.PlayerEntity;

public class ModelGeckoRiderThirdPerson extends GeckoBipedModel {

  public CustomGeoBone bipedLeftArmwear() {
    return getCustomBone("LeftArmLayer");
  }

  public CustomGeoBone bipedRightArmwear() {
    return getCustomBone("RightArmLayer");
  }

  public CustomGeoBone bipedLeftLegwear() {
    return getCustomBone("LeftLegLayer");
  }

  public CustomGeoBone bipedRightLegwear() {
    return getCustomBone("RightLegLayer");
  }

  public CustomGeoBone bipedBodywear() {
    return getCustomBone("BodyLayer");
  }

  public void setVisible(boolean visible) {
    super.setVisible(visible);
    this.bipedLeftArmwear().setHidden(!visible);
    this.bipedRightArmwear().setHidden(!visible);
    this.bipedLeftLegwear().setHidden(!visible);
    this.bipedRightLegwear().setHidden(!visible);
    this.bipedBodywear().setHidden(!visible);
  }

  @Override
  public void setRotationAngles(
      PlayerEntity entityIn,
      float limbSwing,
      float limbSwingAmount,
      float ageInTicks,
      float netHeadYaw,
      float headPitch,
      float partialTick) {
    super.setRotationAngles(
        entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTick);
    CustomGeoBone rightArmLayerClassic = getCustomBone("RightArmLayerClassic");
    CustomGeoBone leftArmLayerClassic = getCustomBone("LeftArmLayerClassic");
    CustomGeoBone rightArmLayerSlim = getCustomBone("RightArmLayerSlim");
    CustomGeoBone leftArmLayerSlim = getCustomBone("LeftArmLayerSlim");
    if (useSmallArms) {
      rightArmLayerClassic.setHidden(true);
      leftArmLayerClassic.setHidden(true);
      rightArmLayerSlim.setHidden(false);
      leftArmLayerSlim.setHidden(false);
    } else {
      rightArmLayerSlim.setHidden(true);
      leftArmLayerSlim.setHidden(true);
      rightArmLayerClassic.setHidden(false);
      leftArmLayerClassic.setHidden(false);
    }
  }
}
