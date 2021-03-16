package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.gui.JumpScreen;
import com.alaharranhonor.swem.tileentity.JumpTE;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenGuiPacket {
	private BlockPos controllerPos;
	private boolean failed;

	public OpenGuiPacket(BlockPos controllerPos) {
		this.controllerPos = controllerPos;

		this.failed = false;

	}

	public OpenGuiPacket(boolean failed) {
		this.failed = failed;
	}

	public static OpenGuiPacket decode(ByteBuf buf) {
		try {
			BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
			return new OpenGuiPacket(controllerPos);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("OpenGuiPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new OpenGuiPacket(true);
		}
	}

	public static void encode(OpenGuiPacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.controllerPos);
	}

	public static void handle(OpenGuiPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			TileEntity te = Minecraft.getInstance().world.getTileEntity(msg.controllerPos);
			if (te instanceof JumpTE) {
				JumpTE controller = (JumpTE) te;
				// Minecraft.getInstance().currentScreen gets the current screen displayed.
				Minecraft.getInstance().displayGuiScreen(new JumpScreen(new TranslationTextComponent("screen.swem.jump_builder"), controller));
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
