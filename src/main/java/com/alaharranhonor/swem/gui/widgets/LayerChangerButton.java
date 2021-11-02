package com.alaharranhonor.swem.gui.widgets;

import com.alaharranhonor.swem.blocks.jumps.JumpLayer;
import com.alaharranhonor.swem.blocks.jumps.StandardLayer;
import com.alaharranhonor.swem.gui.JumpScreen;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.network.jumps.CChangeLayerPacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class LayerChangerButton extends CycableButton {

	private JumpScreen screen;
	private JumpLayer currentLayer;
	private int id = -1;
	private int layer = -1;
	public LayerChangerButton(int x, int y, int width, int height, ITextComponent title, JumpScreen screen) {
		super(x, y, width, height, title, new Press());
		this.screen = screen;

	}

	public JumpScreen getScreen() {
		return screen;
	}


	public void setLayer(int layer) {
		this.layer = layer;
	}

	public void setSelected(JumpLayer layer) {
		this.currentLayer = layer;
	}

	public JumpLayer getCurrentLayer() {
		return this.currentLayer;
	}

	@Override
	public void onPress() {
		super.onPress();
	}


	@Override
	public ITextComponent getMessage() {
		//return new StringTextComponent("LAYER");

		if (this.layer == -1) {
			return new StringTextComponent("Option");
		}
		if (this.screen.layerTypes.get(this.layer) == null) {
			return new StringTextComponent("Option");
		}
		return new StringTextComponent(this.screen.layerTypes.get(this.layer).getDisplayName());
	}

	public static class Press implements LayerChangerButton.IPressable {

		@Override
		public void onPress(CycableButton press) {
			LayerChangerButton button = (LayerChangerButton) press;
			SWEMPacketHandler.INSTANCE.sendToServer(new CChangeLayerPacket(button.screen.controllerPos, button.layer, false));
		}

		@Override
		public void onRightPress(CycableButton press) {
			LayerChangerButton button = (LayerChangerButton) press;
			SWEMPacketHandler.INSTANCE.sendToServer(new CChangeLayerPacket(button.screen.controllerPos, button.layer, true));
		}
	}
}
