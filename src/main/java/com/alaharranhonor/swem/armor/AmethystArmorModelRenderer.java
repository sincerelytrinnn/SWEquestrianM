package com.alaharranhonor.swem.armor;

import com.alaharranhonor.swem.items.SWEMArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class AmethystArmorModelRenderer extends GeoArmorRenderer<SWEMArmorItem> {
	public AmethystArmorModelRenderer() {
		super (new AmethystArmorModel());

		this.headBone = "helmet";
		this.bodyBone = "chestplate";
		this.rightArmBone = "rightArm";
		this.leftArmBone = "leftArm";
		this.rightLegBone = "rightLeg";
		this.leftLegBone = "leftLeg";
		this.rightBootBone = "rightBoot";
		this.leftBootBone = "leftBoot";
	}

}
