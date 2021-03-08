package com.alaharranhonor.swem.proxy;

import com.alaharranhonor.swem.gui.JumpScreen;
import com.alaharranhonor.swem.tileentity.JumpTE;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TranslationTextComponent;

public class ClientProxy {
	public static void openJumpBuilder(JumpTE controller) {
		Minecraft.getInstance().displayGuiScreen(new JumpScreen(new TranslationTextComponent("screen.swem.jump_builder"), controller));
	}
}
