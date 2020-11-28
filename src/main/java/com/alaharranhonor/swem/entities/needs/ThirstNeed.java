package com.alaharranhonor.swem.entities.needs;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

public class ThirstNeed {

	private ThirstState state;

	private SWEMHorseEntityBase horse;

	public ThirstNeed(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.setState(ThirstState.QUENCHED);
	}

	public void tick() {
		if (this.state.getCurrentTicks() == 0) return;
		this.state.setCurrentTicks(this.state.getCurrentTicks() - 1);

		if (this.state.getCurrentTicks() <= this.state.getTickAmountChange() && this.state != ThirstState.EXICCOSIS) {
			this.setStateById(this.state.getId() - 1);
		}
	}

	public void incrementState() {
		if (this.state != ThirstState.QUENCHED) {
			this.setStateById(this.state.getId() + 1);
			if (this.state == ThirstState.QUENCHED) {
				this.state.setCurrentTicks(96000);
			} else {
				this.state.setCurrentTicks(this.getNextState().getTickAmountChange());
			}
		}
	}

	public ThirstState getState() {
		return this.state;
	}

	public ThirstState getNextState() {
		return this.state.values()[this.state.getId() + 1];
	}

	public void setState(ThirstState state) {
		int ticks = 0;
		if (this.state != null) {
			ticks = this.state.getCurrentTicks();
		}
		this.state = state;
		this.state.setCurrentTicks(ticks);
		this.state.setHorse(this.horse);
	}

	public CompoundNBT write(CompoundNBT nbt) {
		if (this.state != null) {
			nbt.putInt("thirstStateID", this.state.getId());
			nbt.putInt("thirstStateTick", this.state.getCurrentTicks());
		}
		return nbt;
	}

	public void read(CompoundNBT nbt) {
		if (nbt.contains("thirstStateID")) {
			int stateId = nbt.getInt("thirstStateID");
			this.setStateById(stateId);
		} else {
			this.setStateById(0);
		}
		if (nbt.contains("thirstStateTick")) {
			int ticks = nbt.getInt("thirstStateTick");
			this.state.setCurrentTicks(ticks);
		}
		this.state.setHorse(this.horse);
	}

	private void setStateById(int id) {
		switch(id) {
			case 0: {
				this.setState(ThirstState.EXICCOSIS);
				this.horse.getDataManager().set(ThirstState.ID, id);
				break;
			}
			case 1: {
				this.setState(ThirstState.DEHYDRATED);
				this.horse.getDataManager().set(ThirstState.ID, id);
				break;
			}
			case 2: {
				this.setState(ThirstState.THIRSTY);
				this.horse.getDataManager().set(ThirstState.ID, id);
				break;
			}
			case 3: {
				this.setState(ThirstState.SATISIFIED);
				this.horse.getDataManager().set(ThirstState.ID, id);
				break;
			}
			case 4: {
				this.setState(ThirstState.QUENCHED);
				this.horse.getDataManager().set(ThirstState.ID, id);
				break;
			}
			default: {
				this.setState(ThirstState.EXICCOSIS);
				this.horse.getDataManager().set(ThirstState.ID, 0);
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

		public static final DataParameter<Integer> ID = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.VARINT);
		private int tickAmountChange;
		private int currentTicks;
		private SWEMHorseEntityBase horse;
		ThirstState(int tickAmountChange) {
			this.tickAmountChange = tickAmountChange;
			this.currentTicks = 0;
		}

		public void setHorse(SWEMHorseEntityBase horse) {
			this.horse = horse;
		}

		public int getId() {
			return this.horse.getDataManager().get(ID);
		}

		public int getTickAmountChange() {
			return tickAmountChange;
		}

		public int getCurrentTicks() {
			return currentTicks;
		}

		public void setCurrentTicks(int currentTicks) {
			this.currentTicks = currentTicks;
		}
	}
}
