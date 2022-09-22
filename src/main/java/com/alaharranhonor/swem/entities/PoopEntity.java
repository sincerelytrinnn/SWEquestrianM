package com.alaharranhonor.swem.entities;

/*
 * All Rights Reserved
 *
 * Copyright (c) 2021, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class PoopEntity extends LivingEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    private int washedAway = 0;
    private int stepDelay = 0;

    /**
     * Instantiates a new Poop entity.
     *
     * @param p_i50225_1_ the p i 50225 1
     * @param world       the world
     */
    public PoopEntity(EntityType<? extends PoopEntity> p_i50225_1_, World world) {
        super(p_i50225_1_, world);
        this.maxUpStep = 0.0F;
        this.noCulling = true;
        this.setYBodyRot(this.getRandom().nextFloat());
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
    public IPacket<?> getAddEntityPacket() {
        this.setRot((float) this.getRandom().nextInt(360), this.getRotationVector().x);
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public HandSide getMainArm() {
        return HandSide.RIGHT;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if ((source.getDirectEntity() instanceof PlayerEntity)) {
            if (!((PlayerEntity) source.getDirectEntity()).abilities.mayBuild) {
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
            if (this.stepDelay == 0 && Math.random() <= 0.2) {
                this.spawnAtLocation(new ItemStack(SWEMItems.POOP.get()));
                this.remove();
            } else {
                this.stepDelay = 20;
            }
        }
    }

    /**
     * Returns whether this Entity is invulnerable to the given DamageSource.
     *
     * @param pSource The damage source
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

        this.stepDelay = Math.max(0, this.stepDelay - 1);
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

    /**
     * Predicate play state.
     *
     * @param <E>   the type parameter
     * @param event the event
     * @return the play state
     */
    public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(
            new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
