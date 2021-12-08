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
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class HorseHungerChange {

	private ItemStack food;
	private int entityID;

	private boolean failed;

	public HorseHungerChange(int entityID, ItemStack foodIn) {
		this.food = foodIn;
		this.entityID = entityID;
		this.failed = false;
	}

	public HorseHungerChange(boolean failed) {
		this.failed = failed;
	}

	public static HorseHungerChange decode(ByteBuf buf) {
		try {
			int entityID = buf.readInt();
			ItemStack food = ((PacketBuffer) buf).readItem();
			return new HorseHungerChange(entityID, food);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("HorseHungerChange: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new HorseHungerChange(true);
		}
	}

	public static void encode(HorseHungerChange msg, PacketBuffer buffer) {
		buffer.writeInt(msg.entityID);
		buffer.writeItem(msg.food);
	}

	public static void handle(HorseHungerChange msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			((SWEMHorseEntityBase)ctx.get().getSender().level.getEntity(msg.entityID)).getNeeds().getHunger().addPoints(msg.food);
		});
		ctx.get().setPacketHandled(true);
	}
}
