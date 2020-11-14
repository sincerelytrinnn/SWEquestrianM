package com.alaharranhonor.swem.entities.goals;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.World;

public class FollowWhistleGoal extends Goal {

	private SWEMHorseEntityBase entity;

	private World entityWorld;

	private double targetX;

	private double targetY;

	private double targetZ;

	private boolean isRunning;

	private double speed;


	public FollowWhistleGoal(SWEMHorseEntityBase entity, double speedIn) {
		this.entity = entity;
		this.entityWorld = entity.world;
		this.speed = speedIn;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean shouldExecute() {
		if (this.entity.getWhistleCaller() != null) {
			return true;
		}
		return false;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting() {
		if (this.entity.getPosX() != targetX && this.entity.getPosY() != targetY && this.entity.getPosZ() != targetZ && this.isRunning) {
			return true;
		}
		return false;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.targetX = this.entity.getWhistleCaller().getPosX();
		this.targetY = this.entity.getWhistleCaller().getPosY();
		this.targetZ = this.entity.getWhistleCaller().getPosZ();
		this.isRunning = true;
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void resetTask() {
		this.entity.setWhistleCaller(null);
		this.entity.getNavigator().clearPath();
		this.isRunning = false;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		if (this.entity.getPosX() != targetX && this.entity.getPosY() != targetY && this.entity.getPosZ() != targetZ) {
			this.entity.getNavigator().tryMoveToXYZ(targetX, targetY, targetZ, this.speed);
		}
	}
}
