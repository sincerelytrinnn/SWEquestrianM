/*package com.alaharranhonor.swem.entities;

import net.minecraft.block.SoundType;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import software.bernie.geckolib.animation.builder.AnimationBuilder;
import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;

public class SWEMHorseEntity extends AbstractHorseEntity implements IAnimatedEntity {

    private EntityAnimationManager manager = new EntityAnimationManager();
    private EntityAnimationController controller = new EntityAnimationController(this, "walkController", 20,
            this::animationPredicate);

    public SWEMHorseEntity(EntityType<? extends AbstractHorseEntity> type, World worldIn){
        super(type, worldIn);
        registerAnimationControllers();
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(28);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20D);
        this.getAttribute(JUMP_STRENGTH).setBaseValue(this.getModifiedJumpStrength());
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

    protected SoundEvent getAmbientSound() {
        super.getAmbientSound();
        return SoundEvents.ENTITY_HORSE_AMBIENT;
    }

    protected void playGallopSound(SoundType p_190680_1_) {
        super.playGallopSound(p_190680_1_);
        if (this.rand.nextInt(10) == 0) {
            this.playSound(SoundEvents.ENTITY_HORSE_BREATHE, p_190680_1_.getVolume() * 0.6F, p_190680_1_.getPitch());
        }
    }

    protected SoundEvent getDeathSound() {
        super.getDeathSound();
        return SoundEvents.ENTITY_HORSE_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        super.getHurtSound(damageSourceIn);
        return SoundEvents.ENTITY_HORSE_HURT;
    }

    protected SoundEvent getAngrySound() {
        super.getAngrySound();
        return SoundEvents.ENTITY_HORSE_ANGRY;
    }

    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = !itemstack.isEmpty();
        if (flag && itemstack.getItem() instanceof SpawnEggItem) {
            return super.processInteract(player, hand);
        } else {
            if (!this.isChild()) {
                if (this.isTame() && player.isSecondaryUseActive()) {
                    this.openGUI(player);
                    return true;
                }

                if (this.isBeingRidden()) {
                    return super.processInteract(player, hand);
                }
            }

            if (flag) {
                if (this.handleEating(player, itemstack)) {
                    if (!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    return true;
                }

                if (itemstack.interactWithEntity(player, this, hand)) {
                    return true;
                }

                if (!this.isTame()) {
                    this.makeMad();
                    return true;
                }

                boolean flag1 = !this.isChild() && !this.isHorseSaddled() && itemstack.getItem() == Items.SADDLE;
                if (this.isArmor(itemstack) || flag1) {
                    this.openGUI(player);
                    return true;
                }
            }

            if (this.isChild()) {
                return super.processInteract(player, hand);
            } else {
                this.mountTo(player);
                return true;
            }
        }
    }

    public boolean wearsArmor() {
        return true;
    }

    private void registerAnimationControllers() {
        manager.addAnimationController(controller);
    }
    
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }
}*/
