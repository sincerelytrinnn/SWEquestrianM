package com.alaharranhonor.swem.blocks;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.BlockStateProperties;

public class SWEMBlockStateProperties extends BlockStateProperties {
	public static final EnumProperty<HitchingPostBase.PostPart> POST_PART = EnumProperty.create("post_part", HitchingPostBase.PostPart.class);
	public static final EnumProperty<FenceBaseBlock.FencePart> FENCE_PART = EnumProperty.create("fence_part", FenceBaseBlock.FencePart.class);
	public static final BooleanProperty FULL_FENCE = BooleanProperty.create("full_fence");
	public static final BooleanProperty HALF_FENCE = BooleanProperty.create("half_fence");
}
