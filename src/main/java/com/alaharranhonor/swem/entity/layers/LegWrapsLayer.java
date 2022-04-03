package com.alaharranhonor.swem.entity.layers;


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
import com.alaharranhonor.swem.items.tack.LegWrapsItem;
import com.alaharranhonor.swem.items.tack.WesternLegWraps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class LegWrapsLayer extends GeoLayerRenderer<SWEMHorseEntity> {

	private IGeoRenderer<SWEMHorseEntity> entityRenderer;

	/**
	 * Instantiates a new Leg wraps layer.
	 *
	 * @param entityRendererIn the entity renderer in
	 */
	public LegWrapsLayer(IGeoRenderer<SWEMHorseEntity> entityRendererIn) {
		super(entityRendererIn);
		this.entityRenderer = entityRendererIn;
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SWEMHorseEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.getLegWraps();
		if (!stack.isEmpty() && stack.getItem() instanceof LegWrapsItem) {
			LegWrapsItem legWraps = (LegWrapsItem) stack.getItem();
			float f, f1, f2;
			if (stack.getItem() instanceof IDyeableArmorItem) {
				IDyeableArmorItem dyeArmor = (IDyeableArmorItem) stack.getItem();

				int i = dyeArmor.getColor(stack);

				f = (float)(i >> 16 & 255) / 255.0F;
				f1 = (float)(i >> 8 & 255) / 255.0F;
				f2 = (float)(i & 255) / 255.0F;

			}/* else {
				f = 1.0F;
				f1 = 1.0F;
				f2 = 1.0F;
			}*/
			f = 1.0F;
			f1 = 1.0F;
			f2 = 1.0F;

			GeoModel horseModel = getEntityModel().getModel(new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/swem_horse.geo.json"));
			// Hide unneeded bones for performance improvement.
			horseModel.getBone("main").get().setHidden(false);

			this.entityRenderer.render(horseModel,
					entitylivingbaseIn,
					partialTicks,
					RenderType.entityCutout(legWraps.getArmorTexture()),
					matrixStackIn,
					bufferIn,
					bufferIn.getBuffer(RenderType.entityCutout(legWraps.getArmorTexture())),
					packedLightIn, OverlayTexture.NO_OVERLAY, f, f1, f2, 1
			);

			horseModel.getBone("main").get().setHidden(true);

		}
	}
}
