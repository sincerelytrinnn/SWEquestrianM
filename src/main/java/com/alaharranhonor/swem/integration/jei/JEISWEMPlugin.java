package com.alaharranhonor.swem.integration.jei;
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

import com.alaharranhonor.swem.util.registry.SWEMItems;
import com.google.common.collect.Lists;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEISWEMPlugin implements IModPlugin {
	/**
	 * The unique ID for this mod plugin.
	 * The namespace should be your mod's modId.
	 */
	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(ModIds.JEI_ID, "swem");

	}

	/**
	 * If your item has subtypes that depend on NBT or capabilities, use this to help JEI identify those subtypes correctly.
	 *
	 * @param registration
	 */
	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		IModPlugin.super.registerItemSubtypes(registration);
	}

	/**
	 * Register special ingredients, beyond the basic ItemStack and FluidStack.
	 *
	 * @param registration
	 */
	@Override
	public void registerIngredients(IModIngredientRegistration registration) {
		IModPlugin.super.registerIngredients(registration);
	}

	/**
	 * Register the categories handled by this plugin.
	 * These are registered before recipes so they can be checked for validity.
	 *
	 * @param registration
	 */
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IModPlugin.super.registerCategories(registration);
	}

	/**
	 * Register modded extensions to the vanilla crafting recipe category.
	 * Custom crafting recipes for your mod should use this to tell JEI how they work.
	 *
	 * @param registration
	 */
	@Override
	public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
		IModPlugin.super.registerVanillaCategoryExtensions(registration);
	}

	/**
	 * Register modded recipes.
	 *
	 * @param registration
	 */
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		IModPlugin.super.registerRecipes(registration);
	}

	/**
	 * Register recipe transfer handlers (move ingredients from the inventory into crafting GUIs).
	 *
	 * @param registration
	 */
	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		IModPlugin.super.registerRecipeTransferHandlers(registration);
	}

	/**
	 * Register recipe catalysts.
	 * Recipe Catalysts are ingredients that are needed in order to craft other things.
	 * Vanilla examples of Recipe Catalysts are the Crafting Table and Furnace.
	 *
	 * @param registration
	 */
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		IModPlugin.super.registerRecipeCatalysts(registration);
	}

	/**
	 * Register various GUI-related things for your mod.
	 * This includes adding clickable areas in your guis to open JEI,
	 * and adding areas on the screen that JEI should avoid drawing.
	 *
	 * @param registration
	 */
	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		IModPlugin.super.registerGuiHandlers(registration);
	}

	/**
	 * Register advanced features for your mod plugin.
	 *
	 * @param registration
	 */
	@Override
	public void registerAdvanced(IAdvancedRegistration registration) {
		IModPlugin.super.registerAdvanced(registration);
	}

	/**
	 * Called when jei's runtime features are available, after all mods have registered.
	 *
	 * @param jeiRuntime
	 */
	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		IModPlugin.super.onRuntimeAvailable(jeiRuntime);
	}
}
