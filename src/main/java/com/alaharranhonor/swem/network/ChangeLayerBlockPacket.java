package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.jumps.JumpBlock;
import com.alaharranhonor.swem.blocks.jumps.JumpLayer;
import com.alaharranhonor.swem.tileentity.JumpPasserTE;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.block.Blocks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ChangeLayerBlockPacket {

	private BlockPos posToPlace;
	private BlockPos controllerPos;
	private JumpLayer layer;
	private Direction facing;
	private String type;
	private boolean failed;

	public ChangeLayerBlockPacket(BlockPos posToPlace, BlockPos controllerPos, JumpLayer layer, Direction facing, String type) {
		this.posToPlace = posToPlace;
		this.controllerPos = controllerPos;
		this.layer = layer;
		this.facing = facing;
		this.type = type;
		this.failed = false;
	}

	public ChangeLayerBlockPacket(boolean failed) {
		this.failed = failed;
	}

	public static ChangeLayerBlockPacket decode(ByteBuf buf) {
		try {
			BlockPos pos = ((PacketBuffer) buf).readBlockPos();
			BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
			JumpLayer layer = JumpLayer.valueOf(((PacketBuffer) buf).readString());
			Direction facing = Direction.valueOf(((PacketBuffer) buf).readString());
			String type = ((PacketBuffer) buf).readString();
			return new ChangeLayerBlockPacket(pos, controllerPos, layer, facing, type);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("ChangeLayerBlockPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new ChangeLayerBlockPacket(true);
		}
	}

	public static void encode(ChangeLayerBlockPacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.posToPlace);
		buffer.writeBlockPos(msg.controllerPos);
		buffer.writeString(msg.layer.name());
		buffer.writeString(msg.facing.name());
		buffer.writeString(msg.type);
	}

	public static void handle(ChangeLayerBlockPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerWorld world = ctx.get().getSender().getServerWorld();

			if (msg.layer == JumpLayer.AIR) {
				world.setBlockState(msg.posToPlace, Blocks.AIR.getDefaultState(), 3);
				return;
			}

			if (world.isBlockLoaded(msg.posToPlace)) {
				if (msg.type.equals("left")) {
					world.setBlockState(msg.posToPlace, msg.layer.getEndState().with(JumpBlock.HORIZONTAL_FACING, msg.facing), 3);
				} else if (msg.type.equals("middle")) {
					world.setBlockState(msg.posToPlace, msg.layer.getMiddleState().with(JumpBlock.HORIZONTAL_FACING, msg.facing), 3);
				} else {
					world.setBlockState(msg.posToPlace, msg.layer.getBetweenState().with(JumpBlock.HORIZONTAL_FACING, msg.facing), 3);
				}
				JumpPasserTE passer = (JumpPasserTE) world.getTileEntity(msg.posToPlace);
				passer.setControllerPos(msg.controllerPos);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
