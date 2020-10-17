package com.alaharranhonor.swem.world.gen;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

public class OreGenUtils {

	public static ConfiguredFeature<?, ?> buildOverWorldFeature(BlockState bstate) {
		return Feature.ORE
				.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, bstate, 5))
				.withPlacement(Placement.field_242907_l.configure(new TopSolidRangeConfig(5, 0, 20))
						.func_242728_a())
				.func_242731_b(8);
	}
}
