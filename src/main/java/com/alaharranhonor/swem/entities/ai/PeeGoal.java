package com.alaharranhonor.swem.entities.ai;

import com.alaharranhonor.swem.blocks.Shavings;
import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import net.minecraft.block.*;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;

public class PeeGoal extends Goal {

	private final SWEMHorseEntityBase peeEntity;
	private final World entityWorld;

	private final int radius = 3;

	private int peeTimer;
	private double speed;
	private BlockPos peeSpot;

	public PeeGoal(SWEMHorseEntityBase peeEntity, double speed) {
		this.peeEntity = peeEntity;
		this.entityWorld = peeEntity.level;
		this.speed = speed;
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
		this.peeTimer = 79;
		this.entityWorld.broadcastEntityEvent(this.peeEntity, (byte)126);
		this.peeEntity.getNavigation().stop();
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void stop() {
		this.peeTimer = 0;
		this.peeSpot = null;
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
		if (this.peeSpot == null) {
			this.peeSpot = this.getPosOfBestBlock(this.peeEntity.blockPosition());
		} else {
			this.peeEntity.getNavigation().moveTo(peeSpot.getX(), peeSpot.getY(), peeSpot.getZ(), this.speed);
		}
		if (peeTimer == 38) {
			if (this.peeSpot != null && this.peeEntity.blockPosition().closerThan(this.peeSpot, 2)) {
				// Add pee particles
				/*for(int i = 0; i < 7; ++i) {
					double d0 = this.peeEntity.getRandom().nextGaussian() * 0.02D;
					double d1 = this.peeEntity.getRandom().nextGaussian() * 0.02D;
					double d2 = this.peeEntity.getRandom().nextGaussian() * 0.02D;
					((ServerWorld)this.peeEntity.level).sendParticles(ParticleTypes.HEART, this.peeEntity.getRandomX(1.0D), this.peeEntity.getRandomY() + 0.5D, this.peeEntity.getRandomZ(1.0D), 7, -d0, -d1, -d2, 0.5);
				}*/
				this.pee(peeSpot);
			}
		}

	}

	private BlockPos getPosOfBestBlock(BlockPos pos) {
		ArrayList<BlockPos> shavingsPos = new ArrayList<>();
		ArrayList<BlockPos> peePos = new ArrayList<>();
		ArrayList<BlockPos> grassBlocks = new ArrayList<>();
		ArrayList<BlockPos> sandBlocks = new ArrayList<>();
		ArrayList<BlockPos> dirtblocks = new ArrayList<>();
		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {

				BlockPos newPos = pos.offset(x, 0, z); // New pos is on the block above the horse is standing.
				BlockPos belowCheck = newPos.below();
				BlockState checkState = this.entityWorld.getBlockState(newPos);
				BlockState belowCheckState = this.entityWorld.getBlockState(belowCheck);
				boolean flag = true;
				if (checkState != Blocks.AIR.defaultBlockState() || checkState.getBlock() == SWEMBlocks.HORSE_PEE.get())
					flag = false;

				if (checkState.getBlock() instanceof Shavings && checkState.getBlock() != SWEMBlocks.SOILED_SHAVINGS.get()) {
					shavingsPos.add(newPos);
				} else if (belowCheckState.getBlock() == Blocks.GRASS_BLOCK && flag) {
					grassBlocks.add(newPos);
				} else if (belowCheckState.getBlock() == Blocks.SAND && flag) {
					sandBlocks.add(newPos);
				} else if (belowCheckState.getBlock() == Blocks.DIRT && flag) {
					dirtblocks.add(newPos);
				} else {
					if (flag
							&& belowCheckState.getBlock() != Blocks.AIR
							&& checkState.getBlock() != SWEMBlocks.HORSE_PEE.get()
							&& belowCheckState.getFluidState().isEmpty()
							&& checkState.getFluidState().isEmpty()
							&& belowCheckState.isFaceSturdy(this.entityWorld, belowCheck, Direction.UP)
					) {
						peePos.add(newPos);
					}
				}
			}
		}

		return !shavingsPos.isEmpty() ? shavingsPos.get(this.peeEntity.getRandom().nextInt(shavingsPos.size()))
				: !grassBlocks.isEmpty() ? grassBlocks.get(this.peeEntity.getRandom().nextInt(grassBlocks.size()))
				: !sandBlocks.isEmpty() ? sandBlocks.get(this.peeEntity.getRandom().nextInt(sandBlocks.size()))
				: !dirtblocks.isEmpty() ? dirtblocks.get(this.peeEntity.getRandom().nextInt(dirtblocks.size()))
				: !peePos.isEmpty() ? peePos.get(this.peeEntity.getRandom().nextInt(peePos.size()))
				: null;
	}

	private void pee(BlockPos posToPee) {
		System.out.println(posToPee);
		BlockState state = this.entityWorld.getBlockState(posToPee);
		if (state.getBlock() instanceof Shavings) {
			int layers = state.getValue(Shavings.LAYERS);
			this.entityWorld.setBlock(posToPee, SWEMBlocks.SOILED_SHAVINGS.get().defaultBlockState().setValue(Shavings.LAYERS, layers), 3);
		} else {
			this.entityWorld.setBlock(posToPee, SWEMBlocks.HORSE_PEE.get().defaultBlockState(), 3);
		}
	}
}
