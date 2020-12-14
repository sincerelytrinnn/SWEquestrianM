package com.alaharranhonor.swem.blocks;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.IStringSerializable;

public class SWEMBlockStateProperties extends BlockStateProperties {
	public static final EnumProperty<HitchingPostBase.PostPart> POST_PART = EnumProperty.create("post_part", HitchingPostBase.PostPart.class);
	public static final BooleanProperty FULL_FENCE = BooleanProperty.create("full_fence");
	public static final BooleanProperty HALF_FENCE = BooleanProperty.create("half_fence");

	public static final IntegerProperty LEVEL_0_2 = IntegerProperty.create("level", 0, 2);
	public static final IntegerProperty LEVEL_0_2_VANILLA = IntegerProperty.create("level_vanilla", 0, 2);

	public static final EnumProperty<DoubleBlockSide> D_SIDE = EnumProperty.create("side", DoubleBlockSide.class);
	public static final EnumProperty<TripleBlockSide> T_SIDE = EnumProperty.create("side", TripleBlockSide.class);
	public static final EnumProperty<TwoWay> TWO_WAY = EnumProperty.create("two_way", TwoWay.class);

	public enum DoubleBlockSide implements IStringSerializable {
		LEFT,
		RIGHT;

		public String toString() {
			return this.getString();
		}

		public String getString() {
			return this == LEFT ? "left" : "right";
		}
	}

	public enum TripleBlockSide implements IStringSerializable {
		LEFT,
		MIDDLE,
		RIGHT;

		public String toString() {
			return this.getString();
		}

		public String getString() {
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
			return this.getString();
		}

		public String getString() {
			return this == SINGLE ? "single" : this == LEFT ? "left" : this == MIDDLE ? "middle" : "right";
		}

		public int getId() {
			return this.id;
		}
	}
}
