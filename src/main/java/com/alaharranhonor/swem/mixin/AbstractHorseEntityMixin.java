package com.alaharranhonor.swem.mixin;

import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(AbstractHorseEntity.class)
public class AbstractHorseEntityMixin {

	// Lnet/minecraft/entity/passive/horse/AbstractHorseEntity;generateRandomJumpStrength()D

	@Inject(at = @At("HEAD"), method = "generateRandomJumpStrength()D", cancellable = true)
	public void generateRandomJumpStrength(CallbackInfoReturnable<Double> callback) {
		Random random = new Random();
		double jump = (double)0.4F + Math.min(random.nextDouble(), 0.7D) * 0.2D + Math.min(random.nextDouble(), 0.7D) * 0.2D + Math.min(random.nextDouble(), 0.7D) * 0.2D;
		callback.setReturnValue(jump);
		callback.cancel();
	}
}
