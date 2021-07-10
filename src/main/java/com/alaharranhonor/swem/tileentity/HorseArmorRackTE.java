package com.alaharranhonor.swem.tileentity;

import com.alaharranhonor.swem.items.SWEMHorseArmorItem;
import com.alaharranhonor.swem.items.tack.AdventureSaddleItem;
import com.alaharranhonor.swem.util.registry.SWEMTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.Random;

public class HorseArmorRackTE extends TileEntity implements IAnimatable {
	private AnimationFactory factory = new AnimationFactory(this);
	public ItemStackHandler itemHandler = createHandler();

	private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

	public HorseArmorRackTE() {
		super(SWEMTileEntities.HORSE_ARMOR_RACK_TILE_ENTITY.get());
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		compound.put("inv", itemHandler.serializeNBT());
		return super.save(compound);
	}

	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT nbt = new CompoundNBT();
		CompoundNBT armor = new CompoundNBT();
		CompoundNBT saddle = new CompoundNBT();
		this.itemHandler.getStackInSlot(0).save(armor);
		this.itemHandler.getStackInSlot(1).save(saddle);
		nbt.put("armor", armor);
		nbt.put("saddle", saddle);
		return this.save(nbt);
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		if (tag.contains("armor")) {
			this.itemHandler.setStackInSlot(0, ItemStack.of(tag.getCompound("armor")));
		}
		if (tag.contains("saddle")) {
			this.itemHandler.setStackInSlot(1, ItemStack.of(tag.getCompound("saddle")));
		}
	}

	@Nullable
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT nbt = new CompoundNBT();
		CompoundNBT armor = new CompoundNBT();
		CompoundNBT saddle = new CompoundNBT();
		this.itemHandler.getStackInSlot(0).save(armor);
		this.itemHandler.getStackInSlot(1).save(saddle);
		nbt.put("armor", armor);
		nbt.put("saddle", saddle);
		return new SUpdateTileEntityPacket(this.getBlockPos(), 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		CompoundNBT tag = pkt.getTag();
		if (tag.contains("armor")) {
			this.itemHandler.setStackInSlot(0, ItemStack.of(tag.getCompound("armor")));
		}
		if (tag.contains("saddle")) {
			this.itemHandler.setStackInSlot(1, ItemStack.of(tag.getCompound("saddle")));
		}
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

	private ItemStackHandler createHandler() {
		return new ItemStackHandler(2) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				if (slot == 0) {
					return stack.getItem() instanceof SWEMHorseArmorItem;
				} else {
					return stack.getItem() instanceof AdventureSaddleItem;
				}
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

	public void dropItems() {
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			if (this.itemHandler.getStackInSlot(i) != ItemStack.EMPTY) {
				ItemEntity entity = new ItemEntity(this.level, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), this.itemHandler.getStackInSlot(i));
				Random RANDOM = this.level.getRandom();
				entity.setDeltaMovement(RANDOM.nextGaussian() * (double)0.05F, RANDOM.nextGaussian() * (double)0.05F + (double)0.2F, RANDOM.nextGaussian() * (double)0.05F);
				this.level.addFreshEntity(entity);
				this.itemHandler.setStackInSlot(i, ItemStack.EMPTY);
			}
		}
	}




	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}
