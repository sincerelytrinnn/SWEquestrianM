package com.alaharranhonor.swem.util;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.config.ConfigHelper;
import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.world.gen.OreGenUtils;
import com.alaharranhonor.swem.world.gen.SWEMOreGen;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

	@SubscribeEvent
	public static void onModConfigEvent(ModConfig.ModConfigEvent event) {

		ModConfig config = event.getConfig();
		if (config.getModId().equals(SWEM.MOD_ID)) {
			if (config.getSpec() == ConfigHolder.SERVER_SPEC) {
				if (SWEMOreGen.AMETHYST_ORE == null) {
					SWEMOreGen.AMETHYST_ORE = OreGenUtils.buildOverWorldFeature(RegistryHandler.AMETHYST_ORE.get().getDefaultState());
				}
				if (SWEMOreGen.CANTAZARITE_ORE == null) {
					SWEMOreGen.CANTAZARITE_ORE = OreGenUtils.buildOverWorldFeature(RegistryHandler.AMETHYST_ORE.get().getDefaultState());
				}
				ConfigHelper.bakeServer(config);
			}
		}
	}
}