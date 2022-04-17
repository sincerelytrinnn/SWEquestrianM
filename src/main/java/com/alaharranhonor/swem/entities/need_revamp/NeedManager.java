package com.alaharranhonor.swem.entities.need_revamp;
/*
 * All Rights Reserved
 *
 * Copyright (c) 2022, AlaharranHonor, Legenden.
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
import com.alaharranhonor.swem.util.SWEMUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;

public class NeedManager {

	private SWEMHorseEntityBase horse;
	private List<INeed> needs;

	public NeedManager(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.needs = new ArrayList<>();
	}

	public void addNeed(INeed need) {
		this.needs.add(need);
	}

	public boolean interact(ItemStack stack) {
		for (INeed need : needs) {
			boolean used = need.interact(stack);
			if (used) {
				SWEMUtil.damageOrShrink(stack);
				return true;
			}
		}
		return false;
	}

	public void tick(int dayTimeTick) {
		for (INeed need : needs) {
			List<Integer> checkTimes = need.getCheckTimes();
			if (checkTimes.contains(dayTimeTick)) {
				need.check(dayTimeTick);
			}
		}
	}

	public void write(CompoundNBT nbt) {
		for (INeed need : needs) {
			need.write(nbt);
		}
	}

	public void read(CompoundNBT nbt) {
		for (INeed need : needs) {
			need.read(nbt);
		}
	}
}
