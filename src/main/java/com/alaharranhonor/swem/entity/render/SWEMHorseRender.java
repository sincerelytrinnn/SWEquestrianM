package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entity.layers.*;
import com.alaharranhonor.swem.entity.model.SWEMHorseModel;
import com.alaharranhonor.swem.items.HorseSaddleItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class SWEMHorseRender extends MobRenderer<SWEMHorseEntity, SWEMHorseModel> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(SWEM.MOD_ID, "textures/entity/swem_horse.png");

    public SWEMHorseRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SWEMHorseModel(), 1.5f);
        this.addLayer(new BlanketLayer(this));
        this.addLayer(new GirthStrapLayer(this));
        this.addLayer(new BreastCollarLayer(this));
        this.addLayer(new BridleLayer(this));
        this.addLayer(new LegWrapsLayer(this));
        this.addLayer(new WesternSaddleLayer(this));
        this.addLayer(new EnglishSaddleLayer(this));
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
