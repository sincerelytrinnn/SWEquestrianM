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
import com.alaharranhonor.swem.items.tack.HalterItem;
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

public class HalterLayer extends GeoLayerRenderer<SWEMHorseEntity> {

	private IGeoRenderer<SWEMHorseEntity> entityRenderer;

	/**
	 * Instantiates a new Halter layer.
	 *
	 * @param entityRendererIn the entity renderer in
	 */
	public HalterLayer(IGeoRenderer<SWEMHorseEntity> entityRendererIn) {
		super(entityRendererIn);
		this.entityRenderer = entityRendererIn;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SWEMHorseEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entity.getHalter();

		if (!stack.isEmpty() && stack.getItem() instanceof HalterItem && entity.getEntityData().get(SWEMHorseEntityBase.RENDER_BRIDLE) && !GeneralEventHandlers.no_render_tack) {

			GeoModel horseModel = getEntityModel().getModel(new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/swem_horse.geo.json"));
			// Hide unneeded bones for performance improvement.
			horseModel.getBone("main").get().setHidden(false);

			HalterItem halter = (HalterItem)stack.getItem();
			this.entityRenderer.render(horseModel,
					entity,
					partialTicks,
					RenderType.entityCutout(halter.getArmorTexture()),
					matrixStackIn,
					bufferIn,
					bufferIn.getBuffer(RenderType.entityCutout(halter.getArmorTexture())),
					packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1
			);

			horseModel.getBone("main").get().setHidden(true);

		}
	}
}
