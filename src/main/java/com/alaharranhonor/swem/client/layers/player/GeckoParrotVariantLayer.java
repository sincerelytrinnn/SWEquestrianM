package com.alaharranhonor.swem.client.layers.player;
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

import com.alaharranhonor.swem.client.render.player.RiderRenderPlayer;
import com.alaharranhonor.swem.client.tools.geckolib.CustomGeoBone;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.ParrotVariantLayer;

public class GeckoParrotVariantLayer extends ParrotVariantLayer<AbstractClientPlayerEntity>
    implements IGeckoRenderLayer {
  private final RiderRenderPlayer renderPlayerAnimated;

  public GeckoParrotVariantLayer(RiderRenderPlayer rendererIn) {
    super(rendererIn);
    renderPlayerAnimated = rendererIn;
  }

  @Override
  public void render(
      MatrixStack matrixStackIn,
      IRenderTypeBuffer bufferIn,
      int packedLightIn,
      AbstractClientPlayerEntity entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch) {
    try {
      CustomGeoBone bone = renderPlayerAnimated.getAnimatedPlayerModel().getCustomBone("Body");
      MatrixStack newMatrixStack = new MatrixStack();
      newMatrixStack.last().normal().mul(bone.getWorldSpaceNormal());
      newMatrixStack.last().pose().multiply(bone.getWorldSpaceXform());
      this.render(
          newMatrixStack,
          bufferIn,
          packedLightIn,
          entitylivingbaseIn,
          limbSwing,
          limbSwingAmount,
          netHeadYaw,
          headPitch,
          true);
      this.render(
          newMatrixStack,
          bufferIn,
          packedLightIn,
          entitylivingbaseIn,
          limbSwing,
          limbSwingAmount,
          netHeadYaw,
          headPitch,
          false);
    } catch (RuntimeException ignored) {
    }
  }
}
