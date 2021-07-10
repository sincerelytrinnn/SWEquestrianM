package com.alaharranhonor.swem.tileentity;

import com.alaharranhonor.swem.container.CantazariteAnvilContainer;
import com.alaharranhonor.swem.util.registry.SWEMTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class CantazariteAnvilTE extends TileEntity implements INamedContainerProvider {

	public ItemStackHandler itemHandler = createHandler();
	private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

	public CantazariteAnvilTE() {
		super(SWEMTileEntities.CANTAZARITE_ANVIL_TILE_ENTITY.get());
	}



	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return handler.cast();
		}
		return super.getCapability(cap, side);
	}


	private ItemStackHandler createHandler() {
		return new ItemStackHandler(3) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				return true;
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

	@Override
	public void setRemoved() {
		super.setRemoved();
		if (itemHandler != null) {
			handler.invalidate();
		}
	}


	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("swem.container.cantazarite_anvil");
	}

	@Nullable
	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return new CantazariteAnvilContainer(p_createMenu_1_, p_createMenu_2_, IWorldPosCallable.create(p_createMenu_3_.getCommandSenderWorld(), this.getBlockPos()));
	}
}
