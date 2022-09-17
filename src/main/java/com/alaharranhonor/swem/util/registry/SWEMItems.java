package com.alaharranhonor.swem.util.registry;

/*
 * All Rights Reserved
 *
 * Copyright (c) 2021, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.armor.*;
import com.alaharranhonor.swem.blocks.BlockItemBase;
import com.alaharranhonor.swem.client.coats.SWEMCoatColor;
import com.alaharranhonor.swem.items.*;
import com.alaharranhonor.swem.items.potions.CantazaritePotionItem;
import com.alaharranhonor.swem.items.potions.PotionItemBase;
import com.alaharranhonor.swem.items.tack.*;
import com.alaharranhonor.swem.tools.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SWEMItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SWEM.MOD_ID);

    /**
     * Init.
     *
     * @param modBus the mod bus
     */
    public static void init(IEventBus modBus) {
        ITEMS.register(modBus);
    }

    public static final RegistryObject<Item> DIAMOND_PLATE = ITEMS.register("diamond_plate", ItemBase::new);
    public static final RegistryObject<Item> GOLD_PLATE = ITEMS.register("gold_plate", ItemBase::new);
    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate", ItemBase::new);
    public static final RegistryObject<Item> DIAMOND_RIVET = ITEMS.register("diamond_rivet", ItemBase::new);
    public static final RegistryObject<Item> GOLD_RIVET = ITEMS.register("gold_rivet", ItemBase::new);
    public static final RegistryObject<Item> IRON_RIVET = ITEMS.register("iron_rivet", ItemBase::new);
    public static final RegistryObject<SWEMArmorItem> LEATHER_RIDING_BOOTS = ITEMS.register("leather_riding_boots", () -> new LeatherRidingBoots("leather_layer", ModArmorMaterial.LEATHER, EquipmentSlotType.FEET, new Item.Properties().tab(SWEM.TAB).stacksTo(1))); // () -> (Supplier<ArmorBaseModel>)

    public static final RegistryObject<SWEMArmorItem> GLOW_RIDING_BOOTS = ITEMS.register("glow_riding_boots", () -> new GlowRidingBoots("glow_layer", ModArmorMaterial.GLOW, EquipmentSlotType.FEET, new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<Item> IRON_SCYTHE = ITEMS.register("iron_scythe", NonWearableItem::new);
    public static final RegistryObject<SWEMArmorItem> IRON_RIDING_BOOTS = ITEMS.register("iron_riding_boots", () -> new IronRidingBoots("iron_layer", ModArmorMaterial.IRON, EquipmentSlotType.FEET, new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<Item> IRON_LONGSWORD = ITEMS.register("iron_longsword", NonWearableItem::new);
    public static final RegistryObject<Item> IRON_LEGGINGS = ITEMS.register("iron_leggings", NonWearableItem::new);
    public static final RegistryObject<Item> IRON_HELMET = ITEMS.register("iron_helmet", NonWearableItem::new);
    public static final RegistryObject<Item> IRON_CHESTPLATE = ITEMS.register("iron_chestplate", NonWearableItem::new);
    public static final RegistryObject<Item> IRON_BOW = ITEMS.register("iron_bow", NonWearableItem::new);
    public static final RegistryObject<Item> GOLD_SCYTHE = ITEMS.register("gold_scythe", NonWearableItem::new);
    public static final RegistryObject<SWEMArmorItem> GOLD_RIDING_BOOTS = ITEMS.register("gold_riding_boots", () -> new GoldRidingBoots("gold_layer", ModArmorMaterial.GOLD, EquipmentSlotType.FEET, new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<Item> GOLD_LONGSWORD = ITEMS.register("gold_longsword", NonWearableItem::new);
    public static final RegistryObject<Item> GOLD_LEGGINGS = ITEMS.register("gold_leggings", NonWearableItem::new);
    public static final RegistryObject<Item> GOLD_HELMET = ITEMS.register("gold_helmet", NonWearableItem::new);
    public static final RegistryObject<Item> GOLD_CHESTPLATE = ITEMS.register("gold_chestplate", NonWearableItem::new);
    public static final RegistryObject<Item> GOLD_BOW = ITEMS.register("gold_bow", NonWearableItem::new);
    public static final RegistryObject<Item> DIAMOND_SCYTHE = ITEMS.register("diamond_scythe", NonWearableItem::new);
    public static final RegistryObject<SWEMArmorItem> DIAMOND_RIDING_BOOTS = ITEMS.register("diamond_riding_boots", () -> new DiamondRidingBoots("diamond_layer", ModArmorMaterial.DIAMOND, EquipmentSlotType.FEET, new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<Item> DIAMOND_LONGSWORD = ITEMS.register("diamond_longsword", NonWearableItem::new);
    public static final RegistryObject<Item> DIAMOND_LEGGINGS = ITEMS.register("diamond_leggings", NonWearableItem::new);
    public static final RegistryObject<Item> DIAMOND_HELMET = ITEMS.register("diamond_helmet", NonWearableItem::new);
    public static final RegistryObject<Item> DIAMOND_CHESTPLATE = ITEMS.register("diamond_chestplate", NonWearableItem::new);
    public static final RegistryObject<Item> DIAMOND_BOW = ITEMS.register("diamond_bow", NonWearableItem::new);
    public static final RegistryObject<SWEMArmorItem> AMETHYST_HELMET = ITEMS.register("amethyst_helmet", () -> new AmethystHelmet("amethyst_layer", ModArmorMaterial.AMETHYST, EquipmentSlotType.HEAD, new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<SWEMArmorItem> AMETHYST_CHESTPLATE = ITEMS.register("amethyst_chestplate", () -> new AmethystChestplate("amethyst_layer", ModArmorMaterial.AMETHYST, EquipmentSlotType.CHEST, new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<SWEMArmorItem> AMETHYST_PANTS = ITEMS.register("amethyst_pants", () -> new AmethystLeggings("amethyst_layer", ModArmorMaterial.AMETHYST, EquipmentSlotType.LEGS, new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<Item> AMETHYST_SHIELD_IRON = ITEMS.register("amethyst_shield_iron", NonWearableItem::new);
    public static final RegistryObject<Item> AMETHYST_SHIELD_GOLD = ITEMS.register("amethyst_shield_gold", NonWearableItem::new);
    public static final RegistryObject<Item> AMETHYST_SHIELD_DIAMOND = ITEMS.register("amethyst_shield_diamond", NonWearableItem::new);
    public static final RegistryObject<SWEMArmorItem> AMETHYST_RIDING_BOOTS = ITEMS.register("amethyst_riding_boots", () -> new AmethystRidingBoots("amethyst_layer", ModArmorMaterial.AMETHYST, EquipmentSlotType.FEET, new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<Item> CANTAZARITE = ITEMS.register("cantazarite", ItemBase::new);
    public static final RegistryObject<Item> AMETHYST = ITEMS.register("amethyst", AmethystItem::new);
    public static final RegistryObject<Item> TIMOTHY_BUSHEL = ITEMS.register("timothy_bushel", ItemBase::new);
    public static final RegistryObject<Item> OAT_BUSHEL = ITEMS.register("oat_bushel", ItemBase::new);
    public static final RegistryObject<Item> ALFALFA_BUSHEL = ITEMS.register("alfalfa_bushel", ItemBase::new);
    public static final RegistryObject<Item> TIMOTHY_SEEDS = ITEMS.register("timothy_seeds", () -> new BlockItemBase(SWEMBlocks.TIMOTHY_PLANT.get()));
    public static final RegistryObject<Item> ALFALFA_SEEDS = ITEMS.register("alfalfa_seeds", () -> new BlockItemBase(SWEMBlocks.ALFALFA_PLANT.get()));
    public static final RegistryObject<Item> OAT_SEEDS = ITEMS.register("oat_seeds", () -> new BlockItemBase(SWEMBlocks.OAT_PLANT.get()));
    public static final RegistryObject<FenceToolItem> FENCE_TOOL = ITEMS.register("fence_tool", () -> new FenceToolItem(new Item.Properties().tab(SWEM.TAB).stacksTo(64)));
    public static final RegistryObject<WhistleItem> WHISTLE = ITEMS.register("whistle", WhistleItem::new);
    public static final RegistryObject<PotionItem> CANTAZARITE_POTION = ITEMS.register("cantazarite_potion", () -> new CantazaritePotionItem(new Item.Properties().stacksTo(1).tab(SWEM.TAB)));
    public static final RegistryObject<Item> CANTAZARITE_DYE = ITEMS.register("cantazarite_dye", ItemBase::new);
    public static final RegistryObject<Item> PENDANT = ITEMS.register("pendant", PendantItem::new);
    public static final RegistryObject<Item> SUGAR_CUBE = ITEMS.register("sugar_cube", ItemBase::new);
    public static final RegistryObject<DesensitizingItem> BELLS = ITEMS.register("bells", () -> new DesensitizingItem(0));
    public static final RegistryObject<DesensitizingItem> HOOLAHOOP = ITEMS.register("hoolahoop", () -> new DesensitizingItem(1));
    public static final RegistryObject<DesensitizingItem> POMPOM = ITEMS.register("pompom", () -> new DesensitizingItem(2));
    public static final RegistryObject<DesensitizingItem> SHOPPING_BAG = ITEMS.register("shopping_bag", () -> new DesensitizingItem(3));
    public static final RegistryObject<DesensitizingItem> TARP = ITEMS.register("tarp", () -> new DesensitizingItem(4));
    public static final RegistryObject<PitchforkTool> PITCHFORK = ITEMS.register("pitchfork", PitchforkTool::new);
    public static final RegistryObject<HorseXPPotion> SPEED_XP_POTION = ITEMS.register("speed_xp_potion", () -> new HorseXPPotion("speed"));
    public static final RegistryObject<HorseXPPotion> JUMP_XP_POTION = ITEMS.register("jump_xp_potion", () -> new HorseXPPotion("jump"));
    public static final RegistryObject<HorseXPPotion> AFFINITY_XP_POTION = ITEMS.register("affinity_xp_potion", () -> new HorseXPPotion("affinity"));
    public static final RegistryObject<HorseXPPotion> HEALTH_XP_POTION = ITEMS.register("health_xp_potion", () -> new HorseXPPotion("health"));
    public static final RegistryObject<HorseXPPotion> ALL_XP_POTION = ITEMS.register("all_xp_potion", () -> new HorseXPPotion("all"));
    public static final RegistryObject<HoseItem> HOSE = ITEMS.register("hose", () -> new HoseItem(new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<SWEMHorseArmorItem> CLOTH_HORSE_ARMOR = ITEMS.register("cloth_horse_armor", () -> new SWEMHorseArmorItem(SWEMHorseArmorItem.HorseArmorTier.CLOTH, 10, "cloth", new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<SWEMHorseArmorItem> IRON_HORSE_ARMOR = ITEMS.register("iron_horse_armor", () -> new SWEMHorseArmorItem(SWEMHorseArmorItem.HorseArmorTier.IRON, 30, "iron", new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<SWEMHorseArmorItem> GOLD_HORSE_ARMOR = ITEMS.register("gold_horse_armor", () -> new SWEMHorseArmorItem(SWEMHorseArmorItem.HorseArmorTier.GOLD, 32, "gold", new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<SWEMHorseArmorItem> DIAMOND_HORSE_ARMOR = ITEMS.register("diamond_horse_armor", () -> new SWEMHorseArmorItem(SWEMHorseArmorItem.HorseArmorTier.DIAMOND, 37, "diamond", new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<SWEMHorseArmorItem> AMETHYST_HORSE_ARMOR = ITEMS.register("amethyst_horse_armor", () -> new SWEMHorseArmorItem(SWEMHorseArmorItem.HorseArmorTier.AMETHYST, 50, "amethyst", new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<SWEMHorseArmorItem> AMETHYST_USA_HORSE_ARMOR = ITEMS.register("amethyst_usa_horse_armor", () -> new SWEMHorseArmorItem(SWEMHorseArmorItem.HorseArmorTier.AMETHYST, 50, "amethyst_usa", new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<SWEMHorseArmorItem> AMETHYST_NETHERITE_HORSE_ARMOR = ITEMS.register("amethyst_netherite_horse_armor", () -> new SWEMHorseArmorItem(SWEMHorseArmorItem.HorseArmorTier.AMETHYST, 50, "amethyst_netherite", new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<SWEMHorseArmorItem> AMETHYST_EMERALD_HORSE_ARMOR = ITEMS.register("amethyst_emerald_horse_armor", () -> new SWEMHorseArmorItem(SWEMHorseArmorItem.HorseArmorTier.AMETHYST, 50, "amethyst_emerald", new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<SWEMHorseArmorItem> AMETHYST_COPPER_HORSE_ARMOR = ITEMS.register("amethyst_copper_horse_armor", () -> new SWEMHorseArmorItem(SWEMHorseArmorItem.HorseArmorTier.AMETHYST, 50, "amethyst_copper", new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<MeasurementTool> MEASUREMENT_TOOL = ITEMS.register("measurement_tool", () -> new MeasurementTool(new Item.Properties().tab(SWEM.TAB).stacksTo(64)));
    public static final RegistryObject<PoopItem> POOP = ITEMS.register("poop", PoopItem::new);
    public static final RegistryObject<TrackerItem> TRACKER = ITEMS.register("tracker", TrackerItem::new);
    public static final RegistryObject<MedicalItem> BANDAGE = ITEMS.register("bandage", () -> new MedicalItem(new Item.Properties().tab(SWEM.TAB), 2.0f, 15.0f));
    public static final RegistryObject<MedicalItem> SALVE = ITEMS.register("salve", () -> new MedicalItem(new Item.Properties().tab(SWEM.TAB), 4.0f, 30.0f));
    public static final RegistryObject<MedicalItem> MEDICATED_BANDAGE = ITEMS.register("medicated_bandage", () -> new MedicalItem(new Item.Properties().tab(SWEM.TAB), 6.0f, 45.0f));
    public static final RegistryObject<MedicalItem> GLISTENING_MELON = ITEMS.register("glistening_melon", () -> new MedicalItem(new Item.Properties().tab(SWEM.TAB), 10.0f, 75.0f));
    public static final RegistryObject<Item> WHITEWASH_FINISH = ITEMS.register("whitewash_finish", () -> new Item(new Item.Properties().tab(SWEM.TAB)));
    public static final RegistryObject<Item> SWEET_FEED_OPENED = SWEMItems.ITEMS.register("sweet_feed_open", () -> new SweetFeed(new Item.Properties().tab(SWEM.TAB).durability(8)));
    public static final RegistryObject<Item> SWEET_FEED = SWEMItems.ITEMS.register("sweet_feed", () -> new SweetFeed.UnopenedSweetFeed(SWEET_FEED_OPENED.get()));
    public static final RegistryObject<Item> REFINED_LEATHER = ITEMS.register("refined_leather", ItemBase::new);
    public static final RegistryObject<Item> PAINT_FILLER = ITEMS.register("paint_filler", ItemBase::new);
    public static final RegistryObject<Item> SHRIMP = ITEMS.register("shrimp", () -> new ShrimpItem(new Item.Properties()));
    public static final RegistryObject<Item> STAR_WORM = ITEMS.register("star_worm", ItemBase::new);
    public static final RegistryObject<Item> STAR_WORM_GOOP = ITEMS.register("star_worm_goop", ItemBase::new);
    public static final RegistryObject<Item> PESTLE_MORTAR = ITEMS.register("pestle_mortar", () -> new PestleMortarItem(new Item.Properties().stacksTo(1).tab(SWEM.TAB)));
    public static final RegistryObject<Item> BONE_MEAL_COMPOST = ITEMS.register("bone_meal_compost", () -> new BoneMealItem(new Item.Properties().tab(SWEM.TAB)));
    public static final RegistryObject<Item> BRUSH = ITEMS.register("brush", BrushItem::new);
    public static final RegistryObject<Item> MERCY_BLADE = ITEMS.register("mercy_blade", () -> new MercyBladeItem(new Item.Properties().tab(SWEM.TAB).durability(1)));
    public static final RegistryObject<Item> LEAD_ANCHOR = ITEMS.register("lead_anchor", LeadAnchorItem::new);
    public static final RegistryObject<Item> WARMER_EGG = ITEMS.register("warmer_egg", ItemBase::new);

    // Rainbow Horse recipe items.
    public static final RegistryObject<Item> COOLER_EGG = ITEMS.register("cooler_egg", ItemBase::new);
    public static final RegistryObject<Item> RAINBOW_EGG = ITEMS.register("rainbow_egg", ItemBase::new);
    public static final RegistryObject<PotionItem> RAINBOW_CHIC = ITEMS.register("rainbow_chic", PotionItemBase::new);
    public static final RegistryObject<Item> DEHYDRATED_RAINBOW = ITEMS.register("dehydrated_rainbow", ItemBase::new);
    public static final RegistryObject<Item> RAINBOW_SWEET_FEED = ITEMS.register("rainbow_sweet_feed", ItemBase::new);
    public static final RegistryObject<Item> RAINBOW_HEAVY_FEED = ITEMS.register("rainbow_heavy_feed", ItemBase::new);
    public static final RegistryObject<Item> RAINBOW_DRY_FEED = ITEMS.register("rainbow_dry_feed", RainbowDryFeedItem::new);
    public static final RegistryObject<Item> RAINBOW_DINDIN = ITEMS.register("rainbow_dindin", () -> new HorseTransformItem(SWEMCoatColor.RAINBOW));

    // Galaxy horse recipe items.
    public static final RegistryObject<Item> LIFE_OFFERING = ITEMS.register("life_offering", ItemBase::new);
    public static final RegistryObject<Item> EARTH_OFFERING = ITEMS.register("earth_offering", ItemBase::new);
    public static final RegistryObject<Item> VIBRANT_OFFERING = ITEMS.register("vibrant_offering", ItemBase::new);
    public static final RegistryObject<Item> OCEAN_OFFERING = ITEMS.register("ocean_offering", ItemBase::new);
    public static final RegistryObject<Item> NOVA_OFFERING = ITEMS.register("nova_offering", ItemBase::new);
    public static final RegistryObject<Item> GALLAXORIUM_OFFERING = ITEMS.register("gallaxorium_offering", Gallaxorium::new);
    public static final RegistryObject<Item> GALLAXIUM_OFFERING = ITEMS.register("gallaxium_offering", Gallaxium::new);
    public static final RegistryObject<Item> GALLAXIA_OFFERING = ITEMS.register("gallaxia_offering", () -> new HorseTransformItem(SWEMCoatColor.GALAXY));


    public static final List<RegistryObject<HalterItem>> HALTERS = new ArrayList<>();
    public static final List<RegistryObject<HorseSaddleItem>> WESTERN_SADDLES = new ArrayList<>();
    public static final List<RegistryObject<BlanketItem>> WESTERN_BLANKETS = new ArrayList<>();
    public static final List<RegistryObject<BridleItem>> WESTERN_BRIDLES = new ArrayList<>();
    public static final List<RegistryObject<GirthStrapItem>> WESTERN_GIRTH_STRAPS = new ArrayList<>();
    public static final List<RegistryObject<LegWrapsItem>> WESTERN_LEG_WRAPS = new ArrayList<>();
    public static final List<RegistryObject<BreastCollarItem>> WESTERN_BREAST_COLLARS = new ArrayList<>();
    public static final List<RegistryObject<BlanketItem>> ENGLISH_BLANKETS = new ArrayList<>();
    public static final List<RegistryObject<LegWrapsItem>> ENGLISH_LEG_WRAPS = new ArrayList<>();
    public static final List<RegistryObject<PastureBlanketItem>> PASTURE_BLANKETS = new ArrayList<>();
    public static final List<RegistryObject<PastureBlanketItem>> PASTURE_BLANKETS_ARMORED = new ArrayList<>();
    public static final List<RegistryObject<SaddlebagItem>> SADDLE_BAGS = new ArrayList<>();

    static {
        for (DyeColor color : DyeColor.values()) {
            HALTERS.add(ITEMS.register("halter_" + color.getName(), () -> new HalterItem("halter_" + color.getName(), new Item.Properties().tab(SWEM.TAB).stacksTo(16))));
            WESTERN_SADDLES.add(ITEMS.register("western_saddle_" + color.getName(), () -> new WesternSaddleItem("western_saddle_" + color.getName(), new Item.Properties().tab(SWEM.TAB).stacksTo(1))));
            WESTERN_BLANKETS.add(ITEMS.register("western_blanket_" + color.getName(), () -> new WesternBlanketItem("western_blanket_" + color.getName(), new Item.Properties().tab(SWEM.TAB).stacksTo(16))));
            WESTERN_BRIDLES.add(ITEMS.register("western_bridle_" + color.getName(), () -> new WesternBridleItem("western_bridle_" + color.getName(), new Item.Properties().tab(SWEM.TAB).stacksTo(16))));
            WESTERN_GIRTH_STRAPS.add(ITEMS.register("western_girth_strap_" + color.getName(), () -> new WesternGirthStrapItem("western_girth_strap_" + color.getName(), new Item.Properties().tab(SWEM.TAB).stacksTo(16))));
            WESTERN_LEG_WRAPS.add(ITEMS.register("western_leg_wraps_" + color.getName(), () -> new WesternLegWraps("western_leg_wraps_" + color.getName(), new Item.Properties().tab(SWEM.TAB).stacksTo(64))));
            WESTERN_BREAST_COLLARS.add(ITEMS.register("western_breast_collar_" + color.getName(), () -> new WesternBreastCollarItem("western_breast_collar_" + color.getName(), new Item.Properties().tab(SWEM.TAB).stacksTo(16))));
            ENGLISH_BLANKETS.add(ITEMS.register("english_blanket_" + color.getName(), () -> new EnglishBlanketItem("english_blanket_" + color.getName(), new Item.Properties().tab(SWEM.TAB).stacksTo(16))));
            ENGLISH_LEG_WRAPS.add(ITEMS.register("english_leg_wraps_" + color.getName(), () -> new EnglishLegWraps("english_leg_wraps_" + color.getName(), new Item.Properties().tab(SWEM.TAB).stacksTo(64))));
            PASTURE_BLANKETS.add(ITEMS.register("pasture_blanket_" + color.getName(), () -> new PastureBlanketItem(SWEMHorseArmorItem.HorseArmorTier.NONE, 0, "pasture_blanket_" + color.getName(), new Item.Properties().tab(SWEM.TAB).stacksTo(16))));
            PASTURE_BLANKETS_ARMORED.add(ITEMS.register("pasture_blanket_" + color.getName() + "_armored", () -> new PastureBlanketItem(SWEMHorseArmorItem.HorseArmorTier.DIAMOND, 37, "pasture_blanket_" + color.getName() + "_armored", new Item.Properties().tab(SWEM.TAB).stacksTo(16))));
            SADDLE_BAGS.add(ITEMS.register("saddle_bag_" + color.getName(), () -> new SaddlebagItem("saddle_bag_" + color.getName(), new Item.Properties().tab(SWEM.TAB).stacksTo(16))));
        }
    }

    public static final RegistryObject<EnglishSaddleItem> ENGLISH_SADDLE_BLACK = ITEMS.register("english_saddle_black", () -> new EnglishSaddleItem("english_saddle_black", new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<EnglishSaddleItem> ENGLISH_SADDLE_BROWN = ITEMS.register("english_saddle_brown", () -> new EnglishSaddleItem("english_saddle_brown", new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<EnglishBridleItem> ENGLISH_BRIDLE_BLACK = ITEMS.register("english_bridle_black", () -> new EnglishBridleItem("english_bridle_black", new Item.Properties().tab(SWEM.TAB).stacksTo(16)));
    public static final RegistryObject<EnglishBridleItem> ENGLISH_BRIDLE_BROWN = ITEMS.register("english_bridle_brown", () -> new EnglishBridleItem("english_bridle_brown", new Item.Properties().tab(SWEM.TAB).stacksTo(16)));
    public static final RegistryObject<EnglishBreastCollar> ENGLISH_BREAST_COLLAR_BLACK = ITEMS.register("english_breast_collar_black", () -> new EnglishBreastCollar("english_breast_collar_black", new Item.Properties().tab(SWEM.TAB).stacksTo(16)));
    public static final RegistryObject<EnglishBreastCollar> ENGLISH_BREAST_COLLAR_BROWN = ITEMS.register("english_breast_collar_brown", () -> new EnglishBreastCollar("english_breast_collar_brown", new Item.Properties().tab(SWEM.TAB).stacksTo(16)));
    public static final RegistryObject<EnglishGirthStrap> ENGLISH_GIRTH_STRAP_BLACK = ITEMS.register("english_girth_strap_black", () -> new EnglishGirthStrap("english_girth_strap_black", new Item.Properties().tab(SWEM.TAB).stacksTo(16)));
    public static final RegistryObject<EnglishGirthStrap> ENGLISH_GIRTH_STRAP_BROWN = ITEMS.register("english_girth_strap_brown", () -> new EnglishGirthStrap("english_girth_strap_brown", new Item.Properties().tab(SWEM.TAB).stacksTo(16)));

    public static final RegistryObject<AdventureSaddleItem> ADVENTURE_SADDLE = ITEMS.register("adventure_saddle", () -> new AdventureSaddleItem("adventure_saddle", new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<AdventureSaddleItem> ADVENTURE_SADDLE_GLOW = ITEMS.register("adventure_saddle_glow", () -> new AdventureSaddleItem("adventure_saddle_glow", new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<AdventureSaddleItem> ADVENTURE_SADDLE_USA = ITEMS.register("adventure_saddle_usa", () -> new AdventureSaddleItem("adventure_saddle_usa", new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<AdventureSaddleItem> ADVENTURE_SADDLE_NETHERITE = ITEMS.register("adventure_saddle_netherite", () -> new AdventureSaddleItem("adventure_saddle_netherite", new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<AdventureSaddleItem> ADVENTURE_SADDLE_EMERALD = ITEMS.register("adventure_saddle_emerald", () -> new AdventureSaddleItem("adventure_saddle_emerald", new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<AdventureSaddleItem> ADVENTURE_SADDLE_COPPER = ITEMS.register("adventure_saddle_copper", () -> new AdventureSaddleItem("adventure_saddle_copper", new Item.Properties().tab(SWEM.TAB).stacksTo(1)));
    public static final RegistryObject<AdventureBlanketItem> ADVENTURE_BLANKET = ITEMS.register("adventure_blanket", () -> new AdventureBlanketItem("adventure_blanket", new Item.Properties().tab(SWEM.TAB).stacksTo(16)));
    public static final RegistryObject<AdventureBridleItem> ADVENTURE_BRIDLE = ITEMS.register("adventure_bridle", () -> new AdventureBridleItem("adventure_bridle", "adventure", new Item.Properties().tab(SWEM.TAB).stacksTo(16)));
    public static final RegistryObject<AdventureLegWraps> ADVENTURE_LEG_WRAPS = ITEMS.register("adventure_leg_wraps", () -> new AdventureLegWraps("adventure_leg_wraps", new Item.Properties().tab(SWEM.TAB).stacksTo(64)));
    public static final RegistryObject<AdventureBreastCollarItem> ADVENTURE_BREAST_COLLAR = ITEMS.register("adventure_breast_collar", () -> new AdventureBreastCollarItem("adventure_breast_collar", new Item.Properties().tab(SWEM.TAB).stacksTo(16)));
    public static final RegistryObject<AdventureGirthStrapItem> ADVENTURE_GIRTH_STRAP = ITEMS.register("adventure_girth_strap", () -> new AdventureGirthStrapItem("adventure_girth_strap", new Item.Properties().tab(SWEM.TAB).stacksTo(16)));


    public static final RegistryObject<WesternBreastCollarItem> DRIVING_HARNESS = ITEMS.register("driving_harness", () -> new WesternBreastCollarItem("driving_harness", new Item.Properties().tab(SWEM.TAB).stacksTo(16)));

    // Tools
    public static final RegistryObject<SwordItem> AMETHYST_SWORD = ITEMS.register("amethyst_sword", () -> new AmethystSword(SWEMItemTier.AMETHYST, 4, 2F, new Item.Properties().tab(SWEM.TAB)));
    public static final RegistryObject<BowItem> AMETHYST_BOW = ITEMS.register("amethyst_bow", () -> new AmethystBow(new Item.Properties().tab(SWEM.TAB)));
    public static final RegistryObject<AmethystScythe> AMETHYST_SCYTHE = ITEMS.register("amethyst_scythe", () -> new AmethystScythe(SWEMItemTier.AMETHYST, 3, 2F, new Item.Properties().tab(SWEM.TAB)));
    public static final RegistryObject<ShieldItem> AMETHYST_SHIELD = ITEMS.register("amethyst_shield", () -> new AmethystShield(new Item.Properties().tab(SWEM.TAB)));



}
