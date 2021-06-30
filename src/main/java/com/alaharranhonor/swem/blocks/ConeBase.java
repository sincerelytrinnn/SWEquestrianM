package com.alaharranhonor.swem.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.stream.Stream;

public class ConeBase extends Block{

    private static final VoxelShape voxelShape = Stream.of(
            Block.makeCuboidShape(6, 13, 6, 10, 19, 10),
            Block.makeCuboidShape(5, 7, 5, 11, 13, 11),
            Block.makeCuboidShape(4, 1, 4, 12, 7, 12),
            Block.makeCuboidShape(2, 0, 2, 14, 1, 14),
            Block.makeCuboidShape(7, 19, 7, 9, 24, 9)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return voxelShape;
    }

    public ConeBase() {
        super(AbstractBlock.Properties.create(Material.IRON)
                .hardnessAndResistance(0f, 0.5f)
                .sound(SoundType.SCAFFOLDING)
                .harvestLevel(0)
        );
    }

    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 0.6f;
    }
}
