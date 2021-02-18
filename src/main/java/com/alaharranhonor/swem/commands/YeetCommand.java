package com.alaharranhonor.swem.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class YeetCommand {

	public static LiteralArgumentBuilder<CommandSource> register() {
		return
				Commands.literal("yeet")
				.then(Commands.literal("burrito")
					.executes(ctx ->  {
						ctx.getSource().sendFeedback(new StringTextComponent("Yetto Burrito"), false);
						return 1;
					})
				)
				.executes(ctx -> {
					ctx.getSource().sendFeedback(new StringTextComponent("Yeet"), false);
					return 1;
				});
	}
}
