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

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
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

public class HitchingPostBase extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty CUSTOM_LEAD = BlockStateProperties.ENABLED;
    private final HitchingPostType type;

    /**
     * Instantiates a new Hitching post base.
     *
     * @param type       the type
     * @param properties the properties
     */
    public HitchingPostBase(HitchingPostType type, Properties properties) {
        super(properties);

        this.type = type;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.LOWER).setValue(CUSTOM_LEAD, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = VoxelShapes.join(Block.box(6, 16, 6, 10, 30, 10), Block.box(6, 0, 6, 10, 16, 10), IBooleanFunction.OR);
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return shape.move(0.0d, -1.0d, 0.0d);
        } else {
            return shape;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockpos = context.getClickedPos();
        BlockPos blockpos1 = blockpos.above();
        return context.getLevel().getBlockState(blockpos1).canBeReplaced(context) ? this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()) : null;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(HALF);
        builder.add(CUSTOM_LEAD);
    }

    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getItemInHand(handIn);
        if (itemstack.getItem() == Items.SHEARS) {
            itemstack.hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(handIn));

            // Destroy both parts of the barrel.
            worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
                worldIn.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 3);
            } else if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
                worldIn.setBlock(pos.below(), Blocks.AIR.defaultBlockState(), 3);
            }

            Item miniHitching = SWEMBlocks.PASTURE_HITCHING_POST_MINI_ITEM.get();
            switch (this.type) {
                case ENGLISH: {
                    miniHitching = SWEMBlocks.ENGLISH_HITCHING_POST_MINI_ITEM.get();
                    break;
                }
                case WESTERN: {
                    miniHitching = SWEMBlocks.WESTERN_HITCHING_POST_MINI_ITEM.get();
                    break;
                }
            }

            ItemEntity entity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(miniHitching));
            ItemEntity entity1 = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(miniHitching));

            worldIn.addFreshEntity(entity);
            worldIn.addFreshEntity(entity1);
            return ActionResultType.SUCCESS;
        }
        if (worldIn.isClientSide) {
            return itemstack.getItem() == Items.LEAD ? ActionResultType.SUCCESS : ActionResultType.PASS;
        } else {
            BlockPos pPos = state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos.above() : pos;
            LeashKnotEntity leashknotentity = null;
            boolean flag = false;
            double d0 = 7.0D;
            int i = pPos.getX();
            int j = pPos.getY();
            int k = pPos.getZ();

            for (SWEMHorseEntityBase mobentity : worldIn.getEntitiesOfClass(SWEMHorseEntityBase.class, new AxisAlignedBB((double) i - 7.0D, (double) j - 7.0D, (double) k - 7.0D, (double) i + 7.0D, (double) j + 7.0D, (double) k + 7.0D))) {
                if (mobentity.getLeashHolder() == player) {
                    if (leashknotentity == null) {
                        leashknotentity = LeashKnotEntity.getOrCreateKnot(worldIn, pPos);
                    }

                    mobentity.setLeashedTo(leashknotentity, true);
                    flag = true;
                }
            }
            if (leashknotentity != null) {
                leashknotentity.setInvisible(true);
                leashknotentity.setPos(leashknotentity.getX(), leashknotentity.getY() - 0.3125, leashknotentity.getZ());
                BlockState toChange = worldIn.getBlockState(pPos);
                worldIn.setBlock(pPos, toChange.setValue(CUSTOM_LEAD, true), 3);
            }
            return flag ? ActionResultType.SUCCESS : ActionResultType.PASS;
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
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!worldIn.isClientSide) {
            BlockPos blockpos = pos.relative(Direction.UP);
            worldIn.setBlock(blockpos, state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
            worldIn.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(worldIn, pos, 3);
        }
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     *
     * @param pState
     * @param pFacing
     * @param pFacingState
     * @param pLevel
     * @param pCurrentPos
     * @param pFacingPos
     */
    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing == getNeighbourDirection(pState.getValue(HALF))) {
            return pFacingState.is(this) && pFacingState.getValue(HALF) != pState.getValue(HALF) ? pState : Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    private static Direction getNeighbourDirection(DoubleBlockHalf part) {
        return part == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN;
    }

    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
     * this block
     *
     * @param pLevel
     * @param pPos
     * @param pState
     * @param pPlayer
     */
    @Override
    public void playerWillDestroy(World pLevel, BlockPos pPos, BlockState pState, PlayerEntity pPlayer) {
        if (!pLevel.isClientSide()) {
            if (pPlayer.isCreative()) {
                preventCreativeDropFromOtherHalf(pLevel, pPos, pState, pPlayer);
            } else {
                dropResources(pState, pLevel, pPos, null, pPlayer, pPlayer.getItemInHand(pPlayer.getUsedItemHand()));
            }
        }


        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    /**
     * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
     * Block.removedByPlayer
     *
     * @param pLevel
     * @param pPlayer
     * @param pPos
     * @param pState
     * @param pTe
     * @param pStack
     */
    @Override
    public void playerDestroy(World pLevel, PlayerEntity pPlayer, BlockPos pPos, BlockState pState, @org.jetbrains.annotations.Nullable TileEntity pTe, ItemStack pStack) {
        super.playerDestroy(pLevel, pPlayer, pPos, Blocks.AIR.defaultBlockState(), pTe, pStack);
    }

    protected void preventCreativeDropFromOtherHalf(World pLevel, BlockPos pPos, BlockState pState, PlayerEntity pPlayer) {
        DoubleBlockHalf part = pState.getValue(HALF);
        if (part == DoubleBlockHalf.UPPER) {
            BlockPos blockpos = pPos.relative(getNeighbourDirection(part));
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (blockstate.getBlock() == this && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
                pLevel.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                pLevel.levelEvent(pPlayer, 2001, blockpos, Block.getId(blockstate));
            }
        }
    }

    public enum HitchingPostType {
        WESTERN(Block.box(5, 0, 9, 11, 32, 15), Block.box(1, 0, 5, 7, 32, 11), Block.box(5, 0, 1, 11, 32, 7), Block.box(9, 0, 5, 15, 32, 11)),

        ENGLISH(Block.box(5, 0, 8, 11, 32, 14), Block.box(2, 0, 5, 8, 32, 11), Block.box(5, 0, 2, 11, 32, 8), Block.box(8, 0, 5, 14, 32, 11)),

        PASTURE(Block.box(6, 0, 10, 10, 32, 14), Block.box(2, 0, 6, 6, 32, 10), Block.box(6, 0, 2, 10, 32, 6), Block.box(10, 0, 6, 14, 32, 10));

        private final VoxelShape north;
        private final VoxelShape east;
        private final VoxelShape south;
        private final VoxelShape west;

        /**
         * Instantiates a new Hitching post type.
         *
         * @param north the north
         * @param east  the east
         * @param south the south
         * @param west  the west
         */
        HitchingPostType(VoxelShape north, VoxelShape east, VoxelShape south, VoxelShape west) {
            this.north = north;
            this.east = east;
            this.south = south;
            this.west = west;
        }

        /**
         * Gets voxel shape.
         *
         * @param facing the facing
         * @return the voxel shape
         */
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
