package com.alaharranhonor.swem.tileentity;

import com.alaharranhonor.swem.blocks.LockerBlock;
import com.alaharranhonor.swem.blocks.TackBoxBlock;
import com.alaharranhonor.swem.container.LockerContainer;
import com.alaharranhonor.swem.container.TackBoxContainer;
import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
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
	public int getSizeInventory() {
		return 54;
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		if (!this.checkLootAndWrite(compound)) {
			ItemStackHelper.saveAllItems(compound, this.lockerContents);
		}
		return compound;
	}



	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		this.lockerContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		if (!this.checkLootAndRead(nbt)) {
			ItemStackHelper.loadAllItems(nbt, this.lockerContents);
		}

	}

	@Override
	public boolean receiveClientEvent(int id, int type) {
		if (id == 1) {
			this.numPlayersUsing = type;
			return true;
		} else {
			return super.receiveClientEvent(id, type);
		}
	}

	@Override
	public void openInventory(PlayerEntity player) {
		if (!player.isSpectator()) {
			if (this.numPlayersUsing < 0) {
				this.numPlayersUsing = 0;
			}

			++this.numPlayersUsing;
			this.onOpenOrClose();
		}
	}

	@Override
	public void closeInventory(PlayerEntity player) {
		if (!player.isSpectator()) {
			--this.numPlayersUsing;
			this.onOpenOrClose();
		}
	}

	protected void onOpenOrClose() {
		Block block = this.getBlockState().getBlock();
		if (block instanceof LockerBlock) {
			this.world.addBlockEvent(this.pos, block, 1, this.numPlayersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, block);
		}
	}

	public static void swapContents(LockerTE te, LockerTE otherTe) {
		NonNullList<ItemStack> list = te.getItems();
		te.setItems(otherTe.getItems());
		otherTe.setItems(list);
	}

	@Override
	public void updateContainingBlockInfo() {
		super.updateContainingBlockInfo();
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
	public void remove() {
		super.remove();
		if (itemHandler != null) {
			itemHandler.invalidate();
		}
	}

	private IItemHandlerModifiable createHandler() {
		return new InvWrapper(this);
	}


}
