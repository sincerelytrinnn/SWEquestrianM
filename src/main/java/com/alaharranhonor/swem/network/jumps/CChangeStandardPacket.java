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

public class CChangeStandardPacket {
	private BlockPos controllerPos;
	private boolean rightClick;
	private boolean failed;

	public CChangeStandardPacket(BlockPos controllerPos, boolean rightClick) {
		this.controllerPos = controllerPos;
		this.rightClick = rightClick;
		this.failed = false;
	}

	public CChangeStandardPacket(boolean failed) {
		this.failed = failed;
	}

	public static CChangeStandardPacket decode(ByteBuf buf) {
		try {
			BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
			boolean rightClick = buf.readBoolean();
			return new CChangeStandardPacket(controllerPos, rightClick);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("CChangeStandardPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new CChangeStandardPacket(true);
		}
	}

	public static void encode(CChangeStandardPacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.controllerPos);
		buffer.writeBoolean(msg.rightClick);
	}

	public static void handle(CChangeStandardPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Container container = ctx.get().getSender().containerMenu;
			if (container instanceof JumpContainer) {
				JumpContainer jumpContainer = (JumpContainer) container;
				if (msg.rightClick) {
					jumpContainer.controller.changeStandardBackwards();
				} else {
					jumpContainer.controller.changeStandardForward();
				}
			}

		});
		ctx.get().setPacketHandled(true);
	}
}
