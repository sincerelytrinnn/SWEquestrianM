package com.alaharranhonor.swem.util.registry;


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
import com.alaharranhonor.swem.enchantments.GalaxyCoatEnchantment;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BookItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SWEMEnchantments {

	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, SWEM.MOD_ID);

	public static void init(IEventBus modBus) {
		ENCHANTMENTS.register(modBus);
	}

	public static final RegistryObject<Enchantment> GALAXY_COAT_ENCHANTMENT = ENCHANTMENTS.register("galaxy_coat", () -> new GalaxyCoatEnchantment(Enchantment.Rarity.COMMON, EnchantmentType.BREAKABLE, EquipmentSlotType.MAINHAND));
}
