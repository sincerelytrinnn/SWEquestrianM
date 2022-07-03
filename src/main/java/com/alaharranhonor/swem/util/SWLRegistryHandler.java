package com.alaharranhonor.swem.util;

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
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.MavenVersionStringHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SWLRegistryHandler {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SWEM.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SWEM.MOD_ID);

    /**
     * Init.
     */
    public static void init(IEventBus eventBus) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> SWLRegistryHandler::checkAccess);
        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
    }

    /**
     * Check access.
     */
    public static void checkAccess() {

        String playerUUID = Minecraft.getInstance().getUser().getUuid().replaceAll("-", "");

        try {
            URL url =
                    new URL(
                            "http://auth.swequestrian.com:9542/check?uuid="
                                    + playerUUID
                                    + "&version="
                                    + MavenVersionStringHelper.artifactVersionToString(
                                    ModList.get().getModFileById("swem").getMods().get(0).getVersion()));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();

            if (content.toString().equalsIgnoreCase("okay")) {
                return;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\no/\n");
        sb.append(
                "Hello random person! Your minecraft crashed because you are not on our approved beta-tester list! :)\n");
        sb.append(
                "If this is a case of redistribution, we very much appreciate your enthusiasm about the mod, however your impatience has banned you from our official servers for a minimum of six months. :(\n");
        sb.append("We hope this has been a wonderful learning experience in the world of piracy.\n");
        sb.append("Have a nice day! :D");

        SWEM.LOGGER.error(sb);

        System.exit(-1);
    }
}
