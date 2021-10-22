package com.alaharranhonor.swem.util;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.armor.AmethystRidingBoots;
import com.alaharranhonor.swem.commands.DevCommand;
import com.alaharranhonor.swem.commands.SWEMCommand;
import com.alaharranhonor.swem.config.ConfigHelper;
import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.entities.RiderEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.entity.render.RiderGeoRenderer;
import com.alaharranhonor.swem.items.SWEMSpawnEggItem;
import com.alaharranhonor.swem.network.*;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMEntities;
import com.alaharranhonor.swem.world.gen.OreGenUtils;
import com.alaharranhonor.swem.world.gen.SWEMOreGen;
import com.alaharranhonor.swem.world.structure.SWEMConfiguredStructures;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GeneralEventHandlers {

	@SubscribeEvent
	public static void onModConfigEvent(ModConfig.ModConfigEvent event) {

		ModConfig config = event.getConfig();
		if (config.getModId().equals(SWEM.MOD_ID)) {
			if (config.getSpec() == ConfigHolder.SERVER_SPEC) {
				if (SWEMOreGen.AMETHYST_ORE == null) {
					SWEMOreGen.AMETHYST_ORE = OreGenUtils.buildOverWorldFeature(SWEMBlocks.AMETHYST_ORE.get().defaultBlockState());
				}
				if (SWEMOreGen.CANTAZARITE_ORE == null) {
					SWEMOreGen.CANTAZARITE_ORE = OreGenUtils.buildOverWorldFeature(SWEMBlocks.CANTAZARITE_ORE.get().defaultBlockState());
				}
				if (SWEMOreGen.SWLM_COBBLE_ORE == null) {
					SWEMOreGen.SWLM_COBBLE_ORE = OreGenUtils.buildOverWorldFeature(SWLRegistryHandler.STAR_WORM_COBBLE.get().defaultBlockState());
				}
				ConfigHelper.bakeServer(config);
			}
		}
	}

	@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	static class ForgeBusHandlers {


		private static int KEY_PRESS_COUNTER = 0;
		private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);


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

			if (event.getCategory() == Biome.Category.PLAINS) {
				event.getGeneration().getStructures().add(() -> SWEMConfiguredStructures.CONFIGURED_BARN);
			}
		}

		@SubscribeEvent
		public static void entityMount(EntityMountEvent event) {
			if (event.isMounting()) return;

			if (event.getEntityBeingMounted() == null) return;

			Entity entity = event.getEntityBeingMounted();

			if (entity instanceof SWEMHorseEntityBase) {
				SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
				SWEMHorseEntityBase.HorseSpeed oldSpeed = horse.currentSpeed;
				horse.currentSpeed = SWEMHorseEntityBase.HorseSpeed.WALK;
				horse.updateSelectedSpeed(oldSpeed);
			}
		}

		@SubscribeEvent
		public static void registerCommands(RegisterCommandsEvent event) {
			//event.getDispatcher().register(YeetCommand.register());
			event.getDispatcher().register(DevCommand.register());
			event.getDispatcher().register(SWEMCommand.register());
		}

		@SubscribeEvent
		public static void onKeyPress(InputEvent.KeyInputEvent event) {
			KeyBinding[] keyBindings = ClientEventHandlers.keyBindings;
			if (KEY_PRESS_COUNTER == 1) {

				Entity check = Minecraft.getInstance().player.getVehicle();
				if (event.getKey() == 'W' && event.getAction() == 0 && check instanceof SWEMHorseEntityBase) {
					// 'W' KEy was released start the 1 second timer.
					executor.schedule(new Runnable() {
						@Override
						public void run() {
							System.out.println("Running scheduled");
							if (Minecraft.getInstance().options.keyUp.isDown()) {
								return;
							} else {
								SWEMPacketHandler.INSTANCE.sendToServer(new SendHorseSpeedChange(2, check.getId()));
							}
						}
					}, 105, TimeUnit.MILLISECONDS);
				}


				// TODO: Remove once speed has been confirmed.
				if (keyBindings[6].consumeClick()) {
					ClientPlayerEntity player = Minecraft.getInstance().player;
					Entity entity = player.getVehicle();
					if (entity instanceof SWEMHorseEntityBase) {
						SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
						player.sendMessage(new StringTextComponent("Speed: " + horse.getAttributeValue(Attributes.MOVEMENT_SPEED)), UUID.randomUUID());
						player.sendMessage(new StringTextComponent("Base Value: " + horse.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue()), UUID.randomUUID());
					}
				}

				if (keyBindings[0].consumeClick()) {
					// Increment Speed.
					ClientPlayerEntity player = Minecraft.getInstance().player;
					Entity entity = player.getVehicle();
					if (entity instanceof SWEMHorseEntityBase) {
						SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
						SWEMPacketHandler.INSTANCE.sendToServer(new SendHorseSpeedChange(1, horse.getId()));
					}
				}

				if (keyBindings[1].consumeClick()) {
					// Decrement speed
					ClientPlayerEntity player = Minecraft.getInstance().player;
					Entity entity = player.getVehicle();
					if (entity instanceof SWEMHorseEntityBase) {
						SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
						SWEMPacketHandler.INSTANCE.sendToServer(new SendHorseSpeedChange(0, horse.getId()));
					}
				}

				if (keyBindings[2].consumeClick()) {
					ClientPlayerEntity player = Minecraft.getInstance().player;
					Entity entity = player.getVehicle();
					if (entity instanceof SWEMHorseEntityBase) {
						SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
						ItemStack saddleBagStack = horse.getHorseInventory().getItem(7);
						if (saddleBagStack.isEmpty()) {
							player.displayClientMessage(new TranslationTextComponent("swem.horse.status.no_saddle_bag"), true);
							return;
						}

						SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(6, horse.getId()));
					}
				}

				if (keyBindings[4].consumeClick()) {
					int value = ConfigHolder.CLIENT.wingsTransparency.get();
					value--;
					if (value < 0) {
						value = 2;
					}
					ConfigHolder.CLIENT.wingsTransparency.set(value);
				}

				if (keyBindings[3].consumeClick()) {
					Entity entity = Minecraft.getInstance().player.getVehicle();
					if (entity instanceof SWEMHorseEntityBase) {
						SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;

						if (!horse.isFlying())  {
							SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(10, horse.getId()));
						}
					}
				}

				KEY_PRESS_COUNTER = 0;
			} else {
				// TODO: FIGURE OUT WHY THIS EVENT IS BEING RUN TWICE, AND GET RID OF THIS UGLY STATIC COUNTER.
				KEY_PRESS_COUNTER++;
			}
		}

		@SubscribeEvent
		public static void onEntityMountEvent(EntityMountEvent event) {
			if (event.isMounting()) return;
			if (event.getEntityBeingMounted() == null) return;

			Entity entity = event.getEntityBeingMounted();
			if (entity instanceof SWEMHorseEntityBase) {
				SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
				if (horse.isFlying()) {
					event.setCanceled(true);
				}
			}
		}

		@SubscribeEvent
		public static void canEntityBeMounted(EntityMountEvent event) {
			if (!event.isMounting()) return;
			if (event.getEntityBeingMounted() == null) return;

			Entity entity = event.getEntityBeingMounted();
			if (entity instanceof SWEMHorseEntityBase) {
				SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;

				if (!horse.canMountPlayer((PlayerEntity) event.getEntityMounting())){
					event.setCanceled(true);
				}
			}
		}

		@SubscribeEvent
		public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
			if (event.getWorld().isClientSide) {
				if (Minecraft.getInstance().options.keySprint.isDown())
					SWEMPacketHandler.INSTANCE.sendToServer(new CMountEntityPacket(event.getTarget()));
			}
		}


		@SubscribeEvent
		public static void onUserHurt(LivingHurtEvent event) {
			if (!(event.getEntity() instanceof PlayerEntity)) return;
			if (event.getSource() != DamageSource.FALL) return;
			PlayerEntity player = (PlayerEntity) event.getEntity();
			if (player.isShiftKeyDown()) return;

			ItemStack stack = player.getItemBySlot(EquipmentSlotType.FEET);
			if (stack.getItem() instanceof AmethystRidingBoots) {
				event.setAmount(-1);
			}
		}



	}
}
