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
import net.minecraft.network.PacketBuffer;
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

import javax.annotation.Nullable;
import java.util.UUID;

public class TackBoxTE extends LockableLootTileEntity implements INamedContainerProvider {

	private NonNullList<ItemStack> tackContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
	private int numPlayersUsing;
	private IItemHandlerModifiable items = createHandler();
	private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);
	private int entityId;
	public TackBoxTE() {
		super(SWEMTileEntities.TACK_BOX_TILE_ENTITY.get());
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("Tackbox");
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.tack_box");
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
		return new TackBoxContainer(id, player, this, 0);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		if (!this.checkLootAndWrite(compound)) {
			ItemStackHelper.saveAllItems(compound, this.tackContents);
		}
		compound.putInt("horseID", entityId);
		return compound;
	}

	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		this.tackContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		if (!this.checkLootAndRead(nbt)) {
			ItemStackHelper.loadAllItems(nbt, this.tackContents);
		}
		entityId = nbt.getInt("horseID");
	}

	private void playSound(SoundEvent event) {
		double dx = (double) this.pos.getX() + 0.5d;
		double dy = (double) this.pos.getY() + 0.5d;
		double dz = (double) this.pos.getZ() + 0.5d;

		this.world.playSound((PlayerEntity)null, dx, dy, dz, event, SoundCategory.BLOCKS, 0.5f, this.world.rand.nextFloat() * 0.1f + 0.9f);
	}

	/**
	 * See {@link Block#eventReceived} for more information. This must return true serverside before it is called
	 * clientside.
	 *
	 * @param id
	 * @param type
	 */
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
		if (block instanceof TackBoxBlock) {
			this.world.addBlockEvent(this.pos, block, 1, this.numPlayersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, block);
		}
	}

	public static int getPlayersUsing(IBlockReader reader, BlockPos pos) {
		BlockState state = reader.getBlockState(pos);
		if (state.hasTileEntity()) {
			TileEntity tileEntity = reader.getTileEntity(pos);
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

	private IItemHandlerModifiable createHandler() {
		return new InvWrapper(this);
	}

	/**
	 * invalidates a tile entity
	 */
	@Override
	public void remove() {
		super.remove();
		if (itemHandler != null) {
			itemHandler.invalidate();
		}
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return 30;
	}


}
