package com.alaharranhonor.swem.datagen;

import com.alaharranhonor.swem.SWEM;
import com.google.gson.Gson;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Mod.EventBusSubscriber(modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) throws IOException {
		DataGenerator data = event.getGenerator();
		if (event.includeServer()) {
			//data.addProvider(new Recipes(data));
			//data.addProvider(new LootTables(data));
		}
		if (event.includeClient()) {
			data.addProvider(new BlockStates(data, event.getExistingFileHelper()));
			//data.addProvider(new Items(data, event.getExistingFileHelper()));


			// Get the translation document.
			// parse it into GSON
			// Read the output into a Data Class.
			// get the Translation array, and then pass it into registerLanguageProviders
			String api = System.getenv("SWEM_TRANSLATION_API_KEY");
			URL url = new URL("https://sheets.googleapis.com/v4/spreadsheets/1nIuoznNlkud57_eE_piMSLIQAsQ9XRIiyzTBtwDC2kg/values/A60:H647?key=");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			assert (con.getResponseCode() < 300);
			Reader targetReader = new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8);
			LanguageSheetData langData = new Gson().fromJson(targetReader, LanguageSheetData.class);
			registerLanguageProviders(data, langData.getValues());
		}
	}

	private static void registerLanguageProviders(DataGenerator data, String[][] values) {
		data.addProvider(new Languages(data, SWEM.MOD_ID, "en_us", values, 2));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "da_dk", values, 3));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "nl_nl", values, 4));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "sv_se", values, 5));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "fr_fr", values, 6));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "pl_pl", values, 7));
	}

}
