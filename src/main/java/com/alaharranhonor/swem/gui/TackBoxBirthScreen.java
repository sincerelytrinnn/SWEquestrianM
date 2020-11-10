package com.alaharranhonor.swem.gui;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.container.TackBoxContainer;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TackBoxBirthScreen extends Screen {
	private static final ResourceLocation TACKBOX_BIRTH_TEXTURE = new ResourceLocation(SWEM.MOD_ID, "textures/gui/container/tackbox_birth.png");

	private TackBoxContainer container;
	private PlayerInventory inv;
	private ITextComponent text;
	private int guiLeft;
	private int guiTop;
	private int xSize;
	private int ySize;
	protected TackBoxBirthScreen(TackBoxContainer screenContainer, PlayerInventory inv, ITextComponent defaultTitle, ITextComponent titleIn) {
		super(titleIn);
		this.container = screenContainer;
		this.inv = inv;

		this.text = defaultTitle;
		this.xSize = 248;
		this.ySize = 207;
	}

	@Override
	public void init(Minecraft minecraft, int width, int height) {
		super.init(minecraft, width, height);
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(TACKBOX_BIRTH_TEXTURE);
		int i = (this.width - 247) / 2;
		int j = (this.height - 207) / 2;
		this.blit(matrixStack, i, j, 0, 0, 247, 207);

		// Title
		this.font.func_243248_b(matrixStack, this.title, (float) this.guiLeft + 13, (float)this.guiTop + 30, 4210752);

		if (this.container.horse != null) {
			SWEMHorseEntityBase horse = this.container.horse;

			this.font.func_243248_b(matrixStack, new StringTextComponent(String.format("Owner: %s", horse.getOwnerDisplayName().getString()) ), this.guiLeft + 17, this.guiTop + 57, 4210752);
			this.font.func_243248_b(matrixStack, new StringTextComponent(String.format("Name: %s", horse.getDisplayName().getString()) ), this.guiLeft + 17, this.guiTop + 67, 4210752);
			this.font.func_243248_b(matrixStack, new StringTextComponent(String.format("Show Name: %s", horse.getDisplayName().getString())), this.guiLeft + 17, this.guiTop + 77, 4210752);

			this.font.func_243248_b(matrixStack, new StringTextComponent("Dam: Phase 2"), this.guiLeft + 17, this.guiTop + 99, 0xb74242);
			this.font.func_243248_b(matrixStack, new StringTextComponent("Sire: Phase 2"), this.guiLeft + 17, this.guiTop + 109, 0xb74242);

			this.font.func_243248_b(matrixStack, new StringTextComponent("Sex: Phase 2"), this.guiLeft + 17, this.guiTop + 123, 0xb74242);
			this.font.func_243248_b(matrixStack, new StringTextComponent("Birthdate: Phase 2"), this.guiLeft + 73, this.guiTop + 123, 0xb74242);

			this.font.func_243248_b(matrixStack, new StringTextComponent("Breed: Phase 2"), this.guiLeft + 17, this.guiTop + 137, 0xb74242);

			this.font.func_243248_b(matrixStack, new StringTextComponent("Temperament: Phase 2"), this.guiLeft + 17, this.guiTop + 151, 0xb74242);
			this.font.func_243248_b(matrixStack, new StringTextComponent("Stamina: Phase 2"), this.guiLeft + 17, this.guiTop + 161, 0xb74242);
			this.font.func_243248_b(matrixStack, new StringTextComponent("Conformation: Phase 2"), this.guiLeft + 136, this.guiTop + 151, 0xb74242);
			this.font.func_243248_b(matrixStack, new StringTextComponent("Immunity: Phase 2"), this.guiLeft + 136, this.guiTop + 161, 0xb74242);


			this.font.func_243248_b(matrixStack, new StringTextComponent("Discipline Affinity:"), this.guiLeft + 17, this.guiTop + 175, 4210752);
			this.font.func_243248_b(matrixStack, new StringTextComponent("Eng: Phase 2"), this.guiLeft + 24, this.guiTop + 185, 0xb74242);
			this.font.func_243248_b(matrixStack, new StringTextComponent("West: Phase 2"), this.guiLeft + 97, this.guiTop + 185, 0xb74242);
			this.font.func_243248_b(matrixStack, new StringTextComponent("Adv: Phase 2"), this.guiLeft + 180, this.guiTop + 185, 0xb74242);

		}

	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (mouseY >= this.guiTop && mouseY <= this.guiTop + 22) {
			if (mouseX >= this.guiLeft + 3 && mouseX <= this.guiLeft + 27) {
				this.getMinecraft().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				minecraft.displayGuiScreen(new TackBoxDefaultScreen(this.container, this.inv, this.text));
				return true;
			}
			if (mouseX >= this.guiLeft + 34 && mouseX <= this.guiLeft + 56) {
				this.getMinecraft().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				return true;
			}
			if (mouseX >= this.guiLeft + 65 && mouseX <= this.guiLeft + 87) {
				this.getMinecraft().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				minecraft.displayGuiScreen(new TackBoxGeneticsScreen(this.container, this.inv, this.text, new TranslationTextComponent("container.swem.tack_box_genetics")));
				return true;
			}
			if (mouseX >= this.guiLeft + 96 && mouseX <= this.guiLeft + 118) {
				this.getMinecraft().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				minecraft.displayGuiScreen(new TackBoxProgressionScreen(this.container, this.inv, this.text, new TranslationTextComponent("container.swem.tack_box_progression")));
				return true;
			}
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}
}
