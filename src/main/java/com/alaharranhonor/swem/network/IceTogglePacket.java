
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
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class IceTogglePacket {
    private boolean failed;

    public IceTogglePacket() {
    }

    public IceTogglePacket(boolean failed) {
        this.failed = failed;
    }

    public boolean isFailed() {
        return this.failed;
    }

    public static IceTogglePacket decode(ByteBuf buf) {
        try {
            return new IceTogglePacket();
        } catch (IndexOutOfBoundsException e) {
            SWEM.LOGGER.error(
                "IceTogglePacket" + ": Unexpected end of packet.\nMessage: "
                    + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()),
                e);
            return new IceTogglePacket(true);
        }
    }

    public static void encode(IceTogglePacket msg, PacketBuffer buf) {
    }

    public static void handle(IceTogglePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get()
            .enqueueWork(
                () -> {
                    ServerPlayerEntity player = ctx.get().getSender();
                    if (player.getVehicle() instanceof SWEMHorseEntityBase) {
                        ((SWEMHorseEntityBase) player.getVehicle()).toggleIce();
                        return;
                    }

                    if (player.getPersistentData().contains("blockIceEffect")) {
                        player.getPersistentData().remove("blockIceEffect");
                        player.displayClientMessage(new TranslationTextComponent("text.swem.status.ice.off"), true);
                    } else {
                        player.getPersistentData().putBoolean("blockIceEffect", true);
                        player.displayClientMessage(new TranslationTextComponent("text.swem.status.ice.on"), true);
                    }
                }
            );
        ctx.get().setPacketHandled(true);
    }

}


