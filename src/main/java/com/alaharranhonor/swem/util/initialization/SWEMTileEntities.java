package com.alaharranhonor.swem.util.initialization;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.jumps.JumpBlock;
import com.alaharranhonor.swem.blocks.jumps.JumpStandardBlock;
import com.alaharranhonor.swem.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SWEMTileEntities {

	public static DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, SWEM.MOD_ID);

	public static void init(IEventBus modBus) {
		TILE_ENTITY_TYPES.register(modBus);
	}

	public static final RegistryObject<TileEntityType<TackBoxTE>> TACK_BOX_TILE_ENTITY = TILE_ENTITY_TYPES.register("tack_box", () -> TileEntityType.Builder.create(TackBoxTE::new, SWEMBlocks.TACK_BOX.get()).build(null));
	public static final RegistryObject<TileEntityType<OneSaddleRackTE>> ONE_SADDLE_RACK_TILE_ENTITY = TILE_ENTITY_TYPES.register("one_saddle_rack", () -> TileEntityType.Builder.create(OneSaddleRackTE::new, SWEMBlocks.ONE_SADDLE_RACK.get()).build(null));
	public static final RegistryObject<TileEntityType<BridleRackTE>> BRIDLE_RACK_TILE_ENTITY = TILE_ENTITY_TYPES.register("bridle_rack", () -> TileEntityType.Builder.create(BridleRackTE::new, SWEMBlocks.BRIDLE_RACK.get()).build(null));
	public static final RegistryObject<TileEntityType<WheelBarrowTE>> WHEEL_BARROW_TILE_ENTITY = TILE_ENTITY_TYPES.register("wheel_barrow", () -> TileEntityType.Builder.create(WheelBarrowTE::new, SWEMBlocks.WHEEL_BARROWS.stream().map(RegistryObject::get).collect(Collectors.toList()).toArray(new Block[SWEMBlocks.WHEEL_BARROWS.size()])).build(null));
	public static final RegistryObject<TileEntityType<CantazariteAnvilTE>> CANTAZARITE_ANVIL_TILE_ENTITY = TILE_ENTITY_TYPES.register("cantazarite_anvil", () -> TileEntityType.Builder.create(CantazariteAnvilTE::new, SWEMBlocks.CANTAZARITE_ANVIL.get()).build(null));
	public static final RegistryObject<TileEntityType<JumpTE>> JUMP_TILE_ENTITY = TILE_ENTITY_TYPES.register("jump_tile_entity", () -> TileEntityType.Builder.create(JumpTE::new, SWEMBlocks.JUMP_STANDARD_SCHOOLING.get(), SWEMBlocks.JUMP_STANDARD_RADIAL.get(), SWEMBlocks.JUMP_STANDARD_VERTICAL_SLAT.get(), SWEMBlocks.JUMP_STANDARD_NONE.get()).build(null));
	public static final RegistryObject<TileEntityType<JumpPasserTE>> JUMP_PASSER_TILE_ENTITY = TILE_ENTITY_TYPES.register("jump_passer_tile_entity", () -> TileEntityType.Builder.create(JumpPasserTE::new, getAllJumpBlocks()).build(null));
	public static final RegistryObject<TileEntityType<LockerTE>> LOCKER_TILE_ENTITY = TILE_ENTITY_TYPES.register("locker", () -> TileEntityType.Builder.create(LockerTE::new, SWEMBlocks.LOCKERS.stream().map(RegistryObject::get).collect(Collectors.toList()).toArray(new Block[SWEMBlocks.LOCKERS.size()])).build(null));



	private static Block[] getAllJumpBlocks() {

		Stream<RegistryObject<JumpBlock>> blocks = Stream.of(SWEMBlocks.JUMP_NUMBERS,
			SWEMBlocks.JUMP_RED_FLAG,
			SWEMBlocks.JUMP_WHITE_FLAG,
			SWEMBlocks.JUMP_RED_WHITE_FLAG,
			SWEMBlocks.JUMP_WALL,
			SWEMBlocks.JUMP_WALL_MINI,
			SWEMBlocks.JUMP_SWEDISH_RAILS,
			SWEMBlocks.JUMP_CROSS_RAILS,
			SWEMBlocks.JUMP_COOP,
			SWEMBlocks.JUMP_BRUSH_BOX,
			SWEMBlocks.JUMP_HEDGE,
			SWEMBlocks.JUMP_STAIR_DROP,
			SWEMBlocks.JUMP_LOG
		);

		blocks = Stream.concat(blocks, SWEMBlocks.PLANKS.stream());
		blocks = Stream.concat(blocks, SWEMBlocks.FANCY_PLANKS.stream());
		blocks = Stream.concat(blocks, SWEMBlocks.PANELS_ARROW.stream());
		blocks = Stream.concat(blocks, SWEMBlocks.PANELS_STRIPE.stream());
		blocks = Stream.concat(blocks, SWEMBlocks.PANELS_WAVE.stream());
		blocks = Stream.concat(blocks, SWEMBlocks.GROUND_POLES.stream());
		blocks = Stream.concat(blocks, SWEMBlocks.ROLL_TOPS.stream());
		blocks = Stream.concat(blocks, SWEMBlocks.FLOWER_BOXES.stream());
		blocks = Stream.concat(blocks, SWEMBlocks.POLE_ON_BOXES_LARGE.stream());
		blocks = Stream.concat(blocks, SWEMBlocks.POLE_ON_BOXES_SMALL.stream());
		blocks = Stream.concat(blocks, SWEMBlocks.CAVALETTIS.stream());
		blocks = Stream.concat(blocks, SWEMBlocks.RAILS.stream());

		Stream<JumpBlock> jumpBlockStream = blocks.map(RegistryObject::get);

		List<JumpBlock> jumpBlocks = jumpBlockStream.collect(Collectors.toList());

		Stream<JumpBlock> jumpBlockStream1 = jumpBlocks.stream();

		return jumpBlockStream1.collect(Collectors.toList()).toArray(new Block[jumpBlocks.size()]);
	}


}
