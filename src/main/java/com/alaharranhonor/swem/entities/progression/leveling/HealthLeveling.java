package com.alaharranhonor.swem.entities.progression.leveling;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

public class HealthLeveling implements ILeveling{

	private SWEMHorseEntityBase horse;
	public static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.VARINT);
	public static final DataParameter<Float> XP = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.FLOAT);
	int level = 0;
	int maxLevel = 5;
	float xp = 0.0f;
	float[] requiredXpArray = new float[]{500, 2000, 4000, 7000};
	String[] levelNames = new String[] {"Health I", "Health II", "Health III", "Health IV", "Health V"};
	public HealthLeveling(SWEMHorseEntityBase horse) {
		this.horse = horse;
	}
	@Override
	public boolean addXP(float amount) {
		this.xp += amount;
		return this.checkLevelUp();
	}

	@Override
	public boolean checkLevelUp() {
		if (this.xp >= this.getRequiredXp() && this.level < this.maxLevel) {
			this.levelUp();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void levelUp() {
		float excessXP = this.xp - this.getRequiredXp();
		this.level++;
		this.xp = excessXP;
	}

	@Override
	public int getLevel() {
		return this.level;
	}

	@Override
	public int getMaxLevel() {
		return this.maxLevel;
	}

	@Override
	public float getXp() {
		return this.horse.getDataManager().get(XP);
	}

	@Override
	public float getRequiredXp() {
		return this.requiredXpArray[this.horse.getDataManager().get(LEVEL)];
	}

	@Override
	public String getLevelName() {
		return this.levelNames[this.horse.getDataManager().get(LEVEL)];
	}

	@Override
	public void write(CompoundNBT compound) {
		this.setDataManager();
		compound.putInt("HealthLevel", this.horse.getDataManager().get(LEVEL));
		compound.putFloat("HealthXP", this.horse.getDataManager().get(XP));
	}

	@Override
	public void read(CompoundNBT compound) {
		if (compound.contains("HealthLevel")) {
			this.level = compound.getInt("HealthLevel");
		}
		if (compound.contains("HealthXP")) {
			this.xp = compound.getFloat("HealthXP");
		}
		this.setDataManager();
	}

	public void setDataManager() {
		EntityDataManager dm = this.horse.getDataManager();
		dm.set(LEVEL, this.level);
		dm.set(XP, this.xp);
	}

	public static void registerData(EntityDataManager dm) {
		dm.register(LEVEL, 0);
		dm.register(XP, 0.0f);
	}
}
