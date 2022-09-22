package com.alaharranhonor.swem.client.layers.player;
/*
 * All Rights Reserved
 *
 * Copyright (c) 2022, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.item.GeoArmorItem;

public class GeckoBipedArmorLayer<
        T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>>
        extends BipedArmorLayer<T, M, A> {

    public GeckoBipedArmorLayer(IEntityRenderer<T, M> p_i50936_1_, A p_i50936_2_, A p_i50936_3_) {
        super(p_i50936_1_, p_i50936_2_, p_i50936_3_);
    }

    @Override
    public void render(
            MatrixStack pMatrixStack,
            IRenderTypeBuffer pBuffer,
            int pPackedLight,
            T pLivingEntity,
            float pLimbSwing,
            float pLimbSwingAmount,
            float pPartialTicks,
            float pAgeInTicks,
            float pNetHeadYaw,
            float pHeadPitch) {
        this.renderArmorPiece(
                pMatrixStack,
                pBuffer,
                pLivingEntity,
                EquipmentSlotType.CHEST,
                pPackedLight,
                this.getArmorModel(EquipmentSlotType.CHEST));
        this.renderArmorPiece(
                pMatrixStack,
                pBuffer,
                pLivingEntity,
                EquipmentSlotType.LEGS,
                pPackedLight,
                this.getArmorModel(EquipmentSlotType.LEGS));
        this.renderArmorPiece(
                pMatrixStack,
                pBuffer,
                pLivingEntity,
                EquipmentSlotType.FEET,
                pPackedLight,
                this.getArmorModel(EquipmentSlotType.FEET));
        this.renderArmorPiece(
                pMatrixStack,
                pBuffer,
                pLivingEntity,
                EquipmentSlotType.HEAD,
                pPackedLight,
                this.getArmorModel(EquipmentSlotType.HEAD));
    }

    private void renderArmorPiece(
            MatrixStack p_241739_1_,
            IRenderTypeBuffer p_241739_2_,
            T p_241739_3_,
            EquipmentSlotType p_241739_4_,
            int p_241739_5_,
            A p_241739_6_) {
        ItemStack itemstack = p_241739_3_.getItemBySlot(p_241739_4_);
        if (itemstack.getItem() instanceof ArmorItem) {
            ArmorItem armoritem = (ArmorItem) itemstack.getItem();
            if (armoritem.getSlot() == p_241739_4_) {
                p_241739_6_ = getArmorModelHook(p_241739_3_, itemstack, p_241739_4_, p_241739_6_);
                this.getParentModel().copyPropertiesTo(p_241739_6_);
                this.setPartVisibility(p_241739_6_, p_241739_4_);
                boolean flag = this.usesInnerModel(p_241739_4_);
                boolean flag1 = itemstack.hasFoil();
                if (armoritem instanceof GeoArmorItem) {
                    p_241739_1_.scale(-1, -1, 1);
                }
                if (armoritem instanceof net.minecraft.item.IDyeableArmorItem) {
                    int i = ((net.minecraft.item.IDyeableArmorItem) armoritem).getColor(itemstack);
                    float f = (float) (i >> 16 & 255) / 255.0F;
                    float f1 = (float) (i >> 8 & 255) / 255.0F;
                    float f2 = (float) (i & 255) / 255.0F;
                    this.renderModel(
                            p_241739_1_,
                            p_241739_2_,
                            p_241739_5_,
                            flag1,
                            p_241739_6_,
                            f,
                            f1,
                            f2,
                            this.getArmorResource(p_241739_3_, itemstack, p_241739_4_, null));
                    this.renderModel(
                            p_241739_1_,
                            p_241739_2_,
                            p_241739_5_,
                            flag1,
                            p_241739_6_,
                            1.0F,
                            1.0F,
                            1.0F,
                            this.getArmorResource(p_241739_3_, itemstack, p_241739_4_, "overlay"));
                } else {
                    this.renderModel(
                            p_241739_1_,
                            p_241739_2_,
                            p_241739_5_,
                            flag1,
                            p_241739_6_,
                            1.0F,
                            1.0F,
                            1.0F,
                            this.getArmorResource(p_241739_3_, itemstack, p_241739_4_, null));
                }
            }
        }
    }

    protected void setPartVisibility(A pModel, EquipmentSlotType pSlot) {
        pModel.setAllVisible(false);
        switch (pSlot) {
            case HEAD:
                pModel.head.visible = true;
                pModel.hat.visible = true;
                break;
            case CHEST:
                pModel.body.visible = true;
                pModel.rightArm.visible = true;
                pModel.leftArm.visible = true;
                break;
            case LEGS:
                pModel.body.visible = true;
                pModel.rightLeg.visible = true;
                pModel.leftLeg.visible = true;
                break;
            case FEET:
                pModel.rightLeg.visible = true;
                pModel.leftLeg.visible = true;
        }
    }

    private void renderModel(
            MatrixStack p_241738_1_,
            IRenderTypeBuffer p_241738_2_,
            int p_241738_3_,
            boolean p_241738_5_,
            A p_241738_6_,
            float p_241738_8_,
            float p_241738_9_,
            float p_241738_10_,
            ResourceLocation armorResource) {
        IVertexBuilder ivertexbuilder =
                ItemRenderer.getArmorFoilBuffer(
                        p_241738_2_, RenderType.armorCutoutNoCull(armorResource), false, p_241738_5_);
        p_241738_6_.renderToBuffer(
                p_241738_1_,
                ivertexbuilder,
                p_241738_3_,
                OverlayTexture.NO_OVERLAY,
                p_241738_8_,
                p_241738_9_,
                p_241738_10_,
                1.0F);
    }

    private A getArmorModel(EquipmentSlotType p_241736_1_) {
        return (A) (this.usesInnerModel(p_241736_1_) ? this.innerModel : this.outerModel);
    }

    private boolean usesInnerModel(EquipmentSlotType pSlot) {
        return pSlot == EquipmentSlotType.LEGS;
    }
}
