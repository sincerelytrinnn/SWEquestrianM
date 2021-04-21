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

public class CDestroyPacket {
	private BlockPos controllerPos;
	private boolean failed;

	public CDestroyPacket(BlockPos controllerPos) {
		this.controllerPos = controllerPos;
		this.failed = false;
	}

	public CDestroyPacket(boolean failed) {
		this.failed = failed;
	}

	public static CDestroyPacket decode(ByteBuf buf) {
		try {
			BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
			return new CDestroyPacket(controllerPos);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("CDestroyPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new CDestroyPacket(true);
		}
	}

	public static void encode(CDestroyPacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.controllerPos);
	}

	public static void handle(CDestroyPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {

			Container container = ctx.get().getSender().openContainer;
			if (container instanceof JumpContainer) {
				JumpContainer jumpContainer = (JumpContainer) container;
				jumpContainer.controller.remove();
			}

		});
		ctx.get().setPacketHandled(true);
	}
}
