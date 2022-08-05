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
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;

public class ModelBipedAnimated<T extends LivingEntity> extends BipedModel<T> {
    public ModelBipedAnimated(float modelSize) {
        super(modelSize);
        this.body = new ModelRendererMatrix(body);
        this.head = new ModelRendererMatrix(head);
        this.rightArm = new ModelRendererMatrix(rightArm);
        this.leftArm = new ModelRendererMatrix(leftArm);
        this.rightLeg = new ModelRendererMatrix(rightLeg);
        this.leftLeg = new ModelRendererMatrix(leftLeg);
    }

    public static void copyFromGeckoModel(
            BipedModel<?> bipedModel, ModelGeckoRiderThirdPerson geckoModel) {
        ((ModelRendererMatrix) bipedModel.body)
                .setWorldXform(geckoModel.bipedBody().getWorldSpaceXform());
        ((ModelRendererMatrix) bipedModel.body)
                .setWorldNormal(geckoModel.bipedBody().getWorldSpaceNormal());

        ((ModelRendererMatrix) bipedModel.head)
                .setWorldXform(geckoModel.bipedHead().getWorldSpaceXform());
        ((ModelRendererMatrix) bipedModel.head)
                .setWorldNormal(geckoModel.bipedHead().getWorldSpaceNormal());

        ((ModelRendererMatrix) bipedModel.leftLeg)
                .setWorldXform(geckoModel.bipedLeftLeg().getWorldSpaceXform());
        ((ModelRendererMatrix) bipedModel.leftLeg)
                .setWorldNormal(geckoModel.bipedLeftLeg().getWorldSpaceNormal());

        ((ModelRendererMatrix) bipedModel.rightLeg)
                .setWorldXform(geckoModel.bipedRightLeg().getWorldSpaceXform());
        ((ModelRendererMatrix) bipedModel.rightLeg)
                .setWorldNormal(geckoModel.bipedRightLeg().getWorldSpaceNormal());

        ((ModelRendererMatrix) bipedModel.rightArm)
                .setWorldXform(geckoModel.bipedRightArm().getWorldSpaceXform());
        ((ModelRendererMatrix) bipedModel.rightArm)
                .setWorldNormal(geckoModel.bipedRightArm().getWorldSpaceNormal());

        ((ModelRendererMatrix) bipedModel.leftArm)
                .setWorldXform(geckoModel.bipedLeftArm().getWorldSpaceXform());
        ((ModelRendererMatrix) bipedModel.leftArm)
                .setWorldNormal(geckoModel.bipedLeftArm().getWorldSpaceNormal());
    }

    public static void setUseMatrixMode(
            BipedModel<? extends LivingEntity> bipedModel, boolean useMatrixMode) {
        ((ModelRendererMatrix) bipedModel.body).setUseMatrixMode(useMatrixMode);
        ((ModelRendererMatrix) bipedModel.head).setUseMatrixMode(useMatrixMode);
        ((ModelRendererMatrix) bipedModel.leftLeg).setUseMatrixMode(useMatrixMode);
        ((ModelRendererMatrix) bipedModel.rightLeg).setUseMatrixMode(useMatrixMode);
        ((ModelRendererMatrix) bipedModel.rightArm).setUseMatrixMode(useMatrixMode);
        ((ModelRendererMatrix) bipedModel.leftArm).setUseMatrixMode(useMatrixMode);
    }
}
