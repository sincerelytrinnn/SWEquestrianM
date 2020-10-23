package com.alaharranhonor.swem.armor;

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
