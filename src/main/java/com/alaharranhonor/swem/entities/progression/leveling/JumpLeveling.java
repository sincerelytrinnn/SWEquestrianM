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
import net.minecraft.util.math.MathHelper;

import static com.alaharranhonor.swem.entities.SWEMHorseEntityBase.JUMP_LEVEL;
import static com.alaharranhonor.swem.entities.SWEMHorseEntityBase.JUMP_XP;

public class JumpLeveling implements ILeveling {
    private final SWEMHorseEntityBase horse;
    private final EntityDataManager dataManager;
    private final float[] requiredXpArray;
    private final String[] levelNames = new String[]{"Jump I", "Jump II", "Jump III", "Jump IV", "Jump V"};

    /**
     * Instantiates a new Jump leveling.
     *
     * @param horse the horse
     */
    public JumpLeveling(SWEMHorseEntityBase horse) {
        this.horse = horse;
        this.dataManager = this.horse.getEntityData();
        this.requiredXpArray = new float[]{ConfigHolder.SERVER.maxHealthXP.get() * 0.1f, ConfigHolder.SERVER.maxHealthXP.get() * 0.225f, ConfigHolder.SERVER.maxHealthXP.get() * 0.3f, ConfigHolder.SERVER.maxHealthXP.get() * 0.375f};
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
        this.horse.levelUpJump();
        this.setXp(excessXP);
    }

    @Override
    public int getLevel() {
        return this.dataManager.get(JUMP_LEVEL);
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.dataManager.set(JUMP_LEVEL, MathHelper.clamp(level, 0, this.getMaxLevel()));
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public float getXp() {
        return this.dataManager.get(JUMP_XP);
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
        this.dataManager.set(JUMP_XP, xp);
    }

    @Override
    public float getRequiredXp() {
        if (this.getLevel() == this.getMaxLevel()) {
            return -1.0f;
        }
        return this.requiredXpArray[this.dataManager.get(JUMP_LEVEL)];
    }

    @Override
    public String getLevelName() {
        return this.levelNames[this.dataManager.get(JUMP_LEVEL)];
    }

    @Override
    public void write(CompoundNBT compound) {
        compound.putInt("JumpLevel", this.dataManager.get(JUMP_LEVEL));
        compound.putFloat("JumpXP", this.dataManager.get(JUMP_XP));
    }

    @Override
    public void read(CompoundNBT compound) {
        if (compound.contains("JumpLevel")) {
            this.setLevel(compound.getInt("JumpLevel"));
        }
        if (compound.contains("JumpXP")) {
            this.setXp(compound.getFloat("JumpXP"));
        }
    }
}
