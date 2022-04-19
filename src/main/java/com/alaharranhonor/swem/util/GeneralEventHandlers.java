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
import com.alaharranhonor.swem.blocks.HitchingPostBase;
import com.alaharranhonor.swem.blocks.LeadAnchorBlock;
import com.alaharranhonor.swem.commands.DevCommand;
import com.alaharranhonor.swem.commands.SWEMCommand;
import com.alaharranhonor.swem.config.ConfigHelper;
import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.network.*;
import com.alaharranhonor.swem.tools.AmethystSword;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import com.alaharranhonor.swem.world.gen.OreGenUtils;
import com.alaharranhonor.swem.world.gen.SWEMOreGen;
import com.alaharranhonor.swem.world.structure.SWEMConfiguredStructures;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.text.*;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GeneralEventHandlers {

	/**
	 * On mod config event.
	 *
	 * @param event the event
	 */
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

				ConfigHelper.bakeServer();
			}
		}
	}

	@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	static class ForgeBusHandlers {


		private static int KEY_PRESS_COUNTER = 0;
		private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);


		/**
		 * On biome loading.
		 *
		 * @param event the event
		 */
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

		/**
		 * Register commands.
		 *
		 * @param event the event
		 */
		@SubscribeEvent
		public static void registerCommands(RegisterCommandsEvent event) {
			//event.getDispatcher().register(YeetCommand.register());
			event.getDispatcher().register(DevCommand.register());
			event.getDispatcher().register(SWEMCommand.register());

			PermissionAPI.registerNode("command.swem.reset_gallop", DefaultPermissionLevel.OP, "Gives permission to reset the gallop cooldown");
			PermissionAPI.registerNode("command.swem.set_gallop_time", DefaultPermissionLevel.OP, "Gives permission to set the max gallop time");
		}



		/**
		 * On key press.
		 *
		 * @param event the event
		 */
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
								if (Minecraft.getInstance().options.keyUp.isDown()) {
									return;
								} else {
									SWEMPacketHandler.INSTANCE.sendToServer(new SendHorseSpeedChange(2, check.getId()));
								}
							}
						}, 105, TimeUnit.MILLISECONDS);
					}
				}

				if (keyBindings[2].consumeClick()) {
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


		/**
		 * Entity mount.
		 *
		 * @param event the event
		 */
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

		/**
		 * On entity mount event.
		 *
		 * @param event the event
		 */
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

		/**
		 * Can entity be mounted.
		 *
		 * @param event the event
		 */
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

		/**
		 * On entity interact.
		 *
		 * @param event the event
		 */
		@SubscribeEvent
		public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
			if (event.getWorld().isClientSide) {
				if (Minecraft.getInstance().options.keySprint.isDown())
					SWEMPacketHandler.INSTANCE.sendToServer(new CMountEntityPacket(event.getTarget()));
			}
		}


		/**
		 * On user hurt.
		 *
		 * @param event the event
		 */
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

		/**
		 * Amethyst sword reduced damage.
		 *
		 * @param event the event
		 */
		@SubscribeEvent
		public static void amethystSwordReducedDamage(LivingHurtEvent event) {
			if (!(event.getEntityLiving() instanceof SWEMHorseEntityBase)) return;
			if (!(event.getSource().getEntity() instanceof PlayerEntity)) return;
			PlayerEntity player = (PlayerEntity) event.getSource().getEntity();
			if (player.getMainHandItem().getItem() instanceof AmethystSword || player.getOffhandItem().getItem() instanceof AmethystSword) {
				event.setAmount(event.getAmount() * 0.25f);
			}
		}

		/**
		 * Lead with bridle reins.
		 *
		 * @param event the event
		 */
		@SubscribeEvent
		public static void leadWithBridleReins(PlayerInteractEvent.EntityInteract event) {
			if (!(event.getTarget() instanceof SWEMHorseEntityBase)) return;
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) event.getTarget();
			if (horse.getLeashHolder() == event.getPlayer() && horse.isBridleLeashed()) {
				horse.setBridleLeashed(false);
				horse.dropLeash(true, false);
				event.setCancellationResult(ActionResultType.CONSUME);
				event.setCanceled(true);
				return;
			}
		}

		/**
		 * Reset hitching post custom knot.
		 *
		 * @param event the event
		 */
		@SubscribeEvent
		public static void resetHitchingPostCustomKnot(PlayerInteractEvent.EntityInteract event) {
			if (!(event.getTarget() instanceof LeashKnotEntity)) return;
			if (event.getWorld().getBlockState(event.getPos()).getBlock() instanceof HitchingPostBase) {
				event.getWorld().setBlock(event.getPos(), event.getWorld().getBlockState(event.getPos()).setValue(HitchingPostBase.CUSTOM_LEAD, false), 3);
				return;
			}

			if (event.getWorld().getBlockState(event.getPos()).getBlock() instanceof LeadAnchorBlock) {
				event.getTarget().remove();
				event.getWorld().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 3);
				if (!event.getPlayer().isCreative()) {
					event.getWorld().addFreshEntity(new ItemEntity(event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), new ItemStack(SWEMItems.LEAD_ANCHOR.get())));
				}
				event.setCanceled(true);
			}

		}


		/**
		 * New year message.
		 *
		 * @param event the event
		 */
		@SubscribeEvent
		public static void newYearMessage(EntityJoinWorldEvent event) {
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

		/**
		 * Hide lead knot entity.
		 *
		 * @param event the event
		 */
		@SubscribeEvent
		public static void hideLeadKnotEntity(EntityJoinWorldEvent event) {
			if (event.getEntity() instanceof LeashKnotEntity && !event.getEntity().level.isClientSide) {
				if (event.getWorld().getBlockState(event.getEntity().blockPosition()).getBlock() instanceof HitchingPostBase || event.getWorld().getBlockState(event.getEntity().blockPosition()).getBlock() instanceof LeadAnchorBlock) {
					event.getEntity().setInvisible(true);
				}
			}
		}

		@SubscribeEvent
		public static void onHorseJoin(EntityJoinWorldEvent event) {
			if (event.getEntity() instanceof SWEMHorseEntityBase) {
				SWEM.setPosForHorse(event.getEntity().getUUID(), event.getEntity().blockPosition());
			}
		}

		@SubscribeEvent
		public static void onHorseLeave(EntityLeaveWorldEvent event) {
			if (event.getEntity() instanceof SWEMHorseEntityBase) {
				SWEM.setPosForHorse(event.getEntity().getUUID(), event.getEntity().blockPosition());
			}
		}

		@SubscribeEvent
		public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
			if (event.getEntity() instanceof PlayerEntity) {
				if (event.getEntity().isPassenger()) {
					event.getEntity().stopRiding();
				}
			}
		}



	}
}
