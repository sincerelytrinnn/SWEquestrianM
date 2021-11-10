package com.alaharranhonor.swem.entities.ai;

import com.alaharranhonor.swem.blocks.HalfBarrelBlock;
import com.alaharranhonor.swem.blocks.WaterTroughBlock;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class LookForWaterGoal extends Goal {

	private SWEMHorseEntityBase horse;

	private double speed;

	private BlockPos foundWater;

	private int movingTimer;

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
		return this.horse.getNeeds().getThirst().getState().getId() < 3 && this.horse.getPassengers().isEmpty() && !this.horse.getNeeds().getThirst().isOnCooldown();

	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void start() {
		this.horse.getNavigation().stop();
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
		this.movingTimer = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean canContinueToUse() {
		return this.horse.getNeeds().getThirst().getState().getId() < 3 && this.horse.getPassengers().isEmpty() && !this.horse.getNeeds().getThirst().isOnCooldown();
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		if (this.horse.getNeeds().getThirst().isOnCooldown()) {
			this.stop();
		}
		if (foundWater == null) {

			ArrayList<BlockPos> waterPools = new ArrayList<>();
			ArrayList<BlockPos> halfBarrels = new ArrayList<>();
			ArrayList<BlockPos> waterTroughs = new ArrayList<>();
			BlockPos entityPos = this.horse.blockPosition();
			for (int i = -3; i < 4; i++) { // X Cord.
				for (int j = -3; j < 4; j++) { // Z Cord
					for (int k = -1; k < 2; k++) { // Y cord
						BlockPos checkPos = entityPos.offset(i, k, j);
						BlockState checkState = this.horse.level.getBlockState(checkPos);
						if (checkState.isAir()) continue;

						if (checkState.getBlock() == Blocks.WATER) {
							waterPools.add(checkPos);
						} else if (checkState.getBlock() instanceof HalfBarrelBlock) {
							if (checkState.getValue(HalfBarrelBlock.LEVEL) > 0) {
								halfBarrels.add(checkPos);
							}
						} else if (checkState.getBlock() instanceof WaterTroughBlock) {
							if (checkState.getValue(WaterTroughBlock.LEVEL) > 0) {
								waterTroughs.add(checkPos);
							}
						}
					}
				}
			}

			this.foundWater = !waterTroughs.isEmpty() ? waterTroughs.get(this.horse.getRandom().nextInt(waterTroughs.size()))
					: !halfBarrels.isEmpty() ? halfBarrels.get(this.horse.getRandom().nextInt(halfBarrels.size()))
					: !waterPools.isEmpty() ? waterPools.get(this.horse.getRandom().nextInt(waterPools.size()))
					: null;

			if (foundWater == null) {
				this.horse.getNavigation().moveTo(entityPos.getX() + this.horse.getRandom().nextInt(14) - 7, entityPos.getY(), this.horse.getRandom().nextInt(14) - 7, this.speed);
				this.stop();
			}
		} else {
			this.movingTimer++;
			if (this.horse.blockPosition().closerThan(this.foundWater, 2)) {
				BlockState foundState = this.horse.level.getBlockState(this.foundWater);
				if (foundState.getBlock() instanceof WaterTroughBlock) {
					((WaterTroughBlock) foundState.getBlock()).setWaterLevel(this.horse.level, this.foundWater, foundState, true);
					this.horse.getNeeds().getThirst().incrementState();
				} else if (foundState.getBlock() instanceof HalfBarrelBlock) {
					((HalfBarrelBlock) foundState.getBlock()).setWaterLevel(this.horse.level, this.foundWater, foundState, foundState.getValue(HalfBarrelBlock.LEVEL) - 1);
					this.horse.getNeeds().getThirst().incrementState();
				} else if (foundState.getBlock() == Blocks.WATER) {
					this.horse.level.setBlock(foundWater, Blocks.AIR.defaultBlockState(), 3);
					this.horse.getNeeds().getThirst().incrementState();
				}

				this.stop();
			} else {
				if (this.movingTimer >= 200) {
					this.stop();
				} else {
					this.horse.getNavigation().moveTo(foundWater.getX(), foundWater.getY(), foundWater.getZ(), this.speed);
				}
			}
		}

	}
}
