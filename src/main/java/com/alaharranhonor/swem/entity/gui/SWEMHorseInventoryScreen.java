package com.alaharranhonor.swem.entity.gui;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.container.SWEMHorseInventoryContainer;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.entities.progression.leveling.AffinityLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.HealthLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.JumpLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.SpeedLeveling;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class SWEMHorseInventoryScreen extends ContainerScreen<SWEMHorseInventoryContainer> implements IHasContainer<SWEMHorseInventoryContainer> {

	private static final ResourceLocation HORSE_GUI_TEXTURES = new ResourceLocation(SWEM.MOD_ID, "textures/gui/container/swem_horse.png");
	/** The EntityHorse whose inventory is currently being accessed. */
	private SWEMHorseEntityBase horseEntity;
	/** The mouse x-position recorded during the last rendered frame. */
	private float mousePosx;
	/** The mouse y-position recorded during the last renderered frame. */
	private float mousePosY;



	public SWEMHorseInventoryScreen(SWEMHorseInventoryContainer p_i51084_1_, PlayerInventory playerInventoryIn, ITextComponent title) {
		super(p_i51084_1_, playerInventoryIn, title);
		//this(p_i51084_1_, playerInventoryIn, p_i51084_1_.horse);
		this.passEvents = false;
		this.xSize = 175;
		this.ySize = 221;
		this.horseEntity = p_i51084_1_.horse;
		this.playerInventoryTitleY = this.ySize - 94;
	}

	@Override
	public SWEMHorseInventoryContainer getContainer() {
		return this.container;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(HORSE_GUI_TEXTURES);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

		// Not sure what this renders, some weird box.
		//if (this.horseEntity.func_230264_L__()) {
//			this.blit(matrixStack, i + 7, j + 35, 18, this.ySize + 54, 18, 18);
//		}

		// Render the horse
		//InventoryScreen.drawEntityOnScreen(i + 51, j + 60, 17, (float)(i + 51) - this.mousePosx, (float)(j + 75 - 50) - this.mousePosY, this.horseEntity);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
		super.drawGuiContainerForegroundLayer(matrixStack, x, y);

		SpeedLeveling speedLeveling = this.horseEntity.progressionManager.getSpeedLeveling();
		AffinityLeveling affinityLeveling = this.horseEntity.progressionManager.getAffinityLeveling();
		JumpLeveling jumpLeveling = this.horseEntity.progressionManager.getJumpLeveling();
		HealthLeveling healthLeveling = this.horseEntity.progressionManager.getHealthLeveling();

		this.font.func_243248_b(matrixStack, new TranslationTextComponent(String.format("%s:", affinityLeveling.getLevelName())), 120.0f, 20.0f, 4210752);
		if (affinityLeveling.getLevel() != affinityLeveling.getMaxLevel()) {
			this.font.func_243248_b(matrixStack, new TranslationTextComponent(String.format("%.0f/%.0f", affinityLeveling.getXp(), affinityLeveling.getRequiredXp())), 120.0f, 30.0f, 4210752);
		}
		// Health TEXT
		this.font.func_243248_b(matrixStack, new TranslationTextComponent(String.format("%s:", healthLeveling.getLevelName())), 120.0f, 45.0f, 4210752);

		this.font.func_243248_b(matrixStack, new TranslationTextComponent(String.format("%.1f/%.0f", this.horseEntity.getHealth(), this.horseEntity.getMaxHealth())), 120.0f, 55.0f, 4210752);

		// Speed TEXT
		this.font.func_243248_b(matrixStack, new TranslationTextComponent(String.format("%s:", speedLeveling.getLevelName())), 120.0f, 70.0f, 4210752);
		if (speedLeveling.getLevel() != speedLeveling.getMaxLevel()) {
			this.font.func_243248_b(matrixStack, new TranslationTextComponent(String.format("%.0f/%.0f", speedLeveling.getXp(), speedLeveling.getRequiredXp())), 120.0f, 80.0f, 4210752);
		}

		// Jump TEXT
		this.font.func_243248_b(matrixStack, new TranslationTextComponent(String.format("%s:", jumpLeveling.getLevelName())), 120.0f, 95.0f, 4210752);
		if (jumpLeveling.getLevel() != jumpLeveling.getMaxLevel()) {
			this.font.func_243248_b(matrixStack, new TranslationTextComponent(String.format("%.0f/%.0f", jumpLeveling.getXp(), jumpLeveling.getRequiredXp())), 120.0f, 105.0f, 4210752);
		}

	}

	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		this.mousePosx = (float)mouseX;
		this.mousePosY = (float)mouseY;
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}


}
