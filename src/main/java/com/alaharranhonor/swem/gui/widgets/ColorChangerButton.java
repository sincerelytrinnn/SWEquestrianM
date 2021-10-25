package com.alaharranhonor.swem.gui.widgets;

import com.alaharranhonor.swem.gui.JumpScreen;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.network.jumps.CChangeColorPacket;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.DyeColor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;


public class ColorChangerButton extends CycableButton {

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
	public ITextComponent getMessage() {
		String colorName = DyeColor.byId(this.screen.layerColors.get(this.layer)).getName();
		String[] names = colorName.split("_");
		String finalName = "";
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			String subName = name.substring(1);
			char firstChar = Character.toUpperCase(name.charAt(0));
			finalName += firstChar + subName;
			if (i + 1 < names.length) {
				finalName += " ";
			}

		}

		return new StringTextComponent(finalName);
	}

	public static class Press implements ColorChangerButton.IPressable {

		@Override
		public void onPress(CycableButton press) {
			ColorChangerButton button = (ColorChangerButton) press;
			SWEMPacketHandler.INSTANCE.sendToServer(new CChangeColorPacket(button.screen.controllerPos, button.layer, false));
		}

		@Override
		public void onRightPress(CycableButton press) {
			ColorChangerButton button = (ColorChangerButton) press;
			SWEMPacketHandler.INSTANCE.sendToServer(new CChangeColorPacket(button.screen.controllerPos, button.layer, true));
		}
	}
}
