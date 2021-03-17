package com.alaharranhonor.swem.gui.widgets;

import com.alaharranhonor.swem.gui.JumpScreen;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.network.jumps.CRemoveLayerPacket;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

public class DeleteLayerButton extends Button {
	private JumpScreen screen;

	public DeleteLayerButton(int x, int y, int width, int height, ITextComponent title, JumpScreen screen) {
		super(x, y, width, height, title, new DeletePressable());
		this.screen = screen;

		if (screen.getContainer().layerAmount == 1) this.active = false;
	}

	private static class DeletePressable implements DeleteLayerButton.IPressable {

		@Override
		public void onPress(Button p_onPress_1_) {
			DeleteLayerButton btn = (DeleteLayerButton) p_onPress_1_;
			int layerToRemove = btn.screen.layerAmount;
			SWEMPacketHandler.INSTANCE.sendToServer(new CRemoveLayerPacket(btn.screen.controllerPos, layerToRemove));
		}
	}
}
