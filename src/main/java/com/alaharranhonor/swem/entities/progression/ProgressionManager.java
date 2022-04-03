package com.alaharranhonor.swem.entities.progression;


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
import com.alaharranhonor.swem.entities.progression.leveling.AffinityLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.HealthLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.JumpLeveling;
import com.alaharranhonor.swem.entities.progression.leveling.SpeedLeveling;
import net.minecraft.nbt.CompoundNBT;

public class ProgressionManager {

	private SWEMHorseEntityBase horse;
	private SpeedLeveling speedLeveling;
	private JumpLeveling jumpLeveling;
	private HealthLeveling healthLeveling;
	private AffinityLeveling affinityLeveling;

	/**
	 * Instantiates a new Progression manager.
	 *
	 * @param horse the horse
	 */
	public ProgressionManager(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.speedLeveling = new SpeedLeveling(horse);
		this.jumpLeveling = new JumpLeveling(horse);
		this.healthLeveling = new HealthLeveling(horse);
		this.affinityLeveling = new AffinityLeveling(horse);
	}

	/**
	 * Write.
	 *
	 * @param compound the compound
	 */
	public void write(CompoundNBT compound) {
		this.speedLeveling.write(compound);
		this.jumpLeveling.write(compound);
		this.healthLeveling.write(compound);
		this.affinityLeveling.write(compound);
	}

	/**
	 * Read.
	 *
	 * @param compound the compound
	 */
	public void read(CompoundNBT compound) {
		this.speedLeveling.read(compound);
		this.jumpLeveling.read(compound);
		this.healthLeveling.read(compound);
		this.affinityLeveling.read(compound);
	}

	/**
	 * Gets speed leveling.
	 *
	 * @return the speed leveling
	 */
	public SpeedLeveling getSpeedLeveling() {
		return speedLeveling;
	}

	/**
	 * Gets jump leveling.
	 *
	 * @return the jump leveling
	 */
	public JumpLeveling getJumpLeveling() {
		return jumpLeveling;
	}

	/**
	 * Gets health leveling.
	 *
	 * @return the health leveling
	 */
	public HealthLeveling getHealthLeveling() {
		return healthLeveling;
	}

	/**
	 * Gets affinity leveling.
	 *
	 * @return the affinity leveling
	 */
	public AffinityLeveling getAffinityLeveling() {
		return affinityLeveling;
	}
}
