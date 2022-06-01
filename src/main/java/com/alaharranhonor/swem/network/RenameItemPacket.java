package com.alaharranhonor.swem.network;

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
import com.alaharranhonor.swem.container.CantazariteAnvilContainer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import java.util.function.Supplier;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SharedConstants;
import net.minecraftforge.fml.network.NetworkEvent;

public class RenameItemPacket {

  private String name;

  private boolean failed;

  /**
   * Instantiates a new Rename item packet.
   *
   * @param name the name
   */
  public RenameItemPacket(String name) {
    this.name = name;
    this.failed = false;
  }

  /**
   * Instantiates a new Rename item packet.
   *
   * @param failed the failed
   */
  public RenameItemPacket(boolean failed) {
    this.failed = failed;
  }

  /**
   * Decode rename item packet.
   *
   * @param buf the buf
   * @return the rename item packet
   */
  public static RenameItemPacket decode(ByteBuf buf) {
    try {
      String name = ((PacketBuffer) buf).readUtf(32767);
      return new RenameItemPacket(name);
    } catch (IndexOutOfBoundsException e) {
      SWEM.LOGGER.error(
          "RenameItemPacket: Unexpected end of packet.\nMessage: "
              + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()),
          e);
      return new RenameItemPacket(true);
    }
  }

  /**
   * Encode.
   *
   * @param msg the msg
   * @param buffer the buffer
   */
  public static void encode(RenameItemPacket msg, PacketBuffer buffer) {
    buffer.writeUtf(msg.name);
  }

  /**
   * Handle.
   *
   * @param msg the msg
   * @param ctx the ctx
   */
  public static void handle(RenameItemPacket msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get()
        .enqueueWork(
            () -> {
              if (ctx.get().getSender().containerMenu instanceof CantazariteAnvilContainer) {
                CantazariteAnvilContainer container =
                    (CantazariteAnvilContainer) ctx.get().getSender().containerMenu;
                String s = SharedConstants.filterText(msg.name);
                if (s.length() <= 35) {
                  container.updateItemName(s);
                }
              }
            });
    ctx.get().setPacketHandled(true);
  }
}
