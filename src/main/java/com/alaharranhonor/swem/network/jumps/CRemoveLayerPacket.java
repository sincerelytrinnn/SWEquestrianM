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

public class CRemoveLayerPacket {
	private BlockPos controllerPos;
	private int layerToRemove;
	private boolean failed;

	public CRemoveLayerPacket(BlockPos controllerPos, int layerToRemove) {
		this.controllerPos = controllerPos;
		this.layerToRemove = layerToRemove;
		this.failed = false;
	}

	public CRemoveLayerPacket(boolean failed) {
		this.failed = failed;
	}

	public static CRemoveLayerPacket decode(ByteBuf buf) {
		try {
			BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
			int layerToRemove = ((PacketBuffer) buf).readVarInt();
			return new CRemoveLayerPacket(controllerPos, layerToRemove);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("CRemoveLayerPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new CRemoveLayerPacket(true);
		}
	}

	public static void encode(CRemoveLayerPacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.controllerPos);
		buffer.writeVarInt(msg.layerToRemove);
	}

	public static void handle(CRemoveLayerPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Container container = ctx.get().getSender().containerMenu;
			if (container instanceof JumpContainer) {
				JumpContainer jumpContainer = (JumpContainer) container;
				jumpContainer.controller.deleteLayer(msg.layerToRemove);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
