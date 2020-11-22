package com.alaharranhonor.swem.entities;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.util.initialization.SWEMEntities;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.ParticleKeyFrameEvent;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import javax.annotation.Nullable;

public class SWEMHorseEntity extends SWEMHorseEntityBase implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);

	public SWEMHorseEntity(EntityType<? extends SWEMHorseEntityBase> type, World worldIn) {
		super(type, worldIn);
		this.ignoreFrustumCheck = true;
	}

	// createChild method
	@Nullable
	@Override
	public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_)
	{

		return SWEMEntities.SWEM_HORSE_ENTITY.get().create(this.world);
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
	{
		SWEMHorseEntityBase horse = null;
		if (event.getAnimatable() instanceof SWEMHorseEntityBase) {
			horse = (SWEMHorseEntityBase) event.getAnimatable();
		}

		if (horse != null && horse.isHorseJumping()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("jump"));
			return PlayState.CONTINUE;
		}

		if (horse != null && horse.isBeingRidden()) {
			if (!event.isMoving()) {
				if (event.getController().getCurrentAnimation() != null) {
					if (event.getController().getCurrentAnimation().animationName.equals("walk")) {
						event.getController().setAnimation(new AnimationBuilder().addAnimation("stand_idle"));
					}
				}
				if (horse.ticksExisted % 140 == 0) {
					int randomNum = horse.getRNG().nextInt(100);
					if (randomNum < 15) {
						event.getController().setAnimation(new AnimationBuilder().addAnimation("look_around_left"));
					} else if (randomNum < 30 && randomNum > 14) {
						event.getController().setAnimation(new AnimationBuilder().addAnimation("look_around_right"));
					} else if (randomNum > 29 && randomNum < 40) {
						event.getController().setAnimation(new AnimationBuilder().addAnimation("hock"));
					} else {
						event.getController().setAnimation(new AnimationBuilder().addAnimation("stand_idle"));
					}
				}

				return PlayState.CONTINUE;
			}
			if (horse.getDataManager().get(SPEED_LEVEL) == 0) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
				return PlayState.CONTINUE;
			} else if (horse.getDataManager().get(SPEED_LEVEL) == 1) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("buck", true));
				return PlayState.CONTINUE;
			} else if (horse.getDataManager().get(SPEED_LEVEL) == 2) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("canter", true));
				return PlayState.CONTINUE;
			} else if (horse.getDataManager().get(SPEED_LEVEL) == 3) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("gallop", true));
			}
		}

		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
			return PlayState.CONTINUE;
		} else {
			if (event.getController().getCurrentAnimation() != null) {
				if (event.getController().getCurrentAnimation().animationName.equals("walk")) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("stand_idle"));
				}
			}
			if (((LivingEntity)event.getAnimatable()).ticksExisted % 140 == 0) {
				int randomNum = ((LivingEntity)event.getAnimatable()).getRNG().nextInt(100);
				if (randomNum < 15) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("look_around_left"));
				} else if (randomNum < 30 && randomNum > 14) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("look_around_right"));
				} else if (randomNum > 29 && randomNum < 35) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("hock"));
				} else {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("stand_idle"));
				}
			}
			return PlayState.CONTINUE;
		}
	}


	/**
	 *
	 * @param event
	 * @param <E>
	 * @return
	 */
	private <E extends Entity> SoundEvent soundListener(SoundKeyframeEvent<E> event)
	{
		// Sound event should be added in the animation.json file.
//		if (event.sound.equals("moving"))
//		{
//			return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValues().toArray()[rand.nextInt((ForgeRegistries.SOUND_EVENTS.getValues().size()))];
//		}
//		else if (event.sound.equals("ambient"))
//		{
//			return getAmbientSound();
//		} else {
//			return null;
//		}
		return null;
	}

	private <E extends Entity> void particleListener(ParticleKeyFrameEvent<E> event)
	{
		// Particle effects should be added in the animation.json file.
//		if (event.effect.equals("moving"))
//		{
//
//		}
//		else if (event.effect.equals("ambient"))
//		{
//
//		} else {
//
//		}

	}

	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController(this, "controller", 5, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}
