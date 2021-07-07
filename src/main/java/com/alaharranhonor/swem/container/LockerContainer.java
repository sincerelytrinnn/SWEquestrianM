package com.alaharranhonor.swem.container;

import com.alaharranhonor.swem.tileentity.LockerTE;
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

public class LockerContainer extends Container {
	public final LockerTE tileEntity;
	private final IWorldPosCallable canInteractWithCallable;
	private boolean leftSideOpened;

	public LockerContainer(final int id, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(id, playerInventory, getTileEntity(playerInventory, data), data.readBoolean());
	}


	public LockerContainer(final int id, final PlayerInventory playerInventory, final LockerTE tileEntity, boolean leftSideOpened) {
		super(SWEMContainers.LOCKER_CONTAINER.get(), id);
		this.tileEntity = tileEntity;
		this.leftSideOpened = leftSideOpened;
		this.canInteractWithCallable = IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos());
		System.out.println(leftSideOpened);
		this.initSlots(playerInventory);
	}

	private void initSlots(PlayerInventory playerInventory) {
		int i = (3 - 4) * 18;
		// Init slots

		for(int j = 0; j < 3; ++j) {
			for(int k = 0; k < 9; ++k) {
				int index = k + j * 9 + (leftSideOpened ? 0 : 27);
				this.addSlot(new Slot(tileEntity, index, 8 + k * 18, 18 + j * 18));
			}
		}

		for(int l = 0; l < 3; ++l) {
			for(int j1 = 0; j1 < 9; ++j1) {
				this.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
			}
		}

		for(int i1 = 0; i1 < 9; ++i1) {
			this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 161 + i));
		}
	}

	@Override
	public boolean stillValid(PlayerEntity playerIn) {
		return stillValid(canInteractWithCallable, playerIn, SWEMBlocks.LOCKER.get());
	}

	private static LockerTE getTileEntity(final PlayerInventory inventory, final PacketBuffer data) {
		Objects.requireNonNull(inventory, "Inventory cannot be null");
		Objects.requireNonNull(data, "Packet Data cannot be null");
		TileEntity tileAtPos = inventory.player.level.getBlockEntity(data.readBlockPos());
		if (tileAtPos instanceof LockerTE) {
			return (LockerTE) tileAtPos;
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
