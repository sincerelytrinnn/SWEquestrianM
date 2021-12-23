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
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMContainers;
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

	public final SWEMHorseEntityBase horse;

	public TackBoxContainer(final int id, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(id, playerInventory, getTileEntity(playerInventory, data));
	}

	public TackBoxContainer(final int id, final PlayerInventory playerInventory, final TackBoxTE tileEntity) {
		super(SWEMContainers.TACKBOX_CONTAINER.get(), id);
		this.tileEntity = tileEntity;
		this.horse = (SWEMHorseEntityBase) playerInventory.player.level.getEntity(this.tileEntity.getTileData().getInt("horseID"));
		this.canInteractWithCallable = IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos());


		// 10 gap between each compartment.
		// Slot is 16x16 without border - 18x18 with border.
		// Each slot in it's compartment has a 3 pixel padding on all sides.
		// First compartment slot start at 10x, 138y with border -1 on both without border.

		int slotStartY = 139;
		// English Section
		int englishX = 11;
		this.addSlot(new Slot(this.tileEntity, 0, englishX, slotStartY) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof EnglishBridleItem;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 1, englishX + 3 + 18, slotStartY){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof EnglishSaddleItem;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 2, englishX, slotStartY + 3 + 18){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof EnglishBreastCollar;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 3, englishX + 3 + 18, slotStartY + 3 + 18){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof EnglishBlanketItem;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 4, englishX, slotStartY + 6 + 36){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof EnglishLegWraps;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 5, englishX + 3 + 18, slotStartY + 6 + 36){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof EnglishGirthStrap;
			}
		});

		//Western Section
		int westernX = 60;
		this.addSlot(new Slot(this.tileEntity, 6, westernX, slotStartY){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof WesternBridleItem;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 7, westernX + 3 + 18, slotStartY){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof WesternSaddleItem;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 8, westernX, slotStartY + 3 + 18){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof WesternBreastCollarItem;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 9, westernX + 3 + 18, slotStartY + 3 + 18){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof WesternBlanketItem;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 10, westernX, slotStartY + 6 + 36){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof WesternLegWraps;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 11, westernX + 3 + 18, slotStartY + 6 + 36){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof WesternGirthStrapItem;
			}
		});

		// Adventure Section
		int adventureX = 110;
		this.addSlot(new Slot(this.tileEntity, 12, adventureX, slotStartY){
			@Override
			public boolean isActive() {
				return true;
			}

			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof AdventureSaddleItem;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 13, adventureX, slotStartY + 3 + 18){
			@Override
			public boolean isActive() {
				return true;
			}

			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof SWEMHorseArmorItem;
			}
		});
		this.addSlot(new Slot(this.tileEntity, 14, adventureX, slotStartY + 6 + 36){
			@Override
			public boolean isActive() {
				return true;
			}
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() instanceof SaddlebagItem;
			}
		});

		// General Compartment
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				this.addSlot(new Slot(this.tileEntity, (i * 5) + j + 15, 137 + (j * 18) + (3 * j), 139 + (i * 18) + (i * 3)));
			}
		}


		// Player Main Inventory
		int startPlayerInvY = 221;
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + col, 47 + (col * 18), startPlayerInvY + (row * 18)));
			}
		}

		// Player Hotbar
		int hotBarY = 279;
		for (int col = 0; col < 9; ++col) {
			this.addSlot(new Slot(playerInventory, col, 47 + col * 18, hotBarY));
		}
	}



	/**
	 * Determines whether supplied player can use this container
	 *
	 * @param playerIn
	 */
	@Override
	public boolean stillValid(PlayerEntity playerIn) {
		return stillValid(canInteractWithCallable, playerIn, SWEMBlocks.TACK_BOX.get());
	}



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
}
