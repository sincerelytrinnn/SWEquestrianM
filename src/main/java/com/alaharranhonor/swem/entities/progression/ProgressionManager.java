package com.alaharranhonor.swem.entities.progression;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.entities.progression.leveling.AffinityLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.HealthLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.JumpLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.SpeedLeveling;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.EntityDataManager;

import javax.annotation.Nullable;

public class ProgressionManager {

	private SWEMHorseEntityBase horse;
	private SpeedLeveling speedLeveling;
	private JumpLeveling jumpLeveling;
	private HealthLeveling healthLeveling;
	private AffinityLeveling affinityLeveling;

	public ProgressionManager(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.speedLeveling = new SpeedLeveling(horse);
		this.jumpLeveling = new JumpLeveling(horse);
		this.healthLeveling = new HealthLeveling(horse);
		this.affinityLeveling = new AffinityLeveling(horse);
	}

	public void write(CompoundNBT compound) {
		this.speedLeveling.write(compound);
		this.jumpLeveling.write(compound);
		this.healthLeveling.write(compound);
		this.affinityLeveling.write(compound);
	}

	public void read(CompoundNBT compound) {
		this.speedLeveling.read(compound);
		this.jumpLeveling.read(compound);
		this.healthLeveling.read(compound);
		this.affinityLeveling.read(compound);
	}

	public static void registerData(EntityDataManager dm) {
		SpeedLeveling.registerData(dm);
		JumpLeveling.registerData(dm);
		HealthLeveling.registerData(dm);
		AffinityLeveling.registerData(dm);
	}

	public void setDataManagers(@Nullable String identifier) {
		switch (identifier) {
			case "jump": {
				this.jumpLeveling.setDataManager();
				break;
			}
			case "speed": {
				this.speedLeveling.setDataManager();
				break;
			}
			case "health": {
				this.healthLeveling.setDataManager();
				break;
			}
			case "affinity": {
				this.affinityLeveling.setDataManager();
				break;
			}
			default: {
				this.speedLeveling.setDataManager();
				this.jumpLeveling.setDataManager();
				this.healthLeveling.setDataManager();
				this.affinityLeveling.setDataManager();
			}
		}
	}

	public SpeedLeveling getSpeedLeveling() {
		return speedLeveling;
	}

	public JumpLeveling getJumpLeveling() {
		return jumpLeveling;
	}

	public HealthLeveling getHealthLeveling() {
		return healthLeveling;
	}

	public AffinityLeveling getAffinityLeveling() {
		return affinityLeveling;
	}
}
