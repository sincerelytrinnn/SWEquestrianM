package com.alaharranhonor.swem.entity.render;


/*
 * All Rights Reserved
 *
 * Copyright (c) 2021, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.alaharranhonor.swem.blocks.BridleRackBlock;
import com.alaharranhonor.swem.entity.model.*;
import com.alaharranhonor.swem.items.tack.*;
import com.alaharranhonor.swem.tileentity.BridleRackTE;
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
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import java.awt.*;
import java.util.Iterator;

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

		stack.pushPose();

		Direction direction = tile.getBlockState().getValue(BridleRackBlock.FACING);

		stack.translate(0, 0, 0);

		stack.translate(0.5d, 0, 0.5d);

		stack.mulPose(new Quaternion(0, 180 - direction.toYRot(), 0, true));

		if (itemStack.getItem() instanceof WesternBridleItem || itemStack.getItem() instanceof AdventureBridleItem) {
			BridleItem item = (BridleItem) itemStack.getItem();
			BridleRackWesternModel model = new BridleRackWesternModel();

			IVertexBuilder builder = bufferIn.getBuffer(RenderType.entityCutout(item.getBridleRackTexture()));
			Color renderColor = this.getRenderColor(tile, partialTicks, stack, bufferIn, builder, packedLightIn);
			Iterator group = model.getModel(model.getModelLocation(null)).topLevelBones.iterator();
			while (group.hasNext()) {
				GeoBone bone = (GeoBone) group.next();
				this.renderRecursively(bone, stack, builder, packedLightIn, OverlayTexture.NO_OVERLAY, (float)renderColor.getRed() / 255.0F, (float)renderColor.getGreen() / 255.0F, (float)renderColor.getBlue() / 255.0F, (float)renderColor.getAlpha() / 255.0F);
			}
			stack.popPose();
			return;
		} else if (itemStack.getItem() instanceof EnglishBridleItem) {
			EnglishBridleItem item = (EnglishBridleItem) itemStack.getItem();
			BridleRackEnglishModel model = new BridleRackEnglishModel();

			IVertexBuilder builder = bufferIn.getBuffer(RenderType.entityCutout(item.getBridleRackTexture()));
			Color renderColor = this.getRenderColor(tile, partialTicks, stack, bufferIn, builder, packedLightIn);
			Iterator group = model.getModel(model.getModelLocation(null)).topLevelBones.iterator();
			while (group.hasNext()) {
				GeoBone bone = (GeoBone) group.next();
				this.renderRecursively(bone, stack, builder, packedLightIn, OverlayTexture.NO_OVERLAY, (float)renderColor.getRed() / 255.0F, (float)renderColor.getGreen() / 255.0F, (float)renderColor.getBlue() / 255.0F, (float)renderColor.getAlpha() / 255.0F);
			}
			stack.popPose();
			return;
		} else if (itemStack.getItem() instanceof HalterItem) {
			HalterItem item = (HalterItem) itemStack.getItem();
			BridleRackHalterModel model = new BridleRackHalterModel();

			IVertexBuilder builder = bufferIn.getBuffer(RenderType.entityCutout(item.getBridleRackTexture()));
			Color renderColor = this.getRenderColor(tile, partialTicks, stack, bufferIn, builder, packedLightIn);
			Iterator group = model.getModel(model.getModelLocation(null)).topLevelBones.iterator();
			while (group.hasNext()) {
				GeoBone bone = (GeoBone) group.next();
				this.renderRecursively(bone, stack, builder, packedLightIn, OverlayTexture.NO_OVERLAY, (float)renderColor.getRed() / 255.0F, (float)renderColor.getGreen() / 255.0F, (float)renderColor.getBlue() / 255.0F, (float)renderColor.getAlpha() / 255.0F);
			}
			stack.popPose();
			return;
		}

		stack.popPose();


	}
}
