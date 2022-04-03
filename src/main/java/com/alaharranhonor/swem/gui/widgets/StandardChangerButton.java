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

import com.alaharranhonor.swem.blocks.jumps.StandardLayer;
import com.alaharranhonor.swem.gui.JumpScreen;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.network.jumps.CChangeStandardPacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class StandardChangerButton extends CycableButton {

	private JumpScreen screen;
	private StandardLayer currentLayer;

	/**
	 * Instantiates a new Standard changer button.
	 *
	 * @param x      the x
	 * @param y      the y
	 * @param width  the width
	 * @param height the height
	 * @param title  the title
	 * @param screen the screen
	 */
	public StandardChangerButton(int x, int y, int width, int height, ITextComponent title, JumpScreen screen) {
		super(x, y, width, height, title, new StandardChangerButton.Press());
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
	 * Sets selected.
	 *
	 * @param layer the layer
	 */
	public void setSelected(StandardLayer layer) {
		this.currentLayer = layer;
	}

	/**
	 * Gets current layer.
	 *
	 * @return the current layer
	 */
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
		return new StringTextComponent(this.getCurrentLayer().getDisplayName());
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
