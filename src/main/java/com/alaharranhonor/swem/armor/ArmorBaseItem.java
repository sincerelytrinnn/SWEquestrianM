package com.alaharranhonor.swem.armor;


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

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import net.minecraft.item.Item.Properties;

public class ArmorBaseItem extends ArmorItem {

	private Supplier<Supplier<ArmorBaseModel>> armorModel;

	public ArmorBaseItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn, Supplier<Supplier<ArmorBaseModel>> armorModel) {
		super(materialIn, slot, builderIn);
		this.armorModel = armorModel;
	}

	@OnlyIn(Dist.CLIENT)
	@Nullable
	@Override
	public final BipedModel getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlotType armorSlot, BipedModel defaultArmor) {
		return armorModel.get().get().applyEntityStats(defaultArmor).applySlot(armorSlot);
	}

	@Nullable
	@Override
	public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return armorModel.get().get().getTexture();
	}
}
