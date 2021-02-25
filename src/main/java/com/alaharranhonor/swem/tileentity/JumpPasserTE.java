package com.alaharranhonor.swem.tileentity;

import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.network.UpdatePasserController;
import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;

public class JumpPasserTE extends TileEntity {

	public BlockPos controllerPos;

	public JumpPasserTE() {
		super(SWEMTileEntities.JUMP_PASSER_TILE_ENTITY.get());
	}

	public BlockPos getControllerPos() {
		return this.controllerPos;
	}

	public void setControllerPos(BlockPos controllerPos) {
		this.controllerPos = controllerPos;
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {

		compound.putIntArray("controller", new int[] {controllerPos.getX(), controllerPos.getY(), controllerPos.getZ()});
		return super.write(compound);
	}

	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		if (nbt.contains("controller")) {
			int[] pos = nbt.getIntArray("controller");
			this.setControllerPos(new BlockPos(pos[0], pos[1], pos[2])); // World is null when reading data. Hence the NullPointerException

		}
		super.read(state, nbt);
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		int[] pos = tag.getIntArray("controller");
		this.setControllerPos(new BlockPos(pos[0], pos[1], pos[2]));
	}
}
