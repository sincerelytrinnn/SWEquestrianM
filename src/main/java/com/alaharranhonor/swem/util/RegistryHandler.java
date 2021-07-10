package com.alaharranhonor.swem.util;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.util.registry.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryHandler {

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
        return setup(entry, new ResourceLocation(SWEM.MOD_ID, name));
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
        entry.setRegistryName(registryName);
        return entry;
    }

    public static void init(IEventBus modBus){
        SWEMItems.init(modBus);
        SWEMBlocks.init(modBus);
        SWEMEntities.init(modBus);
        SWEMContainers.init(modBus);
        SWEMParticles.init(modBus);
        SWEMTileEntities.init(modBus);
        SWEMPaintings.init(modBus);
    }

}
