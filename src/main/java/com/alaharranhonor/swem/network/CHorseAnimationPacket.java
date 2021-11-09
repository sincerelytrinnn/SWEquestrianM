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

import java.util.function.Supplier;

public class CHorseAnimationPacket {
	private int entityID;
	private int action;

	private boolean failed;

	public CHorseAnimationPacket(int entityID, int action) {
		this.entityID = entityID;
		this.action = action;
		this.failed = false;
	}

	public CHorseAnimationPacket(boolean failed) {
		this.failed = failed;
	}

	public static CHorseAnimationPacket decode(ByteBuf buf) {
		try {
			int entityID = buf.readInt();
			int action = buf.readInt();
			return new CHorseAnimationPacket( entityID, action);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("CHorseAnimationPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new CHorseAnimationPacket(true);
		}
	}

	public static void encode(CHorseAnimationPacket msg, PacketBuffer buffer) {
		buffer.writeInt(msg.entityID);
		buffer.writeInt(msg.action);
	}

	public static void handle(CHorseAnimationPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ClientPlayerEntity player = Minecraft.getInstance().player;
			Entity entity = player.level.getEntity(msg.entityID);
			if (!(entity instanceof SWEMHorseEntityBase)) {
				return;
			}
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;

			switch (msg.action) {
				case 1: {
					horse.standAnimationVariant = 1;
					horse.standAnimationTick = 42;
					break;
				}
				case 2: {
					horse.standAnimationVariant = 2;
					horse.standAnimationTick = 42;
					break;
				}

			}
		});
		ctx.get().setPacketHandled(true);
	}
}
