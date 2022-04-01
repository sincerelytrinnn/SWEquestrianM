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

import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.entities.PoopEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.registry.SWEMEntities;
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
		return !this.pooperEntity.isBaby() && this.pooperEntity.level.getGameTime() % (ConfigHolder.SERVER.serverPoopInterval.get() * 20 + pooperEntity.getId()) == 0 && this.pooperEntity.getPassengers().isEmpty() && ConfigHolder.SERVER.serverTickPoopNeed.get();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void start() {
		this.poopTimer = 79;
		this.entityWorld.broadcastEntityEvent(this.pooperEntity, (byte)127);
		this.pooperEntity.getNavigation().stop();
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
		this.pooperEntity.getNavigation().stop();
		this.poopTimer = Math.max(0, this.poopTimer - 1);
		if (this.poopTimer == 48) {
			BlockPos blockpos = this.pooperEntity.blockPosition();
			PoopEntity poop = SWEMEntities.HORSE_POOP_ENTITY.get().create(this.entityWorld);
			BlockPos posToPoop = blockpos.offset(0, 1.5d, 0).relative(this.pooperEntity.getDirection().getOpposite());
			if (poop != null) {
				poop.setPos(posToPoop.getX(), posToPoop.getY(), posToPoop.getZ());
				this.entityWorld.addFreshEntity(poop);
			}
		}
	}
}
