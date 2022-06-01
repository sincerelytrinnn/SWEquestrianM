package com.alaharranhonor.swem.event;

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

import com.alaharranhonor.swem.event.entity.player.FillHoseEvent;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public class EventFactory {
  /**
   * On hose use action result.
   *
   * @param player the player
   * @param world the world
   * @param stack the stack
   * @param target the target
   * @return the action result
   */
  @Nullable
  public static ActionResult<ItemStack> onHoseUse(
      @Nonnull PlayerEntity player,
      @Nonnull World world,
      @Nonnull ItemStack stack,
      @Nullable RayTraceResult target) {
    FillHoseEvent event = new FillHoseEvent(player, stack, world, target);
    if (MinecraftForge.EVENT_BUS.post(event))
      return new ActionResult<ItemStack>(ActionResultType.FAIL, stack);

    if (event.getResult() == Event.Result.ALLOW) {
      if (player.abilities.instabuild)
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);

      stack.shrink(1);
      if (stack.isEmpty())
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, event.getFilledHose());

      if (!player.inventory.add(event.getFilledHose())) player.drop(event.getFilledHose(), false);

      return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
    }
    return null;
  }
}
