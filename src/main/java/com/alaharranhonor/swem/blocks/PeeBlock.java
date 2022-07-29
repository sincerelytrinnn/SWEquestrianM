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
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class PeeBlock extends Block {
    /**
     * Instantiates a new Pee block.
     *
     * @param properties the properties
     */
    public PeeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(
            BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.box(0, 0.05, 0, 0, 0.055, 0);
    }

    @Override
    public BlockState updateShape(
            BlockState stateIn,
            Direction facing,
            BlockState facingState,
            IWorld worldIn,
            BlockPos currentPos,
            BlockPos facingPos) {
        if (facing == Direction.DOWN) {
            if (!facingState.canOcclude()) {
                return Blocks.AIR.defaultBlockState();
            }
        }
        return stateIn;
    }

    @Override
    public boolean canBeReplaced(BlockState pState, BlockItemUseContext pUseContext) {
        return pUseContext.getItemInHand().getItem() instanceof ShavingsItem
                || super.canBeReplaced(pState, pUseContext);
    }

    @Override
    public void onRemove(
            BlockState pState, World pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        if (pNewState.getBlock() instanceof Shavings) {
            pLevel.setBlock(pPos, SWEMBlocks.SOILED_SHAVINGS.get().defaultBlockState(), 3);
        }
    }
}
