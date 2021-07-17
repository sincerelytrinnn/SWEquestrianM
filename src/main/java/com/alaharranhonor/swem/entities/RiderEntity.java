package com.alaharranhonor.swem.entities;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entity.render.RiderGeoRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
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
import software.bernie.geckolib3.resource.GeckoLibCache;

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
					return new AnimationFileLoader().loadAllAnimations(GeckoLibCache.getInstance().parser, new ResourceLocation(SWEM.MOD_ID, "animations/rider.animation.json"), RiderGeoRenderer.INSTANCE).getAnimation(s);
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
		System.out.println("RIDER PREDICATE CALLED");
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	public PlayerEntity getPlayer() {
		return player;
	}


}
