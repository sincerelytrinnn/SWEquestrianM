package com.alaharranhonor.swem.blocks;


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

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.IStringSerializable;

public class SWEMBlockStateProperties {
	public static final EnumProperty<HitchingPostBase.PostPart> POST_PART = EnumProperty.create("post_part", HitchingPostBase.PostPart.class);
	public static final BooleanProperty HALF_FENCE = BooleanProperty.create("half_fence");

	public static final IntegerProperty LEVEL_0_2 = IntegerProperty.create("level", 0, 2);

	public static final EnumProperty<DoubleBlockSide> D_SIDE = EnumProperty.create("side", DoubleBlockSide.class);
	public static final EnumProperty<TripleBlockSide> T_SIDE = EnumProperty.create("side", TripleBlockSide.class);
	public static final EnumProperty<TwoWay> TWO_WAY = EnumProperty.create("two_way", TwoWay.class);
	public static final BooleanProperty CONNECTED_LEFT = BooleanProperty.create("con_left");
	public static final BooleanProperty CONNECTED_RIGHT = BooleanProperty.create("con_right");
	public static final IntegerProperty LEVEL_0_16 = IntegerProperty.create("level", 0, 16);

	public enum DoubleBlockSide implements IStringSerializable {
		LEFT,
		RIGHT;

		public String toString() {
			return this.getSerializedName();
		}

		public String getSerializedName() {
			return this == LEFT ? "left" : "right";
		}
	}

	public enum TripleBlockSide implements IStringSerializable {
		LEFT, // For Jumps = OUTER // For standards = Bottom
		MIDDLE, // For Jumps = MIDDLE // For standards = middle
		RIGHT; // For Jumps = BETWEEN // For standards = top

		public String toString() {
			return this.getSerializedName();
		}

		public String getSerializedName() {
			return this == LEFT ? "left" : this == MIDDLE ? "middle" : "right";
		}
	}

	public enum TwoWay implements IStringSerializable {
		SINGLE(0),
		LEFT(1),
		MIDDLE(2),
		RIGHT(3);

		private int id;

		/**
		 * Instantiates a new Two way.
		 *
		 * @param id the id
		 */
		TwoWay(int id) {
			this.id = id;
		}

		public String toString() {
			return this.getSerializedName();
		}

		public String getSerializedName() {
			return this == SINGLE ? "single" : this == LEFT ? "left" : this == MIDDLE ? "middle" : "right";
		}

		/**
		 * Gets id.
		 *
		 * @return the id
		 */
		public int getId() {
			return this.id;
		}
	}
}
