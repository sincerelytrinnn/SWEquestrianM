package com.alaharranhonor.swem.capability;
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
// Huge thanks to Mowzie's Mobs for making this custom player renderer
// https://www.curseforge.com/minecraft/mc-mods/mowzies-mobs

import com.alaharranhonor.swem.client.render.player.GeckoRider;
import com.alaharranhonor.swem.client.render.player.RiderFirstPersonRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class PlayerCapability {

	public interface IPlayerCapability {
		INBT writeNBT();

		void readNBT(INBT nbt);

		void tick(TickEvent.PlayerTickEvent event);

		void addedToWorld(EntityJoinWorldEvent event);

		boolean isMouseRightDown();

		void setMouseRightDown(boolean mouseRightDown);

		boolean isMouseLeftDown();

		void setMouseLeftDown(boolean mouseLeftDown);

		boolean isPrevSneaking();

		void setPrevSneaking(boolean prevSneaking);

		Vector3d getPrevMotion();

		@OnlyIn(Dist.CLIENT)
		GeckoRider.GeckoRiderThirdPerson getGeckoPlayer();
	}

	public static class PlayerCapabilityImp implements IPlayerCapability {
		public boolean mouseRightDown = false;
		public boolean mouseLeftDown = false;
		public boolean prevSneaking;
		@OnlyIn(Dist.CLIENT)
		private GeckoRider.GeckoRiderThirdPerson geckoPlayer;

		@Override
		public boolean isMouseRightDown() {
			return mouseRightDown;
		}

		@Override
		public void setMouseRightDown(boolean mouseRightDown) {
			this.mouseRightDown = mouseRightDown;
		}

		@Override
		public boolean isMouseLeftDown() {
			return mouseLeftDown;
		}

		@Override
		public void setMouseLeftDown(boolean mouseLeftDown) {
			this.mouseLeftDown = mouseLeftDown;
		}

		@Override
		public boolean isPrevSneaking() {
			return prevSneaking;
		}

		@Override
		public void setPrevSneaking(boolean prevSneaking) {
			this.prevSneaking = prevSneaking;
		}

		@Override
		public Vector3d getPrevMotion() {
			return prevMotion;
		}

		@Override
		public GeckoRider.GeckoRiderThirdPerson getGeckoPlayer() {
			return geckoPlayer;
		}

		public Vector3d prevMotion;

		@Override
		public void addedToWorld(EntityJoinWorldEvent event) {
			if (event.getWorld().isClientSide()) {
				PlayerEntity player = (PlayerEntity) event.getEntity();
				geckoPlayer = new GeckoRider.GeckoRiderThirdPerson(player);
				if (event.getEntity() == Minecraft.getInstance().player) RiderFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON = new GeckoRider.GeckoRiderFirstPerson(player);
			}
		}

		@Override
		public void tick(TickEvent.PlayerTickEvent event) {
			PlayerEntity player = event.player;

			prevMotion = player.position().subtract(new Vector3d(player.xOld, player.yOld, player.zOld));
			prevSneaking = player.isCrouching();
		}

		@Override
		public INBT writeNBT() {
			CompoundNBT compound = new CompoundNBT();


			return compound;
		}

		@Override
		public void readNBT(INBT nbt) {
			CompoundNBT compound = (CompoundNBT) nbt;
		}


	}

	public static class PlayerStorage implements Capability.IStorage<IPlayerCapability> {
		@Override
		public INBT writeNBT(Capability<IPlayerCapability> capability, IPlayerCapability instance, Direction side) {
			return instance.writeNBT();
		}

		@Override
		public void readNBT(Capability<IPlayerCapability> capability, IPlayerCapability instance, Direction side, INBT nbt) {
			instance.readNBT(nbt);
		}
	}

	public static class PlayerProvider implements ICapabilitySerializable<INBT>
	{
		@CapabilityInject(IPlayerCapability.class)
		public static final Capability<IPlayerCapability> PLAYER_CAPABILITY = null;

		private final LazyOptional<IPlayerCapability> instance = LazyOptional.of(PLAYER_CAPABILITY::getDefaultInstance);

		@Override
		public INBT serializeNBT() {
			return PLAYER_CAPABILITY.getStorage().writeNBT(PLAYER_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("Lazy optional must not be empty")), null);
		}

		@Override
		public void deserializeNBT(INBT nbt) {
			PLAYER_CAPABILITY.getStorage().readNBT(PLAYER_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("Lazy optional must not be empty")), null, nbt);
		}

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return cap == PLAYER_CAPABILITY ? instance.cast() : LazyOptional.empty();
		}
	}

}
