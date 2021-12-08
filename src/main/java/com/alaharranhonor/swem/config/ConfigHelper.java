package com.alaharranhonor.swem.config;


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

import com.alaharranhonor.swem.world.gen.SWEMOreGen;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.DecoratedPlacementConfig;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.fml.config.ModConfig;

public final class ConfigHelper {


	public static void bakeClient(final ModConfig config) {
		//
	}

	public static void bakeServer(final ModConfig config) {

		// Vein Size
		((OreFeatureConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.AMETHYST_ORE.config).feature.get().config).feature.get().config).size = ConfigHolder.SERVER.serverAmethystVeinSize.get();
		// Vein Count
		((FeatureSpreadConfig)((DecoratedFeatureConfig) SWEMOreGen.AMETHYST_ORE.config).decorator.config).count().baseValue = ConfigHolder.SERVER.serverAmethystVeinCount.get();
		// Minimum Height the ore can spawn
		((TopSolidRangeConfig)((DecoratedPlacementConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.AMETHYST_ORE.config).feature.get().config).decorator.config).inner.config).bottomOffset = ConfigHolder.SERVER.serverAmethystBottomHeight.get();
		// Maximum Height the ore can spawn.
		((TopSolidRangeConfig)((DecoratedPlacementConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.AMETHYST_ORE.config).feature.get().config).decorator.config).inner.config).maximum = ConfigHolder.SERVER.serverAmethystMaxHeight.get();


		// Vein Size
		((OreFeatureConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.CANTAZARITE_ORE.config).feature.get().config).feature.get().config).size = ConfigHolder.SERVER.serverCantazariteVeinSize.get();
		// Vein Count
		((FeatureSpreadConfig)((DecoratedFeatureConfig) SWEMOreGen.CANTAZARITE_ORE.config).decorator.config).count().baseValue = ConfigHolder.SERVER.serverCantazariteVeinCount.get();
		// Minimum Height the ore can spawn
		((TopSolidRangeConfig)((DecoratedPlacementConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.CANTAZARITE_ORE.config).feature.get().config).decorator.config).inner.config).bottomOffset = ConfigHolder.SERVER.serverCantazariteBottomHeight.get();
		// Maximum Height the ore can spawn.
		((TopSolidRangeConfig)((DecoratedPlacementConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.CANTAZARITE_ORE.config).feature.get().config).decorator.config).inner.config).maximum = ConfigHolder.SERVER.serverCantazariteMaxHeight.get();

		// Vein Size
		((OreFeatureConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.SWLM_COBBLE_ORE.config).feature.get().config).feature.get().config).size = ConfigHolder.SERVER.serverSWLMCobbleVeinSize.get();
		// Vein Count
		((FeatureSpreadConfig)((DecoratedFeatureConfig) SWEMOreGen.SWLM_COBBLE_ORE.config).decorator.config).count().baseValue = ConfigHolder.SERVER.serverSWLMCobbleVeinCount.get();
		// Minimum Height the ore can spawn
		((TopSolidRangeConfig)((DecoratedPlacementConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.SWLM_COBBLE_ORE.config).feature.get().config).decorator.config).inner.config).bottomOffset = ConfigHolder.SERVER.serverSWLMCobbleBottomHeight.get();
		// Maximum Height the ore can spawn.
		((TopSolidRangeConfig)((DecoratedPlacementConfig)((DecoratedFeatureConfig)((DecoratedFeatureConfig) SWEMOreGen.SWLM_COBBLE_ORE.config).feature.get().config).decorator.config).inner.config).maximum = ConfigHolder.SERVER.serverSWLMCobbleMaxHeight.get();
	}
}
