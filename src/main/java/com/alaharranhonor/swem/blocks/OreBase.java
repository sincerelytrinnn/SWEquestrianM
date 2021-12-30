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
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class OreBase extends OreBlock {
    public OreBase(Properties p_i48357_1_) {
        super(p_i48357_1_);
    }

    @Override
    public int xpOnDrop(Random p_220281_1_) {
        if (this == SWEMBlocks.CANTAZARITE_ORE.get()) {
            return MathHelper.nextInt(p_220281_1_, 2, 5);
        } else {
            return this == SWEMBlocks.AMETHYST_ORE.get() ? MathHelper.nextInt(p_220281_1_, 3, 7) : 0;
        }
    }

    public void spawnAfterBreak(BlockState pState, ServerWorld pLevel, BlockPos pPos, ItemStack pStack) {
        super.spawnAfterBreak(pState, pLevel, pPos, pStack);
    }

    @Override
    public int getExpDrop (BlockState state, net.minecraft.world.IWorldReader reader, BlockPos pos,int fortune, int silktouch){
        return silktouch == 0 ? this.xpOnDrop(RANDOM) : 0;
    }
}

