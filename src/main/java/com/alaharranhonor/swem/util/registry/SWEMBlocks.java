package com.alaharranhonor.swem.util.registry;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.*;
import com.alaharranhonor.swem.blocks.BarrelBlock;
import com.alaharranhonor.swem.blocks.jumps.JumpBlock;
import com.alaharranhonor.swem.blocks.jumps.JumpControllerBlock;
import com.alaharranhonor.swem.blocks.jumps.JumpStandardBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraft.block.Blocks.TRIPWIRE_HOOK;

@SuppressWarnings("unused")
public class SWEMBlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SWEM.MOD_ID);

	public static void init(IEventBus modBus) {
		BLOCKS.register(modBus);
	}

	private static <T extends Block> RegistryObject<T> register(String name, Supplier<? extends T> sup) {
		return register(name, sup, SWEMBlocks::itemDefault);
	}

	private static <T extends Block> RegistryObject<T> register(String name, Supplier<? extends T> sup, Function<RegistryObject<T>, Supplier<? extends Item>> itemCreator) {
		RegistryObject<T> ret = registerNoItem(name, sup);
		SWEMItems.ITEMS.register(name, itemCreator.apply(ret));
		return ret;
	}

	private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<? extends T> sup) {
		return BLOCKS.register(name, sup);
	}

	private static Supplier<BlockItem> itemDefault(final RegistryObject<? extends Block> block) {
		return item(block, SWEM.TAB);
	}

	private static Supplier<BlockItem> item(final RegistryObject<? extends Block> block, final ItemGroup itemGroup) {
		return () -> new BlockItem(block.get(), new Item.Properties().tab(itemGroup));
	}

	public static final RegistryObject<Block> CHARCOAL_BLOCK = BLOCKS.register("charcoal_block", () -> new Block(AbstractBlock.Properties.copy(Blocks.COAL_BLOCK)));
	public static final RegistryObject<Block> FUEL_BLOCK = BLOCKS.register("fuel_block", FuelBlock::new);
	public static final RegistryObject<Block> DARK_RUBBER_MAT = BLOCKS.register("dark_rubber_mat", RubberMatBase::new);
	public static final RegistryObject<Block> LIGHT_RUBBER_MAT = BLOCKS.register("light_rubber_mat", RubberMatBase::new);
	public static final RegistryObject<Block> MEDIUM_RUBBER_MAT = BLOCKS.register("medium_rubber_mat", RubberMatBase::new);
	public static final RegistryObject<Block> CANTAZARITE_BLOCK = BLOCKS.register("cantazarite_block", OreCraftedBase::new);
	public static final RegistryObject<OreBlock> CANTAZARITE_ORE = BLOCKS.register("cantazarite_ore", () -> new OreBlock(AbstractBlock.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(1)));
	public static final RegistryObject<OreBlock> AMETHYST_ORE = BLOCKS.register("amethyst_ore", () -> new OreBlock(AbstractBlock.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(2)));
	public static final RegistryObject<Block> TIMOTHY_GRASS = BLOCKS.register("timothy_grass",
			() -> new TimothyGrass(AbstractBlock.Properties.copy(Blocks.WHEAT)));
	public static final RegistryObject<Block> ALFALFA_PLANT = BLOCKS.register("alfalfa_plant",
			() -> new AlfalfaPlant(AbstractBlock.Properties.copy(Blocks.WHEAT)));
	public static final RegistryObject<Block> OAT_PLANT = BLOCKS.register("oat_plant",
			() -> new OatPlant(AbstractBlock.Properties.copy(Blocks.WHEAT)));
	public static final RegistryObject<Block> QUALITY_BALE = BLOCKS.register("quality_bale",
			() -> new HayBlockBase(AbstractBlock.Properties.copy(Blocks.HAY_BLOCK)));
	public static final RegistryObject<Block> DARK_SHAVINGS = BLOCKS.register("dark_shavings",
			() -> new Shavings(AbstractBlock.Properties.of(new Material.Builder(MaterialColor.SNOW).noCollider().notSolidBlocking().nonSolid().destroyOnPush().replaceable().build()).randomTicks().strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.SNOW)));
	public static final RegistryObject<Block> LIGHT_SHAVINGS = BLOCKS.register("light_shavings",
			() -> new Shavings(AbstractBlock.Properties.of(new Material.Builder(MaterialColor.SNOW).noCollider().notSolidBlocking().nonSolid().destroyOnPush().replaceable().build()).randomTicks().strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.SNOW)));
	public static final RegistryObject<Block> SOILED_SHAVINGS = BLOCKS.register("soiled_shavings",
			() -> new Shavings(AbstractBlock.Properties.of(new Material.Builder(MaterialColor.SNOW).noCollider().notSolidBlocking().nonSolid().destroyOnPush().replaceable().build()).randomTicks().strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.SNOW)));
	public static final RegistryObject<Block> BLEACHER_SLAB = BLOCKS.register("bleacher",
			() -> new BleacherBase(AbstractBlock.Properties.of(Material.METAL)));
	public static final RegistryObject<Block> BLEACHER_WIREFRAME = BLOCKS.register("bleacher_wireframe",
			() -> new BleacherWireframeBase(AbstractBlock.Properties.of(Material.METAL)));
	public static final RegistryObject<Block> WESTERN_HITCHING_POST = BLOCKS.register("western_hitching_post",
			() -> new HitchingPostBase(HitchingPostBase.HitchingPostType.WESTERN, AbstractBlock.Properties.of(Material.WOOD)));
	public static final RegistryObject<Block> ENGLISH_HITCHING_POST = BLOCKS.register("english_hitching_post",
			() -> new HitchingPostBase(HitchingPostBase.HitchingPostType.ENGLISH, AbstractBlock.Properties.of(Material.WOOD)));
	public static final RegistryObject<Block> PASTURE_HITCHING_POST = BLOCKS.register("pasture_hitching_post",
			() -> new HitchingPostBase(HitchingPostBase.HitchingPostType.PASTURE, AbstractBlock.Properties.of(Material.WOOD)));

	public static final RegistryObject<Block> ENGLISH_HITCHING_POST_MINI = BLOCKS.register("english_hitching_post_mini",
			() -> new HitchingPostBaseMini(HitchingPostBaseMini.HitchingPostType.ENGLISH, AbstractBlock.Properties.of(Material.WOOD)));
	public static final RegistryObject<Block> WESTERN_HITCHING_POST_MINI = BLOCKS.register("western_hitching_post_mini",
			() -> new HitchingPostBaseMini(HitchingPostBaseMini.HitchingPostType.ENGLISH, AbstractBlock.Properties.of(Material.WOOD)));
	public static final RegistryObject<Block> PASTURE_HITCHING_POST_MINI = BLOCKS.register("pasture_hitching_post_mini",
			() -> new HitchingPostBaseMini(HitchingPostBaseMini.HitchingPostType.ENGLISH, AbstractBlock.Properties.of(Material.WOOD)));

	public static final RegistryObject<Block> HORSE_POO = BLOCKS.register("pile_of_horse_poo", () -> new HorsePoopBlock(AbstractBlock.Properties.of(Material.GRASS)));
	public static final RegistryObject<Block> WESTERN_POLE = BLOCKS.register("western_pole", () -> new WesternPoleBlock(AbstractBlock.Properties.of(Material.WOOD)));
	public static final RegistryObject<Block> INVISIBLE_GLOW_BLOCK = BLOCKS.register("invisible_glow_block", () -> new InvisibleGlowBlock(AbstractBlock.Properties.of(Material.METAL)));
	public static final RegistryObject<Block> WESTERN_FENCE = BLOCKS.register("western_fence", () -> new FenceBaseBlock(AbstractBlock.Properties.of(Material.WOOD)));
	public static final RegistryObject<Block> ENGLISH_FENCE = register("english_fence", () -> new EnglishFenceBlock(AbstractBlock.Properties.of(Material.WOOD).noOcclusion()));
	public static final RegistryObject<Block> PASTURE_FENCE = BLOCKS.register("pasture_fence", () -> new FenceBaseBlock(AbstractBlock.Properties.of(Material.WOOD)));
	public static final RegistryObject<Block> TACK_BOX = BLOCKS.register("tack_box", () -> new TackBoxBlock(AbstractBlock.Properties.of(Material.METAL)));
	public static final RegistryObject<HorseDoorBlock> ACACIA_STALL_HORSE = BLOCKS.register("acacia_stall_horse", () -> new HorseDoorBlock(AbstractBlock.Properties.of(Material.WOOD).noOcclusion().strength(1.0f), DyeColor.BLACK));
	public static final RegistryObject<HorseDoorBlock> BIRCH_STALL_HORSE = BLOCKS.register("birch_stall_horse", () -> new HorseDoorBlock(AbstractBlock.Properties.of(Material.WOOD).noOcclusion().strength(1.0f), DyeColor.BLACK));
	public static final RegistryObject<HorseDoorBlock> DARK_OAK_STALL_HORSE = BLOCKS.register("dark_oak_stall_horse", () -> new HorseDoorBlock(AbstractBlock.Properties.of(Material.WOOD).noOcclusion().strength(1.0f), DyeColor.BLACK));
	public static final RegistryObject<HorseDoorBlock> JUNGLE_STALL_HORSE = BLOCKS.register("jungle_stall_horse", () -> new HorseDoorBlock(AbstractBlock.Properties.of(Material.WOOD).noOcclusion().strength(1.0f), DyeColor.BLACK));
	public static final RegistryObject<HorseDoorBlock> OAK_STALL_HORSE = BLOCKS.register("oak_stall_horse", () -> new HorseDoorBlock(AbstractBlock.Properties.of(Material.WOOD).noOcclusion().strength(1.0f), DyeColor.BLACK));
	public static final RegistryObject<HorseDoorBlock> SPRUCE_STALL_HORSE = BLOCKS.register("spruce_stall_horse", () -> new HorseDoorBlock(AbstractBlock.Properties.of(Material.WOOD).noOcclusion().strength(1.0f), DyeColor.BLACK));
	public static final RegistryObject<CareDoorBlock> ACACIA_STALL_CARE = BLOCKS.register("acacia_stall_care", () -> new CareDoorBlock(AbstractBlock.Properties.of(Material.WOOD).noOcclusion().strength(1.0f), DyeColor.BLACK));
	public static final RegistryObject<CareDoorBlock> BIRCH_STALL_CARE = BLOCKS.register("birch_stall_care", () -> new CareDoorBlock(AbstractBlock.Properties.of(Material.WOOD).noOcclusion().strength(1.0f), DyeColor.BLACK));
	public static final RegistryObject<CareDoorBlock> DARK_OAK_STALL_CARE = BLOCKS.register("dark_oak_stall_care", () -> new CareDoorBlock(AbstractBlock.Properties.of(Material.WOOD).noOcclusion().strength(1.0f), DyeColor.BLACK));
	public static final RegistryObject<CareDoorBlock> JUNGLE_STALL_CARE = BLOCKS.register("jungle_stall_care", () -> new CareDoorBlock(AbstractBlock.Properties.of(Material.WOOD).noOcclusion().strength(1.0f), DyeColor.BLACK));
	public static final RegistryObject<CareDoorBlock> OAK_STALL_CARE = BLOCKS.register("oak_stall_care", () -> new CareDoorBlock(AbstractBlock.Properties.of(Material.WOOD).noOcclusion().strength(1.0f), DyeColor.BLACK));
	public static final RegistryObject<CareDoorBlock> SPRUCE_STALL_CARE = BLOCKS.register("spruce_stall_care", () -> new CareDoorBlock(AbstractBlock.Properties.of(Material.WOOD).noOcclusion().strength(1.0f), DyeColor.BLACK));
	public static final RegistryObject<Block> ONE_SADDLE_RACK = BLOCKS.register("one_saddle_rack", () -> new OneSaddleRack(AbstractBlock.Properties.of(Material.METAL).noOcclusion()));
	public static final RegistryObject<Block> BRIDLE_RACK = BLOCKS.register("bridle_rack", () -> new BridleRackBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion()));
	public static final RegistryObject<Block> METAL_GRATE = BLOCKS.register("metal_grate", () -> new TrapDoorBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion()));
	public static final RegistryObject<Block> LIGHT_FRIENDLY_BARS = BLOCKS.register("light_friendly_bars", () -> new PaneBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion()));
	public static final RegistryObject<Block> MEDIUM_FRIENDLY_BARS = BLOCKS.register("medium_friendly_bars", () -> new PaneBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion()));
	public static final RegistryObject<Block> DARK_FRIENDLY_BARS = BLOCKS.register("dark_friendly_bars", () -> new PaneBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion()));
	public static final RegistryObject<NonParallelBlock> WATER_TROUGH = BLOCKS.register("water_trough", () -> new WaterThroughBlock(AbstractBlock.Properties.of(Material.METAL), DyeColor.BLACK));
	public static final RegistryObject<Block> WET_COMPOST = BLOCKS.register("wet_compost", () -> new Block(AbstractBlock.Properties.of(Material.GRASS).strength(0.5f, 0.5f).sound(SoundType.WET_GRASS)));
	public static final RegistryObject<Block> COMPOST = BLOCKS.register("compost", () -> new Block(AbstractBlock.Properties.of(Material.GRASS)));
	public static final RegistryObject<Block> HORSE_PEE = BLOCKS.register("horse_pee", () -> new PeeBlock(AbstractBlock.Properties.of(Material.GRASS).noOcclusion().isValidSpawn(Blocks::never).isRedstoneConductor(Blocks::never).isSuffocating(Blocks::never).isViewBlocking(Blocks::never)));
	public static final RegistryObject<Block> CANTAZARITE_ANVIL = BLOCKS.register("cantazarite_anvil", () -> new CantazariteAnvilBlock(AbstractBlock.Properties.copy(Blocks.ANVIL).noOcclusion()));
	public static final RegistryObject<Block> TEARING_MAGMA = BLOCKS.register("tearing_magma", () -> new TearingMagma(AbstractBlock.Properties.of(Material.STONE, MaterialColor.NETHER).requiresCorrectToolForDrops().noOcclusion().randomTicks()));
	public static final RegistryObject<Block> GLOW_STRING = BLOCKS.register("glow_string", () -> new GlowTripwireBlock((TripWireHookBlock)TRIPWIRE_HOOK, AbstractBlock.Properties.of(Material.DECORATION).noCollission()));
	public static final RegistryObject<Block> LOCKER = BLOCKS.register("locker", () -> new LockerBlock(AbstractBlock.Properties.of(Material.METAL)));
	public static final RegistryObject<Block> PADDOCK_FEEDER = BLOCKS.register("paddock_feeder", () -> new PaddockFeederBlock(AbstractBlock.Properties.of(Material.WOOD)));
	public static final RegistryObject<Block> HORSE_ARMOR_RACK = BLOCKS.register("horse_armor_rack", () -> new HorseArmorRackBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion()));
	public static final RegistryObject<Block> WESTERN_BARREL = register("western_barrel", () -> new BarrelBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion()));

	public static final RegistryObject<Block> WHITEWASH_PLANK = BLOCKS.register("whitewash_plank", () -> new Block(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F, 3.0F).harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> WHITEWASH_SLAB = BLOCKS.register("whitewash_slab", () -> new SlabBlock(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F, 3.0F).harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> WHITEWASH_STAIRS = BLOCKS.register("whitewash_stairs", () -> new StairsBlock(Blocks.OAK_WOOD.defaultBlockState(), AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F, 3.0F).harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> WHITEWASH_BUTTON = BLOCKS.register("whitewash_button", () -> new WoodButtonBlock(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F, 3.0F).harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> WHITEWASH_FENCE = BLOCKS.register("whitewash_fence", () -> new FenceBlock(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F, 3.0F).harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> WHITEWASH_FENCE_GATE = BLOCKS.register("whitewash_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F, 3.0F).harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> WHITEWASH_DOOR = BLOCKS.register("whitewash_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F, 3.0F).harvestTool(ToolType.AXE)));

    // Jump blocks
	public static final RegistryObject<Block> JUMP_CONTROLLER = BLOCKS.register("jump_controller", () -> new JumpControllerBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion()));
	public static final RegistryObject<Block> JUMP_STANDARD_SCHOOLING = BLOCKS.register("jump_standard_schooling", () -> new JumpStandardBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion()));
	public static final RegistryObject<Block> JUMP_STANDARD_RADIAL = BLOCKS.register("jump_standard_radial", () -> new JumpStandardBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion()));
	public static final RegistryObject<Block> JUMP_STANDARD_VERTICAL_SLAT = BLOCKS.register("jump_standard_vertical_slat", () -> new JumpStandardBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion()));
	public static final RegistryObject<Block> JUMP_STANDARD_NONE = BLOCKS.register("jump_standard_none", () -> new JumpStandardBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion()));

	public static final RegistryObject<JumpBlock> JUMP_NONE = BLOCKS.register("jump_none", () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 1.0d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 1.0d, 0.875d)));
	public static final RegistryObject<JumpBlock> JUMP_LOG = BLOCKS.register("jump_log", () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 1.0d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 1.0d, 0.875d)));
	public static final RegistryObject<JumpBlock> JUMP_STAIR_DROP = BLOCKS.register("jump_stair_drop", () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 1.0d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 1.0d, 0.875d)));

	public static final RegistryObject<JumpBlock> JUMP_HEDGE = BLOCKS.register("jump_hedge", () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 1.0d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 1.0d, 0.875d)));
	public static final RegistryObject<JumpBlock> JUMP_WALL = BLOCKS.register("jump_wall", () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 1.0d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 1.0d, 0.875d)));

	public static final RegistryObject<JumpBlock> JUMP_BRUSH_BOX = BLOCKS.register("jump_brush_box", () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 0.875d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 0.875d, 0.875d)));
	public static final RegistryObject<JumpBlock> JUMP_COOP = BLOCKS.register("jump_coop", () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 0.5625d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 0.5625d, 0.875d)));
	public static final RegistryObject<JumpBlock> JUMP_WALL_MINI = BLOCKS.register("jump_wall_mini", () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 0.5d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 0.5d, 0.875d)));
	//public static final RegistryObject<JumpBlock> JUMP_CROSS_RAILS = BLOCKS.register("jump_cross_rails", () -> new JumpBlock(CROSS_RAILS));

	//public static final RegistryObject<JumpBlock> JUMP_SWEDISH_RAILS = BLOCKS.register("jump_swedish_rails", () -> new JumpBlock(SWEDISH_RAILS));

	//public static final RegistryObject<JumpBlock> JUMP_RED_FLAG = BLOCKS.register("jump_red_flag", () -> new JumpBlock());
	//public static final RegistryObject<JumpBlock> JUMP_WHITE_FLAG = BLOCKS.register("jump_white_flag", () -> new JumpBlock());
	//public static final RegistryObject<JumpBlock> JUMP_RED_WHITE_FLAG = BLOCKS.register("jump_red_white_flag", () -> new JumpBlock());

	//public static final RegistryObject<JumpBlock> JUMP_NUMBERS = BLOCKS.register("jump_numbers", () -> new JumpBlock());


	public static final List<RegistryObject<JumpBlock>> ROLL_TOPS = new ArrayList<>();
	public static final List<RegistryObject<JumpBlock>> RAILS = new ArrayList<>();
	public static final List<RegistryObject<JumpBlock>> GROUND_POLES = new ArrayList<>();
	public static final List<RegistryObject<JumpBlock>> POLE_ON_BOXES_SMALL = new ArrayList<>();
	public static final List<RegistryObject<JumpBlock>> POLE_ON_BOXES_LARGE = new ArrayList<>();
	public static final List<RegistryObject<JumpBlock>> FANCY_PLANKS = new ArrayList<>();
	public static final List<RegistryObject<JumpBlock>> PLANKS = new ArrayList<>();
	public static final List<RegistryObject<JumpBlock>> PANELS_WAVE = new ArrayList<>();
	public static final List<RegistryObject<JumpBlock>> PANELS_STRIPE = new ArrayList<>();
	public static final List<RegistryObject<JumpBlock>> PANELS_ARROW = new ArrayList<>();
	public static final List<RegistryObject<JumpBlock>> FLOWER_BOXES = new ArrayList<>();
	public static final List<RegistryObject<JumpBlock>> CAVALETTIS = new ArrayList<>();
	public static final List<RegistryObject<ConeBase>> CONES = new ArrayList<>();
	public static final List<RegistryObject<WheelBarrowBlock>> WHEEL_BARROWS = new ArrayList<>();
	public static final List<RegistryObject<SlowFeederBlock>> SLOW_FEEDERS = new ArrayList<>();
	public static final List<RegistryObject<NonParallelBlock>> SEPARATORS = new ArrayList<>();
	public static final List<RegistryObject<GrainFeederBlock>> GRAIN_FEEDERS = new ArrayList<>();
	public static final List<RegistryObject<HorseDoorBlock>> PASTURE_GATES_HORSE = new ArrayList<>();
	public static final List<RegistryObject<CareDoorBlock>> PASTURE_GATES_CARE = new ArrayList<>();
	public static final List<RegistryObject<HalfCareDoorBlock>> WEB_GUARDS_CARE = new ArrayList<>();
	public static final List<RegistryObject<HalfHorseDoorBlock>> WEB_GUARDS_HORSE = new ArrayList<>();
	public static final List<RegistryObject<HalfDoorBlock>> WEB_GUARDS_RIDER = new ArrayList<>();
	public static final List<RegistryObject<HalfBarrelBlock>> HALF_BARRELS = new ArrayList<>();

	static {
		for (DyeColor color : DyeColor.values()) {
			// Jumps
			ROLL_TOPS.add(registerNoItem("jump_roll_top_" + color.toString(), () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 0.875d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 0.875d, 0.875d))));
			RAILS.add(registerNoItem("jump_rail_" + color.toString(), () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 0.875d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 0.875d, 0.875d))));
			GROUND_POLES.add(registerNoItem("jump_ground_pole_" + color.toString(), () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 0.1875d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 0.1875d, 0.875d))));
			POLE_ON_BOXES_SMALL.add(registerNoItem("jump_pole_on_box_small_" + color.toString(), () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 0.6875d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 0.6875d, 0.875d))));
			POLE_ON_BOXES_LARGE.add(registerNoItem("jump_pole_on_box_large_" + color.toString(), () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 0.8125d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 0.875d, 0.875d))));
			FANCY_PLANKS.add(registerNoItem("jump_plank_fancy_" + color.toString(), () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 1.0d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 1.0d, 0.875d))));
			PLANKS.add(registerNoItem("jump_plank_" + color.toString(), () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 0.875d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 0.875d, 0.875d))));
			PANELS_WAVE.add(registerNoItem("jump_panel_wave_" + color.toString(), () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 1.0d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 1.0d, 0.875d))));
			PANELS_STRIPE.add(registerNoItem("jump_panel_stripe_" + color.toString(), () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 1.0d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 1.0d, 0.875d))));
			PANELS_ARROW.add(registerNoItem("jump_panel_arrow_" + color.toString(), () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 1.0d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 1.0d, 0.875d))));
			FLOWER_BOXES.add(registerNoItem("jump_flower_box_" + color.toString(), () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 0.875d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 0.875d, 0.875d))));
			CAVALETTIS.add(registerNoItem("jump_cavaletti_" + color.toString(), () -> new JumpBlock(VoxelShapes.box(0.125d, 0, 0, 0.875d, 1.0d, 1.0d), VoxelShapes.box(0, 0, 0.125d, 1.0d, 1.0d, 0.875d))));




			CONES.add(register(color.toString()+"_cone", ConeBase::new, block -> () -> new ConeBlockItem(block.get())));
			WHEEL_BARROWS.add(register("wheel_barrow_"+color.toString(), () -> new WheelBarrowBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion(), color),
					 block -> () -> new BlockItemBase(block.get())));
			SLOW_FEEDERS.add(register("slow_feeder_"+color.toString(), () -> new SlowFeederBlock(AbstractBlock.Properties.of(Material.METAL), color),
					block -> () -> new BlockItemBase(block.get())));
			SEPARATORS.add(register("separator_"+color.toString(), () -> new NonParallelBlock(AbstractBlock.Properties.of(Material.METAL), color),
					block -> () -> new BlockItemBase(block.get())));
			GRAIN_FEEDERS.add(register("grain_feeder_"+color.toString(), () -> new GrainFeederBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion(), color),
					block -> () -> new BlockItemBase(block.get())));
			PASTURE_GATES_HORSE.add(register("pasture_"+color.toString() + "_horse", () -> new HorseDoorBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion().strength(1.0f), color),
					block -> () -> new BlockItemBase(block.get())));
			PASTURE_GATES_CARE.add(register("pasture_"+color.toString() + "_care", () -> new CareDoorBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion().strength(1.0f), color),
					block -> () -> new BlockItemBase(block.get())));
			WEB_GUARDS_CARE.add(register("web_guard_"+color.toString() + "_care", () -> new HalfCareDoorBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion().strength(1.0f), color),
					block -> () -> new BlockItemBase(block.get())));
			WEB_GUARDS_HORSE.add(register("web_guard_"+color.toString() + "_horse", () -> new HalfHorseDoorBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion().strength(1.0f), color),
					block -> () -> new BlockItemBase(block.get())));
			WEB_GUARDS_RIDER.add(register("web_guard_"+color.toString() + "_rider", () -> new HalfDoorBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion().strength(1.0f)),
					block -> () -> new BlockItemBase(block.get())));
			HALF_BARRELS.add(register("half_barrel_"+color.getName(), () -> new HalfBarrelBlock(Block.Properties.of(Material.METAL).noOcclusion()),
					block -> () -> new BlockItemBase(block.get())));
		}
	}


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
	public static final RegistryObject<Item> QUALITY_BALE_ITEM = SWEMItems.ITEMS.register("quality_bale",
			() -> new BlockItemBase(QUALITY_BALE.get()));
	public static final RegistryObject<Item> DARK_SHAVINGS_ITEM = SWEMItems.ITEMS.register("dark_shavings",
			() -> new ShavingsItem(DARK_SHAVINGS.get()));
	public static final RegistryObject<Item> LIGHT_SHAVINGS_ITEM = SWEMItems.ITEMS.register("light_shavings",
			() -> new ShavingsItem(LIGHT_SHAVINGS.get()));
	public static final RegistryObject<Item> SOILED_SHAVINGS_ITEM = SWEMItems.ITEMS.register("soiled_shavings",
			ShavingsItem.SoiledShavingsItem::new);
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
	public static final RegistryObject<Item> ENGLISH_HITCHING_POST_MINI_ITEM = SWEMItems.ITEMS.register("english_hitching_post_mini",
			() -> new BlockItemBase(ENGLISH_HITCHING_POST_MINI.get()));
	public static final RegistryObject<Item> WESTERN_HITCHING_POST_MINI_ITEM = SWEMItems.ITEMS.register("western_hitching_post_mini",
			() -> new BlockItemBase(WESTERN_HITCHING_POST_MINI.get()));
	public static final RegistryObject<Item> PASTURE_HITCHING_POST_MINI_ITEM = SWEMItems.ITEMS.register("pasture_hitching_post_mini",
			() -> new BlockItemBase(PASTURE_HITCHING_POST_MINI.get()));
	public static final RegistryObject<Item> WESTERN_FENCE_ITEM = SWEMItems.ITEMS.register("western_fence", () -> new BlockItemBase(WESTERN_FENCE.get()));
	public static final RegistryObject<Item> PASTURE_FENCE_ITEM = SWEMItems.ITEMS.register("pasture_fence", () -> new BlockItemBase(PASTURE_FENCE.get()));
	public static final RegistryObject<Item> TACK_BOX_ITEM = SWEMItems.ITEMS.register("tack_box", () -> new TackBoxBlockItem(TACK_BOX.get()));
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
	public static final RegistryObject<Item> ONE_SADDLE_RACK_ITEM = SWEMItems.ITEMS.register("one_saddle_rack", () -> new BlockItemBase(ONE_SADDLE_RACK.get()));
	public static final RegistryObject<Item> BRIDLE_RACK_ITEM = SWEMItems.ITEMS.register("bridle_rack", () -> new BlockItemBase(BRIDLE_RACK.get()));
	public static final RegistryObject<Item> METAL_GRATE_ITEM = SWEMItems.ITEMS.register("metal_grate", () -> new BlockItemBase(METAL_GRATE.get()));
	public static final RegistryObject<Item> LIGHT_FRIENDLY_BARS_ITEM = SWEMItems.ITEMS.register("light_friendly_bars", () -> new BlockItemBase(LIGHT_FRIENDLY_BARS.get()));
	public static final RegistryObject<Item> MEDIUM_FRIENDLY_BARS_ITEM = SWEMItems.ITEMS.register("medium_friendly_bars", () -> new BlockItemBase(MEDIUM_FRIENDLY_BARS.get()));
	public static final RegistryObject<Item> DARK_FRIENDLY_BARS_ITEM = SWEMItems.ITEMS.register("dark_friendly_bars", () -> new BlockItemBase(DARK_FRIENDLY_BARS.get()));
	public static final RegistryObject<Item> WATER_TROUGH_ITEM = SWEMItems.ITEMS.register("water_trough", () -> new BlockItemBase(WATER_TROUGH.get()));
	public static final RegistryObject<Item> WET_COMPOST_ITEM = SWEMItems.ITEMS.register("wet_compost", () -> new BlockItemBase(WET_COMPOST.get()));
	public static final RegistryObject<Item> COMPOST_ITEM = SWEMItems.ITEMS.register("compost", () -> new BonemealBlockItem(COMPOST.get()));
	public static final RegistryObject<Item> CANTAZARITE_ANVIL_ITEM = SWEMItems.ITEMS.register("cantazarite_anvil", () -> new BlockItemBase(CANTAZARITE_ANVIL.get()));
	public static final RegistryObject<Item> CHARCOAL_BLOCK_ITEM = SWEMItems.ITEMS.register("charcoal_block", () -> new BlockItemBase(CHARCOAL_BLOCK.get()));
	public static final RegistryObject<Item> LOCKER_ITEM = SWEMItems.ITEMS.register("locker", () -> new BlockItemBase(LOCKER.get()));
	public static final RegistryObject<Item> PADDOCK_FEEDER_ITEM = SWEMItems.ITEMS.register("paddock_feeder", () -> new BlockItemBase(PADDOCK_FEEDER.get()));
	public static final RegistryObject<Item> HORSE_ARMOR_RACK_ITEM = SWEMItems.ITEMS.register("horse_armor_rack", () -> new BlockItemBase(HORSE_ARMOR_RACK.get()));

	public static final RegistryObject<Item> WHITEWASH_PLANK_ITEM = SWEMItems.ITEMS.register("whitewash_plank", () -> new BlockItemBase(WHITEWASH_PLANK.get()));
	public static final RegistryObject<Item> WHITEWASH_SLAB_ITEM = SWEMItems.ITEMS.register("whitewash_slab", () -> new BlockItemBase(WHITEWASH_SLAB.get()));
	public static final RegistryObject<Item> WHITEWASH_STAIRS_ITEM = SWEMItems.ITEMS.register("whitewash_stairs", () -> new BlockItemBase(WHITEWASH_STAIRS.get()));
	public static final RegistryObject<Item> WHITEWASH_BUTTON_ITEM = SWEMItems.ITEMS.register("whitewash_button", () -> new BlockItemBase(WHITEWASH_BUTTON.get()));
	public static final RegistryObject<Item> WHITEWASH_FENCE_ITEM = SWEMItems.ITEMS.register("whitewash_fence", () -> new BlockItemBase(WHITEWASH_FENCE.get()));
	public static final RegistryObject<Item> WHITEWASH_FENCE_GATE_ITEM = SWEMItems.ITEMS.register("whitewash_fence_gate", () -> new BlockItemBase(WHITEWASH_FENCE_GATE.get()));
	public static final RegistryObject<Item> WHITEWASH_DOOR_ITEM = SWEMItems.ITEMS.register("whitewash_door", () -> new BlockItemBase(WHITEWASH_DOOR.get()));

}
