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


import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.util.Collections;

/**
 * Thanks to Mowzie's Mobs for having a reliable 1.16.5 player animation example.
 */
public abstract class CustomAnimatedGeoModel<T extends IAnimatable & IAnimationTickable> extends AnimatedGeoModel<T> {
	public CustomAnimatedGeoModel() {
	}

	public CustomGeoBone getCustomBone(String boneName) {
		IBone bone = this.getBone(boneName);
		return (CustomGeoBone) bone;
	}

	public boolean isInitialized() {
		return !this.getAnimationProcessor().getModelRendererList().isEmpty();
	}

	@Override
	public void setLivingAnimations(T entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
		// Each animation has it's own collection of animations (called the
		// EntityAnimationManager), which allows for multiple independent animations
		AnimationData manager = entity.getFactory().getOrCreateAnimationData(uniqueID);
		if (manager.startTick == null) {
			manager.startTick = (double) (entity.tickTimer() + Minecraft.getInstance().getFrameTime()); // Set start ticks when animation starts playing
		}

		if (!Minecraft.getInstance().isPaused() || manager.shouldPlayWhilePaused) {
			manager.tick = (entity.tickTimer() + Minecraft.getInstance().getFrameTime());
			double gameTick = manager.tick;
			double deltaTicks = gameTick - lastGameTickTime;
			seekTime += deltaTicks;
			lastGameTickTime = gameTick;
		}

		AnimationEvent<T> predicate;
		if (customPredicate == null) {
			predicate = new AnimationEvent<T>(entity, 0, 0, 0, false, Collections.emptyList());
		} else {
			predicate = customPredicate;
		}

		predicate.animationTick = seekTime;
		getAnimationProcessor().preAnimationSetup(predicate.getAnimatable(), seekTime);
		if (!this.getAnimationProcessor().getModelRendererList().isEmpty()) {
			getAnimationProcessor().tickAnimation(entity, uniqueID, seekTime, predicate, GeckoLibCache.getInstance().parser, shouldCrashOnMissing);
		}

		if (!Minecraft.getInstance().isPaused() || manager.shouldPlayWhilePaused) {
			codeAnimations(entity, uniqueID, customPredicate);
		}
	}

	public void codeAnimations(T entity, Integer uniqueID, AnimationEvent<?> customPredicate) {

	}

	public boolean resourceForModelId(AbstractClientPlayerEntity player) {
		return true;
	}

	public float getControllerValue(String controllerName) {
		if (!isInitialized()) return 1.0f;
		return 1.0f - getBone(controllerName).getPositionX();
	}
}
