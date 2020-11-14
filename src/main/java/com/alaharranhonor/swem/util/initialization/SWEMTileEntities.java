package com.alaharranhonor.swem.util.initialization;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.tileentity.TackBoxTE;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SWEMTileEntities {

	public static DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, SWEM.MOD_ID);

	public static void init(IEventBus modBus) {
		TILE_ENTITY_TYPES.register(modBus);
	}

	public static final RegistryObject<TileEntityType<TackBoxTE>> TACK_BOX_TILE_ENTITY = TILE_ENTITY_TYPES.register("tack_box", () -> TileEntityType.Builder.create(TackBoxTE::new, SWEMBlocks.TACK_BOX.get()).build(null));
}
