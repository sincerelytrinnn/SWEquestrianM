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

import com.alaharranhonor.swem.SWEM;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
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
    public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        SWEMHorseEntityBase horse = (SWEMHorseEntityBase) event.getAnimatable();
        if (horse.isBaby()) return babyPredicate(event);
        // Rearing happens on all jumps, because minecraft internally uses the Rear animation for jump
        // animation while pushing the enitity
        // into the sky. So find another check, maybe for like isAngry or some of the sort, to play rear
        // animation instead, of isRearing.
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
            if (anim.animationName.startsWith("JumpLvl") && event.getController().getAnimationState() != AnimationState.Stopped) {
                return PlayState.CONTINUE;
            }
        }

        if (horse.isFlying()) {

            if (horse.getEntityData().get(HorseFlightController.isTurning)) {
                if (event.getController().getCurrentAnimation().animationName.equals("TurnCycle")) {
                    return PlayState.CONTINUE;
                }
                if (horse.getEntityData().get(HorseFlightController.isTurningLeft)) {
                    if (!event.getController().getCurrentAnimation().animationName.equals("Turn")) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("Turn", false).addAnimation("TurnCycle", true));
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
                event.getController().setAnimation(new AnimationBuilder().addAnimation("GoingUp"));
                return PlayState.CONTINUE;
            } else if (horse.getEntityData().get(HorseFlightController.isSlowingDown)) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("SlowDown"));
                return PlayState.CONTINUE;
            } else if (horse.getEntityData().get(HorseFlightController.isFloating)) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("FloatDown"));
                return PlayState.CONTINUE;
            } else if (horse.getEntityData().get(HorseFlightController.isAccelerating)) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedUp"));
                return PlayState.CONTINUE;
            }
        }

        // No idea why this needs to be up here, but something in the following jump if statement,
        // blocks the code execution when jumping into water.
        boolean isInWater = horse.level.getBlockStates(horse.getBoundingBox().contract(0, 0, 0)).allMatch((bs) -> bs.getBlock() == Blocks.WATER);

        if (!isInWater && horse.jumpHeight != 0) {
            int jumpHeight = Math.min((int) horse.jumpHeight, 5);
            event.getController().setAnimation(new AnimationBuilder().addAnimation(String.format("JumpLvl%d", jumpHeight), false).addAnimation(anim.animationName, anim.loop));
            return PlayState.CONTINUE;
        }

        if (horse.kickAnimationTimer > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Kick", false).addAnimation("StandIdle", false));
            return PlayState.CONTINUE;
        }

        if (horse.isStanding()) {
            if (anim != null) {
                if (anim.animationName.equals("Rear") || anim.animationName.equals("Buck")) {
                    return PlayState.CONTINUE;
                }
            }
            event.getController().setAnimation(new AnimationBuilder().addAnimation(horse.getStandVariant() == 2 ? "Buck" : "Rear", false).addAnimation("StandIdle", false));

            return PlayState.CONTINUE;
        }

        if (horse.isInWater() || isInWater) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Swim"));
            return PlayState.CONTINUE;
        }

        if (horse.getEntityData().get(IS_EATING)) {
            if (anim != null && !anim.animationName.equals("EatingLoop"))
                event.getController().setAnimation(new AnimationBuilder().addAnimation("LeanIn", false).addAnimation("EatingLoop", true));
            return PlayState.CONTINUE;
        } else {
            if (anim != null && anim.animationName.equals("EatingLoop")) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("LeanOut", false).addAnimation("StandIdle", false));
                return PlayState.CONTINUE;
            } else if (anim != null && anim.animationName.equals("LeanOut")) return PlayState.CONTINUE;
        }

        if (horse.getEntityData().get(IS_LAYING_DOWN)) {
            if (anim != null && !anim.animationName.equals("LayingDownLoop"))
                event.getController().setAnimation(new AnimationBuilder().addAnimation("LayingDown", false).addAnimation("LayingDownLoop", true));
            return PlayState.CONTINUE;
        } else {
            if (anim != null && anim.animationName.equalsIgnoreCase("LayingDownLoop")) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("GettingUp", false).addAnimation("StandIdle", false));
                return PlayState.CONTINUE;
            } else if (anim != null && anim.animationName.equals("GettingUp")) return PlayState.CONTINUE;
        }


        if (!event.isMoving() && !horse.isBeingMovedByPlayer()) {
            if (animTimer < 2 || (anim != null && event.getController().getCurrentAnimation().animationName.equalsIgnoreCase("Walk") || (anim != null && event.getController().getCurrentAnimation().animationName.equalsIgnoreCase("Trot")) || (anim != null && event.getController().getCurrentAnimation().animationName.equalsIgnoreCase("Canter")) || (anim != null && event.getController().getCurrentAnimation().animationName.equalsIgnoreCase("Extended_anter")) || (anim != null && event.getController().getCurrentAnimation().animationName.equalsIgnoreCase("Gallop")) || (anim != null && event.getController().getCurrentAnimation().animationName.equalsIgnoreCase("WalkingBackwards")))) {

                if (horse.getEntityData().get(IS_SAD)) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("SadStandIdle"));
                    return PlayState.CONTINUE;
                }
                float chance = new Random().nextFloat();
                if (chance < 0.9f || idleAnimCooldown > 1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("StandIdle", true));
                    animTimer = 79;
                    event.getController().markNeedsReload();
                } else if (chance > 0.9f && chance < 0.93f && idleAnimCooldown < 1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Scratch", false).addAnimation("StandIdle", false));
                    animTimer = 79 + 94;
                    idleAnimCooldown = animTimer + 100;
                    event.getController().markNeedsReload();
                } else if (chance > 0.93f && chance < 0.96f && idleAnimCooldown < 1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Shake", false).addAnimation("StandIdle", false));
                    animTimer = 79 + 65;
                    idleAnimCooldown = animTimer + 100;
                    event.getController().markNeedsReload();
                } else if (idleAnimCooldown < 1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("TailSwish", false).addAnimation("StandIdle", false));
                    animTimer = 79 + 79;
                    idleAnimCooldown = animTimer + 100;
                    event.getController().markNeedsReload();
                }
            }
            return PlayState.CONTINUE;
        } else if (horse.isBeingMovedByPlayer() || event.isMoving()) {
            if (horse.isWalkingBackwards) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("WalkingBackwards"));
                return PlayState.CONTINUE;
            }
            if (horse.getEntityData().get(GAIT_LEVEL) == 0) {
                if (horse.getEntityData().get(IS_SAD)) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("SadWalk"));
                } else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Walk"));
                }
            } else if (horse.getEntityData().get(GAIT_LEVEL) == 1) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Trot"));
            } else if (horse.getEntityData().get(GAIT_LEVEL) == 2) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Canter"));
            } else if (horse.getEntityData().get(GAIT_LEVEL) == 3) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("ExtendedCanter"));
            } else if (horse.getEntityData().get(GAIT_LEVEL) == 4) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gallop"));
            }
            return PlayState.CONTINUE;
        }

        SWEM.LOGGER.error("No animation was found.");
        return PlayState.STOP;
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
     * @param event
     * @param <E>
     * @return
     */
    private <E extends Entity> SoundEvent soundListener(SoundKeyframeEvent<E> event) {
        // Sound event should be added in the animation.json file.
        //		if (event.sound.equals("moving"))
        //		{
        //			return (SoundEvent)
        // ForgeRegistries.SOUND_EVENTS.getValues().toArray()[rand.nextInt((ForgeRegistries.SOUND_EVENTS.getValues().size()))];
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
    private <E extends Entity> void particleListener(ParticleKeyFrameEvent<E> event) {
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
