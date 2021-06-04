package com.alaharranhonor.swem.gui.widgets;

import com.alaharranhonor.swem.blocks.jumps.StandardLayer;
import com.alaharranhonor.swem.gui.JumpScreen;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.network.jumps.CChangeStandardPacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class StandardChangerButton extends CycableButton {

	private JumpScreen screen;
	private StandardLayer currentLayer;
	public StandardChangerButton(int x, int y, int width, int height, ITextComponent title, JumpScreen screen) {
		super(x, y, width, height, title, new StandardChangerButton.Press());
		this.screen = screen;

	}

	public JumpScreen getScreen() {
		return screen;
	}

	public void setSelected(StandardLayer layer) {
		this.currentLayer = layer;
	}

	public StandardLayer getCurrentLayer() {
		return this.currentLayer;
	}

	@Override
	public void onPress() {
		super.onPress();
	}

	@Override
	public ITextComponent getMessage() {

		//return new StringTextComponent("STANDARD");
		if (this.getCurrentLayer() == null) {
			return new StringTextComponent("Option");
		}
		return new StringTextComponent(this.getCurrentLayer().name());
	}

	public static class Press implements StandardChangerButton.IPressable {

		@Override
		public void onPress(CycableButton press) {
			StandardChangerButton button = (StandardChangerButton) press;
			SWEMPacketHandler.INSTANCE.sendToServer(new CChangeStandardPacket(button.screen.controllerPos, false));
		}

		@Override
		public void onRightPress(CycableButton press) {
			StandardChangerButton button = (StandardChangerButton) press;
			SWEMPacketHandler.INSTANCE.sendToServer(new CChangeStandardPacket(button.screen.controllerPos, true));
		}
	}
}
