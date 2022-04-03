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

import net.minecraft.nbt.CompoundNBT;

public interface ILeveling {

	/**
	 * Add xp boolean.
	 *
	 * @param amount the amount
	 * @return the boolean
	 */
	boolean addXP(float amount);

	/**
	 * Remove xp.
	 *
	 * @param amount the amount
	 */
	void removeXp(float amount);

	/**
	 * Check level up boolean.
	 *
	 * @return the boolean
	 */
	boolean checkLevelUp();

	/**
	 * Level up.
	 */
	void levelUp();

	/**
	 * Gets level.
	 *
	 * @return the level
	 */
	int getLevel();

	/**
	 * Gets max level.
	 *
	 * @return the max level
	 */
	int getMaxLevel();

	/**
	 * Gets xp.
	 *
	 * @return the xp
	 */
	float getXp();

	/**
	 * Gets required xp.
	 *
	 * @return the required xp
	 */
	float getRequiredXp();

	/**
	 * Gets level name.
	 *
	 * @return the level name
	 */
	String getLevelName();

	/**
	 * Write.
	 *
	 * @param compound the compound
	 */
	void write(CompoundNBT compound);

	/**
	 * Read.
	 *
	 * @param compound the compound
	 */
	void read(CompoundNBT compound);
}
