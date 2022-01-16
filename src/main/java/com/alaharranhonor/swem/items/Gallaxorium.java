package com.alaharranhonor.swem.items;


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
import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.EnchantingTableTileEntity;
import net.minecraft.world.World;

public class Gallaxorium extends ItemBase {

	public Gallaxorium() {
		super(new Item.Properties().durability(1).tab(SWEM.TAB));
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment instanceof GalaxyCoatEnchantment;
	}

	@Override
	public boolean isEnchantable(ItemStack p_77616_1_) {
		return p_77616_1_.getCount() == 1;
	}

	@Override
	public int getEnchantmentValue() {
		return 1;
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}

	@Override
	public void inventoryTick(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int itemSlot, boolean isSelected) {
		if (!(p_77663_3_ instanceof PlayerEntity)) return;
		if (!p_77663_1_.isEnchanted()) return;

		PlayerEntity player = (PlayerEntity) p_77663_3_;
		player.inventory.removeItem(p_77663_1_);
		//player.inventory.add(new ItemStack(SWEMItems.GALLAXIUM.get()));
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return 1;
	}
}
