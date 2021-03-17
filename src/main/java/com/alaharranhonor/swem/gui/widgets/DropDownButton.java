package com.alaharranhonor.swem.gui.widgets;

import com.alaharranhonor.swem.blocks.jumps.JumpLayer;
import com.alaharranhonor.swem.blocks.jumps.StandardLayer;
import com.alaharranhonor.swem.gui.JumpScreen;
import com.alaharranhonor.swem.network.ChangeLayerBlockPacket;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.network.jumps.CChangeColorPacket;
import com.alaharranhonor.swem.network.jumps.CChangeLayerPacket;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class DropDownButton<T> extends Button {

	private JumpScreen screen;
	private T currentLayer;
	private int id = -1;
	private int layer = -1;
	public DropDownButton(int x, int y, int width, int height, ITextComponent title, JumpScreen screen) {
		super(x, y, width, height, title, new Press());
		this.screen = screen;

	}

	public JumpScreen getScreen() {
		return screen;
	}


	public void setLayer(int layer) {
		this.layer = layer;
	}

	public void setSelected(T layer) {
		this.currentLayer = layer;
	}

	public T getCurrentLayer() {
		return this.currentLayer;
	}

	@Override
	public void onPress() {
		SWEMPacketHandler.INSTANCE.sendToServer(new CChangeLayerPacket(this.screen.controllerPos, layer));
		super.onPress();
	}

	public <Q> boolean isLayer(Q type) {
		return type instanceof JumpLayer;
	}

	public <Q> boolean isStandard(Q type) {
		return type instanceof StandardLayer;
	}

	@Override
	public ITextComponent getMessage() {
		if (this.layer == -1) {
			return new StringTextComponent("Option");
		}
		if (this.screen.layerTypes.get(this.layer) == null) {
			return new StringTextComponent("Option");
		}
		return new StringTextComponent(this.screen.layerTypes.get(this.layer).name());
	}

	public static class Press implements DropDownButton.IPressable {

		@Override
		public void onPress(Button p_onPress_1_) {
			DropDownButton button = (DropDownButton) p_onPress_1_;

		}
	}
}
