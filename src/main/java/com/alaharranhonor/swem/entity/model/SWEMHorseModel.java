
package com.alaharranhonor.swem.entity.model;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SWEMHorseModel extends AnimatedGeoModel<SWEMHorseEntity> {


    @Override
    public ResourceLocation getModelLocation(SWEMHorseEntity swemHorseEntity) {
        return new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/swem_horse.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SWEMHorseEntity swemHorseEntity) {
        return new ResourceLocation(SWEM.MOD_ID, "textures/entity/swem_horse.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SWEMHorseEntity swemHorseEntity) {
        return new ResourceLocation(SWEM.MOD_ID, "animations/swem_horse.json");
    }
}