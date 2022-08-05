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
import com.alaharranhonor.swem.armor.AmethystModel;
import com.alaharranhonor.swem.armor.ArmorBaseModel;
import com.alaharranhonor.swem.armor.GenericModel;
import net.minecraft.util.ResourceLocation;

public class ArmorItemRegistration {
    static ArmorBaseModel LeatherArmor =
            new GenericModel(
                    128, 128, new ResourceLocation(SWEM.MOD_ID, "textures/models/armor/leather_layer.png"));
    static ArmorBaseModel GlowArmor =
            new GenericModel(
                    128, 128, new ResourceLocation(SWEM.MOD_ID, "textures/models/armor/glow_layer.png"));
    static ArmorBaseModel IronArmor =
            new GenericModel(
                    128, 128, new ResourceLocation(SWEM.MOD_ID, "textures/models/armor/iron_layer.png"));
    static ArmorBaseModel GoldArmor =
            new GenericModel(
                    128, 128, new ResourceLocation(SWEM.MOD_ID, "textures/models/armor/gold_layer.png"));
    static ArmorBaseModel DiamondArmor =
            new GenericModel(
                    128, 128, new ResourceLocation(SWEM.MOD_ID, "textures/models/armor/diamond_layer.png"));

    static ArmorBaseModel AmethystArmor =
            new AmethystModel(
                    128, 128, new ResourceLocation(SWEM.MOD_ID, "textures/models/armor/amethyst_layer.png"));

    /**
     * Gets leather armor.
     *
     * @return the leather armor
     */
    public static ArmorBaseModel getLeatherArmor() {
        return LeatherArmor;
    }

    /**
     * Gets glow armor.
     *
     * @return the glow armor
     */
    public static ArmorBaseModel getGlowArmor() {
        return GlowArmor;
    }

    /**
     * Gets iron armor.
     *
     * @return the iron armor
     */
    public static ArmorBaseModel getIronArmor() {
        return IronArmor;
    }

    /**
     * Gets gold armor.
     *
     * @return the gold armor
     */
    public static ArmorBaseModel getGoldArmor() {
        return GoldArmor;
    }

    /**
     * Gets diamond armor.
     *
     * @return the diamond armor
     */
    public static ArmorBaseModel getDiamondArmor() {
        return DiamondArmor;
    }

    /**
     * Gets amethyst armor.
     *
     * @return the amethyst armor
     */
    public static ArmorBaseModel getAmethystArmor() {
        return AmethystArmor;
    }
}
