package com.alaharranhonor.swem.entities.goals;

import com.alaharranhonor.swem.blocks.HorsePoopBlock;
import com.alaharranhonor.swem.blocks.Shavings;
import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ArmorStandItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PeeGoal extends Goal {

	private final MobEntity peeEntity;
	private final World entityWorld;

	private final int radius = 3;

	private int peeTimer;

	public PeeGoal(MobEntity peeEntity) {
		this.peeEntity = peeEntity;
		this.entityWorld = peeEntity.world;

	}


	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean shouldExecute() {
		return this.peeEntity.getRNG().nextInt(10000) == 0 && this.peeEntity.getPassengers().isEmpty();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.peeTimer = 9000;
		this.entityWorld.setEntityState(this.peeEntity, (byte)10);
		this.peeEntity.getNavigator().clearPath();
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void resetTask() {
		this.peeTimer = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting() {
		return this.peeTimer > 0;
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
		if (this.peeTimer == 4) {
			BlockPos blockpos = this.peeEntity.getPosition();
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

				BlockPos newPos = pos.add(x, 0, z);
				BlockState checkState = this.entityWorld.getBlockState(newPos);

				boolean flag = true;
				if (this.entityWorld.getBlockState(newPos.offset(Direction.UP)) != Blocks.AIR.getDefaultState())
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
		return shavingsPos.isEmpty() ? bestPos : shavingsPos.get(this.peeEntity.getRNG().nextInt(shavingsPos.size()));
	}

	private void pee(BlockPos posToPee) {
		BlockState state = this.entityWorld.getBlockState(posToPee);
		if (state.getBlock() instanceof Shavings) {
			int layers = state.get(Shavings.LAYERS);
			this.entityWorld.setBlockState(posToPee, SWEMBlocks.SOILED_SHAVINGS.get().getDefaultState().with(Shavings.LAYERS, layers));
		} else {
			// TODO: PLACE THE PEE BLOCK IN THE *posToPee.offset(Direction.UP)*
		}
	}
}
