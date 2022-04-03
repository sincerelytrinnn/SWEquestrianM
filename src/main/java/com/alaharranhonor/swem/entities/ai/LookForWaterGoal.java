package com.alaharranhonor.swem.entities.ai;


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

import com.alaharranhonor.swem.blocks.HalfBarrelBlock;
import com.alaharranhonor.swem.blocks.WaterTroughBlock;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class LookForWaterGoal extends Goal {

	private final SWEMHorseEntityBase horse;

	private final double speed;

	private BlockPos foundWater;

	private int movingTimer;
	private BlockPos movingToPos;

	/**
	 * Instantiates a new Look for water goal.
	 *
	 * @param entityIn the entity in
	 * @param speed    the speed
	 */
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
		return !this.horse.isBaby() && this.horse.getNeeds().getThirst().getState().getId() < 3 && this.horse.getPassengers().isEmpty() && !this.horse.getNeeds().getThirst().isOnCooldown() && this.horse.getLeashHolder() == null;
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
		this.movingToPos = null;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean canContinueToUse() {
		return this.horse.getNeeds().getThirst().getState().getId() < 3 && this.horse.getPassengers().isEmpty() && !this.horse.getNeeds().getThirst().isOnCooldown() && this.horse.getLeashHolder() == null;
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
				this.movingTimer++;
				if (this.movingToPos == null) {
					BlockPos goingToPos = new BlockPos(entityPos.getX() + this.horse.getRandom().nextInt(14) - 7, entityPos.getY(), entityPos.getZ() + this.horse.getRandom().nextInt(14) - 7);
					this.horse.getNavigation().moveTo(goingToPos.getX(), goingToPos.getY(), goingToPos.getZ(), this.speed);
					this.movingToPos = goingToPos;
				} else {
					if (this.horse.blockPosition().closerThan(this.movingToPos, 2)) {
						this.movingToPos = null;
					} else {
						if (this.movingTimer > 200) {
							this.movingTimer = 0;
							this.movingToPos = null;
						} else {
							this.horse.getNavigation().moveTo(this.movingToPos.getX(), this.movingToPos.getY(), this.movingToPos.getZ(), this.speed);
						}
					}
				}
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
