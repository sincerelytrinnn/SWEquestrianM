package com.alaharranhonor.swem.gui.widgets;

import com.alaharranhonor.swem.gui.JumpScreen;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.network.jumps.CDestroyPacket;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

public class DestroyButton extends Button {
	private JumpScreen screen;

	public DestroyButton(int x, int y, int width, int height, ITextComponent title, JumpScreen screen) {
		super(x, y, width, height, title, new DestroyButton.DestroyPressable());
		this.screen = screen;
	}

	private static class DestroyPressable implements DestroyButton.IPressable {

		@Override
		public void onPress(Button p_onPress_1_) {
			DestroyButton btn = (DestroyButton) p_onPress_1_;
			SWEMPacketHandler.INSTANCE.sendToServer(new CDestroyPacket(btn.screen.controllerPos));
			btn.screen.closeScreen();
		}
	}
}
