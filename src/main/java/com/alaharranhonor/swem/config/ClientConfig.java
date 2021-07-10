package com.alaharranhonor.swem.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ClientConfig {

	public final ForgeConfigSpec.IntValue wingsTransparency;

	ClientConfig(final ForgeConfigSpec.Builder builder) {
		builder.push("Wings transparency");

		this.wingsTransparency = builder.comment("Set how visible the horse wings should be! 0 = off, 1 = 50%, 2 = 100%").defineInRange("WingsTransparency", 2, 0, 2);
	}
}
