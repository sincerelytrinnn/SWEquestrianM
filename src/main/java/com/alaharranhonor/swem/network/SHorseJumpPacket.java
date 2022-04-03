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
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class SHorseJumpPacket {
	private int entityID;
	private float jumpHeight;

	private boolean failed;

	/**
	 * Instantiates a new S horse jump packet.
	 *
	 * @param entityID   the entity id
	 * @param jumpHeight the jump height
	 */
	public SHorseJumpPacket(int entityID, float jumpHeight) {
		this.entityID = entityID;
		this.jumpHeight = jumpHeight;
		this.failed = false;
	}

	/**
	 * Instantiates a new S horse jump packet.
	 *
	 * @param failed the failed
	 */
	public SHorseJumpPacket(boolean failed) {
		this.failed = failed;
	}

	/**
	 * Decode s horse jump packet.
	 *
	 * @param buf the buf
	 * @return the s horse jump packet
	 */
	public static SHorseJumpPacket decode(ByteBuf buf) {
		try {
			int entityID = buf.readInt();
			float jumpHeight = buf.readFloat();
			return new SHorseJumpPacket(entityID, jumpHeight);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("CHorseJumpPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new SHorseJumpPacket(true);
		}
	}

	/**
	 * Encode.
	 *
	 * @param msg    the msg
	 * @param buffer the buffer
	 */
	public static void encode(SHorseJumpPacket msg, PacketBuffer buffer) {
		buffer.writeInt(msg.entityID);
		buffer.writeFloat(msg.jumpHeight);
	}

	/**
	 * Handle.
	 *
	 * @param msg the msg
	 * @param ctx the ctx
	 */
	public static void handle(SHorseJumpPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ClientPlayerEntity player = Minecraft.getInstance().player;
			Entity entity = player.level.getEntity(msg.entityID);
			if (!(entity instanceof SWEMHorseEntityBase)) {
				return;
			}
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;

			horse.jumpHeight = msg.jumpHeight;
		});
		ctx.get().setPacketHandled(true);
	}
}
