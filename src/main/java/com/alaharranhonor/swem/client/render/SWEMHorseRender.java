package com.alaharranhonor.swem.client.render;

/*
 * All Rights Reserved
 *
 * Copyright (c) 2021, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.LeadAnchorBlock;
import com.alaharranhonor.swem.client.coats.SWEMCoatColor;
import com.alaharranhonor.swem.client.model.SWEMHorseModel;
import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.items.SWEMHorseArmorItem;
import com.alaharranhonor.swem.items.tack.*;
import com.alaharranhonor.swem.util.GeneralEventHandlers;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.LightType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;

import java.util.Arrays;
import java.util.List;

public class SWEMHorseRender<T extends LivingEntity & IAnimatable>
        extends ExtendedGeoEntityRenderer<SWEMHorseEntity> {

    private static final String[] ADVENTURE_SADDLE_BONE_NAMES = {
            "AdventureSaddle",
            "AdventureSaddleMiddle",
            "AdventureSaddleLeftSide",
            "AdventureSaddleRightSide"
    };
    private static final String[] ENGLISH_SADDLE_BONE_NAMES = {
            "EnglishSaddle", "EnglishSaddleMiddle", "EnglishSaddleLeftSide", "EnglishSaddleRightSide"
    };
    private static final String[] WESTERN_SADDLE_BONE_NAMES = {
            "WesternSaddle", "WesternSaddleMiddle", "WesternSaddleLeftSide", "WesternSaddleRightSide"
    };

    private static final String[] WESTERN_BRIDLE_BONE_NAMES = {
            "WesternBridle",
            "WesternBridleLeftBit",
            "WesternBridleRightBit",
    };

    private static final String[] WESTERN_BRIDLE_REIN_BONE_NAMES = {

            "WesternBridleLeftRein",
            "WesternBridleLeftRein1",
            "WesternBridleLeftRein2",
            "WesternBridleRightRein",
            "WesternBridleRightRein1",
            "WesternBridleRightRein2"
    };

    private static final String[] ENGLISH_BRIDLE_BONE_NAMES = {
            "EnglishBridle",
            "EnglishBridleLeftBit",
            "EnglishBridleRightBit",
    };

    private static final String[] ENGLISH_BRIDLE_REIN_BONE_NAMES = {

            "EnglishBridleLeftRein",
            "EnglishBridleLeftRein1",
            "EnglishBridleLeftRein2",
            "EnglishBridleRightRein",
            "EnglishBridleRightRein1",
            "EnglishBridleRightRein2"
    };

    private static final String[] ARMOR_BONE_NAMES = {
            "ArmorHead",
            "ArmorHead1",
            "ArmorNeck",
            "ArmorNeck1",
            "ArmorRightShoulder",
            "ArmorLeftShoulder",
            "ArmorCloth",
            "ArmorClothLeft",
            "ArmorClothRight",
            "ArmorButt"
    };
    private static final String[] WING_BONE_NAMES = {
            "WingsScapularLeftGROUP",
            "WingsScapularLeft",
            "WingsMarginalLeft",
            "WingsMarginal2Left",
            "WingsAlulaLeft",
            "WingsFlightFeathersRight",
            "WingsScapularRightGROUP",
            "WingsScapularRight",
            "WingsMarginalRight",
            "WingsMarginal2Right",
            "WingsAlulaRight",
            "WingsFlightFeathersRight"
    };

    private static final String[] SADDLE_BAG_AND_BED_ROLL_BONE_NAMES = {
            "SaddleBag", "SaddleBagLeft", "SaddleBagRight", "BedRoll"
    };
    private static final String[] BLANKET_BONE_NAMES = {
            "BlanketMiddle",
            "BlanketBaseBack",
            "BlanketBaseFront",
            "BlanketFrontRight",
            "BlanketFrontLeft",
            "BlanketConnectionMiddle"
    };
    private static final String[] PASTURE_BLANKET_BONE_NAMES = {
            "PBFrontLeft",
            "PBMiddle",
            "PBFrontRight",
            "PBBack"
    };
    private static final String[] GIRTH_STRAP_BONE_NAMES = {"GirthStrapMiddle", "GirthStrapBelly"};
    private static final String[] HALTER_BONE_NAMES = {
            "HalterCheeks", "HalterBridgeOfNose", "HalterSkull", "HalterMouth"
    };
    private static final String[] LEG_WRAPS_BONE_NAMES = {
            "LegWrapsSkull",
            "LegWrapsBridgeOfNose",
            "LegWrapsEarLeft",
            "LegWrapsEarRight",
            "LegWrapsMiddle",
            "LegWrapsBackRight",
            "LegWrapsBackRightHoof",
            "LegWrapsBackLeft",
            "LegWrapsBackLeftHoof",
            "LegWrapsFrontRight",
            "LegWrapsBackFrontHoof",
            "LegWrapsFrontLeft",
            "LegWrapsBackFrontLeft"
    };
    private static final String[] BREAST_COLLAR_BONE_NAMES = {
            "BreastCollarBaseBack",
            "BreastCollarMiddle",
            "BreastCollarConnectionMiddle",
            "BreastCollarBelly",
            "BreastCollarFrontRight",
            "BreastCollarFrontLeft",
            "BreastCollarBreastRight",
            "BreastCollarBreastLeft",
            "BreastCollarBase"
    };

    /**
     * Instantiates a new Swem horse render.
     *
     * @param renderManagerIn the render manager in
     */
    public SWEMHorseRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SWEMHorseModel());
    }

    @Override
    public EntityRendererManager getDispatcher() {
        return super.getDispatcher();
    }

    @Override
    public void render(
            SWEMHorseEntity entity,
            float entityYaw,
            float partialTicks,
            MatrixStack stack,
            IRenderTypeBuffer bufferIn,
            int packedLightIn) {

        List<Entity> leashHolders = entity.getLeashHolders();

        for (Entity leashHolder : leashHolders) {
            if (leashHolder != null) {
                this.renderLeash(entity, partialTicks, stack, bufferIn, leashHolder);
            }
        }

        if (!entity.isBaby()) {
            showBone("Main", entity);
            checkArmorForRendering(entity);
            checkSaddlesForRendering(entity);
            checkBridlesForRendering(entity);
            checkSaddlebagForRendering(entity);
            checkBlanketForRendering(entity);
            checkGirthStrapForRendering(entity);
            checkLegWrapsForRendering(entity);
            checkBreastCollarForRendering(entity);
            checkPastureBlanketForRendering(entity);
        }

        if (entity.isBaby()) {
            stack.pushPose();
            float scale =
                    1.0f
                            + (((ConfigHolder.SERVER.foalAgeInSeconds.get() * 20.0f - entity.getAge())
                            / (ConfigHolder.SERVER.foalAgeInSeconds.get() * 20.0f))
                            * 0.25f);
            stack.scale(scale, scale, scale);
        }
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        if (entity.isBaby()) {
            stack.popPose();
        }
        if (!entity.isBaby())
            this.getGeoModelProvider()
                    .getModel(this.getGeoModelProvider().getModelLocation(entity))
                    .getBone("main")
                    .ifPresent((bone) -> bone.setHidden(true));
    }

    private void checkPastureBlanketForRendering(SWEMHorseEntity entity) {
        ItemStack stack = entity.getSWEMArmor();
        if (stack.getItem() instanceof PastureBlanketItem) {
            Arrays.stream(PASTURE_BLANKET_BONE_NAMES).forEach((boneName) -> showBone(boneName, entity));
        } else {
            Arrays.stream(PASTURE_BLANKET_BONE_NAMES).forEach((boneName) -> hideBone(boneName, entity));
        }
    }

    private void checkBridlesForRendering(SWEMHorseEntity entity) {
        ItemStack bridleStack = entity.getHalter();
        boolean bridleRenderFlag = entity.getEntityData().get(SWEMHorseEntityBase.RENDER_BRIDLE);
        boolean shouldRenderTackFlag = !GeneralEventHandlers.no_render_tack;
        if ((bridleStack.getItem() instanceof AdventureBridleItem
                || bridleStack.getItem() instanceof WesternBridleItem)
                && bridleRenderFlag
                && shouldRenderTackFlag
        ) {
            Arrays.stream(WESTERN_BRIDLE_BONE_NAMES).forEach((n) -> this.showBone(n, entity));
            if (!entity.isBridleLeashed()) {
                Arrays.stream(WESTERN_BRIDLE_REIN_BONE_NAMES).forEach((n) -> this.showBone(n, entity));
            } else {
                Arrays.stream(WESTERN_BRIDLE_REIN_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
            }
        } else {
            Arrays.stream(WESTERN_BRIDLE_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
            Arrays.stream(WESTERN_BRIDLE_REIN_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
        }

        if (bridleStack.getItem() instanceof EnglishBridleItem
                && bridleRenderFlag
                && shouldRenderTackFlag) {
            Arrays.stream(ENGLISH_BRIDLE_BONE_NAMES).forEach((n) -> this.showBone(n, entity));
            if (!entity.isBridleLeashed()) {
                Arrays.stream(ENGLISH_BRIDLE_REIN_BONE_NAMES).forEach((n) -> this.showBone(n, entity));
            } else {
                Arrays.stream(ENGLISH_BRIDLE_REIN_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
            }
        } else {
            Arrays.stream(ENGLISH_BRIDLE_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
            Arrays.stream(ENGLISH_BRIDLE_REIN_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
        }
    }

    private void checkSaddlesForRendering(SWEMHorseEntity entity) {
        ItemStack saddleStack = entity.hasSaddle();
        boolean saddleRenderFlag = entity.getEntityData().get(SWEMHorseEntityBase.RENDER_SADDLE);
        boolean shouldRenderTackFlag = !GeneralEventHandlers.no_render_tack;
        if (saddleStack.getItem() instanceof AdventureSaddleItem
                && saddleRenderFlag
                && shouldRenderTackFlag) {
            Arrays.stream(ADVENTURE_SADDLE_BONE_NAMES).forEach((n) -> this.showBone(n, entity));
        } else {
            Arrays.stream(ADVENTURE_SADDLE_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
        }

        if (saddleStack.getItem() instanceof EnglishSaddleItem
                && saddleRenderFlag
                && shouldRenderTackFlag) {
            Arrays.stream(ENGLISH_SADDLE_BONE_NAMES).forEach((n) -> this.showBone(n, entity));
        } else {
            Arrays.stream(ENGLISH_SADDLE_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
        }

        if (saddleStack.getItem() instanceof WesternSaddleItem
                && saddleRenderFlag
                && shouldRenderTackFlag) {
            Arrays.stream(WESTERN_SADDLE_BONE_NAMES).forEach((n) -> this.showBone(n, entity));
        } else {
            Arrays.stream(WESTERN_SADDLE_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
        }
    }

    private void checkArmorForRendering(SWEMHorseEntity entity) {
        ItemStack saddleStack = entity.getSWEMArmor();
        boolean shouldRenderTackFlag = !GeneralEventHandlers.no_render_tack;
        if (saddleStack.getItem() instanceof SWEMHorseArmorItem && !(saddleStack.getItem() instanceof PastureBlanketItem) && shouldRenderTackFlag) {
            SWEMHorseArmorItem armorItem = (SWEMHorseArmorItem) saddleStack.getItem();

            if (shouldRenderArmor(entity)) {
                Arrays.stream(ARMOR_BONE_NAMES).forEach((n) -> this.showBone(n, entity));

                if (armorItem.tier.getId() >= SWEMHorseArmorItem.HorseArmorTier.DIAMOND.getId()) {
                    hideBone("Mane", entity);
                    hideBone("Bang", entity);
                } else {
                    showBone("Mane", entity);
                    showBone("Bang", entity);
                }
            } else {
                Arrays.stream(ARMOR_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
                showBone("Mane", entity);
                showBone("Bang", entity);
            }

            if (armorItem.tier == SWEMHorseArmorItem.HorseArmorTier.AMETHYST) {
                Arrays.stream(WING_BONE_NAMES).forEach((n) -> this.showBone(n, entity));
            } else {
                Arrays.stream(WING_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
            }
        } else {
            Arrays.stream(ARMOR_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
            Arrays.stream(WING_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
            showBone("Mane", entity);
            showBone("Bang", entity);
        }
    }

    private void checkSaddlebagForRendering(SWEMHorseEntity entity) {
        ItemStack stack = entity.getSaddlebag();
        boolean shouldRenderTackFlag = !GeneralEventHandlers.no_render_tack;

        if (stack.getItem() instanceof SaddlebagItem && shouldRenderTackFlag) {
            Arrays.stream(SADDLE_BAG_AND_BED_ROLL_BONE_NAMES).forEach((n) -> this.showBone(n, entity));
        } else {
            Arrays.stream(SADDLE_BAG_AND_BED_ROLL_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
        }
    }

    private void checkBlanketForRendering(SWEMHorseEntity entity) {
        ItemStack stack = entity.getBlanket();
        boolean blanketRenderFlag = entity.getEntityData().get(SWEMHorseEntityBase.RENDER_BLANKET);
        boolean shouldRenderTackFlag = !GeneralEventHandlers.no_render_tack;

        if (stack.getItem() instanceof BlanketItem && blanketRenderFlag && shouldRenderTackFlag) {
            Arrays.stream(BLANKET_BONE_NAMES).forEach((n) -> this.showBone(n, entity));
        } else {
            Arrays.stream(BLANKET_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
        }
    }

    private void checkGirthStrapForRendering(SWEMHorseEntity entity) {
        ItemStack stack = entity.getGirthStrap();
        boolean girthStrapRenderFlag =
                entity.getEntityData().get(SWEMHorseEntityBase.RENDER_GIRTH_STRAP);
        boolean shouldRenderTackFlag = !GeneralEventHandlers.no_render_tack;

        if (stack.getItem() instanceof GirthStrapItem && girthStrapRenderFlag && shouldRenderTackFlag) {
            Arrays.stream(GIRTH_STRAP_BONE_NAMES).forEach((n) -> this.showBone(n, entity));
        } else {
            Arrays.stream(GIRTH_STRAP_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
        }
    }

    private void checkLegWrapsForRendering(SWEMHorseEntity entity) {
        ItemStack stack = entity.getLegWraps();
        boolean shouldRenderTackFlag = !GeneralEventHandlers.no_render_tack;

        if (stack.getItem() instanceof LegWrapsItem && shouldRenderTackFlag) {
            Arrays.stream(LEG_WRAPS_BONE_NAMES).forEach((n) -> this.showBone(n, entity));
        } else {
            Arrays.stream(LEG_WRAPS_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
        }
    }

    private void checkBreastCollarForRendering(SWEMHorseEntity entity) {
        ItemStack stack = entity.getBreastCollar();
        boolean shouldRenderTackFlag = !GeneralEventHandlers.no_render_tack;

        if (stack.getItem() instanceof BreastCollarItem && shouldRenderTackFlag) {
            Arrays.stream(BREAST_COLLAR_BONE_NAMES).forEach((n) -> this.showBone(n, entity));
        } else {
            Arrays.stream(BREAST_COLLAR_BONE_NAMES).forEach((n) -> this.hideBone(n, entity));
        }
    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, SWEMHorseEntity currentEntity) {
        if (boneName.equalsIgnoreCase("Main")) {
            return this.getGeoModelProvider().getTextureLocation(currentEntity);
        } else if (boneName.contains("Bridle")) {
            if (currentEntity.hasBridle()) {
                BridleItem bridleItem = ((BridleItem) currentEntity.getHalter().getItem());
                return bridleItem.getModelTexture();
            }
        } else if (boneName.contains("Wings")) {
            if (currentEntity.getCoatColor() == SWEMCoatColor.SWIFT_WIND_SHE_RA) {
                return new ResourceLocation(
                        SWEM.MOD_ID, "textures/entity/horse/wings/swift_wind_she_ra.png");
            }

            return new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/wings/amethyst_wings.png");
        } else if (boneName.contains("Armor")) {
            ItemStack stack = currentEntity.getSWEMArmor();
            if (!stack.isEmpty()) {
                SWEMHorseArmorItem armorItem = (SWEMHorseArmorItem) stack.getItem();
                return armorItem.getTexture();
            }
        } else if (boneName.contains("SaddleBag") || boneName.contains("BedRoll")) {
            ItemStack stack = currentEntity.getSaddlebag();
            if (!stack.isEmpty()) {
                return ((SaddlebagItem) stack.getItem()).getArmorTexture();
            }
        } else if (boneName.contains("Halter")) {
            ItemStack stack = currentEntity.getHalter();
            if (!stack.isEmpty()) {
                return ((HalterItem) stack.getItem()).getArmorTexture();
            }
        } else if (boneName.contains("Blanket")) {
            ItemStack stack = currentEntity.getBlanket();
            if (!stack.isEmpty()) {
                return ((BlanketItem) stack.getItem()).getArmorTexture();
            }
        } else if (boneName.contains("GirthStrap")) {
            ItemStack stack = currentEntity.getGirthStrap();
            if (!stack.isEmpty()) {
                return ((GirthStrapItem) stack.getItem()).getArmorTexture();
            }
        } else if (boneName.contains("LegWraps")) {
            ItemStack stack = currentEntity.getLegWraps();
            if (!stack.isEmpty()) {
                return ((LegWrapsItem) stack.getItem()).getArmorTexture();
            }
        } else if (boneName.contains("BreastCollar")) {
            ItemStack stack = currentEntity.getBreastCollar();
            if (!stack.isEmpty()) {
                return ((BreastCollarItem) stack.getItem()).getArmorTexture();
            }
        } else if (boneName.contains("Saddle")) {
            ItemStack stack = currentEntity.hasSaddle();
            if (!stack.isEmpty()) {
                HorseSaddleItem saddleItem = ((HorseSaddleItem) currentEntity.hasSaddle().getItem());
                return saddleItem.getTexture();
            }
        } else if (boneName.contains("PB")) {
            ItemStack stack = currentEntity.getSWEMArmor();
            if (!stack.isEmpty()) {
                return ((PastureBlanketItem) stack.getItem()).getTexture();
            }
        }
        return this.getGeoModelProvider().getTextureLocation(currentEntity);
    }

    /**
     * Hides the bone if it's present.
     *
     * @param boneName the bone to hide
     * @param entity   The entity to get the model from
     */
    public void hideBone(String boneName, SWEMHorseEntity entity) {
        this.getGeoModelProvider()
                .getModel(this.getGeoModelProvider().getModelLocation(entity))
                .getBone(boneName)
                .ifPresent((bone) -> bone.setHidden(true));
    }

    /**
     * Shows the bone if it's present.
     *
     * @param boneName the bone to hide
     * @param entity   The entity to get the model from
     */
    public void showBone(String boneName, SWEMHorseEntity entity) {
        this.getGeoModelProvider()
                .getModel(this.getGeoModelProvider().getModelLocation(entity))
                .getBone(boneName)
                .ifPresent((bone) -> bone.setHidden(false));
    }

    private boolean shouldRenderArmor(SWEMHorseEntity entity) {
        if (entity.getCoatColor() == SWEMCoatColor.SWIFT_WIND_SHE_RA) {
            return false;
        }

        return true;
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        if (Arrays.stream(WING_BONE_NAMES).anyMatch((name) -> name.equalsIgnoreCase(bone.getName()))) {
            alpha = ConfigHolder.CLIENT.wingsTransparency.get() * 0.5f;
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public RenderType getRenderType(
            SWEMHorseEntity animatable,
            float partialTicks,
            MatrixStack stack,
            @Nullable IRenderTypeBuffer renderTypeBuffer,
            @Nullable IVertexBuilder vertexBuilder,
            int packedLightIn,
            ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(textureLocation);
    }

    /**
     * Render leash.
     *
     * @param entityLivingIn the entity living in
     * @param partialTicks   the partial ticks
     * @param matrixStackIn  the matrix stack in
     * @param bufferIn       the buffer in
     * @param leashHolder    the leash holder
     */
    public void renderLeash(
            SWEMHorseEntity entityLivingIn,
            float partialTicks,
            MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn,
            Entity leashHolder) {
        matrixStackIn.pushPose();
        Vector3d vector3d = leashHolder.getRopeHoldPosition(partialTicks);
        vector3d = vector3d.add(this.addRopeHoldPositionOffset(leashHolder));
        double d0 =
                (double)
                        (MathHelper.lerp(partialTicks, entityLivingIn.yBodyRot, entityLivingIn.yBodyRotO)
                                * ((float) Math.PI / 180F))
                        + (Math.PI / 2D);
        Vector3d vector3d1 = entityLivingIn.getLeashOffset();
        double d1 = Math.cos(d0) * vector3d1.z + Math.sin(d0) * vector3d1.x;
        double d2 = Math.sin(d0) * vector3d1.z - Math.cos(d0) * vector3d1.x;
        double d3 =
                MathHelper.lerp((double) partialTicks, entityLivingIn.xo, entityLivingIn.getX()) + d1;
        double d4 =
                MathHelper.lerp((double) partialTicks, entityLivingIn.yo, entityLivingIn.getY())
                        + vector3d1.y;
        double d5 =
                MathHelper.lerp((double) partialTicks, entityLivingIn.zo, entityLivingIn.getZ()) + d2;
        matrixStackIn.translate(d1, vector3d1.y, d2);
        float f = (float) (vector3d.x - d3);
        float f1 = (float) (vector3d.y - d4);
        float f2 = (float) (vector3d.z - d5);
        float f3 = 0.025F;
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.leash());
        Matrix4f matrix4f = matrixStackIn.last().pose();
        float f4 = MathHelper.fastInvSqrt(f * f + f2 * f2) * 0.025F / 2.0F;
        float f5 = f2 * f4;
        float f6 = f * f4;
        BlockPos blockpos = new BlockPos(entityLivingIn.getEyePosition(partialTicks));
        BlockPos blockpos1 = new BlockPos(leashHolder.getEyePosition(partialTicks));
        int i = this.getBlockLightLevel(entityLivingIn, blockpos);
        int j = entityLivingIn.level.getBrightness(LightType.BLOCK, blockpos1);
        int k = entityLivingIn.level.getBrightness(LightType.SKY, blockpos);
        int l = entityLivingIn.level.getBrightness(LightType.SKY, blockpos1);

        // The actually line being rendered, it's colour is decided by the f, f1, f2 inside
        // MobRenderer#addVertexPair
        MobRenderer.renderSide(ivertexbuilder, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6);
        MobRenderer.renderSide(ivertexbuilder, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6);
        matrixStackIn.popPose();
    }

    private Vector3d addRopeHoldPositionOffset(Entity leashHolder) {
        BlockState state = leashHolder.level.getBlockState(leashHolder.blockPosition());
        if (state.getBlock() instanceof LeadAnchorBlock) {
            if (state.getValue(LeadAnchorBlock.FACE) == AttachFace.FLOOR) {
                return new Vector3d(0, -0.35, 0);
            } else if (state.getValue(LeadAnchorBlock.FACE) == AttachFace.CEILING) {
                return new Vector3d(0, -0.1, 0);
            } else {
                if (state.getValue(LeadAnchorBlock.FACING) == Direction.SOUTH) {
                    return new Vector3d(0, -0.4, -0.4);
                } else if (state.getValue(LeadAnchorBlock.FACING) == Direction.NORTH) {
                    return new Vector3d(0, -0.4, 0.4);
                } else if (state.getValue(LeadAnchorBlock.FACING) == Direction.EAST) {
                    return new Vector3d(-0.4, -0.4, 0);
                } else if (state.getValue(LeadAnchorBlock.FACING) == Direction.WEST) {
                    return new Vector3d(0.4, -0.4, 0);
                }
            }
        }

        return new Vector3d(0, 0, 0);
    }

    @Override
    protected boolean isArmorBone(GeoBone bone) {
        return false;
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, SWEMHorseEntity currentEntity) {
        return null;
    }

    @Override
    protected ItemCameraTransforms.TransformType getCameraTransformForItemAtBone(
            ItemStack boneItem, String boneName) {
        return null;
    }

    @Nullable
    @Override
    protected BlockState getHeldBlockForBone(String boneName, SWEMHorseEntity currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(
            MatrixStack matrixStack,
            ItemStack item,
            String boneName,
            SWEMHorseEntity currentEntity,
            IBone bone) {
    }

    @Override
    protected void preRenderBlock(MatrixStack matrixStack, BlockState block, String boneName, SWEMHorseEntity currentEntity) {

    }

    @Override
    protected void postRenderItem(
            MatrixStack matrixStack,
            ItemStack item,
            String boneName,
            SWEMHorseEntity currentEntity,
            IBone bone) {
    }

    @Override
    protected void postRenderBlock(MatrixStack matrixStack, BlockState block, String boneName, SWEMHorseEntity currentEntity) {

    }
}
