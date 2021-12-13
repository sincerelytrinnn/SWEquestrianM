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
						ctx.getSource().sendSuccess(new StringTextComponent("Yetto Burrito"), false);
						return 1;
					})
				)
				.executes(ctx -> {
					ctx.getSource().sendSuccess(new StringTextComponent("Yeet"), false);
					return 1;
				});
	}
}
