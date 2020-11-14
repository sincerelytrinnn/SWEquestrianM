package com.alaharranhonor.swem.entities.progression.leveling;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.EntityDataManager;

public interface ILeveling {
	int level = 0;
	int maxLevel = 0;
	float xp = 0.0f;
	float[] requiredXpArray = new float[0];
	String[] levelNames = new String[0];

	boolean addXP(float amount);

	boolean checkLevelUp();

	void levelUp();

	int getLevel();

	int getMaxLevel();

	float getXp();

	float getRequiredXp();

	String getLevelName();

	void write(CompoundNBT compound);

	void read(CompoundNBT compound);
}
