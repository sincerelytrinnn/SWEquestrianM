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
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class CHorseAnimationPacket {
  private int entityID;
  private int action;

  private boolean failed;

  /**
   * Instantiates a new C horse animation packet.
   *
   * @param entityID the entity id
   * @param action the action
   */
  public CHorseAnimationPacket(int entityID, int action) {
    this.entityID = entityID;
    this.action = action;
    this.failed = false;
  }

  /**
   * Instantiates a new C horse animation packet.
   *
   * @param failed the failed
   */
  public CHorseAnimationPacket(boolean failed) {
    this.failed = failed;
  }

  /**
   * Decode c horse animation packet.
   *
   * @param buf the buf
   * @return the c horse animation packet
   */
  public static CHorseAnimationPacket decode(ByteBuf buf) {
    try {
      int entityID = buf.readInt();
      int action = buf.readInt();
      return new CHorseAnimationPacket(entityID, action);
    } catch (IndexOutOfBoundsException e) {
      SWEM.LOGGER.error(
          "CHorseAnimationPacket: Unexpected end of packet.\nMessage: "
              + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()),
          e);
      return new CHorseAnimationPacket(true);
    }
  }

  /**
   * Encode.
   *
   * @param msg the msg
   * @param buffer the buffer
   */
  public static void encode(CHorseAnimationPacket msg, PacketBuffer buffer) {
    buffer.writeInt(msg.entityID);
    buffer.writeInt(msg.action);
  }

  /**
   * Handle.
   *
   * @param msg the msg
   * @param ctx the ctx
   */
  public static void handle(CHorseAnimationPacket msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get()
        .enqueueWork(
            () -> {
              ClientPlayerEntity player = Minecraft.getInstance().player;
              Entity entity = player.level.getEntity(msg.entityID);
              if (!(entity instanceof SWEMHorseEntityBase)) {
                return;
              }
              SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
              horse.setStandingTimer(142);
              switch (msg.action) {
                case 1:
                  {
                    horse.standAnimationVariant = 1;
                    horse.standAnimationTick = 42;
                    break;
                  }
                case 2:
                  {
                    horse.standAnimationVariant = 2;
                    horse.standAnimationTick = 42;
                    break;
                  }
                case 3:
                  {
                    horse.isWalkingBackwards = true;
                    break;
                  }
                case 4:
                  {
                    horse.isWalkingBackwards = false;
                    break;
                  }
                case 5:
                  {
                    horse.kickAnimationTimer = 21;
                    break;
                  }
                case 9:
                  {
                    SWEMPacketHandler.INSTANCE.sendToServer(
                        new SHorseAnimationPacket(horse.getId(), 5));
                    break;
                  }
              }
            });
    ctx.get().setPacketHandled(true);
  }
}
