package com.alaharranhonor.swem.util;

import com.alaharranhonor.swem.armor.*;
import com.alaharranhonor.swem.blocks.SWEMBlockStateProperties;
import com.alaharranhonor.swem.entity.render.TackBoxRender;
import com.alaharranhonor.swem.gui.SWEMHorseInventoryScreen;
import com.alaharranhonor.swem.entity.render.SWEMHorseRender;
import com.alaharranhonor.swem.entity.render.WormieBoiRender;
import com.alaharranhonor.swem.gui.TackBoxDefaultScreen;
import com.alaharranhonor.swem.items.SWEMArmorItem;
import com.alaharranhonor.swem.items.SWEMSpawnEggItem;
import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import com.alaharranhonor.swem.util.initialization.SWEMContainers;
import com.alaharranhonor.swem.util.initialization.SWEMEntities;
import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.text.Color;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import com.alaharranhonor.swem.SWEM;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    public static KeyBinding[] keyBindings;

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        DeferredWorkQueue.runLater(ClientEventBusSubscriber::initLate);
        registerRenderers(event);
        setRenderLayers();
        registerKeybinds();
    }

    public static void initLate() {
        ScreenManager.registerFactory(SWEMContainers.SWEM_HORSE_CONTAINER.get(), SWEMHorseInventoryScreen::new);
        ScreenManager.registerFactory(SWEMContainers.TACKBOX_CONTAINER.get(), TackBoxDefaultScreen::new);
    }

    public static void registerRenderers(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(SWEMEntities.SWEM_HORSE_ENTITY.get(), SWEMHorseRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SWEMEntities.WORMIE_BOI_ENTITY.get(), WormieBoiRender::new);
        ClientRegistry.bindTileEntityRenderer(SWEMTileEntities.TACK_BOX_TILE_ENTITY.get(), TackBoxRender::new);
        GeoArmorRenderer.registerArmorRenderer(SWEMArmorItem.class, new AmethystArmorModelRenderer());
        GeoArmorRenderer.registerArmorRenderer(LeatherRidingBoots.class, new AmethystArmorModelRenderer());
        GeoArmorRenderer.registerArmorRenderer(GlowRidingBoots.class, new AmethystArmorModelRenderer());
        GeoArmorRenderer.registerArmorRenderer(IronRidingBoots.class, new AmethystArmorModelRenderer());
        GeoArmorRenderer.registerArmorRenderer(GoldRidingBoots.class, new AmethystArmorModelRenderer());
        GeoArmorRenderer.registerArmorRenderer(DiamondRidingBoots.class, new AmethystArmorModelRenderer());
    }

    public static void setRenderLayers() {
        RenderTypeLookup.setRenderLayer(SWEMBlocks.TIMOTHY_GRASS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.OAT_PLANT.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.ALFALFA_PLANT.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.RIDING_DOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.HALF_BARREL.get(), RenderType.getTranslucent());

        RenderTypeLookup.setRenderLayer(SWEMBlocks.SIMPLE_RIDER_DOOR_ORANGE.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(SWEMBlocks.ACACIA_STALL_HORSE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.BIRCH_STALL_HORSE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.DARK_OAK_STALL_HORSE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUNGLE_STALL_HORSE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.OAK_STALL_HORSE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.SPRUCE_STALL_HORSE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.ACACIA_STALL_CARE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.BIRCH_STALL_CARE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.DARK_OAK_STALL_CARE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUNGLE_STALL_CARE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.OAK_STALL_CARE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.SPRUCE_STALL_CARE.get(), RenderType.getCutout());
    }

    public static void registerKeybinds() {
        keyBindings = new KeyBinding[2];

        keyBindings[0] = new KeyBinding("key.swem.horse.increment", GLFW.GLFW_KEY_H, "key.swem.category");
        keyBindings[1] = new KeyBinding("key.swem.horse.decrement", GLFW.GLFW_KEY_G, "key.swem.category");

        for (int i = 0; i < keyBindings.length; i++) {
            ClientRegistry.registerKeyBinding(keyBindings[i]);
        }
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

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new SWEMSpawnEggItem(SWEMEntities.WORMIE_BOI_ENTITY, Color.fromHex("#bf7b05").getColor(), Color.fromHex("#663c02").getColor(), new Item.Properties().group(SWEM.TAB)).setRegistryName("worm_spawn_egg"));
            event.getRegistry().register(new SWEMSpawnEggItem(SWEMEntities.SWEM_HORSE_ENTITY, Color.fromHex("#bf7b05").getColor(), Color.fromHex("#663c02").getColor(), new Item.Properties().group(SWEM.TAB)).setRegistryName("swem_horse_spawn_egg"));
    }


}