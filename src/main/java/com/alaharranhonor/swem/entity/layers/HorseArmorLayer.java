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
		if (!stack.isEmpty()) {

			SWEMHorseArmorItem armorItem = (SWEMHorseArmorItem)stack.getItem();
			this.entityRenderer.render(getEntityModel().getModel(new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/swem_horse.geo.json")),
					entitylivingbaseIn,
					partialTicks,
					RenderType.entityCutout(armorItem.getTexture()),
					matrixStackIn,
					bufferIn,
					bufferIn.getBuffer(RenderType.entityCutout(armorItem.getTexture())),
					packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1
			);

			if (armorItem.tier == SWEMHorseArmorItem.HorseArmorTier.AMETHYST && entitylivingbaseIn.isFlying()) {

				// Check the Client settings for if they want to render the wings or not.

				this.entityRenderer.render(getEntityModel().getModel(new ResourceLocation(SWEM.MOD_ID, "geo/entity/horse/swem_horse.geo.json")),
						entitylivingbaseIn,
						partialTicks,
						RenderType.entityTranslucent(new ResourceLocation(SWEM.MOD_ID, "textures/finished/amethyst_wings.png")),
						matrixStackIn,
						bufferIn,
						bufferIn.getBuffer(RenderType.entityTranslucent(new ResourceLocation(SWEM.MOD_ID, "textures/finished/amethyst_wings.png"))),
						packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, ((float) ConfigHolder.CLIENT.wingsTransparency.get()) * 0.5f);
			}



		}
	}
}
