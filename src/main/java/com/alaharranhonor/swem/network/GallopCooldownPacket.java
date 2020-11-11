package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class GallopCooldownPacket {
	private int cooldown;

	private boolean failed;

	public GallopCooldownPacket(int cooldown) {
		this.cooldown = cooldown;
		this.failed = false;
	}

	public GallopCooldownPacket(boolean failed) {
		this.failed = failed;
	}

	public static GallopCooldownPacket decode(ByteBuf buf) {
		try {
			int cooldown = buf.readInt();
			return new GallopCooldownPacket(cooldown);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("GallopCooldownPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new GallopCooldownPacket(true);
		}
	}

	public static void encode(GallopCooldownPacket msg, PacketBuffer buffer) {
		buffer.writeInt(msg.cooldown);
	}

	public static void handle(GallopCooldownPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Minecraft.getInstance().player.sendStatusMessage(new StringTextComponent("Gallop is still on cooldown. " + msg.cooldown + "s remaining."),true);
		});
		ctx.get().setPacketHandled(true);
	}
}
