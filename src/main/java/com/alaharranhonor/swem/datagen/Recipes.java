
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
            saddleBag(color, p_200404_0_);
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
        westernBlanket(color, p_200404_0_);
        westernBreastCollar(color, p_200404_0_);
        westernBridle(color, p_200404_0_);
        westernGirthStrap(color, p_200404_0_);
        westernLegWraps(color, p_200404_0_);
        westernSaddle(color, p_200404_0_);
    }

    private void westernBlanket(DyeColor color, Consumer<IFinishedRecipe> p_200404_0_) {
        // Regular recipe, from coloured blanket.
        ShapedRecipeBuilder.shaped(SWEMItems.WESTERN_BLANKETS.get(color.getId()).get())
            .define('x', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_carpet")))
            .define('y', SWEMItems.REFINED_LEATHER.get())
            .pattern("xyx")
            .pattern("xxx")
            .group("western_blanket")
            .unlockedBy("has_western_blanket", has(SWEMItems.REFINED_LEATHER.get()))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "western_blanket/western_blanket_" + color.getName()));

        // Interchangeable blanket.
        ShapelessRecipeBuilder.shapeless(SWEMItems.WESTERN_BLANKETS.get(color.getId()).get())
            .requires(SWEMTags.WESTERN_BLANKETS)
            .requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye")))
            .group("western_blanket")
            .unlockedBy("has_western_blanket", has(SWEMItems.REFINED_LEATHER.get()))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "western_blanket/western_blanket_" + color.getName() + "_from_blanket"));
    }

    private void westernBreastCollar(DyeColor color, Consumer<IFinishedRecipe> p_200404_0_) {
        // Regular recipe, from coloured blanket.
        ShapedRecipeBuilder.shaped(SWEMItems.WESTERN_BREAST_COLLARS.get(color.getId()).get())
            .define('x', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_wool")))
            .define('y', SWEMItems.REFINED_LEATHER.get())
            .pattern("y y")
            .pattern(" x ")
            .pattern(" y ")
            .group("western_breast_collar")
            .unlockedBy("has_western_breast_collar", has(SWEMItems.REFINED_LEATHER.get()))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "western_breast_collar/western_breast_collar_" + color.getName()));

        // Interchangeable blanket.
        ShapelessRecipeBuilder.shapeless(SWEMItems.WESTERN_BREAST_COLLARS.get(color.getId()).get())
            .requires(SWEMTags.WESTERN_BREAST_COLLARS)
            .requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye")))
            .group("western_breast_collar")
            .unlockedBy("has_western_breast_collar", has(SWEMItems.REFINED_LEATHER.get()))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "western_breast_collar/western_breast_collar_" + color.getName() + "_from_breast_collar"));
    }

    private void westernBridle(DyeColor color, Consumer<IFinishedRecipe> p_200404_0_) {
        // Regular recipe, from coloured blanket.
        ShapedRecipeBuilder.shaped(SWEMItems.WESTERN_BRIDLES.get(color.getId()).get())
            .define('w', SWEMItems.REFINED_LEATHER.get())
            .define('x', Items.IRON_NUGGET)
            .define('y', Items.TRIPWIRE_HOOK)
            .define('z', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye")))
            .pattern("  w")
            .pattern(" wz")
            .pattern("xyw")
            .group("western_bridle")
            .unlockedBy("has_western_bridle", has(SWEMItems.REFINED_LEATHER.get()))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "western_bridle/western_bridle_" + color.getName()));

        // Interchangeable blanket.
        ShapelessRecipeBuilder.shapeless(SWEMItems.WESTERN_BRIDLES.get(color.getId()).get())
            .requires(SWEMTags.WESTERN_BRIDLES)
            .requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye")))
            .group("western_bridle")
            .unlockedBy("has_western_bridle", has(SWEMItems.REFINED_LEATHER.get()))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "western_bridle/western_bridle_" + color.getName() + "_from_bridle"));
    }

    private void westernGirthStrap(DyeColor color, Consumer<IFinishedRecipe> p_200404_0_) {
        // Regular recipe, from coloured blanket.
        ShapedRecipeBuilder.shaped(SWEMItems.WESTERN_GIRTH_STRAPS.get(color.getId()).get())
            .define('x', Items.IRON_NUGGET)
            .define('y', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_carpet")))
            .define('z', SWEMItems.REFINED_LEATHER.get())
            .pattern("xy ")
            .pattern(" z ")
            .pattern(" yx")
            .group("western_girth_strap")
            .unlockedBy("has_western_girth_strap", has(SWEMItems.REFINED_LEATHER.get()))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "western_girth_strap/western_girth_strap_" + color.getName()));

        // Interchangeable blanket.
        ShapelessRecipeBuilder.shapeless(SWEMItems.WESTERN_GIRTH_STRAPS.get(color.getId()).get())
            .requires(SWEMTags.WESTERN_GIRTH_STRAPS)
            .requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye")))
            .group("western_girth_strap")
            .unlockedBy("has_western_girth_strap", has(SWEMItems.REFINED_LEATHER.get()))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "western_girth_strap/western_girth_strap_" + color.getName() + "_from_girth_strap"));
    }

    private void westernLegWraps(DyeColor color, Consumer<IFinishedRecipe> p_200404_0_) {
        // Regular recipe, from coloured blanket.
        ShapedRecipeBuilder.shaped(SWEMItems.WESTERN_LEG_WRAPS.get(color.getId()).get())
            .define('x', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_carpet")))
            .define('y', Items.DRIED_KELP)
            .pattern("x x")
            .pattern("x x")
            .pattern(" y ")
            .group("western_leg_wraps")
            .unlockedBy("has_western_leg_wraps", has(ItemTags.CARPETS))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "western_leg_wraps/western_leg_wraps_" + color.getName()));

        // Interchangeable leg wraps.
        ShapelessRecipeBuilder.shapeless(SWEMItems.WESTERN_LEG_WRAPS.get(color.getId()).get())
            .requires(SWEMTags.WESTERN_LEG_WRAPS)
            .requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye")))
            .group("western_leg_wraps")
            .unlockedBy("has_western_leg_wraps", has(ItemTags.CARPETS))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "western_leg_wraps/western_leg_wraps_" + color.getName() + "_from_leg_wraps"));
    }

    private void westernSaddle(DyeColor color, Consumer<IFinishedRecipe> p_200404_0_) {
        // Regular recipe, from coloured blanket.
        ShapedRecipeBuilder.shaped(SWEMItems.WESTERN_SADDLES.get(color.getId()).get())
            .define('x', SWEMItems.REFINED_LEATHER.get())
            .define('y', Items.IRON_NUGGET)
            .define('z', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye")))
            .pattern("xzx")
            .pattern("xxx")
            .pattern("yxx")
            .group("western_saddle")
            .unlockedBy("has_western_saddle", has(SWEMItems.REFINED_LEATHER.get()))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "western_saddle/western_saddle_" + color.getName()));

        // Interchangeable blanket.
        ShapelessRecipeBuilder.shapeless(SWEMItems.WESTERN_SADDLES.get(color.getId()).get())
            .requires(SWEMTags.WESTERN_SADDLES)
            .requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye")))
            .group("western_saddle")
            .unlockedBy("has_western_saddle", has(SWEMItems.REFINED_LEATHER.get()))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "western_saddle/western_saddle_" + color.getName() + "_from_saddle"));
    }

    private void saddleBag(DyeColor color, Consumer<IFinishedRecipe> p_200404_0_) {

        ShapedRecipeBuilder.shaped(SWEMItems.SADDLE_BAGS.get(color.getId()).get())
            .define('x', SWEMItems.REFINED_LEATHER.get())
            .define('z', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_wool")))
            .define('y', Items.CHEST)
            .pattern("zzz")
            .pattern("xyx")
            .pattern("xxx")
            .group("saddle_bag")
            .unlockedBy("has_saddle_bag", has(SWEMItems.REFINED_LEATHER.get()))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "saddle_bag/saddle_bag_" + color.getName()));

        // Interchangeable saddle bag.
        ShapelessRecipeBuilder.shapeless(SWEMItems.SADDLE_BAGS.get(color.getId()).get())
            .requires(SWEMTags.SADDLE_BAGS)
            .requires(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", color.getName() + "_dye")))
            .group("saddle_bag")
            .unlockedBy("has_saddle_bag", has(SWEMItems.REFINED_LEATHER.get()))
            .save(p_200404_0_, new ResourceLocation(SWEM.MOD_ID, "saddle_bag/saddle_bag_" + color.getName() + "_from_saddle_bag"));
    }


}
