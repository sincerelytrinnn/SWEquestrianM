package com.alaharranhonor.swem.entities.goals;

import com.alaharranhonor.swem.blocks.SlowFeederBlock;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import com.alaharranhonor.swem.util.initialization.SWEMItems;
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
	public boolean shouldExecute() {
		if (this.horse.getNeeds().getHunger().getState().getId() < 3) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.horse.getNavigator().clearPath();
		this.tickTimer = 0;
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void resetTask() {
		this.foundFood = null;
		this.tickTimer = 0;
		this.blockFound = -1;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting() {
		if (this.horse.getNeeds().getHunger().getState().getId() < 3) {
			return true;
		} else {
			return false;
		}
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
				BlockPos entityPos = this.horse.getPosition();
				for (int i = -3; i < 4; i++) { // X Cord.
					for (int j = -3; j < 4; j++) { // Z Cord
						for (int k = -1; k < 2; k++) { // k = y
							BlockPos checkState = entityPos.add(i, j, k);
							if (this.horse.world.getBlockState(checkState) == Blocks.GRASS_BLOCK.getDefaultState() && this.blockFound <= 0) {
								this.foundFood = checkState;
								this.blockFound = 0;
							} else if (this.horse.world.getBlockState(checkState) == SWEMBlocks.QUALITY_BALE.get().getDefaultState() && this.blockFound <= 1) {
								this.foundFood = checkState;
								this.blockFound = 1;
							} else if ((this.horse.world.getBlockState(checkState) == SWEMBlocks.SLOW_FEEDER.get().getDefaultState().with(SlowFeederBlock.LEVEL, 1) || this.horse.world.getBlockState(checkState) == SWEMBlocks.SLOW_FEEDER.get().getDefaultState().with(SlowFeederBlock.LEVEL, 2)) && this.blockFound <= 2) {
								this.foundFood = checkState;
								this.blockFound = 2;
							}
						}
					}
				}
				this.timesSearched++;
				if (foundFood == null) {
					this.horse.getNavigator().tryMoveToXYZ(entityPos.getX() + this.horse.getRNG().nextInt(14) - 7, entityPos.getY(), this.horse.getRNG().nextInt(14) - 7, this.speed);
				}
			} else {
				if (this.horse.getPosition().withinDistance(this.foundFood, 2)) {
					switch (this.blockFound) {
						case 0: {
							this.horse.getNeeds().getHunger().addPoints(new ItemStack(Items.GRASS_BLOCK));
							if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.horse.world, this.horse)) {
								this.horse.world.playEvent(2001, foundFood, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
								this.horse.world.setBlockState(foundFood, Blocks.DIRT.getDefaultState(), 3);

							}
							break;
						}
						case 1: {
							this.horse.getNeeds().getHunger().addPoints(new ItemStack(SWEMBlocks.QUALITY_BALE_ITEM.get()));
							this.horse.world.setBlockState(foundFood, Blocks.AIR.getDefaultState(), 3);

							break;
						}
						case 2: {
							this.horse.getNeeds().getHunger().addPoints(new ItemStack(SWEMBlocks.QUALITY_BALE_ITEM.get()));
							((SlowFeederBlock) this.horse.world.getBlockState(foundFood).getBlock()).eatHay(this.horse.world, foundFood, this.horse.world.getBlockState(foundFood));
							break;
						}
					}
					foundFood = null;

				} else {
					this.horse.getNavigator().tryMoveToXYZ(foundFood.getX(), foundFood.getY(), foundFood.getZ(), this.speed);
				}

			}
			this.tickTimer++;
		} else {
			this.tickTimer++;
		}

	}
}
