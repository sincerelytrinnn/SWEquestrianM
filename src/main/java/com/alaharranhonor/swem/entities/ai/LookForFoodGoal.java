package com.alaharranhonor.swem.entities.ai;

import com.alaharranhonor.swem.blocks.GrainFeederBlock;
import com.alaharranhonor.swem.blocks.SlowFeederBlock;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

public class LookForFoodGoal extends Goal {

	private SWEMHorseEntityBase horse;

	private double speed;

	private BlockPos foundFood;

	private int tickTimer;

	private int timesSearched;

	private int blockFound;

	public LookForFoodGoal(SWEMHorseEntityBase entityIn, double speed) {
	 	this.horse = entityIn;
	 	this.speed = speed;
	 	this.blockFound = -1;
	}


	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean canUse() {
		return this.horse.getNeeds().getHunger().getState().getId() < 3;

	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void start() {
		this.horse.getNavigation().stop();
		this.tickTimer = 0;
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void stop() {
		this.foundFood = null;
		this.tickTimer = 0;
		this.blockFound = -1;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean canContinueToUse() {
		return this.horse.getNeeds().getHunger().getState().getId() < 3;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		if (this.tickTimer % 3600 == 0) {
			this.timesSearched = 0;
		}
		if ((this.tickTimer % 100 == 0 && this.timesSearched < 4) || foundFood != null) {
			if (foundFood == null) {
				BlockPos entityPos = this.horse.blockPosition();
				for (int i = -3; i < 4; i++) { // X Cord.
					for (int j = -3; j < 4; j++) { // Z Cord
						for (int k = -1; k < 2; k++) { // k = y
							BlockPos checkState = entityPos.offset(i, j, k);
							if (this.horse.level.getBlockState(checkState) == Blocks.GRASS_BLOCK.defaultBlockState() && this.blockFound <= 0) {
								this.foundFood = checkState;
								this.blockFound = 0;
							} else if (this.horse.level.getBlockState(checkState) == SWEMBlocks.QUALITY_BALE.get().defaultBlockState() && this.blockFound <= 1) {
								this.foundFood = checkState;
								this.blockFound = 1;
							} else if ( SWEMBlocks.SLOW_FEEDERS.stream().anyMatch((sf) -> sf.get().defaultBlockState().setValue(SlowFeederBlock.LEVEL, 1) == this.horse.level.getBlockState(checkState)) || SWEMBlocks.SLOW_FEEDERS.stream().anyMatch((sf) -> sf.get().defaultBlockState().setValue(SlowFeederBlock.LEVEL, 2) == this.horse.level.getBlockState(checkState)) && this.blockFound <= 2) {
								this.foundFood = checkState;
								this.blockFound = 2;
							} else if ( SWEMBlocks.GRAIN_FEEDERS.stream().anyMatch((sf) -> sf.get().defaultBlockState().setValue(GrainFeederBlock.OCCUPIED, true) == this.horse.level.getBlockState(checkState)) && this.blockFound <= 2) {
								this.foundFood = checkState;
								this.blockFound = 3;
							}
						}
					}
				}
				this.timesSearched++;
				if (foundFood == null) {
					this.horse.getNavigation().moveTo(entityPos.getX() + this.horse.getRandom().nextInt(14) - 7, entityPos.getY(), this.horse.getRandom().nextInt(14) - 7, this.speed);
				}
			} else {
				if (this.horse.blockPosition().closerThan(this.foundFood, 2)) {
					switch (this.blockFound) {
						case 0: {
							this.horse.getNeeds().getHunger().addPoints(new ItemStack(Items.GRASS_BLOCK));
							if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.horse.level, this.horse)) {
								this.horse.level.levelEvent(2001, foundFood, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
								this.horse.level.setBlock(foundFood, Blocks.DIRT.defaultBlockState(), 3);

							}
							break;
						}
						case 1: {
							this.horse.getNeeds().getHunger().addPoints(new ItemStack(SWEMBlocks.QUALITY_BALE_ITEM.get()));
							this.horse.level.setBlock(foundFood, Blocks.AIR.defaultBlockState(), 3);

							break;
						}
						case 2: {
							this.horse.getNeeds().getHunger().addPoints(new ItemStack(SWEMBlocks.QUALITY_BALE_ITEM.get()));
							((SlowFeederBlock) this.horse.level.getBlockState(foundFood).getBlock()).eat(this.horse.level, foundFood, this.horse.level.getBlockState(foundFood));
							break;
						}
						case 3: {
							this.horse.getNeeds().getHunger().addPoints(new ItemStack(SWEMItems.SWEET_FEED.get()));
							((GrainFeederBlock) this.horse.level.getBlockState(foundFood).getBlock()).eat(this.horse.level, foundFood, this.horse.level.getBlockState(foundFood));
							break;
						}
					}
					foundFood = null;

				} else {
					this.horse.getNavigation().moveTo(foundFood.getX(), foundFood.getY(), foundFood.getZ(), this.speed);
				}

			}
			this.tickTimer++;
		} else {
			this.tickTimer++;
		}

	}
}
