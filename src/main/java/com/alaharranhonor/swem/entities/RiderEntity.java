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
import com.alaharranhonor.swem.entity.render.RiderGeoRenderer;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.file.AnimationFileLoader;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.resource.GeckoLibCache;

import static com.alaharranhonor.swem.entities.SWEMHorseEntityBase.SPEED_LEVEL;

public class RiderEntity implements IAnimatable {
	private AbstractClientPlayerEntity player;
	private final AnimationFactory factory = new AnimationFactory(this);

	public RiderEntity(AbstractClientPlayerEntity player) {
		this.player = player;
	}




	@Override
	public void registerControllers(AnimationData animationData) {
		AnimationController<RiderEntity> controller = new AnimationController<>(this, "controller", 2, this::predicate);
		AnimationController.addModelFetcher((animatable) -> new IAnimatableModel() {
			@Override
			public void setLivingAnimations(Object o, Integer integer, AnimationEvent animationEvent) {

			}

			@Override
			public AnimationProcessor getAnimationProcessor() {
				return null;
			}

			@Override
			public Animation getAnimation(String s, IAnimatable iAnimatable) {
				return new AnimationFileLoader().loadAllAnimations(GeckoLibCache.getInstance().parser, new ResourceLocation(SWEM.MOD_ID, "animations/rider_" + (player.getModelName().equals("default") ? "steve" : "alex") + ".animation.json"), Minecraft.getInstance().getResourceManager()).getAnimation(s);
			}

			@Override
			public void setMolangQueries(IAnimatable iAnimatable, double v) {

			}

		});

		animationData.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		Animation anim = event.getController().getCurrentAnimation();
		if (anim != null) {
			if ((anim.animationName.equals("Jump_Lvl_1Player")
					|| anim.animationName.equals("Jump_Lvl_2Player")
					|| anim.animationName.equals("Jump_Lvl_3Player")
					|| anim.animationName.equals("Jump_Lvl_4Player")
					|| anim.animationName.equals("Jump_Lvl_5Player")
			) && event.getController().getAnimationState() != AnimationState.Stopped) {
				return PlayState.CONTINUE;
			}
		}

		Entity entity = this.getPlayer().getVehicle();
		if (entity instanceof SWEMHorseEntityBase) {
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;


			if (horse.isFlying()) {

				if (horse.getEntityData().get(HorseFlightController.isTurning)) {
					if (event.getController().getCurrentAnimation().animationName.equals("TurnLeftcyclePlayer")) {
						return PlayState.CONTINUE;
					}
					if (horse.getEntityData().get(HorseFlightController.isTurningLeft)) {
						if (!event.getController().getCurrentAnimation().animationName.equals("TurnLeftPlayer")) {
							event.getController().setAnimation(new AnimationBuilder().addAnimation("TurnLeftPlayer", false).addAnimation("TurnLeftcyclePlayer", true));
							return PlayState.CONTINUE;
						}

					}

					if (event.getController().getCurrentAnimation().animationName.equals("TurnLeftPlayer")) {
						return PlayState.CONTINUE;
					}

				}

				if (horse.getEntityData().get(HorseFlightController.isLaunching)) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("LaunchPlayer"));
					return PlayState.CONTINUE;
				}
				if (horse.getEntityData().get(HorseFlightController.isDiving)) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("DivePlayer"));
					return PlayState.CONTINUE;
				}
				if (horse.getEntityData().get(HorseFlightController.didFlap)) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("GoingUpPlayer"));
					return PlayState.CONTINUE;
				} else if (horse.getEntityData().get(HorseFlightController.isSlowingDown)) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("SlowDownPlayer"));
					return PlayState.CONTINUE;
				} else if (horse.getEntityData().get(HorseFlightController.isFloating)) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("FloatDownPlayer"));
					return PlayState.CONTINUE;
				} else if (horse.getEntityData().get(HorseFlightController.isAccelerating)) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedUpPlayer"));
					return PlayState.CONTINUE;
				}
			}

			// No idea why this needs to be up here, but something in the following jump if statement, blocks the code execution when jumping into water.
			boolean isInWater = horse.level.getBlockStates(horse.getBoundingBox().contract(0, 0, 0)).allMatch((bs) -> bs.getBlock() == Blocks.WATER);

			if (horse.jumpHeight != 0) {
				if (horse.jumpHeight > 5.0F) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("JumpLvl5Player", false));
					return PlayState.CONTINUE;
				} else if (horse.jumpHeight > 4.0F) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("JumpLvl4Player", false));
					return PlayState.CONTINUE;
				} else if (horse.jumpHeight > 3.0F) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("JumpLvl3Player", false));
					return PlayState.CONTINUE;
				} else if (horse.jumpHeight > 2.0F) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("JumpLvl2Player", false));
					return PlayState.CONTINUE;
				} else {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("JumpLvl1Player", false));
					return PlayState.CONTINUE;
				}
			}

			if (horse.isStanding()) {
				if (anim != null) {
					if (anim.animationName.equals("RearPlayer") || anim.animationName.equals("BuckPlayer")) {
						return PlayState.CONTINUE;
					}
				}
				event.getController().setAnimation(new AnimationBuilder().addAnimation(horse.getStandVariant() == 2 ? "BuckPlayer" : "RearPlayer"));

				return PlayState.CONTINUE;
			}

			if (horse.isInWater() || isInWater) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("SwimPlayer"));
				return PlayState.CONTINUE;
			}

			float limbSwingAmount = MathHelper.lerp(event.getPartialTick(), horse.animationSpeedOld, horse.animationSpeed);

			boolean isMoving = limbSwingAmount <= -0.15F || limbSwingAmount >= 0.15F;

			if (!isMoving) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("IdlePlayer"));
			} else {
				if (horse.isWalkingBackwards) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("WalkingBackwardsPlayer"));
					return PlayState.CONTINUE;
				}
				if (horse.getEntityData().get(SPEED_LEVEL) == 0) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("WalkPlayer"));
				} else if (horse.getEntityData().get(SPEED_LEVEL) == 1) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("TrotPlayer"));
				} else if (horse.getEntityData().get(SPEED_LEVEL) == 2 ) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("CanterPlayer"));
				} else if (horse.getEntityData().get(SPEED_LEVEL) == 3) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("ExtendedCanterPlayer"));
				} else if (horse.getEntityData().get(SPEED_LEVEL) == 4) {
					event.getController().setAnimation(new AnimationBuilder().addAnimation("GallopPlayer"));
				}
			}

		}
		return PlayState.CONTINUE;
	}

	public PlayerEntity getPlayer() {
		return player;
	}


	public void setPlayer(AbstractClientPlayerEntity player) {
		this.player = player;
	}
}
