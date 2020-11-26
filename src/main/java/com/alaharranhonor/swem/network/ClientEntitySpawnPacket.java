package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.RopeKnotEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.initialization.SWEMEntities;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MinecartTickableSound;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ClientEntitySpawnPacket {

	private int entityId;
	private double x;
	private double y;
	private double z;
	private int speedX;
	private int speedY;
	private int speedZ;
	private int pitch;
	private int yaw;
	private EntityType<?> type;
	private int data;
	private boolean failed;

	public ClientEntitySpawnPacket(int entityId, double xPos, double yPos, double zPos, float pitch, float yaw, EntityType<?> entityType, int entityData, Vector3d speedVector) {
		this.entityId = entityId;
		this.x = xPos;
		this.y = yPos;
		this.z = zPos;
		this.pitch = MathHelper.floor(pitch * 256.0F / 360.0F);
		this.yaw = MathHelper.floor(yaw * 256.0F / 360.0F);
		this.type = entityType;
		this.data = entityData;
		this.speedX = (int)(MathHelper.clamp(speedVector.x, -3.9D, 3.9D) * 8000.0D);
		this.speedY = (int)(MathHelper.clamp(speedVector.y, -3.9D, 3.9D) * 8000.0D);
		this.speedZ = (int)(MathHelper.clamp(speedVector.z, -3.9D, 3.9D) * 8000.0D);
	}

	public ClientEntitySpawnPacket(Entity entity) {
		this(entity, 0);
	}

	public ClientEntitySpawnPacket(Entity entityIn, int typeIn) {
		this(entityIn.getEntityId(), entityIn.getPosX(), entityIn.getPosY(), entityIn.getPosZ(), entityIn.rotationPitch, entityIn.rotationYaw, entityIn.getType(), typeIn, entityIn.getMotion());
	}

	public ClientEntitySpawnPacket(Entity entity, EntityType<?> entityType, int entityData, BlockPos pos) {
		this(entity.getEntityId(), (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), entity.rotationPitch, entity.rotationYaw, entityType, entityData, entity.getMotion());
	}

	public ClientEntitySpawnPacket(boolean failed) {
		this.failed = failed;
	}

	public static ClientEntitySpawnPacket decode(ByteBuf buf) {
		try {
			int entityId = buf.readInt();
			EntityType type = Registry.ENTITY_TYPE.getByValue(buf.readInt());
			double x = buf.readDouble();
			double y = buf.readDouble();
			double z = buf.readDouble();
			int pitch = buf.readByte();
			int yaw = buf.readByte();
			int data = buf.readInt();
			int speedX = buf.readShort();
			int speedY = buf.readShort();
			int speedZ = buf.readShort();
			return new ClientEntitySpawnPacket(entityId, x, y, z, pitch, yaw, type, data, new Vector3d(speedX, speedY, speedZ));
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("ClientEntitySpawnPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new ClientEntitySpawnPacket(true);
		}
	}

	public static void encode(ClientEntitySpawnPacket msg, PacketBuffer buf) {
		buf.writeInt(msg.entityId);
		buf.writeInt(Registry.ENTITY_TYPE.getId(msg.type));
		buf.writeDouble(msg.x);
		buf.writeDouble(msg.y);
		buf.writeDouble(msg.z);
		buf.writeByte(msg.pitch);
		buf.writeByte(msg.yaw);
		buf.writeInt(msg.data);
		buf.writeShort(msg.speedX);
		buf.writeShort(msg.speedY);
		buf.writeShort(msg.speedZ);
	}

	public static void handle(ClientEntitySpawnPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ClientWorld world = Minecraft.getInstance().world;
			double d0 = msg.x;
			double d1 = msg.y;
			double d2 = msg.z;
			EntityType<?> entityType = msg.type;
			Entity entity;
			if (entityType == SWEMEntities.ROPE_KNOT_ENTITY.get()) {
				entity = new RopeKnotEntity(world, new BlockPos(d0, d1, d2));
			} else {
				entity = null;
			}

			if (entity != null) {
				int i = msg.entityId;
				entity.setPacketCoordinates(d0, d1, d2);
				entity.moveForced(d0, d1, d2);
				entity.rotationPitch = (float)(msg.pitch * 360) / 256.0F;
				entity.rotationYaw = (float)(msg.yaw * 360) / 256.0F;
				entity.setEntityId(i);
				world.addEntity(i, entity);
			}
		});
		ctx.get().setPacketHandled(true);
	}

}
