package com.alaharranhonor.swem.entities;


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

import com.alaharranhonor.swem.util.registry.SWEMEntities;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


import javax.annotation.Nullable;

public class WormieBoiEntity extends SheepEntity implements IAnimatable {

	private final AnimationFactory factory = new AnimationFactory(this);

	public WormieBoiEntity(EntityType<? extends SheepEntity> type, World worldIn) {
		super(type, worldIn);
		this.noCulling = true;
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes()
	{
		return MobEntity.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 8.0D)
				.add(Attributes.MOVEMENT_SPEED, (double) 0.15f);
	}

	@Override
	public boolean readyForShearing() {
		return false;
	}

	@Nullable
	@Override
	public SheepEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_)
	{

		return SWEMEntities.WORMIE_BOI_ENTITY.get().create(this.level);
	}

	/**
	 * Plays living's sound at its position
	 */
	@Override
	public void playAmbientSound() {
		this.playSound(SoundEvents.FOX_AMBIENT, 0.15f, 1.0f);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
	{
		if (event.isMoving()) {

			event.getController().setAnimation(new AnimationBuilder().addAnimation("slither2.io", true));
			return PlayState.CONTINUE;
		} else {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));
			return PlayState.CONTINUE;
		}

	}


	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}
