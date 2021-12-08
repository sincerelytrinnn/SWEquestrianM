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

public class CChangeColorPacket {
	private BlockPos controllerPos;
	private int layerToChange;
	private boolean rightClick;
	private boolean failed;

	public CChangeColorPacket(BlockPos controllerPos, int layerToChange, boolean rightClick) {
		this.controllerPos = controllerPos;
		this.layerToChange = layerToChange;
		this.rightClick = rightClick;
		this.failed = false;
	}

	public CChangeColorPacket(boolean failed) {
		this.failed = failed;
	}

	public static CChangeColorPacket decode(ByteBuf buf) {
		try {
			BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
			int layerToChange = ((PacketBuffer) buf).readVarInt();
			boolean rightClick = buf.readBoolean();
			return new CChangeColorPacket(controllerPos, layerToChange, rightClick);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("CChangeColorPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new CChangeColorPacket(true);
		}
	}

	public static void encode(CChangeColorPacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.controllerPos);
		buffer.writeVarInt(msg.layerToChange);
		buffer.writeBoolean(msg.rightClick);
	}

	public static void handle(CChangeColorPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Container container = ctx.get().getSender().containerMenu;
			if (container instanceof JumpContainer) {
				JumpContainer jumpContainer = (JumpContainer) container;
				if (msg.rightClick) {
					jumpContainer.controller.decrementColorVariant(msg.layerToChange);
				} else {
					jumpContainer.controller.incrementColorVariant(msg.layerToChange);
				}
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
