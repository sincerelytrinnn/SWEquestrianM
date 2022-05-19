package com.alaharranhonor.swem.client.layers;


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

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.items.tack.BridleItem;
import com.alaharranhonor.swem.items.tack.WesternBridleItem;
import com.alaharranhonor.swem.util.GeneralEventHandlers;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class WesternBridleLayer extends GeoLayerRenderer<SWEMHorseEntity> {

	private IGeoRenderer entityRenderer;

	/**
	 * Instantiates a new Western bridle layer.
	 *
	 * @param entityRendererIn the entity renderer in
	 */
	public WesternBridleLayer(IGeoRenderer<SWEMHorseEntity> entityRendererIn) {
		super(entityRendererIn);
		this.entityRenderer = entityRendererIn;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SWEMHorseEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.getHalter();
		if (!stack.isEmpty()) {
			if (shouldRender(stack, entitylivingbaseIn)) {

				GeoModel horseModel = getEntityModel().getModel(new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/swem_horse.geo.json"));
				// Hide unneeded bones for performance improvement.
				//GeoBone main = horseModel.getBone("main").get();
				horseModel.getBone("main").get().setHidden(false);
				horseModel.getBone("western_bridle").get().setHidden(false);


				BridleItem bridleItem = (BridleItem)stack.getItem();
				if (!entitylivingbaseIn.isBridleLeashed())
					this.entityRenderer.render(horseModel,
							entitylivingbaseIn,
							partialTicks,
							RenderType.entityCutoutNoCull(bridleItem.getModelTexture()),
							matrixStackIn,
							bufferIn,
							bufferIn.getBuffer(RenderType.entityCutoutNoCull(bridleItem.getModelTexture())),
							packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1
					);

				this.entityRenderer.render(horseModel,
						entitylivingbaseIn,
						partialTicks,
						RenderType.entityCutout(bridleItem.getArmorTexture()),
						matrixStackIn,
						bufferIn,
						bufferIn.getBuffer(RenderType.entityCutout(bridleItem.getArmorTexture())),
						packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1
				);

				horseModel.getBone("main").get().setHidden(true);
				horseModel.getBone("western_bridle").get().setHidden(true);

			}

		}

	}

	/**
	 * Should render boolean.
	 *
	 * @param stack  the stack
	 * @param entity the entity
	 * @return the boolean
	 */
	public boolean shouldRender(ItemStack stack, SWEMHorseEntity entity) {

		return stack.getItem() instanceof WesternBridleItem && entity.getEntityData().get(SWEMHorseEntityBase.RENDER_BRIDLE) && !GeneralEventHandlers.no_render_tack;
	}
}
