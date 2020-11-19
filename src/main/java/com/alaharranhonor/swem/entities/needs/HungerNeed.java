package com.alaharranhonor.swem.entities.needs;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

public class HungerNeed {

	private HungerState state;

	private SWEMHorseEntityBase horse;

	public HungerNeed(SWEMHorseEntityBase horse) {
		this.horse = horse;
	}


	public void tick() {
		this.state.setCurrentTicks(this.state.getCurrentTicks() + 1);

		if (this.state.getCurrentTicks() == this.state.getTicksToDowngrade() && this.state != HungerState.STARVING) {
			this.setStateById(this.state.getId() - 1);
			this.state.setCurrentTicks(0);
		}
	}

	public HungerState getState() {
		return this.state;
	}

	public void setState(HungerState state) {
		this.state = state;
	}

	public CompoundNBT write(CompoundNBT nbt) {
		if (this.state != null) {
			nbt.putInt("hungerStateID", this.state.getId());
			nbt.putInt("hungerStateTick", this.state.getCurrentTicks());
		}
		return nbt;
	}

	public void read(CompoundNBT nbt) {
		if (nbt.contains("hungerStateID")) {
			int stateId = nbt.getInt("hungerStateID");
			this.setStateById(stateId);
		} else {
			this.setStateById(0);
		}
		if (nbt.contains("hungerStateTick")) {
			int ticks = nbt.getInt("hungerStateTick");
			this.state.setCurrentTicks(ticks);
		}
		this.state.setHorse(this.horse);
	}

	private void setStateById(int id) {
		switch(id) {
			case 0: {
				this.setState(HungerState.STARVING);
				this.horse.getDataManager().set(HungerState.ID, id);
				break;
			}
			case 1: {
				this.setState(HungerState.MALNOURISHED);
				this.horse.getDataManager().set(HungerState.ID, id);
				break;
			}
			case 2: {
				this.setState(HungerState.HUNGRY);
				this.horse.getDataManager().set(HungerState.ID, id);
				break;
			}
			case 3: {
				this.setState(HungerState.FED);
				this.horse.getDataManager().set(HungerState.ID, id);
				break;
			}
			case 4: {
				this.setState(HungerState.FULLY_FED);
				this.horse.getDataManager().set(HungerState.ID, id);
				break;
			}
			default: {
				this.setState(HungerState.STARVING);
				this.horse.getDataManager().set(HungerState.ID, id);
			}
		}
	}

	public enum HungerState {

		STARVING(-1),
		MALNOURISHED(72000),
		HUNGRY(72000),
		FED(24000),
		FULLY_FED(12000);

		public static final DataParameter<Integer> ID = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.VARINT);
		private int ticksToDowngrade;
		private int currentTicks;
		private SWEMHorseEntityBase horse;
		HungerState(int ticksToDowngrade) {
			this.ticksToDowngrade = ticksToDowngrade;
			this.currentTicks = 0;
		}

		public void setHorse(SWEMHorseEntityBase horse) {
			this.horse = horse;
		}

		public int getId() {
			return this.horse.getDataManager().get(ID);
		}

		public int getTicksToDowngrade() {
			return ticksToDowngrade;
		}

		public int getCurrentTicks() {
			return currentTicks;
		}

		public void setCurrentTicks(int currentTicks) {
			this.currentTicks = currentTicks;
		}
	}
}
