package com.alaharranhonor.swem.util.initialization;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.rmi.registry.Registry;

@SuppressWarnings("unused")
public class SWEMBlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SWEM.MOD_ID);

	public static void init(IEventBus modBus) {
		BLOCKS.register(modBus);
	}


	public static final RegistryObject<Block> FUEL_BLOCK = BLOCKS.register("fuel_block", FuelBlock::new);
	public static final RegistryObject<Block> DARK_RUBBER_MAT = BLOCKS.register("dark_rubber_mat", RubberMatBase::new);
	public static final RegistryObject<Block> LIGHT_RUBBER_MAT = BLOCKS.register("light_rubber_mat", RubberMatBase::new);
	public static final RegistryObject<Block> MEDIUM_RUBBER_MAT = BLOCKS.register("medium_rubber_mat", RubberMatBase::new);
	public static final RegistryObject<Block> CANTAZARITE_BLOCK = BLOCKS.register("cantazarite_block", OreCraftedBase::new);
	public static final RegistryObject<OreBlock> CANTAZARITE_ORE = BLOCKS.register("cantazarite_ore", () -> new OreBlock(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<OreBlock> AMETHYST_ORE = BLOCKS.register("amethyst_ore", () -> new OreBlock(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<Block> YELLOW_CONE = BLOCKS.register("yellow_cone", ConeBase::new);
	public static final RegistryObject<Block> WHITE_CONE = BLOCKS.register("white_cone", ConeBase::new);
	public static final RegistryObject<Block> RED_CONE = BLOCKS.register("red_cone", ConeBase::new);
	public static final RegistryObject<Block> PURPLE_CONE = BLOCKS.register("purple_cone", ConeBase::new);
	public static final RegistryObject<Block> PINK_CONE = BLOCKS.register("pink_cone", ConeBase::new);
	public static final RegistryObject<Block> ORANGE_CONE = BLOCKS.register("orange_cone", ConeBase::new);
	public static final RegistryObject<Block> MAGENTA_CONE = BLOCKS.register("magenta_cone", ConeBase::new);
	public static final RegistryObject<Block> LIGHT_BLUE_CONE = BLOCKS.register("light_blue_cone", ConeBase::new);
	public static final RegistryObject<Block> GREY_CONE = BLOCKS.register("grey_cone", ConeBase::new);
	public static final RegistryObject<Block> LIME_CONE = BLOCKS.register("lime_cone", ConeBase::new);
	public static final RegistryObject<Block> BLUE_CONE = BLOCKS.register("blue_cone", ConeBase::new);
	public static final RegistryObject<Block> CYAN_CONE = BLOCKS.register("cyan_cone", ConeBase::new);
	public static final RegistryObject<Block> GREEN_CONE = BLOCKS.register("green_cone", ConeBase::new);
	public static final RegistryObject<Block> TIMOTHY_GRASS = BLOCKS.register("timothy_grass",
			() -> new TimothyGrass(Block.Properties.from(Blocks.WHEAT)));
	public static final RegistryObject<Block> ALFALFA_PLANT = BLOCKS.register("alfalfa_plant",
			() -> new TimothyGrass(Block.Properties.from(Blocks.WHEAT)));
	public static final RegistryObject<Block> OAT_PLANT = BLOCKS.register("oat_plant",
			() -> new TimothyGrass(Block.Properties.from(Blocks.WHEAT)));
	public static final RegistryObject<Block> QUALITY_BALE = BLOCKS.register("quality_bale",
			() -> new HayBlockBase(Block.Properties.from(Blocks.HAY_BLOCK)));
	public static final RegistryObject<Block> DARK_SHAVINGS_BLOCK = BLOCKS.register("dark_shavings_block",
			() -> new RegularFallingBlockBase(Block.Properties.from(Blocks.SNOW_BLOCK)));
	public static final RegistryObject<Block> DARK_SHAVINGS = BLOCKS.register("dark_shavings",
			() -> new Shavings(Block.Properties.from(Blocks.SNOW)));
	public static final RegistryObject<Block> LIGHT_SHAVINGS_BLOCK = BLOCKS.register("light_shavings_block",
			() -> new RegularFallingBlockBase(Block.Properties.from(Blocks.SNOW_BLOCK)));
	public static final RegistryObject<Block> LIGHT_SHAVINGS = BLOCKS.register("light_shavings",
			() -> new Shavings(Block.Properties.from(Blocks.SNOW)));
	public static final RegistryObject<Block> SOILED_SHAVINGS_BLOCK = BLOCKS.register("soiled_shavings_block",
			() -> new RegularFallingBlockBase(Block.Properties.from(Blocks.SNOW_BLOCK)));
	public static final RegistryObject<Block> SOILED_SHAVINGS = BLOCKS.register("soiled_shavings",
			() -> new Shavings(Block.Properties.from(Blocks.SNOW)));
	public static final RegistryObject<Block> RIDING_DOOR = BLOCKS.register("riding_door",
			() -> new DoorBase(Block.Properties.from(Blocks.OAK_FENCE_GATE)));
	public static final RegistryObject<Block> BLEACHER_SLAB = BLOCKS.register("bleacher",
			() -> new BleacherBase(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<Block> BLEACHER_WIREFRAME = BLOCKS.register("bleacher_wireframe",
			() -> new BleacherWireframeBase(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<Block> WESTERN_HITCHING_POST = BLOCKS.register("western_hitching_post",
			() -> new HitchingPostBase(HitchingPostBase.HitchingPostType.WESTERN, Block.Properties.create(Material.WOOD)));
	public static final RegistryObject<Block> ENGLISH_HITCHING_POST = BLOCKS.register("english_hitching_post",
			() -> new HitchingPostBase(HitchingPostBase.HitchingPostType.ENGLISH, Block.Properties.create(Material.WOOD)));
	public static final RegistryObject<Block> PASTURE_HITCHING_POST = BLOCKS.register("pasture_hitching_post",
			() -> new HitchingPostBase(HitchingPostBase.HitchingPostType.PASTURE, Block.Properties.create(Material.WOOD)));
	public static final RegistryObject<Block> HORSE_POO = BLOCKS.register("pile_of_horse_poo", () -> new HorsePoopBlock(Block.Properties.create(Material.ORGANIC)));
	public static final RegistryObject<Block> WESTERN_POLE = BLOCKS.register("western_pole", () -> new WesternPoleBlock(Block.Properties.create(Material.WOOD)));
	public static final RegistryObject<Block> INVISIBLE_GLOW_BLOCK = BLOCKS.register("invisible_glow_block", () -> new InvisibleGlowBlock(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<Block> WESTERN_FENCE = BLOCKS.register("western_fence", () -> new FenceBaseBlock(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<Block> PASTURE_FENCE = BLOCKS.register("pasture_fence", () -> new FenceBaseBlock(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<Block> HALF_BARREL = BLOCKS.register("half_barrel", () -> new HalfBarrelBlock(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<Block> TACK_BOX = BLOCKS.register("tack_box", () -> new TackBoxBlock(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<RiderDoorBlock> SIMPLE_RIDER_DOOR_ORANGE = BLOCKS.register("simple_rider_door_orange", () -> new RiderDoorBlock(Block.Properties.create(Material.WOOD).notSolid()));
	public static final RegistryObject<HorseDoorBlock> ACACIA_STALL_HORSE = BLOCKS.register("acacia_stall_horse", () -> new HorseDoorBlock(Block.Properties.create(Material.WOOD).notSolid()));
	public static final RegistryObject<HorseDoorBlock> BIRCH_STALL_HORSE = BLOCKS.register("birch_stall_horse", () -> new HorseDoorBlock(Block.Properties.create(Material.WOOD).notSolid()));
	public static final RegistryObject<HorseDoorBlock> DARK_OAK_STALL_HORSE = BLOCKS.register("dark_oak_stall_horse", () -> new HorseDoorBlock(Block.Properties.create(Material.WOOD).notSolid()));
	public static final RegistryObject<HorseDoorBlock> JUNGLE_STALL_HORSE = BLOCKS.register("jungle_stall_horse", () -> new HorseDoorBlock(Block.Properties.create(Material.WOOD).notSolid()));
	public static final RegistryObject<HorseDoorBlock> OAK_STALL_HORSE = BLOCKS.register("oak_stall_horse", () -> new HorseDoorBlock(Block.Properties.create(Material.WOOD).notSolid()));
	public static final RegistryObject<HorseDoorBlock> SPRUCE_STALL_HORSE = BLOCKS.register("spruce_stall_horse", () -> new HorseDoorBlock(Block.Properties.create(Material.WOOD).notSolid()));
	public static final RegistryObject<CareDoorBlock> ACACIA_STALL_CARE = BLOCKS.register("acacia_stall_care", () -> new CareDoorBlock(Block.Properties.create(Material.WOOD).notSolid()));
	public static final RegistryObject<CareDoorBlock> BIRCH_STALL_CARE = BLOCKS.register("birch_stall_care", () -> new CareDoorBlock(Block.Properties.create(Material.WOOD).notSolid()));
	public static final RegistryObject<CareDoorBlock> DARK_OAK_STALL_CARE = BLOCKS.register("dark_oak_stall_care", () -> new CareDoorBlock(Block.Properties.create(Material.WOOD).notSolid()));
	public static final RegistryObject<CareDoorBlock> JUNGLE_STALL_CARE = BLOCKS.register("jungle_stall_care", () -> new CareDoorBlock(Block.Properties.create(Material.WOOD).notSolid()));
	public static final RegistryObject<CareDoorBlock> OAK_STALL_CARE = BLOCKS.register("oak_stall_care", () -> new CareDoorBlock(Block.Properties.create(Material.WOOD).notSolid()));
	public static final RegistryObject<CareDoorBlock> SPRUCE_STALL_CARE = BLOCKS.register("spruce_stall_care", () -> new CareDoorBlock(Block.Properties.create(Material.WOOD).notSolid()));
	public static final RegistryObject<SlowFeederBlock> SLOW_FEEDER = BLOCKS.register("slow_feeder", () -> new SlowFeederBlock(Block.Properties.create(Material.IRON)));

	// Block Items
	public static final RegistryObject<Item> FUEL_BLOCK_ITEM = SWEMItems.ITEMS.register("fuel_block",
			() -> new FuelBlockItemBase(FUEL_BLOCK.get(), 1600));
	public static final RegistryObject<Item> CANTAZARITE_BLOCK_ITEM = SWEMItems.ITEMS.register("cantazarite_block",
			() -> new BlockItemBase(CANTAZARITE_BLOCK.get()));
	public static final RegistryObject<Item> DARK_RUBBER_MAT_ITEM = SWEMItems.ITEMS.register("dark_rubber_mat",
			() -> new BlockItemBase(DARK_RUBBER_MAT.get()));
	public static final RegistryObject<Item> LIGHT_RUBBER_MAT_ITEM = SWEMItems.ITEMS.register("light_rubber_mat",
			() -> new BlockItemBase(LIGHT_RUBBER_MAT.get()));
	public static final RegistryObject<Item> MEDIUM_RUBBER_MAT_ITEM = SWEMItems.ITEMS.register("medium_rubber_mat",
			() -> new BlockItemBase(MEDIUM_RUBBER_MAT.get()));
	public static final RegistryObject<Item> CANTAZARITE_ORE_ITEM = SWEMItems.ITEMS.register("cantazarite_ore",
			() -> new BlockItemBase(CANTAZARITE_ORE.get()));
	public static final RegistryObject<Item> AMETHYST_ORE_ITEM = SWEMItems.ITEMS.register("amethyst_ore",
			() -> new BlockItemBase(AMETHYST_ORE.get()));
	public static final RegistryObject<Item> YELLOW_CONE_ITEM = SWEMItems.ITEMS.register("yellow_cone",
			() -> new ConeBlockItem(YELLOW_CONE.get()));
	public static final RegistryObject<Item> WHITE_CONE_ITEM = SWEMItems.ITEMS.register("white_cone",
			() -> new ConeBlockItem(WHITE_CONE.get()));
	public static final RegistryObject<Item> RED_CONE_ITEM = SWEMItems.ITEMS.register("red_cone",
			() -> new ConeBlockItem(RED_CONE.get()));
	public static final RegistryObject<Item> PURPLE_CONE_ITEM = SWEMItems.ITEMS.register("purple_cone",
			() -> new ConeBlockItem(PURPLE_CONE.get()));
	public static final RegistryObject<Item> PINK_CONE_ITEM = SWEMItems.ITEMS.register("pink_cone",
			() -> new ConeBlockItem(PINK_CONE.get()));
	public static final RegistryObject<Item> ORANGE_CONE_ITEM = SWEMItems.ITEMS.register("orange_cone",
			() -> new ConeBlockItem(ORANGE_CONE.get()));
	public static final RegistryObject<Item> MAGENTA_CONE_ITEM = SWEMItems.ITEMS.register("magenta_cone",
			() -> new ConeBlockItem(MAGENTA_CONE.get()));
	public static final RegistryObject<Item> LIGHT_BLUE_CONE_ITEM = SWEMItems.ITEMS.register("light_blue_cone",
			() -> new ConeBlockItem(LIGHT_BLUE_CONE.get()));
	public static final RegistryObject<Item> GREY_CONE_ITEM = SWEMItems.ITEMS.register("grey_cone",
			() -> new ConeBlockItem(GREY_CONE.get()));
	public static final RegistryObject<Item> LIME_CONE_ITEM = SWEMItems.ITEMS.register("lime_cone",
			() -> new ConeBlockItem(LIME_CONE.get()));
	public static final RegistryObject<Item> GREEN_CONE_ITEM = SWEMItems.ITEMS.register("green_cone",
			() -> new ConeBlockItem(GREEN_CONE.get()));
	public static final RegistryObject<Item> CYAN_CONE_ITEM = SWEMItems.ITEMS.register("cyan_cone",
			() -> new ConeBlockItem(CYAN_CONE.get()));
	public static final RegistryObject<Item> BLUE_CONE_ITEM = SWEMItems.ITEMS.register("blue_cone",
			() -> new ConeBlockItem(BLUE_CONE.get()));
	public static final RegistryObject<Item> QUALITY_BALE_ITEM = SWEMItems.ITEMS.register("quality_bale",
			() -> new ConeBlockItem(QUALITY_BALE.get()));
	public static final RegistryObject<Item> TIMOTHY_SEEDS = SWEMItems.ITEMS.register("timothy_seeds",
			() -> new BlockItemBase(TIMOTHY_GRASS.get()));
	public static final RegistryObject<Item> ALFALFA_SEEDS = SWEMItems.ITEMS.register("alfalfa_seeds",
			() -> new BlockItemBase(ALFALFA_PLANT.get()));
	public static final RegistryObject<Item> OAT_SEEDS = SWEMItems.ITEMS.register("oat_seeds",
			() -> new BlockItemBase(OAT_PLANT.get()));
	public static final RegistryObject<Item> DARK_SHAVINGS_BLOCK_ITEM = SWEMItems.ITEMS.register("dark_shavings_block",
			() -> new BlockItemBase(DARK_SHAVINGS_BLOCK.get()));
	public static final RegistryObject<Item> DARK_SHAVINGS_ITEM = SWEMItems.ITEMS.register("dark_shavings",
			() -> new ShavingsItem(DARK_SHAVINGS.get()));
	public static final RegistryObject<Item> LIGHT_SHAVINGS_BLOCK_ITEM = SWEMItems.ITEMS.register("light_shavings_block",
			() -> new BlockItemBase(LIGHT_SHAVINGS_BLOCK.get()));
	public static final RegistryObject<Item> LIGHT_SHAVINGS_ITEM = SWEMItems.ITEMS.register("light_shavings",
			() -> new ShavingsItem(LIGHT_SHAVINGS.get()));
	public static final RegistryObject<Item> SOILED_SHAVINGS_BLOCK_ITEM = SWEMItems.ITEMS.register("soiled_shavings_block",
			() -> new BlockItemBase(SOILED_SHAVINGS_BLOCK.get()));
	public static final RegistryObject<Item> SOILED_SHAVINGS_ITEM = SWEMItems.ITEMS.register("soiled_shavings",
			() -> new BlockItemBase(SOILED_SHAVINGS.get()));
	public static final RegistryObject<Item> RIDING_DOOR_ITEM = SWEMItems.ITEMS.register("riding_door",
			() -> new BlockItemBase(RIDING_DOOR.get()));
	public static final RegistryObject<Item> BLEACHER_SLAB_ITEM = SWEMItems.ITEMS.register("bleacher",
			() -> new BlockItemBase(BLEACHER_SLAB.get()));
	public static final RegistryObject<Item> WESTERN_HITCHING_POST_ITEM = SWEMItems.ITEMS.register("western_hitching_post",
			() -> new BlockItemBase(WESTERN_HITCHING_POST.get()));
	public static final RegistryObject<Item> ENGLISH_HITCHING_POST_ITEM = SWEMItems.ITEMS.register("english_hitching_post",
			() -> new BlockItemBase(ENGLISH_HITCHING_POST.get()));
	public static final RegistryObject<Item> PASTURE_HITCHING_POST_ITEM = SWEMItems.ITEMS.register("pasture_hitching_post",
			() -> new BlockItemBase(PASTURE_HITCHING_POST.get()));
	public static final RegistryObject<Item> WESTERN_POLE_ITEM = SWEMItems.ITEMS.register("western_pole",
			() -> new BlockItemBase(WESTERN_POLE.get()));
	public static final RegistryObject<Item> WESTERN_FENCE_ITEM = SWEMItems.ITEMS.register("western_fence", () -> new BlockItemBase(WESTERN_FENCE.get()));
	public static final RegistryObject<Item> PASTURE_FENCE_ITEM = SWEMItems.ITEMS.register("pasture_fence", () -> new BlockItemBase(PASTURE_FENCE.get()));
	public static final RegistryObject<Item> HALF_BARREL_ITEM = SWEMItems.ITEMS.register("half_barrel", () -> new BlockItemBase(HALF_BARREL.get()));
	public static final RegistryObject<Item> TACK_BOX_ITEM = SWEMItems.ITEMS.register("tack_box", () -> new TackBoxBlockItem(TACK_BOX.get()));
	public static final RegistryObject<Item> SIMPLE_RIDER_DOOR_ORANGE_ITEM = SWEMItems.ITEMS.register("simple_rider_door_orange", () -> new BlockItemBase(SIMPLE_RIDER_DOOR_ORANGE.get()));
	public static final RegistryObject<Item> ACACIA_STALL_HORSE_ITEM = SWEMItems.ITEMS.register("acacia_stall_horse", () -> new BlockItemBase(ACACIA_STALL_HORSE.get()));
	public static final RegistryObject<Item> BIRCH_STALL_HORSE_ITEM = SWEMItems.ITEMS.register("birch_stall_horse", () -> new BlockItemBase(BIRCH_STALL_HORSE.get()));
	public static final RegistryObject<Item> DARK_OAK_STALL_HORSE_ITEM = SWEMItems.ITEMS.register("dark_oak_stall_horse", () -> new BlockItemBase(DARK_OAK_STALL_HORSE.get()));
	public static final RegistryObject<Item> JUNGLE_STALL_HORSE_ITEM = SWEMItems.ITEMS.register("jungle_stall_horse", () -> new BlockItemBase(JUNGLE_STALL_HORSE.get()));
	public static final RegistryObject<Item> OAK_STALL_HORSE_ITEM = SWEMItems.ITEMS.register("oak_stall_horse", () -> new BlockItemBase(OAK_STALL_HORSE.get()));
	public static final RegistryObject<Item> SPRUCE_STALL_HORSE_ITEM = SWEMItems.ITEMS.register("spruce_stall_horse", () -> new BlockItemBase(SPRUCE_STALL_HORSE.get()));
	public static final RegistryObject<Item> ACACIA_STALL_CARE_ITEM = SWEMItems.ITEMS.register("acacia_stall_care", () -> new BlockItemBase(ACACIA_STALL_CARE.get()));
	public static final RegistryObject<Item> BIRCH_STALL_CARE_ITEM = SWEMItems.ITEMS.register("birch_stall_care", () -> new BlockItemBase(BIRCH_STALL_CARE.get()));
	public static final RegistryObject<Item> DARK_OAK_STALL_CARE_ITEM = SWEMItems.ITEMS.register("dark_oak_stall_care", () -> new BlockItemBase(DARK_OAK_STALL_CARE.get()));
	public static final RegistryObject<Item> JUNGLE_STALL_CARE_ITEM = SWEMItems.ITEMS.register("jungle_stall_care", () -> new BlockItemBase(JUNGLE_STALL_CARE.get()));
	public static final RegistryObject<Item> OAK_STALL_CARE_ITEM = SWEMItems.ITEMS.register("oak_stall_care", () -> new BlockItemBase(OAK_STALL_CARE.get()));
	public static final RegistryObject<Item> SPRUCE_STALL_CARE_ITEM = SWEMItems.ITEMS.register("spruce_stall_care", () -> new BlockItemBase(SPRUCE_STALL_CARE.get()));
	public static final RegistryObject<Item> SLOW_FEEDER_ITEM = SWEMItems.ITEMS.register("slow_feeder", () -> new BlockItemBase(SLOW_FEEDER.get()));
}
