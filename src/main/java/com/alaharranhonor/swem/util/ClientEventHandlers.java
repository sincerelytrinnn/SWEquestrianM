package com.alaharranhonor.swem.util;


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
import com.alaharranhonor.swem.armor.*;
import com.alaharranhonor.swem.blocks.HalfBarrelBlock;
import com.alaharranhonor.swem.blocks.WaterTroughBlock;
import com.alaharranhonor.swem.entities.RiderEntity;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.entity.render.*;
import com.alaharranhonor.swem.gui.*;
import com.alaharranhonor.swem.items.SWEMSpawnEggItem;
import com.alaharranhonor.swem.particle.*;
import com.alaharranhonor.swem.util.registry.*;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

import java.util.HashMap;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandlers {


	public static KeyBinding[] keyBindings;

	/**
	 * On client setup.
	 *
	 * @param event the event
	 */
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event)
	{
		Atlases.addWoodType(SWEM.WHITEWASH_WT);
		DeferredWorkQueue.runLater(ClientEventHandlers::initLate);
		registerRenderers(event);
		setRenderLayers();
		registerKeybinds();
	}

	/**
	 * On particle factory register.
	 *
	 * @param event the event
	 */
	@SubscribeEvent
	public static void onParticleFactoryRegister(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particleEngine.register(SWEMParticles.BAD.get(), BadParticle.Factory::new);
		Minecraft.getInstance().particleEngine.register(SWEMParticles.ECH.get(), EchParticle.Factory::new);
		Minecraft.getInstance().particleEngine.register(SWEMParticles.MEH.get(), MehParticle.Factory::new);
		Minecraft.getInstance().particleEngine.register(SWEMParticles.YAY.get(), YayParticle.Factory::new);
		Minecraft.getInstance().particleEngine.register(SWEMParticles.WOOT.get(), WootParticle.Factory::new);

	}

	/**
	 * Init late.
	 */
	public static void initLate() {
		ScreenManager.register(SWEMContainers.SWEM_HORSE_CONTAINER.get(), SWEMHorseInventoryScreen::new);
		ScreenManager.register(SWEMContainers.TACKBOX_CONTAINER.get(), TackBoxDefaultScreen::new);
		ScreenManager.register(SWEMContainers.CANTAZARITE_ANVIL_CONTAINER.get(), CantazariteAnvilScreen::new);
		ScreenManager.register(SWEMContainers.SADDLE_BAG_AND_BEDROLL_CONTAINER.get(), SaddlebagAndBedrollScreen::new);
		ScreenManager.register(SWEMContainers.LOCKER_CONTAINER.get(), LockerScreen::new);
		ScreenManager.register(SWEMContainers.JUMP_CONTAINER.get(), JumpScreen::new);

		ItemModelsProperties.register(SWEMItems.AMETHYST_BOW.get(), new ResourceLocation("pull"), (p_239429_0_, p_239429_1_, p_239429_2_) -> {
			if (p_239429_2_ == null) {
				return 0.0F;
			} else {
				return p_239429_2_.getUseItem() != p_239429_0_ ? 0.0F : (float)(p_239429_0_.getUseDuration() - p_239429_2_.getUseItemRemainingTicks()) / 20.0F;
			}
		});
		ItemModelsProperties.register(SWEMItems.AMETHYST_BOW.get(), new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isUsingItem() && p_239428_2_.getUseItem() == p_239428_0_ ? 1.0F : 0.0F);
		ItemModelsProperties.register(SWEMItems.AMETHYST_SHIELD.get(), new ResourceLocation("blocking"), (p_239421_0_, p_239421_1_, p_239421_2_) -> {
			return p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == p_239421_0_ ? 1.0F : 0.0F;
		});
	}

	/**
	 * Register renderers.
	 *
	 * @param event the event
	 */
	public static void registerRenderers(final FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(SWEMEntities.SWEM_HORSE_ENTITY.get(), SWEMHorseRender::new);
		RenderingRegistry.registerEntityRenderingHandler(SWEMEntities.WORMIE_BOI_ENTITY.get(), WormieBoiRender::new);
		RenderingRegistry.registerEntityRenderingHandler(SWEMEntities.HORSE_POOP_ENTITY.get(), PoopRender::new);
		ClientRegistry.bindTileEntityRenderer(SWEMTileEntities.TACK_BOX_TILE_ENTITY.get(), TackBoxRender::new);
		ClientRegistry.bindTileEntityRenderer(SWEMTileEntities.ONE_SADDLE_RACK_TILE_ENTITY.get(), OneSaddleRackRender::new);
		ClientRegistry.bindTileEntityRenderer(SWEMTileEntities.BRIDLE_RACK_TILE_ENTITY.get(), BridleRackRender::new);
		ClientRegistry.bindTileEntityRenderer(SWEMTileEntities.HORSE_ARMOR_RACK_TILE_ENTITY.get(), HorseArmorRackRender::new);
		ClientRegistry.bindTileEntityRenderer(SWEMTileEntities.SWEM_SIGN.get(), SignTileEntityRenderer::new);
		GeoArmorRenderer.registerArmorRenderer(LeatherRidingBoots.class, new AmethystArmorModelRenderer());
		GeoArmorRenderer.registerArmorRenderer(GlowRidingBoots.class, new AmethystArmorModelRenderer());
		GeoArmorRenderer.registerArmorRenderer(IronRidingBoots.class, new AmethystArmorModelRenderer());
		GeoArmorRenderer.registerArmorRenderer(GoldRidingBoots.class, new AmethystArmorModelRenderer());
		GeoArmorRenderer.registerArmorRenderer(DiamondRidingBoots.class, new AmethystArmorModelRenderer());
		GeoArmorRenderer.registerArmorRenderer(AmethystRidingBoots.class, new AmethystArmorModelRenderer());
		GeoArmorRenderer.registerArmorRenderer(AmethystHelmet.class, new AmethystArmorModelRenderer());
		GeoArmorRenderer.registerArmorRenderer(AmethystChestplate.class, new AmethystArmorModelRenderer());
		GeoArmorRenderer.registerArmorRenderer(AmethystLeggings.class, new AmethystArmorModelRenderer());
	}

	/**
	 * Sets render layers.
	 */
	public static void setRenderLayers() {
		RenderTypeLookup.setRenderLayer(SWEMBlocks.TIMOTHY_GRASS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.OAT_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.ALFALFA_PLANT.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.WATER_TROUGH.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.METAL_GRATE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.LIGHT_FRIENDLY_BARS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.MEDIUM_FRIENDLY_BARS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.DARK_FRIENDLY_BARS.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.HORSE_PEE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.WESTERN_HITCHING_POST.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.ENGLISH_HITCHING_POST.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.PASTURE_HITCHING_POST.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.LEAD_ANCHOR.get(), RenderType.cutout());

		// Jump blocks.
		RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_STANDARD_SCHOOLING.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_BRUSH_BOX.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_LOG.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_STAIR_DROP.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_HEDGE.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_COOP.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_WALL.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_WALL_MINI.get(), RenderType.translucent());
		// RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_CROSS_RAILS.get(), RenderType.translucent()());
		// RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_SWEDISH_RAILS.get(), RenderType.translucent()());
		//RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_RED_FLAG.get(), RenderType.translucent()());
		//RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_WHITE_FLAG.get(), RenderType.translucent()());
		// RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_RED_WHITE_FLAG.get(), RenderType.translucent()());
		//RenderTypeLookup.setRenderLayer(SWEMBlocks.JUMP_NUMBERS.get(), RenderType.translucent()());

		RenderTypeLookup.setRenderLayer(SWEMBlocks.ACACIA_STALL_HORSE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.BIRCH_STALL_HORSE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.DARK_OAK_STALL_HORSE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.JUNGLE_STALL_HORSE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.OAK_STALL_HORSE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.SPRUCE_STALL_HORSE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.ACACIA_STALL_CARE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.BIRCH_STALL_CARE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.DARK_OAK_STALL_CARE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.JUNGLE_STALL_CARE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.OAK_STALL_CARE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.SPRUCE_STALL_CARE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.WHITEWASH_STALL_CARE.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(SWEMBlocks.WHITEWASH_STALL_HORSE.get(), RenderType.cutout());

		for (DyeColor color : DyeColor.values()) {
			RenderTypeLookup.setRenderLayer(SWEMBlocks.ROLL_TOPS.get(color.getId()).get(), RenderType.translucent());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.RAILS.get(color.getId()).get(), RenderType.translucent());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.GROUND_POLES.get(color.getId()).get(), RenderType.translucent());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.POLE_ON_BOXES_SMALL.get(color.getId()).get(), RenderType.translucent());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.POLE_ON_BOXES_LARGE.get(color.getId()).get(), RenderType.translucent());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.FANCY_PLANKS.get(color.getId()).get(), RenderType.translucent());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.PLANKS.get(color.getId()).get(), RenderType.translucent());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.PANELS_WAVE.get(color.getId()).get(), RenderType.translucent());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.PANELS_STRIPE.get(color.getId()).get(), RenderType.translucent());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.PANELS_ARROW.get(color.getId()).get(), RenderType.translucent());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.FLOWER_BOXES.get(color.getId()).get(), RenderType.translucent());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.CAVALETTIS.get(color.getId()).get(), RenderType.translucent());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.WHEEL_BARROWS.get(color.getId()).get(), RenderType.cutout());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.SEPARATORS.get(color.getId()).get(), RenderType.cutout());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.PASTURE_GATES_HORSE.get(color.getId()).get(), RenderType.cutout());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.PASTURE_GATES_CARE.get(color.getId()).get(), RenderType.cutout());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.WEB_GUARDS_CARE.get(color.getId()).get(), RenderType.cutout());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.WEB_GUARDS_HORSE.get(color.getId()).get(), RenderType.cutout());
			RenderTypeLookup.setRenderLayer(SWEMBlocks.WEB_GUARDS_RIDER.get(color.getId()).get(), RenderType.cutout());
		}
	}

	/**
	 * Register keybinds.
	 */
	public static void registerKeybinds() {
		keyBindings = new KeyBinding[8];

		keyBindings[0] = new KeyBinding("key.swem.horse.increment", GLFW.GLFW_KEY_H, "key.swem.category");
		keyBindings[1] = new KeyBinding("key.swem.horse.decrement", GLFW.GLFW_KEY_G, "key.swem.category");
		keyBindings[2] = new KeyBinding("key.swem.horse.toggle_saddlebag_and_bedroll", GLFW.GLFW_KEY_K, "key.swem.category");
		keyBindings[3] = new KeyBinding("key.swem.horse.toggle_flight", GLFW.GLFW_KEY_J, "key.swem.category");
		keyBindings[4] = new KeyBinding("key.swem.horse.toggle_wings", GLFW.GLFW_KEY_PERIOD, "key.swem.category");
		keyBindings[5] = new KeyBinding("key.swem.horse.dive_flight", GLFW.GLFW_KEY_X, "key.swem.category");
		keyBindings[7] = new KeyBinding("key.swem.horse.camera_lock", GLFW.GLFW_KEY_LEFT_ALT, "key.swem.category");

		//TODO: REMOVE ONCE SPEED HAS BEEN CONFIRMED
		keyBindings[6] = new KeyBinding("key.swem.horse.check_speed", GLFW.GLFW_KEY_N, "key.swem.category");

		for (int i = 0; i < keyBindings.length; i++) {
			ClientRegistry.registerKeyBinding(keyBindings[i]);
		}
	}

	/**
	 * On register block colors.
	 *
	 * @param event the event
	 */
	@SubscribeEvent
	public static void onRegisterBlockColors(ColorHandlerEvent.Block event) {
		BlockColors colors = event.getBlockColors();
		for (RegistryObject<HalfBarrelBlock> barrelRegistry : SWEMBlocks.HALF_BARRELS) {
			HalfBarrelBlock barrel = barrelRegistry.get();
			colors.register((state, reader, pos, color) -> {
				return reader != null && pos != null ? BiomeRegistry.PLAINS.getWaterColor() : -1; // Figure out how to get the PLAINS biome only.
			}, barrel);
		}

		WaterTroughBlock waterTrough = SWEMBlocks.WATER_TROUGH.get();
		colors.register((state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getAverageWaterColor(reader, pos) : -1, waterTrough);

		colors.register((state, reader, pos, color) -> {
			return reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColors.getDefaultColor();
		}, SWEMBlocks.JUMP_HEDGE.get());

	}

	/**
	 * On register item colors.
	 *
	 * @param event the event
	 */
	@SubscribeEvent
	public static void onRegisterItemColors(ColorHandlerEvent.Item event) {
		event.getItemColors().register((p_210239_0_, p_210239_1_) -> {
			return p_210239_1_ > 0 ? -1 : ((IDyeableArmorItem)p_210239_0_.getItem()).getColor(p_210239_0_);
		}, SWEMItems.WESTERN_LEG_WRAPS.get(), SWEMItems.ENGLISH_LEG_WRAPS.get());
	}

	/**
	 * On register entities.
	 *
	 * @param event the event
	 */
	@SubscribeEvent
	public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event) {
		SWEMSpawnEggItem.initSpawnEggs();
	}

	/**
	 * On register items.
	 *
	 * @param event the event
	 */
	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new SWEMSpawnEggItem(SWEMEntities.WORMIE_BOI_ENTITY, Color.parseColor("#bf7b05").getValue(), Color.parseColor("#663c02").getValue(), new Item.Properties().tab(SWEM.TAB)).setRegistryName("worm_spawn_egg"));
		event.getRegistry().register(new SWEMSpawnEggItem(SWEMEntities.SWEM_HORSE_ENTITY, Color.parseColor("#bf7b05").getValue(), Color.parseColor("#663c02").getValue(), new Item.Properties().tab(SWEM.TAB)).setRegistryName("swem_horse_spawn_egg"));
	}


	@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = SWEM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	static class ForgeBusHandlers {

		public static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation(SWEM.MOD_ID, "textures/gui/icons.png");
		private static final HashMap<PlayerEntity, RiderEntity> animatedPlayers = new HashMap<>();


		/**
		 * On render horse jump bar.
		 *
		 * @param event the event
		 */
		@SubscribeEvent
		public static void onRenderHorseJumpBar(RenderGameOverlayEvent.Pre event) {
			if (event.getType() != RenderGameOverlayEvent.ElementType.JUMPBAR) return;

			MatrixStack stack = event.getMatrixStack();
			Minecraft.getInstance().getTextureManager().bind(GUI_ICONS_LOCATION);
			ClientPlayerEntity player = Minecraft.getInstance().player;

			if (!player.isRidingJumpable()) return;
			Entity entity = player.getVehicle();
			if (!(entity instanceof SWEMHorseEntityBase)) return;

			event.setCanceled(true);
			SWEMHorseEntityBase horse = (SWEMHorseEntityBase) entity;
			if (horse.isFlying()) return;
			int xPosition = event.getWindow().getGuiScaledWidth() / 2 - 100;

			float f = player.getJumpRidingScale();
			int i = 201;
			int j = (int)(f * 200.0F);
			int level = horse.progressionManager.getJumpLeveling().getLevel();
			float modifier = ((level + 1.0F) / 5.0F);

			int amountToDraw = (int)(j * modifier);

			int k = event.getWindow().getGuiScaledHeight() - 32 + 3;
			Minecraft.getInstance().gui.blit(stack, xPosition, k, 0, (5 * level), i, 5, 201, 30);
			if (j > 0) {
				Minecraft.getInstance().gui.blit(stack, xPosition + 1, k, 201 - ((level + 1) * 40), 25, amountToDraw, 5, 201, 30);
			}
		}

		/**
		 * On ui render.
		 *
		 * @param event the event
		 */
		@SubscribeEvent
		public static void onUIRender(RenderGameOverlayEvent.Post event) {
			if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

			if (Minecraft.getInstance().player.getVehicle() instanceof SWEMHorseEntityBase) {
				String gait = SWEMHorseEntityBase.HorseSpeed.values()[Minecraft.getInstance().player.getVehicle().getEntityData().get(SWEMHorseEntityBase.SPEED_LEVEL)].getText();
				float xPos = event.getWindow().getGuiScaledWidth() / 2.0f + (4.5f * 20) + 4;
				float yPos = event.getWindow().getGuiScaledHeight() - 14;
				Minecraft.getInstance().font.draw(event.getMatrixStack(), gait, xPos, yPos, TextFormatting.WHITE.getColor());
			}
		}

		/**
		 * On player render.
		 *
		 * @param event the event
		 */
		@SubscribeEvent
		public static void onPlayerRender(RenderPlayerEvent.Pre event) {

			Entity entity = event.getPlayer().getVehicle();
			if (entity instanceof SWEMHorseEntityBase) {
				event.setCanceled(true);

				if (!animatedPlayers.containsKey(event.getPlayer()))
					animatedPlayers.put(event.getPlayer(), new RiderEntity((AbstractClientPlayerEntity) event.getPlayer()));
				else {
					animatedPlayers.get(event.getPlayer()).setPlayer((AbstractClientPlayerEntity) event.getPlayer());
				}

				event.getMatrixStack().pushPose();
				float yRot = MathHelper.rotLerp(event.getPartialRenderTick(), ((LivingEntity)event.getPlayer().getVehicle()).yBodyRotO, ((LivingEntity)event.getPlayer().getVehicle()).yBodyRot);
				event.getMatrixStack().mulPose(new Quaternion(0, 180 - yRot, 0, true));

				RiderGeoRenderer.INSTANCE.render(
						RiderGeoRenderer.INSTANCE.getGeoModelProvider().getModel(RiderGeoRenderer.INSTANCE.getGeoModelProvider().getModelLocation(animatedPlayers.get(event.getPlayer()))),
						animatedPlayers.get(event.getPlayer()),
						event.getPartialRenderTick(),
						RenderType.entityCutoutNoCull(event.getRenderer().getTextureLocation((AbstractClientPlayerEntity) event.getPlayer())),
						event.getMatrixStack(),
						event.getBuffers(),
						null,
						event.getLight(),
						OverlayTexture.NO_OVERLAY,
						1, 1, 1, 1
				);

				event.getMatrixStack().popPose();
			}

		}


	}
}
