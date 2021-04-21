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

public class CChangeColorPacket {
	private BlockPos controllerPos;
	private int layerToChange;
	private boolean failed;

	public CChangeColorPacket(BlockPos controllerPos, int layerToChange) {
		this.controllerPos = controllerPos;
		this.layerToChange = layerToChange;
		this.failed = false;
	}

	public CChangeColorPacket(boolean failed) {
		this.failed = failed;
	}

	public static CChangeColorPacket decode(ByteBuf buf) {
		try {
			BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
			int layerToChange = ((PacketBuffer) buf).readVarInt();
			return new CChangeColorPacket(controllerPos, layerToChange);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("CChangeColorPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new CChangeColorPacket(true);
		}
	}

	public static void encode(CChangeColorPacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.controllerPos);
		buffer.writeVarInt(msg.layerToChange);
	}

	public static void handle(CChangeColorPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Container container = ctx.get().getSender().openContainer;
			if (container instanceof JumpContainer) {
				JumpContainer jumpContainer = (JumpContainer) container;
				jumpContainer.controller.changeColorVariant(msg.layerToChange);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
