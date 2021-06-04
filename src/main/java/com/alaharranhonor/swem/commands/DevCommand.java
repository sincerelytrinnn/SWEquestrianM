package com.alaharranhonor.swem.commands;

import com.alaharranhonor.swem.entities.SWEMHorseEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.initialization.SWEMItems;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class DevCommand {

	public static LiteralArgumentBuilder<CommandSource> register() {
		return
				Commands.literal("dev")
						.then(Commands.literal("tackup")
								.executes(ctx ->  {
									ServerPlayerEntity player = ctx.getSource().asPlayer();

									Entity riding = player.getRidingEntity();
									if (riding instanceof SWEMHorseEntityBase) {
										SWEMHorseEntityBase horse = (SWEMHorseEntityBase) riding;
										Inventory inv = horse.getHorseInventory();
										inv.setInventorySlotContents(0, new ItemStack(SWEMItems.WESTERN_BRIDLE_ORANGE.get()));
										inv.setInventorySlotContents(1, new ItemStack(SWEMItems.WESTERN_BLANKET_ORANGE.get()));
										inv.setInventorySlotContents(2, new ItemStack(SWEMItems.WESTERN_SADDLE_ORANGE.get()));
										inv.setInventorySlotContents(5, new ItemStack(SWEMItems.WESTERN_GIRTH_STRAP_ORANGE.get()));
									}

									ctx.getSource().sendFeedback(new StringTextComponent("[§bSWEM§f] Your horse has been tacked up sir! (With the accent)"), false);
									return 1;
								})
						)
						.then(Commands.literal("tame")
								.executes(ctx ->  {
									ServerPlayerEntity player = ctx.getSource().asPlayer();
									List<SWEMHorseEntityBase> list = player.world.getEntitiesWithinAABB(SWEMHorseEntityBase.class, new AxisAlignedBB(player.getPosX() - 7.0D, player.getPosY() - 7.0D, player.getPosZ() - 7.0D, player.getPosX() + 7.0D, player.getPosY() + 7.0D, player.getPosZ() + 7.0D));

									for(SWEMHorseEntityBase horse : list) {
										if (horse != null) {
											if (!horse.isTame())
												horse.setTamedBy(player);
										}
									}

									ctx.getSource().sendFeedback(new StringTextComponent("[§bSWEM§f] Your horse has been tamed for you sir! (With the accent)"), false);
									return 1;
								})
						)
						.then(Commands.literal("maxlevel")
								.executes(ctx ->  {
									ServerPlayerEntity player = ctx.getSource().asPlayer();

									Entity riding = player.getRidingEntity();
									if (riding instanceof SWEMHorseEntityBase) {
										SWEMHorseEntityBase horse = (SWEMHorseEntityBase) riding;

										for (int i = 0; i < 4; i++) {
											horse.progressionManager.getAffinityLeveling().addXP(10000);
											horse.progressionManager.getJumpLeveling().addXP(10000);
											horse.progressionManager.getHealthLeveling().addXP(10000);
											horse.progressionManager.getSpeedLeveling().addXP(10000);
										}
									}

									ctx.getSource().sendFeedback(new StringTextComponent("[§bSWEM§f] Your horse has been maxed out sir! (With the accent)"), false);
									return 1;
								})
						)
						.then(Commands.literal("sethealth")
							.executes(ctx -> {
								Entity riding = ctx.getSource().asPlayer().getRidingEntity();
								if (riding instanceof  SWEMHorseEntityBase) {
									SWEMHorseEntityBase horse = (SWEMHorseEntityBase) riding;

									horse.setHealth(10.0f);

									ctx.getSource().sendFeedback(new StringTextComponent("[SWEM] You're horse's health has been set to 10"), false);
									return 1;
								}
								return 0;
							})
						);
	}
}
