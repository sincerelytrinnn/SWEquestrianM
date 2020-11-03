package com.alaharranhonor.swem.entities.progression.leveling;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

public class AffinityLeveling implements ILeveling{

	private SWEMHorseEntityBase horse;
	private EntityDataManager dataManager;
	public static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.VARINT);
	public static final DataParameter<Float> XP = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.FLOAT);
	int maxLevel = 12;
	float[] requiredXpArray = new float[]{500, 1000, 1500, 2000, 3000, 4000, 5000, 7000, 10000, 13000, 17000};
	String[] levelNames = new String[] {"Unwilling", "Reluctant", "Tolerant", "Indifferent", "Accepting",  "Willing",  "Committed", "Trusted",  "Friends",  "Best Friends", "Inseparable", "Bonded", };
	public AffinityLeveling(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.dataManager = this.horse.getDataManager();
	}
	@Override
	public boolean addXP(float amount) {
		this.setXp(this.getXp() + amount);
		return this.checkLevelUp();
	}

	@Override
	public boolean checkLevelUp() {
		if (this.getXp() >= this.getRequiredXp() && this.getLevel() < this.maxLevel) {
			this.levelUp();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void levelUp() {
		float excessXP = this.getXp() - this.getRequiredXp();
		this.setLevel(this.getLevel() + 1);
		this.setXp(excessXP);
	}

	@Override
	public int getLevel() {
		return this.dataManager.get(LEVEL);
	}

	public void setLevel(int level) {
		this.dataManager.set(LEVEL, level);
	}


	@Override
	public int getMaxLevel() {
		return this.maxLevel;
	}

	@Override
	public float getXp() {
		return this.dataManager.get(XP);
	}

	public void setXp(float xp) {
		this.dataManager.set(XP, xp);
	}

	@Override
	public float getRequiredXp() {
		return this.requiredXpArray[this.dataManager.get(LEVEL)];
	}

	@Override
	public String getLevelName() {
		return this.levelNames[this.dataManager.get(LEVEL)];
	}

	@Override
	public void write(CompoundNBT compound) {
		compound.putInt("AffinityLevel", this.dataManager.get(LEVEL));
		compound.putFloat("AffinityXP", this.dataManager.get(XP));
	}

	@Override
	public void read(CompoundNBT compound) {
		if (compound.contains("AffinityLevel")) {
			this.setLevel(compound.getInt("AffinityLevel"));
		}
		if (compound.contains("AffinityXP")) {
			this.setXp(compound.getFloat("AffinityXP"));
		}
	}
}
