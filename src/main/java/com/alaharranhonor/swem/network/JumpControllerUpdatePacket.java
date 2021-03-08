package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.jumps.JumpLayer;
import com.alaharranhonor.swem.tileentity.JumpTE;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.block.Blocks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.core.util.UuidUtil;

import java.util.function.Supplier;

public class JumpControllerUpdatePacket {
	private BlockPos controllerPos;
	private int action;
	private int layerNumber;
	private boolean failed;

	public JumpControllerUpdatePacket(BlockPos controllerPos, int action, int layerNumber) {
		this.controllerPos = controllerPos;
		this.action = action;
		this.layerNumber = layerNumber;

		this.failed = false;

	}

	public JumpControllerUpdatePacket(boolean failed) {
		this.failed = failed;
	}

	public static JumpControllerUpdatePacket decode(ByteBuf buf) {
		try {
			BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
			int action = ((PacketBuffer) buf).readVarInt();
			int layerNumber = ((PacketBuffer) buf).readVarInt();
			return new JumpControllerUpdatePacket(controllerPos, action, layerNumber);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("JumpControllerUpdatePacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new JumpControllerUpdatePacket(true);
		}
	}

	public static void encode(JumpControllerUpdatePacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.controllerPos);
		buffer.writeVarInt(msg.action);
		buffer.writeVarInt(msg.layerNumber);
	}

	public static void handle(JumpControllerUpdatePacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerWorld world = ctx.get().getSender().getServerWorld();

			if (!world.isBlockPresent(msg.controllerPos)) return;

			TileEntity te = world.getTileEntity(msg.controllerPos);
			if (!(te instanceof JumpTE)) return;

			JumpTE controller = (JumpTE) te;

			switch (msg.action) {
				case 0: {
					// Remove TE
					controller.remove();
				}
				case 1: {
					// Add layer
					controller.addLayer(msg.layerNumber);
				}
				case 2: {
					// Delete layer
					controller.deleteLayer(msg.layerNumber);
				}
				case 3: {
					controller.changeColorVariant(msg.layerNumber);
				}
			}

		});
		ctx.get().setPacketHandled(true);
	}
}
