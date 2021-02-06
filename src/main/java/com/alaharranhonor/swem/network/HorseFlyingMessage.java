package com.alaharranhonor.swem.network;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.initialization.SWEMItems;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class HorseFlyingMessage {

	private int action;

	private boolean failed;

	public HorseFlyingMessage(int action) {
		this.action = action;
		this.failed = false;
	}

	public HorseFlyingMessage(boolean failed) {
		this.failed = failed;
	}

	public static HorseFlyingMessage decode(ByteBuf buf) {
		try {
			int action = buf.readInt();
			return new HorseFlyingMessage(action);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("HorseFlyingMessage: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new HorseFlyingMessage(true);
		}
	}

	public static void encode(HorseFlyingMessage msg, PacketBuffer buffer) {
		buffer.writeInt(msg.action);
	}

	public static void handle(HorseFlyingMessage msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			if (player.isPassenger() && player.getRidingEntity() instanceof SWEMHorseEntityBase) {
				SWEMHorseEntityBase horse = (SWEMHorseEntityBase) player.getRidingEntity();
				switch (msg.action) {

					case 0: {
						break;
					}
					case 1: {
						if (horse.isFlying()) {
							horse.setMotion(new Vector3d(0.0D, 0.5D, 0.0D));
						}
						break;
					}
					case 2: {
						if (horse.canFly()) {
							horse.setFlying(!horse.isFlying());
						}
						break;
					}
					case 3: {
						if (horse.isFlying()) {
							Vector3d motion = horse.getLookVec().scale(1.5);
							horse.setMotion(motion.x, 0.0d, motion.z);
						}
						break;
					}
					case 4: {
						if (horse.isFlying()) {
							horse.setMotion(new Vector3d(0.0D, -0.5D, 0.0D));
						}
					}
					case 5: {
						if (horse.isFlying()) {
							horse.setRotation((horse.rotationYaw - 5) % 360.0F, horse.rotationPitch);
						}
					}
					case 6: {
						if (horse.isFlying()) {
							horse.setRotation((horse.rotationYaw + 5) % 360.0F, horse.rotationPitch);
						}
					}

				}
			}


		});
		ctx.get().setPacketHandled(true);
	}
}
