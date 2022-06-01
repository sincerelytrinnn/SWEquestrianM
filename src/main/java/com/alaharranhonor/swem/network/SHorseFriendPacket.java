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
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SHorseFriendPacket {

  private UUID playerUUID;
  private int entityID;
  private int action;

  private boolean failed;

  /**
   * Instantiates a new S horse friend packet.
   *
   * @param playerUUID the player uuid
   * @param entityID the entity id
   * @param action the action
   */
  public SHorseFriendPacket(UUID playerUUID, int entityID, int action) {
    this.playerUUID = playerUUID;
    this.entityID = entityID;
    this.action = action;
    this.failed = false;
  }

  /**
   * Instantiates a new S horse friend packet.
   *
   * @param failed the failed
   */
  public SHorseFriendPacket(boolean failed) {
    this.failed = failed;
  }

  /**
   * Decode s horse friend packet.
   *
   * @param buf the buf
   * @return the s horse friend packet
   */
  public static SHorseFriendPacket decode(ByteBuf buf) {
    try {
      UUID playerUUID = ((PacketBuffer) buf).readUUID();
      int entityID = buf.readInt();
      int action = buf.readInt();
      return new SHorseFriendPacket(playerUUID, entityID, action);
    } catch (IndexOutOfBoundsException e) {
      SWEM.LOGGER.error(
          "SHorseFriendPacket: Unexpected end of packet.\nMessage: "
              + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()),
          e);
      return new SHorseFriendPacket(true);
    }
  }

  /**
   * Encode.
   *
   * @param msg the msg
   * @param buffer the buffer
   */
  public static void encode(SHorseFriendPacket msg, PacketBuffer buffer) {
    buffer.writeUUID(msg.playerUUID);
    buffer.writeInt(msg.entityID);
    buffer.writeInt(msg.action);
  }

  /**
   * Handle.
   *
   * @param msg the msg
   * @param ctx the ctx
   */
  public static void handle(SHorseFriendPacket msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get()
        .enqueueWork(
            () -> {
              ClientPlayerEntity player = Minecraft.getInstance().player;
              Entity entity = player.level.getEntity(msg.entityID);
              if (!(entity instanceof SWEMHorseEntityBase)) {
                return;
              }
              SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;

              switch (msg.action) {
                case 1:
                  { // Add friend
                    horse.addAllowedUUID(msg.playerUUID);
                    break;
                  }
                case 2:
                  { // Remove Friend
                    horse.removeAllowedUUID(msg.playerUUID);
                    break;
                  }
                case 3:
                  { // Remove whole list.
                    horse.removeAllAllowedUUIDs();
                  }
              }
            });
    ctx.get().setPacketHandled(true);
  }
}
