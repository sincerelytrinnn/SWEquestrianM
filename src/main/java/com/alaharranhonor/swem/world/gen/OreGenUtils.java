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

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

public class OreGenUtils {

  /**
   * Build over world feature configured feature.
   *
   * @param bstate the bstate
   * @return the configured feature
   */
  public static ConfiguredFeature<?, ?> buildOverWorldFeature(BlockState bstate) {
    return Feature.ORE
        .configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, bstate, 5))
        .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(5, 0, 20)).squared())
        .count(8);
  }
}
