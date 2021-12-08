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
import com.alaharranhonor.swem.tileentity.TackBoxTE;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncEntityIdToClient {

	private int posX;
	private int posY;
	private int posZ;
	private int entityId;
	private boolean failed;

	public SyncEntityIdToClient(int entityID, int posX, int posY, int posZ) {
		this.entityId = entityID;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.failed = false;
	}

	public SyncEntityIdToClient(boolean failed) {
		this.failed = failed;
	}

	public static SyncEntityIdToClient decode(ByteBuf buf) {
		try {
			int entityID = buf.readInt();
			int posX = buf.readInt();
			int posY = buf.readInt();
			int posZ = buf.readInt();
			return new SyncEntityIdToClient(entityID, posX, posY, posZ);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("SyncEntityIdToClient: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new SyncEntityIdToClient(true);
		}
	}

	public static void encode(SyncEntityIdToClient msg, PacketBuffer buffer) {
		buffer.writeInt(msg.entityId);
		buffer.writeInt(msg.posX);
		buffer.writeInt(msg.posY);
		buffer.writeInt(msg.posZ);
	}

	public static void handle(SyncEntityIdToClient msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ClientWorld world = Minecraft.getInstance().level;
			TileEntity tile = world.getBlockEntity(new BlockPos(msg.posX, msg.posY, msg.posZ));
			if (tile instanceof TackBoxTE) {
				TackBoxTE te = (TackBoxTE) tile;
				te.getTileData().putInt("horseID", msg.entityId);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
