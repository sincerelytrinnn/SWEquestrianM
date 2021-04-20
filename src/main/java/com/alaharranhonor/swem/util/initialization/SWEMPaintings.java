package com.alaharranhonor.swem.util.initialization;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SWEMPaintings {

	public static final DeferredRegister<PaintingType> PAINTINGS = DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, SWEM.MOD_ID);

	public static void init(IEventBus modBus) {
		PAINTINGS.register(modBus);
	}

	public static final RegistryObject<PaintingType> LUCY_REAR = PAINTINGS.register("lucy_rear_oak", () -> new PaintingType(64, 48));
	public static final RegistryObject<PaintingType> LUCY_SMILE = PAINTINGS.register("lucy_smile_oak", () -> new PaintingType(16, 16));
	public static final RegistryObject<PaintingType> LUCY_WITH_BARN = PAINTINGS.register("lucy_with_barn_oak", () -> new PaintingType(16, 32));
	public static final RegistryObject<PaintingType> LUCY_LUNGE = PAINTINGS.register("lucy_lunge_oak", () -> new PaintingType(32, 32));
	public static final RegistryObject<PaintingType> LUCY_IN_LAKE = PAINTINGS.register("lucy_in_lake_oak", () -> new PaintingType(32, 16));
	public static final RegistryObject<PaintingType> LUCY_CHARGING = PAINTINGS.register("lucy_charging_oak", () -> new PaintingType(16, 32));
	public static final RegistryObject<PaintingType> LUCY_AND_JESSIE = PAINTINGS.register("lucy_and_jessie_oak", () -> new PaintingType(64, 64));



}
