package com.alaharranhonor.swem.tileentity;

import com.alaharranhonor.swem.blocks.ShavingsItem;
import com.alaharranhonor.swem.blocks.WheelBarrowBlock;
import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.texture.ITickable;
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
	public WheelBarrowTE() {
		super(SWEMTileEntities.WHEEL_BARROW_TILE_ENTITY.get());
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("inv", itemHandler.serializeNBT());
		return super.write(compound);
	}

	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT nbt = new CompoundNBT();
		CompoundNBT layers = new CompoundNBT();
		this.itemHandler.getStackInSlot(0).write(layers);
		nbt.put("layers", layers);
		return this.write(nbt);
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		if (tag.contains("layers")) {
			this.itemHandler.setStackInSlot(0, ItemStack.read((CompoundNBT) tag.get("layers")));
		}
	}

	@Nullable
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT nbt = new CompoundNBT();
		this.itemHandler.getStackInSlot(0).write(nbt);

		return new SUpdateTileEntityPacket(this.getPos(), 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		this.itemHandler.setStackInSlot(0, ItemStack.read(pkt.getNbtCompound()));
	}

	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		itemHandler.deserializeNBT(nbt.getCompound("inv"));
		super.read(state, nbt);
	}


	@Nullable
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return handler.cast();
		}
		return super.getCapability(cap, side);
	}

	private ItemStackHandler createHandler() {
		return new ItemStackHandler(1) {
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
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
	public void remove() {
		super.remove();
		if (itemHandler != null) {
			handler.invalidate();
		}

	}

	public void dropItems() {
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			if (this.itemHandler.getStackInSlot(i) != ItemStack.EMPTY) {
				ItemEntity entity = new ItemEntity(this.world, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), this.itemHandler.getStackInSlot(i));
				Random RANDOM = this.world.getRandom();
				entity.setMotion(RANDOM.nextGaussian() * (double)0.05F, RANDOM.nextGaussian() * (double)0.05F + (double)0.2F, RANDOM.nextGaussian() * (double)0.05F);
				this.world.addEntity(entity);
				this.itemHandler.setStackInSlot(i, ItemStack.EMPTY);
			}
		}
	}

	public void resetTimer() {
		this.timer = 0;
		this.shouldTick = false;
	}

	public void startTicking() {
		this.shouldTick = true;
	}

	@Override
	public void tick() {
		if (!this.world.isRemote) {
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

	private void resetBlockStateLevel() {
		BlockState state = this.world.getBlockState(this.pos);
		this.world.setBlockState(pos, state.with(WheelBarrowBlock.LEVEL, 0));
	}

	private void dropCompost() {
		this.itemHandler.setStackInSlot(0, ItemStack.EMPTY);
		ItemStack stack = new ItemStack(SWEMBlocks.WET_COMPOST_ITEM.get());
		ItemEntity itemEntity = new ItemEntity(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ(), stack);
		this.world.addEntity(itemEntity);

	}
}
