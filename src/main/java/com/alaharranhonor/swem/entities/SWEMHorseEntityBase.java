package com.alaharranhonor.swem.entities;

import com.alaharranhonor.swem.container.SWEMHorseInventoryContainer;
import com.alaharranhonor.swem.entities.goals.PoopGoal;
import com.alaharranhonor.swem.items.BlanketItem;
import com.alaharranhonor.swem.items.HorseSaddleItem;
import com.alaharranhonor.swem.util.RegistryHandler;
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
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
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
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class SWEMHorseEntityBase extends AbstractHorseEntity implements ISWEMEquipable {




	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
	//private static final DataParameter<Integer> HORSE_VARIANT = EntityDataManager.createKey(HorseEntity.class, DataSerializers.VARINT);
	public static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(RegistryHandler.AMETHYST.get());
	private EatGrassGoal eatGrassGoal;
	private PoopGoal poopGoal;
	private int SWEMHorseGrassTimer;
	private int SWEMHorsePoopTimer;
	private static Random rand = new Random();

	private final LevelingManager leveling;
	private final StatManager stats;
	private LazyOptional<InvWrapper> itemHandler;

	public SWEMHorseEntityBase(EntityType<? extends AbstractHorseEntity> type, World worldIn)
	{
		super(type, worldIn);
		this.leveling = new LevelingManager();
		this.stats = new StatManager();

	}

	// func_233666_p_ -> registerAttributes()
	public static AttributeModifierMap.MutableAttribute setCustomAttributes()
	{
		return MobEntity.func_233666_p_()
				.createMutableAttribute(Attributes.MAX_HEALTH, getAlteredMaxHealth())
				.createMutableAttribute(Attributes.HORSE_JUMP_STRENGTH, getAlteredJumpStrength())
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, getAlteredMovementSpeed());
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.eatGrassGoal = new EatGrassGoal(this);
		this.poopGoal = new PoopGoal(this);
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




	private static double getAlteredMovementSpeed()
	{
		return ((double)0.45F + rand.nextDouble() * 0.3D + rand.nextDouble() * 0.3D + rand.nextDouble() * 0.3D) * 0.25D;
	}

	private static double getAlteredJumpStrength()
	{
		return (double)0.4F + rand.nextDouble() * 1.5D + rand.nextDouble() * 1.5D + rand.nextDouble() * 1.5D;
	}

	private static double getAlteredMaxHealth()
	{
		return 15.0F + (float)rand.nextInt(8) + (float)rand.nextInt(9);
	}

//	protected void registerData() {
//		super.registerData();
//		this.dataManager.register(HORSE_VARIANT, 0);
//	}

	public boolean func_230264_L__() {
		return this.isAlive() && !this.isChild() && this.isTame();
	}

	public void func_230266_a_(@Nullable SoundCategory p_230266_1_, ItemStack stack) {
		this.horseChest.setInventorySlotContents(2, stack);
		if (p_230266_1_ != null) {
			this.world.playMovingSound((PlayerEntity)null, this, SoundEvents.ENTITY_HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
		}

	}

	public boolean isHorseSaddled() {
		return this.isSWEMSaddled();
	}


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
		if (!this.horseChest.getStackInSlot(1).isEmpty()) {
			compound.put("ArmorItem", this.horseChest.getStackInSlot(1).write(new CompoundNBT()));
		}
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
		if (compound.contains("ArmorItem", 10)) {
			ItemStack itemstack = ItemStack.read(compound.getCompound("ArmorItem"));
			if (!itemstack.isEmpty() && this.isSWEMArmor(itemstack)) {
				this.horseChest.setInventorySlotContents(1, itemstack);
			}
		}

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

	protected void func_230275_fc_() {
		if (!this.world.isRemote) {
			super.func_230275_fc_();
			this.func_213804_l(this.horseChest.getStackInSlot(1));
			this.setDropChance(EquipmentSlotType.CHEST, 0.0F);
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
	 * Called by InventoryBasic.onInventoryChanged() on a array that is never filled.
	 */
	public void onInventoryChanged(IInventory invBasic) {
		ItemStack itemstack = this.func_213803_dV();
		super.onInventoryChanged(invBasic);
		ItemStack itemstack1 = this.func_213803_dV();
		if (this.ticksExisted > 20 && this.isSWEMArmor(itemstack1) && itemstack != itemstack1) {
			this.playSound(SoundEvents.ENTITY_HORSE_ARMOR, 0.5F, 1.0F);
		}

		if (this.isSWEMSaddled()) {
			this.setSWEMSaddled();
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
			INamedContainerProvider provider = new INamedContainerProvider() {
				@Override
				public ITextComponent getDisplayName() {
					return ITextComponent.getTextComponentOrEmpty("SWEM Horse");
				}

				@Nullable
				@Override
				public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
					return new SWEMHorseInventoryContainer(p_createMenu_1_, p_createMenu_2_, getEntityId());
				}
			};
			NetworkHooks.openGui((ServerPlayerEntity) playerEntity, provider, buffer -> buffer.writeVarInt(getEntityId()).writeVarInt(getEntityId()));
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
				return actionresulttype;
			}

			if (!this.isTame()) {
				this.makeMad();
				return ActionResultType.func_233537_a_(this.world.isRemote);
			}

			boolean flag = !this.isChild() && !this.isSWEMSaddled() && (itemstack.getItem() instanceof HorseSaddleItem);
			if (this.isSWEMArmor(itemstack) || flag) {
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

	public boolean isBridle(ItemStack stack) {
		//return stack.getItem() instanceof BridleItem;
		return false;
	}

	public boolean isBreastCollar(ItemStack stack) {
		//return stack.getItem() instanceof BreastCollarItem;
		return false;
	}

	public boolean isLegWraps(ItemStack stack) {
		//return stack.getItem() instanceof LegWrapItem;
		return false;
	}

	public boolean isGirthStrap(ItemStack stack) {
		//return stack.getItem() instanceof GirthStrapItem;
		return false;
	}

	public boolean isSWEMArmor(ItemStack stack) {
		//return stack.getItem() instanceof SWEMHorseArmorItem;
		return false;
	}

	public boolean isBlanket(ItemStack stack) {
		return stack.getItem() instanceof BlanketItem;
	}

	public boolean isSaddle(ItemStack stack) {
		return stack.getItem() instanceof HorseSaddleItem;
	}

	private class LevelingManager
	{
		private LevelingManager()
		{

		}

	}

	private class StatManager {
		private StatManager()
		{

		}
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
