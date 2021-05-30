package com.alaharranhonor.swem.network.jumps;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.container.JumpContainer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CAddLayerPacket {
	private BlockPos controllerPos;
	private int layerToAdd;
	private boolean failed;

	public CAddLayerPacket(BlockPos controllerPos, int layerToAdd) {
		this.controllerPos = controllerPos;
		this.layerToAdd = layerToAdd;
		this.failed = false;
	}

	public CAddLayerPacket(boolean failed) {
		this.failed = failed;
	}

	public static CAddLayerPacket decode(ByteBuf buf) {
		try {
			BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
			int layerToAdd = ((PacketBuffer) buf).readVarInt();
			return new CAddLayerPacket(controllerPos, layerToAdd);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("CAddLayerPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new CAddLayerPacket(true);
		}
	}

	public static void encode(CAddLayerPacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.controllerPos);
		buffer.writeVarInt(msg.layerToAdd);
	}

	public static void handle(CAddLayerPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Container container = ctx.get().getSender().openContainer;
			if (container instanceof JumpContainer) {
				JumpContainer jumpContainer = (JumpContainer) container;
				jumpContainer.controller.addLayer(msg.layerToAdd);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
