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
import net.minecraft.nbt.CompoundNBT;

public class NeedManager {

	private final SWEMHorseEntityBase horse;
	private final ThirstNeed thirst;
	private final HungerNeed hunger;

	/**
	 * Instantiates a new Need manager.
	 *
	 * @param horse the horse
	 */
	public NeedManager(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.thirst = new ThirstNeed(horse);
		this.hunger = new HungerNeed(horse);

	}

	/**
	 * Gets thirst.
	 *
	 * @return the thirst
	 */
	public ThirstNeed getThirst() {
		return thirst;
	}

	/**
	 * Gets hunger.
	 *
	 * @return the hunger
	 */
	public HungerNeed getHunger() {
		return hunger;
	}

	/**
	 * Read.
	 *
	 * @param nbt the nbt
	 */
	public void read(CompoundNBT nbt) {
		this.thirst.read(nbt);
		this.hunger.read(nbt);
	}

	/**
	 * Write compound nbt.
	 *
	 * @param nbt the nbt
	 * @return the compound nbt
	 */
	public CompoundNBT write(CompoundNBT nbt) {
		nbt = this.thirst.write(nbt);
		nbt = this.hunger.write(nbt);
		return nbt;
	}

	/**
	 * Tick.
	 */
// SERVER-SIDE ONLY
	public void tick() {
		if (this.horse.isBaby()) return;
		if (ConfigHolder.SERVER.serverTickFoodNeed.get())
			this.hunger.tick();
		if (ConfigHolder.SERVER.serverTickWaterNeed.get())
			this.thirst.tick();
	}
}
