package com.alaharranhonor.swem.gui;

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

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.jumps.JumpLayer;
import com.alaharranhonor.swem.blocks.jumps.StandardLayer;
import com.alaharranhonor.swem.container.JumpContainer;
import com.alaharranhonor.swem.gui.widgets.*;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JumpScreen extends ContainerScreen<JumpContainer> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(SWEM.MOD_ID, "textures/gui/jump_builder.png");
    protected final List<ColorChangerButton> colorButtons = Lists.newArrayList();
    public DeleteLayerButton deleteLayerButton;
    public AddLayerButton addLayerButton;
    public DestroyButton destroyButton;
    public BlockPos controllerPos;
    public int layerAmount;
    public Map<Integer, JumpLayer> layerTypes = new HashMap<>();
    public Map<Integer, Integer> layerColors = new HashMap<>();
    public StandardLayer currentStandard;
    private int xSize;
    private int ySize;
    private int guiTop;
    private int guiLeft;

    /**
     * Instantiates a new Jump screen.
     *
     * @param container the container
     * @param inventory the inventory
     * @param titleIn   the title in
     */
    public JumpScreen(JumpContainer container, PlayerInventory inventory, ITextComponent titleIn) {
        super(container, inventory, titleIn);
        this.xSize = 246; // + 3
        this.ySize = 181; // + 6
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.removeAllButtons();

        // Init the static buttons, but add the listeners inside the initButtons method, since we are
        // clearing the listener children, to avoid standard changing on layer buttons.
        this.deleteLayerButton =
                new DeleteLayerButton(
                        this.guiLeft + 6 + 65,
                        this.guiTop + 18,
                        70,
                        20,
                        new TranslationTextComponent("button.swem.delete_layer"),
                        this);
        this.addLayerButton =
                new AddLayerButton(
                        this.guiLeft + 6,
                        this.guiTop + 18,
                        60,
                        20,
                        new TranslationTextComponent("button.swem.add_layer"),
                        this);
        this.destroyButton =
                new DestroyButton(
                        this.guiLeft + 62 + 100 + 8 + 6 + 14,
                        this.guiTop + 18,
                        50,
                        20,
                        new TranslationTextComponent("button.swem.destroy"),
                        this);

        initButtons();
    }

    /**
     * Init buttons.
     */
    public void initButtons() {
        for (int i = 0; i < 5; i++) {
            LayerChangerButton btn =
                    new LayerChangerButton(
                            this.guiLeft + 65 + 6,
                            this.guiTop + (this.ySize - ((22 * i) + 26)),
                            100,
                            20,
                            new StringTextComponent("Option"),
                            this);
            btn.setLayer(i + 1);
            btn.setSelected(
                    this.layerTypes.get(i + 1) != null ? this.layerTypes.get(i + 1) : JumpLayer.AIR);
            if (this.layerAmount <= i) {
                btn.active = false;
            }
            this.addButton(btn);

            ColorChangerButton colorButton =
                    new ColorChangerButton(
                            this.guiLeft + (62 + 100 + 8 + 6),
                            this.guiTop + (this.ySize - ((22 * i) + 26)),
                            65,
                            20,
                            new StringTextComponent("Color"),
                            this);
            colorButton.setLayer(i + 1);
            if (this.layerTypes.get(i + 1) != null) {
                if (!this.layerTypes.get(i + 1).hasColorVariants()) {
                    colorButton.active = false;
                }
            } else {
                colorButton.active = false;
            }
            this.addColorButton(colorButton);
        }

        StandardChangerButton standardButton =
                new StandardChangerButton(
                        this.guiLeft + 65 + 6,
                        this.guiTop + (this.ySize - ((22 * 5) + 26)),
                        100,
                        20,
                        new StringTextComponent("Option"),
                        this);
        standardButton.setSelected(currentStandard);
        this.addButton(standardButton);

        this.addWidget(this.deleteLayerButton);
        this.addWidget(this.addLayerButton);
        this.addWidget(this.destroyButton);
    }

    /**
     * Add color button.
     *
     * @param btn the btn
     */
    public void addColorButton(ColorChangerButton btn) {
        this.colorButtons.add(btn);
        this.addWidget(btn);
    }

    /**
     * Gets buttons.
     *
     * @return the buttons
     */
    public List<Widget> getButtons() {
        return this.buttons;
    }

    /**
     * Gets color buttons.
     *
     * @return the color buttons
     */
    public List<ColorChangerButton> getColorButtons() {
        return this.colorButtons;
    }

    /**
     * Remove all buttons.
     */
    public void removeAllButtons() {
        this.buttons.clear();
        this.children.clear();
        this.colorButtons.clear();
    }

    /**
     * Remove and re init.
     */
    public void removeAndReInit() {
        removeAllButtons();
        this.initButtons();
    }

    /**
     * Check layer buttons.
     */
    private void checkLayerButtons() {
        this.addLayerButton.active = this.layerAmount < 5;
        this.deleteLayerButton.active = this.layerAmount <= 5 && this.layerAmount > 1;
    }

    /**
     * Update data.
     *
     * @param controllerPos the controller pos
     * @param layerAmount   the layer amount
     * @param layers        the layers
     * @param colors        the colors
     * @param standard      the standard
     */
    public void updateData(
            BlockPos controllerPos,
            int layerAmount,
            Map<Integer, JumpLayer> layers,
            Map<Integer, Integer> colors,
            StandardLayer standard) {
        this.controllerPos = controllerPos;
        this.layerAmount = layerAmount;
        this.layerTypes = layers;
        this.layerColors = colors;
        this.currentStandard = standard;
        this.removeAndReInit();
        this.addLayerButton.active = true;
        this.deleteLayerButton.active = true;
        this.checkLayerButtons();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack); // Darken background overlay

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(
                matrixStack,
                i,
                j,
                0,
                0,
                this.xSize,
                this.ySize); // GUI TEXTURE, needs to be called before anything else is rendered.

        int offSet = -3;
        for (int k = 0; k < 5; k++) {
            offSet += 22;
            this.font.draw(
                    matrixStack,
                    new StringTextComponent("Layer " + (k + 1) + " :"),
                    this.guiLeft + 21.5f,
                    this.guiTop + (this.ySize - offSet),
                    4210752);
        }

        // this.font.drawText(matrixStack,new StringTextComponent("Flag:"), this.guiLeft + 7,
        // this.guiTop + this.ySize - offSet - 23, 4210752);
        this.font.draw(
                matrixStack,
                new StringTextComponent("Standards :"),
                this.guiLeft + 9,
                this.guiTop + this.ySize - offSet - 22,
                4210752);

        for (int k = 0; k < this.buttons.size(); ++k) {
            this.buttons.get(k).render(matrixStack, mouseX, mouseY, partialTicks);
        }
        for (int k = 0; k < this.colorButtons.size(); ++k) {
            this.colorButtons.get(k).render(matrixStack, mouseX, mouseY, partialTicks);
        }
        if (this.deleteLayerButton != null) {
            this.deleteLayerButton.render(matrixStack, mouseX, mouseY, partialTicks);
        }
        if (this.addLayerButton != null) {
            this.addLayerButton.render(matrixStack, mouseX, mouseY, partialTicks);
        }
        if (this.destroyButton != null) {
            this.destroyButton.render(matrixStack, mouseX, mouseY, partialTicks);
        }

        this.font.draw(
                matrixStack, this.title, (float) this.guiLeft + 6, (float) this.guiTop + 6, 4210752);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
    }
}
