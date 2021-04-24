package com.alaharranhonor.swem.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ServerConfig {

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

	public final ForgeConfigSpec.BooleanValue serverTickFoodNeed;
	public final ForgeConfigSpec.BooleanValue serverTickWaterNeed;
	public final ForgeConfigSpec.BooleanValue serverTickPoopNeed;
	public final ForgeConfigSpec.BooleanValue serverTickPeeNeed;

	public final ForgeConfigSpec.BooleanValue halterDependency;
	public final ForgeConfigSpec.BooleanValue needBridleToSteer;
	public final ForgeConfigSpec.BooleanValue blanketBeforeSaddle;
	public final ForgeConfigSpec.BooleanValue saddleBeforeGirthStrap;
	public final ForgeConfigSpec.BooleanValue riderNeedsGirthStrap;

	ServerConfig(ForgeConfigSpec.Builder builder) {

		builder.push("SWEMOreGen");
		this.serverEnableAmethystOre = builder.comment("Enable amethyst ore generation?").translation("swem.config.enableAmethystOre").define("EnableAmethystOre", true);
		this.serverEnableCantazariteOre = builder.comment("Enable cantazarite ore generation?").translation("swem.config.enableCantazariteOre").define("EnableCantazariteOre", true);

		this.serverAmethystVeinSize = builder.comment("Amethyst ore vein size").translation("swem.config.serverAmethystVeinSize").defineInRange("AmethystVeinSize", 8, 1, 2147483647);
		this.serverAmethystVeinCount = builder.comment("Amethyst ore vein count per chunk").translation("swem.config.serverAmethystVeinCount").defineInRange("AmethystVeinCount", 2, 1, 2147483647);
		this.serverAmethystBottomHeight = builder.comment("Amethyst ore minimum height").translation("swem.config.serverAmethystBottomHeight").defineInRange("AmethystBottomHeight", 0, 0, 2147483647);
		this.serverAmethystMaxHeight = builder.comment("Amethyst ore maximum height").translation("swem.config.serverAmethystMaxHeight").defineInRange("AmethystMaxHeight", 15, 1, 2147483647);

		this.serverCantazariteVeinSize = builder.comment("Cantazarite ore vein size").translation("swem.config.serverCantazariteVeinSize").defineInRange("CantazariteVeinSize", 4, 1, 2147483647);
		this.serverCantazariteVeinCount = builder.comment("Cantazarite ore vein count per chunk").translation("swem.config.serverCantazariteVeinCount").defineInRange("CantazariteVeinCount", 6, 1, 2147483647);
		this.serverCantazariteBottomHeight = builder.comment("Cantazarite ore minimum height").translation("swem.config.serverCantazariteBottomHeight").defineInRange("CantazariteBottomHeight", 0, 0, 2147483647);
		this.serverCantazariteMaxHeight = builder.comment("Cantazarite ore maximum height").translation("swem.config.serverCantazariteMaxHeight").defineInRange("CantazariteMaxHeight", 30, 1, 2147483647);
		builder.pop();

		builder.push("SWLMOreGen");
		this.serverEnableSWLMCobbleOre = builder.comment("Enable SWLMCobble ore generation?").translation("swem.config.enableSWLMCobbleOre").define("EnableSWLMCobbleOre", true);
		this.serverSWLMCobbleVeinSize = builder.comment("SWLMCobble ore vein size").translation("swem.config.serverSWLMCobbleVeinSize").defineInRange("SWLMCobbleVeinSize", 7, 1, 2147483647);
		this.serverSWLMCobbleVeinCount = builder.comment("SWLMCobble ore vein count per chunk").translation("swem.config.serverSWLMCobbleVeinCount").defineInRange("SWLMCobbleVeinCount", 12, 1, 2147483647);
		this.serverSWLMCobbleBottomHeight = builder.comment("SWLMCobble ore minimum height").translation("swem.config.serverSWLMCobbleBottomHeight").defineInRange("SWLMCobbleBottomHeight", 50, 1, 2147483647);
		this.serverSWLMCobbleMaxHeight = builder.comment("SWLMCobble ore maximum height").translation("swem.config.serverSWLMCobbleMaxHeight").defineInRange("SWLMCobbleMaxHeight", 128, 1, 2147483647);
		builder.pop();

		builder.push("Config");
		this.serverTickFoodNeed = builder.comment("Enable Food need ticking on swem horses?").translation("swem.config.enableFoodTick").define("foodTick", true);
		this.serverTickWaterNeed = builder.comment("Enable Water need ticking on swem horses?").translation("swem.config.enableWaterTick").define("waterTick", true);
		this.serverTickPoopNeed = builder.comment("Enable Poop ticking on swem horses?").translation("swem.config.enablePoopTick").define("poopTick", true);
		this.serverTickPeeNeed = builder.comment("Enable Pee ticking on swem horses?").translation("swem.config.enablePeeTick").define("peeTick", true);

			builder.push("Tack Dependencies");
			this.halterDependency = builder.comment("Enable/Disable the halter, being needed for any other tack.").translation("swem.config.halterDep").define("HalterDependency", true);
			this.needBridleToSteer = builder.comment("Enable/Disable the need of a bridle in order to steer, the horse. (If disabled, you would still need a saddle.)").translation("swem.config.needBridleToSteer").define("NeedBridleToSteer", true);
			this.blanketBeforeSaddle = builder.comment("Enable/Disable the need of a blanket, before saddling up.").translation("swem.config.needBlanket").define("NeedBlanket", true);
			this.saddleBeforeGirthStrap = builder.comment("Enable/Disable the need of putting the saddle on, before you can put a girth strap on.").translation("swem.config.needSaddle").define("NeedSaddleForGirthStrap", true);
			this.riderNeedsGirthStrap = builder.comment("Enable/Disable the rider falling off the horse, in case there is no girth strap equipped.").translation("swem.config.riderFallsOff").define("RiderFallingOff", true);
			builder.pop();

		builder.pop();
	}
}
