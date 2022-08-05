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

import net.minecraft.block.Block;
import net.minecraft.item.DyeColor;

import java.util.Arrays;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SWEMUtil {

    public static DyeColor[] COLOURS = {DyeColor.WHITE, DyeColor.LIGHT_BLUE, DyeColor.CYAN, DyeColor.BLUE, DyeColor.PINK, DyeColor.MAGENTA, DyeColor.PURPLE, DyeColor.YELLOW, DyeColor.ORANGE, DyeColor.RED, DyeColor.LIME, DyeColor.GREEN, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK, DyeColor.BROWN,};
    private static final DyeColor[] BY_INDEX = Arrays.stream(COLOURS).toArray(DyeColor[]::new);

  public static boolean isInDistanceOfBlock(World level, BlockPos pos, int distance, Block blockToCheck) {
    for (int x = -distance; x <= distance; x++) {
      for (int z = -distance; z <= distance; z++) {
        for (int y = -distance; y <= distance; y++) {
          if (level.getBlockState(pos.offset(x, y, z)).getBlock() == blockToCheck) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Check text overflow string.
   *
   * @param text the text
   * @param maxLimit the max limit
   * @return the string
   */
  public static String checkTextOverflow(String text, int maxLimit) {
    if (text.length() > maxLimit - 2) {
      return text.substring(0, maxLimit - 2) + "...";
    } else {
      return text;
    }
  }

    /**
     * Logical by id dye color.
     *
     * @param pColorId the p color id
     * @return the dye color
     */
    public static DyeColor logicalByIndex(int pColorId) {
        if (pColorId < 0 || pColorId >= BY_INDEX.length) {
            pColorId = 0;
        }

        return BY_INDEX[pColorId];
    }

    /**
     * Helper function for converting internal jump value to jump height in blocks.
     *
     * @param internal The internal jump value.
     * @return The jump height in blocks.
     * @see <a href="https://minecraft.fandom.com/wiki/Tutorials/Horses#Jump_Strength">Jump Strength Wiki</a>
     */
    public static double getJumpBlocksFromInternalJump(double internal) {
        return Math.pow(internal, 1.7D) * 5.293D;
    }

    /**
     * Helper function for converting Jump height in blocks to internal jump value.
     *
     * @param blocks The jump height in blocks.
     * @return The internal jump value.
     * @see <a href="https://minecraft.fandom.com/wiki/Tutorials/Horses#Jump_Strength">Jump Strength Wiki</a>
     */
    public static double getInternalJumpFromBlocks(double blocks) {
        return Math.pow(blocks / 5.293D, 1 / 1.7D);
    }

    /**
     * Helper function for converting internal speed value to blocks per second.
     *
     * @param internal The internals speed value.
     * @return The blocks per second.
     * @see <a href="https://minecraft.fandom.com/wiki/Tutorials/Horses#Speed">Horse Speed Wiki</a>
     */
    public static double getBlocksPerSecondFromInternalSpeed(double internal) {
        return internal * 43.17;
    }

    /**
     * Helper function for converting blocks per seconds to internal speed value.
     *
     * @param blocksPerSecond The blocks per seconds.
     * @return The internal speed value.
     * @see <a href="https://minecraft.fandom.com/wiki/Tutorials/Horses#Speed">Horse Speed Wiki</a>
     */
    public static double getInternalSpeedFromBlocksPerSecond(double blocksPerSecond) {
        return blocksPerSecond / 43.17;
    }

}
