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
import com.alaharranhonor.swem.util.registry.SWEMItems;
import java.util.function.Supplier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum ModArmorMaterial implements IArmorMaterial {
  LEATHER(
      SWEM.MOD_ID + ":leather",
      8,
      new int[] {2, 5, 6, 1},
      0,
      SoundEvents.ARMOR_EQUIP_LEATHER,
      0.0f,
      () -> {
        return Ingredient.of(Items.LEATHER);
      }),

  GLOW(
      SWEM.MOD_ID + ":glow",
      12,
      new int[] {2, 5, 6, 1},
      0,
      SoundEvents.ARMOR_EQUIP_LEATHER,
      0.0f,
      () -> {
        return Ingredient.of(Items.LEATHER);
      }),

  IRON(
      SWEM.MOD_ID + ":iron",
      23,
      new int[] {2, 5, 6, 3},
      0,
      SoundEvents.ARMOR_EQUIP_IRON,
      0.0f,
      () -> {
        return Ingredient.of(Items.IRON_INGOT);
      }),

  GOLD(
      SWEM.MOD_ID + ":gold",
      27,
      new int[] {2, 5, 6, 3},
      0,
      SoundEvents.ARMOR_EQUIP_GOLD,
      0.0f,
      () -> {
        return Ingredient.of(Items.GOLD_INGOT);
      }),

  DIAMOND(
      SWEM.MOD_ID + ":diamond",
      38,
      new int[] {2, 5, 6, 4},
      0,
      SoundEvents.ARMOR_EQUIP_DIAMOND,
      0.0f,
      () -> {
        return Ingredient.of(Items.DIAMOND);
      }),

  AMETHYST(
      SWEM.MOD_ID + ":amethyst",
      61,
      new int[] {5, 8, 14, 5},
      0,
      SoundEvents.ARMOR_EQUIP_DIAMOND,
      2.0f,
      () -> {
        return Ingredient.of(SWEMItems.CANTAZARITE.get());
      });

  private static final int[] MAX_DAMAGE_ARRAY = new int[] {13, 16, 15, 11};
  private final String name;
  private final int maxDamageFactor;
  private final int[] damageReductionAmountArray;
  private final int enchantability;
  private final SoundEvent soundEvent;
  private final float toughness;
  private final Supplier<Ingredient> repairMaterial;

  /**
   * Instantiates a new Mod armor material.
   *
   * @param name the name
   * @param maxDamageFactor the max damage factor
   * @param damageReductionAmountArray the damage reduction amount array
   * @param enchantability the enchantability
   * @param soundEvent the sound event
   * @param toughness the toughness
   * @param repairMaterial the repair material
   */
  ModArmorMaterial(
      String name,
      int maxDamageFactor,
      int[] damageReductionAmountArray,
      int enchantability,
      SoundEvent soundEvent,
      float toughness,
      Supplier<Ingredient> repairMaterial) {
    this.name = name;
    this.maxDamageFactor = maxDamageFactor;
    this.damageReductionAmountArray = damageReductionAmountArray;
    this.enchantability = enchantability;
    this.soundEvent = soundEvent;
    this.toughness = toughness;
    this.repairMaterial = repairMaterial;
  }

  @Override
  public int getDurabilityForSlot(EquipmentSlotType slotIn) {
    return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
  }

  @Override
  public int getDefenseForSlot(EquipmentSlotType slotIn) {
    return this.damageReductionAmountArray[slotIn.getIndex()];
  }

  @Override
  public int getEnchantmentValue() {
    return this.enchantability;
  }

  @Override
  public SoundEvent getEquipSound() {
    return this.soundEvent;
  }

  @Override
  public Ingredient getRepairIngredient() {
    return this.repairMaterial.get();
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public float getToughness() {
    return this.toughness;
  }

  @Override
  public float getKnockbackResistance() {
    return 0;
  }
}
