package com.alaharranhonor.swem.entities.progression.leveling;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

public class SpeedLeveling {

	private SWEMHorseEntityBase horse;

	private EntityDataManager dataManager;
	public static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.VARINT);
	public static final DataParameter<Float> XP = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.FLOAT);
	public static final DataParameter<Integer> MAX_LEVEL = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.VARINT);
	private float[] requiredXpArray = new float[]{500, 2000, 4000, 7000};
	private String[] levelNames = new String[]{"Speed I", "Speed II", "Speed III", "Speed IV", "Speed V"};

	public SpeedLeveling(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.dataManager = this.horse.getDataManager();
	}

	public boolean addXP(float amount) {
		if (this.getLevel() == this.getMaxLevel()) return false;
		this.dataManager.set(XP, this.dataManager.get(XP) + amount);
		return this.checkLevelUp();
	}

	public boolean checkLevelUp() {
		if (this.getXp() >= this.getRequiredXp() && this.getLevel() < this.getMaxLevel()) {
			this.levelUp();
			return true;
		} else {
			return false;
		}
	}

	public void levelUp() {
		float excessXP = this.getXp() - this.getRequiredXp();
		this.setLevel(this.getLevel() + 1);
		this.setXp(excessXP);
	}

	public int getLevel() {
		return this.dataManager.get(LEVEL);
	}

	public void setLevel(int level) {
		this.dataManager.set(LEVEL, level);
	}

	public int getMaxLevel() {
		return this.dataManager.get(MAX_LEVEL);
	}

	public void setMaxLevel(int max_level) {
		this.dataManager.set(MAX_LEVEL, max_level);
	}

	public float getXp() {
		return this.dataManager.get(XP);
	}

	public void setXp(float xp) {
		this.dataManager.set(XP, xp);
	}

	public float getRequiredXp() {
		if (this.getLevel() == this.getMaxLevel()) {
			return -1.0f;
		}
		return this.requiredXpArray[this.dataManager.get(LEVEL)];
	}

	public String getLevelName() {
		if (this.getLevel() == this.getMaxLevel()) {
			return this.levelNames[this.getMaxLevel() - 1];
		}
		return this.levelNames[this.dataManager.get(LEVEL)];
	}

	public void write(CompoundNBT compound) {
		compound.putInt("SpeedLevel", this.dataManager.get(LEVEL));
		compound.putFloat("SpeedXP", this.dataManager.get(XP));
		compound.putInt("SpeedMaxLevel", this.dataManager.get(MAX_LEVEL));
	}

	public void read(CompoundNBT compound) {
		if (compound.contains("SpeedLevel")) {
			this.setLevel(compound.getInt("SpeedLevel"));
		}
		if (compound.contains("SpeedXP")) {
			this.setXp(compound.getFloat("SpeedXP"));
		}
		if (compound.contains("SpeedMaxLevel")) {
			this.setMaxLevel(compound.getInt("SpeedMaxLevel"));
		}
	}
}
