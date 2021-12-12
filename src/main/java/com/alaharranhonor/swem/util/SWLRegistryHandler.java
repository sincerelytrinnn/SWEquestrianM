package com.alaharranhonor.swem.util;


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
import com.alaharranhonor.swem.items.PestleMortarItem;
import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class SWLRegistryHandler {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SWEM.MOD_ID);
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SWEM.MOD_ID);

	public static void init() {
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> SWLRegistryHandler::checkAccess);
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	public static final RegistryObject<Block> STAR_WORM_COBBLE = BLOCKS.register("star_worm_cobble", () -> new Block(AbstractBlock.Properties.copy(Blocks.STONE).lightLevel((state) -> 7)));

	// All star worm block variants
	public static final RegistryObject<Block> STAR_WORM_BLOCK_ACACIA_LOG = BLOCKS.register("star_worm_block_acacia_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_LOG).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BIRCH_LOG = BLOCKS.register("star_worm_block_birch_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_LOG).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_DARK_OAK_LOG = BLOCKS.register("star_worm_block_dark_oak_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.DARK_OAK_LOG).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_JUNGLE_LOG = BLOCKS.register("star_worm_block_jungle_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_LOG).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_OAK_LOG = BLOCKS.register("star_worm_block_oak_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_LOG).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_SPRUCE_LOG = BLOCKS.register("star_worm_block_spruce_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_LOG).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_ACACIA_STRIPPED_LOG = BLOCKS.register("star_worm_block_stripped_acacia_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_ACACIA_LOG).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BIRCH_STRIPPED_LOG = BLOCKS.register("star_worm_block_stripped_birch_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_BIRCH_LOG).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_DARK_OAK_STRIPPED_LOG = BLOCKS.register("star_worm_block_stripped_dark_oak_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_DARK_OAK_LOG).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_JUNGLE_STRIPPED_LOG = BLOCKS.register("star_worm_block_stripped_jungle_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_JUNGLE_LOG).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_OAK_STRIPPED_LOG = BLOCKS.register("star_worm_block_stripped_oak_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_LOG).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_SPRUCE_STRIPPED_LOG = BLOCKS.register("star_worm_block_stripped_spruce_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_SPRUCE_LOG).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_ACACIA_WOOD = BLOCKS.register("star_worm_block_acacia_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_WOOD).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BIRCH_WOOD = BLOCKS.register("star_worm_block_birch_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_WOOD).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_DARK_OAK_WOOD = BLOCKS.register("star_worm_block_dark_oak_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.DARK_OAK_WOOD).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_JUNGLE_WOOD = BLOCKS.register("star_worm_block_jungle_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_WOOD).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_OAK_WOOD = BLOCKS.register("star_worm_block_oak_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_WOOD).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_SPRUCE_WOOD = BLOCKS.register("star_worm_block_spruce_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_WOOD).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_ACACIA_STRIPPED_WOOD = BLOCKS.register("star_worm_block_stripped_acacia_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_ACACIA_WOOD).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BIRCH_STRIPPED_WOOD = BLOCKS.register("star_worm_block_stripped_birch_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_BIRCH_WOOD).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_DARK_OAK_STRIPPED_WOOD = BLOCKS.register("star_worm_block_stripped_dark_oak_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_DARK_OAK_WOOD).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_JUNGLE_STRIPPED_WOOD = BLOCKS.register("star_worm_block_stripped_jungle_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_JUNGLE_WOOD).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_OAK_STRIPPED_WOOD = BLOCKS.register("star_worm_block_stripped_oak_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_WOOD).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_SPRUCE_STRIPPED_WOOD = BLOCKS.register("star_worm_block_stripped_spruce_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_SPRUCE_WOOD).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_ACACIA_PLANKS = BLOCKS.register("star_worm_block_acacia_planks", () -> new Block(AbstractBlock.Properties.copy(Blocks.ACACIA_PLANKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BIRCH_PLANKS = BLOCKS.register("star_worm_block_birch_planks", () -> new Block(AbstractBlock.Properties.copy(Blocks.BIRCH_PLANKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_DARK_OAK_PLANKS = BLOCKS.register("star_worm_block_dark_oak_planks", () -> new Block(AbstractBlock.Properties.copy(Blocks.DARK_OAK_PLANKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_JUNGLE_PLANKS = BLOCKS.register("star_worm_block_jungle_planks", () -> new Block(AbstractBlock.Properties.copy(Blocks.JUNGLE_PLANKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_OAK_PLANKS = BLOCKS.register("star_worm_block_oak_planks", () -> new Block(AbstractBlock.Properties.copy(Blocks.OAK_PLANKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_SPRUCE_PLANKS = BLOCKS.register("star_worm_block_spruce_planks", () -> new Block(AbstractBlock.Properties.copy(Blocks.SPRUCE_PLANKS).lightLevel((state) -> 15)));

	public static final RegistryObject<Block> STAR_WORM_BLOCK_STONE = BLOCKS.register("star_worm_block_stone", () -> new Block(AbstractBlock.Properties.copy(Blocks.STONE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_SMOOTH_STONE = BLOCKS.register("star_worm_block_smooth_stone", () -> new Block(AbstractBlock.Properties.copy(Blocks.SMOOTH_STONE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_COBBLESTONE = BLOCKS.register("star_worm_block_cobblestone", () -> new Block(AbstractBlock.Properties.copy(Blocks.COBBLESTONE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_MOSSY_COBBLESTONE = BLOCKS.register("star_worm_block_mossy_cobblestone", () -> new Block(AbstractBlock.Properties.copy(Blocks.MOSSY_COBBLESTONE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_ANDESITE = BLOCKS.register("star_worm_block_andesite", () -> new Block(AbstractBlock.Properties.copy(Blocks.ANDESITE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_POLISHED_ANDESITE = BLOCKS.register("star_worm_block_polished_andesite", () -> new Block(AbstractBlock.Properties.copy(Blocks.POLISHED_ANDESITE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_DIORITE = BLOCKS.register("star_worm_block_diorite", () -> new Block(AbstractBlock.Properties.copy(Blocks.DIORITE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_POLISHED_DIORITE = BLOCKS.register("star_worm_block_polished_diorite", () -> new Block(AbstractBlock.Properties.copy(Blocks.POLISHED_DIORITE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_GRANITE = BLOCKS.register("star_worm_block_granite", () -> new Block(AbstractBlock.Properties.copy(Blocks.GRANITE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_POLISHED_GRANITE = BLOCKS.register("star_worm_block_polished_granite", () -> new Block(AbstractBlock.Properties.copy(Blocks.POLISHED_GRANITE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_STONE_BRICKS = BLOCKS.register("star_worm_block_stone_bricks", () -> new Block(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CRACKED_STONE_BRICKS = BLOCKS.register("star_worm_block_cracked_stone_bricks", () -> new Block(AbstractBlock.Properties.copy(Blocks.CRACKED_STONE_BRICKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_MOSSY_STONE_BRICKS = BLOCKS.register("star_worm_block_mossy_stone_bricks", () -> new Block(AbstractBlock.Properties.copy(Blocks.MOSSY_STONE_BRICKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CHISELED_STONE_BRICKS = BLOCKS.register("star_worm_block_chiseled_stone_bricks", () -> new Block(AbstractBlock.Properties.copy(Blocks.CHISELED_STONE_BRICKS).lightLevel((state) -> 15)));

	public static final RegistryObject<Block> STAR_WORM_BLOCK_COAL = BLOCKS.register("star_worm_block_coal", () -> new Block(AbstractBlock.Properties.copy(Blocks.COAL_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_IRON = BLOCKS.register("star_worm_block_iron", () -> new Block(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_GOLD = BLOCKS.register("star_worm_block_gold", () -> new Block(AbstractBlock.Properties.copy(Blocks.GOLD_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_DIAMOND = BLOCKS.register("star_worm_block_diamond", () -> new Block(AbstractBlock.Properties.copy(Blocks.DIAMOND_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_EMERALD = BLOCKS.register("star_worm_block_emerald", () -> new Block(AbstractBlock.Properties.copy(Blocks.EMERALD_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_REDSTONE = BLOCKS.register("star_worm_block_redstone", () -> new Block(AbstractBlock.Properties.copy(Blocks.REDSTONE_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_LAPIS = BLOCKS.register("star_worm_block_lapis", () -> new Block(AbstractBlock.Properties.copy(Blocks.LAPIS_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_NETHERITE = BLOCKS.register("star_worm_block_netherite", () -> new Block(AbstractBlock.Properties.copy(Blocks.NETHERITE_BLOCK).lightLevel((state) -> 15)));

	public static final RegistryObject<Block> STAR_WORM_BLOCK_BONE = BLOCKS.register("star_worm_block_bone", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.BONE_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_GRAVEL = BLOCKS.register("star_worm_block_gravel", () -> new GravelBlock(AbstractBlock.Properties.copy(Blocks.GRAVEL).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CLAY = BLOCKS.register("star_worm_block_clay", () -> new Block(AbstractBlock.Properties.copy(Blocks.CLAY).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BRICKS = BLOCKS.register("star_worm_block_bricks", () -> new Block(AbstractBlock.Properties.copy(Blocks.BRICKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_SAND = BLOCKS.register("star_worm_block_sand", () -> new SandBlock(14406560, AbstractBlock.Properties.copy(Blocks.SAND).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_SANDSTONE = BLOCKS.register("star_worm_block_sandstone", () -> new Block(AbstractBlock.Properties.copy(Blocks.SANDSTONE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CUT_SANDSTONE = BLOCKS.register("star_worm_block_cut_sandstone", () -> new Block(AbstractBlock.Properties.copy(Blocks.CUT_SANDSTONE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_SMOOTH_SANDSTONE = BLOCKS.register("star_worm_block_smooth_sandstone", () -> new Block(AbstractBlock.Properties.copy(Blocks.SMOOTH_SANDSTONE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CHISELED_SANDSTONE = BLOCKS.register("star_worm_block_chiseled_sandstone", () -> new Block(AbstractBlock.Properties.copy(Blocks.CHISELED_SANDSTONE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_RED_SAND = BLOCKS.register("star_worm_block_red_sand", () -> new SandBlock(11098145, AbstractBlock.Properties.copy(Blocks.RED_SAND).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_RED_SANDSTONE = BLOCKS.register("star_worm_block_red_sandstone", () -> new Block(AbstractBlock.Properties.copy(Blocks.RED_SANDSTONE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CUT_RED_SANDSTONE = BLOCKS.register("star_worm_block_cut_red_sandstone", () -> new Block(AbstractBlock.Properties.copy(Blocks.CUT_RED_SANDSTONE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_SMOOTH_RED_SANDSTONE = BLOCKS.register("star_worm_block_smooth_red_sandstone", () -> new Block(AbstractBlock.Properties.copy(Blocks.SMOOTH_RED_SANDSTONE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CHISELED_RED_SANDSTONE = BLOCKS.register("star_worm_block_chiseled_red_sandstone", () -> new Block(AbstractBlock.Properties.copy(Blocks.CHISELED_RED_SANDSTONE).lightLevel((state) -> 15)));

	public static final RegistryObject<Block> STAR_WORM_BLOCK_PRISMARINE = BLOCKS.register("star_worm_block_prismarine", () -> new Block(AbstractBlock.Properties.copy(Blocks.PRISMARINE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_PRISMARINE_BRICKS = BLOCKS.register("star_worm_block_prismarine_bricks", () -> new Block(AbstractBlock.Properties.copy(Blocks.PRISMARINE_BRICKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_DARK_PRISMARINE = BLOCKS.register("star_worm_block_dark_prismarine", () -> new Block(AbstractBlock.Properties.copy(Blocks.PRISMARINE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_DRIED_KELP = BLOCKS.register("star_worm_block_dried_kelp", () -> new Block(AbstractBlock.Properties.copy(Blocks.DRIED_KELP_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_DEAD_TUBE_CORAL = BLOCKS.register("star_worm_block_dead_tube_coral", () -> new Block(AbstractBlock.Properties.copy(Blocks.DEAD_TUBE_CORAL_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_DEAD_BRAIN_CORAL = BLOCKS.register("star_worm_block_dead_brain_coral", () -> new Block(AbstractBlock.Properties.copy(Blocks.DEAD_BRAIN_CORAL_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_DEAD_BUBBLE_CORAL = BLOCKS.register("star_worm_block_dead_bubble_coral", () -> new Block(AbstractBlock.Properties.copy(Blocks.DEAD_BUBBLE_CORAL_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_DEAD_FIRE_CORAL = BLOCKS.register("star_worm_block_dead_fire_coral", () -> new Block(AbstractBlock.Properties.copy(Blocks.DEAD_FIRE_CORAL_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_DEAD_HORN_CORAL = BLOCKS.register("star_worm_block_dead_horn_coral", () -> new Block(AbstractBlock.Properties.copy(Blocks.DEAD_HORN_CORAL_BLOCK).lightLevel((state) -> 15)));

	public static final RegistryObject<Block> STAR_WORM_BLOCK_OBSIDIAN = BLOCKS.register("star_worm_block_obsidian", () -> new Block(AbstractBlock.Properties.copy(Blocks.OBSIDIAN).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_NETHERRACK = BLOCKS.register("star_worm_block_netherrack", () -> new Block(AbstractBlock.Properties.copy(Blocks.NETHERRACK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CHISELED_NETHER_BRICKS = BLOCKS.register("star_worm_block_chiseled_nether_bricks", () -> new Block(AbstractBlock.Properties.copy(Blocks.CHISELED_NETHER_BRICKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CRACKED_NETHER_BRICKS = BLOCKS.register("star_worm_block_cracked_nether_bricks", () -> new Block(AbstractBlock.Properties.copy(Blocks.CRACKED_NETHER_BRICKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_NETHER_BRICKS = BLOCKS.register("star_worm_block_nether_bricks", () -> new Block(AbstractBlock.Properties.copy(Blocks.NETHER_BRICKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_RED_NETHER_BRICKS = BLOCKS.register("star_worm_block_red_nether_bricks", () -> new Block(AbstractBlock.Properties.copy(Blocks.RED_NETHER_BRICKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BLACKSTONE = BLOCKS.register("star_worm_block_blackstone", () -> new Block(AbstractBlock.Properties.copy(Blocks.BLACKSTONE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_POLISHED_BLACKSTONE = BLOCKS.register("star_worm_block_polished_blackstone", () -> new Block(AbstractBlock.Properties.copy(Blocks.POLISHED_BLACKSTONE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CRACKED_POLISHED_BLACKSTONE_BRICKS = BLOCKS.register("star_worm_block_cracked_polished_blackstone_bricks", () -> new Block(AbstractBlock.Properties.copy(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CHISELED_POLISHED_BLACKSTONE = BLOCKS.register("star_worm_block_chiseled_polished_blackstone", () -> new Block(AbstractBlock.Properties.copy(Blocks.CHISELED_POLISHED_BLACKSTONE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_GILDED_BLACKSTONE = BLOCKS.register("star_worm_block_gilded_blackstone", () -> new Block(AbstractBlock.Properties.copy(Blocks.GILDED_BLACKSTONE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_NETHER_WART = BLOCKS.register("star_worm_block_nether_wart", () -> new Block(AbstractBlock.Properties.copy(Blocks.NETHER_WART_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CRIMSON_STEM = BLOCKS.register("star_worm_block_crimson_stem", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.CRIMSON_STEM).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_STRIPPED_CRIMSON_STEM = BLOCKS.register("star_worm_block_stripped_crimson_stem", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_CRIMSON_STEM).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CRIMSON_HYPHAE = BLOCKS.register("star_worm_block_crimson_hyphae", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.CRIMSON_HYPHAE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_STRIPPED_CRIMSON_HYPHAE = BLOCKS.register("star_worm_block_stripped_crimson_hyphae", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_CRIMSON_HYPHAE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CRIMSON_PLANKS = BLOCKS.register("star_worm_block_crimson_planks", () -> new Block(AbstractBlock.Properties.copy(Blocks.CRIMSON_PLANKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_WARPED_WART = BLOCKS.register("star_worm_block_warped_wart", () -> new Block(AbstractBlock.Properties.copy(Blocks.WARPED_WART_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_WARPED_STEM = BLOCKS.register("star_worm_block_warped_stem", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.WARPED_STEM).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_STRIPPED_WARPED_STEM = BLOCKS.register("star_worm_block_stripped_warped_stem", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_WARPED_STEM).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_WARPED_HYPHAE = BLOCKS.register("star_worm_block_warped_hyphae", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.WARPED_HYPHAE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_STRIPPED_WARPED_HYPHAE = BLOCKS.register("star_worm_block_stripped_warped_hyphae", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_WARPED_HYPHAE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_WARPED_PLANKS = BLOCKS.register("star_worm_block_warped_planks", () -> new Block(AbstractBlock.Properties.copy(Blocks.WARPED_PLANKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_SOUL_SAND = BLOCKS.register("star_worm_block_soul_sand", () -> new Block(AbstractBlock.Properties.copy(Blocks.SOUL_SAND).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_SOUL_SOIL = BLOCKS.register("star_worm_block_soul_soil", () -> new Block(AbstractBlock.Properties.copy(Blocks.SOUL_SOIL).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_QUARTZ = BLOCKS.register("star_worm_block_quartz", () -> new Block(AbstractBlock.Properties.copy(Blocks.QUARTZ_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_QUARTZ_PILLAR = BLOCKS.register("star_worm_block_quartz_pillar", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.QUARTZ_PILLAR).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CHISELED_QUARTZ = BLOCKS.register("star_worm_block_chiseled_quartz", () -> new Block(AbstractBlock.Properties.copy(Blocks.CHISELED_QUARTZ_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_SMOOTH_QUARTZ = BLOCKS.register("star_worm_block_smooth_quartz", () -> new Block(AbstractBlock.Properties.copy(Blocks.SMOOTH_QUARTZ).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_QUARTZ_BRICKS = BLOCKS.register("star_worm_block_quartz_bricks", () -> new Block(AbstractBlock.Properties.copy(Blocks.QUARTZ_BRICKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BASALT = BLOCKS.register("star_worm_block_basalt", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.BASALT).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_POLISHED_BASALT = BLOCKS.register("star_worm_block_polished_basalt", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.POLISHED_BASALT).lightLevel((state) -> 15)));

	public static final RegistryObject<Block> STAR_WORM_BLOCK_END_STONE = BLOCKS.register("star_worm_block_end_stone", () -> new Block(AbstractBlock.Properties.copy(Blocks.END_STONE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_END_STONE_BRICKS = BLOCKS.register("star_worm_block_end_stone_bricks", () -> new Block(AbstractBlock.Properties.copy(Blocks.END_STONE_BRICKS).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_PURPUR = BLOCKS.register("star_worm_block_purpur", () -> new Block(AbstractBlock.Properties.copy(Blocks.PURPUR_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_PURPUR_PILLAR = BLOCKS.register("star_worm_block_purpur_pillar", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.PURPUR_PILLAR).lightLevel((state) -> 15)));

	public static final RegistryObject<Block> STAR_WORM_BLOCK_BROWN_MUSHROOM = BLOCKS.register("star_worm_block_brown_mushroom", () -> new HugeMushroomBlock(AbstractBlock.Properties.copy(Blocks.BROWN_MUSHROOM_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_RED_MUSHROOM = BLOCKS.register("star_worm_block_red_mushroom", () -> new HugeMushroomBlock(AbstractBlock.Properties.copy(Blocks.RED_MUSHROOM_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_SNOW = BLOCKS.register("star_worm_block_snow", () -> new Block(AbstractBlock.Properties.copy(Blocks.SNOW_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_ICE = BLOCKS.register("star_worm_block_ice", () -> new Block(AbstractBlock.Properties.copy(Blocks.ICE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_PACKED_ICE = BLOCKS.register("star_worm_block_packed_ice", () -> new Block(AbstractBlock.Properties.copy(Blocks.PACKED_ICE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BLUE_ICE = BLOCKS.register("star_worm_block_blue_ice", () -> new Block(AbstractBlock.Properties.copy(Blocks.BLUE_ICE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_SLIME = BLOCKS.register("star_worm_block_slime", () -> new Block(AbstractBlock.Properties.copy(Blocks.SLIME_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_HONEY = BLOCKS.register("star_worm_block_honey", () -> new Block(AbstractBlock.Properties.copy(Blocks.HONEY_BLOCK).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_HONEYCOMB = BLOCKS.register("star_worm_block_honeycomb", () -> new Block(AbstractBlock.Properties.copy(Blocks.HONEYCOMB_BLOCK).lightLevel((state) -> 15)));

	public static final RegistryObject<Block> STAR_WORM_BLOCK_TERRACOTTA = BLOCKS.register("star_worm_block_terracotta", () -> new Block(AbstractBlock.Properties.copy(Blocks.TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BLACK_TERRACOTTA = BLOCKS.register("star_worm_block_black_terracotta", () -> new Block(AbstractBlock.Properties.copy(Blocks.BLACK_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BLUE_TERRACOTTA = BLOCKS.register("star_worm_block_blue_terracotta", () -> new Block(AbstractBlock.Properties.copy(Blocks.BLUE_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BROWN_TERRACOTTA = BLOCKS.register("star_worm_block_brown_terracotta", () -> new Block(AbstractBlock.Properties.copy(Blocks.BROWN_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CYAN_TERRACOTTA = BLOCKS.register("star_worm_block_cyan_terracotta", () -> new Block(AbstractBlock.Properties.copy(Blocks.CYAN_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_GRAY_TERRACOTTA = BLOCKS.register("star_worm_block_gray_terracotta", () -> new Block(AbstractBlock.Properties.copy(Blocks.GRAY_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_GREEN_TERRACOTTA = BLOCKS.register("star_worm_block_green_terracotta", () -> new Block(AbstractBlock.Properties.copy(Blocks.GREEN_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_LIGHT_BLUE_TERRACOTTA = BLOCKS.register("star_worm_block_light_blue_terracotta", () -> new Block(AbstractBlock.Properties.copy(Blocks.LIGHT_BLUE_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_LIGHT_GRAY_TERRACOTTA = BLOCKS.register("star_worm_block_light_gray_terracotta", () -> new Block(AbstractBlock.Properties.copy(Blocks.LIGHT_GRAY_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_LIME_TERRACOTTA = BLOCKS.register("star_worm_block_lime_terracotta", () -> new Block(AbstractBlock.Properties.copy(Blocks.LIME_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_MAGENTA_TERRACOTTA = BLOCKS.register("star_worm_block_magenta_terracotta", () -> new Block(AbstractBlock.Properties.copy(Blocks.MAGENTA_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_ORANGE_TERRACOTTA = BLOCKS.register("star_worm_block_orange_terracotta", () -> new Block(AbstractBlock.Properties.copy(Blocks.ORANGE_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_PINK_TERRACOTTA = BLOCKS.register("star_worm_block_pink_terracotta", () -> new Block(AbstractBlock.Properties.copy(Blocks.PINK_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_PURPLE_TERRACOTTA = BLOCKS.register("star_worm_block_purple_terracotta", () -> new Block(AbstractBlock.Properties.copy(Blocks.PURPLE_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_RED_TERRACOTTA = BLOCKS.register("star_worm_block_red_terracotta", () -> new Block(AbstractBlock.Properties.copy(Blocks.RED_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_WHITE_TERRACOTTA = BLOCKS.register("star_worm_block_white_terracotta", () -> new Block(AbstractBlock.Properties.copy(Blocks.WHITE_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_YELLOW_TERRACOTTA = BLOCKS.register("star_worm_block_yellow_terracotta", () -> new Block(AbstractBlock.Properties.copy(Blocks.YELLOW_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BLACK_GLAZED_TERRACOTTA = BLOCKS.register("star_worm_block_black_glazed_terracotta", () -> new GlazedTerracottaBlock(AbstractBlock.Properties.copy(Blocks.BLACK_GLAZED_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BLUE_GLAZED_TERRACOTTA = BLOCKS.register("star_worm_block_blue_glazed_terracotta", () -> new GlazedTerracottaBlock(AbstractBlock.Properties.copy(Blocks.BLUE_GLAZED_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BROWN_GLAZED_TERRACOTTA = BLOCKS.register("star_worm_block_brown_glazed_terracotta", () -> new GlazedTerracottaBlock(AbstractBlock.Properties.copy(Blocks.BROWN_GLAZED_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CYAN_GLAZED_TERRACOTTA = BLOCKS.register("star_worm_block_cyan_glazed_terracotta", () -> new GlazedTerracottaBlock(AbstractBlock.Properties.copy(Blocks.CYAN_GLAZED_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_GRAY_GLAZED_TERRACOTTA = BLOCKS.register("star_worm_block_gray_glazed_terracotta", () -> new GlazedTerracottaBlock(AbstractBlock.Properties.copy(Blocks.GRAY_GLAZED_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_GREEN_GLAZED_TERRACOTTA = BLOCKS.register("star_worm_block_green_glazed_terracotta", () -> new GlazedTerracottaBlock(AbstractBlock.Properties.copy(Blocks.GREEN_GLAZED_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_LIGHT_BLUE_GLAZED_TERRACOTTA = BLOCKS.register("star_worm_block_light_blue_glazed_terracotta", () -> new GlazedTerracottaBlock(AbstractBlock.Properties.copy(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_LIGHT_GRAY_GLAZED_TERRACOTTA = BLOCKS.register("star_worm_block_light_gray_glazed_terracotta", () -> new GlazedTerracottaBlock(AbstractBlock.Properties.copy(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_LIME_GLAZED_TERRACOTTA = BLOCKS.register("star_worm_block_lime_glazed_terracotta", () -> new GlazedTerracottaBlock(AbstractBlock.Properties.copy(Blocks.LIME_GLAZED_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_MAGENTA_GLAZED_TERRACOTTA = BLOCKS.register("star_worm_block_magenta_glazed_terracotta", () -> new GlazedTerracottaBlock(AbstractBlock.Properties.copy(Blocks.MAGENTA_GLAZED_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_ORANGE_GLAZED_TERRACOTTA = BLOCKS.register("star_worm_block_orange_glazed_terracotta", () -> new GlazedTerracottaBlock(AbstractBlock.Properties.copy(Blocks.ORANGE_GLAZED_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_PINK_GLAZED_TERRACOTTA = BLOCKS.register("star_worm_block_pink_glazed_terracotta", () -> new GlazedTerracottaBlock(AbstractBlock.Properties.copy(Blocks.PINK_GLAZED_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_PURPLE_GLAZED_TERRACOTTA = BLOCKS.register("star_worm_block_purple_glazed_terracotta", () -> new GlazedTerracottaBlock(AbstractBlock.Properties.copy(Blocks.PURPLE_GLAZED_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_RED_GLAZED_TERRACOTTA = BLOCKS.register("star_worm_block_red_glazed_terracotta", () -> new GlazedTerracottaBlock(AbstractBlock.Properties.copy(Blocks.RED_GLAZED_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_WHITE_GLAZED_TERRACOTTA = BLOCKS.register("star_worm_block_white_glazed_terracotta", () -> new GlazedTerracottaBlock(AbstractBlock.Properties.copy(Blocks.WHITE_GLAZED_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_YELLOW_GLAZED_TERRACOTTA = BLOCKS.register("star_worm_block_yellow_glazed_terracotta", () -> new GlazedTerracottaBlock(AbstractBlock.Properties.copy(Blocks.YELLOW_GLAZED_TERRACOTTA).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BLACK_CONCRETE_POWDER = BLOCKS.register("star_worm_block_black_concrete_powder", () -> new FallingBlock(AbstractBlock.Properties.copy(Blocks.BLACK_CONCRETE_POWDER).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BLUE_CONCRETE_POWDER = BLOCKS.register("star_worm_block_blue_concrete_powder", () -> new FallingBlock(AbstractBlock.Properties.copy(Blocks.BLUE_CONCRETE_POWDER).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BROWN_CONCRETE_POWDER = BLOCKS.register("star_worm_block_brown_concrete_powder", () -> new FallingBlock(AbstractBlock.Properties.copy(Blocks.BROWN_CONCRETE_POWDER).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CYAN_CONCRETE_POWDER = BLOCKS.register("star_worm_block_cyan_concrete_powder", () -> new FallingBlock(AbstractBlock.Properties.copy(Blocks.CYAN_CONCRETE_POWDER).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_GRAY_CONCRETE_POWDER = BLOCKS.register("star_worm_block_gray_concrete_powder", () -> new FallingBlock(AbstractBlock.Properties.copy(Blocks.GRAY_CONCRETE_POWDER).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_GREEN_CONCRETE_POWDER = BLOCKS.register("star_worm_block_green_concrete_powder", () -> new FallingBlock(AbstractBlock.Properties.copy(Blocks.GREEN_CONCRETE_POWDER).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_LIGHT_BLUE_CONCRETE_POWDER = BLOCKS.register("star_worm_block_light_blue_concrete_powder", () -> new FallingBlock(AbstractBlock.Properties.copy(Blocks.LIGHT_BLUE_CONCRETE_POWDER).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_LIGHT_GRAY_CONCRETE_POWDER = BLOCKS.register("star_worm_block_light_gray_concrete_powder", () -> new FallingBlock(AbstractBlock.Properties.copy(Blocks.LIGHT_GRAY_CONCRETE_POWDER).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_LIME_CONCRETE_POWDER = BLOCKS.register("star_worm_block_lime_concrete_powder", () -> new FallingBlock(AbstractBlock.Properties.copy(Blocks.LIME_CONCRETE_POWDER).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_MAGENTA_CONCRETE_POWDER = BLOCKS.register("star_worm_block_magenta_concrete_powder", () -> new FallingBlock(AbstractBlock.Properties.copy(Blocks.MAGENTA_CONCRETE_POWDER).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_ORANGE_CONCRETE_POWDER = BLOCKS.register("star_worm_block_orange_concrete_powder", () -> new FallingBlock(AbstractBlock.Properties.copy(Blocks.ORANGE_CONCRETE_POWDER).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_PINK_CONCRETE_POWDER = BLOCKS.register("star_worm_block_pink_concrete_powder", () -> new FallingBlock(AbstractBlock.Properties.copy(Blocks.PINK_CONCRETE_POWDER).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_PURPLE_CONCRETE_POWDER = BLOCKS.register("star_worm_block_purple_concrete_powder", () -> new FallingBlock(AbstractBlock.Properties.copy(Blocks.PURPLE_CONCRETE_POWDER).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_RED_CONCRETE_POWDER = BLOCKS.register("star_worm_block_red_concrete_powder", () -> new FallingBlock(AbstractBlock.Properties.copy(Blocks.RED_CONCRETE_POWDER).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_WHITE_CONCRETE_POWDER = BLOCKS.register("star_worm_block_white_concrete_powder", () -> new FallingBlock(AbstractBlock.Properties.copy(Blocks.WHITE_CONCRETE_POWDER).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_YELLOW_CONCRETE_POWDER = BLOCKS.register("star_worm_block_yellow_concrete_powder", () -> new FallingBlock(AbstractBlock.Properties.copy(Blocks.YELLOW_CONCRETE_POWDER).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BLACK_CONCRETE = BLOCKS.register("star_worm_block_black_concrete", () -> new Block(AbstractBlock.Properties.copy(Blocks.BLACK_CONCRETE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BLUE_CONCRETE = BLOCKS.register("star_worm_block_blue_concrete", () -> new Block(AbstractBlock.Properties.copy(Blocks.BLUE_CONCRETE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BROWN_CONCRETE = BLOCKS.register("star_worm_block_brown_concrete", () -> new Block(AbstractBlock.Properties.copy(Blocks.BROWN_CONCRETE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CYAN_CONCRETE = BLOCKS.register("star_worm_block_cyan_concrete", () -> new Block(AbstractBlock.Properties.copy(Blocks.CYAN_CONCRETE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_GRAY_CONCRETE = BLOCKS.register("star_worm_block_gray_concrete", () -> new Block(AbstractBlock.Properties.copy(Blocks.GRAY_CONCRETE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_GREEN_CONCRETE = BLOCKS.register("star_worm_block_green_concrete", () -> new Block(AbstractBlock.Properties.copy(Blocks.GREEN_CONCRETE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_LIME_CONCRETE = BLOCKS.register("star_worm_block_lime_concrete", () -> new Block(AbstractBlock.Properties.copy(Blocks.LIME_CONCRETE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_LIGHT_BLUE_CONCRETE = BLOCKS.register("star_worm_block_light_blue_concrete", () -> new Block(AbstractBlock.Properties.copy(Blocks.LIGHT_BLUE_CONCRETE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_LIGHT_GRAY_CONCRETE = BLOCKS.register("star_worm_block_light_gray_concrete", () -> new Block(AbstractBlock.Properties.copy(Blocks.LIGHT_GRAY_CONCRETE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_MAGENTA_CONCRETE = BLOCKS.register("star_worm_block_magenta_concrete", () -> new Block(AbstractBlock.Properties.copy(Blocks.MAGENTA_CONCRETE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_ORANGE_CONCRETE = BLOCKS.register("star_worm_block_orange_concrete", () -> new Block(AbstractBlock.Properties.copy(Blocks.ORANGE_CONCRETE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_PINK_CONCRETE = BLOCKS.register("star_worm_block_pink_concrete", () -> new Block(AbstractBlock.Properties.copy(Blocks.PINK_CONCRETE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_PURPLE_CONCRETE= BLOCKS.register("star_worm_block_purple_concrete", () -> new Block(AbstractBlock.Properties.copy(Blocks.PURPLE_CONCRETE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_RED_CONCRETE = BLOCKS.register("star_worm_block_red_concrete", () -> new Block(AbstractBlock.Properties.copy(Blocks.RED_CONCRETE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_WHITE_CONCRETE = BLOCKS.register("star_worm_block_white_concrete", () -> new Block(AbstractBlock.Properties.copy(Blocks.WHITE_CONCRETE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_YELLOW_CONCRETE = BLOCKS.register("star_worm_block_yellow_concrete", () -> new Block(AbstractBlock.Properties.copy(Blocks.YELLOW_CONCRETE).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BLACK_WOOL = BLOCKS.register("star_worm_block_black_wool", () -> new Block(AbstractBlock.Properties.copy(Blocks.BLACK_WOOL).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BLUE_WOOL = BLOCKS.register("star_worm_block_blue_wool", () -> new Block(AbstractBlock.Properties.copy(Blocks.BLUE_WOOL).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_BROWN_WOOL = BLOCKS.register("star_worm_block_brown_wool", () -> new Block(AbstractBlock.Properties.copy(Blocks.BROWN_WOOL).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_CYAN_WOOL = BLOCKS.register("star_worm_block_cyan_wool", () -> new Block(AbstractBlock.Properties.copy(Blocks.CYAN_WOOL).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_GRAY_WOOL = BLOCKS.register("star_worm_block_gray_wool", () -> new Block(AbstractBlock.Properties.copy(Blocks.GRAY_WOOL).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_GREEN_WOOL = BLOCKS.register("star_worm_block_green_wool", () -> new Block(AbstractBlock.Properties.copy(Blocks.GREEN_WOOL).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_LIGHT_BLUE_WOOL = BLOCKS.register("star_worm_block_light_blue_wool", () -> new Block(AbstractBlock.Properties.copy(Blocks.LIGHT_BLUE_WOOL).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_LIGHT_GRAY_WOOL = BLOCKS.register("star_worm_block_light_gray_wool", () -> new Block(AbstractBlock.Properties.copy(Blocks.LIGHT_GRAY_WOOL).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_LIME_WOOL = BLOCKS.register("star_worm_block_lime_wool", () -> new Block(AbstractBlock.Properties.copy(Blocks.LIME_WOOL).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_MAGENTA_WOOL = BLOCKS.register("star_worm_block_magenta_wool", () -> new Block(AbstractBlock.Properties.copy(Blocks.MAGENTA_WOOL).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_ORANGE_WOOL = BLOCKS.register("star_worm_block_orange_wool", () -> new Block(AbstractBlock.Properties.copy(Blocks.ORANGE_WOOL).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_PINK_WOOL = BLOCKS.register("star_worm_block_pink_wool", () -> new Block(AbstractBlock.Properties.copy(Blocks.PINK_WOOL).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_PURPLE_WOOL = BLOCKS.register("star_worm_block_purple_wool", () -> new Block(AbstractBlock.Properties.copy(Blocks.PURPLE_WOOL).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_RED_WOOL = BLOCKS.register("star_worm_block_red_wool", () -> new Block(AbstractBlock.Properties.copy(Blocks.RED_WOOL).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_WHITE_WOOL = BLOCKS.register("star_worm_block_white_wool", () -> new Block(AbstractBlock.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15)));
	public static final RegistryObject<Block> STAR_WORM_BLOCK_YELLOW_WOOL = BLOCKS.register("star_worm_block_yellow_wool", () -> new Block(AbstractBlock.Properties.copy(Blocks.YELLOW_WOOL).lightLevel((state) -> 15)));

	public static final RegistryObject<Item> STAR_WORM = ITEMS.register("star_worm", () -> new Item(new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_GOOP = ITEMS.register("star_worm_goop", () -> new Item(new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> PESTLE_MORTAR = ITEMS.register("pestle_mortar", () -> new PestleMortarItem(new Item.Properties().stacksTo(1).tab(SWEM.SWLMTAB)));

	public static final RegistryObject<Item> STAR_WORM_COBBLE_ITEM = ITEMS.register("star_worm_cobble", () -> new BlockItem(STAR_WORM_COBBLE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));

	public static final RegistryObject<Item> STAR_WORM_BLOCK_ACACIA_LOG_ITEM = ITEMS.register("star_worm_block_acacia_log", () -> new BlockItem(STAR_WORM_BLOCK_ACACIA_LOG.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BIRCH_LOG_ITEM = ITEMS.register("star_worm_block_birch_log", () -> new BlockItem(STAR_WORM_BLOCK_BIRCH_LOG.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_DARK_OAK_LOG_ITEM = ITEMS.register("star_worm_block_dark_oak_log", () -> new BlockItem(STAR_WORM_BLOCK_DARK_OAK_LOG.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_JUNGLE_LOG_ITEM = ITEMS.register("star_worm_block_jungle_log", () -> new BlockItem(STAR_WORM_BLOCK_JUNGLE_LOG.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_OAK_LOG_ITEM = ITEMS.register("star_worm_block_oak_log", () -> new BlockItem(STAR_WORM_BLOCK_OAK_LOG.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_SPRUCE_LOG_ITEM = ITEMS.register("star_worm_block_spruce_log", () -> new BlockItem(STAR_WORM_BLOCK_SPRUCE_LOG.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_ACACIA_STRIPPED_LOG_ITEM = ITEMS.register("star_worm_block_stripped_acacia_log", () -> new BlockItem(STAR_WORM_BLOCK_ACACIA_STRIPPED_LOG.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BIRCH_STRIPPED_LOG_ITEM = ITEMS.register("star_worm_block_stripped_birch_log", () -> new BlockItem(STAR_WORM_BLOCK_BIRCH_STRIPPED_LOG.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_DARK_OAK_STRIPPED_LOG_ITEM = ITEMS.register("star_worm_block_stripped_dark_oak_log", () -> new BlockItem(STAR_WORM_BLOCK_DARK_OAK_STRIPPED_LOG.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_JUNGLE_STRIPPED_LOG_ITEM = ITEMS.register("star_worm_block_stripped_jungle_log", () -> new BlockItem(STAR_WORM_BLOCK_JUNGLE_STRIPPED_LOG.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_OAK_STRIPPED_LOG_ITEM = ITEMS.register("star_worm_block_stripped_oak_log", () -> new BlockItem(STAR_WORM_BLOCK_OAK_STRIPPED_LOG.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_SPRUCE_STRIPPED_LOG_ITEM = ITEMS.register("star_worm_block_stripped_spruce_log", () -> new BlockItem(STAR_WORM_BLOCK_SPRUCE_STRIPPED_LOG.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_ACACIA_WOOD_ITEM = ITEMS.register("star_worm_block_acacia_wood", () -> new BlockItem(STAR_WORM_BLOCK_ACACIA_WOOD.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BIRCH_WOOD_ITEM = ITEMS.register("star_worm_block_birch_wood", () -> new BlockItem(STAR_WORM_BLOCK_BIRCH_WOOD.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_DARK_OAK_WOOD_ITEM = ITEMS.register("star_worm_block_dark_oak_wood", () -> new BlockItem(STAR_WORM_BLOCK_DARK_OAK_WOOD.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_JUNGLE_WOOD_ITEM = ITEMS.register("star_worm_block_jungle_wood", () -> new BlockItem(STAR_WORM_BLOCK_JUNGLE_WOOD.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_OAK_WOOD_ITEM = ITEMS.register("star_worm_block_oak_wood", () -> new BlockItem(STAR_WORM_BLOCK_OAK_WOOD.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_SPRUCE_WOOD_ITEM = ITEMS.register("star_worm_block_spruce_wood", () -> new BlockItem(STAR_WORM_BLOCK_SPRUCE_WOOD.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_ACACIA_STRIPPED_WOOD_ITEM = ITEMS.register("star_worm_block_stripped_acacia_wood", () -> new BlockItem(STAR_WORM_BLOCK_ACACIA_STRIPPED_WOOD.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BIRCH_STRIPPED_WOOD_ITEM = ITEMS.register("star_worm_block_stripped_birch_wood", () -> new BlockItem(STAR_WORM_BLOCK_BIRCH_STRIPPED_WOOD.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_DARK_OAK_STRIPPED_WOOD_ITEM = ITEMS.register("star_worm_block_stripped_dark_oak_wood", () -> new BlockItem(STAR_WORM_BLOCK_DARK_OAK_STRIPPED_WOOD.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_JUNGLE_STRIPPED_WOOD_ITEM = ITEMS.register("star_worm_block_stripped_jungle_wood", () -> new BlockItem(STAR_WORM_BLOCK_JUNGLE_STRIPPED_WOOD.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_OAK_STRIPPED_WOOD_ITEM = ITEMS.register("star_worm_block_stripped_oak_wood", () -> new BlockItem(STAR_WORM_BLOCK_OAK_STRIPPED_WOOD.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_SPRUCE_STRIPPED_WOOD_ITEM = ITEMS.register("star_worm_block_stripped_spruce_wood", () -> new BlockItem(STAR_WORM_BLOCK_SPRUCE_STRIPPED_WOOD.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_ACACIA_PLANKS_ITEM = ITEMS.register("star_worm_block_acacia_planks", () -> new BlockItem(STAR_WORM_BLOCK_ACACIA_PLANKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BIRCH_PLANKS_ITEM = ITEMS.register("star_worm_block_birch_planks", () -> new BlockItem(STAR_WORM_BLOCK_BIRCH_PLANKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_DARK_OAK_PLANKS_ITEM = ITEMS.register("star_worm_block_dark_oak_planks", () -> new BlockItem(STAR_WORM_BLOCK_DARK_OAK_PLANKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_JUNGLE_PLANKS_ITEM = ITEMS.register("star_worm_block_jungle_planks", () -> new BlockItem(STAR_WORM_BLOCK_JUNGLE_PLANKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_OAK_PLANKS_ITEM = ITEMS.register("star_worm_block_oak_planks", () -> new BlockItem(STAR_WORM_BLOCK_OAK_PLANKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_SPRUCE_PLANKS_ITEM = ITEMS.register("star_worm_block_spruce_planks", () -> new BlockItem(STAR_WORM_BLOCK_SPRUCE_PLANKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_STONE_ITEM = ITEMS.register("star_worm_block_stone", () -> new BlockItem(STAR_WORM_BLOCK_STONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_SMOOTH_STONE_ITEM = ITEMS.register("star_worm_block_smooth_stone", () -> new BlockItem(STAR_WORM_BLOCK_SMOOTH_STONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_COBBLESTONE_ITEM = ITEMS.register("star_worm_block_cobblestone", () -> new BlockItem(STAR_WORM_BLOCK_COBBLESTONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_MOSSY_COBBLESTONE_ITEM = ITEMS.register("star_worm_block_mossy_cobblestone", () -> new BlockItem(STAR_WORM_BLOCK_MOSSY_COBBLESTONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_ANDESITE_ITEM = ITEMS.register("star_worm_block_andesite", () -> new BlockItem(STAR_WORM_BLOCK_ANDESITE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_POLISHED_ANDESITE_ITEM = ITEMS.register("star_worm_block_polished_andesite", () -> new BlockItem(STAR_WORM_BLOCK_POLISHED_ANDESITE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_DIORITE_ITEM = ITEMS.register("star_worm_block_diorite", () -> new BlockItem(STAR_WORM_BLOCK_DIORITE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_POLISHED_DIORITE_ITEM = ITEMS.register("star_worm_block_polished_diorite", () -> new BlockItem(STAR_WORM_BLOCK_POLISHED_DIORITE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_GRANITE_ITEM = ITEMS.register("star_worm_block_granite", () -> new BlockItem(STAR_WORM_BLOCK_GRANITE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_POLISHED_GRANITE_ITEM = ITEMS.register("star_worm_block_polished_granite", () -> new BlockItem(STAR_WORM_BLOCK_POLISHED_GRANITE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_STONE_BRICKS_ITEM = ITEMS.register("star_worm_block_stone_bricks", () -> new BlockItem(STAR_WORM_BLOCK_STONE_BRICKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CRACKED_STONE_BRICKS_ITEM = ITEMS.register("star_worm_block_cracked_stone_bricks", () -> new BlockItem(STAR_WORM_BLOCK_CRACKED_STONE_BRICKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_MOSSY_STONE_BRICKS_ITEM = ITEMS.register("star_worm_block_mossy_stone_bricks", () -> new BlockItem(STAR_WORM_BLOCK_MOSSY_STONE_BRICKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CHISELED_STONE_BRICKS_ITEM = ITEMS.register("star_worm_block_chiseled_stone_bricks", () -> new BlockItem(STAR_WORM_BLOCK_CHISELED_STONE_BRICKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_COAL_ITEM = ITEMS.register("star_worm_block_coal", () -> new BlockItem(STAR_WORM_BLOCK_COAL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_IRON_ITEM = ITEMS.register("star_worm_block_iron", () -> new BlockItem(STAR_WORM_BLOCK_IRON.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_GOLD_ITEM = ITEMS.register("star_worm_block_gold", () -> new BlockItem(STAR_WORM_BLOCK_GOLD.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_DIAMOND_ITEM = ITEMS.register("star_worm_block_diamond", () -> new BlockItem(STAR_WORM_BLOCK_DIAMOND.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_EMERALD_ITEM = ITEMS.register("star_worm_block_emerald", () -> new BlockItem(STAR_WORM_BLOCK_EMERALD.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_REDSTONE_ITEM = ITEMS.register("star_worm_block_redstone", () -> new BlockItem(STAR_WORM_BLOCK_REDSTONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_LAPIS_ITEM = ITEMS.register("star_worm_block_lapis", () -> new BlockItem(STAR_WORM_BLOCK_LAPIS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_NETHERITE_ITEM = ITEMS.register("star_worm_block_netherite", () -> new BlockItem(STAR_WORM_BLOCK_NETHERITE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BONE_ITEM = ITEMS.register("star_worm_block_bone", () -> new  BlockItem(STAR_WORM_BLOCK_BONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_GRAVEL_ITEM = ITEMS.register("star_worm_block_gravel", () -> new BlockItem(STAR_WORM_BLOCK_GRAVEL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CLAY_ITEM= ITEMS.register("star_worm_block_clay", () -> new BlockItem(STAR_WORM_BLOCK_CLAY.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BRICKS_ITEM = ITEMS.register("star_worm_block_bricks", () -> new BlockItem(STAR_WORM_BLOCK_BRICKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_SAND_ITEM = ITEMS.register("star_worm_block_sand", () -> new BlockItem(STAR_WORM_BLOCK_SAND.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_SANDSTONE_ITEM = ITEMS.register("star_worm_block_sandstone", () -> new BlockItem(STAR_WORM_BLOCK_SANDSTONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CUT_SANDSTONE_ITEM = ITEMS.register("star_worm_block_cut_sandstone", () -> new BlockItem(STAR_WORM_BLOCK_CUT_SANDSTONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_SMOOTH_SANDSTONE_ITEM = ITEMS.register("star_worm_block_smooth_sandstone", () -> new BlockItem(STAR_WORM_BLOCK_SMOOTH_SANDSTONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CHISELED_SANDSTONE_ITEM = ITEMS.register("star_worm_block_chiseled_sandstone", () -> new BlockItem(STAR_WORM_BLOCK_CHISELED_SANDSTONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_RED_SAND_ITEM = ITEMS.register("star_worm_block_red_sand", () -> new BlockItem(STAR_WORM_BLOCK_RED_SAND.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_RED_SANDSTONE_ITEM = ITEMS.register("star_worm_block_red_sandstone", () -> new BlockItem(STAR_WORM_BLOCK_RED_SANDSTONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CUT_RED_SANDSTONE_ITEM = ITEMS.register("star_worm_block_cut_red_sandstone", () -> new BlockItem(STAR_WORM_BLOCK_CUT_RED_SANDSTONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_SMOOTH_RED_SANDSTONE_ITEM = ITEMS.register("star_worm_block_smooth_red_sandstone", () -> new BlockItem(STAR_WORM_BLOCK_SMOOTH_RED_SANDSTONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CHISELED_RED_SANDSTONE_ITEM = ITEMS.register("star_worm_block_chiseled_red_sandstone", () -> new BlockItem(STAR_WORM_BLOCK_CHISELED_RED_SANDSTONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_PRISMARINE_ITEM = ITEMS.register("star_worm_block_prismarine", () -> new BlockItem(STAR_WORM_BLOCK_PRISMARINE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_PRISMARINE_BRICKS_ITEM = ITEMS.register("star_worm_block_prismarine_bricks", () -> new BlockItem(STAR_WORM_BLOCK_PRISMARINE_BRICKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_DARK_PRISMARINE_ITEM = ITEMS.register("star_worm_block_dark_prismarine", () -> new BlockItem(STAR_WORM_BLOCK_DARK_PRISMARINE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_DRIED_KELP_ITEM = ITEMS.register("star_worm_block_dried_kelp", () -> new BlockItem(STAR_WORM_BLOCK_DRIED_KELP.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_DEAD_TUBE_CORAL_ITEM = ITEMS.register("star_worm_block_dead_tube_coral", () -> new BlockItem(STAR_WORM_BLOCK_DEAD_TUBE_CORAL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_DEAD_BRAIN_CORAL_ITEM = ITEMS.register("star_worm_block_dead_brain_coral", () -> new BlockItem(STAR_WORM_BLOCK_DEAD_BRAIN_CORAL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_DEAD_BUBBLE_CORAL_ITEM = ITEMS.register("star_worm_block_dead_bubble_coral", () -> new BlockItem(STAR_WORM_BLOCK_DEAD_BUBBLE_CORAL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_DEAD_FIRE_CORAL_ITEM = ITEMS.register("star_worm_block_dead_fire_coral", () -> new BlockItem(STAR_WORM_BLOCK_DEAD_FIRE_CORAL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_DEAD_HORN_CORAL_ITEM = ITEMS.register("star_worm_block_dead_horn_coral", () -> new BlockItem(STAR_WORM_BLOCK_DEAD_HORN_CORAL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_OBSIDIAN_ITEM = ITEMS.register("star_worm_block_obsidian", () -> new BlockItem(STAR_WORM_BLOCK_OBSIDIAN.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_NETHERRACK_ITEM = ITEMS.register("star_worm_block_netherrack", () -> new BlockItem(STAR_WORM_BLOCK_NETHERRACK.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_NETHER_BRICKS_ITEM = ITEMS.register("star_worm_block_nether_bricks", () -> new BlockItem(STAR_WORM_BLOCK_NETHER_BRICKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CHISELED_NETHER_BRICKS_ITEM = ITEMS.register("star_worm_block_chiseled_nether_bricks", () -> new BlockItem(STAR_WORM_BLOCK_CHISELED_NETHER_BRICKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CRACKED_NETHER_BRICKS_ITEM = ITEMS.register("star_worm_block_cracked_nether_bricks", () -> new BlockItem(STAR_WORM_BLOCK_CRACKED_NETHER_BRICKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_RED_NETHER_BRICKS_ITEM = ITEMS.register("star_worm_block_red_nether_bricks", () -> new BlockItem(STAR_WORM_BLOCK_RED_NETHER_BRICKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BLACKSTONE_ITEM = ITEMS.register("star_worm_block_blackstone", () -> new BlockItem(STAR_WORM_BLOCK_BLACKSTONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_POLISHED_BLACKSTONE_ITEM = ITEMS.register("star_worm_block_polished_blackstone", () -> new BlockItem(STAR_WORM_BLOCK_POLISHED_BLACKSTONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CRACKED_POLISHED_BLACKSTONE_BRICKS_ITEM = ITEMS.register("star_worm_block_cracked_polished_blackstone_bricks", () -> new BlockItem(STAR_WORM_BLOCK_CRACKED_POLISHED_BLACKSTONE_BRICKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CHISELED_POLISHED_BLACKSTONE_ITEM = ITEMS.register("star_worm_block_chiseled_polished_blackstone", () -> new BlockItem(STAR_WORM_BLOCK_CHISELED_POLISHED_BLACKSTONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_GILDED_BLACKSTONE_ITEM = ITEMS.register("star_worm_block_gilded_blackstone", () -> new BlockItem(STAR_WORM_BLOCK_GILDED_BLACKSTONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_NETHER_WART_ITEM= ITEMS.register("star_worm_block_nether_wart", () -> new BlockItem(STAR_WORM_BLOCK_NETHER_WART.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CRIMSON_STEM_ITEM= ITEMS.register("star_worm_block_crimson_stem", () -> new BlockItem(STAR_WORM_BLOCK_CRIMSON_STEM.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_STRIPPED_CRIMSON_STEM_ITEM= ITEMS.register("star_worm_block_stripped_crimson_stem", () -> new BlockItem(STAR_WORM_BLOCK_STRIPPED_CRIMSON_STEM.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CRIMSON_HYPHAE_ITEM= ITEMS.register("star_worm_block_crimson_hyphae", () -> new BlockItem(STAR_WORM_BLOCK_CRIMSON_HYPHAE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_STRIPPED_CRIMSON_HYPHAE_ITEM= ITEMS.register("star_worm_block_stripped_crimson_hyphae", () -> new BlockItem(STAR_WORM_BLOCK_STRIPPED_CRIMSON_HYPHAE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CRIMSON_PLANKS_ITEM= ITEMS.register("star_worm_block_crimson_planks", () -> new BlockItem(STAR_WORM_BLOCK_CRIMSON_PLANKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_WARPED_WART_ITEM= ITEMS.register("star_worm_block_warped_wart", () -> new BlockItem(STAR_WORM_BLOCK_WARPED_WART.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_WARPED_STEM_ITEM= ITEMS.register("star_worm_block_warped_stem", () -> new BlockItem(STAR_WORM_BLOCK_WARPED_STEM.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_STRIPPED_WARPED_STEM_ITEM= ITEMS.register("star_worm_block_stripped_warped_stem", () -> new BlockItem(STAR_WORM_BLOCK_STRIPPED_WARPED_STEM.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_WARPED_HYPHAE_ITEM= ITEMS.register("star_worm_block_warped_hyphae", () -> new BlockItem(STAR_WORM_BLOCK_WARPED_HYPHAE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_STRIPPED_WARPED_HYPHAE_ITEM= ITEMS.register("star_worm_block_stripped_warped_hyphae", () -> new BlockItem(STAR_WORM_BLOCK_STRIPPED_WARPED_HYPHAE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_WARPED_PLANKS_ITEM= ITEMS.register("star_worm_block_warped_planks", () -> new BlockItem(STAR_WORM_BLOCK_WARPED_PLANKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_SOUL_SAND_ITEM = ITEMS.register("star_worm_block_soul_sand", () -> new BlockItem(STAR_WORM_BLOCK_SOUL_SAND.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_SOUL_SOIL_ITEM = ITEMS.register("star_worm_block_soul_soil", () -> new BlockItem(STAR_WORM_BLOCK_SOUL_SOIL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_QUARTZ_ITEM = ITEMS.register("star_worm_block_quartz", () -> new BlockItem(STAR_WORM_BLOCK_QUARTZ.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_QUARTZ_PILLAR_ITEM = ITEMS.register("star_worm_block_quartz_pillar", () -> new  BlockItem(STAR_WORM_BLOCK_QUARTZ_PILLAR.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CHISELED_QUARTZ_ITEM = ITEMS.register("star_worm_block_chiseled_quartz", () -> new BlockItem(STAR_WORM_BLOCK_CHISELED_QUARTZ.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_SMOOTH_QUARTZ_ITEM = ITEMS.register("star_worm_block_smooth_quartz", () -> new BlockItem(STAR_WORM_BLOCK_SMOOTH_QUARTZ.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_QUARTZ_BRICKS_ITEM = ITEMS.register("star_worm_block_quartz_bricks", () -> new BlockItem(STAR_WORM_BLOCK_QUARTZ_BRICKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BASALT_ITEM = ITEMS.register("star_worm_block_basalt", () -> new BlockItem(STAR_WORM_BLOCK_BASALT.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_POLISHED_BASALT_ITEM = ITEMS.register("star_worm_block_polished_basalt", () -> new BlockItem(STAR_WORM_BLOCK_POLISHED_BASALT.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_END_STONE_ITEM = ITEMS.register("star_worm_block_end_stone", () -> new BlockItem(STAR_WORM_BLOCK_END_STONE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_END_STONE_BRICKS_ITEM = ITEMS.register("star_worm_block_end_stone_bricks", () -> new BlockItem(STAR_WORM_BLOCK_END_STONE_BRICKS.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_PURPUR_ITEM = ITEMS.register("star_worm_block_purpur", () -> new BlockItem(STAR_WORM_BLOCK_PURPUR.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_PURPUR_PILLAR_ITEM = ITEMS.register("star_worm_block_purpur_pillar", () -> new BlockItem(STAR_WORM_BLOCK_PURPUR_PILLAR.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BROWN_MUSHROOM_ITEM = ITEMS.register("star_worm_block_brown_mushroom", () -> new BlockItem(STAR_WORM_BLOCK_BROWN_MUSHROOM.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_RED_MUSHROOM_ITEM = ITEMS.register("star_worm_block_red_mushroom", () -> new BlockItem(STAR_WORM_BLOCK_RED_MUSHROOM.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_SNOW_ITEM = ITEMS.register("star_worm_block_snow", () -> new BlockItem(STAR_WORM_BLOCK_SNOW.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_ICE_ITEM = ITEMS.register("star_worm_block_ice", () -> new BlockItem(STAR_WORM_BLOCK_ICE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_PACKED_ICE_ITEM = ITEMS.register("star_worm_block_packed_ice", () -> new BlockItem(STAR_WORM_BLOCK_PACKED_ICE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BLUE_ICE_ITEM = ITEMS.register("star_worm_block_blue_ice", () -> new BlockItem(STAR_WORM_BLOCK_BLUE_ICE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_HONEY_ITEM = ITEMS.register("star_worm_block_honey", () -> new BlockItem(STAR_WORM_BLOCK_HONEY.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_HONEYCOMB_ITEM = ITEMS.register("star_worm_block_honeycomb", () -> new BlockItem(STAR_WORM_BLOCK_HONEYCOMB.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_SLIME_ITEM = ITEMS.register("star_worm_block_slime", () -> new BlockItem(STAR_WORM_BLOCK_SLIME.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BLACK_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_black_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_BLACK_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BLUE_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_blue_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_BLUE_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BROWN_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_brown_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_BROWN_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CYAN_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_cyan_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_CYAN_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_GRAY_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_gray_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_GRAY_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_GREEN_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_green_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_GREEN_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_LIGHT_BLUE_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_light_blue_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_LIGHT_BLUE_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_LIGHT_GRAY_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_light_gray_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_LIGHT_GRAY_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_LIME_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_lime_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_LIME_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_MAGENTA_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_magenta_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_MAGENTA_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_ORANGE_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_orange_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_ORANGE_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_PINK_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_pink_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_PINK_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_PURPLE_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_purple_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_PURPLE_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_RED_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_red_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_RED_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_WHITE_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_white_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_WHITE_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_YELLOW_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_yellow_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_YELLOW_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BLACK_GLAZED_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_black_glazed_terracotta", () -> new  BlockItem(STAR_WORM_BLOCK_BLACK_GLAZED_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BLUE_GLAZED_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_blue_glazed_terracotta", () -> new  BlockItem(STAR_WORM_BLOCK_BLUE_GLAZED_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BROWN_GLAZED_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_brown_glazed_terracotta", () -> new  BlockItem(STAR_WORM_BLOCK_BROWN_GLAZED_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CYAN_GLAZED_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_cyan_glazed_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_CYAN_GLAZED_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_GRAY_GLAZED_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_gray_glazed_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_GRAY_GLAZED_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_GREEN_GLAZED_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_green_glazed_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_GREEN_GLAZED_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_LIGHT_BLUE_GLAZED_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_light_blue_glazed_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_LIGHT_BLUE_GLAZED_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_LIGHT_GRAY_GLAZED_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_light_gray_glazed_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_LIGHT_GRAY_GLAZED_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_LIME_GLAZED_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_lime_glazed_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_LIME_GLAZED_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final	RegistryObject<Item> STAR_WORM_BLOCK_MAGENTA_GLAZED_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_magenta_glazed_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_MAGENTA_GLAZED_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_ORANGE_GLAZED_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_orange_glazed_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_ORANGE_GLAZED_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_PINK_GLAZED_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_pink_glazed_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_PINK_GLAZED_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_PURPLE_GLAZED_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_purple_glazed_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_PURPLE_GLAZED_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_RED_GLAZED_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_red_glazed_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_RED_GLAZED_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_WHITE_GLAZED_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_white_glazed_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_WHITE_GLAZED_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_YELLOW_GLAZED_TERRACOTTA_ITEM = ITEMS.register("star_worm_block_yellow_glazed_terracotta", () -> new BlockItem(STAR_WORM_BLOCK_YELLOW_GLAZED_TERRACOTTA.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BLACK_CONCRETE_POWDER_ITEM = ITEMS.register("star_worm_block_black_concrete_powder", () -> new BlockItem(STAR_WORM_BLOCK_BLACK_CONCRETE_POWDER.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BLUE_CONCRETE_POWDER_ITEM = ITEMS.register("star_worm_block_blue_concrete_powder", () -> new BlockItem(STAR_WORM_BLOCK_BLUE_CONCRETE_POWDER.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BROWN_CONCRETE_POWDER_ITEM = ITEMS.register("star_worm_block_brown_concrete_powder", () -> new BlockItem(STAR_WORM_BLOCK_BROWN_CONCRETE_POWDER.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CYAN_CONCRETE_POWDER_ITEM = ITEMS.register("star_worm_block_cyan_concrete_powder", () -> new BlockItem(STAR_WORM_BLOCK_CYAN_CONCRETE_POWDER.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_GRAY_CONCRETE_POWDER_ITEM = ITEMS.register("star_worm_block_gray_concrete_powder", () -> new BlockItem(STAR_WORM_BLOCK_GRAY_CONCRETE_POWDER.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_GREEN_CONCRETE_POWDER_ITEM = ITEMS.register("star_worm_block_green_concrete_powder", () -> new BlockItem(STAR_WORM_BLOCK_GREEN_CONCRETE_POWDER.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_LIGHT_BLUE_CONCRETE_POWDER_ITEM = ITEMS.register("star_worm_block_light_blue_concrete_powder", () -> new BlockItem(STAR_WORM_BLOCK_LIGHT_BLUE_CONCRETE_POWDER.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_LIGHT_GRAY_CONCRETE_POWDER_ITEM = ITEMS.register("star_worm_block_light_gray_concrete_powder", () -> new BlockItem(STAR_WORM_BLOCK_LIGHT_GRAY_CONCRETE_POWDER.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_LIME_CONCRETE_POWDER_ITEM = ITEMS.register("star_worm_block_lime_concrete_powder", () -> new BlockItem(STAR_WORM_BLOCK_LIME_CONCRETE_POWDER.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_MAGENTA_CONCRETE_POWDER_ITEM = ITEMS.register("star_worm_block_magenta_concrete_powder", () -> new BlockItem(STAR_WORM_BLOCK_MAGENTA_CONCRETE_POWDER.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_ORANGE_CONCRETE_POWDER_ITEM= ITEMS.register("star_worm_block_orange_concrete_powder", () -> new BlockItem(STAR_WORM_BLOCK_ORANGE_CONCRETE_POWDER.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_PINK_CONCRETE_POWDER_ITEM = ITEMS.register("star_worm_block_pink_concrete_powder", () -> new BlockItem(STAR_WORM_BLOCK_PINK_CONCRETE_POWDER.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_PURPLE_CONCRETE_POWDER_ITEM = ITEMS.register("star_worm_block_purple_concrete_powder", () -> new BlockItem(STAR_WORM_BLOCK_PURPLE_CONCRETE_POWDER.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_RED_CONCRETE_POWDER_ITEM = ITEMS.register("star_worm_block_red_concrete_powder", () -> new BlockItem(STAR_WORM_BLOCK_RED_CONCRETE_POWDER.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_WHITE_CONCRETE_POWDER_ITEM = ITEMS.register("star_worm_block_white_concrete_powder", () -> new BlockItem(STAR_WORM_BLOCK_WHITE_CONCRETE_POWDER.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_YELLOW_CONCRETE_POWDER_ITEM = ITEMS.register("star_worm_block_yellow_concrete_powder", () -> new BlockItem(STAR_WORM_BLOCK_YELLOW_CONCRETE_POWDER.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BLACK_CONCRETE_ITEM = ITEMS.register("star_worm_block_black_concrete", () -> new BlockItem(STAR_WORM_BLOCK_BLACK_CONCRETE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BLUE_CONCRETE_ITEM = ITEMS.register("star_worm_block_blue_concrete", () -> new BlockItem(STAR_WORM_BLOCK_BLUE_CONCRETE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BROWN_CONCRETE_ITEM = ITEMS.register("star_worm_block_brown_concrete", () -> new BlockItem(STAR_WORM_BLOCK_BROWN_CONCRETE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CYAN_CONCRETE_ITEM = ITEMS.register("star_worm_block_cyan_concrete", () -> new BlockItem(STAR_WORM_BLOCK_CYAN_CONCRETE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_GRAY_CONCRETE_ITEM = ITEMS.register("star_worm_block_gray_concrete", () -> new BlockItem(STAR_WORM_BLOCK_GRAY_CONCRETE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_GREEN_CONCRETE_ITEM = ITEMS.register("star_worm_block_green_concrete", () -> new BlockItem(STAR_WORM_BLOCK_GREEN_CONCRETE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_LIME_CONCRETE_ITEM = ITEMS.register("star_worm_block_lime_concrete", () -> new BlockItem(STAR_WORM_BLOCK_LIME_CONCRETE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_LIGHT_BLUE_CONCRETE_ITEM = ITEMS.register("star_worm_block_light_blue_concrete", () -> new BlockItem(STAR_WORM_BLOCK_LIGHT_BLUE_CONCRETE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_LIGHT_GRAY_CONCRETE_ITEM = ITEMS.register("star_worm_block_light_gray_concrete", () -> new BlockItem(STAR_WORM_BLOCK_LIGHT_GRAY_CONCRETE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_MAGENTA_CONCRETE_ITEM = ITEMS.register("star_worm_block_magenta_concrete", () -> new BlockItem(STAR_WORM_BLOCK_MAGENTA_CONCRETE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_ORANGE_CONCRETE_ITEM = ITEMS.register("star_worm_block_orange_concrete", () -> new BlockItem(STAR_WORM_BLOCK_ORANGE_CONCRETE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_PINK_CONCRETE_ITEM = ITEMS.register("star_worm_block_pink_concrete", () -> new BlockItem(STAR_WORM_BLOCK_PINK_CONCRETE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_PURPLE_CONCRETE_ITEM= ITEMS.register("star_worm_block_purple_concrete", () -> new BlockItem(STAR_WORM_BLOCK_PURPLE_CONCRETE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_RED_CONCRETE_ITEM = ITEMS.register("star_worm_block_red_concrete", () -> new BlockItem(STAR_WORM_BLOCK_RED_CONCRETE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_WHITE_CONCRETE_ITEM = ITEMS.register("star_worm_block_white_concrete", () -> new BlockItem(STAR_WORM_BLOCK_WHITE_CONCRETE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_YELLOW_CONCRETE_ITEM = ITEMS.register("star_worm_block_yellow_concrete", () -> new BlockItem(STAR_WORM_BLOCK_YELLOW_CONCRETE.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BLACK_WOOL_ITEM = ITEMS.register("star_worm_block_black_wool", () -> new BlockItem(STAR_WORM_BLOCK_BLACK_WOOL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BLUE_WOOL_ITEM = ITEMS.register("star_worm_block_blue_wool", () -> new BlockItem(STAR_WORM_BLOCK_BLUE_WOOL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_BROWN_WOOL_ITEM = ITEMS.register("star_worm_block_brown_wool", () -> new BlockItem(STAR_WORM_BLOCK_BROWN_WOOL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_CYAN_WOOL_ITEM = ITEMS.register("star_worm_block_cyan_wool", () -> new BlockItem(STAR_WORM_BLOCK_CYAN_WOOL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_GRAY_WOOL_ITEM = ITEMS.register("star_worm_block_gray_wool", () -> new BlockItem(STAR_WORM_BLOCK_GRAY_WOOL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_GREEN_WOOL_ITEM = ITEMS.register("star_worm_block_green_wool", () -> new BlockItem(STAR_WORM_BLOCK_GREEN_WOOL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_LIGHT_BLUE_WOOL_ITEM = ITEMS.register("star_worm_block_light_blue_wool", () -> new BlockItem(STAR_WORM_BLOCK_LIGHT_BLUE_WOOL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_LIGHT_GRAY_WOOL_ITEM = ITEMS.register("star_worm_block_light_gray_wool", () -> new BlockItem(STAR_WORM_BLOCK_LIGHT_GRAY_WOOL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_LIME_WOOL_ITEM = ITEMS.register("star_worm_block_lime_wool", () -> new BlockItem(STAR_WORM_BLOCK_LIME_WOOL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_MAGENTA_WOOL_ITEM = ITEMS.register("star_worm_block_magenta_wool", () -> new BlockItem(STAR_WORM_BLOCK_MAGENTA_WOOL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_ORANGE_WOOL_ITEM = ITEMS.register("star_worm_block_orange_wool", () -> new BlockItem(STAR_WORM_BLOCK_ORANGE_WOOL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_PINK_WOOL_ITEM = ITEMS.register("star_worm_block_pink_wool", () -> new BlockItem(STAR_WORM_BLOCK_PINK_WOOL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_PURPLE_WOOL_ITEM = ITEMS.register("star_worm_block_purple_wool", () -> new BlockItem(STAR_WORM_BLOCK_PURPLE_WOOL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_RED_WOOL_ITEM = ITEMS.register("star_worm_block_red_wool", () -> new BlockItem(STAR_WORM_BLOCK_RED_WOOL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_WHITE_WOOL_ITEM = ITEMS.register("star_worm_block_white_wool", () -> new BlockItem(STAR_WORM_BLOCK_WHITE_WOOL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_BLOCK_YELLOW_WOOL_ITEM = ITEMS.register("star_worm_block_yellow_wool", () -> new BlockItem(STAR_WORM_BLOCK_YELLOW_WOOL.get(), new Item.Properties().tab(SWEM.SWLMTAB)));

	public static void checkAccess() {
		
		String playerUUID = Minecraft.getInstance().getUser().getUuid().replaceAll("-", "");

		try {
			URL url = new URL("http://auth.swequestrian.com:9542/check?uuid=" + playerUUID);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();

			con.disconnect();

			if (content.toString().equalsIgnoreCase("okay")) {
				return;
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException exception) {
			exception.printStackTrace();
		}

		StringBuilder sb = new StringBuilder();
		sb.append("\no/\n");
		sb.append("Hello random person! Your minecraft crashed because you are not on our approved beta-tester list! :)\n");
		sb.append("If this is a case of redistribution, we very much appreciate your enthusiasm about the mod, however your impatience has banned you from our official servers for a minimum of six months. :(\n");
		sb.append("We hope this has been a wonderful learning experience in the world of piracy.\n");
		sb.append("Have a nice day! :D");

		System.out.println(sb.toString());

		System.exit(-1);

	}

}


