package com.alaharranhonor.swem.world.gen;

import com.alaharranhonor.swem.util.SWLRegistryHandler;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class SWEMOreGen {
	public static ConfiguredFeature<?, ?> AMETHYST_ORE;
	public static ConfiguredFeature<?, ?> CANTAZARITE_ORE;
	public static ConfiguredFeature<?, ?> SWLM_COBBLE_ORE;

	public static boolean checkAndInitBiome(BiomeLoadingEvent event) {
		if (event.getCategory() == Biome.Category.NETHER) {
			return true;
		}

		if (event.getCategory() != Biome.Category.THEEND) {
			initOverWorldFeatures();
			return true;
		}
		return false;
	}

	protected static void initOverWorldFeatures() {
		if (AMETHYST_ORE == null) {
			AMETHYST_ORE = OreGenUtils.buildOverWorldFeature(SWEMBlocks.AMETHYST_ORE.get().defaultBlockState());
		}
		if (CANTAZARITE_ORE == null) {
			CANTAZARITE_ORE = OreGenUtils.buildOverWorldFeature(SWEMBlocks.CANTAZARITE_ORE.get().defaultBlockState());
		}
		if (SWLM_COBBLE_ORE == null) {
			SWLM_COBBLE_ORE = OreGenUtils.buildOverWorldFeature(SWLRegistryHandler.STAR_WORM_COBBLE.get().defaultBlockState());
		}
	}

	public static void generateOverworldOres(BiomeLoadingEvent event) {
		event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, AMETHYST_ORE);
		event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CANTAZARITE_ORE);
		event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, SWLM_COBBLE_ORE);
	}
}
