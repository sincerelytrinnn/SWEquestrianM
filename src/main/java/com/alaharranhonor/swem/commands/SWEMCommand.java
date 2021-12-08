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
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.impl.SummonCommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

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

								ctx.getSource().sendSuccess(new StringTextComponent("[§bSWEM§f] You have added " + player.getDisplayName().getString() + " to your allowed list, on " + riding.getDisplayName().getString() + "."), false);
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

								ctx.getSource().sendSuccess(new StringTextComponent("[§bSWEM§f] You have removed " + player.getDisplayName().getString() + " from your allowed list, on " + riding.getDisplayName().getString() + "."), false);
								return 1;
							})))
					.then(Commands.literal("transfer")
						.then(Commands.argument("player", EntityArgument.player())
							.executes(ctx -> {
								ServerPlayerEntity player = EntityArgument.getPlayer(ctx, "player");

								Entity riding = ctx.getSource().getPlayerOrException().getVehicle();
								if (riding instanceof SWEMHorseEntityBase) {
									SWEMHorseEntityBase horse = (SWEMHorseEntityBase) riding;
									horse.ejectPassengers();
									horse.transferHorse(player);
									SWEMPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> horse), new SHorseFriendPacket(UUID.randomUUID(), horse.getId(), 3));
								}

								ctx.getSource().sendSuccess(new StringTextComponent("[§bSWEM§f] You have transferred " + riding.getDisplayName().getString() + " to " + player.getDisplayName().getString() + "."), false);
								return 1;
							})))
				);

	}
}
