package com.alaharranhonor.swem.gui.widgets;

import com.alaharranhonor.swem.gui.JumpScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;


public class ColorChangerButton extends Button {

	private JumpScreen screen;
	private int layer = -1;
	public ColorChangerButton(int x, int y, int width, int height, ITextComponent title, JumpScreen screen) {
		super(x, y, width, height, title, new ColorChangerButton.Press());
		this.screen = screen;

	}

	public JumpScreen getScreen() {
		return screen;
	}


	public void setLayer(int layer) {
		this.layer = layer;
	}

	@Override
	public void onPress() {
		this.screen.jumpController.changeColorVariant(this.layer);
		this.screen.jumpController.placeLayer(this.layer, this.screen.jumpController.getLayer(this.layer));
		super.onPress();
	}

	@Override
	public ITextComponent getMessage() {

		return new StringTextComponent(this.screen.jumpController.getColorVariant(this.layer).name());
	}

	public static class Press implements ColorChangerButton.IPressable {

		@Override
		public void onPress(Button p_onPress_1_) {
			ColorChangerButton button = (ColorChangerButton) p_onPress_1_;
		}
	}
}
