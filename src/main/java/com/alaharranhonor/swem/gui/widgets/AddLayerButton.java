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
import com.alaharranhonor.swem.network.jumps.CAddLayerPacket;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

public class AddLayerButton extends Button {
	private JumpScreen screen;

	/**
	 * Instantiates a new Add layer button.
	 *
	 * @param x      the x
	 * @param y      the y
	 * @param width  the width
	 * @param height the height
	 * @param title  the title
	 * @param screen the screen
	 */
	public AddLayerButton(int x, int y, int width, int height, ITextComponent title, JumpScreen screen) {
		super(x, y, width, height, title, new AddLayerButton.AddPressable());
		this.screen = screen;
	}

	private static class AddPressable implements Button.IPressable {

		@Override
		public void onPress(Button p_onPress_1_) {
			AddLayerButton btn = (AddLayerButton) p_onPress_1_;
			btn.active = false;
			btn.screen.deleteLayerButton.active = false;
			SWEMPacketHandler.INSTANCE.sendToServer(new CAddLayerPacket(btn.screen.controllerPos, btn.screen.layerAmount + 1));
		}
	}
}
