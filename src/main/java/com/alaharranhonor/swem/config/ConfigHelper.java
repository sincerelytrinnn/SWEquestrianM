package com.alaharranhonor.swem.config;

import net.minecraftforge.fml.config.ModConfig;

public final class ConfigHelper {
	public static void bakeClient(final ModConfig config) {
		//
	}

	public static void bakeServer(final ModConfig config) {
		SWLConfig.veinSize = ConfigHolder.SERVER.veinSize.get();
		SWLConfig.veinPerChunk = ConfigHolder.SERVER.veinPerChunk.get();
	}
}
