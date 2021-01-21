package com.alaharranhonor.swem.container;

import com.alaharranhonor.swem.blocks.FuelBlockItemBase;
import com.alaharranhonor.swem.blocks.HitchingPostBase;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.items.HoseItem;
import com.alaharranhonor.swem.items.tack.HalterItem;
import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import com.alaharranhonor.swem.util.initialization.SWEMContainers;
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

public class BedrollContainer extends Container {
	private final IInventory horseInventory;

	public final SWEMHorseEntityBase horse;

	public BedrollContainer(final int id, final PlayerInventory playerInventory, PacketBuffer data) {
		this(id, playerInventory, data.readInt());
	}

	public BedrollContainer(final int id, final PlayerInventory playerInventory, final int entityId) {
		super(SWEMContainers.BED_ROLL_CONTAINER.get(), id);
		this.horse = (SWEMHorseEntityBase) playerInventory.player.world.getEntityByID(entityId);
		this.horseInventory = horse.getBedrollInventory();
		horseInventory.openInventory(playerInventory.player);


		int startSaddlebagInvY = 20;

		this.addSlot(new Slot(horseInventory, 0, 53, startSaddlebagInvY) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof BedItem;
			}

		});

		this.addSlot(new Slot(horseInventory, 1, 71, startSaddlebagInvY) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Items.CAMPFIRE || stack.getItem() == Items.SOUL_CAMPFIRE;
			}

		});

		this.addSlot(new Slot(horseInventory, 2, 89, startSaddlebagInvY) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof FuelBlockItemBase;
			}

		});

		this.addSlot(new Slot(horseInventory, 3, 107, startSaddlebagInvY) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof FlintAndSteelItem;
			}

		});

		// Player Main Inventory
		int startPlayerInvY = 51;
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + col, 8 + (col * 18), startPlayerInvY + (row * 18)));
			}
		}

		// Player Hotbar
		int hotBarY = 109;
		for (int col = 0; col < 9; ++col) {
			this.addSlot(new Slot(playerInventory, col, 8 + col * 18, hotBarY));
		}
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	public boolean canInteractWith(PlayerEntity playerIn) {
		return this.horseInventory.isUsableByPlayer(playerIn) && this.horse.isAlive() && this.horse.getDistance(playerIn) < 8.0F;
	}

	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			int i = this.horseInventory.getSizeInventory();
			if (index < i) {
				if (!this.mergeItemStack(itemstack1, i, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			}

			// Loop over the slots.

			else if (this.getSlot(3).isItemValid(itemstack1) && !this.getSlot(3).getHasStack()) {
				if (!this.mergeItemStack(itemstack1, 3, 4, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.getSlot(2).isItemValid(itemstack1) && !this.getSlot(2).getHasStack()) {
				if (!this.mergeItemStack(itemstack1, 2, 3, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.getSlot(1).isItemValid(itemstack1) && !this.getSlot(1).getHasStack()) {
				if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.getSlot(0).isItemValid(itemstack1)) {
				if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			} else if (i <= 2 || !this.mergeItemStack(itemstack1, 2, i, false)) {
				int j = i + 27;
				int k = j + 9;
				if (index >= j && index < k) {
					if (!this.mergeItemStack(itemstack1, i, j, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= i && index < j) {
					if (!this.mergeItemStack(itemstack1, j, k, false)) {
						return ItemStack.EMPTY;
					}
				} else if (!this.mergeItemStack(itemstack1, j, j, false)) {
					return ItemStack.EMPTY;
				}

				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	/**
	 * Called when the container is closed.
	 */
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
		this.horseInventory.closeInventory(playerIn);
	}
}
