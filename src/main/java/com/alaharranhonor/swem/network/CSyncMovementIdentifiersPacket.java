
/*
 * All Rights Reserved
 *
 * Copyright (c) 2022, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class CSyncMovementIdentifiersPacket {

    private MovementPacketData packetData;
    private UUID entityUUID;
    private boolean failed;


    public CSyncMovementIdentifiersPacket(MovementPacketData packetData, UUID entity) {
        this.packetData = packetData;
        this.entityUUID = entity;
        this.failed = false;
    }

    public CSyncMovementIdentifiersPacket(boolean failed) {
        this.failed = failed;
    }

    public MovementPacketData getPacketData() {
        return packetData;
    }

    public UUID getEntityUUID() {
        return entityUUID;
    }

    public static CSyncMovementIdentifiersPacket decode(ByteBuf buf) {
        try {
            MovementPacketData packetData = MovementPacketData.decode(buf);
            UUID entity = ((PacketBuffer) buf).readUUID();
            return new CSyncMovementIdentifiersPacket(packetData, entity);
        } catch (IndexOutOfBoundsException e) {
            SWEM.LOGGER.error(
                "CSyncMovementIdentifiersPacket: Unexpected end of packet.\nMessage: "
                    + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()),
                e);
            return new CSyncMovementIdentifiersPacket(true);
        }
    }

    /**
     * Encode.
     *
     * @param msg    the msg
     * @param buffer the buffer
     */
    public static void encode(CSyncMovementIdentifiersPacket msg, PacketBuffer buffer) {
        buffer.writeBoolean(msg.getPacketData().isMovingForward);
        buffer.writeBoolean(msg.getPacketData().isMovingBackwards);
        buffer.writeBoolean(msg.getPacketData().isMovingLeft);
        buffer.writeBoolean(msg.getPacketData().isMovingRight);
        buffer.writeUUID(msg.getEntityUUID());
    }


    /**
     * Handle.
     *
     * @param msg the msg
     * @param ctx the ctx
     */
    public static void handle(CSyncMovementIdentifiersPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get()
            .enqueueWork(
                () -> {
                    ServerPlayerEntity player = ctx.get().getSender();
                    Entity entity = player.getLevel().getEntity(msg.getEntityUUID());
                    if (entity instanceof SWEMHorseEntityBase && entity.hasPassenger(player)) {
                        SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
                        horse.getEntityData().set(SWEMHorseEntityBase.IS_MOVING_FORWARD, msg.getPacketData().isMovingForward());
                        horse.getEntityData().set(SWEMHorseEntityBase.IS_MOVING_BACKWARDS, msg.getPacketData().isMovingBackwards());
                        horse.getEntityData().set(SWEMHorseEntityBase.IS_MOVING_LEFT, msg.getPacketData().isMovingLeft());
                        horse.getEntityData().set(SWEMHorseEntityBase.IS_MOVING_RIGHT, msg.getPacketData().isMovingRight());
                    }
                });
        ctx.get().setPacketHandled(true);
    }

    public static class MovementPacketData {
        private boolean isMovingForward;
        private boolean isMovingBackwards;
        private boolean isMovingLeft;
        private boolean isMovingRight;

        public MovementPacketData(boolean isMovingFoward, boolean isMovingBackwards, boolean isMovingLeft, boolean isMovingRight) {
            this.isMovingForward = isMovingFoward;
            this.isMovingBackwards = isMovingBackwards;
            this.isMovingLeft = isMovingLeft;
            this.isMovingRight = isMovingRight;
        }

        public static MovementPacketData decode(ByteBuf buf) {
            boolean isMovingForward = buf.readBoolean();
            boolean isMovingBackwards = buf.readBoolean();
            boolean isMovingLeft = buf.readBoolean();
            boolean isMovingRight = buf.readBoolean();
            return new MovementPacketData(isMovingForward, isMovingBackwards, isMovingLeft, isMovingRight);
        }

        public boolean isMovingForward() {
            return isMovingForward;
        }

        public boolean isMovingBackwards() {
            return isMovingBackwards;
        }

        public boolean isMovingLeft() {
            return isMovingLeft;
        }

        public boolean isMovingRight() {
            return isMovingRight;
        }
    }
}
