package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.entity.model.OneSaddleRackModel;
import com.alaharranhonor.swem.tileentity.OneSaddleRackTE;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class OneSaddleRackRender extends GeoBlockRenderer<OneSaddleRackTE> {


	public OneSaddleRackRender(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn, new OneSaddleRackModel());
	}

	@Override
	public void render(OneSaddleRackTE tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(tile, partialTicks, stack, bufferIn, packedLightIn);
	}
}
