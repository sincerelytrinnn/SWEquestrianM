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
import com.alaharranhonor.swem.entities.needs.HungerNeed;
import com.alaharranhonor.swem.entities.needs.ThirstNeed;
import com.alaharranhonor.swem.gui.widgets.CustomHeightButton;
import com.alaharranhonor.swem.network.HorseStateChange;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Objects;

public class TackBoxDefaultScreen extends ContainerScreen<TackBoxContainer> {

	private static final ResourceLocation TACKBOX_DEFAULT_TEXTURE = new ResourceLocation(SWEM.MOD_ID, "textures/gui/container/tackbox_default.png");

	private final PlayerEntity player;
	private TackBoxContainer container;
	private PlayerInventory inventory;
	private ITextComponent text;
	private int storageLabelX;
	private int storageLabelY;
	private Button permissionButton;


	/**
	 * Instantiates a new Tack box default screen.
	 *
	 * @param screenContainer the screen container
	 * @param inv             the inv
	 * @param titleIn         the title in
	 */
	public TackBoxDefaultScreen(TackBoxContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, new StringTextComponent(screenContainer.horse.getDisplayName().getString() + "'s Tack Box"));
		this.imageWidth = 176;
		this.imageHeight = 245;
		this.player = inv.player;
		this.titleLabelX = 7;
		this.titleLabelY = 28;
		this.storageLabelX = 7;
		this.storageLabelY = 101;
		this.inventoryLabelX = 7;
		this.inventoryLabelY = 152;
		this.container = screenContainer;
		this.inventory = inv;
		this.text = titleIn;
		//this.title = new TranslationTextComponent("Tack and Training");

	}

	@Override
	protected void init() {
		super.init();

		this.permissionButton = new CustomHeightButton(this.leftPos  + 121, this.topPos + 134, 48, 14, new StringTextComponent(getMenu().horse.getPermissionState().name()), p_onPress_1_ -> {
			SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(9, getMenu().horse.getId()));
			p_onPress_1_.setMessage(new StringTextComponent(SWEMHorseEntityBase.RidingPermission.values()[(getMenu().horse.getPermissionState().ordinal() + 1) % 3].name()));
		});

		if (!Objects.equals(getMenu().horse.getOwnerUUID(), Minecraft.getInstance().player.getUUID())) {
			this.permissionButton.active = false;
		}

		this.addButton(this.permissionButton);
	}

	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(TACKBOX_DEFAULT_TEXTURE);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

		if (getMenu().horse.isBeingTracked()) {
			// Overlay, for Tracker.
			blit(matrixStack, i + 9, j + 140, 180, 23, 3, 3);
		}

		int hungerX = i + 41;
		int hungerY = j + 144;
		int hungerXOffset = 179;
		int hungerYOffset = 30;
		int hungerHeight = 3;
		switch (getMenu().horse.getEntityData().get(HungerNeed.HungerState.ID)) {
			case 0: {
				break;
			}
			case 1: {
				blit(matrixStack, hungerX, hungerY, hungerXOffset, hungerYOffset, 12, hungerHeight);
				break;
			}
			case 2: {
				blit(matrixStack, hungerX, hungerY, hungerXOffset, hungerYOffset, 18, hungerHeight);
				break;
			}
			case 3: {
				blit(matrixStack, hungerX, hungerY, hungerXOffset, hungerYOffset, 24, hungerHeight);
				break;
			}
			case 4: {
				blit(matrixStack, hungerX, hungerY, hungerXOffset, hungerYOffset, 28, hungerHeight);
				break;
			}
		}


		int thirstX = i + 82;
		int thirstY = j + 144;
		int thirstXOffset = 179;
		int thirstYOffset = 36;
		int thirstHeight = 3;
		switch (getMenu().horse.getEntityData().get(ThirstNeed.ThirstState.ID)) {
			case 0: {
				break;
			}
			case 1: {
				blit(matrixStack, thirstX, thirstY, thirstXOffset, thirstYOffset, 12, thirstHeight);
				break;
			}
			case 2: {
				blit(matrixStack, thirstX, thirstY, thirstXOffset, thirstYOffset, 18, thirstHeight);
				break;
			}
			case 3: {
				blit(matrixStack, thirstX, thirstY, thirstXOffset, thirstYOffset, 24, thirstHeight);
				break;
			}
			case 4: {
				blit(matrixStack, thirstX, thirstY, thirstXOffset, thirstYOffset, 28, thirstHeight);
				break;
			}
		}
	}

	@Override
	protected void renderLabels(MatrixStack matrixStack, int x, int y) {
		super.renderLabels(matrixStack, x, y);

		this.font.draw(matrixStack, new StringTextComponent("Storage"), storageLabelX, storageLabelY, 4210752);

	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (mouseY >= this.topPos && mouseY <= this.topPos + 22) {
			if (mouseX >= this.leftPos + 3 && mouseX <= this.leftPos + 27) {
				this.getMinecraft().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				return true;
			}
			if (mouseX >= this.leftPos + 34 && mouseX <= this.leftPos + 56) {
				this.getMinecraft().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				minecraft.setScreen(new TackBoxBirthScreen(this.container, this.inventory, this.text, new TranslationTextComponent("container.swem.tack_box_certificate")));
				return true;
			}
			if (mouseX >= this.leftPos + 65 && mouseX <= this.leftPos + 87) {
				this.getMinecraft().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				minecraft.setScreen(new TackBoxGeneticsScreen(this.container, this.inventory, this.text, new TranslationTextComponent("container.swem.tack_box_genetics")));
				return true;
			}
			if (mouseX >= this.leftPos + 96 && mouseX <= this.leftPos + 118) {
				this.getMinecraft().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				minecraft.setScreen(new TackBoxProgressionScreen(this.container, this.inventory, this.text, new TranslationTextComponent("container.swem.tack_box_progression")));
				return true;
			}
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}
}
