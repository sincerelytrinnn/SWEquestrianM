package com.alaharranhonor.swem.blocks;

/*
 * All Rights Reserved
 *
 * Copyright (c) 2022, AlaharranHonor, Legenden.
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
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TackBoxBlockItem extends BlockItem {

  public TackBoxBlockItem(Block block) {
    super(block, new Item.Properties().tab(SWEM.TAB));
  }

  @Override
  public void appendHoverText(
      ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
    super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    if (pStack.hasTag()) {
      if (pStack.getTag().contains("horseName")) {
        pTooltip.add(new StringTextComponent(pStack.getTag().getString("horseName")));
      }
    }
  }

  /**
   * Returns true if the item can be used on the given entity, e.g. shears on sheep.
   *
   * @param stack
   * @param playerIn
   * @param target
   * @param hand
   */
  @Override
  public ActionResultType interactLivingEntity(
      ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
    if (target instanceof SWEMHorseEntityBase) {
      SWEMHorseEntityBase horse = (SWEMHorseEntityBase) target;
      // TODO: ONLY ALLOW HORSES TAMED BY THE PLAYER TO SET THE HORSE ID
      stack.getOrCreateTag().putUUID("horseUUID", horse.getUUID());
      stack.getOrCreateTag().putString("horseName", horse.getDisplayName().getString());
      return ActionResultType.sidedSuccess(playerIn.level.isClientSide);
    }
    return ActionResultType.FAIL;
  }
}
