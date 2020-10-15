package com.alaharranhonor.swem.entities;

import com.alaharranhonor.swem.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animation.builder.AnimationBuilder;
import software.bernie.geckolib.animation.controller.AnimationController;
import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.event.ParticleKeyFrameEvent;
import software.bernie.geckolib.event.SoundKeyframeEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;

import javax.annotation.Nullable;
import java.util.Random;

public class SWEMHorseEntityBase extends AbstractHorseEntity implements IAnimatedEntity {

	public static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(RegistryHandler.AMETHYST.get());

	private EatGrassGoal eatGrassGoal;
	private int SWEMHorseTimer;
	private static Random rand = new Random();

	private final LevelingManager leveling;
	private final StatManager stats;

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
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.2D));
		//this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0d));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, TEMPTATION_ITEMS, false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0D));
		this.goalSelector.addGoal(5, this.eatGrassGoal);
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0f));
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
		return SoundEvents.ENTITY_HORSE_HURT;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.ENTITY_HORSE_STEP, 0.15f, 1.0f);
	}




	@Override
	protected void updateAITasks()
	{
		this.SWEMHorseTimer = this.eatGrassGoal.getEatingGrassTimer();
		super.updateAITasks();
	}

	@Override
	public void livingTick()
	{

		if (this.world.isRemote)
		{
			this.SWEMHorseTimer = Math.max(0, this.SWEMHorseTimer - 1);
		}
		super.livingTick();
	}

	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdates(byte id)
	{
		if (id == 10)
		{
			this.SWEMHorseTimer = 40;
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
		return (double)0.4F + rand.nextDouble() * 0.2D + rand.nextDouble() * 0.2D + rand.nextDouble() * 0.2D;
	}

	private static double getAlteredMaxHealth()
	{
		return 15.0F + (float)rand.nextInt(8) + (float)rand.nextInt(9);
	}

	@Override
	public EntityAnimationManager getAnimationManager() {
		return null;
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
