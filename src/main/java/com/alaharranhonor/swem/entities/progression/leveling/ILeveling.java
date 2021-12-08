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
	int level = 0;
	int maxLevel = 0;
	float xp = 0.0f;
	float[] requiredXpArray = new float[0];
	String[] levelNames = new String[0];

	boolean addXP(float amount);

	boolean checkLevelUp();

	void levelUp();

	int getLevel();

	int getMaxLevel();

	float getXp();

	float getRequiredXp();

	String getLevelName();

	void write(CompoundNBT compound);

	void read(CompoundNBT compound);
}
