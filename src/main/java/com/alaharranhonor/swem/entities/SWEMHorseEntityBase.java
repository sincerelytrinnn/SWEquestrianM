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

import com.alaharranhonor.swem.SWEM;
import com.alaharranhonor.swem.client.coats.SWEMCoatColor;
import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.container.SWEMHorseInventoryContainer;
import com.alaharranhonor.swem.entities.ai.*;
import com.alaharranhonor.swem.entities.needs.HungerNeed;
import com.alaharranhonor.swem.entities.needs.NeedManager;
import com.alaharranhonor.swem.entities.needs.ThirstNeed;
import com.alaharranhonor.swem.entities.progression.ProgressionManager;
import com.alaharranhonor.swem.entities.progression.leveling.AffinityLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.HealthLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.JumpLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.SpeedLeveling;
import com.alaharranhonor.swem.items.SWEMHorseArmorItem;
import com.alaharranhonor.swem.items.TrackerItem;
import com.alaharranhonor.swem.items.tack.*;
import com.alaharranhonor.swem.network.*;
import com.alaharranhonor.swem.util.ClientEventHandlers;
import com.alaharranhonor.swem.util.SWEMUtil;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMEntities;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import com.alaharranhonor.swem.util.registry.SWEMParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.CoatColors;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SMountEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

import static com.alaharranhonor.swem.entities.HorseFlightController.*;

public class SWEMHorseEntityBase extends AbstractHorseEntity implements ISWEMEquipable, IEntityAdditionalSpawnData {

    public static final Ingredient TEMPTATION_ITEMS = Ingredient.of(SWEMItems.SUGAR_CUBE.get());
    public static final Ingredient BREEDING_ITEMS = Ingredient.of(SWEMItems.SWEET_FEED_OPENED.get());
    public static final Ingredient FOOD_ITEMS = Ingredient.of(Items.APPLE, Items.CARROT, SWEMItems.OAT_BUSHEL.get(), SWEMItems.TIMOTHY_BUSHEL.get(), SWEMItems.ALFALFA_BUSHEL.get(), SWEMBlocks.QUALITY_BALE_ITEM.get(), SWEMItems.SUGAR_CUBE.get());
    public static final Ingredient NEGATIVE_FOOD_ITEMS = Ingredient.of(Items.WHEAT, Items.HAY_BLOCK);
    public static final DataParameter<Boolean> JUMPING = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Integer> SPEED_LEVEL = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);
    public static final DataParameter<String> PERMISSION_STRING = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.STRING);
    public static final DataParameter<Boolean> TRACKED = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> RENDER_SADDLE = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> RENDER_BRIDLE = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> RENDER_BLANKET = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> RENDER_GIRTH_STRAP = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> IS_BRIDLE_LEASHED = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
    // Animation variables.
    public static final DataParameter<Integer> JUMP_ANIM_TIMER = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);
    public static final DataParameter<Boolean> IS_EATING = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> IS_LAYING_DOWN = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> IS_SAD = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
    private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
    private static final DataParameter<Integer> HORSE_VARIANT = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);
    private static final DataParameter<Boolean> FLYING = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
    private static final DataParameter<String> OWNER_NAME = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.STRING);
    private static final DataParameter<Boolean> CAMERA_LOCK = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> GALLOP_TIMER = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);
    private static final DataParameter<Integer> GALLOP_COOLDOWN_TIMER = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);
    private static final DataParameter<Boolean> GALLOP_ON_COOLDOWN = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> IS_MOVING_FORWARD = EntityDataManager.defineId((SWEMHorseEntityBase.class), DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> IS_MOVING_BACKWARDS = EntityDataManager.defineId((SWEMHorseEntityBase.class), DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> IS_MOVING_LEFT = EntityDataManager.defineId((SWEMHorseEntityBase.class), DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> IS_MOVING_RIGHT = EntityDataManager.defineId((SWEMHorseEntityBase.class), DataSerializers.BOOLEAN);
    private static final Random rand = new Random();
    public final ProgressionManager progressionManager;
    private final ArrayList<UUID> allowedList = new ArrayList<>();
    private final NeedManager needs;
    private final HorseFlightController flightController;
    public HorseSpeed currentSpeed;
    public Entity leashHolder2;
    public int delayedLeashHolderId2;
    @Nullable
    public CompoundNBT leashInfoTag2;
    public double jumpHeight;
    public int standAnimationTick;
    public int standAnimationVariant;
    public int standingTimer = 0;
    public boolean isWalkingBackwards = false;
    public int kickAnimationTimer;
    public int eatAnimationTick;
    private BlockPos currentPos;
    private LazyOptional<InvWrapper> itemHandler;
    private LazyOptional<InvWrapper> saddlebagItemHandler;
    private Inventory saddlebagInventory;
    private Inventory bedrollInventory;
    private int maxGallopSeconds = 7;
    private PeeGoal peeGoal;
    private PoopGoal poopGoal;
    private BlockPos whistlePos = null;
    private float lockedXRot;
    private float lockedYRot;
    private int poopAnimationTick;
    private int peeAnimationTick;

    /**
     * Instantiates a new Swem horse entity base.
     *
     * @param type    the type
     * @param levelIn the level in
     */
    public SWEMHorseEntityBase(EntityType<? extends AbstractHorseEntity> type, World levelIn) {
        super(type, levelIn);
        this.maxUpStep = 1.1F;
        this.currentPos = this.blockPosition();
        this.progressionManager = new ProgressionManager(this);
        this.currentSpeed = HorseSpeed.WALK;
        this.updateSelectedSpeed(HorseSpeed.WALK);
        this.needs = new NeedManager(this);
        this.initSaddlebagInventory();
        this.flightController = new HorseFlightController(this);
    }

    /**
     * Sets custom attributes.
     *
     * @return the custom attributes
     */
    // createMobAttributes -> registerAttributes()
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH).add(Attributes.JUMP_STRENGTH).add(Attributes.MOVEMENT_SPEED);
    }

    @Override
    protected void randomizeAttributes() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getAlteredMaxHealth());
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getAlteredMovementSpeed());
        this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(this.getAlteredJumpStrength());
    }

    @Override
    protected void registerGoals() {
        // TODO: ADD AI TO FOLLOW WHISTLE POSITION AS TOP PRIORITY
        this.peeGoal = new PeeGoal(this, 4.0d);
        this.poopGoal = new PoopGoal(this);
        // this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 4.0D)); // Unsure why this needs a lower value than the other goals. 4.0 Would make it
        // run at insane speeds.
        this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 4.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 4.0d));
        this.goalSelector.addGoal(3, new TemptGoal(this, 4.0D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 4.0D));
        this.goalSelector.addGoal(2, new HorseAvoidEntityGoal<>(this, PigEntity.class, 12.0f, 4.0d, 5.5d));
        this.goalSelector.addGoal(5, this.poopGoal);
        this.goalSelector.addGoal(5, this.peeGoal);
        this.goalSelector.addGoal(6, new HorseWaterAvoidingRandomWalkingGoal(this, 4.0D)); // Speed 4.0 looks like a good speed, plus it triggers anim.
        this.goalSelector.addGoal(7, new LookForFoodGoal(this, 4.0d));
        this.goalSelector.addGoal(7, new LookForWaterGoal(this, 4.0d));
        // this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(9, new CustomLookRandomlyGoal(this));
    }

    /**
     * Handler for {@link ServerWorld#broadcastEntityEvent(Entity, byte)} This method is being called
     * from the SEntityStatusPacket, which is fired in the goal's start method, with the
     * broadcastAndSendChanges This is so we can set animation timers on the client side on the
     * entity.
     */
    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 10) {
            this.eatAnimationTick = 63;
        } else if (pId == 127) { // Poop goal
            this.poopAnimationTick = 79;
        } else if (pId == 126) { // Pee goal
            this.peeAnimationTick = 79;
        } else {
            super.handleEntityEvent(pId);
        }
    }

    /**
     * Is pooping boolean.
     *
     * @return the boolean
     */
    public boolean isPooping() {
        return this.poopAnimationTick > 0;
    }

    /**
     * Is peeing boolean.
     *
     * @return the boolean
     */
    public boolean isPeeing() {
        return this.peeAnimationTick > 0;
    }

    @Override
    public boolean isStanding() {
        return super.isStanding() || this.standAnimationTick > 0;
    }

    /**
     * @return an integer based on the variant. 2 = buck, 1 = Rear.
     */
    public int getStandVariant() {
        return this.standAnimationVariant;
    }

    /**
     * Get the experience points the entity currently has.
     *
     * @param pPlayer
     */
    @Override
    protected int getExperienceReward(PlayerEntity pPlayer) {
        return 0;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.HORSE_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.HORSE_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        super.getHurtSound(damageSourceIn);
        return SoundEvents.HORSE_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.HORSE_STEP, 0.15f, 1.0f);
    }

    /**
     * Gets owner name.
     *
     * @return the owner name
     */
    public String getOwnerName() {
        return this.entityData.get(OWNER_NAME);
    }

    /**
     * Sets owner name.
     *
     * @param ownerName the owner name
     */
    public void setOwnerName(String ownerName) {
        this.entityData.set(OWNER_NAME, ownerName);
    }

    /**
     * Custom method only called on the server. Used for updating server-side state every tick.
     */
    @Override
    protected void customServerAiStep() {
        this.peeAnimationTick = this.peeGoal.getPeeTimer();
        this.poopAnimationTick = this.poopGoal.getPoopTimer();
        super.customServerAiStep();
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example,
     * zombies and skeletons use this to react to sunlight and start to burn.
     */
    @Override
    public void aiStep() {

        // Tick the animation timers.
        this.peeAnimationTick = Math.max(0, this.peeAnimationTick - 1);
        this.poopAnimationTick = Math.max(0, this.poopAnimationTick - 1);
        this.standAnimationTick = Math.max(0, this.standAnimationTick - 1);
        this.standingTimer = Math.max(0, this.standingTimer - 1);
        this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        this.kickAnimationTimer = Math.max(0, this.kickAnimationTimer - 1);
        if (!this.level.isClientSide) {
            if (this.isCrossTied()) {
                this.lookAt(EntityAnchorArgument.Type.EYES, this.getCrossTieMiddlePoint());
                this.setYBodyRot(this.getYHeadRot());
                this.goalSelector.disableControlFlag(Goal.Flag.JUMP);
                this.goalSelector.disableControlFlag(Goal.Flag.MOVE);
                this.goalSelector.disableControlFlag(Goal.Flag.LOOK);
                this.goalSelector.disableControlFlag(Goal.Flag.TARGET);
            } else {
                this.goalSelector.enableControlFlag(Goal.Flag.JUMP);
                this.goalSelector.enableControlFlag(Goal.Flag.MOVE);
                this.goalSelector.enableControlFlag(Goal.Flag.LOOK);
                this.goalSelector.enableControlFlag(Goal.Flag.TARGET);
            }
            // Tick entity data anim timers
            this.getEntityData().set(JUMP_ANIM_TIMER, Math.max(-1, this.getEntityData().get(JUMP_ANIM_TIMER) - 1));

            if (this.isInWater()) {
                if (this.currentSpeed != HorseSpeed.WALK) {
                    HorseSpeed old = this.currentSpeed;
                    this.currentSpeed = HorseSpeed.WALK;
                    this.updateSelectedSpeed(old);
                }
            }

            if (this.getLeashHolder() instanceof PlayerEntity) {
                this.getLookControl().setLookAt(this.getLeashHolder(), (float) this.getHeadRotSpeed(), (float) this.getMaxHeadXRot());
            }
            if (this.whistlePos != null) {
                this.getNavigation().moveTo(whistlePos.getX(), whistlePos.getY(), whistlePos.getZ(), this.getSpeed());
                if (this.blockPosition().closerThan(this.whistlePos, 2)) {
                    this.whistlePos = null;
                    this.getNavigation().stop();
                }
            }

            if (this.standAnimationTick == 20 && this.getStandVariant() == 2) {
                this.level.getNearbyEntities(LivingEntity.class, new EntityPredicate().range(5), this, this.getBoundingBox().inflate(2)).forEach((entity) -> {
                    if (entity.getVehicle() != this) {
                        entity.hurt(DamageSource.GENERIC, 5);
                        entity.knockback(0.5f, 0.5, 0.5);
                    }
                });
                this.standAnimationVariant = -1;
            }

            if (this.standAnimationTick == 0 && this.getFlag(32)) {
                this.setStanding(false);
            }

            if ((int) (this.level.getDayTime() % 24000L) == 10000) {
                this.resetDaily();
            }

            if (this.entityData.get(GALLOP_ON_COOLDOWN)) {
                // Count the cooldown.
                this.entityData.set(GALLOP_TIMER, this.entityData.get(GALLOP_TIMER) + 1);
                int currentTimer = this.entityData.get(GALLOP_TIMER);
                int cooldownTimer = this.entityData.get(GALLOP_COOLDOWN_TIMER);
                if (currentTimer >= cooldownTimer) {
                    this.resetGallopCooldown();
                }
            } else if (this.currentSpeed == HorseSpeed.GALLOP && !this.entityData.get(GALLOP_ON_COOLDOWN)) {
                // COUNT
                int timer = this.entityData.get(GALLOP_TIMER);
                this.entityData.set(GALLOP_TIMER, this.entityData.get(GALLOP_TIMER) + 1);
                if (timer == this.maxGallopSeconds * 20) {
                    this.decrementSpeed();
                }
            }

            if (this.isTamed()) {
                this.needs.tick();
            }

            if (!this.isVehicle() && !this.isCameraLocked()) {
                this.setCameraLock(true);
            }
        }

        super.aiStep();
    }

    private boolean isCrossTied() {
        return this.leashHolder instanceof LeashKnotEntity && this.leashHolder2 instanceof LeashKnotEntity;
    }

    private Vector3d getCrossTieMiddlePoint() {
        Vector3d leashOne = this.leashHolder.position();
        Vector3d leashTwo = this.leashHolder2.position();
        return new Vector3d((leashOne.x + leashTwo.x) / 2, this.getEyeY(), (leashOne.z + leashTwo.z) / 2);
    }

    /**
     * Reset day based things.
     */
    private void resetDaily() {
        this.needs.getHunger().resetDaily();
        this.progressionManager.getAffinityLeveling().resetDaily();
    }

    /**
     * Gets the current base movement speed value for the current speed level.
     *
     * @return double The speed value used for Movement Speed
     */
    private double getAlteredMovementSpeed() {
        switch (this.progressionManager.getSpeedLeveling().getLevel()) {
            case 1:
                return SWEMUtil.getInternalSpeedFromBlocksPerSecond(13D);
            case 2:
                return SWEMUtil.getInternalSpeedFromBlocksPerSecond(15D);
            case 3:
                return SWEMUtil.getInternalSpeedFromBlocksPerSecond(17D);
            case 4:
                return SWEMUtil.getInternalSpeedFromBlocksPerSecond(19D);
            default:
                return SWEMUtil.getInternalSpeedFromBlocksPerSecond(11D);
        }
    }

    /**
     * Gets the jump strength value for the current jump level.
     *
     * @return double The jump value used for Jump Strength
     */
    private double getAlteredJumpStrength() {
        switch (this.progressionManager.getJumpLeveling().getLevel()) {
            case 1:
                return SWEMUtil.getInternalJumpFromBlocks(2.7D);
            case 2:
                return SWEMUtil.getInternalJumpFromBlocks(3.7D);
            case 3:
                return SWEMUtil.getInternalJumpFromBlocks(4.7D);
            case 4:
                return SWEMUtil.getInternalJumpFromBlocks(5.7D);
            default: {
                return SWEMUtil.getInternalJumpFromBlocks(1.7D);
            }
        }
    }

    /**
     * Gets the current base max health value for the current health level.
     *
     * @return double The max health value used for Max Health
     */
    private double getAlteredMaxHealth() {
        switch (this.progressionManager.getHealthLeveling().getLevel()) {
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

    /**
     * @return float Returns the water slow modifier used when in water.
     */
    @Override
    protected float getWaterSlowDown() {
        return 0.9F;
    }

    /**
     * Wrapper method for {@link #heal(float)} which also adds healthXp.
     *
     * @param healAmount The amount to heal.
     * @param healthXp   The amount of health xp to give.
     */
    public void heal(float healAmount, float healthXp) {
        this.heal(healAmount);
        this.progressionManager.getHealthLeveling().addXP(healthXp);
    }

    /**
     * Defines data that should be synced between SERVER and CLIENT.
     */
    protected void defineSynchedData() {
        super.defineSynchedData();
        // this.entityData.register(HORSE_VARIANT, 0);

        this.entityData.define(SpeedLeveling.LEVEL, 0);
        this.entityData.define(SpeedLeveling.XP, 0.0f);

        this.entityData.define(JumpLeveling.LEVEL, 0);
        this.entityData.define(JumpLeveling.XP, 0.0f);

        this.entityData.define(HealthLeveling.LEVEL, 0);
        this.entityData.define(HealthLeveling.XP, 0.0f);

        this.entityData.define(AffinityLeveling.LEVEL, 0);
        this.entityData.define(AffinityLeveling.XP, 0.0f);

        this.entityData.define(HungerNeed.HungerState.ID, 4);
        this.entityData.define(ThirstNeed.ThirstState.ID, 4);

        this.entityData.define(GALLOP_ON_COOLDOWN, false);
        this.entityData.define(GALLOP_COOLDOWN_TIMER, 0);
        this.entityData.define(GALLOP_TIMER, 0);
        this.entityData.define(SPEED_LEVEL, 0);

        this.entityData.define(HungerNeed.TOTAL_TIMES_FED, 0);

        this.entityData.define(AffinityLeveling.CURRENT_DESENSITIZING_ITEM, ItemStack.EMPTY);
        this.entityData.define(HORSE_VARIANT, SWEMCoatColor.getRandomLapisObtainableCoat().getId());
        this.entityData.define(FLYING, false);
        this.entityData.define(JUMPING, false);
        this.entityData.define(OWNER_NAME, "");

        this.getEntityData().define(isLaunching, false);
        this.getEntityData().define(isFloating, false);
        this.getEntityData().define(isAccelerating, false);
        this.getEntityData().define(isSlowingDown, false);
        this.getEntityData().define(isStillSlowingDown, false);
        this.getEntityData().define(isTurningLeft, false);
        this.getEntityData().define(isTurning, false);
        this.getEntityData().define(isStillTurning, false);
        this.getEntityData().define(didFlap, false);
        this.getEntityData().define(isDiving, false);
        this.entityData.define(PERMISSION_STRING, "ALL");
        this.entityData.define(TRACKED, false);

        this.entityData.define(CAMERA_LOCK, true);

        this.entityData.define(JUMP_ANIM_TIMER, 0);
        this.entityData.define(IS_EATING, false);
        this.entityData.define(IS_LAYING_DOWN, false);
        this.entityData.define(IS_SAD, false);

        this.entityData.define(RENDER_SADDLE, true);
        this.entityData.define(RENDER_BLANKET, true);
        this.entityData.define(RENDER_BRIDLE, true);
        this.entityData.define(RENDER_GIRTH_STRAP, true);

        this.entityData.define(IS_BRIDLE_LEASHED, false);

        this.entityData.define(IS_MOVING_FORWARD, false);
        this.entityData.define(IS_MOVING_BACKWARDS, false);
        this.entityData.define(IS_MOVING_LEFT, false);
        this.entityData.define(IS_MOVING_RIGHT, false);
    }

    public void setMaxGallopSeconds(int gallopSeconds) {
        this.maxGallopSeconds = gallopSeconds;
    }

    /**
     * Setter for the synced data value of the horse being tracked.
     *
     * @param tracked Is being tracked?
     */
    public void setTracked(boolean tracked) {
        this.entityData.set(TRACKED, tracked);
    }

    /**
     * @return boolean Returns if the horse is being tracked or not.
     */
    public boolean isBeingTracked() {
        return this.entityData.get(TRACKED);
    }

    /**
     * Is bridle leashed boolean.
     *
     * @return the boolean
     */
    public boolean isBridleLeashed() {
        return this.entityData.get(IS_BRIDLE_LEASHED);
    }

    /**
     * Sets bridle leashed.
     *
     * @param bridleLeashed the bridle leashed
     */
    public void setBridleLeashed(boolean bridleLeashed) {
        this.entityData.set(IS_BRIDLE_LEASHED, bridleLeashed);
    }

    /**
     * Applies logic related to leashes, for example dragging the entity or breaking the leash.
     */
    @Override
    protected void tickLeash() {

        if (this.getLeashHolder() != null && !this.getLeashHolder().isAlive() && this.level.isEmptyBlock(this.getLeashHolder().blockPosition())) {
            this.dropLeash(true, false);
            return;
        }

        // MobEntity#tickLeash - Start
        if (this.leashInfoTag != null) {
            this.restoreLeashFromSave();
        }

        if (this.leashInfoTag2 != null) {
            this.restoreLeash2FromSave();
        }

        if (this.leashHolder2 != null) {
            if (!this.isAlive() || !this.leashHolder2.isAlive()) {
                this.dropLeash(true, true);
            }
        }

        if (this.leashHolder != null) {
            if (!this.isAlive() || !this.leashHolder.isAlive()) {
                this.dropLeash(true, true);
            }
        }
        // MobEntity#tickLeash - End

        // CreatureEntity#tickLeash - Start
        Entity entity = this.getLeashHolder();
        if (entity != null && entity.level == this.level) {
            this.restrictTo(entity.blockPosition(), 5);
            float f = this.distanceTo(entity);

            this.onLeashDistance(f);
            if (f > 10.0F) {
                this.dropLeash(true, true);
                this.goalSelector.disableControlFlag(Goal.Flag.MOVE);
            } else if (f > 6.0F) {
                double d0 = (entity.getX() - this.getX()) / (double) f;
                double d1 = (entity.getY() - this.getY()) / (double) f;
                double d2 = (entity.getZ() - this.getZ()) / (double) f;
                this.setDeltaMovement(this.getDeltaMovement().add(Math.copySign(d0 * d0 * 0.4D, d0), Math.copySign(d1 * d1 * 0.4D, d1), Math.copySign(d2 * d2 * 0.4D, d2)));
            } else {
                this.goalSelector.enableControlFlag(Goal.Flag.MOVE);
                float f1 = 2.0F;
                Vector3d vector3d = (new Vector3d(entity.getX() - this.getX(), entity.getY() - this.getY(), entity.getZ() - this.getZ())).normalize().scale((double) Math.max(f - 2.0F, 0.0F));
                this.getNavigation().moveTo(this.getX() + vector3d.x, this.getY() + vector3d.y, this.getZ() + vector3d.z, this.followLeashSpeed());
            }
        }
        // CreatureEntity#tickLeash - End
    }

    /**
     * Removes the leash from this entity
     *
     * @param pSendPacket
     * @param pDropLead
     */
    @Override
    public void dropLeash(boolean pSendPacket, boolean pDropLead) {

        if (this.isBridleLeashed()) {
            pDropLead = false;
            if (!this.level.isClientSide) {
                this.setBridleLeashed(false);
            }
        }

        if (this.leashHolder2 != null) {
            this.forcedLoading = false;
            if (!(this.leashHolder2 instanceof PlayerEntity)) {
                this.leashHolder2.forcedLoading = false;
            }

            this.leashHolder2 = null;
            this.leashInfoTag = null;
            if (!this.level.isClientSide && pDropLead) {
                this.spawnAtLocation(Items.LEAD);
            }

            if (!this.level.isClientSide && pSendPacket && this.level instanceof ServerWorld) {
                ((ServerWorld) this.level).getChunkSource().broadcast(this, new SMountEntityPacket(this, (Entity) null));
            }
        }

        if (this.leashHolder != null) {
            this.forcedLoading = false;
            if (!(this.leashHolder instanceof PlayerEntity)) {
                this.leashHolder.forcedLoading = false;
            }

            this.leashHolder = null;
            this.leashInfoTag = null;
            if (!this.level.isClientSide && pDropLead) {
                this.spawnAtLocation(Items.LEAD);
            }

            if (!this.level.isClientSide && pSendPacket && this.level instanceof ServerWorld) {
                ((ServerWorld) this.level).getChunkSource().broadcast(this, new SMountEntityPacket(this, (Entity) null));
            }
        }
    }

    @Override
    public boolean canBeLeashed(PlayerEntity p_184652_1_) {
        return this.canAccessHorse(p_184652_1_) && !this.isLeashed() || (this.isLeashed() && !(this.getLeashHolder() instanceof PlayerEntity) && this.leashHolder2 == null);
    }

    @Override
    public boolean isLeashed() {
        return this.leashHolder != null;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public Entity getLeashHolder() {
        if (this.leashHolder2 == null && this.delayedLeashHolderId2 != 0 && this.level.isClientSide) {
            this.leashHolder2 = this.level.getEntity(this.delayedLeashHolderId2);
        }
        if (leashHolder2 != null) {
            return this.leashHolder2;
        }

        if (this.leashHolder == null && this.delayedLeashHolderId != 0 && this.level.isClientSide) {
            this.leashHolder = this.level.getEntity(this.delayedLeashHolderId);
        }

        return this.leashHolder;
    }

    public List<Entity> getLeashHolders() {
        Entity leashHolderOne = this.leashHolder;
        if (leashHolderOne == null && this.delayedLeashHolderId != 0 && this.level.isClientSide) {
            leashHolderOne = this.level.getEntity(this.delayedLeashHolderId);
        }

        Entity leashHolderTwo = this.leashHolder2;
        if (leashHolderTwo == null && this.delayedLeashHolderId2 != 0 && this.level.isClientSide) {
            leashHolderTwo = this.level.getEntity(this.delayedLeashHolderId2);
        }
        return Arrays.asList(leashHolderOne, leashHolderTwo);
    }

    /**
     * Sets the entity to be leashed to.
     */
    @Override
    public void setLeashedTo(Entity pLeashHolder, boolean pBroadcastPacket) {
        if (this.leashHolder == null || this.leashHolder instanceof PlayerEntity) {
            this.leashHolder = pLeashHolder;
            this.leashInfoTag = null;
            this.forcedLoading = true;
            if (!(this.leashHolder instanceof PlayerEntity)) {
                this.leashHolder.forcedLoading = true;
            }

            if (!this.level.isClientSide && pBroadcastPacket && this.level instanceof ServerWorld) {
                ((ServerWorld) this.level).getChunkSource().broadcast(this, new SMountEntityPacket(this, this.leashHolder));
            }
        } else if (this.leashHolder2 == null || this.leashHolder2 instanceof PlayerEntity) {
            this.leashHolder2 = pLeashHolder;
            this.leashInfoTag2 = null;
            this.forcedLoading = true;
            if (!(this.leashHolder2 instanceof PlayerEntity)) {
                this.leashHolder2.forcedLoading = true;
            }

            if (!this.level.isClientSide && pBroadcastPacket && this.level instanceof ServerWorld) {
                ((ServerWorld) this.level).getChunkSource().broadcast(this, new SMountEntityPacket(this, this.leashHolder2));
            }
        }

        if (this.isPassenger()) {
            this.stopRiding();
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setDelayedLeashHolderId(int pLeashHolderID) {

        if (pLeashHolderID == 0) {
            this.delayedLeashHolderId2 = pLeashHolderID;
            this.delayedLeashHolderId = pLeashHolderID;
        } else {
            Entity setTo = this.level.getEntity(pLeashHolderID);
            if (setTo instanceof LeashKnotEntity || setTo == null) {
                if (this.level.getEntity(this.delayedLeashHolderId) instanceof PlayerEntity || (this.level.getEntity(this.delayedLeashHolderId) == null && this.delayedLeashHolderId == 0)) {
                    this.delayedLeashHolderId = pLeashHolderID;
                } else if (this.level.getEntity(this.delayedLeashHolderId) instanceof LeashKnotEntity || (this.level.getEntity(this.delayedLeashHolderId) == null && this.delayedLeashHolderId != 0)) {
                    this.delayedLeashHolderId2 = pLeashHolderID;
                }
            } else if (setTo instanceof PlayerEntity) {
                if (this.delayedLeashHolderId == 0) {
                    this.delayedLeashHolderId = pLeashHolderID;
                } else if (this.level.getEntity(this.delayedLeashHolderId) instanceof LeashKnotEntity) {
                    this.delayedLeashHolderId2 = pLeashHolderID;
                }
            }
        }
        this.dropLeash(false, false);
    }

    @Override
    public void restoreLeashFromSave() {
        if (this.leashInfoTag != null && this.level instanceof ServerWorld) {
            if (this.leashInfoTag.hasUUID("UUID")) {
                UUID uuid = this.leashInfoTag.getUUID("UUID");
                Entity entity = ((ServerWorld) this.level).getEntity(uuid);
                if (entity != null) {
                    this.setLeashedTo(entity, true);
                    return;
                }
            } else if (this.leashInfoTag.contains("X", 99) && this.leashInfoTag.contains("Y", 99) && this.leashInfoTag.contains("Z", 99)) {
                BlockPos blockpos = new BlockPos(this.leashInfoTag.getInt("X"), this.leashInfoTag.getInt("Y"), this.leashInfoTag.getInt("Z"));
                this.setLeashedTo(LeashKnotEntity.getOrCreateKnot(this.level, blockpos), true);
                return;
            }

            if (this.tickCount > 100) {
                this.spawnAtLocation(Items.LEAD);
                this.leashInfoTag = null;
            }
        }
    }

    public void restoreLeash2FromSave() {
        if (this.leashInfoTag2 != null && this.level instanceof ServerWorld) {
            if (this.leashInfoTag2.hasUUID("UUID")) {
                UUID uuid = this.leashInfoTag2.getUUID("UUID");
                Entity entity = ((ServerWorld) this.level).getEntity(uuid);
                if (entity != null) {
                    this.setLeashedTo(entity, true);
                    return;
                }
            } else if (this.leashInfoTag2.contains("X", 99) && this.leashInfoTag2.contains("Y", 99) && this.leashInfoTag2.contains("Z", 99)) {
                BlockPos blockpos = new BlockPos(this.leashInfoTag2.getInt("X"), this.leashInfoTag2.getInt("Y"), this.leashInfoTag2.getInt("Z"));
                this.setLeashedTo(LeashKnotEntity.getOrCreateKnot(this.level, blockpos), true);
                return;
            }

            if (this.tickCount > 100) {
                this.spawnAtLocation(Items.LEAD);
                this.leashInfoTag2 = null;
            }
        }
    }

    /**
     * Set the owner uuid.
     *
     * @param uniqueId The uuid to be set as the owner.
     */
    @Override
    public void setOwnerUUID(@Nullable UUID uniqueId) {
        super.setOwnerUUID(uniqueId);
        if (uniqueId != null) {
            PlayerEntity player = this.level.getPlayerByUUID(uniqueId);
            if (player != null) {
                this.entityData.set(OWNER_NAME, player.getGameProfile().getName());
            }
        }
    }

    /**
     * Checks if the horse is jumping based on the animation timer, to block a jump before the
     * "animation" is done.
     *
     * @return boolean Returns weather or not the horse is "seen" as jumping.
     */
    @Override
    public boolean isJumping() {
        int timer;

        timer = this.getEntityData().get(JUMP_ANIM_TIMER);

        return this.jumpHeight != 0 || timer > 0;
    }

    /**
     * Checks if horse can be mounted by the player.
     *
     * @param player The player to check if can be mounted.
     * @return boolean Returns wether this horse can be mounted by this player.
     */
    public boolean canMountPlayer(PlayerEntity player) {
        if (this.isStanding() && this.getLastDamageSource() != DamageSource.IN_FIRE && this.getLastDamageSource() != DamageSource.LAVA && this.getLastDamageSource() != DamageSource.DROWN && this.getLastDamageSource() != DamageSource.ON_FIRE && this.getLastDamageSource() != DamageSource.HOT_FLOOR)
            return false;
        if (!this.isTamed()) return true;
        return canAccessHorse(player);
    }

    /**
     * Checks if the player has access to the horse.
     *
     * @param player The player to check permissions for.
     * @return boolean Returns whether or not the player has permission to access the horse.
     */
    public boolean canAccessHorse(PlayerEntity player) {
        if (this.getPermissionState() == RidingPermission.NONE) {
            return player.getUUID().equals(this.getOwnerUUID());
        } else if (this.getPermissionState() == RidingPermission.TRUST) {
            return this.isAllowedUUID(player.getUUID()) || player.getUUID().equals(this.getOwnerUUID());
        } else {
            return true;
        }
    }

    /**
     * Is the horse ready to be saddled.
     *
     * @param player The player to check if they can saddle horse.
     * @return boolean If the player can saddle the horse.
     */
    public boolean isSaddleable(PlayerEntity player) {
        return this.isAlive() && !this.isBaby() && this.isTamed() && this.canAccessHorse(player);
    }

    public void equipSaddle(@Nullable SoundCategory p_230266_1_, ItemStack stackIn, PlayerEntity player) {
        ItemStack stack = stackIn.copy();
        stack.setCount(1);
        boolean flag = player.isSecondaryUseActive();
        if (stack.getItem() instanceof HorseSaddleItem) {
            if (flag) {
                player.addItem(this.inventory.getItem(2));
            }
            this.inventory.setItem(2, stack);
            SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 2, stack));
            if (p_230266_1_ != null) {
                this.level.playSound(null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
            }
        } else if (stack.getItem() instanceof BlanketItem) {
            if (flag) {
                player.addItem(this.inventory.getItem(1));
            }
            this.inventory.setItem(1, stack);
            SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 1, stack));
            if (p_230266_1_ != null) {
                this.level.playSound(null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
            }
        } else if (stack.getItem() instanceof BreastCollarItem) {
            if (flag) {
                player.addItem(this.inventory.getItem(3));
            }
            this.inventory.setItem(3, stack);
            SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 3, stack));
            if (p_230266_1_ != null) {
                this.level.playSound(null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
            }
        } else if (stack.getItem() instanceof HalterItem) {
            if (flag) {
                player.addItem(this.inventory.getItem(0));
            }
            this.inventory.setItem(0, stack);
            SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 0, stack));
            if (p_230266_1_ != null) {
                this.level.playSound(null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
            }
        } else if (stack.getItem() instanceof GirthStrapItem) {
            if (flag) {
                player.addItem(this.inventory.getItem(5));
            }
            this.inventory.setItem(5, stack);
            SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 5, stack));
            if (p_230266_1_ != null) {
                this.level.playSound(null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
            }
        } else if (stack.getItem() instanceof LegWrapsItem) {
            if (flag) {
                player.addItem(this.inventory.getItem(4));
            }
            this.inventory.setItem(4, stack);
            SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 4, stack));
            if (p_230266_1_ != null) {
                this.level.playSound(null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
            }
        } else if (stack.getItem() instanceof SWEMHorseArmorItem) {
            if (this.isWearingArmor() && !flag) return;
            if (flag) {
                player.addItem(this.inventory.getItem(6));
            }
            this.inventory.setItem(6, stack);
            SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 6, stack));
            if (p_230266_1_ != null) {
                this.level.playSound(null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
            }
        } else if (stack.getItem() instanceof SaddlebagItem) {
            if (this.hasSaddleBag() && !flag) return;
            if (flag) {
                player.addItem(this.inventory.getItem(6));
            }
            this.inventory.setItem(7, stack);
            SWEMPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateHorseInventoryMessage(this.getId(), 7, stack));
            if (p_230266_1_ != null) {
                this.level.playSound(null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
            }
        }
    }

    @Override
    public void makeMad() {
        if (!this.isStanding()) {
            this.setStanding(true);
            this.setStandingAnim();
            SoundEvent soundevent = this.getAngrySound();
            if (soundevent != null) {
                this.playSound(soundevent, this.getSoundVolume(), this.getVoicePitch());
            }
        }
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    @Override
    public double getPassengersRidingOffset() {
        double def = super.getDimensions(this.getPose()).height * 0.75D;
        def += 0.3D;
        return def;
    }

    @Override
    public void positionRider(Entity entity) {
        if (!this.hasPassenger(entity)) {
            return;
        }

        float xzOffset = -0.1f;

        if (this.getPassengers().size() > 1) {
            int i = this.getPassengers().indexOf(entity);
            xzOffset = i == 0 ? 0.1f : -0.5f;
        } else if (this.getPassengers().size() > 0 && !(this.getPassengers().get(0) instanceof PlayerEntity)) {
            xzOffset = -0.5f;
        }

        double yOffset = entity.getMyRidingOffset() + this.getPassengersRidingOffset();

        Vector3d vec3 = new Vector3d(xzOffset, 0, 0).yRot(-this.yBodyRot * ((float) Math.PI / 180f) - ((float) Math.PI / 2F));
        entity.setPos(this.getX() + vec3.x, this.getY() + yOffset, this.getZ() + vec3.z);
        this.applyYaw(entity);
        if (entity instanceof AnimalEntity) {
            int degrees = entity.getId() % 2 == 0 ? 90 : 270;
            entity.setYBodyRot(((AnimalEntity) entity).yBodyRot + (float) degrees);
            entity.setYHeadRot(entity.getYHeadRot() + (float) degrees);
        }
    }

    /**
     * Apply yaw.
     *
     * @param entity the entity
     */
    private void applyYaw(Entity entity) {
        if (!(entity instanceof PlayerEntity)) {
            entity.setYBodyRot(this.yBodyRot);
            entity.yRot = this.yBodyRot;
            entity.setYHeadRot(this.yBodyRot);
        }
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        if (!passenger.getType().getCategory().isFriendly() && passenger.getType() != EntityType.PLAYER) return false;

        if (passenger instanceof WaterMobEntity) return false;

        return this.getPassengers().size() < 2;
    }

    /**
     * Is flying boolean.
     *
     * @return the boolean
     */
    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    /**
     * Sets flying.
     *
     * @param flying the flying
     */
    public void setFlying(boolean flying) {
        this.entityData.set(FLYING, flying);
        if (flying) {
            this.flightController.launchFlight();
        } else {
            this.flightController.land();
        }
    }

    @Override
    public int getMaxFallDistance() {
        return 2;
    }

    @Override
    protected void doPlayerRide(PlayerEntity player) {
        this.setEating(false);
        this.setStanding(false);
        if (!this.level.isClientSide) {
            player.yRot = this.yRot - 0.2F;
            player.xRot = this.xRot;
            player.startRiding(this);
        }
        HorseSpeed oldSpeed = this.currentSpeed;
        this.currentSpeed = HorseSpeed.WALK;
        this.updateSelectedSpeed(oldSpeed);
    }

    /**
     * Sets gallop cooldown.
     */
    private void setGallopCooldown() {
        int gallopSoundCounter = this.entityData.get(GALLOP_TIMER);
        int cooldown = gallopSoundCounter * 5;
        this.entityData.set(GALLOP_COOLDOWN_TIMER, cooldown);
        this.entityData.set(GALLOP_ON_COOLDOWN, true);
        this.entityData.set(GALLOP_TIMER, 0);
    }

    /**
     * Reset gallop cooldown.
     */
    public void resetGallopCooldown() {
        this.entityData.set(GALLOP_COOLDOWN_TIMER, 0);
        this.entityData.set(GALLOP_ON_COOLDOWN, false);
        this.entityData.set(GALLOP_TIMER, 0);
    }

    public boolean isHorseSaddled() {
        return this.getFlag(4);
    }

    @Override
    public boolean hasAdventureSaddle() {
        return this.hasSaddle().getItem() instanceof AdventureSaddleItem;
    }

    @Override
    public boolean hasBlanket() {
        return this.inventory.getItem(1).getItem() instanceof BlanketItem;
    }

    @Override
    public boolean hasBreastCollar() {
        return this.inventory.getItem(3).getItem() instanceof BreastCollarItem;
    }

    @Override
    public boolean hasHalter() {
        if (ConfigHolder.SERVER.halterDependency.get()) {
            return this.inventory.getItem(0).getItem() instanceof HalterItem;
        } else {
            return true;
        }
    }

    @Override
    public boolean hasGirthStrap() {
        return this.inventory.getItem(5).getItem() instanceof GirthStrapItem;
    }

    @Override
    public boolean hasLegWraps() {
        return this.inventory.getItem(4).getItem() instanceof LegWrapsItem;
    }

    /**
     * Has saddle item stack.
     *
     * @return the item stack
     */
    public ItemStack hasSaddle() {
        return this.inventory.getItem(2);
    }

    @Override
    protected void createInventory() {
        Inventory inventory = this.inventory;
        this.inventory = new Inventory(this.getInventorySize());
        if (inventory != null) {
            inventory.removeListener(this);
            int i = Math.min(inventory.getContainerSize(), this.inventory.getContainerSize());

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setItem(j, itemstack.copy());
                }
            }
        }

        this.inventory.addListener(this::onHorseInventoryChanged);
        this.updateContainerEquipment();
        this.itemHandler = LazyOptional.of(() -> new InvWrapper(this.inventory));
    }

    /**
     * Gets horse inventory.
     *
     * @return the horse inventory
     */
    public Inventory getHorseInventory() {
        return this.inventory;
    }

    /**
     * Init saddlebag inventory.
     */
    protected void initSaddlebagInventory() {
        Inventory inventory = this.saddlebagInventory;
        this.saddlebagInventory = new Inventory(31);
        if (inventory != null) {
            inventory.removeListener(this);
            int i = Math.min(inventory.getContainerSize(), this.saddlebagInventory.getContainerSize());

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.saddlebagInventory.setItem(j, itemstack.copy());
                }
            }
        }

        this.saddlebagInventory.addListener(this);
        this.saddlebagItemHandler = LazyOptional.of(() -> new InvWrapper(this.saddlebagInventory));
    }

    /**
     * Gets saddlebag inventory.
     *
     * @return the saddlebag inventory
     */
    public Inventory getSaddlebagInventory() {
        return this.saddlebagInventory;
    }

    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        if (!this.inventory.getItem(0).isEmpty()) {
            compound.put("BridleItem", this.inventory.getItem(0).save(new CompoundNBT()));
        }
        if (!this.inventory.getItem(1).isEmpty()) {
            compound.put("BlanketItem", this.inventory.getItem(1).save(new CompoundNBT()));
        }
        if (!this.inventory.getItem(2).isEmpty()) {
            compound.put("SaddleItem", this.inventory.getItem(2).save(new CompoundNBT()));
        }
        if (!this.inventory.getItem(3).isEmpty()) {
            compound.put("BreastCollarItem", this.inventory.getItem(3).save(new CompoundNBT()));
        }
        if (!this.inventory.getItem(4).isEmpty()) {
            compound.put("LegWrapsItem", this.inventory.getItem(4).save(new CompoundNBT()));
        }
        if (!this.inventory.getItem(5).isEmpty()) {
            compound.put("GirthStrapItem", this.inventory.getItem(5).save(new CompoundNBT()));
        }
        if (!this.inventory.getItem(6).isEmpty()) {
            compound.put("SWEMArmorItem", this.inventory.getItem(6).save(new CompoundNBT()));
        }
        if (!this.inventory.getItem(7).isEmpty()) {
            compound.put("SaddlebagItem", this.inventory.getItem(7).save(new CompoundNBT()));
        }

        if (this.leashHolder2 != null) {
            CompoundNBT compoundnbt2 = new CompoundNBT();
            if (this.leashHolder2 instanceof LivingEntity) {
                UUID uuid = this.leashHolder2.getUUID();
                compoundnbt2.putUUID("UUID", uuid);
            } else if (this.leashHolder2 instanceof HangingEntity) {
                BlockPos blockpos = ((HangingEntity) this.leashHolder2).getPos();
                compoundnbt2.putInt("X", blockpos.getX());
                compoundnbt2.putInt("Y", blockpos.getY());
                compoundnbt2.putInt("Z", blockpos.getZ());
            }

            compound.put("Leash2", compoundnbt2);
        } else if (this.leashInfoTag2 != null) {
            compound.put("Leash2", this.leashInfoTag2.copy());
        }

        this.writeSaddlebagInventory(compound);

        this.progressionManager.write(compound);

        this.needs.write(compound);

        compound.putBoolean("flying", this.isFlying());

        compound.putInt("HorseVariant", this.getHorseVariant());

        compound.putString("ownerName", this.getOwnerName());

        CompoundNBT allowedList = new CompoundNBT();
        for (int i = 0; i < this.allowedList.size(); i++) {
            allowedList.putUUID(Integer.toString(i), this.allowedList.get(i));
        }

        compound.put("allowedList", allowedList);
        compound.putString("permissionState", RidingPermission.valueOf(this.entityData.get(PERMISSION_STRING)).name());
        compound.putBoolean("tracked", this.entityData.get(TRACKED));
    }

    /**
     * Gets armor.
     *
     * @return the armor
     */
    public ItemStack getArmor() {
        return this.getItemBySlot(EquipmentSlotType.CHEST);
    }

    /**
     * Sets armor.
     *
     * @param p_213805_1_ the p 213805 1
     */
    private void setArmor(ItemStack p_213805_1_) {
        this.setItemSlot(EquipmentSlotType.CHEST, p_213805_1_);
        this.setDropChance(EquipmentSlotType.CHEST, 0.0F);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("BridleItem", 10)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound("BridleItem"));
            if (!itemstack.isEmpty() && this.isHalter(itemstack)) {
                this.inventory.setItem(0, itemstack);
            }
        }
        if (compound.contains("BlanketItem", 10)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound("BlanketItem"));
            if (!itemstack.isEmpty() && this.isBlanket(itemstack)) {
                this.inventory.setItem(1, itemstack);
            }
        }
        if (compound.contains("SaddleItem", 10)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound("SaddleItem"));
            if (!itemstack.isEmpty() && this.isSaddle(itemstack)) {
                this.inventory.setItem(2, itemstack);
            }
        }
        if (compound.contains("BreastCollarItem", 10)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound("BreastCollarItem"));
            if (!itemstack.isEmpty() && this.isBreastCollar(itemstack)) {
                this.inventory.setItem(3, itemstack);
            }
        }
        if (compound.contains("LegWrapsItem", 10)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound("LegWrapsItem"));
            if (!itemstack.isEmpty() && this.isLegWraps(itemstack)) {
                this.inventory.setItem(4, itemstack);
            }
        }
        if (compound.contains("GirthStrapItem", 10)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound("GirthStrapItem"));
            if (!itemstack.isEmpty() && this.isGirthStrap(itemstack)) {
                this.inventory.setItem(5, itemstack);
            }
        }
        if (compound.contains("SWEMArmorItem", 10)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound("SWEMArmorItem"));
            if (!itemstack.isEmpty() && this.isSWEMArmor(itemstack)) {
                this.inventory.setItem(6, itemstack);
            }
        }
        if (compound.contains("SaddlebagItem", 10)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound("SaddlebagItem"));
            if (!itemstack.isEmpty() && this.isSaddlebag(itemstack)) {
                this.inventory.setItem(7, itemstack);
            }
        }

        if (compound.contains("Leash2", 10)) {
            this.leashInfoTag2 = compound.getCompound("Leash2");
        }

        this.readSaddlebagInventory(compound);

        this.progressionManager.read(compound);

        this.needs.read(compound);

        this.updateContainerEquipment();

        // this.setFlying(compound.getBoolean("flying"));
        if (compound.contains("HorseVariant")) {
            this.setCoatColour(SWEMCoatColor.getById(compound.getInt("HorseVariant")));
        } else {
            if (this.isBaby()) {
                this.setCoatColour(SWEMCoatColor.getRandomFoalCoat());
            } else {
                this.setCoatColour(SWEMCoatColor.getRandomLapisObtainableCoat());
            }
        }

        this.setOwnerName(compound.getString("ownerName"));

        if (compound.contains("allowedList")) {
            CompoundNBT allowList = compound.getCompound("allowedList");
            for (int i = 0; i < allowList.size(); i++) {
                this.addAllowedUUID(allowList.getUUID(Integer.toString(i)));
            }
        }

        if (compound.contains("permissionState")) {
            this.setPermissionState(compound.getString("permissionState"));
        }

        if (compound.contains("tracked")) {
            this.setTracked(compound.getBoolean("tracked"));
        }
    }

    /**
     * Add allowed uuid.
     *
     * @param playerUUID the player uuid
     */
    public void addAllowedUUID(UUID playerUUID) {
        if (!this.allowedList.contains(playerUUID)) {
            this.allowedList.add(playerUUID);
        }
    }

    /**
     * Is allowed uuid boolean.
     *
     * @param playerUUID the player uuid
     * @return the boolean
     */
    public boolean isAllowedUUID(UUID playerUUID) {
        return this.allowedList.contains(playerUUID);
    }

    /**
     * Remove allowed uuid.
     *
     * @param playerUUID the player uuid
     */
    public void removeAllowedUUID(UUID playerUUID) {
        if (this.getOwnerUUID() != null && this.getOwnerUUID().equals(playerUUID)) return;
        this.allowedList.remove(playerUUID);
    }

    /**
     * Transfer horse.
     *
     * @param player the player
     */
    public void transferHorse(PlayerEntity player) {
        this.tameWithName(player);
        this.removeAllAllowedUUIDs();
    }

    /**
     * Remove all allowed UUID's.
     */
    public void removeAllAllowedUUIDs() {
        for (UUID allowed : this.allowedList) {
            this.removeAllowedUUID(allowed);
        }
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    @Override
    public boolean isPushable() {
        return super.isPushable();
    }

    @Override
    public boolean updateFluidHeightAndDoFluidPushing(ITag<Fluid> pFluidTag, double pMotionScale) {
        AxisAlignedBB axisalignedbb = this.getBoundingBox().deflate(0.001D).contract(0, -1, 0);
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.ceil(axisalignedbb.maxX);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.ceil(axisalignedbb.maxY);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.ceil(axisalignedbb.maxZ);
        if (!this.level.hasChunksAt(i, k, i1, j, l, j1)) {
            return false;
        } else {
            double d0 = 0.0D;
            boolean flag = this.isPushedByFluid();
            boolean flag1 = false;
            Vector3d vector3d = Vector3d.ZERO;
            int k1 = 0;
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

            for (int l1 = i; l1 < j; ++l1) {
                for (int i2 = k; i2 < l; ++i2) {
                    for (int j2 = i1; j2 < j1; ++j2) {
                        blockpos$mutable.set(l1, i2, j2);
                        FluidState fluidstate = this.level.getFluidState(blockpos$mutable);
                        if (fluidstate.is(pFluidTag)) {
                            double d1 = (float) i2 + fluidstate.getHeight(this.level, blockpos$mutable);
                            if (d1 >= axisalignedbb.minY) {
                                flag1 = true;
                                d0 = Math.max(d1 - axisalignedbb.minY, d0);
                                if (flag) {
                                    Vector3d vector3d1 = fluidstate.getFlow(this.level, blockpos$mutable);
                                    if (d0 < 0.4D) {
                                        vector3d1 = vector3d1.scale(d0);
                                    }

                                    vector3d = vector3d.add(vector3d1);
                                    ++k1;
                                }
                            }
                        }
                    }
                }
            }

            if (vector3d.length() > 0.0D) {
                if (k1 > 0) {
                    vector3d = vector3d.scale(1.0D / (double) k1);
                }

                vector3d = vector3d.normalize();

                Vector3d vector3d2 = this.getDeltaMovement();
                vector3d = vector3d.scale(pMotionScale);
                double d2 = 0.003D;
                if (Math.abs(vector3d2.x) < 0.003D && Math.abs(vector3d2.z) < 0.003D && vector3d.length() < 0.0045000000000000005D) {
                    vector3d = vector3d.normalize().scale(0.0045000000000000005D);
                }

                this.setDeltaMovement(this.getDeltaMovement().add(vector3d));
            }

            this.fluidHeight.put(pFluidTag, d0);
            return flag1;
        }
    }

    /**
     * Write saddlebag inventory.
     *
     * @param compound the compound
     */
    private void writeSaddlebagInventory(CompoundNBT compound) {

        if (!this.saddlebagInventory.isEmpty()) {
            CompoundNBT saddlebag = new CompoundNBT();
            for (int i = 0; i < this.saddlebagInventory.getContainerSize(); i++) {
                saddlebag.put(Integer.toString(i), this.saddlebagInventory.getItem(i).save(new CompoundNBT()));
            }
            compound.put("saddlebag", saddlebag);
        }
    }

    /**
     * Read saddlebag inventory.
     *
     * @param compound the compound
     */
    private void readSaddlebagInventory(CompoundNBT compound) {
        if (compound.contains("saddlebag")) {
            CompoundNBT saddlebag = compound.getCompound("saddlebag");

            for (int i = 0; i < this.saddlebagInventory.getContainerSize(); i++) {
                if (saddlebag.contains(Integer.toString(i))) {
                    CompoundNBT stackNBT = (CompoundNBT) saddlebag.get(Integer.toString(i));
                    if (stackNBT != null) {
                        this.saddlebagInventory.setItem(i, ItemStack.of(stackNBT));
                    }
                }
            }
        }
    }

    /**
     * Gets coat color.
     *
     * @return the coat color
     */
    public SWEMCoatColor getCoatColor() {
        return SWEMCoatColor.getById(this.getHorseVariant());
    }

    /**
     * Gets horse variant.
     *
     * @return the horse variant
     */
    private int getHorseVariant() {
        return this.entityData.get(HORSE_VARIANT);
    }

    /**
     * Sets horse variant.
     *
     * @param id the id
     */
    private void setHorseVariant(int id) {
        this.entityData.set(HORSE_VARIANT, id);
    }

    /**
     * Calculate potion coat.
     *
     * @param vanillaCoat the vanilla coat
     */
    public void calculatePotionCoat(CoatColors vanillaCoat) {
        int randomNum = rand.nextInt(100) + 1;
        switch (vanillaCoat) {
            case BLACK: {
                if (randomNum < 20) this.setCoatColour(SWEMCoatColor.BLACK);
                else if (randomNum <= 40) this.setCoatColour(SWEMCoatColor.TALLDARKHANDSOME);
                else if (randomNum <= 51) this.setCoatColour(SWEMCoatColor.SWEETBOI);
                else if (randomNum <= 66) this.setCoatColour(SWEMCoatColor.LEOPARD);
                else if (randomNum <= 76) this.setCoatColour(SWEMCoatColor.VALEGRO);
                else if (randomNum <= 86) this.setCoatColour(SWEMCoatColor.SHWOOMPL_MARKIPLIER);
                else if (randomNum <= 91) this.setCoatColour(SWEMCoatColor.LUNAR_ARISHANT);
                else this.setCoatColour(SWEMCoatColor.FRANK_STEVECV);
                break;
            }
            case GRAY: {
                if (randomNum < 7) this.setCoatColour(SWEMCoatColor.WHITE);
                else if (randomNum <= 32) this.setCoatColour(SWEMCoatColor.GRAY);
                else if (randomNum <= 39) this.setCoatColour(SWEMCoatColor.TALLDARKHANDSOME);
                else if (randomNum <= 49) this.setCoatColour(SWEMCoatColor.SWEETBOI);
                else if (randomNum <= 59) this.setCoatColour(SWEMCoatColor.VALEGRO);
                else if (randomNum <= 66) this.setCoatColour(SWEMCoatColor.SHWOOMPL_MARKIPLIER);
                else if (randomNum <= 76) this.setCoatColour(SWEMCoatColor.FINBAR_FOALEY_JACKSEPTICEYE);
                else if (randomNum <= 86) this.setCoatColour(SWEMCoatColor.JOEY_THIS_ESME);
                else if (randomNum <= 93) this.setCoatColour(SWEMCoatColor.GOOSEBERRY_JUSTPEACHY);
                else this.setCoatColour(SWEMCoatColor.TOOTHBRUSH_BOATY);
                break;
            }
            case WHITE: {
                if (randomNum < 25) this.setCoatColour(SWEMCoatColor.WHITE);
                else if (randomNum <= 34) this.setCoatColour(SWEMCoatColor.GRAY);
                else if (randomNum <= 43) this.setCoatColour(SWEMCoatColor.ROAN);
                else if (randomNum <= 54) this.setCoatColour(SWEMCoatColor.PAINT);
                else if (randomNum <= 61) this.setCoatColour(SWEMCoatColor.SWEETBOI);
                else if (randomNum <= 70) this.setCoatColour(SWEMCoatColor.LEOPARD);
                else if (randomNum <= 77) this.setCoatColour(SWEMCoatColor.ARIELS_MALLI);
                else if (randomNum <= 84) this.setCoatColour(SWEMCoatColor.NERO_STARDUST);
                else if (randomNum <= 93) this.setCoatColour(SWEMCoatColor.FRANK_STEVECV);
                else this.setCoatColour(SWEMCoatColor.TOOTHBRUSH_BOATY);
                break;
            }
            case CHESTNUT: {
                if (randomNum < 25) this.setCoatColour(SWEMCoatColor.CHESTNUT);
                else if (randomNum <= 32) this.setCoatColour(SWEMCoatColor.ROAN);
                else if (randomNum <= 39) this.setCoatColour(SWEMCoatColor.NOBUCKLE);
                else if (randomNum <= 50) this.setCoatColour(SWEMCoatColor.WILDANDFREE);
                else if (randomNum <= 55) this.setCoatColour(SWEMCoatColor.MAN_O_WAR);
                else if (randomNum <= 60) this.setCoatColour(SWEMCoatColor.SECRETARIAT);
                else if (randomNum <= 65) this.setCoatColour(SWEMCoatColor.SERGEANT_RECKLESS);
                else if (randomNum <= 75) this.setCoatColour(SWEMCoatColor.DOLLAR_JOHN_WAYNE);
                else if (randomNum <= 85) this.setCoatColour(SWEMCoatColor.ARIELS_MALLI);
                else if (randomNum <= 95) this.setCoatColour(SWEMCoatColor.EL_CAZADOR_MALLI);
                else this.setCoatColour(SWEMCoatColor.ANNIE_LACE);
                break;
            }
            case CREAMY: {
                if (randomNum < 25) this.setCoatColour(SWEMCoatColor.BUCKSKIN);
                else if (randomNum <= 40) this.setCoatColour(SWEMCoatColor.PALOMINO);
                else if (randomNum <= 55) this.setCoatColour(SWEMCoatColor.GOLDEN);
                else if (randomNum <= 66) this.setCoatColour(SWEMCoatColor.TRIGGER_ROY_ROGERS);
                else if (randomNum <= 76) this.setCoatColour(SWEMCoatColor.MR_ED);
                else if (randomNum <= 88) this.setCoatColour(SWEMCoatColor.NERO_STARDUST);
                else this.setCoatColour(SWEMCoatColor.KODIAK_DELPHI);
                break;
            }
            case BROWN: {
                if (randomNum < 20) this.setCoatColour(SWEMCoatColor.BROWN);
                else if (randomNum <= 30) this.setCoatColour(SWEMCoatColor.BUCKSKIN);
                else if (randomNum <= 45) this.setCoatColour(SWEMCoatColor.PAINT);
                else if (randomNum <= 55) this.setCoatColour(SWEMCoatColor.NOBUCKLE);
                else if (randomNum <= 65) this.setCoatColour(SWEMCoatColor.WILDANDFREE);
                else if (randomNum <= 75) this.setCoatColour(SWEMCoatColor.APPY);
                else if (randomNum <= 82) this.setCoatColour(SWEMCoatColor.ARIELS_MALLI);
                else if (randomNum <= 83) this.setCoatColour(SWEMCoatColor.FRANK_STEVECV);
                else this.setCoatColour(SWEMCoatColor.KODIAK_DELPHI);
                break;
            }
            case DARKBROWN: {
                if (randomNum < 20) this.setCoatColour(SWEMCoatColor.BROWN);
                else if (randomNum <= 35) this.setCoatColour(SWEMCoatColor.PAINT);
                else if (randomNum <= 45) this.setCoatColour(SWEMCoatColor.WILDANDFREE);
                else if (randomNum <= 55) this.setCoatColour(SWEMCoatColor.TALLDARKHANDSOME);
                else if (randomNum <= 65) this.setCoatColour(SWEMCoatColor.APPY);
                else if (randomNum <= 72) this.setCoatColour(SWEMCoatColor.VALEGRO);
                else if (randomNum <= 79) this.setCoatColour(SWEMCoatColor.JOERGEN_PEWDIEPIE);
                else if (randomNum <= 89) this.setCoatColour(SWEMCoatColor.EL_CAZADOR_MALLI);
                else this.setCoatColour(SWEMCoatColor.FRANK_STEVECV);
                break;
            }
        }
    }

    /**
     * Sets coat colour.
     *
     * @param coat the coat
     */
    public void setCoatColour(SWEMCoatColor coat) {
        this.setHorseVariant(coat.getId());
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        this.progressionManager.getAffinityLeveling().addXP(100.0f);
    }

    @Override
    protected void updateContainerEquipment() {
        if (!this.level.isClientSide) {
            this.setArmorEquipment(this.getSWEMArmor());
            this.setFlag(4, !this.inventory.getItem(2).isEmpty());
        }
    }

    /**
     * Sets armor equipment.
     *
     * @param p_213804_1_ the p 213804 1
     */
    private void setArmorEquipment(ItemStack p_213804_1_) {
        this.setArmor(p_213804_1_);
        if (!this.level.isClientSide) {
            ModifiableAttributeInstance armorAttribute = this.getAttribute(Attributes.ARMOR);
            if (armorAttribute == null) return;
            armorAttribute.removeModifier(ARMOR_MODIFIER_UUID);
            if (this.isSWEMArmor(p_213804_1_)) {
                int i = ((HorseArmorItem) p_213804_1_.getItem()).getProtection();
                if (i != 0) {
                    armorAttribute.addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", i, AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        if (this.isFlying()) {
            this.flightController.travel();
            return;
        }
        if (!this.level.isClientSide) {
            if (this.tickCount % 5 == 0) {

                if (this.canBeControlledByRider() && this.isVehicle()) {
                    int x = this.blockPosition().getX();
                    int z = this.blockPosition().getZ();
                    if (x != this.currentPos.getX() || z != this.currentPos.getZ()) {
                        x = Math.abs(x - this.currentPos.getX());
                        z = Math.abs(z - this.currentPos.getZ());
                        int dist = ((int) Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)));
                        if (dist > 0 && dist < 25) {
                            boolean speedLevelUp = false;
                            speedLevelUp = this.progressionManager.getSpeedLeveling().addXP(dist * this.currentSpeed.getSkillMultiplier());

                            if (speedLevelUp) {
                                this.levelUpSpeed();
                            }
                            // Affinity leveling, is not affected by speed. so no matter the speed, just add 1xp
                            // per block.
                            this.progressionManager.getAffinityLeveling().addXP(dist * this.currentSpeed.getSkillMultiplier());
                        }
                    }
                }

                // Kick off rider, if no girth strap is equipped.
                if (this.isSWEMSaddled() && (!this.hasGirthStrap() || !ConfigHolder.SERVER.riderNeedsGirthStrap.get()) && this.isVehicle()) {
                    if (this.tickCount % 20 == 0) {
                        int rand = this.getRandom().nextInt(5);
                        if (rand == 0) {
                            Entity rider = this.getPassengers().get(0);
                            rider.stopRiding();
                            ItemStack saddle = this.hasSaddle();
                            this.inventory.setItem(2, ItemStack.EMPTY);
                            this.setSWEMSaddled();
                            this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), saddle));
                        }
                    }
                }
                this.currentPos = this.blockPosition();
            }

            if (!this.inventory.getItem(6).isEmpty()) {
                this.checkArmorPiece(((SWEMHorseArmorItem) this.inventory.getItem(6).getItem()));
            }

        } else {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.getVehicle() != null && Minecraft.getInstance().player.getVehicle().getUUID().equals(this.getUUID())) {

                if (ClientEventHandlers.keyBindings[7].isDown() && this.isCameraLocked() && this.getPassengers().get(0) == Minecraft.getInstance().player) {
                    SWEMPacketHandler.INSTANCE.sendToServer(new CCameraLockPacket(this.getUUID(), false));
                    this.setLockedRotations(this.xRot, this.yRot);

                } else if (!ClientEventHandlers.keyBindings[7].isDown() && !this.isCameraLocked() && this.getPassengers().get(0) == Minecraft.getInstance().player) {
                    SWEMPacketHandler.INSTANCE.sendToServer(new CCameraLockPacket(this.getUUID(), true));
                }
            }
        }
        super.tick();
        if (this.isInWater() && !this.isVehicle()) {
            if (this.wasEyeInWater) {
                this.setDeltaMovement(this.getDeltaMovement().x, .05, this.getDeltaMovement().z); // Set the motion on y with a positive force, because the horse is floating to
                // the top, pull it down, until wasEyeInWater returns true.
            } else {
                this.setDeltaMovement(this.getDeltaMovement().x, -.05, this.getDeltaMovement().z); // Set the motion on y with a negative force, because the horse is floating to
                // the top, pull it down, until wasEyeInWater returns false.
            }

        } else if (this.isInLava() && !this.isEyeInFluid(FluidTags.LAVA) && !this.isVehicle()) {
            this.setDeltaMovement(this.getDeltaMovement().x, -.5, this.getDeltaMovement().z); // Set the motion on y with a negative force, because the horse is floating to
            // the top, pull it down, until wasEyeInWater returns true.
        }
    }

    /**
     * Check armor piece.
     *
     * @param armor the armor
     */
    private void checkArmorPiece(SWEMHorseArmorItem armor) {
        if (armor.tier.getId() >= 0) this.tickClothArmor();
        if (armor.tier.getId() >= 1) this.tickIronArmor();
        if (armor.tier.getId() >= 2) this.tickGoldArmor();
        if (armor.tier.getId() >= 3) this.tickDiamondArmor();
        if (armor.tier.getId() == 4) this.tickAmethystArmor();
    }

    /**
     * Tick cloth armor.
     */
    private void tickClothArmor() {
    }

    /**
     * Tick iron armor.
     */
    private void tickIronArmor() {
    }

    /**
     * Tick gold armor.
     */
    private void tickGoldArmor() {
        if (this.isOnGround()) {
            BlockState blockstate = Blocks.FROSTED_ICE.defaultBlockState();
            float f = (float) Math.min(16, 3);
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

            BlockPos pos = this.blockPosition();

            for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-f, -1.0D, -f), pos.offset(f, -1.0D, f))) {
                if (blockpos.closerThan(this.blockPosition(), f)) {
                    blockpos$mutable.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                    BlockState blockstate1 = level.getBlockState(blockpos$mutable);
                    if (blockstate1.getBlock().isAir(blockstate1, level, blockpos$mutable)) {
                        BlockState blockstate2 = level.getBlockState(blockpos);
                        boolean isFull = blockstate2.getBlock() == Blocks.WATER && blockstate2.getValue(FlowingFluidBlock.LEVEL) == 0; // TODO: Forge, modded waters?
                        if (((blockstate2.getMaterial() == Material.WATER && isFull) || blockstate2.getMaterial() == Material.WATER_PLANT) && blockstate.canSurvive(level, blockpos) && level.isUnobstructed(blockstate, blockpos, ISelectionContext.empty()) && !ForgeEventFactory.onBlockPlace(this, BlockSnapshot.create(level.dimension(), level, blockpos), Direction.UP)) {
                            level.setBlock(blockpos, blockstate, 3);
                            level.getBlockTicks().scheduleTick(blockpos, Blocks.FROSTED_ICE, 20);
                        }
                    }
                }
            }
        }
    }

    /**
     * Tick diamond armor.
     */
    private void tickDiamondArmor() {
        if (this.isOnGround()) {
            BlockState blockstate = SWEMBlocks.TEARING_MAGMA.get().defaultBlockState();
            float f = (float) Math.min(16, 3);
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

            BlockPos pos = this.blockPosition();

            for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-f, -1.0D, -f), pos.offset(f, -1.0D, f))) {
                if (blockpos.closerThan(this.blockPosition(), f)) {
                    blockpos$mutable.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                    BlockState blockstate1 = level.getBlockState(blockpos$mutable);
                    if (blockstate1.isAir(level, blockpos$mutable)) {
                        BlockState blockstate2 = level.getBlockState(blockpos);
                        boolean isFull = blockstate2.getBlock() == Blocks.LAVA && blockstate2.getValue(FlowingFluidBlock.LEVEL) == 0; // TODO: Forge, modded waters?
                        if (blockstate2.getMaterial() == Material.LAVA && isFull && blockstate.canSurvive(level, blockpos) && level.isUnobstructed(blockstate, blockpos, ISelectionContext.empty()) && !ForgeEventFactory.onBlockPlace(this, BlockSnapshot.create(level.dimension(), level, blockpos), Direction.UP)) {
                            level.setBlock(blockpos, blockstate, 3);
                            level.getBlockTicks().scheduleTick(blockpos, SWEMBlocks.TEARING_MAGMA.get(), 20);
                        }
                    }
                }
            }
        }

        this.clearFire();
    }

    /**
     * Tick amethyst armor.
     */
    private void tickAmethystArmor() {
        this.addEffect(new EffectInstance(Effects.SLOW_FALLING, 1, 10, false, false, false));
    }

    @Override
    public EntitySize getDimensions(Pose pPose) {
        if (this.isJumping() && !this.onGround) {
            return super.getDimensions(pPose).scale(1, 0.5f);
        }
        return super.getDimensions(pPose);
    }

  /*
  @Override
  public void refreshDimensions() {
  	if (this.isJumping() && !this.onGround) {
  		EntitySize entitysize = this.getDimensions(this.getPose());
  		Pose pose = this.getPose();
  		EntitySize entitysize1 = this.getDimensions(pose);
  		net.minecraftforge.event.entity.EntityEvent.Size sizeEvent = net.minecraftforge.event.ForgeEventFactory.getEntitySizeForge(this, pose, entitysize, entitysize1, this.getEyeHeight(pose, entitysize1));
  		entitysize1 = sizeEvent.getNewSize();
  		AxisAlignedBB axisalignedbb = this.getBoundingBox();
  		System.out.println("Before move: " + axisalignedbb);
  		axisalignedbb = axisalignedbb.move(0, 10, 0);
  		System.out.println("After move: " + axisalignedbb);
  		this.setBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + (double)entitysize1.width, axisalignedbb.minY + (double)entitysize1.height, axisalignedbb.minZ + (double)entitysize1.width));
  	} else {
  		super.refreshDimensions();
  	}
  }
   */

    @Override
    public void travel(Vector3d travelVector) {

        if (this.isFlying()) {
            return;
        } else {
            if (this.isStanding()) {
                this.setDeltaMovement(0, 0, 0);
                return;
            }

            if (this.isVehicle() && this.canBeControlledByRider() && this.isHorseSaddled()) {
                PlayerEntity livingentity = (PlayerEntity) this.getControllingPassenger();

                float sidewaysMovement = livingentity.xxa * 0.5F;
                float forwardMovement = livingentity.zza;
                if (forwardMovement <= 0.0F) {
                    this.gallopSoundCounter = 0;
                }

                if (this.level.isClientSide()) {
                    this.updateMovementVariables(sidewaysMovement, forwardMovement);
                }

                if (this.onGround && this.playerJumpPendingScale == 0.0F && this.isStanding() && !this.allowStandSliding) {
                    sidewaysMovement = 0.0F;
                    forwardMovement = 0.0F;
                }

                // Check if RNG is higher roll, than disobeying debuff, if so, then do the jump.
                if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.onGround) {
                    double d0 = this.getCustomJump() * (double) this.playerJumpPendingScale * (double) this.getBlockJumpFactor();
                    double d1;
                    if (this.hasEffect(Effects.JUMP)) {
                        d1 = d0 + (double) ((float) (this.getEffect(Effects.JUMP).getAmplifier() + 1) * 0.1F);
                    } else {
                        d1 = d0;
                    }
                    float jumpHeight = (float) (-0.1817584952 * ((float) Math.pow(d1, 3.0F)) + 3.689713992 * ((float) Math.pow(d1, 2.0F)) + 2.128599134 * d1 - 0.343930367);
                    if (this.getRandom().nextDouble() > this.getJumpDisobey(jumpHeight)) {

                        // if (this.getDisobedienceFactor() >
                        // this.progressionManager.getAffinityLeveling().getDebuff()) {
                        Vector3d vector3d = this.getDeltaMovement();
                        this.setDeltaMovement(vector3d.x, d1, vector3d.z);

                        // Check jumpheight, and add XP accordingly.
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

                        this.jumpHeight = jumpHeight;
                        this.startJump(jumpHeight);

                        SWEMPacketHandler.INSTANCE.sendToServer(new AddJumpXPMessage(xpToAdd, this.getId()));

                        this.hasImpulse = true;
                        ForgeHooks.onLivingJump(this);
                        if (forwardMovement > 0.0F) {
                            float f2 = MathHelper.sin(this.yRot * ((float) Math.PI / 180F));
                            float f3 = MathHelper.cos(this.yRot * ((float) Math.PI / 180F));
                            this.setDeltaMovement(this.getDeltaMovement().add(-0.4F * f2 * this.playerJumpPendingScale, 0.0D, 0.4F * f3 * this.playerJumpPendingScale));
                        }
                    } else {
                        this.setStandingAnim();
                    }

                    this.playerJumpPendingScale = 0.0F;
                }

                this.flyingSpeed = this.getSpeed() * 0.1F;
                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (forwardMovement < 0.0f) { // Backwards movement.
                        HorseSpeed oldSpeed = this.currentSpeed;
                        this.currentSpeed = HorseSpeed.WALK;
                        this.updateSelectedSpeed(oldSpeed);
                        livingentity.zza *= 3f;
                        // We multiply with a number close to 4, since in the AbstractHorseEntity it slows the
                        // backwards movement with * 0.25
                        // So we counter that, by check if it's negative, but still make it a bit slower than
                        // regular walking.

                        if (!this.isWalkingBackwards) {
                            SWEMPacketHandler.INSTANCE.sendToServer(new SHorseAnimationPacket(this.getId(), 3));
                        }

                    } else if (this.isWalkingBackwards) {
                        SWEMPacketHandler.INSTANCE.sendToServer(new SHorseAnimationPacket(this.getId(), 4));
                    }

                    super.travel(new Vector3d(sidewaysMovement, travelVector.y, forwardMovement));
                } else {
                    this.setDeltaMovement(Vector3d.ZERO);
                }

                if (this.onGround) {
                    this.playerJumpPendingScale = 0.0F;
                    this.stopJump();
                }

                this.calculateEntityAnimation(this, false);

                boolean flag = this.level.getBlockState(this.blockPosition().offset(this.getDirection().getNormal())).canOcclude();

                // Handles the swimming. Travel is only called when player is riding the entity.
                if ((this.wasEyeInWater || this.fluidOnEyes == FluidTags.LAVA) && !flag && this.getDeltaMovement().y < 0) { // Check if the eyes is in water level, and we don't have a solid block the
                    // way we are facing. If not, then apply an inverse force, to float the
                    // horse.
                    this.setDeltaMovement(this.getDeltaMovement().multiply(1, -1.9, 1));
                }

                if (!this.isCameraLocked()) {
                    this.yRot = this.lockedYRot;
                    this.yRotO = this.yRot;
                    this.xRot = this.lockedXRot * 0.5F;
                    this.setRot(this.yRot, this.xRot);
                    this.yBodyRot = this.yRot;
                    this.yHeadRot = this.yBodyRot;
                } else {
                    this.yRot = livingentity.yRot;
                    this.yRotO = this.yRot;
                    this.xRot = livingentity.xRot * 0.5F;
                    this.setRot(this.yRot, this.xRot);
                    this.yBodyRot = this.yRot;
                    this.yHeadRot = this.yBodyRot;
                }
            } else {
                super.travel(travelVector);
            }
        }
    }

    private void updateMovementVariables(float sidewaysMovement, float forwardMovement) {
        CSyncMovementIdentifiersPacket.MovementPacketData movementPacketData = new CSyncMovementIdentifiersPacket.MovementPacketData(forwardMovement > 0.0F, forwardMovement < 0.0F, sidewaysMovement > 0.0F, sidewaysMovement < 0.0F);
        SWEMPacketHandler.INSTANCE.sendToServer(new CSyncMovementIdentifiersPacket(movementPacketData, this.getUUID()));
    }

    /**
     * Is camera locked boolean.
     *
     * @return the boolean
     */
    public boolean isCameraLocked() {
        return this.entityData.get(CAMERA_LOCK);
    }

    /**
     * Sets camera lock.
     *
     * @param locked the locked
     */
    public void setCameraLock(boolean locked) {
        this.entityData.set(CAMERA_LOCK, locked);
        this.setLockedRotations(this.xRot, this.yRot);
    }

    /**
     * Sets locked rotations.
     *
     * @param xRot the x rot
     * @param yRot the y rot
     */
    public void setLockedRotations(float xRot, float yRot) {
        this.lockedXRot = xRot;
        this.lockedYRot = yRot;
    }

    @Override
    public void moveRelative(float pAmount, Vector3d pRelative) {
        Vector3d vector3d = getInputVector(pRelative, pAmount, this.isCameraLocked() ? this.yRot : this.lockedYRot);
        this.setDeltaMovement(this.getDeltaMovement().add(vector3d));
    }

    @Override
    public boolean isOnGround() {
        if (this.isFlying()) {
            BlockState state = this.level.getBlockState(this.blockPosition().below());
            return state.canOcclude();
        }
        return super.isOnGround();
    }

    @Override
    public void onPlayerJump(int pJumpPower) {
        if (this.isSaddled()) {
            if (pJumpPower < 0) {
                pJumpPower = 0;
            } else {
                this.allowStandSliding = true;
            }

            if (pJumpPower >= 90) {
                this.playerJumpPendingScale = 1.0F;
            } else {
                this.playerJumpPendingScale = 0.4F + 0.4F * (float) pJumpPower / 90.0F;
            }
        }
    }

    /**
     * Gets jump disobey.
     *
     * @param jumpHeight the jump height
     * @return the jump disobey
     */
    public double getJumpDisobey(float jumpHeight) {
        return 0.25 * (this.progressionManager.getJumpLeveling().getLevel() + 1 - 5) / 4 + 0.05 * (jumpHeight - 1) / 4 + 0.7 * this.progressionManager.getAffinityLeveling().getDebuff();
    }

    @Override
    public void knockback(float pStrength, double pRatioX, double pRatioZ) {
    }

    @Override
    public void handleStartJump(int pJumpPower) {
        if (this.getEntityData().get(FLYING)) {
            this.playFlapWingSound();
        } else {
            this.playJumpSound();
        }
    }

    /**
     * checks if horse is in water
     *
     * @return true if horse is in water
     */
    private Boolean checkIsInWater() {
        FluidState fluidstate = this.level.getFluidState(blockPosition());
        if (fluidstate.is(FluidTags.WATER)) {
            return true;
        }
        return false;
    }

    /**
     * Takeoff jump sounds
     */
    @Override
    protected void playJumpSound() {
        if (checkIsInWater()) {
            this.playSound(SoundEvents.PLAYER_SPLASH, 0.3F, 1.0F);
        } else {
            this.playSound(SoundEvents.HORSE_JUMP, 0.4F, 1.0F);
        }
    }

    /**
     * Overridden to handle landing sound
     */
    @Override
    public boolean causeFallDamage(float pFallDistance, float pDamageMultiplier) {
        if (pFallDistance > 1.0F && !checkIsInWater()) {
            this.playSound(SoundEvents.HORSE_LAND, 0.4F, 1.0F);
        } else if (pFallDistance > 1.0F && checkIsInWater()) {
            this.playSound(SoundEvents.PLAYER_SPLASH, 0.1F, 1.0F);
        }
        int i = this.calculateFallDamage(pFallDistance, pDamageMultiplier);
        if (i <= 0) {
            return false;
        } else {
            this.hurt(DamageSource.FALL, (float) i);
            if (this.isVehicle()) {
                for (Entity entity : this.getIndirectPassengers()) {
                    entity.hurt(DamageSource.FALL, (float) i);
                }
            }
            this.playBlockFallSound();
            return true;
        }
    }

    /**
     * Play flap wing sound.
     */
    private void playFlapWingSound() {
        // TODO: ADD A FLAP WING SOUND AND PLAY IT HERE!
    }

    /**
     * Start jump.
     *
     * @param jumpHeight the jump height
     */
    protected void startJump(float jumpHeight) {
        SWEMPacketHandler.INSTANCE.sendToServer(new CHorseJumpPacket(this.getId(), jumpHeight));
    }

    /**
     * Stop jump.
     */
    private void stopJump() {
        if (this.level.isClientSide) {
            SWEMPacketHandler.INSTANCE.sendToServer(new CHorseJumpPacket(this.getId(), 0.0F));
        }
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        List<Entity> playerEntities = this.getPassengers().stream().filter((entity) -> entity instanceof PlayerEntity).collect(Collectors.toList());
        return this.getPassengers().isEmpty() ? null : playerEntities.isEmpty() ? null : playerEntities.get(0);
    }

    /**
     * Gets disobedience factor.
     *
     * @return the disobedience factor
     */
    private float getDisobedienceFactor() {
        return this.getRandom().nextFloat();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source == DamageSource.DROWN) return true;
        if (DamageSource.IN_FIRE.equals(source) || DamageSource.ON_FIRE.equals(source) || DamageSource.LAVA.equals(source) || DamageSource.HOT_FLOOR.equals(source)) {
            ItemStack stack = this.getSWEMArmor();
            if (!stack.isEmpty() && ((SWEMHorseArmorItem) stack.getItem()).tier.getId() >= 3) {
                return true;
            }
        }

        return super.isInvulnerableTo(source);
    }

    /**
     * Level up jump.
     */
    public void levelUpJump() {
        ModifiableAttributeInstance jumpStrength = this.getAttribute(Attributes.JUMP_STRENGTH);
        if (jumpStrength != null) {
            double currentSpeed = jumpStrength.getValue();
            double newSpeed = this.getAlteredJumpStrength();
            jumpStrength.addPermanentModifier(new AttributeModifier(this.progressionManager.getJumpLeveling().getLevelName(), newSpeed - currentSpeed, AttributeModifier.Operation.ADDITION));
        } else {
            SWEM.LOGGER.error("Jump Strength Attribute is null");
        }
    }

    /**
     * Level up speed.
     */
    public void levelUpSpeed() {
        ModifiableAttributeInstance speedInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedInstance != null) {
            speedInstance.setBaseValue(this.getAlteredMovementSpeed());
            this.updateSelectedSpeed(this.currentSpeed);
        } else {
            SWEM.LOGGER.error("Movement Speed Attribute is null");
        }
    }

    /**
     * Level up health.
     */
    public void levelUpHealth() {
        ModifiableAttributeInstance healthInstance = this.getAttribute(Attributes.MAX_HEALTH);
        if (healthInstance != null) {
            healthInstance.setBaseValue(this.getAlteredMaxHealth());
        } else {
            SWEM.LOGGER.error("Max Health Attribute is null");
        }
    }

    /**
     * Called by InventoryBasic.containerChanged() on a array that is never filled.
     */
    public void onHorseInventoryChanged(IInventory invBasic) {
        this.setSWEMSaddled();
        ItemStack itemstack = this.getArmor();
        super.containerChanged(invBasic);

        ItemStack itemstack1 = this.getArmor();
        if (this.tickCount > 20 && this.isSWEMArmor(itemstack1) && itemstack != itemstack1) {
            this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
        }
        if (this.level.isClientSide) return;
        for (int i = 0; i < invBasic.getContainerSize(); i++) {
            SWEMPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new UpdateHorseInventoryMessage(getId(), i, invBasic.getItem(i)));
        }
    }

    public void containerChanged(IInventory invBasic) {
        this.setSWEMSaddled();
        ItemStack itemstack = this.getArmor();
        super.containerChanged(invBasic);

        ItemStack itemstack1 = this.getArmor();
        if (this.tickCount > 20 && this.isSWEMArmor(itemstack1) && itemstack != itemstack1) {
            this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
        }
    }

    /**
     * Is swem saddled boolean.
     *
     * @return the boolean
     */
    protected boolean isSWEMSaddled() {
        return this.inventory.getItem(2).getItem() instanceof HorseSaddleItem;
    }

    /**
     * Sets swem saddled.
     */
    protected void setSWEMSaddled() {
        if (this.level.isClientSide) {
            this.setFlag(4, !this.inventory.getItem(2).isEmpty());
        }
    }

    protected void playGallopSound(SoundType p_190680_1_) {
        super.playGallopSound(p_190680_1_);
        if (this.getRandom().nextInt(10) == 0) {
            this.playSound(SoundEvents.HORSE_BREATHE, p_190680_1_.getVolume() * 0.6F, p_190680_1_.getPitch());
        }

        ItemStack stack = this.inventory.getItem(1);
        if (isSWEMArmor(stack)) stack.onHorseArmorTick(level, this);
    }

    // Get nom-nom sound
    @Nullable
    protected SoundEvent getEatingSound() {
        return SoundEvents.HORSE_EAT;
    }

    protected SoundEvent getAngrySound() {
        super.getAngrySound();
        return SoundEvents.HORSE_ANGRY;
    }

    @Override
    public Vector3d getDismountLocationForPassenger(LivingEntity pLivingEntity) {
        return super.getDismountLocationForPassenger(pLivingEntity);
    }

    @Override
    public void openInventory(PlayerEntity playerEntity) {
        if (!this.level.isClientSide && (!this.isVehicle() || this.hasPassenger(playerEntity)) && this.isTamed()) {
            ITextComponent horseDisplayName = new StringTextComponent(SWEMUtil.checkTextOverflow(this.getDisplayName().getString(), 18));
            INamedContainerProvider provider = new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return horseDisplayName;
                }

                @Override
                public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                    return new SWEMHorseInventoryContainer(p_createMenu_1_, p_createMenu_2_, getId());
                }
            };
            NetworkHooks.openGui((ServerPlayerEntity) playerEntity, provider, buffer -> buffer.writeInt(getId()).writeInt(getId()));
        }
    }

    /**
     * Check is coat transform item boolean.
     *
     * @param playerEntity the player entity
     * @param stack        the stack
     * @return the boolean
     */
    public boolean checkIsCoatTransformItem(PlayerEntity playerEntity, ItemStack stack) {
        if (stack.getItem() == SWEMItems.WHISTLE.get() && stack.getStack().getHoverName().getString().equals("Ocarina")) {
            stack.shrink(1);
            playerEntity.addItem(new ItemStack(SWEMItems.WHISTLE.get()));
            this.setCoatColour(SWEMCoatColor.EPONA_ZELDA);
            return true;
        } else if (stack.getItem() == Items.IRON_NUGGET && stack.getStack().getHoverName().getString().equals("Coin")) {
            stack.shrink(1);
            this.setCoatColour(SWEMCoatColor.ROACH_WITCHER);
            return true;
        } else if (stack.getItem() == Items.LILY_OF_THE_VALLEY && stack.getStack().getHoverName().getString().equals("Mono")) {
            stack.shrink(1);
            this.setCoatColour(SWEMCoatColor.AGRO_SOC);
            return true;
        } else if (stack.getItem() == Items.IRON_SWORD && stack.getStack().getHoverName().getString().equals("Sithus")) {
            stack.shrink(1);
            this.setCoatColour(SWEMCoatColor.SHADOWMERE_OBLIVION);
            return true;
        } else if (stack.getItem() == Items.BLAZE_ROD && stack.getStack().getHoverName().getString().equals("Ponyta")) {
            stack.shrink(1);
            this.setCoatColour(SWEMCoatColor.RAPIDASH_POKEMON);
            return true;
        } else if (stack.getItem() == Items.DIAMOND && stack.getStack().getHoverName().getString().equals("Power of Grayskull")) {
            stack.shrink(1);
            this.setCoatColour(SWEMCoatColor.SWIFT_WIND_SHE_RA);
            return true;
        } else if (stack.getItem() == SWEMItems.ENGLISH_BRIDLE_BLACK.get() && stack.getStack().getHoverName().getString().equals("Becky")) {
            stack.shrink(1);
            this.setCoatColour(SWEMCoatColor.BOB_FREE_REIN);
            return true;
        } else if (stack.getItem() == Items.NETHER_STAR && stack.getHoverName().getString().equals("Lady In Memory")) {
            stack.shrink(1);
            this.setCoatColour(SWEMCoatColor.LADY_JENNY);
            return true;
        }

        return false;
    }

    // Item interaction with horse.
    @Override
    public ActionResultType mobInteract(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemstack = playerEntity.getItemInHand(hand);
        if (!this.isBaby()) {
            if (this.isTamed() && playerEntity.isSecondaryUseActive() && !(itemstack.getItem() instanceof TrackerItem) && !(itemstack.getItem() instanceof HorseTackItem)) {
                this.openInventory(playerEntity);
                return ActionResultType.sidedSuccess(this.level.isClientSide);
            }

            if (this.isVehicle()) {
                this.doPlayerRide(playerEntity);
                return super.mobInteract(playerEntity, hand);
            }
        }

        Item item = itemstack.getItem();

        // Debug item, can fetch both client and server side variables.
        if (item == Items.DEBUG_STICK) {
            if (!this.level.isClientSide) {
                playerEntity.sendMessage(new StringTextComponent("Values is: " + Arrays.toString(this.progressionManager.getAffinityLeveling().requiredXpArray)), Util.NIL_UUID);
            }
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        }

        if (this.isBreedingFood(itemstack)) {
            this.fedBreedingFood(playerEntity, itemstack);
        }

        if (checkIsCoatTransformItem(playerEntity, itemstack)) {
            return ActionResultType.SUCCESS;
        }

        if (!itemstack.isEmpty() && item != Items.SADDLE) {

            if (item == Items.LAPIS_LAZULI && !this.isBaby() && playerEntity.getUUID().equals(this.getOwnerUUID())) {
                if (ConfigHolder.SERVER.lapisCycleCoats.get()) {
                    this.setHorseVariant(SWEMCoatColor.getNextCyclableCoat(this.getHorseVariant()).getId());
                    ItemStack heldItemCopy = itemstack.copy();
                    if (!playerEntity.abilities.instabuild) heldItemCopy.shrink(1);
                    playerEntity.setItemInHand(hand, heldItemCopy);
                    return ActionResultType.SUCCESS;
                }
            }

            if (item == Items.REDSTONE && !this.isBaby() && playerEntity.getUUID().equals(this.getOwnerUUID())) {
                if (ConfigHolder.SERVER.lapisCycleCoats.get()) {
                    this.setHorseVariant(SWEMCoatColor.getPreviousCyclableCoat(this.getHorseVariant()).getId());
                    ItemStack heldItemCopy = itemstack.copy();
                    if (!playerEntity.abilities.instabuild) heldItemCopy.shrink(1);
                    playerEntity.setItemInHand(hand, heldItemCopy);
                    return ActionResultType.SUCCESS;
                }
            }

            if (NEGATIVE_FOOD_ITEMS.test(itemstack)) {
                // Emit negative particle effects.
                if (!this.level.isClientSide) {
                    this.emitBadParticles((ServerWorld) this.level, 4);
                }
                return ActionResultType.FAIL;
            }

            if (FOOD_ITEMS.test(itemstack)) {
                if (this.getNeeds().getHunger().getTotalTimesFed() == 7) {
                    // Emit negative particle effects.
                    if (!this.level.isClientSide) this.emitEchParticles((ServerWorld) this.level, 6);

                    return ActionResultType.PASS;
                }

                if (!this.level.isClientSide) {
                    if (this.getNeeds().getHunger().addPoints(itemstack)) {
                        if (!playerEntity.isCreative()) {
                            itemstack.shrink(1);
                        }
                        if (item == SWEMItems.SUGAR_CUBE.get()) {
                            this.progressionManager.getAffinityLeveling().addXP(5.0F);
                        }

                        this.emitYayParticles((ServerWorld) this.level, 3);
                    } else {
                        this.emitEchParticles((ServerWorld) this.level, 3);
                        // Stop the swing from happening
                        return ActionResultType.SUCCESS;
                    }
                }

                // This here, makes the swing anim, when feeding items. on the client side.
                // Make this fail, if you can't add points.
                return ActionResultType.sidedSuccess(this.level.isClientSide);
            }

            //			if (this.isBreedingItem(itemstack)) {
            //				return this.fedFood(p_230254_1_, itemstack);
            //			}

            if (item == Items.WATER_BUCKET) {
                if (!this.level.isClientSide && this.getNeeds().getThirst().canIncrementState()) {
                    this.getNeeds().getThirst().incrementState();
                    playerEntity.setItemInHand(hand, ((BucketItem) item).getEmptySuccessItem(itemstack, playerEntity));
                    this.emitYayParticles((ServerWorld) this.level, 4);
                    return ActionResultType.CONSUME;
                } else if (this.level.isClientSide && !this.getNeeds().getThirst().canIncrementState()) {
                    // Stop the swing from happening
                    return ActionResultType.SUCCESS;

                } else if (!this.level.isClientSide && !this.getNeeds().getThirst().canIncrementState()) {
                    // Stop the swing from happening
                    this.emitEchParticles((ServerWorld) this.level, 3);
                    return ActionResultType.SUCCESS;
                }
            }

            ActionResultType actionresulttype = itemstack.interactLivingEntity(playerEntity, this, hand);

            if (actionresulttype.consumesAction()) {
                if (item instanceof HorseSaddleItem) {
                    this.setSWEMSaddled();
                }
                return actionresulttype;
            }

            if (!this.isTamed()) {
                this.makeMad();
                return ActionResultType.sidedSuccess(this.level.isClientSide);
            }

            boolean flag = !this.isBaby() && !this.isSWEMSaddled() && (item instanceof HorseSaddleItem);
            boolean flag1 = !this.isBaby() && this.hasHalter() && (item instanceof GirthStrapItem);
            boolean flag2 = !this.isBaby() && this.hasHalter() && (item instanceof BlanketItem);
            boolean flag3 = !this.isBaby() && this.hasHalter() && (item instanceof LegWrapsItem);
            boolean flag4 = !this.isBaby() && this.hasHalter() && (item instanceof BreastCollarItem);
            if (this.isSWEMArmor(itemstack) || flag) {
                this.setSWEMSaddled();
                this.openInventory(playerEntity);
                return ActionResultType.sidedSuccess(this.level.isClientSide);
            }
            if (flag1 || flag2 || flag3 || flag4) {
                this.openInventory(playerEntity);
                return ActionResultType.sidedSuccess(this.level.isClientSide);
            }
        }

        if (this.isBaby()) {
            return super.mobInteract(playerEntity, hand);
        } else {
            this.doPlayerRide(playerEntity);
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        }
    }

    public boolean isBreedingFood(ItemStack pStack) {
        return BREEDING_ITEMS.test(pStack);
    }

    public ActionResultType fedBreedingFood(PlayerEntity pPlayer, ItemStack pStack) {
        boolean flag = this.handleEatingBreedingFood(pPlayer, pStack);
        this.getNeeds().getHunger().addPoints(pStack);
        if (!pPlayer.abilities.instabuild) {
            if (pStack.getItem() == SWEMItems.SWEET_FEED_OPENED.get())
                pStack.setDamageValue(pStack.getDamageValue() + 1);
            else pStack.shrink(1);
        }

        if (this.level.isClientSide) {
            return ActionResultType.CONSUME;
        } else {
            return flag ? ActionResultType.SUCCESS : ActionResultType.PASS;
        }
    }

    public boolean handleEatingBreedingFood(PlayerEntity pPlayer, ItemStack pStack) {
        boolean flag = false;
        float f = 0.0F;
        int i = 0;
        int j = 0;
        Item item = pStack.getItem();
        if (item == SWEMItems.SWEET_FEED_OPENED.get()) {
            i = (int) (ConfigHolder.SERVER.foalAgeInSeconds.get() * 0.5);
            if (!this.level.isClientSide && this.isTamed() && this.getAge() == 0 && !this.isInLove()) {
                flag = true;
                this.setInLove(pPlayer);
            }
        }

        if (this.isBaby() && i > 0) {
            this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
            if (!this.level.isClientSide) {
                this.ageUp(i);
            }

            flag = true;
        }

        return flag;
    }

    /**
     * Emit bad particles.
     *
     * @param world the world
     * @param count the count
     */
    public void emitBadParticles(ServerWorld world, int count) {
        world.sendParticles(SWEMParticles.BAD.get(), this.getX(), this.getY() + 2.5, this.getZ(), count, 0.3D, 0.3D, 0.3D, 0.3D);
    }

    /**
     * Emit ech particles.
     *
     * @param world the world
     * @param count the count
     */
    public void emitEchParticles(ServerWorld world, int count) {
        world.sendParticles(SWEMParticles.ECH.get(), this.getX(), this.getY() + 2.5, this.getZ(), count, 0.3D, 0.3D, 0.3D, 0.3D);
    }

    /**
     * Emit meh particles.
     *
     * @param world the world
     * @param count the count
     */
    public void emitMehParticles(ServerWorld world, int count) {
        world.sendParticles(SWEMParticles.MEH.get(), this.getX(), this.getY() + 2.5, this.getZ(), count, 0.3D, 0.3D, 0.3D, 0.3D);
    }

    /**
     * Emit yay particles.
     *
     * @param world the world
     * @param count the count
     */
    public void emitYayParticles(ServerWorld world, int count) {
        world.sendParticles(SWEMParticles.YAY.get(), this.getX(), this.getY() + 2.5, this.getZ(), count, 0.3D, 0.3D, 0.3D, 0.3D);
    }

    /**
     * Emit woot particles.
     *
     * @param world the world
     * @param count the count
     */
    public void emitWootParticles(ServerWorld world, int count) {
        world.sendParticles(SWEMParticles.WOOT.get(), this.getX(), this.getY() + 2.5, this.getZ(), count, 0.3D, 0.3D, 0.3D, 0.3D);
    }

    @Override
    public ActionResultType interactAt(PlayerEntity player, Vector3d vec, Hand hand) {
        // Vec is a local hit vector for the horse, not sure how vec.x and vec.z applies.

        return ActionResultType.PASS;

    /*if (player.isSecondaryUseActive()) {
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

    if (player.getCommandSenderWorld().isClientSide) return ActionResultType.CONSUME;

    NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
    	@Override
    	public ITextComponent getDisplayName() {
    		return new TranslationTextComponent("container.swem.saddlebag");
    	}

    	@Nullable
    	@Override
    	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
    		return new SaddlebagContainer(p_createMenu_1_, p_createMenu_2_, getId());
    	}
    }, buffer ->   {
    	buffer.writeInt(getId());
    	buffer.writeInt(getId());
    });

    return ActionResultType.CONSUME;*/
    }

    /**
     * Check for back hit boolean.
     *
     * @param vec the vec
     * @return the boolean
     */
    private boolean checkForBackHit(Vector3d vec) {
        Direction facing = this.getDirection();

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
     * Gets needs.
     *
     * @return the needs
     */
    public NeedManager getNeeds() {
        return this.needs;
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    @Override
    public boolean canMate(AnimalEntity pOtherAnimal) {
        if (pOtherAnimal == this) {
            return false;
        } else if (pOtherAnimal.getClass() != this.getClass()) {
            return false;
        } else {
            return this.isInLove() && pOtherAnimal.isInLove();
        }
    }

    @Override
    public void setInLove(@Nullable PlayerEntity pPlayer) {
        this.setInLoveTime(ConfigHolder.SERVER.horseInLoveInSeconds.get() * 20); // Ticks
        if (pPlayer != null) {
            this.loveCause = pPlayer.getUUID();
        }

        this.level.broadcastEntityEvent(this, (byte) 18);
    }

    /**
     * Set whether this entity is a child. Also sets it's baby age.
     *
     * @param pChildZombie
     */
    @Override
    public void setBaby(boolean pChildZombie) {
        this.setAge(pChildZombie ? -(ConfigHolder.SERVER.foalAgeInSeconds.get() * 20) : 0);
    }

    /**
     * This method is executed when a baby is spawned, and when a baby grows up to adult. This makes
     * sure that we replace the foal coat with a "proper" adult coat.
     */
    @Override
    protected void ageBoundaryReached() {
        if (!this.isBaby()) {
            this.setCoatColour(SWEMCoatColor.foalToParentCoat(this.getCoatColor()));
        }
        super.ageBoundaryReached();
    }

    /**
     * This executes, once a baby is about to spawn. Here we can set various offspring attributes,
     * like coats, stats etc.
     *
     * @param world   Server world.
     * @param partner The partner entity that the baby was made with.
     * @return The modified foal that should be created.
     */
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity partner) {
        SWEMHorseEntityBase foal = SWEMEntities.SWEM_HORSE_ENTITY.get().create(world);
        if (foal == null) {
            SWEM.LOGGER.error("Uh oh - A foal could not be spawned, something went wrong.\nPartner 1: " + this + "\nPartner 2: " + partner);
            return null;
        }
        SWEMHorseEntity swemPartner = (SWEMHorseEntity) partner;
        int i = this.getRandom().nextInt(9);
        SWEMCoatColor coatColor;
        if (i < 4) {
            coatColor = SWEMCoatColor.parentToFoalCoat(this.getCoatColor());
        } else if (i < 8) {
            coatColor = SWEMCoatColor.parentToFoalCoat(swemPartner.getCoatColor());
        } else {
            coatColor = SWEMCoatColor.getRandomFoalCoat();
        }
        HorseSpeed oldSpeed = foal.currentSpeed;
        foal.currentSpeed = HorseSpeed.WALK;
        foal.updateSelectedSpeed(oldSpeed);
        foal.setCoatColour(coatColor);

        return foal;
    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld levelIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        SWEMCoatColor coatcolors;
        if (spawnDataIn instanceof SWEMHorseData) {
            coatcolors = ((SWEMHorseData) spawnDataIn).variant;
        } else {
            coatcolors = SWEMCoatColor.getRandomLapisObtainableCoat();
            spawnDataIn = new SWEMHorseData(coatcolors);
        }
        this.setHorseVariant(coatcolors.getId());
        HorseSpeed oldSpeed = this.currentSpeed;
        this.currentSpeed = HorseSpeed.WALK;
        this.updateSelectedSpeed(oldSpeed);
        // this.setVariantAndMarkings(coatcolors, Util.getRandom(CoatTypes.values(), this.rand));
        return super.finalizeSpawn(levelIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public boolean canWearArmor() {
        return true;
    }

    /**
     * Called by the server when constructing the spawn packet. Data should be added to the provided
     * stream.
     *
     * @param buffer The packet data stream
     */
    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeItem(this.inventory.getItem(0));
        buffer.writeItem(this.inventory.getItem(1));
        buffer.writeItem(this.inventory.getItem(2));
        buffer.writeItem(this.inventory.getItem(3));
        buffer.writeItem(this.inventory.getItem(4));
        buffer.writeItem(this.inventory.getItem(5));
        buffer.writeItem(this.inventory.getItem(6));
        buffer.writeItem(this.inventory.getItem(7));
    }

    /**
     * Called by the client when it receives an Entity spawn packet. Data should be read out of the
     * stream in the same way as it was written.
     *
     * @param additionalData The packet data stream
     */
    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        this.inventory.setItem(0, additionalData.readItem());
        this.inventory.setItem(1, additionalData.readItem());
        this.inventory.setItem(2, additionalData.readItem());
        this.inventory.setItem(3, additionalData.readItem());
        this.inventory.setItem(4, additionalData.readItem());
        this.inventory.setItem(5, additionalData.readItem());
        this.inventory.setItem(6, additionalData.readItem());
        this.inventory.setItem(7, additionalData.readItem());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    /**
     * Decrement speed.
     */
    public void decrementSpeed() {
        HorseSpeed oldSpeed = this.currentSpeed;
        if (oldSpeed == HorseSpeed.WALK) return;
        else if (oldSpeed == HorseSpeed.TROT) {
            this.currentSpeed = HorseSpeed.WALK;
        } else if (oldSpeed == HorseSpeed.CANTER_EXT) {
            this.currentSpeed = HorseSpeed.CANTER;
        } else if (oldSpeed == HorseSpeed.CANTER) {
            this.currentSpeed = HorseSpeed.TROT;
        } else if (oldSpeed == HorseSpeed.GALLOP) {
            this.currentSpeed = HorseSpeed.CANTER_EXT;
            this.setGallopCooldown();
        }
        this.updateSelectedSpeed(oldSpeed);
    }

    /**
     * Increment speed.
     */
    public void incrementSpeed() {
        HorseSpeed oldSpeed = this.currentSpeed;
        if (oldSpeed == HorseSpeed.GALLOP) return; // Return if current gait is already max.

        if (this.getRandom().nextDouble() < ((this.progressionManager.getAffinityLeveling().getDebuff() * this.currentSpeed.getSkillMultiplier()) * (this.standingTimer > 0 ? 0.5 : 1))) {
            this.setStandingAnim();
            return;
        } else if (oldSpeed == HorseSpeed.CANTER_EXT) {
            if (this.entityData.get(GALLOP_ON_COOLDOWN)) {
                ArrayList<String> args = new ArrayList<>();
                args.add(String.valueOf(Math.round((this.entityData.get(GALLOP_COOLDOWN_TIMER) - this.entityData.get(GALLOP_TIMER)) / 20)));
                SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) this.getPassengers().get(0)), new ClientStatusMessagePacket(0, 1, args));
                return;
            }
            this.currentSpeed = HorseSpeed.GALLOP;
        } else if (oldSpeed == HorseSpeed.CANTER) {
            this.currentSpeed = HorseSpeed.CANTER_EXT;
        } else if (oldSpeed == HorseSpeed.TROT) {
            if (this.needs.getThirst().getState() == ThirstNeed.ThirstState.EXICCOSIS) {
                SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) this.getPassengers().get(0)), new ClientStatusMessagePacket(1, 0, new ArrayList<>()));
                return;
            }

            if (this.needs.getHunger().getState() == HungerNeed.HungerState.STARVING) {
                SWEMPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) this.getPassengers().get(0)), new ClientStatusMessagePacket(3, 0, new ArrayList<>()));
                return;
            }

            this.currentSpeed = HorseSpeed.CANTER;

        } else if (oldSpeed == HorseSpeed.WALK) {
            this.currentSpeed = HorseSpeed.TROT;
        }
        this.updateSelectedSpeed(oldSpeed);
    }

    /**
     * Update selected speed.
     *
     * @param oldSpeed the old speed
     */
    public void updateSelectedSpeed(HorseSpeed oldSpeed) {

        if (this.currentSpeed == HorseSpeed.TROT) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(SWEMUtil.getInternalSpeedFromBlocksPerSecond(5.5D));
        } else if (this.currentSpeed == HorseSpeed.WALK) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(SWEMUtil.getInternalSpeedFromBlocksPerSecond(1.8D));
        } else if (this.currentSpeed == HorseSpeed.CANTER) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(SWEMUtil.getInternalSpeedFromBlocksPerSecond(11D));
        } else {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getAlteredMovementSpeed());
        }
        this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(oldSpeed.getModifier());
        if (!this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(this.currentSpeed.getModifier())) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(this.currentSpeed.getModifier());
        }

        this.entityData.set(SPEED_LEVEL, this.currentSpeed.speedLevel);
    }

    /**
     * Can fly boolean.
     *
     * @return the boolean
     */
    public boolean canFly() {
        if (!(this.getSWEMArmor().getItem() instanceof SWEMHorseArmorItem)) {
            return false;
        }
        return this.hasSaddle().getItem() instanceof AdventureSaddleItem && ((SWEMHorseArmorItem) this.getSWEMArmor().getItem()).tier.getId() == 4;
    }

    /**
     * Brush.
     */
    public void brush() {
        this.progressionManager.getAffinityLeveling().brush();
    }

    /**
     * Cycle riding permission.
     */
    public void cycleRidingPermission() {
        if (RidingPermission.valueOf(this.entityData.get(PERMISSION_STRING)) == RidingPermission.NONE) {
            this.setPermissionState("TRUST");
        } else if (RidingPermission.valueOf(this.entityData.get(PERMISSION_STRING)) == RidingPermission.TRUST) {
            this.setPermissionState("ALL");
        } else {
            this.setPermissionState("NONE");
        }
    }

    /**
     * Gets permission state.
     *
     * @return the permission state
     */
    public RidingPermission getPermissionState() {
        return RidingPermission.valueOf(this.entityData.get(PERMISSION_STRING));
    }

    /**
     * Sets permission state.
     *
     * @param string the string
     */
    private void setPermissionState(String string) {
        this.entityData.set(PERMISSION_STRING, string);
    }

    @Override
    protected int getInventorySize() {
        return 8;
    }

    /**
     * Is halter boolean.
     *
     * @param stack the stack
     * @return the boolean
     */
    public boolean isHalter(ItemStack stack) {
        return stack.getItem() instanceof HalterItem;
    }

    /**
     * Gets halter.
     *
     * @return the halter
     */
    public ItemStack getHalter() {
        return this.inventory.getItem(0);
    }

    /**
     * Is breast collar boolean.
     *
     * @param stack the stack
     * @return the boolean
     */
    public boolean isBreastCollar(ItemStack stack) {
        return stack.getItem() instanceof BreastCollarItem;
    }

    /**
     * Gets breast collar.
     *
     * @return the breast collar
     */
    public ItemStack getBreastCollar() {
        return this.inventory.getItem(3);
    }

    /**
     * Is leg wraps boolean.
     *
     * @param stack the stack
     * @return the boolean
     */
    public boolean isLegWraps(ItemStack stack) {
        return stack.getItem() instanceof LegWrapsItem;
    }

    /**
     * Gets leg wraps.
     *
     * @return the leg wraps
     */
    public ItemStack getLegWraps() {
        return this.inventory.getItem(4);
    }

    /**
     * Is girth strap boolean.
     *
     * @param stack the stack
     * @return the boolean
     */
    public boolean isGirthStrap(ItemStack stack) {
        return stack.getItem() instanceof GirthStrapItem;
    }

    /**
     * Gets girth strap.
     *
     * @return the girth strap
     */
    public ItemStack getGirthStrap() {
        return this.inventory.getItem(5);
    }

    /**
     * Has bridle boolean.
     *
     * @return the boolean
     */
    public boolean hasBridle() {
        return this.inventory.getItem(0).getItem() instanceof BridleItem;
    }

    public boolean canEquipSaddle() {
        return this.hasBlanket() || !ConfigHolder.SERVER.blanketBeforeSaddle.get();
    }

    public boolean canEquipGirthStrap() {
        return this.isSWEMSaddled() || !ConfigHolder.SERVER.saddleBeforeGirthStrap.get();
    }

    @Override
    public boolean canEquipArmor() {
        return hasAdventureSaddle() && getHalter().getItem() instanceof AdventureBridleItem && getBreastCollar().getItem() instanceof AdventureBreastCollarItem && getGirthStrap().getItem() instanceof AdventureGirthStrapItem && getLegWraps().getItem() instanceof AdventureLegWraps && getBlanket().getItem() instanceof AdventureBlanketItem;
    }

    @Override
    public boolean canBeControlledByRider() {
        if (this.hasBridle() || !ConfigHolder.SERVER.needBridleToSteer.get()) {

            if (this.getControllingPassenger() instanceof LivingEntity && !(this.getControllingPassenger() instanceof AnimalEntity) && this.getControllingPassenger() instanceof PlayerEntity) {
                return true;
            }

            return super.canBeControlledByRider();
        } else {
            return false;
        }
    }

    /**
     * Is swem armor boolean.
     *
     * @param stack the stack
     * @return the boolean
     */
    public boolean isSWEMArmor(ItemStack stack) {
        return stack.getItem() instanceof SWEMHorseArmorItem;
    }

    /**
     * Gets swem armor.
     *
     * @return the swem armor
     */
    public ItemStack getSWEMArmor() {
        return this.inventory.getItem(6);
    }

    /**
     * Is saddlebag boolean.
     *
     * @param stack the stack
     * @return the boolean
     */
    public boolean isSaddlebag(ItemStack stack) {
        return stack.getItem() instanceof SaddlebagItem;
    }

    /**
     * Gets saddlebag.
     *
     * @return the saddlebag
     */
    public ItemStack getSaddlebag() {
        return this.inventory.getItem(7);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) source.getEntity();
            if (this.hasBridle()) {
                if (this.canBeLeashed(player) && player.isShiftKeyDown()) {
                    this.setBridleLeashed(true);
                    this.setLeashedTo(player, true);
                    return false;
                }

                if (this.isLeashed() && this.isBridleLeashed() && player.isShiftKeyDown()) {
                    this.setBridleLeashed(false);
                    this.dropLeash(true, false);
                    return false;
                }
            }
        }

        if (this.isVehicle() && !this.level.isClientSide) {
            this.progressionManager.getAffinityLeveling().removeXp(amount * 15);
        } else if (source.getEntity() != null && source.getEntity().getUUID().equals(this.getOwnerUUID()) && !this.level.isClientSide) {
            this.progressionManager.getAffinityLeveling().removeXp(amount * 15);
        }
        if (source == DamageSource.FALL) {
            if (this.getHealth() <= 6.0F) {
                amount = 0; // Don't damage the horse, when below 6 HP. Still play hurt animations, and deduct
                // affinity.
            } else if (this.getHealth() - amount < 6.0f) {
                amount = this.getHealth() - 6.0f;
            }
        }

        if (source.isExplosion()) {
            amount /= 2;
        }
        if (source.getDirectEntity() instanceof AbstractArrowEntity) {
            amount = this.calculateArrowDamage((AbstractArrowEntity) source.getDirectEntity(), amount);
        }

        if (source == DamageSource.MAGIC || source == DamageSource.WITHER) {
            if (this.getSWEMArmor().getItem() instanceof SWEMHorseArmorItem
                    && ((SWEMHorseArmorItem) this.getSWEMArmor().getItem()).tier.getId() >= SWEMHorseArmorItem.HorseArmorTier.IRON.getId()
            ) {
                amount /= 2;
            }
        }

        if (amount <= 0) {
            return false;
        }


        boolean flag = super.hurt(source, amount);

        if (flag) {
            if (!this.level.isClientSide()) {
                this.emitBadParticles((ServerWorld) this.level, 5);
            }
            if (amount > 1) {
                if (this.standingTimer == 0) {
                    this.setStandingAnim();
                }
            }
        }

        return flag;
    }

    /**
     * Return whether this entity should be rendered as on fire.
     */
    @Override
    public boolean displayFireAnimation() {
        return this.getSWEMArmor().getItem() instanceof SWEMHorseArmorItem
                ? ((SWEMHorseArmorItem) this.getSWEMArmor().getItem()).tier.getId() < SWEMHorseArmorItem.HorseArmorTier.DIAMOND.getId() && super.displayFireAnimation() : super.displayFireAnimation();
    }


    @Override
    protected int calculateFallDamage(float pDistance, float pDamageMultiplier) {
        return MathHelper.ceil((pDistance * 0.5F - 4.0F) * pDamageMultiplier);
    }

    /**
     * Calculate arrow damage float.
     *
     * @param arrow  the arrow
     * @param amount the amount
     * @return the float
     */
    private float calculateArrowDamage(AbstractArrowEntity arrow, float amount) {
        if (!this.isWearingArmor()) return amount;
        if (((SWEMHorseArmorItem) this.getSWEMArmor().getItem()).tier == SWEMHorseArmorItem.HorseArmorTier.IRON) {
            boolean shouldBlock = this.random.nextFloat() > 0.5;
            if (shouldBlock && !arrow.isCritArrow()) {
                amount = 0;
            } else if (shouldBlock && arrow.isCritArrow()) {
                amount = 2;
            }
        } else if (((SWEMHorseArmorItem) this.getSWEMArmor().getItem()).tier == SWEMHorseArmorItem.HorseArmorTier.GOLD) {
            boolean shouldBlock = this.random.nextFloat() > 0.5;
            if (shouldBlock && !arrow.isCritArrow()) {
                amount = 0;
            } else if (shouldBlock && arrow.isCritArrow()) {
                amount = 2;
            }
        } else if (((SWEMHorseArmorItem) this.getSWEMArmor().getItem()).tier == SWEMHorseArmorItem.HorseArmorTier.DIAMOND) {
            boolean shouldBlock = this.random.nextFloat() > 0.25;
            if (shouldBlock && !arrow.isCritArrow()) {
                amount = 0;
            } else if (shouldBlock && arrow.isCritArrow()) {
                amount = 2;
            }
        } else if (((SWEMHorseArmorItem) this.getSWEMArmor().getItem()).tier == SWEMHorseArmorItem.HorseArmorTier.AMETHYST) {
            if (arrow.isCritArrow()) amount = 2;
            else amount = 0;
        }

        return amount;
    }

    /**
     * Sets standing timer.
     *
     * @param timeInTicks the time in ticks
     */
    public void setStandingTimer(int timeInTicks) {
        this.standingTimer = timeInTicks;
    }

    /**
     * Sets standing anim.
     */
    public void setStandingAnim() {
        this.standAnimationTick = 42;
        this.standAnimationVariant = this.getRandom().nextDouble() > 0.5 ? 2 : 1;

        if (this.level.isClientSide) {
            SWEMPacketHandler.INSTANCE.sendToServer(new SHorseAnimationPacket(this.getEntity().getId(), standAnimationVariant));
            if (this.entityData.get(SPEED_LEVEL) != 0)
                SWEMPacketHandler.INSTANCE.sendToServer(new SendHorseSpeedChange(2, this.getId()));
        } else {
            SWEMPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new CHorseAnimationPacket(this.getEntity().getId(), standAnimationVariant));
        }
        this.standingTimer = 142;
    }

    /**
     * Is blanket boolean.
     *
     * @param stack the stack
     * @return the boolean
     */
    public boolean isBlanket(ItemStack stack) {
        return stack.getItem() instanceof BlanketItem;
    }

    /**
     * Gets blanket.
     *
     * @return the blanket
     */
    public ItemStack getBlanket() {
        return this.inventory.getItem(1);
    }

    /**
     * Is saddle boolean.
     *
     * @param stack the stack
     * @return the boolean
     */
    public boolean isSaddle(ItemStack stack) {
        return stack.getItem() instanceof HorseSaddleItem;
    }

    @Override
    public boolean isWearingArmor() {
        return this.inventory.getItem(6).getItem() instanceof SWEMHorseArmorItem;
    }

    /**
     * Has saddle bag boolean.
     *
     * @return the boolean
     */
    public boolean hasSaddleBag() {
        return this.inventory.getItem(7).getItem() instanceof SaddlebagItem;
    }

    /**
     * Gets jump height.
     *
     * @return the jump height
     */
    public float getJumpHeight() {
        float jumpStrength = (float) this.getCustomJump();
        return (float) (-0.1817584952 * ((float) Math.pow(jumpStrength, 3.0F)) + 3.689713992 * ((float) Math.pow(jumpStrength, 2.0F)) + 2.128599134 * jumpStrength - 0.343930367);
    }

    /**
     * Gets owner display name.
     *
     * @return the owner display name
     */
    public ITextComponent getOwnerDisplayName() {
        UUID playerUUID = this.getOwnerUUID();
        if (playerUUID == null) {
            return new TranslationTextComponent("Not owned.");
        }
        PlayerEntity owner = this.level.getPlayerByUUID(playerUUID);
        if (owner == null) {
            SWEM.LOGGER.error("Getting the owner display name failed.");
            return new StringTextComponent("Something went wrong.");
        }
        return owner.getDisplayName();
    }

    /**
     * Sets whistle pos.
     *
     * @param pos the pos
     */
    public void setWhistlePos(BlockPos pos) {
        this.whistlePos = pos;
    }

    @Override
    public Vector3d getLeashOffset() {
        // Makes the leash render from the head of the horse instead of the front body.
        return super.getLeashOffset().add(0, 0, this.getBbWidth() * 0.7);
    }

    protected boolean isBeingMovedByPlayer() {
        return this.getEntityData().get(IS_MOVING_FORWARD) || this.getEntityData().get(IS_MOVING_BACKWARDS) || this.getEntityData().get(IS_MOVING_LEFT) || this.getEntityData().get(IS_MOVING_RIGHT);
    }

    public enum HorseSpeed {
        WALK(new AttributeModifier("HORSE_WALK", 0, AttributeModifier.Operation.ADDITION), 0, 0.05f, "Walk"),
        TROT(new AttributeModifier("HORSE_TROT", 0, AttributeModifier.Operation.ADDITION), 1, 0.1f, "Trot"),
        CANTER(new AttributeModifier("HORSE_CANTER", 0, AttributeModifier.Operation.ADDITION), 2, 0.5f, "Canter"),
        CANTER_EXT(new AttributeModifier("HORSE_CANTER_EXT", 0, AttributeModifier.Operation.ADDITION), 3, 0.8f, "Extended Canter"),
        GALLOP(new AttributeModifier("HORSE_GALLOP", 0.2d, AttributeModifier.Operation.MULTIPLY_TOTAL), 4, 1.0f, "Gallop");
        private final AttributeModifier modifier;
        private final int speedLevel;
        private final float skillMultiplier;
        private final String text;

        /**
         * Instantiates a new Horse speed.
         *
         * @param modifier        the modifier
         * @param speedLevel      the speed level
         * @param skillMultiplier the skill multiplier
         * @param text            the text
         */
        HorseSpeed(AttributeModifier modifier, int speedLevel, float skillMultiplier, String text) {
            this.modifier = modifier;
            this.speedLevel = speedLevel;
            this.skillMultiplier = skillMultiplier;
            this.text = text;
        }

        /**
         * Gets modifier.
         *
         * @return the modifier
         */
        public AttributeModifier getModifier() {
            return this.modifier;
        }

        /**
         * Gets speed level.
         *
         * @return the speed level
         */
        public int getSpeedLevel() {
            return this.speedLevel;
        }

        /**
         * Gets skill multiplier.
         *
         * @return the skill multiplier
         */
        public float getSkillMultiplier() {
            return this.skillMultiplier;
        }

        /**
         * Gets text.
         *
         * @return the text
         */
        public String getText() {
            return text;
        }
    }

    public enum RidingPermission {
        NONE, TRUST, ALL
    }

    public static class SWEMHorseData extends AgeableData {
        public final SWEMCoatColor variant;

        /**
         * Instantiates a new Swem horse data.
         *
         * @param p_i231557_1_ the p i 231557 1
         */
        public SWEMHorseData(SWEMCoatColor p_i231557_1_) {
            super(false);
            this.variant = p_i231557_1_;
        }
    }
}
