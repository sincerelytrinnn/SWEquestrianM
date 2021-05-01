package com.alaharranhonor.swem.entities.goals;

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
	public boolean shouldExecute() {
		if (this.horse.getNeeds().getThirst().getState().getId() < 3) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.horse.getNavigator().clearPath();
		this.tickTimer = 0;
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void resetTask() {
		this.foundWater = null;
		this.tickTimer = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting() {
		if (this.horse.getNeeds().getThirst().getState().getId() < 3) {
			return true;
		} else {
			return false;
		}
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
				BlockPos entityPos = this.horse.getPosition();
				for (int i = -3; i < 4; i++) { // X Cord.
					for (int j = -3; j < 4; j++) { // Z Cord
						for (int k = -1; k < 2; k++) { // k = y
							BlockPos checkState = entityPos.add(i, j, k);
							if (this.horse.world.getBlockState(checkState) == Blocks.WATER.getDefaultState()) {
								this.foundWater = checkState;
								break;
							}
						}
					}
				}
				this.timesSearched++;
				if (foundWater == null) {
					this.horse.getNavigator().tryMoveToXYZ(entityPos.getX() + this.horse.getRNG().nextInt(14) - 7, entityPos.getY(), this.horse.getRNG().nextInt(14) - 7, this.speed);
				}
			} else {
				if (this.horse.getPosition().withinDistance(this.foundWater, 2)) {
					this.horse.getNeeds().getThirst().incrementState();
				} else {
					this.horse.getNavigator().tryMoveToXYZ(foundWater.getX(), foundWater.getY(), foundWater.getZ(), this.speed);
				}

			}
			this.tickTimer++;
		} else {
			this.tickTimer++;
		}

	}
}
