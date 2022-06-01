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

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;

public class SWEMArmorItem extends GeoArmorItem implements IAnimatable {

  private AnimationFactory factory = new AnimationFactory(this);

  private String TEXTURE_PATH;

  /**
   * Instantiates a new Swem armor item.
   *
   * @param path the path
   * @param materialIn the material in
   * @param slot the slot
   * @param builder the builder
   */
  public SWEMArmorItem(
      String path, IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
    super(materialIn, slot, builder);
    this.TEXTURE_PATH = path;
  }

  /**
   * Predicate play state.
   *
   * @param <P> the type parameter
   * @param event the event
   * @return the play state
   */
  private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
    return PlayState.STOP;
  }

  /**
   * Gets texture path.
   *
   * @return the texture path
   */
  public String getTexturePath() {
    return this.TEXTURE_PATH;
  }

  @Override
  public void registerControllers(AnimationData animationData) {
    animationData.addAnimationController(
        new AnimationController(this, "controller", 20, this::predicate));
  }

  @Override
  public AnimationFactory getFactory() {
    return this.factory;
  }
}
