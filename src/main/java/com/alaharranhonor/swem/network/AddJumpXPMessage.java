package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AddJumpXPMessage {

	private float xpToAdd;

	private int entityID;

	private boolean failed;

	public AddJumpXPMessage(float xpToAdd, int entityID) {
		this.xpToAdd = xpToAdd;
		this.entityID = entityID;
		this.failed = false;
	}

	public AddJumpXPMessage(boolean failed) {
		this.failed = failed;
	}

	public static AddJumpXPMessage decode(ByteBuf buf) {
		try {
			float xpToAdd = buf.readFloat();
			int entityID = buf.readInt();
			return new AddJumpXPMessage(xpToAdd, entityID);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("AddJumpXPMessage: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new AddJumpXPMessage(true);
		}
	}

	public static void encode(AddJumpXPMessage msg, PacketBuffer buffer) {
		buffer.writeFloat(msg.xpToAdd);
		buffer.writeInt(msg.entityID);
	}

	public static void handle(AddJumpXPMessage msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			Entity entity = player.world.getEntityByID(msg.entityID);
			if (entity instanceof SWEMHorseEntityBase) {
				SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
				horse.progressionManager.getJumpLeveling().addXP(msg.xpToAdd);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
