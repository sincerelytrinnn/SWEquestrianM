package com.alaharranhonor.swem.entities.needs;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

public class ThirstNeed {

	private ThirstState state;

	private SWEMHorseEntityBase horse;
	private int tickCounter;
	private int drinkingCoolDown;

	public ThirstNeed(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.setState(ThirstState.QUENCHED);
		this.tickCounter = 96_000;
	}

	public void tick() {
		if (this.tickCounter == 0) return;
		this.tickCounter--;

		this.drinkingCoolDown = Math.max(0, this.drinkingCoolDown - 1);

		if (this.tickCounter <= this.state.getTickAmountChange() && this.state != ThirstState.EXICCOSIS) {
			this.setStateById(this.state.getId() - 1);
		}
	}

	public void incrementState() {
		if (this.state != ThirstState.QUENCHED) {
			ThirstState nextState = getNextState();
			this.setStateById(nextState.ordinal());
			if (this.state == ThirstState.QUENCHED) {
				this.tickCounter = 96_000;
			} else {
				this.tickCounter = getNextState().getTickAmountChange();
			}
		}
		this.drinkingCoolDown = 100;
	}

	public boolean isOnCooldown() {
		return this.drinkingCoolDown > 0;
	}

	public ThirstState getState() {
		return this.state;
	}

	public ThirstState getNextState() {
		int thirstId = this.state.getId() + 1;
		if (thirstId > 4) {
			thirstId = 4;
		}
		return ThirstState.values()[thirstId];
	}

	public void setState(ThirstState state) {
		this.state = state;
		this.state.setHorse(this.horse);
	}

	public CompoundNBT write(CompoundNBT nbt) {
		if (this.state != null) {
			nbt.putInt("thirstStateID", this.state.getId());
			nbt.putInt("thirstTick", this.tickCounter);
		}
		return nbt;
	}

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
			this.tickCounter = ticks;
		}
		this.state.setHorse(this.horse);
	}

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
		private int tickAmountChange;
		private SWEMHorseEntityBase horse;
		ThirstState(int tickAmountChange) {
			this.tickAmountChange = tickAmountChange;
		}

		public void setHorse(SWEMHorseEntityBase horse) {
			this.horse = horse;
		}

		public int getId() {
			return this.horse.getEntityData().get(ID);
		}

		public int getTickAmountChange() {
			return tickAmountChange * (ConfigHolder.SERVER.multiplayerHungerThirst.get() ? 72 : 1);
		}

	}
}
