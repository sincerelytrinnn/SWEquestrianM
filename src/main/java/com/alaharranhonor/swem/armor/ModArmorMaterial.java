package com.alaharranhonor.swem.armor;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.util.RegistryHandler;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import java.util.function.Supplier;

public enum ModArmorMaterial implements IArmorMaterial {

	LEATHER(SWEM.MOD_ID + ":leather", 8, new int[]{2, 5, 6, 1}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, () -> {
		return Ingredient.fromItems(Items.LEATHER);
	}),

	IRON(SWEM.MOD_ID + ":iron", 23, new int[] {2, 5, 6, 3}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, () -> {
	    return Ingredient.fromItems(Items.IRON_INGOT);
    }),

    GOLD(SWEM.MOD_ID + ":gold", 27, new int[] {2, 5, 6, 3}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0f, () -> {
        return Ingredient.fromItems(Items.GOLD_INGOT);
    }),

    DIAMOND(SWEM.MOD_ID + ":diamond", 38, new int[] {2, 5, 6, 4}, 0, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0f, () -> {
        return Ingredient.fromItems(Items.DIAMOND);
    }),

    AMETHYST(SWEM.MOD_ID + ":amethyst", 61, new int[] {5, 8, 14, 5}, 0, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0f, () -> {
        return Ingredient.fromItems(RegistryHandler.CANTAZARITE.get());
    });

	private static final int[] MAX_DAMAGE_ARRAY = new int[] {11, 16, 15, 13};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final Supplier<Ingredient> repairMaterial;

    ModArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability,
                      SoundEvent soundEvent, float toughness, Supplier<Ingredient> repairMaterial) {
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getDurability(EquipmentSlotType slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return this.damageReductionAmountArray[slotIn.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return this.repairMaterial.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

	@Override
	public float getKnockbackResistance() {
		return 0;
	}
}
