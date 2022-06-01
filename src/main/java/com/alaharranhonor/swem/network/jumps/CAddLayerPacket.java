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
import java.util.function.Supplier;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class CAddLayerPacket {
  private BlockPos controllerPos;
  private int layerToAdd;
  private boolean failed;

  /**
   * Instantiates a new C add layer packet.
   *
   * @param controllerPos the controller pos
   * @param layerToAdd the layer to add
   */
  public CAddLayerPacket(BlockPos controllerPos, int layerToAdd) {
    this.controllerPos = controllerPos;
    this.layerToAdd = layerToAdd;
    this.failed = false;
  }

  /**
   * Instantiates a new C add layer packet.
   *
   * @param failed the failed
   */
  public CAddLayerPacket(boolean failed) {
    this.failed = failed;
  }

  /**
   * Decode c add layer packet.
   *
   * @param buf the buf
   * @return the c add layer packet
   */
  public static CAddLayerPacket decode(ByteBuf buf) {
    try {
      BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
      int layerToAdd = ((PacketBuffer) buf).readVarInt();
      return new CAddLayerPacket(controllerPos, layerToAdd);
    } catch (IndexOutOfBoundsException e) {
      SWEM.LOGGER.error(
          "CAddLayerPacket: Unexpected end of packet.\nMessage: "
              + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()),
          e);
      return new CAddLayerPacket(true);
    }
  }

  /**
   * Encode.
   *
   * @param msg the msg
   * @param buffer the buffer
   */
  public static void encode(CAddLayerPacket msg, PacketBuffer buffer) {
    buffer.writeBlockPos(msg.controllerPos);
    buffer.writeVarInt(msg.layerToAdd);
  }

  /**
   * Handle.
   *
   * @param msg the msg
   * @param ctx the ctx
   */
  public static void handle(CAddLayerPacket msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get()
        .enqueueWork(
            () -> {
              Container container = ctx.get().getSender().containerMenu;
              if (container instanceof JumpContainer) {
                JumpContainer jumpContainer = (JumpContainer) container;
                jumpContainer.controller.addLayer(msg.layerToAdd);
              }
            });
    ctx.get().setPacketHandled(true);
  }
}
