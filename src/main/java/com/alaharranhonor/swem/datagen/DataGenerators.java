package com.alaharranhonor.swem.datagen;

import com.alaharranhonor.swem.SWEM;
import com.google.gson.Gson;
import io.netty.handler.codec.http.HttpRequestDecoder;
import jdk.nashorn.internal.parser.JSONParser;
import net.minecraft.client.Minecraft;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

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
			//data.addProvider(new BlockStates(data, event.getExistingFileHelper()));
			//data.addProvider(new Items(data, event.getExistingFileHelper()));


			// Get the translation document.
			// parse it into GSON
			// Read the output into a Data Class.
			// get the Translation array, and then pass it into registerLanguageProviders
			String api = System.getenv("SWEM_TRANSLATION_API_KEY");
			HttpGet get = new HttpGet("https://sheets.googleapis.com/v4/spreadsheets/1nIuoznNlkud57_eE_piMSLIQAsQ9XRIiyzTBtwDC2kg/values/A57:D573?key=" + api);

			CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpResponse response = client.execute(get);

			assert (response.getStatusLine().getStatusCode() < 300);
			Reader targetReader = new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8);
			LanguageSheetData langData = new Gson().fromJson(targetReader, LanguageSheetData.class);
			System.out.println(Arrays.deepToString(langData.getValues()));
			registerLanguageProviders(data, langData.getValues());
		}
	}

	private static void registerLanguageProviders(DataGenerator data, String[][] values) {
		data.addProvider(new Languages(data, SWEM.MOD_ID, "en_us", values, 2));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "da_dk", values, 3));
	}

}
