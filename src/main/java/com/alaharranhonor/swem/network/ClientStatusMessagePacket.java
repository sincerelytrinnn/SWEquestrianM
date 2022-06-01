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
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import java.util.ArrayList;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

public class ClientStatusMessagePacket {
  private int action;
  private int argLength;
  private ArrayList<String> args;

  private boolean failed;

  /**
   * Instantiates a new Client status message packet.
   *
   * @param action the action
   * @param argLength the arg length
   * @param args the args
   */
  public ClientStatusMessagePacket(int action, int argLength, ArrayList<String> args) {
    this.action = action;
    this.argLength = argLength;
    this.args = args;
    this.failed = false;
  }

  /**
   * Instantiates a new Client status message packet.
   *
   * @param failed the failed
   */
  public ClientStatusMessagePacket(boolean failed) {
    this.failed = failed;
  }

  /**
   * Decode client status message packet.
   *
   * @param buf the buf
   * @return the client status message packet
   */
  public static ClientStatusMessagePacket decode(ByteBuf buf) {
    try {
      int action = buf.readInt();
      int argLength = buf.readInt();
      ArrayList<String> args = new ArrayList<>();
      for (int i = 0; i < argLength; i++) {
        args.add(((PacketBuffer) buf).readUtf());
      }
      return new ClientStatusMessagePacket(action, argLength, args);
    } catch (IndexOutOfBoundsException e) {
      SWEM.LOGGER.error(
          "GallopCooldownPacket: Unexpected end of packet.\nMessage: "
              + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()),
          e);
      return new ClientStatusMessagePacket(true);
    }
  }

  /**
   * Encode.
   *
   * @param msg the msg
   * @param buffer the buffer
   */
  public static void encode(ClientStatusMessagePacket msg, PacketBuffer buffer) {
    buffer.writeInt(msg.action);
    buffer.writeInt(msg.argLength);
    if (msg.args.size() > 0) {
      msg.args.forEach(buffer::writeUtf);
    }
  }

  /**
   * Handle.
   *
   * @param msg the msg
   * @param ctx the ctx
   */
  public static void handle(ClientStatusMessagePacket msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get()
        .enqueueWork(
            () -> {
              switch (msg.action) {
                case 0:
                  {
                    Minecraft.getInstance()
                        .player
                        .displayClientMessage(
                            new TranslationTextComponent("swem.horse.status.gallop_cooldown")
                                .append(msg.args.get(0) + "s"),
                            true);
                    break;
                  }
                case 1:
                  {
                    Minecraft.getInstance()
                        .player
                        .displayClientMessage(
                            new TranslationTextComponent("swem.horse.status.too_thirsty_to_canter"),
                            true);
                    break;
                  }
                case 2:
                  {
                    Minecraft.getInstance()
                        .player
                        .displayClientMessage(
                            new TranslationTextComponent("swem.status.tack_box_not_bound"), true);
                    break;
                  }
                case 3:
                  {
                    Minecraft.getInstance()
                        .player
                        .displayClientMessage(
                            new TranslationTextComponent("swem.horse.status.too_hungry_to_canter"),
                            true);
                    break;
                  }
              }
            });
    ctx.get().setPacketHandled(true);
  }
}
