package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entity.model.*;
import com.alaharranhonor.swem.items.SWEMHorseArmorItem;
import com.alaharranhonor.swem.items.tack.AdventureSaddleItem;
import com.alaharranhonor.swem.tileentity.HorseArmorRackTE;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;

public class HorseArmorRackRender extends GeoBlockRenderer<HorseArmorRackTE> {

	HorseArmorModelGeo armorModelGeo = new HorseArmorModelGeo();
	AdventureSaddleModel saddleModel = new AdventureSaddleModel();

	public HorseArmorRackRender(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn, new HorseArmorRackModel());
	}

	@Override
	public void render(HorseArmorRackTE tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(tile, partialTicks, stack, bufferIn, packedLightIn);
		ItemStack armor = tile.itemHandler.getStackInSlot(0);
		ItemStack saddle = tile.itemHandler.getStackInSlot(1);

		if (!(armor.getItem() == Items.AIR || armor == ItemStack.EMPTY)) {
			SWEM.LOGGER.debug("Armor not empty/air");
			SWEMHorseArmorItem armorItem = (SWEMHorseArmorItem)armor.getItem();
			IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(bufferIn, RenderType.armorCutoutNoCull(armorItem.getRackTexture()), false, armor.hasFoil());
			GeoModel geoModel = armorModelGeo.getModel(armorModelGeo.getModelLocation(armorItem));
			Iterator group = geoModel.topLevelBones.iterator();

			while (group.hasNext())
			{
				GeoBone bone = (GeoBone) group.next();


				// Childbones are inverted in order for some bones, from what shown in BlockBench.
				// the order of the bones are stored in a json array which does not guarantee order preservation - Gecko

				if (bone.getName().equals("cloth")) {
					GeoBone body = bone.childBones.get(0);
					GeoBone piece = body.childBones.get(0); // Right
					GeoBone piece1 = body.childBones.get(1); // Left

					// We need to check the name, because as mentioned about, it's not a guaranteed order preservation, so for some armor pieces, the sides are swapped.
					if (piece.getName().equals("right")) {
						piece.setPivotX(1);
						piece.setPivotY(22);
						piece.setPivotZ(7);

						piece.setRotationX(1.575f);
						piece.setRotationY(1.3f);

						piece.setPositionY(-19.75f);
						piece.setPositionX(2);
						piece.setPositionZ(-7);

						piece1.setPivotX(1);
						piece1.setPivotY(22);
						piece1.setPivotZ(-7);

						piece1.setRotationX(1.575f);
						piece1.setRotationY(1.575f);

						piece1.setPositionY(-19.75f);
						piece1.setPositionX(2);
						piece1.setPositionZ(7);
					} else {
						piece.setPivotX(1);
						piece.setPivotY(22);
						piece.setPivotZ(-7);

						piece.setRotationX(1.575f);
						piece.setRotationY(1.575f);

						piece.setPositionY(-19.75f);
						piece.setPositionX(2);
						piece.setPositionZ(7);

						piece1.setPivotX(1);
						piece1.setPivotY(22);
						piece1.setPivotZ(7);

						piece1.setRotationX(1.575f);
						piece1.setRotationY(1.3f);

						piece1.setPositionY(-19.75f);
						piece1.setPositionX(2);
						piece1.setPositionZ(-7);
					}





				} else if (bone.getName().equals("iron")) {
					GeoBone head = bone.childBones.get(0);
					bone.setPositionY(-15);
					bone.setPositionX(1);


					head.setPivotY(32);
					head.setPivotX(19);
					head.setPositionY(-1);
					head.setPositionX(0);
					head.setRotationZ(-1.575f); // Rotations are apparently not in degrees. Also this rotation is dependent on the current pivot points and positions.

				} else if (bone.getName().equals("gold"))
				{
					GeoBone feet = bone.childBones.get(0);
					GeoBone head = bone.childBones.get(1);

					bone.setPositionY(-15);
					bone.setPositionX(1);


					head.setPivotY(32);
					head.setPivotX(19);
					head.setPositionY(-1);
					head.setPositionX(0);
					head.setRotationZ(-1.575f); // Rotations are apparently not in degrees. Also this rotation is dependent on the current pivot points and positions.
					feet.setPositionY(17);


				} else if (bone.getName().equals("diamond")) {

					GeoBone neck = bone.childBones.get(0);

					bone.setPositionY(-15);
					bone.setPositionX(1);
					neck.setRotationZ(0);
					neck.setPositionY(-7);
					neck.setPositionX(-4);

				} else {
					bone.setPositionY(-15);
					bone.setPositionX(1);
				}

				stack.pushPose();
				double[] translations = this.calculateTranslations(tile.getBlockState());
				stack.translate(translations[0], translations[1], translations[2]);
				stack.mulPose(this.calculateRotation(tile.getBlockState()));
				this.renderRecursively(bone, stack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				stack.popPose();
			}
		}

		if (!(saddle.getItem() == Items.AIR || saddle == ItemStack.EMPTY)) {
			SWEM.LOGGER.debug("Saddle not empty/air");
			AdventureSaddleItem saddleItem = (AdventureSaddleItem)saddle.getItem();
			IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(bufferIn, RenderType.armorCutoutNoCull(saddleItem.getSaddleRackTexture()), false, saddle.hasFoil());
			GeoModel geoModel = saddleModel.getModel(saddleModel.getModelLocation(saddleItem));
			Iterator group = geoModel.topLevelBones.iterator();
			while (group.hasNext()) {
				GeoBone bone = (GeoBone) group.next();

				stack.pushPose();
				double[] translations = this.calculateTranslations(tile.getBlockState());
				stack.translate(translations[0], translations[1] + 0.5, translations[2]);
				stack.mulPose(this.calculateRotation(tile.getBlockState()));
				stack.mulPose(new Quaternion(0, 90f, 0, true));
				this.renderRecursively(bone, stack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				stack.popPose();
			}
		}


	}

	private Quaternion calculateRotation(BlockState state) {
		switch (state.getValue(HorizontalBlock.FACING)) {
			case SOUTH: {
				return new Quaternion(0, -180.0f, 0, true);
			}
			case WEST: {
				return new Quaternion(0, 90.0f, 0, true);
			}
			case EAST: {
				return new Quaternion(0, -90.0f, 0, true);
			}
			default: {
				return new Quaternion(0, 0, 0, true);
			}
		}
	}

	private double[] calculateTranslations(BlockState state) {
		double[] translations = new double[3];

		switch (state.getValue(HorizontalBlock.FACING)) {
			case NORTH: {
				translations = new double[] {0, 0, 0.5};
				break;
			}
			case SOUTH: {
				translations = new double[] {1.0, 0, 0.5};
				break;
			}
			case EAST: {
				translations = new double[] {0.5, 0, 0};
				break;
			}
			case WEST: {
				translations = new double[] {0.5, 0, 1.0};
				break;
			}
			default: {
				Arrays.fill(translations, 0);
			}

		}
		return translations;
	}
}
