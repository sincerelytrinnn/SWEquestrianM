package com.alaharranhonor.swem.entities.progression.leveling;

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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

public class HealthLeveling implements ILeveling {

    public static final DataParameter<Integer> LEVEL =
            EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);
    public static final DataParameter<Float> XP =
            EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.FLOAT);
    private final SWEMHorseEntityBase horse;
    private final EntityDataManager dataManager;
    private final float[] requiredXpArray;
    private final String[] levelNames =
            new String[]{"Health I", "Health II", "Health III", "Health IV", "Health V"};

    /**
     * Instantiates a new Health leveling.
     *
     * @param horse the horse
     */
    public HealthLeveling(SWEMHorseEntityBase horse) {
        this.horse = horse;
        this.dataManager = this.horse.getEntityData();
        this.requiredXpArray =
                new float[]{
                        ConfigHolder.SERVER.maxHealthXP.get() * 0.1f,
                        ConfigHolder.SERVER.maxHealthXP.get() * 0.225f,
                        ConfigHolder.SERVER.maxHealthXP.get() * 0.3f,
                        ConfigHolder.SERVER.maxHealthXP.get() * 0.375f
                };
    }

    @Override
    public boolean addXP(float amount) {
        if (this.getLevel() == this.getMaxLevel()) return false;
        this.setXp(this.getXp() + amount);
        return this.checkLevelUp();
    }

    @Override
    public void removeXp(float amount) {
        this.setXp(this.getXp() - amount);
    }

    @Override
    public boolean checkLevelUp() {
        if (this.getXp() >= this.getRequiredXp() && this.getLevel() < this.getMaxLevel()) {
            this.levelUp();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void levelUp() {
        float excessXP = this.getXp() - this.getRequiredXp();
        this.setLevel(this.getLevel() + 1);
        this.setXp(excessXP);

        this.horse.levelUpHealth();
    }

    @Override
    public int getLevel() {
        return this.dataManager.get(LEVEL);
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.dataManager.set(LEVEL, level);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public float getXp() {
        return this.dataManager.get(XP);
    }

    /**
     * Sets xp.
     *
     * @param xp the xp
     */
    public void setXp(float xp) {
        if (xp < 0) {
            xp = 0;
        }
        this.dataManager.set(XP, xp);
    }

    @Override
    public float getRequiredXp() {
        if (this.getLevel() == this.getMaxLevel()) {
            return -1.0f;
        }
        return this.requiredXpArray[this.dataManager.get(LEVEL)];
    }

    @Override
    public String getLevelName() {
        return this.levelNames[this.dataManager.get(LEVEL)];
    }

    @Override
    public void write(CompoundNBT compound) {
        compound.putInt("HealthLevel", this.dataManager.get(LEVEL));
        compound.putFloat("HealthXP", this.dataManager.get(XP));
    }

    @Override
    public void read(CompoundNBT compound) {
        if (compound.contains("HealthLevel")) {
            this.setLevel(compound.getInt("HealthLevel"));
        }
        if (compound.contains("HealthXP")) {
            this.setXp(compound.getFloat("HealthXP"));
        }
    }
}
