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

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.items.SWEMHorseArmorItem;
import com.alaharranhonor.swem.items.tack.*;
import com.alaharranhonor.swem.tileentity.TackBoxTE;
import com.alaharranhonor.swem.util.registry.SWEMContainers;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

import java.util.Objects;

public class TackBoxContainer extends Container {

	public final TackBoxTE tileEntity;
	private final IWorldPosCallable canInteractWithCallable;

	public SWEMHorseEntityBase horse;

	/**
	 * Instantiates a new Tack box container.
	 *
	 * @param id              the id
	 * @param playerInventory the player inventory
	 * @param data            the data
	 */
	public TackBoxContainer(final int id, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(id, playerInventory, getTileEntity(playerInventory, data), data);
	}

	public TackBoxContainer(final int id, final PlayerInventory playerInventory, final TackBoxTE tileEntity, final PacketBuffer data) {
		this(id, playerInventory, tileEntity);
		this.horse = (SWEMHorseEntityBase) playerInventory.player.level.getEntity(data.readInt());
	}

	/**
	 * Instantiates a new Tack box container.
	 *
	 * @param id              the id
	 * @param playerInventory the player inventory
	 * @param tileEntity      the tile entity
	 */
	public TackBoxContainer(final int id, final PlayerInventory playerInventory, final TackBoxTE tileEntity) {
		super(SWEMContainers.TACKBOX_CONTAINER.get(), id);
		this.tileEntity = tileEntity;
		this.canInteractWithCallable = IWorldPosCallable.create(playerInventory.player.level, tileEntity.getBlockPos());


		// 10 gap between each compartment.
		// Slot is 16x16 without border - 18x18 with border.
		// Each slot in it's compartment has a 3 pixel padding on all sides.
		// First compartment slot start at 10x, 138y with border -1 on both without border.

		int slotStartY = 39;

		//Generic Section
		int genericX = 8;
		this.addSlot(new Slot(this.tileEntity, 0, genericX, slotStartY){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof HalterItem && !(stack.getItem() instanceof BridleItem);
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 1, genericX, slotStartY + 3 + 18){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof SaddlebagItem;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 2, genericX, slotStartY + 6 + 36){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof SWEMHorseArmorItem;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});

		//Adventure Section
		int adventureX = 35;
		this.addSlot(new Slot(this.tileEntity, 3, adventureX, slotStartY){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof AdventureBridleItem;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 4, adventureX + 3 + 18, slotStartY){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof AdventureSaddleItem;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 5, adventureX, slotStartY + 3 + 18){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof AdventureBreastCollarItem;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 6, adventureX + 3 + 18, slotStartY + 3 + 18){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof AdventureBlanketItem;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 7, adventureX, slotStartY + 6 + 36){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof AdventureLegWraps;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 8, adventureX + 3 + 18, slotStartY + 6 + 36){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof AdventureGirthStrapItem;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});

		// English Section
		int englishX = 83;
		this.addSlot(new Slot(this.tileEntity, 9, englishX, slotStartY) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof EnglishBridleItem;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 10, englishX + 3 + 18, slotStartY){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof EnglishSaddleItem;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 11, englishX, slotStartY + 3 + 18){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof EnglishBreastCollar;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 12, englishX + 3 + 18, slotStartY + 3 + 18){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof EnglishBlanketItem;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 13, englishX, slotStartY + 6 + 36){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof EnglishLegWraps;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 14, englishX + 3 + 18, slotStartY + 6 + 36){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof EnglishGirthStrap;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});

		//Western Section
		int westernX = 131;
		this.addSlot(new Slot(this.tileEntity, 15, westernX, slotStartY){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof WesternBridleItem;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 16, westernX + 3 + 18, slotStartY){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof WesternSaddleItem;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 17, westernX, slotStartY + 3 + 18){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof WesternBreastCollarItem;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 18, westernX + 3 + 18, slotStartY + 3 + 18){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof WesternBlanketItem;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 19, westernX, slotStartY + 6 + 36){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof WesternLegWraps;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 20, westernX + 3 + 18, slotStartY + 6 + 36){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof WesternGirthStrapItem;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}
		});

		// General Compartment
		int generalStorageY = 112;
		for (int col = 0; col < 9; ++col) {
			this.addSlot(new Slot(this.tileEntity, col + 21, 8 + col * 18, generalStorageY));
		}

		


		// Player Main Inventory
		int startPlayerInvY = 163;
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + col, 8 + (col * 18), startPlayerInvY + (row * 18)));
			}
		}

		// Player Hotbar
		int hotBarY = 221;
		for (int col = 0; col < 9; ++col) {
			this.addSlot(new Slot(playerInventory, col, 8 + col * 18, hotBarY));
		}
	}


	/**
	 * Gets tile entity.
	 *
	 * @param inventory the inventory
	 * @param data      the data
	 * @return the tile entity
	 */
	private static TackBoxTE getTileEntity(final PlayerInventory inventory, final PacketBuffer data) {
		Objects.requireNonNull(inventory, "Inventory cannot be null");
		Objects.requireNonNull(data, "Packet Data cannot be null");
		TileEntity tileAtPos = inventory.player.level.getBlockEntity(data.readBlockPos());
		if (tileAtPos instanceof TackBoxTE) {
			return (TackBoxTE) tileAtPos;
		}
		throw new IllegalStateException("Tile entity is not correct." + tileAtPos);
	}



	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack stack = slot.getItem();
			itemstack = stack.copy();
			if (index < 30) {
				if (!moveItemStackTo(stack, 30, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!moveItemStackTo(stack, 0, 30, false)) {
				return ItemStack.EMPTY;
			}

			if (stack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}

		return itemstack;
	}

	@Override
	public boolean stillValid(PlayerEntity playerIn) {
		Block tackbox = playerIn.level.getBlockState(this.tileEntity.getBlockPos()).getBlock();
		return stillValid(canInteractWithCallable, playerIn, tackbox);
	}
}
