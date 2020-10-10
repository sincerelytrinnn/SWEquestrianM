package com.alaharranhonor.swem.util.HorseUtils;

import java.util.Arrays;
import java.util.Comparator;

public enum SWEMCoatColors {
    WHITE(0),
    CREAMY(1),
    CHESTNUT(2),
    BROWN(3),
    BLACK(4),
    GRAY(5),
    DARKBROWN(6);

    private static final SWEMCoatColors[] field_234251_h_ = Arrays.stream(values()).sorted(Comparator.comparingInt(SWEMCoatColors::func_234253_a_)).toArray((p_234255_0_) -> {
        return new SWEMCoatColors[p_234255_0_];
    });
    private final int field_234252_i_;

    private SWEMCoatColors(int p_i231559_3_) {
        this.field_234252_i_ = p_i231559_3_;
    }

    public int func_234253_a_() {
        return this.field_234252_i_;
    }

    public static SWEMCoatColors func_234254_a_(int p_234254_0_) {
        return field_234251_h_[p_234254_0_ % field_234251_h_.length];
    }
}
