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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.UUID;
import java.util.function.Supplier;

public class CCameraLockPacket {
    private UUID horseUUID;
    private boolean locked;
    private boolean failed;

    /**
     * Instantiates a new C camera lock packet.
     *
     * @param horseUUID the horse uuid
     * @param locked    the locked
     */
    public CCameraLockPacket(UUID horseUUID, boolean locked) {
        this.horseUUID = horseUUID;
        this.locked = locked;
        this.failed = false;
    }

    /**
     * Instantiates a new C camera lock packet.
     *
     * @param failed the failed
     */
    public CCameraLockPacket(boolean failed) {
        this.failed = failed;
    }

    /**
     * Decode c camera lock packet.
     *
     * @param buf the buf
     * @return the c camera lock packet
     */
    public static CCameraLockPacket decode(ByteBuf buf) {
        try {
            UUID horseUUID = ((PacketBuffer) buf).readUUID();
            boolean locked = buf.readBoolean();

            return new CCameraLockPacket(horseUUID, locked);
        } catch (IndexOutOfBoundsException e) {
            SWEM.LOGGER.error(
                    "CCameraLockPacket: Unexpected end of packet.\nMessage: "
                            + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()),
                    e);
            return new CCameraLockPacket(true);
        }
    }

    /**
     * Encode.
     *
     * @param msg    the msg
     * @param buffer the buffer
     */
    public static void encode(CCameraLockPacket msg, PacketBuffer buffer) {
        buffer.writeUUID(msg.horseUUID);
        buffer.writeBoolean(msg.locked);
    }

    /**
     * Handle.
     *
     * @param msg the msg
     * @param ctx the ctx
     */
    public static void handle(CCameraLockPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get()
                .enqueueWork(
                        () -> {
                            ServerPlayerEntity player = ctx.get().getSender();

                            Entity entity = ((ServerWorld) player.level).getEntity(msg.horseUUID);
                            if (!(entity instanceof SWEMHorseEntityBase)) {
                                return;
                            }
                            SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;

                            horse.setCameraLock(msg.locked);
                            SWEMPacketHandler.INSTANCE.send(
                                    PacketDistributor.TRACKING_ENTITY.with(() -> horse),
                                    new SCameraLockPacket(horse.getId(), horse.yRot, horse.xRot));
                        });
        ctx.get().setPacketHandled(true);
    }
}
