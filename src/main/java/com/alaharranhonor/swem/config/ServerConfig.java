package com.alaharranhonor.swem.config;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import com.alaharranhonor.swem.SWEM;

final class ServerConfig {

	final ForgeConfigSpec.BooleanValue serverEnableAmethystOre;
	final ForgeConfigSpec.IntValue serverAmethystVeinSize;
	final ForgeConfigSpec.IntValue serverAmethystVeinCount;
	final ForgeConfigSpec.IntValue serverAmethystBottomHeight;
	final ForgeConfigSpec.IntValue serverAmethystMaxHeight;

	final ForgeConfigSpec.BooleanValue serverEnableCantazariteOre;
	final ForgeConfigSpec.IntValue serverCantazariteVeinSize;
	final ForgeConfigSpec.IntValue serverCantazariteVeinCount;
	final ForgeConfigSpec.IntValue serverCantazariteBottomHeight;
	final ForgeConfigSpec.IntValue serverCantazariteMaxHeight;

	ServerConfig(ForgeConfigSpec.Builder builder) {

		builder.push("SWEMOreGen");
		this.serverEnableAmethystOre = builder.comment("Enable amethyst ore generation?").translation("swem.config.enableAmethystOre").define("EnableAmethystOre", true);
		this.serverEnableCantazariteOre = builder.comment("Enable cantazarite ore generation?").translation("swem.config.enableCantazariteOre").define("EnableCantazariteOre", true);

		this.serverAmethystVeinSize = builder.comment("Amethyst ore vein size").translation("swem:config.serverAmethystVeinSize").defineInRange("AmethystVeinSize", 8, 1, 2147483647);
		this.serverAmethystVeinCount = builder.comment("Amethyst ore vein count per chunk").translation("swem:config.serverAmethystVeinCount").defineInRange("AmethystVeinCount", 2, 1, 2147483647);
		this.serverAmethystBottomHeight = builder.comment("Amethyst ore minimum height").translation("swem:config.serverAmethystBottomHeight").defineInRange("AmethystBottomHeight", 0, 1, 2147483647);
		this.serverAmethystMaxHeight = builder.comment("Amethyst ore maximum height").translation("swem:config.serverAmethystMaxHeight").defineInRange("AmethystMaxHeight", 15, 1, 2147483647);

		this.serverCantazariteVeinSize = builder.comment("Cantazarite ore vein size").translation("swem:config.serverCantazariteVeinSize").defineInRange("CantazariteVeinSize", 8, 1, 2147483647);
		this.serverCantazariteVeinCount = builder.comment("Cantazarite ore vein count per chunk").translation("swem:config.serverCantazariteVeinCount").defineInRange("CantazariteVeinCount", 8, 1, 2147483647);
		this.serverCantazariteBottomHeight = builder.comment("Cantazarite ore minimum height").translation("swem:config.serverCantazariteBottomHeight").defineInRange("CantazariteBottomHeight", 0, 1, 2147483647);
		this.serverCantazariteMaxHeight = builder.comment("Cantazarite ore maximum height").translation("swem:config.serverCantazariteMaxHeight").defineInRange("CantazariteMaxHeight", 30, 1, 2147483647);
		builder.pop();
	}
}
