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
import com.alaharranhonor.swem.network.HorseStateChange;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.util.SWEMUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;
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

	private Button permissionButton;



	public SWEMHorseInventoryScreen(SWEMHorseInventoryContainer p_i51084_1_, PlayerInventory playerInventoryIn, ITextComponent title) {
		super(p_i51084_1_, playerInventoryIn, title);
		//this(p_i51084_1_, playerInventoryIn, p_i51084_1_.horse);
		this.passEvents = false;
		this.imageWidth = 176;
		this.imageHeight = 222;
		this.horseEntity = p_i51084_1_.horse;
		this.inventoryLabelY = this.imageHeight - 94;
		this.titleLabelX = 65;
		this.titleLabelY = 22;
	}

	@Override
	protected void init() {
		super.init();
		this.permissionButton = new Button(this.leftPos  + 118, this.topPos + 109, 52, 20, new StringTextComponent(horseEntity.getPermissionState().name()), p_onPress_1_ -> {
			SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(9, horseEntity.getId()));
			p_onPress_1_.setMessage(new StringTextComponent(SWEMHorseEntityBase.RidingPermission.values()[(horseEntity.getPermissionState().ordinal() + 1) % 3].name()));
		});

		if (!Objects.equals(this.horseEntity.getOwnerUUID(), Minecraft.getInstance().player.getUUID())) {
			this.permissionButton.active = false;
		}

		this.addButton(this.permissionButton);
	}

	@Override
	public SWEMHorseInventoryContainer getMenu() {
		return this.menu;
	}

	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(HORSE_GUI_TEXTURES);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

		// Not sure what this renders, some weird box.
		//if (this.horseEntity.isSaddleable()) {
//			this.blit(matrixStack, i + 7, j + 35, 18, this.imageHeight + 54, 18, 18);
//		}

		// Render the horse
		//InventoryScreen.drawEntityOnScreen(i + 51, j + 60, 17, (float)(i + 51) - this.mousePosx, (float)(j + 75 - 50) - this.mousePosY, this.horseEntity);
	}
	
	@Override
	protected void renderLabels(MatrixStack matrixStack, int x, int y) {
		super.renderLabels(matrixStack, x, y);

		SpeedLeveling speedLeveling = this.horseEntity.progressionManager.getSpeedLeveling();
		AffinityLeveling affinityLeveling = this.horseEntity.progressionManager.getAffinityLeveling();
		JumpLeveling jumpLeveling = this.horseEntity.progressionManager.getJumpLeveling();
		HealthLeveling healthLeveling = this.horseEntity.progressionManager.getHealthLeveling();

		// Top Text
		this.font.draw(matrixStack, new TranslationTextComponent("Tack"), 15, 6, 4210752);
		this.font.draw(matrixStack, new TranslationTextComponent("Stats"), 65, 6,4210752);

		// Owner name.
		this.font.draw(matrixStack, new StringTextComponent(SWEMUtil.checkTextOverflow(this.horseEntity.getOwnerName(), 22)), 65.2f, 36.0f, 4210752);

		// Jump TEXT
		TranslationTextComponent jumpInfo;
		if (jumpLeveling.getLevel() != jumpLeveling.getMaxLevel()) {
			jumpInfo = new TranslationTextComponent(String.format("%s: %.0f/%.0f", jumpLeveling.getLevelName(), jumpLeveling.getXp(), jumpLeveling.getRequiredXp()));
		} else {
			jumpInfo = new TranslationTextComponent(String.format("%s", jumpLeveling.getLevelName()));
		}
		this.font.draw(matrixStack, jumpInfo, 65.0f, 49.0f, 4210752);

		// Speed TEXT
		TranslationTextComponent speedInfo;
		if (speedLeveling.getLevel() != speedLeveling.getMaxLevel()) {
			speedInfo = new TranslationTextComponent(String.format("%s: %.0f/%.0f", speedLeveling.getLevelName(), speedLeveling.getXp(), speedLeveling.getRequiredXp()));
		} else {
			speedInfo = new TranslationTextComponent(String.format("%s", speedLeveling.getLevelName()));
		}
		this.font.draw(matrixStack, speedInfo, 65.0f, 64.0f, 4210752);

		// Health TEXT
		this.font.draw(matrixStack, new TranslationTextComponent(String.format("%s: %.1f/%.0f", healthLeveling.getLevelName(), this.horseEntity.getHealth(), this.horseEntity.getMaxHealth())), 65.0f, 78.0f, 4210752);

		// Affinity TEXT
		TranslationTextComponent affinityInfo;
		if (affinityLeveling.getLevel() != affinityLeveling.getMaxLevel()) {
			affinityInfo = new TranslationTextComponent(String.format("%s: %.0f/%.0f", affinityLeveling.getLevelName(), affinityLeveling.getXp(), affinityLeveling.getRequiredXp()));
		} else {
			affinityInfo = new TranslationTextComponent(String.format("%s", affinityLeveling.getLevelName()));
		}
		this.font.draw(matrixStack, affinityInfo, 65.0f, 92.0f, 4210752);


		// Overlay, for Tracker.
		if (this.horseEntity.isBeingTracked()) {
			fillGradient(matrixStack, 9, 117, 12, 120, 0xFF479238, 0xFF85f96d);
		}

		// Hunger.
		switch (this.horseEntity.getEntityData().get(HungerNeed.HungerState.ID)) {
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
		switch (this.horseEntity.getEntityData().get(ThirstNeed.ThirstState.ID)) {
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
		this.renderTooltip(matrixStack, mouseX, mouseY);
		this.permissionButton.render(matrixStack, mouseX, mouseY, partialTicks);
	}

}
