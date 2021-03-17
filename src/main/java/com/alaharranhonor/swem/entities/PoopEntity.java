package com.alaharranhonor.swem.entities;

import com.alaharranhonor.swem.util.initialization.SWEMItems;
import net.minecraft.entity.*;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class PoopEntity extends LivingEntity implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);

	public PoopEntity(EntityType<? extends PoopEntity> p_i50225_1_, World world) {
		super(p_i50225_1_, world);
		this.stepHeight = 0.0F;
		this.ignoreFrustumCheck = true;
	}

	@Override
	public Iterable<ItemStack> getArmorInventoryList() {
		return NonNullList.withSize(1, ItemStack.EMPTY);
	}

	@Override
	public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {

	}

	@Override
	public HandSide getPrimaryHand() {
		return HandSide.RIGHT;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.getTrueSource() instanceof PlayerEntity && ((PlayerEntity)source.getImmediateSource()).abilities.allowEdit) {
			this.entityDropItem(new ItemStack(SWEMItems.POOP.get()));
			this.remove();
		}
		return super.attackEntityFrom(source, amount);
	}

	@Override
	public boolean canBePushed() {
		return false;
	}


	@Override
	protected void collideWithEntity(Entity entityIn) {
	}

	@Override
	public boolean attackable() {
		return false;
	}

	@Override
	public boolean canBeHitWithPotion() {
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
