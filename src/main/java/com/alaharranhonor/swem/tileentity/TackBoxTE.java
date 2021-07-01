package com.alaharranhonor.swem.tileentity;

import com.alaharranhonor.swem.blocks.TackBoxBlock;
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
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
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
import java.util.UUID;

public class TackBoxTE extends LockableLootTileEntity implements INamedContainerProvider, IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);
	private NonNullList<ItemStack> tackContents = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
	private int numPlayersUsing;
	private IItemHandlerModifiable items = createHandler();
	private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);

	public TackBoxTE() {
		super(SWEMTileEntities.TACK_BOX_TILE_ENTITY.get());
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.swem.tack_box");
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.swem.tack_box");
	}

	@Override
	public NonNullList<ItemStack> getItems() {
		return this.tackContents;
	}

	@Override
	public void setItems(NonNullList<ItemStack> itemsIn) {
		this.tackContents = itemsIn;
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new TackBoxContainer(id, player, this);
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		super.save(compound);
		if (!this.trySaveLootTable(compound)) {
			ItemStackHelper.saveAllItems(compound, this.tackContents);
		}
		return compound;
	}



	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		this.tackContents = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(nbt)) {
			ItemStackHelper.loadAllItems(nbt, this.tackContents);
		}

	}

	private void playSound(SoundEvent event) {
		double dx = (double) this.getBlockPos().getX() + 0.5d;
		double dy = (double) this.getBlockPos().getY() + 0.5d;
		double dz = (double) this.getBlockPos().getZ() + 0.5d;

		this.level.playSound((PlayerEntity)null, dx, dy, dz, event, SoundCategory.BLOCKS, 0.5f, this.level.random.nextFloat() * 0.1f + 0.9f);
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
		if (block instanceof TackBoxBlock) {
			this.level.blockEvent(this.getBlockPos(), block, 1, this.numPlayersUsing);
			this.level.updateNeighborsAt(this.getBlockPos(), block);
		}
	}

	public static int getPlayersUsing(IBlockReader reader, BlockPos pos) {
		BlockState state = reader.getBlockState(pos);
		if (state.hasTileEntity()) {
			TileEntity tileEntity = reader.getBlockEntity(pos);
			if (tileEntity instanceof TackBoxTE) {
				return ((TackBoxTE) tileEntity).numPlayersUsing;
			}
		}
		return 0;

	}

	public static void swapContents(TackBoxTE te, TackBoxTE otherTe) {
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

	private IItemHandlerModifiable createHandler() {
		return new InvWrapper(this);
	}

	/**
	 * invalidates a tile entity
	 */
	@Override
	public void setRemoved() {
		super.setRemoved();
		if (itemHandler != null) {
			itemHandler.invalidate();
		}
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getContainerSize() {
		return 30;
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
