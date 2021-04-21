package com.alaharranhonor.swem.gui;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.container.BedrollContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BedrollScreen extends ContainerScreen<BedrollContainer> implements IHasContainer<BedrollContainer> {

	private static final ResourceLocation SADDLE_BAG_TEXTURE = new ResourceLocation(SWEM.MOD_ID, "textures/gui/container/bedroll.png");

	private final PlayerEntity player;
	private BedrollContainer container;
	private PlayerInventory inventory;
	private ITextComponent text;


	public BedrollScreen(BedrollContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.xSize = 176;
		this.ySize = 166;
		this.player = inv.player;
		this.titleX = 7;
		this.titleY = 7;
		this.playerInventoryTitleX = 7;
		this.playerInventoryTitleY = 40;
		this.container = screenContainer;
		this.inventory = inv;
		this.text = titleIn;

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(SADDLE_BAG_TEXTURE);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize, 256, 256);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
		super.drawGuiContainerForegroundLayer(matrixStack, x, y);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}
}
