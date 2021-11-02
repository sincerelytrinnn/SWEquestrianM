package com.alaharranhonor.swem.tileentity;

import com.alaharranhonor.swem.util.registry.SWEMTileEntities;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class SWEMSignTE extends SignTileEntity {


	@Override
	public TileEntityType<?> getType() {
		return SWEMTileEntities.SWEM_SIGN.get();
	}
}
