package com.alaharranhonor.swem.entities;

import net.minecraft.block.SoundType;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.horse.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.world.World;
import software.bernie.geckolib.animation.builder.AnimationBuilder;
import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;

public class SWEMHorseEntity_Backup extends AbstractHorseEntity implements IAnimatedEntity {

    private EntityAnimationManager manager = new EntityAnimationManager();
    private EntityAnimationController controller = new EntityAnimationController(this, "walkController", 20,
            this::animationPredicate);

    public SWEMHorseEntity_Backup(EntityType<? extends AbstractHorseEntity> type, World worldIn){
        super(type, worldIn);
        registerAnimationControllers();
    }

    public void func_230273_eI_() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)this.getModifiedMaxHealth());
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getModifiedMovementSpeed());
        this.getAttribute(Attributes.HORSE_JUMP_STRENGTH).setBaseValue(this.getModifiedJumpStrength());
    }

    @Override
    public EntityAnimationManager getAnimationManager() {
        return manager;
    }

    private <E extends Entity> boolean animationPredicate(AnimationTestEvent<E> event) {
        if(event.isWalking()){
            controller.setAnimation(new AnimationBuilder().addAnimation("walk"));
            return true;
        }
        else{
            controller.setAnimation(new AnimationBuilder().addAnimation("stand_idle"));
            return true;
        }
    }

    public SoundEvent getAmbientSound() {
        super.getAmbientSound();
        return SoundEvents.ENTITY_HORSE_AMBIENT;
    }

    public void playGallopSound(SoundType p_190680_1_) {
        super.playGallopSound(p_190680_1_);
        if (this.rand.nextInt(10) == 0) {
            this.playSound(SoundEvents.ENTITY_HORSE_BREATHE, p_190680_1_.getVolume() * 0.6F, p_190680_1_.getPitch());
        }
    }

    public SoundEvent getDeathSound() {
        super.getDeathSound();
        return SoundEvents.ENTITY_HORSE_DEATH;
    }

    public SoundEvent getHurtSound(DamageSource damageSourceIn) {
        super.getHurtSound(damageSourceIn);
        return SoundEvents.ENTITY_HORSE_HURT;
    }

    public SoundEvent getAngrySound() {
        super.getAngrySound();
        return SoundEvents.ENTITY_HORSE_ANGRY;
    }


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

        if (!itemstack.isEmpty()) {
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

            boolean flag = !this.isChild() && !this.isHorseSaddled() && itemstack.getItem() == Items.SADDLE;
            if (this.isArmor(itemstack) || flag) {
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

    public boolean wearsArmor() {
        return true;
    }

    private void registerAnimationControllers() {
        manager.addAnimationController(controller);
    }
    
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }
}
