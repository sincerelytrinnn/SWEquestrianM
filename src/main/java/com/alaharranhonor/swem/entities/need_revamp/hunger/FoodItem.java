package com.alaharranhonor.swem.entities.need_revamp.hunger;
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

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.BiConsumer;

/**
 * The ordinal (index of the element in this enum) is the priority. So lower down in this file = higher priority, when selecting what to eat.
 */
public enum FoodItem {

    // Grass Feeds.
    GRASS_BLOCK(Ingredient.of(Items.GRASS_BLOCK), 1, 1, 112, -1, "", FoodItem::doNothing),
    OAT_BUSHEL(Ingredient.of(SWEMItems.OAT_BUSHEL.get()), 1, 10, 4, 5, "", FoodItem::doNothing),
    TIMOTHY_BUSHEL(Ingredient.of(SWEMItems.TIMOTHY_BUSHEL.get()), 1, 14, 4, 5, "", FoodItem::doNothing),
    ALFALFA_BUSHEL(Ingredient.of(SWEMItems.ALFALFA_BUSHEL.get()), 1, 14, 4, 5, "", FoodItem::doNothing),
    OAT_BALE(Ingredient.of(SWEMBlocks.OAT_BALE.get()), 1, 50, 2, 3, "", FoodItem::doNothing),
    OAT_BALE_SLAB(Ingredient.of(SWEMBlocks.OAT_BALE_SLAB.get()), 1, 25, 4, 6, "", FoodItem::doNothing),
    TIMOTHY_BALE(Ingredient.of(SWEMBlocks.TIMOTHY_BALE.get()), 1, 56, 2, 3, "", FoodItem::doNothing),
    TIMOTHY_BLAE_SLAB(Ingredient.of(SWEMBlocks.TIMOTHY_BALE_SLAB.get()), 1, 28, 4, 6, "", FoodItem::doNothing),
    ALFALFA_BALE(Ingredient.of(SWEMBlocks.ALFALFA_BALE.get()), 1, 56, 2, 3, "", FoodItem::doNothing),
    ALFALFA_BALE_SLAB(Ingredient.of(SWEMBlocks.ALFALFA_BALE_SLAB.get()), 1, 28, 4, 6, "", FoodItem::doNothing),
    QUALITY_SLAB(Ingredient.of(SWEMBlocks.QUALITY_BALE_SLAB.get()), 1, 112, 2, 3, "", FoodItem::doNothing),
    QUALITY_BALE(Ingredient.of(SWEMBlocks.QUALITY_BALE.get()), 1, 224, 1, 2, "", FoodItem::doNothing),

    // Sweet feed category.
    SWEET_FEED(Ingredient.of(SWEMItems.SWEET_FEED_OPENED.get()), 2, 112, 1, 2, "", FoodItem::doNothing);


    private Ingredient item;
    private int categoryIndex;
    private int points;
    private int min;
    private int max;
    private String extraData;
    private BiConsumer<SWEMHorseEntityBase, FoodItem> extraEffectMethod;

    FoodItem(Ingredient item, int categoryIndex, int points, int min, int max, String data, BiConsumer<SWEMHorseEntityBase, FoodItem> extraEffect) {
        this.item = item;
        this.categoryIndex = categoryIndex;
        this.points = points;
        this.min = min;
        this.max = max;
        this.extraData = data;
        this.extraEffectMethod = extraEffect;
    }

    public Ingredient getItem() {
        return item;
    }

    public int getCategoryIndex() {
        return categoryIndex;
    }

    public int getPoints() {
        return points;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public BiConsumer<SWEMHorseEntityBase, FoodItem> getExtraEffectMethod() {
        return extraEffectMethod;
    }

    private static void doNothing(SWEMHorseEntityBase horse, FoodItem item) {

    }

    private static void addAffinityPoints(SWEMHorseEntityBase horse, FoodItem item) {
        int affinityPoints = Integer.parseInt(item.extraData);
    }


    public static int indexOfByItem(Item item) {
        for (int i = 0; i < values().length; i++) {
            FoodItem foodItem = values()[i];
            if (foodItem.getItem().test(new ItemStack(item))) {
                return i;
            }
        }
        return -1;
    }

    ;

    @Override
    public String toString() {
        return "FoodItem{" +
            "item=" + item +
            ", categoryIndex=" + categoryIndex +
            ", points=" + points +
            ", min=" + min +
            ", max=" + max +
            '}';
    }
}
