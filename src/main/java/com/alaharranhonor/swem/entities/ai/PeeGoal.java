package com.alaharranhonor.swem.entities.ai;

import com.alaharranhonor.swem.blocks.Shavings;
import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class PeeGoal extends Goal {

	private final MobEntity peeEntity;
	private final World entityWorld;

	private final int radius = 3;

	private int peeTimer;

	public PeeGoal(MobEntity peeEntity) {
		this.peeEntity = peeEntity;
		this.entityWorld = peeEntity.level;

	}


	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean canUse() {
		return this.peeEntity.level.getGameTime() % (ConfigHolder.SERVER.serverPeeInterval.get() * 20) == 0 && this.peeEntity.getPassengers().isEmpty() && ConfigHolder.SERVER.serverTickPeeNeed.get();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void start() {
		this.peeTimer = 40;
		this.entityWorld.broadcastEntityEvent(this.peeEntity, (byte)126);
		this.peeEntity.getNavigation().stop();
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void stop() {
		this.peeTimer = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean canContinueToUse() {
		return this.peeTimer > 0 && this.peeEntity.getPassengers().isEmpty();
	}

	public int getPeeTimer() {
		return this.peeTimer;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		this.peeTimer = Math.max(0, this.peeTimer - 1);
		if (peeTimer == 4) {
			BlockPos blockpos = this.peeEntity.blockPosition();
			BlockPos bestPos = this.getPosOfBestBlock(blockpos);
			this.pee(bestPos);
		}

	}

	private BlockPos getPosOfBestBlock(BlockPos pos) {
		int bestBlock = -1;
		BlockPos bestPos = pos;
		ArrayList<BlockPos> shavingsPos = new ArrayList<>();
		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {

				BlockPos newPos = pos.offset(x, 0, z);
				BlockState checkState = this.entityWorld.getBlockState(newPos);

				boolean flag = true;
				if (this.entityWorld.getBlockState(newPos.relative(Direction.UP)) != Blocks.AIR.defaultBlockState())
					flag = false;

				if (checkState.getBlock() instanceof Shavings && checkState.getBlock() != SWEMBlocks.SOILED_SHAVINGS.get()) {
					shavingsPos.add(newPos);
				} else if (checkState.getBlock() instanceof GrassBlock && bestBlock < 3 && flag) {
					bestBlock = 3;
					bestPos = newPos;
				} else if (checkState.getBlock() instanceof SandBlock && bestBlock < 2 && flag) {
					bestBlock = 2;
					bestPos = newPos;
				} else if (checkState.getBlock() == Blocks.DIRT && bestBlock < 1 && flag) {
					bestBlock = 1;
					bestPos = newPos;
				} else {
					if (flag) {
						bestBlock = 0;
						bestPos = pos;
					}
				}
			}
		}
		return shavingsPos.isEmpty() ? bestPos : shavingsPos.get(this.peeEntity.getRandom().nextInt(shavingsPos.size()));
	}

	private void pee(BlockPos posToPee) {
		BlockState state = this.entityWorld.getBlockState(posToPee);
		if (state.getBlock() instanceof Shavings) {
			int layers = state.getValue(Shavings.LAYERS);
			this.entityWorld.setBlock(posToPee, SWEMBlocks.SOILED_SHAVINGS.get().defaultBlockState().setValue(Shavings.LAYERS, layers), 3);
		} else {
			this.entityWorld.setBlock(posToPee, SWEMBlocks.HORSE_PEE.get().defaultBlockState(), 3);
		}
	}
}
