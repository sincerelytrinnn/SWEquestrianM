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
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import java.awt.*;
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
			if (bone.getName().equals("cloth")) {

			}

			if (bone.getName().equals("iron"))
			{

			}

			if (bone.getName().equals("gold"))
			{

			}

			if (bone.getName().equals("diamond")) {

			}

			if (bone.getName().equals("amethyst")) {

			}

			this.renderRecursively(bone, stack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		}

	}
}
