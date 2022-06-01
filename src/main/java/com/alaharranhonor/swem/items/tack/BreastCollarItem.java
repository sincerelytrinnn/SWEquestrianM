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
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BreastCollarItem extends HorseTackItem {

  private final ResourceLocation texture;

  /**
   * Instantiates a new Breast collar item.
   *
   * @param textureName the texture name
   * @param properties the properties
   */
  public BreastCollarItem(String textureName, Properties properties) {
    this(
        new ResourceLocation(
            SWEM.MOD_ID, "textures/entity/horse/breast_collar/" + textureName + ".png"),
        properties);
  }

  /**
   * Instantiates a new Breast collar item.
   *
   * @param texture the texture
   * @param properties the properties
   */
  public BreastCollarItem(ResourceLocation texture, Properties properties) {
    super(properties);
    this.texture = texture;
  }

  public ActionResultType interactLivingEntity(
      ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
    if (target instanceof ISWEMEquipable && target.isAlive()) {
      ISWEMEquipable iequipable = (ISWEMEquipable) target;
      if (playerIn.level.isClientSide && !iequipable.hasHalter()) {
        playerIn.displayClientMessage(
            new StringTextComponent("You need to equip a Halter/Bridle first."), true);
        return ActionResultType.FAIL;
      }
      if ((!iequipable.hasBreastCollar() || playerIn.isSecondaryUseActive())
          && iequipable.isSaddleable(playerIn)
          && iequipable.hasHalter()) {
        if (!playerIn.level.isClientSide) {
          iequipable.equipSaddle(SoundCategory.NEUTRAL, stack, playerIn);
          if (!playerIn.abilities.instabuild) stack.shrink(1);
        }

        return ActionResultType.sidedSuccess(playerIn.level.isClientSide);
      }
    }
    return ActionResultType.PASS;
  }

  /**
   * Gets armor texture.
   *
   * @return the armor texture
   */
  @OnlyIn(Dist.CLIENT)
  public ResourceLocation getArmorTexture() {
    return texture;
  }
}
