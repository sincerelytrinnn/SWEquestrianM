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
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.Nullable;

public class EnglishFenceBlock extends HorizontalBlock {

    // Left = arc.
    // Right = extension.
    public static final EnumProperty<SWEMBlockStateProperties.DoubleBlockSide> PART =
            SWEMBlockStateProperties.D_SIDE;

    /**
     * Instantiates a new English fence block.
     *
     * @param p_i48440_1_ the p i 48440 1
     */
    public EnglishFenceBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(PART, SWEMBlockStateProperties.DoubleBlockSide.LEFT));
    }

    @Override
    public VoxelShape getShape(
            BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.getValue(FACING).getAxis() == Direction.Axis.X) {
            if (state.getValue(PART) == SWEMBlockStateProperties.DoubleBlockSide.RIGHT)
                return VoxelShapes.box(0.375, 0.75, 0.01, 0.625, 0.99, 0.99); // Beam facing East/West

            return VoxelShapes.box(0.375, 0.01, 0.01, 0.625, 0.99, 0.99); // Arch facing East/West
        } else {
            if (state.getValue(PART) == SWEMBlockStateProperties.DoubleBlockSide.RIGHT)
                return VoxelShapes.box(0.01, 0.75, 0.375, 0.99, 0.99, 0.625); // Beam facing North/south

            return VoxelShapes.box(0.01, 0.01, 0.375, 0.99, 0.99, 0.625); // Arch facing North/South
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        return this.defaultBlockState().setValue(FACING, p_196258_1_.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(FACING, PART);
    }
}
