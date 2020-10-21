package com.alaharranhonor.swem.util;

import com.alaharranhonor.swem.entity.gui.SWEMHorseInventoryScreen;
import com.alaharranhonor.swem.entity.render.SWEMHorseRender;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
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
        RenderingRegistry.registerEntityRenderingHandler(RegistryHandler.SWEM_HORSE_ENTITY.get(), SWEMHorseRender::new);
        RenderTypeLookup.setRenderLayer(RegistryHandler.TIMOTHY_GRASS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(RegistryHandler.OAT_PLANT.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(RegistryHandler.ALFALFA_PLANT.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(RegistryHandler.RIDING_DOOR.get(), RenderType.getCutout());
    }

    public static void initLate() {
        ScreenManager.registerFactory(RegistryHandler.SWEM_HORSE_CONTAINER.get(), SWEMHorseInventoryScreen::new);
    }
}