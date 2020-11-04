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
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Timer;


@OnlyIn(Dist.CLIENT)
public class SWEMHorseInventoryScreen extends ContainerScreen<SWEMHorseInventoryContainer> implements IHasContainer<SWEMHorseInventoryContainer> {

	private static final ResourceLocation HORSE_GUI_TEXTURES = new ResourceLocation(SWEM.MOD_ID, "textures/gui/container/swem_horse_western.png");
	/** The EntityHorse whose inventory is currently being accessed. */
	private SWEMHorseEntityBase horseEntity;
	/** The mouse x-position recorded during the last rendered frame. */
	private float mousePosx;
	/** The mouse y-position recorded during the last renderered frame. */
	private float mousePosY;

	public static Timer TIMER;



	public SWEMHorseInventoryScreen(SWEMHorseInventoryContainer p_i51084_1_, PlayerInventory playerInventoryIn, ITextComponent title) {
		super(p_i51084_1_, playerInventoryIn, title);
		//this(p_i51084_1_, playerInventoryIn, p_i51084_1_.horse);
		this.passEvents = false;
		this.xSize = 175;
		this.ySize = 221;
		this.horseEntity = p_i51084_1_.horse;
		this.playerInventoryTitleY = this.ySize - 94;
		this.titleX = 65;
		this.titleY = 22;
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

		// Top Text
		this.font.func_243248_b(matrixStack, new TranslationTextComponent("Tack"), 15, 6, 4210752);
		this.font.func_243248_b(matrixStack, new TranslationTextComponent("Stats"), 65, 6,4210752);

		// Owner name.
		this.font.func_243248_b(matrixStack, this.horseEntity.getOwnerDisplayName(), 65.2f, 36.0f, 4210752);

		// Jump TEXT
		TranslationTextComponent jumpInfo;
		if (jumpLeveling.getLevel() != jumpLeveling.getMaxLevel()) {
			jumpInfo = new TranslationTextComponent(String.format("%s: %.0f/%.0f", jumpLeveling.getLevelName(), jumpLeveling.getXp(), jumpLeveling.getRequiredXp()));
		} else {
			jumpInfo = new TranslationTextComponent(String.format("%s", jumpLeveling.getLevelName()));
		}
		this.font.func_243248_b(matrixStack, jumpInfo, 65.0f, 49.0f, 4210752);

		// Speed TEXT
		TranslationTextComponent speedInfo;
		if (speedLeveling.getLevel() != speedLeveling.getMaxLevel()) {
			speedInfo = new TranslationTextComponent(String.format("%s: %.0f/%.0f", speedLeveling.getLevelName(), speedLeveling.getXp(), speedLeveling.getRequiredXp()));
		} else {
			speedInfo = new TranslationTextComponent(String.format("%s", speedLeveling.getLevelName()));
		}
		this.font.func_243248_b(matrixStack, speedInfo, 65.0f, 64.0f, 4210752);

		// Health TEXT
		this.font.func_243248_b(matrixStack, new TranslationTextComponent(String.format("%s: %.1f/%.0f", healthLeveling.getLevelName(), this.horseEntity.getHealth(), this.horseEntity.getMaxHealth())), 65.0f, 78.0f, 4210752);

		// Affinity TEXT
		TranslationTextComponent affinityInfo;
		if (affinityLeveling.getLevel() != affinityLeveling.getMaxLevel()) {
			affinityInfo = new TranslationTextComponent(String.format("%s: %.0f/%.0f", affinityLeveling.getLevelName(), affinityLeveling.getXp(), affinityLeveling.getRequiredXp()));
		} else {
			affinityInfo = new TranslationTextComponent(String.format("%s", affinityLeveling.getLevelName()));
		}
		this.font.func_243248_b(matrixStack, affinityInfo, 65.0f, 92.0f, 4210752);


		// Gradient left-top = #479238
		// Gradient Right-bottom = #abf99b
		// Tracking Chip
		this.font.func_243248_b(matrixStack, new TranslationTextComponent("Tracking Chip"), 18.0f, 113.0f, 4210752);
		// If enabled draw a 3x3 green box starting at 12x, 115y

		// Whistle
		this.font.func_243248_b(matrixStack, new TranslationTextComponent("Whistle"), 124.0f, 113.0f, 4210752);
		// If enabled draw a 3x3 green box starting at 118x, 115y
	}

	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		this.mousePosx = (float)mouseX;
		this.mousePosY = (float)mouseY;
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	public ITextComponent getTitle() {
		return this.horseEntity.getDisplayName();
	}
}
