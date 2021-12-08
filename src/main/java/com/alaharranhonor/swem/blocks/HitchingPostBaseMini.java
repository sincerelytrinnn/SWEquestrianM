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

import net.minecraft.block.AbstractBlock.Properties;

public class HitchingPostBaseMini extends Block {

    private final HitchingPostType type;
    public static final DirectionProperty FACING = HorizontalBlock.FACING;


    public HitchingPostBaseMini(HitchingPostType type, Properties properties) {
        super(properties);

        this.type = type;
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
        );

    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = this.type.getVoxelShape(state.getValue(FACING));
        return Block.box(6, 0, 6, 10, 14, 10);
        }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }


    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isClientSide) {
            ItemStack itemstack = player.getItemInHand(handIn);
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
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isClientSide) {
            BlockPos blockpos = pos.relative(Direction.UP);
            state.updateNeighbourShapes(worldIn, pos, 3);
        }
    }

    public enum HitchingPostType {

        WESTERN(
                Stream.of(
                        Block.box(6, 0, 6, 10, 13, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.box(6, 0, 6, 10, 13, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.box(6, 0, 6, 10, 13, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.box(6, 0, 6, 10, 13, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get()
        ),

        ENGLISH(
                Stream.of(
                        Block.box(6, 0, 6, 10, 14, 10)
                        ).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.box(6, 0, 6, 10, 14, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.box(6, 0, 6, 10, 14, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.box(6, 0, 6, 10, 14, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get()

        ),

        PASTURE(
                Stream.of(
                        Block.box(6, 0, 6, 10, 16, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.box(6, 0, 6, 10, 16, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.box(6, 0, 6, 10, 16, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get(),
                Stream.of(
                        Block.box(6, 0, 6, 10, 16, 10)
                ).reduce((v1, v2) -> {return VoxelShapes.join(v1, v2, IBooleanFunction.OR);}).get()
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
