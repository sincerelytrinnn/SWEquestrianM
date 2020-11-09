package com.alaharranhonor.swem.util;

import com.alaharranhonor.swem.entity.gui.SWEMHorseInventoryScreen;
import com.alaharranhonor.swem.entity.render.SWEMHorseRender;
import com.alaharranhonor.swem.entity.render.WormieBoiRender;
import com.alaharranhonor.swem.items.SWEMSpawnEggItem;
import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import com.alaharranhonor.swem.util.initialization.SWEMContainers;
import com.alaharranhonor.swem.util.initialization.SWEMEntities;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import com.alaharranhonor.swem.SWEM;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        DeferredWorkQueue.runLater(ClientEventBusSubscriber::initLate);
        registerRenderers(event);
        setRenderLayers();
    }

    public static void initLate() {
        ScreenManager.registerFactory(SWEMContainers.SWEM_HORSE_CONTAINER.get(), SWEMHorseInventoryScreen::new);
    }

    public static void registerRenderers(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(SWEMEntities.SWEM_HORSE_ENTITY.get(), SWEMHorseRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SWEMEntities.WORMIE_BOI_ENTITY.get(), WormieBoiRender::new);
    }

    public static void setRenderLayers() {
        RenderTypeLookup.setRenderLayer(SWEMBlocks.TIMOTHY_GRASS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.OAT_PLANT.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.ALFALFA_PLANT.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.RIDING_DOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.HALF_BARREL.get(), RenderType.getTranslucent());
    }

    @SubscribeEvent
    public static void onRegisterBlockColors(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();
        colors.register((state, reader, pos, color) -> {
            return reader != null && pos != null ? BiomeColors.getWaterColor(reader, pos) : -1;
        }, SWEMBlocks.HALF_BARREL.get());
    }

    @SubscribeEvent
    public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event) {
        SWEMSpawnEggItem.initSpawnEggs();
    }


}