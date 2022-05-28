package com.alaharranhonor.swem.entities.ai;
/*
 * All Rights Reserved
 *
 * Copyright (c) 2022, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.HayBlockBase;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.entities.need_revamp.hunger.FoodItem;
import com.alaharranhonor.swem.tileentity.HorseFeedable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.EnumSet;

public class HorseEatFoodGoal extends Goal {
	private SWEMHorseEntityBase horse;
	private BlockPos bestFoodSourcePos;
	private boolean shouldRun;
	private int eatTickTimer;

	public HorseEatFoodGoal(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean canUse() {
		return this.bestFoodSourcePos != null && this.shouldRun;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean canContinueToUse() {
		return this.bestFoodSourcePos != null;
	}

	public void setup(BlockPos pos) {
		this.bestFoodSourcePos = pos;
		this.shouldRun = true;
	}

	public boolean hasTarget() {
		return this.bestFoodSourcePos != null;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void start() {
		this.horse.getNavigation().stop();
		System.out.println("Going for " + this.bestFoodSourcePos);
	}


	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void stop() {
		this.horse.getNavigation().stop();
		this.horse.getEntityData().set(SWEMHorseEntityBase.IS_EATING, false);
		this.bestFoodSourcePos = null;
		this.shouldRun = false;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {

		if (this.eatTickTimer > 0) {
			this.eatTickTimer--; // Let eat cycle finish, before moving on.
			if (FoodItem.indexOfByItem(this.horse.level.getBlockState(this.bestFoodSourcePos).getBlock().asItem()) == -1 && !(this.horse.level.getBlockEntity(this.bestFoodSourcePos) instanceof HorseFeedable)) {
				// Block Disappeared
				// Start the lean out.
				System.out.println("Stopping");
				this.horse.getEntityData().set(SWEMHorseEntityBase.IS_EATING, false);
			} else {
				if (this.eatTickTimer == 20) {
					// Eat block
					System.out.println("20");
					if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.horse.level, this.horse)) {
						BlockState state = this.horse.level.getBlockState(this.bestFoodSourcePos);
						Item edibleItem;
						if (state.hasTileEntity() && this.horse.level.getBlockEntity(this.bestFoodSourcePos) instanceof HorseFeedable) {
							// Interact with the horse feedable interface.

							HorseFeedable feedable = (HorseFeedable) this.horse.level.getBlockEntity(this.bestFoodSourcePos);
							feedable.eat(0, this.horse);
						} else {
							handleWildBlockEating(state);
							edibleItem = state.getBlock().asItem();
							this.horse.getNeeds().getNeed("hunger").interact(new ItemStack(edibleItem));
						}

						this.bestFoodSourcePos = null; // Reset pos, to not re-eat the block.
					}

					this.horse.getEntityData().set(SWEMHorseEntityBase.IS_EATING, false);
				}
				return;
			}

			if (eatTickTimer == 1) {
				this.stop();
				return;
			}
		}

		// TODO: FIx this check, gets stuck on low value, too high value and it just eats 3 blocks out.
		if (!this.horse.blockPosition().closerThan(this.bestFoodSourcePos, 2.18)) {
			// Move to location, since the horse is not close enough.
			this.horse.getNavigation().moveTo(this.bestFoodSourcePos.getX(), this.bestFoodSourcePos.getY(), this.bestFoodSourcePos.getZ(), 4.0);
		} else {
			this.horse.getNavigation().stop();
			this.eatTickTimer = 63;
			this.horse.getEntityData().set(SWEMHorseEntityBase.IS_EATING, true);
		}


	}

	private void handleWildBlockEating(BlockState state) {
		if (state.getBlock() instanceof HayBlockBase) {
			// Full hay block, eat a slab.
			Block slabBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(state.getBlock().getRegistryName().getNamespace(), state.getBlock().getRegistryName().getPath() + "_slab"));

			if (slabBlock == null) {
				SWEM.LOGGER.error(state.getBlock().getRegistryName() + " did not have a corresponding _slab block present.");
				return;
			}

			this.horse.level.setBlock(this.bestFoodSourcePos, slabBlock.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM), 2);

		} else if (state.getBlock() instanceof SlabBlock) {
			SlabType type = state.getValue(SlabBlock.TYPE);
			if (type == SlabType.DOUBLE) {
				// Set to bottom.
				this.horse.level.setBlock(this.bestFoodSourcePos, state.setValue(SlabBlock.TYPE, SlabType.BOTTOM), 2);
			} else {
				// Eat the slab and set it to air.
				this.horse.level.setBlock(this.bestFoodSourcePos, Blocks.AIR.defaultBlockState(), 2);
			}

		}
	}

}
