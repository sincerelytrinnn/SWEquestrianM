package com.alaharranhonor.swem.network.jumps;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.container.JumpContainer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CChangeLayerPacket {
	private BlockPos controllerPos;
	private int layerToChange;
	private boolean rightClick;
	private boolean failed;

	public CChangeLayerPacket(BlockPos controllerPos, int layerToChange, boolean rightClick) {
		this.controllerPos = controllerPos;
		this.layerToChange = layerToChange;
		this.rightClick = rightClick;
		this.failed = false;
	}

	public CChangeLayerPacket(boolean failed) {
		this.failed = failed;
	}

	public static CChangeLayerPacket decode(ByteBuf buf) {
		try {
			BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
			int layerToChange = ((PacketBuffer) buf).readVarInt();
			boolean rightClick = buf.readBoolean();
			return new CChangeLayerPacket(controllerPos, layerToChange, rightClick);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("CChangeLayerPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new CChangeLayerPacket(true);
		}
	}

	public static void encode(CChangeLayerPacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.controllerPos);
		buffer.writeVarInt(msg.layerToChange);
		buffer.writeBoolean(msg.rightClick);
	}

	public static void handle(CChangeLayerPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Container container = ctx.get().getSender().containerMenu;
			if (container instanceof JumpContainer) {
				JumpContainer jumpContainer = (JumpContainer) container;
				if (msg.rightClick) {
					jumpContainer.controller.changeLayerBackwards(msg.layerToChange);
				} else {
					jumpContainer.controller.changeLayerForward(msg.layerToChange);
				}
			}

		});
		ctx.get().setPacketHandled(true);
	}
}
