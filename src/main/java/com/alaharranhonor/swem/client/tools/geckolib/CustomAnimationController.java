package com.alaharranhonor.swem.client.tools.geckolib;
/*
 * All Rights Reserved
 *
 * Copyright (c) 2022, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
// Huge thanks to Mowzie's Mobs for making this custom player renderer
// https://www.curseforge.com/minecraft/mc-mods/mowzies-mobs

import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;

public class CustomAnimationController<T extends IAnimatable & IAnimationTickable>
    extends AnimationController<T> {
  private double tickOffset;

  public CustomAnimationController(
      T animatable,
      String name,
      float transitionLengthTicks,
      IAnimationPredicate<T> animationPredicate) {
    super(animatable, name, transitionLengthTicks, animationPredicate);
    tickOffset = 0.0d;
  }

  public void playAnimation(T animatable, AnimationBuilder animationBuilder) {
    setAnimation(animationBuilder);
    currentAnimation = this.animationQueue.poll();
    isJustStarting = true;
    adjustTick(animatable.tickTimer());
  }

  @Override
  protected double adjustTick(double tick) {
    if (this.shouldResetTick) {
      if (getAnimationState() == AnimationState.Transitioning) {
        this.tickOffset = tick;
      } else if (getAnimationState() == AnimationState.Running) {
        this.tickOffset = tick;
      }
      this.shouldResetTick = false;
    }
    return Math.max(tick - this.tickOffset, 0.0D);
  }
}
