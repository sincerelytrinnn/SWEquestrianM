package com.alaharranhonor.swem.items.potions;

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

import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.util.registry.SWEMEntities;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.horse.CoatColors;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.item.UseAction;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CantazaritePotionItem extends PotionItem {

  /**
   * Instantiates a new Cantazarite potion item.
   *
   * @param builder the builder
   */
  public CantazaritePotionItem(Properties builder) {
    super(builder);
  }

  public ActionResultType interactLivingEntity(
      ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
    if (target instanceof HorseEntity) {
      HorseEntity horseEntity = (HorseEntity) target;
      CoatColors vanillaCoat = horseEntity.getVariant();

      if (!playerIn.level.isClientSide) {
        BlockPos targetPos = target.blockPosition();
        if (net.minecraftforge.common.ForgeHooks.onLivingDeath(target, DamageSource.GENERIC))
          return ActionResultType.PASS;
        horseEntity.dropEquipment();
        target.remove();
        SWEMHorseEntity horse1 =
            (SWEMHorseEntity)
                SWEMEntities.SWEM_HORSE_ENTITY
                    .get()
                    .spawn(
                        (ServerWorld) playerIn.level,
                        null,
                        playerIn,
                        targetPos,
                        SpawnReason.MOB_SUMMONED,
                        true,
                        false);
        horse1.calculatePotionCoat(vanillaCoat);
      }
      stack.shrink(1);
      return ActionResultType.sidedSuccess(playerIn.level.isClientSide);
    }
    return ActionResultType.PASS;
  }

  @Override
  public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
    return ActionResult.pass(playerIn.getItemInHand(handIn));
  }

  @Override
  public UseAction getUseAnimation(ItemStack stack) {
    return UseAction.NONE;
  }

  @Override
  public void appendHoverText(
      ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    tooltip.add(new TranslationTextComponent("swem.potion.cantazarite_potion.effect"));
  }

  @Override
  public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
    if (this.allowdedIn(group)) {
      items.add(new ItemStack(this));
    }
  }
}
