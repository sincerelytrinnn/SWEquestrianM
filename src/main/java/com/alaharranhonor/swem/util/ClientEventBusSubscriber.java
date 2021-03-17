package com.alaharranhonor.swem.util;

import com.alaharranhonor.swem.armor.*;
import com.alaharranhonor.swem.blocks.*;
import com.alaharranhonor.swem.entity.render.*;
import com.alaharranhonor.swem.gui.*;
import com.alaharranhonor.swem.items.SWEMSpawnEggItem;
import com.alaharranhonor.swem.util.initialization.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.particle.HeartParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeColors;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import com.alaharranhonor.swem.SWEM;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
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
        ScreenManager.registerFactory(SWEMContainers.CANTAZARITE_ANVIL_CONTAINER.get(), CantazariteAnvilScreen::new);
        ScreenManager.registerFactory(SWEMContainers.SADDLE_BAG_CONTAINER.get(), SaddlebagScreen::new);
        ScreenManager.registerFactory(SWEMContainers.BED_ROLL_CONTAINER.get(), BedrollScreen::new);
        ScreenManager.registerFactory(SWEMContainers.LOCKER_CONTAINER.get(), LockerScreen::new);
        ScreenManager.registerFactory(SWEMContainers.JUMP_CONTAINER.get(), JumpScreen::new);

        ItemModelsProperties.registerProperty(SWEMItems.AMETHYST_BOW.get(), new ResourceLocation("pull"), (p_239429_0_, p_239429_1_, p_239429_2_) -> {
            if (p_239429_2_ == null) {
                return 0.0F;
            } else {
                return p_239429_2_.getActiveItemStack() != p_239429_0_ ? 0.0F : (float)(p_239429_0_.getUseDuration() - p_239429_2_.getItemInUseCount()) / 20.0F;
            }
        });
        ItemModelsProperties.registerProperty(SWEMItems.AMETHYST_BOW.get(), new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);
        ItemModelsProperties.registerProperty(SWEMItems.AMETHYST_SHIELD.get(), new ResourceLocation("blocking"), (p_239421_0_, p_239421_1_, p_239421_2_) -> {
            return p_239421_2_ != null && p_239421_2_.isHandActive() && p_239421_2_.getActiveItemStack() == p_239421_0_ ? 1.0F : 0.0F;
        });
    }

    public static void registerRenderers(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(SWEMEntities.SWEM_HORSE_ENTITY.get(), SWEMHorseRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SWEMEntities.WORMIE_BOI_ENTITY.get(), WormieBoiRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SWEMEntities.ROPE_KNOT_ENTITY.get(), RopeKnotRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SWEMEntities.HORSE_POOP_ENTITY.get(), PoopRender::new);
        ClientRegistry.bindTileEntityRenderer(SWEMTileEntities.TACK_BOX_TILE_ENTITY.get(), TackBoxRender::new);
        ClientRegistry.bindTileEntityRenderer(SWEMTileEntities.ONE_SADDLE_RACK_TILE_ENTITY.get(), OneSaddleRackRender::new);
        ClientRegistry.bindTileEntityRenderer(SWEMTileEntities.BRIDLE_RACK_TILE_ENTITY.get(), BridleRackRender::new);
        GeoArmorRenderer.registerArmorRenderer(LeatherRidingBoots.class, new AmethystArmorModelRenderer());
        GeoArmorRenderer.registerArmorRenderer(GlowRidingBoots.class, new AmethystArmorModelRenderer());
        GeoArmorRenderer.registerArmorRenderer(IronRidingBoots.class, new AmethystArmorModelRenderer());
        GeoArmorRenderer.registerArmorRenderer(GoldRidingBoots.class, new AmethystArmorModelRenderer());
        GeoArmorRenderer.registerArmorRenderer(DiamondRidingBoots.class, new AmethystArmorModelRenderer());
        GeoArmorRenderer.registerArmorRenderer(AmethystRidingBoots.class, new AmethystArmorModelRenderer());
        GeoArmorRenderer.registerArmorRenderer(AmethystHelmet.class, new AmethystArmorModelRenderer());
        GeoArmorRenderer.registerArmorRenderer(AmethystChestplate.class, new AmethystArmorModelRenderer());
        GeoArmorRenderer.registerArmorRenderer(AmethystLeggings.class, new AmethystArmorModelRenderer());
    }

    public static void setRenderLayers() {
        RenderTypeLookup.setRenderLayer(SWEMBlocks.TIMOTHY_GRASS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.OAT_PLANT.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.ALFALFA_PLANT.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.WATER_TROUGH.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.METAL_GRATE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.LIGHT_FRIENDLY_BARS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.MEDIUM_FRIENDLY_BARS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.DARK_FRIENDLY_BARS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.HORSE_PEE.get(), RenderType.getCutout());

        // Jump blocks.
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_STANDARD_SCHOOLING.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_BRUSH_BOX.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_LOG.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_STAIR_DROP.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_HEDGE.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_COOP.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_WALL.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_WALL_MINI.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_CROSS_RAILS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_SWEDISH_RAILS.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_RED_FLAG.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_WHITE_FLAG.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_RED_WHITE_FLAG.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_NUMBERS.get(), RenderType.getTranslucent());

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

        for (DyeColor color : DyeColor.values()) {
            RenderTypeLookup.setRenderLayer(SWEMBlocks.ROLL_TOPS.get(color.getId()).get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.RAILS.get(color.getId()).get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.GROUND_POLES.get(color.getId()).get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.POLE_ON_BOXES_SMALL.get(color.getId()).get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.POLE_ON_BOXES_LARGE.get(color.getId()).get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.FANCY_PLANKS.get(color.getId()).get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.PLANKS.get(color.getId()).get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.PANELS_WAVE.get(color.getId()).get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.PANELS_STRIPE.get(color.getId()).get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.PANELS_ARROW.get(color.getId()).get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.FLOWER_BOXES.get(color.getId()).get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.CAVALETTIS.get(color.getId()).get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.WHEEL_BARROWS.get(color.getId()).get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.SEPARATORS.get(color.getId()).get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.PASTURE_GATES_HORSE.get(color.getId()).get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.PASTURE_GATES_CARE.get(color.getId()).get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.RIDER_DOORS.get(color.getId()).get(), RenderType.getCutout());
        }
    }

    public static void registerKeybinds() {
        keyBindings = new KeyBinding[4];

        keyBindings[0] = new KeyBinding("key.swem.horse.increment", GLFW.GLFW_KEY_H, "key.swem.category");
        keyBindings[1] = new KeyBinding("key.swem.horse.decrement", GLFW.GLFW_KEY_G, "key.swem.category");
        keyBindings[2] = new KeyBinding("key.swem.horse.toggle_bedroll", GLFW.GLFW_KEY_K, "key.swem.category");
        keyBindings[3] = new KeyBinding("key.swem.horse.toggle_flight", GLFW.GLFW_KEY_J, "key.swem.category");


        for (int i = 0; i < keyBindings.length; i++) {
            ClientRegistry.registerKeyBinding(keyBindings[i]);
        }
    }

    @SubscribeEvent
    public static void onRegisterBlockColors(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();
        for (RegistryObject<HalfBarrelBlock> barrelRegistry : SWEMBlocks.HALF_BARRELS) {
            HalfBarrelBlock barrel = barrelRegistry.get();
            colors.register((state, reader, pos, color) -> {
                return reader != null && pos != null ? BiomeRegistry.PLAINS.getWaterColor() : -1; // Figure out how to get the PLAINS biome only.
            }, barrel);
        }

        colors.register((state, reader, pos, color) -> {
            return reader != null && pos != null ? BiomeColors.getFoliageColor(reader, pos) : FoliageColors.getDefault();
        }, SWEMBlocks.JUMP_HEDGE.get());

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