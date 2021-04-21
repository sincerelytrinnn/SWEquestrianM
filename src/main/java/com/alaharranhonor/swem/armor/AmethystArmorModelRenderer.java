package com.alaharranhonor.swem.armor;

import com.alaharranhonor.swem.items.SWEMArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class AmethystArmorModelRenderer extends GeoArmorRenderer<SWEMArmorItem> {
	public AmethystArmorModelRenderer() {
		super (new AmethystArmorModel());

		this.headBone = "Head";
		this.bodyBone = "Body";
		this.rightArmBone = "RightArm";
		this.leftArmBone = "LeftArm";
		this.rightLegBone = "LeftLeg";
		this.leftLegBone = "RightLeg";
		this.rightBootBone = "LeftBoot";
		this.leftBootBone = "RightBoot";
	}

}
