package com.alaharranhonor.swem.entities.needs;

import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.nbt.CompoundNBT;

public class NeedManager {

	private ThirstNeed thirst;
	private HungerNeed hunger;

	private SWEMHorseEntityBase horse;

	public NeedManager(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.thirst = new ThirstNeed(horse);
		this.hunger = new HungerNeed(horse);

	}

	public ThirstNeed getThirst() {
		return thirst;
	}

	public HungerNeed getHunger() {
		return hunger;
	}

	public void read(CompoundNBT nbt) {
		this.thirst.read(nbt);
		this.hunger.read(nbt);
	}

	public CompoundNBT write(CompoundNBT nbt) {
		nbt = this.thirst.write(nbt);
		nbt = this.hunger.write(nbt);
		return nbt;
	}

	// SERVER-SIDE ONLY
	public void tick() {
		if (ConfigHolder.SERVER.serverTickFoodNeed.get())
			this.hunger.tick();
		if (ConfigHolder.SERVER.serverTickWaterNeed.get())
			this.thirst.tick();
	}
}
