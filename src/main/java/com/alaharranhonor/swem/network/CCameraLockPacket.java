package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class CCameraLockPacket {
	private UUID horseUUID;
	private boolean locked;
	private boolean failed;

	public CCameraLockPacket(UUID horseUUID, boolean locked) {
		this.horseUUID = horseUUID;
		this.locked = locked;
		this.failed = false;
	}

	public CCameraLockPacket(boolean failed) {
		this.failed = failed;
	}

	public static CCameraLockPacket decode(ByteBuf buf) {
		try {
			UUID horseUUID = ((PacketBuffer) buf).readUUID();
			boolean locked = buf.readBoolean();

			return new CCameraLockPacket(horseUUID, locked);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("CCameraLockPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new CCameraLockPacket(true);
		}
	}

	public static void encode(CCameraLockPacket msg, PacketBuffer buffer) {
		buffer.writeUUID(msg.horseUUID);
		buffer.writeBoolean(msg.locked);
	}

	public static void handle(CCameraLockPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();

			Entity entity = ((ServerWorld)player.level).getEntity(msg.horseUUID);
			if (!(entity instanceof SWEMHorseEntityBase)) {
				return;
			}
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;

			horse.setCameraLock(msg.locked);
		});
		ctx.get().setPacketHandled(true);
	}
}