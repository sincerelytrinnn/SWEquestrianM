package com.alaharranhonor.swem.entities.needs;

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

import com.alaharranhonor.swem.config.ConfigHolder;
import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import com.alaharranhonor.swem.util.registry.SWEMBlocks;
import com.alaharranhonor.swem.util.registry.SWEMItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HungerNeed {

    public static final DataParameter<Integer> TOTAL_TIMES_FED =
            EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);
    private final SWEMHorseEntityBase horse;

    private final ArrayList<Ingredient> FEEDS =
            new ArrayList<>(
                    Stream.of(
                                    Ingredient.of(Items.CARROT),
                                    Ingredient.of(Items.APPLE),
                                    Ingredient.of(SWEMItems.OAT_BUSHEL.get()),
                                    Ingredient.of(SWEMItems.TIMOTHY_BUSHEL.get()),
                                    Ingredient.of(SWEMItems.ALFALFA_BUSHEL.get()),
                                    Ingredient.of(SWEMBlocks.QUALITY_BALE_ITEM.get()),
                                    Ingredient.of(Items.GRASS_BLOCK),
                                    Ingredient.of(SWEMItems.SUGAR_CUBE.get()),
                                    Ingredient.of(SWEMItems.SWEET_FEED.get()))
                            .collect(Collectors.toList()));

    private final int[] POINTS_GIVEN = {1, 1, 5, 5, 5, 15, 1, 1, 15};
    private final int[] MAX_TIMES = {1, 1, 1, 4, 4, 1, -1, 1, 1};
    private HungerState state;
    private int[] TIMES_FED = new int[9];
    private int tickCounter;
    private int points;

    /**
     * Instantiates a new Hunger need.
     *
     * @param horse the horse
     */
    public HungerNeed(SWEMHorseEntityBase horse) {
        this.horse = horse;
        this.setState(HungerState.FULLY_FED);
        this.tickCounter =
                192_000 * (ConfigHolder.SERVER.multiplayerHungerThirst.get() ? 72 : 1); // 192_000;
    }

    /**
     * Tick.
     */
    public void tick() {
        if (this.tickCounter == 0) return;
        this.tickCounter--;
        if (this.tickCounter <= this.state.getTickAmountChange()
                && this.state != HungerState.STARVING) {
            this.setStateById(this.state.getId() - 1);
        }
    }

    /**
     * Add points boolean.
     *
     * @param itemstack the itemstack
     * @return the boolean
     */
    public boolean addPoints(ItemStack itemstack) {
        if (this.getTotalTimesFed() == 7 && itemstack.getItem() != Items.GRASS_BLOCK) {
            return false;
        }
        int itemIndex = this.getItemIndex(itemstack);

        // Early break causes.
        if (itemIndex == -1) return false; // Item was not a legal item.
        if (this.getMaxTimesFed(itemIndex) == this.getTimesFed(itemIndex))
            return false; // That type of item, has already been fed it's max times.
        int points = this.getPointsFromItem(itemIndex);

        if (itemstack.getItem() != Items.GRASS_BLOCK) {
            this.setTotalTimesFed(1);
        }

        this.TIMES_FED[itemIndex]++; // Increment the times fed values.

        // Set the points.
        this.points += points;

        if (this.checkIncrement()) {
            this.incrementState();
        }

        horse.progressionManager.getHealthLeveling().addXP(points);
        if (!horse.isSilent()) {
            horse.playSound(
                    SoundEvents.HORSE_EAT,
                    1.0F,
                    1.0F + (horse.getRandom().nextFloat() - horse.getRandom().nextFloat()) * 0.2F);
        }
        return true;
    }

    /**
     * Check increment boolean.
     *
     * @return the boolean
     */
    public boolean checkIncrement() {
        return this.points >= this.getNextState().getPointsRequired();
    }

    /**
     * Increment state.
     */
    public void incrementState() {
        if (this.state != HungerState.FULLY_FED) {
            this.points -= this.state.getPointsRequired();
            if (this.points < 0) {
                this.points = 0;
            }
            HungerState nextState = this.getNextState();
            this.setStateById(nextState.ordinal());
            if (nextState == HungerState.FULLY_FED) {
                this.tickCounter = 192_000 * (ConfigHolder.SERVER.multiplayerHungerThirst.get() ? 72 : 1);
            } else {
                this.tickCounter = getNextState().getTickAmountChange();
            }
        }
    }

    /**
     * Gets item index.
     *
     * @param itemstack the itemstack
     * @return the item index
     */
    public int getItemIndex(ItemStack itemstack) {
        int index = -1;
        for (int i = 0; i < FEEDS.size(); i++) {
            Ingredient ingredient = FEEDS.get(i);
            if (ingredient.test(itemstack)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Gets times fed.
     *
     * @param index the index
     * @return the times fed
     */
    public int getTimesFed(int index) {
        return this.TIMES_FED[index];
    }

    /**
     * Gets max times fed.
     *
     * @param index the index
     * @return the max times fed
     */
    public int getMaxTimesFed(int index) {
        return this.MAX_TIMES[index];
    }

    /**
     * Gets points from item.
     *
     * @param index the index
     * @return the points from item
     */
    private int getPointsFromItem(int index) {
        return this.POINTS_GIVEN[index];
    }

    /**
     * Gets total times fed.
     *
     * @return the total times fed
     */
    public int getTotalTimesFed() {
        return this.horse.getEntityData().get(TOTAL_TIMES_FED);
    }

    /**
     * Sets total times fed.
     *
     * @param amount the amount
     */
    private void setTotalTimesFed(int amount) {
        this.horse.getEntityData().set(TOTAL_TIMES_FED, this.getTotalTimesFed() + amount);
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public HungerState getState() {
        return this.state;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(HungerState state) {
        this.state = state;
        this.state.setHorse(this.horse);
    }

    /**
     * Gets next state.
     *
     * @return the next state
     */
    public HungerState getNextState() {
        int hungerId = this.state.getId() + 1;
        if (hungerId > 4) {
            hungerId = 4;
        }
        return HungerState.values()[hungerId];
    }

    /**
     * Write compound nbt.
     *
     * @param nbt the nbt
     * @return the compound nbt
     */
    public CompoundNBT write(CompoundNBT nbt) {
        if (this.state != null) {
            nbt.putInt("hungerStateID", this.state.getId());
            nbt.putInt(
                    "hungerTick",
                    ConfigHolder.SERVER.multiplayerHungerThirst.get()
                            ? this.tickCounter / 72
                            : this.tickCounter);
            nbt.putInt("hungerPoints", this.points);
            nbt.putInt("hungerTotalTimesFed", this.getTotalTimesFed());
            nbt.putIntArray("hungerTimesFed", this.TIMES_FED);
        }
        return nbt;
    }

    /**
     * Read.
     *
     * @param nbt the nbt
     */
    public void read(CompoundNBT nbt) {
        if (nbt.contains("hungerStateID")) {
            int stateId = nbt.getInt("hungerStateID");
            this.setStateById(stateId);
        } else {
            this.setStateById(4);
        }
        if (nbt.contains("hungerTick")) {
            int ticks = nbt.getInt("hungerTick");
            if (ticks == 0 && this.state != HungerState.STARVING) {
                ticks = getNextState().getTickAmountChange();
            }
            this.tickCounter = ConfigHolder.SERVER.multiplayerHungerThirst.get() ? ticks * 72 : ticks;
        }
        if (nbt.contains("hungerPoints")) {
            this.points = nbt.getInt("hungerPoints");
        }
        if (nbt.contains("hungerTotalTimesFed")) {
            int totalTimesFed = nbt.getInt("hungerTotalTimesFed");
            this.setTotalTimesFed(totalTimesFed);
        }
        if (nbt.contains("hungerTimesFed")) {
            this.TIMES_FED = nbt.getIntArray("hungerTimesFed");
        }
        this.state.setHorse(this.horse);
    }

    /**
     * Sets state by id.
     *
     * @param id the id
     */
    private void setStateById(int id) {
        switch (id) {
            case 0: {
                this.setState(HungerState.STARVING);
                this.horse.getEntityData().set(HungerState.ID, id);
                break;
            }
            case 1: {
                this.setState(HungerState.MALNOURISHED);
                this.horse.getEntityData().set(HungerState.ID, id);
                break;
            }
            case 2: {
                this.setState(HungerState.HUNGRY);
                this.horse.getEntityData().set(HungerState.ID, id);
                break;
            }
            case 3: {
                this.setState(HungerState.FED);
                this.horse.getEntityData().set(HungerState.ID, id);
                break;
            }
            case 4: {
                this.setState(HungerState.FULLY_FED);
                this.horse.getEntityData().set(HungerState.ID, id);
                break;
            }
            default: {
                this.setState(HungerState.FULLY_FED);
                this.horse.getEntityData().set(HungerState.ID, 4);
            }
        }
    }

    /**
     * Reset daily.
     */
    public void resetDaily() {
        this.horse.getEntityData().set(TOTAL_TIMES_FED, 0);
        Arrays.fill(this.TIMES_FED, 0);
    }

    public enum HungerState {
        STARVING(-1, -1),
        MALNOURISHED(72_000, 15),
        HUNGRY(144_000, 40),
        FED(168_000, 15),
        FULLY_FED(180_000, -1);

        public static final DataParameter<Integer> ID =
                EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);
        private final int tickAmountChange;
        private final int pointsRequired;
        private SWEMHorseEntityBase horse;

        /**
         * Instantiates a new Hunger state.
         *
         * @param tickAmountChange the tick amount change
         * @param pointsRequired   the points required
         */
        HungerState(int tickAmountChange, int pointsRequired) {
            this.tickAmountChange = tickAmountChange;
            this.pointsRequired = pointsRequired;
        }

        /**
         * Sets horse.
         *
         * @param horse the horse
         */
        public void setHorse(SWEMHorseEntityBase horse) {
            this.horse = horse;
        }

        /**
         * Gets id.
         *
         * @return the id
         */
        public int getId() {
            return this.horse.getEntityData().get(ID);
        }

        /**
         * Gets tick amount change.
         *
         * @return the tick amount change
         */
        public int getTickAmountChange() {
            return this.tickAmountChange * (ConfigHolder.SERVER.multiplayerHungerThirst.get() ? 72 : 1);
        }

        /**
         * Gets points required.
         *
         * @return the points required
         */
        public int getPointsRequired() {
            return this.pointsRequired;
        }
    }
}
