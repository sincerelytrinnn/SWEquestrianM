package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.container.CantazariteAnvilContainer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SharedConstants;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RenameItemPacket {

	private String name;

	private boolean failed;

	public RenameItemPacket(String name) {
		this.name = name;
		this.failed = false;
	}

	public RenameItemPacket(boolean failed) {
		this.failed = failed;
	}

	public static RenameItemPacket decode(ByteBuf buf) {
		try {
			String name = ((PacketBuffer) buf).readString(32767);
			return new RenameItemPacket(name);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("RenameItemPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new RenameItemPacket(true);
		}
	}

	public static void encode(RenameItemPacket msg, PacketBuffer buffer) {
		buffer.writeString(msg.name);
	}

	public static void handle(RenameItemPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {

			if (ctx.get().getSender().openContainer instanceof CantazariteAnvilContainer) {
				CantazariteAnvilContainer container = (CantazariteAnvilContainer)ctx.get().getSender().openContainer;
				String s = SharedConstants.filterAllowedCharacters(msg.name);
				if (s.length() <= 35) {
					container.updateItemName(s);
				}
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
