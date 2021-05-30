package com.alaharranhonor.swem.config;

import com.alaharranhonor.swem.world.gen.SWEMOreGen;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.DecoratedPlacementConfig;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.fml.config.ModConfig;

public final class ConfigHelper {


	public static void bakeClient(final ModConfig config) {
		//
	}

	public static void bakeServer(final ModConfig config) {

		// Vein Size
		((OreFeatureConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.AMETHYST_ORE.config).feature.get().config).feature.get().config).size = ConfigHolder.SERVER.serverAmethystVeinSize.get();
		// Vein Count
		((FeatureSpreadConfig)((DecoratedFeatureConfig) SWEMOreGen.AMETHYST_ORE.config).decorator.config).getSpread().base = ConfigHolder.SERVER.serverAmethystVeinCount.get();
		// Minimum Height the ore can spawn
		((TopSolidRangeConfig)((DecoratedPlacementConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.AMETHYST_ORE.config).feature.get().config).decorator.config).inner.config).bottomOffset = ConfigHolder.SERVER.serverAmethystBottomHeight.get();
		// Maximum Height the ore can spawn.
		((TopSolidRangeConfig)((DecoratedPlacementConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.AMETHYST_ORE.config).feature.get().config).decorator.config).inner.config).maximum = ConfigHolder.SERVER.serverAmethystMaxHeight.get();


		// Vein Size
		((OreFeatureConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.CANTAZARITE_ORE.config).feature.get().config).feature.get().config).size = ConfigHolder.SERVER.serverCantazariteVeinSize.get();
		// Vein Count
		((FeatureSpreadConfig)((DecoratedFeatureConfig) SWEMOreGen.CANTAZARITE_ORE.config).decorator.config).getSpread().base = ConfigHolder.SERVER.serverCantazariteVeinCount.get();
		// Minimum Height the ore can spawn
		((TopSolidRangeConfig)((DecoratedPlacementConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.CANTAZARITE_ORE.config).feature.get().config).decorator.config).inner.config).bottomOffset = ConfigHolder.SERVER.serverCantazariteBottomHeight.get();
		// Maximum Height the ore can spawn.
		((TopSolidRangeConfig)((DecoratedPlacementConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.CANTAZARITE_ORE.config).feature.get().config).decorator.config).inner.config).maximum = ConfigHolder.SERVER.serverCantazariteMaxHeight.get();

		// Vein Size
		((OreFeatureConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.SWLM_COBBLE_ORE.config).feature.get().config).feature.get().config).size = ConfigHolder.SERVER.serverSWLMCobbleVeinSize.get();
		// Vein Count
		((FeatureSpreadConfig)((DecoratedFeatureConfig) SWEMOreGen.SWLM_COBBLE_ORE.config).decorator.config).getSpread().base = ConfigHolder.SERVER.serverSWLMCobbleVeinCount.get();
		// Minimum Height the ore can spawn
		((TopSolidRangeConfig)((DecoratedPlacementConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.SWLM_COBBLE_ORE.config).feature.get().config).decorator.config).inner.config).bottomOffset = ConfigHolder.SERVER.serverSWLMCobbleBottomHeight.get();
		// Maximum Height the ore can spawn.
		((TopSolidRangeConfig)((DecoratedPlacementConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.SWLM_COBBLE_ORE.config).feature.get().config).decorator.config).inner.config).maximum = ConfigHolder.SERVER.serverSWLMCobbleMaxHeight.get();
	}
}
