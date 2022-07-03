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

public class CRemoveLayerPacket {
    private BlockPos controllerPos;
    private int layerToRemove;
    private boolean failed;

    /**
     * Instantiates a new C remove layer packet.
     *
     * @param controllerPos the controller pos
     * @param layerToRemove the layer to remove
     */
    public CRemoveLayerPacket(BlockPos controllerPos, int layerToRemove) {
        this.controllerPos = controllerPos;
        this.layerToRemove = layerToRemove;
        this.failed = false;
    }

    /**
     * Instantiates a new C remove layer packet.
     *
     * @param failed the failed
     */
    public CRemoveLayerPacket(boolean failed) {
        this.failed = failed;
    }

    /**
     * Decode c remove layer packet.
     *
     * @param buf the buf
     * @return the c remove layer packet
     */
    public static CRemoveLayerPacket decode(ByteBuf buf) {
        try {
            BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
            int layerToRemove = ((PacketBuffer) buf).readVarInt();
            return new CRemoveLayerPacket(controllerPos, layerToRemove);
        } catch (IndexOutOfBoundsException e) {
            SWEM.LOGGER.error(
                    "CRemoveLayerPacket: Unexpected end of packet.\nMessage: "
                            + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()),
                    e);
            return new CRemoveLayerPacket(true);
        }
    }

    /**
     * Encode.
     *
     * @param msg    the msg
     * @param buffer the buffer
     */
    public static void encode(CRemoveLayerPacket msg, PacketBuffer buffer) {
        buffer.writeBlockPos(msg.controllerPos);
        buffer.writeVarInt(msg.layerToRemove);
    }

    /**
     * Handle.
     *
     * @param msg the msg
     * @param ctx the ctx
     */
    public static void handle(CRemoveLayerPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get()
                .enqueueWork(
                        () -> {
                            Container container = ctx.get().getSender().containerMenu;
                            if (container instanceof JumpContainer) {
                                JumpContainer jumpContainer = (JumpContainer) container;
                                jumpContainer.controller.deleteLayer(msg.layerToRemove);
                            }
                        });
        ctx.get().setPacketHandled(true);
    }
}
