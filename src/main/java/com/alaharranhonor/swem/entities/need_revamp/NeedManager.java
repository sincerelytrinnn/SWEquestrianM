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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeedManager {

	private SWEMHorseEntityBase horse;
	private Map<String, INeed> needs;

	public NeedManager(SWEMHorseEntityBase horse) {
		this.horse = horse;
		this.needs = new HashMap<>();
	}

	public void addNeed(String value, INeed need) {
		this.needs.put(value, need);
	}

	@Nullable
	public INeed getNeed(String value) {
		return this.needs.get(value);
	}

	public boolean interact(ItemStack stack) {
		for (INeed need : needs.values()) {
			boolean used = need.interact(stack);
			if (used) {
				return true;
			}
		}
		return false;
	}

	public void tick(int dayTimeTick) {
		for (INeed need : needs.values()) {
			List<Integer> checkTimes = need.getCheckTimes();
			if (checkTimes.contains(dayTimeTick)) {
				need.check(dayTimeTick);
			}
		}
	}

	public void write(CompoundNBT nbt) {
		for (INeed need : needs.values()) {
			need.write(nbt);
		}
	}

	public void read(CompoundNBT nbt) {
		for (INeed need : needs.values()) {
			need.read(nbt);
		}
	}
}
