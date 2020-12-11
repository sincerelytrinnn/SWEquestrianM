package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.entities.PoopEntity;
import com.alaharranhonor.swem.entity.model.PoopModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PoopRender extends GeoEntityRenderer<PoopEntity> {
	public PoopRender(EntityRendererManager renderManager) {
		super(renderManager, new PoopModel());
	}


}
