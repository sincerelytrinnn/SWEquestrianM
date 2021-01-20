package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ClientStatusMessagePacket {
	private int action;
	private int argLength;
	private ArrayList<String> args;

	private boolean failed;

	public ClientStatusMessagePacket(int action, int argLength, ArrayList<String> args) {
		this.action = action;
		this.argLength = argLength;
		this.args = args;
		this.failed = false;
	}

	public ClientStatusMessagePacket(boolean failed) {
		this.failed = failed;
	}

	public static ClientStatusMessagePacket decode(ByteBuf buf) {
		try {
			int action = buf.readInt();
			int argLength = buf.readInt();
			ArrayList<String> args = new ArrayList<>();
			for (int i = 0; i < argLength; i++) {
				args.add(((PacketBuffer) buf).readString());
			}
			return new ClientStatusMessagePacket(action, argLength, args);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("GallopCooldownPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new ClientStatusMessagePacket(true);
		}
	}

	public static void encode(ClientStatusMessagePacket msg, PacketBuffer buffer) {
		buffer.writeInt(msg.action);
		buffer.writeInt(msg.argLength);
		if (msg.args.size() > 0) {
			msg.args.forEach(buffer::writeString);
		}
	}

	public static void handle(ClientStatusMessagePacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			switch (msg.action) {
				case 0: {
					Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("swem.horse.status.gallop_cooldown").appendString( msg.args.get(0) + "s"),true);
					break;
				}
				case 1: {
					Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("swem.horse.status.too_thirsty_to_canter"), true);
					break;
				}
			}

		});
		ctx.get().setPacketHandled(true);
	}
}
