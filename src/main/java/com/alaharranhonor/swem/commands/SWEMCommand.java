package com.alaharranhonor.swem.commands;


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

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.network.SHorseFriendPacket;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.impl.SummonCommand;
import net.minecraft.command.impl.WeatherCommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.server.command.EnumArgument;

import java.util.List;
import java.util.UUID;

public class SWEMCommand {

	public static LiteralArgumentBuilder<CommandSource> register() {
		return
			Commands.literal("swem")
				.then(Commands.literal("horse")
					.then(Commands.literal("addfriend")
						.then(Commands.argument("player", EntityArgument.player())
							.executes(ctx -> {
								ServerPlayerEntity player = EntityArgument.getPlayer(ctx, "player");

								Entity riding = ctx.getSource().getPlayerOrException().getVehicle();
								if (riding instanceof SWEMHorseEntityBase) {
									SWEMHorseEntityBase horse = (SWEMHorseEntityBase) riding;
									horse.addAllowedUUID(player.getUUID());
									SWEMPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> horse), new SHorseFriendPacket(player.getUUID(), horse.getId(), 1));
								}

								ctx.getSource().sendSuccess(new StringTextComponent("[SWEM] You have added " + player.getDisplayName().getString() + " to your allowed list, on " + riding.getDisplayName().getString() + "."), false);
								return 1;
							})))

					.then(Commands.literal("removefriend")
						.then(Commands.argument("player", EntityArgument.player())
							.executes(ctx -> {
								ServerPlayerEntity player = EntityArgument.getPlayer(ctx, "player");

								Entity riding = ctx.getSource().getPlayerOrException().getVehicle();
								if (riding instanceof SWEMHorseEntityBase) {
									SWEMHorseEntityBase horse = (SWEMHorseEntityBase) riding;
									horse.removeAllowedUUID(player.getUUID());
									SWEMPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> horse), new SHorseFriendPacket(player.getUUID(), horse.getId(), 2));
								}

								ctx.getSource().sendSuccess(new StringTextComponent("[SWEM] You have removed " + player.getDisplayName().getString() + " from your allowed list, on " + riding.getDisplayName().getString() + "."), false);
								return 1;
							})))
					.then(Commands.literal("transfer")
						.then(Commands.argument("player", EntityArgument.player())
							.executes(ctx -> {
								ServerPlayerEntity player = EntityArgument.getPlayer(ctx, "player");

								Entity riding = ctx.getSource().getPlayerOrException().getVehicle();
								if (riding instanceof SWEMHorseEntityBase) {
									SWEMHorseEntityBase horse = (SWEMHorseEntityBase) riding;
									if (!horse.getOwnerUUID().equals(player.getUUID()) && !player.hasPermissions(2)) {
										ctx.getSource().sendFailure(new StringTextComponent("[SWEM] You can't transfer other peoples horses." ));
										return -1;
									}
									horse.ejectPassengers();
									horse.transferHorse(player);
									SWEMPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> horse), new SHorseFriendPacket(UUID.randomUUID(), horse.getId(), 3));
								}

								ctx.getSource().sendSuccess(new StringTextComponent("[SWEM] You have transferred " + riding.getDisplayName().getString() + " to " + player.getDisplayName().getString() + "."), false);
								return 1;
							})))
				).then(Commands.literal("listall")
					.requires((p_198868_0_) -> p_198868_0_.hasPermission(2))
					.executes(ctx -> {
						ServerPlayerEntity player = ctx.getSource().getPlayerOrException();

						player.getLevel().getEntities().forEach((entity) -> {
							if (entity instanceof SWEMHorseEntityBase) {
								SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
								player.sendMessage(new StringTextComponent(horse.getDisplayName().getString() + " - Owner: " + horse.getOwnerDisplayName().getString() + " | X: " + horse.getX() + " - Z: " + horse.getZ()), UUID.randomUUID());
							}
						});
						return 1;
					})
				).then(Commands.literal("lowerlevel")
					.requires((player) -> player.hasPermission(2))
					.then(Commands.argument("levelToSet", IntegerArgumentType.integer())
						.then(Commands.argument("skill", EnumArgument.enumArgument(Skills.class))
							.executes((ctx) -> {
								Entity vehicle = ctx.getSource().getPlayerOrException().getVehicle();
								if (vehicle instanceof SWEMHorseEntityBase) {
									SWEMHorseEntityBase horse = (SWEMHorseEntityBase) vehicle;
									int levelToSet = IntegerArgumentType.getInteger(ctx, "levelToSet") - 1;
									Skills skill = ctx.getArgument("skill", Skills.class);

									switch (skill) {
										case JUMP: {
											if (levelToSet > -1 && levelToSet < 6) {
												horse.progressionManager.getJumpLeveling().setXp(0);
												horse.progressionManager.getJumpLeveling().setLevel(levelToSet);
												ctx.getSource().sendSuccess(new StringTextComponent("The jump level on the horse has been set to: " + levelToSet), false);
											} else
												ctx.getSource().sendSuccess(new StringTextComponent("Incorrect level range."), false);

											break;
										}
										case SPEED: {
											if (levelToSet > -1 && levelToSet < 6) {
												horse.progressionManager.getSpeedLeveling().setXp(0);
												horse.progressionManager.getSpeedLeveling().setLevel(levelToSet);
												ctx.getSource().sendSuccess(new StringTextComponent("The speed level on the horse has been set to: " + levelToSet), false);
											} else
												ctx.getSource().sendSuccess(new StringTextComponent("Incorrect level range."), false);

											break;
										}
										case HEALTH: {
											if (levelToSet > -1 && levelToSet < 6) {
												horse.progressionManager.getHealthLeveling().setXp(0);
												horse.progressionManager.getHealthLeveling().setLevel(levelToSet);
												ctx.getSource().sendSuccess(new StringTextComponent("The health level on the horse has been set to: " + levelToSet), false);
											} else
												ctx.getSource().sendSuccess(new StringTextComponent("Incorrect level range."), false);

											break;
										}

										case AFFINITY: {
											if (levelToSet > -1 && levelToSet < 12) {
												horse.progressionManager.getAffinityLeveling().setXp(0);
												horse.progressionManager.getAffinityLeveling().setLevel(levelToSet);
												ctx.getSource().sendSuccess(new StringTextComponent("The affinity level on the horse has been set to: " + levelToSet), false);
											} else
												ctx.getSource().sendSuccess(new StringTextComponent("Incorrect level range."), false);

											break;
										}

										default: {
											ctx.getSource().sendFailure(new StringTextComponent("Command failed"));
											return -1;
										}
									}

								}

								return 1;
							})


						)

					)


				);




	}
	public enum Skills {
		SPEED,
		JUMP,
		HEALTH,
		AFFINITY;
	}

}
