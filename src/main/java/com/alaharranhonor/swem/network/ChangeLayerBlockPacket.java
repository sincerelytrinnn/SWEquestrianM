package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.jumps.JumpBlock;
import com.alaharranhonor.swem.blocks.jumps.JumpLayer;
import com.alaharranhonor.swem.tileentity.JumpPasserTE;
import com.alaharranhonor.swem.tileentity.JumpTE;
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

	private BlockPos controllerPos;
	private JumpLayer layer;
	private int layerNumber;
	private boolean failed;

	public ChangeLayerBlockPacket(BlockPos controllerPos, JumpLayer layer, int layerNumber) {
		this.controllerPos = controllerPos;
		this.layer = layer;
		this.layerNumber = layerNumber;
		this.failed = false;
	}

	public ChangeLayerBlockPacket(boolean failed) {
		this.failed = failed;
	}

	public static ChangeLayerBlockPacket decode(ByteBuf buf) {
		try {
			BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
			JumpLayer layer = JumpLayer.valueOf(((PacketBuffer) buf).readString(32767));
			int layerNumber = ((PacketBuffer) buf).readVarInt();
			return new ChangeLayerBlockPacket(controllerPos, layer, layerNumber);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("ChangeLayerBlockPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new ChangeLayerBlockPacket(true);
		}
	}

	public static void encode(ChangeLayerBlockPacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.controllerPos);
		buffer.writeString(msg.layer.name());
		buffer.writeVarInt(msg.layerNumber);
	}

	public static void handle(ChangeLayerBlockPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerWorld world = ctx.get().getSender().getServerWorld();

			if (world.isBlockPresent(msg.controllerPos)) {
				JumpTE controller = (JumpTE) world.getTileEntity(msg.controllerPos);
				controller.placeLayer(msg.layerNumber, msg.layer);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
