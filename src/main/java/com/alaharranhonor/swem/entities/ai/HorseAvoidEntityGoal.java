package com.alaharranhonor.swem.entities.ai;

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

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;

public class HorseAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
  private final SWEMHorseEntityBase horse;

  /**
   * Instantiates a new Horse avoid entity goal.
   *
   * @param p_i46404_1_ the p i 46404 1
   * @param p_i46404_2_ the p i 46404 2
   * @param p_i46404_3_ the p i 46404 3
   * @param p_i46404_4_ the p i 46404 4
   * @param p_i46404_6_ the p i 46404 6
   */
  public HorseAvoidEntityGoal(
      SWEMHorseEntityBase p_i46404_1_,
      Class<T> p_i46404_2_,
      float p_i46404_3_,
      double p_i46404_4_,
      double p_i46404_6_) {
    super(p_i46404_1_, p_i46404_2_, p_i46404_3_, p_i46404_4_, p_i46404_6_);
    this.horse = p_i46404_1_;
  }

  @Override
  public boolean canUse() {
    return super.canUse() && this.horse.isControlledByLocalInstance();
  }

  @Override
  public void start() {
    super.start();
    SWEMHorseEntityBase.HorseSpeed oldSpeed = this.horse.currentSpeed;
    this.horse.currentSpeed = SWEMHorseEntityBase.HorseSpeed.WALK;
    this.horse.updateSelectedSpeed(oldSpeed);
  }
}
