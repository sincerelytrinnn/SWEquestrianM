package com.alaharranhonor.swem.entities.need_revamp.thirst;
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
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.BiConsumer;

public enum ThirstItem {

	// Grass Feeds.
	WATER_BUCKET(Ingredient.of(Items.WATER_BUCKET), 0, 1, 2, 3, "", ThirstItem::doNothing);


	private Ingredient item;
	private int categoryIndex;
	private int points;
	private int min;
	private int max;
	private String extraData;
	private BiConsumer<SWEMHorseEntityBase, ThirstItem> extraEffectMethod;
	ThirstItem(Ingredient item, int categoryIndex, int points, int min, int max, String data, BiConsumer<SWEMHorseEntityBase, ThirstItem> extraEffect) {
		this.item = item;
		this.categoryIndex = categoryIndex;
		this.points = points;
		this.min = min;
		this.max = max;
		this.extraData = data;
		this.extraEffectMethod = extraEffect;
	}

	public Ingredient getItem() {
		return item;
	}

	public int getCategoryIndex() {
		return categoryIndex;
	}

	public int getPoints() {
		return points;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public BiConsumer<SWEMHorseEntityBase, ThirstItem> getExtraEffectMethod() {
		return extraEffectMethod;
	}

	private static void doNothing(SWEMHorseEntityBase horse, ThirstItem item) {

	}

	private static void addAffinityPoints(SWEMHorseEntityBase horse, ThirstItem item) {
		int affinityPoints = Integer.parseInt(item.extraData);
	}
}
