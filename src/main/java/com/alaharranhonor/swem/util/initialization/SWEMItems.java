package com.alaharranhonor.swem.util.initialization;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.armor.*;
import com.alaharranhonor.swem.entity.coats.SWEMCoatColors;
import com.alaharranhonor.swem.items.*;
import com.alaharranhonor.swem.items.potions.CantazaritePotionItem;
import com.alaharranhonor.swem.items.tack.*;
import com.alaharranhonor.swem.tools.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public class SWEMItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SWEM.MOD_ID);

	public static void init(IEventBus modBus) {
		ITEMS.register(modBus);
	}


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
	public static final RegistryObject<SWEMArmorItem> LEATHER_RIDING_BOOTS = ITEMS.register("leather_riding_boots", () -> new LeatherRidingBoots("leather_layer",ModArmorMaterial.LEATHER, EquipmentSlotType.FEET, new Item.Properties().group(SWEM.TAB).maxStackSize(1)));// () -> (Supplier<ArmorBaseModel>) ArmorItemRegistration.LeatherArmor));
	public static final RegistryObject<Item> LEATHER_LONGSWORD = ITEMS.register("leather_longsword", ItemBase::new);
	public static final RegistryObject<Item> LEATHER_LEGGINGS = ITEMS.register("leather_leggings", ItemBase::new);
	public static final RegistryObject<Item> LEATHER_HELMET = ITEMS.register("leather_helmet", ItemBase::new);
	public static final RegistryObject<Item> LEATHER_CHESTPLATE = ITEMS.register("leather_chestplate", ItemBase::new);
	public static final RegistryObject<Item> LEATHER_BOW = ITEMS.register("leather_bow", ItemBase::new);
	public static final RegistryObject<SWEMArmorItem> GLOW_RIDING_BOOTS = ITEMS.register("glow_riding_boots", () -> new GlowRidingBoots("glow_layer",ModArmorMaterial.GLOW, EquipmentSlotType.FEET, new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<Item> IRON_SCYTHE = ITEMS.register("iron_scythe", ItemBase::new);
	public static final RegistryObject<SWEMArmorItem> IRON_RIDING_BOOTS = ITEMS.register("iron_riding_boots", () -> new IronRidingBoots("iron_layer",ModArmorMaterial.IRON, EquipmentSlotType.FEET, new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<Item> IRON_LONGSWORD = ITEMS.register("iron_longsword", ItemBase::new);
	public static final RegistryObject<Item> IRON_LEGGINGS = ITEMS.register("iron_leggings", ItemBase::new);
	public static final RegistryObject<Item> IRON_HELMET = ITEMS.register("iron_helmet", ItemBase::new);
	public static final RegistryObject<Item> IRON_CHESTPLATE = ITEMS.register("iron_chestplate", ItemBase::new);
	public static final RegistryObject<Item> IRON_BOW = ITEMS.register("iron_bow", ItemBase::new);
	public static final RegistryObject<Item> GOLD_SCYTHE = ITEMS.register("gold_scythe", ItemBase::new);
	public static final RegistryObject<SWEMArmorItem> GOLD_RIDING_BOOTS = ITEMS.register("gold_riding_boots", () -> new GoldRidingBoots("gold_layer",ModArmorMaterial.GOLD, EquipmentSlotType.FEET, new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<Item> GOLD_LONGSWORD = ITEMS.register("gold_longsword", ItemBase::new);
	public static final RegistryObject<Item> GOLD_LEGGINGS = ITEMS.register("gold_leggings", ItemBase::new);
	public static final RegistryObject<Item> GOLD_HELMET = ITEMS.register("gold_helmet", ItemBase::new);
	public static final RegistryObject<Item> GOLD_CHESTPLATE = ITEMS.register("gold_chestplate", ItemBase::new);
	public static final RegistryObject<Item> GOLD_BOW = ITEMS.register("gold_bow", ItemBase::new);
	public static final RegistryObject<Item> DIAMOND_SCYTHE = ITEMS.register("diamond_scythe", ItemBase::new);
	public static final RegistryObject<SWEMArmorItem> DIAMOND_RIDING_BOOTS = ITEMS.register("diamond_riding_boots", () -> new DiamondRidingBoots("diamond_layer",ModArmorMaterial.DIAMOND, EquipmentSlotType.FEET, new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<Item> DIAMOND_LONGSWORD = ITEMS.register("diamond_longsword", ItemBase::new);
	public static final RegistryObject<Item> DIAMOND_LEGGINGS = ITEMS.register("diamond_leggings", ItemBase::new);
	public static final RegistryObject<Item> DIAMOND_HELMET = ITEMS.register("diamond_helmet", ItemBase::new);
	public static final RegistryObject<Item> DIAMOND_CHESTPLATE = ITEMS.register("diamond_chestplate", ItemBase::new);
	public static final RegistryObject<Item> DIAMOND_BOW = ITEMS.register("diamond_bow", ItemBase::new);
	public static final RegistryObject<SWEMArmorItem> AMETHYST_HELMET = ITEMS.register("amethyst_helmet", () -> new AmethystHelmet("amethyst_layer",ModArmorMaterial.AMETHYST, EquipmentSlotType.HEAD, new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<SWEMArmorItem> AMETHYST_CHESTPLATE = ITEMS.register("amethyst_chestplate", () -> new AmethystChestplate("amethyst_layer",ModArmorMaterial.AMETHYST, EquipmentSlotType.CHEST, new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<SWEMArmorItem> AMETHYST_PANTS = ITEMS.register("amethyst_pants", () -> new AmethystLeggings("amethyst_layer",ModArmorMaterial.AMETHYST, EquipmentSlotType.LEGS, new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<Item> AMETHYST_SHIELD_LEATHER = ITEMS.register("amethyst_shield_leather", ItemBase::new);
	public static final RegistryObject<Item> AMETHYST_SHIELD_IRON = ITEMS.register("amethyst_shield_iron", ItemBase::new);
	public static final RegistryObject<Item> AMETHYST_SHIELD_GOLD = ITEMS.register("amethyst_shield_gold", ItemBase::new);
	public static final RegistryObject<Item> AMETHYST_SHIELD_DIAMOND = ITEMS.register("amethyst_shield_diamond", ItemBase::new);
	public static final RegistryObject<SWEMArmorItem> AMETHYST_RIDING_BOOTS = ITEMS.register("amethyst_riding_boots", () -> new AmethystRidingBoots("amethyst_layer",ModArmorMaterial.AMETHYST, EquipmentSlotType.FEET, new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<Item> CANTAZARITE = ITEMS.register("cantazarite", ItemBase::new);
	public static final RegistryObject<Item> AMETHYST = ITEMS.register("amethyst", AmethystItem::new);
	public static final RegistryObject<Item> TIMOTHY_BUSHEL = ITEMS.register("timothy_bushel", ItemBase::new);
	public static final RegistryObject<Item> OAT_BUSHEL = ITEMS.register("oat_bushel", ItemBase::new);
	public static final RegistryObject<Item> ALFALFA_BUSHEL = ITEMS.register("alfalfa_bushel", ItemBase::new);
	public static final RegistryObject<FenceToolItem> FENCE_TOOL = ITEMS.register("fence_tool", FenceToolItem::new);
	public static final RegistryObject<WhistleItem> WHISTLE = ITEMS.register("whistle", WhistleItem::new);
	public static final RegistryObject<PotionItem> CANTAZARITE_POTION = ITEMS.register("cantazarite_potion", () -> new CantazaritePotionItem(new Item.Properties().maxStackSize(1).group(SWEM.TAB)));
	public static final RegistryObject<Item> CANTAZARITE_DYE = ITEMS.register("cantazarite_dye", ItemBase::new);
	public static final RegistryObject<Item> PENDANT = ITEMS.register("pendant", PendantItem::new);
	public static final RegistryObject<Item> SUGAR_CUBE = ITEMS.register("sugar_cube", ItemBase::new);
	public static final RegistryObject<DesensitizingItem> BELLS = ITEMS.register("bells", () -> new DesensitizingItem(0));
	public static final RegistryObject<DesensitizingItem> HOOLAHOOP = ITEMS.register("hoolahoop", () -> new DesensitizingItem(1));
	public static final RegistryObject<DesensitizingItem> POMPOM = ITEMS.register("pompom", () -> new DesensitizingItem(2));
	public static final RegistryObject<DesensitizingItem> SHOPPING_BAG = ITEMS.register("shopping_bag", () -> new DesensitizingItem(3));
	public static final RegistryObject<DesensitizingItem> TARP = ITEMS.register("tarp", () -> new DesensitizingItem(4));
	public static final RegistryObject<PitchforkTool> PITCHFORK = ITEMS.register("pitchfork", PitchforkTool::new);
	public static final RegistryObject<HorseXPBottle> SPEED_XP_BOTTLE = ITEMS.register("speed_xp_bottle", () -> new HorseXPBottle("speed"));
	public static final RegistryObject<HorseXPBottle> JUMP_XP_BOTTLE = ITEMS.register("jump_xp_bottle", () -> new HorseXPBottle("jump"));
	public static final RegistryObject<HorseXPBottle> AFFINITY_XP_BOTTLE = ITEMS.register("affinity_xp_bottle", () -> new HorseXPBottle("affinity"));
	public static final RegistryObject<HorseXPBottle> HEALTH_XP_BOTTLE = ITEMS.register("health_xp_bottle", () -> new HorseXPBottle("health"));
	public static final RegistryObject<HorseXPBottle> ALL_XP_BOTTLE = ITEMS.register("all_xp_bottle", () -> new HorseXPBottle("all"));
	public static final RegistryObject<HoseItem> HOSE = ITEMS.register("hose", () -> new HoseItem(() -> Fluids.EMPTY, new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<HoseItem> HOSE_WATER = ITEMS.register("hose_water", () -> new HoseItem(() -> Fluids.WATER, new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<SWEMHorseArmorItem> CLOTH_HORSE_ARMOR = ITEMS.register("cloth_horse_armor", () -> new SWEMHorseArmorItem(SWEMHorseArmorItem.HorseArmorTier.CLOTH, 1, "cloth", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<SWEMHorseArmorItem> IRON_HORSE_ARMOR = ITEMS.register("iron_horse_armor", () -> new SWEMHorseArmorItem(SWEMHorseArmorItem.HorseArmorTier.IRON, 2, "iron", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<SWEMHorseArmorItem> GOLD_HORSE_ARMOR = ITEMS.register("gold_horse_armor", () -> new SWEMHorseArmorItem(SWEMHorseArmorItem.HorseArmorTier.GOLD, 3, "gold", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<SWEMHorseArmorItem> DIAMOND_HORSE_ARMOR = ITEMS.register("diamond_horse_armor", () -> new SWEMHorseArmorItem(SWEMHorseArmorItem.HorseArmorTier.DIAMOND, 4, "diamond", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<SWEMHorseArmorItem> AMETHYST_HORSE_ARMOR = ITEMS.register("amethyst_horse_armor", () -> new SWEMHorseArmorItem(SWEMHorseArmorItem.HorseArmorTier.AMETHYST, 5, "amethyst", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<SaddlebagItem> SADDLEBAG = ITEMS.register("saddlebag", () -> new SaddlebagItem("saddlebags"));
	public static final RegistryObject<MeasurementTool> MEASUREMENT_TAPE = ITEMS.register("measurement_tape", MeasurementTool::new);
	public static final RegistryObject<PoopItem> POOP = ITEMS.register("poop", PoopItem::new);
	public static final RegistryObject<TrackerItem> TRACKER = ITEMS.register("tracker", TrackerItem::new);



	// Rainbow Horse recipe items.
	public static final RegistryObject<Item> WARMER_EGG = ITEMS.register("warmer_egg", ItemBase::new);
	public static final RegistryObject<Item> COOLER_EGG = ITEMS.register("cooler_egg", ItemBase::new);
	public static final RegistryObject<Item> RAINBOW_EGG = ITEMS.register("rainbow_egg", ItemBase::new);
	public static final RegistryObject<Item> RAINBOW_CHIC = ITEMS.register("rainbow_chic", ItemBase::new);
	public static final RegistryObject<Item> DEHYDRATED_RAINBOW = ITEMS.register("dehydrated_rainbow", ItemBase::new);
	public static final RegistryObject<Item> RAINBOW_SWEET_FEED = ITEMS.register("rainbow_sweet_feed", ItemBase::new);
	public static final RegistryObject<Item> RAINBOW_HEAVY_FEED = ITEMS.register("rainbow_heavy_feed", ItemBase::new);
	public static final RegistryObject<Item> RAINBOW_DRY_FEED = ITEMS.register("rainbow_dry_feed", RainbowDryFeedItem::new);
	public static final RegistryObject<Item> RAINBOW_DINDIN = ITEMS.register("rainbow_dindin", () -> new HorseTransformItem(SWEMCoatColors.RAINBOW));

	// SADDLES
	public static final RegistryObject<WesternSaddleItem> WESTERN_SADDLE_LIGHT_BLUE = ITEMS.register("western_saddle_light_blue", () -> new WesternSaddleItem("western_saddle_light_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternSaddleItem> WESTERN_SADDLE_WHITE = ITEMS.register("western_saddle_white", () -> new WesternSaddleItem("western_saddle_white", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternSaddleItem> WESTERN_SADDLE_ORANGE = ITEMS.register("western_saddle_orange", () -> new WesternSaddleItem("western_saddle_orange", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternSaddleItem> WESTERN_SADDLE_MAGENTA = ITEMS.register("western_saddle_magenta", () -> new WesternSaddleItem("western_saddle_magenta", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternSaddleItem> WESTERN_SADDLE_YELLOW = ITEMS.register("western_saddle_yellow", () -> new WesternSaddleItem("western_saddle_yellow", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternSaddleItem> WESTERN_SADDLE_LIME = ITEMS.register("western_saddle_lime", () -> new WesternSaddleItem("western_saddle_lime", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternSaddleItem> WESTERN_SADDLE_PINK = ITEMS.register("western_saddle_pink", () -> new WesternSaddleItem("western_saddle_pink", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternSaddleItem> WESTERN_SADDLE_GRAY = ITEMS.register("western_saddle_gray", () -> new WesternSaddleItem("western_saddle_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternSaddleItem> WESTERN_SADDLE_LIGHT_GRAY = ITEMS.register("western_saddle_light_gray", () -> new WesternSaddleItem("western_saddle_light_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternSaddleItem> WESTERN_SADDLE_CYAN = ITEMS.register("western_saddle_cyan", () -> new WesternSaddleItem("western_saddle_cyan", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternSaddleItem> WESTERN_SADDLE_PURPLE = ITEMS.register("western_saddle_purple", () -> new WesternSaddleItem("western_saddle_purple", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternSaddleItem> WESTERN_SADDLE_BLUE = ITEMS.register("western_saddle_blue", () -> new WesternSaddleItem("western_saddle_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternSaddleItem> WESTERN_SADDLE_BROWN = ITEMS.register("western_saddle_brown", () -> new WesternSaddleItem("western_saddle_brown", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternSaddleItem> WESTERN_SADDLE_GREEN = ITEMS.register("western_saddle_green", () -> new WesternSaddleItem("western_saddle_green", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternSaddleItem> WESTERN_SADDLE_RED = ITEMS.register("western_saddle_red", () -> new WesternSaddleItem("western_saddle_red", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternSaddleItem> WESTERN_SADDLE_BLACK = ITEMS.register("western_saddle_black", () -> new WesternSaddleItem("western_saddle_black", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishSaddleItem> ENGLISH_SADDLE_BLACK = ITEMS.register("english_saddle_black", () -> new EnglishSaddleItem("english_saddle_black", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishSaddleItem> ENGLISH_SADDLE_BROWN = ITEMS.register("english_saddle_brown", () -> new EnglishSaddleItem("english_saddle_brown", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<AdventureSaddleItem> ADVENTURE_SADDLE = ITEMS.register("adventure_saddle", () -> new AdventureSaddleItem("adventure_saddle", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<AdventureSaddleItem> ADVENTURE_SADDLE_GLOW = ITEMS.register("adventure_saddle_glow", () -> new AdventureSaddleItem("adventure_saddle_glow", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));

	// BLANKETS
	public static final RegistryObject<EnglishBlanketItem> ENGLISH_BLANKET_BLACK = ITEMS.register("english_blanket_black", () -> new EnglishBlanketItem("english_blanket_black", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBlanketItem> ENGLISH_BLANKET_BLUE = ITEMS.register("english_blanket_blue", () -> new EnglishBlanketItem("english_blanket_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBlanketItem> ENGLISH_BLANKET_BROWN = ITEMS.register("english_blanket_brown", () -> new EnglishBlanketItem("english_blanket_brown", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBlanketItem> ENGLISH_BLANKET_CYAN = ITEMS.register("english_blanket_cyan", () -> new EnglishBlanketItem("english_blanket_cyan", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBlanketItem> ENGLISH_BLANKET_GREEN = ITEMS.register("english_blanket_green", () -> new EnglishBlanketItem("english_blanket_green", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBlanketItem> ENGLISH_BLANKET_GRAY = ITEMS.register("english_blanket_gray", () -> new EnglishBlanketItem("english_blanket_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBlanketItem> ENGLISH_BLANKET_LIGHT_BLUE = ITEMS.register("english_blanket_light_blue", () -> new EnglishBlanketItem("english_blanket_light_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBlanketItem> ENGLISH_BLANKET_LIGHT_GRAY = ITEMS.register("english_blanket_light_gray", () -> new EnglishBlanketItem("english_blanket_light_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBlanketItem> ENGLISH_BLANKET_LIME = ITEMS.register("english_blanket_lime", () -> new EnglishBlanketItem("english_blanket_lime", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBlanketItem> ENGLISH_BLANKET_MAGENTA = ITEMS.register("english_blanket_magenta", () -> new EnglishBlanketItem("english_blanket_magenta", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBlanketItem> ENGLISH_BLANKET_ORANGE = ITEMS.register("english_blanket_orange", () -> new EnglishBlanketItem("english_blanket_orange", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBlanketItem> ENGLISH_BLANKET_PINK = ITEMS.register("english_blanket_pink", () -> new EnglishBlanketItem("english_blanket_pink", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBlanketItem> ENGLISH_BLANKET_PURPLE = ITEMS.register("english_blanket_purple", () -> new EnglishBlanketItem("english_blanket_purple", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBlanketItem> ENGLISH_BLANKET_RED = ITEMS.register("english_blanket_red", () -> new EnglishBlanketItem("english_blanket_red", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBlanketItem> ENGLISH_BLANKET_WHITE = ITEMS.register("english_blanket_white", () -> new EnglishBlanketItem("english_blanket_white", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBlanketItem> ENGLISH_BLANKET_YELLOW = ITEMS.register("english_blanket_yellow", () -> new EnglishBlanketItem("english_blanket_yellow", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBlanketItem> WESTERN_BLANKET_BLACK = ITEMS.register("western_blanket_black", () -> new WesternBlanketItem("western_blanket_black", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBlanketItem> WESTERN_BLANKET_BLUE = ITEMS.register("western_blanket_blue", () -> new WesternBlanketItem("western_blanket_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBlanketItem> WESTERN_BLANKET_BROWN = ITEMS.register("western_blanket_brown", () -> new WesternBlanketItem("western_blanket_brown", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBlanketItem> WESTERN_BLANKET_CYAN = ITEMS.register("western_blanket_cyan", () -> new WesternBlanketItem("western_blanket_cyan", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBlanketItem> WESTERN_BLANKET_GREEN = ITEMS.register("western_blanket_green", () -> new WesternBlanketItem("western_blanket_green", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBlanketItem> WESTERN_BLANKET_GRAY = ITEMS.register("western_blanket_gray", () -> new WesternBlanketItem("western_blanket_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBlanketItem> WESTERN_BLANKET_LIGHT_BLUE = ITEMS.register("western_blanket_light_blue", () -> new WesternBlanketItem("western_blanket_light_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBlanketItem> WESTERN_BLANKET_LIGHT_GRAY = ITEMS.register("western_blanket_light_gray", () -> new WesternBlanketItem("western_blanket_light_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBlanketItem> WESTERN_BLANKET_LIME = ITEMS.register("western_blanket_lime", () -> new WesternBlanketItem("western_blanket_lime", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBlanketItem> WESTERN_BLANKET_MAGENTA = ITEMS.register("western_blanket_magenta", () -> new WesternBlanketItem("western_blanket_magenta", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBlanketItem> WESTERN_BLANKET_ORANGE = ITEMS.register("western_blanket_orange", () -> new WesternBlanketItem("western_blanket_orange", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBlanketItem> WESTERN_BLANKET_PINK = ITEMS.register("western_blanket_pink", () -> new WesternBlanketItem("western_blanket_pink", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBlanketItem> WESTERN_BLANKET_PURPLE = ITEMS.register("western_blanket_purple", () -> new WesternBlanketItem("western_blanket_purple", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBlanketItem> WESTERN_BLANKET_RED = ITEMS.register("western_blanket_red", () -> new WesternBlanketItem("western_blanket_red", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBlanketItem> WESTERN_BLANKET_WHITE = ITEMS.register("western_blanket_white", () -> new WesternBlanketItem("western_blanket_white", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBlanketItem> WESTERN_BLANKET_YELLOW = ITEMS.register("western_blanket_yellow", () -> new WesternBlanketItem("western_blanket_yellow", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));

	// BRIDLE
	public static final RegistryObject<EnglishBridleItem> ENGLISH_BRIDLE_BLACK = ITEMS.register("english_bridle_black", () -> new EnglishBridleItem("english_bridle_black", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBridleItem> ENGLISH_BRIDLE_BROWN = ITEMS.register("english_bridle_brown", () -> new EnglishBridleItem("english_bridle_brown", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBridleItem> WESTERN_BRIDLE_BLACK = ITEMS.register("western_bridle_black", () -> new WesternBridleItem("western_bridle_black", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBridleItem> WESTERN_BRIDLE_BROWN = ITEMS.register("western_bridle_brown", () -> new WesternBridleItem("western_bridle_brown", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBridleItem> WESTERN_BRIDLE_BLUE = ITEMS.register("western_bridle_blue", () -> new WesternBridleItem("western_bridle_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBridleItem> WESTERN_BRIDLE_CYAN = ITEMS.register("western_bridle_cyan", () -> new WesternBridleItem("western_bridle_cyan", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBridleItem> WESTERN_BRIDLE_LIME = ITEMS.register("western_bridle_lime", () -> new WesternBridleItem("western_bridle_lime", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBridleItem> WESTERN_BRIDLE_LIGHT_BLUE = ITEMS.register("western_bridle_light_blue", () -> new WesternBridleItem("western_bridle_light_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBridleItem> WESTERN_BRIDLE_LIGHT_GRAY = ITEMS.register("western_bridle_light_gray", () -> new WesternBridleItem("western_bridle_light_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBridleItem> WESTERN_BRIDLE_GREEN = ITEMS.register("western_bridle_green", () -> new WesternBridleItem("western_bridle_green", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBridleItem> WESTERN_BRIDLE_GRAY = ITEMS.register("western_bridle_gray", () -> new WesternBridleItem("western_bridle_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBridleItem> WESTERN_BRIDLE_ORANGE = ITEMS.register("western_bridle_orange", () -> new WesternBridleItem("western_bridle_orange", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBridleItem> WESTERN_BRIDLE_MAGENTA = ITEMS.register("western_bridle_magenta", () -> new WesternBridleItem("western_bridle_magenta", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBridleItem> WESTERN_BRIDLE_PINK = ITEMS.register("western_bridle_pink", () -> new WesternBridleItem("western_bridle_pink", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBridleItem> WESTERN_BRIDLE_PURPLE = ITEMS.register("western_bridle_purple", () -> new WesternBridleItem("western_bridle_purple", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBridleItem> WESTERN_BRIDLE_RED = ITEMS.register("western_bridle_red", () -> new WesternBridleItem("western_bridle_red", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBridleItem> WESTERN_BRIDLE_WHITE = ITEMS.register("western_bridle_white", () -> new WesternBridleItem("western_bridle_white", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBridleItem> WESTERN_BRIDLE_YELLOW = ITEMS.register("western_bridle_yellow", () -> new WesternBridleItem("western_bridle_yellow", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));

	// HALTER
	public static final RegistryObject<HalterItem> HALTER_WHITE = ITEMS.register("halter_white", () -> new HalterItem("halter_white", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<HalterItem> HALTER_BLACK = ITEMS.register("halter_black", () -> new HalterItem("halter_black", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<HalterItem> HALTER_BLUE = ITEMS.register("halter_blue", () -> new HalterItem("halter_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<HalterItem> HALTER_BROWN = ITEMS.register("halter_brown", () -> new HalterItem("halter_brown", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<HalterItem> HALTER_CYAN = ITEMS.register("halter_cyan", () -> new HalterItem("halter_cyan", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<HalterItem> HALTER_GREEN = ITEMS.register("halter_green", () -> new HalterItem("halter_green", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<HalterItem> HALTER_GRAY = ITEMS.register("halter_gray", () -> new HalterItem("halter_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<HalterItem> HALTER_LIGHT_BLUE = ITEMS.register("halter_light_blue", () -> new HalterItem("halter_light_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<HalterItem> HALTER_LIGHT_GRAY = ITEMS.register("halter_light_gray", () -> new HalterItem("halter_light_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<HalterItem> HALTER_LIME = ITEMS.register("halter_lime", () -> new HalterItem("halter_lime", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<HalterItem> HALTER_MAGENTA = ITEMS.register("halter_magenta", () -> new HalterItem("halter_magenta", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<HalterItem> HALTER_ORANGE = ITEMS.register("halter_orange", () -> new HalterItem("halter_orange", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<HalterItem> HALTER_PINK = ITEMS.register("halter_pink", () -> new HalterItem("halter_pink", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<HalterItem> HALTER_PURPLE = ITEMS.register("halter_purple", () -> new HalterItem("halter_purple", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<HalterItem> HALTER_RED = ITEMS.register("halter_red", () -> new HalterItem("halter_red", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<HalterItem> HALTER_YELLOW = ITEMS.register("halter_yellow", () -> new HalterItem("halter_yellow", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));

	// GIRTH STRAP
	public static final RegistryObject<WesternGirthStrapItem> WESTERN_GIRTH_STRAP_WHITE = ITEMS.register("western_girth_strap_white", () -> new WesternGirthStrapItem("western_girth_strap_white", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternGirthStrapItem> WESTERN_GIRTH_STRAP_BLACK = ITEMS.register("western_girth_strap_black", () -> new WesternGirthStrapItem("western_girth_strap_black", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternGirthStrapItem> WESTERN_GIRTH_STRAP_BROWN = ITEMS.register("western_girth_strap_brown", () -> new WesternGirthStrapItem("western_girth_strap_brown", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternGirthStrapItem> WESTERN_GIRTH_STRAP_BLUE = ITEMS.register("western_girth_strap_blue", () -> new WesternGirthStrapItem("western_girth_strap_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternGirthStrapItem> WESTERN_GIRTH_STRAP_CYAN = ITEMS.register("western_girth_strap_cyan", () -> new WesternGirthStrapItem("western_girth_strap_cyan", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternGirthStrapItem> WESTERN_GIRTH_STRAP_GREEN = ITEMS.register("western_girth_strap_green", () -> new WesternGirthStrapItem("western_girth_strap_green", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternGirthStrapItem> WESTERN_GIRTH_STRAP_GRAY = ITEMS.register("western_girth_strap_gray", () -> new WesternGirthStrapItem("western_girth_strap_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternGirthStrapItem> WESTERN_GIRTH_STRAP_LIGHT_BLUE = ITEMS.register("western_girth_strap_light_blue", () -> new WesternGirthStrapItem("western_girth_strap_light_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternGirthStrapItem> WESTERN_GIRTH_STRAP_LIGHT_GRAY = ITEMS.register("western_girth_strap_light_gray", () -> new WesternGirthStrapItem("western_girth_strap_light_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternGirthStrapItem> WESTERN_GIRTH_STRAP_LIME = ITEMS.register("western_girth_strap_lime", () -> new WesternGirthStrapItem("western_girth_strap_lime", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternGirthStrapItem> WESTERN_GIRTH_STRAP_MAGENTA = ITEMS.register("western_girth_strap_magenta", () -> new WesternGirthStrapItem("western_girth_strap_magenta", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternGirthStrapItem> WESTERN_GIRTH_STRAP_ORANGE = ITEMS.register("western_girth_strap_orange", () -> new WesternGirthStrapItem("western_girth_strap_orange", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternGirthStrapItem> WESTERN_GIRTH_STRAP_PINK = ITEMS.register("western_girth_strap_pink", () -> new WesternGirthStrapItem("western_girth_strap_pink", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternGirthStrapItem> WESTERN_GIRTH_STRAP_PURPLE = ITEMS.register("western_girth_strap_purple", () -> new WesternGirthStrapItem("western_girth_strap_purple", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternGirthStrapItem> WESTERN_GIRTH_STRAP_RED = ITEMS.register("western_girth_strap_red", () -> new WesternGirthStrapItem("western_girth_strap_red", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternGirthStrapItem> WESTERN_GIRTH_STRAP_YELLOW = ITEMS.register("western_girth_strap_yellow", () -> new WesternGirthStrapItem("western_girth_strap_yellow", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishGirthStrap> ENGLISH_GIRTH_STRAP_BLACK = ITEMS.register("english_girth_strap_black", () -> new EnglishGirthStrap("english_girth_strap_black", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishGirthStrap> ENGLISH_GIRTH_STRAP_BROWN = ITEMS.register("english_girth_strap_brown", () -> new EnglishGirthStrap("english_girth_strap_brown", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));

	// LEG WRAPS
	public static final RegistryObject<WesternLegWraps> WESTERN_LEG_WRAPS_WHITE = ITEMS.register("western_leg_wraps_white", () -> new WesternLegWraps("western_leg_wraps_white", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternLegWraps> WESTERN_LEG_WRAPS_BLACK = ITEMS.register("western_leg_wraps_black", () -> new WesternLegWraps("western_leg_wraps_black", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternLegWraps> WESTERN_LEG_WRAPS_BROWN = ITEMS.register("western_leg_wraps_brown", () -> new WesternLegWraps("western_leg_wraps_brown", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternLegWraps> WESTERN_LEG_WRAPS_BLUE = ITEMS.register("western_leg_wraps_blue", () -> new WesternLegWraps("western_leg_wraps_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternLegWraps> WESTERN_LEG_WRAPS_CYAN = ITEMS.register("western_leg_wraps_cyan", () -> new WesternLegWraps("western_leg_wraps_cyan", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternLegWraps> WESTERN_LEG_WRAPS_GREEN = ITEMS.register("western_leg_wraps_green", () -> new WesternLegWraps("western_leg_wraps_green", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternLegWraps> WESTERN_LEG_WRAPS_GRAY = ITEMS.register("western_leg_wraps_gray", () -> new WesternLegWraps("western_leg_wraps_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternLegWraps> WESTERN_LEG_WRAPS_LIGHT_BLUE = ITEMS.register("western_leg_wraps_light_blue", () -> new WesternLegWraps("western_leg_wraps_light_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternLegWraps> WESTERN_LEG_WRAPS_LIGHT_GRAY = ITEMS.register("western_leg_wraps_light_gray", () -> new WesternLegWraps("western_leg_wraps_light_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternLegWraps> WESTERN_LEG_WRAPS_LIME = ITEMS.register("western_leg_wraps_lime", () -> new WesternLegWraps("western_leg_wraps_lime", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternLegWraps> WESTERN_LEG_WRAPS_MAGENTA = ITEMS.register("western_leg_wraps_magenta", () -> new WesternLegWraps("western_leg_wraps_magenta", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternLegWraps> WESTERN_LEG_WRAPS_ORANGE = ITEMS.register("western_leg_wraps_orange", () -> new WesternLegWraps("western_leg_wraps_orange", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternLegWraps> WESTERN_LEG_WRAPS_PINK = ITEMS.register("western_leg_wraps_pink", () -> new WesternLegWraps("western_leg_wraps_pink", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternLegWraps> WESTERN_LEG_WRAPS_PURPLE = ITEMS.register("western_leg_wraps_purple", () -> new WesternLegWraps("western_leg_wraps_purple", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternLegWraps> WESTERN_LEG_WRAPS_RED = ITEMS.register("western_leg_wraps_red", () -> new WesternLegWraps("western_leg_wraps_red", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternLegWraps> WESTERN_LEG_WRAPS_YELLOW = ITEMS.register("western_leg_wraps_yellow", () -> new WesternLegWraps("western_leg_wraps_yellow", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishLegWraps> ENGLISH_LEG_WRAPS_WHITE = ITEMS.register("english_leg_wraps_white", () -> new EnglishLegWraps("english_leg_wraps_white", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishLegWraps> ENGLISH_LEG_WRAPS_BLACK = ITEMS.register("english_leg_wraps_black", () -> new EnglishLegWraps("english_leg_wraps_black", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishLegWraps> ENGLISH_LEG_WRAPS_BROWN = ITEMS.register("english_leg_wraps_brown", () -> new EnglishLegWraps("english_leg_wraps_brown", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishLegWraps> ENGLISH_LEG_WRAPS_BLUE = ITEMS.register("english_leg_wraps_blue", () -> new EnglishLegWraps("english_leg_wraps_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishLegWraps> ENGLISH_LEG_WRAPS_CYAN = ITEMS.register("english_leg_wraps_cyan", () -> new EnglishLegWraps("english_leg_wraps_cyan", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishLegWraps> ENGLISH_LEG_WRAPS_GREEN = ITEMS.register("english_leg_wraps_green", () -> new EnglishLegWraps("english_leg_wraps_green", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishLegWraps> ENGLISH_LEG_WRAPS_GRAY = ITEMS.register("english_leg_wraps_gray", () -> new EnglishLegWraps("english_leg_wraps_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishLegWraps> ENGLISH_LEG_WRAPS_LIGHT_BLUE = ITEMS.register("english_leg_wraps_light_blue", () -> new EnglishLegWraps("english_leg_wraps_light_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishLegWraps> ENGLISH_LEG_WRAPS_LIGHT_GRAY = ITEMS.register("english_leg_wraps_light_gray", () -> new EnglishLegWraps("english_leg_wraps_light_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishLegWraps> ENGLISH_LEG_WRAPS_LIME = ITEMS.register("english_leg_wraps_lime", () -> new EnglishLegWraps("english_leg_wraps_lime", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishLegWraps> ENGLISH_LEG_WRAPS_MAGENTA = ITEMS.register("english_leg_wraps_magenta", () -> new EnglishLegWraps("english_leg_wraps_magenta", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishLegWraps> ENGLISH_LEG_WRAPS_ORANGE = ITEMS.register("english_leg_wraps_orange", () -> new EnglishLegWraps("english_leg_wraps_orange", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishLegWraps> ENGLISH_LEG_WRAPS_PINK = ITEMS.register("english_leg_wraps_pink", () -> new EnglishLegWraps("english_leg_wraps_pink", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishLegWraps> ENGLISH_LEG_WRAPS_PURPLE = ITEMS.register("english_leg_wraps_purple", () -> new EnglishLegWraps("english_leg_wraps_purple", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishLegWraps> ENGLISH_LEG_WRAPS_RED = ITEMS.register("english_leg_wraps_red", () -> new EnglishLegWraps("english_leg_wraps_red", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishLegWraps> ENGLISH_LEG_WRAPS_YELLOW = ITEMS.register("english_leg_wraps_yellow", () -> new EnglishLegWraps("english_leg_wraps_yellow", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));

	// BREAST COLLAR
	public static final RegistryObject<WesternBreastCollarItem> WESTERN_BREAST_COLLAR_WHITE = ITEMS.register("western_breast_collar_white", () -> new WesternBreastCollarItem("western_breast_collar_white", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBreastCollarItem> WESTERN_BREAST_COLLAR_BLACK = ITEMS.register("western_breast_collar_black", () -> new WesternBreastCollarItem("western_breast_collar_black", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBreastCollarItem> WESTERN_BREAST_COLLAR_BROWN = ITEMS.register("western_breast_collar_brown", () -> new WesternBreastCollarItem("western_breast_collar_brown", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBreastCollarItem> WESTERN_BREAST_COLLAR_BLUE = ITEMS.register("western_breast_collar_blue", () -> new WesternBreastCollarItem("western_breast_collar_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBreastCollarItem> WESTERN_BREAST_COLLAR_CYAN = ITEMS.register("western_breast_collar_cyan", () -> new WesternBreastCollarItem("western_breast_collar_cyan", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBreastCollarItem> WESTERN_BREAST_COLLAR_GREEN = ITEMS.register("western_breast_collar_green", () -> new WesternBreastCollarItem("western_breast_collar_green", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBreastCollarItem> WESTERN_BREAST_COLLAR_GRAY = ITEMS.register("western_breast_collar_gray", () -> new WesternBreastCollarItem("western_breast_collar_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBreastCollarItem> WESTERN_BREAST_COLLAR_LIGHT_BLUE = ITEMS.register("western_breast_collar_light_blue", () -> new WesternBreastCollarItem("western_breast_collar_light_blue", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBreastCollarItem> WESTERN_BREAST_COLLAR_LIGHT_GRAY = ITEMS.register("western_breast_collar_light_gray", () -> new WesternBreastCollarItem("western_breast_collar_light_gray", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBreastCollarItem> WESTERN_BREAST_COLLAR_LIME = ITEMS.register("western_breast_collar_lime", () -> new WesternBreastCollarItem("western_breast_collar_lime", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBreastCollarItem> WESTERN_BREAST_COLLAR_MAGENTA = ITEMS.register("western_breast_collar_magenta", () -> new WesternBreastCollarItem("western_breast_collar_magenta", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBreastCollarItem> WESTERN_BREAST_COLLAR_ORANGE = ITEMS.register("western_breast_collar_orange", () -> new WesternBreastCollarItem("western_breast_collar_orange", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBreastCollarItem> WESTERN_BREAST_COLLAR_PINK = ITEMS.register("western_breast_collar_pink", () -> new WesternBreastCollarItem("western_breast_collar_pink", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBreastCollarItem> WESTERN_BREAST_COLLAR_PURPLE = ITEMS.register("western_breast_collar_purple", () -> new WesternBreastCollarItem("western_breast_collar_purple", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBreastCollarItem> WESTERN_BREAST_COLLAR_RED = ITEMS.register("western_breast_collar_red", () -> new WesternBreastCollarItem("western_breast_collar_red", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<WesternBreastCollarItem> WESTERN_BREAST_COLLAR_YELLOW = ITEMS.register("western_breast_collar_yellow", () -> new WesternBreastCollarItem("western_breast_collar_yellow", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBreastCollar> ENGLISH_BREAST_COLLAR_BLACK = ITEMS.register("english_breast_collar_black", () -> new EnglishBreastCollar("english_breast_collar_black", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));
	public static final RegistryObject<EnglishBreastCollar> ENGLISH_BREAST_COLLAR_BROWN = ITEMS.register("english_breast_collar_brown", () -> new EnglishBreastCollar("english_breast_collar_brown", new Item.Properties().group(SWEM.TAB).maxStackSize(1)));

	// Tools
	public static final RegistryObject<SwordItem> AMETHYST_LONGSWORD = ITEMS.register("amethyst_longsword", () ->
			new AmethystSword(SWEMItemTier.AMETHYST, 4, 2F, new Item.Properties().group(SWEM.TAB))
	);
	public static final RegistryObject<BowItem> AMETHYST_BOW = ITEMS.register("amethyst_bow", () ->
			new AmethystBow(new Item.Properties().group(SWEM.TAB))
	);
	public static final RegistryObject<AmethystScythe> AMETHYST_SCYTHE = ITEMS.register("amethyst_scythe", () ->
			new AmethystScythe(SWEMItemTier.AMETHYST, 4, 2F, new Item.Properties().group(SWEM.TAB))
	);
	public static final RegistryObject<ShieldItem> AMETHYST_SHIELD = ITEMS.register("amethyst_shield", () ->
			new AmethystShield(new Item.Properties().group(SWEM.TAB))
	);
}
