package com.alaharranhonor.swem.armor;

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
import com.alaharranhonor.swem.items.SWEMArmorItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class AmethystChestplate extends SWEMArmorItem {
  /**
   * Instantiates a new Amethyst chestplate.
   *
   * @param path the path
   * @param materialIn the material in
   * @param slot the slot
   * @param builder the builder
   */
  public AmethystChestplate(
      String path, IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
    super(path, materialIn, slot, builder);
  }

  @Override
  public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
    return true;
  }

  @Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
  public static class KnockbackNegation {

    /**
     * On knockback.
     *
     * @param event the event
     */
    @SubscribeEvent
    public static void onKnockback(LivingKnockBackEvent event) {
      if (!(event.getEntity() instanceof PlayerEntity)) return;

      PlayerEntity player = (PlayerEntity) event.getEntity();
      if (player.getItemBySlot(EquipmentSlotType.CHEST).getItem() instanceof AmethystChestplate) {
        event.setStrength(0);
        event.setRatioX(0);
        event.setRatioZ(0);
        event.setCanceled(true);
      }
    }
  }
}
