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

public class CChangeStandardPacket {
  private BlockPos controllerPos;
  private boolean rightClick;
  private boolean failed;

  /**
   * Instantiates a new C change standard packet.
   *
   * @param controllerPos the controller pos
   * @param rightClick the right click
   */
  public CChangeStandardPacket(BlockPos controllerPos, boolean rightClick) {
    this.controllerPos = controllerPos;
    this.rightClick = rightClick;
    this.failed = false;
  }

  /**
   * Instantiates a new C change standard packet.
   *
   * @param failed the failed
   */
  public CChangeStandardPacket(boolean failed) {
    this.failed = failed;
  }

  /**
   * Decode c change standard packet.
   *
   * @param buf the buf
   * @return the c change standard packet
   */
  public static CChangeStandardPacket decode(ByteBuf buf) {
    try {
      BlockPos controllerPos = ((PacketBuffer) buf).readBlockPos();
      boolean rightClick = buf.readBoolean();
      return new CChangeStandardPacket(controllerPos, rightClick);
    } catch (IndexOutOfBoundsException e) {
      SWEM.LOGGER.error(
          "CChangeStandardPacket: Unexpected end of packet.\nMessage: "
              + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()),
          e);
      return new CChangeStandardPacket(true);
    }
  }

  /**
   * Encode.
   *
   * @param msg the msg
   * @param buffer the buffer
   */
  public static void encode(CChangeStandardPacket msg, PacketBuffer buffer) {
    buffer.writeBlockPos(msg.controllerPos);
    buffer.writeBoolean(msg.rightClick);
  }

  /**
   * Handle.
   *
   * @param msg the msg
   * @param ctx the ctx
   */
  public static void handle(CChangeStandardPacket msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get()
        .enqueueWork(
            () -> {
              Container container = ctx.get().getSender().containerMenu;
              if (container instanceof JumpContainer) {
                JumpContainer jumpContainer = (JumpContainer) container;
                if (msg.rightClick) {
                  jumpContainer.controller.changeStandardBackwards();
                } else {
                  jumpContainer.controller.changeStandardForward();
                }
              }
            });
    ctx.get().setPacketHandled(true);
  }
}
