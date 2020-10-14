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
import net.minecraft.entity.passive.AnimalEntity;
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
import net.minecraftforge.registries.ForgeRegistries;
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

public class SWEMHorseEntity extends AbstractHorseEntity implements IAnimatedEntity {

	public static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(RegistryHandler.AMETHYST.get());

	private EatGrassGoal eatGrassGoal;
	private int SWEMHorseTimer;
	private EntityAnimationManager manager = new EntityAnimationManager();
	private AnimationController controller = new EntityAnimationController(this, "moveController", 20, this::animationPredicate);

	private static Random rand = new Random();

	public SWEMHorseEntity(EntityType<? extends HorseEntity> type, World worldIn)
	{
		super(type, worldIn);
		registerAnimationControllers();
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


	// createChild method
	@Nullable
	@Override
	public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_)
	{

		return RegistryHandler.SWEM_HORSE_ENTITY.get().create(this.world);
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


	@Override
	public EntityAnimationManager getAnimationManager() {
		return this.manager;
	}

	private <E extends SWEMHorseEntity> boolean animationPredicate(AnimationTestEvent<E> event)
	{
		if (event.isWalking())
		{
			controller.setAnimation(new AnimationBuilder().addAnimation("walk"));
			return true;
		} else {
			controller.setAnimation(new AnimationBuilder().addAnimation("stand_idle"));
			return true;
		}

	}

	private <E extends Entity> SoundEvent soundListener(SoundKeyframeEvent<E> event)
	{
		// Sound event should be added in the animation.json file.
//		if (event.sound.equals("moving"))
//		{
//			return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValues().toArray()[rand.nextInt((ForgeRegistries.SOUND_EVENTS.getValues().size()))];
//		}
//		else if (event.sound.equals("ambient"))
//		{
//			return getAmbientSound();
//		} else {
//			return null;
//		}
		return null;
	}

	private <E extends Entity> void particleListener(ParticleKeyFrameEvent<E> event)
	{
		// Particle effects should be added in the animation.json file.
//		if (event.effect.equals("moving"))
//		{
//
//		}
//		else if (event.effect.equals("ambient"))
//		{
//
//		} else {
//
//		}

	}

	private void registerAnimationControllers()
	{
		manager.addAnimationController(controller);
		//controller.registerSoundListener(this::soundListener);
		//controller.registerParticleListener(this::particleListener);
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
}
