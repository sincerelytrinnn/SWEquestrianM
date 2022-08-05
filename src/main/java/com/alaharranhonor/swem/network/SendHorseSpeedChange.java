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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SendHorseSpeedChange {

    private int action;
    private int entityID;

    private boolean failed;

    /**
     * Instantiates a new Send horse speed change.
     *
     * @param action   the action
     * @param entityID the entity id
     */
    public SendHorseSpeedChange(int action, int entityID) {
        this.action = action;
        this.entityID = entityID;
        this.failed = false;
    }

    /**
     * Instantiates a new Send horse speed change.
     *
     * @param failed the failed
     */
    public SendHorseSpeedChange(boolean failed) {
        this.failed = failed;
    }

    /**
     * Decode send horse speed change.
     *
     * @param buf the buf
     * @return the send horse speed change
     */
    public static SendHorseSpeedChange decode(ByteBuf buf) {
        try {
            int action = buf.readInt();
            int entityID = buf.readInt();
            return new SendHorseSpeedChange(action, entityID);
        } catch (IndexOutOfBoundsException e) {
            SWEM.LOGGER.error(
                    "SendHorseSpeedChange: Unexpected end of packet.\nMessage: "
                            + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()),
                    e);
            return new SendHorseSpeedChange(true);
        }
    }

    /**
     * Encode.
     *
     * @param msg    the msg
     * @param buffer the buffer
     */
    public static void encode(SendHorseSpeedChange msg, PacketBuffer buffer) {
        buffer.writeInt(msg.action);
        buffer.writeInt(msg.entityID);
    }

    /**
     * Handle.
     *
     * @param msg the msg
     * @param ctx the ctx
     */
    public static void handle(SendHorseSpeedChange msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get()
                .enqueueWork(
                        () -> {
                            PlayerEntity player = ctx.get().getSender();
                            if (player == null || player.getVehicle() == null) return;
                            Entity entity = player.level.getEntity(msg.entityID);
                            if (entity instanceof SWEMHorseEntityBase && player.getVehicle().equals(entity)) {
                                SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
                                if (msg.action == 2) {
                                    SWEMHorseEntityBase.HorseSpeed oldSpeed = horse.currentSpeed;
                                    horse.currentSpeed = SWEMHorseEntityBase.HorseSpeed.WALK;
                                    horse.updateSelectedSpeed(oldSpeed);
                                } else if (msg.action == 1) {
                                    if (!horse.isInWater()) {
                                        horse.incrementSpeed();
                                    }
                                } else if (msg.action == 0) {
                                    if (!horse.isInWater()) {
                                        horse.decrementSpeed();
                                    }
                                }
                            }
                        });
        ctx.get().setPacketHandled(true);
    }
}
