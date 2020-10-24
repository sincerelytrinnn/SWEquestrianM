package com.alaharranhonor.swem.entities.goals;

import com.alaharranhonor.swem.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PoopGoal extends Goal {

	private final MobEntity pooperEntity;
	private final World entityWorld;

	private int poopTimer;

	public PoopGoal(MobEntity pooperEntity) {
		this.pooperEntity = pooperEntity;
		this.entityWorld = pooperEntity.world;

	}


	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean shouldExecute() {
		return this.pooperEntity.getRNG().nextInt(10000) == 0;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.poopTimer = 200;
		this.entityWorld.setEntityState(this.pooperEntity, (byte)10);
		this.pooperEntity.getNavigator().clearPath();
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void resetTask() {
		this.poopTimer = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting() {
		return this.poopTimer > 0;
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
			BlockPos blockpos = this.pooperEntity.getPosition();
			this.entityWorld.setBlockState(blockpos, RegistryHandler.HORSE_POO.get().getDefaultState(), 2);
		}
	}
}