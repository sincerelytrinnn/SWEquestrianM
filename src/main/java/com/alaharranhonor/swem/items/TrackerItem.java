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
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.data.HorseData;
import java.util.UUID;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class TrackerItem extends ItemBase {

  @Override
  public ActionResultType interactLivingEntity(
      ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
    if (target instanceof SWEMHorseEntityBase) {
      SWEMHorseEntityBase horse = (SWEMHorseEntityBase) target;

      if (horse.isBaby() || !horse.isTamed()) return ActionResultType.FAIL;

      if (!horse.getOwnerUUID().equals(playerIn.getUUID())) {
        playerIn.displayClientMessage(
            new StringTextComponent("You can't track horses, that aren't yours."), true);
        return ActionResultType.FAIL;
      }

      if (playerIn.isShiftKeyDown()) {
        // Remove tracking status
        CompoundNBT tracked = playerIn.getPersistentData().getCompound("tracked");
        CompoundNBT trackedNew = new CompoundNBT();
        boolean removed = false;
        for (int i = 0; i < tracked.size(); i++) {

          if (tracked.getUUID(String.valueOf(i)).equals(horse.getUUID())) {
            playerIn.displayClientMessage(
                new StringTextComponent("Horse is no longer being tracked"), true);
            removed = true;
            horse.setTracked(false);
            continue;
          }
          trackedNew.putUUID(
              String.valueOf(removed ? i - 1 : i), tracked.getUUID(String.valueOf(i)));
        }

        playerIn.getPersistentData().put("tracked", trackedNew);
      } else {
        // Add tracking status
        CompoundNBT tracked = playerIn.getPersistentData().getCompound("tracked");

        for (int i = 0; i < tracked.size(); i++) {
          if (tracked.getUUID(String.valueOf(i)).equals(horse.getUUID())) {
            playerIn.displayClientMessage(
                new StringTextComponent("Horse is already being tracked."), true);
            return ActionResultType.FAIL;
          }
        }
        tracked.putUUID(Integer.toString(tracked.size()), horse.getUUID());

        horse.setTracked(true);

        playerIn.displayClientMessage(new StringTextComponent("Horse is now being tracked"), true);

        playerIn.getPersistentData().put("tracked", tracked);
      }

      return ActionResultType.CONSUME;
    }
    return ActionResultType.PASS;
  }

  @Override
  public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
    if (!worldIn.isClientSide) {

      CompoundNBT tracked = playerIn.getPersistentData().getCompound("tracked");

      StringBuilder builder = new StringBuilder();

      int horsesNotFound = 0;
      for (int i = 0; i < tracked.size(); i++) {
        UUID uuid = tracked.getUUID(Integer.toString(i));
        HorseData data = SWEM.getHorseData(uuid);
        if (data != null) {
          builder
              .append("Name: ")
              .append(data.getName())
              .append(" x: ")
              .append(data.getPos().getX())
              .append(" - y: ")
              .append(data.getPos().getY())
              .append(" - z: ")
              .append(data.getPos().getZ())
              .append("\n");
        } else {
          horsesNotFound++;
        }
      }

      if (horsesNotFound > 0) {
        builder
            .append(horsesNotFound)
            .append(" horse")
            .append(horsesNotFound > 1 ? "s" : "")
            .append(" was not found.");
      }
      playerIn.sendMessage(new StringTextComponent(builder.toString()), Util.NIL_UUID);
      return ActionResult.consume(playerIn.getItemInHand(handIn));
    }

    return super.use(worldIn, playerIn, handIn);
  }
}
