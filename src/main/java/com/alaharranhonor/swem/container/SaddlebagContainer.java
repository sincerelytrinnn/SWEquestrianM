package com.alaharranhonor.swem.container;

import com.alaharranhonor.swem.blocks.HitchingPostBase;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.items.HoseItem;
import com.alaharranhonor.swem.items.tack.HalterItem;
import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import com.alaharranhonor.swem.util.initialization.SWEMContainers;
import com.alaharranhonor.swem.util.initialization.SWEMItems;
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
		this.horse = (SWEMHorseEntityBase) playerInventory.player.world.getEntityByID(entityId);
		this.horseInventory = horse.getSaddlebagInventory();
		horseInventory.openInventory(playerInventory.player);


		int startSaddlebagInvY = 18;

		this.addSlot(new Slot(horseInventory, 0, 8, startSaddlebagInvY) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof HalterItem;
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 1, 26, startSaddlebagInvY) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof HoseItem;
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 2, 44, startSaddlebagInvY) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return  Block.getBlockFromItem(stack.getItem()) instanceof HitchingPostBase;
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 3, 62, startSaddlebagInvY) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof AirItem;
			} // TODO: Fly spray

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 4, 80, startSaddlebagInvY) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == SWEMItems.SUGAR_CUBE.get();
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 5, 98, startSaddlebagInvY) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == SWEMBlocks.QUALITY_BALE_ITEM.get();
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 6, 116, startSaddlebagInvY) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof AirItem;
			} // TODO: SWEET FEED

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 7, 134, startSaddlebagInvY) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof AirItem;
			} // TODO: BRUSH

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 8, 152, startSaddlebagInvY) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem().getTranslationKey().equals(SWEMBlocks.HALF_BARREL_ITEM.get().getTranslationKey());
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 9, 8, startSaddlebagInvY + 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Blocks.CRAFTING_TABLE.asItem();
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 10, 26, startSaddlebagInvY + 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem().isIn(ItemTags.LOGS);
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 11, 44, startSaddlebagInvY + 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Items.TORCH;
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 12, 62, startSaddlebagInvY + 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Items.BEEF || stack.getItem() == Items.COOKED_BEEF;
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 13, 80, startSaddlebagInvY + 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Items.CHEST;
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 14, 98, startSaddlebagInvY + 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem().isIn(Tags.Items.INGOTS);
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 15, 116, startSaddlebagInvY + 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof BlockItem;
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 16, 134, startSaddlebagInvY + 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof BlockItem;
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 17, 152, startSaddlebagInvY + 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof BucketItem;
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 18, 8, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof SwordItem;
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 19, 26, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof PickaxeItem;
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 20, 44, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof AxeItem;
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 21, 62, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ShovelItem;
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 22, 80, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof BowItem;
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 23, 98, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ArrowItem;
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 24, 116, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ShieldItem;
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 25, 134, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == SWEMItems.CANTAZARITE.get();
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
			}
		});

		this.addSlot(new Slot(horseInventory, 26, 152, startSaddlebagInvY + 2 * 18) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == SWEMBlocks.CANTAZARITE_ANVIL.get().asItem();
			}

			@Override
			public int getSlotStackLimit() {
				return super.getSlotStackLimit();
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


			else if (this.getSlot(6).isItemValid(itemstack1) && !this.getSlot(6).getHasStack()) {
				if (!this.mergeItemStack(itemstack1, 6, 7, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.getSlot(5).isItemValid(itemstack1) && !this.getSlot(5).getHasStack()) {
				if (!this.mergeItemStack(itemstack1, 5, 6, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.getSlot(4).isItemValid(itemstack1) && !this.getSlot(4).getHasStack()) {
				if (!this.mergeItemStack(itemstack1, 4, 5, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.getSlot(3).isItemValid(itemstack1) && !this.getSlot(3).getHasStack()) {
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
