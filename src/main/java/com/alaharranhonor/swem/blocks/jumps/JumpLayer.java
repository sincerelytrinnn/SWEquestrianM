package com.alaharranhonor.swem.blocks.jumps;

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

import com.alaharranhonor.swem.blocks.SWEMBlockStateProperties.TripleBlockSide;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraftforge.fml.RegistryObject;

public enum JumpLayer {
  AIR("Air", Blocks.AIR.defaultBlockState(), 1, 1, 5, false, new ArrayList<>()),

  NONE("None", SWEMBlocks.JUMP_NONE.get().defaultBlockState(), 1, 5, 1, false, new ArrayList<>()),

  LOG(
      "Log",
      SWEMBlocks.JUMP_LOG
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      SWEMBlocks.JUMP_LOG
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      1,
      1,
      false,
      new ArrayList<>()),
  CAVALETTI(
      "Cavaletti",
      SWEMBlocks.CAVALETTIS
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      SWEMBlocks.CAVALETTIS
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      1,
      1,
      true,
      SWEMBlocks.CAVALETTIS),
  POLE_ON_BOX_SMALL(
      "Pole On Box Small",
      SWEMBlocks.POLE_ON_BOXES_SMALL
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      SWEMBlocks.POLE_ON_BOXES_SMALL
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      1,
      1,
      true,
      SWEMBlocks.POLE_ON_BOXES_SMALL),
  POLE_ON_BOX_LARGE(
      "Pole On Box Large",
      SWEMBlocks.POLE_ON_BOXES_LARGE
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      SWEMBlocks.POLE_ON_BOXES_LARGE
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      1,
      1,
      true,
      SWEMBlocks.POLE_ON_BOXES_LARGE),
  STAIR_DROP(
      "Stair Drop",
      SWEMBlocks.JUMP_STAIR_DROP
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      SWEMBlocks.CAVALETTIS
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      1,
      1,
      false,
      new ArrayList<>()),

  HEDGE(
      "Hedge",
      SWEMBlocks.JUMP_HEDGE
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      SWEMBlocks.JUMP_HEDGE
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      1,
      2,
      false,
      new ArrayList<>()),
  WALL(
      "Wall",
      SWEMBlocks.JUMP_WALL
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      SWEMBlocks.JUMP_WALL
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      5,
      2,
      false,
      new ArrayList<>()),

  BRUSH_BOX(
      "Brush Box",
      SWEMBlocks.JUMP_BRUSH_BOX
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      SWEMBlocks.JUMP_BRUSH_BOX
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      1,
      1,
      false,
      new ArrayList<>()),
  FLOWER_BOX(
      "Flower Box",
      SWEMBlocks.FLOWER_BOXES
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      SWEMBlocks.FLOWER_BOXES
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      1,
      1,
      true,
      SWEMBlocks.FLOWER_BOXES),
  COOP(
      "Coop",
      SWEMBlocks.JUMP_COOP
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      SWEMBlocks.JUMP_COOP
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      1,
      1,
      false,
      new ArrayList<>()),
  ROLL_TOP(
      "Roll Top",
      SWEMBlocks.ROLL_TOPS
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      SWEMBlocks.ROLL_TOPS
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      1,
      1,
      true,
      SWEMBlocks.ROLL_TOPS),
  WALL_MINI(
      "Wall Mini",
      SWEMBlocks.JUMP_WALL_MINI
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      SWEMBlocks.JUMP_WALL_MINI
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      1,
      1,
      false,
      new ArrayList<>()),
  GROUND_POLE(
      "Ground Pole",
      SWEMBlocks.GROUND_POLES
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      1,
      1,
      1,
      true,
      SWEMBlocks.GROUND_POLES),

  RAIL(
      "Rail",
      SWEMBlocks.RAILS
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      5,
      1,
      true,
      SWEMBlocks.RAILS),
  PLANK(
      "Plank",
      SWEMBlocks.PLANKS
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      SWEMBlocks.PLANKS
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      5,
      1,
      true,
      SWEMBlocks.PLANKS),
  PLANK_FANCY(
      "Plank Fancy",
      SWEMBlocks.FANCY_PLANKS
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      SWEMBlocks.FANCY_PLANKS
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.RIGHT),
      SWEMBlocks.FANCY_PLANKS
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      5,
      1,
      true,
      SWEMBlocks.FANCY_PLANKS),

  PANEL_WAVE(
      "Panel Wave",
      SWEMBlocks.PANELS_WAVE
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      SWEMBlocks.PANELS_WAVE
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      4,
      1,
      true,
      SWEMBlocks.PANELS_WAVE),
  PANEL_ARROW(
      "Panel Arrow",
      SWEMBlocks.PANELS_ARROW
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      SWEMBlocks.PANELS_ARROW
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      4,
      1,
      true,
      SWEMBlocks.PANELS_ARROW),
  PANEL_STRIPE(
      "Panel Stripe",
      SWEMBlocks.PANELS_STRIPE
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT),
      SWEMBlocks.PANELS_STRIPE
          .get(0)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE),
      1,
      4,
      1,
      true,
      SWEMBlocks.PANELS_STRIPE);

  // CROSS_RAILS(JUMP_CROSS_RAILS.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE,
  // TripleBlockSide.LEFT),JUMP_CROSS_RAILS.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 1, 5, 1),

  // SWEDISH_RAILS(JUMP_SWEDISH_RAILS.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE,
  // TripleBlockSide.LEFT),JUMP_SWEDISH_RAILS.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE), 2, 5, 1),

  // RED_FLAG(JUMP_RED_FLAG.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE,
  // TripleBlockSide.MIDDLE), 6, 6, 2),
  // WHITE_FLAG(JUMP_WHITE_FLAG.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE,
  // TripleBlockSide.MIDDLE), 6, 6, 2),
  // RED_WHITE_FLAG(JUMP_RED_WHITE_FLAG.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE,
  // TripleBlockSide.MIDDLE), 6, 6, 2),

  // NUMBERS(JUMP_NUMBERS.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE,
  // TripleBlockSide.LEFT),JUMP_NUMBERS.get().defaultBlockState()().setValue(JumpBlock.JUMP_PIECE,
  // TripleBlockSide.MIDDLE), 2, 6, 2);

  String displayName;
  BlockState endState;
  BlockState betweenState;
  BlockState middleState;
  int minLayer;
  int maxLayer;
  int minHeight;
  boolean hasColorVariants;
  List<RegistryObject<JumpBlock>> colorVariants;

  /**
   * Instantiates a new Jump layer.
   *
   * @param displayName the display name
   * @param allState the all state
   * @param minLayer the min layer
   * @param maxLayer the max layer
   * @param minHeight the min height
   * @param hasColorVariants the has color variants
   * @param colorVariants the color variants
   */
  JumpLayer(
      String displayName,
      BlockState allState,
      int minLayer,
      int maxLayer,
      int minHeight,
      boolean hasColorVariants,
      List<RegistryObject<JumpBlock>> colorVariants) {
    this.displayName = displayName;
    this.endState = allState;
    this.betweenState = allState;
    this.middleState = allState;
    this.minLayer = minLayer;
    this.maxLayer = maxLayer;
    this.minHeight = minHeight;
    this.hasColorVariants = hasColorVariants;
    this.colorVariants = colorVariants;
  }

  /**
   * Instantiates a new Jump layer.
   *
   * @param displayName the display name
   * @param endState the end state
   * @param middleState the middle state
   * @param minLayer the min layer
   * @param maxLayer the max layer
   * @param minHeight the min height
   * @param hasColorVariants the has color variants
   * @param colorVariants the color variants
   */
  JumpLayer(
      String displayName,
      BlockState endState,
      BlockState middleState,
      int minLayer,
      int maxLayer,
      int minHeight,
      boolean hasColorVariants,
      List<RegistryObject<JumpBlock>> colorVariants) {
    this.displayName = displayName;
    this.endState = endState;
    this.betweenState = middleState;
    this.middleState = middleState;
    this.minLayer = minLayer;
    this.maxLayer = maxLayer;
    this.minHeight = minHeight;
    this.hasColorVariants = hasColorVariants;
    this.colorVariants = colorVariants;
  }

  /**
   * Instantiates a new Jump layer.
   *
   * @param displayName the display name
   * @param endState the end state
   * @param betweenState the between state
   * @param middleState the middle state
   * @param minLayer the min layer
   * @param maxLayer the max layer
   * @param minHeight the min height
   * @param hasColorVariants the has color variants
   * @param colorVariants the color variants
   */
  JumpLayer(
      String displayName,
      BlockState endState,
      BlockState betweenState,
      BlockState middleState,
      int minLayer,
      int maxLayer,
      int minHeight,
      boolean hasColorVariants,
      List<RegistryObject<JumpBlock>> colorVariants) {
    this.displayName = displayName;
    this.endState = endState;
    this.betweenState = betweenState;
    this.middleState = middleState;
    this.minLayer = minLayer;
    this.maxLayer = maxLayer;
    this.minHeight = minHeight;
    this.hasColorVariants = hasColorVariants;
    this.colorVariants = colorVariants;
  }

  /**
   * Gets end state.
   *
   * @param color the color
   * @return the end state
   */
  public BlockState getEndState(int color) {
    if (this.hasColorVariants) {
      return this.colorVariants
          .get(color)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.LEFT);
    }
    return endState;
  }

  /**
   * Gets between state.
   *
   * @param color the color
   * @return the between state
   */
  public BlockState getBetweenState(int color) {
    if (this.hasColorVariants) {
      return this.colorVariants
          .get(color)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.RIGHT);
    }
    return betweenState;
  }

  /**
   * Gets middle state.
   *
   * @param color the color
   * @return the middle state
   */
  public BlockState getMiddleState(int color) {
    if (this.hasColorVariants) {
      return this.colorVariants
          .get(color)
          .get()
          .defaultBlockState()
          .setValue(JumpBlock.JUMP_PIECE, TripleBlockSide.MIDDLE);
    }
    return middleState;
  }

  /**
   * Gets min layer.
   *
   * @return the min layer
   */
  public int getMinLayer() {
    return minLayer;
  }

  /**
   * Gets max layer.
   *
   * @return the max layer
   */
  public int getMaxLayer() {
    return maxLayer;
  }

  /**
   * Gets min height.
   *
   * @return the min height
   */
  public int getMinHeight() {
    return minHeight;
  }

  /**
   * Has color variants boolean.
   *
   * @return the boolean
   */
  public boolean hasColorVariants() {
    return this.hasColorVariants;
  }

  /**
   * Gets display name.
   *
   * @return the display name
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Gets color variants.
   *
   * @return the color variants
   */
  public List<RegistryObject<JumpBlock>> getColorVariants() {
    return this.colorVariants;
  }

  /**
   * Test for rail boolean.
   *
   * @param layer the layer
   * @return the boolean
   */
  public static boolean testForRail(JumpLayer layer) {
    return layer == RAIL || layer == PLANK || layer == PLANK_FANCY; // || layer == SWEDISH_RAILS;
  }

  /**
   * Test for none boolean.
   *
   * @param layer the layer
   * @return the boolean
   */
  public static boolean testForNone(JumpLayer layer) {
    return layer == NONE;
  }
}
