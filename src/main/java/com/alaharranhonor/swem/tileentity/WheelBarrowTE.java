package com.alaharranhonor.swem.tileentity;

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

import com.alaharranhonor.swem.blocks.ShavingsItem;
import com.alaharranhonor.swem.blocks.WheelBarrowBlock;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import com.alaharranhonor.swem.util.registry.SWEMTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Random;

public class WheelBarrowTE extends TileEntity implements ITickableTileEntity {

    public ItemStackHandler itemHandler = createHandler();

    private int timer = 0;
    private boolean shouldTick = false;

    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    /**
     * Instantiates a new Wheel barrow te.
     */
    public WheelBarrowTE() {
        super(SWEMTileEntities.WHEEL_BARROW_TILE_ENTITY.get());
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        return super.save(compound);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        CompoundNBT layers = new CompoundNBT();
        this.itemHandler.getStackInSlot(0).save(layers);
        nbt.put("layers", layers);
        return this.save(nbt);
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        if (tag.contains("layers")) {
            this.itemHandler.setStackInSlot(0, ItemStack.of((CompoundNBT) tag.get("layers")));
        }
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.itemHandler.getStackInSlot(0).save(nbt);

        return new SUpdateTileEntityPacket(this.getBlockPos(), 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.itemHandler.setStackInSlot(0, ItemStack.of(pkt.getTag()));
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        super.load(state, nbt);
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    /**
     * Create handler item stack handler.
     *
     * @return the item stack handler
     */
    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return stack.getItem() instanceof ShavingsItem.SoiledShavingsItem;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 8;
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                if (!this.isItemValid(slot, stack)) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    /**
     * invalidates a tile entity
     */
    @Override
    public void setRemoved() {
        super.setRemoved();
        if (itemHandler != null) {
            handler.invalidate();
        }
    }

    /**
     * Drop items.
     */
    public void dropItems() {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            if (this.itemHandler.getStackInSlot(i) != ItemStack.EMPTY) {
                ItemEntity entity =
                        new ItemEntity(
                                this.level,
                                this.getBlockPos().getX(),
                                this.getBlockPos().getY(),
                                this.getBlockPos().getZ(),
                                this.itemHandler.getStackInSlot(i));
                Random RANDOM = this.level.getRandom();
                entity.setDeltaMovement(
                        RANDOM.nextGaussian() * (double) 0.05F,
                        RANDOM.nextGaussian() * (double) 0.05F + (double) 0.2F,
                        RANDOM.nextGaussian() * (double) 0.05F);
                this.level.addFreshEntity(entity);
                this.itemHandler.setStackInSlot(i, ItemStack.EMPTY);
            }
        }
    }

    /**
     * Reset timer.
     */
    public void resetTimer() {
        this.timer = 0;
        this.shouldTick = false;
    }

    /**
     * Start ticking.
     */
    public void startTicking() {
        this.shouldTick = true;
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide) {
            if (shouldTick) {
                this.timer++;
                if (this.timer % 20 == 0) {
                    this.resetBlockStateLevel();
                    this.dropCompost();
                    this.resetTimer();
                }
            }
        }
    }

    /**
     * Reset block state level.
     */
    private void resetBlockStateLevel() {
        BlockState state = this.level.getBlockState(this.getBlockPos());
        this.level.setBlock(this.getBlockPos(), state.setValue(WheelBarrowBlock.LEVEL, 0), 3);
    }

    /**
     * Drop compost.
     */
    private void dropCompost() {
        this.itemHandler.setStackInSlot(0, ItemStack.EMPTY);
        ItemStack stack = new ItemStack(SWEMItems.BONE_MEAL_COMPOST.get());
        ItemEntity itemEntity =
                new ItemEntity(
                        this.level,
                        this.getBlockPos().getX(),
                        this.getBlockPos().getY(),
                        this.getBlockPos().getZ(),
                        stack);
        this.level.addFreshEntity(itemEntity);
    }
}
