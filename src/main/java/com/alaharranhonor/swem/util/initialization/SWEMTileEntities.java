package com.alaharranhonor.swem.util.initialization;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.tileentity.BridleRackTE;
import com.alaharranhonor.swem.tileentity.OneSaddleRackTE;
import com.alaharranhonor.swem.tileentity.TackBoxTE;
import com.alaharranhonor.swem.tileentity.WheelBarrowTE;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;

public class SWEMTileEntities {

	public static DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, SWEM.MOD_ID);

	public static void init(IEventBus modBus) {
		TILE_ENTITY_TYPES.register(modBus);
	}

	public static final RegistryObject<TileEntityType<TackBoxTE>> TACK_BOX_TILE_ENTITY = TILE_ENTITY_TYPES.register("tack_box", () -> TileEntityType.Builder.create(TackBoxTE::new, SWEMBlocks.TACK_BOX.get()).build(null));
	public static final RegistryObject<TileEntityType<OneSaddleRackTE>> ONE_SADDLE_RACK_TILE_ENTITY = TILE_ENTITY_TYPES.register("one_saddle_rack", () -> TileEntityType.Builder.create(OneSaddleRackTE::new, SWEMBlocks.ONE_SADDLE_RACK.get()).build(null));
	public static final RegistryObject<TileEntityType<BridleRackTE>> BRIDLE_RACK_TILE_ENTITY = TILE_ENTITY_TYPES.register("bridle_rack", () -> TileEntityType.Builder.create(BridleRackTE::new, SWEMBlocks.BRIDLE_RACK.get()).build(null));
	public static final RegistryObject<TileEntityType<WheelBarrowTE>> WHEEL_BARROW_TILE_ENTITY = TILE_ENTITY_TYPES.register("wheel_barrow", () -> TileEntityType.Builder.create(WheelBarrowTE::new, SWEMBlocks.WHEEL_BARROWS.stream().map((block) -> block.get()).collect(Collectors.toList()).toArray(new Block[SWEMBlocks.WHEEL_BARROWS.size()])).build(null));
}
