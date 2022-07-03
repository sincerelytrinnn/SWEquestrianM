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
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.ElytraModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class CustomElytraModel<T extends LivingEntity> extends ElytraModel<T> {
    public ModelRendererMatrix bipedBody;

    public CustomElytraModel(ModelRenderer bipedBody) {
        this.bipedBody = new ModelRendererMatrix(bipedBody);
        this.bipedBody.cubes.clear();
        this.bipedBody.addChild(rightWing);
        this.bipedBody.addChild(leftWing);
        rightWing.zRot = 2;
        leftWing.zRot = 2;
    }

    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(this.bipedBody);
    }
}
