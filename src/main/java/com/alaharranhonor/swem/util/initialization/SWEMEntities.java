package com.alaharranhonor.swem.util.initialization;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.PoopEntity;
import com.alaharranhonor.swem.entities.RopeKnotEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entities.WormieBoiEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SWEMEntities {

	public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, SWEM.MOD_ID);

	public static void init(IEventBus modBus) {
		ENTITY_TYPES.register(modBus);
	}

	public static final RegistryObject<EntityType<SWEMHorseEntity>> SWEM_HORSE_ENTITY = ENTITY_TYPES.register("swem_horse",
			() -> EntityType.Builder.create(SWEMHorseEntity::new, EntityClassification.CREATURE)
					.size(1.73f, 1.99f) // Hitbox Size
					.build(new ResourceLocation(SWEM.MOD_ID, "swem_horse").toString())
	);
	public static final RegistryObject<EntityType<RopeKnotEntity>> ROPE_KNOT_ENTITY =
		ENTITY_TYPES.register("rope_knot",
				() -> EntityType.Builder.<RopeKnotEntity>create(RopeKnotEntity::new,EntityClassification.MISC)
						.disableSerialization()
						.size(0.5F, 0.5F)
						.trackingRange(10)
						.func_233608_b_(Integer.MAX_VALUE)
						.build(new ResourceLocation(SWEM.MOD_ID, "rope_knot").toString())
);

	public static final RegistryObject<EntityType<WormieBoiEntity>> WORMIE_BOI_ENTITY = ENTITY_TYPES.register("wormieboi",
			() -> EntityType.Builder.create(WormieBoiEntity::new, EntityClassification.CREATURE)
					.size(1.0f, 0.4f)
					.build(new ResourceLocation(SWEM.MOD_ID, "wormieboi").toString())
	);
	public static final RegistryObject<EntityType<PoopEntity>> HORSE_POOP_ENTITY = ENTITY_TYPES.register("horse_poop",
			() -> EntityType.Builder.create(PoopEntity::new, EntityClassification.MISC)
					.size(0.6f, 0.2f) // 0.186f
					.build(new ResourceLocation(SWEM.MOD_ID, "horse_poop").toString())
	);
}
