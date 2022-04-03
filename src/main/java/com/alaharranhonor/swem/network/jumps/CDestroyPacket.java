package com.alaharranhonor.swem.network.jumps;


/*
 * All Rights Reserved
 *
 * Copyright (c) 2021, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.container.JumpContainer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CDestroyPacket {
	private BlockPos controllerPos;
	private boolean failed;

	/**
	 * Instantiates a new C destroy packet.
	 *
	 * @param controllerPos the controller pos
	 */
	public CDestroyPacket(BlockPos controllerPos) {
		this.controllerPos = controllerPos;
		this.failed = false;
	}

	/**
	 * Instantiates a new C destroy packet.
	 *
	 * @param failed the failed
	 */
	public CDestroyPacket(boolean failed) {
		this.failed = failed;
	}

	/**
	 * Decode c destroy packet.
	 *
	 * @param buf the buf
	 * @return the c destroy packet
	 */
	public static CDestroyPacket decode(ByteBuf buf) {
		try {
			BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
			return new CDestroyPacket(controllerPos);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("CDestroyPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new CDestroyPacket(true);
		}
	}

	/**
	 * Encode.
	 *
	 * @param msg    the msg
	 * @param buffer the buffer
	 */
	public static void encode(CDestroyPacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.controllerPos);
	}

	/**
	 * Handle.
	 *
	 * @param msg the msg
	 * @param ctx the ctx
	 */
	public static void handle(CDestroyPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {

			Container container = ctx.get().getSender().containerMenu;
			if (container instanceof JumpContainer) {
				JumpContainer jumpContainer = (JumpContainer) container;
				jumpContainer.controller.setRemoved();
			}

		});
		ctx.get().setPacketHandled(true);
	}
}
