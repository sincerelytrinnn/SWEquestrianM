package com.alaharranhonor.swem.init;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.items.PestleMortarItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class SWLItems {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SWEM.MOD_ID);
	
	public static final RegistryObject<Item> STAR_WORM = ITEMS.register("star_worm", () -> new Item(new Item.Properties().group(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> STAR_WORM_GOOP = ITEMS.register("star_worm_goop", () -> new Item(new Item.Properties().group(SWEM.SWLMTAB)));
	public static final RegistryObject<Item> PESTLE_MORTAR = ITEMS.register("pestle_mortar", () -> new PestleMortarItem(new Item.Properties().maxStackSize(1).maxDamage(128).group(SWEM.SWLMTAB)));
}
