package com.alaharranhonor.swem.tileentity;

import com.alaharranhonor.swem.util.registry.SWEMTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

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
	public CompoundNBT save(CompoundNBT compound) {
		if (this.controllerPos != null) {
			compound.putIntArray("controller", new int[] {controllerPos.getX(), controllerPos.getY(), controllerPos.getZ()});
		}
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		if (nbt.contains("controller")) {
			int[] pos = nbt.getIntArray("controller");
			this.setControllerPos(new BlockPos(pos[0], pos[1], pos[2]));

		}
		super.load(state, nbt);
	}

}
