package com.alaharranhonor.swem.entities.progression.leveling;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

public class HealthLeveling implements ILeveling{

	private SWEMHorseEntityBase horse;
	private EntityDataManager dataManager;
	public static final DataParameter<Integer> LEVEL = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);
	public static final DataParameter<Float> XP = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.FLOAT);
	private float[] requiredXpArray = new float[]{500, 2000, 4000, 7000};
	private String[] levelNames = new String[] {"Health I", "Health II", "Health III", "Health IV", "Health V"};
	public HealthLeveling(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.dataManager = this.horse.getEntityData();
	}
	@Override
	public boolean addXP(float amount) {
		if (this.getLevel() == this.getMaxLevel()) return false;
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
		return 4;
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
		if (this.getLevel() == this.getMaxLevel()) {
			return -1.0f;
		}
		return this.requiredXpArray[this.dataManager.get(LEVEL)];
	}

	@Override
	public String getLevelName() {
		return this.levelNames[this.dataManager.get(LEVEL)];
	}

	@Override
	public void write(CompoundNBT compound) {
		compound.putInt("HealthLevel", this.dataManager.get(LEVEL));
		compound.putFloat("HealthXP", this.dataManager.get(XP));
	}

	@Override
	public void read(CompoundNBT compound) {
		if (compound.contains("HealthLevel")) {
			this.setLevel(compound.getInt("HealthLevel"));
		}
		if (compound.contains("HealthXP")) {
			this.setXp(compound.getFloat("HealthXP"));
		}
	}
}
