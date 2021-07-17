package com.alaharranhonor.swem.entities;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entity.render.RiderGeoRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
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
	private final PlayerEntity player;
	private final AnimationFactory factory = new AnimationFactory(this);

	public RiderEntity(PlayerEntity player) {
		this.player = player;
	}




	@Override
	public void registerControllers(AnimationData animationData) {
		AnimationController<RiderEntity> controller = new AnimationController(this, "controller", 1, this::predicate);
		AnimationController.addModelFetcher((animatable) -> {
			return new IAnimatableModel() {
				@Override
				public void setLivingAnimations(Object o, Integer integer, AnimationEvent animationEvent) {

				}

				@Override
				public AnimationProcessor getAnimationProcessor() {
					return null;
				}

				@Override
				public Animation getAnimation(String s, IAnimatable iAnimatable) {
					return new AnimationFileLoader().loadAllAnimations(GeckoLibCache.getInstance().parser, new ResourceLocation(SWEM.MOD_ID, "animations/rider_steve.animation.json"), Minecraft.getInstance().getResourceManager()).getAnimation(s);
				}

				@Override
				public void setMolangQueries(IAnimatable iAnimatable, double v) {

				}

			};
		});

		animationData.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		Entity entity = this.getPlayer().getVehicle();
		if (entity instanceof SWEMHorseEntityBase) {
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
			float limbSwingAmount = MathHelper.lerp(event.getPartialTick(), horse.animationSpeedOld, horse.animationSpeed);

			boolean isMoving = limbSwingAmount <= -0.15F || limbSwingAmount >= 0.15F;

			if (!isMoving) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("Idle"));
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

		}
		return PlayState.CONTINUE;
	}

	public PlayerEntity getPlayer() {
		return player;
	}


}
