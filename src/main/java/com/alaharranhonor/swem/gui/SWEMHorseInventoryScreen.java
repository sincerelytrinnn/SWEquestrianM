package com.alaharranhonor.swem.gui;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.container.SWEMHorseInventoryContainer;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.entities.needs.HungerNeed;
import com.alaharranhonor.swem.entities.needs.ThirstNeed;
import com.alaharranhonor.swem.entities.progression.leveling.AffinityLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.HealthLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.JumpLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.SpeedLeveling;
import com.alaharranhonor.swem.util.SWEMUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Timer;
import java.util.UUID;


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
		this.xSize = 176;
		this.ySize = 222;
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
		this.font.drawText(matrixStack, new TranslationTextComponent("Tack"), 15, 6, 4210752);
		this.font.drawText(matrixStack, new TranslationTextComponent("Stats"), 65, 6,4210752);

		// Owner name.
		this.font.drawText(matrixStack, new StringTextComponent(SWEMUtil.checkTextOverflow(this.horseEntity.getOwnerName(), 22)), 65.2f, 36.0f, 4210752);

		// Jump TEXT
		TranslationTextComponent jumpInfo;
		if (jumpLeveling.getLevel() != jumpLeveling.getMaxLevel()) {
			jumpInfo = new TranslationTextComponent(String.format("%s: %.0f/%.0f", jumpLeveling.getLevelName(), jumpLeveling.getXp(), jumpLeveling.getRequiredXp()));
		} else {
			jumpInfo = new TranslationTextComponent(String.format("%s", jumpLeveling.getLevelName()));
		}
		this.font.drawText(matrixStack, jumpInfo, 65.0f, 49.0f, 4210752);

		// Speed TEXT
		TranslationTextComponent speedInfo;
		if (speedLeveling.getLevel() != speedLeveling.getMaxLevel()) {
			speedInfo = new TranslationTextComponent(String.format("%s: %.0f/%.0f", speedLeveling.getLevelName(), speedLeveling.getXp(), speedLeveling.getRequiredXp()));
		} else {
			speedInfo = new TranslationTextComponent(String.format("%s", speedLeveling.getLevelName()));
		}
		this.font.drawText(matrixStack, speedInfo, 65.0f, 64.0f, 4210752);

		// Health TEXT
		this.font.drawText(matrixStack, new TranslationTextComponent(String.format("%s: %.1f/%.0f", healthLeveling.getLevelName(), this.horseEntity.getHealth(), this.horseEntity.getMaxHealth())), 65.0f, 78.0f, 4210752);

		// Affinity TEXT
		TranslationTextComponent affinityInfo;
		if (affinityLeveling.getLevel() != affinityLeveling.getMaxLevel()) {
			affinityInfo = new TranslationTextComponent(String.format("%s: %.0f/%.0f", affinityLeveling.getLevelName(), affinityLeveling.getXp(), affinityLeveling.getRequiredXp()));
		} else {
			affinityInfo = new TranslationTextComponent(String.format("%s", affinityLeveling.getLevelName()));
		}
		this.font.drawText(matrixStack, affinityInfo, 65.0f, 92.0f, 4210752);


		// Overlay, for Tracker.
		//fillGradient(matrixStack, 9, 117, 12, 120, 0xFF479238, 0xFF85f96d);

		// Hunger.
		switch (this.horseEntity.getDataManager().get(HungerNeed.HungerState.ID)) {
			case 0: {
				fill(matrixStack, 45, 121, 46, 124, 0xFFc6c6c6);
				fill(matrixStack, 47, 121, 53, 124, 0xFFc6c6c6);
				fill(matrixStack, 53, 121, 59, 124, 0xFFc6c6c6);
				fill(matrixStack, 59, 121, 65, 124, 0xFFc6c6c6);
				fill(matrixStack, 65, 121, 70, 124, 0xFFc6c6c6);
				break;
			}
			case 1: {
				fill(matrixStack, 53, 121, 59, 124, 0xFFc6c6c6);
				fill(matrixStack, 59, 121, 65, 124, 0xFFc6c6c6);
				fill(matrixStack, 65, 121, 70, 124, 0xFFc6c6c6);
				break;
			}
			case 2: {
				fill(matrixStack, 59, 121, 65, 124, 0xFFc6c6c6);
				fill(matrixStack, 65, 121, 70, 124, 0xFFc6c6c6);
				break;
			}
			case 3: {
				fill(matrixStack, 65, 121, 70, 124, 0xFFc6c6c6);
				break;
			}
		}

		// Thirst
		switch (this.horseEntity.getDataManager().get(ThirstNeed.ThirstState.ID)) {
			case 0: {
				fill(matrixStack, 86, 121, 87, 124, 0xFFc6c6c6);
				fill(matrixStack, 88, 121, 94, 124, 0xFFc6c6c6);
				fill(matrixStack, 94, 121, 100, 124, 0xFFc6c6c6);
				fill(matrixStack, 100, 121, 106, 124, 0xFFc6c6c6);
				fill(matrixStack, 106, 121, 111, 124, 0xFFc6c6c6);
				break;
			}
			case 1: {
				fill(matrixStack, 94, 121, 100, 124, 0xFFc6c6c6);
				fill(matrixStack, 100, 121, 106, 124, 0xFFc6c6c6);
				fill(matrixStack, 106, 121, 111, 124, 0xFFc6c6c6);
				break;
			}
			case 2: {
				fill(matrixStack, 100, 121, 106, 124, 0xFFc6c6c6);
				fill(matrixStack, 106, 121, 111, 124, 0xFFc6c6c6);
				break;
			}
			case 3: {
				fill(matrixStack, 106, 121, 111, 124, 0xFFc6c6c6);
				break;
			}
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
