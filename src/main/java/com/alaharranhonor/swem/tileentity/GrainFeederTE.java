package com.alaharranhonor.swem.tileentity;
/*
 * All Rights Reserved
 *
 * Copyright (c) 2022, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.alaharranhonor.swem.blocks.GrainFeederBlock;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import com.alaharranhonor.swem.util.registry.SWEMTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class GrainFeederTE extends TileEntity implements HorseFeedable {
    private ItemStackHandler itemHandler = createHandler();
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public GrainFeederTE() {
        super(SWEMTileEntities.GRAIN_FEEDER.get());
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv"));
        super.load(p_230337_1_, tag);
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());
        return super.save(tag);
    }

    public IItemHandler getHandler() {
        return this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).resolve().get();
    }

    /**
     * invalidates a tile entity
     */
    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                World world = GrainFeederTE.this.getLevel();
                ItemStack stack = this.getStackInSlot(slot);
                world.setBlock(GrainFeederTE.this.getBlockPos(), world.getBlockState(GrainFeederTE.this.getBlockPos()).setValue(GrainFeederBlock.OCCUPIED, !stack.isEmpty()), 2);
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.getItem() == SWEMItems.SWEET_FEED_OPENED.get();
            }

            @NotNull
            @Override
            public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if (stack.getItem() != SWEMItems.SWEET_FEED_OPENED.get()) {
                    // Invalid item.
                    return stack;
                }

                if (this.getSlotLimit(slot) == this.getStackInSlot(slot).getCount()) {
                    return stack;
                }

                ItemStack copyStack = stack.copy();
                copyStack.setCount(1);

                return super.insertItem(slot, copyStack, simulate);
            }

            @Override
            public int getSlotLimit(int slot) {
                // First slot (only), only allow 2 slabs to be present.
                return slot == 0 ? 1 : super.getSlotLimit(slot);
            }
        };
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public boolean isEmpty(int slot) {
        return this.itemHandler.getStackInSlot(slot).isEmpty();
    }

    @Override
    public void eat(int slot, SWEMHorseEntityBase horse) {
        ItemStack extractedItemStack = this.itemHandler.extractItem(slot, 1, false);
        horse.getNeeds().getHunger().addPoints(extractedItemStack);
    }

    /**
     * NEVER DO ANYTHING WITH THIS STACK!
     *
     * @param slot
     * @return A copy of the itemstack currently in the TE
     */
    @Override
    public ItemStack peekStack(int slot) {
        return this.itemHandler.getStackInSlot(slot).copy();
    }
}
