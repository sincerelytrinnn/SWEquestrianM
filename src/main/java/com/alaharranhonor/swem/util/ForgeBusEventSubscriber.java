package com.alaharranhonor.swem.util;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.world.gen.SWEMOreGen;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyBindingMap;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import static net.minecraftforge.forgespi.Environment.Keys.DIST;

@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeBusEventSubscriber {

	private static int KEY_PRESS_COUNTER = 0;

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
	public static void onKeyPress(InputEvent.KeyInputEvent event) {
		KeyBinding[] keyBindings = ClientEventBusSubscriber.keyBindings;
		if (KEY_PRESS_COUNTER == 1) {

			if (keyBindings[0].isPressed()) {
				// Increment Speed.
				ClientPlayerEntity player = Minecraft.getInstance().player;
				Entity entity = player.getRidingEntity();
				if (entity instanceof SWEMHorseEntityBase) {
					SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
					horse.incrementSpeed();
				}
			}

			if (keyBindings[1].isPressed()) {
				// Decrement speed
				ClientPlayerEntity player = Minecraft.getInstance().player;
				Entity entity = player.getRidingEntity();
				if (entity instanceof SWEMHorseEntityBase) {
					SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
					horse.decrementSpeed();
				}
			}

			KEY_PRESS_COUNTER = 0;
		} else {
			// TODO: FIGURE OUT WHY THIS EVENT IS BEING RUN TWICE, AND GET RID OF THIS UGLY STATIC COUNTER.
			KEY_PRESS_COUNTER++;
		}
	}
}
