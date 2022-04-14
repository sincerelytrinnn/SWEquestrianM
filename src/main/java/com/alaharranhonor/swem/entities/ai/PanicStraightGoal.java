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

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class PanicStraightGoal extends Goal {
	private final SWEMHorseEntityBase horse;
	protected final double speedModifier;
	protected double posX;
	protected double posY;
	protected double posZ;
	protected boolean isRunning;

	/**
	 * Instantiates a new Panic straight goal.
	 *
	 * @param creature the creature
	 * @param speedIn  the speed in
	 */
	public PanicStraightGoal(SWEMHorseEntityBase creature, double speedIn) {
		this.horse = creature;
		this.speedModifier = speedIn;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public void start() {
		this.horse.getNavigation().stop();
		SWEMHorseEntityBase.HorseSpeed oldSpeed = this.horse.currentSpeed;
		this.horse.currentSpeed = SWEMHorseEntityBase.HorseSpeed.GALLOP;
		this.horse.updateSelectedSpeed(oldSpeed);
		this.horse.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier);
		this.isRunning = true;
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void stop() {
		SWEMHorseEntityBase.HorseSpeed oldSpeed = this.horse.currentSpeed;
		this.horse.currentSpeed = SWEMHorseEntityBase.HorseSpeed.WALK;
		this.horse.updateSelectedSpeed(oldSpeed);
		this.horse.getNavigation().stop();
	}

	protected boolean findRandomPosition() {
		Direction direction = this.horse.getDirection();
		Vector3i vector3i = direction.getNormal();
		vector3i = vector3i.relative(direction, MathHelper.clamp(this.horse.progressionManager.getAffinityLeveling().getMaxLevel() - this.horse.progressionManager.getAffinityLeveling().getLevel(), 4, 14));
		BlockPos currentPos = this.horse.blockPosition();
		BlockPos pos = currentPos.offset(vector3i);
		this.posX = pos.getX();
		this.posY = pos.getY();
		this.posZ = pos.getZ();
		return true;

	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean canUse() {
		if (this.horse.getLastHurtByMob() == null && !this.horse.isOnFire() || this.horse.isVehicle() || this.horse.isBaby()) {
			return false;
		} else {
			if (this.horse.isOnFire()) {
				BlockPos blockpos = this.lookForWater(this.horse.level, this.horse, 5, 4);
				if (blockpos != null) {
					this.posX = (double)blockpos.getX();
					this.posY = (double)blockpos.getY();
					this.posZ = (double)blockpos.getZ();
					return true;
				}
			}

			return this.findRandomPosition();
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean canContinueToUse() {
		return !this.horse.getNavigation().isDone();
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		if (this.horse.getNavigation().isDone()) {
			this.stop();
		}
	}

	@Nullable
	protected BlockPos lookForWater(IBlockReader pLevel, Entity pEntity, int pHorizontalRange, int pVerticalRange) {
		BlockPos blockpos = pEntity.blockPosition();
		int i = blockpos.getX();
		int j = blockpos.getY();
		int k = blockpos.getZ();
		float f = (float)(pHorizontalRange * pHorizontalRange * pVerticalRange * 2);
		BlockPos blockpos1 = null;
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		for(int l = i - pHorizontalRange; l <= i + pHorizontalRange; ++l) {
			for(int i1 = j - pVerticalRange; i1 <= j + pVerticalRange; ++i1) {
				for(int j1 = k - pHorizontalRange; j1 <= k + pHorizontalRange; ++j1) {
					blockpos$mutable.set(l, i1, j1);
					if (pLevel.getFluidState(blockpos$mutable).is(FluidTags.WATER)) {
						float f1 = (float)((l - i) * (l - i) + (i1 - j) * (i1 - j) + (j1 - k) * (j1 - k));
						if (f1 < f) {
							f = f1;
							blockpos1 = new BlockPos(blockpos$mutable);
						}
					}
				}
			}
		}

		return blockpos1;
	}
}
