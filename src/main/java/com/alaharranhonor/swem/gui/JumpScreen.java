package com.alaharranhonor.swem.gui;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.jumps.JumpLayer;
import com.alaharranhonor.swem.blocks.jumps.StandardLayer;
import com.alaharranhonor.swem.gui.widgets.*;
import com.alaharranhonor.swem.tileentity.JumpTE;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JumpScreen extends Screen {

	private static final ResourceLocation TEXTURE = new ResourceLocation(SWEM.MOD_ID, "textures/gui/jump_builder.png");
	private int xSize;
	private int ySize;
	private int guiTop;
	private int guiLeft;
	public JumpTE jumpController;

	public DeleteLayerButton deleteLayerButton;
	public AddLayerButton addLayerButton;

	public DestroyButton destroyButton;

	protected final List<ColorChangerButton> colorButtons = Lists.newArrayList();


	public JumpScreen(ITextComponent titleIn, JumpTE jumpController) {
		super(titleIn);
		this.xSize = 243;
		this.ySize = 175;

		this.jumpController = jumpController;
	}

	@Override
	public void init(Minecraft minecraft, int width, int height) {
		super.init(minecraft, width, height);
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		initButtons();

		this.deleteLayerButton = new DeleteLayerButton(this.guiLeft + 100, this.guiTop + 12, 50, 20, new TranslationTextComponent("button.swem.delete_layer"), this);
		this.addListener(deleteLayerButton);

		this.addLayerButton = new AddLayerButton(this.guiLeft + 155, this.guiTop + 12, 50, 20, new TranslationTextComponent("button.swem.add_layer"), this);
		this.addListener(addLayerButton);

		this.destroyButton = new DestroyButton(this.guiLeft + 14, this.guiTop + 148, 50, 20, new TranslationTextComponent("button.swem.destroy"), this);
		this.addListener(destroyButton);

	}

	public void initButtons() {
		for (int i = 0; i < this.jumpController.getLayerAmount() + 2; i++) {
			if (i + 1 == this.jumpController.getLayerAmount() + 1) {
				continue;
			}

			if (i + 1 <= this.jumpController.getLayerAmount()) {
				DropDownButton<JumpLayer> btn = new DropDownButton<>(this.guiLeft + 62, this.guiTop + (this.ySize - ((23 * i) + 23 * 2)), 113, 20, new StringTextComponent("Option"), this);
				btn.setApplicableLayers(jumpController.getApplicableLayers(i + 1));
				if (jumpController.getLayer(i + 1) != null) {
					btn.setSelected(jumpController.getLayer(i + 1));
				}
				btn.setLayer(i + 1);
				this.addButton(btn);


				ColorChangerButton colorButton = new ColorChangerButton(this.guiLeft + (62 + 113 + 8), this.guiTop + (this.ySize - ((23 * i) + 23 * 2)), 90, 20, new StringTextComponent("Color"), this);
				colorButton.setLayer(i + 1);

				this.addColorButton(colorButton);



			}

			if (i + 1 == this.jumpController.getLayerAmount() + 2) {
				DropDownButton<StandardLayer> btn = new DropDownButton<>(this.guiLeft + 62, this.guiTop + (this.ySize - ((23 * i) + 23 * 2)), 113, 20, new StringTextComponent("Option"), this);

				//btn.setApplicableLayers(Arrays.asList(StandardLayer.values()));
				btn.setSelected(jumpController.getCurrentStandard());
				this.addButton(btn);
			}

		}
	}

	public void addColorButton(ColorChangerButton btn) {
		this.colorButtons.add(btn);
		this.addListener(btn);
	}
	public List<Widget> getButtons() {
		return this.buttons;
	}

	public void removeAllButtons() {
		this.buttons.clear();
		this.initButtons();
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack); // Darken background overlay

		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize); // GUI TEXTURE, needs to be called before anything else is rendered.

		int offSet = 16;
		for (int k = 0; k < this.jumpController.getLayerAmount(); k++) {
			offSet += 23;
			this.font.func_243248_b(matrixStack, new StringTextComponent("Layer " + (k + 1) + ":"), this.guiLeft + 7, this.guiTop + (this.ySize - offSet), 4210752);
		}

		this.font.func_243248_b(matrixStack,new StringTextComponent("Flag:"), this.guiLeft + 7, this.guiTop + this.ySize - offSet - 23, 4210752);
		this.font.func_243248_b(matrixStack, new StringTextComponent("Standards:"), this.guiLeft + 7, this.guiTop + this.ySize - offSet - (23*2), 4210752);

		super.render(matrixStack, mouseX, mouseY, partialTicks); // Only purpose is calling render on widgets.
		for(int k = 0; k < this.colorButtons.size(); ++k) {
			this.colorButtons.get(k).render(matrixStack, mouseX, mouseY, partialTicks);
		}
		this.deleteLayerButton.render(matrixStack, mouseX, mouseY, partialTicks);
		this.addLayerButton.render(matrixStack, mouseX, mouseY, partialTicks);
		this.destroyButton.render(matrixStack, mouseX, mouseY, partialTicks);

		this.font.func_243248_b(matrixStack, this.title, (float) this.guiLeft + 6, (float)this.guiTop + 6, 4210752);
	}

}
