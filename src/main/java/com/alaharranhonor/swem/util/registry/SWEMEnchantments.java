package com.alaharranhonor.swem.util.registry;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.enchantments.GalaxyCoatEnchantment;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BookItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SWEMEnchantments {

	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, SWEM.MOD_ID);

	public static void init(IEventBus modBus) {
		ENCHANTMENTS.register(modBus);
	}

	public static final RegistryObject<Enchantment> GALAXY_COAT_ENCHANTMENT = ENCHANTMENTS.register("galaxy_coat", () -> new GalaxyCoatEnchantment(Enchantment.Rarity.COMMON, EnchantmentType.BREAKABLE, EquipmentSlotType.MAINHAND));
}
