package com.alaharranhonor.swem.network.jumps;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.blocks.jumps.JumpLayer;
import com.alaharranhonor.swem.blocks.jumps.StandardLayer;
import com.alaharranhonor.swem.gui.JumpScreen;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SDataSendPacket {
	private BlockPos controllerPos;
	private int layerAmount;
	private Map<Integer, JumpLayer> layerTypes = new HashMap<>();
	private Map<Integer, Integer> layerColors = new HashMap<>();
	private StandardLayer currentStandard;
	private boolean failed;

	public SDataSendPacket(BlockPos controllerPos, int layerAmount, Map<Integer, JumpLayer> layerTypes, Map<Integer, Integer> layerColors, StandardLayer currentStandard) {
		this.controllerPos = controllerPos;
		this.layerAmount = layerAmount;
		this.layerTypes = layerTypes;
		this.layerColors = layerColors;
		this.currentStandard = currentStandard;
		this.failed = false;
	}

	public SDataSendPacket(boolean failed) {
		this.failed = failed;
	}

	public static SDataSendPacket decode(ByteBuf buf) {
		try {
			BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
			int layerAmount = ((PacketBuffer) buf).readVarInt();
			Map<Integer, JumpLayer> layers = new HashMap<>();
			Map<Integer, Integer> colors = new HashMap<>();
			for (int i = 0; i < layerAmount; i++) {
				layers.put(i + 1, JumpLayer.valueOf(((PacketBuffer) buf).readUtf(32767)));
				colors.put(i + 1, ((PacketBuffer) buf).readVarInt());
			}
			StandardLayer standard = StandardLayer.valueOf(((PacketBuffer) buf).readUtf(32767));

			return new SDataSendPacket(controllerPos, layerAmount, layers, colors, standard);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("SDataSendPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new SDataSendPacket(true);
		}
	}

	public static void encode(SDataSendPacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.controllerPos);
		buffer.writeVarInt(msg.layerAmount);
		for (int i = 0; i < msg.layerAmount; i++) {
			buffer.writeUtf(msg.layerTypes.get(i + 1).name());
			buffer.writeVarInt(msg.layerColors.get(i + 1));
		}
		buffer.writeUtf(msg.currentStandard.name());
	}

	public static void handle(SDataSendPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Screen screen = Minecraft.getInstance().screen;
			if (screen instanceof JumpScreen) {
				JumpScreen jumpScreen = (JumpScreen) screen;
				jumpScreen.updateData(msg.controllerPos, msg.layerAmount, msg.layerTypes, msg.layerColors, msg.currentStandard);
			}

		});
		ctx.get().setPacketHandled(true);
	}
}
