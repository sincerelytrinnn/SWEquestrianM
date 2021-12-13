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
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateHorseInventoryMessage {
	private int entityId;
	private int slotIndex;
	private ItemStack stack;

	private boolean failed;

	public UpdateHorseInventoryMessage(int entityId, int slotIndex, ItemStack stack) {
		this.entityId = entityId;
		this.slotIndex = slotIndex;
		this.stack = stack;
		this.failed = false;
	}

	public UpdateHorseInventoryMessage(boolean failed) {
		this.failed = failed;
	}

	public static UpdateHorseInventoryMessage decode(ByteBuf buf) {
		try {
			int entityId = buf.readInt();
			int slotIndex = buf.readInt();
			ItemStack stack = ((PacketBuffer) buf).readItem();
			return new UpdateHorseInventoryMessage(entityId, slotIndex, stack);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("UpdateHorseInventoryMessage: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new UpdateHorseInventoryMessage(true);
		}
	}

	public static void encode(UpdateHorseInventoryMessage msg, PacketBuffer buf) {
		buf.writeInt(msg.entityId);
		buf.writeInt(msg.slotIndex);
		buf.writeItem(msg.stack);
	}

	public static void handle(UpdateHorseInventoryMessage msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Entity entity = Minecraft.getInstance().level.getEntity(msg.entityId);
			if (entity instanceof SWEMHorseEntityBase) {
				SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
				horse.getHorseInventory().setItem(msg.slotIndex, msg.stack);
			}
		});
		ctx.get().setPacketHandled(true);
	}


}
