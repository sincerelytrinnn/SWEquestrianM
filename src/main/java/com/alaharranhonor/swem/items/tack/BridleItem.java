package com.alaharranhonor.swem.items.tack;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BridleItem extends HalterItem {

	private ResourceLocation modelTexture;

	private ResourceLocation bridleRackTexture;


	public BridleItem(String textureName, Properties properties) {
		super(new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/bridle/" + textureName + ".png"), properties);
		this.modelTexture = new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/bridle/" + textureName + "_model.png");
		if (textureName.contains("bridle_")) {
			this.bridleRackTexture = new ResourceLocation(SWEM.MOD_ID, "textures/tile/bridle_rack/bridle_rack_" + Arrays.stream(textureName.split("bridle_")).collect(Collectors.joining("")) + ".png");
		}
	}

	public ResourceLocation getModelTexture() {
		return this.modelTexture;
	}

	@Override
	public ResourceLocation getBridleRackTexture() {
		return this.bridleRackTexture;
	}
}
