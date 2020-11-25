package com.alaharranhonor.swem.tileentity;

import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class OneSaddleRackTE extends TileEntity implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);
	private ItemStack saddle = ItemStack.EMPTY;
	private int numPlayersUsing;

	public OneSaddleRackTE() {
		super(SWEMTileEntities.ONE_SADDLE_RACK_TILE_ENTITY.get());
	}

	public ItemStack getItems() {
		return this.saddle;
	}

	public void setItems(ItemStack itemsIn) {
		this.saddle = itemsIn;
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);

		CompoundNBT nbt = new CompoundNBT();
		if (!saddle.isEmpty()) {
			saddle.write(nbt);
		}
		compound.put("saddle", nbt);
		return compound;
	}




	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		if (nbt.contains("saddle")) {
			saddle = ItemStack.read((CompoundNBT) nbt.get("saddle"));
		}
	}

	public static void swapContents(TackBoxTE te, TackBoxTE otherTe) {
		NonNullList<ItemStack> list = te.getItems();
		te.setItems(otherTe.getItems());
		otherTe.setItems(list);
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
