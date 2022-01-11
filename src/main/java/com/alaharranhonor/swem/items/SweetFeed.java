package com.alaharranhonor.swem.items;


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

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SweetFeed extends Item {
    public SweetFeed(Properties item) {
        super(new Item.Properties().tab(SWEM.TAB).stacksTo(1).defaultDurability(8));
    }

    public static class UnopenedSweetFeed extends Item {
        private Item sweetFeed;

        public UnopenedSweetFeed(Item sweetFeed) {
            super(new Item.Properties().tab(SWEM.TAB).stacksTo(16));
            this.sweetFeed = sweetFeed;
        }

        /**
         * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
         * {@link //#onItemUse}.
         *
         * @param pLevel
         * @param pPlayer
         * @param pHand
         */
        @Override
        public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
            if (!pLevel.isClientSide) {
                pPlayer.getItemInHand(pHand).shrink(1);
                ItemStack sweetFeed = new ItemStack(this.sweetFeed);
                pPlayer.addItem(sweetFeed);
            }
            return super.use(pLevel, pPlayer, pHand);
        }
    }
}
