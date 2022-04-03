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
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.SWEMUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.UUID;

public class TackBoxBirthScreen extends Screen {
	private static final ResourceLocation TACKBOX_BIRTH_TEXTURE = new ResourceLocation(SWEM.MOD_ID, "textures/gui/container/tackbox_birth.png");

	private TackBoxContainer container;
	private PlayerInventory inv;
	private ITextComponent text;
	private int guiLeft;
	private int guiTop;
	private int xSize;
	private int ySize;

	/**
	 * Instantiates a new Tack box birth screen.
	 *
	 * @param screenContainer the screen container
	 * @param inv             the inv
	 * @param defaultTitle    the default title
	 * @param titleIn         the title in
	 */
	protected TackBoxBirthScreen(TackBoxContainer screenContainer, PlayerInventory inv, ITextComponent defaultTitle, ITextComponent titleIn) {
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
		this.minecraft.getTextureManager().bind(TACKBOX_BIRTH_TEXTURE);
		int i = (this.width - 247) / 2;
		int j = (this.height - 207) / 2;
		this.blit(matrixStack, i, j, 0, 0, 247, 207);

		// Title
		this.font.draw(matrixStack, this.title, (float) this.guiLeft + 13, (float)this.guiTop + 30, 4210752);

		if (this.container.horse != null) {
			SWEMHorseEntityBase horse = this.container.horse;

			this.font.draw(matrixStack, new StringTextComponent(String.format("Owner: %s", SWEMUtil.checkTextOverflow(horse.getOwnerName(), 24)) ), this.guiLeft + 17, this.guiTop + 57, 4210752);
			this.font.draw(matrixStack, new StringTextComponent(String.format("Name: %s", SWEMUtil.checkTextOverflow(horse.getName().getString(), 24)) ), this.guiLeft + 17, this.guiTop + 67, 4210752);
			this.font.draw(matrixStack, new StringTextComponent(String.format("Show Name: %s", SWEMUtil.checkTextOverflow("Phase 2", 24))), this.guiLeft + 17, this.guiTop + 77, 4210752);

			this.font.draw(matrixStack, new StringTextComponent("Dam: Phase 2"), this.guiLeft + 17, this.guiTop + 99, 4210752);
			this.font.draw(matrixStack, new StringTextComponent("Sire: Phase 2"), this.guiLeft + 17, this.guiTop + 109, 4210752);

			this.font.draw(matrixStack, new StringTextComponent("Sex: Phase 2"), this.guiLeft + 17, this.guiTop + 123, 4210752);
			this.font.draw(matrixStack, new StringTextComponent("Birthdate: Phase 2"), this.guiLeft + 90, this.guiTop + 123, 4210752);

			this.font.draw(matrixStack, new StringTextComponent("Breed: Phase 2"), this.guiLeft + 17, this.guiTop + 137, 4210752);

			this.font.draw(matrixStack, new StringTextComponent("Temperament: Phase 2"), this.guiLeft + 17, this.guiTop + 151, 4210752);
			this.font.draw(matrixStack, new StringTextComponent("Stamina: Phase 2"), this.guiLeft + 17, this.guiTop + 161, 4210752);
			this.font.draw(matrixStack, new StringTextComponent("Confirmation: Phase 2"), this.guiLeft + 133, this.guiTop + 151, 4210752);
			this.font.draw(matrixStack, new StringTextComponent("Immunity: Phase 2"), this.guiLeft + 133, this.guiTop + 161, 4210752);


			this.font.draw(matrixStack, new StringTextComponent("Discipline Affinity:"), this.guiLeft + 17, this.guiTop + 173, 4210752);
			this.font.draw(matrixStack, new StringTextComponent("Eng: Phase 2"), this.guiLeft + 24, this.guiTop + 183, 4210752);
			this.font.draw(matrixStack, new StringTextComponent("West: Phase 2"), this.guiLeft + 97, this.guiTop + 183, 4210752);
			this.font.draw(matrixStack, new StringTextComponent("Adv: Phase 2"), this.guiLeft + 180, this.guiTop + 183, 4210752);

		}

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
				return true;
			}
			if (mouseX >= this.guiLeft + 65 && mouseX <= this.guiLeft + 87) {
				this.getMinecraft().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				minecraft.setScreen(new TackBoxGeneticsScreen(this.container, this.inv, this.text, new TranslationTextComponent("container.swem.tack_box_genetics")));
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
