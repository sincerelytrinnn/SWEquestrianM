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

import java.util.function.Supplier;

public class AddJumpXPMessage {

	private float xpToAdd;

	private int entityID;

	private boolean failed;

	/**
	 * Instantiates a new Add jump xp message.
	 *
	 * @param xpToAdd  the xp to add
	 * @param entityID the entity id
	 */
	public AddJumpXPMessage(float xpToAdd, int entityID) {
		this.xpToAdd = xpToAdd;
		this.entityID = entityID;
		this.failed = false;
	}

	/**
	 * Instantiates a new Add jump xp message.
	 *
	 * @param failed the failed
	 */
	public AddJumpXPMessage(boolean failed) {
		this.failed = failed;
	}

	/**
	 * Decode add jump xp message.
	 *
	 * @param buf the buf
	 * @return the add jump xp message
	 */
	public static AddJumpXPMessage decode(ByteBuf buf) {
		try {
			float xpToAdd = buf.readFloat();
			int entityID = buf.readInt();
			return new AddJumpXPMessage(xpToAdd, entityID);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("AddJumpXPMessage: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new AddJumpXPMessage(true);
		}
	}

	/**
	 * Encode.
	 *
	 * @param msg    the msg
	 * @param buffer the buffer
	 */
	public static void encode(AddJumpXPMessage msg, PacketBuffer buffer) {
		buffer.writeFloat(msg.xpToAdd);
		buffer.writeInt(msg.entityID);
	}

	/**
	 * Handle.
	 *
	 * @param msg the msg
	 * @param ctx the ctx
	 */
	public static void handle(AddJumpXPMessage msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			Entity entity = player.level.getEntity(msg.entityID);
			if (entity instanceof SWEMHorseEntityBase) {
				SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
				horse.progressionManager.getJumpLeveling().addXP(msg.xpToAdd);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
