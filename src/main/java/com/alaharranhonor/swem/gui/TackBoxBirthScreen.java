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
import com.alaharranhonor.swem.container.TackBoxContainer;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.entities.progression.leveling.AffinityLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.HealthLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.JumpLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.SpeedLeveling;
import com.alaharranhonor.swem.util.SWEMUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TackBoxBirthScreen extends Screen {
  private static final ResourceLocation TACKBOX_BIRTH_TEXTURE =
      new ResourceLocation(SWEM.MOD_ID, "textures/gui/container/tackbox_birth.png");

  private TackBoxContainer container;
  private PlayerInventory inv;
  private ITextComponent text;
  private int guiLeft;
  private int guiTop;
  private int xSize;
  private int ySize;

  /**
   * Instantiates a new Tack box birth screen.
   *
   * @param screenContainer the screen container
   * @param inv the inv
   * @param defaultTitle the default title
   * @param titleIn the title in
   */
  protected TackBoxBirthScreen(
      TackBoxContainer screenContainer,
      PlayerInventory inv,
      ITextComponent defaultTitle,
      ITextComponent titleIn) {
    super(titleIn);
    this.container = screenContainer;
    this.inv = inv;
    this.text = defaultTitle;
    this.xSize = 250;
    this.ySize = 209;
  }

  @Override
  public void init(Minecraft minecraft, int width, int height) {
    super.init(minecraft, width, height);
    this.guiLeft = (this.width - this.xSize) / 2;
    this.guiTop = (this.height - this.ySize) / 2;
  }

  @Override
  public boolean isPauseScreen() {
    return false;
  }

  @Override
  public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(matrixStack);
    super.render(matrixStack, mouseX, mouseY, partialTicks);
    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.minecraft.getTextureManager().bind(TACKBOX_BIRTH_TEXTURE);
    int i = (this.width - this.xSize) / 2;
    int j = (this.height - this.ySize) / 2;
    this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

    // Title
    this.font.draw(
        matrixStack, this.title, (float) this.guiLeft + 7, (float) this.guiTop + 29, 4210752);

    if (this.container.horse != null) {
      SWEMHorseEntityBase horse = this.container.horse;

      SpeedLeveling speedLeveling = horse.progressionManager.getSpeedLeveling();
      AffinityLeveling affinityLeveling = horse.progressionManager.getAffinityLeveling();
      JumpLeveling jumpLeveling = horse.progressionManager.getJumpLeveling();
      HealthLeveling healthLeveling = horse.progressionManager.getHealthLeveling();

      float textX = this.guiLeft + 24.0f;

      // Horse name.
      this.font.draw(matrixStack, horse.getDisplayName(), textX, this.guiTop + 40, 4210752);

      // Owner name.
      this.font.draw(
          matrixStack,
          new StringTextComponent(SWEMUtil.checkTextOverflow(horse.getOwnerName(), 22)),
          textX + 0.2f,
          this.guiTop + 54,
          4210752);

      // Jump TEXT
      TranslationTextComponent jumpInfo;
      if (jumpLeveling.getLevel() != jumpLeveling.getMaxLevel()) {
        jumpInfo =
            new TranslationTextComponent(
                String.format(
                    "%s: %.0f/%.0f",
                    jumpLeveling.getLevelName(),
                    jumpLeveling.getXp(),
                    jumpLeveling.getRequiredXp()));
      } else {
        jumpInfo = new TranslationTextComponent(String.format("%s", jumpLeveling.getLevelName()));
      }
      this.font.draw(matrixStack, jumpInfo, textX, this.guiTop + 68, 4210752);

      // Speed TEXT
      TranslationTextComponent speedInfo;
      if (speedLeveling.getLevel() != speedLeveling.getMaxLevel()) {
        speedInfo =
            new TranslationTextComponent(
                String.format(
                    "%s: %.0f/%.0f",
                    speedLeveling.getLevelName(),
                    speedLeveling.getXp(),
                    speedLeveling.getRequiredXp()));
      } else {
        speedInfo = new TranslationTextComponent(String.format("%s", speedLeveling.getLevelName()));
      }
      this.font.draw(matrixStack, speedInfo, textX, this.guiTop + 82, 4210752);

      // Health TEXT
      this.font.draw(
          matrixStack,
          new TranslationTextComponent(
              String.format(
                  "%s: %.1f/%.0f",
                  healthLeveling.getLevelName(), horse.getHealth(), horse.getMaxHealth())),
          textX,
          this.guiTop + 96,
          4210752);

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
        affinityInfo =
            new TranslationTextComponent(
                String.format("%s: ", affinityLeveling.getLevelName())
                    + String.format("%.0f", currentXP)
                    + (currentThousands ? "k" : "")
                    + "/"
                    + String.format("%.0f", requiredXP)
                    + (requiredThousands ? "k" : ""));
      } else {
        affinityInfo =
            new TranslationTextComponent(String.format("%s", affinityLeveling.getLevelName()));
      }
      this.font.draw(matrixStack, affinityInfo, textX, this.guiTop + 111, 4210752);
    }
  }

  @Override
  public boolean mouseClicked(double mouseX, double mouseY, int button) {
    if (mouseY >= this.guiTop && mouseY <= this.guiTop + 22) {
      if (mouseX >= this.guiLeft + 3 && mouseX <= this.guiLeft + 27) {
        this.getMinecraft()
            .getSoundManager()
            .play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        minecraft.setScreen(new TackBoxDefaultScreen(this.container, this.inv, this.text));
        return true;
      }
      if (mouseX >= this.guiLeft + 34 && mouseX <= this.guiLeft + 56) {
        this.getMinecraft()
            .getSoundManager()
            .play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        return true;
      }
      if (mouseX >= this.guiLeft + 65 && mouseX <= this.guiLeft + 87) {
        this.getMinecraft()
            .getSoundManager()
            .play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        minecraft.setScreen(
            new TackBoxGeneticsScreen(
                this.container,
                this.inv,
                this.text,
                new TranslationTextComponent("container.swem.tack_box_genetics")));
        return true;
      }
      if (mouseX >= this.guiLeft + 96 && mouseX <= this.guiLeft + 118) {
        this.getMinecraft()
            .getSoundManager()
            .play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        minecraft.setScreen(
            new TackBoxProgressionScreen(
                this.container,
                this.inv,
                this.text,
                new TranslationTextComponent("container.swem.tack_box_progression")));
        return true;
      }
    }
    return super.mouseClicked(mouseX, mouseY, button);
  }
}
