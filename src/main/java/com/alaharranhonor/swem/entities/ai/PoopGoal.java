package com.alaharranhonor.swem.entities.ai;

import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.entities.PoopEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.registry.SWEMEntities;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PoopGoal extends Goal {

	private final SWEMHorseEntityBase pooperEntity;
	private final World entityWorld;

	private int poopTimer;

	public PoopGoal(SWEMHorseEntityBase pooperEntity) {
		this.pooperEntity = pooperEntity;
		this.entityWorld = pooperEntity.level;

	}


	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean canUse() {
		return this.pooperEntity.level.getGameTime() % (ConfigHolder.SERVER.serverPoopInterval.get() * 20) == 0 && this.pooperEntity.getPassengers().isEmpty() && ConfigHolder.SERVER.serverTickPoopNeed.get();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void start() {
		this.poopTimer = 40;
		this.entityWorld.broadcastEntityEvent(this.pooperEntity, (byte)127);
		this.pooperEntity.getNavigation().stop();
		SWEMHorseEntityBase.HorseSpeed oldSpeed = this.pooperEntity.currentSpeed;
		this.pooperEntity.currentSpeed = SWEMHorseEntityBase.HorseSpeed.WALK;
		this.pooperEntity.updateSelectedSpeed(oldSpeed);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void stop() {
		this.poopTimer = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean canContinueToUse() {
		return this.poopTimer > 0 && this.pooperEntity.getPassengers().isEmpty();
	}

	public int getPoopTimer() {
		return this.poopTimer;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		this.poopTimer = Math.max(0, this.poopTimer - 1);
		if (this.poopTimer == 4) {
			BlockPos blockpos = this.pooperEntity.blockPosition();
			PoopEntity poop = SWEMEntities.HORSE_POOP_ENTITY.get().create(this.entityWorld);
			BlockPos posToPoop = blockpos.offset(0, 1.5d, 0).relative(this.pooperEntity.getDirection().getOpposite());
			poop.setPos(posToPoop.getX(), posToPoop.getY(), posToPoop.getZ());
			this.entityWorld.addFreshEntity(poop);
		}
	}
}
