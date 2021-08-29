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
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class SHorseJumpPacket {
	private int entityID;
	private float jumpHeight;

	private boolean failed;

	public SHorseJumpPacket(int entityID, float jumpHeight) {
		this.entityID = entityID;
		this.jumpHeight = jumpHeight;
		this.failed = false;
	}

	public SHorseJumpPacket(boolean failed) {
		this.failed = failed;
	}

	public static SHorseJumpPacket decode(ByteBuf buf) {
		try {
			int entityID = buf.readInt();
			float jumpHeight = buf.readFloat();
			return new SHorseJumpPacket(entityID, jumpHeight);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("CHorseJumpPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new SHorseJumpPacket(true);
		}
	}

	public static void encode(SHorseJumpPacket msg, PacketBuffer buffer) {
		buffer.writeInt(msg.entityID);
		buffer.writeFloat(msg.jumpHeight);
	}

	public static void handle(SHorseJumpPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ClientPlayerEntity player = Minecraft.getInstance().player;
			Entity entity = player.level.getEntity(msg.entityID);
			if (!(entity instanceof SWEMHorseEntityBase)) {
				return;
			}
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;

			horse.jumpHeight = msg.jumpHeight;
		});
		ctx.get().setPacketHandled(true);
	}
}
