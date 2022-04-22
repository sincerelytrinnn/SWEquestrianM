package com.alaharranhonor.swem.gui;


/*
 * All Rights Reserved
 *
 * Copyright (c) 2021, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.container.SWEMHorseInventoryContainer;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.entities.progression.leveling.AffinityLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.HealthLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.JumpLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.SpeedLeveling;
import com.alaharranhonor.swem.gui.widgets.CustomHeightButton;
import com.alaharranhonor.swem.network.HorseStateChange;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.util.SWEMUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


@OnlyIn(Dist.CLIENT)
public class SWEMHorseInventoryScreen extends ContainerScreen<SWEMHorseInventoryContainer> implements IHasContainer<SWEMHorseInventoryContainer> {

	private static final ResourceLocation HORSE_GUI_TEXTURES = new ResourceLocation(SWEM.MOD_ID, "textures/gui/container/swem_horse.png");
	/** The EntityHorse whose inventory is currently being accessed. */
	private SWEMHorseEntityBase horseEntity;
	/** The mouse x-position recorded during the last rendered frame. */
	private float mousePosx;
	/** The mouse y-position recorded during the last renderered frame. */
	private float mousePosY;

	public static Timer TIMER;
	private int tackSet = 0;

	private Button permissionButton;


	/**
	 * Instantiates a new Swem horse inventory screen.
	 *
	 * @param p_i51084_1_       the p i 51084 1
	 * @param playerInventoryIn the player inventory in
	 * @param title             the title
	 */
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
		TIMER = new Timer();
		TIMER.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				tackSet = (tackSet + 1) % 3;
			}
		}, 3000, 3000);
	}

	@Override
	protected void init() {
		super.init();
		this.permissionButton = new CustomHeightButton(this.leftPos  + 122, this.topPos + 111, 48, 14, new StringTextComponent(horseEntity.getPermissionState().name()), p_onPress_1_ -> {
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

		// Flash different tack sets.
		// Tackset 2 = adventure
		if (tackSet != 0) {
			for (int xSlot = 0; xSlot < 2; xSlot++) {
				for (int ySlot = 0; ySlot < 3; ySlot++) {
					blit(matrixStack, this.leftPos + 8 + (xSlot * 21), this.topPos + 17 + (ySlot * 21), 180 + (xSlot * 21), 4 + (ySlot * 21) + (tackSet == 2 ? 67 : 0), 16, 16);
				}
			}
		}


		//overlay tack slots
		for (Slot slot : this.menu.slots) {
			if (slot.hasItem()) {
				blit(matrixStack, this.leftPos + slot.x, this.topPos + slot.y, 8, 140, 16, 16);
			}
		}

		// Overlay, for Tracker.
		if (this.horseEntity.isBeingTracked()) {
			blit(matrixStack, this.leftPos + 9, this.topPos + 117, 180, 138, 3, 3);
		}

		int hungerX = this.leftPos + 41;
		int hungerY = this.topPos + 121;
		int hungerXOffset = 179;
		int hungerYOffset = 145;
		int hungerHeight = 3;

		switch (this.horseEntity.getEntityData().get(SWEMHorseEntityBase.HUNGER_LEVEL)) {
			case 0: {
				break;
			}
			case 1: {
				blit(matrixStack, hungerX, hungerY, hungerXOffset, hungerYOffset, 12, hungerHeight);
				break;
			}
			case 2: {
				blit(matrixStack, hungerX, hungerY, hungerXOffset, hungerYOffset, 18, hungerHeight);
				break;
			}
			case 3: {
				blit(matrixStack, hungerX, hungerY, hungerXOffset, hungerYOffset, 24, hungerHeight);
				break;
			}
			case 4: {
				blit(matrixStack, hungerX, hungerY, hungerXOffset, hungerYOffset, 28, hungerHeight);
				break;
			}
		}


		int thirstX = this.leftPos + 82;
		int thirstY = this.topPos + 121;
		int thirstXOffset = 179;
		int thirstYOffset = 151;
		int thirstHeight = 3;
		switch (this.horseEntity.getEntityData().get(SWEMHorseEntityBase.THIRST_LEVEL)) {
			case 0: {
				break;
			}
			case 1: {
				blit(matrixStack, thirstX, thirstY, thirstXOffset, thirstYOffset, 12, thirstHeight);
				break;
			}
			case 2: {
				blit(matrixStack, thirstX, thirstY, thirstXOffset, thirstYOffset, 18, thirstHeight);
				break;
			}
			case 3: {
				blit(matrixStack, thirstX, thirstY, thirstXOffset, thirstYOffset, 24, thirstHeight);
				break;
			}
			case 4: {
				blit(matrixStack, thirstX, thirstY, thirstXOffset, thirstYOffset, 28, thirstHeight);
				break;
			}
		}

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
			float currentXP = affinityLeveling.getXp();
			float requiredXP = affinityLeveling.getRequiredXp();
			boolean currentThousands = false;
			boolean requiredThousands = false;
			if (currentXP > 1000) {
				currentXP /= 1000;
				currentThousands = true;
			}
			if (requiredXP > 1000) {
				requiredXP /= 1000;
				requiredThousands = true;
			}
			affinityInfo = new TranslationTextComponent(String.format("%s: ", affinityLeveling.getLevelName()) + String.format("%.0f", currentXP) + (currentThousands ? "k" : "") + "/" +  String.format("%.0f", requiredXP) + (requiredThousands ? "k" : ""));
		} else {
			affinityInfo = new TranslationTextComponent(String.format("%s", affinityLeveling.getLevelName()));
		}
		this.font.draw(matrixStack, affinityInfo, 65.0f, 92.0f, 4210752);

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
