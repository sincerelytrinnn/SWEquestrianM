package com.alaharranhonor.swem.datagen;

import com.alaharranhonor.swem.SWEM;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator data = event.getGenerator();
		if (event.includeServer()) {
			//data.addProvider(new Recipes(data));
			//data.addProvider(new LootTables(data));
		}
		if (event.includeClient()) {
			data.addProvider(new BlockStates(data, event.getExistingFileHelper()));
			//data.addProvider(new Items(data, event.getExistingFileHelper()));
		}
	}

}
