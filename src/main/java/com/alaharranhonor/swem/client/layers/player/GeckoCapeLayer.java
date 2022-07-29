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

import com.alaharranhonor.swem.client.model.tools.ModelRendererMatrix;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.model.PlayerModel;

public class GeckoCapeLayer extends CapeLayer {
    public GeckoCapeLayer(
            IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>
                    playerModelIn) {
        super(playerModelIn);
    }

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
        matrixStackIn.last().pose().setIdentity();
        matrixStackIn.pushPose();
        matrixStackIn
                .last()
                .pose()
                .multiply(((ModelRendererMatrix) this.getParentModel().body).getWorldXform());
        super.render(
                matrixStackIn,
                bufferIn,
                packedLightIn,
                entitylivingbaseIn,
                limbSwing,
                limbSwingAmount,
                partialTicks,
                ageInTicks,
                netHeadYaw,
                headPitch);
        matrixStackIn.popPose();
    }
}
