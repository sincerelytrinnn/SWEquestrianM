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

public class CHorseJumpPacket {
	private int entityID;
	private float jumpHeight;

	private boolean failed;

	/**
	 * Instantiates a new C horse jump packet.
	 *
	 * @param entityID   the entity id
	 * @param jumpHeight the jump height
	 */
	public CHorseJumpPacket(int entityID, float jumpHeight) {
		this.entityID = entityID;
		this.jumpHeight = jumpHeight;
		this.failed = false;
	}

	/**
	 * Instantiates a new C horse jump packet.
	 *
	 * @param failed the failed
	 */
	public CHorseJumpPacket(boolean failed) {
		this.failed = failed;
	}

	/**
	 * Decode c horse jump packet.
	 *
	 * @param buf the buf
	 * @return the c horse jump packet
	 */
	public static CHorseJumpPacket decode(ByteBuf buf) {
		try {
			int entityID = buf.readInt();
			float jumpHeight = buf.readFloat();
			return new CHorseJumpPacket(entityID, jumpHeight);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("CHorseJumpPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new CHorseJumpPacket(true);
		}
	}

	/**
	 * Encode.
	 *
	 * @param msg    the msg
	 * @param buffer the buffer
	 */
	public static void encode(CHorseJumpPacket msg, PacketBuffer buffer) {
		buffer.writeInt(msg.entityID);
		buffer.writeFloat(msg.jumpHeight);
	}

	/**
	 * Handle.
	 *
	 * @param msg the msg
	 * @param ctx the ctx
	 */
	public static void handle(CHorseJumpPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {

			Entity entity = ctx.get().getSender().level.getEntity(msg.entityID);
			if (!(entity instanceof SWEMHorseEntityBase)) {
				return;
			}
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
			if (msg.jumpHeight > 0) {
				if (msg.jumpHeight > 5) {
					horse.getEntityData().set(SWEMHorseEntityBase.JUMP_ANIM_TIMER, 36);
				} else if (msg.jumpHeight > 4) {
					horse.getEntityData().set(SWEMHorseEntityBase.JUMP_ANIM_TIMER, 28);
				} else if (msg.jumpHeight > 3) {
					horse.getEntityData().set(SWEMHorseEntityBase.JUMP_ANIM_TIMER, 24);
				} else if (msg.jumpHeight > 2) {
					horse.getEntityData().set(SWEMHorseEntityBase.JUMP_ANIM_TIMER, 30);
				} else {
					horse.getEntityData().set(SWEMHorseEntityBase.JUMP_ANIM_TIMER, 21);
				}
			}


			SWEMPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> horse), new SHorseJumpPacket(msg.entityID, msg.jumpHeight));
		});
		ctx.get().setPacketHandled(true);
	}
}
