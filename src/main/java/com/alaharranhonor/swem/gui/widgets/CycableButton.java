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

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CycableButton extends AbstractButton {
  public static final CycableButton.ITooltip EMPTY_TOOLTIP =
      (button, matrixStack, mouseX, mouseY) -> {};
  protected final CycableButton.IPressable onPress;
  protected final CycableButton.ITooltip onTooltip;

  /**
   * Instantiates a new Cycable button.
   *
   * @param x the x
   * @param y the y
   * @param width the width
   * @param height the height
   * @param title the title
   * @param pressedAction the pressed action
   */
  public CycableButton(
      int x,
      int y,
      int width,
      int height,
      ITextComponent title,
      CycableButton.IPressable pressedAction) {
    this(x, y, width, height, title, pressedAction, EMPTY_TOOLTIP);
  }

  /**
   * Instantiates a new Cycable button.
   *
   * @param x the x
   * @param y the y
   * @param width the width
   * @param height the height
   * @param title the title
   * @param pressedAction the pressed action
   * @param onTooltip the on tooltip
   */
  public CycableButton(
      int x,
      int y,
      int width,
      int height,
      ITextComponent title,
      CycableButton.IPressable pressedAction,
      CycableButton.ITooltip onTooltip) {
    super(x, y, width, height, title);
    this.onPress = pressedAction;
    this.onTooltip = onTooltip;
  }

  public void onPress() {
    this.onPress.onPress(this);
  }

  /** On right press. */
  public void onRightPress() {
    this.onPress.onRightPress(this);
  }

  /**
   * On right click.
   *
   * @param mouseX the mouse x
   * @param mouseY the mouse y
   */
  public void onRightClick(double mouseX, double mouseY) {
    this.onRightPress();
  }

  @Override
  public boolean mouseClicked(double mouseX, double mouseY, int button) {
    if (this.active && this.visible) {
      if (this.isValidClickButton(button)) {
        boolean flag = this.clicked(mouseX, mouseY);
        if (flag) {
          this.playDownSound(Minecraft.getInstance().getSoundManager());
          if (button == 0) {
            this.onClick(mouseX, mouseY);
          } else if (button == 1) {
            this.onRightClick(mouseX, mouseY);
          }
          return true;
        }
      }

      return false;
    } else {
      return false;
    }
  }

  @Override
  protected boolean isValidClickButton(int button) {
    return button == 0 || button == 1;
  }

  public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    super.renderButton(matrixStack, mouseX, mouseY, partialTicks);
    if (this.isHovered()) {
      this.renderToolTip(matrixStack, mouseX, mouseY);
    }
  }

  public void renderToolTip(MatrixStack matrixStack, int mouseX, int mouseY) {
    this.onTooltip.onTooltip(this, matrixStack, mouseX, mouseY);
  }

  @OnlyIn(Dist.CLIENT)
  public interface IPressable {
    /**
     * On press.
     *
     * @param button the button
     */
    void onPress(CycableButton button);

    /**
     * On right press.
     *
     * @param button the button
     */
    void onRightPress(CycableButton button);
  }

  @OnlyIn(Dist.CLIENT)
  public interface ITooltip {
    /**
     * On tooltip.
     *
     * @param p_onTooltip_1_ the p on tooltip 1
     * @param p_onTooltip_2_ the p on tooltip 2
     * @param p_onTooltip_3_ the p on tooltip 3
     * @param p_onTooltip_4_ the p on tooltip 4
     */
    void onTooltip(
        CycableButton p_onTooltip_1_,
        MatrixStack p_onTooltip_2_,
        int p_onTooltip_3_,
        int p_onTooltip_4_);
  }
}
