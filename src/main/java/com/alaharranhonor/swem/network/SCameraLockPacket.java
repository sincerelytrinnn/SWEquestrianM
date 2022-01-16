package com.alaharranhonor.swem.network;


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

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.UUID;
import java.util.function.Supplier;

public class SCameraLockPacket {
	private int horseId;
	private float yRot;
	private float xRot;
	private boolean failed;

	public SCameraLockPacket(int horseId, float yRot, float xRot) {
		this.horseId = horseId;
		this.yRot = yRot;
		this.xRot = xRot;
		this.failed = false;
	}

	public SCameraLockPacket(boolean failed) {
		this.failed = failed;
	}

	public static SCameraLockPacket decode(ByteBuf buf) {
		try {
			int horseUUID = buf.readInt();
			float yRot = buf.readFloat();
			float xRot = buf.readFloat();

			return new SCameraLockPacket(horseUUID, yRot, xRot);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("SCameraLockPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new SCameraLockPacket(true);
		}
	}

	public static void encode(SCameraLockPacket msg, PacketBuffer buffer) {
		buffer.writeInt(msg.horseId);
		buffer.writeFloat(msg.yRot);
		buffer.writeFloat(msg.xRot);
	}

	public static void handle(SCameraLockPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Entity entity = Minecraft.getInstance().level.getEntity(msg.horseId);
			if (!(entity instanceof SWEMHorseEntityBase)) {
				return;
			}
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;

			horse.setLockedRotations(msg.xRot, msg.yRot);
		});
		ctx.get().setPacketHandled(true);
	}
}