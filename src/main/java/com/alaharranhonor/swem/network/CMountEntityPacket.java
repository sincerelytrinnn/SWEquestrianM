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
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CMountEntityPacket {

	private int targetId;
	private boolean failed;

	public CMountEntityPacket(int targetId) {
		this.targetId = targetId;
		this.failed = failed;
	}

	public CMountEntityPacket(Entity target) {
		this.targetId = target.getId();
		this.failed = false;
	}

	public CMountEntityPacket(boolean failed) {
		this.failed = failed;
	}

	public static CMountEntityPacket decode(ByteBuf buf) {
		try {
			int targetId = buf.readInt();
			return new CMountEntityPacket(targetId);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("CMountEntityPacket: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new CMountEntityPacket(true);
		}
	}

	public static void encode(CMountEntityPacket msg, PacketBuffer buffer) {
		buffer.writeInt(msg.targetId);
	}

	private static boolean tryMounting(Entity rider, Entity mount) {
		if (!(rider instanceof LivingEntity)) {
			return false;
		}

		return rider.startRiding(mount);
	}

	public static void handle(CMountEntityPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			Entity target = player.level.getEntity(msg.targetId);
			if (target == null) {
				SWEM.LOGGER.warn("Could not find entity with id: " + msg.targetId + " requested by " + player.getName().getString());
				return;
			}

			// Can only mount friendly creatures.
			if (!target.getType().getCategory().isFriendly())
				return;

			// Cannot mount other players
			if (target instanceof PlayerEntity)
				return;


			// If passenger is mounted, mount the target behind you.
			if (player.isPassenger()) {
				Entity mount = player.getVehicle();
				if (mount instanceof SWEMHorseEntityBase) {
					if (tryMounting(target, mount))
						return;
				}
			}


			// If player is not passenger, dismount all passengers.
			if (target instanceof SWEMHorseEntityBase) {
				for (Entity passenger : target.getPassengers()) {
					if (passenger instanceof PlayerEntity && passenger != player)
						return;

				}
				target.ejectPassengers();
			}



		});
		ctx.get().setPacketHandled(true);
	}
}
