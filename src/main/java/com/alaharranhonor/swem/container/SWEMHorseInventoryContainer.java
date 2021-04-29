package com.alaharranhonor.swem.container;

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.initialization.SWEMContainers;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SWEMHorseInventoryContainer extends Container {

	private final IInventory horseInventory;

	public final SWEMHorseEntityBase horse;

	public SWEMHorseInventoryContainer(final int id, final PlayerInventory playerInventory, PacketBuffer data) {
		this(id, playerInventory, data.readInt());
	}

	public SWEMHorseInventoryContainer(final int id, final PlayerInventory playerInventory, final int entityId) {
		super(SWEMContainers.SWEM_HORSE_CONTAINER.get(), id);
		this.horse = (SWEMHorseEntityBase) playerInventory.player.world.getEntityByID(entityId);
		this.horseInventory = horse.getHorseInventory();
		horseInventory.openInventory(playerInventory.player);


		// Bridle slot 1
		this.addSlot(new Slot(horseInventory, 0, 8, 17) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			public boolean isItemValid(ItemStack stack) {
				return horse.isHalter(stack);
			}

			/**
			 * Actualy only call when we want to render the white square effect over the slots. Return always True, except
			 * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
			 */
			@OnlyIn(Dist.CLIENT)
			public boolean isEnabled() {
				return horse.func_230276_fq_();
			}

			/**
			 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
			 * case of armor slots)
			 */
			public int getSlotStackLimit() {
				return 1;
			}


		});

		// Blanket slot 2
		this.addSlot(new Slot(horseInventory, 1, 29, 38) {
			 /**
			  * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			  */
			 public boolean isItemValid(ItemStack stack) {
				 return horse.isBlanket(stack) && horse.hasHalter();
			 }

			 /**
			  * Actualy only call when we want to render the white square effect over the slots. Return always True, except
			  * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
			  */
			 @OnlyIn(Dist.CLIENT)
			 public boolean isEnabled() {
				 return horse.func_230276_fq_();
			 }

			 /**
			  * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
			  * case of armor slots)
			  */
			 public int getSlotStackLimit() {
				 return 1;
			 }
		});

		// Saddle slot 3
		this.addSlot(new Slot(horseInventory, 2, 29, 17) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			public boolean isItemValid(ItemStack stack) {
				return horse.isSaddle(stack) && horse.canEquipSaddle();
			}

			/**
			 * Actualy only call when we want to render the white square effect over the slots. Return always True, except
			 * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
			 */
			@OnlyIn(Dist.CLIENT)
			public boolean isEnabled() {
				return horse.func_230276_fq_();
			}

			/**
			 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
			 * case of armor slots)
			 */
			public int getSlotStackLimit() {
				return 1;
			}

			@Override
			public void onSlotChanged() {
				ItemStack stack = this.getStack();
				if (stack == ItemStack.EMPTY) {
					ItemStack breastCollar = horseInventory.getStackInSlot(3);
					ItemStack girthStrap = horseInventory.getStackInSlot(5);

					if (breastCollar != ItemStack.EMPTY) {
						ItemEntity stackToSpawn = new ItemEntity(horse.getEntityWorld(), horse.getPosX(), horse.getPosY(), horse.getPosZ(), breastCollar);
						horse.getEntityWorld().addEntity(stackToSpawn);
						horseInventory.setInventorySlotContents(3, ItemStack.EMPTY);

					}

					if (girthStrap != ItemStack.EMPTY) {
						ItemEntity stackToSpawn = new ItemEntity(horse.getEntityWorld(), horse.getPosX(), horse.getPosY(), horse.getPosZ(), girthStrap);
						horse.getEntityWorld().addEntity(stackToSpawn);
						horseInventory.setInventorySlotContents(5, ItemStack.EMPTY);
					}
				}
			}
		});

		// Breast Collar slot 4
		this.addSlot(new Slot(horseInventory, 3, 8, 38) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			public boolean isItemValid(ItemStack stack) {
				return horse.isBreastCollar(stack) && horse.hasHalter();
			}

			/**
			 * Actualy only call when we want to render the white square effect over the slots. Return always True, except
			 * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
			 */
			@OnlyIn(Dist.CLIENT)
			public boolean isEnabled() {
				return horse.func_230276_fq_();
			}

			/**
			 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
			 * case of armor slots)
			 */
			public int getSlotStackLimit() {
				return 1;
			}
		});

		// Leg Wraps Slot 5
		this.addSlot(new Slot(horseInventory, 4, 8, 59) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			public boolean isItemValid(ItemStack stack) {
				return horse.isLegWraps(stack) && horse.hasHalter();
			}

			/**
			 * Actualy only call when we want to render the white square effect over the slots. Return always True, except
			 * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
			 */
			@OnlyIn(Dist.CLIENT)
			public boolean isEnabled() {
				return horse.func_230276_fq_();
			}

			/**
			 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
			 * case of armor slots)
			 */
			public int getSlotStackLimit() {
				return 1;
			}
		});

		// Girth Strap Slot 6
		this.addSlot(new Slot(horseInventory, 5, 29, 59) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			public boolean isItemValid(ItemStack stack) {
				return horse.isGirthStrap(stack) && horse.canEquipGirthStrap();
			}

			/**
			 * Actualy only call when we want to render the white square effect over the slots. Return always True, except
			 * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
			 */
			@OnlyIn(Dist.CLIENT)
			public boolean isEnabled() {
				return horse.func_230276_fq_();
			}

			/**
			 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
			 * case of armor slots)
			 */
			public int getSlotStackLimit() {
				return 1;
			}
		});

		// SWEM Horse Armor Slot 7
		this.addSlot(new Slot(horseInventory, 6, 8, 87) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			public boolean isItemValid(ItemStack stack) {
				return horse.isSWEMArmor(stack);
			}

			/**
			 * Actualy only call when we want to render the white square effect over the slots. Return always True, except
			 * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
			 */
			@OnlyIn(Dist.CLIENT)
			public boolean isEnabled() {
				return horse.func_230276_fq_();
			}

			/**
			 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
			 * case of armor slots)
			 */
			public int getSlotStackLimit() {
				return 1;
			}

		});

		// SaddleBag Slot 8
		this.addSlot(new Slot(horseInventory, 7, 29, 87) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			public boolean isItemValid(ItemStack stack) {
				return horse.isSaddlebag(stack);
			}

			/**
			 * Actualy only call when we want to render the white square effect over the slots. Return always True, except
			 * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
			 */
			@OnlyIn(Dist.CLIENT)
			public boolean isEnabled() {
				return horse.func_230276_fq_();
			}

			/**
			 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
			 * case of armor slots)
			 */
			public int getSlotStackLimit() {
				return 1;
			}

			@Override
			public void onSlotChanged() {
				ItemStack stack = this.getStack();
				if (stack.isEmpty() && !horse.getSaddlebagInventory().isEmpty()) {
					for (int i = 0; i < horse.getSaddlebagInventory().getSizeInventory(); i++) {
						ItemStack stackToDrop = horse.getSaddlebagInventory().getStackInSlot(i);
						ItemEntity stackToSpawn = new ItemEntity(horse.getEntityWorld(), horse.getPosX(), horse.getPosY(), horse.getPosZ(), stackToDrop);
						horse.getEntityWorld().addEntity(stackToSpawn);
					}
					horse.getSaddlebagInventory().clear();
				}
				if (stack.isEmpty() && !horse.getBedrollInventory().isEmpty()) {
					for (int i = 0; i < horse.getBedrollInventory().getSizeInventory(); i++) {
						ItemStack stackToDrop = horse.getBedrollInventory().getStackInSlot(i);
						ItemEntity stackToSpawn = new ItemEntity(horse.getEntityWorld(), horse.getPosX(), horse.getPosY(), horse.getPosZ(), stackToDrop);
						horse.getEntityWorld().addEntity(stackToSpawn);
					}
					horse.getBedrollInventory().clear();
				}
			}
		});

		// Player Main Inventory
		int startPlayerInvY = 140;
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + col, 8 + (col * 18), startPlayerInvY + (row * 18)));
			}
		}

		// Player Hotbar
		int hotBarY = 198;
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
			} else if (this.getSlot(6).isItemValid(itemstack1) && !this.getSlot(6).getHasStack()) {
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
