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
		this.horse = (SWEMHorseEntityBase) playerInventory.player.level.getEntity(entityId);
		this.horseInventory = horse.getHorseInventory();
		horseInventory.startOpen(playerInventory.player);


		// Bridle slot 1
		this.addSlot(new Slot(horseInventory, 0, 8, 17) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			public boolean mayPlace(ItemStack stack) {
				return horse.isHalter(stack);
			}

			/**
			 * Actualy only call when we want to render the white square effect over the slots. Return always True, except
			 * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
			 */
			@OnlyIn(Dist.CLIENT)
			public boolean isEnabled() {
				return horse.canWearArmor();
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
			 public boolean mayPlace(ItemStack stack) {
				 return horse.isBlanket(stack) && horse.hasHalter();
			 }

			 /**
			  * Actualy only call when we want to render the white square effect over the slots. Return always True, except
			  * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
			  */
			 @OnlyIn(Dist.CLIENT)
			 public boolean isEnabled() {
				 return horse.canWearArmor();
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
			public boolean mayPlace(ItemStack stack) {
				return horse.isSaddle(stack) && horse.canEquipSaddle();
			}

			/**
			 * Actualy only call when we want to render the white square effect over the slots. Return always True, except
			 * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
			 */
			@OnlyIn(Dist.CLIENT)
			public boolean isEnabled() {
				return horse.canWearArmor();
			}

			/**
			 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
			 * case of armor slots)
			 */
			public int getSlotStackLimit() {
				return 1;
			}

			@Override
			public void setChanged() {
				ItemStack stack = this.getItem();
				if (stack == ItemStack.EMPTY) {
					ItemStack breastCollar = horseInventory.getItem(3);
					ItemStack girthStrap = horseInventory.getItem(5);

					if (breastCollar != ItemStack.EMPTY) {
						ItemEntity stackToSpawn = new ItemEntity(horse.getCommandSenderWorld(), horse.getX(), horse.getY(), horse.getZ(), breastCollar);
						horse.getCommandSenderWorld().addFreshEntity(stackToSpawn);
						horseInventory.setItem(3, ItemStack.EMPTY);

					}

					if (girthStrap != ItemStack.EMPTY) {
						ItemEntity stackToSpawn = new ItemEntity(horse.getCommandSenderWorld(), horse.getX(), horse.getY(), horse.getZ(), girthStrap);
						horse.getCommandSenderWorld().addFreshEntity(stackToSpawn);
						horseInventory.setItem(5, ItemStack.EMPTY);
					}
				}
			}
		});

		// Breast Collar slot 4
		this.addSlot(new Slot(horseInventory, 3, 8, 38) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			public boolean mayPlace(ItemStack stack) {
				return horse.isBreastCollar(stack) && horse.hasHalter();
			}

			/**
			 * Actualy only call when we want to render the white square effect over the slots. Return always True, except
			 * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
			 */
			@OnlyIn(Dist.CLIENT)
			public boolean isEnabled() {
				return horse.canWearArmor();
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
			public boolean mayPlace(ItemStack stack) {
				return horse.isLegWraps(stack) && horse.hasHalter();
			}

			/**
			 * Actualy only call when we want to render the white square effect over the slots. Return always True, except
			 * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
			 */
			@OnlyIn(Dist.CLIENT)
			public boolean isEnabled() {
				return horse.canWearArmor();
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
			public boolean mayPlace(ItemStack stack) {
				return horse.isGirthStrap(stack) && horse.canEquipGirthStrap();
			}

			/**
			 * Actualy only call when we want to render the white square effect over the slots. Return always True, except
			 * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
			 */
			@OnlyIn(Dist.CLIENT)
			public boolean isEnabled() {
				return horse.canWearArmor();
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
			public boolean mayPlace(ItemStack stack) {
				return horse.isSWEMArmor(stack) && horse.canEquipArmor();
			}

			/**
			 * Actualy only call when we want to render the white square effect over the slots. Return always True, except
			 * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
			 */
			@OnlyIn(Dist.CLIENT)
			public boolean isEnabled() {
				return horse.canWearArmor();
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
			public boolean mayPlace(ItemStack stack) {
				return horse.isSaddlebag(stack);
			}

			/**
			 * Actualy only call when we want to render the white square effect over the slots. Return always True, except
			 * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
			 */
			@OnlyIn(Dist.CLIENT)
			public boolean isEnabled() {
				return horse.canWearArmor();
			}

			/**
			 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
			 * case of armor slots)
			 */
			public int getSlotStackLimit() {
				return 1;
			}

			@Override
			public void setChanged() {
				ItemStack stack = this.getItem();
				if (stack.isEmpty() && !horse.getSaddlebagInventory().isEmpty()) {
					for (int i = 0; i < horse.getSaddlebagInventory().getContainerSize(); i++) {
						ItemStack stackToDrop = horse.getSaddlebagInventory().getItem(i);
						ItemEntity stackToSpawn = new ItemEntity(horse.getCommandSenderWorld(), horse.getX(), horse.getY(), horse.getZ(), stackToDrop);
						horse.getCommandSenderWorld().addFreshEntity(stackToSpawn);
					}
					horse.getSaddlebagInventory().clearContent();
				}
				if (stack.isEmpty() && !horse.getBedrollInventory().isEmpty()) {
					for (int i = 0; i < horse.getBedrollInventory().getContainerSize(); i++) {
						ItemStack stackToDrop = horse.getBedrollInventory().getItem(i);
						ItemEntity stackToSpawn = new ItemEntity(horse.getCommandSenderWorld(), horse.getX(), horse.getY(), horse.getZ(), stackToDrop);
						horse.getCommandSenderWorld().addFreshEntity(stackToSpawn);
					}
					horse.getBedrollInventory().clearContent();
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
	public boolean stillValid(PlayerEntity playerIn) {
		return this.horseInventory.stillValid(playerIn) && this.horse.isAlive() && this.horse.distanceTo(playerIn) < 8.0F;
	}

	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			ItemStack itemstack = itemstack1.copy();
			int i = this.horseInventory.getContainerSize();
			if (index < i) {
				if (!this.moveItemStackTo(itemstack1, i, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (this.getSlot(6).mayPlace(itemstack1) && !this.getSlot(6).hasItem()) {
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

		return ItemStack.EMPTY;
	}

	/**
	 * Called when the container is closed.
	 */
	public void removed(PlayerEntity playerIn) {
		super.removed(playerIn);
		this.horseInventory.stopOpen(playerIn);
	}




}
