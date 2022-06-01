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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class HalterItem extends HorseTackItem implements IAnimatable {

  private final ResourceLocation texture;

  private ResourceLocation bridleRackTexture;

  private AnimationFactory factory = new AnimationFactory(this);

  /**
   * Instantiates a new Halter item.
   *
   * @param textureName the texture name
   * @param properties the properties
   */
  public HalterItem(String textureName, Properties properties) {
    this(
        new ResourceLocation(SWEM.MOD_ID, "textures/entity/horse/halter/" + textureName + ".png"),
        properties);
    this.bridleRackTexture =
        new ResourceLocation(
            SWEM.MOD_ID, "textures/tile/bridle_rack/bridle_rack_" + textureName + ".png");
  }

  /**
   * Instantiates a new Halter item.
   *
   * @param texture the texture
   * @param properties the properties
   */
  public HalterItem(ResourceLocation texture, Properties properties) {
    super(properties);
    this.texture = texture;
  }

  public ActionResultType interactLivingEntity(
      ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
    if (target instanceof ISWEMEquipable && target.isAlive()) {
      ISWEMEquipable iequipable = (ISWEMEquipable) target;
      if ((!iequipable.hasHalter() || playerIn.isSecondaryUseActive())
          && iequipable.isSaddleable(playerIn)) {
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

  /**
   * Gets bridle rack texture.
   *
   * @return the bridle rack texture
   */
  public ResourceLocation getBridleRackTexture() {
    return this.bridleRackTexture;
  }

  @Override
  public void registerControllers(AnimationData animationData) {}

  @Override
  public AnimationFactory getFactory() {
    return this.factory;
  }
}
