package com.alaharranhonor.swem.util;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.armor.AmethystModel;
import com.alaharranhonor.swem.armor.ArmorBaseModel;
import com.alaharranhonor.swem.armor.GenericModel;
import net.minecraft.util.ResourceLocation;

public class ArmorItemRegistration {
	static ArmorBaseModel LeatherArmor = new GenericModel(128, 128, new ResourceLocation(SWEM.MOD_ID, "textures/models/armor/leather_layer.png"));
	static ArmorBaseModel GlowArmor = new GenericModel(128, 128, new ResourceLocation(SWEM.MOD_ID, "textures/models/armor/glow_layer.png"));
	static ArmorBaseModel IronArmor = new GenericModel(128, 128, new ResourceLocation(SWEM.MOD_ID, "textures/models/armor/iron_layer.png"));
	static ArmorBaseModel GoldArmor = new GenericModel(128, 128, new ResourceLocation(SWEM.MOD_ID, "textures/models/armor/gold_layer.png"));
	static ArmorBaseModel DiamondArmor = new GenericModel(128, 128, new ResourceLocation(SWEM.MOD_ID, "textures/models/armor/diamond_layer.png"));

	static ArmorBaseModel AmethystArmor = new AmethystModel(128, 128, new ResourceLocation(SWEM.MOD_ID, "textures/models/armor/amethyst_layer.png"));


	public static ArmorBaseModel getLeatherArmor() {
		return LeatherArmor;
	}
	public static ArmorBaseModel getGlowArmor() {
		return GlowArmor;
	}
	public static ArmorBaseModel getIronArmor() {
		return IronArmor;
	}
	public static ArmorBaseModel getGoldArmor() {
		return GoldArmor;
	}
	public static ArmorBaseModel getDiamondArmor() {
		return DiamondArmor;
	}

	public static ArmorBaseModel getAmethystArmor() {
		return AmethystArmor;
	}

}
