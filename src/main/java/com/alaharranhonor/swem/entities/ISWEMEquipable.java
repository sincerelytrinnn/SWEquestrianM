package com.alaharranhonor.swem.entities;

import net.minecraft.entity.IEquipable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;

import javax.annotation.Nullable;

public interface ISWEMEquipable extends IEquipable {
	boolean isSaddleable();

	void equipSaddle(@Nullable SoundCategory p_230266_1_, ItemStack stack);

	boolean isHorseSaddled();

	boolean hasAdventureSaddle();

	boolean hasBlanket();

	boolean hasBreastCollar();

	boolean hasHalter();

	boolean hasGirthStrap();

	boolean hasLegWraps();

	boolean canEquipSaddle();
	boolean canEquipGirthStrap();

	boolean canEquipArmor();
}
