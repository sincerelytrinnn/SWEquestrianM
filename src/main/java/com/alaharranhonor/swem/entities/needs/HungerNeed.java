package com.alaharranhonor.swem.entities.needs;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.config.ServerConfig;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMItems;
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
					Ingredient.of(Items.CARROT),
					Ingredient.of(Items.APPLE),
					Ingredient.of(SWEMItems.OAT_BUSHEL.get()),
					Ingredient.of(SWEMItems.TIMOTHY_BUSHEL.get()),
					Ingredient.of(SWEMItems.ALFALFA_BUSHEL.get()),
					Ingredient.of(SWEMBlocks.QUALITY_BALE_ITEM.get()),
					Ingredient.of(Items.GRASS_BLOCK),
					Ingredient.of(SWEMItems.SUGAR_CUBE.get()),
					Ingredient.of(SWEMItems.SWEET_FEED.get())
			).collect(Collectors.toList()));

	private int[] POINTS_GIVEN = {1, 1, 5, 5, 5, 15, 1, 1, 15};
	private int[] TIMES_FED = new int[9];
	private int[] MAX_TIMES = {1, 1, 1, 4, 4, 1, -1, 1, 1};

	private int tickCounter;
	private int points;

	public static final DataParameter<Integer> TOTAL_TIMES_FED = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);

	public HungerNeed(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.setState(HungerState.FULLY_FED);
		this.tickCounter = 192_000; //192_000;
	}


	public void tick() {
		if (this.tickCounter == 0) return;
		this.tickCounter--;
		if (this.tickCounter <= this.state.getTickAmountChange() && this.state != HungerState.STARVING) {
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

		// Set the points.
		this.points += points;

		if (this.checkIncrement()) {
			this.incrementState();
		}

		horse.progressionManager.getHealthLeveling().addXP(points);
		return true;
	}

	public boolean checkIncrement() {
		return this.points >= this.getNextState().getPointsRequired();
	}

	public void incrementState() {
		if (this.state != HungerState.FULLY_FED) {
			this.points -= this.state.getPointsRequired();
			HungerState nextState = this.getNextState();
			this.setStateById(nextState.ordinal());
			if (nextState == HungerState.FULLY_FED) {
				this.tickCounter = 192_000;
			} else {
				this.tickCounter = getNextState().getTickAmountChange();
			}
		}
	}

	public int getItemIndex(ItemStack itemstack) {
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

	public int getTimesFed(int index) {
		return this.TIMES_FED[index];
	}

	public int getMaxTimesFed(int index) {
		return this.MAX_TIMES[index];
	}

	private int getPointsFromItem(int index) {
		return this.POINTS_GIVEN[index];
	}

	public int getTotalTimesFed() {
		return this.horse.getEntityData().get(TOTAL_TIMES_FED);
	}

	private void setTotalTimesFed(int amount) {
		this.horse.getEntityData().set(TOTAL_TIMES_FED, this.getTotalTimesFed() + amount);
	}

	public HungerState getState() {
		return this.state;
	}

	public HungerState getNextState() {
		int hungerId = this.state.getId() + 1;
		if (hungerId > 4) {
			hungerId = 4;
		}
		return HungerState.values()[hungerId];
	}

	public void setState(HungerState state) {
		this.state = state;
		this.state.setHorse(this.horse);
	}

	public CompoundNBT write(CompoundNBT nbt) {
		if (this.state != null) {
			nbt.putInt("hungerStateID", this.state.getId());
			nbt.putInt("hungerTick", this.tickCounter);
			nbt.putInt("hungerPoints", this.points);
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
		if (nbt.contains("hungerTick")) {
			int ticks = nbt.getInt("hungerTick");
			if (ticks == 0 && this.state != HungerState.STARVING) {
				ticks = getNextState().tickAmountChange;
			}
			this.tickCounter = ticks;
		}
		if (nbt.contains("hungerPoints")) {
			int points = nbt.getInt("hungerPoints");
			this.points = points;
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
				this.horse.getEntityData().set(HungerState.ID, id);
				break;
			}
			case 1: {
				this.setState(HungerState.MALNOURISHED);
				this.horse.getEntityData().set(HungerState.ID, id);
				break;
			}
			case 2: {
				this.setState(HungerState.HUNGRY);
				this.horse.getEntityData().set(HungerState.ID, id);
				break;
			}
			case 3: {
				this.setState(HungerState.FED);
				this.horse.getEntityData().set(HungerState.ID, id);
				break;
			}
			case 4: {
				this.setState(HungerState.FULLY_FED);
				this.horse.getEntityData().set(HungerState.ID, id);
				break;
			}
			default: {
				this.setState(HungerState.FULLY_FED);
				this.horse.getEntityData().set(HungerState.ID, 4);
			}
		}
	}

	public void resetDaily() {
		this.horse.getEntityData().set(TOTAL_TIMES_FED, 0);
		Arrays.fill(this.TIMES_FED, 0);
	}

	public enum HungerState {

		STARVING(-1, -1),
		MALNOURISHED(72_000, 15),
		HUNGRY(144_000, 40),
		FED(168_000, 15),
		FULLY_FED(180_000, -1);

		public static final DataParameter<Integer> ID = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);
		private int tickAmountChange;
		private int pointsRequired;
		private SWEMHorseEntityBase horse;
		HungerState(int tickAmountChange, int pointsRequired) {
			this.tickAmountChange = tickAmountChange;
			this.pointsRequired = pointsRequired;
		}

		public void setHorse(SWEMHorseEntityBase horse) {
			this.horse = horse;
		}

		public int getId() {
			return this.horse.getEntityData().get(ID);
		}

		public int getTickAmountChange() {
			return this.tickAmountChange * (ConfigHolder.SERVER.multiplayerHungerThirst.get() ? 72 : 1);
		}


		public int getPointsRequired() {
			return this.pointsRequired;
		}

	}
}
