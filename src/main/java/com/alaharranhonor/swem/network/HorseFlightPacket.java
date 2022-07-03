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
import com.alaharranhonor.swem.entities.HorseFlightController;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class HorseFlightPacket {
    private int action;
    private int entityID;

    private boolean failed;

    /**
     * Instantiates a new Horse flight packet.
     *
     * @param action   the action
     * @param entityID the entity id
     */
    public HorseFlightPacket(int action, int entityID) {
        this.action = action;
        this.entityID = entityID;
        this.failed = false;
    }

    /**
     * Instantiates a new Horse flight packet.
     *
     * @param failed the failed
     */
    public HorseFlightPacket(boolean failed) {
        this.failed = failed;
    }

    /**
     * Decode horse flight packet.
     *
     * @param buf the buf
     * @return the horse flight packet
     */
    public static HorseFlightPacket decode(ByteBuf buf) {
        try {
            int action = buf.readInt();
            int entityID = buf.readInt();
            return new HorseFlightPacket(action, entityID);
        } catch (IndexOutOfBoundsException e) {
            SWEM.LOGGER.error(
                    "HorseFlightPacket: Unexpected end of packet.\nMessage: "
                            + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()),
                    e);
            return new HorseFlightPacket(true);
        }
    }

    /**
     * Encode.
     *
     * @param msg    the msg
     * @param buffer the buffer
     */
    public static void encode(HorseFlightPacket msg, PacketBuffer buffer) {
        buffer.writeInt(msg.action);
        buffer.writeInt(msg.entityID);
    }

    /**
     * Handle.
     *
     * @param msg the msg
     * @param ctx the ctx
     */
    public static void handle(HorseFlightPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get()
                .enqueueWork(
                        () -> {
                            SWEMHorseEntityBase horse =
                                    (SWEMHorseEntityBase) ctx.get().getSender().level.getEntity(msg.entityID);
                            switch (msg.action) {
                                case 0: { // set floating
                                    horse.getEntityData().set(HorseFlightController.isFloating, true);
                                    break;
                                }
                                case 1: {
                                    horse.getEntityData().set(HorseFlightController.isFloating, false);
                                    horse.getEntityData().set(HorseFlightController.isAccelerating, true);
                                    break;
                                }
                                case 2: {
                                    horse.getEntityData().set(HorseFlightController.isFloating, false);
                                    horse.getEntityData().set(HorseFlightController.isAccelerating, false);
                                    horse.getEntityData().set(HorseFlightController.isSlowingDown, true);
                                    break;
                                }
                                case 3: {
                                    horse.getEntityData().set(HorseFlightController.isTurningLeft, true);
                                    horse.getEntityData().set(HorseFlightController.isTurning, true);
                                    horse.getEntityData().set(HorseFlightController.isStillTurning, true);
                                    break;
                                }
                                case 4: {
                                    horse.getEntityData().set(HorseFlightController.isTurningLeft, false);
                                    horse.getEntityData().set(HorseFlightController.isTurning, true);
                                    horse.getEntityData().set(HorseFlightController.isStillTurning, true);
                                    break;
                                }
                                case 5: {
                                    horse.getEntityData().set(HorseFlightController.isFloating, false);
                                    horse.getEntityData().set(HorseFlightController.didFlap, true);
                                    break;
                                }
                                case 6: {
                                    horse.getEntityData().set(HorseFlightController.isStillTurning, false);
                                    break;
                                }
                                case 7: {
                                    horse.getEntityData().set(HorseFlightController.isTurning, false);
                                    horse.getEntityData().set(HorseFlightController.isDiving, true);
                                    break;
                                }
                                case 8: {
                                    horse.getEntityData().set(HorseFlightController.isDiving, false);
                                    break;
                                }
                                case 9: {
                                    horse.getEntityData().set(HorseFlightController.isFloating, false);
                                    horse.getEntityData().set(HorseFlightController.isAccelerating, false);
                                    horse.getEntityData().set(HorseFlightController.isSlowingDown, true);
                                    horse.getEntityData().set(HorseFlightController.isStillSlowingDown, true);
                                    break;
                                }
                                case 10: {
                                    horse.getEntityData().set(HorseFlightController.isStillSlowingDown, false);
                                    break;
                                }
                            }
                        });
        ctx.get().setPacketHandled(true);
    }
}
