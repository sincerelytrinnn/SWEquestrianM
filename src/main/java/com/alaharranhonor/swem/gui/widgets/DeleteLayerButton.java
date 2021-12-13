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
import com.alaharranhonor.swem.network.jumps.CRemoveLayerPacket;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

public class DeleteLayerButton extends Button {
	private JumpScreen screen;

	public DeleteLayerButton(int x, int y, int width, int height, ITextComponent title, JumpScreen screen) {
		super(x, y, width, height, title, new DeletePressable());
		this.screen = screen;

		if (screen.getMenu().layerAmount == 1) this.active = false;
	}

	private static class DeletePressable implements Button.IPressable {

		@Override
		public void onPress(Button p_onPress_1_) {
			DeleteLayerButton btn = (DeleteLayerButton) p_onPress_1_;
			int layerToRemove = btn.screen.layerAmount;
			SWEMPacketHandler.INSTANCE.sendToServer(new CRemoveLayerPacket(btn.screen.controllerPos, layerToRemove));
		}
	}
}
