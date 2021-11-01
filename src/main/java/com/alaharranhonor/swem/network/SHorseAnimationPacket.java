package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SHorseAnimationPacket {
	private int entityID;
	private int action;

	private boolean failed;

	public SHorseAnimationPacket(int entityID, int action) {
		this.entityID = entityID;
		this.action = action;
		this.failed = false;
	}

	public SHorseAnimationPacket(boolean failed) {
		this.failed = failed;
	}

	public static SHorseAnimationPacket decode(ByteBuf buf) {
		try {
			int entityID = buf.readInt();
			int action = buf.readInt();
			return new SHorseAnimationPacket( entityID, action);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("SHorseAnimationPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new SHorseAnimationPacket(true);
		}
	}

	public static void encode(SHorseAnimationPacket msg, PacketBuffer buffer) {
		buffer.writeInt(msg.entityID);
		buffer.writeInt(msg.action);
	}

	public static void handle(SHorseAnimationPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ClientPlayerEntity player = Minecraft.getInstance().player;
			Entity entity = player.level.getEntity(msg.entityID);
			if (!(entity instanceof SWEMHorseEntityBase)) {
				return;
			}
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;

			switch (msg.action) {
				case 1: { // Add friend
					horse.standAnimationVariant = 1;
					horse.standAnimationTick = 42;
					break;
				}
				case 2: { // Remove Friend
					horse.standAnimationVariant = 2;
					horse.standAnimationTick = 42;
					break;
				}

			}
		});
		ctx.get().setPacketHandled(true);
	}
}
