package com.alaharranhonor.swem.container;


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

import com.alaharranhonor.swem.items.SWEMArmorItem;
import com.alaharranhonor.swem.util.registry.SWEMContainers;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractRepairContainer;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class CantazariteAnvilContainer extends AbstractRepairContainer {
	public int materialCost;
	private String repairedItemName;
	private final IntReferenceHolder maximumCost = IntReferenceHolder.standalone();

	public CantazariteAnvilContainer(int id, PlayerInventory playerInventory, PacketBuffer data) {
		this(id, playerInventory, IWorldPosCallable.NULL);
	}

	public CantazariteAnvilContainer(int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable) {
		super(SWEMContainers.CANTAZARITE_ANVIL_CONTAINER.get(), id, playerInventory, worldPosCallable);
		this.addDataSlot(this.maximumCost);
	}

	protected boolean isValidBlock(BlockState p_230302_1_) {
		return p_230302_1_.is(BlockTags.ANVIL);
	}

	protected boolean mayPickup(PlayerEntity p_230303_1_, boolean p_230303_2_) {
		return (p_230303_1_.abilities.instabuild || p_230303_1_.experienceLevel >= this.maximumCost.get());
	}

	protected ItemStack onTake(PlayerEntity p_230301_1_, ItemStack p_230301_2_) {
		if (!p_230301_1_.abilities.instabuild && !(this.inputSlots.getItem(0).getItem() instanceof SWEMArmorItem)) {
			p_230301_1_.giveExperienceLevels(-this.maximumCost.get());
		}

		net.minecraftforge.common.ForgeHooks.onAnvilRepair(p_230301_1_, p_230301_2_, this.inputSlots.getItem(0), this.inputSlots.getItem(1));

		this.inputSlots.setItem(0, ItemStack.EMPTY);
		if (this.materialCost > 0) {
			ItemStack itemstack = this.inputSlots.getItem(1);
			if (!itemstack.isEmpty() && itemstack.getCount() > this.materialCost) {
				itemstack.shrink(this.materialCost);
				this.inputSlots.setItem(1, itemstack);
			} else {
				this.inputSlots.setItem(1, ItemStack.EMPTY);
			}
		} else {
			this.inputSlots.setItem(1, ItemStack.EMPTY);
		}

		this.maximumCost.set(0);
		this.access.execute((p_234633_1_, p_234633_2_) -> p_234633_1_.levelEvent(1030, p_234633_2_, 0));


		return p_230301_2_;
	}

	/**
	 * called when the Anvil Input Slot changes, calculates the new result and puts it in the output slot
	 */
	public void createResult() {
		ItemStack itemstack = this.inputSlots.getItem(0);
		this.maximumCost.set(1);
		int i = 0;
		int j = 0;
		int k = 0;
		if (itemstack.isEmpty()) {
			this.resultSlots.setItem(0, ItemStack.EMPTY);
			this.maximumCost.set(0);
		} else {
			ItemStack itemstack1 = itemstack.copy();
			ItemStack itemstack2 = this.inputSlots.getItem(1);
			Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemstack1);
			j = j + itemstack.getBaseRepairCost() + (itemstack2.isEmpty() ? 0 : itemstack2.getBaseRepairCost());
			this.materialCost = 0;
			boolean flag = false;

			if (!itemstack2.isEmpty()) {
				flag = itemstack2.getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantments(itemstack2).isEmpty();
				if (itemstack1.isDamageableItem() && itemstack1.getItem().isValidRepairItem(itemstack, itemstack2)) {
					int l2 = Math.min(itemstack1.getDamageValue(), itemstack1.getMaxDamage() / 4);
					if (l2 <= 0) {
						this.resultSlots.setItem(0, ItemStack.EMPTY);
						this.maximumCost.set(0);
						return;
					}

					int i3;
					for(i3 = 0; l2 > 0 && i3 < itemstack2.getCount(); ++i3) {
						int j3 = itemstack1.getDamageValue() - l2;
						itemstack1.setDamageValue(j3);
						++i;
						l2 = Math.min(itemstack1.getDamageValue(), itemstack1.getMaxDamage() / 4);
					}

					this.materialCost = i3;
				} else {
					if (!flag && (itemstack1.getItem() != itemstack2.getItem() || !itemstack1.isDamageableItem())) {
						this.resultSlots.setItem(0, ItemStack.EMPTY);
						this.maximumCost.set(0);
						return;
					}

					if (itemstack1.isDamageableItem() && !flag) {
						int l = itemstack.getMaxDamage() - itemstack.getDamageValue();
						int i1 = itemstack2.getMaxDamage() - itemstack2.getDamageValue();
						int j1 = i1 + itemstack1.getMaxDamage() * 12 / 100;
						int k1 = l + j1;
						int l1 = itemstack1.getMaxDamage() - k1;
						if (l1 < 0) {
							l1 = 0;
						}

						if (l1 < itemstack1.getDamageValue()) {
							itemstack1.setDamageValue(l1);
							i += 2;
						}
					}

					Map<Enchantment, Integer> map1 = EnchantmentHelper.getEnchantments(itemstack2);
					boolean flag2 = false;
					boolean flag3 = false;

					for(Enchantment enchantment1 : map1.keySet()) {
						if (enchantment1 != null) {
							int i2 = map.getOrDefault(enchantment1, 0);
							int j2 = map1.get(enchantment1);
							j2 = i2 == j2 ? j2 + 1 : Math.max(j2, i2);
							boolean flag1 = enchantment1.canEnchant(itemstack);
							if (this.player.abilities.instabuild || itemstack.getItem() == Items.ENCHANTED_BOOK) {
								flag1 = true;
							}

							for(Enchantment enchantment : map.keySet()) {
								if (enchantment != enchantment1 && !enchantment1.isCompatibleWith(enchantment)) {
									flag1 = false;
									++i;
								}
							}

							if (!flag1) {
								flag3 = true;
							} else {
								flag2 = true;
								if (j2 > enchantment1.getMaxLevel()) {
									j2 = enchantment1.getMaxLevel();
								}

								map.put(enchantment1, j2);
								int k3 = 0;
								switch(enchantment1.getRarity()) {
									case COMMON:
										k3 = 1;
										break;
									case UNCOMMON:
										k3 = 2;
										break;
									case RARE:
										k3 = 4;
										break;
									case VERY_RARE:
										k3 = 8;
								}

								if (flag) {
									k3 = Math.max(1, k3 / 2);
								}

								i += k3 * j2;
								if (itemstack.getCount() > 1) {
									i = 40;
								}
							}
						}
					}

					if (flag3 && !flag2) {
						this.resultSlots.setItem(0, ItemStack.EMPTY);
						this.maximumCost.set(0);
						return;
					}
				}
			}

			if (StringUtils.isBlank(this.repairedItemName)) {
				if (itemstack.hasCustomHoverName()) {
					k = 1;
					i += k;
					itemstack1.resetHoverName();
				}
			} else if (!this.repairedItemName.equals(itemstack.getDisplayName().getString())) {
				k = 1;
				i += k;
				itemstack1.setHoverName(new StringTextComponent(this.repairedItemName));
			}
			if (flag && !itemstack1.isBookEnchantable(itemstack2)) itemstack1 = ItemStack.EMPTY;

			this.maximumCost.set(j + i);
			if (i <= 0) {
				itemstack1 = ItemStack.EMPTY;
			}

			if (k == i && k > 0 && this.maximumCost.get() >= 40) {
				this.maximumCost.set(39);
			}

			if (this.maximumCost.get() >= 40 && !this.player.abilities.instabuild) {
				itemstack1 = ItemStack.EMPTY;
			}

			if (!itemstack1.isEmpty()) {
				int k2 = itemstack1.getBaseRepairCost();
				if (!itemstack2.isEmpty() && k2 < itemstack2.getBaseRepairCost()) {
					k2 = itemstack2.getBaseRepairCost();
				}

				if (k != i || k == 0) {
					k2 = getNewRepairCost(k2);
				}

				itemstack1.setRepairCost(k2);
				EnchantmentHelper.setEnchantments(map, itemstack1);
			}

			this.resultSlots.setItem(0, itemstack1);
			this.broadcastChanges();
		}
	}

	public static int getNewRepairCost(int oldRepairCost) {
		return oldRepairCost * 2 + 1;
	}

	/**
	 * used by the Anvil GUI to update the Item Name being typed by the player
	 */
	public void updateItemName(String newName) {
		this.repairedItemName = newName;
		if (this.getSlot(2).hasItem()) {
			ItemStack itemstack = this.getSlot(2).getItem();
			if (StringUtils.isBlank(newName)) {
				itemstack.resetHoverName();
			} else {
				itemstack.setHoverName(new StringTextComponent(this.repairedItemName));
			}
		}

		this.createResult();
	}

	/**
	 * Get's the maximum xp cost
	 */
	@OnlyIn(Dist.CLIENT)
	public int getMaximumCost() {
		return this.maximumCost.get();
	}

}
