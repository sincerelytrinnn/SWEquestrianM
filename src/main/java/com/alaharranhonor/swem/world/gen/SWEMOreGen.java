//package com.alaharranhonor.swem.world.gen;
//
//import com.alaharranhonor.swem.SWEM;
//import com.alaharranhonor.swem.util.RegistryHandler;
//import net.minecraft.block.BlockState;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.gen.GenerationStage;
//import net.minecraft.world.gen.feature.Feature;
//import net.minecraft.world.gen.feature.OreFeatureConfig;
//import net.minecraft.world.gen.placement.ConfiguredPlacement;
//import net.minecraft.world.gen.placement.CountRangeConfig;
//import net.minecraft.world.gen.placement.Placement;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
//import net.minecraftforge.registries.ForgeRegistries;
//
//@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
//public class SWEMOreGen {
//
//    @SubscribeEvent
//    public static void GenerateOres(FMLLoadCompleteEvent event){
//        for(Biome biome : ForgeRegistries.BIOMES){
//            if(biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND){
//                genOre(biome, 10, 1, 5, 35, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
//                        RegistryHandler.CANTAZARITE_ORE.get().getDefaultState(), 8);
//                genOre(biome, 5, 1, 5, 20, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
//                        RegistryHandler.AMETHYST_ORE.get().getDefaultState(), 5);
//            }
//        }
//    }
//
//    private static void genOre(Biome biome, int rarity, int bottomOffset, int topOffset, int max, OreFeatureConfig.FillerBlockType filler,
//                               BlockState defaultBlockState, int size){
//        CountRangeConfig range = new CountRangeConfig(rarity, bottomOffset, topOffset, max);
//        OreFeatureConfig feature = new OreFeatureConfig(filler, defaultBlockState, size);
//        ConfiguredPlacement config = Placement.COUNT_RANGE.configure(range);
//        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(feature).withPlacement(config));
//    }
//}
