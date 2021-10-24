package com.alaharranhonor.swem.entities.ai;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;

public class HorseAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
	private SWEMHorseEntityBase horse;
	public HorseAvoidEntityGoal(SWEMHorseEntityBase p_i46404_1_, Class<T> p_i46404_2_, float p_i46404_3_, double p_i46404_4_, double p_i46404_6_) {
		super(p_i46404_1_, p_i46404_2_, p_i46404_3_, p_i46404_4_, p_i46404_6_);
		this.horse = p_i46404_1_;
	}

	@Override
	public void start() {
		super.start();
		SWEMHorseEntityBase.HorseSpeed oldSpeed = this.horse.currentSpeed;
		this.horse.currentSpeed = SWEMHorseEntityBase.HorseSpeed.WALK;
		this.horse.updateSelectedSpeed(oldSpeed);
	}
}
