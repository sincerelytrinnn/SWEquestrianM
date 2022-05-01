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

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
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

import java.util.Random;

public class SWEMHorseEntity extends SWEMHorseEntityBase implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);

	private int animTimer = 0;
	private int idleAnimCooldown = 0;

	/**
	 * Instantiates a new Swem horse entity.
	 *
	 * @param type    the type
	 * @param worldIn the world in
	 */
	public SWEMHorseEntity(EntityType<? extends SWEMHorseEntityBase> type, World worldIn) {
		super(type, worldIn);
		this.noCulling = true;
	}

	@Override
	public void tick() {
		if (this.level.isClientSide) {
			animTimer = Math.max(animTimer - 1, 0);
			idleAnimCooldown = Math.max(idleAnimCooldown - 1, 0);
		}
		super.tick();
	}

	/**
	 * Predicate play state.
	 *
	 * @param <E>   the type parameter
	 * @param event the event
	 * @return the play state
	 */
	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
	{

		SWEMHorseEntityBase horse = (SWEMHorseEntityBase) event.getAnimatable();
		if (horse.isBaby()) return babyPredicate(event);
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
		boolean isInWater = horse.level.getBlockStates(horse.getBoundingBox().contract(0, 0, 0)).allMatch((bs) -> bs.getBlock() == Blocks.WATER);

		if (!isInWater && horse.jumpHeight != 0) {
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


		if (horse.kickAnimationTimer > 0) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("Kick", false));
			return PlayState.CONTINUE;
		}


		if (horse.isStanding()) {
			if (anim != null) {
				if (anim.animationName.equals("Rear") || anim.animationName.equals("Buck")) {
					return PlayState.CONTINUE;
				}
			}
			event.getController().setAnimation(new AnimationBuilder().addAnimation(horse.getStandVariant() == 2 ? "Buck" : "Rear", false));

			return PlayState.CONTINUE;
		}

		if (horse.isInWater() || isInWater) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("Swim"));
			return PlayState.CONTINUE;
		}

		if (horse.eatingAnim) {
			if (anim != null && !anim.animationName.equals("Eating_Loop"))
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Lean_In", false).addAnimation("Eating_Loop", true));
			return PlayState.CONTINUE;
		} else {
			if (anim != null && anim.animationName.equals("Eating_Loop")) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Lean_Out", false).addAnimation("Stand_Idle", false));
				return PlayState.CONTINUE;
			} else if (anim != null && anim.animationName.equals("Lean_Out"))
				return PlayState.CONTINUE;
		}

		if (horse.isLayingDown) {
			if (anim != null && !anim.animationName.equals("Laying_Down_Loop"))
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Laying_Down", false).addAnimation("Laying_Down_Loop", true));
			return PlayState.CONTINUE;
		} else {
			// Add lean out here.
		}

		boolean playerMovesHorse = false;
		if ( horse.getControllingPassenger() != null) {
			playerMovesHorse = ((PlayerEntity)horse.getControllingPassenger()).xxa > 0;
			System.out.println("XXA Moves the horse: " + playerMovesHorse);
			if (!playerMovesHorse) {
				playerMovesHorse = ((PlayerEntity)horse.getControllingPassenger()).zza > 0;
				System.out.println("ZZA moves the horse: " + playerMovesHorse);
			}
		}

		if (!event.isMoving() && !playerMovesHorse) {
			if (animTimer < 2 || (
				event.getController().getCurrentAnimation().animationName.equalsIgnoreCase("Walk")
				|| event.getController().getCurrentAnimation().animationName.equalsIgnoreCase("Trot")
				|| event.getController().getCurrentAnimation().animationName.equalsIgnoreCase("Canter")
				|| event.getController().getCurrentAnimation().animationName.equalsIgnoreCase("Extended_Canter")
				|| event.getController().getCurrentAnimation().animationName.equalsIgnoreCase("Gallop")
				|| event.getController().getCurrentAnimation().animationName.equalsIgnoreCase("Walking_Backwards")

			)) {
				float chance = new Random().nextFloat();
				if (chance < 0.9f || idleAnimCooldown > 1) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("Stand_Idle", true));
					animTimer = 79;
					event.getController().markNeedsReload();
				} else if (chance > 0.9f && chance < 0.93f && idleAnimCooldown < 1) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("Scratch", false).addAnimation("Stand_Idle", false));
					animTimer = 79 + 90;
					idleAnimCooldown = animTimer + 100;
					event.getController().markNeedsReload();
				} else if (chance > 0.93f && chance < 0.96f && idleAnimCooldown < 1) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("Shake", false).addAnimation("Stand_Idle", false));
					animTimer = 79 + 62;
					idleAnimCooldown = animTimer + 100;
					event.getController().markNeedsReload();
				} else if (idleAnimCooldown < 1) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("Tail_Swish", false).addAnimation("Stand_Idle", false));
					animTimer = 79 + 79;
					idleAnimCooldown = animTimer + 100;
					event.getController().markNeedsReload();
				}
			}
			return PlayState.CONTINUE;
		} else if (playerMovesHorse || event.isMoving()) {
			if (horse.isWalkingBackwards) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Walking_Backwards"));
				return PlayState.CONTINUE;
			}
			if (horse.getEntityData().get(SPEED_LEVEL) == 0) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Walk"));
			} else if (horse.getEntityData().get(SPEED_LEVEL) == 1) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Trot"));
			} else if (horse.getEntityData().get(SPEED_LEVEL) == 2) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Canter"));
			} else if ( horse.getEntityData().get(SPEED_LEVEL) == 3) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Extended_Canter"));
			} else if (horse.getEntityData().get(SPEED_LEVEL) == 4) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Gallop_Transition", false).addAnimation("Gallop"));
			}
			return PlayState.CONTINUE;
		}



		event.getController().setAnimation(new AnimationBuilder().addAnimation("Jump_Level_3", false));
		return PlayState.CONTINUE;
	}

	public <E extends IAnimatable> PlayState babyPredicate(AnimationEvent<E> event) {
		SWEMHorseEntityBase horse = (SWEMHorseEntityBase) event.getAnimatable();

		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("gait.walk"));
		} else {
			if (event.getController().getAnimationState() == AnimationState.Stopped || event.getController().getCurrentAnimation().animationName.equalsIgnoreCase("gait.walk")) {
				float chance = new Random().nextFloat();
				if (chance < 0.95f) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("idle.stand", false));
				} else {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("idle.shake", false));
				}
			}
		}

		return PlayState.CONTINUE;
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

	/**
	 * Particle listener.
	 *
	 * @param <E>   the type parameter
	 * @param event the event
	 */
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
		animationData.addAnimationController(new AnimationController<>(this, "controller", 2, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}
