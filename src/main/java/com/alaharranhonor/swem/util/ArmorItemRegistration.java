package com.alaharranhonor.swem.util;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.armor.ArmorBaseModel;
import com.alaharranhonor.swem.armor.LeatherModel;
import net.minecraft.util.ResourceLocation;

public class ArmorItemRegistration {
	static ArmorBaseModel LeatherArmor = new LeatherModel(128, 128, new ResourceLocation(SWEM.MOD_ID, "textures/models/armor/leather_layer.png"));

	public static ArmorBaseModel getLeatherArmor() {
		return LeatherArmor;
	}
}
