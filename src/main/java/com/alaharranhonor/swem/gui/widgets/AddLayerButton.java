package com.alaharranhonor.swem.gui.widgets;

import com.alaharranhonor.swem.gui.JumpScreen;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.network.jumps.CAddLayerPacket;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

public class AddLayerButton extends Button {
	private JumpScreen screen;

	public AddLayerButton(int x, int y, int width, int height, ITextComponent title, JumpScreen screen) {
		super(x, y, width, height, title, new AddLayerButton.AddPressable());
		this.screen = screen;
	}

	private static class AddPressable implements AddLayerButton.IPressable {

		@Override
		public void onPress(Button p_onPress_1_) {
			AddLayerButton btn = (AddLayerButton) p_onPress_1_;
			SWEMPacketHandler.INSTANCE.sendToServer(new CAddLayerPacket(btn.screen.controllerPos, btn.screen.layerAmount + 1));
		}
	}
}
