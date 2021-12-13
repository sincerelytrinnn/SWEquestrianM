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

import com.alaharranhonor.swem.blocks.HitchingPostBase;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.items.tack.HalterItem;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMContainers;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;


public class SaddlebagContainer extends Container {
	private final IInventory horseInventory;

	public final SWEMHorseEntityBase horse;

	public SaddlebagContainer(final int id, final PlayerInventory playerInventory, PacketBuffer data) {
		this(id, playerInventory, data.readInt());
	}

	public SaddlebagContainer(final int id, final PlayerInventory playerInventory, final int entityId) {
		super(SWEMContainers.SADDLE_BAG_CONTAINER.get(), id);
		this.horse = (SWEMHorseEntityBase) playerInventory.player.level.getEntity(entityId);
		this.horseInventory = horse.getSaddlebagInventory();
		horseInventory.startOpen(playerInventory.player);


		int startSaddlebagInvY = 18;

		this.addSlot(new Slot(horseInventory, 0, 8, startSaddlebagInvY) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof HalterItem;
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 1, 26, startSaddlebagInvY) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;//stack.getItem() instanceof HoseItem;
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 2, 44, startSaddlebagInvY) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return  Block.byItem(stack.getItem()) instanceof HitchingPostBase;
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 3, 62, startSaddlebagInvY) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof AirItem;
			} // TODO: Fly spray

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 4, 80, startSaddlebagInvY) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() == SWEMItems.SUGAR_CUBE.get();
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 5, 98, startSaddlebagInvY) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() == SWEMBlocks.QUALITY_BALE_ITEM.get();
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 6, 116, startSaddlebagInvY) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof AirItem;
			} // TODO: SWEET FEED

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 7, 134, startSaddlebagInvY) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof AirItem;
			} // TODO: BRUSH

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 8, 152, startSaddlebagInvY) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;//stack.getItem().getTranslationKey().equals(SWEMBlocks.HALF_BARREL_ITEM.get().getTranslationKey());
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 9, 8, startSaddlebagInvY + 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() == Blocks.CRAFTING_TABLE.asItem();
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 10, 26, startSaddlebagInvY + 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem().is(ItemTags.LOGS);
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 11, 44, startSaddlebagInvY + 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() == Items.TORCH;
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 12, 62, startSaddlebagInvY + 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() == Items.BEEF || stack.getItem() == Items.COOKED_BEEF;
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 13, 80, startSaddlebagInvY + 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() == Items.CHEST;
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 14, 98, startSaddlebagInvY + 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem().is(Tags.Items.INGOTS);
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 15, 116, startSaddlebagInvY + 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof BlockItem;
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 16, 134, startSaddlebagInvY + 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof BlockItem;
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 17, 152, startSaddlebagInvY + 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof BucketItem;
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 18, 8, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof SwordItem;
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 19, 26, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof PickaxeItem;
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 20, 44, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof AxeItem;
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 21, 62, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof ShovelItem;
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 22, 80, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof BowItem;
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 23, 98, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof ArrowItem;
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 24, 116, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof ShieldItem;
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 25, 134, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() == SWEMItems.CANTAZARITE.get();
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		this.addSlot(new Slot(horseInventory, 26, 152, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() == SWEMBlocks.CANTAZARITE_ANVIL.get().asItem();
			}

			@Override
			public int getMaxStackSize() {
				return super.getMaxStackSize();
			}
		});

		// Player Main Inventory
		int startPlayerInvY = 84;
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + col, 8 + (col * 18), startPlayerInvY + (row * 18)));
			}
		}

		// Player Hotbar
		int hotBarY = 142;
		for (int col = 0; col < 9; ++col) {
			this.addSlot(new Slot(playerInventory, col, 8 + col * 18, hotBarY));
		}
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	public boolean stillValid(PlayerEntity playerIn) {
		return this.horseInventory.stillValid(playerIn) && this.horse.isAlive() && this.horse.distanceTo(playerIn) < 8.0F;
	}

	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			int i = this.horseInventory.getContainerSize();
			if (index < i) {
				if (!this.moveItemStackTo(itemstack1, i, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			}

			// Loop over the slots.


			else if (this.getSlot(6).mayPlace(itemstack1) && !this.getSlot(6).hasItem()) {
				if (!this.moveItemStackTo(itemstack1, 6, 7, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.getSlot(5).mayPlace(itemstack1) && !this.getSlot(5).hasItem()) {
				if (!this.moveItemStackTo(itemstack1, 5, 6, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.getSlot(4).mayPlace(itemstack1) && !this.getSlot(4).hasItem()) {
				if (!this.moveItemStackTo(itemstack1, 4, 5, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.getSlot(3).mayPlace(itemstack1) && !this.getSlot(3).hasItem()) {
				if (!this.moveItemStackTo(itemstack1, 3, 4, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.getSlot(2).mayPlace(itemstack1) && !this.getSlot(2).hasItem()) {
				if (!this.moveItemStackTo(itemstack1, 2, 3, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.getSlot(1).mayPlace(itemstack1) && !this.getSlot(1).hasItem()) {
				if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.getSlot(0).mayPlace(itemstack1)) {
				if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			} else if (i <= 2 || !this.moveItemStackTo(itemstack1, 2, i, false)) {
				int j = i + 27;
				int k = j + 9;
				if (index >= j && index < k) {
					if (!this.moveItemStackTo(itemstack1, i, j, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= i && index < j) {
					if (!this.moveItemStackTo(itemstack1, j, k, false)) {
						return ItemStack.EMPTY;
					}
				} else if (!this.moveItemStackTo(itemstack1, j, j, false)) {
					return ItemStack.EMPTY;
				}

				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}

		return itemstack;
	}

	/**
	 * Called when the container is closed.
	 */
	public void removed(PlayerEntity playerIn) {
		super.removed(playerIn);
		this.horseInventory.stopOpen(playerIn);
	}
}
