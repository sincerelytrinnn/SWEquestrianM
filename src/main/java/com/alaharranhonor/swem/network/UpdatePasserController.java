package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.jumps.JumpStandardBlock;
import com.alaharranhonor.swem.blocks.jumps.StandardLayer;
import com.alaharranhonor.swem.tileentity.JumpPasserTE;
import com.alaharranhonor.swem.util.initialization.SWEMTileEntities;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdatePasserController {
		private BlockPos pos;
		private BlockPos controllerPos;
		private boolean failed;

		public UpdatePasserController(BlockPos pos, BlockPos controllerPos) {
			this.pos = pos;
			this.controllerPos = controllerPos;
			this.failed = false;
		}

		public UpdatePasserController(boolean failed) {
			this.failed = failed;
		}

		public static UpdatePasserController decode(ByteBuf buf) {
			try {
				BlockPos pos = ((PacketBuffer) buf).readBlockPos();
				BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
				return new UpdatePasserController(pos, controllerPos);
			} catch (IndexOutOfBoundsException e) {
				SWEM.LOGGER.error("UpdatePasserController: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
				return new UpdatePasserController(true);
			}
		}

		public static void encode(UpdatePasserController msg, PacketBuffer buffer) {
			buffer.writeBlockPos(msg.pos);
			buffer.writeBlockPos(msg.controllerPos);
		}

		public static void handle(UpdatePasserController msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				ClientWorld world = Minecraft.getInstance().world;

				if (world.isBlockLoaded(msg.pos)) {
					JumpPasserTE te = (JumpPasserTE) world.getTileEntity(msg.pos);
					te.setControllerPos(msg.controllerPos);
				}

			});
			ctx.get().setPacketHandled(true);
		}
}
