package com.alaharranhonor.swem.entities.ai;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

public class LookForWaterGoal extends Goal {

	private SWEMHorseEntityBase horse;

	private double speed;

	private BlockPos foundWater;

	private int tickTimer;

	private int timesSearched;

	public LookForWaterGoal(SWEMHorseEntityBase entityIn, double speed) {
	 	this.horse = entityIn;
	 	this.speed = speed;
	}


	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean canUse() {
		return this.horse.getNeeds().getThirst().getState().getId() < 3;

	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void start() {
		this.horse.getNavigation().stop();
		this.tickTimer = 0;
		SWEMHorseEntityBase.HorseSpeed oldSpeed = this.horse.currentSpeed;
		this.horse.currentSpeed = SWEMHorseEntityBase.HorseSpeed.WALK;
		this.horse.updateSelectedSpeed(oldSpeed);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void stop() {
		this.foundWater = null;
		this.tickTimer = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean canContinueToUse() {
		return this.horse.getNeeds().getThirst().getState().getId() < 3;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		if (this.tickTimer % 3600 == 0) {
			this.timesSearched = 0;
		}
		if ((this.tickTimer % 100 == 0 && this.timesSearched < 4) || foundWater != null) {
			if (foundWater == null) {
				BlockPos entityPos = this.horse.blockPosition();
				for (int i = -3; i < 4; i++) { // X Cord.
					for (int j = -3; j < 4; j++) { // Z Cord
						for (int k = -1; k < 2; k++) { // k = y
							BlockPos checkState = entityPos.offset(i, j, k);
							if (this.horse.level.getBlockState(checkState) == Blocks.WATER.defaultBlockState()) {
								this.foundWater = checkState;
								break;
							}
						}
					}
				}
				this.timesSearched++;
				if (foundWater == null) {
					this.horse.getNavigation().moveTo(entityPos.getX() + this.horse.getRandom().nextInt(14) - 7, entityPos.getY(), this.horse.getRandom().nextInt(14) - 7, this.speed);
				}
			} else {
				if (this.horse.blockPosition().closerThan(this.foundWater, 2)) {
					this.horse.getNeeds().getThirst().incrementState();
				} else {
					this.horse.getNavigation().moveTo(foundWater.getX(), foundWater.getY(), foundWater.getZ(), this.speed);
				}

			}
			this.tickTimer++;
		} else {
			this.tickTimer++;
		}

	}
}
