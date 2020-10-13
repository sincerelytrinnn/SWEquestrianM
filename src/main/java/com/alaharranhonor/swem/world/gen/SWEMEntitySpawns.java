package com.alaharranhonor.swem.world.gen;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SWEMEntitySpawns {

	@SubscribeEvent
	public static void spawnEntities(FMLLoadCompleteEvent event)
	{
		for(Biome biome : ForgeRegistries.BIOMES)
		{
			if (biome.getCategory() == Biome.Category.NETHER)
			{
				// Nether entities
			}
			else if (biome.getCategory() == Biome.Category.THEEND)
			{
				// End entities?
			}
			else
			{
				if (biome.getCategory() != Biome.Category.OCEAN)
				{
					// Make sure the biome is not an occean.
					// Hmmm not sure.
				}
				// Overworld entities
			}
		}
	}
}
