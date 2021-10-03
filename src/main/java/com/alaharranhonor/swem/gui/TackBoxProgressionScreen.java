package com.alaharranhonor.swem.gui;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.container.TackBoxContainer;
import com.alaharranhonor.swem.gui.widgets.ProgressionBoxes;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.HoverEvent;

import java.util.Collection;

public class TackBoxProgressionScreen extends Screen {
	private static final ResourceLocation TACKBOX_PROGRESSION_TEXTURE = new ResourceLocation(SWEM.MOD_ID, "textures/gui/container/tackbox_progression.png");
	private TackBoxContainer container;
	private PlayerInventory inv;
	private ITextComponent text;
	private int guiLeft;
	private int guiTop;
	private int xSize;
	private int ySize;

	public TackBoxProgressionScreen(TackBoxContainer screenContainer, PlayerInventory inv, ITextComponent defaultTitle, ITextComponent titleIn) {
		super(titleIn);
		this.container = screenContainer;
		this.inv = inv;
		this.text = defaultTitle;
		this.xSize = 250;
		this.ySize = 208;
	}

	@Override
	public void init(Minecraft minecraft, int width, int height) {
		super.init(minecraft, width, height);
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		for (Advancement adv : Minecraft.getInstance().player.connection.getAdvancements().getAdvancements().getAllAdvancements()) {
			if (adv.getId().getNamespace().equals("swem"))
				System.out.println(adv.getId());
		}
	}



	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(TACKBOX_PROGRESSION_TEXTURE);
		int i = (this.width - 247) / 2;
		int j = (this.height - 207) / 2;
		this.blit(matrixStack, i, j, 0, 0, 247, 207);
		this.font.draw(matrixStack, this.title, (float) this.guiLeft + 13, (float)this.guiTop + 30, 4210752);

		for (ProgressionBoxes pb : ProgressionBoxes.values()) {
			Advancement adv = Minecraft.getInstance().player.connection.getAdvancements().getAdvancements().get(new ResourceLocation(SWEM.MOD_ID, pb.getPath()));
			if (adv == null) continue; // Advancement has not been completed, don't overlay the blue box.
			this.blit(matrixStack, pb.getX() + this.guiLeft, pb.getY() + this.guiTop, 247, 25, 3, 3);

			if (pb.isMouseOver(mouseX, mouseY, this.guiLeft, this.guiTop)) {

				// Place the same render call that happens in AdvancementsScreen
				this.font.draw(matrixStack, adv.getDisplay().getTitle().copy().append("\n").append(adv.getDisplay().getDescription()), pb.getX() + this.guiLeft, pb.getY() + this.guiTop, 6724056);
			}
		}

		/*
		if ((mouseX > this.guiLeft && mouseX < this.guiLeft + this.xSize) && (mouseY > this.guiTop && mouseY < this.guiTop + this.ySize)) {
			for (ProgressionBoxes pb : ProgressionBoxes.values()) {
				if (pb.isMouseOver(mouseX, mouseY, this.guiLeft, this.guiTop)) {
					Minecraft.getInstance().player.connection.getAdvancements().progress.keySet().forEach((adv) -> {
						if (adv.getId().getNamespace().equals("swem")) {
							System.out.println(adv.getId().toString());
						}
					});

					Advancement adv = Minecraft.getInstance().player.connection.getAdvancements().getAdvancements().get(new ResourceLocation(SWEM.MOD_ID, pb.getPath()));
					// Place the same render call that happens in AdvancementsScreen
					this.font.draw(matrixStack, adv.getDisplay().getTitle().copy().append("\n").append(adv.getDisplay().getDescription()), pb.getX() + this.guiLeft, pb.getY() + this.guiTop, 6724056);
				}
			}
		}
		 */

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
				minecraft.setScreen(new TackBoxGeneticsScreen(this.container, this.inv, this.text, new TranslationTextComponent("container.swem.tack_box_genetics")));
				return true;
			}
			if (mouseX >= this.guiLeft + 96 && mouseX <= this.guiLeft + 118) {
				this.getMinecraft().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				return true;
			}
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}
}
