package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entity.coats.SWEMCoatColors;
import com.alaharranhonor.swem.entity.layers.*;
import com.alaharranhonor.swem.entity.model.SWEMHorseModel;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LeashKnotRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.LightType;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class SWEMHorseRender extends GeoEntityRenderer<SWEMHorseEntity> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(SWEM.MOD_ID, "textures/entity/swem_horse.png");
    private static final Map<SWEMCoatColors, ResourceLocation> VARIANTS = Util.make(Maps.newEnumMap(SWEMCoatColors.class), (iter) -> {
        iter.put(SWEMCoatColors.WHITE, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/white_coat.png"));
        iter.put(SWEMCoatColors.GRAY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/gray_coat.png"));
        iter.put(SWEMCoatColors.BLACK, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/black_coat.png"));
        iter.put(SWEMCoatColors.DARKBROWN, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/dark_brown_coat.png"));
        iter.put(SWEMCoatColors.BROWN, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/brown_coat.png"));
        iter.put(SWEMCoatColors.REDROAN, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/red_roan_coat.png"));
        iter.put(SWEMCoatColors.BUCKSKIN, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/buckskin_coat.png"));
        iter.put(SWEMCoatColors.PAINT, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/paint_coat.png"));
        iter.put(SWEMCoatColors.PALOMINO, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/palomino_coat.png"));
        iter.put(SWEMCoatColors.NOBUCKLE, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/nobuckle_coat.png"));
        iter.put(SWEMCoatColors.WILDANDFREE, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/wild_and_free_coat.png"));
        iter.put(SWEMCoatColors.TALLDARKHANDSOME, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/tall_dark_handsome_coat.png"));
        iter.put(SWEMCoatColors.SWEETBOY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/sweet_boy_coat.png"));
        iter.put(SWEMCoatColors.APPY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/appy_coat.png"));
        iter.put(SWEMCoatColors.GOLDEN, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/golden_coat.png"));
        iter.put(SWEMCoatColors.LEOPARD, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/leopard_coat.png"));
        iter.put(SWEMCoatColors.GALAXY, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/galaxy_coat.png"));
        iter.put(SWEMCoatColors.RAINBOW, new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/coats/rainbow_coat.png"));
    });

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
        this.addLayer(new LegWrapsLayer(this));
        this.addLayer(new WesternSaddleLayer(this));
        this.addLayer(new EnglishSaddleLayer(this));
        this.addLayer(new AdventureSaddleLayer(this));

    }

    @Override
    public EntityRendererManager getRenderManager() {
        return super.getRenderManager();
    }


    @Override
    public ResourceLocation getEntityTexture(SWEMHorseEntity entity) {
        return VARIANTS.get(entity.getCoatColor());
    }

    @Override
    public void render(SWEMHorseEntity entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        Entity leashHolder = entity.getLeashHolder();
        if (leashHolder != null) {
            this.renderLeash(entity, partialTicks, stack, bufferIn, leashHolder);
        }
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);

    }

    public void renderLeash(SWEMHorseEntity entityLivingIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Entity leashHolder) {
        matrixStackIn.push();
        Vector3d vector3d = leashHolder.getLeashPosition(partialTicks);
        double d0 = (double)(MathHelper.lerp(partialTicks, entityLivingIn.renderYawOffset, entityLivingIn.prevRenderYawOffset) * ((float)Math.PI / 180F)) + (Math.PI / 2D);
        Vector3d vector3d1 = entityLivingIn.getLeashStartPosition();
        double d1 = Math.cos(d0) * vector3d1.z + Math.sin(d0) * vector3d1.x;
        double d2 = Math.sin(d0) * vector3d1.z - Math.cos(d0) * vector3d1.x;
        double d3 = MathHelper.lerp((double)partialTicks, entityLivingIn.prevPosX, entityLivingIn.getPosX()) + d1;
        double d4 = MathHelper.lerp((double)partialTicks, entityLivingIn.prevPosY, entityLivingIn.getPosY()) + vector3d1.y;
        double d5 = MathHelper.lerp((double)partialTicks, entityLivingIn.prevPosZ, entityLivingIn.getPosZ()) + d2;
        matrixStackIn.translate(d1, vector3d1.y, d2);
        float f = (float)(vector3d.x - d3);
        float f1 = (float)(vector3d.y - d4);
        float f2 = (float)(vector3d.z - d5);
        float f3 = 0.025F;
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getLeash());
        Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
        float f4 = MathHelper.fastInvSqrt(f * f + f2 * f2) * 0.025F / 2.0F;
        float f5 = f2 * f4;
        float f6 = f * f4;
        BlockPos blockpos = new BlockPos(entityLivingIn.getEyePosition(partialTicks));
        BlockPos blockpos1 = new BlockPos(leashHolder.getEyePosition(partialTicks));
        int i = this.getBlockLight(entityLivingIn, blockpos);
        int j = entityLivingIn.world.getLightFor(LightType.BLOCK, blockpos1);
        int k = entityLivingIn.world.getLightFor(LightType.SKY, blockpos);
        int l = entityLivingIn.world.getLightFor(LightType.SKY, blockpos1);

        // The actually line being rendered, it's colour is decided by the f, f1, f2 inside MobRenderer#addVertexPair
        MobRenderer.renderSide(ivertexbuilder, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6);
        MobRenderer.renderSide(ivertexbuilder, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6);
        matrixStackIn.pop();
    }

}
