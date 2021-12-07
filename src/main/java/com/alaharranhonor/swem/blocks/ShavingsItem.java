package com.alaharranhonor.swem.blocks;

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

public class ShavingsItem extends BlockItem {
    public ShavingsItem(Block block) {
        super(block, new Item.Properties().tab(SWEM.TAB).stacksTo(1).defaultDurability(8));
    }

    @Override
    public boolean updateCustomBlockEntityTag(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        stack.hurtAndBreak(1, player, playerEntity -> {});
        ItemStack newItem = stack;
        player.inventory.add(player.inventory.selected, newItem);
        return false;
    }

    public static class SoiledShavingsItem extends Item {

        public SoiledShavingsItem() {
            super(new Item.Properties().tab(SWEM.TAB).stacksTo(16));
        }


    }

    public static class UnopenedShavingsItem extends Item {
        private Item shavingItem;

        public UnopenedShavingsItem(Item shavingItem) {
            super(new Item.Properties().tab(SWEM.TAB).stacksTo(16));
            this.shavingItem = shavingItem;
        }

        /**
         * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
         * {@link #onItemUse}.
         *
         * @param pLevel
         * @param pPlayer
         * @param pHand
         */
        @Override
        public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
            if (!pLevel.isClientSide) {
                pPlayer.getItemInHand(pHand).shrink(1);
                ItemStack shavings = new ItemStack(this.shavingItem);
                pPlayer.addItem(shavings);
            }
            return super.use(pLevel, pPlayer, pHand);
        }
    }
}
