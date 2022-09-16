
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
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author legenden 16/09/2022
 * <a href="https://github.com/magnushjensen">GitHub</a>
 */
public class CHorseAttackPacket {


    private UUID entityUUID;
    private int action;
    private boolean failed;

    public CHorseAttackPacket(UUID entityUUID, int action) {
        this.entityUUID = entityUUID;
        this.action = action;
    }

    public CHorseAttackPacket(boolean failed) {
        this.failed = failed;
    }

    public boolean isFailed() {
        return this.failed;
    }

    public static CHorseAttackPacket decode(ByteBuf buf) {
        try {
            PacketBuffer buffer = (PacketBuffer) buf;
            return new CHorseAttackPacket(
                buffer.readUUID(),
                buffer.readVarInt()
            );
        } catch (IndexOutOfBoundsException e) {
            SWEM.LOGGER.error(
                "CHorseAttackPacket" + ": Unexpected end of packet.\nMessage: "
                    + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()),
                e);
            return new CHorseAttackPacket(true);
        }
    }

    public static void encode(CHorseAttackPacket msg, PacketBuffer buf) {
        buf.writeUUID(msg.entityUUID);
        buf.writeVarInt(msg.action);
    }

    public static void handle(CHorseAttackPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get()
            .enqueueWork(
                () -> {
                    ServerPlayerEntity player = ctx.get().getSender();
                    if (player == null) {
                        return;
                    }

                    if (!(player.getVehicle() instanceof SWEMHorseEntityBase)) {
                        player.displayClientMessage(
                            SWEM.getSwemTextComponent()
                                .append("You are not riding a SWEM horse!"),
                            false);
                        return;
                    }

                    SWEMHorseEntityBase horse = (SWEMHorseEntityBase) player.getVehicle();

                    if (!horse.getUUID().equals(msg.entityUUID)) {
                        player.displayClientMessage(
                            SWEM.getSwemTextComponent()
                                .append("Something went wrong trying to attack!"),
                            false);
                        return;
                    }

                    if (!horse.canAttack()) {
                        player.displayClientMessage(
                            SWEM.getSwemTextComponent()
                                .append("Your horse is not ready to attack!"),
                            false);
                        return;
                    }

                    switch (msg.action) {
                        case 0: {
                            if (horse.progressionManager.getAffinityLeveling().getLevel() < 7) {
                                player.displayClientMessage(
                                    SWEM.getSwemTextComponent()
                                        .append("Your horse does not trust you enough to perform a bite!"),
                                    false);
                                return;
                            }
                            horse.startBite();
                            break;
                        }
                        case 1: {
                            if (horse.progressionManager.getAffinityLeveling().getLevel() < 8) {
                                player.displayClientMessage(
                                    SWEM.getSwemTextComponent()
                                        .append("Your horse does not trust you enough to perform a kick!"),
                                    false);
                                return;
                            }
                            horse.startKick();
                            break;
                        }
                        case 2: {
                            if (horse.progressionManager.getAffinityLeveling().getLevel() < 9) {
                                player.displayClientMessage(
                                    SWEM.getSwemTextComponent()
                                        .append("Your horse does not trust you enough to perform a stomp!"),
                                    false);
                                return;
                            }
                            horse.startStomp();
                            break;
                        }
                    }
                }
            );
        ctx.get().setPacketHandled(true);
    }


}
