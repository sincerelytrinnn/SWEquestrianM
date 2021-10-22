package com.alaharranhonor.swem.entities;

import com.alaharranhonor.swem.util.registry.SWEMEntities;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.PistonTileEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.AnimationState;
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
		this.noCulling = true;
	}

	// createChild method
	@Nullable
	@Override
	public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_)
	{
		return SWEMEntities.SWEM_HORSE_ENTITY.get().create(this.level);
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
	{

		SWEMHorseEntityBase horse = (SWEMHorseEntityBase) event.getAnimatable();

		// Rearing happens on all jumps, because minecraft internally uses the Rear animation for jump animation while pushing the enitity
		// into the sky. So find another check, maybe for like isAngry or some of the sort, to play rear animation instead, of isRearing.
		// This is called from AbstractHorseEntity#handleStartJump()

		/*if (horse.isRearing()) { //

			String animationName = event.getController().getCurrentAnimation().animationName;
			if (animationName.equals("Rear") || animationName.equals("Buck")) // Exit early if the animation is already playing.
				return PlayState.CONTINUE;

			float animationValue = horse.getRNG().nextFloat();
			GeckoLibCache.getInstance().parser.setValue("anim_speed", 3);
			if (animationValue < 0.5) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Rear"));
			} else {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("buck"));
			}
			return PlayState.CONTINUE;
		}*/

		Animation anim = event.getController().getCurrentAnimation();
		if (anim != null) {
			if ((anim.animationName.equals("Jump_Lvl_1")
					|| anim.animationName.equals("Jump_Lvl_2")
					|| anim.animationName.equals("Jump_Lvl_3")
					|| anim.animationName.equals("Jump_Lvl_4")
					|| anim.animationName.equals("Jump_Lvl_5")
			) && event.getController().getAnimationState() != AnimationState.Stopped) {
				return PlayState.CONTINUE;
			}
		}

		if (horse.isFlying()) {

			if (horse.getEntityData().get(HorseFlightController.isTurning)) {
				System.out.println("State: " + event.getController().getAnimationState().name());
				if (event.getController().getCurrentAnimation().animationName.equals("Turn_Cycle")) {
					return PlayState.CONTINUE;
				}
				if (horse.getEntityData().get(HorseFlightController.isTurningLeft)) {
					if (!event.getController().getCurrentAnimation().animationName.equals("Turn")) {
						event.getController().setAnimation(new AnimationBuilder().addAnimation("Turn", false).addAnimation("Turn_Cycle", true));
						return PlayState.CONTINUE;
					}

				}

				if (event.getController().getCurrentAnimation().animationName.equals("Turn")) {
					return PlayState.CONTINUE;
				}

			}

			if (horse.getEntityData().get(HorseFlightController.isLaunching)) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Launch"));
				return PlayState.CONTINUE;
			}
			if (horse.getEntityData().get(HorseFlightController.isDiving)) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Dive"));
				return PlayState.CONTINUE;
			}
			if (horse.getEntityData().get(HorseFlightController.didFlap)) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Going_Up"));
				return PlayState.CONTINUE;
			} else if (horse.getEntityData().get(HorseFlightController.isSlowingDown)) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Slow_Down"));
				return PlayState.CONTINUE;
			} else if (horse.getEntityData().get(HorseFlightController.isFloating)) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Float_Down"));
				return PlayState.CONTINUE;
			} else if (horse.getEntityData().get(HorseFlightController.isAccelerating)) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Speed_Up"));
				return PlayState.CONTINUE;
			}
		}


		// No idea why this needs to be up here, but something in the following jump if statement, blocks the code execution when jumping into water.
		boolean isInWater = horse.level.getBlockStates(horse.getBoundingBox().contract(0, 1, 0)).allMatch((bs) -> bs.getBlock() == Blocks.WATER);

		if (!isInWater && horse.getEntityData().get(SWEMHorseEntityBase.JUMPING) && horse.jumpHeight != 0) {
			if (horse.jumpHeight > 5.0F) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Jump_Lvl_5", false));
				return PlayState.CONTINUE;
			} else if (horse.jumpHeight > 4.0F) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Jump_Lvl_4", false));
				return PlayState.CONTINUE;
			} else if (horse.jumpHeight > 3.0F) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Jump_Lvl_3", false));
				return PlayState.CONTINUE;
			} else if (horse.jumpHeight > 2.0F) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Jump_Lvl_2", false));
				return PlayState.CONTINUE;
			} else {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Jump_Lvl_1", false));
				return PlayState.CONTINUE;
			}
		}



		if (horse.isStanding()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("Rear"));
			return PlayState.CONTINUE;
		}

		System.out.println(isInWater);
		if (horse.isInWater() || isInWater) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("Swim"));
			return PlayState.CONTINUE;
		}



		if (!event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("Stand_Idle"));
		} else {
			if (horse.getEntityData().get(SPEED_LEVEL) == 0) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Walk"));
			} else if (horse.getEntityData().get(SPEED_LEVEL) == 1) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Trot"));
			} else if (horse.getEntityData().get(SPEED_LEVEL) == 2) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Canter"));
			} else if (horse.getEntityData().get(SPEED_LEVEL) == 3) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Gallop"));
			}
		}
		return PlayState.CONTINUE;


		/*SWEMHorseEntityBase horse = null;
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
		}*/
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
		animationData.addAnimationController(new AnimationController(this, "controller", 2, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}
