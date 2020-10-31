package com.alaharranhonor.swem.util;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.world.gen.SWEMOreGen;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeBusEventSubscriber {

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onBiomeLoading(BiomeLoadingEvent event)
	{
		if (!SWEMOreGen.checkAndInitBiome(event)) {
			return;
		}
		if (event.getCategory() == Biome.Category.NETHER) {
			// Nether oregen
		} else {
			SWEMOreGen.generateOverworldOres(event);
		}
	}

	@SubscribeEvent
	public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
		if (event.getEntity() instanceof SWEMHorseEntityBase) {
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) event.getEntity();
			float jumpStrength = (float) event.getEntityLiving().getMotion().y;
			float jumpHeight = (float) (-0.1817584952 * ((float)Math.pow(jumpStrength, 3.0F)) + 3.689713992 * ((float)Math.pow(jumpStrength, 2.0F)) + 2.128599134 * jumpStrength - 0.343930367);
			if (jumpHeight > 6.0f) {
				horse.leveling.addXP(25.0f);
			} else if (jumpHeight > 4.0f) {
				horse.leveling.addXP(10.0f);
			} else if (jumpHeight > 2.0f) {
				horse.leveling.addXP(1.0f);
			}
		}
	}

}
