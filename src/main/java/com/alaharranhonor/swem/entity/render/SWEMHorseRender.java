package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntity_Backup;
import com.alaharranhonor.swem.entity.model.SWEMHorseModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class SWEMHorseRender extends MobRenderer<SWEMHorseEntity, SWEMHorseModel<SWEMHorseEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(SWEM.MOD_ID, "textures/entity/swem_horse.png");

    public SWEMHorseRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SWEMHorseModel<>(), 1.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(SWEMHorseEntity entity) {
        return TEXTURE;
    }
}
