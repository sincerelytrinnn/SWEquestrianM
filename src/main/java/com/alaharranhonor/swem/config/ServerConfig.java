package com.alaharranhonor.swem.config;

import net.minecraftforge.common.ForgeConfigSpec;
import com.alaharranhonor.swem.SWEM;

final class ServerConfig {
	final ForgeConfigSpec.IntValue veinSize;
	final ForgeConfigSpec.IntValue veinPerChunk;

	ServerConfig(final ForgeConfigSpec.Builder builder) {
		builder.push("worldgen");
		veinSize = builder
				.comment("The size of star worm cobble veins")
				.translation(SWEM.MOD_ID + ".config.veinSize")
				.defineInRange("veinSize", 7, 0, Integer.MAX_VALUE);
		veinPerChunk = builder
				.comment("The amount of star worm cobble veins per chunk")
				.translation(SWEM.MOD_ID + ".config.veinPerChunk")
				.defineInRange("veinPerChunk", 2, 0, Integer.MAX_VALUE);
		builder.pop();
	}
}
