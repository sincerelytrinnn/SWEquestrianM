package com.alaharranhonor.swem.util;


/*
 * All Rights Reserved
 *
 * Copyright (c) 2021, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.armor.AmethystRidingBoots;
import com.alaharranhonor.swem.commands.DevCommand;
import com.alaharranhonor.swem.commands.SWEMCommand;
import com.alaharranhonor.swem.config.ConfigHelper;
import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.container.SaddlebagContainer;
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
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.text.*;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.Month;
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
				if (SWEMOreGen.SWEM_COBBLE_ORE == null) {
					SWEMOreGen.SWEM_COBBLE_ORE = OreGenUtils.buildOverWorldFeature(SWEMBlocks.STAR_WORM_COBBLE.get().defaultBlockState());
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
		public static void registerCommands(RegisterCommandsEvent event) {
			//event.getDispatcher().register(YeetCommand.register());
			event.getDispatcher().register(DevCommand.register());
			event.getDispatcher().register(SWEMCommand.register());
		}

		@SubscribeEvent
		public static void onKeyPress(InputEvent.KeyInputEvent event) {
			KeyBinding[] keyBindings = ClientEventHandlers.keyBindings;
			if (KEY_PRESS_COUNTER == 1) {

				if (event.getKey() == 'W' && event.getAction() == 0 && Minecraft.getInstance().player != null) {
					Entity check = Minecraft.getInstance().player.getVehicle();
					if (check instanceof SWEMHorseEntityBase) {
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
				}

				if (keyBindings[7].consumeClick()) {
					SWEMPacketHandler.INSTANCE.sendToServer(new SContainerPacket(0));
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
					if (entity instanceof SWEMHorseEntityBase && player.zza > 0) {
						SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
						SWEMPacketHandler.INSTANCE.sendToServer(new SendHorseSpeedChange(1, horse.getId()));
					}
				}

				if (keyBindings[1].consumeClick()) {
					// Decrement speed
					ClientPlayerEntity player = Minecraft.getInstance().player;
					Entity entity = player.getVehicle();
					if (entity instanceof SWEMHorseEntityBase && player.zza > 0) {
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
					/*
					Entity entity = Minecraft.getInstance().player.getVehicle();
					if (entity instanceof SWEMHorseEntityBase) {
						SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;

						if (!horse.isFlying() && horse.canFly())  {
							SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(10, horse.getId()));
						}
					}
					 */
				}

				KEY_PRESS_COUNTER = 0;
			} else {
				// TODO: FIGURE OUT WHY THIS EVENT IS BEING RUN TWICE, AND GET RID OF THIS UGLY STATIC COUNTER.
				KEY_PRESS_COUNTER++;
			}
		}


		// Update horse speed when dismounted, to a walk gait.
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

				if (horse.level.isClientSide) {
					SWEMPacketHandler.INSTANCE.sendToServer(new SHorseAnimationPacket(horse.getId(), 4));
				}
			}
		}

		// Doon't dismount player if the player/horse is flying.
		// This is the cause of desyncing when hitting shift while flying.
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

		// Check if the player can mount the horse.
		@SubscribeEvent
		public static void canEntityBeMounted(EntityMountEvent event) {
			if (!event.isMounting()) return;
			if (event.getEntityBeingMounted() == null) return;

			Entity entity = event.getEntityBeingMounted();
			if (entity instanceof SWEMHorseEntityBase) {
				SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
				if (event.getEntityMounting() instanceof PlayerEntity) {
					if (!horse.canMountPlayer((PlayerEntity) event.getEntityMounting())){
						event.setCanceled(true);
					}
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

		@SubscribeEvent
		public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
			if (event.getEntity() instanceof PlayerEntity && event.getEntity().level.isClientSide) {
				LocalDateTime time = LocalDateTime.now();
				if (time.getMonth() == Month.DECEMBER && time.getDayOfMonth() == 31) {

					IFormattableTextComponent hi = new StringTextComponent("[SWEM] Hi " + event.getEntity().getName().getString()).withStyle(TextFormatting.RED);
					IFormattableTextComponent content = new StringTextComponent("\n Us here at the SWEM team, hope you have a good new years! We hope you get a good start on 2022, and we thank you for helping out the project become a reality! Thank you for supporting us and happy new years! //legenden").setStyle(Style.EMPTY.withColor(Color.parseColor("#FF7F7F")));
					IFormattableTextComponent fireworks = new StringTextComponent("\n Now go out and set off some pretty fireworks!").setStyle(Style.EMPTY.withColor(Color.parseColor("#545454")));

					event.getEntity().sendMessage(hi.append(content).append(fireworks), Util.NIL_UUID);
				}
			}
		}



	}
}
