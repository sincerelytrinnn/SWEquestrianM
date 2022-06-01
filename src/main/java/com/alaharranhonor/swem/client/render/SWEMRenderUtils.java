package com.alaharranhonor.swem.client.render;
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
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;

public class SWEMRenderUtils {

  public static void moveBackFromPivotMirror(GeoCube cube, MatrixStack stack) {
    Vector3f pivot = cube.pivot;
    stack.translate(
        (double) (pivot.x() / 16.0F), (double) (-pivot.y() / 16.0F), (double) (-pivot.z() / 16.0F));
  }

  public static void moveToPivotMirror(GeoBone bone, MatrixStack stack) {
    stack.translate(
        (double) (-bone.rotationPointX / 16.0F),
        (double) (bone.rotationPointY / 16.0F),
        (double) (bone.rotationPointZ / 16.0F));
  }

  public static void moveBackFromPivotMirror(GeoBone bone, MatrixStack stack) {
    stack.translate(
        (double) (bone.rotationPointX / 16.0F),
        (double) (-bone.rotationPointY / 16.0F),
        (double) (-bone.rotationPointZ / 16.0F));
  }

  public static void translateMirror(GeoBone bone, MatrixStack stack) {
    stack.translate(
        (double) (bone.getPositionX() / 16.0F),
        (double) (bone.getPositionY() / 16.0F),
        (double) (bone.getPositionZ() / 16.0F));
  }

  public static void rotateMirror(GeoBone bone, MatrixStack stack) {
    if (bone.getRotationZ() != 0.0F) {
      stack.mulPose(Vector3f.ZP.rotation(-bone.getRotationZ()));
    }

    if (bone.getRotationY() != 0.0F) {
      stack.mulPose(Vector3f.YP.rotation(-bone.getRotationY()));
    }

    if (bone.getRotationX() != 0.0F) {
      stack.mulPose(Vector3f.XP.rotation(bone.getRotationX()));
    }
  }

  public static void rotateMirror(GeoCube bone, MatrixStack stack) {
    Vector3f rotation = bone.rotation;
    stack.mulPose(new Quaternion(0.0F, 0.0F, -rotation.z(), false));
    stack.mulPose(new Quaternion(0.0F, -rotation.y(), 0.0F, false));
    stack.mulPose(new Quaternion(rotation.x(), 0.0F, 0.0F, false));
  }
}
