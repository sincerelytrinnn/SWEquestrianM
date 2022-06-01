package com.alaharranhonor.swem.blocks;

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

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class FuelBlock extends Block {
  /** Instantiates a new Fuel block. */
  public FuelBlock() {
    super(
        AbstractBlock.Properties.of(Material.METAL)
            .strength(2.0f, 6.0f)
            .sound(SoundType.STONE)
            .harvestLevel(1)
            .harvestTool(ToolType.PICKAXE));
  }

  /**
   * Currently only called by fire when it is on top of this block. Returning true will prevent the
   * fire from naturally dying during updating. Also prevents firing from dying from rain.
   *
   * @param state The current state
   * @param world The current world
   * @param pos Block position in world
   * @param side The face that the fire is coming from
   * @return True if this block sustains fire, meaning it will never go out.
   */
  @Override
  public boolean isFireSource(BlockState state, IWorldReader world, BlockPos pos, Direction side) {
    return true;
  }
}
