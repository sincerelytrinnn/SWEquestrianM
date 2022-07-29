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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SixWayBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SlowFeederBlock extends Block {

    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty WEST = SixWayBlock.WEST;

    public static final IntegerProperty LEVEL = SWEMBlockStateProperties.LEVEL_0_2;
    private final DyeColor colour;

    /**
     * Instantiates a new Slow feeder block.
     *
     * @param properties the properties
     * @param colour     the colour
     */
    public SlowFeederBlock(Properties properties, DyeColor colour) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(NORTH, Boolean.valueOf(false))
                        .setValue(EAST, Boolean.valueOf(false))
                        .setValue(SOUTH, Boolean.valueOf(false))
                        .setValue(WEST, Boolean.valueOf(false))
                        .setValue(LEVEL, 0));
        this.colour = colour;
    }

    /**
     * Gets colour.
     *
     * @return the colour
     */
    public DyeColor getColour() {
        return this.colour;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader iblockreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();
        BlockState blockstate = iblockreader.getBlockState(blockpos1);
        BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
        BlockState blockstate2 = iblockreader.getBlockState(blockpos3);
        BlockState blockstate3 = iblockreader.getBlockState(blockpos4);
        return this.defaultBlockState()
                .setValue(NORTH, this.isBlock(blockstate))
                .setValue(EAST, this.isBlock(blockstate1))
                .setValue(SOUTH, this.isBlock(blockstate2))
                .setValue(WEST, this.isBlock(blockstate3));
    }

    @Override
    public ActionResultType use(
            BlockState state,
            World worldIn,
            BlockPos pos,
            PlayerEntity player,
            Hand handIn,
            BlockRayTraceResult hit) {
        ItemStack itemstack = player.getItemInHand(handIn);
        if (itemstack.isEmpty()) {
            return ActionResultType.PASS;
        } else {
            int level_swem = state.getValue(LEVEL);
            Item item = itemstack.getItem();
            if (item == SWEMBlocks.QUALITY_BALE.get().asItem()) {
                if (level_swem == 0) {
                    this.setHayLevel(worldIn, pos, state, 2);
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    return ActionResultType.sidedSuccess(worldIn.isClientSide);
                } else if (level_swem == 1) {
                    this.setHayLevel(worldIn, pos, state, level_swem + 1);
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                        player.addItem(new ItemStack(SWEMBlocks.QUALITY_BALE_SLAB.get()));
                    }
                    return ActionResultType.sidedSuccess(worldIn.isClientSide);
                } else {
                    return ActionResultType.PASS;
                }

            } else if (item == SWEMBlocks.QUALITY_BALE_SLAB.get().asItem()) {
                if (level_swem < 2) {
                    this.setHayLevel(worldIn, pos, state, level_swem + 1);
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    return ActionResultType.sidedSuccess(worldIn.isClientSide);
                } else {
                    return ActionResultType.PASS;
                }
            } else {
                return ActionResultType.PASS;
            }
        }
    }

    /**
     * Is block boolean.
     *
     * @param state the state
     * @return the boolean
     */
    private Boolean isBlock(BlockState state) {
        if (!state.equals(Blocks.AIR.defaultBlockState())
                && !state.equals(Blocks.CAVE_AIR.defaultBlockState())
                && !state.equals(Blocks.VOID_AIR.defaultBlockState())) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, LEVEL);
    }

    @Override
    public VoxelShape getShape(
            BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.box(0, 0, 0, 15.99, 15.99, 15.99);
    }

    @Override
    public VoxelShape getCollisionShape(
            BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
        return VoxelShapes.box(0.01, 0.01, 0.01, 0.99, 1.5, 0.99);
    }

    /**
     * Is feedable boolean.
     *
     * @param worldIn the world in
     * @param state   the state
     * @return the boolean
     */
    public boolean isFeedable(World worldIn, BlockState state) {
        int level = state.getValue(LEVEL);

        return level > 0;
    }

    /**
     * Sets hay level.
     *
     * @param worldIn the world in
     * @param pos     the pos
     * @param state   the state
     * @param level   the level
     */
    public void setHayLevel(World worldIn, BlockPos pos, BlockState state, int level) {
        worldIn.setBlock(pos, state.setValue(LEVEL, Integer.valueOf(MathHelper.clamp(level, 0, 2))), 3);
    }

    /**
     * Eat.
     *
     * @param worldIn the world in
     * @param pos     the pos
     * @param state   the state
     */
    public void eat(World worldIn, BlockPos pos, BlockState state) {
        int level = state.getValue(LEVEL);

        if (level > 0) {
            worldIn.setBlock(
                    pos, state.setValue(LEVEL, Integer.valueOf(MathHelper.clamp(level - 1, 0, 2))), 3);
        }
    }
}
