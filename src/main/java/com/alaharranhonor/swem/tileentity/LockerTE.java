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

import com.alaharranhonor.swem.blocks.LockerBlock;
import com.alaharranhonor.swem.container.LockerContainer;
import com.alaharranhonor.swem.util.registry.SWEMTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class LockerTE extends LockableLootTileEntity implements INamedContainerProvider {

	private NonNullList<ItemStack> lockerContents = NonNullList.withSize(54, ItemStack.EMPTY);
	protected int numPlayersUsing;

	private IItemHandlerModifiable items = createHandler();
	private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);
	private boolean leftSideOpened;




	public LockerTE() {
		super(SWEMTileEntities.LOCKER_TILE_ENTITY.get());
	}

	@Override
	public NonNullList<ItemStack> getItems() {
		return this.lockerContents;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) {
		this.lockerContents = itemsIn;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.swem.tack_box");
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.swem.locker");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new LockerContainer(id, player, this, this.leftSideOpened);
	}

	public void setLeftSideOpened(boolean leftSideOpened) {
		this.leftSideOpened = leftSideOpened;
	}

	@Override
	public int getContainerSize() {
		return 54;
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		super.save(compound);
		if (!this.trySaveLootTable(compound)) {
			ItemStackHelper.saveAllItems(compound, this.lockerContents);
		}
		return compound;
	}



	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		this.lockerContents = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(nbt)) {
			ItemStackHelper.loadAllItems(nbt, this.lockerContents);
		}

	}

	@Override
	public boolean triggerEvent(int id, int type) {
		if (id == 1) {
			this.numPlayersUsing = type;
			return true;
		} else {
			return super.triggerEvent(id, type);
		}
	}

	@Override
	public void startOpen(PlayerEntity player) {
		if (!player.isSpectator()) {
			if (this.numPlayersUsing < 0) {
				this.numPlayersUsing = 0;
			}

			++this.numPlayersUsing;
			this.onOpenOrClose();
		}
	}

	@Override
	public void stopOpen(PlayerEntity player) {
		if (!player.isSpectator()) {
			--this.numPlayersUsing;
			this.onOpenOrClose();
		}
	}

	protected void onOpenOrClose() {
		Block block = this.getBlockState().getBlock();
		if (block instanceof LockerBlock) {
			this.level.blockEvent(this.getBlockPos(), block, 1, this.numPlayersUsing);
			this.level.updateNeighborsAt(this.getBlockPos(), block);
		}
	}

	public static void swapContents(LockerTE te, LockerTE otherTe) {
		NonNullList<ItemStack> list = te.getItems();
		te.setItems(otherTe.getItems());
		otherTe.setItems(list);
	}

	@Override
	public void clearCache() {
		super.clearCache();
		if (this.itemHandler != null) {
			this.itemHandler.invalidate();
			this.itemHandler = null;
		}
	}

	@Nullable
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return itemHandler.cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		if (itemHandler != null) {
			itemHandler.invalidate();
		}
	}

	private IItemHandlerModifiable createHandler() {
		return new InvWrapper(this);
	}


}
