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
import com.alaharranhonor.swem.container.TackBoxContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TackBoxGeneticsScreen extends Screen {
	private static final ResourceLocation TACKBOX_GENETICS_TEXTURE = new ResourceLocation(SWEM.MOD_ID, "textures/gui/container/tackbox_genetics.png");

	private TackBoxContainer container;
	private PlayerInventory inv;
	private ITextComponent text;
	private int guiLeft;
	private int guiTop;
	private int xSize;
	private int ySize;

	/**
	 * Instantiates a new Tack box genetics screen.
	 *
	 * @param screenContainer the screen container
	 * @param inv             the inv
	 * @param defaultTitle    the default title
	 * @param titleIn         the title in
	 */
	protected TackBoxGeneticsScreen(TackBoxContainer screenContainer, PlayerInventory inv, ITextComponent defaultTitle, ITextComponent titleIn) {
		super(titleIn);
		this.container = screenContainer;
		this.inv = inv;
		this.text = defaultTitle;
		this.xSize = 249;
		this.ySize = 208;
	}

	@Override
	public void init(Minecraft minecraft, int width, int height) {
		super.init(minecraft, width, height);
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(TACKBOX_GENETICS_TEXTURE);
		int i = (this.width - 247) / 2;
		int j = (this.height - 207) / 2;
		this.blit(matrixStack, i, j, 0, 0, 247, 207);
		this.font.draw(matrixStack, this.title, (float) this.guiLeft + 13, (float)this.guiTop + 30, 4210752);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (mouseY >= this.guiTop && mouseY <= this.guiTop + 22) {
			if (mouseX >= this.guiLeft + 3 && mouseX <= this.guiLeft + 27) {
				this.getMinecraft().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				minecraft.setScreen(new TackBoxDefaultScreen(this.container, this.inv, this.text));
				return true;
			}
			if (mouseX >= this.guiLeft + 34 && mouseX <= this.guiLeft + 56) {
				this.getMinecraft().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				minecraft.setScreen(new TackBoxBirthScreen(this.container, this.inv, this.text, new TranslationTextComponent("container.swem.tack_box_certificate")));
				return true;
			}
			if (mouseX >= this.guiLeft + 65 && mouseX <= this.guiLeft + 87) {
				this.getMinecraft().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				return true;
			}
			if (mouseX >= this.guiLeft + 96 && mouseX <= this.guiLeft + 118) {
				this.getMinecraft().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				minecraft.setScreen(new TackBoxProgressionScreen(this.container, this.inv, this.text, new TranslationTextComponent("container.swem.tack_box_progression")));
				return true;
			}
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}
}
