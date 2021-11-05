package com.alaharranhonor.swem.blocks;

import javax.annotation.Nullable;

import com.alaharranhonor.swem.tools.PitchforkTool;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.*;

import net.minecraft.block.AbstractBlock;

public class Shavings extends Block {
    public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;
    public static final VoxelShape[] SHAPES = new VoxelShape[]{VoxelShapes.empty(), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public Shavings(AbstractBlock.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, Integer.valueOf(1)));
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        switch(type) {
            case LAND:
                return state.getValue(LAYERS) < 5;
            case WATER:
                return false;
            case AIR:
                return false;
            default:
                return false;
        }
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.getValue(LAYERS)];
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.getValue(LAYERS) - 1];
    }

    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos.below());
        Block block = blockstate.getBlock();
        if (block != Blocks.ICE && block != Blocks.PACKED_ICE && block != Blocks.BARRIER) {
            if (block != Blocks.HONEY_BLOCK && block != Blocks.SOUL_SAND) {
                return Block.isFaceFull(blockstate.getCollisionShape(worldIn, pos.below()), Direction.UP) || block == this && blockstate.getValue(LAYERS) == 8;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public boolean canBeReplaced(BlockState state, BlockItemUseContext useContext) {
        int i = state.getValue(LAYERS);
        if (this.getBlock() == SWEMBlocks.SOILED_SHAVINGS.get()) return false;
        if (useContext.getItemInHand().getItem() == this.asItem() && i < 8) {
            if (useContext.replacingClickedOnBlock()) {
                return useContext.getClickedFace() == Direction.UP;
            } else {
                return true;
            }
        } else {
            return i == 1;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        if (blockstate.getBlock() == this) {
            int i = blockstate.getValue(LAYERS);
            return blockstate.setValue(LAYERS, Integer.valueOf(Math.min(8, i + 1)));
        } else {
            return super.getStateForPlacement(context);
        }
    }

    public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }

    @Override
    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.playerWillDestroy(worldIn, pos, state, player);
        if (player.abilities.instabuild) return;
        if (player.getMainHandItem().getItem() instanceof PitchforkTool) {
            int damage = state.getValue(LAYERS);
            ItemStack shavingsItem = new ItemStack(state.getBlock().asItem());
            shavingsItem.hurtAndBreak(8 - damage, player, playerEntity -> {});
            ItemEntity shavingEntity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), shavingsItem);
            worldIn.addFreshEntity(shavingEntity);
        } else {
            ItemStack shavingsItem = new ItemStack(state.getBlock().asItem());
            shavingsItem.hurtAndBreak(7, player, playerEntity -> {});
            ItemEntity shavingEntity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), shavingsItem);
            worldIn.addFreshEntity(shavingEntity);
        }
    }


}