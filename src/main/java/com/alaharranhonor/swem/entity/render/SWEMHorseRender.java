package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entity.layers.*;
import com.alaharranhonor.swem.entity.model.SWEMHorseModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SWEMHorseRender extends GeoEntityRenderer<SWEMHorseEntity>{

    protected static final ResourceLocation TEXTURE = new ResourceLocation(SWEM.MOD_ID, "textures/entity/swem_horse.png");

    public SWEMHorseRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SWEMHorseModel());
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
        return TEXTURE;
    }
}
