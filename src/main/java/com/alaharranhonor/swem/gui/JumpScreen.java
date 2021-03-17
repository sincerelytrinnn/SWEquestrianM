package com.alaharranhonor.swem.gui;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.jumps.JumpLayer;
import com.alaharranhonor.swem.blocks.jumps.StandardLayer;
import com.alaharranhonor.swem.container.JumpContainer;
import com.alaharranhonor.swem.gui.widgets.*;
import com.alaharranhonor.swem.tileentity.JumpTE;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.*;

public class JumpScreen extends ContainerScreen<JumpContainer> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(SWEM.MOD_ID, "textures/gui/jump_builder.png");
	private int xSize;
	private int ySize;
	private int guiTop;
	private int guiLeft;
	public DeleteLayerButton deleteLayerButton;
	public AddLayerButton addLayerButton;

	public DestroyButton destroyButton;

	protected final List<ColorChangerButton> colorButtons = Lists.newArrayList();

	public BlockPos controllerPos;
	public int layerAmount;
	public Map<Integer, JumpLayer> layerTypes = new HashMap<>();
	public Map<Integer, Integer> layerColors = new HashMap<>();
	public StandardLayer currentStandard;


	public JumpScreen(JumpContainer container, PlayerInventory inventory, ITextComponent titleIn) {
		super(container, inventory, titleIn);
		this.xSize = 243;
		this.ySize = 175;

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
		for (int i = 0; i < this.layerAmount + 2; i++) {
			if (i + 1 == this.layerAmount + 1) {
				continue;
			}

			if (i + 1 <= this.layerAmount) {
				DropDownButton<JumpLayer> btn = new DropDownButton<>(this.guiLeft + 62, this.guiTop + (this.ySize - ((23 * i) + 23 * 2)), 113, 20, new StringTextComponent("Option"), this);
				btn.setLayer(i + 1);
				btn.setSelected(this.layerTypes.get(i + 1));
				this.addButton(btn);


				ColorChangerButton colorButton = new ColorChangerButton(this.guiLeft + (62 + 113 + 8), this.guiTop + (this.ySize - ((23 * i) + 23 * 2)), 90, 20, new StringTextComponent("Color"), this);
				colorButton.setLayer(i + 1);
				if (!this.layerTypes.get(i + 1).hasColorVariants()) {
					colorButton.active = false;
				}
				this.addColorButton(colorButton);



			}

			if (i + 1 == this.layerAmount + 2) {
				DropDownButton<StandardLayer> btn = new DropDownButton<>(this.guiLeft + 62, this.guiTop + (this.ySize - ((23 * i) + 23 * 2)), 113, 20, new StringTextComponent("Option"), this);
				btn.setSelected(currentStandard);
				btn.active = false;
				//this.addButton(btn);
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

	public List<ColorChangerButton> getColorButtons() {
		return this.colorButtons;
	}

	public void removeAllButtons() {
		this.buttons.clear();
		this.colorButtons.clear();
		this.initButtons();
	}

	private void checkLayerButtons() {
		this.addLayerButton.active = this.layerAmount < 5;
		this.deleteLayerButton.active = this.layerAmount <= 5 && this.layerAmount > 1;
	}

	public void updateData(BlockPos controllerPos, int layerAmount, Map<Integer, JumpLayer> layers, Map<Integer, Integer> colors, StandardLayer standard) {
		this.controllerPos = controllerPos;
		this.layerAmount = layerAmount;
		this.layerTypes = layers;
		this.layerColors = colors;
		this.currentStandard = standard;
		this.removeAllButtons();
		this.checkLayerButtons();
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
		for (int k = 0; k < this.layerAmount; k++) {
			offSet += 23;
			this.font.func_243248_b(matrixStack, new StringTextComponent("Layer " + (k + 1) + ":"), this.guiLeft + 7, this.guiTop + (this.ySize - offSet), 4210752);
		}

		//this.font.func_243248_b(matrixStack,new StringTextComponent("Flag:"), this.guiLeft + 7, this.guiTop + this.ySize - offSet - 23, 4210752);
		//this.font.func_243248_b(matrixStack, new StringTextComponent("Standards:"), this.guiLeft + 7, this.guiTop + this.ySize - offSet - (23*2), 4210752);

		for (int k = 0; k < this.buttons.size(); ++k) {
			this.buttons.get(k).render(matrixStack, mouseX, mouseY, partialTicks);
		}
		for (int k = 0; k < this.colorButtons.size(); ++k) {
			this.colorButtons.get(k).render(matrixStack, mouseX, mouseY, partialTicks);
		}
		this.deleteLayerButton.render(matrixStack, mouseX, mouseY, partialTicks);
		this.addLayerButton.render(matrixStack, mouseX, mouseY, partialTicks);
		this.destroyButton.render(matrixStack, mouseX, mouseY, partialTicks);

		this.font.func_243248_b(matrixStack, this.title, (float) this.guiLeft + 6, (float)this.guiTop + 6, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {

	}

}
