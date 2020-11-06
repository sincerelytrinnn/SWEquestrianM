package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.entities.WormieBoiEntity;
import com.alaharranhonor.swem.entity.model.WormieBoiModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib.renderers.geo.GeoEntityRenderer;

public class WormieBoiRender extends GeoEntityRenderer<WormieBoiEntity> {

	public WormieBoiRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new WormieBoiModel());
	}

	@Override
	public ResourceLocation getEntityTexture(WormieBoiEntity entity) {
		return super.getEntityTexture(entity);
	}

	@Override
	public EntityRendererManager getRenderManager() {
		return super.getRenderManager();
	}


}
