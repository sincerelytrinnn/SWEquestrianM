package com.alaharranhonor.swem.network.jumps;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.container.JumpContainer;
import com.alaharranhonor.swem.tileentity.JumpTE;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CChangeLayerPacket {
	private BlockPos controllerPos;
	private int layerToChange;
	private boolean failed;

	public CChangeLayerPacket(BlockPos controllerPos, int layerToChange) {
		this.controllerPos = controllerPos;
		this.layerToChange = layerToChange;
		this.failed = false;
	}

	public CChangeLayerPacket(boolean failed) {
		this.failed = failed;
	}

	public static CChangeLayerPacket decode(ByteBuf buf) {
		try {
			BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
			int layerToChange = ((PacketBuffer) buf).readVarInt();
			return new CChangeLayerPacket(controllerPos, layerToChange);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("CChangeLayerPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new CChangeLayerPacket(true);
		}
	}

	public static void encode(CChangeLayerPacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.controllerPos);
		buffer.writeVarInt(msg.layerToChange);
	}

	public static void handle(CChangeLayerPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Container container = ctx.get().getSender().openContainer;
			if (container instanceof JumpContainer) {
				JumpContainer jumpContainer = (JumpContainer) container;
				jumpContainer.controller.changeLayer(msg.layerToChange);
			}

		});
		ctx.get().setPacketHandled(true);
	}
}
