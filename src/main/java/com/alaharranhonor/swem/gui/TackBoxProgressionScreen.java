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
import com.alaharranhonor.swem.gui.widgets.ProgressionBoxes;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.advancements.AdvancementState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;

import java.util.List;

public class TackBoxProgressionScreen extends Screen {
    private static final ResourceLocation WIDGETS_LOCATION =
            new ResourceLocation("minecraft", "textures/gui/advancements/widgets.png");
    private static final ResourceLocation TACKBOX_PROGRESSION_TEXTURE =
            new ResourceLocation(SWEM.MOD_ID, "textures/gui/container/tackbox_progression.png");
    private static final int[] TEST_SPLIT_OFFSETS = new int[]{0, 10, -10, 25, -25};
    private TackBoxContainer container;
    private PlayerInventory inv;
    private ITextComponent text;
    private int guiLeft;
    private int guiTop;
    private int xSize;
    private int ySize;

    /**
     * Instantiates a new Tack box progression screen.
     *
     * @param screenContainer the screen container
     * @param inv             the inv
     * @param defaultTitle    the default title
     * @param titleIn         the title in
     */
    public TackBoxProgressionScreen(
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

    /**
     * Gets max width.
     *
     * @param pManager the p manager
     * @param pText    the p text
     * @return the max width
     */
    private static float getMaxWidth(CharacterManager pManager, List<ITextProperties> pText) {
        return (float) pText.stream().mapToDouble(pManager::stringWidth).max().orElse(0.0D);
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
        this.minecraft.getTextureManager().bind(TACKBOX_PROGRESSION_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
        this.font.draw(
                matrixStack, this.title, (float) this.guiLeft + 7, (float) this.guiTop + 29, 4210752);

        for (ProgressionBoxes pb : ProgressionBoxes.values()) {
            Advancement adv =
                    Minecraft.getInstance()
                            .player
                            .connection
                            .getAdvancements()
                            .getAdvancements()
                            .get(new ResourceLocation(SWEM.MOD_ID, pb.getPath()));
            AdvancementProgress advProgress =
                    Minecraft.getInstance().player.connection.getAdvancements().progress.get(adv);
            if (adv == null) {
                // Advancement has not been completed, overlay gray box.
                continue;
            }

            // Not sure what this call does :o
            // I've been told it's best to just leave it...
            // this.blit(matrixStack, pb.getX() + this.guiLeft, pb.getY() + this.guiTop, this.xSize, 25,
            // 3, 3);

            if (pb.isMouseOver(mouseX, mouseY, this.guiLeft, this.guiTop)) {
                // Place the same render call that happens in AdvancementsScreen
                drawAdvancementHover(
                        matrixStack, mouseX, mouseY, 0, this.xSize, this.ySize, adv, advProgress);
                // AdvancementsScreen
            }
        }

    /*
    if ((mouseX > this.guiLeft && mouseX < this.guiLeft + ((int) advancement.getDisplay().getX())Size) && (mouseY > this.guiTop && mouseY < this.guiTop + ((int) advancement.getDisplay().getY())Size)) {
    	for (ProgressionBoxes pb : ProgressionBoxes.values()) {
    		if (pb.isMouseOver(mouseX, mouseY, this.guiLeft, this.guiTop)) {
    			Minecraft.getInstance().player.connection.getAdvancements().progress.keySet().forEach((adv) -> {
    				if (adv.getId().getNamespace().equals("swem")) {
    					System.out.println(adv.getId().toString());
    				}
    			});

    			Advancement adv = Minecraft.getInstance().player.connection.getAdvancements().getAdvancements().get(new ResourceLocation(SWEM.MOD_ID, pb.getPath()));
    			// Place the same render call that happens in AdvancementsScreen
    			this.font.draw(matrixStack, adv.getDisplay().getTitle().copy().append("\n").append(adv.getDisplay().getDescription()), pb.getX() + this.guiLeft, pb.getY() + this.guiTop, 6724056);
    		}
    	}
    }
     */

    }

    /**
     * Draw advancement hover.
     *
     * @param pMatrixStack the p matrix stack
     * @param pX           the p x
     * @param pY           the p y
     * @param pFade        the p fade
     * @param pWidth       the p width
     * @param pHeight      the p height
     * @param advancement  the advancement
     * @param progress     the progress
     */
    public void drawAdvancementHover(
            MatrixStack pMatrixStack,
            int pX,
            int pY,
            float pFade,
            int pWidth,
            int pHeight,
            Advancement advancement,
            AdvancementProgress progress) {
        final IReorderingProcessor advancementTitle =
                LanguageMap.getInstance()
                        .getVisualOrder(
                                this.minecraft.font.substrByWidth(advancement.getDisplay().getTitle(), 163));
        final int advancementWidth =
                (29
                        + this.minecraft.font.width(advancementTitle)
                        + ((advancement.getMaxCriteraRequired()) > 1
                        ? this.minecraft.font.width("  ")
                        + this.minecraft.font.width("0")
                        * (String.valueOf(advancement.getMaxCriteraRequired()).length())
                        * 2
                        + this.minecraft.font.width("/")
                        : 0))
                        + 3
                        + 5;

        int iText = advancement.getMaxCriteraRequired();
        int jText = String.valueOf(iText).length();
        int kText =
                iText > 1
                        ? Minecraft.getInstance().font.width("  ")
                        + Minecraft.getInstance().font.width("0") * jText * 2
                        + Minecraft.getInstance().font.width("/")
                        : 0;
        int sWidth = 29 + Minecraft.getInstance().font.width(advancementTitle) + kText;
        List<IReorderingProcessor> description =
                LanguageMap.getInstance()
                        .getVisualOrder(
                                this.findOptimalLines(
                                        TextComponentUtils.mergeStyles(
                                                advancement.getDisplay().getDescription().copy(),
                                                Style.EMPTY.withColor(advancement.getDisplay().getFrame().getChatColor())),
                                        sWidth));

        boolean flag =
                pWidth + pX + advancement.getDisplay().getX() + advancementWidth + 26 >= this.xSize;
        String s = progress == null ? null : progress.getProgressText();
        int i = s == null ? 0 : this.minecraft.font.width(s);
        boolean flag1 = 113 - pY - advancement.getDisplay().getY() - 26 <= 6 + description.size() * 9;
        float f = progress == null ? 0.0F : progress.getPercent();
        int j = MathHelper.floor(f * (float) advancementWidth);
        AdvancementState advancementstate;
        AdvancementState advancementstate1;
        AdvancementState advancementstate2;
        if (f >= 1.0F) {
            j = advancementWidth / 2;
            advancementstate = AdvancementState.OBTAINED;
            advancementstate1 = AdvancementState.OBTAINED;
            advancementstate2 = AdvancementState.OBTAINED;
        } else if (j < 2) {
            j = advancementWidth / 2;
            advancementstate = AdvancementState.UNOBTAINED;
            advancementstate1 = AdvancementState.UNOBTAINED;
            advancementstate2 = AdvancementState.UNOBTAINED;
        } else if (j > advancementWidth - 2) {
            j = advancementWidth / 2;
            advancementstate = AdvancementState.OBTAINED;
            advancementstate1 = AdvancementState.OBTAINED;
            advancementstate2 = AdvancementState.UNOBTAINED;
        } else {
            advancementstate = AdvancementState.OBTAINED;
            advancementstate1 = AdvancementState.UNOBTAINED;
            advancementstate2 = AdvancementState.UNOBTAINED;
        }

        int k = advancementWidth - j;
        this.minecraft.getTextureManager().bind(WIDGETS_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        int l = pY + ((int) advancement.getDisplay().getY());
        int i1;
        if (flag) {
            i1 = pX + ((int) advancement.getDisplay().getX()) - advancementWidth + 26 + 6;
        } else {
            i1 = pX + ((int) advancement.getDisplay().getX());
        }

        int j1 = 32 + description.size() * 9;
        if (!description.isEmpty()) {
            if (flag1) {
                this.render9Sprite(pMatrixStack, i1, l + 26 - j1, advancementWidth, j1, 10, 200, 26, 0, 52);
            } else {
                this.render9Sprite(pMatrixStack, i1, l, advancementWidth, j1, 10, 200, 26, 0, 52);
            }
        }

        this.blit(pMatrixStack, i1, l, 0, advancementstate.getIndex() * 26, j, 26);
        this.blit(pMatrixStack, i1 + j, l, 200 - k, advancementstate1.getIndex() * 26, k, 26);
        this.blit(
                pMatrixStack,
                pX + ((int) advancement.getDisplay().getX()) + 3,
                pY + ((int) advancement.getDisplay().getY()),
                advancement.getDisplay().getFrame().getTexture(),
                128 + advancementstate2.getIndex() * 26,
                26,
                26);
        if (flag) {
            this.minecraft.font.drawShadow(
                    pMatrixStack,
                    advancementTitle,
                    (float) (i1 + 5),
                    (float) (pY + ((int) advancement.getDisplay().getY()) + 9),
                    -1);
            if (s != null) {
                this.minecraft.font.drawShadow(
                        pMatrixStack,
                        s,
                        (float) (pX + ((int) advancement.getDisplay().getX()) - i),
                        (float) (pY + ((int) advancement.getDisplay().getY()) + 9),
                        -1);
            }
        } else {
            this.minecraft.font.drawShadow(
                    pMatrixStack,
                    advancementTitle,
                    (float) (pX + ((int) advancement.getDisplay().getX()) + 32),
                    (float) (pY + ((int) advancement.getDisplay().getY()) + 9),
                    -1);
            if (s != null) {
                this.minecraft.font.drawShadow(
                        pMatrixStack,
                        s,
                        (float) (pX + ((int) advancement.getDisplay().getX()) + advancementWidth - i - 5),
                        (float) (pY + ((int) advancement.getDisplay().getY()) + 9),
                        -1);
            }
        }

        if (flag1) {
            for (int k1 = 0; k1 < description.size(); ++k1) {
                this.minecraft.font.draw(
                        pMatrixStack,
                        description.get(k1),
                        (float) (i1 + 5),
                        (float) (l + 26 - j1 + 7 + k1 * 9),
                        -5592406);
            }
        } else {
            for (int l1 = 0; l1 < description.size(); ++l1) {
                this.minecraft.font.draw(
                        pMatrixStack,
                        description.get(l1),
                        (float) (i1 + 5),
                        (float) (pY + ((int) advancement.getDisplay().getY()) + 9 + 17 + l1 * 9),
                        -5592406);
            }
        }

        this.minecraft
                .getItemRenderer()
                .renderAndDecorateFakeItem(
                        advancement.getDisplay().getIcon(),
                        pX + ((int) advancement.getDisplay().getX()) + 8,
                        pY + ((int) advancement.getDisplay().getY()) + 5);
    }

    /**
     * Render 9 sprite.
     *
     * @param pMatrixStack the p matrix stack
     * @param pX           the p x
     * @param pY           the p y
     * @param pWidth       the p width
     * @param pHeight      the p height
     * @param pPadding     the p padding
     * @param pUWidth      the p u width
     * @param pVHeight     the p v height
     * @param pUOffset     the p u offset
     * @param pVOffset     the p v offset
     */
    protected void render9Sprite(
            MatrixStack pMatrixStack,
            int pX,
            int pY,
            int pWidth,
            int pHeight,
            int pPadding,
            int pUWidth,
            int pVHeight,
            int pUOffset,
            int pVOffset) {
        this.blit(pMatrixStack, pX, pY, pUOffset, pVOffset, pPadding, pPadding);
        this.renderRepeating(
                pMatrixStack,
                pX + pPadding,
                pY,
                pWidth - pPadding - pPadding,
                pPadding,
                pUOffset + pPadding,
                pVOffset,
                pUWidth - pPadding - pPadding,
                pVHeight);
        this.blit(
                pMatrixStack,
                pX + pWidth - pPadding,
                pY,
                pUOffset + pUWidth - pPadding,
                pVOffset,
                pPadding,
                pPadding);
        this.blit(
                pMatrixStack,
                pX,
                pY + pHeight - pPadding,
                pUOffset,
                pVOffset + pVHeight - pPadding,
                pPadding,
                pPadding);
        this.renderRepeating(
                pMatrixStack,
                pX + pPadding,
                pY + pHeight - pPadding,
                pWidth - pPadding - pPadding,
                pPadding,
                pUOffset + pPadding,
                pVOffset + pVHeight - pPadding,
                pUWidth - pPadding - pPadding,
                pVHeight);
        this.blit(
                pMatrixStack,
                pX + pWidth - pPadding,
                pY + pHeight - pPadding,
                pUOffset + pUWidth - pPadding,
                pVOffset + pVHeight - pPadding,
                pPadding,
                pPadding);
        this.renderRepeating(
                pMatrixStack,
                pX,
                pY + pPadding,
                pPadding,
                pHeight - pPadding - pPadding,
                pUOffset,
                pVOffset + pPadding,
                pUWidth,
                pVHeight - pPadding - pPadding);
        this.renderRepeating(
                pMatrixStack,
                pX + pPadding,
                pY + pPadding,
                pWidth - pPadding - pPadding,
                pHeight - pPadding - pPadding,
                pUOffset + pPadding,
                pVOffset + pPadding,
                pUWidth - pPadding - pPadding,
                pVHeight - pPadding - pPadding);
        this.renderRepeating(
                pMatrixStack,
                pX + pWidth - pPadding,
                pY + pPadding,
                pPadding,
                pHeight - pPadding - pPadding,
                pUOffset + pUWidth - pPadding,
                pVOffset + pPadding,
                pUWidth,
                pVHeight - pPadding - pPadding);
    }

    /**
     * Render repeating.
     *
     * @param pMatrixStack the p matrix stack
     * @param pX           the p x
     * @param pY           the p y
     * @param pBorderToU   the p border to u
     * @param pBorderToV   the p border to v
     * @param pUOffset     the p u offset
     * @param pVOffset     the p v offset
     * @param pUWidth      the p u width
     * @param pVHeight     the p v height
     */
    protected void renderRepeating(
            MatrixStack pMatrixStack,
            int pX,
            int pY,
            int pBorderToU,
            int pBorderToV,
            int pUOffset,
            int pVOffset,
            int pUWidth,
            int pVHeight) {
        for (int i = 0; i < pBorderToU; i += pUWidth) {
            int j = pX + i;
            int k = Math.min(pUWidth, pBorderToU - i);

            for (int l = 0; l < pBorderToV; l += pVHeight) {
                int i1 = pY + l;
                int j1 = Math.min(pVHeight, pBorderToV - l);
                this.blit(pMatrixStack, j, i1, pUOffset, pVOffset, k, j1);
            }
        }
    }

    /**
     * Find optimal lines list.
     *
     * @param pComponent the p component
     * @param pMaxWidth  the p max width
     * @return the list
     */
    private List<ITextProperties> findOptimalLines(ITextComponent pComponent, int pMaxWidth) {
        CharacterManager charactermanager = this.minecraft.font.getSplitter();
        List<ITextProperties> list = null;
        float f = Float.MAX_VALUE;

        for (int i : TEST_SPLIT_OFFSETS) {
            List<ITextProperties> list1 =
                    charactermanager.splitLines(pComponent, pMaxWidth - i, Style.EMPTY);
            float f1 = Math.abs(getMaxWidth(charactermanager, list1) - (float) pMaxWidth);
            if (f1 <= 10.0F) {
                return list1;
            }

            if (f1 < f) {
                f = f1;
                list = list1;
            }
        }

        return list;
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
                minecraft.setScreen(
                        new TackBoxBirthScreen(
                                this.container,
                                this.inv,
                                this.text,
                                new TranslationTextComponent("container.swem.tack_box_certificate")));
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
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
