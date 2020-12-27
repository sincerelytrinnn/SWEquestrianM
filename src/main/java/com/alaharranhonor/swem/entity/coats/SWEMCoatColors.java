package com.alaharranhonor.swem.entity.coats;

import java.util.*;

public enum SWEMCoatColors {
	WHITE(0),
	GRAY(1),
	BLACK(2),
	DARKBROWN(3),
	BROWN(4),
	REDROAN(5),
	BUCKSKIN(6),
	PAINT(7),
	PALOMINO(8),
	NOBUCKLE(9),
	WILDANDFREE(10),
	TALLDARKHANDSOME(11),
	SWEETBOY(12),
	APPY(13),
	GOLDEN(14),
	LEOPARD(15),
	GALAXY(16),
	RAINBOW(17);

	private static final SWEMCoatColors[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(SWEMCoatColors::getId)).toArray(SWEMCoatColors[]::new);
	private final int id;
	SWEMCoatColors(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static SWEMCoatColors getById(int id) {
		return VALUES[id % VALUES.length];
	}



}
