package com.alaharranhonor.swem.client.render.player;
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

import com.alaharranhonor.swem.capability.CapabilityHandler;
import com.alaharranhonor.swem.capability.PlayerCapability;
import com.alaharranhonor.swem.client.model.ModelGeckoRiderFirstPerson;
import com.alaharranhonor.swem.client.model.ModelGeckoRiderThirdPerson;
import com.alaharranhonor.swem.client.tools.geckolib.CustomAnimatedGeoModel;
import com.alaharranhonor.swem.client.tools.geckolib.CustomAnimationController;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;

public abstract class GeckoRider implements IAnimatable, IAnimationTickable {

	protected IGeoRenderer<GeckoRider> renderer;
	protected CustomAnimatedGeoModel<GeckoRider> model;

	private int tickTimer = 0;

	private PlayerEntity player;
	private AnimationFactory factory = new AnimationFactory(this);
	public static final String THIRD_PERSON_CONTROLLER_NAME = "thirdPersonAnimation";
	public static final String FIRST_PERSON_CONTROLLER_NAME = "firstPersonAnimation";
	public AnimationBuilder animationBuilder;
	public String animationName;


	public enum Perspective {
		FIRST_PERSON,
		THIRD_PERSON
	}

	public GeckoRider(PlayerEntity player) {
		this.player = player;
		setup(player);
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new CustomAnimationController(this, getControllerName(), 4, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	public PlayerEntity getPlayer() {
		return player;
	}

	@Override
	public void tick() {
		tickTimer++;
	}

	@Override
	public int tickTimer() {
		return tickTimer;
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> e) {
		e.getController().transitionLengthTicks = 0;
		PlayerEntity player = getPlayer();
		if (player == null) {
			return PlayState.STOP;
		}

		if (this.player.getVehicle() instanceof SWEMHorseEntityBase) {
			if (e.getController().getCurrentAnimation() != null) {
				animationName = e.getController().getCurrentAnimation().animationName;
			}
			e.getController().setAnimation(animationBuilder);
			return PlayState.CONTINUE;
		}

		e.getController().setAnimation(new AnimationBuilder().addAnimation("IdleGround"));
		return PlayState.CONTINUE;

	}

	@Nullable
	public static GeckoRider getRiderPlayer(PlayerEntity player, Perspective perspective) {
		if (perspective == Perspective.FIRST_PERSON) return RiderFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON;
		PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, PlayerCapability.PlayerProvider.PLAYER_CAPABILITY);
		if (playerCapability != null) {
			return playerCapability.getGeckoPlayer();
		}
		return null;
	}

	public static CustomAnimationController<GeckoRider> getAnimationController(PlayerEntity player, Perspective perspective) {
		PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, PlayerCapability.PlayerProvider.PLAYER_CAPABILITY);
		if (playerCapability != null) {
			GeckoRider geckoRider;
			if (perspective == Perspective.FIRST_PERSON) geckoRider = RiderFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON;
			else geckoRider = playerCapability.getGeckoPlayer();
			if (geckoRider != null) {
				String name = perspective == Perspective.FIRST_PERSON ? FIRST_PERSON_CONTROLLER_NAME : THIRD_PERSON_CONTROLLER_NAME;
				return (CustomAnimationController<GeckoRider>) GeckoLibUtil.getControllerForID(geckoRider.getFactory(), player.getUUID().hashCode(), name);
			}
		}
		return null;
	}

	public IGeoRenderer<GeckoRider> getPlayerRenderer() {
		return renderer;
	}

	public CustomAnimatedGeoModel<GeckoRider> getModel() {
		return model;
	}

	public abstract String getControllerName();

	public abstract Perspective getPerspective();

	public abstract void setup(PlayerEntity player);

	public static class GeckoRiderFirstPerson extends GeckoRider {
		public GeckoRiderFirstPerson(PlayerEntity player) {
			super(player);
		}

		@Override
		public String getControllerName() {
			return FIRST_PERSON_CONTROLLER_NAME;
		}

		@Override
		public Perspective getPerspective() {
			return Perspective.FIRST_PERSON;
		}

		@Override
		public void setup(PlayerEntity player) {
			ModelGeckoRiderFirstPerson modelGeckoRider = new ModelGeckoRiderFirstPerson();
			model = modelGeckoRider;
			model.resourceForModelId((AbstractClientPlayerEntity) player);
			RiderFirstPersonRenderer geckoRenderer = new RiderFirstPersonRenderer(Minecraft.getInstance(), modelGeckoRider);
			renderer = geckoRenderer;
			if (!geckoRenderer.getModelsToLoad().containsKey(this.getClass())) {
				geckoRenderer.getModelsToLoad().put(this.getClass(), geckoRenderer);
			}
		}
	}

	public static class GeckoRiderThirdPerson extends GeckoRider {
		public GeckoRiderThirdPerson(PlayerEntity player) {
			super(player);
		}

		@Override
		public String getControllerName() {
			return THIRD_PERSON_CONTROLLER_NAME;
		}

		@Override
		public Perspective getPerspective() {
			return Perspective.THIRD_PERSON;
		}

		@Override
		public void setup(PlayerEntity player) {
			ModelGeckoRiderThirdPerson modelGeckoRider = new ModelGeckoRiderThirdPerson();
			model = modelGeckoRider;
			model.resourceForModelId((AbstractClientPlayerEntity) player);
			RiderRenderPlayer geckoRenderer = new RiderRenderPlayer(Minecraft.getInstance().getEntityRenderDispatcher(), modelGeckoRider);
			renderer = geckoRenderer;
			if (!geckoRenderer.getModelsToLoad().containsKey(this.getClass())) {
				geckoRenderer.getModelsToLoad().put(this.getClass(), geckoRenderer);
			}
		}
	}
}
