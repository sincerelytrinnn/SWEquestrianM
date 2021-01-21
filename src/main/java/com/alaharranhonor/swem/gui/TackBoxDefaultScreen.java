package com.alaharranhonor.swem.gui;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.container.TackBoxContainer;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TackBoxDefaultScreen extends ContainerScreen<TackBoxContainer> {

	private static final ResourceLocation TACKBOX_DEFAULT_TEXTURE = new ResourceLocation(SWEM.MOD_ID, "textures/gui/container/tackbox_default.png");

	private final PlayerEntity player;
	private TackBoxContainer container;
	private PlayerInventory inventory;
	private ITextComponent text;



	public TackBoxDefaultScreen(TackBoxContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.xSize = 248;
		this.ySize = 303;
		this.player = inv.player;
		this.titleX = 13;
		this.titleY = 32;
		this.playerInventoryTitleX = 47;
		this.playerInventoryTitleY = 209;
		this.container = screenContainer;
		this.inventory = inv;
		this.text = titleIn;
		//this.title = new TranslationTextComponent("Tack and Training");

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
			SWEMHorseEntityBase horse = getContainer().horse;

			if (horse.progressionManager.getJumpLeveling().getLevel() != horse.progressionManager.getJumpLeveling().getMaxLevel()) {
				this.font.func_243248_b(matrixStack, new StringTextComponent(String.format("Jump Status: %s %.0f/%.0f", horse.progressionManager.getJumpLeveling().getLevelName(), horse.progressionManager.getJumpLeveling().getXp(), horse.progressionManager.getJumpLeveling().getRequiredXp())), 18, 49, 4210752);
			} else {
				this.font.func_243248_b(matrixStack, new StringTextComponent(String.format("Jump Status: %s", horse.progressionManager.getJumpLeveling().getLevelName())), 18, 49, 4210752);
			}

			if (horse.progressionManager.getSpeedLeveling().getLevel() != horse.progressionManager.getSpeedLeveling().getMaxLevel()) {
				this.font.func_243248_b(matrixStack, new StringTextComponent( String.format("Speed Status: %s %.0f/%.0f",horse.progressionManager.getSpeedLeveling().getLevelName(), horse.progressionManager.getSpeedLeveling().getXp(), horse.progressionManager.getSpeedLeveling().getRequiredXp()) ), 18, 59, 4210752);
			} else {
				this.font.func_243248_b(matrixStack, new StringTextComponent( String.format("Speed Status: %s",horse.progressionManager.getSpeedLeveling().getLevelName())), 18, 59, 4210752);
			}

			if (horse.progressionManager.getHealthLeveling().getLevel() != horse.progressionManager.getHealthLeveling().getMaxLevel()) {
				this.font.func_243248_b(matrixStack, new StringTextComponent( String.format("Health Status: %s %.0f/%.0f",horse.progressionManager.getHealthLeveling().getLevelName(), horse.progressionManager.getHealthLeveling().getXp(), horse.progressionManager.getHealthLeveling().getRequiredXp()) ), 18, 69, 4210752);
			} else {
				this.font.func_243248_b(matrixStack, new StringTextComponent( String.format("Health Status: %s",horse.progressionManager.getHealthLeveling().getLevelName())), 18, 69, 4210752);
			}

			if (horse.progressionManager.getAffinityLeveling().getLevel() != horse.progressionManager.getAffinityLeveling().getMaxLevel()) {
				this.font.func_243248_b(matrixStack, new StringTextComponent( String.format("Affinity Status: %s %.0f/%.0f",horse.progressionManager.getAffinityLeveling().getLevelName(), horse.progressionManager.getAffinityLeveling().getXp(), horse.progressionManager.getAffinityLeveling().getRequiredXp()) ), 18, 79, 4210752);
			} else {
				this.font.func_243248_b(matrixStack, new StringTextComponent( String.format("Affinity Status: %s",horse.progressionManager.getAffinityLeveling().getLevelName())), 18, 79, 4210752);
			}

			// 17px x for stats.
		}
		this.font.func_243248_b(matrixStack, new StringTextComponent("English"), 13, 128, 4210752);
		this.font.func_243248_b(matrixStack, new StringTextComponent("Western"), 59, 128, 4210752);
		this.font.func_243248_b(matrixStack, new StringTextComponent("Adv."), 109, 128, 4210752);
		this.font.func_243248_b(matrixStack, new StringTextComponent("General"), 168, 128, 4210752);

		// Overlay, for Tracker.
		//fillGradient(matrixStack, 214, 32, 217, 35, 0xFF479238, 0xFF85f96d);



		// 69 123




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
				minecraft.displayGuiScreen(new TackBoxBirthScreen(this.container, this.inventory, this.text, new TranslationTextComponent("container.swem.tack_box_certificate")));
				return true;
			}
			if (mouseX >= this.guiLeft + 65 && mouseX <= this.guiLeft + 87) {
				this.getMinecraft().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				minecraft.displayGuiScreen(new TackBoxGeneticsScreen(this.container, this.inventory, this.text, new TranslationTextComponent("container.swem.tack_box_genetics")));
				return true;
			}
			if (mouseX >= this.guiLeft + 96 && mouseX <= this.guiLeft + 118) {
				this.getMinecraft().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				minecraft.displayGuiScreen(new TackBoxProgressionScreen(this.container, this.inventory, this.text, new TranslationTextComponent("container.swem.tack_box_progression")));
				return true;
			}
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}
}
