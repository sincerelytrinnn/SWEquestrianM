package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SendHorseSpeedChange {

	private int action;
	private int entityID;

	private boolean failed;

	public SendHorseSpeedChange(int action, int entityID) {
		this.action = action;
		this.entityID = entityID;
		this.failed = false;
	}

	public SendHorseSpeedChange(boolean failed) {
		this.failed = failed;
	}

	public static SendHorseSpeedChange decode(ByteBuf buf) {
		try {
			int action = buf.readInt();
			int entityID = buf.readInt();
			return new SendHorseSpeedChange(action, entityID);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("SendHorseSpeedChange: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new SendHorseSpeedChange(true);
		}
	}

	public static void encode(SendHorseSpeedChange msg, PacketBuffer buffer) {
		buffer.writeInt(msg.action);
		buffer.writeInt(msg.entityID);
	}

	public static void handle(SendHorseSpeedChange msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			PlayerEntity player = ctx.get().getSender();
			Entity entity = player.world.getEntityByID(msg.entityID);
			if (entity instanceof SWEMHorseEntityBase) {
				SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
				if (msg.action == 1) {
					horse.incrementSpeed();
				} else if (msg.action == 0) {
					horse.decrementSpeed();
				}
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
