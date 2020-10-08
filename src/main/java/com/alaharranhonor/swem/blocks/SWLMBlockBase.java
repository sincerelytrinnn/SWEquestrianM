package com.alaharranhonor.swem.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class SWLMBlockBase extends Block {
    public SWLMBlockBase(MaterialColor adobe, Properties properties) {
        super(properties);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 15;
    }
}
