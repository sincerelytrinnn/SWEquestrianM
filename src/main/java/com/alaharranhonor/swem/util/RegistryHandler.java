package com.alaharranhonor.swem.util;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.armor.*;
import com.alaharranhonor.swem.blocks.*;
import com.alaharranhonor.swem.container.SWEMHorseInventoryContainer;
import com.alaharranhonor.swem.entities.RopeKnotEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.items.AmethystItem;
import com.alaharranhonor.swem.items.BlanketItem;
import com.alaharranhonor.swem.items.HorseSaddleItem;
import com.alaharranhonor.swem.items.ItemBase;
import com.alaharranhonor.swem.tools.SWEMItemTier;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.entity.ChestedHorseRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryHandler {

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
        return setup(entry, new ResourceLocation(SWEM.MOD_ID, name));
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
        entry.setRegistryName(registryName);
        return entry;
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SWEM.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SWEM.MOD_ID);
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, SWEM.MOD_ID);
    public static DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, SWEM.MOD_ID);



    public static void init(){
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINER_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Items
    public static final RegistryObject<Item> WOOD_COIL = ITEMS.register("wood_coil", ItemBase::new);
    public static final RegistryObject<Item> TETHER = ITEMS.register("tether", ItemBase::new);
    public static final RegistryObject<Item> WIRE = ITEMS.register("wire", ItemBase::new);
    public static final RegistryObject<Item> DIAMOND_PLATE = ITEMS.register("diamond_plate", ItemBase::new);
    public static final RegistryObject<Item> GOLD_PLATE = ITEMS.register("gold_plate", ItemBase::new);
    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate", ItemBase::new);
    public static final RegistryObject<Item> LEATHER_PLATE = ITEMS.register("leather_plate", ItemBase::new);
    public static final RegistryObject<Item> DIAMOND_RIVET = ITEMS.register("diamond_rivet", ItemBase::new);
    public static final RegistryObject<Item> GOLD_RIVET = ITEMS.register("gold_rivet", ItemBase::new);
    public static final RegistryObject<Item> IRON_RIVET = ITEMS.register("iron_rivet", ItemBase::new);
    public static final RegistryObject<Item> LEATHER_RIVET = ITEMS.register("leather_rivet", ItemBase::new);
    public static final RegistryObject<Item> LEATHER_SCYTHE = ITEMS.register("leather_scythe", ItemBase::new);
    public static final RegistryObject<ArmorItem> LEATHER_RIDING_BOOTS = ITEMS.register("leather_riding_boots", () -> new LeatherRidingBoots(ModArmorMaterial.LEATHER, EquipmentSlotType.FEET, new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<Item> LEATHER_LONGSWORD = ITEMS.register("leather_longsword", ItemBase::new);
    public static final RegistryObject<Item> LEATHER_LEGGINGS = ITEMS.register("leather_leggings", ItemBase::new);
    public static final RegistryObject<Item> LEATHER_HELMET = ITEMS.register("leather_helmet", ItemBase::new);
    public static final RegistryObject<Item> LEATHER_CHESTPLATE = ITEMS.register("leather_chestplate", ItemBase::new);
    public static final RegistryObject<Item> LEATHER_BOW = ITEMS.register("leather_bow", ItemBase::new);
    public static final RegistryObject<ArmorItem> GLOW_RIDING_BOOTS = ITEMS.register("glow_riding_boots", () -> new GlowRidingBoots(ModArmorMaterial.GLOW, EquipmentSlotType.FEET, new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<Item> IRON_SCYTHE = ITEMS.register("iron_scythe", ItemBase::new);
    public static final RegistryObject<ArmorItem> IRON_RIDING_BOOTS = ITEMS.register("iron_riding_boots", () -> new IronRidingBoots(ModArmorMaterial.IRON, EquipmentSlotType.FEET, new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<Item> IRON_LONGSWORD = ITEMS.register("iron_longsword", ItemBase::new);
    public static final RegistryObject<Item> IRON_LEGGINGS = ITEMS.register("iron_leggings", ItemBase::new);
    public static final RegistryObject<Item> IRON_HELMET = ITEMS.register("iron_helmet", ItemBase::new);
    public static final RegistryObject<Item> IRON_CHESTPLATE = ITEMS.register("iron_chestplate", ItemBase::new);
    public static final RegistryObject<Item> IRON_BOW = ITEMS.register("iron_bow", ItemBase::new);
    public static final RegistryObject<Item> GOLD_SCYTHE = ITEMS.register("gold_scythe", ItemBase::new);
    public static final RegistryObject<ArmorItem> GOLD_RIDING_BOOTS = ITEMS.register("gold_riding_boots", () -> new GoldRidingBoots(ModArmorMaterial.GOLD, EquipmentSlotType.FEET, new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<Item> GOLD_LONGSWORD = ITEMS.register("gold_longsword", ItemBase::new);
    public static final RegistryObject<Item> GOLD_LEGGINGS = ITEMS.register("gold_leggings", ItemBase::new);
    public static final RegistryObject<Item> GOLD_HELMET = ITEMS.register("gold_helmet", ItemBase::new);
    public static final RegistryObject<Item> GOLD_CHESTPLATE = ITEMS.register("gold_chestplate", ItemBase::new);
    public static final RegistryObject<Item> GOLD_BOW = ITEMS.register("gold_bow", ItemBase::new);
    public static final RegistryObject<Item> DIAMOND_SCYTHE = ITEMS.register("diamond_scythe", ItemBase::new);
    public static final RegistryObject<ArmorItem> DIAMOND_RIDING_BOOTS = ITEMS.register("diamond_riding_boots", () -> new DiamondRidingBoots(ModArmorMaterial.DIAMOND, EquipmentSlotType.FEET, new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<Item> DIAMOND_LONGSWORD = ITEMS.register("diamond_longsword", ItemBase::new);
    public static final RegistryObject<Item> DIAMOND_LEGGINGS = ITEMS.register("diamond_leggings", ItemBase::new);
    public static final RegistryObject<Item> DIAMOND_HELMET = ITEMS.register("diamond_helmet", ItemBase::new);
    public static final RegistryObject<Item> DIAMOND_CHESTPLATE = ITEMS.register("diamond_chestplate", ItemBase::new);
    public static final RegistryObject<Item> DIAMOND_BOW = ITEMS.register("diamond_bow", ItemBase::new);
    public static final RegistryObject<Item> AMETHYST_BOW = ITEMS.register("amethyst_bow", ItemBase::new);
    public static final RegistryObject<Item> AMETHYST_HELMET = ITEMS.register("amethyst_helmet", ItemBase::new);
    public static final RegistryObject<Item> AMETHYST_CHESTPLATE = ITEMS.register("amethyst_chestplate", ItemBase::new);
    public static final RegistryObject<Item> AMETHYST_PANTS = ITEMS.register("amethyst_pants", ItemBase::new);
    public static final RegistryObject<Item> AMETHYST_RIDING_BOOTS = ITEMS.register("amethyst_riding_boots", ItemBase::new);
    public static final RegistryObject<Item> AMETHYST_SCYTHE = ITEMS.register("amethyst_scythe", ItemBase::new);
    public static final RegistryObject<Item> AMETHYST_SHIELD = ITEMS.register("amethyst_shield", ItemBase::new);
    public static final RegistryObject<Item> AMETHYST_SHIELD_LEATHER = ITEMS.register("amethyst_shield_leather", ItemBase::new);
    public static final RegistryObject<Item> AMETHYST_SHIELD_IRON = ITEMS.register("amethyst_shield_iron", ItemBase::new);
    public static final RegistryObject<Item> AMETHYST_SHIELD_GOLD = ITEMS.register("amethyst_shield_gold", ItemBase::new);
    public static final RegistryObject<Item> AMETHYST_SHIELD_DIAMOND = ITEMS.register("amethyst_shield_diamond", ItemBase::new);
    public static final RegistryObject<Item> CANTAZARITE = ITEMS.register("cantazarite", ItemBase::new);
    public static final RegistryObject<Item> AMETHYST = ITEMS.register("amethyst", AmethystItem::new);
    public static final RegistryObject<Item> TIMOTHY_BUSHEL = ITEMS.register("timothy_bushel", ItemBase::new);
    public static final RegistryObject<Item> OAT_BUSHEL = ITEMS.register("oat_bushel", ItemBase::new);
    public static final RegistryObject<Item> ALFALFA_BUSHEL = ITEMS.register("alfalfa_bushel", ItemBase::new);
    public static final RegistryObject<Item> SWEM_WORM = ITEMS.register("swem_worm", ItemBase::new);
    public static final RegistryObject<HorseSaddleItem> WESTERN_SADDLE_LIGHT_BLUE = ITEMS.register("western_saddle_light_blue", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> WESTERN_SADDLE_WHITE = ITEMS.register("western_saddle_white", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> WESTERN_SADDLE_ORANGE = ITEMS.register("western_saddle_orange", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> WESTERN_SADDLE_MAGENTA = ITEMS.register("western_saddle_magenta", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> WESTERN_SADDLE_YELLOW = ITEMS.register("western_saddle_yellow", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> WESTERN_SADDLE_LIME = ITEMS.register("western_saddle_lime", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> WESTERN_SADDLE_PINK = ITEMS.register("western_saddle_pink", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> WESTERN_SADDLE_GRAY = ITEMS.register("western_saddle_gray", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> WESTERN_SADDLE_LIGHT_GRAY = ITEMS.register("western_saddle_light_gray", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> WESTERN_SADDLE_CYAN = ITEMS.register("western_saddle_cyan", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> WESTERN_SADDLE_PURPLE = ITEMS.register("western_saddle_purple", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> WESTERN_SADDLE_BLUE = ITEMS.register("western_saddle_blue", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> WESTERN_SADDLE_BROWN = ITEMS.register("western_saddle_brown", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> WESTERN_SADDLE_GREEN = ITEMS.register("western_saddle_green", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> WESTERN_SADDLE_RED = ITEMS.register("western_saddle_red", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> WESTERN_SADDLE_BLACK = ITEMS.register("western_saddle_black", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> ENGLISH_SADDLE_BLACK = ITEMS.register("english_saddle_black", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> ENGLISH_SADDLE_BROWN = ITEMS.register("english_saddle_brown", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> ADVENTURE_SADDLE_ONE = ITEMS.register("adventure_saddle_1", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> ADVENTURE_SADDLE_TWO = ITEMS.register("adventure_saddle_2", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<HorseSaddleItem> ADVENTURE_SADDLE_THREE = ITEMS.register("adventure_saddle_3", () -> new HorseSaddleItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<BlanketItem> ENGLISH_BLANKET_BLACK = ITEMS.register("english_blanket_black", () -> new BlanketItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<BlanketItem> ENGLISH_BLANKET_BLUE = ITEMS.register("english_blanket_blue", () -> new BlanketItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<BlanketItem> ENGLISH_BLANKET_BROWN = ITEMS.register("english_blanket_brown", () -> new BlanketItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<BlanketItem> ENGLISH_BLANKET_CYAN = ITEMS.register("english_blanket_cyan", () -> new BlanketItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<BlanketItem> ENGLISH_BLANKET_GREEN = ITEMS.register("english_blanket_green", () -> new BlanketItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<BlanketItem> ENGLISH_BLANKET_GRAY = ITEMS.register("english_blanket_gray", () -> new BlanketItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<BlanketItem> ENGLISH_BLANKET_LIGHT_BLUE = ITEMS.register("english_blanket_light_blue", () -> new BlanketItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<BlanketItem> ENGLISH_BLANKET_LIGHT_GRAY = ITEMS.register("english_blanket_light_gray", () -> new BlanketItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<BlanketItem> ENGLISH_BLANKET_LIME = ITEMS.register("english_blanket_lime", () -> new BlanketItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<BlanketItem> ENGLISH_BLANKET_MAGENTA = ITEMS.register("english_blanket_magenta", () -> new BlanketItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<BlanketItem> ENGLISH_BLANKET_ORANGE = ITEMS.register("english_blanket_orange", () -> new BlanketItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<BlanketItem> ENGLISH_BLANKET_PINK = ITEMS.register("english_blanket_pink", () -> new BlanketItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<BlanketItem> ENGLISH_BLANKET_PURPLE = ITEMS.register("english_blanket_purple", () -> new BlanketItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<BlanketItem> ENGLISH_BLANKET_RED = ITEMS.register("english_blanket_red", () -> new BlanketItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<BlanketItem> ENGLISH_BLANKET_WHITE = ITEMS.register("english_blanket_white", () -> new BlanketItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
    public static final RegistryObject<BlanketItem> ENGLISH_BLANKET_YELLOW = ITEMS.register("english_blanket_yellow", () -> new BlanketItem(new Item.Properties().group(SWEM.TAB).maxStackSize(1)));


    // Tools
    public static final RegistryObject<SwordItem> AMETHYST_LONGSWORD = ITEMS.register("amethyst_longsword", () ->
            new SwordItem(SWEMItemTier.AMETHYST, 10, 2F, new Item.Properties().group(SWEM.TAB))
    );
// Blocks
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
    public static final RegistryObject<Block> WESTERN_FENCE = BLOCKS.register("western_fence", () -> new FenceBaseBlock(FenceBaseBlock.FenceType.ENGLISH, Block.Properties.create(Material.IRON)));

    // Block Items
    public static final RegistryObject<Item> FUEL_BLOCK_ITEM = ITEMS.register("fuel_block",
            () -> new FuelBlockItemBase(FUEL_BLOCK.get(), 1600));
    public static final RegistryObject<Item> CANTAZARITE_BLOCK_ITEM = ITEMS.register("cantazarite_block",
            () -> new BlockItemBase(CANTAZARITE_BLOCK.get()));
    public static final RegistryObject<Item> DARK_RUBBER_MAT_ITEM = ITEMS.register("dark_rubber_mat",
            () -> new BlockItemBase(DARK_RUBBER_MAT.get()));
    public static final RegistryObject<Item> LIGHT_RUBBER_MAT_ITEM = ITEMS.register("light_rubber_mat",
            () -> new BlockItemBase(LIGHT_RUBBER_MAT.get()));
    public static final RegistryObject<Item> MEDIUM_RUBBER_MAT_ITEM = ITEMS.register("medium_rubber_mat",
            () -> new BlockItemBase(MEDIUM_RUBBER_MAT.get()));
    public static final RegistryObject<Item> CANTAZARITE_ORE_ITEM = ITEMS.register("cantazarite_ore",
            () -> new BlockItemBase(CANTAZARITE_ORE.get()));
    public static final RegistryObject<Item> AMETHYST_ORE_ITEM = ITEMS.register("amethyst_ore",
            () -> new BlockItemBase(AMETHYST_ORE.get()));
    public static final RegistryObject<Item> YELLOW_CONE_ITEM = ITEMS.register("yellow_cone",
            () -> new BlockItemBase(YELLOW_CONE.get()));
    public static final RegistryObject<Item> WHITE_CONE_ITEM = ITEMS.register("white_cone",
            () -> new BlockItemBase(WHITE_CONE.get()));
    public static final RegistryObject<Item> RED_CONE_ITEM = ITEMS.register("red_cone",
            () -> new BlockItemBase(RED_CONE.get()));
    public static final RegistryObject<Item> PURPLE_CONE_ITEM = ITEMS.register("purple_cone",
            () -> new BlockItemBase(PURPLE_CONE.get()));
    public static final RegistryObject<Item> PINK_CONE_ITEM = ITEMS.register("pink_cone",
            () -> new BlockItemBase(PINK_CONE.get()));
    public static final RegistryObject<Item> ORANGE_CONE_ITEM = ITEMS.register("orange_cone",
            () -> new BlockItemBase(ORANGE_CONE.get()));
    public static final RegistryObject<Item> MAGENTA_CONE_ITEM = ITEMS.register("magenta_cone",
            () -> new BlockItemBase(MAGENTA_CONE.get()));
    public static final RegistryObject<Item> LIGHT_BLUE_CONE_ITEM = ITEMS.register("light_blue_cone",
            () -> new BlockItemBase(LIGHT_BLUE_CONE.get()));
    public static final RegistryObject<Item> GREY_CONE_ITEM = ITEMS.register("grey_cone",
            () -> new BlockItemBase(GREY_CONE.get()));
    public static final RegistryObject<Item> LIME_CONE_ITEM = ITEMS.register("lime_cone",
            () -> new BlockItemBase(LIME_CONE.get()));
    public static final RegistryObject<Item> GREEN_CONE_ITEM = ITEMS.register("green_cone",
            () -> new BlockItemBase(GREEN_CONE.get()));
    public static final RegistryObject<Item> CYAN_CONE_ITEM = ITEMS.register("cyan_cone",
            () -> new BlockItemBase(CYAN_CONE.get()));
    public static final RegistryObject<Item> BLUE_CONE_ITEM = ITEMS.register("blue_cone",
            () -> new BlockItemBase(BLUE_CONE.get()));
    public static final RegistryObject<Item> QUALITY_BALE_ITEM = ITEMS.register("quality_bale",
            () -> new BlockItemBase(QUALITY_BALE.get()));
    public static final RegistryObject<Item> TIMOTHY_SEEDS = ITEMS.register("timothy_seeds",
            () -> new BlockItemBase(TIMOTHY_GRASS.get()));
    public static final RegistryObject<Item> ALFALFA_SEEDS = ITEMS.register("alfalfa_seeds",
            () -> new BlockItemBase(ALFALFA_PLANT.get()));
    public static final RegistryObject<Item> OAT_SEEDS = ITEMS.register("oat_seeds",
            () -> new BlockItemBase(OAT_PLANT.get()));
    public static final RegistryObject<Item> DARK_SHAVINGS_BLOCK_ITEM = ITEMS.register("dark_shavings_block",
            () -> new BlockItemBase(DARK_SHAVINGS_BLOCK.get()));
    public static final RegistryObject<Item> DARK_SHAVINGS_ITEM = ITEMS.register("dark_shavings",
            () -> new ShavingsItem(DARK_SHAVINGS.get()));
    public static final RegistryObject<Item> LIGHT_SHAVINGS_BLOCK_ITEM = ITEMS.register("light_shavings_block",
            () -> new BlockItemBase(LIGHT_SHAVINGS_BLOCK.get()));
    public static final RegistryObject<Item> LIGHT_SHAVINGS_ITEM = ITEMS.register("light_shavings",
            () -> new ShavingsItem(LIGHT_SHAVINGS.get()));
    public static final RegistryObject<Item> SOILED_SHAVINGS_BLOCK_ITEM = ITEMS.register("soiled_shavings_block",
            () -> new BlockItemBase(SOILED_SHAVINGS_BLOCK.get()));
    public static final RegistryObject<Item> SOILED_SHAVINGS_ITEM = ITEMS.register("soiled_shavings",
            () -> new BlockItemBase(SOILED_SHAVINGS.get()));
    public static final RegistryObject<Item> RIDING_DOOR_ITEM = ITEMS.register("riding_door",
            () -> new BlockItemBase(RIDING_DOOR.get()));
    public static final RegistryObject<Item> BLEACHER_SLAB_ITEM = ITEMS.register("bleacher",
            () -> new BlockItemBase(BLEACHER_SLAB.get()));
    public static final RegistryObject<Item> WESTERN_HITCHING_POST_ITEM = ITEMS.register("western_hitching_post",
            () -> new BlockItemBase(WESTERN_HITCHING_POST.get()));
    public static final RegistryObject<Item> ENGLISH_HITCHING_POST_ITEM = ITEMS.register("english_hitching_post",
            () -> new BlockItemBase(ENGLISH_HITCHING_POST.get()));
    public static final RegistryObject<Item> PASTURE_HITCHING_POST_ITEM = ITEMS.register("pasture_hitching_post",
            () -> new BlockItemBase(PASTURE_HITCHING_POST.get()));
    public static final RegistryObject<Item> WESTERN_POLE_ITEM = ITEMS.register("western_pole",
            () -> new BlockItemBase(WESTERN_POLE.get()));
    public static final RegistryObject<Item> WESTERN_FENCE_ITEM = ITEMS.register("western_fence", () -> new BlockItemBase(WESTERN_FENCE.get()));

    // Entity's
    public static final RegistryObject<EntityType<SWEMHorseEntity>> SWEM_HORSE_ENTITY = ENTITY_TYPES.register("swem_horse",
        () -> EntityType.Builder.create(SWEMHorseEntity::new, EntityClassification.CREATURE)
            .size(1.13f, 2.3f) // Hitbox Size
            .build(new ResourceLocation(SWEM.MOD_ID, "swem_horse").toString())
    );
    public static final RegistryObject<EntityType<RopeKnotEntity>> ROPE_KNOT_ENTITY =
            ENTITY_TYPES.register("rope_knot",
        () -> EntityType.Builder.<RopeKnotEntity>create(RopeKnotEntity::new,EntityClassification.MISC)
                .disableSerialization()
                .size(0.5F, 0.5F)
                .trackingRange(10)
                .func_233608_b_(Integer.MAX_VALUE)
                .build("rope_knot")
    );

    // Containers
    public static final RegistryObject<ContainerType<SWEMHorseInventoryContainer>> SWEM_HORSE_CONTAINER = CONTAINER_TYPES.register("swem_horse_container",
    () -> IForgeContainerType.create(SWEMHorseInventoryContainer::new)
    );


}