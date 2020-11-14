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
	}

	public void tick() {

	}

	public ThirstState getState() {
		return this.state;
	}

	public void setState(ThirstState state) {
		this.state = state;
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
				this.horse.getDataManager().set(ThirstState.ID, id);
			}
		}
	}


	public enum ThirstState {

		EXICCOSIS(-1),
		DEHYDRATED(24000),
		THIRSTY(48000),
		SATISIFIED(12000),
		QUENCHED(6000);

		public static final DataParameter<Integer> ID = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.VARINT);
		private int ticksToDowngrade;
		private int currentTicks;
		private SWEMHorseEntityBase horse;
		ThirstState(int ticksToDowngrade) {
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
