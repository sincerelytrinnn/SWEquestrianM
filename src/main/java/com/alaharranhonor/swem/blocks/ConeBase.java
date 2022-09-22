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

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.stream.Stream;

public class ConeBase extends Block {

    private static final VoxelShape voxelShape =
            Stream.of(
                            Block.box(6, 13, 6, 10, 19, 10),
                            Block.box(5, 7, 5, 11, 13, 11),
                            Block.box(4, 1, 4, 12, 7, 12),
                            Block.box(2, 0, 2, 14, 1, 14),
                            Block.box(7, 19, 7, 9, 24, 9))
                    .reduce(
                            (v1, v2) -> {
                                return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
                            })
                    .get();

    /**
     * Instantiates a new Cone base.
     */
    public ConeBase() {
        super(
                AbstractBlock.Properties.of(Material.METAL)
                        .strength(0.5f, 0.5f)
                        .sound(SoundType.SCAFFOLDING)
                        .harvestLevel(0));
    }

    @Override
    public VoxelShape getShape(
            BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return voxelShape;
    }

    @Override
    public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 0.6f;
    }
}
