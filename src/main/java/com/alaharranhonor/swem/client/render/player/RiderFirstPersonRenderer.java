package com.alaharranhonor.swem.client.render.player;
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

import com.alaharranhonor.swem.client.model.ModelGeckoRiderFirstPerson;
import com.alaharranhonor.swem.client.render.SWEMRenderUtils;
import com.alaharranhonor.swem.client.tools.geckolib.CustomGeoBone;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.FirstPersonRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.RenderUtils;

import java.util.HashMap;
import java.util.Iterator;

public class RiderFirstPersonRenderer extends FirstPersonRenderer
        implements IGeoRenderer<GeckoRider> {
    public static GeckoRider.GeckoRiderFirstPerson GECKO_PLAYER_FIRST_PERSON;

    private static HashMap<Class<? extends GeckoRider>, RiderFirstPersonRenderer> modelsToLoad =
            new HashMap<>();

    static {
        AnimationController.addModelFetcher(
                (IAnimatable object) -> {
                    if (object instanceof GeckoRider.GeckoRiderFirstPerson) {
                        RiderFirstPersonRenderer render = modelsToLoad.get(object.getClass());
                        return (IAnimatableModel<Object>) render.getGeoModelProvider();
                    } else {
                        return null;
                    }
                });
    }

    boolean mirror;
    private ModelGeckoRiderFirstPerson modelProvider;

    public RiderFirstPersonRenderer(Minecraft mcIn, ModelGeckoRiderFirstPerson modelProvider) {
        super(mcIn);
        this.modelProvider = modelProvider;
    }

    public RiderFirstPersonRenderer getModelProvider(Class<? extends GeckoRider> animatable) {
        return modelsToLoad.get(animatable);
    }

    public HashMap<Class<? extends GeckoRider>, RiderFirstPersonRenderer> getModelsToLoad() {
        return modelsToLoad;
    }

    public void renderItemInFirstPerson(
            AbstractClientPlayerEntity player,
            float pitch,
            float partialTicks,
            Hand handIn,
            float swingProgress,
            ItemStack stack,
            float equippedProgress,
            MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn,
            int combinedLightIn,
            GeckoRider geckoPlayer) {
        boolean flag = handIn == Hand.MAIN_HAND;
        HandSide handside = flag ? player.getMainArm() : player.getMainArm().getOpposite();
        mirror = player.getMainArm() == HandSide.LEFT;

        if (flag) {
            this.modelProvider.setLivingAnimations(geckoPlayer, player.getUUID().hashCode());

            RenderType rendertype = RenderType.itemEntityTranslucentCull(getTextureLocation(geckoPlayer));
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(rendertype);
            matrixStackIn.translate(0, -2, -1);
            render(
                    getGeoModelProvider().getModel(getGeoModelProvider().getModelLocation(geckoPlayer)),
                    geckoPlayer,
                    partialTicks,
                    rendertype,
                    matrixStackIn,
                    bufferIn,
                    ivertexbuilder,
                    combinedLightIn,
                    OverlayTexture.NO_OVERLAY,
                    1.0F,
                    1.0F,
                    1.0F,
                    1.0F);
        }

    /*Ability.HandDisplay handDisplay = Ability.HandDisplay.DEFAULT;
    float offHandEquipProgress = 0.0f;
    AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
    if (abilityCapability != null && abilityCapability.getActiveAbility() != null) {
    	Ability ability = abilityCapability.getActiveAbility();
    	ItemStack stackOverride = flag ? ability.heldItemMainHandOverride() : ability.heldItemOffHandOverride();
    	if (stackOverride != null) stack = stackOverride;

    	handDisplay = flag ? ability.getFirstPersonMainHandDisplay() : ability.getFirstPersonOffHandDisplay();

    	if (ability.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP)
    		offHandEquipProgress = MathHelper.clamp(1f - (ability.getTicksInSection() + partialTicks) / 5f, 0f, 1f);
    	else if (ability.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY && ability.getCurrentSection() instanceof AbilitySection.AbilitySectionDuration)
    		offHandEquipProgress = MathHelper.clamp((ability.getTicksInSection() + partialTicks - ((AbilitySection.AbilitySectionDuration)ability.getCurrentSection()).duration + 5) / 5f, 0f, 1f);
    }

    if (handDisplay != Ability.HandDisplay.DONT_RENDER && modelProvider.isInitialized()) {
    	int sideMult = handside == HandSide.RIGHT ? -1 : 1;
    	if (mirror) handside = handside.opposite();
    	String sideName = handside == HandSide.RIGHT ? "Right" : "Left";
    	String boneName = sideName + "Arm";
    	CustomGeoBone bone = this.modelProvider.getMowzieBone(boneName);

    	MatrixStack newMatrixStack = new MatrixStack();

    	float fixedPitchController = 1f - this.modelProvider.getControllerValue("FixedPitchController" + sideName);
    	newMatrixStack.rotate(new Quaternion(Vector3f.XP, pitch * fixedPitchController, true));

    	newMatrixStack.getLast().getNormal().mul(bone.getWorldSpaceNormal());
    	newMatrixStack.getLast().getMatrix().mul(bone.getWorldSpaceXform());
    	newMatrixStack.translate(sideMult * 0.547, 0.7655, 0.625);

    	if (mirror) handside = handside.opposite();

    	if (stack.isEmpty() && !flag && handDisplay == Ability.HandDisplay.FORCE_RENDER && !player.isInvisible()) {
    		newMatrixStack.translate(0, -1 * offHandEquipProgress, 0);
    		super.renderArmFirstPerson(newMatrixStack, bufferIn, combinedLightIn, 0.0f, 0.0f, handside);
    	}
    	else {
    		super.renderItemInFirstPerson(player, partialTicks, pitch, handIn, 0.0f, stack, 0.0f, newMatrixStack, bufferIn, combinedLightIn);
    	}
    }*/
    }

    public void setSmallArms() {
        this.modelProvider.setUseSmallArms(true);
    }

    @Override
    public GeoModelProvider<GeckoRider> getGeoModelProvider() {
        return this.modelProvider;
    }

    public ModelGeckoRiderFirstPerson getAnimatedPlayerModel() {
        return this.modelProvider;
    }

    @Override
    public ResourceLocation getTextureLocation(GeckoRider geckoPlayer) {
        return ((AbstractClientPlayerEntity) geckoPlayer.getPlayer()).getSkinTextureLocation();
    }

    @Override
    public void renderRecursively(
            GeoBone bone,
            MatrixStack matrixStack,
            IVertexBuilder bufferIn,
            int packedLightIn,
            int packedOverlayIn,
            float red,
            float green,
            float blue,
            float alpha) {
        matrixStack.pushPose();
        if (mirror) {
            SWEMRenderUtils.translateMirror(bone, matrixStack);
            SWEMRenderUtils.moveToPivotMirror(bone, matrixStack);
            SWEMRenderUtils.rotateMirror(bone, matrixStack);
            RenderUtils.scale(bone, matrixStack);
        } else {
            RenderUtils.translate(bone, matrixStack);
            RenderUtils.moveToPivot(bone, matrixStack);
            RenderUtils.rotate(bone, matrixStack);
            RenderUtils.scale(bone, matrixStack);
        }
        // Record xform matrices for relevant bones
        if (bone instanceof CustomGeoBone) {
            CustomGeoBone mowzieBone = (CustomGeoBone) bone;
            if (mowzieBone.name.equals("LeftArm") || mowzieBone.name.equals("RightArm")) {
                matrixStack.pushPose();
                MatrixStack.Entry entry = matrixStack.last();
                mowzieBone.setWorldSpaceNormal(entry.normal().copy());
                mowzieBone.setWorldSpaceXform(entry.pose().copy());
                matrixStack.popPose();
            }
        }
        if (mirror) {
            SWEMRenderUtils.moveBackFromPivotMirror(bone, matrixStack);
        } else {
            RenderUtils.moveBackFromPivot(bone, matrixStack);
        }
        if (!bone.isHidden) {
            Iterator var10 = bone.childCubes.iterator();

            while (var10.hasNext()) {
                GeoCube cube = (GeoCube) var10.next();
                matrixStack.pushPose();
                this.renderCube(
                        cube, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                matrixStack.popPose();
            }

            var10 = bone.childBones.iterator();

            while (var10.hasNext()) {
                GeoBone childBone = (GeoBone) var10.next();
                this.renderRecursively(
                        childBone,
                        matrixStack,
                        bufferIn,
                        packedLightIn,
                        packedOverlayIn,
                        red,
                        green,
                        blue,
                        alpha);
            }
        }

        matrixStack.popPose();
    }
}
