package com.alaharranhonor.swem.util.registry;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.loot.GrassDropsModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SWEMLootModifiers {

	public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, SWEM.MOD_ID);

	public static void init(IEventBus modBus) {
		LOOT_MODIFIERS.register(modBus);
	}

	public static final RegistryObject<GrassDropsModifier.Serializer> GRASS_DROPS = LOOT_MODIFIERS.register("grass_drops", GrassDropsModifier.Serializer::new);
}
