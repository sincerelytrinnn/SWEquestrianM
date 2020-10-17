package com.alaharranhonor.swem.util;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.config.ConfigHelper;
import com.alaharranhonor.swem.config.ConfigHolder;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

	@SubscribeEvent
	public static void onModConfigEvent(ModConfig.ModConfigEvent event) {
		ModConfig config = event.getConfig();
		if (config.getSpec() == ConfigHolder.SERVER_SPEC) {
			ConfigHelper.bakeServer(config);
		}
	}
}
