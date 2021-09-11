package com.alaharranhonor.swem.entities;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.container.SWEMHorseInventoryContainer;
import com.alaharranhonor.swem.container.SaddlebagContainer;
import com.alaharranhonor.swem.entities.ai.*;
import com.alaharranhonor.swem.entities.misc.WhistleManager;
import com.alaharranhonor.swem.entities.misc.WhistleManagerProvider;
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
import com.alaharranhonor.swem.util.SWEMUtil;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import com.alaharranhonor.swem.util.registry.SWEMParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.entity.model.PlayerModel;
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
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
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
import net.minecraftforge.common.util.LazyOptional;
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
		implements ISWEMEquipable, IEntityAdditionalSpawnData, WhistleManagerProvider<SWEMHorseEntityBase> {

	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
	private static final DataParameter<Integer> HORSE_VARIANT = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);

	public static final Ingredient TEMPTATION_ITEMS = Ingredient.of(SWEMItems.AMETHYST.get());
	public static final Ingredient FOOD_ITEMS = Ingredient.of(Items.APPLE, Items.CARROT, SWEMItems.OAT_BUSHEL.get(), SWEMItems.TIMOTHY_BUSHEL.get(), SWEMItems.ALFALFA_BUSHEL.get(), SWEMBlocks.QUALITY_BALE_ITEM.get(), SWEMItems.SUGAR_CUBE.get());
	public static final Ingredient NEGATIVE_FOOD_ITEMS = Ingredient.of(Items.WHEAT, Items.HAY_BLOCK);
	private static final DataParameter<Boolean> FLYING = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Boolean> JUMPING = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	private static final DataParameter<String> OWNER_NAME = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.STRING);
	private static final EntitySize JUMPING_SIZE = EntitySize.scalable(1.5f, 1.5f);
	private PathNavigator oldNavigator;
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
	private ArrayList<UUID> allowedList = new ArrayList<>();

	public HorseSpeed currentSpeed;

	private NeedManager needs;
	private HorseFlightController flightController;

	// Animation variable.
	public double jumpHeight;

	private final WhistleManager<SWEMHorseEntityBase> whistleManager;

	public SWEMHorseEntityBase(EntityType<? extends AbstractHorseEntity> type, World levelIn)
	{
		super(type, levelIn);
		this.currentPos = this.blockPosition();
		this.progressionManager = new ProgressionManager(this);
		this.currentSpeed = HorseSpeed.TROT;
		this.needs = new NeedManager(this);
		this.whistleManager = new WhistleManager<>(this);
		this.initSaddlebagInventory();
		this.initBedrollInventory();
		this.oldNavigator = navigation;
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
		super.registerGoals();
		this.goalSelector.addGoal(0, new WalkToWhistlerGoal<>(this));
		//this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicStraightGoal(this, 1.2D));
		//this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2D));
		//this.goalSelector.addGoal(2, new BreedGoal(this, 1.0d));
		//this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, TEMPTATION_ITEMS, false));
		//this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PigEntity.class, 12.0f, 1.0d, 1.0d));
		this.goalSelector.addGoal(5, new PoopGoal(this));
		this.goalSelector.addGoal(5, new PeeGoal(this));
		//this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
		//this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(8, new LookForFoodGoal(this, 1.0d));
		this.goalSelector.addGoal(8, new LookForWaterGoal(this, 1.0d));
		this.goalSelector.addGoal(9, new EatGrassGoal(this));
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
	public void aiStep()
	{


		if (!this.level.isClientSide) {
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

			//this.needs.tick();
		}
		super.aiStep();
	}

	private void resetDaily() {
		this.needs.getHunger().resetDaily();
		this.progressionManager.getAffinityLeveling().resetCurrentSwipes();
	}




	private double getAlteredMovementSpeed()
	{
		switch (this.progressionManager.getSpeedLeveling().getLevel()) {
			case 1:
				return 0.284629981024667d;
			case 2:
				return 0.332068311195445d;
			case 3:
				return 0.379506641366223d;
			case 4:
				return 0.426944971537001d;
			default:
				return 0.237191650853889d;
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
		this.entityData.define(HORSE_VARIANT, 12);

		this.entityData.define(FLYING, false);
		this.entityData.define(JUMPING, false);
		this.entityData.define(OWNER_NAME, "");

		this.getEntityData().define(isLaunching, false);
		this.getEntityData().define(isFloating, false);
		this.getEntityData().define(isAccelerating, false);
		this.getEntityData().define(isSlowingDown, false);
		this.getEntityData().define(isTurningLeft, false);
		this.getEntityData().define(isTurning, false);
		this.getEntityData().define(isStillTurning, false);
		this.getEntityData().define(didFlap, false);
		this.getEntityData().define(isDiving, false);
		this.entityData.define(PERMISSION_STRING, "NONE");

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
		return super.isJumping();
	}

	public boolean canMountPlayer(PlayerEntity player) {
		if (!this.isTamed()) return true;
		if (Objects.equals(this.getOwnerUUID(), player.getUUID())) return true;

		if (RidingPermission.valueOf(this.entityData.get(PERMISSION_STRING)) == RidingPermission.NONE) return false;
		else if (RidingPermission.valueOf(this.entityData.get(PERMISSION_STRING)) == RidingPermission.EVERYONE) return true;
		else {
			return this.allowedList.contains(player.getUUID());
		}
	}


	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby() && this.isTamed();
	}

	public void equipSaddle(@Nullable SoundCategory p_230266_1_, ItemStack stackIn) {
		ItemStack stack = stackIn.copy();
		if (stack.getItem() instanceof HorseSaddleItem) {
			this.inventory.setItem(2, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 2, stack));
			if (p_230266_1_ != null) {
				this.level.playSound((PlayerEntity)null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof BlanketItem) {
			this.inventory.setItem(1, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 1, stack));
			if (p_230266_1_ != null) {
				this.level.playSound((PlayerEntity)null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof BreastCollarItem) {
			this.inventory.setItem(3, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 3, stack));
			if (p_230266_1_ != null) {
				this.level.playSound((PlayerEntity)null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof HalterItem) {
			this.inventory.setItem(0, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 0, stack));
			if (p_230266_1_ != null) {
				this.level.playSound((PlayerEntity)null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof GirthStrapItem) {
			this.inventory.setItem(5, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 5, stack));
			if (p_230266_1_ != null) {
				this.level.playSound((PlayerEntity)null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof LegWrapsItem) {
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
		super.makeMad();
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
	protected int calculateFallDamage(float distance, float damageMultiplier) {
		return 0; //TODO: MAKE THE HORSE GO DOWN TO 3 HEARTS AT MAX.
	}

	@Override
	public int getMaxFallDistance() {
		return 5000;
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
		this.currentSpeed = HorseSpeed.TROT;
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
		this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.inventory));
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
		this.saddlebagItemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.saddlebagInventory));
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
		this.bedrollItemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.bedrollInventory));
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

		this.setHorseVariant(compound.getInt("HorseVariant"));

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

	}


	public void addAllowedUUID(UUID playerUUID) {
		if (!this.allowedList.contains(playerUUID)) {
			this.allowedList.add(playerUUID);
		}
	}

	public void removeAllowedUUID(UUID playerUUID) {
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

				if (this.canBeControlledByRider() && this.isVehicle() && this.currentSpeed != HorseSpeed.WALK && this.currentSpeed != HorseSpeed.TROT) {
					int x = this.blockPosition().getX();
					int z = this.blockPosition().getZ();
					if (x != this.currentPos.getX() || z != this.currentPos.getZ()) {
						x = Math.abs(x - this.currentPos.getX());
						z = Math.abs(z - this.currentPos.getZ());
						int dist = ((int)Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)));
						if (dist > 0 && dist < 25) {

							if (this.currentSpeed == HorseSpeed.CANTER) {
								this.progressionManager.getSpeedLeveling().addXP(dist);
							} else if (this.currentSpeed == HorseSpeed.GALLOP) {
								this.progressionManager.getSpeedLeveling().addXP(dist * 2);
							}

							// Affinity leveling, is not affected by speed. so no matter the speed, just add 1xp per block.
							this.progressionManager.getAffinityLeveling().addXP(dist);
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


		}
		super.tick();
		if (this.isInWater() && !this.wasEyeInWater && !this.isVehicle()) {
			if (this.getDeltaMovement().y > 0) {
				this.setDeltaMovement(this.getDeltaMovement().x, -.15, this.getDeltaMovement().z); // Set the motion on y with a negative force, because the horse is floating to the top, pull it down, until wasEyeInWater returns true.
			}
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
						if (blockstate2.getMaterial() == Material.WATER && isFull && blockstate.canSurvive(level, blockpos) && level.isUnobstructed(blockstate, blockpos, ISelectionContext.empty()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(this, net.minecraftforge.common.util.BlockSnapshot.create(level.dimension(), level, blockpos), net.minecraft.util.Direction.UP)) {
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
						if (blockstate2.getMaterial() == Material.LAVA && isFull && blockstate.canSurvive(level, blockpos) && level.isUnobstructed(blockstate, blockpos, ISelectionContext.empty()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(this, net.minecraftforge.common.util.BlockSnapshot.create(level.dimension(), level, blockpos), net.minecraft.util.Direction.UP)) {
							level.setBlock(blockpos, blockstate, 3);
							level.getBlockTicks().scheduleTick(blockpos, SWEMBlocks.TEARING_MAGMA.get(), 20);
						}
					}
				}
			}

		} else if (this.isInLava() && !this.wasEyeInWater && !this.isVehicle()) {
			if (this.getDeltaMovement().y > 0) {
				this.setDeltaMovement(this.getDeltaMovement().x, -.15, this.getDeltaMovement().z); // Set the motion on y with a negative force, because the horse is floating to the top, pull it down, until wasEyeInWater returns true.
			}
		}


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
			if (this.isVehicle() && this.canBeControlledByRider() && this.isHorseSaddled()) {
				PlayerEntity livingentity = (PlayerEntity) this.getControllingPassenger();

				this.yRot = livingentity.yRot;
				this.yRotO = this.yRot;
				this.xRot = livingentity.xRot * 0.5F;
				this.setRot(this.yRot, this.xRot);
				this.yBodyRot = this.yRot;
				this.yHeadRot = this.yBodyRot;
				float f = livingentity.xxa * 0.5F;
				float f1 = livingentity.zza;
				if (f1 <= 0.0F) {
					f1 *= 0.25F;
					this.gallopSoundCounter = 0;
				}

				if (this.onGround && this.playerJumpPendingScale == 0.0F && this.isStanding() && !this.allowStandSliding) {
					f = 0.0F;
					f1 = 0.0F;
				}


				// Check if RNG is higher roll, than disobeying debuff, if so, then do the jump.
				if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.onGround) {
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
					net.minecraftforge.common.ForgeHooks.onLivingJump(this);
					if (f1 > 0.0F) {
						float f2 = MathHelper.sin(this.yRot * ((float) Math.PI / 180F));
						float f3 = MathHelper.cos(this.yRot * ((float) Math.PI / 180F));
						this.setDeltaMovement(this.getDeltaMovement().add((double) (-0.4F * f2 * this.playerJumpPendingScale), 0.0D, (double) (0.4F * f3 * this.playerJumpPendingScale)));
					}


					this.playerJumpPendingScale = 0.0F;
					//} else {
					//	this.makeMad();
					//}
				}


				this.flyingSpeed = this.getSpeed() * 0.1F;
				if (this.isControlledByLocalInstance()) {
					this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
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
				if (this.wasEyeInWater && !flag && this.getDeltaMovement().y < 0) { // Check if the eyes is in water level, and we don't have a solid block the way we are facing. If not, then apply a inverse force, to float the horse.
					this.setDeltaMovement(this.getDeltaMovement().multiply(1, -1.9, 1));
				}
			} else {
				super.travel(travelVector);
			}
		}

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
		SWEMPacketHandler.INSTANCE.sendToServer(new CHorseJumpPacket(this.getId(), true, jumpHeight));
	}

	private void stopJump() {
		if (this.getEntityData().get(JUMPING)) {
			SWEMPacketHandler.INSTANCE.sendToServer(new CHorseJumpPacket(this.getId(), false, 0.0F));
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
		if (source == DamageSource.FALL) return true;
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
		double currentSpeed = this.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
		double newSpeed = this.getAlteredMovementSpeed();
		this.getAttribute(Attributes.MOVEMENT_SPEED).addPermanentModifier(new AttributeModifier(this.progressionManager.getSpeedLeveling().getLevelName(), newSpeed - currentSpeed, AttributeModifier.Operation.ADDITION));
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
			if (this.isTamed() && playerEntity.isSecondaryUseActive()) {
				this.openInventory(playerEntity);
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}

			if (this.isVehicle()) {
				return super.mobInteract(playerEntity, hand);
			}
		}

		Item item = itemstack.getItem();

		if (!itemstack.isEmpty() && item != Items.SADDLE) {
			if (item == Items.LAPIS_LAZULI) {
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
				return ActionResultType.FAIL;
			}

			if (FOOD_ITEMS.test(itemstack)) {
				if (this.getNeeds().getHunger().getTotalTimesFed() == 7) {
					// Emit negative particle effects.
					return ActionResultType.FAIL;
				}

				if (item == SWEMItems.SUGAR_CUBE.get()) {
					// Add some affinity points and spawn particles.
					if (!this.level.isClientSide) {
						this.progressionManager.getAffinityLeveling().addXP(5.0F);
						this.getNeeds().getHunger().addPoints(itemstack);

						this.level.addParticle(SWEMParticles.YAY.get(), this.getX(), this.getY() + 1.5, this.getZ(), 3.0, 0.3D, 0.3D);
					}

				}
				if (!this.level.isClientSide) {
					this.getNeeds().getHunger().addPoints(itemstack);
				}
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}

//			if (this.isBreedingItem(itemstack)) {
//				return this.fedFood(p_230254_1_, itemstack);
//			}



			if (item == Items.WATER_BUCKET) {
				SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(0, this.getId()));
				playerEntity.setItemInHand(hand, ((BucketItem) item).getEmptySuccessItem(itemstack, playerEntity));
				return ActionResultType.sidedSuccess(this.level.isClientSide);
			}

			ActionResultType actionresulttype = itemstack.interactLivingEntity(playerEntity, this, hand);
			System.out.println("Item interaction hit");
			if (actionresulttype.consumesAction()) {
				if (item instanceof HorseSaddleItem && actionresulttype.consumesAction()) {
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



	@Override
	public ActionResultType interactAt(PlayerEntity player, Vector3d vec, Hand hand) {
		// Vec is a local hit vector for the horse, not sure how vec.x and vec.z applies.


		if (player.isSecondaryUseActive()) {
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

		return ActionResultType.CONSUME;
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
		CoatColors coatcolors;
		if (spawnDataIn instanceof HorseEntity.HorseData) {
			coatcolors = ((HorseEntity.HorseData)spawnDataIn).variant;
		} else {
			coatcolors = Util.getRandom(CoatColors.values(), this.rand);
			spawnDataIn = new HorseEntity.HorseData(coatcolors);
		}

		this.setVariantAndMarkings(coatcolors, Util.getRandom(CoatTypes.values(), this.rand));
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
		} else if (oldSpeed == HorseSpeed.CANTER) {
			this.currentSpeed = HorseSpeed.TROT;
		}
		else if (oldSpeed == HorseSpeed.GALLOP) {
			this.currentSpeed = HorseSpeed.CANTER;
			this.setGallopCooldown();
		}
		this.updateSelectedSpeed(oldSpeed);
	}

	public void incrementSpeed() {
		HorseSpeed oldSpeed = this.currentSpeed;
		if (oldSpeed == HorseSpeed.GALLOP) return;
		else if (oldSpeed == HorseSpeed.CANTER) {
			if (this.entityData.get(GALLOP_ON_COOLDOWN)) {
				ArrayList<String> args = new ArrayList<>();
				args.add(String.valueOf(Math.round(( this.entityData.get(GALLOP_COOLDOWN_TIMER) - this.entityData.get(GALLOP_TIMER) ) / 20)));
				SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) this.getPassengers().get(0)), new ClientStatusMessagePacket(0, 1, args));
				return;
			}
			this.currentSpeed = HorseSpeed.GALLOP;
		}
		else if (oldSpeed == HorseSpeed.TROT) {
			if (this.needs.getThirst().getState() == ThirstNeed.ThirstState.EXICCOSIS || this.needs.getHunger().getState() == HungerNeed.HungerState.STARVING) {
				SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) this.getPassengers().get(0)), new ClientStatusMessagePacket(1, 0, new ArrayList<>()));
				return;
			}

			this.currentSpeed = HorseSpeed.CANTER;

		} else if (oldSpeed == HorseSpeed.WALK) {
			this.currentSpeed = HorseSpeed.TROT;
		}
		this.updateSelectedSpeed(oldSpeed);
	}

	public void updateSelectedSpeed(HorseSpeed oldSpeed) {
		this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(oldSpeed.getModifier());
		if (!this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(this.currentSpeed.getModifier())) {
			this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(this.currentSpeed.getModifier());
		}
		this.entityData.set(SPEED_LEVEL, this.currentSpeed.speedLevel);
	}

	public boolean canFly() {
		// && ((SWEMHorseArmorItem) this.getSWEMArmor().getItem()).tier.getId() == 4
		if (!this.hasSaddle().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public WhistleManager<SWEMHorseEntityBase> getWhistleManager() {
		return this.whistleManager;
	}

	public void cycleRidingPermission() {
		if (RidingPermission.valueOf(this.entityData.get(PERMISSION_STRING)) == RidingPermission.NONE) {
			this.setPermissionState("ALLOWED");
		} else if (RidingPermission.valueOf(this.entityData.get(PERMISSION_STRING)) == RidingPermission.ALLOWED) {
			this.setPermissionState("EVERYONE");
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

	public static class HorseData extends AgeableEntity.AgeableData {
		public final CoatColors variant;

		public HorseData(CoatColors p_i231557_1_) {
			super(true);
			this.variant = p_i231557_1_;
		}
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
		return super.hurt(source, amount);
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
		return super.isWearingArmor();
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

	public enum HorseSpeed {

		WALK(new AttributeModifier("HORSE_WALK", -0.85d, AttributeModifier.Operation.MULTIPLY_TOTAL), 0),
		TROT(new AttributeModifier("HORSE_TROT", -0.65d, AttributeModifier.Operation.MULTIPLY_TOTAL), 1),
		CANTER(new AttributeModifier("HORSE_CANTER", -0.1d, AttributeModifier.Operation.MULTIPLY_TOTAL), 2),
		GALLOP(new AttributeModifier("HORSE_GALLOP", 0, AttributeModifier.Operation.ADDITION), 3);
		private AttributeModifier modifier;
		private int speedLevel;
		HorseSpeed(AttributeModifier modifier, int speedLevel) {
			this.modifier = modifier;
			this.speedLevel = speedLevel;
		}

		public AttributeModifier getModifier() {
			return this.modifier;
		}

		public int getSpeedLevel() {
			return this.speedLevel;
		}

	}

	public enum RidingPermission {

		NONE,
		ALLOWED,
		EVERYONE;
	}
}
