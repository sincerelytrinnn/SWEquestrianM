package com.alaharranhonor.swem.entities.ai;

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

import com.alaharranhonor.swem.blocks.GrainFeederBlock;
import com.alaharranhonor.swem.blocks.PaddockFeederBlock;
import com.alaharranhonor.swem.blocks.SlowFeederBlock;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.EnumSet;

public class LookForFoodGoal extends Goal {

    private final SWEMHorseEntityBase horse;

    private final double speed;

    private BlockPos foundFood;

    private int movingTimer;
    private BlockPos movingToPos;

    /**
     * Instantiates a new Look for food goal.
     *
     * @param entityIn the entity in
     * @param speed    the speed
     */
    public LookForFoodGoal(SWEMHorseEntityBase entityIn, double speed) {
        this.horse = entityIn;
        this.speed = speed;
        this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for
     * execution in this method as well.
     */
    @Override
    public boolean canUse() {
        return !this.horse.isBaby()
                && this.horse.getNeeds().getHunger().getState().getId() < 3
                && this.horse.getPassengers().isEmpty()
                && this.horse.getLeashHolder() == null;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.horse.getNavigation().stop();
        SWEMHorseEntityBase.HorseSpeed oldSpeed = this.horse.currentSpeed;
        this.horse.currentSpeed = SWEMHorseEntityBase.HorseSpeed.WALK;
        this.horse.updateSelectedSpeed(oldSpeed);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    @Override
    public void stop() {
        this.foundFood = null;
        this.movingTimer = 0;
        this.movingToPos = null;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return this.horse.getNeeds().getHunger().getState().getId() < 3
                && this.horse.getPassengers().isEmpty()
                && this.horse.getLeashHolder() == null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void tick() {
        if (foundFood == null) {
            ArrayList<BlockPos> grassPos = new ArrayList<>();
            ArrayList<BlockPos> qualityBalePos = new ArrayList<>();
            ArrayList<BlockPos> slowFeederPos = new ArrayList<>();
            ArrayList<BlockPos> paddockFeederPos = new ArrayList<>();
            ArrayList<BlockPos> grainFeederPos = new ArrayList<>();
            BlockPos entityPos = this.horse.blockPosition();
            for (int i = -3; i < 4; i++) { // X Cord.
                for (int j = -3; j < 4; j++) { // Z Cord
                    for (int k = -1; k < 2; k++) { // Y Cord
                        BlockPos checkPos = entityPos.offset(i, k, j);
                        BlockState checkState = this.horse.level.getBlockState(checkPos);
                        if (checkState == Blocks.GRASS_BLOCK.defaultBlockState()) {
                            grassPos.add(checkPos);
                        } else if (checkState == SWEMBlocks.QUALITY_BALE.get().defaultBlockState()
                                || checkState == SWEMBlocks.QUALITY_BALE_SLAB.get().defaultBlockState()) {
                            if (this.horse
                                    .getNeeds()
                                    .getHunger()
                                    .getTimesFed(
                                            this.horse
                                                    .getNeeds()
                                                    .getHunger()
                                                    .getItemIndex(new ItemStack(SWEMBlocks.QUALITY_BALE.get().asItem())))
                                    < this.horse
                                    .getNeeds()
                                    .getHunger()
                                    .getMaxTimesFed(
                                            this.horse
                                                    .getNeeds()
                                                    .getHunger()
                                                    .getItemIndex(new ItemStack(SWEMBlocks.QUALITY_BALE.get().asItem())))) {
                                qualityBalePos.add(checkPos);
                            }
                        } else if (checkState.getBlock() instanceof SlowFeederBlock) {
                            if (checkState.getValue(SlowFeederBlock.LEVEL) > 0) {
                                if (this.horse
                                        .getNeeds()
                                        .getHunger()
                                        .getTimesFed(
                                                this.horse
                                                        .getNeeds()
                                                        .getHunger()
                                                        .getItemIndex(new ItemStack(SWEMBlocks.QUALITY_BALE.get().asItem())))
                                        < this.horse
                                        .getNeeds()
                                        .getHunger()
                                        .getMaxTimesFed(
                                                this.horse
                                                        .getNeeds()
                                                        .getHunger()
                                                        .getItemIndex(new ItemStack(SWEMBlocks.QUALITY_BALE.get().asItem())))) {
                                    slowFeederPos.add(checkPos);
                                }
                            }
                        } else if (checkState.getBlock() instanceof PaddockFeederBlock) {
                            if (this.horse.level.getBlockState(checkPos).getValue(PaddockFeederBlock.LEVEL) > 0) {
                                if (this.horse
                                        .getNeeds()
                                        .getHunger()
                                        .getTimesFed(
                                                this.horse
                                                        .getNeeds()
                                                        .getHunger()
                                                        .getItemIndex(new ItemStack(SWEMBlocks.QUALITY_BALE.get().asItem())))
                                        < this.horse
                                        .getNeeds()
                                        .getHunger()
                                        .getMaxTimesFed(
                                                this.horse
                                                        .getNeeds()
                                                        .getHunger()
                                                        .getItemIndex(new ItemStack(SWEMBlocks.QUALITY_BALE.get().asItem())))) {
                                    paddockFeederPos.add(checkPos);
                                }
                            }
                        } else if (checkState.getBlock() instanceof GrainFeederBlock) {
                            if (checkState.getValue(GrainFeederBlock.OCCUPIED)) {
                                if (this.horse
                                        .getNeeds()
                                        .getHunger()
                                        .getTimesFed(
                                                this.horse
                                                        .getNeeds()
                                                        .getHunger()
                                                        .getItemIndex(new ItemStack(SWEMItems.SWEET_FEED.get())))
                                        < this.horse
                                        .getNeeds()
                                        .getHunger()
                                        .getMaxTimesFed(
                                                this.horse
                                                        .getNeeds()
                                                        .getHunger()
                                                        .getItemIndex(new ItemStack(SWEMItems.SWEET_FEED.get())))) {
                                    grainFeederPos.add(checkPos);
                                }
                            }
                        }
                    }
                }
            }

            this.foundFood =
                    !grainFeederPos.isEmpty()
                            ? grainFeederPos.get(this.horse.getRandom().nextInt(grainFeederPos.size()))
                            : !slowFeederPos.isEmpty()
                            ? slowFeederPos.get(this.horse.getRandom().nextInt(slowFeederPos.size()))
                            : !qualityBalePos.isEmpty()
                            ? qualityBalePos.get(this.horse.getRandom().nextInt(qualityBalePos.size()))
                            : !paddockFeederPos.isEmpty()
                            ? paddockFeederPos.get(
                            this.horse.getRandom().nextInt(paddockFeederPos.size()))
                            : !grassPos.isEmpty()
                            ? grassPos.get(this.horse.getRandom().nextInt(grassPos.size()))
                            : null;
            if (foundFood == null) {
                this.movingTimer++;
                if (this.movingToPos == null) {
                    BlockPos goingToPos =
                            new BlockPos(
                                    entityPos.getX() + this.horse.getRandom().nextInt(14) - 7,
                                    entityPos.getY(),
                                    entityPos.getZ() + this.horse.getRandom().nextInt(14) - 7);
                    this.horse
                            .getNavigation()
                            .moveTo(goingToPos.getX(), goingToPos.getY(), goingToPos.getZ(), this.speed);
                    this.movingToPos = goingToPos;
                } else {
                    if (this.horse.blockPosition().closerThan(this.movingToPos, 2)) {
                        this.movingToPos = null;
                    } else {
                        if (this.movingTimer > 200) {
                            this.movingTimer = 0;
                            this.movingToPos = null;
                        } else {
                            this.horse
                                    .getNavigation()
                                    .moveTo(
                                            this.movingToPos.getX(),
                                            this.movingToPos.getY(),
                                            this.movingToPos.getZ(),
                                            this.speed);
                        }
                    }
                }
            }
        } else {
            this.movingTimer++;
            if (this.horse.blockPosition().closerThan(this.foundFood, 2)) {
                BlockState foundState = this.horse.level.getBlockState(this.foundFood);
                if (foundState.getBlock() == Blocks.GRASS_BLOCK) {
                    this.horse.getNeeds().getHunger().addPoints(new ItemStack(Items.GRASS_BLOCK));
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(
                            this.horse.level, this.horse)) {
                        this.horse.level.levelEvent(
                                2001, foundFood, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
                        this.horse.level.setBlock(foundFood, Blocks.DIRT.defaultBlockState(), 3);
                    }

                } else if (foundState.getBlock() == SWEMBlocks.QUALITY_BALE.get()) {

                    if (this.horse
                            .getNeeds()
                            .getHunger()
                            .addPoints(new ItemStack(SWEMBlocks.QUALITY_BALE.get().asItem()))) {
                        this.horse.level.setBlock(
                                foundFood, SWEMBlocks.QUALITY_BALE_SLAB.get().defaultBlockState(), 3);
                    }

                } else if (foundState.getBlock() == SWEMBlocks.QUALITY_BALE_SLAB.get()) {
                    if (this.horse
                            .getNeeds()
                            .getHunger()
                            .addPoints(new ItemStack(SWEMBlocks.QUALITY_BALE.get().asItem()))) {
                        if (foundState.getValue(SlabBlock.TYPE) != SlabType.DOUBLE) {
                            this.horse.level.setBlock(foundFood, Blocks.AIR.defaultBlockState(), 3);
                        } else {
                            this.horse.level.setBlock(
                                    foundFood, foundState.setValue(SlabBlock.TYPE, SlabType.BOTTOM), 3);
                        }
                    }
                } else if (foundState.getBlock() instanceof SlowFeederBlock) {

                    if (this.horse
                            .getNeeds()
                            .getHunger()
                            .addPoints(new ItemStack(SWEMBlocks.QUALITY_BALE.get().asItem()))) {
                        ((SlowFeederBlock) this.horse.level.getBlockState(foundFood).getBlock())
                                .eat(this.horse.level, foundFood, foundState);
                    }

                } else if (foundState.getBlock() instanceof GrainFeederBlock) {

                    if (this.horse
                            .getNeeds()
                            .getHunger()
                            .addPoints(new ItemStack(SWEMItems.SWEET_FEED.get()))) {
                        ((GrainFeederBlock) this.horse.level.getBlockState(foundFood).getBlock())
                                .eat(this.horse.level, foundFood, this.horse.level.getBlockState(foundFood));
                    }
                } else if (foundState.getBlock() instanceof PaddockFeederBlock) {
                    if (this.horse
                            .getNeeds()
                            .getHunger()
                            .addPoints(new ItemStack(SWEMBlocks.QUALITY_BALE.get().asItem()))) {
                        ((PaddockFeederBlock) this.horse.level.getBlockState(foundFood).getBlock())
                                .eat(this.horse.level, foundFood, this.horse.level.getBlockState(foundFood));
                    }
                }

                this.stop();

            } else {
                if (this.movingTimer >= 200) {
                    this.stop();
                } else {
                    this.horse
                            .getNavigation()
                            .moveTo(foundFood.getX(), foundFood.getY(), foundFood.getZ(), this.speed);
                }
            }
        }
    }
}
