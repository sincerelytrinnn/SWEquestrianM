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
import net.minecraft.network.datasync.EntityDataManager;

import static com.alaharranhonor.swem.entities.SWEMHorseEntityBase.SPEED_LEVEL;
import static com.alaharranhonor.swem.entities.SWEMHorseEntityBase.SPEED_XP;

public class SpeedLeveling implements ILeveling {


    private final SWEMHorseEntityBase horse;
    private final EntityDataManager dataManager;
    private final float[] requiredXpArray;
    private final String[] levelNames =
        new String[]{"Speed I", "Speed II", "Speed III", "Speed IV", "Speed V"};

    /**
     * Instantiates a new Speed leveling.
     *
     * @param horse the horse
     */
    public SpeedLeveling(SWEMHorseEntityBase horse) {
        this.horse = horse;
        this.dataManager = this.horse.getEntityData();
        this.requiredXpArray =
            new float[]{
                ConfigHolder.SERVER.maxSpeedXP.get() * 0.1f,
                ConfigHolder.SERVER.maxSpeedXP.get() * 0.225f,
                ConfigHolder.SERVER.maxSpeedXP.get() * 0.3f,
                ConfigHolder.SERVER.maxSpeedXP.get() * 0.375f
            };
    }

    public boolean addXP(float amount) {
        if (this.getLevel() == this.getMaxLevel()) return false;
        this.dataManager.set(SPEED_XP, this.dataManager.get(SPEED_XP) + amount);
        return this.checkLevelUp();
    }

    @Override
    public void removeXp(float amount) {
        this.setXp(this.getXp() - amount);
    }

    public boolean checkLevelUp() {
        if (this.getXp() >= this.getRequiredXp() && this.getLevel() < this.getMaxLevel()) {
            this.levelUp();
            return true;
        } else {
            return false;
        }
    }

    public void levelUp() {
        float excessXP = this.getXp() - this.getRequiredXp();
        this.setLevel(this.getLevel() + 1);
        this.setXp(excessXP);
    }

    public int getLevel() {
        return this.dataManager.get(SPEED_LEVEL);
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.dataManager.set(SPEED_LEVEL, level);
    }

    public int getMaxLevel() {
        return 4;
    }

    public float getXp() {
        return this.dataManager.get(SPEED_XP);
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
        this.dataManager.set(SPEED_XP, xp);
    }

    public float getRequiredXp() {
        if (this.getLevel() == this.getMaxLevel()) {
            return -1.0f;
        }
        return this.requiredXpArray[this.dataManager.get(SPEED_LEVEL)];
    }

    public String getLevelName() {
        return this.levelNames[this.dataManager.get(SPEED_LEVEL)];
    }

    public void write(CompoundNBT compound) {
        compound.putInt("SpeedLevel", this.dataManager.get(SPEED_LEVEL));
        compound.putFloat("SpeedXP", this.dataManager.get(SPEED_XP));
    }

    public void read(CompoundNBT compound) {
        if (compound.contains("SpeedLevel")) {
            this.setLevel(compound.getInt("SpeedLevel"));
        }
        if (compound.contains("SpeedXP")) {
            this.setXp(compound.getFloat("SpeedXP"));
        }
    }
}
