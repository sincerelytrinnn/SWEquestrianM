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

import com.alaharranhonor.swem.blocks.jumps.JumpLayer;
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

    /**
     * Instantiates a new Layer changer button.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param title  the title
     * @param screen the screen
     */
    public LayerChangerButton(
            int x, int y, int width, int height, ITextComponent title, JumpScreen screen) {
        super(x, y, width, height, title, new Press());
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

    /**
     * Sets selected.
     *
     * @param layer the layer
     */
    public void setSelected(JumpLayer layer) {
        this.currentLayer = layer;
    }

    /**
     * Gets current layer.
     *
     * @return the current layer
     */
    public JumpLayer getCurrentLayer() {
        return this.currentLayer;
    }

    @Override
    public void onPress() {
        super.onPress();
    }

    @Override
    public ITextComponent getMessage() {
        // return new StringTextComponent("LAYER");

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
            SWEMPacketHandler.INSTANCE.sendToServer(
                    new CChangeLayerPacket(button.screen.controllerPos, button.layer, false));
        }

        @Override
        public void onRightPress(CycableButton press) {
            LayerChangerButton button = (LayerChangerButton) press;
            SWEMPacketHandler.INSTANCE.sendToServer(
                    new CChangeLayerPacket(button.screen.controllerPos, button.layer, true));
        }
    }
}
