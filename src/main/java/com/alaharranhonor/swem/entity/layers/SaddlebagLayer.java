package com.alaharranhonor.swem.entity.layers;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entity.model.SaddlebagModel;
import com.alaharranhonor.swem.items.SWEMHorseArmorItem;
import com.alaharranhonor.swem.items.tack.SaddlebagItem;
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

public class SaddlebagLayer extends GeoLayerRenderer<SWEMHorseEntity> {

	private final SaddlebagModel saddlebagModel = new SaddlebagModel();
	private IGeoRenderer entityRenderer;

	public SaddlebagLayer(IGeoRenderer<SWEMHorseEntity> entityRendererIn) {
		super(entityRendererIn);
		this.entityRenderer = entityRendererIn;
	}

	@Override
	public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, SWEMHorseEntity swemHorseEntity, float v, float v1, float v2, float v3, float v4, float v5) {
		ItemStack stack = swemHorseEntity.getSaddlebag();
		if (!stack.isEmpty()) {
			GeoModel horseModel = this.entityRenderer.getGeoModelProvider().getModel(this.entityRenderer.getGeoModelProvider().getModelLocation(swemHorseEntity));
			SaddlebagItem bagItem = (SaddlebagItem)stack.getItem();
			IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(iRenderTypeBuffer, RenderType.getArmorCutoutNoCull(saddlebagModel.getTextureLocation(bagItem)), false, stack.hasEffect());
			Iterator group = saddlebagModel.getModel(saddlebagModel.getModelLocation(bagItem)).topLevelBones.iterator();
			while (group.hasNext()) {
				GeoBone bone = (GeoBone) group.next();

				GeoBone horseBack = horseModel.getBone("base").get();
				GeoBone horseBody = horseModel.getBone("body").get();

				bone.setPivotX(horseBack.getPivotX());
				bone.setPivotZ(horseBack.getPivotZ());
				bone.setPivotY(horseBack.getPivotY());
				bone.setRotationY(horseBack.getRotationY());
				bone.setRotationX(horseBack.getRotationX());
				bone.setRotationZ(horseBack.getRotationZ());
				bone.setPositionY(horseBody.getPositionY());

				matrixStack.push();
				matrixStack.translate(0, 1.55D, 0.55D);
				matrixStack.rotate(new Quaternion(0.0F, -90.0F, 0.0F, true));
				this.entityRenderer.renderRecursively(bone, matrixStack, ivertexbuilder, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStack.pop();
			}
		}
	}
}
