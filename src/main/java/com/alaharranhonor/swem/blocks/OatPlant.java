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

import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class OatPlant extends CropsBlock {
  private static final VoxelShape[] SHAPE_BY_AGE =
      new VoxelShape[] {
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
      };

  /**
   * Instantiates a new Oat plant.
   *
   * @param builder the builder
   */
  public OatPlant(Properties builder) {
    super(builder);
  }

  @Override
  protected IItemProvider getBaseSeedId() {
    return SWEMItems.OAT_SEEDS.get();
  }

  @Override
  public VoxelShape getShape(
      BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
  }
}
