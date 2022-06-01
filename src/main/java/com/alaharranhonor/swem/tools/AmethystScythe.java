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

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class AmethystScythe extends HoeItem {

  private final float attackDamage;
  /** Modifiers applied when the item is in the mainhand of a user. */
  private final Multimap<Attribute, AttributeModifier> attributeModifiers;

  /**
   * Instantiates a new Amethyst scythe.
   *
   * @param tier the tier
   * @param attackDamageIn the attack damage in
   * @param attackSpeedIn the attack speed in
   * @param builderIn the builder in
   */
  public AmethystScythe(
      IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
    super(tier, attackDamageIn, attackSpeedIn, builderIn);
    this.attackDamage = (float) attackDamageIn + tier.getAttackDamageBonus();
    ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
    builder.put(
        Attributes.ATTACK_DAMAGE,
        new AttributeModifier(
            BASE_ATTACK_DAMAGE_UUID,
            "Weapon modifier",
            (double) this.attackDamage,
            AttributeModifier.Operation.ADDITION));
    builder.put(
        Attributes.ATTACK_SPEED,
        new AttributeModifier(
            BASE_ATTACK_SPEED_UUID,
            "Weapon modifier",
            (double) attackSpeedIn,
            AttributeModifier.Operation.ADDITION));
    this.attributeModifiers = builder.build();
  }

  public float getAttackDamage() {
    return this.attackDamage;
  }

  /**
   * Current implementations of this method in child classes do not use the entry argument beside
   * ev. They just raise the damage on the stack.
   */
  public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {

    if (!(attacker instanceof PlayerEntity)) return false;
    PlayerEntity player = (PlayerEntity) attacker;

    float f = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
    float f1;
    if (target instanceof LivingEntity) {
      f1 =
          EnchantmentHelper.getDamageBonus(
              player.getMainHandItem(), ((LivingEntity) target).getMobType());
    } else {
      f1 = EnchantmentHelper.getDamageBonus(player.getMainHandItem(), CreatureAttribute.UNDEFINED);
    }

    float f2 = player.getAttackStrengthScale(0.5F);
    f = f * (0.2F + f2 * f2 * 0.8F);
    f1 = f1 * f2;

    boolean flag = f2 > 0.9F;

    boolean flag2 =
        flag
            && player.fallDistance > 0.0F
            && !player.isOnGround()
            && !player.onClimbable()
            && !player.isInWater()
            && !player.hasEffect(Effects.BLINDNESS)
            && !player.isPassenger()
            && target instanceof LivingEntity;
    flag2 = flag2 && !player.isSprinting();
    net.minecraftforge.event.entity.player.CriticalHitEvent hitResult =
        net.minecraftforge.common.ForgeHooks.getCriticalHit(
            player, target, flag2, flag2 ? 1.5F : 1.0F);
    flag2 = hitResult != null;
    if (flag2) {
      f *= hitResult.getDamageModifier();
    }

    float f3 = 1.0F + (1.0F - 1.0F / (float) (2 + 1)) * f;

    for (LivingEntity livingentity :
        player.level.getEntitiesOfClass(
            LivingEntity.class, target.getBoundingBox().inflate(1.0D, 0.25D, 1.0D))) {
      if (livingentity != player
          && livingentity != target
          && !player.isAlliedTo(livingentity)
          && (!(livingentity instanceof ArmorStandEntity)
              || !((ArmorStandEntity) livingentity).isMarker())
          && player.distanceToSqr(livingentity) < 9.0D) {
        livingentity.knockback(
            0.4F,
            (double) MathHelper.sin(player.yRot * ((float) Math.PI / 180F)),
            (double) (-MathHelper.cos(player.yRot * ((float) Math.PI / 180F))));
        livingentity.hurt(DamageSource.playerAttack(player), f3);
      }
    }

    player.level.playSound(
        (PlayerEntity) null,
        player.getX(),
        player.getY(),
        player.getZ(),
        SoundEvents.PLAYER_ATTACK_SWEEP,
        player.getSoundSource(),
        1.0F,
        1.0F);
    player.sweepAttack();
    stack.hurtAndBreak(
        1,
        attacker,
        (entity) -> {
          entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
        });
    return true;
  }

  /**
   * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item"
   * statistic.
   */
  public boolean onBlockDestroyed(
      ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
    if (!worldIn.isClientSide && state.getDestroySpeed(worldIn, pos) != 0.0F) {
      stack.hurtAndBreak(
          1,
          entityLiving,
          (entity) -> {
            entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
          });
    }

    return true;
  }

  /** Gets a map of item attribute modifiers, used by ItemSword to increase hit damage. */
  public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(
      EquipmentSlotType equipmentSlot) {
    return equipmentSlot == EquipmentSlotType.MAINHAND
        ? this.attributeModifiers
        : super.getDefaultAttributeModifiers(equipmentSlot);
  }
}
