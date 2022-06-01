package com.alaharranhonor.swem.blocks;
/*
 * All Rights Reserved
 *
 * Copyright (c) 2022, AlaharranHonor, Legenden.
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
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class LeadAnchorBlock extends HorizontalFaceBlock {
  public LeadAnchorBlock(Properties p_i48440_1_) {
    super(p_i48440_1_);
    this.registerDefaultState(
        this.stateDefinition
            .any()
            .setValue(FACING, Direction.NORTH)
            .setValue(FACE, AttachFace.WALL));
  }

  @Override
  public VoxelShape getShape(
      BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
    if (pState.getValue(FACE) == AttachFace.FLOOR) {
      return VoxelShapes.box(0.375, 0.001, 0.375, 0.625, 0.4, 0.625);
    } else if (pState.getValue(FACE) == AttachFace.CEILING) {
      return VoxelShapes.box(0.375, 0.6, 0.375, 0.625, 0.999, 0.625);
    } else {
      if (pState.getValue(LeadAnchorBlock.FACING) == Direction.SOUTH) {
        return VoxelShapes.box(0.34375, 0.25, 0.001, 0.65625, 0.625, 0.3125);
      } else if (pState.getValue(LeadAnchorBlock.FACING) == Direction.NORTH) {
        return VoxelShapes.box(0.34375, 0.25, 0.6875, 0.65625, 0.625, 0.999);
      } else if (pState.getValue(LeadAnchorBlock.FACING) == Direction.EAST) {
        return VoxelShapes.box(0.001, 0.25, 0.34375, 0.3125, 0.625, 0.65625);
      } else if (pState.getValue(LeadAnchorBlock.FACING) == Direction.WEST) {
        return VoxelShapes.box(0.6875, 0.25, 0.34375, 0.999, 0.625, 0.65625);
      }
    }
    return VoxelShapes.box(0.375, 0.6, 0.375, 0.625, 0.999, 0.625);
  }

  @Override
  protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
    pBuilder.add(FACING, FACE);
  }
}
