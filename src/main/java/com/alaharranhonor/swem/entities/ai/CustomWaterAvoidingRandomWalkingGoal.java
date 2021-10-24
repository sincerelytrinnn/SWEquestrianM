package com.alaharranhonor.swem.entities.ai;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;

public class CustomWaterAvoidingRandomWalkingGoal extends WaterAvoidingRandomWalkingGoal {
	private SWEMHorseEntityBase horse;
	public CustomWaterAvoidingRandomWalkingGoal(SWEMHorseEntityBase p_i47301_1_, double p_i47301_2_) {
		super(p_i47301_1_, p_i47301_2_);
		this.horse = p_i47301_1_;
	}

	@Override
	public void start() {
		super.start();
		SWEMHorseEntityBase.HorseSpeed oldSpeed = this.horse.currentSpeed;
		this.horse.currentSpeed = SWEMHorseEntityBase.HorseSpeed.WALK;
		this.horse.updateSelectedSpeed(oldSpeed);

	}
}
