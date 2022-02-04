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
import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entity.coats.SWEMCoatColors;
import com.alaharranhonor.swem.items.SWEMHorseArmorItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import java.util.Iterator;

public class HorseArmorLayer extends GeoLayerRenderer<SWEMHorseEntity> {
	private final IGeoRenderer<SWEMHorseEntity> entityRenderer;

	public HorseArmorLayer(IGeoRenderer<SWEMHorseEntity> entityRendererIn) {
		super(entityRendererIn);
		this.entityRenderer = entityRendererIn;
	}



	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SWEMHorseEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.getSWEMArmor();
		if (!stack.isEmpty() && stack.getItem() instanceof SWEMHorseArmorItem) {
			GeoModel horseModel = getEntityModel().getModel(new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/swem_horse.geo.json"));
			// Hide unneeded bones for performance improvement.
			horseModel.getBone("amethyst_armor").get().setHidden(false);
			horseModel.getBone("diamond_armor").get().setHidden(false);
			horseModel.getBone("gold_armor").get().setHidden(false);
			horseModel.getBone("iron_armor").get().setHidden(false);
			horseModel.getBone("cloth_armor").get().setHidden(false);

			SWEMHorseArmorItem armorItem = (SWEMHorseArmorItem)stack.getItem();
			if (shouldRenderArmour(entitylivingbaseIn)) {
				this.entityRenderer.render(horseModel,
					entitylivingbaseIn,
					partialTicks,
					RenderType.entityCutout(armorItem.getTexture()),
					matrixStackIn,
					bufferIn,
					bufferIn.getBuffer(RenderType.entityCutout(armorItem.getTexture())),
					packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1
				);
			}


			if (armorItem.tier == SWEMHorseArmorItem.HorseArmorTier.AMETHYST ) {

				// Check the Client settings for if they want to render the wings or not.
				horseModel.getBone("Scapular").get().setHidden(false);
				horseModel.getBone("Scapular2").get().setHidden(false);

				ResourceLocation wingTexture = getWingTexture(entitylivingbaseIn);
				this.entityRenderer.render(horseModel,
						entitylivingbaseIn,
						partialTicks,
						RenderType.entityTranslucent(wingTexture),
						matrixStackIn,
						bufferIn,
						bufferIn.getBuffer(RenderType.entityTranslucent(wingTexture)),
						packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, ((float) ConfigHolder.CLIENT.wingsTransparency.get()) * 0.5f);
			}

			horseModel.getBone("amethyst_armor").get().setHidden(true);
			horseModel.getBone("diamond_armor").get().setHidden(true);
			horseModel.getBone("gold_armor").get().setHidden(true);
			horseModel.getBone("iron_armor").get().setHidden(true);
			horseModel.getBone("cloth_armor").get().setHidden(true);
			horseModel.getBone("Scapular").get().setHidden(true);
			horseModel.getBone("Scapular2").get().setHidden(true);



		}
	}

	public boolean shouldRenderArmour(SWEMHorseEntity horse) {
		if (horse.getCoatColor() == SWEMCoatColors.SWIFT_WIND_SHE_RA) {
			return false;
		}

		return true;
	}

	public ResourceLocation getWingTexture(SWEMHorseEntity horse) {
		if (horse.getCoatColor() == SWEMCoatColors.SWIFT_WIND_SHE_RA) {
			return new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/wings/swift_wind_she_ra.png");
		}


		return new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/wings/amethyst_wings.png");
	}
}
