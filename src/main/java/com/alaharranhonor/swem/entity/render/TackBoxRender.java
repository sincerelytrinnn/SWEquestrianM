package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.entity.model.TackBoxModel;
import com.alaharranhonor.swem.tileentity.TackBoxTE;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class TackBoxRender extends GeoBlockRenderer<TackBoxTE> {
	public TackBoxRender(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn, new TackBoxModel());
	}


}
