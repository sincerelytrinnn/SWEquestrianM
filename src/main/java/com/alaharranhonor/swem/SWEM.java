package com.alaharranhonor.swem;

import com.alaharranhonor.swem.blocks.TimothyGrass;
import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.entities.PoopEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.items.potions.BrewingRecipes;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.entities.WormieBoiEntity;
import com.alaharranhonor.swem.util.RegistryHandler;
import com.alaharranhonor.swem.util.SWLRegistryHandler;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMEntities;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import com.alaharranhonor.swem.util.registry.SWEMStructure;
import com.alaharranhonor.swem.world.structure.SWEMConfiguredStructures;
import com.mojang.serialization.Codec;
import net.minecraft.block.ComposterBlock;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Mod("swem")
public class SWEM
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "swem";

    static {
        GeckoLibMod.DISABLE_IN_DEV = true;
    }

    public SWEM() {
        // Register the setup method for modloading
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        RegistryHandler.init(modEventBus);
        SWLRegistryHandler.init();
        GeckoLib.initialize();


        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register config
        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigHolder.SERVER_SPEC);
    }

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        SWEMBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> !(block instanceof TimothyGrass))
                .forEach(block -> {
                    final Item.Properties properties = new Item.Properties().tab(TAB);
                    final BlockItem blockItem = new BlockItem(block, properties);
                    blockItem.setRegistryName(block.getRegistryName());
                    registry.register(blockItem);
                });

        LOGGER.debug("Registered BlockItems!");

    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            GlobalEntityTypeAttributes.put(SWEMEntities.SWEM_HORSE_ENTITY.get(), SWEMHorseEntityBase.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(SWEMEntities.WORMIE_BOI_ENTITY.get(), WormieBoiEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(SWEMEntities.HORSE_POOP_ENTITY.get(), PoopEntity.createLivingAttributes().build());
            BrewingRecipeRegistry.addRecipe(new BrewingRecipes.CantazariteBrewingRecipe());
            BrewingRecipeRegistry.addRecipe(new BrewingRecipes.RainbowChicPotion());

            ComposterBlock.COMPOSTABLES.put(SWEMItems.ALFALFA_SEEDS.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(SWEMItems.ALFALFA_BUSHEL.get(), 0.65F);
            ComposterBlock.COMPOSTABLES.put(SWEMItems.OAT_SEEDS.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(SWEMItems.OAT_BUSHEL.get(), 0.65F);
            ComposterBlock.COMPOSTABLES.put(SWEMItems.TIMOTHY_SEEDS.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(SWEMItems.TIMOTHY_BUSHEL.get(), 0.65F);
            ComposterBlock.COMPOSTABLES.put(SWEMBlocks.QUALITY_BALE_ITEM.get(), 0.85F);
            ComposterBlock.COMPOSTABLES.put(SWEMBlocks.WET_COMPOST_ITEM.get(), 0.85F);
            ComposterBlock.COMPOSTABLES.put(SWEMBlocks.COMPOST_ITEM.get(), 0.85F);

            SWEMStructure.setupStructures();
            SWEMConfiguredStructures.registerConfiguredStructures();

        });

        SWEMPacketHandler.init();
    }






    /**
     * Will go into the world's chunkgenerator and manually add our structure spacing.
     * If the spacing is not added, the structure doesn't spawn.
     *
     * Use this for dimension blacklists for your structure.
     * (Don't forget to attempt to remove your structure too from the map if you are blacklisting that dimension!)
     * (It might have your structure in it already.)
     *
     * Basically use this to make absolutely sure the chunkgenerator can or cannot spawn your structure.
     */
    private static Method GETCODEC_METHOD;
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)event.getWorld();

            /*
             * Skip Terraforged's chunk generator as they are a special case of a mod locking down their chunkgenerator.
             * They will handle your structure spacing for your if you add to WorldGenRegistries.NOISE_GENERATOR_SETTINGS in your structure's registration.
             * This here is done with reflection as this tutorial is not about setting up and using Mixins.
             * If you are using mixins, you can call the codec method with an invoker mixin instead of using reflection.
             */
            try {
                if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));
                if(cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            }
            catch(Exception e){
                SWEM.LOGGER.error("Was unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }

            /*
             * Prevent spawning our structure in Vanilla's superflat world as
             * people seem to want their superflat worlds free of modded structures.
             * Also that vanilla superflat is really tricky and buggy to work with in my experience.
             */
            if(serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.dimension().equals(World.OVERWORLD)){
                return;
            }

            /*
             * putIfAbsent so people can override the spacing with dimension datapacks themselves if they wish to customize spacing more precisely per dimension.
             * Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
             *
             * NOTE: if you add per-dimension spacing configs, you can't use putIfAbsent as WorldGenRegistries.NOISE_GENERATOR_SETTINGS in FMLCommonSetupEvent
             * already added your default structure spacing to some dimensions. You would need to override the spacing with .put(...)
             * And if you want to do dimension blacklisting, you need to remove the spacing entry entirely from the map below to prevent generation safely.
             */
            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
            tempMap.putIfAbsent(SWEMStructure.BARN.get(), DimensionStructuresSettings.DEFAULTS.get(SWEMStructure.BARN.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }


    public static final ItemGroup SWLMTAB = new ItemGroup("SWLMTab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(SWLRegistryHandler.STAR_WORM.get());
        }
    };

    public static final ItemGroup TAB = new ItemGroup("SWEMTab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(SWEMItems.WESTERN_SADDLE_LIGHT_BLUE.get());
        }
    };
}
