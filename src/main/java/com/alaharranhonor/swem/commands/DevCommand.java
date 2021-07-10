package com.alaharranhonor.swem.commands;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
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
									ServerPlayerEntity player = ctx.getSource().getPlayerOrException();

									Entity riding = player.getVehicle();
									if (riding instanceof SWEMHorseEntityBase) {
										SWEMHorseEntityBase horse = (SWEMHorseEntityBase) riding;
										Inventory inv = horse.getHorseInventory();
										inv.setItem(0, new ItemStack(SWEMItems.WESTERN_BRIDLE_ORANGE.get()));
										inv.setItem(1, new ItemStack(SWEMItems.WESTERN_BLANKET_ORANGE.get()));
										inv.setItem(2, new ItemStack(SWEMItems.WESTERN_SADDLE_ORANGE.get()));
										inv.setItem(5, new ItemStack(SWEMItems.WESTERN_GIRTH_STRAP_ORANGE.get()));
									}

									ctx.getSource().sendSuccess(new StringTextComponent("[§bSWEM§f] Your horse has been tacked up sir! (With the accent)"), false);
									return 1;
								})
						)
						.then(Commands.literal("tame")
								.executes(ctx ->  {
									ServerPlayerEntity player = ctx.getSource().getPlayerOrException();
									List<SWEMHorseEntityBase> list = player.level.getEntitiesOfClass(SWEMHorseEntityBase.class, new AxisAlignedBB(player.position().x - 7.0D, player.position().y - 7.0D, player.position().z- 7.0D, player.blockPosition().getX() + 7.0D, player.blockPosition().getY() + 7.0D, player.blockPosition().getZ() + 7.0D));

									for(SWEMHorseEntityBase horse : list) {
										if (horse != null) {
											if (!horse.isTamed())
												horse.tameWithName(player);
										}
									}

									ctx.getSource().sendSuccess(new StringTextComponent("[§bSWEM§f] Your horse has been tamed for you sir! (With the accent)"), false);
									return 1;
								})
						)
						.then(Commands.literal("maxlevel")
								.executes(ctx ->  {
									ServerPlayerEntity player = ctx.getSource().getPlayerOrException();

									Entity riding = player.getVehicle();
									if (riding instanceof SWEMHorseEntityBase) {
										SWEMHorseEntityBase horse = (SWEMHorseEntityBase) riding;

										for (int i = 0; i < 4; i++) {
											horse.progressionManager.getAffinityLeveling().addXP(10000);
											horse.progressionManager.getJumpLeveling().addXP(10000);
											horse.progressionManager.getHealthLeveling().addXP(10000);
											horse.progressionManager.getSpeedLeveling().addXP(10000);
										}
									}

									ctx.getSource().sendSuccess(new StringTextComponent("[§bSWEM§f] Your horse has been maxed out sir! (With the accent)"), false);
									return 1;
								})
						)
						.then(Commands.literal("sethealth")
							.executes(ctx -> {
								Entity riding = ctx.getSource().getPlayerOrException().getVehicle();
								if (riding instanceof  SWEMHorseEntityBase) {
									SWEMHorseEntityBase horse = (SWEMHorseEntityBase) riding;

									horse.setHealth(10.0f);

									ctx.getSource().sendSuccess(new StringTextComponent("[SWEM] You're horse's health has been set to 10"), false);
									return 1;
								}
								return 0;
							})
						);
	}
}
