package com.alaharranhonor.swem.enchantments;

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
import com.alaharranhonor.swem.armor.LeatherRidingBoots;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class UpstepEnchantment extends Enchantment {
  /**
   * Instantiates a new Upstep enchantment.
   *
   * @param rarityIn the rarity in
   * @param typeIn the type in
   * @param slots the slots
   */
  public UpstepEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
    super(rarityIn, typeIn, slots);
  }

  @Override
  public int getMaxLevel() {
    return 1;
  }

  @Override
  public int getMinLevel() {
    return 1;
  }

  @Override
  public boolean isAllowedOnBooks() {
    return false;
  }

  /**
   * Determines if this enchantment can be applied to a specific ItemStack.
   *
   * @param pStack The ItemStack to test.
   */
  @Override
  public boolean canEnchant(ItemStack pStack) {
    return false;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return false;
  }

  @Override
  protected boolean checkCompatibility(Enchantment ench) {
    return !ench.equals(Enchantments.DEPTH_STRIDER);
  }

  @Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
  public static class UpstepEquipped {
    /**
     * Check for players wearing lrb.
     *
     * @param event the event
     */
    @SubscribeEvent
    public static void CheckForPlayersWearingLRB(TickEvent.PlayerTickEvent event) {
      if (event.phase.equals(TickEvent.Phase.END)) {
        if (event.player.inventory.armor.get(0).getItem() instanceof LeatherRidingBoots) {
          event.player.maxUpStep = 1.0f;
        } else {
          event.player.maxUpStep = 0.6f;
        }
      }
    }
  }
}
