package com.alaharranhonor.swem.gui.widgets;

import com.alaharranhonor.swem.gui.JumpScreen;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

public class DeleteLayerButton extends Button {
	private JumpScreen screen;

	public DeleteLayerButton(int x, int y, int width, int height, ITextComponent title, JumpScreen screen) {
		super(x, y, width, height, title, new DeletePressable());
		this.screen = screen;

		if (screen.jumpController.getLayerAmount() == 1) this.active = false;
	}

	private static class DeletePressable implements DeleteLayerButton.IPressable {

		@Override
		public void onPress(Button p_onPress_1_) {
			DeleteLayerButton btn = (DeleteLayerButton) p_onPress_1_;
			int oldLayerAmount = btn.screen.jumpController.getLayerAmount();
			btn.screen.jumpController.deleteLayer(oldLayerAmount);
			btn.screen.removeAllButtons();
			if (btn.screen.jumpController.getLayerAmount() == 1) {
				btn.active = false;
			}
			btn.screen.addLayerButton.active = true;
		}
	}
}
