package com.alaharranhonor.swem.datagen;


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
			URL url = new URL("https://sheets.googleapis.com/v4/spreadsheets/1nIuoznNlkud57_eE_piMSLIQAsQ9XRIiyzTBtwDC2kg/values/A60:W822?key=");
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
		data.addProvider(new Languages(data, SWEM.MOD_ID, "de_de", values, 8));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "de_de", values, 8));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "ro_ro", values, 9));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "pt_br", values, 10));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "pt_pt", values, 11));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "it_it", values, 12));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "fi_fi", values, 13));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "ru_ru", values, 14));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "ja_jp", values, 16));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "sk_sk", values, 17));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "cs_cz", values, 18));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "hu_hu", values, 19));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "nn_no", values, 20));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "ga_ie", values, 21));
		data.addProvider(new Languages(data, SWEM.MOD_ID, "et_ee", values, 22));
	}

}
