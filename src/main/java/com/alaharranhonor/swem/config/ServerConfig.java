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

	final ForgeConfigSpec.BooleanValue serverEnableSWLMCobbleOre;
	final ForgeConfigSpec.IntValue serverSWLMCobbleVeinSize;
	final ForgeConfigSpec.IntValue serverSWLMCobbleVeinCount;
	final ForgeConfigSpec.IntValue serverSWLMCobbleBottomHeight;
	final ForgeConfigSpec.IntValue serverSWLMCobbleMaxHeight;

	ServerConfig(ForgeConfigSpec.Builder builder) {

		builder.push("SWEMOreGen");
		this.serverEnableAmethystOre = builder.comment("Enable amethyst ore generation?").translation("swem.config.enableAmethystOre").define("EnableAmethystOre", true);
		this.serverEnableCantazariteOre = builder.comment("Enable cantazarite ore generation?").translation("swem.config.enableCantazariteOre").define("EnableCantazariteOre", true);

		this.serverAmethystVeinSize = builder.comment("Amethyst ore vein size").translation("swem:config.serverAmethystVeinSize").defineInRange("AmethystVeinSize", 8, 1, 2147483647);
		this.serverAmethystVeinCount = builder.comment("Amethyst ore vein count per chunk").translation("swem:config.serverAmethystVeinCount").defineInRange("AmethystVeinCount", 2, 1, 2147483647);
		this.serverAmethystBottomHeight = builder.comment("Amethyst ore minimum height").translation("swem:config.serverAmethystBottomHeight").defineInRange("AmethystBottomHeight", 0, 0, 2147483647);
		this.serverAmethystMaxHeight = builder.comment("Amethyst ore maximum height").translation("swem:config.serverAmethystMaxHeight").defineInRange("AmethystMaxHeight", 15, 1, 2147483647);

		this.serverCantazariteVeinSize = builder.comment("Cantazarite ore vein size").translation("swem:config.serverCantazariteVeinSize").defineInRange("CantazariteVeinSize", 8, 1, 2147483647);
		this.serverCantazariteVeinCount = builder.comment("Cantazarite ore vein count per chunk").translation("swem:config.serverCantazariteVeinCount").defineInRange("CantazariteVeinCount", 8, 1, 2147483647);
		this.serverCantazariteBottomHeight = builder.comment("Cantazarite ore minimum height").translation("swem:config.serverCantazariteBottomHeight").defineInRange("CantazariteBottomHeight", 0, 0, 2147483647);
		this.serverCantazariteMaxHeight = builder.comment("Cantazarite ore maximum height").translation("swem:config.serverCantazariteMaxHeight").defineInRange("CantazariteMaxHeight", 30, 1, 2147483647);
		builder.pop();

		builder.push("SWLMOreGen");
		this.serverEnableSWLMCobbleOre = builder.comment("Enable SWLMCobble ore generation?").translation("swem.config.enableSWLMCobbleOre").define("EnableSWLMCobbleOre", true);
		this.serverSWLMCobbleVeinSize = builder.comment("SWLMCobble ore vein size").translation("swem:config.serverSWLMCobbleVeinSize").defineInRange("SWLMCobbleVeinSize", 7, 1, 2147483647);
		this.serverSWLMCobbleVeinCount = builder.comment("SWLMCobble ore vein count per chunk").translation("swem:config.serverSWLMCobbleVeinCount").defineInRange("SWLMCobbleVeinCount", 12, 1, 2147483647);
		this.serverSWLMCobbleBottomHeight = builder.comment("SWLMCobble ore minimum height").translation("swem:config.serverSWLMCobbleBottomHeight").defineInRange("SWLMCobbleBottomHeight", 50, 1, 2147483647);
		this.serverSWLMCobbleMaxHeight = builder.comment("SWLMCobble ore maximum height").translation("swem:config.serverSWLMCobbleMaxHeight").defineInRange("SWLMCobbleMaxHeight", 128, 1, 2147483647);
		builder.pop();
	}
}
