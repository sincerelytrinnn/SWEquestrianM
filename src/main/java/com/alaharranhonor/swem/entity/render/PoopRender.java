package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.PoopEntity;
import com.alaharranhonor.swem.entity.model.PoopModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PoopRender extends GeoEntityRenderer<PoopEntity> {
	public PoopRender(EntityRendererManager renderManager) {
		super(renderManager, new PoopModel());
	}

	@Override
	public ResourceLocation getEntityTexture(PoopEntity entity) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse_poop.png");
	}


}
