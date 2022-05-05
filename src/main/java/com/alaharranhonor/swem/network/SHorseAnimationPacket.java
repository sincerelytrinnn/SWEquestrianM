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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class SHorseAnimationPacket {
	private int entityID;
	private int action;

	private boolean failed;

	/**
	 * Instantiates a new S horse animation packet.
	 *
	 * @param entityID the entity id
	 * @param action   the action
	 */
	public SHorseAnimationPacket(int entityID, int action) {
		this.entityID = entityID;
		this.action = action;
		this.failed = false;
	}

	/**
	 * Instantiates a new S horse animation packet.
	 *
	 * @param failed the failed
	 */
	public SHorseAnimationPacket(boolean failed) {
		this.failed = failed;
	}

	/**
	 * Decode s horse animation packet.
	 *
	 * @param buf the buf
	 * @return the s horse animation packet
	 */
	public static SHorseAnimationPacket decode(ByteBuf buf) {
		try {
			int entityID = buf.readInt();
			int action = buf.readInt();
			return new SHorseAnimationPacket( entityID, action);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("SHorseAnimationPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new SHorseAnimationPacket(true);
		}
	}

	/**
	 * Encode.
	 *
	 * @param msg    the msg
	 * @param buffer the buffer
	 */
	public static void encode(SHorseAnimationPacket msg, PacketBuffer buffer) {
		buffer.writeInt(msg.entityID);
		buffer.writeInt(msg.action);
	}

	/**
	 * Handle.
	 *
	 * @param msg the msg
	 * @param ctx the ctx
	 */
	public static void handle(SHorseAnimationPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			Entity entity = player.level.getEntity(msg.entityID);
			if (!(entity instanceof SWEMHorseEntityBase)) {
				return;
			}
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
			horse.setStandingTimer(142);
			switch (msg.action) {
				case 1: {
					horse.standAnimationVariant = 1;
					horse.standAnimationTick = 42;
					break;
				}
				case 2: {
					horse.standAnimationVariant = 2;
					horse.standAnimationTick = 42;
					break;
				}
				case 3: { // Backwards walking packet.
					SWEMPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> horse), new CHorseAnimationPacket(horse.getId(), 3));
					break;
				}
				case 4: { // Stop Backwards walking packet.
					SWEMPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> horse), new CHorseAnimationPacket(horse.getId(), 4));
					break;
				}
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
