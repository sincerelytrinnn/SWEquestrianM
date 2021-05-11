package com.alaharranhonor.swem.entities;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.config.ServerConfig;
import com.alaharranhonor.swem.container.SWEMHorseInventoryContainer;
import com.alaharranhonor.swem.container.SaddlebagContainer;
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
import com.alaharranhonor.swem.util.SWEMUtil;
import com.alaharranhonor.swem.util.initialization.SWEMBlocks;
import com.alaharranhonor.swem.util.initialization.SWEMItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.passive.horse.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BucketItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class 	SWEMHorseEntityBase extends AbstractHorseEntity implements ISWEMEquipable, IEntityAdditionalSpawnData {




	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
	private static final DataParameter<Integer> HORSE_VARIANT = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.VARINT);

	public static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(SWEMItems.AMETHYST.get());
	public static final Ingredient FOOD_ITEMS = Ingredient.fromItems(Items.APPLE, Items.CARROT, SWEMItems.OAT_BUSHEL.get(), SWEMItems.TIMOTHY_BUSHEL.get(), SWEMItems.ALFALFA_BUSHEL.get(), SWEMBlocks.QUALITY_BALE_ITEM.get(), SWEMItems.SUGAR_CUBE.get());
	public static final Ingredient NEGATIVE_FOOD_ITEMS = Ingredient.fromItems(Items.WHEAT, Items.HAY_BLOCK);
	private static final DataParameter<Boolean> FLYING = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> JUMPING = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	private PathNavigator oldNavigator;
	private EatGrassGoal eatGrassGoal;
	private PoopGoal poopGoal;
	private PeeGoal peeGoal;
	private int SWEMHorseGrassTimer;
	private int SWEMHorsePoopTimer;
	private int SWEMHorsePeeTimer;
	private static Random rand = new Random();

	public final ProgressionManager progressionManager;
	private BlockPos currentPos;

	private LazyOptional<InvWrapper> itemHandler;
	private LazyOptional<InvWrapper> saddlebagItemHandler;
	private LazyOptional<InvWrapper> bedrollItemHandler;
	private Inventory saddlebagInventory;
	private Inventory bedrollInventory;
	public static DataParameter<Boolean> whistleBound = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);

	private static final DataParameter<Integer> GALLOP_TIMER = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> GALLOP_COOLDOWN_TIMER = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> GALLOP_ON_COOLDOWN = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
	public static DataParameter<Integer> SPEED_LEVEL = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.VARINT);

	@Nullable
	private LivingEntity whistleCaller;

	public HorseSpeed currentSpeed;

	private NeedManager needs;
	private boolean isLaunching;
	private boolean isLanding;

	public SWEMHorseEntityBase(EntityType<? extends AbstractHorseEntity> type, World worldIn)
	{
		super(type, worldIn);
		this.currentPos = this.getPosition();
		this.progressionManager = new ProgressionManager(this);
		this.currentSpeed = HorseSpeed.TROT;
		this.needs = new NeedManager(this);
		this.initSaddlebagInventory();
		this.initBedrollInventory();
		this.oldNavigator = navigator;
	}

	@Override
	protected void func_230273_eI_() {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getAlteredMaxHealth());
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getAlteredMovementSpeed());
		this.getAttribute(Attributes.HORSE_JUMP_STRENGTH).setBaseValue(this.getAlteredJumpStrength());
	}

	// func_233666_p_ -> registerAttributes()
	public static AttributeModifierMap.MutableAttribute setCustomAttributes()
	{
		return MobEntity.func_233666_p_()
				.createMutableAttribute(Attributes.MAX_HEALTH)
				.createMutableAttribute(Attributes.HORSE_JUMP_STRENGTH)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED);
	}

	@Override
	protected void registerGoals() {
		// TODO: ADD AI TO FOLLOW WHISTLE POSITION AS TOP PRIORITY
		super.registerGoals();
		this.eatGrassGoal = new EatGrassGoal(this);
		this.poopGoal = new PoopGoal(this);
		this.peeGoal = new PeeGoal(this);
		this.goalSelector.addGoal(0, new FollowWhistleGoal(this, 1.0d));
		//this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new LookForWaterGoal(this, 1.0d));
		this.goalSelector.addGoal(1, new LookForFoodGoal(this, 1.0d));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.2D));
		//this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0d));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, TEMPTATION_ITEMS, false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0D));
		//this.goalSelector.addGoal(5, this.poopGoal);
		//this.goalSelector.addGoal(5, this.peeGoal);
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
		this.goalSelector.addGoal(7, this.eatGrassGoal);
		//this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
	}

	@Override
	protected int getExperiencePoints(PlayerEntity player) {
		return 0;
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_HORSE_AMBIENT;
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_HORSE_DEATH;
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		super.getHurtSound(damageSourceIn);
		return SoundEvents.ENTITY_HORSE_HURT;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.ENTITY_HORSE_STEP, 0.15f, 1.0f);
	}




	@Override
	protected void updateAITasks()
	{
		this.SWEMHorsePoopTimer = this.poopGoal.getPoopTimer();
		this.SWEMHorseGrassTimer = this.eatGrassGoal.getEatingGrassTimer();
		this.SWEMHorsePeeTimer = this.peeGoal.getPeeTimer();
		super.updateAITasks();
	}

	@Override
	public void livingTick()
	{

		if (this.world.isRemote)
		{
			this.SWEMHorsePoopTimer = Math.max(0, this.SWEMHorsePoopTimer - 1);
			this.SWEMHorseGrassTimer = Math.max(0, this.SWEMHorseGrassTimer - 1);
			this.SWEMHorsePeeTimer = Math.max(0, this.SWEMHorsePeeTimer - 1);

			if (this.onGround && this.isHorseJumping()) {
				this.jumpPower = 0.0F;
				this.setHorseJumping(false);
				SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(8, this.getEntityId()));
			}
		}
		if (!this.world.isRemote) {
			if ((int)(this.world.getDayTime() % 24000L) == 10000) {
				this.resetDaily();
			}

			if (this.dataManager.get(GALLOP_ON_COOLDOWN)) {
				// Count the cooldown.
				this.dataManager.set(GALLOP_TIMER, this.dataManager.get(GALLOP_TIMER) + 1);
				int currentTimer = this.dataManager.get(GALLOP_TIMER);
				int cooldownTimer = this.dataManager.get(GALLOP_COOLDOWN_TIMER);
				if (currentTimer >= cooldownTimer) {
					this.resetGallopCooldown();
				}
			} else if (this.currentSpeed == HorseSpeed.GALLOP && !this.dataManager.get(GALLOP_ON_COOLDOWN)) {
				// COUNT
				int timer = this.dataManager.get(GALLOP_TIMER);
				this.dataManager.set(GALLOP_TIMER, this.dataManager.get(GALLOP_TIMER) + 1);
				if (timer == 7*20) {
					this.decrementSpeed();
				}
			}

			//this.needs.tick();
		}
		super.livingTick();
	}

	private void resetDaily() {
		this.needs.getHunger().resetDaily();
		this.progressionManager.getAffinityLeveling().resetCurrentSwipes();
	}

	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdates(byte id)
	{
		if (id == 10)
		{
			this.SWEMHorseGrassTimer = 40;
			this.SWEMHorsePoopTimer = 80;
			this.SWEMHorsePeeTimer = 80;
		} else {
			super.handleStatusUpdate(id);
		}
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

	protected void registerData() {
		super.registerData();
		//this.dataManager.register(HORSE_VARIANT, 0);

		this.dataManager.register(SpeedLeveling.LEVEL, 0);
		this.dataManager.register(SpeedLeveling.XP, 0.0f);

		this.dataManager.register(JumpLeveling.LEVEL, 0);
		this.dataManager.register(JumpLeveling.XP, 0.0f);

		this.dataManager.register(HealthLeveling.LEVEL, 0);
		this.dataManager.register(HealthLeveling.XP, 0.0f);

		this.dataManager.register(AffinityLeveling.LEVEL, 0);
		this.dataManager.register(AffinityLeveling.XP, 0.0f);

		this.dataManager.register(HungerNeed.HungerState.ID, 4);
		this.dataManager.register(ThirstNeed.ThirstState.ID, 4);

		this.dataManager.register(whistleBound, false);

		this.dataManager.register(GALLOP_ON_COOLDOWN, false);
		this.dataManager.register(GALLOP_COOLDOWN_TIMER, 0);
		this.dataManager.register(GALLOP_TIMER, 0);
		this.dataManager.register(SPEED_LEVEL, 0);

		this.dataManager.register(HungerNeed.TOTAL_TIMES_FED, 0);

		this.dataManager.register(AffinityLeveling.CURRENT_DESENSITIZING_ITEM, ItemStack.EMPTY);
		this.dataManager.register(HORSE_VARIANT, 12);

		this.dataManager.register(FLYING, false);
		this.dataManager.register(JUMPING, false);

	}

	@Override
	public void setHorseJumping(boolean jumping) {
		super.setHorseJumping(jumping);
		if (!this.world.isRemote)
			this.dataManager.set(JUMPING, jumping);
	}

	@Override
	public boolean isHorseJumping() {
		return this.dataManager.get(JUMPING);
	}

	public boolean func_230264_L__() {
		return this.isAlive() && !this.isChild() && this.isTame();
	}

	public void func_230266_a_(@Nullable SoundCategory p_230266_1_, ItemStack stackIn) {
		ItemStack stack = stackIn.copy();
		if (stack.getItem() instanceof HorseSaddleItem) {
			this.horseChest.setInventorySlotContents(2, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getEntityId(), 2, stack));
			if (p_230266_1_ != null) {
				this.world.playMovingSound((PlayerEntity)null, this, SoundEvents.ENTITY_HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof BlanketItem) {
			this.horseChest.setInventorySlotContents(1, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getEntityId(), 1, stack));
			if (p_230266_1_ != null) {
				this.world.playMovingSound((PlayerEntity)null, this, SoundEvents.ENTITY_HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof BreastCollarItem) {
			this.horseChest.setInventorySlotContents(3, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getEntityId(), 3, stack));
			if (p_230266_1_ != null) {
				this.world.playMovingSound((PlayerEntity)null, this, SoundEvents.ENTITY_HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof HalterItem) {
			this.horseChest.setInventorySlotContents(0, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getEntityId(), 0, stack));
			if (p_230266_1_ != null) {
				this.world.playMovingSound((PlayerEntity)null, this, SoundEvents.ENTITY_HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof GirthStrapItem) {
			this.horseChest.setInventorySlotContents(5, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getEntityId(), 5, stack));
			if (p_230266_1_ != null) {
				this.world.playMovingSound((PlayerEntity)null, this, SoundEvents.ENTITY_HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof LegWrapsItem) {
			this.horseChest.setInventorySlotContents(4, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getEntityId(), 4, stack));
			if (p_230266_1_ != null) {
				this.world.playMovingSound((PlayerEntity)null, this, SoundEvents.ENTITY_HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof SWEMHorseArmorItem) {
			this.horseChest.setInventorySlotContents(6, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getEntityId(), 6, stack));
			if (p_230266_1_ != null) {
				this.world.playMovingSound((PlayerEntity)null, this, SoundEvents.ENTITY_HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
			}
		} else if (stack.getItem() instanceof SaddlebagItem) {
			this.horseChest.setInventorySlotContents(7, stack);
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getEntityId(), 7, stack));
			if (p_230266_1_ != null) {
				this.world.playMovingSound((PlayerEntity)null, this, SoundEvents.ENTITY_HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
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
	public double getMountedYOffset() {
		double def = (double)(this.getSize(this.getPose()).height * 0.75D);
		def += 0.15D;
		return def;
	}

	@Override
	public void positionRider(Entity entity, IMoveCallback callback) {
		if (this.isPassenger(entity)) {
			double d0 = this.getPosY() + this.getMountedYOffset() + entity.getYOffset();
			callback.accept(entity, this.getPosX(), d0, this.getPosZ());
		}
	}

	public boolean isFlying() {
		return this.dataManager.get(FLYING);
	}

	public void setFlying(boolean flying) {
		this.dataManager.set(FLYING, flying);
		if (flying) {
			this.setNoGravity(true);
			this.launchFlight();
		} else {
			this.landFlight();
		}
	}

	private void launchFlight() {
		int airHeight = this.checkHeightInAir();
		if (airHeight < 6) {
			this.isLaunching = true;
		}
	}

	private void landFlight() {
		int airHeight = this.checkHeightInAir();
		if (airHeight > 1) {
			this.isLanding = true;
		}
	}


	private int checkHeightInAir() {
		BlockPos currentPos = this.getPosition();
		BlockState checkState = this.world.getBlockState(currentPos);
		int counter = 0;
		while (checkState == Blocks.AIR.getDefaultState()) {
			counter++;
			currentPos = currentPos.down();
			checkState = this.world.getBlockState(currentPos);
		}
		return counter;
	}



	@Override
	protected int calculateFallDamage(float distance, float damageMultiplier) {
		return 0;
	}

	@Override
	public int getMaxFallHeight() {
		return 400;
	}

	@Override
	protected void mountTo(PlayerEntity player) {
		this.setEatingHaystack(false);
		this.setRearing(false);
		if (!this.world.isRemote) {
			player.rotationYaw = this.rotationYaw - 0.2F;
			player.rotationPitch = this.rotationPitch;
			player.startRiding(this);
		}
		HorseSpeed oldSpeed = this.currentSpeed;
		this.currentSpeed = HorseSpeed.TROT;
		this.updateSelectedSpeed(oldSpeed);
	}


	private void setGallopCooldown() {
		int gallopTime = this.dataManager.get(GALLOP_TIMER);
		int cooldown = gallopTime * 5;
		this.dataManager.set(GALLOP_COOLDOWN_TIMER, cooldown);
		this.dataManager.set(GALLOP_ON_COOLDOWN, true);
		this.dataManager.set(GALLOP_TIMER, 0);
	}

	private void resetGallopCooldown() {
		this.dataManager.set(GALLOP_COOLDOWN_TIMER, 0);
		this.dataManager.set(GALLOP_ON_COOLDOWN, false);
		this.dataManager.set(GALLOP_TIMER, 0);
	}



	public boolean isHorseSaddled() {
		return this.getHorseWatchableBoolean(4);
	}

	@Override
	public boolean hasAdventureSaddle() {
		return this.hasSaddle().getItem() instanceof AdventureSaddleItem;
	}

	@Override
	public boolean hasBlanket() {
		return this.horseChest.getStackInSlot(1).getItem() instanceof BlanketItem;
	}

	@Override
	public boolean hasBreastCollar() {
		return this.horseChest.getStackInSlot(3).getItem() instanceof BreastCollarItem;
	}

	@Override
	public boolean hasHalter() {
		if (ConfigHolder.SERVER.halterDependency.get()) {
			return this.horseChest.getStackInSlot(0).getItem() instanceof HalterItem;
		} else {
			return true;
		}

	}

	@Override
	public boolean hasGirthStrap() {
		return this.horseChest.getStackInSlot(5).getItem() instanceof GirthStrapItem;
	}

	@Override
	public boolean hasLegWraps() {
		return this.horseChest.getStackInSlot(4).getItem() instanceof LegWrapsItem;
	}

	public ItemStack hasSaddle() {
		return this.horseChest.getStackInSlot(2);
	};

	@Override
	protected void initHorseChest() {
		Inventory inventory = this.horseChest;
		this.horseChest = new Inventory(this.getInventorySize());
		if (inventory != null) {
			inventory.removeListener(this);
			int i = Math.min(inventory.getSizeInventory(), this.horseChest.getSizeInventory());

			for(int j = 0; j < i; ++j) {
				ItemStack itemstack = inventory.getStackInSlot(j);
				if (!itemstack.isEmpty()) {
					this.horseChest.setInventorySlotContents(j, itemstack.copy());
				}
			}
		}

		this.horseChest.addListener(this::onHorseInventoryChanged);
		this.func_230275_fc_();
		this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.horseChest));
	}

	public Inventory getHorseInventory() {
		return this.horseChest;
	}

	protected void initSaddlebagInventory() {
		Inventory inventory = this.saddlebagInventory;
		this.saddlebagInventory = new Inventory(27);
		if (inventory != null) {
			inventory.removeListener(this);
			int i = Math.min(inventory.getSizeInventory(), this.saddlebagInventory.getSizeInventory());

			for(int j = 0; j < i; ++j) {
				ItemStack itemstack = inventory.getStackInSlot(j);
				if (!itemstack.isEmpty()) {
					this.saddlebagInventory.setInventorySlotContents(j, itemstack.copy());
				}
			}
		}

		this.saddlebagInventory.addListener(this::onInventoryChanged);
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
			int i = Math.min(inventory.getSizeInventory(), this.bedrollInventory.getSizeInventory());

			for(int j = 0; j < i; ++j) {
				ItemStack itemstack = inventory.getStackInSlot(j);
				if (!itemstack.isEmpty()) {
					this.bedrollInventory.setInventorySlotContents(j, itemstack.copy());
				}
			}
		}

		this.bedrollInventory.addListener(this::onInventoryChanged);
		this.bedrollItemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.bedrollInventory));
	}

	public Inventory getBedrollInventory() {
		return this.bedrollInventory;
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
//		compound.putInt("Variant", this.getHorseVariant());
		if (!this.horseChest.getStackInSlot(0).isEmpty()) {
			compound.put("BridleItem", this.horseChest.getStackInSlot(0).write(new CompoundNBT()));
		}
		if (!this.horseChest.getStackInSlot(1).isEmpty()) {
			compound.put("BlanketItem", this.horseChest.getStackInSlot(1).write(new CompoundNBT()));
		}
		if (!this.horseChest.getStackInSlot(2).isEmpty()) {
			compound.put("SaddleItem", this.horseChest.getStackInSlot(2).write(new CompoundNBT()));
		}
		if (!this.horseChest.getStackInSlot(3).isEmpty()) {
			compound.put("BreastCollarItem", this.horseChest.getStackInSlot(3).write(new CompoundNBT()));
		}
		if (!this.horseChest.getStackInSlot(4).isEmpty()) {
			compound.put("LegWrapsItem", this.horseChest.getStackInSlot(4).write(new CompoundNBT()));
		}
		if (!this.horseChest.getStackInSlot(5).isEmpty()) {
			compound.put("GirthStrapItem", this.horseChest.getStackInSlot(5).write(new CompoundNBT()));
		}
		if (!this.horseChest.getStackInSlot(6).isEmpty()) {
			compound.put("SWEMArmorItem", this.horseChest.getStackInSlot(6).write(new CompoundNBT()));
		}
		if (!this.horseChest.getStackInSlot(7).isEmpty()) {
			compound.put("SaddlebagItem", this.horseChest.getStackInSlot(7).write(new CompoundNBT()));
		}

		this.writeSaddlebagInventory(compound);
		this.writeBedrollInventory(compound);

		compound.putBoolean("whistleBound", this.getWhistleBound());

		this.progressionManager.write(compound);

		this.needs.write(compound);

		compound.putBoolean("flying", this.isFlying());

		compound.putInt("HorseVariant", this.getHorseVariant());
	}

	public ItemStack func_213803_dV() {
		return this.getItemStackFromSlot(EquipmentSlotType.CHEST);
	}

	private void func_213805_k(ItemStack p_213805_1_) {
		this.setItemStackToSlot(EquipmentSlotType.CHEST, p_213805_1_);
		this.setDropChance(EquipmentSlotType.CHEST, 0.0F);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
//		this.func_234242_w_(compound.getInt("Variant"));
		if (compound.contains("BridleItem", 10)) {
			ItemStack itemstack = ItemStack.read(compound.getCompound("BridleItem"));
			if (!itemstack.isEmpty() && this.isHalter(itemstack)) {
				this.horseChest.setInventorySlotContents(0, itemstack);
			}
		}
		if (compound.contains("BlanketItem", 10)) {
			ItemStack itemstack = ItemStack.read(compound.getCompound("BlanketItem"));
			if (!itemstack.isEmpty() && this.isBlanket(itemstack)) {
				this.horseChest.setInventorySlotContents(1, itemstack);
			}
		}
		if (compound.contains("SaddleItem", 10)) {
			ItemStack itemstack = ItemStack.read(compound.getCompound("SaddleItem"));
			if (!itemstack.isEmpty() && this.isSaddle(itemstack)) {
				this.horseChest.setInventorySlotContents(2, itemstack);
			}
		}
		if (compound.contains("BreastCollarItem", 10)) {
			ItemStack itemstack = ItemStack.read(compound.getCompound("BreastCollarItem"));
			if (!itemstack.isEmpty() && this.isBreastCollar(itemstack)) {
				this.horseChest.setInventorySlotContents(3, itemstack);
			}
		}
		if (compound.contains("LegWrapsItem", 10)) {
			ItemStack itemstack = ItemStack.read(compound.getCompound("LegWrapsItem"));
			if (!itemstack.isEmpty() && this.isLegWraps(itemstack)) {
				this.horseChest.setInventorySlotContents(4, itemstack);
			}
		}
		if (compound.contains("GirthStrapItem", 10)) {
			ItemStack itemstack = ItemStack.read(compound.getCompound("GirthStrapItem"));
			if (!itemstack.isEmpty() && this.isGirthStrap(itemstack)) {
				this.horseChest.setInventorySlotContents(5, itemstack);
			}
		}
		if (compound.contains("SWEMArmorItem", 10)) {
			ItemStack itemstack = ItemStack.read(compound.getCompound("SWEMArmorItem"));
			if (!itemstack.isEmpty() && this.isSWEMArmor(itemstack)) {
				this.horseChest.setInventorySlotContents(6, itemstack);
			}
		}
		if (compound.contains("SaddlebagItem", 10)) {
			ItemStack itemstack = ItemStack.read(compound.getCompound("SaddlebagItem"));
			if (!itemstack.isEmpty() && this.isSaddlebag(itemstack)) {
				this.horseChest.setInventorySlotContents(7, itemstack);
			}
		}

		this.readSaddlebagInventory(compound);
		this.readBedrollInventory(compound);

		this.setWhistleBound(compound.getBoolean("whistleBound"));

		this.progressionManager.read(compound);

		this.needs.read(compound);

		this.func_230275_fc_();

		//this.setFlying(compound.getBoolean("flying"));

		this.setHorseVariant(compound.getInt("HorseVariant"));
	}

	private void writeSaddlebagInventory(CompoundNBT compound) {

		if (!this.saddlebagInventory.isEmpty()) {
			CompoundNBT saddlebag = new CompoundNBT();
			for (int i = 0; i < this.saddlebagInventory.getSizeInventory(); i++) {
				saddlebag.put(Integer.toString(i), this.saddlebagInventory.getStackInSlot(i).write(new CompoundNBT()));
			}
			compound.put("saddlebag", saddlebag);
		}

	}

	private void readSaddlebagInventory(CompoundNBT compound) {
		if (compound.contains("saddlebag")) {
			CompoundNBT saddlebag = compound.getCompound("saddlebag");

			for (int i = 0; i < this.saddlebagInventory.getSizeInventory(); i++) {
				if (saddlebag.contains(Integer.toString(i))) {
					CompoundNBT stackNBT = (CompoundNBT) saddlebag.get(Integer.toString(i));
					ItemStack readStack = ItemStack.read(stackNBT);
					this.saddlebagInventory.setInventorySlotContents(i, readStack);
				}
			}
		}
	}

	private void writeBedrollInventory(CompoundNBT compound) {

		if (!this.bedrollInventory.isEmpty()) {
			CompoundNBT bedroll = new CompoundNBT();
			for (int i = 0; i < this.bedrollInventory.getSizeInventory(); i++) {
				bedroll.put(Integer.toString(i), this.bedrollInventory.getStackInSlot(i).write(new CompoundNBT()));
			}
			compound.put("bedroll", bedroll);
		}

	}

	private void readBedrollInventory(CompoundNBT compound) {
		if (compound.contains("bedroll")) {
			CompoundNBT bedroll = compound.getCompound("bedroll");

			for (int i = 0; i < this.bedrollInventory.getSizeInventory(); i++) {
				if (bedroll.contains(Integer.toString(i))) {
					CompoundNBT stackNBT = (CompoundNBT) bedroll.get(Integer.toString(i));
					ItemStack readStack = ItemStack.read(stackNBT);
					this.bedrollInventory.setInventorySlotContents(i, readStack);
				}
			}
		}
	}

	private void setHorseVariant(int id) {
		this.dataManager.set(HORSE_VARIANT, id);
	}


	private void func_234238_a_(CoatColors p_234238_1_, CoatTypes p_234238_2_) {
//		this.func_234242_w_(p_234238_1_.getId() & 255 | p_234238_2_.getId() << 8 & '\uff00');
	}

	public SWEMCoatColors getCoatColor() {
		return SWEMCoatColors.getById(this.getHorseVariant() & 255);
	}

	private int getHorseVariant() {
		return this.dataManager.get(HORSE_VARIANT);
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
	public void setHorseTamed(boolean tamed) {
		super.setHorseTamed(tamed);
		this.progressionManager.getAffinityLeveling().addXP(100.0f);
	}

	@Override
	protected void func_230275_fc_() {
		if (!this.world.isRemote) {
			this.setHorseWatchableBoolean(4, !this.horseChest.getStackInSlot(2).isEmpty());
		}
	}

	private void func_213804_l(ItemStack p_213804_1_) {
		this.func_213805_k(p_213804_1_);
		if (!this.world.isRemote) {
			this.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER_UUID);
			if (this.isSWEMArmor(p_213804_1_)) {
				int i = ((HorseArmorItem)p_213804_1_.getItem()).getArmorValue();
				if (i != 0) {
					this.getAttribute(Attributes.ARMOR).applyNonPersistentModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", (double)i, AttributeModifier.Operation.ADDITION));
				}
			}
		}

	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick() {
		if (!this.world.isRemote) {
			if (this.ticksExisted % 5 == 0 && !isFlying()) {

				if (this.canBeSteered() && this.isBeingRidden() && this.currentSpeed != HorseSpeed.WALK && this.currentSpeed != HorseSpeed.TROT) {
					int x = this.getPosition().getX();
					int z = this.getPosition().getZ();
					if (x != this.currentPos.getX() || z != this.currentPos.getZ()) {
						x = Math.abs(x - this.currentPos.getX());
						z = Math.abs(z - this.currentPos.getZ());
						int dist = ((int)Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)));
						if (dist > 0 && dist < 25) {
							boolean speedLevelUp = false;
							if (this.currentSpeed == HorseSpeed.CANTER) {
								speedLevelUp = this.progressionManager.getSpeedLeveling().addXP(dist);
							} else if (this.currentSpeed == HorseSpeed.GALLOP) {
								speedLevelUp = this.progressionManager.getSpeedLeveling().addXP(dist * 2);
							}
							if (speedLevelUp) {
								this.levelUpSpeed();
							}
							// Affinity leveling, is not affected by speed. so no matter the speed, just add 1xp per block.
							this.progressionManager.getAffinityLeveling().addXP(dist);
						}


					}
				}

				// Kick off rider, if no girth strap is equipped.
				if (this.isSWEMSaddled() && (!this.hasGirthStrap() || !ConfigHolder.SERVER.riderNeedsGirthStrap.get()) && this.isBeingRidden()) {
					if (this.ticksExisted % 20 == 0) {
						int rand = this.getRNG().nextInt(5);
						if (rand == 0) {
							Entity rider = this.getPassengers().get(0);
							rider.stopRiding();
							ItemStack saddle = this.hasSaddle();
							this.horseChest.setInventorySlotContents(2, ItemStack.EMPTY);
							this.setSWEMSaddled();
							this.world.addEntity(new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), saddle));
						}
					}

				}
				this.currentPos = this.getPosition();
			}

			if (!this.horseChest.getStackInSlot(6).isEmpty()) {
				this.checkArmorPiece(((SWEMHorseArmorItem)this.horseChest.getStackInSlot(6).getItem()));
			}


		}
		super.tick();
		if (this.isInWater() && !this.eyesInWater && !this.isBeingRidden()) {
			if (this.getMotion().getY() > 0) {
				this.setMotion(this.getMotion().getX(), -.15, this.getMotion().getZ()); // Set the motion on y with a negative force, because the horse is floating to the top, pull it down, until eyesInWater returns true.
			}
		}

		if (!this.world.isRemote) {
			int airHeight = this.checkHeightInAir();
			if (this.isLaunching && airHeight < 6) {
				this.setMotion(this.getMotion().add(0.0d, 0.15d, 0.0d));
			} else if (this.isLaunching) {
				this.setMotion(Vector3d.ZERO);
				this.isLaunching = false;
			} else if (this.isLanding && airHeight > 1) {
				Vector3d lookVec = this.getLookVec();
				Vector3d downwards = new Vector3d(lookVec.x, -0.2D, lookVec.z);
				this.setMotion(downwards);
			}

			if (this.isLanding && airHeight <= 1) {
				this.isLanding = false;
				this.setNoGravity(false);
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
			BlockState blockstate = Blocks.FROSTED_ICE.getDefaultState();
			float f = (float)Math.min(16, 3);
			BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

			BlockPos pos = this.getPosition();

			for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add((double)(-f), -1.0D, (double)(-f)), pos.add((double)f, -1.0D, (double)f))) {
				if (blockpos.withinDistance(this.getPositionVec(), (double)f)) {
					blockpos$mutable.setPos(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
					BlockState blockstate1 = world.getBlockState(blockpos$mutable);
					if (blockstate1.isAir(world, blockpos$mutable)) {
						BlockState blockstate2 = world.getBlockState(blockpos);
						boolean isFull = blockstate2.getBlock() == Blocks.WATER && blockstate2.get(FlowingFluidBlock.LEVEL) == 0; //TODO: Forge, modded waters?
						if (blockstate2.getMaterial() == Material.WATER && isFull && blockstate.isValidPosition(world, blockpos) && world.placedBlockCollides(blockstate, blockpos, ISelectionContext.dummy()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(this, net.minecraftforge.common.util.BlockSnapshot.create(world.getDimensionKey(), world, blockpos), net.minecraft.util.Direction.UP)) {
							world.setBlockState(blockpos, blockstate);
							world.getPendingBlockTicks().scheduleTick(blockpos, Blocks.FROSTED_ICE, 60);
						}
					}
				}
			}

		}
	}

	private void tickDiamondArmor() {
		if (this.isOnGround()) {
			BlockState blockstate = SWEMBlocks.TEARING_MAGMA.get().getDefaultState();
			float f = (float)Math.min(16, 3);
			BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

			BlockPos pos = this.getPosition();

			for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add((double)(-f), -1.0D, (double)(-f)), pos.add((double)f, -1.0D, (double)f))) {
				if (blockpos.withinDistance(this.getPositionVec(), (double)f)) {
					blockpos$mutable.setPos(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
					BlockState blockstate1 = world.getBlockState(blockpos$mutable);
					if (blockstate1.isAir(world, blockpos$mutable)) {
						BlockState blockstate2 = world.getBlockState(blockpos);
						boolean isFull = blockstate2.getBlock() == Blocks.LAVA && blockstate2.get(FlowingFluidBlock.LEVEL) == 0; //TODO: Forge, modded waters?
						if (blockstate2.getMaterial() == Material.LAVA && isFull && blockstate.isValidPosition(world, blockpos) && world.placedBlockCollides(blockstate, blockpos, ISelectionContext.dummy()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(this, net.minecraftforge.common.util.BlockSnapshot.create(world.getDimensionKey(), world, blockpos), net.minecraft.util.Direction.UP)) {
							world.setBlockState(blockpos, blockstate);
							world.getPendingBlockTicks().scheduleTick(blockpos, SWEMBlocks.TEARING_MAGMA.get(), 60);
						}
					}
				}
			}

		} else if (this.isInLava() && !this.eyesInWater && !this.isBeingRidden()) {
			if (this.getMotion().getY() > 0) {
				this.setMotion(this.getMotion().getX(), -.15, this.getMotion().getZ()); // Set the motion on y with a negative force, because the horse is floating to the top, pull it down, until eyesInWater returns true.
			}
		}


	}

	private void tickAmethystArmor() {

	}


	@Override
	public void travel(Vector3d travelVector) {
		if (this.isFlying()) {
			this.onGround = true;
		}
		if (this.isBeingRidden() && this.canBeSteered() && this.isHorseSaddled()) {
			LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();

			this.rotationYaw = livingentity.rotationYaw;
			this.prevRotationYaw = this.rotationYaw;
			this.rotationPitch = livingentity.rotationPitch * 0.5F;
			this.setRotation(this.rotationYaw, this.rotationPitch);
			this.renderYawOffset = this.rotationYaw;
			this.rotationYawHead = this.renderYawOffset;
			float f = livingentity.moveStrafing * 0.5F;
			float f1 = livingentity.moveForward;
			if (f1 <= 0.0F) {
				f1 *= 0.25F;
				this.gallopTime = 0;
			}

			if (this.onGround && this.jumpPower == 0.0F && this.isRearing() && !this.allowStandSliding) {
				f = 0.0F;
				f1 = 0.0F;
			}

			 // Check if RNG is higher roll, than disobeying debuff, if so, then do the jump.
			if (this.jumpPower > 0.0F && !this.isHorseJumping() && this.onGround && !this.isFlying()) {
				double d0 = this.getHorseJumpStrength() * (double) this.jumpPower * (double) this.getJumpFactor();
				double d1;
				if (this.isPotionActive(Effects.JUMP_BOOST)) {
					d1 = d0 + (double) ((float) (this.getActivePotionEffect(Effects.JUMP_BOOST).getAmplifier() + 1) * 0.1F);
				} else {
					d1 = d0;
				}


				this.setHorseJumping(true);
				SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(7, this.getEntityId()));
				//if (this.getDisobedienceFactor() > this.progressionManager.getAffinityLeveling().getDebuff()) {
				Vector3d vector3d = this.getMotion();
				this.setMotion(vector3d.x, d1, vector3d.z);



				// Check jumpheight, and add XP accordingly.
				float jumpHeight = (float) (-0.1817584952 * ((float)Math.pow(d1, 3.0F)) + 3.689713992 * ((float)Math.pow(d1, 2.0F)) + 2.128599134 * d1 - 0.343930367);
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

				SWEMPacketHandler.INSTANCE.sendToServer(new AddJumpXPMessage(xpToAdd, this.getEntityId()));


				this.isAirBorne = true;
				net.minecraftforge.common.ForgeHooks.onLivingJump(this);
				if (f1 > 0.0F) {
					float f2 = MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180F));
					float f3 = MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180F));
					this.setMotion(this.getMotion().add((double) (-0.4F * f2 * this.jumpPower), 0.0D, (double) (0.4F * f3 * this.jumpPower)));
				}



				this.jumpPower = 0.0F;
				//} else {
				//	this.makeMad();
				//}
			}


			this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;
			if (this.canPassengerSteer() && !isFlying() && !isLanding) {
				this.setAIMoveSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
				super.travel(new Vector3d((double) f, travelVector.y, (double) f1));
			} else if ((livingentity instanceof PlayerEntity) && !isFlying()) {
				this.setMotion(Vector3d.ZERO);
			}

			if (this.onGround) {
				this.jumpPower = 0.0F;
				this.setHorseJumping(false);
			}

			this.func_233629_a_(this, false);


			boolean flag = this.world.getBlockState(this.getPosition().add(this.getHorizontalFacing().getDirectionVec())).isSolid();

			// Handles the swimming. Travel is only called when player is riding the entity.
			if (this.eyesInWater && !flag && this.getMotion().getY() < 0) { // Check if the eyes is in water level, and we don't have a solid block the way we are facing. If not, then apply a inverse force, to float the horse.
				this.setMotion(this.getMotion().mul(1, -1.9, 1));
			}
		} else {
			super.travel(travelVector);
		}

	}

	private float getDisobedienceFactor() {
		return this.getRNG().nextFloat();
	}

	@Override
	public boolean isInvulnerableTo(DamageSource source) {
		if (source == DamageSource.DROWN) return true;
		if (source == DamageSource.FALL) return true;
		if (source == DamageSource.IN_FIRE || source == DamageSource.ON_FIRE || source == DamageSource.LAVA) {
			ItemStack stack = this.getSWEMArmor();
			if (!stack.isEmpty() && ((SWEMHorseArmorItem) stack.getItem()).tier.getId() >= 3) {
				return true;
			}
		}

		return super.isInvulnerableTo(source);
	}


	public void levelUpJump() {
		double currentSpeed = this.getAttribute(Attributes.HORSE_JUMP_STRENGTH).getValue();
		double newSpeed = this.getAlteredJumpStrength();
		this.getAttribute(Attributes.HORSE_JUMP_STRENGTH).applyPersistentModifier(new AttributeModifier(this.progressionManager.getJumpLeveling().getLevelName(), newSpeed - currentSpeed, AttributeModifier.Operation.ADDITION));
	}

	public void levelUpSpeed() {
		double currentSpeed = this.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
		double newSpeed = this.getAlteredMovementSpeed();
		this.getAttribute(Attributes.MOVEMENT_SPEED).applyPersistentModifier(new AttributeModifier(this.progressionManager.getSpeedLeveling().getLevelName(), newSpeed - currentSpeed, AttributeModifier.Operation.ADDITION));
	}

	/**
	 * Called by InventoryBasic.onInventoryChanged() on a array that is never filled.
	 */
	public void onHorseInventoryChanged(IInventory invBasic) {
		this.setSWEMSaddled();
		ItemStack itemstack = this.func_213803_dV();
		super.onInventoryChanged(invBasic);

		ItemStack itemstack1 = this.func_213803_dV();
		if (this.ticksExisted > 20 && this.isSWEMArmor(itemstack1) && itemstack != itemstack1) {
			this.playSound(SoundEvents.ENTITY_HORSE_ARMOR, 0.5F, 1.0F);
		}
		if (this.world.isRemote) return;
		for (int i = 0; i < invBasic.getSizeInventory(); i++) {
			SWEMPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new UpdateHorseInventoryMessage(getEntityId(), i, invBasic.getStackInSlot(i)));
		}
	}

	public void onInventoryChanged(IInventory invBasic) {
		this.setSWEMSaddled();
		ItemStack itemstack = this.func_213803_dV();
		super.onInventoryChanged(invBasic);

		ItemStack itemstack1 = this.func_213803_dV();
		if (this.ticksExisted > 20 && this.isSWEMArmor(itemstack1) && itemstack != itemstack1) {
			this.playSound(SoundEvents.ENTITY_HORSE_ARMOR, 0.5F, 1.0F);
		}
	}

	protected boolean isSWEMSaddled() {
		return this.horseChest.getStackInSlot(2).getItem() instanceof HorseSaddleItem;
	}



	protected void setSWEMSaddled() {
		if (this.world.isRemote) {
			this.setHorseWatchableBoolean(4, !this.horseChest.getStackInSlot(2).isEmpty());
		}
	}

	protected void playGallopSound(SoundType p_190680_1_) {
		super.playGallopSound(p_190680_1_);
		if (this.rand.nextInt(10) == 0) {
			this.playSound(SoundEvents.ENTITY_HORSE_BREATHE, p_190680_1_.getVolume() * 0.6F, p_190680_1_.getPitch());
		}

		ItemStack stack = this.horseChest.getStackInSlot(1);
		if (isSWEMArmor(stack)) stack.onHorseArmorTick(world, this);
	}

	// Get nom-nom sound
	@Nullable
	protected SoundEvent func_230274_fe_() {
		return SoundEvents.ENTITY_HORSE_EAT;
	}



	protected SoundEvent getAngrySound() {
		super.getAngrySound();
		return SoundEvents.ENTITY_HORSE_ANGRY;
	}

	@Override
	public void openGUI(PlayerEntity playerEntity) {
		if (!this.world.isRemote && (!this.isBeingRidden() || this.isPassenger(playerEntity)) && this.isTame()) {
			ITextComponent horseDisplayName = new StringTextComponent(SWEMUtil.checkTextOverflow(this.getDisplayName().getString(), 18));
			INamedContainerProvider provider = new INamedContainerProvider() {
				@Override
				public ITextComponent getDisplayName() {
					return horseDisplayName;
				}

				@Nullable
				@Override
				public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
					return new SWEMHorseInventoryContainer(p_createMenu_1_, p_createMenu_2_, getEntityId());
				}
			};
			NetworkHooks.openGui((ServerPlayerEntity) playerEntity, provider, buffer ->
					buffer
						.writeInt(getEntityId())
						.writeInt(getEntityId())
			);

		}
	}



	// Item interaction with horse.
	@Override
	public ActionResultType getEntityInteractionResult(PlayerEntity p_230254_1_, Hand p_230254_2_) {
		ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
		if (!this.isChild()) {
			if (this.isTame() && p_230254_1_.isSecondaryUseActive()) {
				this.openGUI(p_230254_1_);
				return ActionResultType.func_233537_a_(this.world.isRemote);
			}

			if (this.isBeingRidden()) {
				return super.getEntityInteractionResult(p_230254_1_, p_230254_2_);
			}
		}

		if (!itemstack.isEmpty() && itemstack.getItem() != Items.SADDLE) {
			if (itemstack.getItem() == Items.LAPIS_LAZULI) {
				if (ConfigHolder.SERVER.lapisCycleCoats.get()) {
					this.setHorseVariant((this.getHorseVariant() + 1) % (SWEMCoatColors.values().length - 2));
					ItemStack heldItemCopy = itemstack.copy();
					if (!p_230254_1_.abilities.isCreativeMode)
						heldItemCopy.shrink(1);
					p_230254_1_.setHeldItem(p_230254_2_, heldItemCopy);
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
				SWEMPacketHandler.INSTANCE.sendToServer(new HorseHungerChange(this.getEntityId(), itemstack));
				return ActionResultType.func_233537_a_(this.world.isRemote);
			}

//			if (this.isBreedingItem(itemstack)) {
//				return this.func_241395_b_(p_230254_1_, itemstack);
//			}



			if (itemstack.getItem() == Items.WATER_BUCKET) {
				SWEMPacketHandler.INSTANCE.sendToServer(new HorseStateChange(0, this.getEntityId()));
				p_230254_1_.setHeldItem(p_230254_2_, ((BucketItem) itemstack.getItem()).emptyBucket(itemstack, p_230254_1_));
				return ActionResultType.func_233537_a_(this.world.isRemote);
			}

			ActionResultType actionresulttype = itemstack.interactWithEntity(p_230254_1_, this, p_230254_2_);
			if (actionresulttype.isSuccessOrConsume()) {
				if (itemstack.getItem() instanceof HorseSaddleItem && actionresulttype.isSuccessOrConsume()) {
					this.setSWEMSaddled();
				}
				if (itemstack.getItem() instanceof WhistleItem && actionresulttype.isSuccessOrConsume()) {
				}
				return actionresulttype;
			}

			if (!this.isTame()) {
				this.makeMad();
				return ActionResultType.func_233537_a_(this.world.isRemote);
			}

			boolean flag = !this.isChild() && !this.isSWEMSaddled() && (itemstack.getItem() instanceof HorseSaddleItem);
			boolean flag1 = !this.isChild() && this.hasHalter() && (itemstack.getItem() instanceof GirthStrapItem);
			boolean flag2 = !this.isChild() && this.hasHalter() && (itemstack.getItem() instanceof BlanketItem);
			boolean flag3 = !this.isChild() && this.hasHalter() && (itemstack.getItem() instanceof LegWrapsItem);
			boolean flag4 = !this.isChild() && this.hasHalter() && (itemstack.getItem() instanceof BreastCollarItem);
			if (this.isSWEMArmor(itemstack) || flag) {
				this.setSWEMSaddled();
				this.openGUI(p_230254_1_);
				return ActionResultType.func_233537_a_(this.world.isRemote);
			}
			if (flag1 || flag2 || flag3 || flag4) {
				this.openGUI(p_230254_1_);
				return ActionResultType.func_233537_a_(this.world.isRemote);
			}

		}

		if (this.isChild()) {
			return super.getEntityInteractionResult(p_230254_1_, p_230254_2_);
		} else {
			this.mountTo(p_230254_1_);
			return ActionResultType.func_233537_a_(this.world.isRemote);
		}
	}

	@Override
	public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
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

		if (player.getEntityWorld().isRemote) return ActionResultType.CONSUME;

		NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
			@Override
			public ITextComponent getDisplayName() {
				return new TranslationTextComponent("container.swem.saddlebag");
			}

			@Nullable
			@Override
			public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
				return new SaddlebagContainer(p_createMenu_1_, p_createMenu_2_, getEntityId());
			}
		}, buffer ->   {
			buffer.writeInt(getEntityId());
			buffer.writeInt(getEntityId());
		});

		return ActionResultType.CONSUME;
	}

	private boolean checkForBackHit(Vector3d vec) {
		Direction facing = this.getHorizontalFacing();

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
	public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		AbstractHorseEntity abstracthorseentity;
		HorseEntity horseentity = (HorseEntity)p_241840_2_;
		abstracthorseentity = EntityType.HORSE.create(p_241840_1_);
		int i = this.rand.nextInt(9);
		CoatColors coatcolors;
		if (i < 4) {
//				coatcolors = this.func_234239_eK_();
		} else if (i < 8) {
			coatcolors = horseentity.func_234239_eK_();
		} else {
			coatcolors = Util.getRandomObject(CoatColors.values(), this.rand);
		}

		int j = this.rand.nextInt(5);
		CoatTypes coattypes;
		if (j < 2) {
//				coattypes = this.func_234240_eM_();
		} else if (j < 4) {
			coattypes = horseentity.func_234240_eM_();
		} else {
			coattypes = Util.getRandomObject(CoatTypes.values(), this.rand);
		}

//			((SWEMHorseEntityBase)abstracthorseentity).func_234238_a_(coatcolors, coattypes);

		this.setOffspringAttributes(p_241840_2_, abstracthorseentity);
		return abstracthorseentity;
	}

	public boolean func_230276_fq_() {
		return true;
	}


	@Nullable
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		CoatColors coatcolors;
		if (spawnDataIn instanceof HorseEntity.HorseData) {
			coatcolors = ((HorseEntity.HorseData)spawnDataIn).variant;
		} else {
			coatcolors = Util.getRandomObject(CoatColors.values(), this.rand);
			spawnDataIn = new HorseEntity.HorseData(coatcolors);
		}

		this.func_234238_a_(coatcolors, Util.getRandomObject(CoatTypes.values(), this.rand));
		return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	/**
	 * Called by the server when constructing the spawn packet.
	 * Data should be added to the provided stream.
	 *
	 * @param buffer The packet data stream
	 */
	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeItemStack(this.horseChest.getStackInSlot(0));
		buffer.writeItemStack(this.horseChest.getStackInSlot(1));
		buffer.writeItemStack(this.horseChest.getStackInSlot(2));
		buffer.writeItemStack(this.horseChest.getStackInSlot(3));
		buffer.writeItemStack(this.horseChest.getStackInSlot(4));
		buffer.writeItemStack(this.horseChest.getStackInSlot(5));
		buffer.writeItemStack(this.horseChest.getStackInSlot(6));
		buffer.writeItemStack(this.horseChest.getStackInSlot(7));
	}

	/**
	 * Called by the client when it receives a Entity spawn packet.
	 * Data should be read out of the stream in the same way as it was written.
	 *
	 * @param additionalData The packet data stream
	 */
	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		this.horseChest.setInventorySlotContents(0, additionalData.readItemStack());
		this.horseChest.setInventorySlotContents(1, additionalData.readItemStack());
		this.horseChest.setInventorySlotContents(2, additionalData.readItemStack());
		this.horseChest.setInventorySlotContents(3, additionalData.readItemStack());
		this.horseChest.setInventorySlotContents(4, additionalData.readItemStack());
		this.horseChest.setInventorySlotContents(5, additionalData.readItemStack());
		this.horseChest.setInventorySlotContents(6, additionalData.readItemStack());
		this.horseChest.setInventorySlotContents(7, additionalData.readItemStack());
	}

	@Override
	public IPacket<?> createSpawnPacket() {
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
			if (this.dataManager.get(GALLOP_ON_COOLDOWN)) {
				ArrayList<String> args = new ArrayList<>();
				args.add(String.valueOf(Math.round(( this.dataManager.get(GALLOP_COOLDOWN_TIMER) - this.dataManager.get(GALLOP_TIMER) ) / 20)));
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
		this.getAttribute(Attributes.MOVEMENT_SPEED).applyNonPersistentModifier(this.currentSpeed.getModifier());
		this.dataManager.set(SPEED_LEVEL, this.currentSpeed.speedLevel);
	}

	public boolean canFly() {
		// && ((SWEMHorseArmorItem) this.getSWEMArmor().getItem()).tier.getId() == 4
		if (!this.hasSaddle().isEmpty()) {
			return true;
		} else {
			return false;
		}
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
		return this.horseChest.getStackInSlot(0);
	}

	public boolean isBreastCollar(ItemStack stack) {
		return stack.getItem() instanceof BreastCollarItem;
	}

	public ItemStack getBreastCollar() {
		return this.horseChest.getStackInSlot(3);
	}

	public boolean isLegWraps(ItemStack stack) {
		return stack.getItem() instanceof LegWrapsItem;
	}

	public ItemStack getLegWraps() {
		return this.horseChest.getStackInSlot(4);
	}

	public boolean isGirthStrap(ItemStack stack) {
		return stack.getItem() instanceof GirthStrapItem;
	}

	public ItemStack getGirthStrap() {
		return this.horseChest.getStackInSlot(5);
	}

	private boolean hasBridle() {
		return this.horseChest.getStackInSlot(0).getItem() instanceof BridleItem;
	}

	public boolean canEquipSaddle() {
		return this.hasBlanket() || !ConfigHolder.SERVER.blanketBeforeSaddle.get();
	}

	public boolean canEquipGirthStrap() {
		return this.isSWEMSaddled() || !ConfigHolder.SERVER.saddleBeforeGirthStrap.get();
	}

	@Override
	public boolean canBeSteered() {
		if (this.hasBridle() || !ConfigHolder.SERVER.needBridleToSteer.get()) {
			if (this.isFlying()) {
				return false;
			}
			return super.canBeSteered();
		} else {
			return false;
		}
	}

	public boolean isSWEMArmor(ItemStack stack) {
		return stack.getItem() instanceof SWEMHorseArmorItem;
	}

	public ItemStack getSWEMArmor() {
		return this.horseChest.getStackInSlot(6);
	}

	public boolean isSaddlebag(ItemStack stack) {
		return stack.getItem() instanceof SaddlebagItem;
	}

	public ItemStack getSaddlebag() {
		return this.horseChest.getStackInSlot(7);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return super.attackEntityFrom(source, amount);
	}

	public boolean isBlanket(ItemStack stack) {
		return stack.getItem() instanceof BlanketItem;
	}

	public ItemStack getBlanket() {
		return this.horseChest.getStackInSlot(1);
	}

	public boolean isSaddle(ItemStack stack) {
		return stack.getItem() instanceof HorseSaddleItem;
	}

	@Override
	public boolean func_230277_fr_() {
		return super.func_230277_fr_();
	}

	public float getJumpHeight() {
		float jumpStrength = (float) this.getHorseJumpStrength();
		float jumpHeight = (float) (-0.1817584952 * ((float)Math.pow(jumpStrength, 3.0F)) + 3.689713992 * ((float)Math.pow(jumpStrength, 2.0F)) + 2.128599134 * jumpStrength - 0.343930367);
		return jumpHeight;
	}

	public boolean getWhistleBound() {
		return this.dataManager.get(whistleBound);
	}


	public void setWhistleBound(boolean bound) {
		this.dataManager.set(whistleBound, bound);
	}

	public LivingEntity getWhistleCaller() {
		return this.whistleCaller;
	}

	public void setWhistleCaller(LivingEntity player) {
		this.whistleCaller = player;
	}

	public ITextComponent getOwnerDisplayName() {
		UUID PlayerUUID = this.getOwnerUniqueId();
		if (PlayerUUID == null) {
			return new TranslationTextComponent("Not owned.");
		}
		return this.world.getPlayerByUuid(PlayerUUID).getDisplayName();
	}

	public enum HorseSpeed {

		WALK(new AttributeModifier("HORSE_WALK", -0.8d, AttributeModifier.Operation.MULTIPLY_TOTAL), 0),
		TROT(new AttributeModifier("HORSE_TROT", -0.6d, AttributeModifier.Operation.MULTIPLY_TOTAL), 1),
		CANTER(new AttributeModifier("HORSE_CANTER", 0, AttributeModifier.Operation.MULTIPLY_TOTAL), 2),
		GALLOP(new AttributeModifier("HORSE_GALLOP", 0.07115276974015008d, AttributeModifier.Operation.ADDITION), 3);
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


}
