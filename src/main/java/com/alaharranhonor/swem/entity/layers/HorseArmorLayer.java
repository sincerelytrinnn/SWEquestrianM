package com.alaharranhonor.swem.entity.layers;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entity.model.HorseArmorModelGeo;
import com.alaharranhonor.swem.items.SWEMHorseArmorItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import java.util.Iterator;

public class HorseArmorLayer extends GeoLayerRenderer<SWEMHorseEntity> {
	private final HorseArmorModelGeo geoModel = new HorseArmorModelGeo();
	private final IGeoRenderer<SWEMHorseEntity> entityRenderer;

	public HorseArmorLayer(IGeoRenderer<SWEMHorseEntity> entityRendererIn) {
		super(entityRendererIn);
		this.entityRenderer = entityRendererIn;
	}



	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SWEMHorseEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = entitylivingbaseIn.getSWEMArmor();
		if (!stack.isEmpty()) {
			GeoModel horseModel = this.entityRenderer.getGeoModelProvider().getModel(this.entityRenderer.getGeoModelProvider().getModelLocation(entitylivingbaseIn));
			SWEMHorseArmorItem armorItem = (SWEMHorseArmorItem)stack.getItem();
			IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(bufferIn, RenderType.getArmorCutoutNoCull(armorItem.getArmorTexture()), false, stack.hasEffect());
			Iterator group = geoModel.getModel(geoModel.getModelLocation(armorItem)).topLevelBones.iterator();
			while (group.hasNext()) {
				GeoBone bone = (GeoBone) group.next();
				if (bone.getName().equals("cloth")) {

					GeoBone horseStomach = horseModel.getBone("body").get();
					SWEM.LOGGER.debug(String.format("Stomach: Rot X: %s - Rot Y: %s - Rot Z: %s | Piv X: %s - Piv Y: %s - Piv Z: %s", horseStomach.getRotationX(), horseStomach.getRotationY(), horseStomach.getRotationZ(), horseStomach.getPivotX(), horseStomach.getPivotY(), horseStomach.getPivotZ()));
					bone.setPivotZ(0);
					bone.setPivotX(0);
					bone.setPivotY(12);
					bone.setRotationZ(horseStomach.getRotationZ());
					bone.setRotationX(horseStomach.getRotationX());

					bone.setPositionY(horseStomach.getPositionY());
				}

				if (bone.getName().equals("iron")) {
					//GeoBone horseHead = horseModel.getBone("head").get();
					GeoBone horseBack = horseModel.getBone("back").get().childBones.get(0);
					//GeoBone horseFront = horseModel.getBone("base").get();



					GeoBone back = bone.childBones.get(1);
					//back.setPivotZ(11);
					//back.setPivotX(-11);
					//back.setPivotY(0);
					//back.setPivotZ(0);
					//back.setPivotX(0);
					//back.setPivotY(0);

					//back.setRotationZ(horseBack.getRotationZ());

					//back.setPositionZ(horseBack.getPositionZ());
					//back.setPositionX(horseBack.getPositionZ() + 1);
					//back.setPositionY(horseModel.getBone("body").get().getPositionY());


				}

				// Gold has feet and head2
				// Diamond has neck and butt
				// Amethyst has breast, front and back-side

				matrixStackIn.push();
				matrixStackIn.rotate(new Quaternion(0.0F, 90.0F, 0.0F, true));
				this.entityRenderer.renderRecursively(bone, matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.pop();
			}
		}
	}
}
