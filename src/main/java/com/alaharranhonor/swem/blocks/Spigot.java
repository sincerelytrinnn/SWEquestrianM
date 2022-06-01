package com.alaharranhonor.swem.blocks;

import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class Spigot extends HorizontalBlock {

  public static final DirectionProperty FACING = HorizontalBlock.FACING;

  public static final VoxelShape SHAPE_N =
      Stream.of(Block.box(6, 8, 0, 10, 12, 5))
          .reduce(
              (v1, v2) -> {
                return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
              })
          .get();
  public static final VoxelShape SHAPE_E =
      Stream.of(Block.box(11, 8, 6, 16, 12, 10))
          .reduce(
              (v1, v2) -> {
                return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
              })
          .get();
  public static final VoxelShape SHAPE_S =
      Stream.of(Block.box(6, 8, 11, 10, 12, 16))
          .reduce(
              (v1, v2) -> {
                return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
              })
          .get();
  public static final VoxelShape SHAPE_W =
      Stream.of(Block.box(0, 8, 6, 5, 12, 10))
          .reduce(
              (v1, v2) -> {
                return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
              })
          .get();

  /**
   * Instantiates a new Spigot.
   *
   * @param properties the properties
   */
  public Spigot(AbstractBlock.Properties properties) {
    super(properties);
    this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
  }

  @Override
  public VoxelShape getShape(
      BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    switch (state.getValue(FACING)) {
      case NORTH:
        {
          return SHAPE_N;
        }
      case EAST:
        {
          return SHAPE_E;
        }
      case SOUTH:
        {
          return SHAPE_S;
        }
      case WEST:
        {
          return SHAPE_W;
        }
      default:
        {
          return SHAPE_N;
        }
    }
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockItemUseContext context) {
    return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
  }

  @Override
  protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(FACING);
  }
}
