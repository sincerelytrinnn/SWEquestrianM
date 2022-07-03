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
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.vector.Vector3f;

public class GeckoHeldItemLayer
        extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>
        implements IGeckoRenderLayer {
    private RiderRenderPlayer renderPlayerAnimated;

    public GeckoHeldItemLayer(RiderRenderPlayer entityRendererIn) {
        super(entityRendererIn);
        renderPlayerAnimated = entityRendererIn;
    }

    @Override
    public void render(
            MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn,
            int packedLightIn,
            AbstractClientPlayerEntity entitylivingbaseIn,
            float pLimbSwing,
            float pLimbSwingAmount,
            float pPartialTicks,
            float pAgeInTicks,
            float pNetHeadYaw,
            float pHeadPitch) {
        if (!renderPlayerAnimated.getAnimatedPlayerModel().isInitialized()) return;
        boolean flag = entitylivingbaseIn.getMainArm() == HandSide.RIGHT;
        ItemStack mainHandStack = entitylivingbaseIn.getMainHandItem();
        ItemStack offHandStack = entitylivingbaseIn.getOffhandItem();

        ItemStack itemstack = flag ? offHandStack : mainHandStack;
        ItemStack itemstack1 = flag ? mainHandStack : offHandStack;
        if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
            matrixStackIn.pushPose();
            if (this.getParentModel().young) {
                float f = 0.5F;
                matrixStackIn.translate(0.0D, 0.75D, 0.0D);
                matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            }

            this.func_229135_a_(
                    entitylivingbaseIn,
                    itemstack1,
                    ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND,
                    HandSide.RIGHT,
                    matrixStackIn,
                    bufferIn,
                    packedLightIn);
            this.func_229135_a_(
                    entitylivingbaseIn,
                    itemstack,
                    ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND,
                    HandSide.LEFT,
                    matrixStackIn,
                    bufferIn,
                    packedLightIn);
            matrixStackIn.popPose();
        }
    }

    private void func_229135_a_(
            LivingEntity entity,
            ItemStack itemStack,
            ItemCameraTransforms.TransformType transformType,
            HandSide side,
            MatrixStack matrixStack,
            IRenderTypeBuffer buffer,
            int packedLightIn) {
        if (!itemStack.isEmpty()) {
            String boneName = side == HandSide.RIGHT ? "RightHeldItem" : "LeftHeldItem";
            CustomGeoBone bone = renderPlayerAnimated.getAnimatedPlayerModel().getCustomBone(boneName);
            MatrixStack newMatrixStack = new MatrixStack();
            newMatrixStack.last().normal().mul(bone.getWorldSpaceNormal());
            newMatrixStack.last().pose().multiply(bone.getWorldSpaceXform());
            newMatrixStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            boolean flag = side == HandSide.LEFT;
            Minecraft.getInstance()
                    .getItemInHandRenderer()
                    .renderItem(
                            entity, itemStack, transformType, flag, newMatrixStack, buffer, packedLightIn);
        }
    }
}
