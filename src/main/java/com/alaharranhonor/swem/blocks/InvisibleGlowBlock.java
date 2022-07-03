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
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class InvisibleGlowBlock extends Block {
    /**
     * Instantiates a new Invisible glow block.
     *
     * @param properties the properties
     */
    public InvisibleGlowBlock(Properties properties) {
        super(properties);
    }

    /**
     * Get a light value for this block, taking into account the given state and coordinates, normal
     * ranges are between 0 and 15
     *
     * @param state
     * @param world
     * @param pos
     * @return The light value
     */
    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 8;
    }

    @Override
    public VoxelShape getShape(
            BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockItemUseContext useContext) {
        return true;
    }
}
