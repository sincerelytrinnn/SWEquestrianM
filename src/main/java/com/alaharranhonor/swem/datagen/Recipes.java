
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

package com.alaharranhonor.swem.datagen;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import com.alaharranhonor.swem.util.registry.SWEMTags;
import net.minecraft.data.*;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> p_200404_0_) {

        for (DyeColor color : DyeColor.values()) {
            pastureBlanket(color, p_200404_0_);
            halfBarrels(color, p_200404_0_);
            englishTack(color, p_200404_0_);
            westernTack(color, p_200404_0_);
        }

        ShapedRecipeBuilder.shaped(SWEMItems.CLOTH_HORSE_ARMOR.get())
            .define('x', ItemTags.CARPETS)
            .pattern("x x")
            .pattern("x x")
            .pattern("x x")
            .group("horse_armor")
            .unlockedBy("has_cloth_horse_armor", has(ItemTags.CARPETS))
            .save(p_200404_0_);

        ShapedRecipeBuilder.shaped(SWEMItems.DRIVING_HARNESS.get())
            .define('x', Items.BLACK_CARPET)
            .define('n', Items.IRON_NUGGET)
            .pattern(" xn")
            .pattern("nxx")
            .pattern(" nx")
            .unlockedBy("has_black_carpet", has(Items.BLACK_CARPET))
            .save(p_200404_0_);
    }

    private void pastureBlanket(DyeColor color, Consumer<IFinishedRecipe> p_200404_0_) {
        ShapedRecipeBuilder.shaped(SWEMItems.PASTURE_BLANKETS.get(color.getId()).get())
            .define('#', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_carpet")))
            .define('x', Items.DRIED_KELP)
            .pattern(" ##")
            .pattern("###")
            .pattern("#x#")
            .group("pasture_blanket")
            .unlockedBy("has_pasture_blanket", has(ItemTags.CARPETS))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "pasture_blanket/pasture_blanket_" + color.getName()));

        ShapedRecipeBuilder.shaped(SWEMItems.PASTURE_BLANKETS_ARMORED.get(color.getId()).get())
            .define('#', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_carpet")))
            .define('x', Items.DRIED_KELP)
            .define('y', Items.IRON_CHESTPLATE)
            .pattern("y##")
            .pattern("###")
            .pattern("#x#")
            .group("pasture_blanket_armored")
            .unlockedBy("has_pasture_blanket_armored", has(ItemTags.CARPETS))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "pasture_blanket/pasture_blanket_armored_" + color.getName()));

        ShapelessRecipeBuilder.shapeless(SWEMItems.PASTURE_BLANKETS_ARMORED.get(color.getId()).get())
            .requires(SWEMItems.PASTURE_BLANKETS.get(color.getId()).get())
            .requires(Items.IRON_CHESTPLATE)
            .group("pasture_blanket_armored")
            .unlockedBy("has_pasture_blanket_armored", has(ItemTags.CARPETS))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "pasture_blanket/pasture_blanket_" + color.getName()) + "_armored_from_blanket");
    }

    private void halfBarrels(DyeColor color, Consumer<IFinishedRecipe> p_200404_0_) {
        ShapelessRecipeBuilder.shapeless(SWEMBlocks.HALF_BARRELS.get(color.getId()).get())
            .requires(SWEMTags.HALF_BARRELS)
            .requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye")))
            .group("half_barrels")
            .unlockedBy("has_half_barrels", has(Items.DRIED_KELP))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "half_barrel/half_barrel_" + color.getName()));
    }

    private void englishTack(DyeColor color, Consumer<IFinishedRecipe> p_200404_0_) {
        englishBlanket(color, p_200404_0_);
        englishLegWraps(color, p_200404_0_);
    }

    private void englishBlanket(DyeColor color, Consumer<IFinishedRecipe> p_200404_0_) {
        // Regular recipe, from coloured blanket.
        ShapedRecipeBuilder.shaped(SWEMItems.ENGLISH_BLANKETS.get(color.getId()).get())
            .define('x', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_carpet")))
            .define('y', SWEMItems.REFINED_LEATHER.get())
            .pattern("xyx")
            .pattern("xx ")
            .group("english_blanket")
            .unlockedBy("has_english_blanket", has(ItemTags.CARPETS))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "english_blanket/english_blanket_" + color.getName()));

        // Interchangeable blanket.
        ShapelessRecipeBuilder.shapeless(SWEMItems.ENGLISH_BLANKETS.get(color.getId()).get())
            .requires(SWEMTags.ENGLISH_BLANKETS)
            .requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye")))
            .group("english_blanket")
            .unlockedBy("has_english_blanket", has(ItemTags.CARPETS))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "english_blanket/english_blanket_" + color.getName() + "_from_blanket"));
    }

    private void englishLegWraps(DyeColor color, Consumer<IFinishedRecipe> p_200404_0_) {
        // Regular recipe, from coloured blanket.
        ShapedRecipeBuilder.shaped(SWEMItems.ENGLISH_LEG_WRAPS.get(color.getId()).get())
            .define('x', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_carpet")))
            .define('y', Items.DRIED_KELP)
            .pattern("x x")
            .pattern("x x")
            .pattern("y y")
            .group("english_leg_wraps")
            .unlockedBy("has_english_leg_wraps", has(ItemTags.CARPETS))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "english_leg_wraps/english_leg_wraps_" + color.getName()));

        // Interchangeable leg wraps.
        ShapelessRecipeBuilder.shapeless(SWEMItems.ENGLISH_LEG_WRAPS.get(color.getId()).get())
            .requires(SWEMTags.ENGLISH_LEG_WRAPS)
            .requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye")))
            .group("english_leg_wraps")
            .unlockedBy("has_english_leg_wraps", has(ItemTags.CARPETS))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "english_leg_wraps/english_leg_wraps_" + color.getName()) + "_from_leg_wraps");
    }

    private void westernTack(DyeColor color, Consumer<IFinishedRecipe> p_200404_0_) {

    }


}
