package com.alaharranhonor.swem.blocks;

import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.BlockStateProperties;

public class SWEMBlockStateProperties extends BlockStateProperties {
	public static final EnumProperty<HitchingPostBase.PostPart> POST_PART = EnumProperty.create("post_part", HitchingPostBase.PostPart.class);
}