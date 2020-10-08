package com.alaharranhonor.swem.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SWLMGravelBlock extends FallingBlock {
    public SWLMGravelBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public int getDustColor(BlockState state, IBlockReader p_189876_2_, BlockPos p_189876_3_) {
        return -8356741;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 15;
    }
}
