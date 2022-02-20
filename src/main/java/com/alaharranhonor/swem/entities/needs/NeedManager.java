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

	private final ThirstNeed thirst;
	private final HungerNeed hunger;

	public NeedManager(SWEMHorseEntityBase horse) {
		this.thirst = new ThirstNeed(horse);
		this.hunger = new HungerNeed(horse);

	}

	public ThirstNeed getThirst() {
		return thirst;
	}

	public HungerNeed getHunger() {
		return hunger;
	}

	public void read(CompoundNBT nbt) {
		this.thirst.read(nbt);
		this.hunger.read(nbt);
	}

	public CompoundNBT write(CompoundNBT nbt) {
		nbt = this.thirst.write(nbt);
		nbt = this.hunger.write(nbt);
		return nbt;
	}

	// SERVER-SIDE ONLY
	public void tick() {
		if (ConfigHolder.SERVER.serverTickFoodNeed.get())
			this.hunger.tick();
		if (ConfigHolder.SERVER.serverTickWaterNeed.get())
			this.thirst.tick();
	}
}
