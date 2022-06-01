package com.alaharranhonor.swem.items.tack;

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
import com.alaharranhonor.swem.entities.ISWEMEquipable;
import com.alaharranhonor.swem.items.ItemBase;
import java.util.List;
import java.util.Set;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SaddlebagItem extends ItemBase implements IAnimatable {

  private AnimationFactory factory = new AnimationFactory(this);
  private ResourceLocation texture;

  /**
   * Instantiates a new Saddlebag item.
   *
   * @param texturePath the texture path
   */
  public SaddlebagItem(String texturePath) {
    super();
    this.texture =
        new ResourceLocation(
            SWEM.MOD_ID, "textures/entity/horse/saddlebags/" + texturePath + ".png");
  }

  public ActionResultType interactLivingEntity(
      ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
    if (target instanceof ISWEMEquipable && target.isAlive()) {
      ISWEMEquipable iequipable = (ISWEMEquipable) target;
      if (iequipable.isSaddleable(playerIn)) {
        if (!playerIn.level.isClientSide) {
          iequipable.equipSaddle(SoundCategory.NEUTRAL, stack, playerIn);
          if (!playerIn.abilities.instabuild) stack.shrink(1);
        }

        return ActionResultType.sidedSuccess(playerIn.level.isClientSide);
      }
    }
    return ActionResultType.PASS;
  }

  @Override
  public void registerControllers(AnimationData animationData) {}

  @Override
  public AnimationFactory getFactory() {
    return this.factory;
  }

  /**
   * Gets armor texture.
   *
   * @return the armor texture
   */
  public ResourceLocation getArmorTexture() {
    return this.texture;
  }

  @Override
  public ITextComponent getName(ItemStack pStack) {
    if (pStack.hasTag() && pStack.getTag().contains("items")) {
      return new TranslationTextComponent("item.swem.saddlebag.has_items")
          .setStyle(Style.EMPTY.withColor(TextFormatting.LIGHT_PURPLE));
    }
    return super.getName(pStack);
  }

  @Override
  public void appendHoverText(
      ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
    if (pStack.hasTag() && pStack.getTag().contains("items")) {
      CompoundNBT items = pStack.getTag().getCompound("items");
      Set<String> keys = items.getAllKeys();
      for (int i = 0; i < items.size(); i++) {

        ItemStack stack = ItemStack.of(items.getCompound(keys.toArray(new String[0])[i]));
        pTooltip.add(
            new StringTextComponent(stack.getDisplayName().getString() + " x " + stack.getCount())
                .withStyle(Style.EMPTY.withColor(TextFormatting.GRAY)));
      }
    }
    super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
  }
}
