package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.HorseFlightController;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class HorseFlightPacket {
	private int action;
	private int entityID;

	private boolean failed;

	public HorseFlightPacket(int action, int entityID) {
		this.action = action;
		this.entityID = entityID;
		this.failed = false;
	}

	public HorseFlightPacket(boolean failed) {
		this.failed = failed;
	}

	public static HorseFlightPacket decode(ByteBuf buf) {
		try {
			int action = buf.readInt();
			int entityID = buf.readInt();
			return new HorseFlightPacket(action, entityID);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("HorseFlightPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new HorseFlightPacket(true);
		}
	}

	public static void encode(HorseFlightPacket msg, PacketBuffer buffer) {
		buffer.writeInt(msg.action);
		buffer.writeInt(msg.entityID);
	}

	public static void handle(HorseFlightPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) ctx.get().getSender().level.getEntity(msg.entityID);
			switch (msg.action) {
				case 0: { // set floating
					horse.getEntityData().set(HorseFlightController.isFloating, true);
					break;
				}
				case 1: {
					horse.getEntityData().set(HorseFlightController.isFloating, false);
					horse.getEntityData().set(HorseFlightController.isAccelerating, true);
					break;
				}
				case 2: {
					horse.getEntityData().set(HorseFlightController.isFloating, false);
					horse.getEntityData().set(HorseFlightController.isAccelerating, false);
					horse.getEntityData().set(HorseFlightController.isSlowingDown, true);
					break;
				}
				case 3: {
					horse.getEntityData().set(HorseFlightController.isFloating, false);
					horse.getEntityData().set(HorseFlightController.isTurningLeft, true);
					horse.getEntityData().set(HorseFlightController.isTurning, true);
					break;
				}
				case 4: {
					horse.getEntityData().set(HorseFlightController.isFloating, false);
					horse.getEntityData().set(HorseFlightController.isTurningLeft, false);
					horse.getEntityData().set(HorseFlightController.isTurning, true);
					break;
				}
				case 5: {
					horse.getEntityData().set(HorseFlightController.isFloating, false);
					horse.getEntityData().set(HorseFlightController.didFlap, true);
					break;
				}

			}

		});
		ctx.get().setPacketHandled(true);
	}
}
