package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.blocks.BridleRackBlock;
import com.alaharranhonor.swem.blocks.OneSaddleRack;
import com.alaharranhonor.swem.entity.model.*;
import com.alaharranhonor.swem.items.tack.*;
import com.alaharranhonor.swem.tileentity.BridleRackTE;
import com.alaharranhonor.swem.tileentity.OneSaddleRackTE;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import java.awt.*;

public class BridleRackRender extends GeoBlockRenderer<BridleRackTE> {


	public BridleRackRender(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn, new BridleRackModel());
	}

	@Override
	public void render(BridleRackTE tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(tile, partialTicks, stack, bufferIn, packedLightIn);
		ItemStack itemStack = tile.itemHandler.getStackInSlot(0);
		if (itemStack.getItem() == Items.AIR || itemStack == ItemStack.EMPTY) {
			return;
		}

		stack.push();

		Direction direction = tile.getBlockState().get(BridleRackBlock.HORIZONTAL_FACING);

		stack.translate(0, 1.5, 0);

		switch (direction) {
			case WEST:
				stack.translate(0.4375, 0, 0.5);
				break;
			case EAST:
				stack.translate(0.5625, 0, 0.5);
				break;
			case SOUTH:
				stack.translate(0.5, 0, 0.5625);
				break;
			case NORTH:
				stack.translate(0.5, 0, 0.5);
				break;
		}

		stack.rotate(new Quaternion(0, 180 - direction.getHorizontalAngle(), 180, true));

		if (itemStack.getItem() instanceof WesternBridleItem) {
			WesternBridleItem item = (WesternBridleItem) itemStack.getItem();
			BridleRackWesternModel model = new BridleRackWesternModel<>();

			IVertexBuilder builder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(item.getBridleRackTexture()));
			Color renderColor = this.getRenderColor(tile, partialTicks, stack, bufferIn, builder, packedLightIn);
			model.render(stack, builder, packedLightIn, OverlayTexture.NO_OVERLAY, (float)renderColor.getRed() / 255.0F, (float)renderColor.getGreen() / 255.0F, (float)renderColor.getBlue() / 255.0F, (float)renderColor.getAlpha() / 255.0F);
			stack.pop();
			return;
		} else if (itemStack.getItem() instanceof EnglishBridleItem) {
			EnglishBridleItem item = (EnglishBridleItem) itemStack.getItem();
			BridleRackEnglishModel model = new BridleRackEnglishModel<>();

			IVertexBuilder builder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(item.getBridleRackTexture()));
			Color renderColor = this.getRenderColor(tile, partialTicks, stack, bufferIn, builder, packedLightIn);
			model.render(stack, builder, packedLightIn, OverlayTexture.NO_OVERLAY, (float)renderColor.getRed() / 255.0F, (float)renderColor.getGreen() / 255.0F, (float)renderColor.getBlue() / 255.0F, (float)renderColor.getAlpha() / 255.0F);
			stack.pop();
			return;
		} else if (itemStack.getItem() instanceof HalterItem) {
			HalterItem item = (HalterItem) itemStack.getItem();
			BridleRackHalterModel model = new BridleRackHalterModel<>();

			IVertexBuilder builder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(item.getBridleRackTexture()));
			Color renderColor = this.getRenderColor(tile, partialTicks, stack, bufferIn, builder, packedLightIn);
			model.render(stack, builder, packedLightIn, OverlayTexture.NO_OVERLAY, (float)renderColor.getRed() / 255.0F, (float)renderColor.getGreen() / 255.0F, (float)renderColor.getBlue() / 255.0F, (float)renderColor.getAlpha() / 255.0F);
		}

		stack.pop();


	}
}
