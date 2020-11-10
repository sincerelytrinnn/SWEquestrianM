package com.alaharranhonor.swem.entities;

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.container.SWEMHorseInventoryContainer;
import com.alaharranhonor.swem.entities.goals.FollowWhistleGoal;
import com.alaharranhonor.swem.entities.goals.PoopGoal;
import com.alaharranhonor.swem.entities.progression.ProgressionManager;
import com.alaharranhonor.swem.entities.progression.leveling.AffinityLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.HealthLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.JumpLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.SpeedLeveling;
import com.alaharranhonor.swem.items.*;
import com.alaharranhonor.swem.network.AddJumpXPMessage;
import com.alaharranhonor.swem.network.SWEMPacketHandler;
import com.alaharranhonor.swem.network.UpdateHorseInventoryMessage;
import com.alaharranhonor.swem.util.RegistryHandler;
import com.alaharranhonor.swem.util.initialization.SWEMItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.horse.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
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
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
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
import java.util.Random;
import java.util.UUID;

public class SWEMHorseEntityBase extends AbstractHorseEntity implements ISWEMEquipable, IEntityAdditionalSpawnData {




	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
	//private static final DataParameter<Integer> HORSE_VARIANT = EntityDataManager.createKey(HorseEntity.class, DataSerializers.VARINT);

	public static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(SWEMItems.AMETHYST.get());
	private EatGrassGoal eatGrassGoal;
	private PoopGoal poopGoal;
	private int SWEMHorseGrassTimer;
	private int SWEMHorsePoopTimer;
	private static Random rand = new Random();

	public final ProgressionManager progressionManager;
	private BlockPos currentPos;
	private LazyOptional<InvWrapper> itemHandler;
	public static DataParameter<Boolean> whistleBound = EntityDataManager.createKey(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);

	@Nullable
	private LivingEntity whistleCaller;

	public SWEMHorseEntityBase(EntityType<? extends AbstractHorseEntity> type, World worldIn)
	{
		super(type, worldIn);
		this.currentPos = this.getPosition();
		this.progressionManager = new ProgressionManager(this);

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
		this.goalSelector.addGoal(0, new FollowWhistleGoal(this, 1.0d));
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.2D));
		//this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0d));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, TEMPTATION_ITEMS, false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0D));
		this.goalSelector.addGoal(5, this.eatGrassGoal);
		this.goalSelector.addGoal(5, this.poopGoal);
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
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
		super.updateAITasks();
	}

	@Override
	public void livingTick()
	{

		if (this.world.isRemote)
		{
			this.SWEMHorsePoopTimer = Math.max(0, this.SWEMHorsePoopTimer - 1);
			this.SWEMHorseGrassTimer = Math.max(0, this.SWEMHorseGrassTimer - 1);
		}
		super.livingTick();
	}

	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdates(byte id)
	{
		if (id == 10)
		{
			this.SWEMHorseGrassTimer = 40;
			this.SWEMHorsePoopTimer = 80;
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
		this.dataManager.register(SpeedLeveling.MAX_LEVEL, 5);

		this.dataManager.register(JumpLeveling.LEVEL, 0);
		this.dataManager.register(JumpLeveling.XP, 0.0f);
		this.dataManager.register(JumpLeveling.MAX_LEVEL, 5);

		this.dataManager.register(HealthLeveling.LEVEL, 0);
		this.dataManager.register(HealthLeveling.XP, 0.0f);
		this.dataManager.register(HealthLeveling.MAX_LEVEL, 5);

		this.dataManager.register(AffinityLeveling.LEVEL, 0);
		this.dataManager.register(AffinityLeveling.XP, 0.0f);
		this.dataManager.register(AffinityLeveling.MAX_LEVEL, 12);

		this.dataManager.register(whistleBound, false);


	}

	public boolean func_230264_L__() {
		return this.isAlive() && !this.isChild() && this.isTame();
	}

	public void func_230266_a_(@Nullable SoundCategory p_230266_1_, ItemStack stack) {
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
		if (this.hasSaddle().getItem() instanceof WesternSaddleItem) {
			def += 0.15D;
		}
		return def;
	}

	@Override
	public void positionRider(Entity entity, IMoveCallback callback) {
		if (this.isPassenger(entity)) {
			double d0 = this.getPosY() + this.getMountedYOffset() + entity.getYOffset();
			callback.accept(entity, this.getPosX(), d0, this.getPosZ() - 0.2F);
		}
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
	}



	public boolean isHorseSaddled() {
		return this.getHorseWatchableBoolean(4);
	}

	@Override
	public boolean hasBlanket() {
		return this.horseChest.getStackInSlot(1).getItem() instanceof HorseSaddleItem;
	}

	@Override
	public boolean hasBreastCollar() {
		return this.horseChest.getStackInSlot(3).getItem() instanceof BreastCollarItem;
	}

	@Override
	public boolean hasHalter() {
		return this.horseChest.getStackInSlot(0).getItem() instanceof HalterItem;
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

		this.horseChest.addListener(this);
		this.func_230275_fc_();
		this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.horseChest));
	}

	public Inventory getHorseInventory() {
		return this.horseChest;
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

		compound.putBoolean("whistleBound", this.getWhistleBound());

		this.progressionManager.write(compound);
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

		this.setWhistleBound(compound.getBoolean("whistleBound"));

		this.progressionManager.read(compound);

//		this.leveling.setLevel(compound.getInt("CurrentLevel"));
//		this.leveling.setXP(compound.getFloat("CurrentXP"));
//		this.leveling.setXPRequired(compound.getInt("RequiredXP"));

		this.func_230275_fc_();
	}

//	private void func_234242_w_(int p_234242_1_) {
//		this.dataManager.set(HORSE_VARIANT, p_234242_1_);
//	}
//
//	private int getHorseVariant() {
//		return this.dataManager.get(HORSE_VARIANT);
//	}

	private void func_234238_a_(CoatColors p_234238_1_, CoatTypes p_234238_2_) {
//		this.func_234242_w_(p_234238_1_.getId() & 255 | p_234238_2_.getId() << 8 & '\uff00');
	}

//	public CoatColors func_234239_eK_() {
//		return CoatColors.func_234254_a_(this.getHorseVariant() & 255);
//	}

//	public CoatTypes func_234240_eM_() {
//		return CoatTypes.func_234248_a_((this.getHorseVariant() & '\uff00') >> 8);
//	}


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
		if (this.ticksExisted % 5 == 0) {
			if (this.canBeSteered() && this.isBeingRidden()) { // Check for the current set speed. If it's canter, add the distance, if it's gallop, add the distance * 3) {
				int x = this.getPosition().getX();
				int z = this.getPosition().getZ();
				if (x != this.currentPos.getX() || z != this.currentPos.getZ()) {
					x = Math.abs(x - this.currentPos.getX());
					z = Math.abs(z - this.currentPos.getZ());
					int dist = ((int)Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)));
					if (dist > 0 && dist < 25) {
						boolean speedLevelUp = this.progressionManager.getSpeedLeveling().addXP(dist);
						if (speedLevelUp) {
							this.levelUpSpeed();
						}
						// Affinity leveling, is not affected by speed. so no matter the speed, just add 1xp per block.
						this.progressionManager.getAffinityLeveling().addXP(dist);
					}


				}
				this.currentPos = this.getPosition();
			} else {
				this.currentPos = this.getPosition();
			}
		}

		if (this.ticksExisted % 100 == 0) {
			// TODO: CHECK FOR FOOD AND WATER IN A 5x5 Proximity if Thirsty or Hungry.
		}
		super.tick();
	}

	@Override
	public void travel(Vector3d travelVector) {
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

			if (this.jumpPower > 0.0F && !this.isHorseJumping() && this.onGround) {
				double d0 = this.getHorseJumpStrength() * (double) this.jumpPower * (double) this.getJumpFactor();
				double d1;
				if (this.isPotionActive(Effects.JUMP_BOOST)) {
					d1 = d0 + (double) ((float) (this.getActivePotionEffect(Effects.JUMP_BOOST).getAmplifier() + 1) * 0.1F);
				} else {
					d1 = d0;
				}

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


				this.setHorseJumping(true);
				this.isAirBorne = true;
				net.minecraftforge.common.ForgeHooks.onLivingJump(this);
				if (f1 > 0.0F) {
					float f2 = MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180F));
					float f3 = MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180F));
					this.setMotion(this.getMotion().add((double) (-0.4F * f2 * this.jumpPower), 0.0D, (double) (0.4F * f3 * this.jumpPower)));
				}



				this.jumpPower = 0.0F;
			}

			this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;
			if (this.canPassengerSteer()) {
				this.setAIMoveSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
				super.travel(new Vector3d((double) f, travelVector.y, (double) f1));
			} else if (livingentity instanceof PlayerEntity) {
				this.setMotion(Vector3d.ZERO);
			}

			if (this.onGround) {
				this.jumpPower = 0.0F;
				this.setHorseJumping(false);
			}

			this.func_233629_a_(this, false);
		} else {
			super.travel(travelVector);
		}

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
			ITextComponent horseDisplayName = this.getDisplayName();
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
	public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
		ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
		if (!this.isChild()) {
			if (this.isTame() && p_230254_1_.isSecondaryUseActive()) {
				this.openGUI(p_230254_1_);
				return ActionResultType.func_233537_a_(this.world.isRemote);
			}

			if (this.isBeingRidden()) {
				return super.func_230254_b_(p_230254_1_, p_230254_2_);
			}
		}

		if (!itemstack.isEmpty() && itemstack.getItem() != Items.SADDLE) {
			if (this.isBreedingItem(itemstack)) {
				return this.func_241395_b_(p_230254_1_, itemstack);
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
			if (this.isSWEMArmor(itemstack) || flag) {
				this.setSWEMSaddled();
				this.openGUI(p_230254_1_);
				return ActionResultType.func_233537_a_(this.world.isRemote);
			}
		}

		if (this.isChild()) {
			return super.func_230254_b_(p_230254_1_, p_230254_2_);
		} else {
			this.mountTo(p_230254_1_);
			return ActionResultType.func_233537_a_(this.world.isRemote);
		}
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

	// createChild method
	public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		AbstractHorseEntity abstracthorseentity;
		if (p_241840_2_ instanceof DonkeyEntity) {
			abstracthorseentity = EntityType.MULE.create(p_241840_1_);
		} else {
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
		}

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

	public boolean isSWEMArmor(ItemStack stack) {
		//return stack.getItem() instanceof SWEMHorseArmorItem;
		return false;
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

	public enum HorseType {

		Western(1.0f, 1.0f, 1.0f);

		private float movementModifier;
		private float healthModifier;
		private float jumpModifier;
		HorseType(float movementModifier, float healthModifier, float jumpModifier) {
			this.movementModifier = movementModifier;
			this.healthModifier = healthModifier;
			this.jumpModifier = jumpModifier;
		}
	}


}
