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

public class CChangeLayerPacket {
	private BlockPos controllerPos;
	private int layerToChange;
	private boolean rightClick;
	private boolean failed;

	/**
	 * Instantiates a new C change layer packet.
	 *
	 * @param controllerPos the controller pos
	 * @param layerToChange the layer to change
	 * @param rightClick    the right click
	 */
	public CChangeLayerPacket(BlockPos controllerPos, int layerToChange, boolean rightClick) {
		this.controllerPos = controllerPos;
		this.layerToChange = layerToChange;
		this.rightClick = rightClick;
		this.failed = false;
	}

	/**
	 * Instantiates a new C change layer packet.
	 *
	 * @param failed the failed
	 */
	public CChangeLayerPacket(boolean failed) {
		this.failed = failed;
	}

	/**
	 * Decode c change layer packet.
	 *
	 * @param buf the buf
	 * @return the c change layer packet
	 */
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

	/**
	 * Encode.
	 *
	 * @param msg    the msg
	 * @param buffer the buffer
	 */
	public static void encode(CChangeLayerPacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.controllerPos);
		buffer.writeVarInt(msg.layerToChange);
		buffer.writeBoolean(msg.rightClick);
	}

	/**
	 * Handle.
	 *
	 * @param msg the msg
	 * @param ctx the ctx
	 */
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
