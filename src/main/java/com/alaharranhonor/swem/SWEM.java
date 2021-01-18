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
import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import com.alaharranhonor.swem.util.initialization.SWEMEntities;
import com.alaharranhonor.swem.util.initialization.SWEMItems;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

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
        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        RegistryHandler.init(modEventBus);
        SWLRegistryHandler.init();
        GeckoLib.initialize();



        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register config
        //modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigHolder.SERVER_SPEC);
    }

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        SWEMBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> !(block instanceof TimothyGrass))
                .forEach(block -> {
                    final Item.Properties properties = new Item.Properties().group(TAB);
                    final BlockItem blockItem = new BlockItem(block, properties);
                    blockItem.setRegistryName(block.getRegistryName());
                    registry.register(blockItem);
                });

        LOGGER.debug("Registered BlockItems!");
    }

    private void setup(final FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put(SWEMEntities.SWEM_HORSE_ENTITY.get(), SWEMHorseEntityBase.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(SWEMEntities.WORMIE_BOI_ENTITY.get(), WormieBoiEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(SWEMEntities.HORSE_POOP_ENTITY.get(), PoopEntity.registerAttributes().create());
            BrewingRecipeRegistry.addRecipe(new BrewingRecipes.CantazariteBrewingRecipe());
            BrewingRecipeRegistry.addRecipe(new BrewingRecipes.RainbowChicPotion());
        });

        SWEMPacketHandler.init();
    }


    public static final ItemGroup SWLMTAB = new ItemGroup("SWLMTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(SWEMItems.SWEM_WORM.get());
        }
    };

    public static final ItemGroup TAB = new ItemGroup("SWEMTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(SWEMItems.WESTERN_SADDLE_LIGHT_BLUE.get());
        }
    };
}
