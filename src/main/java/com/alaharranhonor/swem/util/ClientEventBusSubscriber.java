package com.alaharranhonor.swem.util;

//import com.alaharranhonor.swem.entity.render.SWEMHorseRender;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entity.render.SWEMHorseRender;
import com.alaharranhonor.swem.init.SWLBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import com.alaharranhonor.swem.SWEM;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        RenderingRegistry.registerEntityRenderingHandler(RegistryHandler.SWEM_HORSE_ENTITY.get(), SWEMHorseRender::new);
        GlobalEntityTypeAttributes.put(RegistryHandler.SWEM_HORSE_ENTITY.get(), LivingEntity.func_233639_cI_().func_233813_a_()/*(or your own)*/);
        RenderTypeLookup.setRenderLayer(RegistryHandler.TIMOTHY_GRASS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(RegistryHandler.OAT_PLANT.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(RegistryHandler.ALFALFA_PLANT.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(RegistryHandler.RIDING_DOOR.get(), RenderType.getCutout());
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        SWLBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .forEach(block -> {
                    final Item.Properties properties = new Item.Properties().group(SWEM.SWLMTAB);
                    final BlockItem blockItem = new BlockItem(block, properties);
                    blockItem.setRegistryName(block.getRegistryName());
                    registry.register(blockItem);
                });
    }
}