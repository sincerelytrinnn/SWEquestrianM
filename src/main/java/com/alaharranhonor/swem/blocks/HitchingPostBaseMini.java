package com.alaharranhonor.swem.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.LeadItem;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class HitchingPostBaseMini extends Block {

    private final HitchingPostType type;
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;


    public HitchingPostBaseMini(HitchingPostType type, Properties properties) {
        super(properties);

        this.type = type;
        this.setDefaultState(
                this.stateContainer.getBaseState()
                        .with(FACING, Direction.NORTH)
        );

    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = this.type.getVoxelShape(state.get(FACING));
        return Block.makeCuboidShape(6, 0, 6, 10, 14, 10);
        }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }


    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            ItemStack itemstack = player.getHeldItem(handIn);
            return itemstack.getItem() == Items.LEAD ? ActionResultType.SUCCESS : ActionResultType.PASS;
        } else {
            return LeadItem.bindPlayerMobs(player, worldIn, pos);
        }
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     *
     * @param worldIn
     * @param pos
     * @param state
     * @param placer
     * @param stack
     */
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isRemote) {
            BlockPos blockpos = pos.offset(Direction.UP);
            state.updateNeighbours(worldIn, pos, 3);
        }
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     *
     * @param stateIn
     * @param facing
     * @param facingState
     * @param worldIn
     * @param currentPos
     * @param facingPos
     */
    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public enum HitchingPostType {

        WESTERN(
                Stream.of(
                        Block.makeCuboidShape(6, 0, 6, 10, 13, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.makeCuboidShape(6, 0, 6, 10, 13, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.makeCuboidShape(6, 0, 6, 10, 13, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.makeCuboidShape(6, 0, 6, 10, 13, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()
        ),

        ENGLISH(
                Stream.of(
                        Block.makeCuboidShape(6, 0, 6, 10, 14, 10)
                        ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.makeCuboidShape(6, 0, 6, 10, 14, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.makeCuboidShape(6, 0, 6, 10, 14, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.makeCuboidShape(6, 0, 6, 10, 14, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()

        ),

        PASTURE(
                Stream.of(
                        Block.makeCuboidShape(6, 0, 6, 10, 16, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.makeCuboidShape(6, 0, 6, 10, 16, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.makeCuboidShape(6, 0, 6, 10, 16, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.makeCuboidShape(6, 0, 6, 10, 16, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()
        );


        private final VoxelShape north;
        private final VoxelShape east;
        private final VoxelShape south;
        private final VoxelShape west;
        HitchingPostType(VoxelShape north, VoxelShape east, VoxelShape south, VoxelShape west) {
            this.north = north;
            this.east = east;
            this.south = south;
            this.west = west;
        }

        public VoxelShape getVoxelShape(Direction facing) {
            switch (facing) {
                case EAST:
                    return this.east;
                case SOUTH:
                    return this.south;
                case WEST:
                    return this.west;
                default:
                    return this.north;
            }
        }
    }





}
