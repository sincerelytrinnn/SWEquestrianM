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
import com.alaharranhonor.swem.client.layers.*;
import com.alaharranhonor.swem.client.model.SWEMHorseModel;
import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.LightType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SWEMHorseRender extends GeoEntityRenderer<SWEMHorseEntity> {

  protected static final ResourceLocation TEXTURE =
      new ResourceLocation(SWEM.MOD_ID, "textures/entity/swem_horse.png");
  private static int LAST_ARMOR_TIER = -1;

  /**
   * Instantiates a new Swem horse render.
   *
   * @param renderManagerIn the render manager in
   */
  public SWEMHorseRender(EntityRendererManager renderManagerIn) {
    super(renderManagerIn, new SWEMHorseModel());
    this.addLayer(new HorseArmorLayer(this));
    this.addLayer(new SaddlebagLayer(this));
    this.addLayer(new BlanketLayer(this));
    this.addLayer(new GirthStrapLayer(this));
    this.addLayer(new BreastCollarLayer(this));
    this.addLayer(new LegWrapsLayer(this));
    this.addLayer(new HalterLayer(this));
    this.addLayer(new EnglishBridleLayer(this));
    this.addLayer(new WesternBridleLayer(this));
    this.addLayer(new AdventureBridleLayer(this));
    this.addLayer(new WesternSaddleLayer(this));
    this.addLayer(new EnglishSaddleLayer(this));
    this.addLayer(new AdventureSaddleLayer(this));
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
      this.getGeoModelProvider()
          .getModel(this.getGeoModelProvider().getModelLocation(entity))
          .getBone("main")
          .ifPresent((bone) -> bone.setHidden(false));
      this.getGeoModelProvider()
          .getModel(this.getGeoModelProvider().getModelLocation(entity))
          .getBone("western_bridle")
          .ifPresent((bone) -> bone.setHidden(true));
      this.getGeoModelProvider()
          .getModel(this.getGeoModelProvider().getModelLocation(entity))
          .getBone("western_saddle")
          .ifPresent((bone) -> bone.setHidden(true));
      this.getGeoModelProvider()
          .getModel(this.getGeoModelProvider().getModelLocation(entity))
          .getBone("saddlebag")
          .ifPresent((bone) -> bone.setHidden(true));
      this.getGeoModelProvider()
          .getModel(this.getGeoModelProvider().getModelLocation(entity))
          .getBone("bedroll")
          .ifPresent((bone) -> bone.setHidden(true));
      this.getGeoModelProvider()
          .getModel(this.getGeoModelProvider().getModelLocation(entity))
          .getBone("englishbridle")
          .ifPresent((bone) -> bone.setHidden(true));
      this.getGeoModelProvider()
          .getModel(this.getGeoModelProvider().getModelLocation(entity))
          .getBone("english_saddle")
          .ifPresent((bone) -> bone.setHidden(true));
      this.getGeoModelProvider()
          .getModel(this.getGeoModelProvider().getModelLocation(entity))
          .getBone("adventure_saddle")
          .ifPresent((bone) -> bone.setHidden(true));
      this.getGeoModelProvider()
          .getModel(this.getGeoModelProvider().getModelLocation(entity))
          .getBone("cloth_armor")
          .ifPresent((bone) -> bone.setHidden(true));
      this.getGeoModelProvider()
          .getModel(this.getGeoModelProvider().getModelLocation(entity))
          .getBone("iron_armor")
          .ifPresent((bone) -> bone.setHidden(true));
      this.getGeoModelProvider()
          .getModel(this.getGeoModelProvider().getModelLocation(entity))
          .getBone("gold_armor")
          .ifPresent((bone) -> bone.setHidden(true));
      this.getGeoModelProvider()
          .getModel(this.getGeoModelProvider().getModelLocation(entity))
          .getBone("diamond_armor")
          .ifPresent((bone) -> bone.setHidden(true));
      this.getGeoModelProvider()
          .getModel(this.getGeoModelProvider().getModelLocation(entity))
          .getBone("amethyst_armor")
          .ifPresent((bone) -> bone.setHidden(true));
      this.getGeoModelProvider()
          .getModel(this.getGeoModelProvider().getModelLocation(entity))
          .getBone("Scapular")
          .ifPresent((bone) -> bone.setHidden(true));
      this.getGeoModelProvider()
          .getModel(this.getGeoModelProvider().getModelLocation(entity))
          .getBone("Scapular2")
          .ifPresent((bone) -> bone.setHidden(true));
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

  @Override
  public void render(
      GeoModel model,
      SWEMHorseEntity animatable,
      float partialTicks,
      RenderType type,
      MatrixStack matrixStackIn,
      @Nullable IRenderTypeBuffer renderTypeBuffer,
      @Nullable IVertexBuilder vertexBuilder,
      int packedLightIn,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float alpha) {
    /*ItemStack armor = animatable.getSWEMArmor();
    if (animatable.isSWEMArmor(armor)) {
        SWEMHorseArmorItem armorItem = (SWEMHorseArmorItem) armor.getItem();
        if (armorItem.tier.getId() != LAST_ARMOR_TIER) {
            LAST_ARMOR_TIER = armorItem.tier.getId();

            if (armorItem.tier.getId() > 0) {
                model.getBone("headarmor2").ifPresent((bone) -> bone.setHidden(true.s
                model.getBone("ironleftshoulder").ifPresent((bone) -> bone.setHidden(true.s
                model.getBone("ironrightshoulder").ifPresent((bone) -> bone.setHidden(true.s
                model.getBone("ironbutt").ifPresent((bone) -> bone.setHidden(true.s
            } else {
                model.getBone("headarmor2").ifPresent((bone) -> bone.setHidden(true.s
                model.getBone("ironleftshoulder").ifPresent((bone) -> bone.setHidden(true.s
                model.getBone("ironrightshoulder").ifPresent((bone) -> bone.setHidden(true.s
                model.getBone("ironbutt").ifPresent((bone) -> bone.setHidden(true.s
            }

            // No gold bones.

            if (armorItem.tier.getId() > 2) {
                // Show Diamond armor bones.
                model.getBone("neckarmor").ifPresent((bone) -> bone.setHidden(true.s
                model.getBone("diamondbutt").ifPresent((bone) -> bone.setHidden(true.s
            } else {
                model.getBone("neckarmor").ifPresent((bone) -> bone.setHidden(true.s
                model.getBone("diamondbutt").ifPresent((bone) -> bone.setHidden(true.s
            }

            if (armorItem.tier.getId() > 3) {
                // Show Amethyst armor bones.
                model.getBone("frontrightshoulder").ifPresent((bone) -> bone.setHidden(true.s
                model.getBone("leftrightshoulder").ifPresent((bone) -> bone.setHidden(true.s
            } else {
                model.getBone("frontrightshoulder").ifPresent((bone) -> bone.setHidden(true.s
                model.getBone("leftrightshoulder").ifPresent((bone) -> bone.setHidden(true.s
            }
        }
    }*/
    super.render(
        model,
        animatable,
        partialTicks,
        type,
        matrixStackIn,
        renderTypeBuffer,
        vertexBuilder,
        packedLightIn,
        packedOverlayIn,
        red,
        green,
        blue,
        alpha);
  }

  /**
   * Render leash.
   *
   * @param entityLivingIn the entity living in
   * @param partialTicks the partial ticks
   * @param matrixStackIn the matrix stack in
   * @param bufferIn the buffer in
   * @param leashHolder the leash holder
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
}
