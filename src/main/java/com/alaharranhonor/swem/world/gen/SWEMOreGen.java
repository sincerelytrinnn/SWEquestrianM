package com.alaharranhonor.swem.world.gen;

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

import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class SWEMOreGen {
    public static ConfiguredFeature<?, ?> AMETHYST_ORE;
    public static ConfiguredFeature<?, ?> CANTAZARITE_ORE;
    public static ConfiguredFeature<?, ?> SWEM_COBBLE_ORE;

    /**
     * Check and init biome boolean.
     *
     * @param event the event
     * @return the boolean
     */
    public static boolean checkAndInitBiome(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.NETHER) {
            return true;
        }

        if (event.getCategory() != Biome.Category.THEEND) {
            initOverWorldFeatures();
            return true;
        }
        return false;
    }

    /**
     * Init over world features.
     */
    protected static void initOverWorldFeatures() {
        if (AMETHYST_ORE == null) {
            AMETHYST_ORE =
                    OreGenUtils.buildOverWorldFeature(SWEMBlocks.AMETHYST_ORE.get().defaultBlockState());
        }
        if (CANTAZARITE_ORE == null) {
            CANTAZARITE_ORE =
                    OreGenUtils.buildOverWorldFeature(SWEMBlocks.CANTAZARITE_ORE.get().defaultBlockState());
        }
        if (SWEM_COBBLE_ORE == null) {
            SWEM_COBBLE_ORE =
                    OreGenUtils.buildOverWorldFeature(SWEMBlocks.STAR_WORM_COBBLE.get().defaultBlockState());
        }
    }

    /**
     * Generate overworld ores.
     *
     * @param event the event
     */
    public static void generateOverworldOres(BiomeLoadingEvent event) {
        event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, AMETHYST_ORE);
        event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, CANTAZARITE_ORE);
        event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, SWEM_COBBLE_ORE);
    }
}
