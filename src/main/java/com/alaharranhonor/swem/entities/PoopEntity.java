package com.alaharranhonor.swem.entities;

import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class PoopEntity extends LivingEntity implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);
	private int washedAway = 0;

	public PoopEntity(EntityType<? extends PoopEntity> p_i50225_1_, World world) {
		super(p_i50225_1_, world);
		this.maxUpStep = 0.0F;
		this.noCulling = true;
	}

	@Override
	public Iterable<ItemStack> getArmorSlots() {
		return NonNullList.withSize(1, ItemStack.EMPTY);
	}

	@Override
	public ItemStack getItemBySlot(EquipmentSlotType slotIn) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemSlot(EquipmentSlotType slotIn, ItemStack stack) {

	}

	@Override
	public HandSide getMainArm() {
		return HandSide.RIGHT;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if ((source.getDirectEntity() instanceof PlayerEntity)) {
			if (!((PlayerEntity)source.getDirectEntity()).abilities.mayBuild) {
				return false;
			}
		}
		if (source == DamageSource.DROWN) return false;
		this.spawnAtLocation(new ItemStack(SWEMItems.POOP.get()));
		this.remove();
		return true;
	}

	@Override
	public boolean isPushable() {
		return false;
	}


	@Override
	protected void doPush(Entity entityIn) {
		if (entityIn instanceof SWEMHorseEntityBase) {
			this.spawnAtLocation(new ItemStack(SWEMItems.POOP.get()));
			this.remove();
		}
	}

	/**
	 * Returns whether this Entity is invulnerable to the given DamageSource.
	 *
	 * @param pSource
	 */
	@Override
	public boolean isInvulnerableTo(DamageSource pSource) {
		return pSource == DamageSource.DROWN || super.isInvulnerableTo(pSource);
	}

	@Override
	protected void serverAiStep() {
		if (washedAway >= 100) {
			this.spawnAtLocation(new ItemStack(SWEMItems.POOP.get()));
			this.remove();
		}

		if (this.isInWaterOrRain() && this.level.getGameTime() % 20 == 0) {
			this.washedAway++;
		}



	}

	@Override
	public boolean attackable() {
		return false;
	}

	@Override
	public boolean isAffectedByPotions() {
		return false;
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return null;
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}
