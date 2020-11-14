package com.alaharranhonor.swem.util.initialization;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.container.SWEMHorseInventoryContainer;
import com.alaharranhonor.swem.container.TackBoxContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SWEMContainers {
	public static DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, SWEM.MOD_ID);

	public static void init(IEventBus modBus) {
		CONTAINER_TYPES.register(modBus);
	}

	public static final RegistryObject<ContainerType<SWEMHorseInventoryContainer>> SWEM_HORSE_CONTAINER = CONTAINER_TYPES.register("swem_horse_container",
			() -> IForgeContainerType.create(SWEMHorseInventoryContainer::new)
	);

	public static final RegistryObject<ContainerType<TackBoxContainer>> TACKBOX_CONTAINER = CONTAINER_TYPES.register("tackbox_container",
			() -> IForgeContainerType.create(TackBoxContainer::new)
	);
}
