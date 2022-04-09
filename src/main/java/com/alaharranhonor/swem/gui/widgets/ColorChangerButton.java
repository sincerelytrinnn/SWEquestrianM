package com.alaharranhonor.swem.gui.widgets;


/*
 * All Rights Reserved
 *
 * Copyright (c) 2021, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.alaharranhonor.swem.gui.JumpScreen;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.network.jumps.CChangeColorPacket;
import com.alaharranhonor.swem.util.SWEMUtil;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;


public class ColorChangerButton extends CycableButton {

	private JumpScreen screen;
	private int layer = -1;

	/**
	 * Instantiates a new Color changer button.
	 *
	 * @param x      the x
	 * @param y      the y
	 * @param width  the width
	 * @param height the height
	 * @param title  the title
	 * @param screen the screen
	 */
	public ColorChangerButton(int x, int y, int width, int height, ITextComponent title, JumpScreen screen) {
		super(x, y, width, height, title, new ColorChangerButton.Press());
		this.screen = screen;

	}

	/**
	 * Gets screen.
	 *
	 * @return the screen
	 */
	public JumpScreen getScreen() {
		return screen;
	}


	/**
	 * Sets layer.
	 *
	 * @param layer the layer
	 */
	public void setLayer(int layer) {
		this.layer = layer;
	}

	@Override
	public ITextComponent getMessage() {
		if (this.screen.layerAmount < this.layer) {
			return new StringTextComponent("White");
		}
		String colorName = SWEMUtil.logicalByIndex(this.screen.layerColors.get(this.layer)).getName();
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
