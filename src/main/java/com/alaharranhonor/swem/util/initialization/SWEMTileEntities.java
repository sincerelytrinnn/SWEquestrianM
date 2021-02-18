package com.alaharranhonor.swem.util.initialization;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.jumps.JumpStandardBlock;
import com.alaharranhonor.swem.tileentity.*;
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
	public static final RegistryObject<TileEntityType<CantazariteAnvilTE>> CANTAZARITE_ANVIL_TILE_ENTITY = TILE_ENTITY_TYPES.register("cantazarite_anvil", () -> TileEntityType.Builder.create(CantazariteAnvilTE::new, SWEMBlocks.CANTAZARITE_ANVIL.get()).build(null));
	public static final RegistryObject<TileEntityType<JumpTE>> JUMP_TILE_ENTITY = TILE_ENTITY_TYPES.register("jump_tile_entity", () -> TileEntityType.Builder.create(JumpTE::new, SWEMBlocks.JUMP_STANDARD_SCHOOLING.get(), SWEMBlocks.JUMP_STANDARD_RADIAL.get(), SWEMBlocks.JUMP_STANDARD_VERTICAL_SLAT.get(), SWEMBlocks.JUMP_STANDARD_NONE.get()).build(null));
	public static final RegistryObject<TileEntityType<JumpPasserTE>> JUMP_PASSER_TILE_ENTITY = TILE_ENTITY_TYPES.register("jump_passer_tile_entity", () -> TileEntityType.Builder.create(JumpPasserTE::new, SWEMBlocks.JUMP_STANDARD_SCHOOLING.get(), SWEMBlocks.JUMP_STANDARD_RADIAL.get(), SWEMBlocks.JUMP_STANDARD_VERTICAL_SLAT.get(), SWEMBlocks.JUMP_PLANK_FANCY.get(), SWEMBlocks.JUMP_PLANK.get(), SWEMBlocks.JUMP_NUMBERS.get(), SWEMBlocks.JUMP_RED_WHITE_FLAG.get(), SWEMBlocks.JUMP_WHITE_FLAG.get(), SWEMBlocks.JUMP_RED_FLAG.get(), SWEMBlocks.JUMP_SWEDISH_RAILS.get(), SWEMBlocks.JUMP_CROSS_RAILS.get(), SWEMBlocks.JUMP_PANELS.get(), SWEMBlocks.JUMP_GROUND_POLE.get(), SWEMBlocks.JUMP_WALL_MINI.get(), SWEMBlocks.JUMP_WALL.get(), SWEMBlocks.JUMP_ROLL_TOP.get(), SWEMBlocks.JUMP_COOP.get(), SWEMBlocks.JUMP_FLOWER_BOX.get(), SWEMBlocks.JUMP_BRUSH_BOX.get(), SWEMBlocks.JUMP_HEDGE.get(), SWEMBlocks.JUMP_STAIR_DROP.get(), SWEMBlocks.JUMP_POLE_ON_BOX.get(), SWEMBlocks.JUMP_CAVALETTI.get(), SWEMBlocks.JUMP_LOG.get(), SWEMBlocks.JUMP_RAIL.get()).build(null));
}
