package com.alaharranhonor.swem.gui.widgets;

import com.alaharranhonor.swem.blocks.jumps.JumpLayer;
import com.alaharranhonor.swem.blocks.jumps.StandardLayer;
import com.alaharranhonor.swem.gui.JumpScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class DropDownButton<T> extends Button {

	private JumpScreen screen;
	private List<T> applicableLayers = new ArrayList<>();
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

	public List<T> getApplicableLayers() {
		return applicableLayers;
	}

	public void setApplicableLayers(List<T> layers) {
		this.applicableLayers = layers;
		if (!this.applicableLayers.contains(currentLayer) && this.applicableLayers.size() > 0) {
			setSelected(this.applicableLayers.get(this.applicableLayers.indexOf(JumpLayer.NONE)));
		}
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public void setSelected(T layer) {
		this.currentLayer = layer;
		this.id = this.applicableLayers.indexOf(layer);
	}

	public T getCurrentLayer() {
		return this.currentLayer;
	}

	@Override
	public void onPress() {
		if (!this.applicableLayers.isEmpty()) {
			if (id + 1 > applicableLayers.size() - 1) {
				id = 0;
			} else {
				id++;
			}

			T object = this.applicableLayers.get(id);

			if (isLayer(object)) {
				this.screen.jumpController.placeLayer(layer, (JumpLayer) object);
				this.screen.getButtons().forEach((widget) -> {
					DropDownButton btn = (DropDownButton) widget;
					btn.setApplicableLayers(this.screen.jumpController.getApplicableLayers(btn.layer));
				});
			} else if (isStandard(object)) {
				//this.screen.jumpController.placeStandards((StandardLayer) object);

			}
			this.setSelected(object);

		}
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
		if (id == -1 || this.applicableLayers.isEmpty() && currentLayer == null) {
			return new StringTextComponent("Option");
		}

		return new StringTextComponent(((Enum) currentLayer).name());
	}

	public static class Press implements DropDownButton.IPressable {

		@Override
		public void onPress(Button p_onPress_1_) {
			DropDownButton button = (DropDownButton) p_onPress_1_;

		}
	}
}
