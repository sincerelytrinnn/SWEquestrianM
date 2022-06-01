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

import net.minecraft.block.BlockState;
import net.minecraft.block.TripWireBlock;
import net.minecraft.block.TripWireHookBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class GlowTripwireBlock extends TripWireBlock {
  /**
   * Instantiates a new Glow tripwire block.
   *
   * @param hook the hook
   * @param properties the properties
   */
  public GlowTripwireBlock(TripWireHookBlock hook, Properties properties) {
    super(hook, properties);
  }

  @Override
  public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
    return 15;
  }
}
