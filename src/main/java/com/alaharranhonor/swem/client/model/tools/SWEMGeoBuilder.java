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

import com.alaharranhonor.swem.client.tools.geckolib.CustomGeoBone;
import net.minecraft.util.math.vector.Vector3f;
import org.apache.commons.lang3.ArrayUtils;
import software.bernie.geckolib3.geo.raw.pojo.Bone;
import software.bernie.geckolib3.geo.raw.pojo.Cube;
import software.bernie.geckolib3.geo.raw.pojo.ModelProperties;
import software.bernie.geckolib3.geo.raw.tree.RawBoneGroup;
import software.bernie.geckolib3.geo.render.GeoBuilder;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.util.VectorUtils;

public class SWEMGeoBuilder extends GeoBuilder {

  @Override
  public GeoBone constructBone(RawBoneGroup bone, ModelProperties properties, GeoBone parent) {
    CustomGeoBone geoBone = new CustomGeoBone();

    Bone rawBone = bone.selfBone;
    Vector3f rotation =
        VectorUtils.convertDoubleToFloat(VectorUtils.fromArray(rawBone.getRotation()));
    Vector3f pivot = VectorUtils.convertDoubleToFloat(VectorUtils.fromArray(rawBone.getPivot()));
    rotation.mul(-1, -1, 1);

    geoBone.mirror = rawBone.getMirror();
    geoBone.dontRender = rawBone.getNeverRender();
    geoBone.reset = rawBone.getReset();
    geoBone.inflate = rawBone.getInflate();
    geoBone.parent = parent;
    geoBone.setModelRendererName(rawBone.getName());

    geoBone.setRotationX((float) Math.toRadians(rotation.x()));
    geoBone.setRotationY((float) Math.toRadians(rotation.y()));
    geoBone.setRotationZ((float) Math.toRadians(rotation.z()));

    geoBone.rotationPointX = -pivot.x();
    geoBone.rotationPointY = pivot.y();
    geoBone.rotationPointZ = pivot.z();

    if (!ArrayUtils.isEmpty(rawBone.getCubes())) {
      for (Cube cube : rawBone.getCubes()) {
        geoBone.childCubes.add(
            GeoCube.createFromPojoCube(
                cube,
                properties,
                geoBone.inflate == null ? null : geoBone.inflate / 16,
                geoBone.mirror));
      }
    }

    for (RawBoneGroup child : bone.children.values()) {
      geoBone.childBones.add(constructBone(child, properties, geoBone));
    }

    return geoBone;
  }
}
