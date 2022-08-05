package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.container.SaddlebagAndBedrollContainer;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class SContainerPacket {
    private int action;
    private boolean failed;

    /**
     * Instantiates a new S container packet.
     *
     * @param action the action
     */
    public SContainerPacket(int action) {
        this.action = action;
        this.failed = false;
    }

    /**
     * Instantiates a new S container packet.
     *
     * @param failed the failed
     */
    public SContainerPacket(boolean failed) {
        this.failed = failed;
    }

    /**
     * Decode s container packet.
     *
     * @param buf the buf
     * @return the s container packet
     */
    public static SContainerPacket decode(ByteBuf buf) {
        try {
            int action = buf.readInt();
            return new SContainerPacket(action);
        } catch (IndexOutOfBoundsException e) {
            SWEM.LOGGER.error(
                    "SContainerPacket: Unexpected end of packet.\nMessage: "
                            + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()),
                    e);
            return new SContainerPacket(true);
        }
    }

    /**
     * Encode.
     *
     * @param msg    the msg
     * @param buffer the buffer
     */
    public static void encode(SContainerPacket msg, PacketBuffer buffer) {
        buffer.writeInt(msg.action);
    }

    /**
     * Handle.
     *
     * @param msg the msg
     * @param ctx the ctx
     */
    public static void handle(SContainerPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get()
                .enqueueWork(
                        () -> {
                            ServerPlayerEntity serverPlayer = ctx.get().getSender();
                            Entity entity = ctx.get().getSender().getVehicle();

                            if (entity instanceof SWEMHorseEntityBase) {
                                SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
                                if (horse.getSaddlebag() != ItemStack.EMPTY) {
                                    if (msg.action == 0) {
                                        NetworkHooks.openGui(
                                                serverPlayer,
                                                new INamedContainerProvider() {
                                                    @Override
                                                    public ITextComponent getDisplayName() {
                                                        return new TranslationTextComponent("container.swem.saddlebag");
                                                    }

                                                    @Nullable
                                                    @Override
                                                    public Container createMenu(
                                                            int p_createMenu_1_,
                                                            PlayerInventory p_createMenu_2_,
                                                            PlayerEntity p_createMenu_3_) {
                                                        return new SaddlebagAndBedrollContainer(
                                                                p_createMenu_1_, p_createMenu_2_, horse.getId());
                                                    }
                                                },
                                                buffer -> {
                                                    buffer.writeInt(horse.getId());
                                                    buffer.writeInt(horse.getId());
                                                });
                                    }
                                }
                            }
                        });
        ctx.get().setPacketHandled(true);
    }
}

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

/*


 */
