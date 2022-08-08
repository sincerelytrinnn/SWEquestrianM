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

import com.alaharranhonor.swem.blocks.CantazariteAnvilBlock;
import com.alaharranhonor.swem.blocks.FuelBlockItemBase;
import com.alaharranhonor.swem.blocks.HalfBarrelBlock;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.items.*;
import com.alaharranhonor.swem.items.tack.HalterItem;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMContainers;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.block.Block;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

public class SaddlebagAndBedrollContainer extends Container {
    public final SWEMHorseEntityBase horse;
    private final IInventory horseInventory;

    /**
     * Instantiates a new Saddlebag container.
     *
     * @param id              the id
     * @param playerInventory the player inventory
     * @param data            the data
     */
    public SaddlebagAndBedrollContainer(
        final int id, final PlayerInventory playerInventory, PacketBuffer data) {
        this(id, playerInventory, data.readInt());
    }

    /**
     * Instantiates a new Saddlebag container.
     *
     * @param id              the id
     * @param playerInventory the player inventory
     * @param entityId        the entity id
     */
    public SaddlebagAndBedrollContainer(
        final int id, final PlayerInventory playerInventory, final int entityId) {
        super(SWEMContainers.SADDLE_BAG_AND_BEDROLL_CONTAINER.get(), id);
        this.horse = (SWEMHorseEntityBase) playerInventory.player.level.getEntity(entityId);
        this.horseInventory = horse.getSaddlebagInventory();
        horseInventory.startOpen(playerInventory.player);

        int startBedrollInvY = 18;

        this.addSlot(
            new Slot(horseInventory, 0, 53, startBedrollInvY) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof BedItem;
                }
            });

        this.addSlot(
            new Slot(horseInventory, 1, 71, startBedrollInvY) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return Block.byItem(stack.getItem()) instanceof CampfireBlock;
                }
            });

        this.addSlot(
            new Slot(horseInventory, 2, 89, startBedrollInvY) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof FuelBlockItemBase;
                }
            });

        this.addSlot(
            new Slot(horseInventory, 3, 107, startBedrollInvY) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof FlintAndSteelItem;
                }
            });

        int startSaddlebagInvY = 49;

        this.addSlot(
            new Slot(horseInventory, 4, 8, startSaddlebagInvY) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof HalterItem;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 5, 26, startSaddlebagInvY) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof LeadAnchorItem || stack.getItem() instanceof LeadItem;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 6, 44, startSaddlebagInvY) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() == SWEMBlocks.QUALITY_BALE.get().asItem();
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 7, 62, startSaddlebagInvY) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof SweetFeed
                        || stack.getItem() instanceof SweetFeed.UnopenedSweetFeed;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 8, 80, startSaddlebagInvY) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof BrushItem;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 9, 98, startSaddlebagInvY) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof BucketItem || stack.getItem() instanceof MilkBucketItem;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 10, 116, startSaddlebagInvY) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return Block.byItem(stack.getItem()) instanceof HalfBarrelBlock;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 11, 134, startSaddlebagInvY) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof MedicalItem;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 12, 152, startSaddlebagInvY) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof TrackerItem;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 13, 8, startSaddlebagInvY + 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() == Items.CRAFTING_TABLE;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 14, 26, startSaddlebagInvY + 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem().is(ItemTags.LOGS) || stack.getItem().is(ItemTags.PLANKS);
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 15, 44, startSaddlebagInvY + 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() == Items.CHEST;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 16, 62, startSaddlebagInvY + 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem().is(Tags.Items.INGOTS);
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 17, 80, startSaddlebagInvY + 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof BlockItem;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 18, 98, startSaddlebagInvY + 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof BucketItem || stack.getItem() instanceof MilkBucketItem;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 19, 116, startSaddlebagInvY + 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof MercyBladeItem;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 20, 134, startSaddlebagInvY + 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem().isEdible();
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 21, 152, startSaddlebagInvY + 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return Block.byItem(stack.getItem()) instanceof TorchBlock;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 22, 8, startSaddlebagInvY + 2 * 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof BowItem;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 23, 26, startSaddlebagInvY + 2 * 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof SwordItem;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 24, 44, startSaddlebagInvY + 2 * 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof PickaxeItem;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 25, 62, startSaddlebagInvY + 2 * 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof AxeItem;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 26, 80, startSaddlebagInvY + 2 * 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof ShovelItem;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 27, 98, startSaddlebagInvY + 2 * 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof ShieldItem;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 28, 116, startSaddlebagInvY + 2 * 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof ArrowItem;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 29, 134, startSaddlebagInvY + 2 * 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() == SWEMItems.CANTAZARITE.get();
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        this.addSlot(
            new Slot(horseInventory, 30, 152, startSaddlebagInvY + 2 * 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return Block.byItem(stack.getItem()) instanceof CantazariteAnvilBlock;
                }

                @Override
                public int getMaxStackSize() {
                    return super.getMaxStackSize();
                }
            });

        // Player Main Inventory
        int startPlayerInvY = 116;
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(
                    new Slot(
                        playerInventory,
                        9 + (row * 9) + col,
                        8 + (col * 18),
                        startPlayerInvY + (row * 18)));
            }
        }

        // Player Hotbar
        int hotBarY = 174;
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, hotBarY));
        }
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean stillValid(PlayerEntity playerIn) {
        return this.horseInventory.stillValid(playerIn)
            && this.horse.isAlive()
            && this.horse.distanceTo(playerIn) < 8.0F;
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack
     * between the player inventory and the other inventory(s).
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
