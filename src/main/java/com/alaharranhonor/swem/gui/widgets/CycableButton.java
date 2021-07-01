package com.alaharranhonor.swem.gui.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CycableButton extends AbstractButton {
	public static final CycableButton.ITooltip EMPTY_TOOLTIP = (button, matrixStack, mouseX, mouseY) -> {
	};
	protected final CycableButton.IPressable onPress;
	protected final CycableButton.ITooltip onTooltip;

	public CycableButton(int x, int y, int width, int height, ITextComponent title, CycableButton.IPressable pressedAction) {
		this(x, y, width, height, title, pressedAction, EMPTY_TOOLTIP);
	}

	public CycableButton(int x, int y, int width, int height, ITextComponent title, CycableButton.IPressable pressedAction, CycableButton.ITooltip onTooltip) {
		super(x, y, width, height, title);
		this.onPress = pressedAction;
		this.onTooltip = onTooltip;
	}

	public void onPress() {
		this.onPress.onPress(this);
	}

	public void onRightPress() {
		this.onPress.onRightPress(this);
	}

	public void onRightClick(double mouseX, double mouseY) {
		this.onRightPress();
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (this.active && this.visible) {
			if (this.isValidClickButton(button)) {
				boolean flag = this.clicked(mouseX, mouseY);
				if (flag) {
					this.playDownSound(Minecraft.getInstance().getSoundManager());
					if (button == 0) {
						this.onClick(mouseX, mouseY);
					} else if (button == 1) {
						this.onRightClick(mouseX, mouseY);
					}
					return true;
				}
			}

			return false;
		} else {
			return false;
		}
	}

	@Override
	protected boolean isValidClickButton(int button) {
		return button == 0 || button == 1;
	}

	public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.renderButton(matrixStack, mouseX, mouseY, partialTicks);
		if (this.isHovered()) {
			this.renderToolTip(matrixStack, mouseX, mouseY);
		}

	}

	public void renderToolTip(MatrixStack matrixStack, int mouseX, int mouseY) {
		this.onTooltip.onTooltip(this, matrixStack, mouseX, mouseY);
	}

	@OnlyIn(Dist.CLIENT)
	public interface IPressable {
		void onPress(CycableButton button);
		void onRightPress(CycableButton button);
	}

	@OnlyIn(Dist.CLIENT)
	public interface ITooltip {
		void onTooltip(CycableButton p_onTooltip_1_, MatrixStack p_onTooltip_2_, int p_onTooltip_3_, int p_onTooltip_4_);
	}
}
