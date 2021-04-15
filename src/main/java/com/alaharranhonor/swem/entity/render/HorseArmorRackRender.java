package com.alaharranhonor.swem.entity.render;

import com.alaharranhonor.swem.blocks.OneSaddleRack;
import com.alaharranhonor.swem.entity.model.*;
import com.alaharranhonor.swem.items.SWEMHorseArmorItem;
import com.alaharranhonor.swem.items.tack.AdventureSaddleItem;
import com.alaharranhonor.swem.items.tack.EnglishSaddleItem;
import com.alaharranhonor.swem.items.tack.WesternSaddleItem;
import com.alaharranhonor.swem.tileentity.HorseArmorRackTE;
import com.alaharranhonor.swem.tileentity.OneSaddleRackTE;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.item.minecart.MinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;

public class HorseArmorRackRender extends GeoBlockRenderer<HorseArmorRackTE> {

	HorseArmorModelGeo armorModelGeo = new HorseArmorModelGeo();

	public HorseArmorRackRender(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn, new HorseArmorRackModel());
	}

	@Override
	public void render(HorseArmorRackTE tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(tile, partialTicks, stack, bufferIn, packedLightIn);
		ItemStack itemStack = tile.itemHandler.getStackInSlot(0);
		if (itemStack.getItem() == Items.AIR || itemStack == ItemStack.EMPTY) {
			return;
		}

		SWEMHorseArmorItem armorItem = (SWEMHorseArmorItem)itemStack.getItem();
		IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(bufferIn, RenderType.getArmorCutoutNoCull(armorItem.getArmorTexture()), false, itemStack.hasEffect());
		GeoModel geoModel = armorModelGeo.getModel(armorModelGeo.getModelLocation(armorItem));
		Iterator group = geoModel.topLevelBones.iterator();

		while (group.hasNext())
		{
			GeoBone bone = (GeoBone) group.next();


			// Childbones are inverted in order for some bones, from what shown in BlockBench.
			// the order of the bones are stored in a json array which does not guarantee order preservation - Gecko

			if (bone.getName().equals("cloth")) {
				GeoBone body = bone.childBones.get(0);
				body.setPositionY(3);
			}
			if (bone.getName().equals("iron")) {
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

			stack.push();
			double[] translations = this.calculateTranslations(tile.getBlockState());
			stack.translate(translations[0], translations[1], translations[2]);
			stack.rotate(this.calculateRotation(tile.getBlockState()));
			this.renderRecursively(bone, stack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			stack.pop();
		}

	}

	private Quaternion calculateRotation(BlockState state) {
		switch (state.get(HorizontalBlock.HORIZONTAL_FACING)) {
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

		switch (state.get(HorizontalBlock.HORIZONTAL_FACING)) {
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
