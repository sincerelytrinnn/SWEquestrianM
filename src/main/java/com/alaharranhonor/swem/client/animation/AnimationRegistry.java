
/*
 * All Rights Reserved
 *
 * Copyright (c) 2022, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.alaharranhonor.swem.client.animation;

import com.alaharranhonor.swem.SWEM;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.data.gson.AnimationSerializing;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alaharranhonor.swem.SWEM.LOGGER;

public class AnimationRegistry {
    public static Map<String, KeyframeAnimation> animations = new HashMap<>();

    public static void load(IResourceManager resourceManager) {
        String dataFolder = "animations";
        for (ResourceLocation entry : resourceManager.listResources(dataFolder, fileName -> fileName.equals("player.animation.json"))) {
            String identifier = entry.getNamespace();
            String resource = entry.getPath();
            System.out.println("Loading animation: " + identifier + ":" + resource);
            try {
                List<KeyframeAnimation> readAnimations = AnimationSerializing.deserializeAnimation(SWEM.class.getResourceAsStream("../../../assets/" + identifier + "/" + resource));
                for (KeyframeAnimation animation : readAnimations) {
                    animations.put(animation.extraData.get("name").toString(), animation);
                }
            } catch (Exception e) {
                LOGGER.error("Failed to load animation " + identifier.toString());
                e.printStackTrace();
            }
        }
    }
}
