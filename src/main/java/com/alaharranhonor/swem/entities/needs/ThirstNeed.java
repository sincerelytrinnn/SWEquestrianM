package com.alaharranhonor.swem.entities.needs;


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
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

public class ThirstNeed {

	private ThirstState state;

	private final SWEMHorseEntityBase horse;
	private int tickCounter;
	private int drinkingCoolDown;

	/**
	 * Instantiates a new Thirst need.
	 *
	 * @param horse the horse
	 */
	public ThirstNeed(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.setState(ThirstState.QUENCHED);
		this.tickCounter = 96_000 * (ConfigHolder.SERVER.multiplayerHungerThirst.get() ? 72 : 1);
	}

	/**
	 * Tick.
	 */
	public void tick() {
		if (this.tickCounter == 0) return;
		this.tickCounter--;

		this.drinkingCoolDown = Math.max(0, this.drinkingCoolDown - 1);

		if (this.tickCounter <= this.state.getTickAmountChange() && this.state != ThirstState.EXICCOSIS) {
			this.setStateById(this.state.getId() - 1);
		}
	}

	/**
	 * Can increment state boolean.
	 *
	 * @return the boolean
	 */
	public boolean canIncrementState() {
		return this.state != ThirstState.QUENCHED;
	}

	/**
	 * Increment state.
	 */
	public void incrementState() {
		if (this.state != ThirstState.QUENCHED) {
			ThirstState nextState = getNextState();
			this.setStateById(nextState.ordinal());
			if (this.state == ThirstState.QUENCHED) {
				this.tickCounter = 96_000 * (ConfigHolder.SERVER.multiplayerHungerThirst.get() ? 72 : 1);
			} else {
				this.tickCounter = getNextState().getTickAmountChange();
			}
		}
		this.drinkingCoolDown = 100;
	}

	/**
	 * Is on cooldown boolean.
	 *
	 * @return the boolean
	 */
	public boolean isOnCooldown() {
		return this.drinkingCoolDown > 0;
	}

	/**
	 * Gets state.
	 *
	 * @return the state
	 */
	public ThirstState getState() {
		return this.state;
	}

	/**
	 * Gets next state.
	 *
	 * @return the next state
	 */
	public ThirstState getNextState() {
		int thirstId = this.state.getId() + 1;
		if (thirstId > 4) {
			thirstId = 4;
		}
		return ThirstState.values()[thirstId];
	}

	/**
	 * Sets state.
	 *
	 * @param state the state
	 */
	public void setState(ThirstState state) {
		this.state = state;
		this.state.setHorse(this.horse);
	}

	/**
	 * Write compound nbt.
	 *
	 * @param nbt the nbt
	 * @return the compound nbt
	 */
	public CompoundNBT write(CompoundNBT nbt) {
		if (this.state != null) {
			nbt.putInt("thirstStateID", this.state.getId());
			nbt.putInt("thirstTick", ConfigHolder.SERVER.multiplayerHungerThirst.get() ? this.tickCounter / 72 : this.tickCounter);
		}
		return nbt;
	}

	/**
	 * Read.
	 *
	 * @param nbt the nbt
	 */
	public void read(CompoundNBT nbt) {
		if (nbt.contains("thirstStateID")) {
			int stateId = nbt.getInt("thirstStateID");
			this.setStateById(stateId);
		} else {
			this.setStateById(4);
		}
		if (nbt.contains("thirstTick")) {
			int ticks = nbt.getInt("thirstTick");
			if (ticks == 0 && this.state != ThirstState.EXICCOSIS) {
				ticks = getNextState().getTickAmountChange();
			}
			this.tickCounter = ConfigHolder.SERVER.multiplayerHungerThirst.get() ? ticks * 72 : ticks;
		}
		this.state.setHorse(this.horse);
	}

	/**
	 * Sets state by id.
	 *
	 * @param id the id
	 */
	public void setStateById(int id) {
		switch(id) {
			case 0: {
				this.setState(ThirstState.EXICCOSIS);
				this.horse.getEntityData().set(ThirstState.ID, id);
				break;
			}
			case 1: {
				this.setState(ThirstState.DEHYDRATED);
				this.horse.getEntityData().set(ThirstState.ID, id);
				break;
			}
			case 2: {
				this.setState(ThirstState.THIRSTY);
				this.horse.getEntityData().set(ThirstState.ID, id);
				break;
			}
			case 3: {
				this.setState(ThirstState.SATISIFIED);
				this.horse.getEntityData().set(ThirstState.ID, id);
				break;
			}
			case 4: {
				this.setState(ThirstState.QUENCHED);
				this.horse.getEntityData().set(ThirstState.ID, id);
				break;
			}
			default: {
				this.setState(ThirstState.EXICCOSIS);
				this.horse.getEntityData().set(ThirstState.ID, 0);
			}
		}
	}


	public enum ThirstState {
	// Water bucket is 6000 ticks
		EXICCOSIS(-1),
		DEHYDRATED(24000),
		THIRSTY(72000),
		SATISIFIED(84000),
		QUENCHED(90000);

		public static final DataParameter<Integer> ID = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);
		private final int tickAmountChange;
		private SWEMHorseEntityBase horse;

		/**
		 * Instantiates a new Thirst state.
		 *
		 * @param tickAmountChange the tick amount change
		 */
		ThirstState(int tickAmountChange) {
			this.tickAmountChange = tickAmountChange;
		}

		/**
		 * Sets horse.
		 *
		 * @param horse the horse
		 */
		public void setHorse(SWEMHorseEntityBase horse) {
			this.horse = horse;
		}

		/**
		 * Gets id.
		 *
		 * @return the id
		 */
		public int getId() {
			return this.horse.getEntityData().get(ID);
		}

		/**
		 * Gets tick amount change.
		 *
		 * @return the tick amount change
		 */
		public int getTickAmountChange() {
			return tickAmountChange * (ConfigHolder.SERVER.multiplayerHungerThirst.get() ? 72 : 1);
		}

	}
}
