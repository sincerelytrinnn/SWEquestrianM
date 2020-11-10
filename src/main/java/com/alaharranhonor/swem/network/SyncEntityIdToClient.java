package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.tileentity.TackBoxTE;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncEntityIdToClient {

	private int posX;
	private int posY;
	private int posZ;
	private int entityId;
	private boolean failed;

	public SyncEntityIdToClient(int entityID, int posX, int posY, int posZ) {
		this.entityId = entityID;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.failed = false;
	}

	public SyncEntityIdToClient(boolean failed) {
		this.failed = failed;
	}

	public static SyncEntityIdToClient decode(ByteBuf buf) {
		try {
			int entityID = buf.readInt();
			int posX = buf.readInt();
			int posY = buf.readInt();
			int posZ = buf.readInt();
			return new SyncEntityIdToClient(entityID, posX, posY, posZ);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("SyncEntityIdToClient: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new SyncEntityIdToClient(true);
		}
	}

	public static void encode(SyncEntityIdToClient msg, PacketBuffer buffer) {
		buffer.writeInt(msg.entityId);
		buffer.writeInt(msg.posX);
		buffer.writeInt(msg.posY);
		buffer.writeInt(msg.posZ);
	}

	public static void handle(SyncEntityIdToClient msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ClientWorld world = Minecraft.getInstance().world;
			TileEntity tile = world.getTileEntity(new BlockPos(msg.posX, msg.posY, msg.posZ));
			if (tile instanceof TackBoxTE) {
				TackBoxTE te = (TackBoxTE) tile;
				te.getTileData().putInt("horseID", msg.entityId);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
