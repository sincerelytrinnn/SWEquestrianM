package com.alaharranhonor.swem.armor;

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
import com.alaharranhonor.swem.items.SWEMArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AmethystArmorModel extends AnimatedGeoModel<SWEMArmorItem> {
    @Override
    public ResourceLocation getModelLocation(SWEMArmorItem swemArmorItem) {
        if (DefaultPlayerSkin.getSkinModelName(Minecraft.getInstance().player.getUUID())
                .equals("default")) {
            return new ResourceLocation(SWEM.MOD_ID, "geo/armor/amethyst_armor_set.geo.json");
        } else {
            return new ResourceLocation(SWEM.MOD_ID, "geo/armor/amethyst_armor_set_slim.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(SWEMArmorItem swemArmorItem) {
        // if (Minecraft.getInstance().player.getSkinType().equals("default")) {
        return new ResourceLocation(
                SWEM.MOD_ID, "textures/models/armor/" + swemArmorItem.getTexturePath() + ".png");
    /*} else {
    	return new ResourceLocation(SWEM.MOD_ID, "textures/models/armor/" + swemArmorItem.getTexturePath() +"_slim.png");
    }*/
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SWEMArmorItem swemArmorItem) {
        return new ResourceLocation(SWEM.MOD_ID, "animations/swem_horse.json");
    }
}
