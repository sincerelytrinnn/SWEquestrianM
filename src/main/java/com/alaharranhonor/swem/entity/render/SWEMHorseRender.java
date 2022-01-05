package com.alaharranhonor.swem.entity.render;


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
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entity.coats.SWEMCoatColors;
import com.alaharranhonor.swem.entity.layers.*;
import com.alaharranhonor.swem.entity.model.SWEMHorseModel;
import com.alaharranhonor.swem.items.SWEMHorseArmorItem;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.LightType;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class SWEMHorseRender extends GeoEntityRenderer<SWEMHorseEntity> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(SWEM.MOD_ID, "textures/entity/swem_horse.png");


    public SWEMHorseRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SWEMHorseModel());
        this.addLayer(new HorseArmorLayer(this));
        this.addLayer(new SaddlebagLayer(this));
        this.addLayer(new BlanketLayer(this));
        this.addLayer(new GirthStrapLayer(this));
        this.addLayer(new BreastCollarLayer(this));
        this.addLayer(new HalterLayer(this));
        this.addLayer(new EnglishBridleLayer(this));
        this.addLayer(new WesternBridleLayer(this));
        this.addLayer(new AdventureBridleLayer(this));
        this.addLayer(new LegWrapsLayer(this));
        this.addLayer(new WesternSaddleLayer(this));
        this.addLayer(new EnglishSaddleLayer(this));
        this.addLayer(new AdventureSaddleLayer(this));

    }

    @Override
    public EntityRendererManager getDispatcher() {
        return super.getDispatcher();
    }


    @Override
    public ResourceLocation getTextureLocation(SWEMHorseEntity entity) {
        return SWEMHorseModel.VARIANTS.get(entity.getCoatColor());
    }

    @Override
    public void render(SWEMHorseEntity entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        Entity leashHolder = entity.getLeashHolder();

        ItemStack armor = entity.getSWEMArmor();
        if (entity.isSWEMArmor(armor)) {
            SWEMHorseArmorItem armorItem = (SWEMHorseArmorItem) armor.getItem();
            if (armorItem.tier.getId() < 4) { // Hide Amethyst armor bones.
                
            }
        }

        if (leashHolder != null) {
            this.renderLeash(entity, partialTicks, stack, bufferIn, leashHolder);
        }
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);

    }

    public void renderLeash(SWEMHorseEntity entityLivingIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Entity leashHolder) {
        matrixStackIn.pushPose();
        Vector3d vector3d = leashHolder.getRopeHoldPosition(partialTicks);
        double d0 = (double)(MathHelper.lerp(partialTicks, entityLivingIn.yBodyRot, entityLivingIn.yBodyRotO) * ((float)Math.PI / 180F)) + (Math.PI / 2D);
        Vector3d vector3d1 = entityLivingIn.getLeashOffset();
        double d1 = Math.cos(d0) * vector3d1.z + Math.sin(d0) * vector3d1.x;
        double d2 = Math.sin(d0) * vector3d1.z - Math.cos(d0) * vector3d1.x;
        double d3 = MathHelper.lerp((double)partialTicks, entityLivingIn.xo, entityLivingIn.getX()) + d1;
        double d4 = MathHelper.lerp((double)partialTicks, entityLivingIn.yo, entityLivingIn.getY()) + vector3d1.y;
        double d5 = MathHelper.lerp((double)partialTicks, entityLivingIn.zo, entityLivingIn.getZ()) + d2;
        matrixStackIn.translate(d1, vector3d1.y, d2);
        float f = (float)(vector3d.x - d3);
        float f1 = (float)(vector3d.y - d4);
        float f2 = (float)(vector3d.z - d5);
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

        // The actually line being rendered, it's colour is decided by the f, f1, f2 inside MobRenderer#addVertexPair
        MobRenderer.renderSide(ivertexbuilder, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6);
        MobRenderer.renderSide(ivertexbuilder, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6);
        matrixStackIn.popPose();
    }

}
