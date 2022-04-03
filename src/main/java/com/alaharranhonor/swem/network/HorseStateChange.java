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
import com.alaharranhonor.swem.container.BedrollContainer;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import static com.alaharranhonor.swem.entities.HorseFlightController.*;
import static com.alaharranhonor.swem.entities.HorseFlightController.isDiving;

public class HorseStateChange {

	private int action;
	private int entityID;

	private boolean failed;

	/**
	 * Instantiates a new Horse state change.
	 *
	 * @param action   the action
	 * @param entityID the entity id
	 */
	public HorseStateChange(int action, int entityID) {
		this.action = action;
		this.entityID = entityID;
		this.failed = false;
	}

	/**
	 * Instantiates a new Horse state change.
	 *
	 * @param failed the failed
	 */
	public HorseStateChange(boolean failed) {
		this.failed = failed;
	}

	/**
	 * Decode horse state change.
	 *
	 * @param buf the buf
	 * @return the horse state change
	 */
	public static HorseStateChange decode(ByteBuf buf) {
		try {
			int action = buf.readInt();
			int entityID = buf.readInt();
			return new HorseStateChange(action, entityID);
		} catch (IndexOutOfBoundsException e) {
			SWEM.LOGGER.error("HorseStateChange: Unexpected end of packet.\nMessage: " + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()), e);
			return new HorseStateChange(true);
		}
	}

	/**
	 * Encode.
	 *
	 * @param msg    the msg
	 * @param buffer the buffer
	 */
	public static void encode(HorseStateChange msg, PacketBuffer buffer) {
		buffer.writeInt(msg.action);
		buffer.writeInt(msg.entityID);
	}

	/**
	 * Handle.
	 *
	 * @param msg the msg
	 * @param ctx the ctx
	 */
	public static void handle(HorseStateChange msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) ctx.get().getSender().level.getEntity(msg.entityID);
			switch (msg.action) {

				case 0: {
					horse.getNeeds().getThirst().incrementState();
					break;
				}
				case 1: {
					horse.progressionManager.getAffinityLeveling().desensitize(new ItemStack(SWEMItems.BELLS.get()));
					break;
				}
				case 2: {
					horse.progressionManager.getAffinityLeveling().desensitize(new ItemStack(SWEMItems.HOOLAHOOP.get()));
					break;
				}
				case 3: {
					horse.progressionManager.getAffinityLeveling().desensitize(new ItemStack(SWEMItems.POMPOM.get()));
					break;
				}
				case 4: {
					horse.progressionManager.getAffinityLeveling().desensitize(new ItemStack(SWEMItems.SHOPPING_BAG.get()));
					break;
				}
				case 5: {
					horse.progressionManager.getAffinityLeveling().desensitize(new ItemStack(SWEMItems.TARP.get()));
					break;
				}
				case 6: {
					if (ctx.get().getSender().getVehicle() == null) return;
					if (!(ctx.get().getSender().getVehicle() instanceof SWEMHorseEntityBase)) return;
					SWEMHorseEntityBase ridingHorse = (SWEMHorseEntityBase) ctx.get().getSender().getVehicle();

					if (ridingHorse.getId() != msg.entityID) return;

					NetworkHooks.openGui(ctx.get().getSender(), new INamedContainerProvider() {
						@Override
						public ITextComponent getDisplayName() {
							return new TranslationTextComponent("container.swem.bedroll");
						}

						@Nullable
						@Override
						public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
							return new BedrollContainer(p_createMenu_1_, p_createMenu_2_, msg.entityID);
						}
					}, packetBuffer -> {
						packetBuffer.writeInt(msg.entityID);
						packetBuffer.writeInt(msg.entityID);
					});
					break;
				}
				case 7: {
					horse.getEntityData().set(SWEMHorseEntityBase.JUMPING, true);
					break;
				}
				case 8: {
					horse.setIsJumping(false);
					break;
				}
				case 9: {
					horse.cycleRidingPermission();
					break;
				}
				case 10: {
					horse.setFlying(!horse.isFlying());
					if (!horse.isFlying()) {
						horse.getEntityData().set(isFloating, false);
						horse.getEntityData().set(isAccelerating, false);
						horse.getEntityData().set(isSlowingDown, false);
						horse.getEntityData().set(isTurningLeft, false);
						horse.getEntityData().set(isTurning, false);
						horse.getEntityData().set(isStillTurning, false);
						horse.getEntityData().set(didFlap, false);
						horse.getEntityData().set(isDiving, false);
						horse.getEntityData().set(isLaunching, false);
					} else {
						horse.getEntityData().set(isLaunching, true);
					}
				}

			}

		});
		ctx.get().setPacketHandled(true);
	}
}
