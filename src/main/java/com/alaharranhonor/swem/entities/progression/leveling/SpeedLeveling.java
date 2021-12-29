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

import com.alaharranhonor.swem.entities.SWEMHorseEntityBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

public class SpeedLeveling implements ILeveling {

	private SWEMHorseEntityBase horse;

	private EntityDataManager dataManager;
	public static final DataParameter<Integer> LEVEL = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.INT);
	public static final DataParameter<Float> XP = EntityDataManager.defineId(SWEMHorseEntityBase.class, DataSerializers.FLOAT);
	private float[] requiredXpArray = new float[]{500, 2000, 4000, 7000};
	private String[] levelNames = new String[]{"Speed I", "Speed II", "Speed III", "Speed IV", "Speed V"};

	public SpeedLeveling(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.dataManager = this.horse.getEntityData();
	}

	public boolean addXP(float amount) {
		if (this.getLevel() == this.getMaxLevel()) return false;
		this.dataManager.set(XP, this.dataManager.get(XP) + amount);
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
		return this.dataManager.get(LEVEL);
	}

	public void setLevel(int level) {
		this.dataManager.set(LEVEL, level);
	}

	public int getMaxLevel() {
		return 4;
	}

	public float getXp() {
		return this.dataManager.get(XP);
	}

	public void setXp(float xp) {
		if (xp < 0) {
			xp = 0;
		}
		this.dataManager.set(XP, xp);
	}

	public float getRequiredXp() {
		if (this.getLevel() == this.getMaxLevel()) {
			return -1.0f;
		}
		return this.requiredXpArray[this.dataManager.get(LEVEL)];
	}

	public String getLevelName() {
		return this.levelNames[this.dataManager.get(LEVEL)];
	}

	public void write(CompoundNBT compound) {
		compound.putInt("SpeedLevel", this.dataManager.get(LEVEL));
		compound.putFloat("SpeedXP", this.dataManager.get(XP));
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

