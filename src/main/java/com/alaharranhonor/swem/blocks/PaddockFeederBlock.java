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

import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class PaddockFeederBlock extends Block {
  public static final DirectionProperty FACING = BlockStateProperties.FACING;
  public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
  public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_CAULDRON;
  public static final EnumProperty<SWEMBlockStateProperties.DoubleBlockSide> SIDE =
      SWEMBlockStateProperties.D_SIDE;

  /**
   * Instantiates a new Paddock feeder block.
   *
   * @param builder the builder
   */
  public PaddockFeederBlock(AbstractBlock.Properties builder) {
    super(builder);
    this.registerDefaultState(
        this.stateDefinition
            .any()
            .setValue(FACING, Direction.NORTH)
            .setValue(HALF, DoubleBlockHalf.LOWER));
  }

  public VoxelShape getShape(
      BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    Direction direction = state.getValue(FACING);
    DoubleBlockHalf half = state.getValue(HALF);
    SWEMBlockStateProperties.DoubleBlockSide side = state.getValue(SIDE);

    switch (direction) {
      case NORTH:
        {
          if (side == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
            return Block.box(0, 0, 1, 14, 16, 15);
          } else {
            return Block.box(2, 0, 1, 16, 16, 15);
          }
        }
      case EAST:
        {
          if (side == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
            return Block.box(1, 0, 0, 15, 16, 14);
          } else {
            return Block.box(1, 0, 2, 15, 16, 16);
          }
        }
      case SOUTH:
        {
          if (side == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
            return Block.box(2, 0, 1, 16, 16, 15);
          } else {
            return Block.box(0, 0, 1, 14, 16, 15);
          }
        }
      case WEST:
        {
          if (side == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
            return Block.box(1, 0, 2, 15, 16, 16);
          } else {
            return Block.box(1, 0, 0, 15, 16, 14);
          }
        }
    }

    return VoxelShapes.block();
  }

  @Nullable
  public BlockState getStateForPlacement(BlockItemUseContext context) {
    Direction direction = context.getHorizontalDirection();

    return this.defaultBlockState()
        .setValue(FACING, direction.getOpposite())
        .setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.LEFT);
  }

  @Override
  public void playerWillDestroy(
      World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
    super.playerWillDestroy(worldIn, pos, state, player);
    if (!worldIn.isClientSide) {

      BlockState state1 = state;
      if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
        state1 = worldIn.getBlockState(pos.above());
      }
      int level = state1.getValue(LEVEL);
      if (level > 0) {
        ItemEntity itemEntity =
            new ItemEntity(
                worldIn,
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                new ItemStack(SWEMBlocks.QUALITY_BALE_ITEM.get(), level));
        worldIn.addFreshEntity(itemEntity);
      }
      this.getAllParts(state, pos, worldIn).stream()
          .forEach(
              (blockPos) -> {
                worldIn.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 35);
              });
    }

    if (!player.isCreative()) dropResources(state, worldIn, pos);
  }

  /** Called by ItemBlocks after a block is set in the world, to allow post-place logic */
  public void setPlacedBy(
      World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {

    // Z-Axis is Norht/South
    // X-Axis is East/West
    worldIn.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    if (placer.getDirection().getAxis() == Direction.Axis.Z) {
      switch (placer.getDirection()) {
        case NORTH:
          {
            worldIn.setBlock(
                pos.east(),
                state.setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT),
                3);
            worldIn.setBlock(
                pos.east().above(),
                state
                    .setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT)
                    .setValue(HALF, DoubleBlockHalf.UPPER),
                3);
            break;
          }
        case SOUTH:
          {
            worldIn.setBlock(
                pos.west(),
                state.setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT),
                3);
            worldIn.setBlock(
                pos.west().above(),
                state
                    .setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT)
                    .setValue(HALF, DoubleBlockHalf.UPPER),
                3);
            break;
          }
      }
    } else if (placer.getDirection().getAxis() == Direction.Axis.X) {
      switch (placer.getDirection()) {
        case EAST:
          {
            worldIn.setBlock(
                pos.south(),
                state.setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT),
                3);
            worldIn.setBlock(
                pos.south().above(),
                state
                    .setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT)
                    .setValue(HALF, DoubleBlockHalf.UPPER),
                3);
            break;
          }
        case WEST:
          {
            worldIn.setBlock(
                pos.north(),
                state.setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT),
                3);
            worldIn.setBlock(
                pos.north().above(),
                state
                    .setValue(SIDE, SWEMBlockStateProperties.DoubleBlockSide.RIGHT)
                    .setValue(HALF, DoubleBlockHalf.UPPER),
                3);
            break;
          }
      }
    }
  }

  public ActionResultType use(
      BlockState state,
      World worldIn,
      BlockPos pos,
      PlayerEntity player,
      Hand handIn,
      BlockRayTraceResult hit) {
    ItemStack stack = player.getItemInHand(handIn);
    if (stack.isEmpty()) {
      return ActionResultType.PASS;
    } else {
      BlockState state1 = state;
      BlockPos pos1 = pos;
      if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
        state1 = worldIn.getBlockState(pos.above());
        pos1 = pos.above();
      }

      int level = state1.getValue(LEVEL);
      BlockState stateOther;
      BlockPos posOther;

      Direction facing = state.getValue(FACING);

      if (state.getValue(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
        posOther = pos1.relative(facing.getCounterClockWise());
        stateOther = worldIn.getBlockState(posOther);
      } else {
        posOther = pos1.relative(facing.getClockWise());
        stateOther = worldIn.getBlockState(posOther);
      }

      Item item = stack.getItem();
      if (item == SWEMBlocks.QUALITY_BALE_ITEM.get()) {
        level++;
        worldIn.setBlock(pos1, state1.setValue(LEVEL, MathHelper.clamp(level, 0, 3)), 3);
        worldIn.setBlock(posOther, stateOther.setValue(LEVEL, MathHelper.clamp(level, 0, 3)), 3);

        if (!player.isCreative()) stack.shrink(1);

        return ActionResultType.CONSUME;
      } else {
        return ActionResultType.FAIL;
      }
    }
  }

  /**
   * Gets all parts.
   *
   * @param state the state
   * @param pos the pos
   * @param worldIn the world in
   * @return the all parts
   */
  public ArrayList<BlockPos> getAllParts(BlockState state, BlockPos pos, World worldIn) {
    Direction direction = state.getValue(FACING);
    ArrayList<BlockPos> positions = new ArrayList<>();
    if (state.getValue(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.LEFT) {
      // Check right and above.
      if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
        positions.add(pos);
        positions.add(pos.above());
        positions.add(pos.relative(direction.getCounterClockWise()));
        positions.add(pos.relative(direction.getCounterClockWise()).above());
      } else {
        positions.add(pos.below());
        positions.add(pos);
        positions.add(pos.relative(direction.getCounterClockWise()).below());
        positions.add(pos.relative(direction.getCounterClockWise()));
      }
    } else if (state.getValue(SIDE) == SWEMBlockStateProperties.DoubleBlockSide.RIGHT) {
      // Check Left and above.
      if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
        positions.add(pos);
        positions.add(pos.above());
        positions.add(pos.relative(direction.getClockWise()));
        positions.add(pos.relative(direction.getClockWise()).above());
      } else {
        positions.add(pos.below());
        positions.add(pos);
        positions.add(pos.relative(direction.getClockWise()).below());
        positions.add(pos.relative(direction.getClockWise()));
      }
    }

    return positions;
  }

  public void neighborChanged(
      BlockState state,
      World worldIn,
      BlockPos pos,
      Block blockIn,
      BlockPos fromPos,
      boolean isMoving) {}

  public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
    return this.getAllParts(state, pos, (World) worldIn).stream()
        .allMatch(
            (pos1) -> {
              BlockState state1 = worldIn.getBlockState(pos1);
              return state1 == Blocks.AIR.defaultBlockState();
            });
  }

  @Override
  public PushReaction getPistonPushReaction(BlockState pState) {
    return PushReaction.BLOCK;
  }

  public BlockState rotate(BlockState state, Rotation rot) {
    return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
  }

  public BlockState mirror(BlockState state, Mirror mirrorIn) {
    return mirrorIn == Mirror.NONE
        ? state
        : state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
  }

  protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(HALF, FACING, SIDE, LEVEL);
  }

  /**
   * Eat.
   *
   * @param level the level
   * @param foundFood the found food
   * @param blockState the block state
   */
  public void eat(World level, BlockPos foundFood, BlockState blockState) {
    getAllParts(blockState, foundFood, level).stream()
        .forEach(
            (pos) -> {
              BlockState feederState = level.getBlockState(pos);
              if (feederState.getBlock() instanceof PaddockFeederBlock) {
                int value = feederState.getValue(LEVEL);
                level.setBlock(pos, feederState.setValue(LEVEL, Math.max(0, --value)), 3);
              }
            });
  }
}
