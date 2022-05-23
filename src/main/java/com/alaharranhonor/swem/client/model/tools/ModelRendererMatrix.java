package com.alaharranhonor.swem.client.model.tools;
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

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;

public class ModelRendererMatrix extends ModelRenderer {
	private Matrix4f worldXform;
	private Matrix3f worldNormal;

	private boolean useMatrixMode;

	public ModelRendererMatrix(ModelRenderer original) {
		super((int) original.xTexSize, (int) original.yTexSize, original.xTexOffs, original.yTexOffs);
		copyFrom(original);
		cubes.addAll(original.cubes);
		children.addAll(original.children);

		worldNormal = new Matrix3f();
		worldNormal.setIdentity();
		worldXform = new Matrix4f();
		worldXform.setIdentity();

		useMatrixMode = true;
	}

	@Override
	public void translateAndRotate(MatrixStack pPoseStack) {
		if (!useMatrixMode || getWorldNormal() == null || getWorldXform() == null) {
			super.translateAndRotate(pPoseStack);
		}
		else {
			MatrixStack.Entry last = pPoseStack.last();
			last.pose().setIdentity();
			last.normal().setIdentity();
			last.pose().multiply(getWorldXform());
			last.normal().mul(getWorldNormal());
		}
		useMatrixMode = false;
	}

	@Override
	public void copyFrom(ModelRenderer modelRendererIn) {
		if (modelRendererIn instanceof ModelRendererMatrix) {
			ModelRendererMatrix other = (ModelRendererMatrix) modelRendererIn;
			this.setWorldNormal(other.getWorldNormal());
			this.setWorldXform(other.getWorldXform());
		}
		super.copyFrom(modelRendererIn);
	}

	public Matrix3f getWorldNormal() {
		return worldNormal;
	}

	public void setWorldNormal(Matrix3f worldNormal) {
		this.worldNormal = worldNormal;
	}

	public Matrix4f getWorldXform() {
		return worldXform;
	}

	public void setWorldXform(Matrix4f worldXform) {
		this.worldXform = worldXform;
	}

	public void setUseMatrixMode(boolean useMatrixMode) {
		this.useMatrixMode = useMatrixMode;
	}

	public boolean isUseMatrixMode() {
		return useMatrixMode;
	}
}
