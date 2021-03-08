package com.alaharranhonor.swem.gui.widgets;

import com.alaharranhonor.swem.gui.JumpScreen;
import com.alaharranhonor.swem.network.JumpControllerUpdatePacket;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
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
			int newLayerAmount = btn.screen.jumpController.getLayerAmount() + 1;
			SWEMPacketHandler.INSTANCE.sendToServer(new JumpControllerUpdatePacket(btn.screen.jumpController.getPos(), 1, newLayerAmount));
			btn.screen.jumpController.addLayer(newLayerAmount);
			btn.screen.removeAllButtons();
			if (btn.screen.jumpController.getLayerAmount() == 5) {
				btn.active = false;
			}
			btn.screen.deleteLayerButton.active = true;
		}
	}
}
