package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.initialization.SWEMItems;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class HorseStateChange {

	private int action;
	private int entityID;

	private boolean failed;

	public HorseStateChange(int action, int entityID) {
		this.action = action;
		this.entityID = entityID;
		this.failed = false;
	}

	public HorseStateChange(boolean failed) {
		this.failed = failed;
	}

	public static HorseStateChange decode(ByteBuf buf) {
		try {
			int action = buf.readInt();
			int entityID = buf.readInt();
			return new HorseStateChange(action, entityID);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("HorseStateChange: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new HorseStateChange(true);
		}
	}

	public static void encode(HorseStateChange msg, PacketBuffer buffer) {
		buffer.writeInt(msg.action);
		buffer.writeInt(msg.entityID);
	}

	public static void handle(HorseStateChange msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) ctx.get().getSender().world.getEntityByID(msg.entityID);
			switch (msg.action) {

				case 0: {
					horse.getNeeds().getThirst().incrementState();
					break;
				}
				case 1: {
					horse.progressionManager.getAffinityLeveling().desensitize(new ItemStack(SWEMItems.BELLS.get()));
					break;
				}
				case 2: {
					horse.progressionManager.getAffinityLeveling().desensitize(new ItemStack(SWEMItems.HOOLAHOOP.get()));
					break;
				}
				case 3: {
					horse.progressionManager.getAffinityLeveling().desensitize(new ItemStack(SWEMItems.POMPOM.get()));
					break;
				}
				case 4: {
					horse.progressionManager.getAffinityLeveling().desensitize(new ItemStack(SWEMItems.SHOPPING_BAG.get()));
					break;
				}
				case 5: {
					horse.progressionManager.getAffinityLeveling().desensitize(new ItemStack(SWEMItems.TARP.get()));
					break;
				}
			}

		});
		ctx.get().setPacketHandled(true);
	}
}