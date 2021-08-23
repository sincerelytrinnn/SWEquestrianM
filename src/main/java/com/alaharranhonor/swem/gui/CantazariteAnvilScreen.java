package com.alaharranhonor.swem.gui;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.container.CantazariteAnvilContainer;
import com.alaharranhonor.swem.items.SWEMArmorItem;
import com.alaharranhonor.swem.network.RenameItemPacket;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.AbstractRepairScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CantazariteAnvilScreen extends AbstractRepairScreen<CantazariteAnvilContainer> {
	private static final ResourceLocation ANVIL_RESOURCE = new ResourceLocation(SWEM.MOD_ID, "textures/gui/container/cantazarite_anvil.png");
	private static final ITextComponent TOO_EXPENSIVE_TEXT = new TranslationTextComponent("swem.container.anvil");
	private TextFieldWidget nameField;

	public CantazariteAnvilScreen(CantazariteAnvilContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title, ANVIL_RESOURCE);
		this.titleLabelX = 60;
	}

	@Override
	public void tick() {
		super.tick();
		this.nameField.tick();
	}

	protected void subInit() {
		this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.nameField = new TextFieldWidget(this.font, i + 62, j + 24, 103, 12, new TranslationTextComponent("container.repair"));
		this.nameField.setCanLoseFocus(false);
		this.nameField.setTextColor(-1);
		this.nameField.setTextColorUneditable(-1);
		this.nameField.setBordered(false);
		this.nameField.setMaxLength(35);
		this.nameField.setResponder(this::renameItem);
		this.children.add(this.nameField);
		this.setInitialFocus(this.nameField);
	}

	public void resize(Minecraft minecraft, int width, int height) {
		String s = this.nameField.getHighlighted();
		this.init(minecraft, width, height);
		this.nameField.setValue(s);
	}

	public void removed() {
		super.removed();
		this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
	}

	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == 256) {
			this.minecraft.player.closeContainer();
		}

		return !this.nameField.keyPressed(keyCode, scanCode, modifiers) && !this.nameField.canConsumeInput() ? super.keyPressed(keyCode, scanCode, modifiers) : true;
	}

	private void renameItem(String name) {
		if (!name.isEmpty()) {
			String s = name;
			Slot slot = this.menu.getSlot(0);
			if (slot != null && slot.hasItem() && !slot.getItem().hasCustomHoverName() && name.equals(slot.getItem().getHoverName().getString())) {
				s = "";
			}

			this.menu.updateItemName(s);
			SWEMPacketHandler.INSTANCE.sendToServer(new RenameItemPacket(s));
		}
	}

	protected void renderLabels(MatrixStack matrixStack, int x, int y) {
		RenderSystem.disableBlend();
		super.renderLabels(matrixStack, x, y);
		int i = this.menu.getMaximumCost();
		if (i > 0 && !(this.menu.getSlot(0).getItem().getItem() instanceof SWEMArmorItem)) {
			int j = 8453920;
			ITextComponent itextcomponent;
			if (i >= 40 && !this.minecraft.player.abilities.instabuild) {
				itextcomponent = TOO_EXPENSIVE_TEXT;
				j = 16736352;
			} else if (!this.menu.getSlot(2).hasItem()) {
				itextcomponent = null;
			} else {
				itextcomponent = new TranslationTextComponent("container.repair.cost", i);
				if (!this.menu.getSlot(2).mayPickup(this.inventory.player)) {
					j = 16736352;
				}
			}

			if (itextcomponent != null) {
				int k = this.imageWidth - 8 - this.font.width(itextcomponent) - 2;
				int l = 69;
				fill(matrixStack, k - 2, 67, this.imageWidth - 8, 79, 1325400064);
				this.font.drawShadow(matrixStack, itextcomponent, (float)k, 69.0F, j);
			}
		}

	}

	public void renderFg(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.nameField.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	/**
	 * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
	 * contents of that slot.
	 */
	public void slotChanged(Container containerToSend, int slotInd, ItemStack stack) {
		if (slotInd == 0) {
			this.nameField.setValue(stack.isEmpty() ? "" : stack.getHoverName().getString());
			this.nameField.setEditable(!stack.isEmpty());
			this.setFocused(this.nameField);
		}

	}
}
