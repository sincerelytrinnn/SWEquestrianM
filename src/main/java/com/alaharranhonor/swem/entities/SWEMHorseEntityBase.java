package com.alaharranhonor.swem.entities;


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
import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.container.SWEMHorseInventoryContainer;
import com.alaharranhonor.swem.entities.ai.*;
import com.alaharranhonor.swem.entities.needs.HungerNeed;
import com.alaharranhonor.swem.entities.needs.NeedManager;
import com.alaharranhonor.swem.entities.needs.ThirstNeed;
import com.alaharranhonor.swem.entities.progression.ProgressionManager;
import com.alaharranhonor.swem.entities.progression.leveling.AffinityLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.HealthLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.JumpLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.SpeedLeveling;
import com.alaharranhonor.swem.entity.coats.SWEMCoatColors;
import com.alaharranhonor.swem.items.*;
import com.alaharranhonor.swem.items.tack.*;
import com.alaharranhonor.swem.network.*;
import com.alaharranhonor.swem.util.ClientEventHandlers;
import com.alaharranhonor.swem.util.SWEMUtil;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import com.alaharranhonor.swem.util.registry.SWEMParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.passive.horse.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SEntityPacket;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

import static com.alaharranhonor.swem.entities.HorseFlightController.*;


public class SWEMHorseEntityBase
		extends AbstractHorseEntity
		implements ISWEMEquipable, IEntityAdditionalSpawnData {

	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
	private static final DataParameter<Integer> HORSE_VARIANT = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);

	public static final Ingredient TEMPTATION_ITEMS = Ingredient.of(SWEMItems.AMETHYST.get());
	public static final Ingredient FOOD_ITEMS = Ingredient.of(Items.APPLE, Items.CARROT, SWEMItems.OAT_BUSHEL.get(), SWEMItems.TIMOTHY_BUSHEL.get(), SWEMItems.ALFALFA_BUSHEL.get(), SWEMBlocks.QUALITY_BALE_ITEM.get(), SWEMItems.SUGAR_CUBE.get(), SWEMItems.SWEET_FEED.get());
	public static final Ingredient NEGATIVE_FOOD_ITEMS = Ingredient.of(Items.WHEAT, Items.HAY_BLOCK);
	private static final DataParameter<Boolean> FLYING = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Boolean> JUMPING = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	private static final DataParameter<String> OWNER_NAME = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.STRING);
	private static final EntitySize JUMPING_SIZE = EntitySize.scalable(1.5f, 1.5f);
	private static final DataParameter<Boolean> CAMERA_LOCK = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	private static Random rand = new Random();

	public final ProgressionManager progressionManager;
	private BlockPos currentPos;
	private LazyOptional<InvWrapper> itemHandler;
	private LazyOptional<InvWrapper> saddlebagItemHandler;
	private LazyOptional<InvWrapper> bedrollItemHandler;
	private Inventory saddlebagInventory;
	private Inventory bedrollInventory;
	private static final DataParameter<Integer> GALLOP_TIMER = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);
	private static final DataParameter<Integer> GALLOP_COOLDOWN_TIMER = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);
	private static final DataParameter<Boolean> GALLOP_ON_COOLDOWN = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	public final static DataParameter<Integer> SPEED_LEVEL = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);
	public final static DataParameter<String> PERMISSION_STRING = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.STRING);
	public final static DataParameter<Boolean> TRACKED = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	private ArrayList<UUID> allowedList = new ArrayList<>();

	public HorseSpeed currentSpeed;

	private NeedManager needs;
	private HorseFlightController flightController;

	private PeeGoal peeGoal;
	private PoopGoal poopGoal;

	private BlockPos whistlePos = null;

	private float lockedXRot;
	private float lockedYRot;
	private double lockedXDir;
	private double lockedZDir;

	// Animation variables.
	public final static DataParameter<Integer> JUMP_ANIM_TIMER = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);
	public double jumpHeight;
	private int poopAnimationTick;
	private int peeAnimationTick;
	public int standAnimationTick;
	public int standAnimationVariant;
	public int standingTimer = 0;
	public boolean isWalkingBackwards = false;



	public SWEMHorseEntityBase(EntityType<? extends AbstractHorseEntity> type, World levelIn)
	{
		super(type, levelIn);
		this.currentPos = this.blockPosition();
		this.progressionManager = new ProgressionManager(this);
		this.currentSpeed = HorseSpeed.WALK;
		this.needs = new NeedManager(this);
		this.initSaddlebagInventory();
		this.initBedrollInventory();
		this.flightController = new HorseFlightController(this);
	}

	@Override
	protected void randomizeAttributes() {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getAlteredMaxHealth());
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getAlteredMovementSpeed());
		this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(this.getAlteredJumpStrength());
	}

	// createMobAttributes -> registerAttributes()
	public static AttributeModifierMap.MutableAttribute setCustomAttributes()
	{
		return MobEntity.createMobAttributes()
				.add(Attributes.MAX_HEALTH)
				.add(Attributes.JUMP_STRENGTH)
				.add(Attributes.MOVEMENT_SPEED);
	}

	@Override
	protected void registerGoals() {
		// TODO: ADD AI TO FOLLOW WHISTLE POSITION AS TOP PRIORITY
		this.peeGoal = new PeeGoal(this, 4.0d);
		this.poopGoal = new PoopGoal(this);
		//this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicStraightGoal(this, 4.0D));
		this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 4.0D));
		//this.goalSelector.addGoal(2, new BreedGoal(this, 1.0d));
		//this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, TEMPTATION_ITEMS, false));
		//this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new HorseAvoidEntityGoal<>(this, PigEntity.class, 12.0f, 4.0d, 5.5d));
		this.goalSelector.addGoal(5, this.poopGoal);
		this.goalSelector.addGoal(5, this.peeGoal);
		this.goalSelector.addGoal(6, new HorseWaterAvoidingRandomWalkingGoal(this, 4.0D)); //Speed 4.0 looks like a good speed, plus it triggers anim.
		this.goalSelector.addGoal(7, new LookForFoodGoal(this, 4.0d));
		this.goalSelector.addGoal(7, new LookForWaterGoal(this, 4.0d));
		//this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
	}

	// This method is being called from the SEntityStatusPacket, which is fired in the goal's start method, with the broadcastAndSendChanges
	// This is so we can set animation timers on the client side on the entity.
	@Override
	public void handleEntityEvent(byte p_70103_1_) {
		if (p_70103_1_ == 127) { // Poop goal
			this.poopAnimationTick = 79;
		} else if (p_70103_1_ == 126) { // Pee goal
			this.peeAnimationTick = 79;
		} else {
			super.handleEntityEvent(p_70103_1_);
		}
	}

	public boolean isPooping() {
		return this.poopAnimationTick > 0;
	}

	public boolean isPeeing() {
		return this.peeAnimationTick > 0;
	}

	@Override
	public boolean isStanding() {
		return super.isStanding() || this.standAnimationTick > 0;
	}

	/**
	 *
	 * @return an integer based on the variant. 2 = buck, 1 = Rear.
	 */
	public int getStandVariant() {
		return this.standAnimationVariant;
	}

	@Override
	protected int getExperienceReward(PlayerEntity player) {
		return 0;
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.HORSE_AMBIENT;
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.HORSE_DEATH;
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		super.getHurtSound(damageSourceIn);
		return SoundEvents.HORSE_HURT;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.HORSE_STEP, 0.15f, 1.0f);
	}


	public String getOwnerName() {
		return this.entityData.get(OWNER_NAME);
	}

	public void setOwnerName(String ownerName) {
		this.entityData.set(OWNER_NAME, ownerName);
	}

	@Override
	protected void customServerAiStep() {
		this.peeAnimationTick = this.peeGoal.getPeeTimer();
		this.poopAnimationTick = this.poopGoal.getPoopTimer();
		super.customServerAiStep();
	}

	@Override
	public void aiStep()
	{

		// Tick the animation timers.
		this.peeAnimationTick = Math.max(0, this.peeAnimationTick - 1);
		this.poopAnimationTick = Math.max(0, this.poopAnimationTick - 1);
		this.standAnimationTick = Math.max(0, this.standAnimationTick - 1);
		this.standingTimer = Math.max(0, this.standingTimer - 1);
		if (!this.level.isClientSide) {

			// Tick entity data anim timers
			this.getEntityData().set(JUMP_ANIM_TIMER, Math.max(-1, this.getEntityData().get(JUMP_ANIM_TIMER) - 1));


			if (this.getLeashHolder() instanceof PlayerEntity) {
				this.getLookControl().setLookAt(this.getLeashHolder(), (float)this.getHeadRotSpeed(), (float)this.getMaxHeadXRot());
			}
			if (this.whistlePos != null) {
				this.getNavigation().moveTo(whistlePos.getX(), whistlePos.getY(), whistlePos.getZ(), this.getSpeed());
				if (this.blockPosition().closerThan(this.whistlePos, 2)) {
					this.whistlePos = null;
					this.getNavigation().stop();
				}
			}

			if (this.standAnimationTick == 20 && this.getStandVariant() == 2) {
				this.level.getNearbyEntities(LivingEntity.class, new EntityPredicate().range(5), this, this.getBoundingBox().inflate(2)).forEach((entity) -> {
					if (entity.getVehicle() != this) {
						entity.hurt(DamageSource.GENERIC, 5);
						entity.knockback(0.5f, 0.5, 0.5);
					}
				});
				this.standAnimationVariant = -1;
			}

			if (this.standAnimationTick == 0 && this.getFlag(32)) {
				this.setStanding(false);
			}

			if ((int)(this.level.getDayTime() % 24000L) == 10000) {
				this.resetDaily();
			}

			if (this.entityData.get(GALLOP_ON_COOLDOWN)) {
				// Count the cooldown.
				this.entityData.set(GALLOP_TIMER, this.entityData.get(GALLOP_TIMER) + 1);
				int currentTimer = this.entityData.get(GALLOP_TIMER);
				int cooldownTimer = this.entityData.get(GALLOP_COOLDOWN_TIMER);
				if (currentTimer >= cooldownTimer) {
					this.resetGallopCooldown();
				}
			} else if (this.currentSpeed == HorseSpeed.GALLOP && !this.entityData.get(GALLOP_ON_COOLDOWN)) {
				// COUNT
				int timer = this.entityData.get(GALLOP_TIMER);
				this.entityData.set(GALLOP_TIMER, this.entityData.get(GALLOP_TIMER) + 1);
				if (timer == 7*20) {
					this.decrementSpeed();
				}
			}

			if (this.isTamed()) {
				this.needs.tick();
			}
		}
		super.aiStep();
	}

	private void resetDaily() {
		this.needs.getHunger().resetDaily();
		this.progressionManager.getAffinityLeveling().resetDaily();
	}




	private double getAlteredMovementSpeed()
	{

		//TODO: Remove, once speed has been confirmed.
		/*
		switch (this.progressionManager.getSpeedLeveling().getLevel()) {
			case 1:
				return 0.286d;
			case 2:
				return 0.3905d;
			case 3:
				return 0.517d;
			case 4:
				return 0.649d;
			default:
				return 0.1826d;
		}
		 */

		switch (this.progressionManager.getSpeedLeveling().getLevel()) {
			case 1:
				return 0.357d;
			case 2:
				return 0.408d;
			case 3:
				return 0.452d;
			case 4:
				return 0.5d;
			default:
				return 0.3096d;
		}
	}

	private double getAlteredJumpStrength()
	{
		switch(this.progressionManager.getJumpLeveling().getLevel()) {
			case 1:
				return 0.642707;
			case 2:
				return 0.783313;
			case 3:
				return 0.90862;
			case 4:
				return 1.02295;
			default: {
				return 0.478591;
			}
		}
	}

	private double getAlteredMaxHealth()
	{
		switch(this.progressionManager.getHealthLeveling().getLevel()) {
			case 1:
				return 24.0D;
			case 2:
				return 28.0D;
			case 3:
				return 32.0D;
			case 4:
				return 34.0D;
			default:
				return 20.0D;
		}
	}

	@Override
	protected float getWaterSlowDown() {
		return 0.9F;
	}

	public void heal(float healAmount, float xp) {
		this.heal(healAmount);
		this.progressionManager.getHealthLeveling().addXP(xp);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		//this.entityData.register(HORSE_VARIANT, 0);

		this.entityData.define(SpeedLeveling.LEVEL, 0);
		this.entityData.define(SpeedLeveling.XP, 0.0f);

		this.entityData.define(JumpLeveling.LEVEL, 0);
		this.entityData.define(JumpLeveling.XP, 0.0f);

		this.entityData.define(HealthLeveling.LEVEL, 0);
		this.entityData.define(HealthLeveling.XP, 0.0f);

		this.entityData.define(AffinityLeveling.LEVEL, 0);
		this.entityData.define(AffinityLeveling.XP, 0.0f);

		this.entityData.define(HungerNeed.HungerState.ID, 4);
		this.entityData.define(ThirstNeed.ThirstState.ID, 4);


		this.entityData.define(GALLOP_ON_COOLDOWN, false);
		this.entityData.define(GALLOP_COOLDOWN_TIMER, 0);
		this.entityData.define(GALLOP_TIMER, 0);
		this.entityData.define(SPEED_LEVEL, 0);

		this.entityData.define(HungerNeed.TOTAL_TIMES_FED, 0);

		this.entityData.define(AffinityLeveling.CURRENT_DESENSITIZING_ITEM, ItemStack.EMPTY);
		this.entityData.define(HORSE_VARIANT, this.random.nextInt(SWEMCoatColors.values().length - 2));
		this.entityData.define(FLYING, false);
		this.entityData.define(JUMPING, false);
		this.entityData.define(OWNER_NAME, "");

		this.getEntityData().define(isLaunching, false);
		this.getEntityData().define(isFloating, false);
		this.getEntityData().define(isAccelerating, false);
		this.getEntityData().define(isSlowingDown, false);
		this.getEntityData().define(isStillSlowingDown, false);
		this.getEntityData().define(isTurningLeft, false);
		this.getEntityData().define(isTurning, false);
		this.getEntityData().define(isStillTurning, false);
		this.getEntityData().define(didFlap, false);
		this.getEntityData().define(isDiving, false);
		this.entityData.define(PERMISSION_STRING, "ALL");
		this.entityData.define(TRACKED, false);

		this.entityData.define(CAMERA_LOCK, true);

		this.entityData.define(JUMP_ANIM_TIMER, 0);

	}

	public void setTracked(boolean tracked) {
		this.entityData.set(TRACKED, tracked);
	}

	public boolean isBeingTracked() {
		return this.entityData.get(TRACKED);
	}

	@Override
	public void setOwnerUUID(@Nullable UUID uniqueId) {
		super.setOwnerUUID(uniqueId);
		if (uniqueId != null) {
			PlayerEntity player = this.level.getPlayerByUUID(uniqueId);
			if (player != null) {
				this.entityData.set(OWNER_NAME, player.getGameProfile().getName());
			}
		}
	}


	@Override
	public boolean isJumping() {
		int timer = this.getEntityData().get(JUMP_ANIM_TIMER);
		return this.jumpHeight != 0 || timer > 0;
	}

	public boolean canMountPlayer(PlayerEntity player) {
		if (this.isStanding()
				&& this.getLastDamageSource() != DamageSource.IN_FIRE
				&& this.getLastDamageSource() != DamageSource.LAVA
				&& this.getLastDamageSource() != DamageSource.DROWN
				&& this.getLastDamageSource() != DamageSource.ON_FIRE
				&& this.getLastDamageSource() != DamageSource.HOT_FLOOR) return false;
		if (!this.isTamed()) return true;
		return canAccessHorse(player);
	}

	public boolean canAccessHorse(PlayerEntity player) {
		if (this.getPermissionState() == RidingPermission.NONE) {
			return player.getUUID().equals(this.getOwnerUUID());
		} else if (this.getPermissionState() == RidingPermission.TRUST) {
			return this.isAllowedUUID(player.getUUID()) || player.getUUID().equals(this.getOwnerUUID());
		} else {
			return true;
		}
	}

	public boolean isSaddleable(PlayerEntity player) {
		return this.isAlive() && !this.isBaby() && this.isTamed() && this.canAccessHorse(player);
	}

	public void equipSaddle(@Nullable SoundCategory p_230266_1_, ItemStack stackIn, PlayerEntity player) {
		ItemStack stack = stackIn.copy();
		boolean flag = player.isSecondaryUseActive() && !player.isCreative();
		if (stack.getItem() instanceof HorseSaddleItem) {
			if (flag) {
				player.addItem(this.inventory.getItem(2));
			}
			this.inventory.setItem(2, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 2, stack));
			if (p_230266_1_ != null) {
				this.level.playSound((PlayerEntity)null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof BlanketItem) {
			if (flag) {
				player.addItem(this.inventory.getItem(1));
			}
			this.inventory.setItem(1, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 1, stack));
			if (p_230266_1_ != null) {
				this.level.playSound((PlayerEntity)null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof BreastCollarItem) {
			if (flag) {
				player.addItem(this.inventory.getItem(3));
			}
			this.inventory.setItem(3, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 3, stack));
			if (p_230266_1_ != null) {
				this.level.playSound((PlayerEntity)null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof HalterItem) {
			if (flag) {
				player.addItem(this.inventory.getItem(0));
			}
			this.inventory.setItem(0, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 0, stack));
			if (p_230266_1_ != null) {
				this.level.playSound((PlayerEntity)null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof GirthStrapItem) {
			if (flag) {
				player.addItem(this.inventory.getItem(5));
			}
			this.inventory.setItem(5, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 5, stack));
			if (p_230266_1_ != null) {
				this.level.playSound((PlayerEntity)null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof LegWrapsItem) {
			if (flag) {
				player.addItem(this.inventory.getItem(4));
			}
			this.inventory.setItem(4, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 4, stack));
			if (p_230266_1_ != null) {
				this.level.playSound((PlayerEntity)null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof SWEMHorseArmorItem) {
			this.inventory.setItem(6, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 6, stack));
			if (p_230266_1_ != null) {
				this.level.playSound((PlayerEntity)null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof SaddlebagItem) {
			this.inventory.setItem(7, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 7, stack));
			if (p_230266_1_ != null) {
				this.level.playSound((PlayerEntity)null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		}

	}

	@Override
	public void makeMad() {
		if (!this.isStanding()) {
			this.setStanding(true);
			this.setStandingAnim();
			SoundEvent soundevent = this.getAngrySound();
			if (soundevent != null) {
				this.playSound(soundevent, this.getSoundVolume(), this.getVoicePitch());
			}
		}
	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding this one.
	 */
	@Override
	public double getPassengersRidingOffset() {
		double def = (double)(this.getDimensions(this.getPose()).height * 0.75D);
		def += 0.3D;
		return def;
	}


	@Override
	public void positionRider(Entity entity) {
		if (!this.hasPassenger(entity)) {
			return;
		}

		float xzOffset = -0.1f;

		if (this.getPassengers().size() > 1) {
			int i = this.getPassengers().indexOf(entity);
			xzOffset = i == 0 ? 0.1f : -0.5f;
		}

		double yOffset = entity.getMyRidingOffset() + this.getPassengersRidingOffset();

		Vector3d vec3 = new Vector3d(xzOffset, 0, 0).yRot(-this.yBodyRot * ((float) Math.PI / 180f) - ((float) Math.PI / 2F));
		entity.setPos(this.getX() + vec3.x, this.getY() + yOffset, this.getZ() + vec3.z);
		this.applyYaw(entity);
		if (entity instanceof AnimalEntity && this.getPassengers().size() > 1) {
			int degrees = entity.getId() % 2 == 0 ? 90 : 270;
			entity.setYBodyRot(((AnimalEntity) entity).yBodyRot + (float) degrees);
			entity.setYHeadRot(entity.getYHeadRot() + (float) degrees);
		}
	}



	private void applyYaw(Entity entity) {
		if (!(entity instanceof PlayerEntity)) {
			entity.setYBodyRot(this.yBodyRot);
			entity.yRot = this.yBodyRot;
			entity.setYHeadRot(this.yBodyRot);
		}
	}



	@Override
	protected boolean canAddPassenger(Entity passenger) {
		if (!passenger.getType().getCategory().isFriendly()) return false;

		if (passenger instanceof WaterMobEntity) return false;



		return this.getPassengers().size() < 2;
	}

	public boolean isFlying() {
		return this.entityData.get(FLYING);
	}

	public void setFlying(boolean flying) {
		this.entityData.set(FLYING, flying);
		if (flying) {
			this.flightController.launchFlight();
		} else {
			this.flightController.land();
		}
	}



	private int checkHeightInAir() {
		BlockPos currentPos = this.blockPosition();
		BlockState checkState = this.level.getBlockState(currentPos);
		int counter = 0;
		while (checkState == Blocks.AIR.defaultBlockState()) {
			counter++;
			currentPos = currentPos.below();
			checkState = this.level.getBlockState(currentPos);
		}
		return counter;
	}


	@Override
	public int getMaxFallDistance() {
		return 2;
	}

	@Override
	protected void doPlayerRide(PlayerEntity player) {
		this.setEating(false);
		this.setStanding(false);
		if (!this.level.isClientSide) {
			player.yRot = this.yRot - 0.2F;
			player.xRot = this.xRot;
			player.startRiding(this);
		}
		HorseSpeed oldSpeed = this.currentSpeed;
		this.currentSpeed = HorseSpeed.WALK;
		this.updateSelectedSpeed(oldSpeed);
	}


	private void setGallopCooldown() {
		int gallopSoundCounter = this.entityData.get(GALLOP_TIMER);
		int cooldown = gallopSoundCounter * 5;
		this.entityData.set(GALLOP_COOLDOWN_TIMER, cooldown);
		this.entityData.set(GALLOP_ON_COOLDOWN, true);
		this.entityData.set(GALLOP_TIMER, 0);
	}

	private void resetGallopCooldown() {
		this.entityData.set(GALLOP_COOLDOWN_TIMER, 0);
		this.entityData.set(GALLOP_ON_COOLDOWN, false);
		this.entityData.set(GALLOP_TIMER, 0);
	}



	public boolean isHorseSaddled() {
		return this.getFlag(4);
	}

	@Override
	public boolean hasAdventureSaddle() {
		return this.hasSaddle().getItem() instanceof AdventureSaddleItem;
	}

	@Override
	public boolean hasBlanket() {
		return this.inventory.getItem(1).getItem() instanceof BlanketItem;
	}

	@Override
	public boolean hasBreastCollar() {
		return this.inventory.getItem(3).getItem() instanceof BreastCollarItem;
	}

	@Override
	public boolean hasHalter() {
		if (ConfigHolder.SERVER.halterDependency.get()) {
			return this.inventory.getItem(0).getItem() instanceof HalterItem;
		} else {
			return true;
		}

	}

	@Override
	public boolean hasGirthStrap() {
		return this.inventory.getItem(5).getItem() instanceof GirthStrapItem;
	}

	@Override
	public boolean hasLegWraps() {
		return this.inventory.getItem(4).getItem() instanceof LegWrapsItem;
	}

	public ItemStack hasSaddle() {
		return this.inventory.getItem(2);
	};

	@Override
	protected void createInventory() {
		Inventory inventory = this.inventory;
		this.inventory = new Inventory(this.getInventorySize());
		if (inventory != null) {
			inventory.removeListener(this);
			int i = Math.min(inventory.getContainerSize(), this.inventory.getContainerSize());

			for(int j = 0; j < i; ++j) {
				ItemStack itemstack = inventory.getItem(j);
				if (!itemstack.isEmpty()) {
					this.inventory.setItem(j, itemstack.copy());
				}
			}
		}

		this.inventory.addListener(this::onHorseInventoryChanged);
		this.updateContainerEquipment();
		this.itemHandler = LazyOptional.of(() -> new InvWrapper(this.inventory));
	}

	public Inventory getHorseInventory() {
		return this.inventory;
	}

	protected void initSaddlebagInventory() {
		Inventory inventory = this.saddlebagInventory;
		this.saddlebagInventory = new Inventory(27);
		if (inventory != null) {
			inventory.removeListener(this);
			int i = Math.min(inventory.getContainerSize(), this.saddlebagInventory.getContainerSize());

			for(int j = 0; j < i; ++j) {
				ItemStack itemstack = inventory.getItem(j);
				if (!itemstack.isEmpty()) {
					this.saddlebagInventory.setItem(j, itemstack.copy());
				}
			}
		}

		this.saddlebagInventory.addListener(this::containerChanged);
		this.saddlebagItemHandler = LazyOptional.of(() -> new InvWrapper(this.saddlebagInventory));
	}

	public Inventory getSaddlebagInventory() {
		return this.saddlebagInventory;
	}

	protected void initBedrollInventory() {
		Inventory inventory = this.bedrollInventory;
		this.bedrollInventory = new Inventory(4);
		if (inventory != null) {
			inventory.removeListener(this);
			int i = Math.min(inventory.getContainerSize(), this.bedrollInventory.getContainerSize());

			for(int j = 0; j < i; ++j) {
				ItemStack itemstack = inventory.getItem(j);
				if (!itemstack.isEmpty()) {
					this.bedrollInventory.setItem(j, itemstack.copy());
				}
			}
		}

		this.bedrollInventory.addListener(this::containerChanged);
		this.bedrollItemHandler = LazyOptional.of(() -> new InvWrapper(this.bedrollInventory));
	}

	public Inventory getBedrollInventory() {
		return this.bedrollInventory;
	}

	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
//		compound.putInt("Variant", this.getHorseVariant());
		if (!this.inventory.getItem(0).isEmpty()) {
			compound.put("BridleItem", this.inventory.getItem(0).save(new CompoundNBT()));
		}
		if (!this.inventory.getItem(1).isEmpty()) {
			compound.put("BlanketItem", this.inventory.getItem(1).save(new CompoundNBT()));
		}
		if (!this.inventory.getItem(2).isEmpty()) {
			compound.put("SaddleItem", this.inventory.getItem(2).save(new CompoundNBT()));
		}
		if (!this.inventory.getItem(3).isEmpty()) {
			compound.put("BreastCollarItem", this.inventory.getItem(3).save(new CompoundNBT()));
		}
		if (!this.inventory.getItem(4).isEmpty()) {
			compound.put("LegWrapsItem", this.inventory.getItem(4).save(new CompoundNBT()));
		}
		if (!this.inventory.getItem(5).isEmpty()) {
			compound.put("GirthStrapItem", this.inventory.getItem(5).save(new CompoundNBT()));
		}
		if (!this.inventory.getItem(6).isEmpty()) {
			compound.put("SWEMArmorItem", this.inventory.getItem(6).save(new CompoundNBT()));
		}
		if (!this.inventory.getItem(7).isEmpty()) {
			compound.put("SaddlebagItem", this.inventory.getItem(7).save(new CompoundNBT()));
		}

		this.writeSaddlebagInventory(compound);
		this.writeBedrollInventory(compound);


		this.progressionManager.write(compound);

		this.needs.write(compound);

		compound.putBoolean("flying", this.isFlying());

		compound.putInt("HorseVariant", this.getHorseVariant());

		compound.putString("ownerName", this.getOwnerName());

		CompoundNBT allowedList = new CompoundNBT();
		for (int i = 0; i < this.allowedList.size(); i++) {
			allowedList.putUUID(Integer.toString(i), this.allowedList.get(i));
		}

		compound.put("allowedList", allowedList);
		compound.putString("permissionState", RidingPermission.valueOf(this.entityData.get(PERMISSION_STRING)).name());
		compound.putBoolean("tracked", this.entityData.get(TRACKED));
	}

	public ItemStack getArmor() {
		return this.getItemBySlot(EquipmentSlotType.CHEST);
	}

	private void setArmor(ItemStack p_213805_1_) {
		this.setItemSlot(EquipmentSlotType.CHEST, p_213805_1_);
		this.setDropChance(EquipmentSlotType.CHEST, 0.0F);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
//		this.setTypeVariant(compound.getInt("Variant"));
		if (compound.contains("BridleItem", 10)) {
			ItemStack itemstack = ItemStack.of(compound.getCompound("BridleItem"));
			if (!itemstack.isEmpty() && this.isHalter(itemstack)) {
				this.inventory.setItem(0, itemstack);
			}
		}
		if (compound.contains("BlanketItem", 10)) {
			ItemStack itemstack = ItemStack.of(compound.getCompound("BlanketItem"));
			if (!itemstack.isEmpty() && this.isBlanket(itemstack)) {
				this.inventory.setItem(1, itemstack);
			}
		}
		if (compound.contains("SaddleItem", 10)) {
			ItemStack itemstack = ItemStack.of(compound.getCompound("SaddleItem"));
			if (!itemstack.isEmpty() && this.isSaddle(itemstack)) {
				this.inventory.setItem(2, itemstack);
			}
		}
		if (compound.contains("BreastCollarItem", 10)) {
			ItemStack itemstack = ItemStack.of(compound.getCompound("BreastCollarItem"));
			if (!itemstack.isEmpty() && this.isBreastCollar(itemstack)) {
				this.inventory.setItem(3, itemstack);
			}
		}
		if (compound.contains("LegWrapsItem", 10)) {
			ItemStack itemstack = ItemStack.of(compound.getCompound("LegWrapsItem"));
			if (!itemstack.isEmpty() && this.isLegWraps(itemstack)) {
				this.inventory.setItem(4, itemstack);
			}
		}
		if (compound.contains("GirthStrapItem", 10)) {
			ItemStack itemstack = ItemStack.of(compound.getCompound("GirthStrapItem"));
			if (!itemstack.isEmpty() && this.isGirthStrap(itemstack)) {
				this.inventory.setItem(5, itemstack);
			}
		}
		if (compound.contains("SWEMArmorItem", 10)) {
			ItemStack itemstack = ItemStack.of(compound.getCompound("SWEMArmorItem"));
			if (!itemstack.isEmpty() && this.isSWEMArmor(itemstack)) {
				this.inventory.setItem(6, itemstack);
			}
		}
		if (compound.contains("SaddlebagItem", 10)) {
			ItemStack itemstack = ItemStack.of(compound.getCompound("SaddlebagItem"));
			if (!itemstack.isEmpty() && this.isSaddlebag(itemstack)) {
				this.inventory.setItem(7, itemstack);
			}
		}

		this.readSaddlebagInventory(compound);
		this.readBedrollInventory(compound);


		this.progressionManager.read(compound);

		this.needs.read(compound);

		this.updateContainerEquipment();

		//this.setFlying(compound.getBoolean("flying"));

		int variant = compound.getInt("HorseVariant");
		this.setHorseVariant(variant % (SWEMCoatColors.values().length - 2));

		this.setOwnerName(compound.getString("ownerName"));

		if (compound.contains("allowedList")) {
			CompoundNBT allowList = compound.getCompound("allowedList");
			for (int i = 0; i < allowList.size(); i++) {
				this.addAllowedUUID(allowList.getUUID(Integer.toString(i)));
			}
		}

		if (compound.contains("permissionState")) {
			this.setPermissionState(compound.getString("permissionState"));
		}

		if (compound.contains("tracked")) {
			this.setTracked(compound.getBoolean("tracked"));
		}

	}


	public void addAllowedUUID(UUID playerUUID) {
		if (!this.allowedList.contains(playerUUID)) {
			this.allowedList.add(playerUUID);
		}
	}

	public boolean isAllowedUUID(UUID playerUUID) {
		return this.allowedList.contains(playerUUID);
	}

	public void removeAllowedUUID(UUID playerUUID) {
		if (this.getOwnerUUID().equals(playerUUID)) return;
		this.allowedList.remove(playerUUID);
	}

	public void transferHorse(PlayerEntity player) {
		this.tameWithName(player);
		this.removeAllAllowedUUIDs();
	}

	public void removeAllAllowedUUIDs() {
		for (UUID allowed : this.allowedList) {
			this.removeAllowedUUID(allowed);
		}
	}

	@Override
	public boolean updateFluidHeightAndDoFluidPushing(ITag<Fluid> pFluidTag, double pMotionScale) {
		AxisAlignedBB axisalignedbb = this.getBoundingBox().deflate(0.001D).contract(0, -1, 0);
		int i = MathHelper.floor(axisalignedbb.minX);
		int j = MathHelper.ceil(axisalignedbb.maxX);
		int k = MathHelper.floor(axisalignedbb.minY);
		int l = MathHelper.ceil(axisalignedbb.maxY);
		int i1 = MathHelper.floor(axisalignedbb.minZ);
		int j1 = MathHelper.ceil(axisalignedbb.maxZ);
		if (!this.level.hasChunksAt(i, k, i1, j, l, j1)) {
			return false;
		} else {
			double d0 = 0.0D;
			boolean flag = this.isPushedByFluid();
			boolean flag1 = false;
			Vector3d vector3d = Vector3d.ZERO;
			int k1 = 0;
			BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

			for(int l1 = i; l1 < j; ++l1) {
				for(int i2 = k; i2 < l; ++i2) {
					for(int j2 = i1; j2 < j1; ++j2) {
						blockpos$mutable.set(l1, i2, j2);
						FluidState fluidstate = this.level.getFluidState(blockpos$mutable);
						if (fluidstate.is(pFluidTag)) {
							double d1 = (double)((float)i2 + fluidstate.getHeight(this.level, blockpos$mutable));
							if (d1 >= axisalignedbb.minY) {
								flag1 = true;
								d0 = Math.max(d1 - axisalignedbb.minY, d0);
								if (flag) {
									Vector3d vector3d1 = fluidstate.getFlow(this.level, blockpos$mutable);
									if (d0 < 0.4D) {
										vector3d1 = vector3d1.scale(d0);
									}

									vector3d = vector3d.add(vector3d1);
									++k1;
								}
							}
						}
					}
				}
			}

			if (vector3d.length() > 0.0D) {
				if (k1 > 0) {
					vector3d = vector3d.scale(1.0D / (double)k1);
				}


				vector3d = vector3d.normalize();


				Vector3d vector3d2 = this.getDeltaMovement();
				vector3d = vector3d.scale(pMotionScale * 1.0D);
				double d2 = 0.003D;
				if (Math.abs(vector3d2.x) < 0.003D && Math.abs(vector3d2.z) < 0.003D && vector3d.length() < 0.0045000000000000005D) {
					vector3d = vector3d.normalize().scale(0.0045000000000000005D);
				}

				this.setDeltaMovement(this.getDeltaMovement().add(vector3d));
			}

			this.fluidHeight.put(pFluidTag, d0);
			return flag1;
		}
	}

	private void writeSaddlebagInventory(CompoundNBT compound) {

		if (!this.saddlebagInventory.isEmpty()) {
			CompoundNBT saddlebag = new CompoundNBT();
			for (int i = 0; i < this.saddlebagInventory.getContainerSize(); i++) {
				saddlebag.put(Integer.toString(i), this.saddlebagInventory.getItem(i).save(new CompoundNBT()));
			}
			compound.put("saddlebag", saddlebag);
		}

	}

	private void readSaddlebagInventory(CompoundNBT compound) {
		if (compound.contains("saddlebag")) {
			CompoundNBT saddlebag = compound.getCompound("saddlebag");

			for (int i = 0; i < this.saddlebagInventory.getContainerSize(); i++) {
				if (saddlebag.contains(Integer.toString(i))) {
					CompoundNBT stackNBT = (CompoundNBT) saddlebag.get(Integer.toString(i));
					ItemStack readStack = ItemStack.of(stackNBT);
					this.saddlebagInventory.setItem(i, readStack);
				}
			}
		}
	}

	private void writeBedrollInventory(CompoundNBT compound) {

		if (!this.bedrollInventory.isEmpty()) {
			CompoundNBT bedroll = new CompoundNBT();
			for (int i = 0; i < this.bedrollInventory.getContainerSize(); i++) {
				bedroll.put(Integer.toString(i), this.bedrollInventory.getItem(i).save(new CompoundNBT()));
			}
			compound.put("bedroll", bedroll);
		}

	}

	private void readBedrollInventory(CompoundNBT compound) {
		if (compound.contains("bedroll")) {
			CompoundNBT bedroll = compound.getCompound("bedroll");

			for (int i = 0; i < this.bedrollInventory.getContainerSize(); i++) {
				if (bedroll.contains(Integer.toString(i))) {
					CompoundNBT stackNBT = (CompoundNBT) bedroll.get(Integer.toString(i));
					ItemStack readStack = ItemStack.of(stackNBT);
					this.bedrollInventory.setItem(i, readStack);
				}
			}
		}
	}

	private void setHorseVariant(int id) {
		this.entityData.set(HORSE_VARIANT, id);
	}


	private void setVariantAndMarkings(CoatColors p_234238_1_, CoatTypes p_234238_2_) {
//		this.setTypeVariant(p_234238_1_.getId() & 255 | p_234238_2_.getId() << 8 & '\uff00');
	}

	public SWEMCoatColors getCoatColor() {
		return SWEMCoatColors.getById(this.getHorseVariant() & 255);
	}

	private int getHorseVariant() {
		return this.entityData.get(HORSE_VARIANT);
	}

	public void calculatePotionCoat(CoatColors vanillaCoat) {
		int randomNum = rand.nextInt(100) + 1;
		switch (vanillaCoat) {
			case BLACK: {
				if (randomNum <= 65)
					this.setHorseVariant(2);
				else if (randomNum <= 95)
					this.setHorseVariant(12);
				else
					this.setHorseVariant(11);
				break;
			}
			case GRAY: {
				if (randomNum <= 7)
					this.setHorseVariant(11);
				else if (randomNum <= 97)
					this.setHorseVariant(1);
				else
					this.setHorseVariant(0);
				break;
			}
			case WHITE: {
				if (randomNum <= 10)
					this.setHorseVariant(15);
				else if (randomNum <= 25)
					this.setHorseVariant(1);
				else
					this.setHorseVariant(0);
				break;
			}
			case CHESTNUT: {
				if (randomNum <= 30)
					this.setHorseVariant(13);
				else if (randomNum <= 90)
					this.setHorseVariant(10);
				else
					this.setHorseVariant(5);
				break;
			}
			case CREAMY: {
				if (randomNum <= 49)
					this.setHorseVariant(6);
				else if (randomNum <= 98)
					this.setHorseVariant(8);
				else
					this.setHorseVariant(14);
				break;
			}
			case BROWN: {
				if (randomNum <= 20)
					this.setHorseVariant(7);
				else if (randomNum <= 29)
					this.setHorseVariant(6);
				else
					this.setHorseVariant(4);
				break;
			}
			case DARKBROWN: {
				if (randomNum <= 80)
					this.setHorseVariant(3);
				else if (randomNum <= 85)
					this.setHorseVariant(9);
				else
					this.setHorseVariant(7);
				break;
			}
		}
	}

	public void setCoatColour(SWEMCoatColors coat) {
		this.setHorseVariant(coat.getId());
	}


	@Override
	public void setTamed(boolean tamed) {
		super.setTamed(tamed);
		this.progressionManager.getAffinityLeveling().addXP(100.0f);
	}

	@Override
	protected void updateContainerEquipment() {
		if (!this.level.isClientSide) {
			this.setFlag(4, !this.inventory.getItem(2).isEmpty());
		}
	}

	private void setArmorEquipment(ItemStack p_213804_1_) {
		this.setArmor(p_213804_1_);
		if (!this.level.isClientSide) {
			this.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER_UUID);
			if (this.isSWEMArmor(p_213804_1_)) {
				int i = ((HorseArmorItem)p_213804_1_.getItem()).getProtection();
				if (i != 0) {
					this.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", (double)i, AttributeModifier.Operation.ADDITION));
				}
			}
		}

	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick() {
		if (this.isFlying()) {
			this.flightController.travel();
			return;
		}
		if (!this.level.isClientSide) {
			if (this.tickCount % 5 == 0) {

				if (this.canBeControlledByRider() && this.isVehicle()) {
					int x = this.blockPosition().getX();
					int z = this.blockPosition().getZ();
					if (x != this.currentPos.getX() || z != this.currentPos.getZ()) {
						x = Math.abs(x - this.currentPos.getX());
						z = Math.abs(z - this.currentPos.getZ());
						int dist = ((int)Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)));
						if (dist > 0 && dist < 25) {
							boolean speedLevelUp = false;
							speedLevelUp = this.progressionManager.getSpeedLeveling().addXP(dist * this.currentSpeed.getSkillMultiplier());

							if (speedLevelUp) {
								this.levelUpSpeed();
							}
							// Affinity leveling, is not affected by speed. so no matter the speed, just add 1xp per block.
							this.progressionManager.getAffinityLeveling().addXP(dist * this.currentSpeed.getSkillMultiplier());
						}


					}
				}

				// Kick off rider, if no girth strap is equipped.
				if (this.isSWEMSaddled() && (!this.hasGirthStrap() || !ConfigHolder.SERVER.riderNeedsGirthStrap.get()) && this.isVehicle()) {
					if (this.tickCount % 20 == 0) {
						int rand = this.getRandom().nextInt(5);
						if (rand == 0) {
							Entity rider = this.getPassengers().get(0);
							rider.stopRiding();
							ItemStack saddle = this.hasSaddle();
							this.inventory.setItem(2, ItemStack.EMPTY);
							this.setSWEMSaddled();
							this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), saddle));
						}
					}

				}
				this.currentPos = this.blockPosition();
			}

			if (!this.inventory.getItem(6).isEmpty()) {
				this.checkArmorPiece(((SWEMHorseArmorItem)this.inventory.getItem(6).getItem()));
			}


		} else {
			if (Minecraft.getInstance().player.getVehicle() != null && Minecraft.getInstance().player.getVehicle().getUUID().equals(this.getUUID())) {

				if (ClientEventHandlers.keyBindings[8].isDown() && this.isCameraLocked()) {
					SWEMPacketHandler.INSTANCE.sendToServer(new CCameraLockPacket(this.getUUID(), false));
					this.setLockedRotations(this.xRot, this.yRot);


				} else if (!ClientEventHandlers.keyBindings[8].isDown() && !this.isCameraLocked()) {
					SWEMPacketHandler.INSTANCE.sendToServer(new CCameraLockPacket(this.getUUID(), true));
				}
			}
		}
		super.tick();
		if (this.isInWater() && !this.isVehicle()) {
			if (this.wasEyeInWater) {
				this.setDeltaMovement(this.getDeltaMovement().x, .05, this.getDeltaMovement().z); // Set the motion on y with a negative force, because the horse is floating to the top, pull it down, until wasEyeInWater returns true.
			} else {
				this.setDeltaMovement(this.getDeltaMovement().x, -.05, this.getDeltaMovement().z); // Set the motion on y with a negative force, because the horse is floating to the top, pull it down, until wasEyeInWater returns true.

			}

		} else if (this.isInLava() && !this.isEyeInFluid(FluidTags.LAVA) && !this.isVehicle()) {
			this.setDeltaMovement(this.getDeltaMovement().x, -.5, this.getDeltaMovement().z);// Set the motion on y with a negative force, because the horse is floating to the top, pull it down, until wasEyeInWater returns true.
		}
	}

	private void checkArmorPiece(SWEMHorseArmorItem armor) {
		if (armor.tier.getId() >= 0)
			this.tickClothArmor();
		if (armor.tier.getId() >= 1)
			this.tickIronArmor();
		if (armor.tier.getId() >= 2)
			this.tickGoldArmor();
		if (armor.tier.getId() >= 3)
			this.tickDiamondArmor();
		if (armor.tier.getId() == 4)
			this.tickAmethystArmor();
	}

	private void tickClothArmor() {

	}

	private void tickIronArmor() {

	}

	private void tickGoldArmor() {
		if (this.isOnGround()) {
			BlockState blockstate = Blocks.FROSTED_ICE.defaultBlockState();
			float f = (float)Math.min(16, 3);
			BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

			BlockPos pos = this.blockPosition();

			for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-f, -1.0D, -f), pos.offset(f, -1.0D, f))) {
				if (blockpos.closerThan(this.blockPosition(), f)) {
					blockpos$mutable.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
					BlockState blockstate1 = level.getBlockState(blockpos$mutable);
					if (blockstate1.isAir(level, blockpos$mutable)) {
						BlockState blockstate2 = level.getBlockState(blockpos);
						boolean isFull = blockstate2.getBlock() == Blocks.WATER && blockstate2.getValue(FlowingFluidBlock.LEVEL) == 0; //TODO: Forge, modded waters?
						if (blockstate2.getMaterial() == Material.WATER && isFull && blockstate.canSurvive(level, blockpos) && level.isUnobstructed(blockstate, blockpos, ISelectionContext.empty()) && !ForgeEventFactory.onBlockPlace(this, BlockSnapshot.create(level.dimension(), level, blockpos), Direction.UP)) {
							level.setBlock(blockpos, blockstate, 3);
							level.getBlockTicks().scheduleTick(blockpos, Blocks.FROSTED_ICE, 20);
						}
					}
				}
			}

		}
	}

	private void tickDiamondArmor() {
		if (this.isOnGround()) {
			BlockState blockstate = SWEMBlocks.TEARING_MAGMA.get().defaultBlockState();
			float f = (float)Math.min(16, 3);
			BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

			BlockPos pos = this.blockPosition();

			for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset((double)(-f), -1.0D, (double)(-f)), pos.offset((double)f, -1.0D, (double)f))) {
				if (blockpos.closerThan(this.blockPosition(), (double)f)) {
					blockpos$mutable.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
					BlockState blockstate1 = level.getBlockState(blockpos$mutable);
					if (blockstate1.isAir(level, blockpos$mutable)) {
						BlockState blockstate2 = level.getBlockState(blockpos);
						boolean isFull = blockstate2.getBlock() == Blocks.LAVA && blockstate2.getValue(FlowingFluidBlock.LEVEL) == 0; //TODO: Forge, modded waters?
						if (blockstate2.getMaterial() == Material.LAVA && isFull && blockstate.canSurvive(level, blockpos) && level.isUnobstructed(blockstate, blockpos, ISelectionContext.empty()) && !ForgeEventFactory.onBlockPlace(this, BlockSnapshot.create(level.dimension(), level, blockpos), Direction.UP)) {
							level.setBlock(blockpos, blockstate, 3);
							level.getBlockTicks().scheduleTick(blockpos, SWEMBlocks.TEARING_MAGMA.get(), 20);
						}
					}
				}
			}
		}

		this.clearFire();


	}

	private void tickAmethystArmor() {

	}


	@Override
	public EntitySize getDimensions(Pose poseIn) {
		return super.getDimensions(poseIn);
		/*if (this.isJumping()) {
			return JUMPING_SIZE;
		} else {
			return super.getDimensions(poseIn);
		}*/
	}

	@Override
	public void travel(Vector3d travelVector) {

		if (this.isFlying()) {
			return;
		} else {
			if (this.isStanding()) {
				this.setDeltaMovement(0, 0, 0);
				return;
			}


			if (this.isVehicle() && this.canBeControlledByRider() && this.isHorseSaddled()) {
				PlayerEntity livingentity = (PlayerEntity) this.getControllingPassenger();

				float f = livingentity.xxa * 0.5F;
				float f1 = livingentity.zza;
				if (f1 <= 0.0F) {
					this.gallopSoundCounter = 0;
				}

				if (this.onGround && this.playerJumpPendingScale == 0.0F && this.isStanding() && !this.allowStandSliding) {
					f = 0.0F;
					f1 = 0.0F;
				}


				// Check if RNG is higher roll, than disobeying debuff, if so, then do the jump.
				if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.onGround) {
					if (this.getRandom().nextDouble() > this.progressionManager.getAffinityLeveling().getDebuff()) {
						double d0 = this.getCustomJump() * (double) this.playerJumpPendingScale * (double) this.getBlockJumpFactor();
						double d1;
						if (this.hasEffect(Effects.JUMP)) {
							d1 = d0 + (double) ((float) (this.getEffect(Effects.JUMP).getAmplifier() + 1) * 0.1F);
						} else {
							d1 = d0;
						}

						//if (this.getDisobedienceFactor() > this.progressionManager.getAffinityLeveling().getDebuff()) {
						Vector3d vector3d = this.getDeltaMovement();
						this.setDeltaMovement(vector3d.x, d1, vector3d.z);


						// Check jumpheight, and add XP accordingly.
						float jumpHeight = (float) (-0.1817584952 * ((float) Math.pow(d1, 3.0F)) + 3.689713992 * ((float) Math.pow(d1, 2.0F)) + 2.128599134 * d1 - 0.343930367);
						float xpToAdd = 0.0f;
						if (jumpHeight >= 4.0f) {
							xpToAdd = 40.0f;
						} else if (jumpHeight >= 3.0f) {
							xpToAdd = 30.0f;
						} else if (jumpHeight >= 2.0f) {
							xpToAdd = 25.0f;
						} else if (jumpHeight >= 1.0f) {
							xpToAdd = 20.0f;
						}

						this.jumpHeight = jumpHeight;
						this.startJump(jumpHeight);


						SWEMPacketHandler.INSTANCE.sendToServer(new AddJumpXPMessage(xpToAdd, this.getId()));


						this.hasImpulse = true;
						ForgeHooks.onLivingJump(this);
						if (f1 > 0.0F) {
							float f2 = MathHelper.sin(this.yRot * ((float) Math.PI / 180F));
							float f3 = MathHelper.cos(this.yRot * ((float) Math.PI / 180F));
							this.setDeltaMovement(this.getDeltaMovement().add((double) (-0.4F * f2 * this.playerJumpPendingScale), 0.0D, (double) (0.4F * f3 * this.playerJumpPendingScale)));
						}
					} else {
						this.setStandingAnim();
						SWEMPacketHandler.INSTANCE.sendToServer(new SendHorseSpeedChange(2, this.getId()));
					}

					this.playerJumpPendingScale = 0.0F;
					//} else {
					//	this.makeMad();
					//}
				}



				this.flyingSpeed = this.getSpeed() * 0.1F;
				if (this.isControlledByLocalInstance()) {
					this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
					if (f1 < 0.0f) { // Backwards movement.
						HorseSpeed oldSpeed = this.currentSpeed;
						this.currentSpeed = HorseSpeed.WALK;
						this.updateSelectedSpeed(oldSpeed);
						livingentity.zza *= 3f;
						// We multiply with a number close to 4, since in the AbstractHorseEntity it slows the backwards movement with * 0.25
						// So we counter that, by check if it's negative, but still make it a bit slower than regular walking.


						if (!this.isWalkingBackwards) {
							SWEMPacketHandler.INSTANCE.sendToServer(new SHorseAnimationPacket(this.getId(), 3));
						}

					} else if (this.isWalkingBackwards) {
						SWEMPacketHandler.INSTANCE.sendToServer(new SHorseAnimationPacket(this.getId(), 4));
					}


					super.travel(new Vector3d((double) f, travelVector.y, (double) f1));
				} else if ((livingentity instanceof PlayerEntity)) {
					this.setDeltaMovement(Vector3d.ZERO);
				}

				if (this.onGround) {
					this.playerJumpPendingScale = 0.0F;
					this.stopJump();
				}

				this.calculateEntityAnimation(this, false);


				boolean flag = this.level.getBlockState(this.blockPosition().offset(this.getDirection().getNormal())).canOcclude();

				// Handles the swimming. Travel is only called when player is riding the entity.
				if ((this.wasEyeInWater || this.fluidOnEyes == FluidTags.LAVA) && !flag && this.getDeltaMovement().y < 0) { // Check if the eyes is in water level, and we don't have a solid block the way we are facing. If not, then apply a inverse force, to float the horse.
					this.setDeltaMovement(this.getDeltaMovement().multiply(1, -1.9, 1));
				}

				if (!this.isCameraLocked()) {
					this.yRot = this.lockedYRot;
					this.yRotO = this.yRot;
					this.xRot = this.lockedXRot * 0.5F;
					this.setRot(this.yRot, this.xRot);
					this.yBodyRot = this.yRot;
					this.yHeadRot = this.yBodyRot;
				} else {
					this.yRot = livingentity.yRot;
					this.yRotO = this.yRot;
					this.xRot = livingentity.xRot * 0.5F;
					this.setRot(this.yRot, this.xRot);
					this.yBodyRot = this.yRot;
					this.yHeadRot = this.yBodyRot;
				}
			} else {
				super.travel(travelVector);
			}


		}

	}



	public boolean isCameraLocked() {
		return this.entityData.get(CAMERA_LOCK);
	}

	public void setCameraLock(boolean locked) {
		this.entityData.set(CAMERA_LOCK, locked);
		this.setLockedRotations(this.xRot, this.yRot);
	}

	public void setLockedRotations(float xRot, float yRot) {
		this.lockedXRot = xRot;
		this.lockedYRot = yRot;
	}

	@Override
	public void moveRelative(float pAmount, Vector3d pRelative) {
		Vector3d vector3d = getInputVector(pRelative, pAmount, this.isCameraLocked() ? this.yRot : this.lockedYRot);
		this.setDeltaMovement(this.getDeltaMovement().add(vector3d));
	}

	@Override
	public boolean isOnGround() {
		if (this.isFlying()) {
			BlockState state = this.level.getBlockState(this.blockPosition().below());
			if (state.canOcclude()) {
				return true;
			} else {
				return false;
			}
		}
		return super.isOnGround();
	}


	@Override
	public void onPlayerJump(int p_110206_1_) {
		if (this.isSaddled()) {
			if (p_110206_1_ < 0) {
				p_110206_1_ = 0;
			} else {
				this.allowStandSliding = true;
			}

			if (p_110206_1_ >= 90) {
				this.playerJumpPendingScale = 1.0F;
			} else {
				this.playerJumpPendingScale = 0.4F + 0.4F * (float)p_110206_1_ / 90.0F;
			}

		}
	}

	@Override
	public void knockback(float p_233627_1_, double p_233627_2_, double p_233627_4_) {
	}

	@Override
	public void handleStartJump(int p_184775_1_) {
		if (this.getEntityData().get(FLYING)) {
			this.playFlapWingSound();
		} else {
			this.playJumpSound();
		}
	}

	private void playFlapWingSound() {
		// TODO: ADD A FLAP WING SOUND AND PLAY IT HERE!
	}


	private void startJump(float jumpHeight) {
		SWEMPacketHandler.INSTANCE.sendToServer(new CHorseJumpPacket(this.getId(), jumpHeight));
	}

	private void stopJump() {
		if (this.level.isClientSide) {
			SWEMPacketHandler.INSTANCE.sendToServer(new CHorseJumpPacket(this.getId(), 0.0F));
		}
	}

	@Nullable
	@Override
	public Entity getControllingPassenger() {
		List<Entity> playerEntities = this.getPassengers().stream().filter((entity) -> entity instanceof PlayerEntity).collect(Collectors.toList());
		return this.getPassengers().isEmpty() ? null : playerEntities.isEmpty() ? null : playerEntities.get(0);
	}

	private float getDisobedienceFactor() {
		return this.getRandom().nextFloat();
	}

	@Override
	public boolean isInvulnerableTo(DamageSource source) {
		if (source == DamageSource.DROWN) return true;
		if (DamageSource.IN_FIRE.equals(source) || DamageSource.ON_FIRE.equals(source) || DamageSource.LAVA.equals(source) || DamageSource.HOT_FLOOR.equals(source)) {
			ItemStack stack = this.getSWEMArmor();
			if (!stack.isEmpty() && ((SWEMHorseArmorItem) stack.getItem()).tier.getId() >= 3) {
				return true;
			}
		}

		return super.isInvulnerableTo(source);
	}


	public void levelUpJump() {
		double currentSpeed = this.getAttribute(Attributes.JUMP_STRENGTH).getValue();
		double newSpeed = this.getAlteredJumpStrength();
		this.getAttribute(Attributes.JUMP_STRENGTH).addPermanentModifier(new AttributeModifier(this.progressionManager.getJumpLeveling().getLevelName(), newSpeed - currentSpeed, AttributeModifier.Operation.ADDITION));
	}

	public void levelUpSpeed() {
		// TODO: Remove once speed has been confirmed.
		/*
		double currentSpeed = this.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
		double newSpeed = this.getAlteredMovementSpeed();
		this.getAttribute(Attributes.MOVEMENT_SPEED).addPermanentModifier(new AttributeModifier(this.progressionManager.getSpeedLeveling().getLevelName(), newSpeed - currentSpeed, AttributeModifier.Operation.ADDITION));
		 */

		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getAlteredMovementSpeed());
	}

	public void levelUpHealth() {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getAlteredMaxHealth());
	}

	/**
	 * Called by InventoryBasic.containerChanged() on a array that is never filled.
	 */
	public void onHorseInventoryChanged(IInventory invBasic) {
		this.setSWEMSaddled();
		ItemStack itemstack = this.getArmor();
		super.containerChanged(invBasic);

		ItemStack itemstack1 = this.getArmor();
		if (this.tickCount > 20 && this.isSWEMArmor(itemstack1) && itemstack != itemstack1) {
			this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
		}
		if (this.level.isClientSide) return;
		for (int i = 0; i < invBasic.getContainerSize(); i++) {
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new UpdateHorseInventoryMessage(getId(), i, invBasic.getItem(i)));
		}
	}

	public void containerChanged(IInventory invBasic) {
		this.setSWEMSaddled();
		ItemStack itemstack = this.getArmor();
		super.containerChanged(invBasic);

		ItemStack itemstack1 = this.getArmor();
		if (this.tickCount > 20 && this.isSWEMArmor(itemstack1) && itemstack != itemstack1) {
			this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
		}
	}

	protected boolean isSWEMSaddled() {
		return this.inventory.getItem(2).getItem() instanceof HorseSaddleItem;
	}



	protected void setSWEMSaddled() {
		if (this.level.isClientSide) {
			this.setFlag(4, !this.inventory.getItem(2).isEmpty());
		}
	}

	protected void playGallopSound(SoundType p_190680_1_) {
		super.playGallopSound(p_190680_1_);
		if (this.rand.nextInt(10) == 0) {
			this.playSound(SoundEvents.HORSE_BREATHE, p_190680_1_.getVolume() * 0.6F, p_190680_1_.getPitch());
		}

		ItemStack stack = this.inventory.getItem(1);
		if (isSWEMArmor(stack)) stack.onHorseArmorTick(level, this);
	}

	// Get nom-nom sound
	@Nullable
	protected SoundEvent getEatingSound() {
		return SoundEvents.HORSE_EAT;
	}



	protected SoundEvent getAngrySound() {
		super.getAngrySound();
		return SoundEvents.HORSE_ANGRY;
	}

	@Override
	public Vector3d getDismountLocationForPassenger(LivingEntity pLivingEntity) {
		return super.getDismountLocationForPassenger(pLivingEntity);
	}

	@Override
	public void openInventory(PlayerEntity playerEntity) {
		if (!this.level.isClientSide && (!this.isVehicle() || this.hasPassenger(playerEntity)) && this.isTamed()) {
			ITextComponent horseDisplayName = new StringTextComponent(SWEMUtil.checkTextOverflow(this.getDisplayName().getString(), 18));
			INamedContainerProvider provider = new INamedContainerProvider() {
				@Override
				public ITextComponent getDisplayName() {
					return horseDisplayName;
				}

				@Nullable
				@Override
				public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
					return new SWEMHorseInventoryContainer(p_createMenu_1_, p_createMenu_2_, getId());
				}
			};
			NetworkHooks.openGui((ServerPlayerEntity) playerEntity, provider, buffer ->
					buffer
						.writeInt(getId())
						.writeInt(getId())
			);

		}
	}



	// Item interaction with horse.
	@Override
	public ActionResultType mobInteract(PlayerEntity playerEntity, Hand hand) {
		ItemStack itemstack = playerEntity.getItemInHand(hand);
		if (!this.isBaby()) {
			if (this.isTamed() && playerEntity.isSecondaryUseActive() && !(itemstack.getItem() instanceof TrackerItem) && !(itemstack.getItem() instanceof HorseTackItem)) {
				this.openInventory(playerEntity);
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}

			if (this.isVehicle()) {
				return super.mobInteract(playerEntity, hand);
			}
		}

		Item item = itemstack.getItem();

		if (!itemstack.isEmpty() && item != Items.SADDLE) {
			if (item == Items.LAPIS_LAZULI && playerEntity.getUUID().equals(this.getOwnerUUID())) {
				if (ConfigHolder.SERVER.lapisCycleCoats.get()) {
					this.setHorseVariant((this.getHorseVariant() + 1) % (SWEMCoatColors.values().length - 2));
					ItemStack heldItemCopy = itemstack.copy();
					if (!playerEntity.abilities.instabuild)
						heldItemCopy.shrink(1);
					playerEntity.setItemInHand(hand, heldItemCopy);
					return ActionResultType.SUCCESS;
				}
			}

			if (NEGATIVE_FOOD_ITEMS.test(itemstack)) {
				// Emit negative particle effects.
				if (!this.level.isClientSide) {
					((ServerWorld) this.level).sendParticles(SWEMParticles.BAD.get(), this.getX(), this.getY() + 2.5, this.getZ(), 4, 0.3D, 0.3D, 0.3D, 0.3D);
				}
				return ActionResultType.FAIL;
			}

			if (FOOD_ITEMS.test(itemstack)) {
				if (this.getNeeds().getHunger().getTotalTimesFed() == 7) {
					// Emit negative particle effects.
					if (!this.level.isClientSide)
						((ServerWorld) this.level).sendParticles(SWEMParticles.ECH.get(), this.getX(), this.getY() + 2.5, this.getZ(), 6, 0.3D, 0.3D, 0.3D, 0.3D);

					return ActionResultType.PASS;
				}

				if (!this.level.isClientSide) {
					if (this.getNeeds().getHunger().addPoints(itemstack)) {
						if (!playerEntity.isCreative()) {
							itemstack.shrink(1);
						}
						if (item == SWEMItems.SUGAR_CUBE.get()) {
							this.progressionManager.getAffinityLeveling().addXP(5.0F);
						}

						((ServerWorld) this.level).sendParticles(SWEMParticles.YAY.get(), this.getX(), this.getY() + 2.5, this.getZ(), 3, 0.3D, 0.3D, 0.3D, 0.3D);
					} else {
						((ServerWorld) this.level).sendParticles(SWEMParticles.ECH.get(), this.getX(), this.getY() + 2.5, this.getZ(), 3, 0.3D, 0.3D, 0.3D, 0.3D);
						// Stop the swing from happening
						return ActionResultType.SUCCESS;
					}


				}

				// This here, makes the swing anim, when feeding items. on the client side.
				// Make this fail, if you can't add points.
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}

//			if (this.isBreedingItem(itemstack)) {
//				return this.fedFood(p_230254_1_, itemstack);
//			}



			if (item == Items.WATER_BUCKET) {
				if (!this.level.isClientSide && this.getNeeds().getThirst().canIncrementState()) {
					this.getNeeds().getThirst().incrementState();
					playerEntity.setItemInHand(hand, ((BucketItem) item).getEmptySuccessItem(itemstack, playerEntity));
					((ServerWorld) this.level).sendParticles(SWEMParticles.YAY.get(), this.getX(), this.getY() + 2.5, this.getZ(), 4, 0.3D, 0.3D, 0.3D, 0.3D);
					return ActionResultType.CONSUME;
				} else if (this.level.isClientSide && !this.getNeeds().getThirst().canIncrementState()) {
					// Stop the swing from happening
					return ActionResultType.SUCCESS;

				} else if (!this.level.isClientSide && !this.getNeeds().getThirst().canIncrementState()) {
					// Stop the swing from happening
					((ServerWorld) this.level).sendParticles(SWEMParticles.ECH.get(), this.getX(), this.getY() + 2.5, this.getZ(), 3, 0.3D, 0.3D, 0.3D, 0.3D);
					return ActionResultType.SUCCESS;
				}
			}

			ActionResultType actionresulttype = itemstack.interactLivingEntity(playerEntity, this, hand);

			if (actionresulttype.consumesAction()) {
				if (item instanceof HorseSaddleItem) {
					this.setSWEMSaddled();
				}
				return actionresulttype;
			}

			if (!this.isTamed()) {
				this.makeMad();
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}

			boolean flag = !this.isBaby() && !this.isSWEMSaddled() && (item instanceof HorseSaddleItem);
			boolean flag1 = !this.isBaby() && this.hasHalter() && (item instanceof GirthStrapItem);
			boolean flag2 = !this.isBaby() && this.hasHalter() && (item instanceof BlanketItem);
			boolean flag3 = !this.isBaby() && this.hasHalter() && (item instanceof LegWrapsItem);
			boolean flag4 = !this.isBaby() && this.hasHalter() && (item instanceof BreastCollarItem);
			if (this.isSWEMArmor(itemstack) || flag) {
				this.setSWEMSaddled();
				this.openInventory(playerEntity);
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}
			if (flag1 || flag2 || flag3 || flag4) {
				this.openInventory(playerEntity);
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}

		}

		if (this.isBaby()) {
			return super.mobInteract(playerEntity, hand);
		} else {
			this.doPlayerRide(playerEntity);
			return ActionResultType.sidedSuccess(this.level.isClientSide);
		}
	}

	public void emitBadParticles(ServerWorld world, int count) {
		world.sendParticles(SWEMParticles.BAD.get(), this.getX(), this.getY() + 2.5, this.getZ(), count, 0.3D, 0.3D, 0.3D, 0.3D);;
	}

	public void emitEchParticles(ServerWorld world, int count) {
		world.sendParticles(SWEMParticles.ECH.get(), this.getX(), this.getY() + 2.5, this.getZ(), count, 0.3D, 0.3D, 0.3D, 0.3D);;
	}

	public void emitMehParticles(ServerWorld world, int count) {
		world.sendParticles(SWEMParticles.MEH.get(), this.getX(), this.getY() + 2.5, this.getZ(), count, 0.3D, 0.3D, 0.3D, 0.3D);;
	}

	public void emitYayParticles(ServerWorld world, int count) {
		world.sendParticles(SWEMParticles.YAY.get(), this.getX(), this.getY() + 2.5, this.getZ(), count, 0.3D, 0.3D, 0.3D, 0.3D);;
	}

	public void emitWootParticles(ServerWorld world, int count) {
		world.sendParticles(SWEMParticles.WOOT.get(), this.getX(), this.getY() + 2.5, this.getZ(), count, 0.3D, 0.3D, 0.3D, 0.3D);;
	}



	@Override
	public ActionResultType interactAt(PlayerEntity player, Vector3d vec, Hand hand) {
		// Vec is a local hit vector for the horse, not sure how vec.x and vec.z applies.

		return ActionResultType.PASS;

		/*if (player.isSecondaryUseActive()) {
			return ActionResultType.PASS;
		}

		ItemStack stack = this.getSaddlebag();

		if (stack == ItemStack.EMPTY) {
			return ActionResultType.PASS;
		}

		SaddlebagItem saddleBag = (SaddlebagItem) stack.getItem();

		boolean backHit = this.checkForBackHit(vec);

		if (!backHit) {
			return ActionResultType.PASS;
		}

		if (player.getCommandSenderWorld().isClientSide) return ActionResultType.CONSUME;

		NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
			@Override
			public ITextComponent getDisplayName() {
				return new TranslationTextComponent("container.swem.saddlebag");
			}

			@Nullable
			@Override
			public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
				return new SaddlebagContainer(p_createMenu_1_, p_createMenu_2_, getId());
			}
		}, buffer ->   {
			buffer.writeInt(getId());
			buffer.writeInt(getId());
		});

		return ActionResultType.CONSUME;*/
	}

	private boolean checkForBackHit(Vector3d vec) {
		Direction facing = this.getDirection();

		switch (facing) {
			case NORTH: {
				if (vec.z > 0) {
					SWEM.LOGGER.debug("Back was hit, while facing NORTH.");
					return true;
				}
				break;
			}
			case SOUTH: {
				if (vec.z < 0) {
					SWEM.LOGGER.debug("Back was hit, while facing SOUTH.");
					return true;
				}
				break;
			}
			case EAST: {
				if (vec.x > 0) {
					SWEM.LOGGER.debug("Back was hit, while facing EAST.");
					return true;
				}
				break;
			}
			case WEST: {
				if (vec.x < 0) {
					SWEM.LOGGER.debug("Back was hit, while facing north.");
					return true;
				}
				break;
			}
		}
		return false;
	}

	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
//	public boolean canMateWith(AnimalEntity otherAnimal) {
//		if (otherAnimal == this) {
//			return false;
//		} else if (!(otherAnimal instanceof DonkeyEntity) && !(otherAnimal instanceof HorseEntity)) {
//			return false;
//		} else {
//			return this.canMate() && ((AbstractHorseEntity)otherAnimal).canMate();
//		}
//	}

	public NeedManager getNeeds() {
		return this.needs;
	}

	// createChild method
	public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		AbstractHorseEntity abstracthorseentity;
		HorseEntity horseentity = (HorseEntity)p_241840_2_;
		abstracthorseentity = EntityType.HORSE.create(p_241840_1_);
		int i = this.rand.nextInt(9);
		CoatColors coatcolors;
		if (i < 4) {
//				coatcolors = this.getVariant();
		} else if (i < 8) {
			coatcolors = horseentity.getVariant();
		} else {
			coatcolors = Util.getRandom(CoatColors.values(), this.rand);
		}

		int j = this.rand.nextInt(5);
		CoatTypes coattypes;
		if (j < 2) {
//				coattypes = this.getMarkings();
		} else if (j < 4) {
			coattypes = horseentity.getMarkings();
		} else {
			coattypes = Util.getRandom(CoatTypes.values(), this.rand);
		}

//			((SWEMHorseEntityBase)abstracthorseentity).setVariantAndMarkings(coatcolors, coattypes);

		this.setOffspringAttributes(p_241840_2_, abstracthorseentity);
		return abstracthorseentity;
	}

	public boolean canWearArmor() {
		return true;
	}


	@Nullable
	public ILivingEntityData finalizeSpawn(IServerWorld levelIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		SWEMCoatColors coatcolors;
		if (spawnDataIn instanceof SWEMHorseData) {
			coatcolors = ((SWEMHorseData)spawnDataIn).variant;
		} else {
			coatcolors = SWEMCoatColors.values()[this.rand.nextInt(SWEMCoatColors.values().length - 2)];
			spawnDataIn = new SWEMHorseData(coatcolors);
		}

		this.setHorseVariant(coatcolors.getId());
		//this.setVariantAndMarkings(coatcolors, Util.getRandom(CoatTypes.values(), this.rand));
		return super.finalizeSpawn(levelIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	/**
	 * Called by the server when constructing the spawn packet.
	 * Data should be added to the provided stream.
	 *
	 * @param buffer The packet data stream
	 */
	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeItem(this.inventory.getItem(0));
		buffer.writeItem(this.inventory.getItem(1));
		buffer.writeItem(this.inventory.getItem(2));
		buffer.writeItem(this.inventory.getItem(3));
		buffer.writeItem(this.inventory.getItem(4));
		buffer.writeItem(this.inventory.getItem(5));
		buffer.writeItem(this.inventory.getItem(6));
		buffer.writeItem(this.inventory.getItem(7));
	}

	/**
	 * Called by the client when it receives a Entity spawn packet.
	 * Data should be read out of the stream in the same way as it was written.
	 *
	 * @param additionalData The packet data stream
	 */
	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		this.inventory.setItem(0, additionalData.readItem());
		this.inventory.setItem(1, additionalData.readItem());
		this.inventory.setItem(2, additionalData.readItem());
		this.inventory.setItem(3, additionalData.readItem());
		this.inventory.setItem(4, additionalData.readItem());
		this.inventory.setItem(5, additionalData.readItem());
		this.inventory.setItem(6, additionalData.readItem());
		this.inventory.setItem(7, additionalData.readItem());
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public void decrementSpeed() {
		HorseSpeed oldSpeed = this.currentSpeed;
		if (oldSpeed == HorseSpeed.WALK) return;
		else if (oldSpeed == HorseSpeed.TROT) {
			this.currentSpeed = HorseSpeed.WALK;
		} else if (oldSpeed == HorseSpeed.CANTER_EXT) {
			this.currentSpeed = HorseSpeed.CANTER;
		}
		else if (oldSpeed == HorseSpeed.CANTER) {
			this.currentSpeed = HorseSpeed.TROT;
		}
		else if (oldSpeed == HorseSpeed.GALLOP) {
			this.currentSpeed = HorseSpeed.CANTER_EXT;
			this.setGallopCooldown();
		}
		this.updateSelectedSpeed(oldSpeed);
	}

	public void incrementSpeed() {
		HorseSpeed oldSpeed = this.currentSpeed;
		if (oldSpeed == HorseSpeed.GALLOP) return; // Return if current gait is already max.

		if (this.getRandom().nextDouble() < ((this.progressionManager.getAffinityLeveling().getDebuff() * this.currentSpeed.getSkillMultiplier()) * (this.standingTimer > 0 ? 0.5 : 1))) {
			this.setStandingAnim();
			return;
		}

		else if (oldSpeed == HorseSpeed.CANTER_EXT) {
			if (this.entityData.get(GALLOP_ON_COOLDOWN)) {
				ArrayList<String> args = new ArrayList<>();
				args.add(String.valueOf(Math.round(( this.entityData.get(GALLOP_COOLDOWN_TIMER) - this.entityData.get(GALLOP_TIMER) ) / 20)));
				SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) this.getPassengers().get(0)), new ClientStatusMessagePacket(0, 1, args));
				return;
			}
			this.currentSpeed = HorseSpeed.GALLOP;
		}

		else if (oldSpeed == HorseSpeed.CANTER) {
			this.currentSpeed = HorseSpeed.CANTER_EXT;
		}
		else if (oldSpeed == HorseSpeed.TROT) {
			if (this.needs.getThirst().getState() == ThirstNeed.ThirstState.EXICCOSIS) {
				SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) this.getPassengers().get(0)), new ClientStatusMessagePacket(1, 0, new ArrayList<>()));
				return;
			}

			if (this.needs.getHunger().getState() == HungerNeed.HungerState.STARVING) {
				SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) this.getPassengers().get(0)), new ClientStatusMessagePacket(3, 0, new ArrayList<>()));
				return;
			}

			this.currentSpeed = HorseSpeed.CANTER;

		} else if (oldSpeed == HorseSpeed.WALK) {
			this.currentSpeed = HorseSpeed.TROT;
		}
		this.updateSelectedSpeed(oldSpeed);
	}

	public void updateSelectedSpeed(HorseSpeed oldSpeed) {

		if (this.currentSpeed == HorseSpeed.TROT) {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.123d);
		} else if (this.currentSpeed == HorseSpeed.WALK) {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0425d);
		} else if (this.currentSpeed == HorseSpeed.CANTER) {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.285d);
		} else {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getAlteredMovementSpeed());
		}
		this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(oldSpeed.getModifier());
		if (!this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(this.currentSpeed.getModifier())) {
			this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(this.currentSpeed.getModifier());
		}

		//TODO: Remove once speed has been confirmed.
		/*
		if (oldSpeed == HorseSpeed.CANTER) {
			if (this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(oldSpeed.getModifier())) {
				this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(oldSpeed.getModifier());
			}
		}

		if (this.currentSpeed == HorseSpeed.CANTER) {
			if (!this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(this.currentSpeed.getModifier())) {
				this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(this.currentSpeed.getModifier());
			}
		}
		if (this.currentSpeed == HorseSpeed.CANTER && oldSpeed == HorseSpeed.TROT) {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getAlteredMovementSpeed());
		}

		if (this.currentSpeed == HorseSpeed.WALK) {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.03d);
		} else if (this.currentSpeed == HorseSpeed.TROT) {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.123d);
		}
		 */
		this.entityData.set(SPEED_LEVEL, this.currentSpeed.speedLevel);
	}

	public boolean canFly() {
		if (!(this.getSWEMArmor().getItem() instanceof SWEMHorseArmorItem)) {
			return false;
		}
		return this.hasSaddle().getItem() instanceof AdventureSaddleItem && ((SWEMHorseArmorItem) this.getSWEMArmor().getItem()).tier.getId() == 4;
	}

	public void brush() {
		this.progressionManager.getAffinityLeveling().brush();
	}


	public void cycleRidingPermission() {
		if (RidingPermission.valueOf(this.entityData.get(PERMISSION_STRING)) == RidingPermission.NONE) {
			this.setPermissionState("TRUST");
		} else if (RidingPermission.valueOf(this.entityData.get(PERMISSION_STRING)) == RidingPermission.TRUST) {
			this.setPermissionState("ALL");
		} else {
			this.setPermissionState("NONE");
		}
	}

	public RidingPermission getPermissionState() {
		return RidingPermission.valueOf(this.entityData.get(PERMISSION_STRING));
	}

	private void setPermissionState(String string) {
		this.entityData.set(PERMISSION_STRING, string);
	}

	@Override
	protected int getInventorySize() {
		return 8;
	}

	public boolean isHalter(ItemStack stack) {
		return stack.getItem() instanceof HalterItem;
	}

	public ItemStack getHalter() {
		return this.inventory.getItem(0);
	}

	public boolean isBreastCollar(ItemStack stack) {
		return stack.getItem() instanceof BreastCollarItem;
	}

	public ItemStack getBreastCollar() {
		return this.inventory.getItem(3);
	}

	public boolean isLegWraps(ItemStack stack) {
		return stack.getItem() instanceof LegWrapsItem;
	}

	public ItemStack getLegWraps() {
		return this.inventory.getItem(4);
	}

	public boolean isGirthStrap(ItemStack stack) {
		return stack.getItem() instanceof GirthStrapItem;
	}

	public ItemStack getGirthStrap() {
		return this.inventory.getItem(5);
	}

	private boolean hasBridle() {
		return this.inventory.getItem(0).getItem() instanceof BridleItem;
	}

	public boolean canEquipSaddle() {
		return this.hasBlanket() || !ConfigHolder.SERVER.blanketBeforeSaddle.get();
	}

	public boolean canEquipGirthStrap() {
		return this.isSWEMSaddled() || !ConfigHolder.SERVER.saddleBeforeGirthStrap.get();
	}

	@Override
	public boolean canEquipArmor() {
		return hasAdventureSaddle() && getHalter().getItem() instanceof AdventureBridleItem && getBreastCollar().getItem() instanceof AdventureBreastCollarItem && getGirthStrap().getItem() instanceof AdventureGirthStrapItem && getLegWraps().getItem() instanceof AdventureLegWrapsItem && getBlanket().getItem() instanceof AdventureBlanketItem;
	}

	@Override
	public boolean canBeControlledByRider() {
		if (this.hasBridle() || !ConfigHolder.SERVER.needBridleToSteer.get()) {

			if (this.getControllingPassenger() instanceof LivingEntity
			&& !(this.getControllingPassenger() instanceof AnimalEntity)
			&& this.getControllingPassenger() instanceof PlayerEntity) {
				return true;
			}

			return super.canBeControlledByRider();
		} else {
			return false;
		}
	}

	public boolean isSWEMArmor(ItemStack stack) {
		return stack.getItem() instanceof SWEMHorseArmorItem;
	}

	public ItemStack getSWEMArmor() {
		return this.inventory.getItem(6);
	}

	public boolean isSaddlebag(ItemStack stack) {
		return stack.getItem() instanceof SaddlebagItem;
	}

	public ItemStack getSaddlebag() {
		return this.inventory.getItem(7);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (this.isVehicle() && !this.level.isClientSide) {
			this.progressionManager.getAffinityLeveling().removeXp(amount * 15);
		} else if (source.getEntity() != null && source.getEntity().getUUID().equals(this.getOwnerUUID()) && !this.level.isClientSide) {
			this.progressionManager.getAffinityLeveling().removeXp(amount * 15);
		}
		if (source == DamageSource.FALL) {
			if (this.getHealth() <= 6.0F) {
				amount = 0; // Don't damage the horse, when below 6 HP. Still play hurt anims, and deduct affinity.
			} else if (this.getHealth() - amount < 6.0f) {
				amount = this.getHealth() - 6.0f;
			}
		}
		if (this.standingTimer == 0) {
			this.setStandingAnim();
		}
		if (source.isExplosion()) {
			amount /= 2;
		}
		if (source.getDirectEntity() instanceof AbstractArrowEntity) {
			amount = this.calculateArrowDamage((AbstractArrowEntity) source.getDirectEntity(), amount);
		}
		return super.hurt(source, amount);
	}

	@Override
	protected int calculateFallDamage(float pDistance, float pDamageMultiplier) {
		return MathHelper.ceil((pDistance * 0.5F - 4.0F) * pDamageMultiplier);
	}

	private float calculateArrowDamage(AbstractArrowEntity arrow, float amount) {
		if (!this.isWearingArmor()) return amount;
		if (((SWEMHorseArmorItem)this.getSWEMArmor().getItem()).tier == SWEMHorseArmorItem.HorseArmorTier.IRON) {
			boolean shouldBlock = this.random.nextFloat() > 0.5;
			if (shouldBlock && !arrow.isCritArrow()) {
				amount = 0;
			} else if (shouldBlock && arrow.isCritArrow()) {
				amount = 2;
			}
		} else if (((SWEMHorseArmorItem)this.getSWEMArmor().getItem()).tier == SWEMHorseArmorItem.HorseArmorTier.GOLD) {
			boolean shouldBlock = this.random.nextFloat() > 0.5;
			if (shouldBlock && !arrow.isCritArrow()) {
				amount = 0;
			} else if (shouldBlock && arrow.isCritArrow()) {
				amount = 2;
			}
		} else if (((SWEMHorseArmorItem)this.getSWEMArmor().getItem()).tier == SWEMHorseArmorItem.HorseArmorTier.DIAMOND) {
			boolean shouldBlock = this.random.nextFloat() > 0.25;
			if (shouldBlock && !arrow.isCritArrow()) {
				amount = 0;
			} else if (shouldBlock && arrow.isCritArrow()) {
				amount = 2;
			}
		} else if (((SWEMHorseArmorItem)this.getSWEMArmor().getItem()).tier == SWEMHorseArmorItem.HorseArmorTier.AMETHYST) {
			if (arrow.isCritArrow())
				amount = 2;
			else
				amount = 0;
		}

		return amount;
	}

	public void setStandingTimer(int timeInTicks) {
		this.standingTimer = timeInTicks;
	}

	public void setStandingAnim() {
		this.standAnimationTick = 42;
		this.standAnimationVariant = this.getRandom().nextDouble() > 0.5 ? 2 : 1;

		if (this.level.isClientSide) {
			SWEMPacketHandler.INSTANCE.sendToServer(new SHorseAnimationPacket(this.getEntity().getId(), standAnimationVariant));
			this.standingTimer = 142;
		} else {
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new CHorseAnimationPacket(this.getEntity().getId(), standAnimationVariant));
			this.standingTimer = 142;
		}
	}

	public boolean isBlanket(ItemStack stack) {
		return stack.getItem() instanceof BlanketItem;
	}

	public ItemStack getBlanket() {
		return this.inventory.getItem(1);
	}

	public boolean isSaddle(ItemStack stack) {
		return stack.getItem() instanceof HorseSaddleItem;
	}

	@Override
	public boolean isWearingArmor() {
		return this.inventory.getItem(6).getItem() instanceof SWEMHorseArmorItem;
	}

	public boolean hasSaddleBag() {
		return this.inventory.getItem(7).getItem() instanceof SaddlebagItem;
	}

	public float getJumpHeight() {
		float jumpStrength = (float) this.getCustomJump();
		float jumpHeight = (float) (-0.1817584952 * ((float)Math.pow(jumpStrength, 3.0F)) + 3.689713992 * ((float)Math.pow(jumpStrength, 2.0F)) + 2.128599134 * jumpStrength - 0.343930367);
		return jumpHeight;
	}

	public ITextComponent getOwnerDisplayName() {
		UUID PlayerUUID = this.getOwnerUUID();
		if (PlayerUUID == null) {
			return new TranslationTextComponent("Not owned.");
		}
		return this.level.getPlayerByUUID(PlayerUUID).getDisplayName();
	}

	public void setWhistlePos(BlockPos pos) {
		this.whistlePos = pos;
	}

	public enum HorseSpeed {

		/*
		WALK(new AttributeModifier("HORSE_WALK", 0, AttributeModifier.Operation.ADDITION), 0),
		TROT(new AttributeModifier("HORSE_TROT", 0, AttributeModifier.Operation.ADDITION), 1),
		 */
		WALK(new AttributeModifier("HORSE_WALK", 0, AttributeModifier.Operation.ADDITION), 0, 0.05f),
		TROT(new AttributeModifier("HORSE_TROT", 0, AttributeModifier.Operation.ADDITION), 1, 0.1f),
		CANTER(new AttributeModifier("HORSE_CANTER", 0, AttributeModifier.Operation.ADDITION), 2, 0.5f),
		CANTER_EXT(new AttributeModifier("HORSE_CANTER_EXT", 0, AttributeModifier.Operation.ADDITION), 3, 0.8f),
		GALLOP(new AttributeModifier("HORSE_GALLOP", 0.2d, AttributeModifier.Operation.MULTIPLY_TOTAL), 4, 1.0f);
		private AttributeModifier modifier;
		private int speedLevel;
		private float skillMultiplier;
		HorseSpeed(AttributeModifier modifier, int speedLevel, float skillMultiplier) {
			this.modifier = modifier;
			this.speedLevel = speedLevel;
			this.skillMultiplier = skillMultiplier;
		}

		public AttributeModifier getModifier() {
			return this.modifier;
		}

		public int getSpeedLevel() {
			return this.speedLevel;
		}

		public float getSkillMultiplier() {
			return this.skillMultiplier;
		}

	}

	public enum RidingPermission {

		NONE,
		TRUST,
		ALL;
	}

	public static class SWEMHorseData extends AgeableData {
		public final SWEMCoatColors variant;

		public SWEMHorseData(SWEMCoatColors p_i231557_1_) {
			super(false);
			this.variant = p_i231557_1_;
		}
	}
}
