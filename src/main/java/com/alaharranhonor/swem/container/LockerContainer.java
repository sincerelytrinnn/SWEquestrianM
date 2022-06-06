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

import com.alaharranhonor.swem.tileentity.LockerTE;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMContainers;
import java.util.Objects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

public class LockerContainer extends Container {
  public final LockerTE tileEntity;
  private final IWorldPosCallable canInteractWithCallable;
  private final boolean leftSideOpened;

  /**
   * Instantiates a new Locker container.
   *
   * @param id the id
   * @param playerInventory the player inventory
   * @param data the data
   */
  public LockerContainer(
      final int id, final PlayerInventory playerInventory, final PacketBuffer data) {
    this(id, playerInventory, getTileEntity(playerInventory, data), data.readBoolean());
  }

  /**
   * Instantiates a new Locker container.
   *
   * @param id the id
   * @param playerInventory the player inventory
   * @param tileEntity the tile entity
   * @param leftSideOpened the left side opened
   */
  public LockerContainer(
      final int id,
      final PlayerInventory playerInventory,
      final LockerTE tileEntity,
      boolean leftSideOpened) {
    super(SWEMContainers.LOCKER_CONTAINER.get(), id);
    this.tileEntity = tileEntity;
    this.leftSideOpened = leftSideOpened;
    this.canInteractWithCallable =
        IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos());
    this.initSlots(playerInventory);
  }

  /**
   * Init slots.
   *
   * @param playerInventory the player inventory
   */
  private void initSlots(PlayerInventory playerInventory) {
    int i = (3 - 4) * 18;
    // Init slots

    for (int j = 0; j < 2; ++j) {
      for (int k = 0; k < 9; ++k) {
        int index = k + j * 9 + (leftSideOpened ? 0 : 18);
        this.addSlot(new Slot(tileEntity, index, 8 + k * 18, 18 + j * 18));
      }
    }

    for (int l = 0; l < 3; ++l) {
      for (int j1 = 0; j1 < 9; ++j1) {
        this.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 85 + l * 18 + i));
      }
    }

    for (int i1 = 0; i1 < 9; ++i1) {
      this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 143 + i));
    }
  }

  @Override
  public boolean stillValid(PlayerEntity playerIn) {
    return stillValid(canInteractWithCallable, playerIn, SWEMBlocks.LOCKER.get());
  }

  /**
   * Gets tile entity.
   *
   * @param inventory the inventory
   * @param data the data
   * @return the tile entity
   */
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
