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
import com.alaharranhonor.swem.world.structure.BarnStructure;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class SWEMStructure {

	public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, SWEM.MOD_ID);

	/**
	 * Init.
	 *
	 * @param modBus the mod bus
	 */
	public static void init(IEventBus modBus) {
		STRUCTURES.register(modBus);
	}

	public static final RegistryObject<Structure<NoFeatureConfig>> BARN = STRUCTURES.register("barn", () -> new BarnStructure(NoFeatureConfig.CODEC));


	/**
	 * This is where we set the rarity of your structures and determine if land conforms to it.
	 * See the comments in below for more details.
	 */
	public static void setupStructures() {
		setupMapSpacingAndLand(
				BARN.get(),
				new StructureSeparationSettings(25, /* average distance apart in chunks between spawn attempts */
						15, /* minimum distance apart in chunks between spawn attempts. MUST BE LESS THAN ABOVE VALUE*/
						1234567890), /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */
				true);
	}


	/**
	 * Adds the provided structure to the registry, and adds the separation settings.
	 * The rarity of the structure is determined based on the values passed into
	 * this method in the structureSeparationSettings argument.
	 * This method is called by setupStructures above.
	 */
	public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings separationSettings, boolean transformSorroundingLand) {
		/*
		 * We need to add our structures into the map in Structure class
		 * alongside vanilla structures or else it will cause errors.
		 *
		 * If the registration is setup properly for the structure,
		 * getRegistryName() should never return null.
		 */
		Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);


		/*
		 * Whether surrounding land will be modified automatically to conform to the bottom of the structure.
		 * Basically, it adds land at the base of the structure like it does for Villages and Outposts.
		 * Doesn't work well on structure that have pieces stacked vertically or change in heights.
		 *
		 * Note: The air space this method will create will be filled with water if the structure is below sealevel.
		 * This means this is best for structure above sealevel so keep that in mind.
		 *
		 * NOISE_AFFECTING_FEATURES requires AccessTransformer  (See resources/META-INF/accesstransformer.cfg)
		 */
		if (transformSorroundingLand) {
			Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder()
					.addAll(Structure.NOISE_AFFECTING_FEATURES)
					.add(structure)
					.build();
		}


		/*
		 * This is the map that holds the default spacing of all structures.
		 * Always add your structure to here so that other mods can utilize it if needed.
		 *
		 * However, while it does propagate the spacing to some correct dimensions from this map,
		 * it seems it doesn't always work for code made dimensions as they read from this list beforehand.
		 *
		 * Instead, we will use the WorldEvent.Load event in StructureTutorialMain to add the structure
		 * spacing from this list into that dimension or to do dimension blacklisting properly.
		 * We also use our entry in DimensionStructuresSettings.DEFAULTS in WorldEvent.Load as well.
		 *
		 * DEFAULTS requires AccessTransformer  (See resources/META-INF/accesstransformer.cfg)
		 */

		DimensionStructuresSettings.DEFAULTS =
				ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
				.putAll(DimensionStructuresSettings.DEFAULTS)
				.put(structure, separationSettings)
				.build();


		/*
		 * There are very few mods that relies on seeing your structure in the noise settings registry before the world is made.
		 *
		 * You may see some mods add their spacings to DimensionSettings.BUILTIN_OVERWORLD instead of the NOISE_GENERATOR_SETTINGS loop below but
		 * that field only applies for the default overworld and won't add to other worldtypes or dimensions (like amplified or Nether).
		 * So yeah, don't do DimensionSettings.BUILTIN_OVERWORLD. Use the NOISE_GENERATOR_SETTINGS loop below instead if you must.
		 */
		WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
			Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();

			/*
			 * Pre-caution in case a mod makes the structure map immutable like datapacks do.
			 * I take no chances myself. You never know what another mods does...
			 *
			 * structureConfig requires AccessTransformer  (See resources/META-INF/accesstransformer.cfg)
			 */
			if(structureMap instanceof ImmutableMap){
				Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
				tempMap.put(structure, separationSettings);
				settings.getValue().structureSettings().structureConfig = tempMap;
			}
			else{
				structureMap.put(structure, separationSettings);
			}
		});
	}
}
