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

import com.alaharranhonor.swem.client.model.tools.ModelRendererMatrix;
import com.google.common.collect.Lists;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

import java.util.List;
import java.util.Random;

public class ModelPlayerAnimated<T extends LivingEntity> extends PlayerModel<T> {
    private List<ModelRenderer> modelRenderers = Lists.newArrayList();

    public ModelPlayerAnimated(float modelSize, boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
        this.body = new ModelRendererMatrix(body);
        this.head = new ModelRendererMatrix(head);
        this.rightArm = new ModelRendererMatrix(rightArm);
        this.leftArm = new ModelRendererMatrix(leftArm);
        this.rightLeg = new ModelRendererMatrix(rightLeg);
        this.leftLeg = new ModelRendererMatrix(leftLeg);

        this.hat = new ModelRendererMatrix(hat);
        this.jacket = new ModelRendererMatrix(jacket);
        this.leftSleeve = new ModelRendererMatrix(leftSleeve);
        this.rightSleeve = new ModelRendererMatrix(rightSleeve);
        this.leftPants = new ModelRendererMatrix(leftPants);
        this.rightPants = new ModelRendererMatrix(rightPants);

        modelRenderers.add(this.cloak);
        if (smallArmsIn) {
            modelRenderers.add(leftArm);
            modelRenderers.add(rightArm);
            modelRenderers.add(leftSleeve);
            modelRenderers.add(rightSleeve);
        } else {
            modelRenderers.add(leftArm);
            modelRenderers.add(leftSleeve);
            modelRenderers.add(rightSleeve);
        }
        modelRenderers.add(leftLeg);
        modelRenderers.add(leftPants);
        modelRenderers.add(rightPants);
        modelRenderers.add(jacket);
    }

    public static void setUseMatrixMode(
            BipedModel<? extends LivingEntity> bipedModel, boolean useMatrixMode) {
        ModelBipedAnimated.setUseMatrixMode(bipedModel, useMatrixMode);
    }

    @Override
    public void setupAnim(
            T entityIn,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch) {
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.jacket.copyFrom(this.body);
        this.hat.copyFrom(this.head);
    }

    @Override
    public ModelRenderer getRandomModelPart(Random pRandom) {
        return this.modelRenderers.get(pRandom.nextInt(this.modelRenderers.size()));
    }

    @Override
    public void copyPropertiesTo(EntityModel<T> pModel) {
        super.copyPropertiesTo(pModel);
        if (pModel instanceof CustomElytraModel) {
            CustomElytraModel<?> elytraModel = (CustomElytraModel<?>) pModel;
            elytraModel.bipedBody.copyFrom(this.body);
        }
    }

    @Override
    public void copyPropertiesTo(BipedModel<T> pModel) {
        if (!(pModel.body instanceof ModelRendererMatrix)) {
            pModel.head = new ModelRendererMatrix(pModel.head);
            pModel.hat = new ModelRendererMatrix(pModel.hat);
            pModel.body = new ModelRendererMatrix(pModel.body);
            pModel.leftArm = new ModelRendererMatrix(pModel.leftArm);
            pModel.rightArm = new ModelRendererMatrix(pModel.rightArm);
            pModel.leftLeg = new ModelRendererMatrix(pModel.leftLeg);
            pModel.rightLeg = new ModelRendererMatrix(pModel.rightLeg);
        }
        setUseMatrixMode(pModel, true);
        super.copyPropertiesTo(pModel);
    }
}
