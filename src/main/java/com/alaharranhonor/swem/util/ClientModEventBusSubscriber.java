package com.alaharranhonor.swem.util;

import com.alaharranhonor.swem.armor.*;
import com.alaharranhonor.swem.blocks.*;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.entity.render.*;
import com.alaharranhonor.swem.gui.*;
import com.alaharranhonor.swem.items.SWEMSpawnEggItem;
import com.alaharranhonor.swem.particle.*;
import com.alaharranhonor.swem.util.registry.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
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
public class ClientModEventBusSubscriber {

    public static KeyBinding[] keyBindings;

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        DeferredWorkQueue.runLater(ClientModEventBusSubscriber::initLate);
        registerRenderers(event);
        setRenderLayers();
        registerKeybinds();
    }

    @SubscribeEvent
    public static void onParticleFactoryRegister(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(SWEMParticles.BAD.get(), BadParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(SWEMParticles.ECH.get(), EchParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(SWEMParticles.MEH.get(), MehParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(SWEMParticles.YAY.get(), YayParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(SWEMParticles.WOOT.get(), WootParticle.Factory::new);

    }

    public static void initLate() {
        ScreenManager.register(SWEMContainers.SWEM_HORSE_CONTAINER.get(), SWEMHorseInventoryScreen::new);
        ScreenManager.register(SWEMContainers.TACKBOX_CONTAINER.get(), TackBoxDefaultScreen::new);
        ScreenManager.register(SWEMContainers.CANTAZARITE_ANVIL_CONTAINER.get(), CantazariteAnvilScreen::new);
        ScreenManager.register(SWEMContainers.SADDLE_BAG_CONTAINER.get(), SaddlebagScreen::new);
        ScreenManager.register(SWEMContainers.BED_ROLL_CONTAINER.get(), BedrollScreen::new);
        ScreenManager.register(SWEMContainers.LOCKER_CONTAINER.get(), LockerScreen::new);
        ScreenManager.register(SWEMContainers.JUMP_CONTAINER.get(), JumpScreen::new);

        ItemModelsProperties.register(SWEMItems.AMETHYST_BOW.get(), new ResourceLocation("pull"), (p_239429_0_, p_239429_1_, p_239429_2_) -> {
            if (p_239429_2_ == null) {
                return 0.0F;
            } else {
                return p_239429_2_.getUseItem() != p_239429_0_ ? 0.0F : (float)(p_239429_0_.getUseDuration() - p_239429_2_.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemModelsProperties.register(SWEMItems.AMETHYST_BOW.get(), new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isUsingItem() && p_239428_2_.getUseItem() == p_239428_0_ ? 1.0F : 0.0F);
        ItemModelsProperties.register(SWEMItems.AMETHYST_SHIELD.get(), new ResourceLocation("blocking"), (p_239421_0_, p_239421_1_, p_239421_2_) -> {
            return p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == p_239421_0_ ? 1.0F : 0.0F;
        });
    }

    public static void registerRenderers(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(SWEMEntities.SWEM_HORSE_ENTITY.get(), SWEMHorseRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SWEMEntities.WORMIE_BOI_ENTITY.get(), WormieBoiRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SWEMEntities.HORSE_POOP_ENTITY.get(), PoopRender::new);
        ClientRegistry.bindTileEntityRenderer(SWEMTileEntities.TACK_BOX_TILE_ENTITY.get(), TackBoxRender::new);
        ClientRegistry.bindTileEntityRenderer(SWEMTileEntities.ONE_SADDLE_RACK_TILE_ENTITY.get(), OneSaddleRackRender::new);
        ClientRegistry.bindTileEntityRenderer(SWEMTileEntities.BRIDLE_RACK_TILE_ENTITY.get(), BridleRackRender::new);
        ClientRegistry.bindTileEntityRenderer(SWEMTileEntities.HORSE_ARMOR_RACK_TILE_ENTITY.get(), HorseArmorRackRender::new);
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
        RenderTypeLookup.setRenderLayer(SWEMBlocks.TIMOTHY_GRASS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.OAT_PLANT.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.ALFALFA_PLANT.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.WATER_TROUGH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.METAL_GRATE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.LIGHT_FRIENDLY_BARS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.MEDIUM_FRIENDLY_BARS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.DARK_FRIENDLY_BARS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.HORSE_PEE.get(), RenderType.cutout());

        // Jump blocks.
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_STANDARD_SCHOOLING.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_BRUSH_BOX.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_LOG.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_STAIR_DROP.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_HEDGE.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_COOP.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_WALL.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_WALL_MINI.get(), RenderType.translucent());
       // RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_CROSS_RAILS.get(), RenderType.translucent()());
       // RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_SWEDISH_RAILS.get(), RenderType.translucent()());
        //RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_RED_FLAG.get(), RenderType.translucent()());
        //RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_WHITE_FLAG.get(), RenderType.translucent()());
       // RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_RED_WHITE_FLAG.get(), RenderType.translucent()());
        //RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_NUMBERS.get(), RenderType.translucent()());

        RenderTypeLookup.setRenderLayer(SWEMBlocks.ACACIA_STALL_HORSE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.BIRCH_STALL_HORSE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.DARK_OAK_STALL_HORSE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUNGLE_STALL_HORSE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.OAK_STALL_HORSE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.SPRUCE_STALL_HORSE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.ACACIA_STALL_CARE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.BIRCH_STALL_CARE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.DARK_OAK_STALL_CARE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.JUNGLE_STALL_CARE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.OAK_STALL_CARE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SWEMBlocks.SPRUCE_STALL_CARE.get(), RenderType.cutout());

        for (DyeColor color : DyeColor.values()) {
            RenderTypeLookup.setRenderLayer(SWEMBlocks.ROLL_TOPS.get(color.getId()).get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.RAILS.get(color.getId()).get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.GROUND_POLES.get(color.getId()).get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.POLE_ON_BOXES_SMALL.get(color.getId()).get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.POLE_ON_BOXES_LARGE.get(color.getId()).get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.FANCY_PLANKS.get(color.getId()).get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.PLANKS.get(color.getId()).get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.PANELS_WAVE.get(color.getId()).get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.PANELS_STRIPE.get(color.getId()).get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.PANELS_ARROW.get(color.getId()).get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.FLOWER_BOXES.get(color.getId()).get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.CAVALETTIS.get(color.getId()).get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.WHEEL_BARROWS.get(color.getId()).get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.SEPARATORS.get(color.getId()).get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.PASTURE_GATES_HORSE.get(color.getId()).get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.PASTURE_GATES_CARE.get(color.getId()).get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.WEB_GUARDS_CARE.get(color.getId()).get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.WEB_GUARDS_HORSE.get(color.getId()).get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(SWEMBlocks.WEB_GUARDS_RIDER.get(color.getId()).get(), RenderType.cutout());
        }
    }

    public static void registerKeybinds() {
        keyBindings = new KeyBinding[5];

        keyBindings[0] = new KeyBinding("key.swem.horse.increment", GLFW.GLFW_KEY_H, "key.swem.category");
        keyBindings[1] = new KeyBinding("key.swem.horse.decrement", GLFW.GLFW_KEY_G, "key.swem.category");
        keyBindings[2] = new KeyBinding("key.swem.horse.toggle_bedroll", GLFW.GLFW_KEY_K, "key.swem.category");
        keyBindings[3] = new KeyBinding("key.swem.horse.toggle_flight", GLFW.GLFW_KEY_J, "key.swem.category");
        keyBindings[4] = new KeyBinding("key.swem.horse.toggle_wings", GLFW.GLFW_KEY_PERIOD, "key.swem.category");


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

        WaterTroughBlock waterTrough = SWEMBlocks.WATER_TROUGH.get();
        colors.register((state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getAverageWaterColor(reader, pos) : -1, waterTrough);

        colors.register((state, reader, pos, color) -> {
            return reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColors.getDefaultColor();
        }, SWEMBlocks.JUMP_HEDGE.get());

    }

    @SubscribeEvent
    public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event) {
        SWEMSpawnEggItem.initSpawnEggs();
    }



    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new SWEMSpawnEggItem(SWEMEntities.WORMIE_BOI_ENTITY, Color.parseColor("#bf7b05").getValue(), Color.parseColor("#663c02").getValue(), new Item.Properties().tab(SWEM.TAB)).setRegistryName("worm_spawn_egg"));
            event.getRegistry().register(new SWEMSpawnEggItem(SWEMEntities.SWEM_HORSE_ENTITY, Color.parseColor("#bf7b05").getValue(), Color.parseColor("#663c02").getValue(), new Item.Properties().tab(SWEM.TAB)).setRegistryName("swem_horse_spawn_egg"));
    }


}
