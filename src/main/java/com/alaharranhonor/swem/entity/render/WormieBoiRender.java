package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.WormieBoiEntity;
import com.alaharranhonor.swem.entity.model.WormieBoiModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WormieBoiRender extends GeoEntityRenderer<WormieBoiEntity> {

	public WormieBoiRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new WormieBoiModel());
	}

	@Override
	public ResourceLocation getEntityTexture(WormieBoiEntity entity) {
		return new ResourceLocation(SWEM.MOD_ID, "textures/entity/wormieboi.png");
	}

	@Override
	public EntityRendererManager getRenderManager() {
		return super.getRenderManager();
	}


}
