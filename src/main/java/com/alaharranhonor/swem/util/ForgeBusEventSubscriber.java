package com.alaharranhonor.swem.util;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.armor.AmethystRidingBoots;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.network.HorseFlyingMessage;
import com.alaharranhonor.swem.network.HorseStateChange;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.network.SendHorseSpeedChange;
import com.alaharranhonor.swem.world.gen.SWEMOreGen;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeBusEventSubscriber {

	public static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation(SWEM.MOD_ID, "textures/gui/icons.png");

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
					SWEMPacketHandler.INSTANCE.sendToServer(new SendHorseSpeedChange(1, horse.getEntityId()));
				}
			}

			if (keyBindings[1].isPressed()) {
				// Decrement speed
				ClientPlayerEntity player = Minecraft.getInstance().player;
				Entity entity = player.getRidingEntity();
				if (entity instanceof SWEMHorseEntityBase) {
					SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
					SWEMPacketHandler.INSTANCE.sendToServer(new SendHorseSpeedChange(0, horse.getEntityId()));
				}
			}

			if (keyBindings[2].isPressed()) {
				ClientPlayerEntity player = Minecraft.getInstance().player;
				Entity entity = player.getRidingEntity();
				if (entity instanceof SWEMHorseEntityBase) {
					SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
					ItemStack saddleBagStack = horse.getHorseInventory().getStackInSlot(7);
					if (saddleBagStack.isEmpty()) {
						player.sendStatusMessage(new TranslationTextComponent("swem.horse.status.no_saddle_bag"), true);
						return;
					}

					SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(6, horse.getEntityId()));
				}
			}

			if (Minecraft.getInstance().gameSettings.keyBindForward.isKeyDown()) {
				SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlyingMessage(3));
			}

			if (Minecraft.getInstance().gameSettings.keyBindRight.isKeyDown()) {
				Entity entity = Minecraft.getInstance().player.getRidingEntity();
				if (entity != null) {
					if (entity instanceof SWEMHorseEntityBase) {
						SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
						if (horse.isFlying()) {
							SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlyingMessage(6));
							horse.setRotation((horse.rotationYaw + 5) % 360.0F, horse.rotationPitch);
						}

					}
				}
			}

			if (Minecraft.getInstance().gameSettings.keyBindLeft.isKeyDown()) {
				Entity entity = Minecraft.getInstance().player.getRidingEntity();
				if (entity != null) {
					if (entity instanceof SWEMHorseEntityBase) {
						SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
						if (horse.isFlying()) {
							SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlyingMessage(5));
							horse.setRotation((horse.rotationYaw - 5) % 360.0F, horse.rotationPitch);
						}

					}
				}
			}

			if (Minecraft.getInstance().gameSettings.keyBindJump.isPressed()) {
				SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlyingMessage(1));
			}

			if (Minecraft.getInstance().gameSettings.keyBindDrop.isKeyDown()) {
				SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlyingMessage(4));
			}

			if (keyBindings[3].isPressed()) {
				SWEMPacketHandler.INSTANCE.sendToServer(new HorseFlyingMessage(2));
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

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onRenderHorseJumpBar(RenderGameOverlayEvent.Pre event) {
		if (event.getType() != RenderGameOverlayEvent.ElementType.JUMPBAR) return;

		MatrixStack stack = event.getMatrixStack();
		Minecraft.getInstance().getTextureManager().bindTexture(GUI_ICONS_LOCATION);
		ClientPlayerEntity player = Minecraft.getInstance().player;

		if (!player.isRidingHorse()) return;
		Entity entity = player.getRidingEntity();
		if (!(entity instanceof SWEMHorseEntityBase)) return;

		event.setCanceled(true);
		SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;

		int xPosition = event.getWindow().getScaledWidth() / 2 - 100;

		float f = player.getHorseJumpPower();
		int i = 201;
		int j = (int)(f * 200.0F);
		int level = horse.progressionManager.getJumpLeveling().getLevel();
		float modifier = ((level + 1.0F) / 5.0F);

		int amountToDraw = (int)(j * modifier);

		int k = event.getWindow().getScaledHeight() - 32 + 3;
		Minecraft.getInstance().ingameGUI.blit(stack, xPosition, k, 0, (5 * level), i, 5, 201, 30);
		if (j > 0) {
			Minecraft.getInstance().ingameGUI.blit(stack, xPosition + 1, k, 201 - ((level + 1) * 40), 25, amountToDraw, 5, 201, 30);
		}

	}

	@SubscribeEvent
	public static void onUserHurt(LivingHurtEvent event) {
		if (!(event.getEntity() instanceof PlayerEntity)) return;
		if (event.getSource() != DamageSource.FALL) return;
		PlayerEntity player = (PlayerEntity) event.getEntity();
		if (player.isSneaking()) return;

		ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.FEET);
		if (stack.getItem() instanceof AmethystRidingBoots) {
			event.setAmount(-1);
		}
	}
}
