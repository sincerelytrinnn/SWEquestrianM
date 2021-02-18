package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.jumps.JumpBlock;
import com.alaharranhonor.swem.blocks.jumps.JumpLayer;
import com.alaharranhonor.swem.blocks.jumps.JumpStandardBlock;
import com.alaharranhonor.swem.blocks.jumps.StandardLayer;
import com.alaharranhonor.swem.tileentity.JumpPasserTE;
import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.block.Blocks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ChangeStandardPacket {
	private BlockPos posToPlace;
	private BlockPos controllerPos;
	private StandardLayer layer;
	private Direction facing;
	private String type;
	private boolean failed;

	public ChangeStandardPacket(BlockPos posToPlace, BlockPos controllerPos, StandardLayer layer, Direction facing, String type) {
		this.posToPlace = posToPlace;
		this.controllerPos = controllerPos;
		this.layer = layer;
		this.facing = facing;
		this.type = type;
		this.failed = false;
	}

	public ChangeStandardPacket(boolean failed) {
		this.failed = failed;
	}

	public static ChangeStandardPacket decode(ByteBuf buf) {
		try {
			BlockPos pos = ((PacketBuffer) buf).readBlockPos();
			BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
			StandardLayer layer = StandardLayer.valueOf(((PacketBuffer) buf).readString());
			Direction facing = Direction.valueOf(((PacketBuffer) buf).readString());
			String type = ((PacketBuffer) buf).readString();
			return new ChangeStandardPacket(pos, controllerPos, layer, facing, type);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("ChangeStandardPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new ChangeStandardPacket(true);
		}
	}

	public static void encode(ChangeStandardPacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.posToPlace);
		buffer.writeBlockPos(msg.controllerPos);
		buffer.writeString(msg.layer.name());
		buffer.writeString(msg.facing.name());
		buffer.writeString(msg.type);
	}

	public static void handle(ChangeStandardPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerWorld world = ctx.get().getSender().getServerWorld();

			if (world.isBlockLoaded(msg.posToPlace)) {
				JumpPasserTE passer = SWEMTileEntities.JUMP_PASSER_TILE_ENTITY.get().create();
				passer.setPos(msg.posToPlace);
				if (msg.type.equals("top")) {
					world.setBlockState(msg.posToPlace, msg.layer.getTopState().with(JumpStandardBlock.HORIZONTAL_FACING, msg.facing), 3);
				} else if (msg.type.equals("middle")) {
					world.setBlockState(msg.posToPlace, msg.layer.getMiddleState().with(JumpStandardBlock.HORIZONTAL_FACING, msg.facing), 3);
				} else {
					world.setBlockState(msg.posToPlace, msg.layer.getBottomState().with(JumpStandardBlock.HORIZONTAL_FACING, msg.facing), 3);
				}
				world.removeTileEntity(msg.posToPlace);
				world.addTileEntity(passer);
				passer.setControllerPos(msg.controllerPos);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
