package com.alaharranhonor.swem.items;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.util.ResourceLocation;

public class BridleItem extends HalterItem {

	private ResourceLocation modelTexture;


	public BridleItem(String textureName, Properties properties) {
		super(new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/bridle/" + textureName + ".png"), properties);
		this.modelTexture = new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/bridle/" + textureName + "_model.png");
	}

	public ResourceLocation getModelTexture() {
		return this.modelTexture;
	}
}
