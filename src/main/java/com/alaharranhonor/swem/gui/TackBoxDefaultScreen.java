package com.alaharranhonor.swem.gui;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.container.TackBoxContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TackBoxDefaultScreen extends ContainerScreen<TackBoxContainer> {

	private static final ResourceLocation TACKBOX_DEFAULT_TEXTURE = new ResourceLocation(SWEM.MOD_ID, "textures/gui/container/tackbox_default.png");

	private final PlayerEntity player;
	private TackBoxContainer container;
	private PlayerInventory inventory;
	private ITextComponent text;



	public TackBoxDefaultScreen(TackBoxContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.xSize = 247;
		this.ySize = 302;
		this.player = inv.player;
		this.titleX = 13;
		this.titleY = 32;
		this.playerInventoryTitleX = 47;
		this.playerInventoryTitleY = 209;
		this.container = screenContainer;
		this.inventory = inv;
		this.text = titleIn;

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(TACKBOX_DEFAULT_TEXTURE);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize, 256, 512);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
		super.drawGuiContainerForegroundLayer(matrixStack, x, y);

		// TODO: CHECK IF HORSE IS SET; ELSE DON'T RENDER HORSE STUFF. TO AVOID CRASH
		if (getContainer().horse != null) {
			this.font.func_243248_b(matrixStack, getContainer().horse.getOwnerDisplayName(), 150, 150, 4210752);
		}

	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (mouseY >= this.guiTop && mouseY <= this.guiTop + 22) {
			if (mouseX >= this.guiLeft + 3 && mouseX <= this.guiLeft + 27) {
				this.getMinecraft().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				return true;
			}
			if (mouseX >= this.guiLeft + 34 && mouseX <= this.guiLeft + 56) {
				this.getMinecraft().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				minecraft.displayGuiScreen(new TackBoxBirthScreen(this.container, this.inventory, this.text, new TranslationTextComponent("Birth Certificate")));
				return true;
			}
			if (mouseX >= this.guiLeft + 65 && mouseX <= this.guiLeft + 87) {
				this.getMinecraft().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				minecraft.displayGuiScreen(new TackBoxGeneticsScreen(this.container, this.inventory, this.text, new TranslationTextComponent("Genetics")));
				return true;
			}
			if (mouseX >= this.guiLeft + 96 && mouseX <= this.guiLeft + 118) {
				this.getMinecraft().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				minecraft.displayGuiScreen(new TackBoxProgressionScreen(this.container, this.inventory, this.text, new TranslationTextComponent("Progression")));
				return true;
			}
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}
}
