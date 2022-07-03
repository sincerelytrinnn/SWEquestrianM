package com.alaharranhonor.swem.client.tools.geckolib;
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

import com.alaharranhonor.swem.client.tools.RigUtils;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector4f;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class CustomGeoBone extends GeoBone {
    public Matrix4f rotMat;
    private Matrix4f modelSpaceXform;
    private boolean trackXform;
    private Matrix4f worldSpaceXform;
    private Matrix3f worldSpaceNormal;

    public CustomGeoBone() {
        super();
        modelSpaceXform = new Matrix4f();
        modelSpaceXform.setIdentity();
        trackXform = false;
        rotMat = null;

        worldSpaceXform = new Matrix4f();
        worldSpaceXform.setIdentity();
        worldSpaceNormal = new Matrix3f();
        worldSpaceNormal.setIdentity();
    }

    public CustomGeoBone getParent() {
        return (CustomGeoBone) parent;
    }

    public boolean isTrackingXform() {
        return trackXform;
    }

    public void setTrackXform(boolean trackXform) {
        this.trackXform = trackXform;
    }

    public Matrix4f getModelSpaceXform() {
        setTrackXform(true);
        return modelSpaceXform;
    }

    public Vector3d getModelPosition() {
        Matrix4f matrix = getModelSpaceXform();
        Vector4f vec = new Vector4f(0, 0, 0, 1);
        vec.transform(matrix);
        return new Vector3d(-vec.x() * 16f, vec.y() * 16f, vec.z() * 16f);
    }

    public void setModelPosition(Vector3d pos) {
        // TODO: Doesn't work on bones with parent transforms
        CustomGeoBone parent = getParent();
        Matrix4f identity = new Matrix4f();
        identity.setIdentity();
        Matrix4f matrix = parent == null ? identity : parent.getModelSpaceXform().copy();
        matrix.invert();
        Vector4f vec =
                new Vector4f(-(float) pos.x() / 16f, (float) pos.y() / 16f, (float) pos.z() / 16f, 1);
        vec.transform(matrix);
        setPosition(-vec.x() * 16f, vec.y() * 16f, vec.z() * 16f);
    }

    public Matrix4f getModelRotationMat() {
        Matrix4f matrix = getModelSpaceXform().copy();
        RigUtils.removeMatrixTranslation(matrix);
        return matrix;
    }

    public void setModelRotationMat(Matrix4f mat) {
        rotMat = mat;
    }

    public Matrix3f getWorldSpaceNormal() {
        return worldSpaceNormal;
    }

    public void setWorldSpaceNormal(Matrix3f worldSpaceNormal) {
        this.worldSpaceNormal = worldSpaceNormal;
    }

    public Matrix4f getWorldSpaceXform() {
        return worldSpaceXform;
    }

    public void setWorldSpaceXform(Matrix4f worldSpaceXform) {
        this.worldSpaceXform = worldSpaceXform;
    }

    // Position utils
    public void addPosition(Vector3d vec) {
        addPosition((float) vec.x(), (float) vec.y(), (float) vec.z());
    }

    public void addPosition(float x, float y, float z) {
        addPositionX(x);
        addPositionY(y);
        addPositionZ(z);
    }

    public void addPositionX(float x) {
        setPositionX(getPositionX() + x);
    }

    public void addPositionY(float y) {
        setPositionY(getPositionY() + y);
    }

    public void addPositionZ(float z) {
        setPositionZ(getPositionZ() + z);
    }

    public void setPosition(float x, float y, float z) {
        setPositionX(x);
        setPositionY(y);
        setPositionZ(z);
    }

    public Vector3d getPosition() {
        return new Vector3d(getPositionX(), getPositionY(), getPositionZ());
    }

    public void setPosition(Vector3d vec) {
        setPosition((float) vec.x(), (float) vec.y(), (float) vec.z());
    }

    // Rotation utils
    public void addRotation(Vector3d vec) {
        addRotation((float) vec.x(), (float) vec.y(), (float) vec.z());
    }

    public void addRotation(float x, float y, float z) {
        addRotationX(x);
        addRotationY(y);
        addRotationZ(z);
    }

    public void addRotationX(float x) {
        setRotationX(getRotationX() + x);
    }

    public void addRotationY(float y) {
        setRotationY(getRotationY() + y);
    }

    public void addRotationZ(float z) {
        setRotationZ(getRotationZ() + z);
    }

    public void setRotation(float x, float y, float z) {
        setRotationX(x);
        setRotationY(y);
        setRotationZ(z);
    }

    public Vector3d getRotation() {
        return new Vector3d(getRotationX(), getRotationY(), getRotationZ());
    }

    public void setRotation(Vector3d vec) {
        setRotation((float) vec.x(), (float) vec.y(), (float) vec.z());
    }

    // Scale utils
    public void multiplyScale(Vector3d vec) {
        multiplyScale((float) vec.x(), (float) vec.y(), (float) vec.z());
    }

    public void multiplyScale(float x, float y, float z) {
        setScaleX(getScaleX() * x);
        setScaleY(getScaleY() * y);
        setScaleZ(getScaleZ() * z);
    }

    public void setScale(float x, float y, float z) {
        setScaleX(x);
        setScaleY(y);
        setScaleZ(z);
    }

    public Vector3d getScale() {
        return new Vector3d(getScaleX(), getScaleY(), getScaleZ());
    }

    public void setScale(Vector3d vec) {
        setScale((float) vec.x(), (float) vec.y(), (float) vec.z());
    }

    public void addRotationOffsetFromBone(CustomGeoBone source) {
        setRotationX(
                getRotationX() + source.getRotationX() - source.getInitialSnapshot().rotationValueX);
        setRotationY(
                getRotationY() + source.getRotationY() - source.getInitialSnapshot().rotationValueY);
        setRotationZ(
                getRotationZ() + source.getRotationZ() - source.getInitialSnapshot().rotationValueZ);
    }
}
