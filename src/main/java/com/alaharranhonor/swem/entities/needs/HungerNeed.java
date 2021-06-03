package com.alaharranhonor.swem.entities.needs;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import com.alaharranhonor.swem.util.initialization.SWEMItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HungerNeed {

	private HungerState state;

	private SWEMHorseEntityBase horse;

	private ArrayList<Ingredient> FEEDS = new ArrayList<Ingredient>(
			Stream.of(
					Ingredient.fromItems(Items.CARROT),
					Ingredient.fromItems(Items.APPLE),
					Ingredient.fromItems(SWEMItems.OAT_BUSHEL.get()),
					Ingredient.fromItems(SWEMItems.TIMOTHY_BUSHEL.get()),
					Ingredient.fromItems(SWEMItems.ALFALFA_BUSHEL.get()),
					Ingredient.fromItems(SWEMBlocks.QUALITY_BALE_ITEM.get()),
					Ingredient.fromItems(Items.GRASS_BLOCK),
					Ingredient.fromItems(SWEMItems.SUGAR_CUBE.get())
			).collect(Collectors.toList()));

	private int[] POINTS_GIVEN = {1, 1, 5, 5, 5, 15, 1, 1};
	private int[] TIMES_FED = new int[8];
	private int[] MAX_TIMES = {1, 1, 1, 4, 4, 1, -1, 1};

	public static final DataParameter<Integer> TOTAL_TIMES_FED = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.VARINT);

	public HungerNeed(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.setState(HungerState.FULLY_FED);
	}


	public void tick() {
		if (this.state.getCurrentTicks() == 0) return;
		this.state.setCurrentTicks(this.state.getCurrentTicks() - 1);

		if (this.state.getCurrentTicks() <= this.state.getTickAmountChange() && this.state != HungerState.STARVING) {
			this.setStateById(this.state.getId() - 1);
		}
	}

	public boolean addPoints(ItemStack itemstack) {
		if (this.getTotalTimesFed() == 7 && itemstack.getItem() != Items.GRASS_BLOCK) {
			return false;
		}
		int itemIndex = this.getItemIndex(itemstack);

		// Early break causes.
		if (itemIndex == -1) return false; // Item was not a legal item.
		if (this.getMaxTimesFed(itemIndex) == this.getTimesFed(itemIndex)) return false; // That type of item, has already been fed it's max times.
		int points = this.getPointsFromItem(itemIndex);

		if (itemstack.getItem() != Items.GRASS_BLOCK) {
			this.setTotalTimesFed(1);
		}

		this.TIMES_FED[itemIndex]++; // Increment the times fed values.

		this.state.setCurrentPoints(this.state.getCurrentPoints() + points);
		// Set the points.

		if (this.checkIncrement()) {
			this.incrementState();
		}
		return true;
	}

	public boolean checkIncrement() {
		return this.state.getCurrentPoints() >= this.state.getPointsRequired();
	}

	public void incrementState() {
		if (this.state != HungerState.FED) {
			this.setStateById(this.state.getId() + 1);
			if (this.state == HungerState.FED) {
				this.state.setCurrentTicks(192000);
			} else {
				this.state.setCurrentTicks(this.getNextState().getTickAmountChange());
			}
		}
	}

	private int getItemIndex(ItemStack itemstack) {
		int index = -1;
		for (int i = 0; i < FEEDS.size(); i++) {
			Ingredient ingredient = FEEDS.get(i);
			if (ingredient.test(itemstack)) {
				index = i;
				break;
			}
		}
		return index;
	}

	private int getTimesFed(int index) {
		return this.TIMES_FED[index];
	}

	private int getMaxTimesFed(int index) {
		return this.MAX_TIMES[index];
	}

	private int getPointsFromItem(int index) {
		return this.POINTS_GIVEN[index];
	}

	public int getTotalTimesFed() {
		return this.horse.getDataManager().get(TOTAL_TIMES_FED);
	}

	private void setTotalTimesFed(int amount) {
		this.horse.getDataManager().set(TOTAL_TIMES_FED, this.getTotalTimesFed() + amount);
	}

	public HungerState getState() {
		return this.state;
	}

	public HungerState getNextState() {
		SWEM.LOGGER.debug(this.state.getId());
		int hungerId = this.state.getId() + 1;
		if (hungerId > 4) {
			hungerId = 4;
		}
		return HungerState.values()[hungerId];
	}

	public void setState(HungerState state) {
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
			nbt.putInt("hungerStateID", this.state.getId());
			nbt.putInt("hungerStateTick", this.state.getCurrentTicks());
			nbt.putInt("hungerStatePoints", this.state.getCurrentPoints());
			nbt.putInt("hungerTotalTimesFed", this.getTotalTimesFed());
			nbt.putIntArray("hungerTimesFed", this.TIMES_FED);

		}
		return nbt;
	}

	public void read(CompoundNBT nbt) {
		if (nbt.contains("hungerStateID")) {
			int stateId = nbt.getInt("hungerStateID");
			this.setStateById(stateId);
		} else {
			this.setStateById(4);
		}
		if (nbt.contains("hungerStateTick")) {
			int ticks = nbt.getInt("hungerStateTick");
			this.state.setCurrentTicks(ticks);
		}
		if (nbt.contains("hungerStatePoints")) {
			int points = nbt.getInt("hungerStatePoints");
			this.state.setCurrentPoints(points);
		}
		if (nbt.contains("hungerTotalTimesFed")) {
			int totalTimesFed = nbt.getInt("hungerTotalTimesFed");
			this.setTotalTimesFed(totalTimesFed);
		}
		if (nbt.contains("hungerTimesFed")) {
			this.TIMES_FED = nbt.getIntArray("hungerTimesFed");
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
				this.setState(HungerState.FULLY_FED);
				this.horse.getDataManager().set(HungerState.ID, 4);
			}
		}
	}

	public void resetDaily() {
		this.horse.getDataManager().set(TOTAL_TIMES_FED, 0);
		this.state.resetCurrentPoints();
		Arrays.fill(this.TIMES_FED, 0);
	}

	public enum HungerState {

		STARVING(-1, -1),
		MALNOURISHED(72000, 15),
		HUNGRY(144000, 40),
		FED(168000, 15),
		FULLY_FED(180000, -1);

		public static final DataParameter<Integer> ID = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.VARINT);
		private int tickAmountChange;
		private int pointsRequired;
		private int currentPoints;
		private int currentTicks;
		private SWEMHorseEntityBase horse;
		HungerState(int tickAmountChange, int pointsRequired) {
			this.tickAmountChange = tickAmountChange;
			this.pointsRequired = pointsRequired;
			this.currentPoints = 0;
			this.currentTicks = 0;
		}

		public void setHorse(SWEMHorseEntityBase horse) {
			this.horse = horse;
		}

		public int getId() {
			return this.horse.getDataManager().get(ID);
		}

		public int getTickAmountChange() {
			return this.tickAmountChange;
		}

		public int getCurrentTicks() {
			return this.currentTicks;
		}

		public void setCurrentTicks(int currentTicks) {
			this.currentTicks = currentTicks;
		}

		public int getPointsRequired() {
			return this.pointsRequired;
		}

		public int getCurrentPoints() {
			return this.currentPoints;
		}

		public void setCurrentPoints(int points) {
			this.currentPoints = points;
		}

		public void resetCurrentPoints() {
			this.currentPoints = 0;
		}

	}
}
