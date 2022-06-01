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
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class CMountEntityPacket {

  private int targetId;
  private boolean failed;

  /**
   * Instantiates a new C mount entity packet.
   *
   * @param targetId the target id
   */
  public CMountEntityPacket(int targetId) {
    this.targetId = targetId;
    this.failed = failed;
  }

  /**
   * Instantiates a new C mount entity packet.
   *
   * @param target the target
   */
  public CMountEntityPacket(Entity target) {
    this.targetId = target.getId();
    this.failed = false;
  }

  /**
   * Instantiates a new C mount entity packet.
   *
   * @param failed the failed
   */
  public CMountEntityPacket(boolean failed) {
    this.failed = failed;
  }

  /**
   * Decode c mount entity packet.
   *
   * @param buf the buf
   * @return the c mount entity packet
   */
  public static CMountEntityPacket decode(ByteBuf buf) {
    try {
      int targetId = buf.readInt();
      return new CMountEntityPacket(targetId);
    } catch (IndexOutOfBoundsException e) {
      SWEM.LOGGER.error(
          "CMountEntityPacket: Unexpected end of packet.\nMessage: "
              + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()),
          e);
      return new CMountEntityPacket(true);
    }
  }

  /**
   * Encode.
   *
   * @param msg the msg
   * @param buffer the buffer
   */
  public static void encode(CMountEntityPacket msg, PacketBuffer buffer) {
    buffer.writeInt(msg.targetId);
  }

  /**
   * Try mounting boolean.
   *
   * @param rider the rider
   * @param mount the mount
   * @return the boolean
   */
  private static boolean tryMounting(Entity rider, Entity mount) {
    if (!(rider instanceof LivingEntity)) {
      return false;
    }

    return rider.startRiding(mount);
  }

  /**
   * Handle.
   *
   * @param msg the msg
   * @param ctx the ctx
   */
  public static void handle(CMountEntityPacket msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get()
        .enqueueWork(
            () -> {
              ServerPlayerEntity player = ctx.get().getSender();
              Entity target = player.level.getEntity(msg.targetId);
              if (target == null) {
                SWEM.LOGGER.warn(
                    "Could not find entity with id: "
                        + msg.targetId
                        + " requested by "
                        + player.getName().getString());
                return;
              }

              // Can only mount friendly creatures.
              if (!target.getType().getCategory().isFriendly()) return;

              // Cannot mount other players
              if (target instanceof PlayerEntity) return;

              // If passenger is mounted, mount the target behind you.
              if (player.isPassenger()) {
                Entity mount = player.getVehicle();
                if (mount instanceof SWEMHorseEntityBase
                    && !(target
                        instanceof
                        SWEMHorseEntityBase)) { // Only works on SWEM Horses, but can't mount other
                                                // swem horses.
                  if (tryMounting(target, mount)) return;
                }
              }

              // If player is not passenger, dismount all passengers.
              if (target instanceof SWEMHorseEntityBase) {
                if (!((SWEMHorseEntityBase) target).getOwnerUUID().equals(player.getUUID())) {
                  return; // Only let the horse owner dismount other people.
                }
                for (Entity passenger : target.getPassengers()) {
                  if (passenger instanceof PlayerEntity && passenger == player) continue;
                  passenger.stopRiding();
                }
              }
            });
    ctx.get().setPacketHandled(true);
  }
}
