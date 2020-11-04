package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class UpdateHorseInventoryMessage {
	private int entityId;
	private int slotIndex;
	private ItemStack stack;

	private boolean failed;

	public UpdateHorseInventoryMessage(int entityId, int slotIndex, ItemStack stack) {
		this.entityId = entityId;
		this.slotIndex = slotIndex;
		this.stack = stack;
		this.failed = false;
	}

	public UpdateHorseInventoryMessage(boolean failed) {
		this.failed = failed;
	}

	public static UpdateHorseInventoryMessage decode(ByteBuf buf) {
		try {
			int entityId = buf.readInt();
			int slotIndex = buf.readInt();
			ItemStack stack = ((PacketBuffer) buf).readItemStack();
			return new UpdateHorseInventoryMessage(entityId, slotIndex, stack);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("UpdateHorseInventoryMessage: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new UpdateHorseInventoryMessage(true);
		}
	}

	public static void encode(UpdateHorseInventoryMessage msg, PacketBuffer buf) {
		buf.writeInt(msg.entityId);
		buf.writeInt(msg.slotIndex);
		buf.writeItemStack(msg.stack);
	}

	public static void handle(UpdateHorseInventoryMessage msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);
			if (entity instanceof SWEMHorseEntityBase) {
				SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
				horse.getHorseInventory().setInventorySlotContents(msg.slotIndex, msg.stack);
			}
		});
		ctx.get().setPacketHandled(true);
	}


}
