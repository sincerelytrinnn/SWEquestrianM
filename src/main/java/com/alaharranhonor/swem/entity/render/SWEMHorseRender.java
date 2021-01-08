package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entity.coats.SWEMCoatColors;
import com.alaharranhonor.swem.entity.layers.*;
import com.alaharranhonor.swem.entity.model.SWEMHorseModel;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;
import java.util.Iterator;
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
}
