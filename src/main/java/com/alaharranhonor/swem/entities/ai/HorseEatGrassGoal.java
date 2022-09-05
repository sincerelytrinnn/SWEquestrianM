
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

package com.alaharranhonor.swem.entities.ai;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;

public class HorseEatGrassGoal extends Goal {

	private SWEMHorseEntityBase horse;
	private int tickTimer;
	private BlockPos grassPos;
	private int eatTickTimer;

	public HorseEatGrassGoal(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean canUse() {
		return this.horse.getRandom().nextDouble() > 0.8 && isStandingOnGrass() && !this.horse.getEatFoodGoal().hasTarget();
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean canContinueToUse() {
		return this.tickTimer > 0;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void start() {
		this.horse.getNavigation().stop();

		// Set a random timer, that this task will execute.
		this.tickTimer = (this.horse.getRandom().nextInt(30-5) + 5) * 20;
		setRandomGrassPos();
	}


	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void stop() {
		this.tickTimer = 0;
		this.horse.getNavigation().stop();
		this.horse.getEntityData().set(SWEMHorseEntityBase.IS_EATING, false);
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void tick() {
		this.tickTimer--;

		if (this.eatTickTimer > 0) {
			if (!this.horse.level.getBlockState(this.grassPos).is(Blocks.GRASS_BLOCK)) {
				this.eatTickTimer = 0;
				this.horse.getEntityData().set(SWEMHorseEntityBase.IS_EATING, false);
				setRandomGrassPos();
			} else {
				if (this.eatTickTimer == 20) {
					// Eat block
					if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.horse.level, this.horse)) {
						this.horse.level.levelEvent(2001, this.grassPos, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
						this.horse.level.setBlock(this.grassPos, Blocks.DIRT.defaultBlockState(), 2);
						this.horse.getNeeds().getNeed("hunger").interact(new ItemStack(Items.GRASS_BLOCK));
					}
					setRandomGrassPos();
					this.horse.getEntityData().set(SWEMHorseEntityBase.IS_EATING, false);
				}
				this.eatTickTimer--; // Let eat cycle finish, before moving on.
				return;
			}
		}

		// TODO: FIx this check, gets stuck on low value, too high value and it just eats 3 blocks out.
		if (!this.horse.blockPosition().closerThan(this.grassPos, 2.18)) {
			// Move to location, since the horse is not close enough.
			this.horse.getNavigation().moveTo(this.grassPos.getX(), this.grassPos.getY(), this.grassPos.getZ(), 4.0);
		} else {
			this.horse.getNavigation().stop();
			this.eatTickTimer = 63;
			this.horse.getEntityData().set(SWEMHorseEntityBase.IS_EATING, true);
		}


	}

	public boolean isStandingOnGrass() {
		World world = this.horse.level;
		BlockPos pos = this.horse.blockPosition().below();
		BlockState state = world.getBlockState(pos);
		// TODO: Perhaps add a soft bedding tag, that is often used in combination with outsides/grass places.
		return state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT);
	}

	public void setRandomGrassPos() {
		BlockPos currentPos = this.horse.blockPosition();

		// Generate random numbers to add to current pos, to check for grass pos.

		do {
			// Generate a random number in the range of -3 and 3 for X and Z
			int offsetX = this.horse.getRandom().nextInt(6) - 3;
			int offsetZ = this.horse.getRandom().nextInt(6) - 3;

			//Offset the current pos, and check for grass.
			BlockPos newPos = currentPos.offset(offsetX, -1, offsetZ);
			if (this.horse.level.getBlockState(newPos).is(Blocks.GRASS_BLOCK)) {
				this.grassPos = newPos;
			}
		} while (this.grassPos == null);
	}
}
