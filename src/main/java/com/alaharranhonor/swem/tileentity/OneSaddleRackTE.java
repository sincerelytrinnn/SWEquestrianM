package com.alaharranhonor.swem.tileentity;

import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import net.minecraft.tileentity.TileEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class OneSaddleRackTE extends TileEntity implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);

	public OneSaddleRackTE() {
		super(SWEMTileEntities.ONE_SADDLE_RACK_TILE_ENTITY.get());
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}
