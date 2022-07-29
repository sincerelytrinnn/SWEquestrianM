package com.alaharranhonor.swem.tools;

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

import com.alaharranhonor.swem.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class FenceToolItem extends Item {
    /**
     * Instantiates a new Fence tool item.
     *
     * @param pProperties the p properties
     */
    public FenceToolItem(Properties pProperties) {
        super(pProperties);
    }

    /**
     * Called when this item is used when targetting a Block
     *
     * @param context
     */
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        BlockState targetState = context.getLevel().getBlockState(context.getClickedPos());

        Block target = targetState.getBlock();
        if (target instanceof FenceBaseBlock) {
            context
                    .getLevel()
                    .setBlock(
                            context.getClickedPos(),
                            targetState.setValue(
                                    SWEMBlockStateProperties.HALF_FENCE,
                                    !targetState.getValue(SWEMBlockStateProperties.HALF_FENCE)),
                            3);
            return ActionResultType.CONSUME;
        }
        if (target instanceof EnglishFenceBlock) {
            if (context.getPlayer().isShiftKeyDown()) {
                context
                        .getLevel()
                        .setBlock(context.getClickedPos(), targetState.cycle(EnglishFenceBlock.FACING), 3);
            } else {
                context
                        .getLevel()
                        .setBlock(context.getClickedPos(), targetState.cycle(EnglishFenceBlock.PART), 3);
            }
            return ActionResultType.CONSUME;
        }
        if (target instanceof SeparatorBlock) {
            context
                    .getLevel()
                    .setBlock(context.getClickedPos(), targetState.cycle(NonParallelBlock.PART), 3);
            return ActionResultType.CONSUME;
        }
        return ActionResultType.PASS;
    }
}
