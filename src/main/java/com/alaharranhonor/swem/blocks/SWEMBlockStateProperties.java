package com.alaharranhonor.swem.blocks;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IStringSerializable;

public class SWEMBlockStateProperties extends BlockStateProperties {
	public static final EnumProperty<HitchingPostBase.PostPart> POST_PART = EnumProperty.create("post_part", HitchingPostBase.PostPart.class);
	public static final BooleanProperty HALF_FENCE = BooleanProperty.create("half_fence");

	public static final IntegerProperty LEVEL_0_2 = IntegerProperty.create("level", 0, 2);
	public static final IntegerProperty LEVEL_0_2_VANILLA = IntegerProperty.create("level_vanilla", 0, 2);

	public static final EnumProperty<DoubleBlockSide> D_SIDE = EnumProperty.create("side", DoubleBlockSide.class);
	public static final EnumProperty<TripleBlockSide> T_SIDE = EnumProperty.create("side", TripleBlockSide.class);
	public static final EnumProperty<TwoWay> TWO_WAY = EnumProperty.create("two_way", TwoWay.class);
	public static final BooleanProperty CONNECTED_LEFT = BooleanProperty.create("con_left");
	public static final BooleanProperty CONNECTED_RIGHT = BooleanProperty.create("con_right");
	public static final IntegerProperty LEVEL_0_12 = IntegerProperty.create("level", 0, 12);

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

		TwoWay(int id) {
			this.id = id;
		}

		public String toString() {
			return this.getSerializedName();
		}

		public String getSerializedName() {
			return this == SINGLE ? "single" : this == LEFT ? "left" : this == MIDDLE ? "middle" : "right";
		}

		public int getId() {
			return this.id;
		}
	}
}
